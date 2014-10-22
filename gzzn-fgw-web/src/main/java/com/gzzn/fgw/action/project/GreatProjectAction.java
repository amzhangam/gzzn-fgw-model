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
import com.google.gson.Gson;
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
import com.gzzn.fgw.model.BudgetOrgan;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysDx;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.pojo.ReportPojo;
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
import com.gzzn.fgw.webUtil.ProjectlogProcess;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.fgw.webUtil.ReadFileByteUtil;
import com.gzzn.fgw.webUtil.UploadFileUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;


/**
 * 
 * <p>Title: GreatProjectAction</p>
 * <p>Description:  重大项目信息维护 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-26 amzhang  new
 */
@Namespace(value = "/project/zdxm")
public class GreatProjectAction extends BaseAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(GreatProjectAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private ProjectbaseinfoParam projectParams;//系统管理查询参数
	private String sfzdxmTreeJson;//树以JSON格式存储
	private String sysDeptTreeJson;//部门树以JSON格式存储
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
	private PageUtil<PjbaseinfoPojo> page = new PageUtil<PjbaseinfoPojo>();
	private Map<Integer,String> sblxMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmlxMap = new HashMap<Integer, String>();
	private Map<Integer,String> ndMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();
	private Map<Integer,String> zjxzMap = new HashMap<Integer, String>();
	private Map<Integer,String> pjaudittypeMap = new HashMap<Integer, String>();
	private List<Pjadjunct> pjadjuncts = new ArrayList<Pjadjunct>();
	private List<SysProjectlog> projectlogs = new ArrayList<SysProjectlog>();
	private List<Pjbaseinfo> pjbaseinfos = new ArrayList<Pjbaseinfo>();
	private String pageFlag;
	
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
	
	private String xmcblb;
	
	private Integer zdxm;
	
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
	private ISysDeptService deptService;
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
	private Boolean moreSearchBool=false;//显示更多查询条件... 是否显示
	private ReportPojo params;//用于存储查询参数
	
	@Autowired
	private JdbcTemplate jdbcTemplate2;
	
	public ReportPojo getParams() {
		return params;
	}

	public void setParams(ReportPojo params) {
		this.params = params;
	}

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

		String middle = getQuerySql(IConstants.XMZT_10);
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.modifiedTime desc,xmsbXmlx, xmsbHylb,t.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		 List<PjbaseinfoPojo> list = service.findList(countSql,objSql,getOrganMap());
		 
		 setDownloadFileName(downFileNameTranscode("广州市政府投资项目库已通过审核项目汇总表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		 setExcelFile(new ProjectbaseinfoListExpExcel().expExcelTempFile(getLoginUser(), list, getXmztMap(), expExcelFilePath+"pjbaseExp.xls", 5));
		 
		 logger.info("导出广州市政府投资项目库已通过审核项目汇总表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		 return "excel";
	}
	
	/**LHQ加入：项目信息管理列表 导出报表功能*/
	@Action(value = "expNoPassExcel", results = { @Result(name="excel", type="stream",
			params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String expNoPassExcel(){
		long begin = System.currentTimeMillis();
		
		String middle = getQuerySql(IConstants.XMZT_12);
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.modifiedTime desc,xmsbXmlx, xmsbHylb,t.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		List<PjbaseinfoPojo> list = service.findList(countSql,objSql,getOrganMap());
		
		setDownloadFileName(downFileNameTranscode("广州市政府投资项目库已通过审核项目汇总表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		setExcelFile(new ProjectbaseinfoListExpExcel().expExcelTempFile(getLoginUser(), list, getXmztMap(), expExcelFilePath+"pjbaseExp.xls", 5));
		
		logger.info("导出广州市政府投资项目库已通过审核项目汇总表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "excel";
	}
	

	private Map<Integer,String> getOrganMap(){
		Map<Integer,String> organMap = new HashMap<Integer, String>();
		List<BudgetOrgan> budgetOrgans = commonService.findAll(BudgetOrgan.class);
		if(budgetOrgans!=null&&!budgetOrgans.isEmpty()){
			for(BudgetOrgan budgetOrgan:budgetOrgans){
				if(budgetOrgan!=null&&budgetOrgan.getSysOrganization()!=null&&budgetOrgan.getSysOrganization().getOrganizationId()!=null&&!organMap.containsKey(budgetOrgan.getSysOrganization().getOrganizationId())){
					organMap.put(budgetOrgan.getSysOrganization().getOrganizationId(),budgetOrgan.getId().getOrganName());
				}
			}
		}
		return organMap;
	}
	
	//进入未处理列表界面
	@Action("list")
	public String list() throws Exception
	{
		String middle = getQuerySql(IConstants.XMZT_10);
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.modifiedTime desc,xmsbXmlx, xmsbHylb,t.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		page.setSize(15);
		
		page = service.findList(page,countSql, objSql,getOrganMap());
		
		return "success";
	}
	
	//进入未处理列表界面
	@Action("nopasslist")
	public String nopasslist() throws Exception
	{
		String middle = getQuerySql(IConstants.XMZT_12);
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.modifiedTime desc,xmsbXmlx, xmsbHylb,t.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		page.setSize(15);
		
		page = service.findList(page,countSql, objSql,getOrganMap());
		
		return "success";
	}
	
	/**
	   * 项目信息管理列表查询条件初始化
	   * @param cond
	   * @return
	   */
	  private String getQuerySql(Integer pjstatus){
		  
		  SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		  
		  String sqlString = "";
		  
		  if(projectParams != null){
				if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
					
					sqlString += " and t.xmsbXmlx is not null and t.xmsbXmlx.xmlxmc = '" + projectParams.getXmsbXmlx().getXmlxmc().trim() + "' ";
				}
				if(projectParams.getXmsbHylb()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbHylb().getHylbmc())){
					sqlString += " and t.xmsbHylb is not null and t.xmsbHylb.hylbmc = '" + projectParams.getXmsbHylb().getHylbmc().trim() + "' ";
				}
				if(projectParams.getXmsbZjxz()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbZjxz().getZjxzmc())){
					sqlString += " and t.xmsbZjxz is not null and t.xmsbZjxz.zjxzmc = '" + projectParams.getXmsbZjxz().getZjxzmc().trim() + "' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getXmcblb())) {
					sqlString += " and t.xmcblb is not null and t.xmcblb like '%" + projectParams.getXmcblb().trim() + "%' ";
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
				if(StringUtils.isNotEmpty(projectParams.getAuditdeptname())) {
					sqlString += " and t.auditdeptname is not null and t.auditdeptname like '%" + projectParams.getAuditdeptname().trim() + "%' ";
				}
//				if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
//					sqlString += " and t.nextauditdeptname is not null and t.nextauditdeptname like '%" + projectParams.getNextauditdeptname().trim() + "%' ";
//				}
				if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
					sqlString += " and t.declartime >= to_date('" + projectParams.getBeginTime() + "','yyyy-MM-dd')";
				}
				if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
					sqlString += " and t.declartime <= to_date('" + projectParams.getEndTime() + "','yyyy-MM-dd')";
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
				if(projectParams.getSfzdxm()!=null){
					
					if(projectParams.getSfzdxm()==IConstants.SFZDXM_1){
						sqlString += " and t.sfzdxm = " + IConstants.SFZDXM_1;
					}
					else{
						sqlString += " and (t.sfzdxm is null or t.sfzdxm <> " + IConstants.SFZDXM_1 + ")";
					}
				}
				if(StringUtils.isNotEmpty(projectParams.getBeginModifiedTime())){//修改时间：开始时间
					sqlString += " and t.modifiedTime >= to_date('" + projectParams.getBeginModifiedTime() + "','yyyy-MM-dd')";
				}
				if(StringUtils.isNotEmpty(projectParams.getEndModifiedTime())){//修改时间：结束时间
					sqlString += " and t.modifiedTime <= to_date('" + projectParams.getEndModifiedTime() + "','yyyy-MM-dd')";
				}
				if(projectParams.getExpectfinishinvestType()!=null&&!projectParams.getExpectfinishinvestType().equals("")){//项目总投资类型
					
					if(projectParams.getExpectfinishinvestType().equals(IConstants.EXPECTFINISHINVEST_TYPE_1)){
						sqlString += " and t.xmztz is not null and t.xmztz < " + IConstants.XMZTZ;
					}
					else{
						sqlString += " and t.xmztz is not null and t.xmztz >= " + IConstants.XMZTZ;
					}
				}
				if(projectParams.getXmztzBegin()!=null){//项目总投资
					
					sqlString += " and t.xmztz is not null and t.xmztz >= " + projectParams.getXmztzBegin();
				}
				if(projectParams.getXmztzEnd()!=null){//项目总投资
					
					sqlString += " and t.xmztz is not null and t.xmztz <= " + projectParams.getXmztzEnd();
				}
			}
		  
		  if(user!=null&&user.getSysOrganization()!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_1)){//业主看自己起草的以及主管单位帮它起草的项目
			  
			  sqlString += " and((t.sysOrganizationByRecordOrgan is not null and t.sysOrganizationByRecordOrgan.organizationId = " + user.getSysOrganization().getOrganizationId() + ")";
			  sqlString += " or(t.sysOrganizationByDeclareunitsid is not null and t.sysOrganizationByDeclareunitsid.organizationId = " + user.getSysOrganization().getOrganizationId() + "))";
			  
		  }
		  else if(user!=null&&user.getSysOrganization()!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位看自己起草的以及业主申报给它的项目
			  
			  sqlString += " and((t.sysOrganizationByRecordOrgan is not null and t.sysOrganizationByRecordOrgan.organizationId = " + user.getSysOrganization().getOrganizationId() + ")";
			  sqlString += " or(t.sysOrganizationByDirectorunitsid is not null and t.sysOrganizationByDirectorunitsid.organizationId = " + user.getSysOrganization().getOrganizationId() + "))";
			  
		  }
		  else if(user!=null&&user.getUserType()!=null&&(user.getUserType().equals(IConstants.USER_TYPE_3))){//发改委用户 除了别人的草稿，其它项目都可以看
			  
			  sqlString += " and((t.sysOrganizationByRecordOrgan is not null and t.sysOrganizationByRecordOrgan.organizationId = " + user.getSysOrganization().getOrganizationId() + ")";
			  sqlString += " or(t.pjstatus > 0))";
			  
		  }
		  else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//超级管理员admin所有项目都可以看，包括草稿和已经逻辑删除的项目
			  
		  }
		  else{//其他人员啥都不能看
			  sqlString += " and t.projectid<0";
		  }
		  
		  sqlString += " and(t.deleted is null or t.deleted = 0)";
		  
		  sqlString += " and t.projectcode not like 'PJ%' ";
		  
		  sqlString += " and t.pjstatus = " + pjstatus;
		  
		  return sqlString;
	  }
	
//	/**
//	   * 将params加入到Condition中
//	   * @param cond
//	   * @return
//	   */
//	  private Condition addParamsCond(Condition cond){
//		  if(params != null){
//				//预计至XX年底累计完成投资
//				if(params.getExpectFinishInvestYear()!=null && params.getExpectFinishInvestYear()>0){
//					cond.add("pjbaseinfo.expectfinishinvestyear", Operator.EQ, params.getExpectFinishInvestYear());
//				}
//				if(this.checkInteger(params.getExpectFinishInvest())){
//					cond.add("pjbaseinfo.expectfinishinvest", Operator.GE, params.getExpectFinishInvest());
//				}
//				if(this.checkInteger(params.getExpectFinishInvest2())){
//					cond.add("pjbaseinfo.expectfinishinvest", Operator.LE, params.getExpectFinishInvest2());
//				}
//				if(this.checkInteger(params.getExpectFinishOtherInvest())){
//					cond.add("pjbaseinfo.expectfinishotherinvest", Operator.GE, params.getExpectFinishOtherInvest());
//				}
//				if(this.checkInteger(params.getExpectFinishOtherInvest2())){
//					cond.add("pjbaseinfo.expectfinishotherinvest", Operator.LE, params.getExpectFinishOtherInvest2());
//				}
//				
//				//项目总投资
//				if(this.checkInteger(params.getPjinvestSum())){
//					cond.add("pjinvestsum", Operator.GE, params.getPjinvestSum());
//				}
//				if(this.checkInteger(params.getPjinvestSum2())){
//					cond.add("pjinvestsum", Operator.LE, params.getPjinvestSum2());
//				}	
//				if(this.checkInteger(params.getPjinvestCity())){
//					cond.add("pjinvestcity", Operator.GE, params.getPjinvestCity());
//				}
//				if(this.checkInteger(params.getPjinvestCity2())){
//					cond.add("pjinvestcity", Operator.LE, params.getPjinvestCity2());
//				}	
//				if(this.checkInteger(params.getPjinvestCompany())){
//					cond.add("pjinvestcompany", Operator.GE, params.getPjinvestCompany());
//				}
//				if(this.checkInteger(params.getPjinvestCompany2())){
//					cond.add("pjinvestcompany", Operator.LE, params.getPjinvestCompany2());
//				}	
//				if(this.checkInteger(params.getPjinvestBank())){
//					cond.add("pjinvestbank", Operator.GE, params.getPjinvestBank());
//				}
//				if(this.checkInteger(params.getPjinvestBank2())){
//					cond.add("pjinvestbank", Operator.LE, params.getPjinvestBank2());
//				}	
//				if(this.checkInteger(params.getPjinvestOther())){
//					cond.add("pjinvestother", Operator.GE, params.getPjinvestOther());
//				}
//				if(this.checkInteger(params.getPjinvestOther2())){
//					cond.add("pjinvestother", Operator.LE, params.getPjinvestOther2());
//				}
//				
//				//投资计划建议
//				if(params.getPlanInvestYear()!=null && params.getPlanInvestYear()>0){
//					cond.add("planinvestyear", Operator.EQ, params.getPlanInvestYear());
//				}
//				if(this.checkInteger(params.getPlanInvestSum())){
//					cond.add("planinvestsum", Operator.GE, params.getPlanInvestSum());
//				}
//				if(this.checkInteger(params.getPlanInvestSum2())){
//					cond.add("planinvestsum", Operator.LE, params.getPlanInvestSum2());
//				}	
//				if(this.checkInteger(params.getPlanInvestCity())){
//					cond.add("planinvestcity", Operator.GE, params.getPlanInvestCity());
//				}
//				if(this.checkInteger(params.getPlanInvestCity2())){
//					cond.add("planinvestcity", Operator.LE, params.getPlanInvestCity2());
//				}	
//				if(this.checkInteger(params.getPlanInvestCompany())){
//					cond.add("planinvestcompany", Operator.GE, params.getPlanInvestCompany());
//				}
//				if(this.checkInteger(params.getPlanInvestCompany2())){
//					cond.add("planinvestcompany", Operator.LE, params.getPlanInvestCompany2());
//				}	
//				if(this.checkInteger(params.getPlanInvestBank())){
//					cond.add("planinvestbank", Operator.GE, params.getPlanInvestBank());
//				}
//				if(this.checkInteger(params.getPjinvestBank2())){
//					cond.add("planinvestbank", Operator.LE, params.getPlanInvestBank2());
//				}	
//				if(this.checkInteger(params.getPlanInvestOther())){
//					cond.add("planinvestother", Operator.GE, params.getPlanInvestOther());
//				}
//				if(this.checkInteger(params.getPlanInvestOther2())){
//					cond.add("planinvestother", Operator.LE, params.getPlanInvestOther2());
//				}	
//		  }
//		  return cond;
//	  }
	
	/**
	   * 将params加入到Condition中
	   * @param cond
	   * @return
	   */
	  private Condition addParamsCond(Condition cond){
		  if(params != null){
				//预计至XX年底累计完成投资
				if(params.getExpectFinishInvestYear()!=null && params.getExpectFinishInvestYear()>0){
					cond.add("expectfinishinvestyear", Operator.EQ, params.getExpectFinishInvestYear());
				}
				if(this.checkInteger(params.getExpectFinishInvest())){
					cond.add("expectfinishinvest", Operator.GE, params.getExpectFinishInvest());
				}
				if(this.checkInteger(params.getExpectFinishInvest2())){
					cond.add("expectfinishinvest", Operator.LE, params.getExpectFinishInvest2());
				}
				if(this.checkInteger(params.getExpectFinishOtherInvest())){
					cond.add("expectfinishotherinvest", Operator.GE, params.getExpectFinishOtherInvest());
				}
				if(this.checkInteger(params.getExpectFinishOtherInvest2())){
					cond.add("expectfinishotherinvest", Operator.LE, params.getExpectFinishOtherInvest2());
				}
				
				//项目总投资
				if(this.checkInteger(params.getPjinvestSum())){
					cond.add("pjinvestsum", Operator.GE, params.getPjinvestSum());
				}
				if(this.checkInteger(params.getPjinvestSum2())){
					cond.add("pjinvestsum", Operator.LE, params.getPjinvestSum2());
				}	
				if(this.checkInteger(params.getPjinvestCity())){
					cond.add("pjinvestcity", Operator.GE, params.getPjinvestCity());
				}
				if(this.checkInteger(params.getPjinvestCity2())){
					cond.add("pjinvestcity", Operator.LE, params.getPjinvestCity2());
				}	
				if(this.checkInteger(params.getPjinvestCompany())){
					cond.add("pjinvestcompany", Operator.GE, params.getPjinvestCompany());
				}
				if(this.checkInteger(params.getPjinvestCompany2())){
					cond.add("pjinvestcompany", Operator.LE, params.getPjinvestCompany2());
				}	
				if(this.checkInteger(params.getPjinvestBank())){
					cond.add("pjinvestbank", Operator.GE, params.getPjinvestBank());
				}
				if(this.checkInteger(params.getPjinvestBank2())){
					cond.add("pjinvestbank", Operator.LE, params.getPjinvestBank2());
				}	
				if(this.checkInteger(params.getPjinvestOther())){
					cond.add("pjinvestother", Operator.GE, params.getPjinvestOther());
				}
				if(this.checkInteger(params.getPjinvestOther2())){
					cond.add("pjinvestother", Operator.LE, params.getPjinvestOther2());
				}
				
				//投资计划建议
				if(params.getPlanInvestYear()!=null && params.getPlanInvestYear()>0){
					cond.add("planinvestyear", Operator.EQ, params.getPlanInvestYear());
				}
				if(this.checkInteger(params.getPlanInvestSum())){
					cond.add("planinvestsum", Operator.GE, params.getPlanInvestSum());
				}
				if(this.checkInteger(params.getPlanInvestSum2())){
					cond.add("planinvestsum", Operator.LE, params.getPlanInvestSum2());
				}	
				if(this.checkInteger(params.getPlanInvestCity())){
					cond.add("planinvestcity", Operator.GE, params.getPlanInvestCity());
				}
				if(this.checkInteger(params.getPlanInvestCity2())){
					cond.add("planinvestcity", Operator.LE, params.getPlanInvestCity2());
				}	
				if(this.checkInteger(params.getPlanInvestCompany())){
					cond.add("planinvestcompany", Operator.GE, params.getPlanInvestCompany());
				}
				if(this.checkInteger(params.getPlanInvestCompany2())){
					cond.add("planinvestcompany", Operator.LE, params.getPlanInvestCompany2());
				}	
				if(this.checkInteger(params.getPlanInvestBank())){
					cond.add("planinvestbank", Operator.GE, params.getPlanInvestBank());
				}
				if(this.checkInteger(params.getPjinvestBank2())){
					cond.add("planinvestbank", Operator.LE, params.getPlanInvestBank2());
				}	
				if(this.checkInteger(params.getPlanInvestOther())){
					cond.add("planinvestother", Operator.GE, params.getPlanInvestOther());
				}
				if(this.checkInteger(params.getPlanInvestOther2())){
					cond.add("planinvestother", Operator.LE, params.getPlanInvestOther2());
				}	
		  }
		  return cond;
	  }
	  
	  
	  private Boolean checkInteger(Double gNum){
		  if(gNum!=null && gNum>=0){
				 return true;
		  }
		  return false;
	  }
	
	
	
	@Action("getNextUserJson")
	public String getNextUserJson(){
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		nextUserJson = userService.findHandlerTreeJson(false,user.getSysDept().getDeptId());
		outPutJSON(nextUserJson);
		
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
	

	@Action("getSfzdxmJson")
	public String getSfzdxmJson(){
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
			TreeNodesPojo objTree1 = new TreeNodesPojo();
			objTree1.setId(String.valueOf(0));
			objTree1.setName("否");
			listTree.add(objTree1);
			
			TreeNodesPojo objTree2 = new TreeNodesPojo();
			objTree2.setId(String.valueOf(1));
			objTree2.setName("是");
			listTree.add(objTree2);
		
			sfzdxmTreeJson = new Gson().toJson(listTree);
		outPutJSON(sfzdxmTreeJson);
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
	
	@GzznLog
	@Action(value = "processGreatProject")
	public String processGreatProject() throws Exception{
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		try{
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					
					Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i));
					
					if(pjbaseinfo!=null){
						
						pjbaseinfo.setSfzdxm(Integer.valueOf(zdxm));
						
						commonService.saveOrUpdate(pjbaseinfo);
						
						String str = "";
						
						if(zdxm==1){
							str = "重大项目";
						}
						else{
							str = "非重大项目";
						}
						
						logObject = new LogObject((user.getSysDept()!=null?user.getSysDept().getDeptname():"")+user.getUserName()+"将项目"+pjbaseinfo.getProjectname()+"设为"+str);
						
					}
				}
			}
			outPutMsg(true, "是否重大项目设置成功！");
		}
		catch(Exception e){
			outPutError("操作失败！");
			e.printStackTrace();
			throw e;
		}
		return null;
		}
	
	@GzznLog
	@Action(value = "returnProject")
	public String returnProject() throws Exception{
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		if(user!=null&&user.getUserType()!=null&&(user.getUserType().equals(IConstants.USER_TYPE_3)||user.getUserType().equals(IConstants.USER_TYPE_5))&&user.getSysDept()!=null&&user.getSysDept().getFullcode().equalsIgnoreCase(fgwtzc)){
			try{
				if (StringUtils.isNotEmpty(id)) {
					for (String i : id.split("@")) {
						
						Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i));
						
						if(pjbaseinfo!=null){
							
							pjbaseinfo.setPjstatus(IConstants.XMZT_12);
							pjbaseinfo.setAuditdept(user.getSysDept().getDeptId());
							pjbaseinfo.setAuditdeptname(user.getSysDept().getDeptname());
							pjbaseinfo.setRecordername(user.getUserName());
							pjbaseinfo.setRecordertime(new Date());
							
							Pjauditlog newObj = new Pjauditlog();
							newObj.setSysOrganizationByOrganizationId(user.getSysOrganization());
							newObj.setSysDeptByDeptId(user.getSysDept());
							newObj.setSysDeptBySenddeptid(user.getSysDept());
							newObj.setSysOrganizationByPjauditunits(user.getSysOrganization());
							newObj.setPjbaseinfo(pjbaseinfo);
							newObj.setPjaudittype(IConstants.SHLX_9);
							newObj.setRecordertime(new Date());
							newObj.setRecordername(user.getUserName());
							newObj.setPjauditresult(IConstants.SHJG_2);
							
							commonService.save(newObj);
							
							commonService.saveOrUpdate(pjbaseinfo);
							
							logObject = new LogObject((user.getSysDept()!=null?user.getSysDept().getDeptname():"")+user.getUserName()+"将项目"+pjbaseinfo.getProjectname()+"退回");
							
							String[] strs = new String[1];
							strs[0] = obj.getMobilePhone();
							String sfnr = pjbaseinfo.getSysOrganizationByDeclareunitsid().getOrganizationName()+"，贵单位报送的:"+pjbaseinfo.getProjectname()+"存在问题，现退回请改正后再次申报。"+user.getSysOrganization().getOrganizationName()+(user.getSysDept()!=null?" " + user.getSysDept().getDeptname():"")+sdf.format(new Date());
							try {
//								smsService.invoke(strs,sfnr);
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
							}
							
						}
					}
				}
				outPutMsg(true, "项目退回成功！");
			}
			catch(Exception e){
				outPutError("操作失败！");
				e.printStackTrace();
				throw e;
			}
		}
		else{
			outPutError("无权进行此项操作！");
		}
		return null;
	}
	
	@Action("ztjd")
	public String ztjd() throws Exception{
		if(StringUtils.isNotEmpty(id)){
			List<Integer> ids = new ArrayList<Integer>();
			for (String i : id.split("@")) {
				ids.add(Integer.valueOf(i));
			}
			Condition con = new Condition();
			con.add("projectid",Operator.ISNOTNULL,null);
			con.add("projectid", Operator.IN, ids);
			pjbaseinfos = commonService.find(Pjbaseinfo.class, con);
		}
		return "success";
	}
	
	private void projectloglist() throws Exception
	{
		Condition con = new Condition();
		con.add("pjbaseinfo", Operator.ISNOTNULL,null);
		con.add("pjbaseinfo.projectid", Operator.EQ,obj.getProjectid());
		Order order = new Order(Direction.DESC, "operationDatetime");
		Sort sort = new Sort(order);
//		projectlogs = commonService.find(SysProjectlog.class, con,sort);
		List<SysProjectlog> tempProjectlogs = commonService.find(SysProjectlog.class, con,sort);
		if(tempProjectlogs!=null&&!tempProjectlogs.isEmpty()){
			int count = 0;
			for(SysProjectlog projectlog:tempProjectlogs){
				if(++count>5){
					break;
				}
				else{
					projectlogs.add(projectlog);
				}
			}
		}
		
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
		}
		else{
			obj = (Pjbaseinfo) getHttpSession().getAttribute("obj");
			subObj = (Projectinvest) getHttpSession().getAttribute("subObj");
			
		}
		projectloglist();
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
		try {
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
			
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){//发改委用户
				if(user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equals(fgwtzc)){//投资处
					
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
						pjauditlog.setPjaudittype(IConstants.SHLX_6);
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
					}
				}
				else{//其他处室
					if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长
						pjauditlog.setPjaudittype(IConstants.SHLX_2);
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
					}
				}
			}
			
			commonService.saveOrUpdate(pjauditlog);
			
			Pjbaseinfo tempPjbaseinfo = commonService.findOne(Pjbaseinfo.class,obj.getProjectid());
			
			if(tempPjbaseinfo!=null){
				
				tempPjbaseinfo.setPjstatus(obj.getPjstatus());
				
				commonService.saveOrUpdate(tempPjbaseinfo);
			}
		
			logObject = new LogObject("分派日志信息",pjauditlog.getPjauditlogid(),obj.getProjectname()+user.getUserName()+"分派给相应的处室",obj);
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存数据失败";
		}
		
		return "success";
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
					commonService.delete(Pjbaseinfo.class, Integer.parseInt(i));
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
	public PageUtil<PjbaseinfoPojo> getPage() {
		return page;
	}
	public void setPage(PageUtil<PjbaseinfoPojo> page) {
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


	@Action("xmcblb")
	public String xmcblb(){
		
		if(StringUtils.isNotEmpty(id)){
			request.setAttribute("id",id);
		}
		if(StringUtils.isNotEmpty(pageFlag)){
			request.setAttribute("pageFlag",pageFlag);
		}
		
		return "success";
	}
	
	@Action("zdxm")
	public String zdxm(){
		
		if(StringUtils.isNotEmpty(id)){
			request.setAttribute("id",id);
		}
		
		return "success";
	}
	
//	@GzznLog
//	@Action(value = "setXmcblb", results = { @Result(location = "list.ac", type = "redirectAction", params = {
//			"message", "${message}", "encode", "true" }) })
//	public String setXmcblb() throws Exception{
//		if (StringUtils.isNotEmpty(id)) {
//			for (String i : id.split("@")) {
//				
//				Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i));
//				
//				pjbaseinfo.setXmcblb(xmcblb);
//				
//				commonService.saveOrUpdate(pjbaseinfo);
//			}
//		}
//		return "success";
//	}
	
	@GzznLog
	@Action(value = "setXmcblb")
	public String setXmcblb() throws Exception{
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		try{
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					
					Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i));
					
					
					Pjbaseinfo beforePjbaseinfo = new Pjbaseinfo();
					
					PojoCopyUtil.copySameTypeField(pjbaseinfo,beforePjbaseinfo);
					
					Projectinvest beforeProjectinvest = (pjbaseinfo!=null&&pjbaseinfo.getProjectinvests()!=null&&!pjbaseinfo.getProjectinvests().isEmpty())?pjbaseinfo.getProjectinvests().iterator().next():null;
					
					pjbaseinfo.setXmcblb(xmcblb);
					
					commonService.saveOrUpdate(pjbaseinfo);
					
					if(beforePjbaseinfo!=null&&beforeProjectinvest!=null){
						ProjectlogProcess.createProjectLog(beforePjbaseinfo,pjbaseinfo,beforeProjectinvest,beforeProjectinvest,user,commonService);
					}
					
					logObject = new LogObject((user.getSysDept()!=null?user.getSysDept().getDeptname():"")+user.getUserName()+"将项目"+pjbaseinfo.getProjectname()+"储备类别设为"+xmcblb);
					
				}
			}
//			outPutMsg(true, "设置项目储备类别成功！");
			
			outPutMsg(true, pageFlag);
		}
		catch(Exception e){
			outPutError("操作失败！");
			e.printStackTrace();
			throw e;
		}
		return null;
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

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
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

	public List<SysProjectlog> getProjectlogs() {
		return projectlogs;
	}


	public void setProjectlogs(List<SysProjectlog> projectlogs) {
		this.projectlogs = projectlogs;
	}


	public SysUser getNextUser() {
		return nextUser;
	}


	public void setNextUser(SysUser nextUser) {
		this.nextUser = nextUser;
	}


	public List<Pjbaseinfo> getPjbaseinfos() {
		return pjbaseinfos;
	}


	public void setPjbaseinfos(List<Pjbaseinfo> pjbaseinfos) {
		this.pjbaseinfos = pjbaseinfos;
	}


	public String getXmcblb() {
		return xmcblb;
	}


	public void setXmcblb(String xmcblb) {
		this.xmcblb = xmcblb;
	}
	
	public void setZjxzTreeJson(String zjxzTreeJson) {
		this.zjxzTreeJson = zjxzTreeJson;
	}

	public Boolean getMoreSearchBool() {
		return moreSearchBool;
	}

	public void setMoreSearchBool(Boolean moreSearchBool) {
		this.moreSearchBool = moreSearchBool;
	}

	public Integer getZdxm() {
		return zdxm;
	}

	public void setZdxm(Integer zdxm) {
		this.zdxm = zdxm;
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

	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}
	
	
	
}
