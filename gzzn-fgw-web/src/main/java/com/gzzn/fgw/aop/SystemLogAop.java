package com.gzzn.fgw.aop;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javassist.expr.NewArray;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import oracle.net.aso.e;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysOperationlog;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbHylb;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.XmsbZjxz;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;

/**
 * <p>Title: OperationLogAndExceptionAop</p>
 * <p>Description: 异常处理,系统日志,操作日志</p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-23下午5:48:33  ChengZhi    new<p>
 */
@Aspect
@Component
public class SystemLogAop {

	private static Logger logger = LoggerFactory.getLogger(SystemLogAop.class);
	
	private Properties prop = PropertiesUtil.getProperties("projectProperty.properties");
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private ICommonService commonService;

	@Pointcut("@annotation(com.gzzn.fgw.aop.GzznLog)")
	public void action() {

	}

	@Before("action()")
	public void actionBefore(JoinPoint joinpoint) {
		//		logger.info("页面访问url={}", ServletActionContext.getRequest().getServletPath());
	}

	/**
	 * 系统记录
	 * @param joinpoint
	 */
	@After("action()")
	public void actionAfter(JoinPoint pjp) {
		logger.info("进入日志方法......");
		LogObject logObject = (LogObject) ServletActionContext.getRequest().getAttribute("logObject");
		if(logObject == null){
			logger.warn("未找到LogObject对象" + pjp.getTarget());
			return;
		}
		
		String operationContent = getOperationContent(pjp, logObject);
		
		SysUser user = (SysUser) ServletActionContext.getRequest().getSession()
				.getAttribute(CommonFiled.SESSION_LOGIN_USER);
		
		//用于记录用户注册信息
		if(user==null && logObject.getSysUser()!=null){ user = logObject.getSysUser();}

		if (user!= null && StringUtils.isNotEmpty(operationContent)) {
			//记录用户操作日志
			SysOperationlog operationlog = new SysOperationlog();
			if(user.getSysOrganization()!=null){
				operationlog.setSysOrganization(user.getSysOrganization());
			}
			if (user.getSysDept() != null) {
				operationlog.setSysDept(user.getSysDept());
			}
			//operationlog.setOperationContent(operationContent);
			operationlog.setSysUser(user);
			operationlog.setOperationDatetime(new Date());
			operationlog.setOperationContent(operationContent);
			commonService.save(operationlog);
			
			
			MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
			Method method = joinPointObject.getMethod();
			GzznLog annotation = method.getAnnotation(GzznLog.class);
			switch (annotation.value()) {
			case DELETE:
			{
				operationContent = "删除了" + logObject.getEntityName() + "，" + "名称是：" + logObject.getObjectName() + "，Id为：" + logObject.getObjectId();
				//如果logType = pjLog；同时需要记录项目日志
				if(logObject.getLogType()!=null && logObject.getLogType().equalsIgnoreCase("pjLog")){
					
					SysProjectlog projectlog = new SysProjectlog();
					if(user.getSysOrganization()!=null){
						projectlog.setSysOrganization(user.getSysOrganization());
					}
					if (user.getSysDept() != null) {
						projectlog.setSysDept(user.getSysDept());
					}
					if(logObject.getBeforePjbaseinfo()!=null){//项目修改日志
						
					}
					else{//项目其他日志
						try {
							projectlog.setOperationContent(operationContent.getBytes("UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					projectlog.setSysUser(user);
					projectlog.setOperationDatetime(new Date());//new Timestamp(System.currentTimeMillis())
					projectlog.setPjbaseinfo(logObject.getPjbaseinfo());
					projectlog.setRead(logObject.getRead());
					commonService.save(projectlog);
				}
				break;
			}
			default:
				break;
			}
				
			
		}
	}
	
	

	private String getOperationContent(JoinPoint pjp, LogObject logObject) {
		String operationContent = "";
		MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();
		GzznLog annotation = method.getAnnotation(GzznLog.class);
		switch (annotation.value()) {
		case DELETE:
			operationContent = "删除了" + logObject.getEntityName() + "，" + "名称是：" + logObject.getObjectName() + "，Id为：" + logObject.getObjectId();
			break;
		case SAVE:
			operationContent = "新增或修改了" + logObject.getEntityName() + "，" + "名称是：" + logObject.getObjectName() + "，Id为："
					+ logObject.getObjectId();
			break;
		case OTHER:
			operationContent = logObject.getOperationContent();
			break;
		default:
			break;
		}
		logger.debug(operationContent);
		
		return operationContent;
	}
	
	
}
