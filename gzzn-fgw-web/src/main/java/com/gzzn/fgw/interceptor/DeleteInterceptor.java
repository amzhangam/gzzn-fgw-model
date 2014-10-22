package com.gzzn.fgw.interceptor;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class DeleteInterceptor extends AbstractInterceptor implements Serializable{

	private static final long serialVersionUID = 469248215534109146L;
	private Logger logger = Logger.getLogger(DeleteInterceptor.class);
	@Autowired
	private ICommonService commonService;

	public String intercept(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getAction().getClass().getName();
		logger.info("action name:"+actionName);
		Map<String,Object> map = invocation.getInvocationContext().getParameters();
		String[] projectids = (String[]) map.get("ids");
//		logger.info("projectids="+((projectids!=null&&projectids.length>0)?projectids[0]:""));
		
		Map session = (Map) invocation.getInvocationContext().getSession();
//		logger.info("get session :"+session);
		if (session != null) {
			SysUser user = (SysUser) session.get(CommonFiled.SESSION_LOGIN_USER);
//			logger.info("get user :"+user);
			if(actionName!=null&&actionName.indexOf("ProjectbaseinfoAction")>-1){
				
				if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){
					return invocation.invoke();
				}
				else{
					Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.valueOf(projectids[0]));
					if(pjbaseinfo!=null&&pjbaseinfo.getSysOrganizationByRecordOrgan()!=null&&pjbaseinfo.getSysOrganizationByRecordOrgan().getOrganizationId().equals(user.getSysOrganization().getOrganizationId())){
						return invocation.invoke();
					}
				}
			}
			else{
				if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){
					return invocation.invoke();
				}
			}
		}
		
		return "deleteDenied";
	}


}
