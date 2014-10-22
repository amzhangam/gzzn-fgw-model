package com.gzzn.fgw.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.fgw.webUtil.RandomValidateCode;
import com.gzzn.fgw.webUtil.ValidateUtil;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysModule;
import com.gzzn.fgw.model.SysPermissionobject;
import com.gzzn.fgw.model.SysRolePermissionobject;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysUserRole;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysDeptService;
import com.gzzn.fgw.service.sys.ISysDictionaryitemsService;
import com.gzzn.fgw.service.sys.ISysUserService;
import com.gzzn.util.common.MessageUtil;
import com.gzzn.util.security.Md5Encrypt;

/**
 * 登录控制器
 * 
 * @author yjf
 * @date 2013-8-23
 * @version v1.0
 */

@Namespace(value = "/login")
public class LoginAction extends BaseAction<SysUser> {

	private String verfCode;//验证码信息
	private String loginName;
	private String loginPwd;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	@Autowired
	private ISysUserService userService;
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysDictionaryitemsService dictionaryitemsService;
	@Autowired
	private ISysDeptService sysDeptService;
	
	@Value("${system.code}")
	private String systemCode;
	
	private String ssologin = PropertiesUtil.getProperties("project.properties").getProperty(
			"sso.login");

	
	@GzznLog
	@Action("login")
	public String login() {
		//1、验证验证码是否正确
		String checkCode = (String)session.get(RandomValidateCode.RANDOMCODEKEY);
		if(verfCode == null){
			outPutError("验证码不能为空");
			return null;
		}else if(!verfCode.equalsIgnoreCase(checkCode)){
			logger.info("checkCode="+checkCode);
			logger.info("verfCode="+verfCode);
			outPutError("验证码输入错误");
			return null;
		}
		//2、验证用户名及其密码是否正确
		String complexPwdStr = loginPwd;//用于检测用户密码是否符合复杂度规范
		loginPwd = Md5Encrypt.getPassMD5(loginPwd);
		System.out.println(loginPwd);
		SysUser user = userService.login(loginName, loginPwd);
		if (user == null) {
			logger.info("用户名密码不匹配");
			outPutError(MessageUtil.MSG_ERROR_LOGIN);
		}
		else if(user.getUseStatus()==null){//用户状态=1，待审批;user.getUserType().equals(IConstants.USER_TYPE_1)
			logger.info("业主注册资料审批中，请等审批短信通知后再进行登陆");
			outPutError("业主注册资料审批中，请等审批短信通知后再进行登陆");
		}
		else if(user.getUseStatus().equals(IConstants.SHENHE_STATUS_1)){//用户状态=1，待审批;user.getUserType().equals(IConstants.USER_TYPE_1)
			logger.info("业主注册资料审批中，请等审批短信通知后再进行登陆");
			outPutError("业主注册资料审批中，请等审批短信通知后再进行登陆");
		}
		else if(user.getUseStatus().equals(IConstants.SHENHE_STATUS_3)){//用户状态=3，审核未通过
			logger.info("业主注册资料审批不通过，请修改后重新提交");
			outPutError("业主注册资料审批不通过，<a href='./sys/organization/ownerRegist.ac?operType=2&id="+ user.getUserId() +"'><font color='yellow'>请修改后重新提交</font></a>");
		}
		else if(user.getUseStatus().equals(IConstants.SHENHE_STATUS_4)){//用户状态=4，销户
			logger.info("对不起，该账户已被销户");
			outPutError("对不起，该账户已被销户");
		}else {
			logger.info(user.getUserName() + "登录成功");
			logger.info("systemCode={}", systemCode);

			//用户信息
			getHttpSession().setAttribute(CommonFiled.SESSION_LOGIN_USER, user);

			//添加用户权限
			long now = System.currentTimeMillis();
			initPermission(user);
			findDirectionaryitems();
			findSysDepts();
			findSysXqs();
			findSysXmlxs();
			logger.info("加载用户权限数据耗时={}毫秒",(System.currentTimeMillis()-now));
			logObject = new LogObject("用户成功登录系统， 账号："+ user.getLoginName());
			if(ValidateUtil.Regular(complexPwdStr, ValidateUtil.COMPLEXPWD)){
				outPutMsg(true, "登录成功");
			}else{
				outPutMsg(true, "密码不符合复杂度规范，请修改");
			}
		}

		return null;
	}
	
	@GzznLog
	@Action("slogin")
	public String ssoLogin() {
	    request.getSession().setAttribute("ssologin",ssologin);
		String userId = (String) request.getSession().getAttribute("user_id");
		SysUser user = commonService.findOne(SysUser.class,Integer.valueOf(userId));
		if (user == null) {
			logger.info("用户名密码不匹配");
			outPutError(MessageUtil.MSG_ERROR_LOGIN);
		}
		else if(user.getUseStatus()==null){//用户状态=1，待审批;user.getUserType().equals(IConstants.USER_TYPE_1)
			logger.info("业主注册资料审批中，请等审批短信通知后再进行登陆");
			outPutError("业主注册资料审批中，请等审批短信通知后再进行登陆");
		}
		else if(user.getUseStatus().equals(IConstants.SHENHE_STATUS_1)){//用户状态=1，待审批;user.getUserType().equals(IConstants.USER_TYPE_1)
			logger.info("业主注册资料审批中，请等审批短信通知后再进行登陆");
			outPutError("业主注册资料审批中，请等审批短信通知后再进行登陆");
		}
		else if(user.getUseStatus().equals(IConstants.SHENHE_STATUS_3)){//用户状态=3，审核未通过
			logger.info("业主注册资料审批不通过，请修改后重新提交");
			outPutError("业主注册资料审批不通过，<a href='./sys/organization/ownerRegist.ac?id="+ user.getUserId() +"'><font color='yellow'>请修改后重新提交</font></a>");
		}
		else if(user.getUseStatus().equals(IConstants.SHENHE_STATUS_4)){//用户状态=4，销户
			logger.info("对不起，该账户已被销户");
			outPutError("对不起，该账户已被销户");
		}
		else {
			logger.info(user.getUserName() + "登录成功");
			logger.info("systemCode={}", systemCode);

			//用户信息
			getHttpSession().setAttribute(CommonFiled.SESSION_LOGIN_USER, user);
			//添加用户权限
			long now = System.currentTimeMillis();
			initPermission(user);
			findDirectionaryitems();
			findSysDepts();
			findSysXqs();
			findSysXmlxs();
			logger.info("加载用户权限数据耗时={}毫秒",(System.currentTimeMillis()-now));
			logObject = new LogObject("用户成功登录系统， 账号："+ user.getLoginName());
			outPutMsg(true, "登录成功");
		}

		return null;
	}
	
	/**
	 * session缓存数据字典表 SYS_DICTIONARYITEMS
	 * 
	 * 
	 */
	private void findDirectionaryitems(){
		List<SysDictionaryitems> list = dictionaryitemsService.findDictionaryitems();
		List<SysDictionaryitems> yearselect = new ArrayList<SysDictionaryitems>();
		Map<String,String> fjlxMap = new HashMap<String, String>();
		Map<String,String> xmztMap = new HashMap<String, String>();
		List<SysDictionaryitems> xmjdselect = new ArrayList<SysDictionaryitems>();
		List<SysDictionaryitems> xmflselect = new ArrayList<SysDictionaryitems>();
		if(list!=null&&!list.isEmpty()){
			getHttpSession().setAttribute(CommonFiled.SESSION_DIRECTIONARYITEMS, list);
			if(list!=null&&!list.isEmpty()){
				for(SysDictionaryitems dictionaryitems:list){
					if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(IConstants.DICTIONARY_ITEM_FJLX)){
						fjlxMap.put(dictionaryitems.getItemvalue(),dictionaryitems.getItemtext());
					}
					if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(IConstants.DICTIONARY_ITEM_XMZT)){
						xmztMap.put(dictionaryitems.getItemvalue(),dictionaryitems.getItemtext());
					}
					if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(IConstants.DICTIONARY_ITEM_NF)){
						yearselect.add(dictionaryitems);
					}
					if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(IConstants.DICTIONARY_ITEM_XMJD)){
						xmjdselect.add(dictionaryitems);
					}
					if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(IConstants.DICTIONARY_ITEM_XMFL)){
						xmflselect.add(dictionaryitems);
					}
				}
			}
		}
		getHttpSession().setAttribute(CommonFiled.SESSION_DIRECTIONARYITEMS_NF, yearselect);
		getHttpSession().setAttribute(CommonFiled.SESSION_FJLX_MAP, fjlxMap);
		getHttpSession().setAttribute(CommonFiled.SESSION_XMZT_MAP, xmztMap);
		getHttpSession().setAttribute(CommonFiled.SESSION_XMJD_MAP, xmjdselect);
		getHttpSession().setAttribute(CommonFiled.SESSION_XMFL_MAP, xmflselect);
	}
	
	private void findSysDepts(){
		List<SysDept> list = commonService.findAll(SysDept.class);
		Map<Integer,SysDept> deptMap = new HashMap<Integer, SysDept>();
		if(list!=null&&!list.isEmpty()){
			if(list!=null&&!list.isEmpty()){
				for(SysDept dept:list){
					deptMap.put(dept.getDeptId(), dept);
				}
			}
		}
		getHttpSession().setAttribute(CommonFiled.SESSION_DEPT_MAP, deptMap);
	}
	
	private void findSysXqs(){
		List<SysXq> list = commonService.findAll(SysXq.class);
		Map<Integer,SysXq> map = new HashMap<Integer, SysXq>();
		if(list!=null&&!list.isEmpty()){
			if(list!=null&&!list.isEmpty()){
				for(SysXq entity:list){
					map.put(entity.getXqId(), entity);
				}
			}
		}
		getHttpSession().setAttribute(CommonFiled.SESSION_XQ_MAP, map);
	}
	
	private void findSysXmlxs(){
		List<XmsbXmlx> list = commonService.findAll(XmsbXmlx.class);
		Map<Integer,XmsbXmlx> map = new HashMap<Integer, XmsbXmlx>();
		if(list!=null&&!list.isEmpty()){
			if(list!=null&&!list.isEmpty()){
				for(XmsbXmlx entity:list){
					map.put(entity.getXmlxId(), entity);
				}
			}
		}
		getHttpSession().setAttribute(CommonFiled.SESSION_XMLX_MAP, map);
	}

	private void initPermission(SysUser user) {
		Map<String, Boolean> sysMap = new HashMap<String, Boolean>();
		Map<String, Boolean> moduleMap = new HashMap<String, Boolean>();
		Map<String, Boolean> objectMap = new HashMap<String, Boolean>();
		Map<String, String> logMap = new HashMap<String, String>();
		for (SysUserRole userRole : user.getSysUserRoles()) {
			for (SysRolePermissionobject rp : userRole.getSysRole().getSysRolePermissionobjects()) {
				if (systemCode.contains(rp.getSysSystem().getSystemCode())) {
					//添加System对象
					if(!sysMap.containsKey(rp.getSysSystem().getSystemCode())){
						sysMap.put(rp.getSysSystem().getSystemCode(), true);
					}
					
					//添加Module对象
					SysModule m = rp.getSysModule();
					do {
						String mStr = m.getModuleDesc();
						if (!moduleMap.containsKey(mStr)) {
							moduleMap.put(mStr, true);
						}
						m = m.getParentModule();
					} while (m != null);

					SysPermissionobject obj = rp.getSysPermissionobject();
					if (obj != null) {
						//添加obj对象
						String oStr = obj.getPermissionobjectCode();
						if (!objectMap.containsKey(oStr)) {
							objectMap.put(oStr, true);
						}
						//添加日志操作映射表
						String url = obj.getUrl();
						if (!logMap.containsKey(url)) {
							logMap.put(url, obj.getPermissionobjectDesc());
						}
					}
				}
			}
		}
		System.out.println(moduleMap.toString());
		System.out.println(sysMap.toString());
		System.out.println(objectMap.toString());
		System.out.println(logMap.toString());

		getHttpSession().setAttribute(CommonFiled.SESSION_SYSTEM_MAP, sysMap);
		getHttpSession().setAttribute(CommonFiled.SESSION_MODULE_MAP, moduleMap);
		getHttpSession().setAttribute(CommonFiled.SESSION_OBJECT_MAP, objectMap);
		getHttpSession().setAttribute(CommonFiled.SESSION_LOG_MAP, logMap);
	}

	@GzznLog
	@Action("logout")
	public String logOut() {
		logger.info("SysUserAction.logOut()");
		SysUser user = (SysUser) getHttpSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		getHttpSession().removeAttribute(CommonFiled.SESSION_LOGIN_USER);
		getHttpSession().removeAttribute(CommonFiled.SESSION_LOGIN_USER_PERMISSION);
		getHttpSession().removeAttribute(CommonFiled.SESSION_SYSTEM_MAP);
		getHttpSession().removeAttribute(CommonFiled.SESSION_MODULE_MAP);
		getHttpSession().removeAttribute(CommonFiled.SESSION_OBJECT_MAP);
		getHttpSession().removeAttribute(CommonFiled.SESSION_LOG_MAP);
		getHttpSession().removeAttribute("showNoteStart");
		logger.info(user.getUserName() + "注销");
		logObject = new LogObject("用户成功退出系统， 账号："+ user.getLoginName(), user);
		outPutMsg(true, user.getUserName() + "注销成功");

		return null;
	}

	@Action("exit")
	public String exit() {
		getHttpSession().invalidate();
		return "success";
	}

	public String getSsologin() {
		return ssologin;
	}

	public void setSsologin(String ssologin) {
		this.ssologin = ssologin;
	}

	public String getVerfCode() {
		return verfCode;
	}

	public void setVerfCode(String verfCode) {
		this.verfCode = verfCode;
	}
	
	
	
}
