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
 * <p>Description:  主管单位审核通过项目信息维护 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-26 amzhang  new
 */
@Namespace(value = "/project/zgdwshtgxm")
public class DirectOrganPassProjectAction extends BaseAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(DirectOrganPassProjectAction.class);
	private ProjectbaseinfoParam projectParams;//系统管理查询参数	
	private String id;//编辑或删除的id，多个间用@隔开
	private Pjbaseinfo obj;//对象
	private Projectinvest subObj;
	private String message;//返回页面信息
	private Pjauditlog pjauditlog;
	private List<Pjadjunct> pjadjuncts = new ArrayList<Pjadjunct>();
	private List<Pjauditlog> pjauditlogs = new ArrayList<Pjauditlog>();
	private PageUtil<Pjbaseinfo> page = new PageUtil<Pjbaseinfo>();
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();
	private String sysDeptTreeJson;//部门树以JSON格式存储
	private String yearTreeJson;//年份树以JSON格式存储
	private String xmlxTreeJson;//树以JSON格式存储
	private String hylbTreeJson;//树以JSON格式存储
	private String zjxzTreeJson;//树以JSON格式存储
	private String szqyTreeJson;//树以JSON格式存储
	private String projectStatusTreeJson;//树以JSON格式存储
	private String ownerOrganizationTreeJson;//业主单位树以JSON格式存储
	private String manageOrganizationTreeJson;//主管单位树以JSON格式存储
	
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
		
			String directSelfSqlString = getDirectSelfQuerySql();
			String beforeString = " from Pjbaseinfo t where 1=1 ";
			String endString = " order by t.modifiedTime desc,t.declartime desc,t.xmsbXmlx,t.xmsbHylb ";
			directSelfSqlString = beforeString + directSelfSqlString + endString;
			
		 List<PjbaseinfoPojo> list = pjauditlogService.findAllExpList(objSql,directSelfSqlString,"1",user.getSysOrganization().getOrganizationId());
		 
		 setDownloadFileName(downFileNameTranscode("主管单位审核通过项目汇总表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		 setExcelFile(new ProjectbaseinfoListExpExcel().expExcelTempFile(getLoginUser(), list, getXmztMap(), expExcelFilePath+"pjbaseExp.xls", 5));
		 
		 logger.info("主管单位审核通过项目汇总表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
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
		
		String countBefore = " select count(*) from Pjauditlog t where 1=1 ";
		
		page.setSize(15);
		
		String directSelfSqlString = getDirectSelfQuerySql();
		String beforeString = " from Pjbaseinfo t where 1=1 ";
		String endString = " order by t.modifiedTime desc,t.declartime desc,t.xmsbXmlx,t.xmsbHylb,t.projectid ";
		directSelfSqlString = beforeString + directSelfSqlString + endString;
		
		page = pjauditlogService.findPagePassList(page,objSql,directSelfSqlString,"1",user.getSysOrganization().getOrganizationId());
		
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
		  
		  if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
				if(user.getSysOrganization()!=null){
					
					sqlString += " and t.sysOrganizationByPjauditunits is not null and t.sysOrganizationByPjauditunits.organizationId='" + user.getSysOrganization().getOrganizationId() + "'";
					
//					sqlString += " and t.pjaudittype is not null and t.pjaudittype=" + IConstants.SHLX_1;
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
	  
	  /**
	   * 项目信息管理列表查询条件初始化
	   * @param cond
	   * @return
	   */
	  private String getDirectSelfQuerySql(){
		  
		  SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		  
		  String sqlString = "";
		  
		  if(projectParams != null){
			  if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
				  
				  sqlString += " and t.xmsbXmlx is not null and t.xmsbXmlx.xmlxmc = '" + projectParams.getXmsbXmlx().getXmlxmc().trim() + "' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
				  sqlString += " and t.projectname is not null and t.projectname like '%" + projectParams.getProjectname().trim() + "%' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
				  sqlString += " and t.projectcode is not null and t.projectcode like '%" + projectParams.getProjectcode().trim() + "%' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
				  sqlString += " and t.sysOrganizationByDeclareunitsid is not null and t.sysOrganizationByDeclareunitsid.organizationName like '%" + projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim() + "%' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
				  sqlString += " and t.declartime is not null and t.declartime >= to_date('" + projectParams.getBeginTime() + "','yyyy-MM-dd')";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
				  sqlString += " and t.declartime is not null and t.declartime <= to_date('" + projectParams.getEndTime() + "','yyyy-MM-dd')";
			  }
			  
			  if(projectParams.getXmsbHylb()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbHylb().getHylbmc())){
				  sqlString += " and t.xmsbHylb is not null and t.xmsbHylb.hylbmc = '" + projectParams.getXmsbHylb().getHylbmc().trim() + "' ";
			  }
			  if(projectParams.getXmsbZjxz()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbZjxz().getZjxzmc())){
				  sqlString += " and t.xmsbZjxz is not null and t.xmsbZjxz.zjxzmc = '" + projectParams.getXmsbZjxz().getZjxzmc().trim() + "' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
				  sqlString += " and t.nextauditdeptname is not null and t.nextauditdeptname like '%" + projectParams.getNextauditdeptname().trim() + "%' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getZgdw())){//主管单位
				  sqlString += " and t.sysOrganizationByDirectorunitsid is not null and t.sysOrganizationByDirectorunitsid.organizationName like '%" + projectParams.getZgdw().trim() + "%' ";
			  }
			  if(projectParams.getXmfl()!=null&&!projectParams.getXmfl().equals("")){
				  sqlString += " and t.xmfl is not null and t.xmfl = " + projectParams.getXmfl();
			  }
			  if(StringUtils.isNotEmpty(projectParams.getManageunitsname())) {
				  sqlString += " and t.manageunitsname is not null and t.manageunitsname like '%" + projectParams.getManageunitsname().trim() + "%' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getProjectcontent())) {
				  sqlString += " and t.projectcontent is not null and t.projectcontent like '%" + projectParams.getProjectcontent().trim() + "%' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getDeclaregist())) {
				  sqlString += " and t.declaregist is not null and t.declaregist like '%" + projectParams.getDeclaregist().trim() + "%' ";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getBeginModifiedTime())){//修改时间：开始时间
				  sqlString += " and t.modifiedTime >= to_date('" + projectParams.getBeginModifiedTime() + "','yyyy-MM-dd')";
			  }
			  if(StringUtils.isNotEmpty(projectParams.getEndModifiedTime())){//修改时间：结束时间
				  sqlString += " and t.modifiedTime <= to_date('" + projectParams.getEndModifiedTime() + "','yyyy-MM-dd')";
			  }
			  if(projectParams.getExpectfinishinvestType()!=null&&!projectParams.getExpectfinishinvestType().equals("")){//项目总投资
				  
				  if(projectParams.getExpectfinishinvestType().equals(IConstants.EXPECTFINISHINVEST_TYPE_1)){
					  sqlString += " and t.xmztz is not null and t.xmztz < " + IConstants.XMZTZ;
				  }
				  else{
					  sqlString += " and t.xmztz is not null and t.xmztz >= " + IConstants.XMZTZ;
				  }
			  }
		  }
		  
		  if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
			  if(user.getSysOrganization()!=null){
				  
				  sqlString += " and t.sysOrganizationByDeclareunitsid is not null and t.sysOrganizationByDirectorunitsid is not null and t.sysOrganizationByDirectorunitsid.organizationId = t.sysOrganizationByDeclareunitsid.organizationId " +
							" and t.sysOrganizationByDeclareunitsid.organizationId = " + user.getSysOrganization().getOrganizationId() + " and t.pjstatus>1";
			  }
			  else{
				  sqlString += " and t.projectid=0";//加入一个条件让它无记录
			  }
		  }
		  else{
			  sqlString += " and t.projectid=0";//加入一个条件让它无记录
		  }
		  
		  sqlString += " and t.projectcode is not null and t.projectcode not like 'PJ%' ";
		  
		  sqlString += " and(t.deleted is null or t.deleted = 0)";
		  
		  
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public Map<Integer, String> getXmztMap() {
		xmztMap = putDictionaryitemsToMap(xmztMap, IConstants.DICTIONARY_ITEM_XMZT);
		return xmztMap;
	}
	
//	public Map<Integer, String> getXmztMap() {
//		xmztMap.clear();
//		xmztMap.put(IConstants.XMXZT_1,IConstants.XMXZT_1_NAME);
//		xmztMap.put(IConstants.XMXZT_2,IConstants.XMXZT_2_NAME);
//		xmztMap.put(IConstants.XMXZT_3,IConstants.XMXZT_3_NAME);
//		xmztMap.put(IConstants.XMXZT_4,IConstants.XMXZT_4_NAME);
//		xmztMap.put(IConstants.XMXZT_5,IConstants.XMXZT_5_NAME);
//		xmztMap.put(IConstants.XMXZT_6,IConstants.XMXZT_6_NAME);
//		xmztMap.put(IConstants.XMXZT_7,IConstants.XMXZT_7_NAME);
//		xmztMap.put(IConstants.XMXZT_8,IConstants.XMXZT_8_NAME);
//		return xmztMap;
//	}

	public void setXmztMap(Map<Integer, String> xmztMap) {
		this.xmztMap = xmztMap;
	}



	public ProjectbaseinfoParam getProjectParams() {
		return projectParams;
	}



	public void setProjectParams(ProjectbaseinfoParam projectParams) {
		this.projectParams = projectParams;
	}



	public String getSysDeptTreeJson() {
		return sysDeptTreeJson;
	}



	public void setSysDeptTreeJson(String sysDeptTreeJson) {
		this.sysDeptTreeJson = sysDeptTreeJson;
	}



	public String getYearTreeJson() {
		return yearTreeJson;
	}



	public void setYearTreeJson(String yearTreeJson) {
		this.yearTreeJson = yearTreeJson;
	}

	public void setXmlxTreeJson(String xmlxTreeJson) {
		this.xmlxTreeJson = xmlxTreeJson;
	}

	public void setHylbTreeJson(String hylbTreeJson) {
		this.hylbTreeJson = hylbTreeJson;
	}

	public void setZjxzTreeJson(String zjxzTreeJson) {
		this.zjxzTreeJson = zjxzTreeJson;
	}

	public void setSzqyTreeJson(String szqyTreeJson) {
		this.szqyTreeJson = szqyTreeJson;
	}



	public String getProjectStatusTreeJson() {
		return projectStatusTreeJson;
	}



	public void setProjectStatusTreeJson(String projectStatusTreeJson) {
		this.projectStatusTreeJson = projectStatusTreeJson;
	}



	public String getOwnerOrganizationTreeJson() {
		return ownerOrganizationTreeJson;
	}



	public void setOwnerOrganizationTreeJson(String ownerOrganizationTreeJson) {
		this.ownerOrganizationTreeJson = ownerOrganizationTreeJson;
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


	public void setManageOrganizationTreeJson(String manageOrganizationTreeJson) {
		this.manageOrganizationTreeJson = manageOrganizationTreeJson;
	}
	
}
