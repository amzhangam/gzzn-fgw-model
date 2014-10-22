package com.gzzn.fgw.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gzzn.util.common.LogUtil;
import com.gzzn.util.properties.PropertiesHelper;

public class StartupApplicationContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
//		LogUtil.getLogger().debug("加载项目基本属性设置文件");

		//el表达式获取${configObj["jsp.layout.default.top.logofont1"]} 
		servletContextEvent.getServletContext().setAttribute("configObj",
				PropertiesHelper.getInstance().getDefault());
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
