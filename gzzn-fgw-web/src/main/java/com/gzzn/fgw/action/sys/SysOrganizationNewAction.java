package com.gzzn.fgw.action.sys;

import java.util.Arrays;
import java.util.Date;
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
import com.gzzn.util.security.Md5Encrypt;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: SysOrganizationNewAction</p>
 * <p>Description:  机构信息维护 </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-6-16 下午7:37:54 lhq  new
 */
@Namespace(value = "/sys/organNew")
@ParentPackage("struts-common")
public class SysOrganizationNewAction extends BaseAction<SysOrgPojo> {
	private static Logger logger = LoggerFactory.getLogger(SysOrganizationNewAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private String pageName;//页面名称，操作成功后返回的页面的名称
	private SysQueryParam sysParams;//系统管理查询参数
	private SysOrganization obj;//单位对象
	private Map<Object,String> dwxzMap;//获取全部的单位性质信息
	private String message;//返回页面信息
	private PageUtil<SysOrgPojo> page = new PageUtil<SysOrgPojo>();
	private SysXq sysXq;
	private Integer workunitsquality;
	private SysUser ownerUser;
	private String verfCode;//验证码信息
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysOrganizationService service;
	
	//进入单位信息列表界面
	@Action("list")
	public String list(){
		Condition con = this.initCon();
		service.findList(page, con, new Sort(Direction.ASC, "organizationId"));
		return "success";
	}
	
	private Condition initCon(){
		Condition con = new Condition();
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getOrganName())) {
				con.add("organizationName", Operator.LIKE, sysParams.getOrganName());
			}
			if (StringUtils.isNotEmpty(sysParams.getWorkunitstype())) {
				con.add("workunitstype", Operator.ISNOTNULL, null);
				con.add("workunitstype", Operator.IN, Arrays.asList(sysParams.getWorkunitstype().split(",")));
			}
			if (StringUtils.isNotEmpty(sysParams.getWorkunitsquality())) {
				con.add("workunitsquality", Operator.ISNOTNULL, null);
				con.add("workunitsquality", Operator.IN, Arrays.asList(sysParams.getWorkunitsquality().split(",")));
			}
			if (StringUtils.isNotEmpty(sysParams.getXqId())) {
				con.add("sysXq", Operator.ISNOTNULL, null);
				con.add("sysXq.xqId", Operator.IN, Arrays.asList(sysParams.getXqId().split(",")));
			}
			if(StringUtils.isNotEmpty(sysParams.getWorkunitsstatus())){
				con.add("workunitsstatus", Operator.ISNOTNULL, null);
				con.add("workunitsstatus", Operator.IN, Arrays.asList(sysParams.getWorkunitsstatus().split(",")));
			}
		}
		return con;
	}
	
	//进入编辑单位信息
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
	
	//保存单位信息
	@GzznLog
	@Action(interceptorRefs={@InterceptorRef("tokenStack")},value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			//单位状态
			obj.setWorkunitsstatus(obj.getWorkunitsstatus()==null?IConstants.SHENHE_STATUS_2:obj.getWorkunitsstatus());
			//obj.setWorkunitstype(IConstants.ORGAN_TYPE_2);
			//所在区域
			SysXq sysXq = null;
			if(obj.getSysXq().getXqId()!=null && obj.getSysXq().getXqId()>0){
				sysXq = commonService.findOne(SysXq.class, obj.getSysXq().getXqId());
			}
			obj.setSysXq(sysXq);
			service.saveOrUpdate(obj);
			
			Map<Object,String> dWLXMap = service.getDWLXMap();//单位类型Map
			logObject = new LogObject("新增或修改了单位信息，单位类型是："+ dWLXMap.get(obj.getWorkunitsquality()) +"，名称是："+ obj.getOrganizationName() +"，Id为："+ obj.getOrganizationId());
		} catch (Exception e) {
			message = "保存数据失败";
		}

		return "success";
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
	
	/**进入业主信息审核列表界面*/
	@Action("listOwnerVerify")
	public String listOwnerVerify(){
		long begin = System.currentTimeMillis();
		
		if(sysParams==null){
			sysParams = new SysQueryParam();
			sysParams.setWorkunitsstatus(String.valueOf(IConstants.SHENHE_STATUS_1));//工作单位状态【用于审核业主注册信息】：1-待审批；
		}
		if(StringUtils.isEmpty(sysParams.getWorkunitstype())){
			sysParams.setWorkunitstype(String.valueOf(IConstants.ORGAN_TYPE_1));//单位类型：1-业主单位
		}
		Condition con = this.initCon();//查询条件
		service.findList(page, con, new Sort(Direction.ASC, "organizationId"));
		
		logger.info("进入业主信息审核列表界面，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**进入注册业主信息编辑界面*/
	@Action("editOwnerVerify")
	public String editOwnerVerify() {
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysOrganization.class, Integer.parseInt(id));
			//查找该业主单位对应的业主信息
			Condition con = new Condition();
			con.add("sysOrganization", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationId", Operator.EQ, obj.getOrganizationId());
			ownerUser = commonService.findOne(SysUser.class, con);
		}
		
		return "success";
	}
	
	
	/**保存注册业主信息*/
	@GzznLog(LogType.SAVE)
	@Action(value = "saveOwnerVerify", results = { @Result(location = "listOwnerVerify.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String saveOwnerVerify(){
		long begin = System.currentTimeMillis();
		message = "保存数据成功";
		try {
			//所在区域
			SysXq sysXqObj = null;
			if(obj.getSysXq()!=null && obj.getSysXq().getXqId()!=null){
				sysXqObj = commonService.findOne(SysXq.class, obj.getSysXq().getXqId());
			}
			obj.setSysXq(sysXqObj);
			obj.setWorkunitstype(IConstants.ORGAN_TYPE_1);//业主单位
			if(obj.getWorkunitsstatus()==null){//审核状态：管理人员加入的单位审核状态为通过
				obj.setWorkunitsstatus(IConstants.SHENHE_STATUS_2);
			}
			service.saveOrUpdate(obj);
			
			//保存业主单位的用户信息
			SysUser tempobj = null;
			ownerUser.setUserType(IConstants.USER_TYPE_1);//业主用户
			if(ownerUser.getUseStatus()==null){//审核状态：管理人员加入的用户审核状态为通过
				ownerUser.setUseStatus(IConstants.SHENHE_STATUS_2);
			}
			if(ownerUser.getUserId()!=null){
				tempobj = commonService.findOne(SysUser.class, ownerUser.getUserId());
			}
			if(tempobj==null){//为空时
				tempobj = new SysUser();
			}
			if(StringUtils.isNotEmpty(ownerUser.getLoginPwd())){
				tempobj.setLoginPwd(Md5Encrypt.getPassMD5(ownerUser.getLoginPwd()));
			}
			tempobj.setSysOrganization(obj);//用户所属的机构信息
			tempobj.setLoginName(ownerUser.getLoginName());
			tempobj.setUserName(ownerUser.getUserName());
			tempobj.setSex(ownerUser.getSex());
			tempobj.setTelmobile(ownerUser.getTelmobile());
			tempobj.setUserType(ownerUser.getUserType());
			tempobj.setUseStatus(ownerUser.getUseStatus());
			commonService.saveOrUpdate(tempobj);
			
			logObject = new LogObject("新增或修改了业主信息，业主单位："+obj.getOrganizationName()+",业主登录帐号："+ownerUser.getLoginName()+",业主真实姓名："+ownerUser.getUserName());
		} catch (Exception e) {
			message = "保存数据失败";
			e.printStackTrace();
		}
		logger.info("保存注册业主信息，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}
	
	
	//删除单位信息
	@GzznLog
	@Action(interceptorRefs={@InterceptorRef("deleteStack")},value = "delete",results={@Result(name="listOwnerVerify",location="listOwnerVerify.ac", type = "redirectAction",
											   params = {"message", "${message}", "encode", "true" })
									 ,@Result(name="list",location="list.ac", type = "redirectAction",
									  		   params = {"message", "${message}", "encode", "true" })
									, @Result(name="deleteDenied", location="deleteDenied", type = "redirectAction")
			})
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					commonService.delete(SysOrganization.class, Integer.parseInt(i));
				}
				logObject = new LogObject("删除"+(pageName!=null && pageName.equalsIgnoreCase("listOwnerVerify")?"注册业主":"单位")+"信息，ids=" + id);
			}
		} catch (Exception e) {
			message = "删除数据失败";
		}
		
		if(pageName!=null && pageName.equalsIgnoreCase("listOwnerVerify")){
			return "listOwnerVerify";
		}else{
			return "list";
		}
	}
	
	/**查找待审核的业主注册信息*/
	@Action("ownerVerify")
	public String ownerVerify() {
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysOrganization.class, Integer.parseInt(id));
			dwxzMap = service.getDWXZMap();//获取全部的单位性质信息
			//查找该业主单位对应的业主信息
			Condition con = new Condition();
			con.add("sysOrganization", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationId", Operator.EQ, obj.getOrganizationId());
			ownerUser = commonService.findOne(SysUser.class, con);
		}
		return "success";
	}
	
	/**审核业主注册信息：verityStatus审核状态，1-待审批、2-审批通过、3-审批不通过*/
	@GzznLog
	@Action(value = "verifyProcess", results = { @Result(location = "listOwnerVerify.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String verifyProcess(){
		message = "审核业主信息成功";
		try {
			//获取审核状态
			String  verityStatus = (String) request.getParameter("verityStatus");
			Integer statusType = StringUtils.isNotEmpty(verityStatus)?Integer.valueOf(verityStatus):IConstants.SHENHE_STATUS_1;
			//注册单位信息
			SysOrganization orgObj = commonService.findOne(SysOrganization.class, obj.getOrganizationId());
			if(orgObj!=null){
				orgObj.setWorkunitsstatus(statusType);
				commonService.saveOrUpdate(orgObj);
			}
			//注册用户信息
			SysUser userObj = null;
			if(ownerUser!=null&&ownerUser.getUserId()!=null){
				userObj = commonService.findOne(SysUser.class, ownerUser.getUserId());
			}
			if(userObj!=null){
				userObj.setUseStatus(statusType);
				commonService.saveOrUpdate(userObj);
				logObject = new LogObject("审核业主注册信息，审核状态："+ (statusType.equals(IConstants.SHENHE_STATUS_2)?"审核通过":"审核不通过" ) 
						+"，注册单位："+ orgObj.getOrganizationName() +"，业主用户名："+ userObj.getLoginName() 
						+"，业主真实姓名："+ userObj.getUserName());
			}
			else{
				logObject = new LogObject("审核业主注册信息，审核状态："+ (statusType.equals(IConstants.SHENHE_STATUS_2)?"审核通过":"审核不通过" ) 
						+"，注册单位："+ orgObj.getOrganizationName());

			}
		} catch (Exception e) {
			message = "审核业主信息失败";
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
	
	/**验证企业是否已经注册*/
	@Action("checkOrganName")
	public String  checkOrganName(){
		Condition con=new Condition();
		String organName = (String) request.getParameter("organizationName");
		if(StringUtils.isNotEmpty(organName)){
			con.add("organizationName", Operator.ISNOTNULL, null);
			con.add("organizationName", Operator.EQ, organName);
			List<SysOrganization> sysOrgList = commonService.find(SysOrganization.class, con);
			if(sysOrgList!=null && sysOrgList.size()>0){
				obj = sysOrgList.get(0);
				//request.setAttribute("organ",sysOrgList.get(0));
				//outPutMsg(true, "已经存在该单位");
				String xqIdString = obj.getSysXq()!=null?obj.getSysXq().getXqId().toString():"";
				String string = "{\"flag\":\"true\"" +
						",\"organizationId\":\""+obj.getOrganizationId()+"\"" +
						",\"workunitsquality\":\""+obj.getWorkunitsquality()+"\"" +
						",\"xqId\":\""+xqIdString+"\"" +
						",\"workunitsregistercode\":\""+obj.getWorkunitsregistercode()+"\"" +
						",\"workunitsperson\":\""+obj.getWorkunitsperson()+"\"" +
						",\"workunitslinkman\":\""+obj.getWorkunitslinkman()+"\"" +
						",\"workunitslinkmantel\":\""+obj.getWorkunitslinkmantel()+"\"" +
						",\"workunitsaddress\":\""+obj.getWorkunitsaddress()+"\"" +
						"}";
				outJsonString(string);
			}
			else{
				String string = "{\"flag\":\"false\"}";
				outJsonString(string);
			}
		}
		else{
			String string = "{\"flag\":\"false\"}";
			outJsonString(string);
		}
		return null;
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
	
	
	/**
	 * 进入业主注册信息
	 * @return
	 */
	@Action("ownerRegist")
	public String ownerRegist() {
		if(StringUtils.isNotEmpty(id)){
			ownerUser = commonService.findOne(SysUser.class, Long.parseLong(id));//用户信息
			if(ownerUser!=null && ownerUser.getSysOrganization()!=null){
				obj = ownerUser.getSysOrganization();
			}
		}
		
		return "success";
	}
	
	
	
	/**
	 * 保存业主注册信息
	 * @return
	 */
	@GzznLog
	@Action(value = "registOwner", results = { @Result(location = "registSuccess.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String registOwner(){
		long begin = System.currentTimeMillis();
		message = ownerUser.getUserId()!=null&&ownerUser.getUserId()>0?"修改注册信息成功":"业主注册成功";
		try {
			if(obj.getOrganizationId()==null){
				
				//单位相关信息
				obj.setWorkunitstype(IConstants.ORGAN_TYPE_1);//单位类型：1-业主单位
				obj.setWorkunitsstatus(IConstants.SHENHE_STATUS_1);//工作单位状态：1-待审批
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

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public Map<Object, String> getDwxzMap() {
		return dwxzMap;
	}

	public void setDwxzMap(Map<Object, String> dwxzMap) {
		this.dwxzMap = dwxzMap;
	}

	public String getVerfCode() {
		return verfCode;
	}

	public void setVerfCode(String verfCode) {
		this.verfCode = verfCode;
	}

	

	
	

	

	

}
