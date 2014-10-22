package com.gzzn.fgw.action.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Value;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Condition.Paramerter;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.common.persist.Sort;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysUserRole;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysUserService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.util.security.Md5Encrypt;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: SysUserAction
 * <p>Description: 处理与用户相关的请求
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-20下午5:52:11  CHENGZHI    NEW<P>
 */

@Namespace(value = "/sys")
@ParentPackage("struts-common")
public class SysUserAction extends BaseAction<SysUser> {
	@Resource
	private ISysUserService sysUserService;
	@Resource
	private ICommonService commonService;
	@Value("${system.code}")
	private String systemCode;
	private String message;//返回页面信息
	private String loginName;
	private String loginPwd;
	private List<SysUser> sysUserList;
	private SysUser sysUser;
	

	private Integer roleId;

	private String status;
	
	private Integer userType;
	
	private Integer roleType;

	private String oldPass;

	private String newPass;

	private Integer conUserId;

	private SysUser dto = new SysUser();

	private PageUtil<SysUser> page = new PageUtil<SysUser>();
	
	private Map<Integer,String> yhlxMap = new HashMap<Integer, String>();//用户类型
	private Map<Integer,String> yhztMap = new HashMap<Integer, String>();//用户状态
	private Map<Integer,String> jslxMap = new HashMap<Integer, String>();//角色类型

	private String ids;

	@Action("sysUserQuery")
	public String query() {
		Condition con = new Condition();
		/*String level = this.getCheckLevel();
		if (level != null && (level.indexOf("9") >= 0 || level.indexOf("8") >= 0)) {//管理员查询所有记录
			this.setLevel(true);
		} else {//普通用户查询自己的记录
			con.add("userId", Operator.EQ, this.getUser().getUserId());
		}*/
		Order order1 = new Order(Direction.DESC, "modifiedTime");
		Order order2 = new Order(Direction.DESC, "userName");
		Sort sort = new Sort(order1,order2);
		if (StringUtils.isNotEmpty(dto.getUserName())) {
			con.add("userName", Operator.LIKE, dto.getUserName());
		}
		if (StringUtils.isNotEmpty(dto.getLoginName())) {
			con.add("loginName", Operator.LIKE, dto.getLoginName());
		}
		if (StringUtils.isNotEmpty(dto.getTelmobile())) {
			con.add("telmobile", Operator.LIKE, dto.getTelmobile());
		}
		if (this.dto.getUseStatus() != null) {
			con.add("useStatus", Operator.ISNOTNULL,null);
			con.add("useStatus", Operator.EQ, this.dto.getUseStatus());
		}
		if (this.dto.getUserType() != null) {
			con.add("userType", Operator.EQ, this.dto.getUserType());
		}
		if (this.dto.getSysOrganization() != null && StringUtils.isNotEmpty(this.dto.getSysOrganization().getOrganizationName())) {
			con.add("sysOrganization.organizationName", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationName", Operator.LIKE, this.dto.getSysOrganization().getOrganizationName());
		}
		if (this.dto.getSysOrganization() != null && this.dto.getSysOrganization().getOrganizationId() != null) {
			con.add("sysOrganization.organizationId", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationId", Operator.EQ, this.dto.getSysOrganization().getOrganizationId());
		}
		if (this.dto.getSysDept() != null && this.dto.getSysDept().getDeptId() != null) {
			con.add("sysDept.deptId", Operator.ISNOTNULL,null);
			con.add("sysDept.deptId", Operator.EQ, this.dto.getSysDept().getDeptId());
		}
		
		if (this.dto.getRoleType() != null) {
			con.add("roleType", Operator.ISNOTNULL,null);
			con.add("roleType", Operator.EQ, this.dto.getRoleType());
		}
		if (this.dto.getSysDuty() != null && this.dto.getSysDuty().getDutyId() != null) {
			con.add("sysDuty.dutyId", Operator.ISNOTNULL,null);
			con.add("sysDuty.dutyId", Operator.EQ, this.dto.getSysDuty().getDutyId());
		}
		this.setConUserId(this.getUser().getUserId());
		sysUserService.loadData(SysUser.class, page, con, sort);
		return "success";
	}
	
	@Action("userVerifyQuery")
	public String userVerifyQuery() {
		Condition con = new Condition();
		/*String level = this.getCheckLevel();
		if (level != null && (level.indexOf("9") >= 0 || level.indexOf("8") >= 0)) {//管理员查询所有记录
			this.setLevel(true);
		} else {//普通用户查询自己的记录
			con.add("userId", Operator.EQ, this.getUser().getUserId());
		}*/
		Sort sort = new Sort("userName");
		if (StringUtils.isNotEmpty(dto.getUserName())) {
			con.add("userName", Operator.LIKE, dto.getUserName());
		}
		if (StringUtils.isNotEmpty(dto.getLoginName())) {
			con.add("loginName", Operator.LIKE, dto.getLoginName());
		}
		if (StringUtils.isNotEmpty(dto.getTelmobile())) {
			con.add("telmobile", Operator.LIKE, dto.getTelmobile());
		}
		if (this.dto.getUseStatus() != null) {
			con.add("useStatus", Operator.ISNOTNULL,null);
			con.add("useStatus", Operator.EQ, this.dto.getUseStatus());
		}
		if (this.dto.getUserType() != null) {
			con.add("userType", Operator.EQ, this.dto.getUserType());
		}
		if (this.dto.getSysOrganization() != null && StringUtils.isNotEmpty(this.dto.getSysOrganization().getOrganizationName())) {
			con.add("sysOrganization.organizationName", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationName", Operator.LIKE, this.dto.getSysOrganization().getOrganizationName());
		}
		if (this.dto.getSysOrganization() != null && this.dto.getSysOrganization().getOrganizationId() != null) {
			con.add("sysOrganization.organizationId", Operator.ISNOTNULL,null);
			con.add("sysOrganization.organizationId", Operator.EQ, this.dto.getSysOrganization().getOrganizationId());
		}
		if (this.dto.getSysDept() != null && this.dto.getSysDept().getDeptId() != null) {
			con.add("sysDept.deptId", Operator.ISNOTNULL,null);
			con.add("sysDept.deptId", Operator.EQ, this.dto.getSysDept().getDeptId());
		}
		
		if (this.dto.getRoleType() != null) {
			con.add("roleType", Operator.ISNOTNULL,null);
			con.add("roleType", Operator.EQ, this.dto.getRoleType());
		}
		if (this.dto.getSysDuty() != null && this.dto.getSysDuty().getDutyId() != null) {
			con.add("sysDuty.dutyId", Operator.ISNOTNULL,null);
			con.add("sysDuty.dutyId", Operator.EQ, this.dto.getSysDuty().getDutyId());
		}
		con.add("useStatus",Operator.ISNOTNULL,null);
		con.add("useStatus",Operator.NE,2);
		this.setConUserId(this.getUser().getUserId());
		sysUserService.loadData(SysUser.class, page, con, sort);
		return "success";
	}

	@Action("sysUserNewOrEdits")
	public String newOrEdit() {
		if ("add".equals(this.status)) {//添加

		} else {//修改
			this.setDto(commonService.findOne(SysUser.class, dto.getUserId()));
		}
		return "success";
	}
	
	@Action("userVerify")
	public String userVerify() {
		this.setDto(commonService.findOne(SysUser.class, dto.getUserId()));
		return "success";
	}

	@Action(interceptorRefs={@InterceptorRef("tokenStack")},value = "sysUserAdd", results = { @Result(location = "sysUserQuery.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String sysUserAdd() {
		message = "保存用户信息成功";
		logger.info("------------>保存与修改<-----------");
		SysUser sysUser = null;
		try {
			if (null == dto.getUserId()) {
				sysUser = new SysUser();
				sysUser.setCreateTime(new Date());
			} else {
				sysUser = commonService.findOne(SysUser.class, dto.getUserId());
			}
			//sysUser.setSysDept(dto.getSysDept().getDeptId()!=null?dto.getSysDept():null);
			sysUser.setSysDept(dto.getSysDept()!=null&&dto.getSysDept().getDeptId()!=null?dto.getSysDept():null);
			sysUser.setSysDuty(dto.getSysDuty()!=null&&dto.getSysDuty().getDutyId()!=null?dto.getSysDuty():null);
			sysUser.setUserName(dto.getUserName());
			sysUser.setSex(dto.getSex());
			sysUser.setLoginName(dto.getLoginName());
			if(StringUtils.isNotEmpty(dto.getLoginPwd())){
				sysUser.setLoginPwd(Md5Encrypt.getPassMD5(dto.getLoginPwd()));
			}
			sysUser.setTel(dto.getTel());
			sysUser.setFax(dto.getFax());
			sysUser.setTelmobile(dto.getTelmobile());
			sysUser.setEmail(dto.getEmail());
//			sysUser.setValidFromDate(dto.getValidFromDate());
//			sysUser.setValidToDate(dto.getValidToDate());
			sysUser.setUseStatus(dto.getUseStatus()==null?2:dto.getUseStatus());
			sysUser.setRoleType(dto.getRoleType());
			if(sysUser.getRoleType()==null||sysUser.getRoleType()==0){
				sysUser.setRoleType(IConstants.ROLE_TYPE_FGW_CODE_3);
			}
			sysUser.setLoginStatus(0);//默认给个值表示不在线
			sysUser.setUserType(dto.getUserType());
			if(dto.getUserType()==3){//查找对应部门所属单位信息
//				SysDept sysDept = commonService.findOne(SysDept.class, dto.getSysDept().getDeptId());
				Condition condition = new Condition();
				condition.add("workunitstype", Operator.ISNOTNULL,null);
				condition.add("workunitstype", Operator.EQ,IConstants.ORGAN_TYPE_3);
				SysOrganization fgwOrganization = commonService.findOne(SysOrganization.class, condition);
				sysUser.setSysOrganization(fgwOrganization);
						
			}
			else{
				sysUser.setSysOrganization(dto.getSysOrganization()!=null&&dto.getSysOrganization().getOrganizationId()!=null?dto.getSysOrganization():null);
			}
			if(dto.getSysDuty()!=null&&dto.getSysDuty().getDutyId()!=null){
				sysUser.setUserduty(dto.getSysDuty().getDutyName());
			}
			//sysUser.setVipinfoflag(dto.getVipinfoflag());//还vip？干掉。
			commonService.save(sysUser);
			logObject = new LogObject("新增或修改了用户信息，用户名称："+sysUser.getUserName()+",业主登录帐号："+sysUser.getLoginName());
		} catch (Exception e) {
			message = "保存用户信息失败";
			e.printStackTrace();
		}
		return "success";
	}

	@GzznLog
	@Action(interceptorRefs={@InterceptorRef("deleteStack")},value="sysUserDelete", results={@Result(name="deleteDenied", location="deleteDenied", type = "redirectAction")})
	public void delete() {
		logger.info("------------>删除操作<-----------");
		try {
			if (StringUtils.isNotEmpty(this.ids)) {
				for (String i : ids.split("@")) {
					commonService.delete(SysUser.class, Integer.valueOf(i));
				}
				logObject = new LogObject("删除用户信息，ids=" + ids);
			}
			outJsonString("{\"success\":true,\"info\":\"删除成功\"}");
		} catch (NumberFormatException e) {
			outJsonString("{\"success\":false,\"info\":\"删除失败\"}");
		}
	}
	
	/**审核业主注册信息：verityStatus审核状态，1-待审批、2-审批通过、3-审批不通过*/
	@GzznLog
	@Action(value = "verifyProcess", results = { @Result(location = "userVerifyQuery.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String verifyProcess(){
		try {
			//获取审核状态
			String  verityStatus = (String) request.getParameter("verityStatus");
			Integer statusType = StringUtils.isNotEmpty(verityStatus)?Integer.valueOf(verityStatus):IConstants.SHENHE_STATUS_1;
			//注册用户信息
			SysUser userObj = null;
			if(dto!=null&&dto.getUserId()!=null){
				userObj = commonService.findOne(SysUser.class, dto.getUserId());
			}
			if(userObj!=null){
				userObj.setUseStatus(statusType);
				commonService.saveOrUpdate(userObj);
				logObject = new LogObject("审核用户信息，审核状态："+ (statusType.equals(IConstants.SHENHE_STATUS_2)?"审核通过":"审核不通过" ) 
						+"，业主用户名："+ userObj.getLoginName() 
						+"，业主真实姓名："+ userObj.getUserName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	

	@Action("grantUserRole")
	public String grantUserRole() {
		List<TreeNodesPojo> treeNodesList = new ArrayList<TreeNodesPojo>();
		//第一步：先查出角色表中的数据sys_role
		List<SysRole> sysRoles = commonService.findAll(SysRole.class);
		//第二步：通过用户的ID去查询用户对应角色表
		Condition con = new Condition("sysUser.userId", Operator.EQ, this.dto.getUserId());
		List<SysUserRole> sysUserRoles = commonService.find(SysUserRole.class, con);
		//第三步：用查出的中间表用roleId与角色表进行对比，相等就选中
		TreeNodesPojo treeNodesPojo = null;
		if (null != sysUserRoles && sysUserRoles.size() > 0) {
			for (SysRole info : sysRoles) {
				treeNodesPojo = new TreeNodesPojo();
				treeNodesPojo.setId(info.getRoleId().toString());
				treeNodesPojo.setName(info.getRoleName());
				treeNodesPojo.setpId("0");
				for (SysUserRole userRoleInfo : sysUserRoles) {
					if (userRoleInfo.getSysRole() == null) {
						break;
					}
					if (info.getRoleId().equals(userRoleInfo.getSysRole().getRoleId())) {
						treeNodesPojo.setChecked(true);
						break;
					}
				}
				treeNodesList.add(treeNodesPojo);
			}
		} else {
			for (SysRole info : sysRoles) {
				treeNodesPojo = new TreeNodesPojo();
				treeNodesPojo.setId(info.getRoleId().toString());
				treeNodesPojo.setName(info.getRoleName());
				treeNodesPojo.setpId("0");
				treeNodesList.add(treeNodesPojo);
			}
		}
		if (this.dto.getUserId() != null) {
			this.setDto(commonService.findOne(SysUser.class, this.dto.getUserId()));
		}
		request.setAttribute("treeNodesList", JSONSerializer.toJSON(treeNodesList));
		return "success";
	}

	/**
	 * 
	 * 方法描述：检查密码
	 * 创建时间：2013-12-19下午6:09:33
	 * 创建人：lxb
	 *
	 */
	@Action("userCheckPass")
	public void checkPass() {
		if (this.dto.getUserId() != null) {
			SysUser sysUser = commonService.findOne(SysUser.class, this.dto.getUserId());
			if (sysUser != null) {
				String oldPass = Md5Encrypt.getPassMD5(this.oldPass);
				if (oldPass.equals(sysUser.getLoginPwd())) {
					outPutString("true");
				} else {
					outPutString("false");
				}
			}
		}
	}

	/**
	 * 
	 * 方法描述：修改密码编辑页
	 * 创建时间：2013-12-19下午5:07:48
	 * 创建人：lxb
	 * @return
	 *
	 */
	@Action("userModifyPassEdit")
	public String userModifyPassEdit() {
		return "success";
	}
	
	/**
	 * 
	 * 方法描述：管理员修改密码编辑页
	 * 创建时间：2013-12-19下午5:07:48
	 * 创建人：lxb
	 * @return
	 *
	 */
	@Action("adminModifyPassEdit")
	public String adminModifyPassEdit() {
		return "success";
	}
	
	/**
	 * 
	 * 方法描述：普通用户修改密码页
	 * 创建时间：2013-12-20下午8:39:04
	 * 创建人：lxb
	 * @return
	 *
	 */
	@Action(value = "genUserModPassEdit", results = { 
			@Result(name = "success", location = "./userModifyPassEdit.jsp") }) 
	public String genUserModPassEdit(){
		SysUser user=this.getUser();
		this.dto.setUserId(user.getUserId());
		return "success";
	}
	
	/**
	 * 
	 * 方法描述：普通用户编辑页
	 * 创建时间：2013-12-20下午8:40:04
	 * 创建人：lxb
	 * @return
	 *
	 */
	@Action(value = "genUserEdit", results = { 
			@Result(name = "success", location = "./sysUserNewOrEdits.jsp") }) 
	public String genUserEdit(){
		SysUser user=this.getUser();
		this.setDto(commonService.findOne(SysUser.class, user.getUserId()));
		return "success";
	}

	@Action("userSavePassword")
	public void savePassword() {
		if (this.newPass != null && !this.newPass.equals(this.dto.getLoginPwd())) {
			outJsonString("{\"success\":false,\"info\":\"密码不一致！\"}");
			return;
		}
		if (StringUtils.isEmpty(this.newPass) || StringUtils.isEmpty(this.dto.getLoginPwd())) {
			outJsonString("{\"success\":false,\"info\":\"修改失败！\"}");
			return;
		}
		SysUser sysUser = commonService.findOne(SysUser.class, this.dto.getUserId());
		sysUser.setLoginPwd(Md5Encrypt.getPassMD5(this.dto.getLoginPwd()));
		try {
			commonService.update(sysUser);
			outJsonString("{\"success\":true,\"info\":\"修改成功！\"}");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			outJsonString("{\"success\":false,\"info\":\"修改失败！\"}");
		}
	}
	
	/**
	 * 
	 * 方法描述：用户名相同的验证
	 * 创建时间：2013-12-24下午7:19:06
	 * 创建人：lxb
	 *
	 */
	@Action("checkLoginName")
	public void checkLoginName(){
		Condition con=new Condition();
		if(this.dto.getUserId()!=null){
			con.add("userId", Operator.NE, this.dto.getUserId());
		}
		con.add("loginName", Operator.ISNOTNULL, null);
		con.add("loginName", Operator.EQ, this.dto.getLoginName());
		List<SysUser> sysusers=commonService.find(SysUser.class, con);
		if(null!=sysusers&&sysusers.size()>0){
			outPutString("false");
		}else{
			outPutString("true");
		}
	}

	@Action("getUserTypeTreeJson")
	public String getUserTypeTreeJson(){
		
		outPutJSON(sysUserService.findUserTypeTreeJson(false));
		
		return null;
	}
	
	@Action("getUseStatusTreeJson")
	public String getUseStatusTreeJson(){
		
		outPutJSON(sysUserService.findUseStatusTreeJson(false));
		
		return null;
	}
	
	@Action("getRoleTypeTreeJson")
	public String getRoleTypeTreeJson(){
		
		outPutJSON(sysUserService.findRoleTypeTreeJson(false));
		
		return null;
	}
	
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

	public List<SysUser> getSysUserList() {
		return sysUserList;
	}

	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public SysUser getDto() {
		return dto;
	}

	public void setDto(SysUser dto) {
		this.dto = dto;
	}

	public PageUtil<SysUser> getPage() {
		return page;
	}

	public void setPage(PageUtil<SysUser> page) {
		this.page = page;
	}

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Override
	public SysUser getModel() {
		return dto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public Integer getConUserId() {
		return conUserId;
	}

	public void setConUserId(Integer conUserId) {
		this.conUserId = conUserId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Map<Integer, String> getYhlxMap() {
		yhlxMap = new HashMap<Integer, String>();
		yhlxMap.put(Integer.valueOf(IConstants.USER_TYPE_1), IConstants.USER_TYPE_NAME_1);
		yhlxMap.put(Integer.valueOf(IConstants.USER_TYPE_2), IConstants.USER_TYPE_NAME_2);
		yhlxMap.put(Integer.valueOf(IConstants.USER_TYPE_3), IConstants.USER_TYPE_NAME_3);
		yhlxMap.put(Integer.valueOf(IConstants.USER_TYPE_4), IConstants.USER_TYPE_NAME_4);
		return yhlxMap;
	}
	public void setYhlxMap(Map<Integer, String> yhlxMap) {
		this.yhlxMap = yhlxMap;
	}
	
	public Map<Integer, String> getYhztMap() {
		yhztMap = new HashMap<Integer, String>();
		yhztMap.put(Integer.valueOf(IConstants.SHENHE_STATUS_1), IConstants.SHENHE_STATUS_NAME_1);
		yhztMap.put(Integer.valueOf(IConstants.SHENHE_STATUS_2), IConstants.SHENHE_STATUS_NAME_2);
		yhztMap.put(Integer.valueOf(IConstants.SHENHE_STATUS_3), IConstants.SHENHE_STATUS_NAME_3);
		yhztMap.put(Integer.valueOf(IConstants.SHENHE_STATUS_4), IConstants.SHENHE_STATUS_NAME_4);
		return yhztMap;
	}
	public void setYhztMap(Map<Integer, String> yhztMap) {
		this.yhztMap = yhztMap;
	}

	public Map<Integer, String> getJslxMap() {
		jslxMap = new HashMap<Integer, String>();
		jslxMap.put(IConstants.ROLE_TYPE_FGW_CODE_1, IConstants.ROLE_TYPE_FGW_NAME_1);
		jslxMap.put(IConstants.ROLE_TYPE_FGW_CODE_2, IConstants.ROLE_TYPE_FGW_NAME_2);
		jslxMap.put(IConstants.ROLE_TYPE_FGW_CODE_3, IConstants.ROLE_TYPE_FGW_NAME_3);
		return jslxMap;
	}

	public void setJslxMap(Map<Integer, String> jslxMap) {
		this.jslxMap = jslxMap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
