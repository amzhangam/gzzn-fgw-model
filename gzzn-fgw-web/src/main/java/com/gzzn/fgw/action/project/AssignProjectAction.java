package com.gzzn.fgw.action.project;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import javassist.expr.NewArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.project.IProjectbaseinfoService;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.service.project.pojo.ProjectbaseinfoParam;
import com.gzzn.fgw.service.report.IPjauditlogService;
import com.gzzn.fgw.service.sys.ISysDeptService;
import com.gzzn.fgw.service.sys.ISysDictionaryitemsService;
import com.gzzn.fgw.service.sys.ISysOrganizationService;
import com.gzzn.fgw.service.sys.ISysUserService;
import com.gzzn.fgw.service.sys.ISysXqService;
import com.gzzn.fgw.service.sys.IXmsbHylbService;
import com.gzzn.fgw.service.sys.IXmsbXmlxService;
import com.gzzn.fgw.service.sys.IXmsbZjxzService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.FjObject;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.fgw.webUtil.ReadFileByteUtil;
import com.gzzn.fgw.webUtil.UploadFileUtil;
import com.gzzn.util.web.PageUtil;


/**
 * 
 * <p>Title: SysDeptAction</p>
 * <p>Description:  待处理项目信息维护 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-26 amzhang  new
 */
@Namespace(value = "/project/xmfp")
public class AssignProjectAction extends BaseAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(AssignProjectAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private ProjectbaseinfoParam projectParams;//系统管理查询参数
	private String nextUserJson;//部门树以JSON格式存储
	private String yearTreeJson;//年份树以JSON格式存储
	private String xmlxTreeJson;//树以JSON格式存储
	private String hylbTreeJson;//树以JSON格式存储
	private String zjxzTreeJson;//树以JSON格式存储
	private String szqyTreeJson;//树以JSON格式存储
	private String projectStatusTreeJson;//树以JSON格式存储
	private String ownerOrganizationTreeJson;//业主单位树以JSON格式存储
	private String manageOrganizationTreeJson;//主管单位树以JSON格式存储
	private SysUser nextUser;//对象
	private Pjbaseinfo obj;//对象
	private Projectinvest subObj;
	private String message;//返回页面信息
	private Pjauditlog pjauditlog;
	private SysOrganization sysProcessOrganization;
	private PageUtil<Pjauditlog> page = new PageUtil<Pjauditlog>();
	private Map<Integer,String> sblxMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmlxMap = new HashMap<Integer, String>();
	private Map<Integer,String> ndMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();
	private Map<Integer,String> zjxzMap = new HashMap<Integer, String>();
	private Map<Integer,String> pjaudittypeMap = new HashMap<Integer, String>();
	private List<Pjadjunct> pjadjuncts = new ArrayList<Pjadjunct>();
	private List<Pjauditlog> pjauditlogs = new ArrayList<Pjauditlog>();
	
	private String lxpf;
	private String ghxz;
	private String ydys;
	private String hjyx;
	private String jnpg;
	private String kypf;
	private String cbsj;
	private String zbtb;
	private String zdcq;
	private String qtqq;
	
	private String fpath = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"upload.dir");//上传文件的路径
	
	private String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"fgwtzc.fullcode");
	
	private InputStream stream; //用于下载
	
	private String preString = "UploadFile";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Date date = new Date();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IProjectbaseinfoService service;
	
	@Autowired
	private IPjauditlogService pjauditlogService;
	
	@Autowired
	private IXmsbHylbService hylbService;
	@Autowired
	private IXmsbXmlxService xmlxService;
	@Autowired
	private IXmsbZjxzService zjxzService;
	@Autowired
	private ISysUserService userService;
	@Autowired
	private ISysOrganizationService organService;
	@Autowired
	private ISysDictionaryitemsService dictionaryitemsService;
	@Autowired
	private ISysXqService xqService;
	 
//	//进入未处理列表界面
//	@Action("list")
//	public String list() throws Exception
//	{
//		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
//		Condition con = new Condition();
//		if(projectParams != null){
//			if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
//				con.add("pjbaseinfo.xmsbXmlx",Operator.ISNOTNULL,null);
//				con.add("pjbaseinfo.xmsbXmlx.xmlxmc", Operator.EQ, projectParams.getXmsbXmlx().getXmlxmc());
//			}
//			if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
//				con.add("pjbaseinfo.projectname",Operator.ISNOTNULL,null);
//				con.add("pjbaseinfo.projectname", Operator.LIKE, projectParams.getProjectname());
//			}
//			if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
//				con.add("pjbaseinfo.projectcode",Operator.ISNOTNULL,null);
//				con.add("pjbaseinfo.projectcode", Operator.LIKE, projectParams.getProjectcode());
//			}
//			if(projectParams.getSysOrganizationByDeclareunitsid()!=null&&projectParams.getSysOrganizationByDeclareunitsid().getOrganizationId()!=null){
//				con.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationName",Operator.LIKE,projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName());
//			}
//			if(StringUtils.isNotEmpty(projectParams.getBeginTime())) {
//				con.add("pjbaseinfo.declartime",Operator.ISNOTNULL,null);
//				con.add("pjbaseinfo.declartime", Operator.GE, sdf.parse(projectParams.getBeginTime()));
//			}
//			if(StringUtils.isNotEmpty(projectParams.getEndTime())) {
//				con.add("pjbaseinfo.declartime",Operator.ISNOTNULL,null);
//				con.add("pjbaseinfo.declartime", Operator.LE, sdf.parse(projectParams.getEndTime()));
//			}
//		}
//		con.add("recordertime", Operator.ISNULL,null);
//		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){
//			if(user.getSysDept()!=null){
//				con.add("sysDeptBySenddeptid", Operator.ISNOTNULL,null);
//				con.add("sysDeptBySenddeptid.deptId", Operator.EQ,user.getSysDept().getDeptId());
//				con.add("pjbaseinfo.pjstatus",Operator.ISNOTNULL,null);
//				if(user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equalsIgnoreCase(fgwtzc)){
//					con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_6);
//				}
//				else{
//					con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_3);
//				}
//			}
//			else{
//				con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
//			}
//		}
//		else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){
//			if(user.getSysOrganization()!=null){
//				con.add("pjbaseinfo.sysOrganizationByDirectorunitsid",Operator.ISNOTNULL,null);
//				con.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId",Operator.EQ,user.getSysOrganization().getOrganizationId());
//				con.add("pjbaseinfo.pjstatus",Operator.ISNOTNULL,null);
//				con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_1);
//			}
//			else{
//				con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
//			}
//		}
//		else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//超级管理员admin
//			
//		}
//		else{
//			con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
//		}
//		Order order2 = new Order(Direction.ASC, "pjbaseinfo.xmsbXmlx.xmlxId");
//		Order order3 = new Order(Direction.ASC, "pjbaseinfo.xmsbHylb.hylbId");
//		Sort sort = new Sort(order2,order3);
//		pjauditlogService.findList(page, con, sort);
//		
//		return "success";
//	}
	
	//进入未处理列表界面
	@Action("list")
	public String list() throws Exception
	{
		String middle = getQuerySql();
		String objBefore = " from Pjauditlog t where 1=1 ";
		String objEnd = " order by t.pjbaseinfo.modifiedTime desc,t.pjbaseinfo.declartime desc,t.pjbaseinfo.xmsbXmlx,t.pjbaseinfo.xmsbHylb,t.pjbaseinfo.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjauditlog t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		page.setSize(15);
		
		page = pjauditlogService.findList(page,countSql, objSql);
		
		return "success";
	}
	
	/**
	   * 项目信息管理列表查询条件初始化
	   * @param cond
	   * @return
	   */
	  private String getQuerySql(){
		  
		  SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		  
		  String sqlString = "";
		  
		  if(projectParams != null){
			  if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
					
					sqlString += " and t.pjbaseinfo.xmsbXmlx is not null and t.pjbaseinfo.xmsbXmlx.xmlxmc = '" + projectParams.getXmsbXmlx().getXmlxmc().trim() + "' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
					sqlString += " and t.pjbaseinfo.projectname is not null and t.pjbaseinfo.projectname like '%" + projectParams.getProjectname().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
					sqlString += " and t.pjbaseinfo.projectcode is not null and t.pjbaseinfo.projectcode like '%" + projectParams.getProjectcode().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
					sqlString += " and t.pjbaseinfo.sysOrganizationByDeclareunitsid is not null and t.pjbaseinfo.sysOrganizationByDeclareunitsid.organizationName like '%" + projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
					sqlString += " and t.pjbaseinfo.declartime is not null and t.pjbaseinfo.declartime >= to_date('" + projectParams.getBeginTime() + "','yyyy-MM-dd')";
				}
				if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
					sqlString += " and t.pjbaseinfo.declartime is not null and t.pjbaseinfo.declartime <= to_date('" + projectParams.getEndTime() + "','yyyy-MM-dd')";
				}
			}
		  
		  if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){
				if(user.getSysDept()!=null){
					sqlString += " and t.sysDeptBySenddeptid is not null and t.sysDeptBySenddeptid.deptId=" + user.getSysDept().getDeptId();
					sqlString += " and t.pjbaseinfo.pjstatus is not null";
					if(user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equalsIgnoreCase(fgwtzc)){
						sqlString += " and t.pjbaseinfo.pjstatus = " + IConstants.XMZT_6;
					}
					else{
						sqlString += " and t.pjbaseinfo.pjstatus = " + IConstants.XMZT_3;
					}
				}
				else{
					sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
				}
				sqlString += " and(t.pjbaseinfo.deleted is null or t.pjbaseinfo.deleted = 0)";
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
				if(user.getSysOrganization()!=null){
					sqlString += " and t.pjbaseinfo.sysOrganizationByDirectorunitsid is not null and t.pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId=" + user.getSysOrganization().getOrganizationId();
					sqlString += " and t.pjbaseinfo.pjstatus is not null and t.pjbaseinfo.pjstatus = " + IConstants.XMZT_1;
				}
				else{
					sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
				}
				sqlString += " and(t.pjbaseinfo.deleted is null or t.pjbaseinfo.deleted = 0)";
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//超级管理员admin
				
			}
			else{
				sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
				sqlString += " and(t.pjbaseinfo.deleted is null or t.pjbaseinfo.deleted = 0)";
			}
		  
		  sqlString += " and t.recordertime is null";
		  sqlString += " and t.pjstatus<>" + IConstants.XMZT_13 + " and t.pjstatus<>" + IConstants.XMZT_14;
			  
		  return sqlString;
	  }
	
	
	private void pjauditloglist() throws Exception
	{
		Condition con = new Condition();
		con.add("pjbaseinfo", Operator.ISNOTNULL,null);
		con.add("pjbaseinfo.projectid", Operator.EQ,obj.getProjectid());
		Order order = new Order(Direction.DESC, "recordertime");
		Sort sort = new Sort(order);
		pjauditlogs = commonService.find(Pjauditlog.class, con,sort);
		
	}
	
	@Action("getNextUserJson")
	public String getNextUserJson(){
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		nextUserJson = userService.findHandlerTreeJson(false,user.getSysDept().getDeptId());
		outPutJSON(nextUserJson);
		
		return null;
	}
	
	@Action("getYearJson")
	public String getYearJson(){
		yearTreeJson = service.findSysDictionaryitemsJson(IConstants.DICTIONARY_ITEM_NF);//年份Json串
		outPutJSON(yearTreeJson);
		return null;
	}
	

	@Action("getHylbTreeJson")
	public String getHylbTreeJson(){
		
		hylbTreeJson = hylbService.findXmsbHylbTreeJson(true);
		outPutJSON(hylbTreeJson);
		return null;
	}
	
	@Action("getXmlxTreeJson")
	public String getXmlxTreeJson(){
		
		xmlxTreeJson = xmlxService.findXmsbXmlxTreeJson(true);
		outPutJSON(xmlxTreeJson);
		return null;
	}
	
	@Action("getZjxzTreeJson")
	public String getZjxzTreeJson(){
		
		zjxzTreeJson = zjxzService.findXmsbZjxzTreeJson(true);
		outPutJSON(zjxzTreeJson);
		return null;
	}
	
	@Action("getSzqyTreeJson")
	public String getSzqyTreeJson(){
		
		szqyTreeJson = xqService.findSysXqTreeJson(false);
		outPutJSON(szqyTreeJson);
		return null;
	}
	
	@Action("getProjectStatusJson")
	public String getProjectStatusJson(){
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		@SuppressWarnings("unchecked")
		List<SysDictionaryitems> list = (List<SysDictionaryitems>) getHttpSession().getAttribute(CommonFiled.SESSION_DIRECTIONARYITEMS);
		if(list!=null&&!list.isEmpty()){
			for(SysDictionaryitems dictionaryitems:list){
				if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(IConstants.DICTIONARY_ITEM_XMZT)){
					TreeNodesPojo objTree = new TreeNodesPojo();
					objTree.setId(dictionaryitems.getItemvalue());
					objTree.setName(dictionaryitems.getItemtext());
					listTree.add(objTree);
				}
			}
		}
		
		projectStatusTreeJson = new Gson().toJson(listTree);
		outPutJSON(projectStatusTreeJson);
		return null;
	}
	
//	@Action("getProjectStatusJson")
//	public String getProjectStatusJson(){
//		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
//		TreeNodesPojo objTree1 = new TreeNodesPojo();
//		objTree1.setId(IConstants.XMXZT_1+"");
//		objTree1.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree1);
//		TreeNodesPojo objTree2 = new TreeNodesPojo();
//		objTree2.setId(IConstants.XMXZT_1+"");
//		objTree2.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree2);
//		TreeNodesPojo objTree3 = new TreeNodesPojo();
//		objTree3.setId(IConstants.XMXZT_1+"");
//		objTree3.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree3);
//		TreeNodesPojo objTree4 = new TreeNodesPojo();
//		objTree4.setId(IConstants.XMXZT_1+"");
//		objTree4.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree4);
//		TreeNodesPojo objTree5 = new TreeNodesPojo();
//		objTree5.setId(IConstants.XMXZT_1+"");
//		objTree5.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree5);
//		TreeNodesPojo objTree6 = new TreeNodesPojo();
//		objTree6.setId(IConstants.XMXZT_1+"");
//		objTree6.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree6);
//		TreeNodesPojo objTree7 = new TreeNodesPojo();
//		objTree7.setId(IConstants.XMXZT_1+"");
//		objTree7.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree7);
//		TreeNodesPojo objTree8 = new TreeNodesPojo();
//		objTree8.setId(IConstants.XMXZT_1+"");
//		objTree8.setName(IConstants.XMXZT_1_NAME);
//		listTree.add(objTree8);
//		
//		projectStatusTreeJson = new Gson().toJson(listTree);
//		outPutJSON(projectStatusTreeJson);
//		return null;
//	}
	
	@Action("assign")
	public String assign() throws Exception{
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		if(StringUtils.isNotEmpty(id)){
			
			obj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(id));
			if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
				subObj = obj.getProjectinvests().iterator().next();
			}
			if(obj!=null){
				getHttpSession().setAttribute("obj",obj);
			}
			if(subObj!=null){
				getHttpSession().setAttribute("subObj",subObj);
			}
			
			
			Condition condition = new Condition();
			condition.add("objectid",Operator.EQ,obj.getProjectid());
			pjadjuncts = commonService.find(Pjadjunct.class, condition);
			if(pjadjuncts!=null&&!pjadjuncts.isEmpty()){
				for(Pjadjunct pjadjunct:pjadjuncts){
					if(IConstants.FJLX_0==pjadjunct.getPjadjuncttype()){
						if(lxpf==null||lxpf.equals("")){
							lxpf = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_1==pjadjunct.getPjadjuncttype()){
						if(ghxz==null||ghxz.equals("")){
							ghxz = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_2==pjadjunct.getPjadjuncttype()){
						if(ydys==null||ydys.equals("")){
							ydys = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_3==pjadjunct.getPjadjuncttype()){
						if(hjyx==null||hjyx.equals("")){
							hjyx = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_4==pjadjunct.getPjadjuncttype()){
						if(jnpg==null||jnpg.equals("")){
							jnpg = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_5==pjadjunct.getPjadjuncttype()){
						if(kypf==null||kypf.equals("")){
							kypf = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_6==pjadjunct.getPjadjuncttype()){
						if(cbsj==null||cbsj.equals("")){
							cbsj = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_7==pjadjunct.getPjadjuncttype()){
						if(zbtb==null||zbtb.equals("")){
							zbtb = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_8==pjadjunct.getPjadjuncttype()){
						if(zdcq==null||zdcq.equals("")){
							zdcq = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_9==pjadjunct.getPjadjuncttype()){
						if(qtqq==null||qtqq.equals("")){
							qtqq = pjadjunct.getFilename();
						}
					}
				}
			}
			
			Condition con = new Condition();
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位用户

				con.add("pjbaseinfo", Operator.ISNOTNULL,null);
				con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
				con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
				con.add("recordertime", Operator.ISNULL,null);
				pjauditlog = commonService.findOne(Pjauditlog.class, con);
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//发改委用户
				if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
					
					con.add("pjbaseinfo", Operator.ISNOTNULL,null);
					con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
					con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
					con.add("recordertime", Operator.ISNULL,null);
					if(obj.getPjstatus().equals(IConstants.XMZT_6)){
						con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_6);
					}
					else if(obj.getPjstatus().equals(IConstants.XMZT_9)){
						con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_9);
					}
					else{
						con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
					}
					pjauditlog = commonService.findOne(Pjauditlog.class, con);
				}
				else{//其他处室
					con.add("pjbaseinfo", Operator.ISNOTNULL,null);
					con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
					con.add("sysDeptBySenddeptid", Operator.ISNOTNULL,null);
					con.add("sysDeptBySenddeptid.deptId",Operator.EQ,user.getSysDept().getDeptId());
					con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
					con.add("recordertime", Operator.ISNULL,null);
					if(obj.getPjstatus().equals(IConstants.XMZT_2)){
						con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_2);
					}
					else if(obj.getPjstatus().equals(IConstants.XMZT_5)){
						con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_5);
					}
					else{
						con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
					}
					pjauditlog = commonService.findOne(Pjauditlog.class, con);
					
					Condition condition1 = new Condition();
					condition1.add("fullcode",Operator.ISNOTNULL,null);
					condition1.add("fullcode",Operator.EQ,fgwtzc);
					SysDept tzcDept = (SysDept) commonService.findOne(SysDept.class, condition1);
					if(tzcDept!=null){
						pjauditlog.setSysDeptBySenddeptid(tzcDept);
						pjauditlog.setPjauditresult(null);
					}
				}
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//系统管理员
				con.add("pjbaseinfo", Operator.ISNOTNULL,null);
				con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
				con.add("recordertime", Operator.ISNULL,null);
				con.add("pjbaseinfo.pjstatus",Operator.GE,IConstants.XMZT_2);
				con.add("pjbaseinfo.pjstatus",Operator.LE,IConstants.XMZT_9);
				con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_3);
				con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_4);
				con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_7);
				con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_8);
				pjauditlog = commonService.findOne(Pjauditlog.class, con);
			}
			else{
				con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
			}
			
		}
		else{
			obj = (Pjbaseinfo) getHttpSession().getAttribute("obj");
			subObj = (Projectinvest) getHttpSession().getAttribute("subObj");
			
		}
		pjauditloglist();
		return "success";
	}
	
	@Action("view")
	public String view() throws Exception{
		if(StringUtils.isNotEmpty(id)){
			
			obj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(id));
			if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
				subObj = obj.getProjectinvests().iterator().next();
			}
			if(obj!=null){
				getHttpSession().setAttribute("obj",obj);
			}
			if(subObj!=null){
				getHttpSession().setAttribute("subObj",subObj);
			}
			
			List<FjObject> fjList =  (List<FjObject>) getHttpSession().getAttribute(CommonFiled.SESSION_FJ);
			
			if(fjList==null){
				fjList =  new ArrayList<FjObject>();
			}
			
			Condition condition = new Condition();
			condition.add("objectid",Operator.EQ,obj.getProjectid());
			pjadjuncts = commonService.find(Pjadjunct.class, condition);
			if(pjadjuncts!=null&&!pjadjuncts.isEmpty()){
				for(Pjadjunct pjadjunct:pjadjuncts){
					if(IConstants.FJLX_0==pjadjunct.getPjadjuncttype()){
						if(lxpf==null||lxpf.equals("")){
							lxpf = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_1==pjadjunct.getPjadjuncttype()){
						if(ghxz==null||ghxz.equals("")){
							ghxz = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_2==pjadjunct.getPjadjuncttype()){
						if(ydys==null||ydys.equals("")){
							ydys = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_3==pjadjunct.getPjadjuncttype()){
						if(hjyx==null||hjyx.equals("")){
							hjyx = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_4==pjadjunct.getPjadjuncttype()){
						if(jnpg==null||jnpg.equals("")){
							jnpg = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_5==pjadjunct.getPjadjuncttype()){
						if(kypf==null||kypf.equals("")){
							kypf = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_6==pjadjunct.getPjadjuncttype()){
						if(cbsj==null||cbsj.equals("")){
							cbsj = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_7==pjadjunct.getPjadjuncttype()){
						if(zbtb==null||zbtb.equals("")){
							zbtb = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_8==pjadjunct.getPjadjuncttype()){
						if(zdcq==null||zdcq.equals("")){
							zdcq = pjadjunct.getFilename();
						}
					}
					else if(IConstants.FJLX_9==pjadjunct.getPjadjuncttype()){
						if(qtqq==null||qtqq.equals("")){
							qtqq = pjadjunct.getFilename();
						}
					}
				}
			}
			
//			setObjectProperty(fjList);
			
		}
		else{
			obj = (Pjbaseinfo) getHttpSession().getAttribute("obj");
			subObj = (Projectinvest) getHttpSession().getAttribute("subObj");
			
		}
		pjauditloglist();
		return "success";
	}
	
	
	
	@Action("getOwnerOrganizationJson")
	public String getOwnerOrganizationJson(){
		ownerOrganizationTreeJson = service.findSysOrginationJson(IConstants.ORGAN_TYPE_1);
		outPutJSON(ownerOrganizationTreeJson);
		return null;
	}
	
	//TODO
	//这个anction的功能是不是有错，是想检索项目建设管理单位吗？
	@Action("getManageOrganizationJson")
	public String getManageOrganizationJson(){
		manageOrganizationTreeJson = service.findSysOrginationJson(IConstants.ORGAN_TYPE_1);
		outPutJSON(manageOrganizationTreeJson);
		return null;
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "../dclxm/list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		obj = commonService.findOne(Pjbaseinfo.class,obj.getProjectid());
		try {
			Map<Integer,SysDept> deptMap = (Map<Integer, SysDept>) getHttpSession().getAttribute(CommonFiled.SESSION_DEPT_MAP);
			Map<Integer,SysXq> xqMap = (Map<Integer, SysXq>) getHttpSession().getAttribute(CommonFiled.SESSION_XQ_MAP);
			Map<Integer,XmsbXmlx> xmlxMap = (Map<Integer, XmsbXmlx>) getHttpSession().getAttribute(CommonFiled.SESSION_XMLX_MAP);
			sysProcessOrganization = getSysProcessOrganization();
			SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			Pjauditlog tempPjaduitlog = commonService.findOne(Pjauditlog.class, pjauditlog.getPjauditlogid());
			pjauditlog.setSysOrganizationByOrganizationId(tempPjaduitlog.getSysOrganizationByOrganizationId());
			pjauditlog.setSysDeptByDeptId(tempPjaduitlog.getSysDeptByDeptId());
			pjauditlog.setSysOrganizationByPjauditunits(user.getSysOrganization());
			pjauditlog.setPjbaseinfo(tempPjaduitlog.getPjbaseinfo());
			pjauditlog.setSysDeptBySenddeptid(user.getSysDept());
			
			//更新项目最新的这条日志
			pjauditlog.setRecordertime(new Date());
			pjauditlog.setSysUser(user);
			pjauditlog.setRecordername(user.getUserName());
			pjauditlog.setPjauditresult(null);
			
			obj.setAuditdept(user.getSysDept()!=null?user.getSysDept().getDeptId():null);
			obj.setAuditdeptname(user.getSysDept()!=null?user.getSysDept().getDeptname():null);
			obj.setSysUserByRecorderid(user);
			obj.setRecordername(user.getUserName());
			obj.setRecordertime(new Date());
			
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){//发改委用户
				if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
					
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
//						pjauditlog.setPjaudittype(IConstants.SHLX_6);
						//产生一条新的审核日志记录
						Pjauditlog newObj = new Pjauditlog();
						newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
						newObj.setSysDeptByDeptId(user.getSysDept());
						newObj.setSysDeptBySenddeptid(user.getSysDept());
						newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
						newObj.setPjbaseinfo(obj);
						newObj.setSysUser(nextUser);
						newObj.setRecordername(nextUser!=null?nextUser.getUserName():null);
						newObj.setPjaudittype(IConstants.SHLX_7);
						commonService.save(newObj);
						obj.setPjstatus(IConstants.XMZT_7);//投资处经办审核
						obj.setNextauditdept(user.getSysDept().getDeptId());
						obj.setNextauditdeptname(user.getSysDept().getDeptname());
					}
				}
				else{//其他处室
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
//						pjauditlog.setPjaudittype(IConstants.SHLX_2);
						//产生一条新的审核日志记录
						Pjauditlog newObj = new Pjauditlog();
						newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
						newObj.setSysDeptByDeptId(user.getSysDept());
						newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
						newObj.setPjbaseinfo(obj);
						newObj.setSysDeptBySenddeptid(user.getSysDept());
						newObj.setSysUser(nextUser);
						newObj.setRecordername(nextUser!=null?nextUser.getUserName():null);
						newObj.setPjaudittype(IConstants.SHLX_3);
						commonService.save(newObj);
						obj.setPjstatus(IConstants.XMZT_3);//各处室经办审核
						obj.setNextauditdept(user.getSysDept().getDeptId());
						obj.setNextauditdeptname(user.getSysDept().getDeptname());
					}
				}
			}
			
			commonService.saveOrUpdate(pjauditlog);
			
		
			commonService.saveOrUpdate(obj);
			logObject = new LogObject("分派日志信息",pjauditlog.getPjauditlogid()," " + obj.getProjectname()+user.getUserName()+"分派给本处室的"+(nextUser!=null?nextUser.getUserName():""),obj);
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存数据失败";
		}
		return "success";
	}
	
	@Action("batchassign")
	public String batchassign(){
		
		if(StringUtils.isNotEmpty(id)){
			request.setAttribute("id",id);
		}
		
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "batchsave")
	public String batchsave(){
		message = "保存数据成功";
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		sysProcessOrganization = getSysProcessOrganization();
		
		if (StringUtils.isNotEmpty(id)) {
			int totalcount = 0;
			int successcount = 0;
			int failcount = 0;
			
			for (String i : id.split("@")) {
				totalcount++;
				Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i));
				
				if(pjbaseinfo!=null&&pjbaseinfo.getPjstatus()==IConstants.XMZT_3){
					continue;
				}
				
				Pjauditlog myPjauditlog = null;
				Condition con = new Condition();
				if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//发改委用户
					if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
						
						con.add("pjbaseinfo", Operator.ISNOTNULL,null);
						con.add("pjbaseinfo.projectid",Operator.EQ,pjbaseinfo.getProjectid());
						con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
						con.add("recordertime", Operator.ISNULL,null);
						if(pjbaseinfo.getPjstatus().equals(IConstants.XMZT_6)){
							con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_6);
						}
						else if(pjbaseinfo.getPjstatus().equals(IConstants.XMZT_9)){
							con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_9);
						}
						else{
							con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
						}
						myPjauditlog = commonService.findOne(Pjauditlog.class, con);
					}
					else{//其他处室
						con.add("pjbaseinfo", Operator.ISNOTNULL,null);
						con.add("pjbaseinfo.projectid",Operator.EQ,pjbaseinfo.getProjectid());
						con.add("sysDeptBySenddeptid", Operator.ISNOTNULL,null);
						con.add("sysDeptBySenddeptid.deptId",Operator.EQ,user.getSysDept().getDeptId());
						con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
						con.add("recordertime", Operator.ISNULL,null);
						if(pjbaseinfo.getPjstatus().equals(IConstants.XMZT_2)){
							con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_2);
						}
						else if(pjbaseinfo.getPjstatus().equals(IConstants.XMZT_5)){
							con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_5);
						}
						else{
							con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
						}
						myPjauditlog = commonService.findOne(Pjauditlog.class, con);
						
						Condition condition1 = new Condition();
						condition1.add("fullcode",Operator.ISNOTNULL,null);
						condition1.add("fullcode",Operator.EQ,fgwtzc);
						SysDept tzcDept = (SysDept) commonService.findOne(SysDept.class, condition1);
						if(tzcDept!=null&&myPjauditlog!=null){
							myPjauditlog.setSysDeptBySenddeptid(tzcDept);
							myPjauditlog.setPjauditresult(null);
						}
					}
				}
				else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//系统管理员
					con.add("pjbaseinfo", Operator.ISNOTNULL,null);
					con.add("pjbaseinfo.projectid",Operator.EQ,pjbaseinfo.getProjectid());
					con.add("recordertime", Operator.ISNULL,null);
					con.add("pjbaseinfo.pjstatus",Operator.GE,IConstants.XMZT_2);
					con.add("pjbaseinfo.pjstatus",Operator.LE,IConstants.XMZT_9);
					con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_3);
					con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_4);
					con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_7);
					con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_8);
					myPjauditlog = commonService.findOne(Pjauditlog.class, con);
				}
				else{
					outPutError("用户所属角色无权分派！");
					return null;
				}
				
				try {
					if(myPjauditlog!=null){
						Map<Integer,SysDept> deptMap = (Map<Integer, SysDept>) getHttpSession().getAttribute(CommonFiled.SESSION_DEPT_MAP);
						Map<Integer,SysXq> xqMap = (Map<Integer, SysXq>) getHttpSession().getAttribute(CommonFiled.SESSION_XQ_MAP);
						Map<Integer,XmsbXmlx> xmlxMap = (Map<Integer, XmsbXmlx>) getHttpSession().getAttribute(CommonFiled.SESSION_XMLX_MAP);
						myPjauditlog.setSysOrganizationByPjauditunits(user.getSysOrganization());
						myPjauditlog.setSysDeptBySenddeptid(user.getSysDept());
						
						//更新项目最新的这条日志
						myPjauditlog.setRecordertime(new Date());
						myPjauditlog.setSysUser(user);
						myPjauditlog.setRecordername(user.getUserName());
						myPjauditlog.setPjauditresult(null);
						
						pjbaseinfo.setAuditdept(user.getSysDept()!=null?user.getSysDept().getDeptId():null);
						pjbaseinfo.setAuditdeptname(user.getSysDept()!=null?user.getSysDept().getDeptname():null);
						pjbaseinfo.setSysUserByRecorderid(user);
						pjbaseinfo.setRecordername(user.getUserName());
						pjbaseinfo.setRecordertime(new Date());
						
						if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){//发改委用户
							if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
								
								if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
//									myPjauditlog.setPjaudittype(IConstants.SHLX_6);
									//产生一条新的审核日志记录
									Pjauditlog newObj = new Pjauditlog();
									newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
									newObj.setSysDeptByDeptId(user.getSysDept());
									newObj.setSysDeptBySenddeptid(user.getSysDept());
									newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
									newObj.setPjbaseinfo(pjbaseinfo);
									newObj.setSysUser(nextUser);
									newObj.setRecordername(nextUser!=null?nextUser.getUserName():null);
									newObj.setPjaudittype(IConstants.SHLX_7);
									commonService.save(newObj);
									pjbaseinfo.setPjstatus(IConstants.XMZT_7);//投资处经办审核
									pjbaseinfo.setNextauditdept(user.getSysDept().getDeptId());
									pjbaseinfo.setNextauditdeptname(user.getSysDept().getDeptname());
								}
							}
							else{//其他处室
								if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
//									myPjauditlog.setPjaudittype(IConstants.SHLX_2);
									//产生一条新的审核日志记录
									Pjauditlog newObj = new Pjauditlog();
									newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
									newObj.setSysDeptByDeptId(user.getSysDept());
									newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
									newObj.setPjbaseinfo(pjbaseinfo);
									newObj.setSysDeptBySenddeptid(user.getSysDept());
									newObj.setSysUser(nextUser);
									newObj.setRecordername(nextUser!=null?nextUser.getUserName():null);
									newObj.setPjaudittype(IConstants.SHLX_3);
									commonService.save(newObj);
									pjbaseinfo.setPjstatus(IConstants.XMZT_3);//各处室经办审核
									pjbaseinfo.setNextauditdept(user.getSysDept().getDeptId());
									pjbaseinfo.setNextauditdeptname(user.getSysDept().getDeptname());
								}
							}
						}
						
						commonService.saveOrUpdate(myPjauditlog);
						
					
						commonService.saveOrUpdate(pjbaseinfo);
						logObject = new LogObject("分派日志信息",myPjauditlog.getPjauditlogid(),pjbaseinfo.getProjectname()+user.getUserName()+"分派给相应的处室",pjbaseinfo);
						successcount++;
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
					message = "保存数据失败";
				}
			}
			outPutMsg(true, "分派成功"+successcount+"条"+(successcount==totalcount?"":"，另有"+(totalcount-successcount)+"条之前已经分派过了。"));
		}
		
		return null;
	}
	
	public SysOrganization getSysProcessOrganization() {
		Condition condition = new Condition();
		condition.add("workunitstype", Operator.ISNOTNULL,null);
		condition.add("workunitstype", Operator.EQ,IConstants.ORGAN_TYPE_3);
		sysProcessOrganization = commonService.findOne(SysOrganization.class, condition);
		return sysProcessOrganization;
	}

	public void setSysProcessOrganization(SysOrganization sysProcessOrganization) {
		this.sysProcessOrganization = sysProcessOrganization;
	}

	//删除待处理项目信息
	@GzznLog
	@Action(value = "delete", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					//commonService.delete(Pjbaseinfo.class, Integer.parseInt(i));
					Pjbaseinfo delObj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i));
					if(delObj!=null){
						delObj.setDeleted(IConstants.DEL_FLAG_TRUE);//删除标记：0-非删除 1-逻辑删除
						commonService.saveOrUpdate(delObj);
					}
				}
				logObject = new LogObject("删除待处理项目信息，ids=" + id);
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		return "success";
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ProjectbaseinfoParam getProjectParams() {
		return projectParams;
	}

	public void setProjectParams(ProjectbaseinfoParam projectParams) {
		this.projectParams = projectParams;
	}

	public Pjbaseinfo getObj() {
		return obj;
	}
	public void setObj(Pjbaseinfo obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<Pjauditlog> getPage() {
		return page;
	}
	public void setPage(PageUtil<Pjauditlog> page) {
		this.page = page;
	}

//	public Map<Integer, String> getXmlxMap() {
//		xmlxMap = putDictionaryitemsToMap(xmlxMap, IConstants.DICTIONARY_ITEM_XMLX);
//		return xmlxMap;
//	}
//
//	public void setXmlxMap(Map<Integer, String> xmlx) {
//		xmlxMap = xmlx;
//	}

	public Map<Integer, String> getNdMap() {
		ndMap = putDictionaryitemsToMap(ndMap, IConstants.DICTIONARY_ITEM_NF);
		return ndMap;
	}

	public void setNdMap(Map<Integer, String> nd) {
		ndMap = nd;
	}
	
	public Map<Integer, String> getXmztMap() {
		xmztMap = putDictionaryitemsToMap(xmztMap, IConstants.DICTIONARY_ITEM_XMZT);
		return xmztMap;
	}

	public void setXmztMap(Map<Integer, String> xmztMap) {
		this.xmztMap = xmztMap;
	}
	
	public Map<Integer, String> getZjxzMap() {
		
		zjxzMap = putDictionaryitemsToMap(zjxzMap, IConstants.DICTIONARY_ITEM_ZJXZ);
		return zjxzMap;
	}

	public void setZjxzMap(Map<Integer, String> zjxzMap) {
		this.zjxzMap = zjxzMap;
	}

	private Map<Integer, String> putDictionaryitemsToMap(Map<Integer,String> map,String name){
		@SuppressWarnings("unchecked")
		List<SysDictionaryitems> list = (List<SysDictionaryitems>) getHttpSession().getAttribute(CommonFiled.SESSION_DIRECTIONARYITEMS);
		if(list!=null&&!list.isEmpty()){
			for(SysDictionaryitems dictionaryitems:list){
				if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(name)){
					map.put(Integer.valueOf(dictionaryitems.getItemvalue()),dictionaryitems.getItemtext());
				}
			}
		}
		return map;
	}

	public Map<Integer, String> getSblxMap() {
		sblxMap.put(IConstants.DECLARE_TYPE_CODE_1,IConstants.DECLARE_TYPE_NAME_1);
		sblxMap.put(IConstants.DECLARE_TYPE_CODE_2,IConstants.DECLARE_TYPE_NAME_2);
		return sblxMap;
	}

	public void setSblxMap(Map<Integer, String> sblxMap) {
		this.sblxMap = sblxMap;
	}

	public Projectinvest getSubObj() {
		return subObj;
	}

	public void setSubObj(Projectinvest subObj) {
		this.subObj = subObj;
	}


	@Action("checkFile")
	public void checkFile() throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			Pjadjunct vo = this.commonService.findOne(Pjadjunct.class, Integer.valueOf(id));
			//检测下载文件是否存在
//			String docAliasfileName = vo.getFileurl()==null?"":vo.getFileurl().substring(vo.getFileurl().lastIndexOf("\\")+1);
//			String declartimeString = vo.getPjbaseinfo().getDeclartime()==null?null:ymd.format(vo.getPjbaseinfo().getDeclartime());
//			String mypathString  = fpath;
//			if(declartimeString!=null){
//				mypathString += declartimeString + "\\";
//			}
//			boolean flag = UploadFileUtil.checkFileName(docAliasfileName, mypathString);
			File file = new File(vo.getFileurl());
			boolean flag = file.exists();
			if (!flag) {
				outJsonString("{\"info\":\"false\"}");
			} else {
				outJsonString("{\"info\":\"true\"}");
			}
		}
	}

	@GzznLog
	//文件下载
	@Action("download")
	public void download() {
		try {
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				Pjadjunct vo = this.commonService
						.findOne(Pjadjunct.class, Integer.valueOf(id));
//				String docAliasfileName = vo.getFileurl()==null?"":vo.getFileurl().substring(vo.getFileurl().indexOf("_")+1);
//
//				String declartimeString = vo.getPjbaseinfo().getDeclartime()==null?null:ymd.format(vo.getPjbaseinfo().getDeclartime());
//				
//				String mypathString  = fpath;
//				
//				if(declartimeString!=null){
//					mypathString += declartimeString + "\\";
//				}
//				File file = new File(mypathString + docAliasfileName);
				File file = new File(vo.getFileurl());
				byte[] bytes = ReadFileByteUtil.getBytes(file);
				String fileName = URLEncoder.encode(vo.getFilename(), "UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
				ServletOutputStream out = response.getOutputStream();
				out.write(bytes);
				out.close();
				logObject = new LogObject("下载了一份附件，附件名称为：" + vo.getFilename());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public String getLxpf() {
		return lxpf;
	}

	public void setLxpf(String lxpf) {
		this.lxpf = lxpf;
	}

	public String getGhxz() {
		return ghxz;
	}

	public void setGhxz(String ghxz) {
		this.ghxz = ghxz;
	}

	public String getYdys() {
		return ydys;
	}

	public void setYdys(String ydys) {
		this.ydys = ydys;
	}

	public String getHjyx() {
		return hjyx;
	}

	public void setHjyx(String hjyx) {
		this.hjyx = hjyx;
	}

	public String getJnpg() {
		return jnpg;
	}

	public void setJnpg(String jnpg) {
		this.jnpg = jnpg;
	}

	public String getKypf() {
		return kypf;
	}

	public void setKypf(String kypf) {
		this.kypf = kypf;
	}

	public String getCbsj() {
		return cbsj;
	}

	public void setCbsj(String cbsj) {
		this.cbsj = cbsj;
	}

	public String getZbtb() {
		return zbtb;
	}

	public void setZbtb(String zbtb) {
		this.zbtb = zbtb;
	}

	public String getZdcq() {
		return zdcq;
	}

	public void setZdcq(String zdcq) {
		this.zdcq = zdcq;
	}

	public String getQtqq() {
		return qtqq;
	}

	public void setQtqq(String qtqq) {
		this.qtqq = qtqq;
	}

	

	public String getOwnerOrganizationTreeJson() {
		return ownerOrganizationTreeJson;
	}

	public void setOwnerOrganizationTreeJson(String ownerOrganizationTreeJson) {
		this.ownerOrganizationTreeJson = ownerOrganizationTreeJson;
	}

	public String getManageOrganizationTreeJson() {
		return manageOrganizationTreeJson;
	}

	public void setManageOrganizationTreeJson(String manageOrganizationTreeJson) {
		this.manageOrganizationTreeJson = manageOrganizationTreeJson;
	}

	public Map<Integer, String> getPjaudittypeMap() {
		pjaudittypeMap = putDictionaryitemsToMap(pjaudittypeMap, IConstants.DICTIONARY_ITEM_XMSHLX);
		return pjaudittypeMap;
	}


	public void setPjaudittypeMap(Map<Integer, String> pjaudittypeMap) {
		this.pjaudittypeMap = pjaudittypeMap;
	}


	public Map<Integer, String> getXmlxMap() {
		return xmlxMap;
	}

	public void setXmlxMap(Map<Integer, String> xmlxMap) {
		this.xmlxMap = xmlxMap;
	}

	public String getPreString() {
		return preString;
	}

	public void setPreString(String preString) {
		this.preString = preString;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IProjectbaseinfoService getService() {
		return service;
	}

	public void setService(IProjectbaseinfoService service) {
		this.service = service;
	}

	public IXmsbHylbService getHylbService() {
		return hylbService;
	}

	public void setHylbService(IXmsbHylbService hylbService) {
		this.hylbService = hylbService;
	}

	public IXmsbXmlxService getXmlxService() {
		return xmlxService;
	}

	public void setXmlxService(IXmsbXmlxService xmlxService) {
		this.xmlxService = xmlxService;
	}

	public IXmsbZjxzService getZjxzService() {
		return zjxzService;
	}

	public void setZjxzService(IXmsbZjxzService zjxzService) {
		this.zjxzService = zjxzService;
	}

	public ISysOrganizationService getOrganService() {
		return organService;
	}

	public void setOrganService(ISysOrganizationService organService) {
		this.organService = organService;
	}

	public ISysDictionaryitemsService getDictionaryitemsService() {
		return dictionaryitemsService;
	}

	public void setDictionaryitemsService(
			ISysDictionaryitemsService dictionaryitemsService) {
		this.dictionaryitemsService = dictionaryitemsService;
	}

	public ISysXqService getXqService() {
		return xqService;
	}

	public void setXqService(ISysXqService xqService) {
		this.xqService = xqService;
	}

	public List<Pjadjunct> getPjadjuncts() {
		return pjadjuncts;
	}

	public void setPjadjuncts(List<Pjadjunct> pjadjuncts) {
		this.pjadjuncts = pjadjuncts;
	}

	public Pjauditlog getPjauditlog() {
		return pjauditlog;
	}

	public void setPjauditlog(Pjauditlog pjauditlog) {
		this.pjauditlog = pjauditlog;
	}


	public List<Pjauditlog> getPjauditlogs() {
		return pjauditlogs;
	}


	public void setPjauditlogs(List<Pjauditlog> pjauditlogs) {
		this.pjauditlogs = pjauditlogs;
	}


	public SysUser getNextUser() {
		return nextUser;
	}


	public void setNextUser(SysUser nextUser) {
		this.nextUser = nextUser;
	}

	
	
}
