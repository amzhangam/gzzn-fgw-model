package com.gzzn.fgw.quartz;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>Title: QuartzMain</p>
 * <p>Description: 调度器入口类，自动扫描所有{@link com.gzzn.fgw.quartz.CronJobDetail}对象</p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author yjf
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-11-27 下午5:49:37  yjf    new
 */
@Component
@Lazy(false)
public class QuartzMain {
	private static Logger logger = LoggerFactory.getLogger(QuartzMain.class);

	private static Scheduler sched;

	@Resource
	private ApplicationContext context;

	@PostConstruct
	public void startQuartz() {
		logger.info("进入调度器启动入口");
		try {
			sched = StdSchedulerFactory.getDefaultScheduler();

			Map<String, CronJobDetail> map = context.getBeansOfType(CronJobDetail.class);
			for (Entry<String, CronJobDetail> e : map.entrySet()) {
				logger.info("扫描并添加新任务['{}']", e.getKey());
				sched.scheduleJob(e.getValue().getObject(), createTrigger(e));
			}

			sched.start();

		} catch (SchedulerException e) {
			logger.error("", e);
		}
	}

	private CronTrigger createTrigger(Entry<String, CronJobDetail> e) {
		CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(e.getValue()
				.getCronExpression());
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(
				e.getKey());

		return triggerBuilder.withSchedule(cron).build();
	}

	@PreDestroy
	public void shutdown() {
		if (sched != null) {
			try {
				sched.shutdown(true);
			} catch (SchedulerException e) {
				logger.error("", e);
			}
		}
	}
}
