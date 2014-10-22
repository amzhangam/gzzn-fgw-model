package com.gzzn.fgw.action.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysOrganizationService;
import com.gzzn.fgw.service.sys.pojo.SysOrgPojo;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.RandomValidateCode;
import com.gzzn.util.exception.CustomException;
import com.gzzn.util.security.Md5Encrypt;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: SysOrganizationAction</p>
 * <p>Description:  机构信息维护 </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-4 下午7:37:54 lhq  new
 */
@Namespace(value = "/sys/organization")
@ParentPackage("struts-common")
public class SysOrganizationAction extends BaseAction<SysOrgPojo> {
	private static Logger logger = LoggerFactory.getLogger(SysOrganizationAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private SysQueryParam sysParams;//系统管理查询参数
	private SysOrganization obj;//单位对象
	private String verfCode;//验证码信息
	private String operType;//当前操作类型：1-新增注册；2-修改注册信息
	private String message;//返回页面信息
	private PageUtil<SysOrgPojo> page = new PageUtil<SysOrgPojo>();
	private SysXq sysXq;
	private Integer workunitsquality;
	private SysUser ownerUser;
	private Map<Integer,String> xzMap = new HashMap<Integer, String>();
	private Map<Integer,String> lxMap = new HashMap<Integer, String>();
	private Map<Integer,String> ztMap = new HashMap<Integer, String>();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysOrganizationService service;
	
	//进入主管单位信息列表界面
	@Action("list")
	public String list(){
		Condition con = new Condition();
		con.add("workunitstype", Operator.ISNOTNULL,null);
//		con.add("workunitstype", Operator.EQ, CommonFiled.ORGAN_TYPE_2);
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getOrganName())) {
				con.add("organizationName", Operator.LIKE, sysParams.getOrganName());
			}
			if (StringUtils.isNotEmpty(sysParams.getWorkunitstype())) {
				con.add("workunitstype", Operator.EQ, Integer.valueOf(sysParams.getWorkunitstype()));
			}
			if (StringUtils.isNotEmpty(sysParams.getWorkunitsquality())) {
				con.add("workunitsquality", Operator.EQ, Integer.valueOf(sysParams.getWorkunitsquality()));
			}
			if (sysParams.getSysXq()!=null&&sysParams.getSysXq().getXqId()!=null) {
				con.add("sysXq.xqId", Operator.EQ, this.sysParams.getSysXq().getXqId());
			}
		}
		Order order1 = new Order(Direction.DESC, "modifiedTime");
		Order order2 = new Order(Direction.ASC, "organizationId");
		Sort sort = new Sort( order1,order2);
		
		service.findList(page, con, sort);
		
		
		
		return "success";
	}
	
	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysOrganization.class, Integer.parseInt(id));
		}
		else{
			obj = new SysOrganization();
		}
		return "success";
	}
	
	
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			obj.setWorkunitstype(IConstants.ORGAN_TYPE_2);
			service.saveOrUpdate(obj);
			logObject = new LogObject("主管单位信息",obj.getOrganizationId(),obj.getOrganizationName(),null);
		} catch (Exception e) {
			message = "保存数据失败";
		}

		return "success";
	}
	
	//删除主管单位信息
	@GzznLog
	@Action(value = "delete", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					commonService.delete(SysOrganization.class, Integer.parseInt(i));
				}
				logObject = new LogObject("删除主管单位信息，ids=" + id);
			}
		} catch (Exception e) {
			message = "删除主管单位数据失败";
		}

		return "success";
	}
	
	//进入业主单位信息列表界面
	@Action("listOwner")
	public String listOwner(){
		Condition con = new Condition();
		con.add("workunitstype", Operator.ISNOTNULL,null);
		con.add("workunitstype", Operator.EQ, IConstants.ORGAN_TYPE_1);
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getOrganName())) {
				con.add("organizationName", Operator.LIKE, sysParams.getOrganName());
			}
			if (this.getSysXq() != null && this.getSysXq().getXqId() != null) {
				con.add("sysXq.xqId", Operator.EQ, this.getSysXq().getXqId());
			}
			if (this.getWorkunitsquality() != null ) {
				con.add("workunitsquality", Operator.EQ, this.getWorkunitsquality());
			}
		}
		Order order2 = new Order(Direction.ASC, "organizationId");
		Sort sort = new Sort( order2);
		
		service.findList(page, con, sort);
		
		return "success";
	}
	
	@Action("editOwner")
	public String editOwner(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysOrganization.class, Integer.parseInt(id));
			sysXq = obj.getSysXq();
			workunitsquality = obj.getWorkunitsquality();
			
			Condition con = new Condition();
			con.add("sysOrganization", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationId", Operator.EQ, obj.getOrganizationId());
			
			ownerUser = commonService.findOne(SysUser.class, con);
		}
		else{
			obj = new SysOrganization();
		}
		
		return "success";
	}
	
	
	@GzznLog(LogType.SAVE)
	@Action(value = "saveOwner", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
//	@Action(value = "saveOwner")
	public String saveOwner(){
		message = "保存数据成功";
		try {
			obj.setWorkunitstype(IConstants.ORGAN_TYPE_1);
			if(obj.getWorkunitsstatus()==null){
				obj.setWorkunitsstatus(IConstants.SHENHE_STATUS_1);
			}
			if(sysXq==null||sysXq.getXqId()==null){
				obj.setSysXq(null);
			}
			else{
				obj.setSysXq(sysXq);
			}
			service.saveOrUpdate(obj);
			if(ownerUser!=null&&ownerUser.getUserId()==null){
				if(StringUtils.isNotEmpty(ownerUser.getLoginPwd())){
					ownerUser.setLoginPwd(Md5Encrypt.getPassMD5(ownerUser.getLoginPwd()));
				}
			}
			else if(ownerUser!=null){
				SysUser tempobj = commonService.findOne(SysUser.class, ownerUser.getUserId());
				ownerUser.setLoginPwd(tempobj.getLoginPwd());
			}
			ownerUser.setSysOrganization(obj);
			commonService.saveOrUpdate(ownerUser);
			logObject = new LogObject("业主信息编辑成功，业主单位："+obj.getOrganizationName()+",业主用户名："+ownerUser.getLoginName()+",业主真实姓名："+ownerUser.getUserName());
//			outJsonString("{\"success\":true,\"info\":\"操作成功！\"}");
		} catch (Exception e) {
//			outJsonString("{\"success\":false,\"info\":\"操作失败！\"}");
			message = "保存数据失败";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	//删除业主单位信息
	@GzznLog
	@Action(value = "deleteOwner", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String deleteOwner(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					commonService.delete(SysOrganization.class, Integer.parseInt(i));
				}
				logObject = new LogObject("删除业主单位信息，ids=" + id);
			}
		} catch (Exception e) {
			message = "删除业主单位数据失败";
		}
		
		return "success";
	}
	
	/**
	 * 
	 * 方法描述：获取单位树数据
	 * 创建时间：2013-12-16下午8:13:35
	 * 创建人：lxb
	 *
	 */
	@Action("getOrganizationTreeJson")
	public void getOrganizationTreeJson(){
		String userType = request.getParameter("ut");
		if(userType!=null&&userType.equals(IConstants.USER_TYPE_1)){
			userType = IConstants.ORGAN_TYPE_1+"";
		}
		else if(userType!=null&&userType.equals(IConstants.USER_TYPE_2)){
			userType = IConstants.ORGAN_TYPE_2+"";
		}
		outJsonString(service.findSysOrgTreeJson(false,userType));
	}
	
	/**
	 * 
	 * 方法描述：获取单位树数据
	 * 创建时间：2013-12-16下午8:13:35
	 * 创建人：lxb
	 *
	 */
	@Action("getOrganTreeJson")
	public String getOrganTreeJson(){
		String userType = request.getParameter("ut");
		if(userType!=null&&userType.equals(IConstants.USER_TYPE_1)){
			userType = IConstants.ORGAN_TYPE_1+"";
		}else if(userType!=null&&userType.equals(IConstants.USER_TYPE_2)){
			userType = IConstants.ORGAN_TYPE_2+"";
		}
		outPutJSON(service.findSysOrgTreeJson(false,userType));
		return null;
	}
	
	
	@Action("getAreaJson")
	public String getAreaJson(){
		
		outPutJSON(service.findAreaTreeJson(false));
		
		return null;
	}
	
	@Action("getUnitNatureJson")
	public String getUnitNatureJson(){
		
		outPutJSON(service.findUnitNatureTreeJson(false));
		
		return null;
	}
	
	@Action("getUnitStatusJson")
	public String getUnitStatusJson(){
		
		outPutJSON(service.findUnitStatusTreeJson(false));
		
		return null;
	}

	@Action("getUnitTypeJson")
	public String getUnitTyupeJson(){
		
		outPutJSON(service.findUnitTypeTreeJson(false));
		
		return null;
	}
	
	/**
	 * 进入业主注册信息
	 * @return
	 */
	@Action("ownerRegist")
	public String ownerRegist() {
		if(StringUtils.isEmpty(operType)){//当前操作类型：1-新增注册；2-修改注册信息;默认为1
			operType = "1";
		}
		if(StringUtils.isNotEmpty(id)){
			ownerUser = commonService.findOne(SysUser.class, Integer.valueOf(id));//用户信息
			if(ownerUser!=null && ownerUser.getSysOrganization()!=null){
				obj = ownerUser.getSysOrganization();
			}
			else{
				obj = new SysOrganization();
			}
		}
		else{
			obj = new SysOrganization();
		}
		
		return "success";
	}
	
	/**企业注册时：验证用户名是否已经重复*/
	@Action("checkRegistName")
	public void checkRegistName(){
		Condition con=new Condition();
		if(ownerUser.getUserId()!=null){
			con.add("userId", Operator.NE, ownerUser.getUserId());
		}
		if(StringUtils.isNotEmpty(ownerUser.getLoginName())){
			con.add("loginName", Operator.ISNOTNULL, null);
			con.add("loginName", Operator.EQ, ownerUser.getLoginName());
		}
		List<SysUser> sysUserList = commonService.find(SysUser.class, con);
		if(sysUserList != null && sysUserList.size()>0){
			outPutString("false");
		}else{
			outPutString("true");
		}
	}
	
	/**企业注册时：验证企业工商注册号是否已经重复*/
	@Action("checkRegistCode")
	public void checkRegistCode(){
		Condition con=new Condition();
		
		if(obj.getOrganizationId()!=null && obj.getOrganizationId()>0){
			con.add("organizationId", Operator.NE, obj.getOrganizationId());
		}
		if(StringUtils.isNotEmpty(obj.getWorkunitsregistercode())){
			con.add("workunitsregistercode", Operator.ISNOTNULL, null);
			con.add("workunitsregistercode", Operator.EQ, obj.getWorkunitsregistercode());
		}
		
		List<SysOrganization> sysOrgList = commonService.find(SysOrganization.class, con);
		if(sysOrgList!=null && sysOrgList.size()>0){
			outPutString("false");
		}else{
			outPutString("true");
		}
	}
	
	/**生成验证码图片*/
	@Action("genVerfCodeImage")
	public void genVerfCodeImage(){
		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
           //request.getSession().setAttribute("requestTime", System.currentTimeMillis());
           System.err.println("生成验证码:"+request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**验证验证码图片的正确性*/
	@Action("checkVerfCodeImage")
	public void checkVerfCodeImage(){
		String checkCode = (String)session.get(RandomValidateCode.RANDOMCODEKEY);
		if(verfCode != null && verfCode.equalsIgnoreCase(checkCode)){
			//System.out.println("验证码正确");
			outPutString("true");
		}else{
			//System.out.println("验证码错误");
			outPutString("false");
		}
	}
	
	/**
	 * 保存业主注册信息:
	 * @return
	 */
	@GzznLog
	@Action(interceptorRefs = { @InterceptorRef("tokenStack") }, value = "registOwner", results = { @Result(location = "registSuccess.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String registOwner(){
		long begin = System.currentTimeMillis();
		message = ownerUser.getUserId()!=null&&ownerUser.getUserId()>0?"修改注册信息成功":"业主注册成功";
		//1、当前为注册用户信息，需要验证验证码是否正确
		if(ownerUser==null || ownerUser.getUserId()==null){
			String checkCode = (String)session.get(RandomValidateCode.RANDOMCODEKEY);
			if(!StringUtils.isNotEmpty(verfCode)){
				throw new CustomException("验证码不能为空");
			}else if(!verfCode.equalsIgnoreCase(checkCode)){
				throw new CustomException("验证码输入错误");
			}
		}
		try {
			if(obj.getOrganizationId()==null || (obj.getOrganizationId()!=null && StringUtils.isNotEmpty(operType) && operType.equals("2")) ){
				//单位相关信息
				obj.setWorkunitstype(IConstants.ORGAN_TYPE_1);//单位类型：1-业主单位
				if (StringUtils.isNotEmpty(operType) && operType.equals("2")
						&& obj.getWorkunitsstatus() != null && obj.getWorkunitsstatus().equals(3)) {//用户修改注册信息
					obj.setWorkunitsstatus(IConstants.SHENHE_STATUS_1);//工作单位状态：1-待审批
				}else{
					obj.setWorkunitsstatus(IConstants.SHENHE_STATUS_1);//工作单位状态：1-待审批
				}
				SysXq sysXqObj = null;
				if(obj!=null && obj.getSysXq().getXqId()!=null){
					sysXqObj = commonService.findOne(SysXq.class, obj.getSysXq().getXqId());
				}
				obj.setSysXq(sysXqObj);
				service.saveOrUpdate(obj);
			}
			
			//个人信息
			SysUser sysUserObj = null;
			if(ownerUser.getUserId()!=null&&ownerUser.getUserId()>0){//用户修改注册信息
				sysUserObj = commonService.findOne(SysUser.class, ownerUser.getUserId());//用户信息
			}
			if(sysUserObj==null){//新增用户信息
				sysUserObj = new SysUser();
				sysUserObj.setCreateTime(new Date());
			}
			sysUserObj.setUserType(IConstants.ORGAN_TYPE_1);//设置用户类型为：1-业主
			sysUserObj.setUseStatus(IConstants.SHENHE_STATUS_1);//设置用户状态：1-待审批
			sysUserObj.setSysOrganization(obj);
			if(StringUtils.isNotEmpty(ownerUser.getLoginName())){
				sysUserObj.setLoginName(ownerUser.getLoginName());
			}
			if(StringUtils.isNotEmpty(ownerUser.getLoginPwd())){
				sysUserObj.setLoginPwd(Md5Encrypt.getPassMD5(ownerUser.getLoginPwd()));
			}
			sysUserObj.setUserName(ownerUser.getUserName());
			sysUserObj.setSex(ownerUser.getSex());
			sysUserObj.setTelmobile(ownerUser.getTelmobile());
			if(sysUserObj.getRoleType()==null||sysUserObj.getRoleType()==0){
				sysUserObj.setRoleType(IConstants.ROLE_TYPE_FGW_CODE_3);
			}
			commonService.saveOrUpdate(sysUserObj);
			
			logObject = new LogObject(message+"，注册单位："+obj.getOrganizationName()+",业主用户名："+sysUserObj.getLoginName()+",业主真实姓名："+sysUserObj.getUserName(), sysUserObj);
		} catch (Exception e) {
			e.printStackTrace();
			message = ownerUser.getUserId()!=null&&ownerUser.getUserId()>0?"修改注册信息失败":"业主注册失败";
		}
		logger.info(message+"，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	


	@Action("addOwner")
	public String addOwner() {
		return "success";
	}
	
	//进入业主信息审核列表界面
	@Action("listOwnerVerify")
	public String listOwnerVerify(){
		Condition con = new Condition();
		con.add("workunitstype", Operator.ISNOTNULL,null);
		con.add("workunitstype", Operator.EQ, IConstants.ORGAN_TYPE_1);
		con.add("workunitsstatus", Operator.ISNOTNULL, null);
//		con.add("workunitsstatus", Operator.NE, IConstants.SHENHE_STATUS_1);
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getOrganName())) {
				con.add("organizationName", Operator.LIKE, sysParams.getOrganName());
			}
			if(sysParams.getOrganStatus()!=null) {
				con.add("workunitsstatus", Operator.EQ, sysParams.getOrganStatus());
			}
		}
		if (this.getSysXq() != null && this.getSysXq().getXqId() != null) {
			con.add("sysXq.xqId", Operator.EQ, this.getSysXq().getXqId());
		}
		if (this.getWorkunitsquality() != null ) {
			con.add("workunitsquality", Operator.ISNOTNULL, null);
			con.add("workunitsquality", Operator.EQ, this.getWorkunitsquality());
		}
		Order order2 = new Order(Direction.ASC, "organizationId");
		Sort sort = new Sort( order2);
		
		service.findList(page, con, sort);
		
		return "success";
	}
		
	
	@Action("editOwnerVerify")
	public String editOwnerVerify() {
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysOrganization.class, Integer.parseInt(id));
			sysXq = obj.getSysXq();
			workunitsquality = obj.getWorkunitsquality();
			
			Condition con = new Condition();
			con.add("sysOrganization", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationId", Operator.EQ, obj.getOrganizationId());
			
			ownerUser = commonService.findOne(SysUser.class, con);
		}
		
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "saveOwnerVerify", results = { @Result(location = "listOwnerVerify.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
//	@Action(value = "saveOwner")
	public String saveOwnerVerify(){
		message = "保存数据成功";
		try {
			obj.setWorkunitstype(IConstants.ORGAN_TYPE_1);
			if(obj.getWorkunitsstatus()==null){
				obj.setWorkunitsstatus(IConstants.SHENHE_STATUS_1);
			}
			if(sysXq==null||sysXq.getXqId()==null){
				obj.setSysXq(null);
			}
			else{
				obj.setSysXq(sysXq);
			}
			
			service.saveOrUpdate(obj);
			if(ownerUser!=null&&ownerUser.getUserId()==null){
				if(StringUtils.isNotEmpty(ownerUser.getLoginPwd())){
					ownerUser.setLoginPwd(Md5Encrypt.getPassMD5(ownerUser.getLoginPwd()));
				}
			}
			else if(ownerUser!=null){
				SysUser tempobj = commonService.findOne(SysUser.class, ownerUser.getUserId());
				ownerUser.setLoginPwd(tempobj.getLoginPwd());
			}
			ownerUser.setSysOrganization(obj);
			commonService.saveOrUpdate(ownerUser);
			logObject = new LogObject("业主信息编辑成功，业主单位："+obj.getOrganizationName()+",业主用户名："+ownerUser.getLoginName()+",业主真实姓名："+ownerUser.getUserName());
//			outJsonString("{\"success\":true,\"info\":\"操作成功！\"}");
		} catch (Exception e) {
//			outJsonString("{\"success\":false,\"info\":\"操作失败！\"}");
			message = "保存数据失败";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	@Action("ownerVerify")
	public String ownerVerify() {
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysOrganization.class, Integer.parseInt(id));
			sysXq = obj.getSysXq();
			workunitsquality = obj.getWorkunitsquality();
			
			Condition con = new Condition();
			con.add("sysOrganization", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationId", Operator.EQ, obj.getOrganizationId());
			
			ownerUser = commonService.findOne(SysUser.class, con);
		}
		
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "verifyProcess", results = { @Result(location = "listOwnerVerify.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String verifyProcess(){
		try {
			String statusString = (String) request.getParameter("verityStatus");
			obj.setWorkunitsstatus(statusString==null?null:Integer.valueOf(statusString));
			
			SysOrganization tempobj = commonService.findOne(SysOrganization.class, obj.getOrganizationId());
			tempobj.setWorkunitsstatus(statusString==null?null:Integer.valueOf(statusString));
			service.saveOrUpdate(tempobj);
			if(statusString!=null&&statusString.equals(IConstants.SHENHE_STATUS_2)){
				logObject = new LogObject("业主注册审核通过，注册单位："+obj.getOrganizationName()+",业主用户名："+ownerUser.getLoginName()+",业主真实姓名："+ownerUser.getUserName());
			}
			else if(statusString!=null&&statusString.equals(IConstants.SHENHE_STATUS_3)){
				logObject = new LogObject("业主注册审核不通过，注册单位："+obj.getOrganizationName()+",业主用户名："+ownerUser.getLoginName()+",业主真实姓名："+ownerUser.getUserName());
			}
			
		} catch (Exception e) {
			message = "业主注册失败";
			e.printStackTrace();
		}
		return "success";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysOrganization getObj() {
		return obj;
	}

	public void setObj(SysOrganization obj) {
		this.obj = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PageUtil<SysOrgPojo> getPage() {
		return page;
	}

	public void setPage(PageUtil<SysOrgPojo> page) {
		this.page = page;
	}

	public SysQueryParam getSysParams() {
		return sysParams;
	}

	public void setSysParams(SysQueryParam sysParams) {
		this.sysParams = sysParams;
	}

	
	public SysXq getSysXq() {
		return sysXq;
	}

	public void setSysXq(SysXq sysXq) {
		this.sysXq = sysXq;
	}

	public Integer getWorkunitsquality() {
		return workunitsquality;
	}

	public void setWorkunitsquality(Integer workunitsquality) {
		this.workunitsquality = workunitsquality;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public ISysOrganizationService getService() {
		return service;
	}

	public void setService(ISysOrganizationService service) {
		this.service = service;
	}

	public SysUser getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(SysUser ownerUser) {
		this.ownerUser = ownerUser;
	}

	public Map<Integer, String> getXzMap() {
		xzMap = new HashMap<Integer, String>();
		xzMap.put(IConstants.DWXZ_CODE_1, IConstants.DWXZ_NAME_1);
		xzMap.put(IConstants.DWXZ_CODE_2, IConstants.DWXZ_NAME_2);
		xzMap.put(IConstants.DWXZ_CODE_3, IConstants.DWXZ_NAME_3);
		xzMap.put(IConstants.DWXZ_CODE_4, IConstants.DWXZ_NAME_4);
		xzMap.put(IConstants.DWXZ_CODE_5, IConstants.DWXZ_NAME_5);
		return xzMap;
	}

	public void setXzMap(Map<Integer, String> xzMap) {
		this.xzMap = xzMap;
	}
	
	public Map<Integer, String> getLxMap() {
		lxMap = new HashMap<Integer, String>();
		lxMap.put(IConstants.ORGAN_TYPE_1, IConstants.ORGAN_TYPE_NAME_1);
		lxMap.put(IConstants.ORGAN_TYPE_2, IConstants.ORGAN_TYPE_NAME_2);
		lxMap.put(IConstants.ORGAN_TYPE_3, IConstants.ORGAN_TYPE_NAME_3);
		return lxMap;
	}
	
	public void setLxMap(Map<Integer, String> lxMap) {
		this.lxMap = lxMap;
	}
	
	public Map<Integer, String> getZtMap() {
		ztMap = new HashMap<Integer, String>();
		ztMap.put(IConstants.SHENHE_STATUS_1, IConstants.SHENHE_STATUS_NAME_1);
		ztMap.put(IConstants.SHENHE_STATUS_2, IConstants.SHENHE_STATUS_NAME_2);
		ztMap.put(IConstants.SHENHE_STATUS_3, IConstants.SHENHE_STATUS_NAME_3);
		return ztMap;
	}
	
	public void setZtMap(Map<Integer, String> ztMap) {
		this.ztMap = ztMap;
	}

	public String getVerfCode() {
		return verfCode;
	}

	public void setVerfCode(String verfCode) {
		this.verfCode = verfCode;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}
	
}
