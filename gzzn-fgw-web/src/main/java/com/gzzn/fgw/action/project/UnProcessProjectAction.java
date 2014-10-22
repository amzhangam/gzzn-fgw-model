package com.gzzn.fgw.action.project;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import javassist.expr.NewArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

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
import com.gzzn.fgw.expExcel.ProjectbaseinfoListExpExcel;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysDx;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.SysYjmb;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.ISmsService;
import com.gzzn.fgw.service.project.IProjectbaseinfoService;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.service.project.pojo.ProjectbaseinfoParam;
import com.gzzn.fgw.service.report.IPjauditlogService;
import com.gzzn.fgw.service.sys.ISysDeptService;
import com.gzzn.fgw.service.sys.ISysDictionaryitemsService;
import com.gzzn.fgw.service.sys.ISysOrganizationService;
import com.gzzn.fgw.service.sys.ISysXqService;
import com.gzzn.fgw.service.sys.IXmsbHylbService;
import com.gzzn.fgw.service.sys.IXmsbXmlxService;
import com.gzzn.fgw.service.sys.IXmsbZjxzService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.PojoCopyUtil;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.FjObject;
import com.gzzn.fgw.webUtil.ProjectlogProcess;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.fgw.webUtil.ReadFileByteUtil;
import com.gzzn.fgw.webUtil.UploadFileUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.common.MessageUtil;
import com.gzzn.util.security.Md5Encrypt;
import com.gzzn.util.string.StringUitl;
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
@Namespace(value = "/project/dclxm")
public class UnProcessProjectAction extends BaseAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(UnProcessProjectAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private String ids;//删除的id，多个间用@隔开
	private ProjectbaseinfoParam projectParams;//系统管理查询参数
	private String sysDeptTreeJson;//部门树以JSON格式存储
	private String yearTreeJson;//年份树以JSON格式存储
	private String xmlxTreeJson;//树以JSON格式存储
	private String hylbTreeJson;//树以JSON格式存储
	private String zjxzTreeJson;//树以JSON格式存储
	private String szqyTreeJson;//树以JSON格式存储
	private String projectStatusTreeJson;//树以JSON格式存储
	private String ownerOrganizationTreeJson;//业主单位树以JSON格式存储
	private String manageOrganizationTreeJson;//主管单位树以JSON格式存储
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
	
	
	private String fpath = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"upload.dir");//上传文件的路径
	
	private String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"fgwtzc.fullcode");
	
	private InputStream stream; //用于下载
	
	private String preString = "UploadFile";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private SimpleDateFormat sdfOnlyYear = new SimpleDateFormat("yyyy");
	
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
	private ISysDeptService deptService;
	@Autowired
	private ISysOrganizationService organService;
	@Autowired
	private ISysDictionaryitemsService dictionaryitemsService;
	@Autowired
	private ISysXqService xqService;
	@Resource
	private ISmsService smsService;
	
	private Boolean moreSearchBool=false;//显示更多查询条件... 是否显示
	
	@Autowired
	private JdbcTemplate jdbcTemplate2;
	
	/**LHQ加入：导出报表功能*/
	private InputStream excelFile;//excel导出文件流
	private String downloadFileName;//excel导出文件名称
	private String expExcelFilePath = getWebRootPath() + "/upload/pjbaseinfo/";//导出Excel文件的路径
	
	/**LHQ加入：项目信息管理列表 导出报表功能*/
	@Action(value = "expExcel", results = { @Result(name="excel", type="stream",
		  params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String expExcel(){
		 long begin = System.currentTimeMillis();

		 String middle = getQuerySql();
		String objBefore = " from Pjauditlog t where 1=1 ";
		String objEnd = " order by t.pjbaseinfo.modifiedTime desc,t.pjbaseinfo.declartime desc,t.pjbaseinfo.xmsbXmlx,t.pjbaseinfo.xmsbHylb ";
			
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		 List<PjbaseinfoPojo> list = pjauditlogService.findList(countSql,objSql);
		 
		 setDownloadFileName(downFileNameTranscode("广州市政府投资项目库待处理项目汇总表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		 setExcelFile(new ProjectbaseinfoListExpExcel().expExcelTempFile(getLoginUser(), list, getXmztMap(), expExcelFilePath+"pjbaseExp.xls", 5));
		 
		 logger.info("导出广州市政府投资项目库待处理项目汇总表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		 return "excel";
	}
	
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
//		con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_13);
//		con.add("pjbaseinfo.pjstatus",Operator.NE,IConstants.XMZT_14);
//		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){
//			if(user.getSysDept()!=null){
//				con.add("sysDeptBySenddeptid", Operator.ISNOTNULL,null);
//				con.add("sysDeptBySenddeptid.deptId", Operator.EQ,user.getSysDept().getDeptId());
//				if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_3)){
//					con.add("sysUser", Operator.ISNOTNULL,null);
//					con.add("sysUser.userId", Operator.EQ,user.getUserId());
//				}
//				else if(user.getRoleType()==null||!user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){
//					con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
//				}
//			}
//			else{
//				con.add("pjbaseinfo.projectid",Operator.LT,0);//加入一个条件让它无记录
//			}
//		}
//		else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
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
//		Order order0 = new Order(Direction.DESC, "pjbaseinfo.modifiedTime");
//		Order order1 = new Order(Direction.DESC, "pjbaseinfo.declartime");
//		Order order2 = new Order(Direction.ASC, "pjbaseinfo.xmsbXmlx.xmlxId");
//		Order order3 = new Order(Direction.ASC, "pjbaseinfo.xmsbHylb.hylbId");
//		Sort sort = new Sort(order0,order1,order2,order3);
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
		String objEnd = " order by t.pjbaseinfo.modifiedTime desc,t.pjbaseinfo.declartime desc,t.pjbaseinfo.xmsbXmlx,t.pjbaseinfo.xmsbHylb ";
		
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
				if(projectParams.getSysOrganizationByDeclareunitsid()!=null&&StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
					sqlString += " and t.pjbaseinfo.sysOrganizationByDeclareunitsid is not null and t.pjbaseinfo.sysOrganizationByDeclareunitsid.organizationName like '%" + projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
					sqlString += " and t.pjbaseinfo.declartime is not null and t.pjbaseinfo.declartime >= to_date('" + projectParams.getBeginTime() + "','yyyy-MM-dd')";
				}
				if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
					sqlString += " and t.pjbaseinfo.declartime is not null and t.pjbaseinfo.declartime <= to_date('" + projectParams.getEndTime() + "','yyyy-MM-dd')";
				}
				
				if(projectParams.getXmsbHylb()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbHylb().getHylbmc())){
					sqlString += " and t.pjbaseinfo.xmsbHylb is not null and t.pjbaseinfo.xmsbHylb.hylbmc = '" + projectParams.getXmsbHylb().getHylbmc().trim() + "' ";
				}
				if(projectParams.getXmsbZjxz()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbZjxz().getZjxzmc())){
					sqlString += " and t.pjbaseinfo.xmsbZjxz is not null and t.pjbaseinfo.xmsbZjxz.zjxzmc = '" + projectParams.getXmsbZjxz().getZjxzmc().trim() + "' ";
				}
				if(projectParams.getPjstatus()!=null){
					sqlString += " and t.pjbaseinfo.pjstatus is not null and t.pjbaseinfo.pjstatus = " + projectParams.getPjstatus();
				}
				if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
					sqlString += " and t.pjbaseinfo.nextauditdeptname is not null and t.pjbaseinfo.nextauditdeptname like '%" + projectParams.getNextauditdeptname().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getZgdw())){//主管单位
					sqlString += " and t.pjbaseinfo.sysOrganizationByDirectorunitsid is not null and t.pjbaseinfo.sysOrganizationByDirectorunitsid.organizationName like '%" + projectParams.getZgdw().trim() + "%' ";
				}
				if(projectParams.getXmfl()!=null&&!projectParams.getXmfl().equals("")){
					sqlString += " and t.pjbaseinfo.xmfl is not null and t.pjbaseinfo.xmfl = " + projectParams.getXmfl();
				}
				if(StringUtils.isNotEmpty(projectParams.getManageunitsname())) {
					sqlString += " and t.pjbaseinfo.manageunitsname is not null and t.pjbaseinfo.manageunitsname like '%" + projectParams.getManageunitsname().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getProjectcontent())) {
					sqlString += " and t.pjbaseinfo.projectcontent is not null and t.pjbaseinfo.projectcontent like '%" + projectParams.getProjectcontent().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getDeclaregist())) {
					sqlString += " and t.pjbaseinfo.declaregist is not null and t.pjbaseinfo.declaregist like '%" + projectParams.getDeclaregist().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getBeginModifiedTime())){//修改时间：开始时间
					sqlString += " and t.pjbaseinfo.modifiedTime >= to_date('" + projectParams.getBeginModifiedTime() + "','yyyy-MM-dd')";
				}
				if(StringUtils.isNotEmpty(projectParams.getEndModifiedTime())){//修改时间：结束时间
					sqlString += " and t.pjbaseinfo.modifiedTime <= to_date('" + projectParams.getEndModifiedTime() + "','yyyy-MM-dd')";
				}
				if(projectParams.getExpectfinishinvestType()!=null&&!projectParams.getExpectfinishinvestType().equals("")){//项目总投资类型
					
					if(projectParams.getExpectfinishinvestType().equals(IConstants.EXPECTFINISHINVEST_TYPE_1)){
						sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz < " + IConstants.XMZTZ;
					}
					else{
						sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz >= " + IConstants.XMZTZ;
					}
				}
				if(StringUtils.isNotEmpty(projectParams.getSfctzchz())){
					if(projectParams.getSfctzchz().equals(IConstants.SFCTZCHZ_TYPE_1)){
						sqlString += " and t.pjbaseinfo.pjstatus = " + IConstants.XMZT_6 + " and t.sysDeptByDeptId is not null and t.sysDeptByDeptId.deptId is not null " + 
								" and t.sysDeptByDeptId.deptId <> 12 and (t.sfzj is null or t.sfzj="+IConstants.SFZJ_0+")" ;
					}
					else{
						sqlString += " and ((t.pjbaseinfo.pjstatus <> " + IConstants.XMZT_6 + ")  or(t.sysDeptByDeptId is null)  or ( t.sysDeptByDeptId.deptId = 12) or (t.pjbaseinfo.pjstatus = " + IConstants.XMZT_6 + " and t.sfzj is not null and t.sfzj="+IConstants.SFZJ_1+"))" ;
					}
				}
				if(projectParams.getXmztzBegin()!=null){//项目总投资
					
					sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz >= " + projectParams.getXmztzBegin();
				}
				if(projectParams.getXmztzEnd()!=null){//项目总投资
					
					sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz <= " + projectParams.getXmztzEnd();
				}
			}
		  
		  if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){
				if(user.getSysDept()!=null){
					sqlString += " and t.sysDeptBySenddeptid is not null and t.sysDeptBySenddeptid.deptId=" + user.getSysDept().getDeptId();
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_3)){
						sqlString += " and t.sysUser is not null and t.sysUser.userId=" + user.getUserId();
					}
					else if(user.getRoleType()==null||!user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){
						sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
					}
				}
				else{
					sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
				}
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
				if(user.getSysOrganization()!=null){
					sqlString += " and t.pjbaseinfo.sysOrganizationByDirectorunitsid is not null and t.pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId=" + user.getSysOrganization().getOrganizationId();
					sqlString += " and t.pjbaseinfo.pjstatus is not null and t.pjbaseinfo.pjstatus = " + IConstants.XMZT_1;
				}
				else{
					sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
				}
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//超级管理员admin
				
			}
			else{
				sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
			}
		  
		  sqlString += " and(t.pjbaseinfo.deleted is null or t.pjbaseinfo.deleted = 0)";
		  sqlString += " and t.recordertime is null";
		  sqlString += " and t.pjbaseinfo.pjstatus<>" + IConstants.XMZT_13 + " and t.pjbaseinfo.pjstatus<>" + IConstants.XMZT_14;
			  
		  return sqlString;
	  }
	
//	//cs
//	@Action("cszw")
//	public String cszw() throws Exception
//	{
//		String projectName = request.getParameter("projectName");
//		String projectContent = request.getParameter("projectContent");
//		String projectContact = request.getParameter("projectContact");
//		try {
//			projectName = new String(projectName.getBytes("ISO-8859-1"), "UTF-8");
//			projectContent = new String(projectContent.getBytes("ISO-8859-1"), "UTF-8");
//			projectContact = new String(projectContact.getBytes("ISO-8859-1"), "UTF-8");
//			System.out.println("项目名称="+projectName);
//			System.out.println("项目内容="+projectContent);
//			System.out.println("项目联系人="+projectContact);
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		return "success";
//	}
	
	
	private void pjauditloglist() throws Exception
	{
		Condition con = new Condition();
		con.add("pjbaseinfo", Operator.ISNOTNULL,null);
		con.add("pjbaseinfo.projectid", Operator.EQ,obj.getProjectid());
		Order order = new Order(Direction.DESC, "recordertime");
		Sort sort = new Sort(order);
		pjauditlogs = commonService.find(Pjauditlog.class, con,sort);
		
	}
	
	@Action("mind")
	public String mind(){
		id = request.getParameter("id");
		List<SysYjmb> list = commonService.findAll(SysYjmb.class);
		request.setAttribute("list",list);
		return "success";
	}
	
	
	@Action("getMind")
	public String getMind() {
		String yjmbId = request.getParameter("yjmbId");
		if(StringUtils.isNotEmpty(yjmbId)){
			List<SysYjmb> list = commonService.findAll(SysYjmb.class);
			if(list!=null&&!list.isEmpty()){
				for(SysYjmb yjmb:list){
					if(Integer.valueOf(yjmbId).equals(yjmb.getYjmbId())){
						String mb = yjmb.getMb();
						outPutMsg(true, mb);
						break;
					}
				}
			}
		}
		return null;
	}
	
	
	@Action("getSysDeptJson")
	public String getSysDeptJson(){
		long begin=System.currentTimeMillis();
		
		sysDeptTreeJson = deptService.findSysDeptTreeJson(false);//上级部门Json串
		outPutJSON(sysDeptTreeJson);
		
		logger.info("Ajax根据机构id获取部门Json串，耗损时间为：{} 毫秒",System.currentTimeMillis()-begin);
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
	
	@Action("edit")
	public String edit() throws Exception{
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
//			if(pjadjuncts!=null&&!pjadjuncts.isEmpty()){
//				for(Pjadjunct pjadjunct:pjadjuncts){
//					if(IConstants.FJLX_0==pjadjunct.getPjadjuncttype()){
//						if(lxpf==null||lxpf.equals("")){
//							lxpf = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_1==pjadjunct.getPjadjuncttype()){
//						if(ghxz==null||ghxz.equals("")){
//							ghxz = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_2==pjadjunct.getPjadjuncttype()){
//						if(ydys==null||ydys.equals("")){
//							ydys = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_3==pjadjunct.getPjadjuncttype()){
//						if(hjyx==null||hjyx.equals("")){
//							hjyx = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_4==pjadjunct.getPjadjuncttype()){
//						if(jnpg==null||jnpg.equals("")){
//							jnpg = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_5==pjadjunct.getPjadjuncttype()){
//						if(kypf==null||kypf.equals("")){
//							kypf = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_6==pjadjunct.getPjadjuncttype()){
//						if(cbsj==null||cbsj.equals("")){
//							cbsj = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_7==pjadjunct.getPjadjuncttype()){
//						if(zbtb==null||zbtb.equals("")){
//							zbtb = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_8==pjadjunct.getPjadjuncttype()){
//						if(zdcq==null||zdcq.equals("")){
//							zdcq = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_9==pjadjunct.getPjadjuncttype()){
//						if(qtqq==null||qtqq.equals("")){
//							qtqq = pjadjunct.getFilename();
//						}
//					}
//				}
//			}
			
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位用户

				Condition con = new Condition();
				con.add("pjbaseinfo", Operator.ISNOTNULL,null);
				con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
				con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
				con.add("recordertime", Operator.ISNULL,null);
				pjauditlog = commonService.findOne(Pjauditlog.class, con);
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){//发改委用户
				if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
					
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
						Condition con = new Condition();
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
						pjauditlog = commonService.findOne(Pjauditlog.class, con);
					}
					else{//经办
						Condition con = new Condition();
						con.add("pjbaseinfo", Operator.ISNOTNULL,null);
						con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
						con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
						con.add("recordertime", Operator.ISNULL,null);
						con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_7);
						pjauditlog = commonService.findOne(Pjauditlog.class, con);
					}
				}
				else{//其他处室
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
						Condition con = new Condition();
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
						pjauditlog = commonService.findOne(Pjauditlog.class, con);
					}
					else{//经办
						Condition con = new Condition();
						con.add("pjbaseinfo", Operator.ISNOTNULL,null);
						con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
						con.add("sysDeptBySenddeptid", Operator.ISNOTNULL,null);
						con.add("sysDeptBySenddeptid.deptId",Operator.EQ,user.getSysDept().getDeptId());
						con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
						con.add("recordertime", Operator.ISNULL,null);
						con.add("pjbaseinfo.pjstatus",Operator.EQ,IConstants.XMZT_3);
						pjauditlog = commonService.findOne(Pjauditlog.class, con);
					}
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
				Condition con = new Condition();
				con.add("pjbaseinfo", Operator.ISNOTNULL,null);
				con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
				con.add("recordertime", Operator.ISNULL,null);
				pjauditlog = commonService.findOne(Pjauditlog.class, con);
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
//			if(pjadjuncts!=null&&!pjadjuncts.isEmpty()){
//				for(Pjadjunct pjadjunct:pjadjuncts){
//					if(IConstants.FJLX_0==pjadjunct.getPjadjuncttype()){
//						if(lxpf==null||lxpf.equals("")){
//							lxpf = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_1==pjadjunct.getPjadjuncttype()){
//						if(ghxz==null||ghxz.equals("")){
//							ghxz = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_2==pjadjunct.getPjadjuncttype()){
//						if(ydys==null||ydys.equals("")){
//							ydys = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_3==pjadjunct.getPjadjuncttype()){
//						if(hjyx==null||hjyx.equals("")){
//							hjyx = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_4==pjadjunct.getPjadjuncttype()){
//						if(jnpg==null||jnpg.equals("")){
//							jnpg = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_5==pjadjunct.getPjadjuncttype()){
//						if(kypf==null||kypf.equals("")){
//							kypf = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_6==pjadjunct.getPjadjuncttype()){
//						if(cbsj==null||cbsj.equals("")){
//							cbsj = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_7==pjadjunct.getPjadjuncttype()){
//						if(zbtb==null||zbtb.equals("")){
//							zbtb = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_8==pjadjunct.getPjadjuncttype()){
//						if(zdcq==null||zdcq.equals("")){
//							zdcq = pjadjunct.getFilename();
//						}
//					}
//					else if(IConstants.FJLX_9==pjadjunct.getPjadjuncttype()){
//						if(qtqq==null||qtqq.equals("")){
//							qtqq = pjadjunct.getFilename();
//						}
//					}
//				}
//			}
			
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
	
	@GzznLog
	@Action(value = "save")
	public String save(){
		sysProcessOrganization = getSysProcessOrganization();
		obj = commonService.findOne(Pjbaseinfo.class,obj.getProjectid());
		message = "保存数据成功";
		boolean pass = true;
		try {
			
			SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			SysDept nextProcessDept = pjauditlog.getSysDeptBySenddeptid();
			Map<Integer,SysDept> deptMap = (Map<Integer, SysDept>) getHttpSession().getAttribute(CommonFiled.SESSION_DEPT_MAP);
			Map<Integer,SysXq> xqMap = (Map<Integer, SysXq>) getHttpSession().getAttribute(CommonFiled.SESSION_XQ_MAP);
			Map<Integer,XmsbXmlx> xmlxMap = (Map<Integer, XmsbXmlx>) getHttpSession().getAttribute(CommonFiled.SESSION_XMLX_MAP);
			if(deptMap!=null&&nextProcessDept!=null&&deptMap.get(nextProcessDept.getDeptId())!=null){
				nextProcessDept = deptMap.get(nextProcessDept.getDeptId());
			}
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
			
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位用户
//				pjauditlog.setPjaudittype(IConstants.SHLX_1);
				obj.setAuditdept(user.getSysOrganization()!=null?user.getSysOrganization().getOrganizationId():null);
				obj.setAuditdeptname(user.getSysOrganization()!=null?user.getSysOrganization().getOrganizationName():null);
				obj.setSysUserByRecorderid(user);
				obj.setRecordername(user.getUserName());
				obj.setRecordertime(new Date());
				if(pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_1)){//审核通过
					//产生一条新的审核日志记录
					Pjauditlog newObj = new Pjauditlog();
					newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
					newObj.setSysDeptByDeptId(user.getSysDept());
					newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
					newObj.setPjbaseinfo(obj);
					newObj.setSysDeptBySenddeptid(nextProcessDept);
					obj.setNextauditdept(nextProcessDept.getDeptId());
					obj.setNextauditdeptname(nextProcessDept.getDeptname());
					if(nextProcessDept!=null&&nextProcessDept.getFullcode()!=null&&nextProcessDept.getFullcode().equals(fgwtzc)){//发往投资处
						obj.setPjstatus(IConstants.XMZT_6);//待投资处长审核
						newObj.setPjaudittype(IConstants.SHLX_6);
					}
					else{
						obj.setPjstatus(IConstants.XMZT_2);//待各处室处长审核
						newObj.setPjaudittype(IConstants.SHLX_2);
					}
					commonService.save(newObj);
					
				}
				else{//不通过,更新项目最新的这条日志,生成一条项目日志，并将项目状态更新为审核不通过12
					obj.setPjstatus(IConstants.XMZT_12);//审核不通过
					obj.setNextauditdept(null);
					obj.setNextauditdeptname(null);
					pass = false;
				}
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){//发改委用户
				obj.setAuditdept(user.getSysDept()!=null?user.getSysDept().getDeptId():null);
				obj.setAuditdeptname(user.getSysDept()!=null?user.getSysDept().getDeptname():null);
				obj.setSysUserByRecorderid(user);
				obj.setRecordername(user.getUserName());
				obj.setRecordertime(new Date());
				if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
					
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
						obj.setNextauditdept(null);
						obj.setNextauditdeptname(null);
//						pjauditlog.setPjaudittype(IConstants.SHLX_6);
						if(pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_1)){//审核通过
								obj.setPjstatus(IConstants.XMZT_10);//正常项目
						}
						else if(pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_3)){//转交其他处室
							obj.setPjstatus(IConstants.XMZT_2);//待各处室处长审核
							//产生一条新的审核日志记录
							Pjauditlog newObj = new Pjauditlog();
							newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
							newObj.setSysDeptByDeptId(user.getSysDept());
							newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
							newObj.setPjbaseinfo(obj);
							newObj.setSysDeptBySenddeptid(nextProcessDept);
							newObj.setPjaudittype(IConstants.SHLX_2);
							newObj.setSfzj(IConstants.SFZJ_1);
							obj.setNextauditdept(nextProcessDept.getDeptId());
							obj.setNextauditdeptname(nextProcessDept.getDeptname());
							commonService.save(newObj);
						}
						else{//不通过,更新项目最新的这条日志,生成一条项目日志，并将项目状态更新为审核不通过12
							obj.setPjstatus(IConstants.XMZT_12);//审核不通过
							pass = false;
						}
					}
					else{//经办
//						pjauditlog.setPjaudittype(IConstants.SHLX_7);
						//产生一条新的审核日志记录
						Pjauditlog newObj = new Pjauditlog();
						newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
						newObj.setSysDeptByDeptId(user.getSysDept());
						newObj.setSysDeptBySenddeptid(user.getSysDept());
						newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
						newObj.setPjbaseinfo(obj);
						newObj.setPjaudittype(IConstants.SHLX_9);
						obj.setPjstatus(IConstants.XMZT_9);//请投资处长复核
						obj.setNextauditdept(user.getSysDept().getDeptId());
						obj.setNextauditdeptname(user.getSysDept().getDeptname());
						commonService.save(newObj);
					}
				}
				else{//其他处室
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
//						pjauditlog.setPjaudittype(IConstants.SHLX_2);
						if(pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_1)){//审核通过
								obj.setPjstatus(IConstants.XMZT_6);//待投资处审核
								//产生一条新的审核日志记录
								Pjauditlog newObj = new Pjauditlog();
								newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
								newObj.setSysDeptByDeptId(user.getSysDept());
								newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
								newObj.setPjbaseinfo(obj);
								newObj.setSysDeptBySenddeptid(nextProcessDept);
								newObj.setPjaudittype(IConstants.SHLX_6);
								obj.setNextauditdept(nextProcessDept.getDeptId());
								obj.setNextauditdeptname(nextProcessDept.getDeptname());
								commonService.save(newObj);
						}
						else if(pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_3)){//转交其他处室
							//产生一条新的审核日志记录
							Pjauditlog newObj = new Pjauditlog();
							newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
							newObj.setSysDeptByDeptId(user.getSysDept());
							newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
							newObj.setPjbaseinfo(obj);
							newObj.setSysDeptBySenddeptid(nextProcessDept);
							newObj.setSfzj(IConstants.SFZJ_1);
							obj.setNextauditdept(nextProcessDept.getDeptId());
							obj.setNextauditdeptname(nextProcessDept.getDeptname());
							if(nextProcessDept!=null&&nextProcessDept.getFullcode()!=null&&nextProcessDept.getFullcode().equals(fgwtzc)){//转交投资处
								obj.setPjstatus(IConstants.XMZT_6);//待投资处长审核
								newObj.setPjaudittype(IConstants.SHLX_6);
							}
							else{
								obj.setPjstatus(IConstants.XMZT_2);//待各处室处长审核
								newObj.setPjaudittype(IConstants.SHLX_2);
							}
							commonService.save(newObj);
						}
						else{//不通过,更新项目最新的这条日志,生成一条项目日志，并将项目状态更新为审核不通过12
							obj.setPjstatus(IConstants.XMZT_12);//审核不通过
							obj.setNextauditdept(null);
							obj.setNextauditdeptname(null);
							pass = false;
						}
					}
					else{//经办
//						pjauditlog.setPjaudittype(IConstants.SHLX_3);
						//产生一条新的审核日志记录
						Pjauditlog newObj = new Pjauditlog();
						newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
						newObj.setSysDeptByDeptId(user.getSysDept());
						newObj.setSysDeptBySenddeptid(user.getSysDept());
						newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
						newObj.setPjbaseinfo(obj);
						newObj.setPjaudittype(IConstants.SHLX_5);
						obj.setPjstatus(IConstants.XMZT_5);//请各处室处长复核
						obj.setNextauditdept(user.getSysDept().getDeptId());
						obj.setNextauditdeptname(user.getSysDept().getDeptname());
						commonService.save(newObj);
					}
				}
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//系统管理员
				if(pjauditlog.getPjauditresult()!=null&&pjauditlog.getPjauditresult().equals(IConstants.SHJG_1)){//审核通过
					obj.setPjstatus(IConstants.XMZT_10);//正常项目
				}
				else{
					obj.setPjstatus(IConstants.XMZT_12);//审核不通过
					pass = false;
				}
				obj.setNextauditdept(null);
				obj.setNextauditdeptname(null);
			}
			commonService.saveOrUpdate(pjauditlog);
		
//			Pjbaseinfo tempPjbaseinfo = commonService.findOne(Pjbaseinfo.class,obj.getProjectid());
//			
//			if(tempPjbaseinfo!=null){
//				if(obj.getSysOrganizationByDeclareunitsid()==null||obj.getSysOrganizationByDeclareunitsid().getOrganizationId()==null){
//					obj.setSysOrganizationByDeclareunitsid(null);
//				}
//				if(obj.getSysOrganizationByDirectorunitsid()==null||obj.getSysOrganizationByDirectorunitsid().getOrganizationId()==null){
//					obj.setSysOrganizationByDirectorunitsid(null);
//				}
//				if(obj.getSysUserByNexttacheer()==null||obj.getSysUserByNexttacheer().getUserId()==null){
//					obj.setSysUserByNexttacheer(null);
//				}
//				if(obj.getSysUserByRecorderid()==null||obj.getSysUserByRecorderid().getUserId()==null){
//					obj.setSysUserByRecorderid(null);
//				}
//				
//				if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
//					subObj = obj.getProjectinvests().iterator().next();
//				}
//				
//				if(obj.getXmsbHylb()==null||obj.getXmsbHylb().getHylbId()==null){
//					obj.setXmsbHylb(null);
//				}
//				if(obj.getXmsbXmlx()==null||obj.getXmsbXmlx().getXmlxId()==null){
//					obj.setXmsbXmlx(null);
//				}
//				if(obj.getXmsbZjxz()==null||obj.getXmsbZjxz().getZjxzId()==null){
//					obj.setXmsbZjxz(null);
//				}
//				if(obj.getSysXq()==null||obj.getSysXq().getXqId()==null){
//					obj.setSysXq(null);
//				}
//				PojoCopyUtil.copySameTypeField(obj, tempPjbaseinfo);
//				
//				tempPjbaseinfo.setPjstatus(obj.getPjstatus());
//				
//				SysXq tempXq = xqMap.get(obj.getSysXq().getXqId());
//				
//				tempPjbaseinfo.setSysXq(tempXq);
//				
//				XmsbXmlx tempXmlx = xmlxMap.get(obj.getXmsbXmlx().getXmlxId());
//				
//				tempPjbaseinfo.setXmsbXmlx(tempXmlx);
//				
//				commonService.saveOrUpdate(tempPjbaseinfo);
//			}
//			
//			Projectinvest tempProjectinvest = commonService.findOne(Projectinvest.class,subObj.getPjinvestid());
//			
//			if(tempProjectinvest!=null){
////				tempProjectinvest.setPjinvestAdvice(subObj.getPjinvestAdvice());
////				tempProjectinvest.setPjinvestBank(subObj.getPjinvestBank());
////				tempProjectinvest.setPjinvestBusiness(subObj.getPjinvestBusiness());
////				tempProjectinvest.setPjinvestCenter(subObj.getPjinvestCenter());
////				tempProjectinvest.setPjinvestCity(subObj.getPjinvestCity());
//				PojoCopyUtil.copySameTypeField(subObj, tempProjectinvest);
//				tempProjectinvest.setPjbaseinfo(obj);
//				tempProjectinvest.setPjinvestadvice(tempProjectinvest.getPjinvestadvice()!=null?tempProjectinvest.getPjinvestadvice().trim():null);
//				tempProjectinvest.setPlaninvestadvice(tempProjectinvest.getPlaninvestadvice()!=null?tempProjectinvest.getPlaninvestadvice().trim():null);
//				commonService.saveOrUpdate(tempProjectinvest);
//			}
			commonService.saveOrUpdate(obj);
			String resultString = "";
			if(pjauditlog.getPjauditresult().equals(IConstants.SHJG_1)){
				resultString = "通过";
			}
			else if(pjauditlog.getPjauditresult().equals(IConstants.SHJG_2)){
				resultString = "不通过";
			}
			else if(pjauditlog.getPjauditresult().equals(IConstants.SHJG_3)){
				resultString = "转交"+(nextProcessDept!=null?nextProcessDept.getDeptname():"其它处室");
			}
			
			logObject = new LogObject(obj.getProjectname()+",审核"+resultString);
//			logObject = new LogObject("短信发送成功");
			outPutMsg(true, "提交成功！");
			//审核不通过，发短信通知业主
			String flag = request.getParameter("flag");
			if(!pass&&obj.getMobilePhone()!=null&&flag!=null&&flag.equalsIgnoreCase("true")){
				String[] strs = new String[1];
				strs[0] = obj.getMobilePhone();
				String sfnr = obj.getSysOrganizationByDeclareunitsid().getOrganizationName()+"，贵单位报送的:"+obj.getProjectname()+"存在"+pjauditlog.getPjauditmind()+"问题，现退回请改正后再次申报。"+user.getSysOrganization().getOrganizationName()+(user.getSysDept()!=null?" " + user.getSysDept().getDeptname():"")+sdf.format(new Date());
				try {
//					smsService.invoke(strs,sfnr);
					String sql = " insert into api_mt_FGWGovInfo(SM_ID,SRC_ID,MOBILES,CONTENT) values(0,8888,'" + strs[0] + "','" + sfnr +"')";
					logger.info(sql);
					jdbcTemplate2.update(sql);
					outPutMsg(true, "短信发送成功");
					//调用发送短信接口，发送短信
					SysDx dx = new SysDx();
					dx.setLxrmc(obj.getDeclarerid());
					dx.setSfbj(2);
					dx.setSfnr(sfnr);
					dx.setSfsj(new Date());
					dx.setSjhm(obj.getMobilePhone());
					dx.setSysDeptByDeptId(user.getSysDept());
					dx.setSysDeptByReceiveDeptId(null);
					dx.setSysOrganization(obj.getSysOrganizationByDeclareunitsid());
					dx.setSysUser(user);
					commonService.save(dx);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					outPutError("短信发送失败！");
				}
			}
			else if(pass&&obj.getMobilePhone()!=null&&flag!=null&&flag.equalsIgnoreCase("true")){
				String[] strs = new String[1];
				strs[0] = obj.getMobilePhone();
				String sfnr = obj.getSysOrganizationByDeclareunitsid().getOrganizationName()+"，贵单位报送的:"+obj.getProjectname()+"经"+user.getSysOrganization().getOrganizationName()+(user.getSysDept()!=null?" " + user.getSysDept().getDeptname():"")+"审核通过。审核意见："+pjauditlog.getPjauditmind()+"。"+sdf.format(new Date());
				try {
//					smsService.invoke(strs,sfnr);
					String sql = " insert into api_mt_FGWGovInfo(SM_ID,SRC_ID,MOBILES,CONTENT) values(0,8888,'" + strs[0] + "','" + sfnr +"')";
					logger.info(sql);
					jdbcTemplate2.update(sql);
					outPutMsg(true, "短信发送成功");
					//调用发送短信接口，发送短信
					SysDx dx = new SysDx();
					dx.setLxrmc(obj.getDeclarerid());
					dx.setSfbj(2);
					dx.setSfnr(sfnr);
					dx.setSfsj(new Date());
					dx.setSjhm(obj.getMobilePhone());
					dx.setSysDeptByDeptId(user.getSysDept());
					dx.setSysDeptByReceiveDeptId(null);
					dx.setSysOrganization(obj.getSysOrganizationByDeclareunitsid());
					dx.setSysUser(user);
					commonService.save(dx);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					outPutError("短信发送失败！");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存数据失败";
			outPutError("操作失败！");
		}
		
		return null;
	}
	
	@GzznLog
	@Action(value = "batchqrsh")
	public String batchqrsh() throws Exception{
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		try{
			int totalcount = 0;
			int successcount = 0;
			if (StringUtils.isNotEmpty(id)) {
				Condition condition1 = new Condition();
				condition1.add("fullcode",Operator.ISNOTNULL,null);
				condition1.add("fullcode",Operator.EQ,fgwtzc);
				SysDept tzcDept = (SysDept) commonService.findOne(SysDept.class, condition1);
				for (String i : id.split("@")) {
					totalcount++;
					if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//发改委用户
						if(user.getSysDept()!=null&&(user.getSysDept().getFullcode()==null||(user.getSysDept().getFullcode()!=null&&!user.getSysDept().getFullcode().equals(fgwtzc)))){//各处室处长
							Condition con = new Condition();
							con.add("pjbaseinfo", Operator.ISNOTNULL,null);
							con.add("pjbaseinfo.projectid",Operator.EQ,Integer.parseInt(i));
							con.add("sysOrganizationByPjauditunits", Operator.ISNOTNULL,null);
							con.add("sysOrganizationByPjauditunits.organizationId", Operator.EQ,user.getSysOrganization().getOrganizationId());
							con.add("sysDeptBySenddeptid", Operator.ISNOTNULL,null);
							con.add("sysDeptBySenddeptid.deptId", Operator.EQ,user.getSysDept().getDeptId());
							con.add("recordertime", Operator.ISNULL,null);
							con.add("pjaudittype",Operator.ISNOTNULL,null);
							con.add("pjaudittype",Operator.EQ,IConstants.SHLX_5);
							pjauditlog = commonService.findOne(Pjauditlog.class, con);
							if(pjauditlog!=null){
								pjauditlog.setRecordertime(new Date());
								pjauditlog.setSysUser(user);
								pjauditlog.setRecordername(user.getUserName());
								pjauditlog.setPjauditresult(IConstants.FGWGCS_QX_1);
								commonService.save(pjauditlog);
								//产生一条新的审核日志记录
								Pjauditlog newObj = new Pjauditlog();
								newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
								newObj.setSysDeptByDeptId(user.getSysDept());
								newObj.setSysOrganizationByPjauditunits(getSysProcessOrganization());
								newObj.setPjbaseinfo(pjauditlog.getPjbaseinfo());
								newObj.setSysDeptBySenddeptid(tzcDept);
								newObj.setPjaudittype(IConstants.SHLX_6);
								if(tzcDept!=null){
									pjauditlog.getPjbaseinfo().setNextauditdept(tzcDept.getDeptId());
									pjauditlog.getPjbaseinfo().setNextauditdeptname(tzcDept.getDeptname());
								}
								pjauditlog.getPjbaseinfo().setPjstatus(IConstants.XMZT_6);
								commonService.save(newObj);
								commonService.save(pjauditlog.getPjbaseinfo());
								logObject = new LogObject(user.getSysDept().getDeptname()+user.getUserName()+"确认审核通过项目:"+pjauditlog.getPjbaseinfo().getProjectname());
								successcount++;
							}
						}
					}
				}
			}
			
			outPutMsg(true, "确认审核通过并提交投资处成功"+successcount+"条"+(successcount==totalcount?"":"，有"+(totalcount-successcount)+"条未处于待处长确认审核状态。"));
		}
		catch(Exception e){
			outPutError("批量操作失败！");
			e.printStackTrace();
			throw e;
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
			if (StringUtils.isNotEmpty(ids)) {
				for (String i : ids.split("@")) {
					//commonService.delete(Pjbaseinfo.class, Integer.parseInt(i));
					Pjbaseinfo delObj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i));
					if(delObj!=null){
						delObj.setDeleted(IConstants.DEL_FLAG_TRUE);//删除标记：0-非删除 1-逻辑删除
						commonService.saveOrUpdate(delObj);
					}
				}
				logObject = new LogObject("删除待处理项目信息，ids=" + ids);
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		return "success";
	}
	
	@GzznLog
	@Action("toxmps")
	public String toxmps() {
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		id = request.getParameter("id");
		String mind = request.getParameter("mind");
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(Pjbaseinfo.class, Integer.valueOf(id));
			if(obj!=null){
				if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){//发改委用户
					if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
						
						if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
							Condition con = new Condition();
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
							pjauditlog = commonService.findOne(Pjauditlog.class, con);
						}
					}
					else{//其他处室
						if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
							Condition con = new Condition();
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
							pjauditlog = commonService.findOne(Pjauditlog.class, con);
						}
					}
				}
				else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//系统管理员
					Condition con = new Condition();
					con.add("pjbaseinfo", Operator.ISNOTNULL,null);
					con.add("pjbaseinfo.projectid",Operator.EQ,obj.getProjectid());
					con.add("recordertime", Operator.ISNULL,null);
					pjauditlog = commonService.findOne(Pjauditlog.class, con);
				}
				
				Pjauditlog tempPjaduitlog = commonService.findOne(Pjauditlog.class, pjauditlog.getPjauditlogid());
				pjauditlog.setSysOrganizationByOrganizationId(tempPjaduitlog.getSysOrganizationByOrganizationId());
				pjauditlog.setSysDeptByDeptId(tempPjaduitlog.getSysDeptByDeptId());
				pjauditlog.setSysOrganizationByPjauditunits(user.getSysOrganization());
				pjauditlog.setPjbaseinfo(tempPjaduitlog.getPjbaseinfo());
				pjauditlog.setSysDeptBySenddeptid(user.getSysDept());
				pjauditlog.setPjauditmind(mind);
				
				//更新项目最新的这条日志
				pjauditlog.setRecordertime(new Date());
				pjauditlog.setSysUser(user);
				pjauditlog.setRecordername(user.getUserName());
				commonService.save(pjauditlog);
				
				//转研评中心。。。。。。。。。。。。。
				try{
					String declartimeString = obj.getDeclartime()==null?null:sdfOnlyYear.format(obj.getDeclartime());
					String mypath = fpath;
					if(declartimeString!=null){
						mypath += declartimeString + "\\";
					}
					service.toxmps(obj,mypath,user.getSysDept()!=null?user.getSysDept().getDeptname():"投资处");
					logger.info("转研评中心成功");
					//产生一条新的审核日志记录
					Pjauditlog newObj = new Pjauditlog();
					newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
					newObj.setSysDeptByDeptId(user.getSysDept());
					newObj.setSysDeptBySenddeptid(user.getSysDept());
					newObj.setSysOrganizationByPjauditunits(sysProcessOrganization);
					newObj.setPjbaseinfo(obj);
					commonService.save(newObj);
					logObject = new LogObject(obj.getProjectname()+"转研评中心成功");
					outPutMsg(true, "转研评中心成功");
				}
				catch (Exception e) {
					outPutError("转研评中心失败");
					e.printStackTrace();
				}
			}
			else{
				outPutError("没找到该项目，因此转研评中心失败");
			}
		}
		else{
			logger.info("没找到该项目");
			outPutError("没找到该项目，因此转研评中心失败");
		}

		return null;
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

//	public String getLxpf() {
//		return lxpf;
//	}
//
//	public void setLxpf(String lxpf) {
//		this.lxpf = lxpf;
//	}
//
//	public String getGhxz() {
//		return ghxz;
//	}
//
//	public void setGhxz(String ghxz) {
//		this.ghxz = ghxz;
//	}
//
//	public String getYdys() {
//		return ydys;
//	}
//
//	public void setYdys(String ydys) {
//		this.ydys = ydys;
//	}
//
//	public String getHjyx() {
//		return hjyx;
//	}
//
//	public void setHjyx(String hjyx) {
//		this.hjyx = hjyx;
//	}
//
//	public String getJnpg() {
//		return jnpg;
//	}
//
//	public void setJnpg(String jnpg) {
//		this.jnpg = jnpg;
//	}
//
//	public String getKypf() {
//		return kypf;
//	}
//
//	public void setKypf(String kypf) {
//		this.kypf = kypf;
//	}
//
//	public String getCbsj() {
//		return cbsj;
//	}
//
//	public void setCbsj(String cbsj) {
//		this.cbsj = cbsj;
//	}
//
//	public String getZbtb() {
//		return zbtb;
//	}
//
//	public void setZbtb(String zbtb) {
//		this.zbtb = zbtb;
//	}
//
//	public String getZdcq() {
//		return zdcq;
//	}
//
//	public void setZdcq(String zdcq) {
//		this.zdcq = zdcq;
//	}
//
//	public String getQtqq() {
//		return qtqq;
//	}
//
//	public void setQtqq(String qtqq) {
//		this.qtqq = qtqq;
//	}

	

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

	public ISysDeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(ISysDeptService deptService) {
		this.deptService = deptService;
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

	public Boolean getMoreSearchBool() {
		return moreSearchBool;
	}

	public void setMoreSearchBool(Boolean moreSearchBool) {
		this.moreSearchBool = moreSearchBool;
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String getExpExcelFilePath() {
		return expExcelFilePath;
	}

	public void setExpExcelFilePath(String expExcelFilePath) {
		this.expExcelFilePath = expExcelFilePath;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	
}
