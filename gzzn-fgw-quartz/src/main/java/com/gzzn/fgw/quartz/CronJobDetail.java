package com.gzzn.fgw.quartz;

import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

public class CronJobDetail extends MethodInvokingJobDetailFactoryBean {

	private String cronExpression;

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
