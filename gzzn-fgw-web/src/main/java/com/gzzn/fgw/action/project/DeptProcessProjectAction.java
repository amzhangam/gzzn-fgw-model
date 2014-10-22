package com.gzzn.fgw.action.project;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
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
import com.gzzn.fgw.expExcel.ProjectbaseinfoListExpExcel;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
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
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.FjObject;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.fgw.webUtil.ReadFileByteUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: SysDeptAction</p>
 * <p>Description:  各处室审核通过项目信息维护 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-26 amzhang  new
 */
@Namespace(value = "/project/csshtgxm")
public class DeptProcessProjectAction extends BaseAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(DeptProcessProjectAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
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
	private PageUtil<Pjbaseinfo> page = new PageUtil<Pjbaseinfo>();
	private Map<Integer,String> sblxMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmlxMap = new HashMap<Integer, String>();
	private Map<Integer,String> ndMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();
	private Map<Integer,String> zjxzMap = new HashMap<Integer, String>();
	private Map<Integer,String> pjaudittypeMap = new HashMap<Integer, String>();
	private List<Pjadjunct> pjadjuncts = new ArrayList<Pjadjunct>();
	private List<Pjauditlog> pjauditlogs = new ArrayList<Pjauditlog>();
	
	private InputStream stream; //用于下载
	
	
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
	
	private String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"fgwtzc.fullcode");
	
	private Boolean moreSearchBool=false;//显示更多查询条件... 是否显示
	
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
		 SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		 String middle = getQuerySql();
		String objBefore = " from Pjauditlog t where 1=1 ";
		String objEnd = " order by t.pjbaseinfo.modifiedTime desc,t.pjbaseinfo.declartime desc,t.pjbaseinfo.xmsbXmlx,t.pjbaseinfo.xmsbHylb,t.pjbaseinfo.projectid desc ";
			
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
//		 List<PjbaseinfoPojo> list = pjauditlogService.findList(countSql,objSql);
		 List<PjbaseinfoPojo> list = pjauditlogService.findAllExpList(objSql,null,"2",user.getSysOrganization().getOrganizationId());
		 
		 setDownloadFileName(downFileNameTranscode("处室审核通过项目汇总表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		 setExcelFile(new ProjectbaseinfoListExpExcel().expExcelTempFile(getLoginUser(), list, getXmztMap(), expExcelFilePath+"pjbaseExp.xls", 5));
		 
		 logger.info("处室审核通过项目汇总表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		 return "excel";
	}
	

	
	//进入未处理列表界面
	@Action("list")
	public String list() throws Exception
	{
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		String middle = getQuerySql();
		String objBefore = " from Pjauditlog t where 1=1 ";
		String objEnd = " order by t.pjbaseinfo.modifiedTime desc,t.pjbaseinfo.declartime desc,t.pjbaseinfo.xmsbXmlx,t.pjbaseinfo.xmsbHylb,t.pjbaseinfo.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		page.setSize(15);
		
		page = pjauditlogService.findPagePassList(page,objSql,null,"2",user.getSysOrganization().getOrganizationId());
		
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
				if(projectParams.getXmztzBegin()!=null){//项目总投资
					
					sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz >= " + projectParams.getXmztzBegin();
				}
				if(projectParams.getXmztzEnd()!=null){//项目总投资
					
					sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz <= " + projectParams.getXmztzEnd();
				}
			}
		  
		  if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){
				if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&fgwtzc!=null&&!user.getSysDept().getFullcode().equals(fgwtzc)){
					
					sqlString += " and t.sysDeptBySenddeptid is not null and t.sysDeptBySenddeptid.deptId=" + user.getSysDept().getDeptId();
					
//					sqlString += " and t.pjaudittype is not null and (t.pjaudittype=" + IConstants.SHLX_2 + " or t.pjaudittype=" + IConstants.SHLX_3 + " or t.pjaudittype=" + IConstants.SHLX_5 + ")";
				}
				else{
					sqlString += " and t.pjbaseinfo.projectid=0";//加入一个条件让它无记录
				}
			}
			else{
				sqlString += " and t.pjbaseinfo.projectid=0";//加入一个条件让它无记录
			}
		  
		  sqlString += " and t.pjbaseinfo.projectcode is not null and t.pjbaseinfo.projectcode not like 'PJ%' ";
		  sqlString += " and(t.pjbaseinfo.deleted is null or t.pjbaseinfo.deleted = 0)";
			  
		  return sqlString;
	  }
	
//	//进入未处理列表界面
//	@Action("list")
//	public String list() throws Exception
//	{
//		String middle = getQuerySql();
//		String objBefore = " from Pjauditlog t where 1=1 ";
//		String objEnd = " order by t.pjbaseinfo.modifiedTime desc,t.pjbaseinfo.declartime desc,t.pjbaseinfo.xmsbXmlx,t.pjbaseinfo.xmsbHylb ";
//		
//		String objSql = objBefore + middle + objEnd;
//		
//		String countBefore = " select count(*) from Pjauditlog t where 1=1 ";
//		
//		String countSql = countBefore + middle;
//		
//		page.setSize(15);
//		
//		page = pjauditlogService.findList(page,countSql, objSql);
//		
//		return "success";
//	}
//	
//	/**
//	   * 项目信息管理列表查询条件初始化
//	   * @param cond
//	   * @return
//	   */
//	  private String getQuerySql(){
//		  
//		  SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
//		  
//		  String sqlString = "";
//		  
//		  if(projectParams != null){
//				if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
//					
//					sqlString += " and t.pjbaseinfo.xmsbXmlx is not null and t.pjbaseinfo.xmsbXmlx.xmlxmc = '" + projectParams.getXmsbXmlx().getXmlxmc().trim() + "' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
//					sqlString += " and t.pjbaseinfo.projectname is not null and t.pjbaseinfo.projectname like '%" + projectParams.getProjectname().trim() + "%' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
//					sqlString += " and t.pjbaseinfo.projectcode is not null and t.pjbaseinfo.projectcode like '%" + projectParams.getProjectcode().trim() + "%' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
//					sqlString += " and t.pjbaseinfo.sysOrganizationByDeclareunitsid is not null and t.pjbaseinfo.sysOrganizationByDeclareunitsid.organizationName like '%" + projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim() + "%' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
//					sqlString += " and t.pjbaseinfo.declartime is not null and t.pjbaseinfo.declartime >= to_date('" + projectParams.getBeginTime() + "','yyyy-MM-dd')";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
//					sqlString += " and t.pjbaseinfo.declartime is not null and t.pjbaseinfo.declartime <= to_date('" + projectParams.getEndTime() + "','yyyy-MM-dd')";
//				}
//				
//				if(projectParams.getXmsbHylb()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbHylb().getHylbmc())){
//					sqlString += " and t.pjbaseinfo.xmsbHylb is not null and t.pjbaseinfo.xmsbHylb.hylbmc = '" + projectParams.getXmsbHylb().getHylbmc().trim() + "' ";
//				}
//				if(projectParams.getXmsbZjxz()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbZjxz().getZjxzmc())){
//					sqlString += " and t.pjbaseinfo.xmsbZjxz is not null and t.pjbaseinfo.xmsbZjxz.zjxzmc = '" + projectParams.getXmsbZjxz().getZjxzmc().trim() + "' ";
//				}
//				if(projectParams.getPjstatus()!=null){
//					sqlString += " and t.pjbaseinfo.pjstatus is not null and t.pjbaseinfo.pjstatus = " + projectParams.getPjstatus();
//				}
//				if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
//					sqlString += " and t.pjbaseinfo.nextauditdeptname is not null and t.pjbaseinfo.nextauditdeptname like '%" + projectParams.getNextauditdeptname().trim() + "%' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getZgdw())){//主管单位
//					sqlString += " and t.pjbaseinfo.sysOrganizationByDirectorunitsid is not null and t.pjbaseinfo.sysOrganizationByDirectorunitsid.organizationName like '%" + projectParams.getZgdw().trim() + "%' ";
//				}
//				if(projectParams.getXmfl()!=null&&!projectParams.getXmfl().equals("")){
//					sqlString += " and t.pjbaseinfo.xmfl is not null and t.pjbaseinfo.xmfl = " + projectParams.getXmfl();
//				}
//				if(StringUtils.isNotEmpty(projectParams.getManageunitsname())) {
//					sqlString += " and t.pjbaseinfo.manageunitsname is not null and t.pjbaseinfo.manageunitsname like '%" + projectParams.getManageunitsname().trim() + "%' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getProjectcontent())) {
//					sqlString += " and t.pjbaseinfo.projectcontent is not null and t.pjbaseinfo.projectcontent like '%" + projectParams.getProjectcontent().trim() + "%' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getDeclaregist())) {
//					sqlString += " and t.pjbaseinfo.declaregist is not null and t.pjbaseinfo.declaregist like '%" + projectParams.getDeclaregist().trim() + "%' ";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getBeginModifiedTime())){//修改时间：开始时间
//					sqlString += " and t.pjbaseinfo.modifiedTime >= to_date('" + projectParams.getBeginModifiedTime() + "','yyyy-MM-dd')";
//				}
//				if(StringUtils.isNotEmpty(projectParams.getEndModifiedTime())){//修改时间：结束时间
//					sqlString += " and t.pjbaseinfo.modifiedTime <= to_date('" + projectParams.getEndModifiedTime() + "','yyyy-MM-dd')";
//				}
//				if(projectParams.getExpectfinishinvestType()!=null&&!projectParams.getExpectfinishinvestType().equals("")){//项目总投资
//					
//					if(projectParams.getExpectfinishinvestType().equals(IConstants.EXPECTFINISHINVEST_TYPE_1)){
//						sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz < " + IConstants.XMZTZ;
//					}
//					else{
//						sqlString += " and t.pjbaseinfo.xmztz is not null and t.pjbaseinfo.xmztz >= " + IConstants.XMZTZ;
//					}
//				}
//			}
//		  
//		  if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){
//				if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&fgwtzc!=null&&!user.getSysDept().getFullcode().equals(fgwtzc)){
//					
//					sqlString += " and t.sysDeptBySenddeptid is not null and t.sysDeptBySenddeptid.fullcode='" + fgwtzc + "'";
//					
//					sqlString += " and t.sysDeptByDeptId is not null and t.sysDeptByDeptId.deptId=" + user.getSysDept().getDeptId();
//					
//					sqlString += " and t.pjauditresult is null ";
//
//					sqlString += " and t.pjaudittype is not null and t.pjaudittype=" + IConstants.SHLX_6;
//				}
//				else{
//					sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
//				}
//			}
//			else{
//				sqlString += " and t.pjbaseinfo.projectid<0";//加入一个条件让它无记录
//			}
//		  
//		  sqlString += " and(t.pjbaseinfo.deleted is null or t.pjbaseinfo.deleted = 0)";
//		  sqlString += " and t.recordertime is null";
//		  sqlString += " and t.pjbaseinfo.pjstatus=" + IConstants.XMZT_6;
//			  
//		  return sqlString;
//	  }
	
	
	private void pjauditloglist() throws Exception
	{
		Condition con = new Condition();
		con.add("pjbaseinfo", Operator.ISNOTNULL,null);
		con.add("pjbaseinfo.projectid", Operator.EQ,obj.getProjectid());
		Order order = new Order(Direction.DESC, "recordertime");
		Sort sort = new Sort(order);
		pjauditlogs = commonService.find(Pjauditlog.class, con,sort);
		
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
	public PageUtil<Pjbaseinfo> getPage() {
		return page;
	}
	public void setPage(PageUtil<Pjbaseinfo> page) {
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

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
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

	
}
