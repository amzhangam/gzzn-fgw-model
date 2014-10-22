package com.gzzn.fgw.action.project;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.expExcel.BudgetOrganProjectListExpExcel;
import com.gzzn.fgw.expExcel.ProjectbaseinfoListExpExcel;
import com.gzzn.fgw.model.BudgetOrgan;
import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjauditlog;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.ProjectinvestSh;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.ReportPojo;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.project.IProjectbaseinfoService;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.service.project.pojo.ProjectbaseinfoParam;
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
import com.gzzn.fgw.webUtil.ProjectlogProcess;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;


/**
 * 
 * <p>Title: BudgetOrganProjectAction</p>
 * <p>Description:  预算单位项目汇总 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-26 amzhang  new
 */
@Namespace(value = "/project/ysdwxm")
public class BudgetOrganProjectAction extends BaseAction<PjbaseinfoPojo> {
	protected static Logger logger = LoggerFactory.getLogger(BudgetOrganProjectAction.class);
	
//	private Double pjinvestcity;
//	private Double planinvestcity;
//	private String pjauditmind;
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
	private ProjectinvestSh subObjSh;
	private String message;//返回页面信息
	private Pjauditlog pjauditlog;
	private SysOrganization sysProcessOrganization;
	private PageUtil<PjbaseinfoPojo> page = new PageUtil<PjbaseinfoPojo>();
	private Map<Integer,String> sblxMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmlxMap = new HashMap<Integer, String>();
	private Map<Integer,String> ndMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();
	private Map<Integer,String> xmflMap = new HashMap<Integer, String>();
	private Map<Integer,String> organMap = new HashMap<Integer, String>();
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
	private String fxpg;
	private String sgys;
	
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

		String middle = getQuerySql();
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.xmfl,xmsbXmlx, xmsbHylb,t.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		 List<PjbaseinfoPojo> list = service.findList(countSql,objSql,getOrganMap());
		 
		 setDownloadFileName(downFileNameTranscode("广州市预算单位项目汇总表__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		 setExcelFile(new BudgetOrganProjectListExpExcel().expExcelTempFile(getLoginUser(), list, 
				 getXmflMap(),getOrganMap(), expExcelFilePath+"budgetOrganProjectExp.xls", 1));
		 
		 logger.info("导出广州市预算单位项目汇总表，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		 return "excel";
	}

	private Map<Integer,String> getXmflMap(){
		Map<Integer,String> xmflMap = new HashMap<Integer, String>();
		xmflMap.put(IConstants.XMFL_1,IConstants.XMFL_NAME_1);
		xmflMap.put(IConstants.XMFL_2,IConstants.XMFL_NAME_2);
//		xmflMap.put(IConstants.XMFL_3,IConstants.XMFL_NAME_3);
		return xmflMap;
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
		String middle = getQuerySql();
		String objBefore = " from Pjbaseinfo t where 1=1 ";
		String objEnd = " order by t.xmfl,xmsbXmlx, xmsbHylb,t.projectid desc ";
		
		String objSql = objBefore + middle + objEnd;
		
		String countBefore = " select count(*) from Pjbaseinfo t where 1=1 ";
		
		String countSql = countBefore + middle;
		
		page.setSize(15);
		
		page = service.findList(page,countSql, objSql,getOrganMap());
		
		request.setAttribute("xmflMap",getXmflMap());
		
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
				if(StringUtils.isNotEmpty(projectParams.getProjectname())) {
					sqlString += " and t.projectname is not null and t.projectname like '%" + projectParams.getProjectname().trim() + "%' ";
				}
				if(StringUtils.isNotEmpty(projectParams.getProjectcode())) {
					sqlString += " and t.projectcode is not null and t.projectcode like '%" + projectParams.getProjectcode().trim() + "%' ";
				}
				if(projectParams.getSysOrganizationByDeclareunitsid()!=null&&StringUtils.isNotEmpty(projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName())) {
					sqlString += " and t.sysOrganizationByDeclareunitsid is not null and t.sysOrganizationByDeclareunitsid.organizationName like '%" + projectParams.getSysOrganizationByDeclareunitsid().getOrganizationName().trim() + "%' ";
				}
				if(projectParams.getXmsbXmlx()!=null&&projectParams.getXmsbXmlx().getXmlxId()!=null) {
					if(projectParams.getXmsbXmlx().getXmlxId()==2){//新建项目
						sqlString += " and t.xmsbXmlx is not null and ((t.xmsbXmlx.xmlxId >=2 and t.xmsbXmlx.xmlxId <=4)or (t.xmsbXmlx.xmlxId >=7 and t.xmsbXmlx.xmlxId <=10))" ;
					}
					else{//续建项目
						sqlString += " and t.xmsbXmlx is not null and t.xmsbXmlx.xmlxId = 6";
					}
					
				}
				if(StringUtils.isNotEmpty(projectParams.getZgdw())){//主管单位
					sqlString += " and t.sysOrganizationByDirectorunitsid is not null and t.sysOrganizationByDirectorunitsid.organizationName like '%" + projectParams.getZgdw().trim() + "%' ";
				}
				if(projectParams.getXmfl()!=null&&!projectParams.getXmfl().equals("")){
					sqlString += " and t.xmfl is not null and t.xmfl = " + projectParams.getXmfl();
				}
				if(projectParams.getExpectfinishinvestType()!=null&&!projectParams.getExpectfinishinvestType().equals("")){//项目总投资
					
					if(projectParams.getExpectfinishinvestType().equals(IConstants.SHENHE_EXPECTFINISHINVEST_TYPE_1)){//等于0或没有输入的
						sqlString += " and (t.pjinvestcitySh is null or t.pjinvestcitySh <= 0)";
					}
					else if(projectParams.getExpectfinishinvestType().equals(IConstants.SHENHE_EXPECTFINISHINVEST_TYPE_2)){//大于0
						sqlString += " and (t.pjinvestcitySh is not null and t.pjinvestcitySh >0)";
					}
					else if(projectParams.getExpectfinishinvestType().equals(IConstants.SHENHE_EXPECTFINISHINVEST_TYPE_3)){
						sqlString += " and t.pjinvestcitySh is not null and t.pjinvestcitySh>0 and t.pjinvestcitySh < " + IConstants.XMZTZ;
					}
					else{
						sqlString += " and t.pjinvestcitySh is not null and t.pjinvestcitySh >= " + IConstants.XMZTZ;
					}
				}
				if(StringUtils.isNotEmpty(projectParams.getNextauditdeptname())) {
					sqlString += " and t.pjauditdept is not null and t.pjauditdept like '%" + projectParams.getNextauditdeptname().trim() + "%' ";
				}
			}
		  
		  if(user!=null&&user.getUserType()!=null&&
				  (
						  (
								  user.getUserType().equals(IConstants.USER_TYPE_3)
								  //&&user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null&&user.getSysDept().getFullcode().equalsIgnoreCase(fgwtzc)
						  )
						  ||user.getUserType().equals(IConstants.USER_TYPE_5)
				  )){//发改委用户和管理员可以看
			  
//			  sqlString += " and t.sysOrganizationByDeclareunitsid is not null and t.sysOrganizationByDeclareunitsid.organizationId " + 
//			  " in (select b.sysOrganization.organizationId from BudgetOrgan b where b.id.organStatus is not null and b.id.organStatus='有效')";
		  }
		  else{//其他人员啥都不能看
			  sqlString += " and 2=3";
		  }
		  
//		  sqlString += " and t.xmsbXmlx is not null and ((t.xmsbXmlx.xmlxId >=2 and t.xmsbXmlx.xmlxId <=4)or (t.xmsbXmlx.xmlxId >=7 and t.xmsbXmlx.xmlxId <=10) or (t.xmsbXmlx.xmlxId = 6))" ;
//		  
//		  sqlString += " and t.xmfl is not null and (t.xmfl = 1 or t.xmfl = 2) ";
		  
		  sqlString += " and(t.deleted is null or t.deleted = 0)";
		  
		  sqlString += " and t.projectcode not like 'PJ%' ";
		  
		  sqlString += " and t.pjstatus>="+IConstants.XMZT_2+" and t.pjstatus<="+IConstants.XMZT_10;
		  
		  return sqlString;
	  }
	  
	  @Action("pjauditmind")
		public String pjauditmind(){
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
					
					if(obj.getProjectinvestShs()!=null&&!obj.getProjectinvestShs().isEmpty()){
						subObjSh = obj.getProjectinvestShs().iterator().next();
					}
					if(subObjSh!=null){
						getHttpSession().setAttribute("subObjSh",subObjSh);
					}
					else{
						subObjSh = new ProjectinvestSh();
						PojoCopyUtil.copySameTypeField(subObj,subObjSh);
						subObjSh.setPjinvestcitySh(0d);
						subObjSh.setPjinvestsumSh(subObj.getPjinvestsum());
						subObjSh.setPlaninvestcitySh(0d);
						subObjSh.setPlaninvestsumSh(subObj.getPlaninvestsum());
						subObjSh.setPjinvestidSh(null);
						subObjSh.setPjbaseinfo(subObj.getPjbaseinfo());
						getHttpSession().setAttribute("subObjSh",subObjSh);
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
					getHttpSession().removeAttribute("subObjSh");
				}
				
			}
			else{
				obj = new Pjbaseinfo();
				subObj = new Projectinvest();
				subObjSh = new ProjectinvestSh();
			}
			Calendar calendar = Calendar.getInstance();
			Integer nextYear = calendar.get(Calendar.YEAR)+1;
			request.setAttribute("nextYear",nextYear);
			
			return "success";
		}
	
	  @GzznLog
		@Action(value = "setPjauditmind")
		public String setPjauditmind() throws Exception{
			SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			try{
				if (StringUtils.isNotEmpty(id)) {
						
						Pjbaseinfo pjbaseinfo = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(id));
						
						if(obj!=null){
							if(obj.getProjectinvests()!=null&&!obj.getProjectinvests().isEmpty()){
								subObj = obj.getProjectinvests().iterator().next();
							}
						}
						
						pjbaseinfo.setPjinvestcitySh(subObjSh.getPjinvestcitySh());
						
						Pjbaseinfo beforePjbaseinfo = new Pjbaseinfo();
						
						ProjectinvestSh beforeProjectinvestSh = new ProjectinvestSh();
						
						ProjectinvestSh projectinvestSh = (pjbaseinfo!=null&&pjbaseinfo.getProjectinvestShs()!=null&&!pjbaseinfo.getProjectinvestShs().isEmpty())?pjbaseinfo.getProjectinvestShs().iterator().next():new ProjectinvestSh();
						
						Projectinvest beforeProjectinvest = (pjbaseinfo!=null&&pjbaseinfo.getProjectinvests()!=null&&!pjbaseinfo.getProjectinvests().isEmpty())?pjbaseinfo.getProjectinvests().iterator().next():new Projectinvest();
						
						if(projectinvestSh.getPjinvestidSh()==null){
							
							PojoCopyUtil.copySameTypeField(beforeProjectinvest,projectinvestSh);
							projectinvestSh.setPjinvestcitySh(beforeProjectinvest.getPjinvestcity());
							projectinvestSh.setPjinvestsumSh(beforeProjectinvest.getPjinvestsum());
							projectinvestSh.setPlaninvestcitySh(beforeProjectinvest.getPlaninvestcity());
							projectinvestSh.setPlaninvestsumSh(beforeProjectinvest.getPlaninvestsum());
							projectinvestSh.setPjinvestidSh(null);
							
							PojoCopyUtil.copySameTypeField(beforeProjectinvest,beforeProjectinvestSh);
							beforeProjectinvestSh.setPjinvestcitySh(beforeProjectinvest.getPjinvestcity());
							beforeProjectinvestSh.setPjinvestsumSh(beforeProjectinvest.getPjinvestsum());
							beforeProjectinvestSh.setPlaninvestcitySh(beforeProjectinvest.getPlaninvestcity());
							beforeProjectinvestSh.setPlaninvestsumSh(beforeProjectinvest.getPlaninvestsum());
							beforeProjectinvestSh.setPjinvestidSh(null);
							
						}
						else{
							
							PojoCopyUtil.copySameTypeField(projectinvestSh,beforeProjectinvestSh);
						}
						
						projectinvestSh.setPjauditmind(subObjSh.getPjauditmind());
						
						projectinvestSh.setPjinvestcitySh(subObjSh.getPjinvestcitySh());
						
						projectinvestSh.setPlaninvestcitySh(subObjSh.getPlaninvestcitySh());
						
						Double tempPjinvestsum =0.00d;
						
						Double tempPlaninvestsum =0.00d;
						
						if(projectinvestSh.getPjinvestsumSh()==null){
							
							tempPjinvestsum = subObjSh.getPjinvestcitySh()!=null?subObjSh.getPjinvestcitySh():0.00d;
							
							tempPlaninvestsum = subObjSh.getPlaninvestcitySh()!=null?subObjSh.getPlaninvestcitySh():0.00d;
							
						}
						else{
							Double tempNewPjinvestcity = subObjSh.getPjinvestcitySh()!=null?subObjSh.getPjinvestcitySh():0.00d;
							
							Double tempOldPjinvestcity = beforeProjectinvestSh.getPjinvestcitySh()!=null?beforeProjectinvestSh.getPjinvestcitySh():0.00d;
							
							Double tempNewPlaninvestcity = subObjSh.getPlaninvestcitySh()!=null?subObjSh.getPlaninvestcitySh():0.00d;
							
							Double tempOldPlaninvestcity = beforeProjectinvestSh.getPlaninvestcitySh()!=null?beforeProjectinvestSh.getPlaninvestcitySh():0.00d;
							
							tempPjinvestsum = projectinvestSh.getPjinvestsumSh()+(tempNewPjinvestcity-tempOldPjinvestcity); 
							
							tempPlaninvestsum = (projectinvestSh.getPlaninvestsumSh()!=null?projectinvestSh.getPlaninvestsumSh():0.00d)+(tempNewPlaninvestcity-tempOldPlaninvestcity); 
						}
						
						
						projectinvestSh.setPjinvestsumSh(tempPjinvestsum);
						
						projectinvestSh.setPlaninvestsumSh(tempPlaninvestsum);
								
						
						PojoCopyUtil.copySameTypeField(pjbaseinfo,beforePjbaseinfo);
						
						
						Date pjaudittime = new Date();
						
						projectinvestSh.setPjauditdept(user.getSysDept()!=null?user.getSysDept().getDeptname():null);
						
						projectinvestSh.setPjaudituser(user.getUserName());
						
						projectinvestSh.setPjaudittime(pjaudittime);
						
						pjbaseinfo.setPjauditdept(user.getSysDept()!=null?user.getSysDept().getDeptname():null);
						
						pjbaseinfo.setPjaudituser(user.getUserName());
						
						pjbaseinfo.setPjaudittime(pjaudittime);
						
						commonService.saveOrUpdate(pjbaseinfo);
						
						commonService.saveOrUpdate(projectinvestSh);
						
						logObject = new LogObject((user.getSysDept()!=null?user.getSysDept().getDeptname():"")+user.getUserName()+"将项目"+pjbaseinfo.getProjectname()+
								"市财政资金总投资审核为"+(projectinvestSh.getPjinvestcitySh()==null?"0万元":(projectinvestSh.getPjinvestcitySh()+"万元，")) + 
								"市财政资金"+beforeProjectinvest.getPlaninvestyear()+"年度投资计划审核为"+(projectinvestSh.getPlaninvestcitySh()==null?"0万元":(projectinvestSh.getPlaninvestcitySh()+"万元"))
								);
						
						String contentString = "审核市财政资金总投资，由"+(beforeProjectinvestSh.getPjinvestcitySh()==null?"0万元":(beforeProjectinvestSh.getPjinvestcitySh()+"万元"))+"审核修改为：" + 
						
						(projectinvestSh.getPjinvestcitySh()==null?"0万元":(projectinvestSh.getPjinvestcitySh()+"万元")) + 
						
						"市财政资金"+beforeProjectinvest.getPlaninvestyear()+"年度投资计划，由"+(beforeProjectinvestSh.getPlaninvestcitySh()==null?"0万元":(beforeProjectinvestSh.getPlaninvestcitySh()+"万元"))+"审核修改为：" + 
						
						(projectinvestSh.getPlaninvestcitySh()==null?"0万元":(projectinvestSh.getPlaninvestcitySh()+"万元"));
						
						saveProjectlog(user,pjbaseinfo,contentString);
						
				}
//				outPutMsg(true, "设置项目储备类别成功！");
				
				outPutMsg(true, "审核意见保存成功");
			}
			catch(Exception e){
				outPutError("操作失败！");
				e.printStackTrace();
				throw e;
			}
			getHttpSession().removeAttribute("obj");
			getHttpSession().removeAttribute("subObj");
			getHttpSession().removeAttribute("subObjSh");
			return null;
		}
	  
	  private void saveProjectlog(SysUser user,Pjbaseinfo beforeSourceObj,String contentString){
		  
		  SysProjectlog projectlog = new SysProjectlog();
			if(user.getSysOrganization()!=null){
				projectlog.setSysOrganization(user.getSysOrganization());
			}
			if (user.getSysDept() != null) {
				projectlog.setSysDept(user.getSysDept());
			}
			try {
				projectlog.setOperationContent(contentString.getBytes("UTF-8"));
				projectlog.setSysUser(user);
				projectlog.setOperationDatetime(new Date());//new Timestamp(System.currentTimeMillis())
				projectlog.setPjbaseinfo(beforeSourceObj);
				commonService.save(projectlog);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
//		@Action("getProjectStatusJson")
//		public String getProjectStatusJson(){
//			List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
//			TreeNodesPojo objTree1 = new TreeNodesPojo();
//			objTree1.setId(IConstants.XMXZT_1+"");
//			objTree1.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree1);
//			TreeNodesPojo objTree2 = new TreeNodesPojo();
//			objTree2.setId(IConstants.XMXZT_1+"");
//			objTree2.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree2);
//			TreeNodesPojo objTree3 = new TreeNodesPojo();
//			objTree3.setId(IConstants.XMXZT_1+"");
//			objTree3.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree3);
//			TreeNodesPojo objTree4 = new TreeNodesPojo();
//			objTree4.setId(IConstants.XMXZT_1+"");
//			objTree4.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree4);
//			TreeNodesPojo objTree5 = new TreeNodesPojo();
//			objTree5.setId(IConstants.XMXZT_1+"");
//			objTree5.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree5);
//			TreeNodesPojo objTree6 = new TreeNodesPojo();
//			objTree6.setId(IConstants.XMXZT_1+"");
//			objTree6.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree6);
//			TreeNodesPojo objTree7 = new TreeNodesPojo();
//			objTree7.setId(IConstants.XMXZT_1+"");
//			objTree7.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree7);
//			TreeNodesPojo objTree8 = new TreeNodesPojo();
//			objTree8.setId(IConstants.XMXZT_1+"");
//			objTree8.setName(IConstants.XMXZT_1_NAME);
//			listTree.add(objTree8);
//			
//			projectStatusTreeJson = new Gson().toJson(listTree);
//			outPutJSON(projectStatusTreeJson);
//			return null;
//		}
	
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

	public ProjectinvestSh getSubObjSh() {
		return subObjSh;
	}

	public void setSubObjSh(ProjectinvestSh subObjSh) {
		this.subObjSh = subObjSh;
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

	public void setXmflMap(Map<Integer, String> xmflMap) {
		this.xmflMap = xmflMap;
	}

	public void setOrganMap(Map<Integer, String> organMap) {
		this.organMap = organMap;
	}
	
}
