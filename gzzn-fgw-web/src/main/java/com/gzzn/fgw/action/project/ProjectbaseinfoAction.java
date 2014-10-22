package com.gzzn.fgw.action.project;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javassist.expr.NewArray;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import net.sf.ehcache.search.expression.Criteria;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
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
import com.gzzn.fgw.action.FileSupportAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.expExcel.ProjectbaseinfoExpExcel;
import com.gzzn.fgw.expExcel.ProjectbaseinfoListExpExcel;
import com.gzzn.fgw.model.BudgetOrgan;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbHylb;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.XmsbZjxz;
import com.gzzn.fgw.model.pojo.ReportPojo;
import com.gzzn.fgw.model.pojo.ReportPojoOld;
import com.gzzn.fgw.model.pojo.SysProjectlogPojo;
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
import com.gzzn.fgw.service.sys.ISysProjectlogService;
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
import com.gzzn.util.exception.CustomException;
import com.gzzn.util.web.PageUtil;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;
import com.sun.tools.javac.resources.javac;

/**
 * 
 * <p>Title: SysDeptAction</p>
 * <p>Description:  申报项目信息维护 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-26 amzhang  new
 */
@Namespace(value = "/project/pjbaseinfo")
@ParentPackage("struts-common")
public class ProjectbaseinfoAction  extends FileSupportAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(ProjectbaseinfoAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private String from;//从哪个页面点击编辑过来 1=项目信息管理   2=待处理项目管理
	private String ids;//删除的id，多个间用@隔开
	private ProjectbaseinfoParam projectParams;//系统管理查询参数
	private ReportPojo params;//用于存储查询参数
	public ReportPojo getParams() {
		return params;
	}

	public void setParams(ReportPojo params) {
		this.params = params;
	}


	private String sysDeptTreeJson;//部门树以JSON格式存储
	private String yearTreeJson;//年份树以JSON格式存储
//	private String projectTypeTreeJson;//树以JSON格式存储
	private String xmlxTreeJson;//树以JSON格式存储
	private String hylbTreeJson;//树以JSON格式存储
	private String zjxzTreeJson;//树以JSON格式存储
	private String szqyTreeJson;//树以JSON格式存储
//	private String sysHyflTreeJson;//树以JSON格式存储
	private String projectStatusTreeJson;//树以JSON格式存储
	private String ownerOrganizationTreeJson;//业主单位树以JSON格式存储
	private String manageOrganizationTreeJson;//主管单位树以JSON格式存储
	private Pjbaseinfo obj;//对象
	private Projectinvest subObj;
	private String message;//返回页面信息
	private PageUtil<PjbaseinfoPojo> page = new PageUtil<PjbaseinfoPojo>();
	private PageUtil<Pjauditlog> pagelog = new PageUtil<Pjauditlog>();
	private Map<Integer,String> sblxMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmlxMap = new HashMap<Integer, String>();
	private Map<Integer,String> ndMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();
	private Map<Integer,String> zjxzMap = new HashMap<Integer, String>();
	private Map<Integer,String> pjaudittypeMap = new HashMap<Integer, String>();
	private List<Pjadjunct> pjadjuncts = new ArrayList<Pjadjunct>();
	
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
	private String fxpg;
	private String sgys;
	
	private List<File> uploadLxpf; //立项批复
	private List<String> uploadLxpfFileName; 
	private List<String> uploadLxpfContentType; 
	
	private List<File> uploadGhxz; //规划选址
	private List<String> uploadGhxzFileName; 
	private List<String> uploadGhxzContentType; 
	
	private List<File> uploadYdys; //用地预审
	private List<String> uploadYdysFileName; 
	private List<String> uploadYdysContentType; 
	
	private List<File> uploadHjyx; //环境影响评价
	private List<String> uploadHjyxFileName; 
	private List<String> uploadHjyxContentType; 
	
	private List<File> uploadJnpg; //节能评估审查
	private List<String> uploadJnpgFileName; 
	private List<String> uploadJnpgContentType; 
	
	private List<File> uploadKypf; //可研批复
	private List<String> uploadKypfFileName; 
	private List<String> uploadKypfContentType; 
	
	private List<File> uploadCbsj; //初步设计及概算
	private List<String> uploadCbsjFileName; 
	private List<String> uploadCbsjContentType; 
	
	private List<File> uploadZbtb; //招标投标情况
	private List<String> uploadZbtbFileName; 
	private List<String> uploadZbtbContentType; 
	
	private List<File> uploadZdcq; //征地拆迁
	private List<String> uploadZdcqFileName; 
	private List<String> uploadZdcqContentType; 
	
	private List<File> uploadQtqq; //其他前期工作
	private List<String> uploadQtqqFileName; 
	private List<String> uploadQtqqContentType; 
	
	private List<File> uploadSbyj; //项目申报依据
	private List<String> uploadSbyjFileName; 
	private List<String> uploadSbyjContentType; 
	
	private List<File> uploadXxjd; //工程形象进度
	private List<String> uploadXxjdFileName; 
	private List<String> uploadXxjdContentType; 
	
	private List<File> uploadCzwt; //存在的问题
	private List<String> uploadCzwtFileName; 
	private List<String> uploadCzwtContentType; 
	
	private List<File> uploadSmcl; //项目书面材料
	private List<String> uploadSmclFileName; 
	private List<String> uploadSmclContentType; 
	
	private List<File> uploadFxpg; //(重大项目)社会风险评估)
	private List<String> uploadFxpgFileName; 
	private List<String> uploadFxpgContentType; 
	
	private List<File> uploadSgys; //施工图设计与预算
	private List<String> uploadSgysFileName; 
	private List<String> uploadSgysContentType; 
	
	private List<File> uploadQtsx; //需要补充的其它事项
	private List<String> uploadQtsxFileName; 
	private List<String> uploadQtsxContentType; 
	
	private List<File> uploadJbqk; //投资项目基本情况描述(对于更新改造类项目必填)
	private List<String> uploadJbqkFileName; 
	private List<String> uploadJbqkContentType; 
	
	private String fpath = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"upload.dir");//上传文件的路径
	
	private String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"fgwtzc.fullcode");
//	private Properties prop = PropertiesUtil.getProperties("projectProperty.properties");
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat sdfOnlyYear = new SimpleDateFormat("yyyy");
	
	private InputStream stream; //用于下载
	
	private String preString = "UploadFile";
	
	private SimpleDateFormat sdfFull = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private Date date = new Date();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IProjectbaseinfoService service;
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
	@Autowired
	private IPjauditlogService pjauditlogService;
	@Autowired
	private ISysProjectlogService projectlogService;
	 
	private String fileName;//文件名称
	private String fileUrl;//文件路径
	private Boolean moreSearchBool=false;//显示更多查询条件... 是否显示
	
	
	
//	//进入机构信息列表界面
//	@Action("list")
//	public String list(){
//		removeSession();
//		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
//		Condition con = addParamsCond();
//		Sort sort = addSort();
//		
//		page = service.findList(page, con, sort, user);
//		return "success";
//	}
	
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
	
	//进入机构信息列表界面
	@Action("list")
	public String list(){
		removeSession();
		
		String middle = getQuerySql();
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.modifiedTime desc,pjstatus,declartime desc,projectid desc ";
		
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
	  private String getQuerySql(){
		  
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
				if(projectParams.getPjstatus()!=null){
					sqlString += " and t.pjstatus is not null and t.pjstatus = " + projectParams.getPjstatus();
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
				if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
					sqlString += " and t.nextauditdeptname is not null and t.nextauditdeptname like '%" + projectParams.getNextauditdeptname().trim() + "%' ";
				}
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
			  sqlString += " and(t.deleted is null or t.deleted = 0)";
			  
		  }
		  else if(user!=null&&user.getSysOrganization()!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位看自己起草的以及业主申报给它的项目
			  
			  sqlString += " and((t.sysOrganizationByRecordOrgan is not null and t.sysOrganizationByRecordOrgan.organizationId = " + user.getSysOrganization().getOrganizationId() + ")";
			  sqlString += " or(t.sysOrganizationByDirectorunitsid is not null and t.sysOrganizationByDirectorunitsid.organizationId = " + user.getSysOrganization().getOrganizationId() + "))";
			  sqlString += " and(t.deleted is null or t.deleted = 0)";
			  
		  }
		  else if(user!=null&&user.getUserType()!=null&&(user.getUserType().equals(IConstants.USER_TYPE_3))){//发改委用户 除了别人的草稿，其它项目都可以看
			  
			  sqlString += " and((t.sysOrganizationByRecordOrgan is not null and t.sysOrganizationByRecordOrgan.organizationId = " + user.getSysOrganization().getOrganizationId() + ")";
			  sqlString += " or(t.pjstatus > 0))";
			  sqlString += " and(t.deleted is null or t.deleted = 0)";
		  }
		  else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//超级管理员admin所有项目都可以看，包括草稿和已经逻辑删除的项目
			  
		  }
		  else{//其他人员啥都不能看
			  sqlString += " and t.projectid<0";
			  sqlString += " and(t.deleted is null or t.deleted = 0)";
		  }
			  
		  return sqlString;
	  }
	
	/**
	 * 项目信息管理列表查询排序
	 * @return
	 */
	private Sort addSort(){
		Order order0 = new Order(Direction.DESC, "modifiedTime");
		Order order1 = new Order(Direction.ASC, "pjstatus");
		Order order2 = new Order(Direction.DESC, "declartime");
		Order order3 = new Order(Direction.DESC, "projectid");
		Sort sort = new Sort(order0,order1, order2,order3);
		return sort;
	}
	
//	/**
//	 * 项目信息管理列表查询排序
//	 * @return
//	 */
//	private Sort addSort(){
//		Order order0 = new Order(Direction.DESC, "pjbaseinfo.modifiedTime");
//		Order order1 = new Order(Direction.ASC, "pjbaseinfo.pjstatus");
//		Order order2 = new Order(Direction.DESC, "pjbaseinfo.declartime");
//		Order order3 = new Order(Direction.DESC, "pjbaseinfo.projectid");
//		Sort sort = new Sort(order0,order1, order2,order3);
//		return sort;
//	}
//	
//	/**
//	   * 项目信息管理列表查询条件初始化
//	   * @param cond
//	   * @return
//	   */
//	  private Condition addParamsCond(){
//		  Condition con = new Condition();
//		  
//		  if(projectParams != null){
//				if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
//					con.add("pjbaseinfo.xmsbXmlx",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.xmsbXmlx.xmlxmc", Operator.EQ, projectParams.getXmsbXmlx().getXmlxmc().trim());
//				}
//				if(projectParams.getXmsbHylb()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbHylb().getHylbmc())){
//					con.add("pjbaseinfo.xmsbHylb",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.xmsbHylb.hylbmc", Operator.EQ, projectParams.getXmsbHylb().getHylbmc().trim());
//				}
//				if(projectParams.getXmsbZjxz()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbZjxz().getZjxzmc())){
//					con.add("pjbaseinfo.xmsbZjxz",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.xmsbZjxz.zjxzmc", Operator.EQ, projectParams.getXmsbZjxz().getZjxzmc().trim());
//				}
//				if(projectParams.getPjstatus()!=null){
//					con.add("pjbaseinfo.pjstatus",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.pjstatus", Operator.EQ, projectParams.getPjstatus());
//				}
//				if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
//					con.add("pjbaseinfo.projectname",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.projectname", Operator.LIKE, projectParams.getProjectname());
//				}
//				if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
//					con.add("pjbaseinfo.projectcode",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.projectcode", Operator.LIKE, projectParams.getProjectcode());
//				}
////				if(StringUtils.isNotEmpty(projectParams.getAuditdeptname())) {
////					con.add("auditdeptname",Operator.ISNOTNULL,null);
////					con.add("auditdeptname", Operator.LIKE, projectParams.getAuditdeptname());
////				}
//				if(StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
//					con.add("pjbaseinfo.sysOrganizationByDeclareunitsid",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationName", Operator.LIKE, projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim());
//				}
//				if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
//					con.add("pjbaseinfo.nextauditdeptname",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.nextauditdeptname", Operator.EQ, projectParams.getNextauditdeptname().trim());
//				}
//				if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
//					con.add("pjbaseinfo.declartime", Operator.GE, DateUtil.parse(projectParams.getBeginTime(),"yyyy-MM-dd"));
//				}
//				if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
//					con.add("pjbaseinfo.declartime", Operator.LE, DateUtil.parse(projectParams.getEndTime(),"yyyy-MM-dd"));
//				}
//				if(StringUtils.isNotEmpty(projectParams.getZgdw())){//主管单位
//					con.add("pjbaseinfo.sysOrganizationByDirectorunitsid", Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationName", Operator.LIKE, projectParams.getZgdw());
//				}
//				if(projectParams.getXmfl()!=null&&!projectParams.getXmfl().equals("")){
//					con.add("pjbaseinfo.xmfl",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.xmfl",Operator.EQ,projectParams.getXmfl());
//				}
//				if(StringUtils.isNotEmpty(projectParams.getManageunitsname())) {
//					con.add("pjbaseinfo.manageunitsname",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.manageunitsname", Operator.LIKE, projectParams.getManageunitsname());
//				}
//				if(StringUtils.isNotEmpty(projectParams.getProjectcontent())) {
//					con.add("pjbaseinfo.projectcontent",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.projectcontent", Operator.LIKE, projectParams.getProjectcontent());
//				}
//				if(StringUtils.isNotEmpty(projectParams.getDeclaregist())) {
//					con.add("pjbaseinfo.declaregist",Operator.ISNOTNULL,null);
//					con.add("pjbaseinfo.declaregist", Operator.LIKE, projectParams.getDeclaregist());
//				}
//			}
//		  
//		  if(params != null){
//				//预计至XX年底累计完成投资
//				if(params.getExpectFinishInvestYear()!=null && params.getExpectFinishInvestYear()>0){
//					con.add("pjbaseinfo.expectfinishinvestyear", Operator.EQ, params.getExpectFinishInvestYear());
//				}
//				if(this.checkInteger(params.getExpectFinishInvest())){
//					con.add("pjbaseinfo.expectfinishinvest", Operator.GE, params.getExpectFinishInvest());
//				}
//				if(this.checkInteger(params.getExpectFinishInvest2())){
//					con.add("pjbaseinfo.expectfinishinvest", Operator.LE, params.getExpectFinishInvest2());
//				}
//				if(this.checkInteger(params.getExpectFinishOtherInvest())){
//					con.add("pjbaseinfo.expectfinishotherinvest", Operator.GE, params.getExpectFinishOtherInvest());
//				}
//				if(this.checkInteger(params.getExpectFinishOtherInvest2())){
//					con.add("pjbaseinfo.expectfinishotherinvest", Operator.LE, params.getExpectFinishOtherInvest2());
//				}
//				
//				//项目总投资
//				if(this.checkInteger(params.getPjinvestSum())){
//					con.add("pjinvestsum", Operator.GE, params.getPjinvestSum());
//				}
//				if(this.checkInteger(params.getPjinvestSum2())){
//					con.add("pjinvestsum", Operator.LE, params.getPjinvestSum2());
//				}	
//				if(this.checkInteger(params.getPjinvestCity())){
//					con.add("pjinvestcity", Operator.GE, params.getPjinvestCity());
//				}
//				if(this.checkInteger(params.getPjinvestCity2())){
//					con.add("pjinvestcity", Operator.LE, params.getPjinvestCity2());
//				}	
//				if(this.checkInteger(params.getPjinvestCompany())){
//					con.add("pjinvestcompany", Operator.GE, params.getPjinvestCompany());
//				}
//				if(this.checkInteger(params.getPjinvestCompany2())){
//					con.add("pjinvestcompany", Operator.LE, params.getPjinvestCompany2());
//				}	
//				if(this.checkInteger(params.getPjinvestBank())){
//					con.add("pjinvestbank", Operator.GE, params.getPjinvestBank());
//				}
//				if(this.checkInteger(params.getPjinvestBank2())){
//					con.add("pjinvestbank", Operator.LE, params.getPjinvestBank2());
//				}	
//				if(this.checkInteger(params.getPjinvestOther())){
//					con.add("pjinvestother", Operator.GE, params.getPjinvestOther());
//				}
//				if(this.checkInteger(params.getPjinvestOther2())){
//					con.add("pjinvestother", Operator.LE, params.getPjinvestOther2());
//				}
//				
//				//投资计划建议
//				if(params.getPlanInvestYear()!=null && params.getPlanInvestYear()>0){
//					con.add("planinvestyear", Operator.EQ, params.getPlanInvestYear());
//				}
//				if(this.checkInteger(params.getPlanInvestSum())){
//					con.add("planinvestsum", Operator.GE, params.getPlanInvestSum());
//				}
//				if(this.checkInteger(params.getPlanInvestSum2())){
//					con.add("planinvestsum", Operator.LE, params.getPlanInvestSum2());
//				}	
//				if(this.checkInteger(params.getPlanInvestCity())){
//					con.add("planinvestcity", Operator.GE, params.getPlanInvestCity());
//				}
//				if(this.checkInteger(params.getPlanInvestCity2())){
//					con.add("planinvestcity", Operator.LE, params.getPlanInvestCity2());
//				}	
//				if(this.checkInteger(params.getPlanInvestCompany())){
//					con.add("planinvestcompany", Operator.GE, params.getPlanInvestCompany());
//				}
//				if(this.checkInteger(params.getPlanInvestCompany2())){
//					con.add("planinvestcompany", Operator.LE, params.getPlanInvestCompany2());
//				}	
//				if(this.checkInteger(params.getPlanInvestBank())){
//					con.add("planinvestbank", Operator.GE, params.getPlanInvestBank());
//				}
//				if(this.checkInteger(params.getPjinvestBank2())){
//					con.add("planinvestbank", Operator.LE, params.getPlanInvestBank2());
//				}	
//				if(this.checkInteger(params.getPlanInvestOther())){
//					con.add("planinvestother", Operator.GE, params.getPlanInvestOther());
//				}
//				if(this.checkInteger(params.getPlanInvestOther2())){
//					con.add("planinvestother", Operator.LE, params.getPlanInvestOther2());
//				}	
//		  }
//		  return con;
//	  }
	
	/**
	   * 项目信息管理列表查询条件初始化
	   * @param cond
	   * @return
	   */
	  private Condition addParamsCond(){
		  Condition con = new Condition();
		  
		  if(projectParams != null){
				if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
					con.add("xmsbXmlx",Operator.ISNOTNULL,null);
					con.add("xmsbXmlx.xmlxmc", Operator.EQ, projectParams.getXmsbXmlx().getXmlxmc().trim());
				}
				if(projectParams.getXmsbHylb()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbHylb().getHylbmc())){
					con.add("xmsbHylb",Operator.ISNOTNULL,null);
					con.add("xmsbHylb.hylbmc", Operator.EQ, projectParams.getXmsbHylb().getHylbmc().trim());
				}
				if(projectParams.getXmsbZjxz()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbZjxz().getZjxzmc())){
					con.add("xmsbZjxz",Operator.ISNOTNULL,null);
					con.add("xmsbZjxz.zjxzmc", Operator.EQ, projectParams.getXmsbZjxz().getZjxzmc().trim());
				}
				if(projectParams.getPjstatus()!=null){
					con.add("pjstatus",Operator.ISNOTNULL,null);
					con.add("pjstatus", Operator.EQ, projectParams.getPjstatus());
				}
				if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
					con.add("projectname",Operator.ISNOTNULL,null);
					con.add("projectname", Operator.LIKE, projectParams.getProjectname());
				}
				if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
					con.add("projectcode",Operator.ISNOTNULL,null);
					con.add("projectcode", Operator.LIKE, projectParams.getProjectcode());
				}
//				if(StringUtils.isNotEmpty(projectParams.getAuditdeptname())) {
//					con.add("auditdeptname",Operator.ISNOTNULL,null);
//					con.add("auditdeptname", Operator.LIKE, projectParams.getAuditdeptname());
//				}
				if(StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
					con.add("sysOrganizationByDeclareunitsid",Operator.ISNOTNULL,null);
					con.add("sysOrganizationByDeclareunitsid.organizationName", Operator.LIKE, projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim());
				}
				if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
					con.add("nextauditdeptname",Operator.ISNOTNULL,null);
					con.add("nextauditdeptname", Operator.EQ, projectParams.getNextauditdeptname().trim());
				}
				if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
					con.add("declartime", Operator.GE, DateUtil.parse(projectParams.getBeginTime(),"yyyy-MM-dd"));
				}
				if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
					con.add("declartime", Operator.LE, DateUtil.parse(projectParams.getEndTime(),"yyyy-MM-dd"));
				}
				if(StringUtils.isNotEmpty(projectParams.getZgdw())){//主管单位
					con.add("sysOrganizationByDirectorunitsid", Operator.ISNOTNULL,null);
					con.add("sysOrganizationByDirectorunitsid.organizationName", Operator.LIKE, projectParams.getZgdw());
				}
				if(projectParams.getXmfl()!=null&&!projectParams.getXmfl().equals("")){
					con.add("xmfl",Operator.ISNOTNULL,null);
					con.add("xmfl",Operator.EQ,projectParams.getXmfl());
				}
				if(StringUtils.isNotEmpty(projectParams.getManageunitsname())) {
					con.add("manageunitsname",Operator.ISNOTNULL,null);
					con.add("manageunitsname", Operator.LIKE, projectParams.getManageunitsname());
				}
				if(StringUtils.isNotEmpty(projectParams.getProjectcontent())) {
					con.add("projectcontent",Operator.ISNOTNULL,null);
					con.add("projectcontent", Operator.LIKE, projectParams.getProjectcontent());
				}
				if(StringUtils.isNotEmpty(projectParams.getDeclaregist())) {
					con.add("declaregist",Operator.ISNOTNULL,null);
					con.add("declaregist", Operator.LIKE, projectParams.getDeclaregist());
				}
			}
		  
//		  if(params != null){
//				//预计至XX年底累计完成投资
//				if(params.getExpectFinishInvestYear()!=null && params.getExpectFinishInvestYear()>0){
//					con.add("expectfinishinvestyear", Operator.EQ, params.getExpectFinishInvestYear());
//				}
//				if(this.checkInteger(params.getExpectFinishInvest())){
//					con.add("expectfinishinvest", Operator.GE, params.getExpectFinishInvest());
//				}
//				if(this.checkInteger(params.getExpectFinishInvest2())){
//					con.add("expectfinishinvest", Operator.LE, params.getExpectFinishInvest2());
//				}
//				if(this.checkInteger(params.getExpectFinishOtherInvest())){
//					con.add("expectfinishotherinvest", Operator.GE, params.getExpectFinishOtherInvest());
//				}
//				if(this.checkInteger(params.getExpectFinishOtherInvest2())){
//					con.add("expectfinishotherinvest", Operator.LE, params.getExpectFinishOtherInvest2());
//				}
//				
//				//项目总投资
//				if(this.checkInteger(params.getPjinvestSum())){
//					con.add("pjinvestsum", Operator.GE, params.getPjinvestSum());
//				}
//				if(this.checkInteger(params.getPjinvestSum2())){
//					con.add("pjinvestsum", Operator.LE, params.getPjinvestSum2());
//				}	
//				if(this.checkInteger(params.getPjinvestCity())){
//					con.add("pjinvestcity", Operator.GE, params.getPjinvestCity());
//				}
//				if(this.checkInteger(params.getPjinvestCity2())){
//					con.add("pjinvestcity", Operator.LE, params.getPjinvestCity2());
//				}	
//				if(this.checkInteger(params.getPjinvestCompany())){
//					con.add("pjinvestcompany", Operator.GE, params.getPjinvestCompany());
//				}
//				if(this.checkInteger(params.getPjinvestCompany2())){
//					con.add("pjinvestcompany", Operator.LE, params.getPjinvestCompany2());
//				}	
//				if(this.checkInteger(params.getPjinvestBank())){
//					con.add("pjinvestbank", Operator.GE, params.getPjinvestBank());
//				}
//				if(this.checkInteger(params.getPjinvestBank2())){
//					con.add("pjinvestbank", Operator.LE, params.getPjinvestBank2());
//				}	
//				if(this.checkInteger(params.getPjinvestOther())){
//					con.add("pjinvestother", Operator.GE, params.getPjinvestOther());
//				}
//				if(this.checkInteger(params.getPjinvestOther2())){
//					con.add("pjinvestother", Operator.LE, params.getPjinvestOther2());
//				}
//				
//				//投资计划建议
//				if(params.getPlanInvestYear()!=null && params.getPlanInvestYear()>0){
//					con.add("planinvestyear", Operator.EQ, params.getPlanInvestYear());
//				}
//				if(this.checkInteger(params.getPlanInvestSum())){
//					con.add("planinvestsum", Operator.GE, params.getPlanInvestSum());
//				}
//				if(this.checkInteger(params.getPlanInvestSum2())){
//					con.add("planinvestsum", Operator.LE, params.getPlanInvestSum2());
//				}	
//				if(this.checkInteger(params.getPlanInvestCity())){
//					con.add("planinvestcity", Operator.GE, params.getPlanInvestCity());
//				}
//				if(this.checkInteger(params.getPlanInvestCity2())){
//					con.add("planinvestcity", Operator.LE, params.getPlanInvestCity2());
//				}	
//				if(this.checkInteger(params.getPlanInvestCompany())){
//					con.add("planinvestcompany", Operator.GE, params.getPlanInvestCompany());
//				}
//				if(this.checkInteger(params.getPlanInvestCompany2())){
//					con.add("planinvestcompany", Operator.LE, params.getPlanInvestCompany2());
//				}	
//				if(this.checkInteger(params.getPlanInvestBank())){
//					con.add("planinvestbank", Operator.GE, params.getPlanInvestBank());
//				}
//				if(this.checkInteger(params.getPjinvestBank2())){
//					con.add("planinvestbank", Operator.LE, params.getPlanInvestBank2());
//				}	
//				if(this.checkInteger(params.getPlanInvestOther())){
//					con.add("planinvestother", Operator.GE, params.getPlanInvestOther());
//				}
//				if(this.checkInteger(params.getPlanInvestOther2())){
//					con.add("planinvestother", Operator.LE, params.getPlanInvestOther2());
//				}	
//		  }
		  return con;
	  }
	  
	  
	  private Boolean checkInteger(Double gNum){
		  if(gNum!=null && gNum>=0){
				 return true;
		  }
		  return false;
	  }
	
//	//进入机构信息列表界面
//	@Action("list")
//	public String list(){
//		removeSession();
//		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
//		Condition con = new Condition();
//		String sqlString = " from Pjbaseinfo where 1=1 ";
//		if(projectParams != null){
//			if(projectParams.getXmsbXmlx()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbXmlx().getXmlxmc())){
//				sqlString += " and xmsbXmlx is not null and xmsbXmlx.xmlxmc = '" + projectParams.getXmsbXmlx().getXmlxmc().trim() + "'";
//			}
//			if(projectParams.getXmsbHylb()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbHylb().getHylbmc())){
//				sqlString += " and xmsbHylb is not null and xmsbHylb.hylbmc = '" + projectParams.getXmsbHylb().getHylbmc().trim()+ "'";
//			}
//			if(projectParams.getXmsbZjxz()!=null&&StringUtils.isNotEmpty(projectParams.getXmsbZjxz().getZjxzmc())){
//				sqlString += " and xmsbZjxz is not null and xmsbZjxz.zjxzmc = '" + projectParams.getXmsbZjxz().getZjxzmc().trim()+ "'";
//			}
//			if(projectParams.getPjstatus()!=null){
//				sqlString += (" and pjstatus is not null and pjstatus = ") + projectParams.getPjstatus();
//			}
//			if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
//				sqlString += " and projectname is not null and projectname like '%" + projectParams.getProjectname().trim()+ "%'";
//			}
//			if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
//				sqlString += " and projectcode is not null and projectcode like '%" + projectParams.getProjectcode().trim()+ "%'";
//			}
//			if(StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
//				sqlString += " and sysOrganizationByDeclareunitsid is not null and sysOrganizationByDeclareunitsid.organizationName like '%" + projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim()+ "%'";
//			}
//			if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
//				sqlString += " and nextauditdeptname is not null and nextauditdeptname = '" + projectParams.getNextauditdeptname().trim()+ "'";
//			}
//			if(StringUtils.isNotEmpty(projectParams.getBeginTime())){//申报时间：开始时间
//				sqlString += " and declartime >= '" + DateUtil.parse(projectParams.getBeginTime(),"yyyy-MM-dd");
//			}
//			if(StringUtils.isNotEmpty(projectParams.getEndTime())){//申报时间：结束时间
//				sqlString += " and declartime <= '" + DateUtil.parse(projectParams.getEndTime(),"yyyy-MM-dd");
//			}
//		}
//		sqlString += " order by modifiedTime desc nulls last ,pjstatus,declartime desc nulls last,projectid desc ";
//		
//		logger.info(sqlString);
//		
//		page = service.findListbySql(sqlString, page, user);
//		
//		return "success";
//	}
	
	@Action("edit")
	public String edit(){
		request.setAttribute("from",from);
		if(StringUtils.isNotEmpty(id)){
			
			obj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(id));
			if(obj!=null){
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
						else if(IConstants.FJLX_17==pjadjunct.getPjadjuncttype()){
							if(fxpg==null||fxpg.equals("")){
								fxpg = pjadjunct.getFilename();
							}
						}
						else if(IConstants.FJLX_18==pjadjunct.getPjadjuncttype()){
							if(sgys==null||sgys.equals("")){
								sgys = pjadjunct.getFilename();
							}
						}
					}
				}
			}
			else{
				getHttpSession().removeAttribute("obj");
				getHttpSession().removeAttribute("subObj");
			}
			
		}
		else{
//			obj = (Pjbaseinfo) getHttpSession().getAttribute("obj");
//			subObj = (Projectinvest) getHttpSession().getAttribute("subObj");
			obj = new Pjbaseinfo();
			subObj = new Projectinvest();
		}
		Calendar calendar = Calendar.getInstance();
		Integer nextYear = calendar.get(Calendar.YEAR)+1;
		request.setAttribute("nextYear",nextYear);
		
		return "success";
	}
	
	@Action("viewSh")
	public String viewSh(){
		removeSession();
		if(StringUtils.isNotEmpty(id)){
			
			Condition con = new Condition();
			con.add("pjbaseinfo.projectid",Operator.EQ,Integer.valueOf(id));
			Order order1 = new Order(Direction.DESC, "pjauditlogid");
			Sort sort = new Sort(order1);
			List<Pjauditlog> pjauditlogs = commonService.find(Pjauditlog.class,con, sort);
			if(pjauditlogs!=null&&!pjauditlogs.isEmpty()){
				request.setAttribute("pjauditlogs",pjauditlogs);
			}
		}
		
		return "success";
	}
	
	@Action("viewProjectlog")
	public String viewProjectlog(){
		removeSession();
		if(StringUtils.isNotEmpty(id)){
			
			Condition con = new Condition();
			con.add("pjbaseinfo.projectid",Operator.EQ,Integer.valueOf(id));
			Order order1 = new Order(Direction.DESC, "operationDatetime");
			Sort sort = new Sort(order1);
			List<SysProjectlog> sysProjectlogs = commonService.find(SysProjectlog.class,con, sort);
			List<SysProjectlogPojo> listPojo = projectlogService.getLogPojoList(sysProjectlogs);
			if(sysProjectlogs!=null&&!sysProjectlogs.isEmpty()){
				request.setAttribute("sysProjectlogs",listPojo);
			}
		}
		
		return "success";
	}
	
	@GzznLog
	@Action("restoreProject")
	public String restoreProject(){
		removeSession();
		if(StringUtils.isNotEmpty(id)){
			try{
				Pjbaseinfo delObj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(id));
				if(delObj!=null){
					delObj.setDeleted(null);
					commonService.saveOrUpdate(delObj);
					logObject = new LogObject("恢复申报项目信息，ids=" + id);
					outPutMsg(true,"恢复项目成功!");
				}
				else{
					outPutError("恢复项目失败!没有找到该项目");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				outPutError("恢复项目失败!");
				return null;
			}
		}
		else{
			outPutError("恢复项目失败!项目Id没有收到");
		}
		
		return null;
	}
	
	@Action("view")
	public String view(){
		removeSession();
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
		
		return "success";
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
	
//	@Action("getSysHyflJson")
//	public String getSysHyflJson(){
//		
//		sysHyflTreeJson = hyflService.findSysHyflTreeJson(false);
//		outPutJSON(sysHyflTreeJson);
//		return null;
//	}
//	
//	@Action("getProjectTypeJson")
//	public String getProjectTypeJson(){
//		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
//		@SuppressWarnings("unchecked")
//		List<SysDictionaryitems> list = (List<SysDictionaryitems>) getHttpSession().getAttribute(CommonFiled.SESSION_DIRECTIONARYITEMS);
//		if(list!=null&&!list.isEmpty()){
//			for(SysDictionaryitems dictionaryitems:list){
//				if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(IConstants.DICTIONARY_ITEM_XMLX)){
//					TreeNodesPojo objTree = new TreeNodesPojo();
//					objTree.setId(dictionaryitems.getItemvalue());
//					objTree.setName(dictionaryitems.getItemtext());
//					listTree.add(objTree);
//				}
//			}
//		}
//		
//		projectTypeTreeJson = new Gson().toJson(listTree);
//		outPutJSON(projectTypeTreeJson);
//		return null;
//	}
	
	
	
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
	
	@Action("getOwnerOrganizationJson")
	public String getOwnerOrganizationJson(){
		ownerOrganizationTreeJson = service.findSysOrginationJson(null);//为空表示取业主和主管单位
		outPutJSON(ownerOrganizationTreeJson);
		return null;
	}
	
	//TODO
	//这个anction的功能是不是有错，是想检索项目建设管理单位吗？
	@Action("getManageOrganizationJson")
	public String getManageOrganizationJson(){
		manageOrganizationTreeJson = service.findSysOrginationJson(IConstants.ORGAN_TYPE_2);//只取主管单位
		outPutJSON(manageOrganizationTreeJson);
		return null;
	}
	
	/**
	 * 创建指定规则的项目编码
	 * 
	 * 项目编码编码逻辑：
		项目类型（新开工项目、续建项目、预备项目）、资金性质(一般项目、基本建设统筹资金项目)、落地区县、申报时间（YYYYMMDD）、三位流水号(1-999)、奇偶校验位。
		先将这些项按顺序串联起来，得到的数转成二进制数，然后对其进行偶校验，以决定最后一位是加0还是加1，得到项目编码
		奇偶校验位：偶校验(即看二进制1的个数是否为偶数，如果是偶数，则在项目编码最后一位加0，否则最后一位加1)
		三位流水号，变量从1-999，循环赋值。每个项目都取一个流水号，值递增。
		
		2014-06-27      修改项目编码编码逻辑：
		落地区县、申报时间（YYYYMMDD）、项目类型（新开工项目、续建项目、预备项目）、资金性质(一般项目、基本建设统筹资金项目)、三位流水号(1-999)、奇偶校验位。
		先将这些项按顺序串联起来，得到的数转成二进制数，然后对其进行偶校验，以决定最后一位是加0还是加1，得到项目编码
		奇偶校验位：偶校验(即看二进制1的个数是否为偶数，如果是偶数，则在项目编码最后一位加0，否则最后一位加1)
		三位流水号，变量从1-999，循环赋值。每个项目都取一个流水号，值递增。
	 * @param lshDictionaryitems
	 * @return
	 */
	private String createProjectCode(Pjbaseinfo pjbaseinfo,SysDictionaryitems lshDictionaryitems){
		
		String projectCode = "";
		
		projectCode += pjbaseinfo.getSysXq().getXzqydm();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//jsJava1.0需要使用DateFormat对象，不要弄错就是了 
		
		Date date=new Date(); 
		
		String sbrq=sdf.format(date); 
		
		projectCode += sbrq;
		
		projectCode += pjbaseinfo.getXmsbXmlx().getXmlxdm();
		
		String zjxzdm = "0";//默认0
		
		projectCode += zjxzdm;
		
		
		
		Condition condition = new Condition();
		
		condition.add("name",Operator.ISNOTNULL,null);
		
		condition.add("name", Operator.EQ, IConstants.DICTIONARY_ITEM_LSH);
		
		if(lshDictionaryitems!=null){
			
			projectCode += lshDictionaryitems.getItemvalue();
			
			//对二进制数进行偶校验(即看1的个数是否为偶数，如果是偶数，则在项目编码最后一位加0，否则最后一位加1)
			BigInteger bigInteger = new BigInteger(projectCode);
			
			String binaryData = bigInteger.toString(2);
			
			char[] charDatas = binaryData.toCharArray();
			
			int length = charDatas.length;
			
			int count = 0;//1的个数
			
			for(int i=0;i<length;i++){
				
				if(String.valueOf(charDatas[i]).equals("1")){
					
					count++;
				}
			}
			if(count%2==0){
				
				projectCode += "0";
			}
			else{
				
				projectCode += "1";
			}
			
		}
		
		return projectCode;
	}
	
	 /**
     * 验证项目名称是否重复
     * @return
     */
	@Action("checkRepeat")
    public String checkRepeat(){
    	
    	String xmmc = request.getParameter("xmmc");
    	
    	if(id!=null&&!id.trim().equals("")){
    		
    		Condition con = new Condition();
    		
    		con.add("projectname", Operator.ISNOTNULL,null);
    		con.add("projectname", Operator.EQ,xmmc);
    		con.add("deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
    		con.add("projectid", Operator.NE,Integer.valueOf(id));
			
			List<Pjbaseinfo> psrws = (List<Pjbaseinfo>) commonService.find(Pjbaseinfo.class,con);
			
			if(psrws!=null&&!psrws.isEmpty()){
				
				outPutError("项目名称重复");
				
			}
			else{
				
				outPutMsg(true,"OK");
			}
			
    	}
    	else{
    		
    		Condition con = new Condition();
    		
    		con.add("projectname", Operator.ISNOTNULL,null);
    		con.add("projectname", Operator.EQ,xmmc);
    		con.add("deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
			
			List<Pjbaseinfo> psrws = (List<Pjbaseinfo>) commonService.find(Pjbaseinfo.class,con);
			
			
			if(psrws!=null&&!psrws.isEmpty()){
				
				outPutError("项目名称重复");
				
			}
			else{
				outPutMsg(true,"OK");
			}
    	}
    	return null;
    }
	
	/**
	 * 验证项目名称是否是重新申报过的项目名称
	 * @return
	 */
	@Action("checkRepeatProjectName")
	public String checkRepeatProjectName(){
		
		SimpleDateFormat tempSdf = new SimpleDateFormat("yyyy");
		
		String endsString = tempSdf.format(new Date());
		
		String xmmc = "";
		
		String projectid = request.getParameter("projectid");
		
		if(StringUtils.isNotEmpty(projectid)){
			
			Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(projectid));
			
			if(pjbaseinfo!=null){
				
				xmmc = pjbaseinfo.getProjectname();
				
			}
		}
		
		xmmc += "(" +(Integer.valueOf(endsString)+1) + ")"; 
		
		Condition con = new Condition();
		
		con.add("projectname", Operator.ISNOTNULL,null);
		
		con.add("projectname", Operator.EQ,xmmc);
		con.add("deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
		
		List<Pjbaseinfo> psrws = (List<Pjbaseinfo>) commonService.find(Pjbaseinfo.class,con);
		
		if(psrws!=null&&!psrws.isEmpty()){
			
			outPutError(xmmc+"已经存在，请先修改项目名称");
			
		}
		else{
			
			outPutMsg(true,"OK");
		}
			
		return null;
	}
	
	private void checkOrganization(){
		
		Condition con = new Condition();
		
		con.add("organizationName", Operator.ISNOTNULL,null);
		
		con.add("organizationName", Operator.EQ,obj.getSysOrganizationByDeclareunitsid().getOrganizationName());
		
		List<SysOrganization> organizations = (List<SysOrganization>) commonService.find(SysOrganization.class,con);
		
		if(organizations==null||organizations.isEmpty()){
			
			SysOrganization organization = new SysOrganization();
			
			organization.setOrganizationName(obj.getSysOrganizationByDeclareunitsid().getOrganizationName());
			
			organization.setWorkunitstype(IConstants.ORGAN_TYPE_1);
			
			commonService.save(organization);
			
			obj.setSysOrganizationByDeclareunitsid(organization);
		}
		
	}
	
	@GzznLog(LogType.SAVE)
	@Action(interceptorRefs={
			@InterceptorRef("defaultStack"),
			@InterceptorRef("tokenSession")
			},
			value = "tempSave", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String tempSave(){
		
		message = "保存草稿数据成功";
		
		SysUser user = (SysUser) ServletActionContext.getRequest().getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		
		try {
			
			if(obj.getPjstatus()!=null&&obj.getPjstatus().equals(IConstants.XMZT_12)){
				obj.setAuditdept(null);
				obj.setAuditdeptname(null);
				obj.setNextauditdept(null);
				obj.setNextauditdeptname(null);
				obj.setNexttacheername(null);
				obj.setRecordername(null);
				obj.setRecordertime(null);
			}
			
			if(obj.getPjstatus()==null||!obj.getPjstatus().equals(IConstants.XMZT_12)){
				obj.setPjstatus(IConstants.XMZT_0);//草稿
			}
			
			if(obj.getSysOrganizationByDeclareunitsid()==null||obj.getSysOrganizationByDeclareunitsid().getOrganizationId()==null){
				obj.setSysOrganizationByDeclareunitsid(null);
			}
			if(obj.getSysOrganizationByDirectorunitsid()==null||obj.getSysOrganizationByDirectorunitsid().getOrganizationId()==null){
				obj.setSysOrganizationByDirectorunitsid(null);
			}
			if(obj.getSysUserByNexttacheer()==null||obj.getSysUserByNexttacheer().getUserId()==null){
				obj.setSysUserByNexttacheer(null);
			}
			if(obj.getSysUserByRecorderid()==null||obj.getSysUserByRecorderid().getUserId()==null){
				obj.setSysUserByRecorderid(null);
			}
			if(obj.getXmsbHylb()==null||obj.getXmsbHylb().getHylbId()==null){
				obj.setXmsbHylb(null);
			}
			if(obj.getXmsbXmlx()==null||obj.getXmsbXmlx().getXmlxId()==null){
				obj.setXmsbXmlx(null);
			}
			if(obj.getXmsbZjxz()==null||obj.getXmsbZjxz().getZjxzId()==null){
				obj.setXmsbZjxz(null);
			}
			if(obj.getSysXq()==null||obj.getSysXq().getXqId()==null){
				obj.setSysXq(null);
			}
			
			//checkOrganization();

			if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
				subObj = obj.getProjectinvests().iterator().next();
			}
			obj.setSysOrganizationByRecordOrgan(user.getSysOrganization());
			
			obj.setXmztz(subObj.getPjinvestsum());
			
			commonService.saveOrUpdate(obj);
			
			subObj.setPjbaseinfo(obj);
			
			commonService.saveOrUpdate(subObj);
			
			
			
			uploadProcess(user,uploadLxpf,uploadLxpfFileName,uploadLxpfContentType,IConstants.FJLX_0);
			uploadProcess(user,uploadGhxz,uploadGhxzFileName,uploadGhxzContentType,IConstants.FJLX_1);
			uploadProcess(user,uploadYdys,uploadYdysFileName,uploadYdysContentType,IConstants.FJLX_2);
			uploadProcess(user,uploadHjyx,uploadHjyxFileName,uploadHjyxContentType,IConstants.FJLX_3);
			uploadProcess(user,uploadJnpg,uploadJnpgFileName,uploadJnpgContentType,IConstants.FJLX_4);
			uploadProcess(user,uploadKypf,uploadKypfFileName,uploadKypfContentType,IConstants.FJLX_5);
			uploadProcess(user,uploadCbsj,uploadCbsjFileName,uploadCbsjContentType,IConstants.FJLX_6);
			uploadProcess(user,uploadZbtb,uploadZbtbFileName,uploadZbtbContentType,IConstants.FJLX_7);
			uploadProcess(user,uploadZdcq,uploadZdcqFileName,uploadZdcqContentType,IConstants.FJLX_8);
			uploadProcess(user,uploadQtqq,uploadQtqqFileName,uploadQtqqContentType,IConstants.FJLX_9);
			uploadProcess(user,uploadSbyj,uploadSbyjFileName,uploadSbyjContentType,IConstants.FJLX_10);
			uploadProcess(user,uploadXxjd,uploadXxjdFileName,uploadXxjdContentType,IConstants.FJLX_11);
			uploadProcess(user,uploadCzwt,uploadCzwtFileName,uploadCzwtContentType,IConstants.FJLX_12);
			uploadProcess(user,uploadSmcl,uploadSmclFileName,uploadSmclContentType,IConstants.FJLX_13);
			uploadProcess(user,uploadFxpg,uploadFxpgFileName,uploadFxpgContentType,IConstants.FJLX_17);
			uploadProcess(user,uploadSgys,uploadSgysFileName,uploadSgysContentType,IConstants.FJLX_18);
			uploadProcess(user,uploadQtsx,uploadQtsxFileName,uploadQtsxContentType,IConstants.FJLX_19);
			uploadProcess(user,uploadJbqk,uploadJbqkFileName,uploadJbqkContentType,IConstants.FJLX_20);
			
//			setObjectProperty(fjList);
			
			logObject = new LogObject("申报项目信息草稿",obj.getProjectid(),obj.getProjectname(),obj);
			
			removeSession();
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存数据失败";
		}

		return "success";
	}
	

	@GzznLog(LogType.SAVE)
	@Action(interceptorRefs={
			@InterceptorRef("defaultStack"),
			@InterceptorRef("tokenSession")
	},
	value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		if(user!=null&&user.getUserType()!=null&&!user.getUserType().equals(IConstants.USER_TYPE_5)){
			try {
				Map<Integer,SysDept> deptMap = (Map<Integer, SysDept>) getHttpSession().getAttribute(CommonFiled.SESSION_DEPT_MAP);
				Map<Integer,SysXq> xqMap = (Map<Integer, SysXq>) getHttpSession().getAttribute(CommonFiled.SESSION_XQ_MAP);
				Map<Integer,XmsbXmlx> xmlxMap = (Map<Integer, XmsbXmlx>) getHttpSession().getAttribute(CommonFiled.SESSION_XMLX_MAP);
				
				
				SysDictionaryitems lshDictionaryitems = null;
				
				boolean needchangelsh = false;
				
				Pjauditlog pjauditlog = new Pjauditlog();
				
				SysXq tempXq = xqMap.get(obj.getSysXq().getXqId());
				
				obj.setSysXq(tempXq);
				
				XmsbXmlx tempXmlx = xmlxMap.get(obj.getXmsbXmlx().getXmlxId());
				
				obj.setXmsbXmlx(tempXmlx);
				
				if(obj.getPjstatus()==null||(obj.getPjstatus()!=null&&obj.getPjstatus().equals(IConstants.XMZT_0))){
					
					Condition condition = new Condition();
					
					condition.add("name",Operator.ISNOTNULL,null);
					
					condition.add("name", Operator.EQ, IConstants.DICTIONARY_ITEM_LSH);
					
					lshDictionaryitems = commonService.findOne(SysDictionaryitems.class, condition);
					
					if(obj.getProjectcode()==null||obj.getProjectcode().equals("")){
						String projectCode = createProjectCode(obj,lshDictionaryitems);
						
						obj.setProjectcode(projectCode);
						
						needchangelsh = true;
					}
				}
				
				if(obj.getSysOrganizationByDeclareunitsid()==null||obj.getSysOrganizationByDeclareunitsid().getOrganizationId()==null){
					obj.setSysOrganizationByDeclareunitsid(null);
				}
				if(obj.getSysOrganizationByDirectorunitsid()==null||obj.getSysOrganizationByDirectorunitsid().getOrganizationId()==null){
					obj.setSysOrganizationByDirectorunitsid(null);
				}
				if(obj.getSysUserByNexttacheer()==null||obj.getSysUserByNexttacheer().getUserId()==null){
					obj.setSysUserByNexttacheer(null);
				}
				if(obj.getSysUserByRecorderid()==null||obj.getSysUserByRecorderid().getUserId()==null){
					obj.setSysUserByRecorderid(null);
				}
				
				if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
					subObj = obj.getProjectinvests().iterator().next();
				}
				
				if(obj.getXmsbHylb()==null||obj.getXmsbHylb().getHylbId()==null){
					obj.setXmsbHylb(null);
				}
				if(obj.getXmsbXmlx()==null||obj.getXmsbXmlx().getXmlxId()==null){
					obj.setXmsbXmlx(null);
				}
				if(obj.getXmsbZjxz()==null||obj.getXmsbZjxz().getZjxzId()==null){
					obj.setXmsbZjxz(null);
				}
				if(obj.getSysXq()==null||obj.getSysXq().getXqId()==null){
					obj.setSysXq(null);
				}
				
				//checkOrganization();
				
				obj.setDeclartime(new Date());
				pjauditlog.setSysOrganizationByOrganizationId(obj.getSysOrganizationByDeclareunitsid());
				pjauditlog.setSysDeptByDeptId(user.getSysDept());
				if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_1)){//业主用户
					
					obj.setPjstatus(IConstants.XMZT_1);//待主管单位审核
					pjauditlog.setSysOrganizationByPjauditunits(obj.getSysOrganizationByDirectorunitsid());
					pjauditlog.setPjaudittype(IConstants.SHLX_1);//主管审核
					obj.setNextauditdept(obj.getSysOrganizationByDirectorunitsid()!=null?obj.getSysOrganizationByDirectorunitsid().getOrganizationId():null);
					obj.setNextauditdeptname(obj.getSysOrganizationByDirectorunitsid()!=null?obj.getSysOrganizationByDirectorunitsid().getOrganizationName():null);
				}
				else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位用户
					Condition condition = new Condition();
					condition.add("workunitstype", Operator.ISNOTNULL,null);
					condition.add("workunitstype", Operator.EQ,IConstants.ORGAN_TYPE_3);
					SysOrganization fgwOrganization = commonService.findOne(SysOrganization.class, condition);
					if(obj.getNextauditdept()!=null&&obj.getNextauditdept()!=0){
						pjauditlog.setSysOrganizationByPjauditunits(fgwOrganization);
						pjauditlog.setSysDeptBySenddeptid(deptMap.get(obj.getNextauditdept()));
					}
					else{
						pjauditlog.setSysOrganizationByPjauditunits(obj.getSysOrganizationByDirectorunitsid());
					}
					if(obj.getNextauditdept()!=null){
						SysDept dept = deptMap.get(obj.getNextauditdept());
						obj.setNextauditdept(dept.getDeptId());
						obj.setNextauditdeptname(dept.getDeptname());
						if(dept!=null&&dept.getFullcode()!=null&&dept.getFullcode().equals(fgwtzc)){
							obj.setPjstatus(IConstants.XMZT_6);//待各处室处长审核
							pjauditlog.setPjaudittype(IConstants.SHLX_6);//投资处长审核
						}
						else{
							obj.setPjstatus(IConstants.XMZT_2);//待各处室处长审核
							pjauditlog.setPjaudittype(IConstants.SHLX_2);//各处室处长审核
						}
					}
					else{
						obj.setNextauditdept(obj.getSysOrganizationByDirectorunitsid()!=null?obj.getSysOrganizationByDirectorunitsid().getOrganizationId():null);
						obj.setNextauditdeptname(obj.getSysOrganizationByDirectorunitsid()!=null?obj.getSysOrganizationByDirectorunitsid().getOrganizationName():null);
						obj.setPjstatus(IConstants.XMZT_1);//待各处室处长审核
						pjauditlog.setPjaudittype(IConstants.SHLX_1);//主管单位审核
					}
				}
	//			else{
	//				if(obj.getPjstatus()!=null&&!obj.getPjstatus().equals(IConstants.XMZT_12)){
	//					if(obj.getAuditdept()!=null){
	//						SysDept dept = deptMap.get(obj.getAuditdept());
	//						obj.setNextauditdept(dept.getDeptId());
	//						obj.setNextauditdeptname(dept.getDeptname());
	//						if(dept!=null&&dept.getFullcode()!=null&&dept.getFullcode().equals(fgwtzc)){
	//							obj.setPjstatus(IConstants.XMZT_6);//待各处室处长审核
	//							pjauditlog.setPjaudittype(IConstants.SHLX_6);//投资处长审核
	//						}
	//						else{
	//							obj.setPjstatus(IConstants.XMZT_2);//待各处室处长审核
	//							pjauditlog.setPjaudittype(IConstants.SHLX_2);//各处室处长审核
	//						}
	//					}
	//					else{
	//						obj.setPjstatus(IConstants.XMZT_2);//待各处室处长审核
	//						obj.setNextauditdept(obj.getSysOrganizationByDirectorunitsid()!=null?obj.getSysOrganizationByDirectorunitsid().getOrganizationId():null);
	//						obj.setNextauditdeptname(obj.getSysOrganizationByDirectorunitsid()!=null?obj.getSysOrganizationByDirectorunitsid().getOrganizationName():null);
	//						pjauditlog.setPjaudittype(IConstants.SHLX_2);//各处室处长审核
	//					}
	//				}
	//				Condition condition = new Condition();
	//				condition.add("workunitstype", Operator.ISNOTNULL,null);
	//				condition.add("workunitstype", Operator.EQ,IConstants.ORGAN_TYPE_3);
	//				SysOrganization fgwOrganization = commonService.findOne(SysOrganization.class, condition);
	//				pjauditlog.setSysOrganizationByPjauditunits(fgwOrganization);
	//				pjauditlog.setSysDeptBySenddeptid(deptMap.get(obj.getAuditdept()));
	//			}
				
				
				obj.setXmztz(subObj.getPjinvestsum());
				
//				obj.setAuditdept(null);
//				obj.setAuditdeptname(null);
				if(obj.getProjectid()!=null){
					Pjbaseinfo beforePjbaseinfo = commonService.findOne(Pjbaseinfo.class, obj.getProjectid());
					Projectinvest beforeProjectinvest = commonService.findOne(Projectinvest.class, subObj.getPjinvestid());
					
					
					ProjectlogProcess.createProjectLog(beforePjbaseinfo,obj,beforeProjectinvest,subObj,user,commonService);
	//				logObject = new LogObject("修改申报项目信息",obj.getProjectid(),obj.getProjectname(),beforePjbaseinfo,obj,"pjLog");
					
					obj.setSysOrganizationByRecordOrgan(beforePjbaseinfo.getSysOrganizationByRecordOrgan());
					
					commonService.saveOrUpdate(obj);
					
					subObj.setPjbaseinfo(obj);
					
					commonService.saveOrUpdate(subObj);
					
					pjauditlog.setPjbaseinfo(obj);
					
					commonService.save(pjauditlog);
					
					logObject = new LogObject("申报项目信息",obj.getProjectid(),obj.getProjectname(),obj);
					
					uploadFiles(user,lshDictionaryitems,needchangelsh);
				}
				else{
					Condition con = new Condition();
					
					con.add("projectname", Operator.ISNOTNULL,null);
					con.add("projectname", Operator.EQ,obj.getProjectname());
					con.add("deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
					
					List<Pjbaseinfo> pjbaseinfos = (List<Pjbaseinfo>) commonService.find(Pjbaseinfo.class,con);
					
					if(pjbaseinfos==null||pjbaseinfos.isEmpty()){
						
						obj.setSysOrganizationByRecordOrgan(user.getSysOrganization());
						
						commonService.saveOrUpdate(obj);
						
						subObj.setPjbaseinfo(obj);
						
						commonService.saveOrUpdate(subObj);
						
						pjauditlog.setPjbaseinfo(obj);
						
						commonService.save(pjauditlog);
						
						logObject = new LogObject("申报项目信息",obj.getProjectid(),obj.getProjectname(),obj);
						
						uploadFiles(user,lshDictionaryitems,needchangelsh);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				message = "保存数据失败";
			}
		}
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(interceptorRefs={
			@InterceptorRef("defaultStack"),
			@InterceptorRef("tokenSession")
	},
	value = "adminTempSave", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String adminTempSave(){
		
		message = "保存草稿数据成功";
		
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		
		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//管理员
		
			if(obj.getProjectid()!=null){
				
				try {
					
					if(obj.getPjstatus()==null){
						obj.setPjstatus(IConstants.XMZT_0);//草稿
					}
					
					if(obj.getSysOrganizationByDeclareunitsid()==null||obj.getSysOrganizationByDeclareunitsid().getOrganizationId()==null){
						obj.setSysOrganizationByDeclareunitsid(null);
					}
					if(obj.getSysOrganizationByDirectorunitsid()==null||obj.getSysOrganizationByDirectorunitsid().getOrganizationId()==null){
						obj.setSysOrganizationByDirectorunitsid(null);
					}
					if(obj.getSysUserByNexttacheer()==null||obj.getSysUserByNexttacheer().getUserId()==null){
						obj.setSysUserByNexttacheer(null);
					}
					if(obj.getSysUserByRecorderid()==null||obj.getSysUserByRecorderid().getUserId()==null){
						obj.setSysUserByRecorderid(null);
					}
					if(obj.getXmsbHylb()==null||obj.getXmsbHylb().getHylbId()==null){
						obj.setXmsbHylb(null);
					}
					if(obj.getXmsbXmlx()==null||obj.getXmsbXmlx().getXmlxId()==null){
						obj.setXmsbXmlx(null);
					}
					if(obj.getXmsbZjxz()==null||obj.getXmsbZjxz().getZjxzId()==null){
						obj.setXmsbZjxz(null);
					}
					if(obj.getSysXq()==null||obj.getSysXq().getXqId()==null){
						obj.setSysXq(null);
					}
					
					//checkOrganization();
					
					if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
						subObj = obj.getProjectinvests().iterator().next();
					}
					if(obj.getProjectid()==null){
						obj.setSysOrganizationByRecordOrgan(user.getSysOrganization());
					}
					
					obj.setXmztz(subObj.getPjinvestsum());
					
					commonService.saveOrUpdate(obj);
					
					subObj.setPjbaseinfo(obj);
					
					commonService.saveOrUpdate(subObj);
					
					
					
					uploadProcess(user,uploadLxpf,uploadLxpfFileName,uploadLxpfContentType,IConstants.FJLX_0);
					uploadProcess(user,uploadGhxz,uploadGhxzFileName,uploadGhxzContentType,IConstants.FJLX_1);
					uploadProcess(user,uploadYdys,uploadYdysFileName,uploadYdysContentType,IConstants.FJLX_2);
					uploadProcess(user,uploadHjyx,uploadHjyxFileName,uploadHjyxContentType,IConstants.FJLX_3);
					uploadProcess(user,uploadJnpg,uploadJnpgFileName,uploadJnpgContentType,IConstants.FJLX_4);
					uploadProcess(user,uploadKypf,uploadKypfFileName,uploadKypfContentType,IConstants.FJLX_5);
					uploadProcess(user,uploadCbsj,uploadCbsjFileName,uploadCbsjContentType,IConstants.FJLX_6);
					uploadProcess(user,uploadZbtb,uploadZbtbFileName,uploadZbtbContentType,IConstants.FJLX_7);
					uploadProcess(user,uploadZdcq,uploadZdcqFileName,uploadZdcqContentType,IConstants.FJLX_8);
					uploadProcess(user,uploadQtqq,uploadQtqqFileName,uploadQtqqContentType,IConstants.FJLX_9);
					uploadProcess(user,uploadSbyj,uploadSbyjFileName,uploadSbyjContentType,IConstants.FJLX_10);
					uploadProcess(user,uploadXxjd,uploadXxjdFileName,uploadXxjdContentType,IConstants.FJLX_11);
					uploadProcess(user,uploadCzwt,uploadCzwtFileName,uploadCzwtContentType,IConstants.FJLX_12);
					uploadProcess(user,uploadSmcl,uploadSmclFileName,uploadSmclContentType,IConstants.FJLX_13);
					uploadProcess(user,uploadFxpg,uploadFxpgFileName,uploadFxpgContentType,IConstants.FJLX_17);
					uploadProcess(user,uploadSgys,uploadSgysFileName,uploadSgysContentType,IConstants.FJLX_18);
					uploadProcess(user,uploadQtsx,uploadQtsxFileName,uploadQtsxContentType,IConstants.FJLX_19);
					uploadProcess(user,uploadJbqk,uploadJbqkFileName,uploadJbqkContentType,IConstants.FJLX_20);
					
		//			setObjectProperty(fjList);
					
					logObject = new LogObject("申报项目信息草稿",obj.getProjectid(),obj.getProjectname(),obj);
					
					removeSession();
					
				} catch (Exception e) {
					e.printStackTrace();
					message = "保存数据失败";
				}
			}
		}
		return "success";
	}
	

	
	@GzznLog(LogType.SAVE)
	@Action(interceptorRefs={
			@InterceptorRef("defaultStack"),
			@InterceptorRef("tokenSession")
			},
			value = "adminSave", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String adminSave(){
		message = "保存数据成功";
		try {
			Map<Integer,SysDept> deptMap = (Map<Integer, SysDept>) getHttpSession().getAttribute(CommonFiled.SESSION_DEPT_MAP);
			Map<Integer,SysXq> xqMap = (Map<Integer, SysXq>) getHttpSession().getAttribute(CommonFiled.SESSION_XQ_MAP);
			Map<Integer,XmsbXmlx> xmlxMap = (Map<Integer, XmsbXmlx>) getHttpSession().getAttribute(CommonFiled.SESSION_XMLX_MAP);
			
			SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){//管理员
			
				if(obj.getProjectid()!=null){
					SysXq tempXq = xqMap.get(obj.getSysXq().getXqId());
					
					obj.setSysXq(tempXq);
					
					XmsbXmlx tempXmlx = xmlxMap.get(obj.getXmsbXmlx().getXmlxId());
					
					obj.setXmsbXmlx(tempXmlx);
					
					if(obj.getSysOrganizationByDeclareunitsid()==null||obj.getSysOrganizationByDeclareunitsid().getOrganizationId()==null){
						obj.setSysOrganizationByDeclareunitsid(null);
					}
					if(obj.getSysOrganizationByDirectorunitsid()==null||obj.getSysOrganizationByDirectorunitsid().getOrganizationId()==null){
						obj.setSysOrganizationByDirectorunitsid(null);
					}
					if(obj.getSysUserByNexttacheer()==null||obj.getSysUserByNexttacheer().getUserId()==null){
						obj.setSysUserByNexttacheer(null);
					}
					if(obj.getSysUserByRecorderid()==null||obj.getSysUserByRecorderid().getUserId()==null){
						obj.setSysUserByRecorderid(null);
					}
					
					if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
						subObj = obj.getProjectinvests().iterator().next();
					}
					
					if(obj.getXmsbHylb()==null||obj.getXmsbHylb().getHylbId()==null){
						obj.setXmsbHylb(null);
					}
					if(obj.getXmsbXmlx()==null||obj.getXmsbXmlx().getXmlxId()==null){
						obj.setXmsbXmlx(null);
					}
					if(obj.getXmsbZjxz()==null||obj.getXmsbZjxz().getZjxzId()==null){
						obj.setXmsbZjxz(null);
					}
					if(obj.getSysXq()==null||obj.getSysXq().getXqId()==null){
						obj.setSysXq(null);
					}
					
					//checkOrganization();
					
//					if(obj.getDeclartime()==null){
//						obj.setDeclartime(new Date());
//					}
					
					Pjbaseinfo beforePjbaseinfo = commonService.findOne(Pjbaseinfo.class, obj.getProjectid());
					
					Projectinvest beforeProjectinvest = commonService.findOne(Projectinvest.class, subObj.getPjinvestid());
					
					obj.setXmztz(subObj.getPjinvestsum());
					
					ProjectlogProcess.createProjectLog(beforePjbaseinfo,obj,beforeProjectinvest,subObj,user,commonService);
					
					logObject = new LogObject("修改申报项目信息",obj.getProjectid(),obj.getProjectname(),beforePjbaseinfo,obj,"pjLog");
					
					commonService.saveOrUpdate(obj);
					
					subObj.setPjbaseinfo(obj);
					
					commonService.saveOrUpdate(subObj);
					
					uploadFiles(user,null,false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存数据失败";
		}
		
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(interceptorRefs={
			@InterceptorRef("defaultStack"),
			@InterceptorRef("tokenSession")
	},
	value = "dclSave", results = { @Result(location = "../dclxm/list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String dclSave(){
		message = "保存数据成功";
		try {
			Map<Integer,SysDept> deptMap = (Map<Integer, SysDept>) getHttpSession().getAttribute(CommonFiled.SESSION_DEPT_MAP);
			Map<Integer,SysXq> xqMap = (Map<Integer, SysXq>) getHttpSession().getAttribute(CommonFiled.SESSION_XQ_MAP);
			Map<Integer,XmsbXmlx> xmlxMap = (Map<Integer, XmsbXmlx>) getHttpSession().getAttribute(CommonFiled.SESSION_XMLX_MAP);
			
			SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			
			if(user!=null&&user.getUserType()!=null&&(user.getUserType().equals(IConstants.USER_TYPE_2)||user.getUserType().equals(IConstants.USER_TYPE_3))){//管理员
				
				if(obj.getProjectid()!=null){
					SysXq tempXq = xqMap.get(obj.getSysXq().getXqId());
					
					obj.setSysXq(tempXq);
					
					XmsbXmlx tempXmlx = xmlxMap.get(obj.getXmsbXmlx().getXmlxId());
					
					obj.setXmsbXmlx(tempXmlx);
					
					if(obj.getSysOrganizationByDeclareunitsid()==null||obj.getSysOrganizationByDeclareunitsid().getOrganizationId()==null){
						obj.setSysOrganizationByDeclareunitsid(null);
					}
					if(obj.getSysOrganizationByDirectorunitsid()==null||obj.getSysOrganizationByDirectorunitsid().getOrganizationId()==null){
						obj.setSysOrganizationByDirectorunitsid(null);
					}
					if(obj.getSysUserByNexttacheer()==null||obj.getSysUserByNexttacheer().getUserId()==null){
						obj.setSysUserByNexttacheer(null);
					}
					if(obj.getSysUserByRecorderid()==null||obj.getSysUserByRecorderid().getUserId()==null){
						obj.setSysUserByRecorderid(null);
					}
					
					if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
						subObj = obj.getProjectinvests().iterator().next();
					}
					
					if(obj.getXmsbHylb()==null||obj.getXmsbHylb().getHylbId()==null){
						obj.setXmsbHylb(null);
					}
					if(obj.getXmsbXmlx()==null||obj.getXmsbXmlx().getXmlxId()==null){
						obj.setXmsbXmlx(null);
					}
					if(obj.getXmsbZjxz()==null||obj.getXmsbZjxz().getZjxzId()==null){
						obj.setXmsbZjxz(null);
					}
					if(obj.getSysXq()==null||obj.getSysXq().getXqId()==null){
						obj.setSysXq(null);
					}
					
					//checkOrganization();
					
//					if(obj.getDeclartime()==null){
//						obj.setDeclartime(new Date());
//					}
					
					Pjbaseinfo beforePjbaseinfo = commonService.findOne(Pjbaseinfo.class, obj.getProjectid());
					
					Projectinvest beforeProjectinvest = commonService.findOne(Projectinvest.class, subObj.getPjinvestid());
					
					obj.setXmztz(subObj.getPjinvestsum());
					
					ProjectlogProcess.createProjectLog(beforePjbaseinfo,obj,beforeProjectinvest,subObj,user,commonService);
					
					logObject = new LogObject("修改申报项目信息",obj.getProjectid(),obj.getProjectname(),beforePjbaseinfo,obj,"pjLog");
					
					commonService.saveOrUpdate(obj);
					
					subObj.setPjbaseinfo(obj);
					
					commonService.saveOrUpdate(subObj);
					
					uploadFiles(user,null,false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存数据失败";
		}
		
		return "success";
	}
	
	private void uploadFiles(SysUser user,SysDictionaryitems lshDictionaryitems,boolean needchangelsh)throws Exception{
		uploadProcess(user,uploadLxpf,uploadLxpfFileName,uploadLxpfContentType,IConstants.FJLX_0);
		uploadProcess(user,uploadGhxz,uploadGhxzFileName,uploadGhxzContentType,IConstants.FJLX_1);
		uploadProcess(user,uploadYdys,uploadYdysFileName,uploadYdysContentType,IConstants.FJLX_2);
		uploadProcess(user,uploadHjyx,uploadHjyxFileName,uploadHjyxContentType,IConstants.FJLX_3);
		uploadProcess(user,uploadJnpg,uploadJnpgFileName,uploadJnpgContentType,IConstants.FJLX_4);
		uploadProcess(user,uploadKypf,uploadKypfFileName,uploadKypfContentType,IConstants.FJLX_5);
		uploadProcess(user,uploadCbsj,uploadCbsjFileName,uploadCbsjContentType,IConstants.FJLX_6);
		uploadProcess(user,uploadZbtb,uploadZbtbFileName,uploadZbtbContentType,IConstants.FJLX_7);
		uploadProcess(user,uploadZdcq,uploadZdcqFileName,uploadZdcqContentType,IConstants.FJLX_8);
		uploadProcess(user,uploadQtqq,uploadQtqqFileName,uploadQtqqContentType,IConstants.FJLX_9);
		uploadProcess(user,uploadSbyj,uploadSbyjFileName,uploadSbyjContentType,IConstants.FJLX_10);
		uploadProcess(user,uploadXxjd,uploadXxjdFileName,uploadXxjdContentType,IConstants.FJLX_11);
		uploadProcess(user,uploadCzwt,uploadCzwtFileName,uploadCzwtContentType,IConstants.FJLX_12);
		uploadProcess(user,uploadSmcl,uploadSmclFileName,uploadSmclContentType,IConstants.FJLX_13);
		uploadProcess(user,uploadFxpg,uploadFxpgFileName,uploadFxpgContentType,IConstants.FJLX_17);
		uploadProcess(user,uploadSgys,uploadSgysFileName,uploadSgysContentType,IConstants.FJLX_18);
		uploadProcess(user,uploadQtsx,uploadQtsxFileName,uploadQtsxContentType,IConstants.FJLX_19);
		uploadProcess(user,uploadJbqk,uploadJbqkFileName,uploadJbqkContentType,IConstants.FJLX_20);
		
		removeSession();
		
		if(needchangelsh&&lshDictionaryitems!=null){//更新流水号
			
			long temp = Long.valueOf(lshDictionaryitems.getItemvalue())+1;
			
			lshDictionaryitems.setItemvalue(String.valueOf(temp));
			
			commonService.saveOrUpdate(lshDictionaryitems);
			
		}
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "resave", results = { 
			@Result(name = "success", location = "./edit.jsp") }) 
	public String resave(){
		message = "保存数据成功";
		try {
			SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			
			if(StringUtils.isNotEmpty(id)){
				
				obj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(id));
				
				
				if(obj!=null){
					
					Pjbaseinfo tempObj = new Pjbaseinfo();
					
					PojoCopyUtil.copySameTypeField(obj, tempObj);
					tempObj.setXmsbHylb(obj.getXmsbHylb());
					tempObj.setXmsbXmlx(obj.getXmsbXmlx());
					tempObj.setXmsbZjxz(obj.getXmsbZjxz());
					tempObj.setSysOrganizationByDeclareunitsid(obj.getSysOrganizationByDeclareunitsid());
					tempObj.setSysOrganizationByDirectorunitsid(obj.getSysOrganizationByDirectorunitsid());
					tempObj.setSysUserByNexttacheer(obj.getSysUserByNexttacheer());
					tempObj.setSysUserByRecorderid(obj.getSysUserByRecorderid());
					tempObj.setSysXq(obj.getSysXq());
					tempObj.setProjectid(null);
					tempObj.setProjectcode(null);
					tempObj.setDeclartime(null);
					tempObj.setPjstatus(IConstants.XMZT_0);
					
//					SimpleDateFormat tempSdf = new SimpleDateFormat("yyyy");
//					
//					String endsString = tempSdf.format(new Date());
//					
//					
//					if(obj.getProjectname().length()>75){
//						tempObj.setProjectname(obj.getProjectname().substring(0,75)+"(" +(Integer.valueOf(endsString)+1) + ")");
//					}
//					else{
//						tempObj.setProjectname(obj.getProjectname()+"(" +(Integer.valueOf(endsString)+1) + ")");
//					}
					tempObj.setAuditdept(null);
					tempObj.setAuditdeptname(null);
					tempObj.setNextauditdept(null);
					tempObj.setNextauditdeptname(null);
					tempObj.setRecordername(null);
					tempObj.setRecordertime(null);
					
					tempObj.setSysOrganizationByRecordOrgan(user.getSysOrganization());
					
					if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
						
						subObj = obj.getProjectinvests().iterator().next();
						
						if(subObj!=null&&subObj.getPjinvestsum()!=null&&tempObj.getXmztz()==null){
							tempObj.setXmztz(subObj.getPjinvestsum());
						}
					}
					
					commonService.save(tempObj);
					
					id = tempObj.getProjectid().toString();
					
					if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
						
						subObj = obj.getProjectinvests().iterator().next();
					
						Projectinvest projectinvest = new Projectinvest();
						
						PojoCopyUtil.copySameTypeField(subObj, projectinvest);
						
						projectinvest.setPjbaseinfo(tempObj);
						
						projectinvest.setPjinvestid(null);
						
						commonService.save(projectinvest);
						
						subObj = projectinvest;
					}
					Condition condition = new Condition();
					condition.add("objectid",Operator.EQ,obj.getProjectid());
					pjadjuncts = commonService.find(Pjadjunct.class, condition);
					if(pjadjuncts!=null&&!pjadjuncts.isEmpty()){
						for(Pjadjunct pjadjunct:pjadjuncts){
							Pjadjunct newPjadjunct = new Pjadjunct();
							PojoCopyUtil.copySameTypeField(pjadjunct, newPjadjunct);
							newPjadjunct.setObjectid(tempObj.getProjectid());
							newPjadjunct.setPjbaseinfo(tempObj);
							newPjadjunct.setPjadjunctid(null);
							newPjadjunct.setRecorderid(user!=null?user.getUserId():null);
							newPjadjunct.setRecordername(user!=null?user.getUserName():null);
							newPjadjunct.setRecordertime(new Date());
							
//							String oldDocAliasfileName = pjadjunct.getFileurl()==null?"":pjadjunct.getFileurl().substring(pjadjunct.getFileurl().lastIndexOf("\\")+1);
//							
//							String oldYearString = sdfOnlyYear.format(obj.getDeclartime());
							
							String mypathString  = fpath;
							
//							String oldFullPathString = mypathString + oldYearString +"\\"+obj.getProjectid()+"\\";
//							
//							File oldFile = new File(oldFullPathString + oldDocAliasfileName);
							
							String oldFullPathString = pjadjunct.getFileurl();
							
							File oldFile = new File(oldFullPathString);

							String declartimeString = sdfOnlyYear.format(new Date());
							
							if(declartimeString!=null){
								mypathString += declartimeString + "\\"+tempObj.getProjectid()+"\\";
							}
							int index = pjadjunct.getFileurl().indexOf(".");
							String extend = pjadjunct.getFileurl().substring(index+1);
							Random rnd = new Random();
							int num = 100 + rnd.nextInt(900);
							String newFullPathFile = mypathString+sdfFull.format(date)+num + "." + extend;
							UploadFileUtil.uploadFile(oldFile, newFullPathFile);
							newPjadjunct.setFileurl(newFullPathFile);
							try {
								commonService.save(newPjadjunct);
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					obj = tempObj;
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = "保存数据失败";
		}
		
		return edit();
	}
	
	private void removeSession(){
		getHttpSession().removeAttribute("obj");
		getHttpSession().removeAttribute("subObj");
		
		getHttpSession().removeAttribute("lxpf");
		getHttpSession().removeAttribute("ghxz");
		getHttpSession().removeAttribute("ydys");
		getHttpSession().removeAttribute("hjyx");
		getHttpSession().removeAttribute("jnpg");
		getHttpSession().removeAttribute("kypf");
		getHttpSession().removeAttribute("cbsj");
		getHttpSession().removeAttribute("zbtb");
		getHttpSession().removeAttribute("zdcq");
		getHttpSession().removeAttribute("qtqq");
		
		getHttpSession().removeAttribute(CommonFiled.SESSION_FJ);
	}
	
	//删除申报项目信息
	@GzznLog
	@Action(interceptorRefs={@InterceptorRef("deleteStack")},value = "delete", results = { @Result(name="deleteDenied", location="deleteDenied", type = "redirectAction") })
	public void delete(){
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
				logObject = new LogObject("删除申报项目信息，ids=" + ids);
				outJsonString("{\"success\":true,\"info\":\"删除成功\"}");
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
			outJsonString("{\"success\":false,\"info\":\"删除失败\"}");
		}

	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setSysDeptTreeJson(String sysDeptTreeJson) {
		this.sysDeptTreeJson = sysDeptTreeJson;
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

//	@GzznLog
//	//上传项目前期进展附件
//	@Action(value = "fjsc", results = { @Result(location = "edit.ac", type = "redirectAction", params = {
//			"message", "${message}","encode", "true"}) })
//	public String fjsc(){
//		SysUser user = (SysUser) ServletActionContext.getRequest().getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
//		
//		List<FjObject> fjList =  (List<FjObject>) getHttpSession().getAttribute(CommonFiled.SESSION_FJ);
//		
//		if(fjList==null){
//			fjList =  new ArrayList<FjObject>();
//		}
//		
//		if(obj!=null){
//			getHttpSession().setAttribute("obj",obj);
//		}
//		if(subObj!=null){
//			getHttpSession().setAttribute("subObj",subObj);
//		}
//			
//		uploadProcess(user,uploadLxpf,uploadLxpfFileName,uploadLxpfContentType,IConstants.FJLX_0);
//		uploadProcess(user,uploadGhxz,uploadGhxzFileName,uploadGhxzContentType,IConstants.FJLX_1);
//		uploadProcess(user,uploadYdys,uploadYdysFileName,uploadYdysContentType,IConstants.FJLX_2);
//		uploadProcess(user,uploadHjyx,uploadHjyxFileName,uploadHjyxContentType,IConstants.FJLX_3);
//		uploadProcess(user,uploadJnpg,uploadJnpgFileName,uploadJnpgContentType,IConstants.FJLX_4);
//		uploadProcess(user,uploadKypf,uploadKypfFileName,uploadKypfContentType,IConstants.FJLX_5);
//		uploadProcess(user,uploadCbsj,uploadCbsjFileName,uploadCbsjContentType,IConstants.FJLX_6);
//		uploadProcess(user,uploadZbtb,uploadZbtbFileName,uploadZbtbContentType,IConstants.FJLX_7);
//		uploadProcess(user,uploadZdcq,uploadZdcqFileName,uploadZdcqContentType,IConstants.FJLX_8);
//		uploadProcess(user,uploadQtqq,uploadQtqqFileName,uploadQtqqContentType,IConstants.FJLX_9);
//		uploadProcess(user,uploadSbyj,uploadSbyjFileName,uploadSbyjContentType,IConstants.FJLX_10);
//		uploadProcess(user,uploadXxjd,uploadXxjdFileName,uploadXxjdContentType,IConstants.FJLX_11);
//		uploadProcess(user,uploadCzwt,uploadCzwtFileName,uploadCzwtContentType,IConstants.FJLX_12);
//		uploadProcess(user,uploadSmcl,uploadSmclFileName,uploadSmclContentType,IConstants.FJLX_13);
//		
////		setObjectProperty(fjList);
//		
//		return "success";
//	}
	
//	private void setObjectProperty(List<FjObject> fjList){
//		
//		getHttpSession().setAttribute("lxpf",lxpf);
//		getHttpSession().setAttribute("ghxz",ghxz);
//		getHttpSession().setAttribute("ydys",ydys);
//		getHttpSession().setAttribute("hjyx",hjyx);
//		getHttpSession().setAttribute("jnpg",jnpg);
//		getHttpSession().setAttribute("kypf",kypf);
//		getHttpSession().setAttribute("cbsj",cbsj);
//		getHttpSession().setAttribute("zbtb",zbtb);
//		getHttpSession().setAttribute("zdcq",zdcq);
//		getHttpSession().setAttribute("qtqq",qtqq);
//		
//		getHttpSession().setAttribute(CommonFiled.SESSION_FJ, fjList);
//		
//	}
	
	@GzznLog
	//删除附件
	@Action(value = "deleteFj")
	public String deleteFj(){
		
		
//		String fjlx = (String) ServletActionContext.getRequest().getParameter("fjlx");
		
		String fjId = (String) ServletActionContext.getRequest().getParameter("fjId");
		
//		if(fjlx!=null){
			
			try {
				if (StringUtils.isNotEmpty(fjId)) {
					Pjadjunct fjObj = commonService.findOne(Pjadjunct.class, Integer.parseInt(fjId));
					if(fjObj!=null){
						String fullPathFile = fjObj.getFileurl();
						if(fullPathFile!=null){
							commonService.delete(fjObj);
							File file = new File(fullPathFile);
							if(file.exists()){
								file.delete();
							}
						}
					}
					logObject = new LogObject("删除申报项目附件，ids=" + id);
					
					outPutMsg(true,"OK");
					
				}
				else{
					outPutError("附件不存在");
				}
			} catch (Exception e) {
				logger.error("",e);
				message = "删除数据失败";
				outPutError("删除附件失败");
			}
//		}
		
		return null;
	}
	
	/**下载模板*/
	@Action("downloadFile")
	public String downloadFile(){
		fileName = chTranscoding(fileName);//get方式进行中文转码
		String msg = super.doDownload(fileUrl, fileName.substring(0,fileName.lastIndexOf(".")));
		if(!msg.equalsIgnoreCase("success")){
			sendScriptMsg(msg);
		}
		return null;
	}
	
	/**设置中文转码*/
	private String chTranscoding(String chName) {
		String retName = "";
		try {
			//keyword = new String(request.getParameter("q").getBytes("iso8859-1"), "utf-8");
			retName = new String(chName.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retName;
	}

	

	private void uploadProcess(SysUser user,
			List<File> uploadFjs,List<String> uploadFjFileNames,List<String> uploadFjContentTypes,
			Integer fjlx
			){
		if(uploadFjs!=null&&!uploadFjs.isEmpty()){
			int i=0;
			for(File file:uploadFjs){
				int index = uploadFjFileNames.get(i).indexOf(".");
				String extend = uploadFjFileNames.get(i).substring(index+1);
				FjObject fjObject = new FjObject();
				long fjId = System.currentTimeMillis();
				Pjadjunct pjadjunct = new Pjadjunct();
				pjadjunct.setPjadjuncttype(fjlx);
				pjadjunct.setRecorderid(user!=null?user.getUserId():null);
				pjadjunct.setRecordername(user!=null?user.getUserName():null);
				pjadjunct.setRecordertime(new Date());
				pjadjunct.setObjectid(obj.getProjectid());
				pjadjunct.setPjbaseinfo(obj);
				String fileName = uploadFjFileNames.get(i);
				String wh = "";
				if(fjlx==IConstants.FJLX_0&&lxpf!=null&&!lxpf.equals("")){
					wh = lxpf;
				}
				else if(fjlx==IConstants.FJLX_1&&ghxz!=null&&!ghxz.equals("")){
					wh = ghxz;
				}
				else if(fjlx==IConstants.FJLX_2&&ydys!=null&&!ydys.equals("")){
					wh = ydys;
				}
				else if(fjlx==IConstants.FJLX_3&&hjyx!=null&&!hjyx.equals("")){
					wh = hjyx;
				}
				else if(fjlx==IConstants.FJLX_4&&jnpg!=null&&!jnpg.equals("")){
					wh = jnpg;
				}
				else if(fjlx==IConstants.FJLX_5&&kypf!=null&&!kypf.equals("")){
					wh = kypf;
				}
				else if(fjlx==IConstants.FJLX_6&&cbsj!=null&&!cbsj.equals("")){
					wh = cbsj;
				}
				else if(fjlx==IConstants.FJLX_7&&zbtb!=null&&!zbtb.equals("")){
					wh = zbtb;
				}
				else if(fjlx==IConstants.FJLX_8&&zdcq!=null&&!zdcq.equals("")){
					wh = zdcq;
				}
				else if(fjlx==IConstants.FJLX_9&&qtqq!=null&&!qtqq.equals("")){
					wh = qtqq;
				}
				else if(fjlx==IConstants.FJLX_17&&fxpg!=null&&!fxpg.equals("")){
					wh = fxpg;
				}
				else if(fjlx==IConstants.FJLX_18&&sgys!=null&&!sgys.equals("")){
					wh = sgys;
				}
//				if(fileName!=null&&fileName.getBytes().length>50){
//					fileName = fileName.substring(0,10)+"......"+ "." + extend;
//				}
				pjadjunct.setFilename(fileName);
				pjadjunct.setWh(wh);
				String declartimeString = pjadjunct.getPjbaseinfo().getDeclartime()==null?null:sdfOnlyYear.format(pjadjunct.getPjbaseinfo().getDeclartime());
				String mypathString  = fpath;
				if(declartimeString!=null){
					mypathString += declartimeString + "\\"+obj.getProjectid()+"\\";
				}
				Random rnd = new Random();
				int num = 100 + rnd.nextInt(900);
				String newFullPathFile = mypathString+sdfFull.format(date)+num + "." + extend;
				UploadFileUtil.uploadFile(file, newFullPathFile);
				pjadjunct.setFileurl(newFullPathFile);
				
				try {
					commonService.saveOrUpdate(pjadjunct);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
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
	
	@Action("checkFileSize")
	public String checkFileSize() throws Exception {
		if(uploadLxpf!=null&&!uploadLxpf.isEmpty()){
			for(File file:uploadLxpf){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadGhxz!=null&&!uploadGhxz.isEmpty()){
			for(File file:uploadGhxz){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadYdys!=null&&!uploadYdys.isEmpty()){
			for(File file:uploadYdys){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadHjyx!=null&&!uploadHjyx.isEmpty()){
			for(File file:uploadHjyx){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadJnpg!=null&&!uploadJnpg.isEmpty()){
			for(File file:uploadJnpg){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadKypf!=null&&!uploadKypf.isEmpty()){
			for(File file:uploadKypf){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadCbsj!=null&&!uploadCbsj.isEmpty()){
			for(File file:uploadCbsj){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadZbtb!=null&&!uploadZbtb.isEmpty()){
			for(File file:uploadZbtb){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadZdcq!=null&&!uploadZdcq.isEmpty()){
			for(File file:uploadZdcq){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadQtqq!=null&&!uploadQtqq.isEmpty()){
			for(File file:uploadQtqq){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadSbyj!=null&&!uploadSbyj.isEmpty()){
			for(File file:uploadSbyj){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadXxjd!=null&&!uploadXxjd.isEmpty()){
			for(File file:uploadXxjd){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadCzwt!=null&&!uploadCzwt.isEmpty()){
			for(File file:uploadCzwt){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadSmcl!=null&&!uploadSmcl.isEmpty()){
			for(File file:uploadSmcl){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadFxpg!=null&&!uploadFxpg.isEmpty()){
			for(File file:uploadFxpg){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadSgys!=null&&!uploadSgys.isEmpty()){
			for(File file:uploadSgys){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadQtsx!=null&&!uploadQtsx.isEmpty()){
			for(File file:uploadQtsx){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		else if(uploadJbqk!=null&&!uploadJbqk.isEmpty()){
			for(File file:uploadJbqk){
				if (file.length()>20*1024*1024) {
					outPutError("文件大小不能超过20M");
					return null;
				}
			}
		}
		outPutMsg(true,"OK");
		return null;
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

	

	

	public List<File> getUploadSbyj() {
		return uploadSbyj;
	}

	public void setUploadSbyj(List<File> uploadSbyj) {
		this.uploadSbyj = uploadSbyj;
	}

	public List<String> getUploadSbyjFileName() {
		return uploadSbyjFileName;
	}

	public void setUploadSbyjFileName(List<String> uploadSbyjFileName) {
		this.uploadSbyjFileName = uploadSbyjFileName;
	}

	public List<String> getUploadSbyjContentType() {
		return uploadSbyjContentType;
	}

	public void setUploadSbyjContentType(
			List<String> uploadSbyjContentType) {
		this.uploadSbyjContentType = uploadSbyjContentType;
	}

	public List<File> getUploadLxpf() {
		return uploadLxpf;
	}

	public void setUploadLxpf(List<File> uploadLxpf) {
		this.uploadLxpf = uploadLxpf;
	}

	public List<String> getUploadLxpfFileName() {
		return uploadLxpfFileName;
	}

	public void setUploadLxpfFileName(List<String> uploadLxpfFileName) {
		this.uploadLxpfFileName = uploadLxpfFileName;
	}

	public List<String> getUploadLxpfContentType() {
		return uploadLxpfContentType;
	}

	public void setUploadLxpfContentType(List<String> uploadLxpfContentType) {
		this.uploadLxpfContentType = uploadLxpfContentType;
	}

	public List<File> getUploadGhxz() {
		return uploadGhxz;
	}

	public void setUploadGhxz(List<File> uploadGhxz) {
		this.uploadGhxz = uploadGhxz;
	}

	public List<String> getUploadGhxzFileName() {
		return uploadGhxzFileName;
	}

	public void setUploadGhxzFileName(List<String> uploadGhxzFileName) {
		this.uploadGhxzFileName = uploadGhxzFileName;
	}

	public List<String> getUploadGhxzContentType() {
		return uploadGhxzContentType;
	}

	public void setUploadGhxzContentType(List<String> uploadGhxzContentType) {
		this.uploadGhxzContentType = uploadGhxzContentType;
	}

	public List<File> getUploadYdys() {
		return uploadYdys;
	}

	public void setUploadYdys(List<File> uploadYdys) {
		this.uploadYdys = uploadYdys;
	}

	public List<String> getUploadYdysFileName() {
		return uploadYdysFileName;
	}

	public void setUploadYdysFileName(List<String> uploadYdysFileName) {
		this.uploadYdysFileName = uploadYdysFileName;
	}

	public List<String> getUploadYdysContentType() {
		return uploadYdysContentType;
	}

	public void setUploadYdysContentType(List<String> uploadYdysContentType) {
		this.uploadYdysContentType = uploadYdysContentType;
	}

	public List<File> getUploadHjyx() {
		return uploadHjyx;
	}

	public void setUploadHjyx(List<File> uploadHjyx) {
		this.uploadHjyx = uploadHjyx;
	}

	public List<String> getUploadHjyxFileName() {
		return uploadHjyxFileName;
	}

	public void setUploadHjyxFileName(List<String> uploadHjyxFileName) {
		this.uploadHjyxFileName = uploadHjyxFileName;
	}

	public List<String> getUploadHjyxContentType() {
		return uploadHjyxContentType;
	}

	public void setUploadHjyxContentType(List<String> uploadHjyxContentType) {
		this.uploadHjyxContentType = uploadHjyxContentType;
	}

	public List<File> getUploadJnpg() {
		return uploadJnpg;
	}

	public void setUploadJnpg(List<File> uploadJnpg) {
		this.uploadJnpg = uploadJnpg;
	}

	public List<String> getUploadJnpgFileName() {
		return uploadJnpgFileName;
	}

	public void setUploadJnpgFileName(List<String> uploadJnpgFileName) {
		this.uploadJnpgFileName = uploadJnpgFileName;
	}

	public List<String> getUploadJnpgContentType() {
		return uploadJnpgContentType;
	}

	public void setUploadJnpgContentType(List<String> uploadJnpgContentType) {
		this.uploadJnpgContentType = uploadJnpgContentType;
	}

	public List<File> getUploadKypf() {
		return uploadKypf;
	}

	public void setUploadKypf(List<File> uploadKypf) {
		this.uploadKypf = uploadKypf;
	}

	public List<String> getUploadKypfFileName() {
		return uploadKypfFileName;
	}

	public void setUploadKypfFileName(List<String> uploadKypfFileName) {
		this.uploadKypfFileName = uploadKypfFileName;
	}

	public List<String> getUploadKypfContentType() {
		return uploadKypfContentType;
	}

	public void setUploadKypfContentType(List<String> uploadKypfContentType) {
		this.uploadKypfContentType = uploadKypfContentType;
	}

	public List<File> getUploadCbsj() {
		return uploadCbsj;
	}

	public void setUploadCbsj(List<File> uploadCbsj) {
		this.uploadCbsj = uploadCbsj;
	}

	public List<String> getUploadCbsjFileName() {
		return uploadCbsjFileName;
	}

	public void setUploadCbsjFileName(List<String> uploadCbsjFileName) {
		this.uploadCbsjFileName = uploadCbsjFileName;
	}

	public List<String> getUploadCbsjContentType() {
		return uploadCbsjContentType;
	}

	public void setUploadCbsjContentType(List<String> uploadCbsjContentType) {
		this.uploadCbsjContentType = uploadCbsjContentType;
	}

	public List<File> getUploadZbtb() {
		return uploadZbtb;
	}

	public void setUploadZbtb(List<File> uploadZbtb) {
		this.uploadZbtb = uploadZbtb;
	}

	public List<String> getUploadZbtbFileName() {
		return uploadZbtbFileName;
	}

	public void setUploadZbtbFileName(List<String> uploadZbtbFileName) {
		this.uploadZbtbFileName = uploadZbtbFileName;
	}

	public List<String> getUploadZbtbContentType() {
		return uploadZbtbContentType;
	}

	public void setUploadZbtbContentType(List<String> uploadZbtbContentType) {
		this.uploadZbtbContentType = uploadZbtbContentType;
	}

	public List<File> getUploadZdcq() {
		return uploadZdcq;
	}

	public void setUploadZdcq(List<File> uploadZdcq) {
		this.uploadZdcq = uploadZdcq;
	}

	public List<String> getUploadZdcqFileName() {
		return uploadZdcqFileName;
	}

	public void setUploadZdcqFileName(List<String> uploadZdcqFileName) {
		this.uploadZdcqFileName = uploadZdcqFileName;
	}

	public List<String> getUploadZdcqContentType() {
		return uploadZdcqContentType;
	}

	public void setUploadZdcqContentType(List<String> uploadZdcqContentType) {
		this.uploadZdcqContentType = uploadZdcqContentType;
	}

	public List<File> getUploadQtqq() {
		return uploadQtqq;
	}

	public void setUploadQtqq(List<File> uploadQtqq) {
		this.uploadQtqq = uploadQtqq;
	}

	public List<String> getUploadQtqqFileName() {
		return uploadQtqqFileName;
	}

	public void setUploadQtqqFileName(List<String> uploadQtqqFileName) {
		this.uploadQtqqFileName = uploadQtqqFileName;
	}

	public List<String> getUploadQtqqContentType() {
		return uploadQtqqContentType;
	}

	public void setUploadQtqqContentType(List<String> uploadQtqqContentType) {
		this.uploadQtqqContentType = uploadQtqqContentType;
	}

	public List<File> getUploadXxjd() {
		return uploadXxjd;
	}

	public void setUploadXxjd(List<File> uploadXxjd) {
		this.uploadXxjd = uploadXxjd;
	}

	public List<String> getUploadXxjdFileName() {
		return uploadXxjdFileName;
	}

	public void setUploadXxjdFileName(List<String> uploadXxjdFileName) {
		this.uploadXxjdFileName = uploadXxjdFileName;
	}

	public List<String> getUploadXxjdContentType() {
		return uploadXxjdContentType;
	}

	public void setUploadXxjdContentType(List<String> uploadXxjdContentType) {
		this.uploadXxjdContentType = uploadXxjdContentType;
	}

	public List<File> getUploadCzwt() {
		return uploadCzwt;
	}

	public void setUploadCzwt(List<File> uploadCzwt) {
		this.uploadCzwt = uploadCzwt;
	}

	public List<String> getUploadCzwtFileName() {
		return uploadCzwtFileName;
	}

	public void setUploadCzwtFileName(List<String> uploadCzwtFileName) {
		this.uploadCzwtFileName = uploadCzwtFileName;
	}

	public List<String> getUploadCzwtContentType() {
		return uploadCzwtContentType;
	}

	public void setUploadCzwtContentType(List<String> uploadCzwtContentType) {
		this.uploadCzwtContentType = uploadCzwtContentType;
	}

	public List<File> getUploadSmcl() {
		return uploadSmcl;
	}

	public void setUploadSmcl(List<File> uploadSmcl) {
		this.uploadSmcl = uploadSmcl;
	}

	public List<String> getUploadSmclFileName() {
		return uploadSmclFileName;
	}

	public void setUploadSmclFileName(List<String> uploadSmclFileName) {
		this.uploadSmclFileName = uploadSmclFileName;
	}

	public List<String> getUploadSmclContentType() {
		return uploadSmclContentType;
	}

	public void setUploadSmclContentType(List<String> uploadSmclContentType) {
		this.uploadSmclContentType = uploadSmclContentType;
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

	public String getFxpg() {
		return fxpg;
	}

	public void setFxpg(String fxpg) {
		this.fxpg = fxpg;
	}

	public String getSgys() {
		return sgys;
	}

	public void setSgys(String sgys) {
		this.sgys = sgys;
	}

	public List<File> getUploadFxpg() {
		return uploadFxpg;
	}

	public void setUploadFxpg(List<File> uploadFxpg) {
		this.uploadFxpg = uploadFxpg;
	}

	public List<String> getUploadFxpgFileName() {
		return uploadFxpgFileName;
	}

	public void setUploadFxpgFileName(List<String> uploadFxpgFileName) {
		this.uploadFxpgFileName = uploadFxpgFileName;
	}

	public List<String> getUploadFxpgContentType() {
		return uploadFxpgContentType;
	}

	public void setUploadFxpgContentType(List<String> uploadFxpgContentType) {
		this.uploadFxpgContentType = uploadFxpgContentType;
	}

	public List<File> getUploadSgys() {
		return uploadSgys;
	}

	public void setUploadSgys(List<File> uploadSgys) {
		this.uploadSgys = uploadSgys;
	}

	public List<String> getUploadSgysFileName() {
		return uploadSgysFileName;
	}

	public void setUploadSgysFileName(List<String> uploadSgysFileName) {
		this.uploadSgysFileName = uploadSgysFileName;
	}

	public List<String> getUploadSgysContentType() {
		return uploadSgysContentType;
	}

	public void setUploadSgysContentType(List<String> uploadSgysContentType) {
		this.uploadSgysContentType = uploadSgysContentType;
	}

	public List<File> getUploadQtsx() {
		return uploadQtsx;
	}

	public void setUploadQtsx(List<File> uploadQtsx) {
		this.uploadQtsx = uploadQtsx;
	}

	public List<String> getUploadQtsxFileName() {
		return uploadQtsxFileName;
	}

	public void setUploadQtsxFileName(List<String> uploadQtsxFileName) {
		this.uploadQtsxFileName = uploadQtsxFileName;
	}

	public List<String> getUploadQtsxContentType() {
		return uploadQtsxContentType;
	}

	public void setUploadQtsxContentType(List<String> uploadQtsxContentType) {
		this.uploadQtsxContentType = uploadQtsxContentType;
	}

	public List<File> getUploadJbqk() {
		return uploadJbqk;
	}

	public void setUploadJbqk(List<File> uploadJbqk) {
		this.uploadJbqk = uploadJbqk;
	}

	public List<String> getUploadJbqkFileName() {
		return uploadJbqkFileName;
	}

	public void setUploadJbqkFileName(List<String> uploadJbqkFileName) {
		this.uploadJbqkFileName = uploadJbqkFileName;
	}

	public List<String> getUploadJbqkContentType() {
		return uploadJbqkContentType;
	}

	public void setUploadJbqkContentType(List<String> uploadJbqkContentType) {
		this.uploadJbqkContentType = uploadJbqkContentType;
	}

	public String getYearTreeJson() {
		return yearTreeJson;
	}

	public void setYearTreeJson(String yearTreeJson) {
		this.yearTreeJson = yearTreeJson;
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

	public String getManageOrganizationTreeJson() {
		return manageOrganizationTreeJson;
	}

	public void setManageOrganizationTreeJson(String manageOrganizationTreeJson) {
		this.manageOrganizationTreeJson = manageOrganizationTreeJson;
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
		return sdfFull;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdfFull = sdf;
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

	public String getSysDeptTreeJson() {
		return sysDeptTreeJson;
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

	public List<Pjadjunct> getPjadjuncts() {
		return pjadjuncts;
	}

	public void setPjadjuncts(List<Pjadjunct> pjadjuncts) {
		this.pjadjuncts = pjadjuncts;
	}

//	private void createProjectLog(Pjbaseinfo beforeSourceObj,Pjbaseinfo afterSourceObj,Projectinvest beforeProjectinvest,Projectinvest afterProjectinvest){
//		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
//		SysProjectlog projectlog = new SysProjectlog();
//		if(user.getSysOrganization()!=null){
//			projectlog.setSysOrganization(user.getSysOrganization());
//		}
//		if (user.getSysDept() != null) {
//			projectlog.setSysDept(user.getSysDept());
//		}
//			Method[] srcMethods = beforeSourceObj.getClass().getMethods();
//			Method[] srcMethods2 = beforeProjectinvest.getClass().getMethods();
//			Map record = new HashMap();
//			for (Method mm : srcMethods) {
//				String name = mm.getName();// 方法名称
//				// 过滤掉目标对象的set方法
//				if (name.startsWith("set")) {
//					continue;
//				}
//				// 找到源对象中对应的get方法
//				if (name.startsWith("get") && prop.containsKey(name.substring(3).toUpperCase())) {
//					try {
//						
//						Object o1 = mm.invoke(beforeSourceObj);
//						Object o2 = mm.invoke(afterSourceObj);
//						if(!isEqualObject(o1,o2)){
//							if(o1!=null&&o2!=null){
//								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
//										String value = "修改前值为："+o1+" \n修改后值为："+o2;;
//										record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof Date){
//									String value = "修改前值为："+sdf.format((Date)o1)+" \n修改后值为："+sdf.format((Date)o2);
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysOrganization){
//									String value = "修改前值为："+((SysOrganization)o1).getOrganizationName()+" \n修改后值为："+((SysOrganization)o2).getOrganizationName();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysUser){
//									String value = "修改前值为："+((SysUser)o1).getUserName()+" \n修改后值为："+((SysUser)o2).getUserName();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysXq){
//									String value = "修改前值为："+((SysXq)o1).getXqmc()+" \n修改后值为："+((SysXq)o2).getXqmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbHylb){
//									String value = "修改前值为："+((XmsbHylb)o1).getHylbmc()+" \n修改后值为："+((XmsbHylb)o2).getHylbmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbXmlx){
//									String value = "修改前值为："+((XmsbXmlx)o1).getXmlxmc()+" \n修改后值为："+((XmsbXmlx)o2).getXmlxmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbZjxz){
//									String value = "修改前值为："+((XmsbZjxz)o1).getZjxzmc()+" \n修改后值为："+((XmsbZjxz)o2).getZjxzmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//							}
//							else if(o1!=null&&o2==null){
//								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
//									String value = "修改前值为："+o1+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof Date){
//									String value = "修改前值为："+sdf.format((Date)o1)+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysOrganization){
//									String value = "修改前值为："+((SysOrganization)o1).getOrganizationName()+" \n修改后值为："+((SysOrganization)o2).getOrganizationName();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysUser){
//									String value = "修改前值为："+((SysUser)o1).getUserName()+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysXq){
//									String value = "修改前值为："+((SysXq)o1).getXqmc()+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbHylb){
//									String value = "修改前值为："+((XmsbHylb)o1).getHylbmc()+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbXmlx){
//									String value = "修改前值为："+((XmsbXmlx)o1).getXmlxmc()+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbZjxz){
//									String value = "修改前值为："+((XmsbZjxz)o1).getZjxzmc()+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//							}
//							else if(o1==null&&o2!=null){
//								if(o2 instanceof String || o2 instanceof Integer || o2 instanceof Double){
//									String value = "修改前值为：  \n修改后值为："+o2;
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof Date){
//									String value = "修改前值为：  \n修改后值为："+sdf.format((Date)o2);
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysOrganization){
//									String value = "修改前值为：  \n修改后值为："+((SysOrganization)o2).getOrganizationName();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysUser){
//									String value = "修改前值为：  \n修改后值为："+((SysUser)o2).getUserName();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof SysXq){
//									String value = "修改前值为：  \n修改后值为："+((SysXq)o2).getXqmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbHylb){
//									String value = "修改前值为：  \n修改后值为："+((XmsbHylb)o2).getHylbmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbXmlx){
//									String value = "修改前值为：  \n修改后值为："+((XmsbXmlx)o2).getXmlxmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//								else if(o1 instanceof XmsbZjxz){
//									String value = "修改前值为：  \n修改后值为："+((XmsbZjxz)o2).getZjxzmc();
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//							}
//							
//						}
//						
//					} catch (Exception e) {
//						//e.printStackTrace();
//					}
//				}
//			}
//	        
//			for (Method mm : srcMethods2) {
//				String name = mm.getName();// 方法名称
//				// 过滤掉目标对象的set方法
//				if (name.startsWith("set")) {
//					continue;
//				}
//				// 找到源对象中对应的get方法
//				if (name.startsWith("get") && prop.containsKey(name.substring(3).toUpperCase())) {
//					try {
//						
//						Object o1 = mm.invoke(beforeProjectinvest);
//						Object o2 = mm.invoke(afterProjectinvest);
//						if(!isEqualObject(o1,o2)){
//							if(o1!=null&&o2!=null){
//								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
//										String value = "修改前值为："+o1+" \n修改后值为："+o2;;
//										record.put(name.substring(3).toUpperCase(), value);
//								}
//							}
//							else if(o1!=null&&o2==null){
//								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
//									String value = "修改前值为："+o1+" \n修改后值为：";
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//							}
//							else if(o1==null&&o2!=null){
//								if(o2 instanceof String || o2 instanceof Integer || o2 instanceof Double){
//									String value = "修改前值为：  \n修改后值为："+o2;
//									record.put(name.substring(3).toUpperCase(), value);
//								}
//							}
//							
//						}
//						
//					} catch (Exception e) {
//						//e.printStackTrace();
//					}
//				}
//			}
//			
//	        String contentString = "申报项目基本信息的修改情况：\n";
//	        Iterator iterator = record.keySet().iterator();
//	        while (iterator.hasNext()) {
//				Object key = (Object) iterator.next();
//				Object value = record.get(key);
////				System.out.println(prop.getProperty(key.toString())+" "+value);
//				contentString+="\n" + prop.getProperty(key.toString())+"\n";
//				contentString+=value+"\n";
//			}
//	        
//	        try {
//				projectlog.setOperationContent(contentString.getBytes("UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		projectlog.setSysUser(user);
//		projectlog.setOperationDatetime(new Date());//new Timestamp(System.currentTimeMillis())
//		projectlog.setPjbaseinfo(beforeSourceObj);
//		commonService.save(projectlog);
//	}
//
//	private boolean isEqualObject(Object obj1,Object obj2){
//		boolean b = false;
//		if(obj1==null&&obj2!=null){
//			return b;
//		}
//		else if(obj1!=null&&obj2==null){
//			return b;
//		}
//		else if(obj1==null&&obj2==null){
//			b = true;
//		}
//		else{
//			if(obj1 instanceof SysOrganization){
//				
//				if(((SysOrganization)obj1).getOrganizationId().equals(((SysOrganization)obj2).getOrganizationId())){
//					b = true;
//				}
//			}
//			else if(obj1 instanceof SysUser){
//				if(((SysUser)obj1).getUserId().equals(((SysUser)obj2).getUserId())){
//					b = true;
//				}
//			}
//			else if(obj1 instanceof SysXq){
//				if(((SysXq)obj1).getXqId().equals(((SysXq)obj2).getXqId())){
//					b = true;
//				}
//			}
//			else if(obj1 instanceof XmsbHylb){
//				if(((XmsbHylb)obj1).getHylbId().equals(((XmsbHylb)obj2).getHylbId())){
//					b = true;
//				}
//			}
//			else if(obj1 instanceof XmsbXmlx){
//				if(((XmsbXmlx)obj1).getXmlxId().equals(((XmsbXmlx)obj2).getXmlxId())){
//					b = true;
//				}
//			}
//			else if(obj1 instanceof XmsbZjxz){
//				if(((XmsbZjxz)obj1).getZjxzId().equals(((XmsbZjxz)obj2).getZjxzId())){
//					b = true;
//				}
//			}
//			else if(obj1 instanceof String){
//				if(((String)obj1).trim().equals(((String)obj2).trim())){
//					b = true;
//				}
//			}
//			else{
//				if(obj1.equals(obj2)){
//					b = true;
//				}
//			}
//		}
//		return b;
//	}
	
	public Map<Integer, String> getPjaudittypeMap() {
		pjaudittypeMap = putDictionaryitemsToMap(pjaudittypeMap, IConstants.DICTIONARY_ITEM_XMSHLX);
		return pjaudittypeMap;
	}


	public void setPjaudittypeMap(Map<Integer, String> pjaudittypeMap) {
		this.pjaudittypeMap = pjaudittypeMap;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
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
		 SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		 Condition con = addParamsCond();
		 Sort sort = addSort();
//		 List<Pjbaseinfo> listData = commonService.find(Pjbaseinfo.class, con, sort);

		String middle = getQuerySql();
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.modifiedTime desc,pjstatus,declartime desc,projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		 List<PjbaseinfoPojo> list = service.findList(countSql,objSql,getOrganMap());
		 
		 downloadFileName =	downFileNameTranscode("广州市政府投资项目库申报单位项目汇总表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 excelFile = new ProjectbaseinfoListExpExcel().expExcelTempFile(getLoginUser(), list, getXmztMap(), expExcelFilePath+"pjbaseExp.xls", 5);
		 
		 logger.info("导出广州市政府投资项目库申报单位项目汇总表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		 return "excel";
	}
	
	
	
	/**LHQ加入：项目信息管理列表 导出报表功能*/
	@Action(value = "expOneExcel", results = { @Result(name="excel", type="stream",
		  params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String expOneExcel(){
		 long begin = System.currentTimeMillis();
		 getPjRelatedInfoById();//根据项目ID获取单个项目的相关信息
		 downloadFileName =	downFileNameTranscode("广州市政府投资项目申报表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 excelFile = new ProjectbaseinfoExpExcel().expExcelTempFile(obj, subObj, getHttpSession(), pjadjuncts, expExcelFilePath+"pjdeclExp.xls");
			 
		 logger.info("导出广州市政府投资项目申报表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		 return "excel";
	}

	/**
	 * 根据项目ID获取单个项目的相关信息
	 */
	private void getPjRelatedInfoById() {
		if (StringUtils.isNotEmpty(id)) {
			obj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(id));
			if (obj != null) {
				if (obj.getProjectinvests() != null
						&& !obj.getProjectinvests().isEmpty()) {
					subObj = obj.getProjectinvests().iterator().next();
				}
				if (obj != null) {
					getHttpSession().setAttribute("obj", obj);
				}
				if (subObj != null) {
					getHttpSession().setAttribute("subObj", subObj);
				}
				Condition condition = new Condition();
				condition.add("objectid", Operator.EQ, obj.getProjectid());
				pjadjuncts = commonService.find(Pjadjunct.class, condition);// 与该项目相关的附件信息
				if (pjadjuncts != null && !pjadjuncts.isEmpty()) {
					for (Pjadjunct pjadjunct : pjadjuncts) {
						if (IConstants.FJLX_0 == pjadjunct.getPjadjuncttype()) {
							if (lxpf == null || lxpf.equals("")) {
								lxpf = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_1 == pjadjunct.getPjadjuncttype()) {
							if (ghxz == null || ghxz.equals("")) {
								ghxz = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_2 == pjadjunct.getPjadjuncttype()) {
							if (ydys == null || ydys.equals("")) {
								ydys = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_3 == pjadjunct.getPjadjuncttype()) {
							if (hjyx == null || hjyx.equals("")) {
								hjyx = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_4 == pjadjunct.getPjadjuncttype()) {
							if (jnpg == null || jnpg.equals("")) {
								jnpg = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_5 == pjadjunct.getPjadjuncttype()) {
							if (kypf == null || kypf.equals("")) {
								kypf = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_6 == pjadjunct.getPjadjuncttype()) {
							if (cbsj == null || cbsj.equals("")) {
								cbsj = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_7 == pjadjunct.getPjadjuncttype()) {
							if (zbtb == null || zbtb.equals("")) {
								zbtb = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_8 == pjadjunct.getPjadjuncttype()) {
							if (zdcq == null || zdcq.equals("")) {
								zdcq = pjadjunct.getFilename();
							}
						} else if (IConstants.FJLX_9 == pjadjunct.getPjadjuncttype()) {
							if (qtqq == null || qtqq.equals("")) {
								qtqq = pjadjunct.getFilename();
							}
						}
					}
				}
			} else {
				getHttpSession().removeAttribute("obj");
				getHttpSession().removeAttribute("subObj");
			}
		} else {
			//obj = (Pjbaseinfo) getHttpSession().getAttribute("obj");
			//subObj = (Projectinvest) getHttpSession().getAttribute("subObj");
			obj = new Pjbaseinfo();
			subObj = new Projectinvest();
		}
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

	public Boolean getMoreSearchBool() {
		return moreSearchBool;
	}

	public void setMoreSearchBool(Boolean moreSearchBool) {
		this.moreSearchBool = moreSearchBool;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	
	
	
}
