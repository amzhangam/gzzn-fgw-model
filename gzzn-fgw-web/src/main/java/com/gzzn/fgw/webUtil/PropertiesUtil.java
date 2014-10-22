package com.gzzn.fgw.webUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	/**
	 * 增加属性文件值
	 * 
	 * @param key
	 * @param value
	 */
	public static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static void addProperties(String key[], String value[], String file) {
		Properties iniFile = getProperties(file);
		FileOutputStream oFile = null;
		try {
			iniFile.put(key, value);
			oFile = new FileOutputStream(file, true);
			iniFile.store(oFile, "modify properties file");
		} catch (FileNotFoundException e) {
			logger.warn("do " + file + " FileNotFoundException:", e);
		} catch (IOException e) {
			logger.warn("do " + file + " IOException:", e);
		} finally {
			try {
				if (oFile != null) {
					oFile.close();
				}
			} catch (IOException e) {
				logger.warn("do " + file + " IOException:", e);
			}
		}
	}

	/**
	 * 读取配置文件
	 * 
	 * @return
	 */
	public static Properties getProperties(String filename) {
		Properties pro = null;
		try {
			URL url = PropertiesUtil.class.getClassLoader().getResource(filename);
			if(url!=null){
				pro = new Properties();
				pro.load(url.openStream());
			}

		} 
		catch (IOException e) {
			
			logger.info("try to load report file error:"+e.getMessage());
			
			e.printStackTrace();
			
		}
		return pro;
	}

	/**
	 * 保存属性到文件中
	 * 
	 * @param pro
	 * @param file
	 */
	public static void saveProperties(Properties pro, String file) {
		if (pro == null) {
			return;
		}
		FileOutputStream oFile = null;
		try {
			oFile = new FileOutputStream(file, false);
			pro.store(oFile, "modify properties file");
		} catch (FileNotFoundException e) {
			logger.warn("do " + file + " FileNotFoundException:", e);
		} catch (IOException e) {
			logger.warn("do " + file + " IOException:", e);
		} finally {
			try {
				if (oFile != null) {
					oFile.close();
				}
			} catch (IOException e) {
				logger.warn("do " + file + " IOException:", e);
			}
		}
	}

	/**
	 * 修改属性文件
	 * 
	 * @param key
	 * @param value
	 */
	public static void updateProperties(String key, String value, String file) {
		// key为空则返回
		if (key == null || "".equalsIgnoreCase(key)) {
			return;
		}
		Properties pro = getProperties(file);
		if (pro == null) {
			pro = new Properties();
		}
		pro.put(key, value);

		// 保存属性到文件中
		saveProperties(pro, file);
	}

}
