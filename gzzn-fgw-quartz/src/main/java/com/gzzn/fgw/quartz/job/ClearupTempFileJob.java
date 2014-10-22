package com.gzzn.fgw.quartz.job;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClearupTempFileJob {
	private static Logger logger = LoggerFactory.getLogger(ClearupTempFileJob.class);

	private static long DATE_UNIT = 1000 * 60 * 60 * 24;//一天的毫秒数

	@Value("${clearup.temp.dir}")
	private String tempDir;
	@Value("${clearup.temp.deadline}")
	private int deadline;

	synchronized public void execute() {
		logger.info("开始执行清理临时文件..tempDir={}", tempDir);
		if (StringUtils.isNotEmpty(tempDir)) {
			for (String s : tempDir.split("[;, ]")) {
				File dir = new File(s);
				if (dir.exists() && dir.isDirectory()) {
					logger.info("清理目录下[{}]的{}天前的文件：", s, deadline);
					try {
						for (File f : dir.listFiles(getFileFilter())) {
							FileUtils.deleteQuietly(f);
						}
					} catch (Exception e) {
						logger.warn("", e);
					}
				}
			}
		}
		logger.info("结束执行清理临时文件.");
	}

	private FileFilter getFileFilter() {
		return new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				long last = pathname.lastModified();
				return (System.currentTimeMillis() - last) / (DATE_UNIT * deadline) > 1;
			}

		};
	}
}
