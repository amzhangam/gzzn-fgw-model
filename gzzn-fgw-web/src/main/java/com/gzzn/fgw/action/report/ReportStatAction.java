package com.gzzn.fgw.action.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
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
import com.gzzn.fgw.expExcel.BudgetOrganProjectListExpExcel;
import com.gzzn.fgw.expExcel.DeptProjectListExpExcel;
import com.gzzn.fgw.expExcel.DeptProjectStatExpExcel;
import com.gzzn.fgw.expExcel.ReportStatExpExcel;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.ReportPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.report.IPjauditlogService;
import com.gzzn.fgw.service.report.IReportService;
import com.gzzn.fgw.service.sys.pojo.CsxmPojo;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.PropertiesUtil;

import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;
/**
 * 报表统计
 * <p/>
 * <i>Copyright (c) 2014 ITDCL  All right reserved.</i> 
 * 
 * @author lhq
 * @version 1.0
 *
 * 修改记录:(日期+修改内容)<br/>
 * ——2014-05-04 lhq  new
 */
@Namespace("/report")
public class ReportStatAction extends BaseAction<ReportPojo> {	
	private static Logger logger = LoggerFactory.getLogger(ReportStatAction.class);
	
	/*基本属性*/
	private ReportPojo params;//用于存储查询参数	
	private Boolean moreSearchBool=false;//显示更多查询条件... 是否显示
	private PageUtil<Projectinvest> page=new PageUtil<Projectinvest>();//页面数据集合
	private Map<Integer,String> xmlxMap = new HashMap<Integer, String>();//项目类型（值：新开工项目、续建项目、预备项目）
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();//项目状态（值：草稿、待处长审核等）
	private InputStream excelFile;//excel导出文件流
	private String downloadFileName;//excel导出文件名称
	private String expExcelFilePath = getWebRootPath() + "/upload/pjbaseinfo/";//导出Excel文件的路径
	 
	/*业务操作类*/
	@Autowired
	private IReportService reportService;	
	@Resource 
	IPjauditlogService pjauditlogService;
	@Autowired
	private ICommonService commonService;
	private String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty(
			"fgwtzc.fullcode");
	
	
	/**导出报表功能*/
	@SuppressWarnings("unchecked")
	@Action(value = "expExcel", results = { @Result(name="excel", type="stream",
		  params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String expExcel(){
		 long begin = System.currentTimeMillis();
		 String name = "统计报表";
		 List<Projectinvest> expdata = new ArrayList<Projectinvest>();
		 
		 if(params!=null){
			 if(params.getReportType().equalsIgnoreCase("1")){//1-申报项目计划报表
				 name = "申报项目计划报表";
			 }else if(params.getReportType().equalsIgnoreCase("2")){//2-业主/主管单位提交项目计划报表
				 name = "业主（主管单位）提交项目计划报表"; 
			 }else if(params.getReportType().equalsIgnoreCase("3")){//3-审核通过报表
				 name = "审核通过报表";
			 }else if(params.getReportType().equalsIgnoreCase("4")){//4-审核不通过报表
				 name = "审核不通过报表";
			 }else if(params.getReportType().equalsIgnoreCase("5")){//5-项目申请汇总报表
				 name = "项目申请汇总报表";
			 }else if(params.getReportType().equalsIgnoreCase("6")){//6-项目管理：正式项目库
				 name = "正式项目库";
			 }
		 }
		 if(params!=null && params.getReportType().equalsIgnoreCase("5")){//5-项目申请汇总报表
			Map<String, Object> map = this.getSqhzReportList();
			if(map.get("flag").equals(true)){
				expdata = (List<Projectinvest>) map.get("listData");
			}
		 }else{
			 Map<String, Object> map = this.initCon();
			 if(map.get("flag").equals(true)){//满足相关条件，查询数据库且返回相关数据给用户
				 Order order1 = new Order(Direction.DESC, "pjbaseinfo.startyear");
				 Order order2 = new Order(Direction.DESC, "pjbaseinfo.endyear");
				 Sort sort = new Sort(order1, order2);
				 expdata = reportService.findStatList((Condition)map.get("cond"), sort);
			 }
		 }
		 //setDownloadFileName(JxlExcelCellUtil.setExcelFileName(name+"__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		 setDownloadFileName(downFileNameTranscode(name+"__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		 setExcelFile(new ReportStatExpExcel().expExcelFile(expdata, getXmztMap(), params));
		 
		 logger.info("导出{}，耗损时间为：{} 毫秒", name, System.currentTimeMillis() - begin);
		 return "excel";
	}
	
	/**导出各处室项目情况统计报表功能*/
//	@SuppressWarnings("unchecked")
	@Action(value = "expDeptProjectExcel", results = { @Result(name="excel", type="stream",
	params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String expDeptProjectExcel(){
		long begin = System.currentTimeMillis();
		String name = "各处室项目情况统计报表";
//		List<CsxmPojo> expdata = getDeptProjectStat();
		List<CsxmPojo> expdata = (List<CsxmPojo>) request.getSession().getAttribute("csxmPojos");
		//setDownloadFileName(JxlExcelCellUtil.setExcelFileName(name+"__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		setDownloadFileName(downFileNameTranscode(name+"__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls"));
		setExcelFile(new DeptProjectStatExpExcel().expExcelFile(expdata));
		
		 setExcelFile(new DeptProjectListExpExcel().expExcelTempFile(getLoginUser(), expdata, 
				 expExcelFilePath+"deptProjectExp.xls", 4));
		logger.info("导出{}，耗损时间为：{} 毫秒", name, System.currentTimeMillis() - begin);
		
		return "excel";
	}

	
	/**
	 * 申报计划项目报表 统计  
	 * @return
	 */
	@Action("sbReport")
	public String sbReport(){
		long begin = System.currentTimeMillis();
		
		if(params==null){
			params = new ReportPojo();
			params.setReportType("1");
		}
		this.getReportPageList();
		
		logger.info("申报计划项目报表 统计【可查询用户类型1,2】，耗损时间为：{} 毫秒 ， 当前用户类型={} ",
				System.currentTimeMillis() - begin, getLoginUser().getUserType());
		return "success";
	}
	
	/**
	 * 业主/主管单位提交项目计划报表
	 * @return
	 */
	@Action("tjReport")
	public String tjReport(){
		long begin = System.currentTimeMillis();
		
		if(params==null){
			params = new ReportPojo();
			params.setReportType("2");
		}
		this.getReportPageList();
		
		logger.info("业主/主管单位提交项目计划报表 统计【可查询用户类型2,3】，耗损时间为：{} 毫秒 ， 当前用户类型={} ",
				System.currentTimeMillis() - begin, getLoginUser().getUserType());
		return "success";
	}
	
	/**
	 * 审核通过报表
	 * @return
	 */
	@Action("passReport")
	public String passReport(){
		long begin = System.currentTimeMillis();
		
		if(params==null){
			params = new ReportPojo();
			params.setReportType("3");
		}
		this.getReportPageList();
		
		logger.info("审核通过报表  统计【可查询用户类型2,3】，耗损时间为：{} 毫秒 ， 当前用户类型={} ",
				System.currentTimeMillis() - begin, getLoginUser().getUserType());
		return "success";
	}
	
//	/**
//	 * 项目管理：正式项目库
//	 * @return
//	 */
//	@Action(value = "pjPlanReport", results = { 
//			@Result(name = "success", location = "./../project/pjplanreport/list.jsp") }) 
//	public String itemYearReport(){
//		long begin = System.currentTimeMillis();
//		
//		if(params==null){
//			params = new ReportPojo();
//			params.setReportType("6");
//		}
//		this.getReportPageList();
//		
//		logger.info("查询正式项目库列表 ，耗损时间为：{} 毫秒  ", System.currentTimeMillis() - begin);
//		return "success";
//	}
	
	/**
	 * 项目管理：正式项目库
	 * @return
	 */
	@Action(value = "zsxmk", results = { 
			@Result(name = "success", location = "./../project/zsxmk/list.jsp") }) 
	public String zsxmkReport(){
		long begin = System.currentTimeMillis();
		
		if(params==null){
			params = new ReportPojo();
			params.setReportType("6");
		}
		this.getReportPageList();
		
		logger.info("查询正式项目库列表 ，耗损时间为：{} 毫秒  ", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**
	 * 项目管理：正式项目库
	 * @return
	 */
	@Action(value = "zbxmk", results = { 
			@Result(name = "success", location = "./../project/zbxmk/list.jsp") }) 
	public String zbxmkReport(){
		long begin = System.currentTimeMillis();
		
		if(params==null){
			params = new ReportPojo();
			params.setReportType("7");
		}
		this.getReportPageList();
		
		logger.info("查询储备项目库列表 ，耗损时间为：{} 毫秒  ", System.currentTimeMillis() - begin);
		return "success";
	}
	
	/**
	 * 审核不通过报表
	 * @return
	 */
	@Action("notPassReport")
	public String notPassReport(){
		long begin = System.currentTimeMillis();
		
		if(params==null){
			params = new ReportPojo();
			params.setReportType("4");
		}
		this.getReportPageList();
		
		logger.info("审核不通过报表  统计【可查询用户类型2,3】，耗损时间为：{} 毫秒 ， 当前用户类型={} ",
				System.currentTimeMillis() - begin, getLoginUser().getUserType());
		return "success";
	}
	
	/**
	 * 各处室项目情况统计报表
	 * @return
	 */
	@Action("gcsxmtjReport")
	public String gcsxmtjReport(){
		
		List<CsxmPojo> csxmPojos = getDeptProjectStat();
		
		request.setAttribute("csxmPojos",csxmPojos);
		request.getSession().setAttribute("csxmPojos",csxmPojos);
		
		return "success";
	}
	
	private List<CsxmPojo> getDeptProjectStat(){
		long begin = System.currentTimeMillis();
		Map<Integer,SysDept> deptMap = (Map<Integer, SysDept>) getHttpSession().getAttribute(CommonFiled.SESSION_DEPT_MAP);
		String unPassSql = " from Pjbaseinfo t where (t.pjstatus=2 or t.pjstatus=3 or t.pjstatus=5) and(t.deleted is null or t.deleted = 0) and t.declartime is not null " +
				"and (" +
				"(t.declartime>to_date('2014-07-01 00:00:00','yyyy-MM-dd HH24:mi:ss'))or(to_char(t.declartime,'yyyy-MM-dd')='2014-07-01'))" ;//待各处室处长审核或确认审核
		unPassSql += " and t.projectcode is not null and t.projectcode not like 'PJ%' ";
		unPassSql += " and(t.deleted is null or t.deleted = 0)";
		String passSql = " from Pjauditlog t where t.recordertime is not null and t.pjauditresult is not null and t.pjauditresult='1'" +
				" and t.pjaudittype is not null and (t.pjaudittype=2 or t.pjaudittype=5) and t.sysDeptBySenddeptid is not null " +
				" and t.sysDeptBySenddeptid.deptId not in(12,22,25) and t.pjbaseinfo.pjstatus not in(2,3,5)" ;//各处室至少审核通过过一次
		passSql += " and t.pjbaseinfo.projectcode is not null and t.pjbaseinfo.projectcode not like 'PJ%' ";
		passSql += " and(t.pjbaseinfo.deleted is null or t.pjbaseinfo.deleted = 0)";
		List<CsxmPojo> csxmPojos = reportService.findList(unPassSql,passSql, deptMap, fgwtzc);
		
		logger.info("各处室项目情况统计报表，耗损时间为：{} 毫秒   ",
		System.currentTimeMillis() - begin);
		return csxmPojos;
	}
	
	
	
	
	/**获取报表页面查询数据*/
	private void getReportPageList(){
		Map<String, Object> map = this.initCon();
		if(map.get("flag").equals(true)){//满足相关条件，查询数据库且返回相关数据给用户
			 //Sort sort = new Sort(Direction.DESC,"pjbaseinfo.startyear");
			 Order order1 = new Order(Direction.DESC, "pjbaseinfo.startyear");
			 Order order2 = new Order(Direction.DESC, "pjbaseinfo.endyear");
			 Sort sort = new Sort(order1, order2);
			 reportService.findStatPageList(page, (Condition)map.get("cond"), sort);
		}
	}
	
	/**
	 * 项目申请汇总报表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("sqhzReport")
	public String sqhzReport(){
		long begin = System.currentTimeMillis();
		
		if(params==null){
			params = new ReportPojo();
			params.setReportType("5");
		}
		List<Projectinvest> list = new ArrayList<Projectinvest>();
		Map<String, Object> map = this.getSqhzReportList();
		if(map.get("flag").equals(true)){//满足相关条件，查询数据库且返回相关数据给用户
			list = (List<Projectinvest>) map.get("listData");
			page.setCount(list.size());
			page.setList(list.subList(page.getOffset(), page.getEndIndex()));
		 }
		
		logger.info("项目申请汇总报表  统计【可查询用户类型2】，耗损时间为：{} 毫秒 ， 当前用户类型={} ",
				System.currentTimeMillis() - begin, getLoginUser().getUserType());
		return "success";
	}
	
	/**
	 * 获取 5-项目申请汇总报表 的数据List
	 * @return
	 */
	private Map<String, Object> getSqhzReportList(){
		List<Projectinvest> list = new ArrayList<Projectinvest>();
		Map<String, Object> map = this.initCon();
		if(map.get("flag").equals(true)){//满足相关条件，查询数据库且返回相关数据给用户
			 //A类情况数据：A、业主申报项目的主管单位ID为本单位ID（当前登陆的主管单位）
			 list = commonService.find(Projectinvest.class, (Condition)map.get("cond"));
			 
			 //B类情况数据：B、主管单位申报项目的申报单位ID为本单位ID（当前登陆的主管单位）
			 Condition cond2=new Condition();
			 cond2.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationId"
					   , Operator.EQ, getLoginUser().getSysOrganization().getOrganizationId()); 
			 cond2.add("pjbaseinfo.pjstatus", Operator.GT, 1);//项目状态大于1且不=11（过期）
			 cond2.add("pjbaseinfo.pjstatus", Operator.NE, 11);
			 cond2.add("pjbaseinfo.deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
			 list.addAll(commonService.find(Projectinvest.class, this.addParamsCond(cond2)));
			
			 //对list进行排序处理
			 Collections.sort(list, new Comparator<Projectinvest>(){    
	                public int compare(Projectinvest arg0, Projectinvest arg1) { 
	                	int a = arg1.getPjbaseinfo().getStartyear().compareTo(arg0.getPjbaseinfo().getStartyear());
	                	//先按startyear降序后再按endyear降序
	                	if(a!=0){
	                		return a;
	                	}else{
	                		return arg1.getPjbaseinfo().getEndyear().compareTo(arg0.getPjbaseinfo().getEndyear());
	                	}
	                 }    
	         }); 
		}
		map.put("listData", list);
		return map;
	}
	
	
	/**
	 * 所有条件都是针对com.gzzn.fgw.model.Projectinvest实体类,
	 * 查询条件初始化
	 * @return
	 */
	  private Map<String, Object> initCon(){
		  Boolean flag = false;//是否要进行库表查询
		  Condition cond=new Condition();
		  SysUser user=getLoginUser();	
		  
		  //logger.info("报表类型：1-申报项目计划报表  ；2-业主/主管单位提交项目计划报表；3-审核通过报表；4-审核不通过报表；5-项目申请汇总报表，当前报表类型={}",params.getReportType());
		  //logger.info("用户类型：1-业主；2-主管单位；3-发改委各部门，当前用户类型={}",user.getUserType());
		  
		  if(params.getReportType().equalsIgnoreCase("1")){//1-申报项目计划报表
			  if(user.getUserType().equals(IConstants.USER_TYPE_1) || user.getUserType().equals(IConstants.USER_TYPE_2)){//1-业主、2-主管单位【DECLAREUNITSID 申报单位ID】
				  //申报单位ID（业主申报——业主ID、主管单位申报——主管单位ID）
				  cond.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationId"
						   , Operator.EQ, user.getSysOrganization().getOrganizationId());
				  //项目状态不为草稿
				  cond.add("pjbaseinfo.pjstatus", Operator.NE, 0);
				  
				  flag = true;
			  }
		  }else if(params.getReportType().equalsIgnoreCase("2")){//2-业主/主管单位提交项目计划报表【DirectorUnitsId 主管单位ID】
			  if(user.getUserType().equals(IConstants.USER_TYPE_2)){//2-主管单位：业主将信息提交给主管单位
				  //业主申报信息时选择的主管单位ID
				  cond.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId"
						   , Operator.EQ, user.getSysOrganization().getOrganizationId()); 
				  /** 项目状态=1 待主管单位审核
				  cond.add("pjbaseinfo.pjstatus", Operator.EQ, 1);*/
				  //项目状态大于1且不=11（过期）
				  cond.add("pjbaseinfo.pjstatus", Operator.GT, 1);
				  cond.add("pjbaseinfo.pjstatus", Operator.NE, 11);
				  
				  /**PJAUDITTYPE项目审核类型=1（主管审核）*/
				  Condition cond2=new Condition();
				  cond2.add("pjaudittype", Operator.EQ, 1);
				  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);
				  
				  this.addProjectIdsIn(projectIds, cond);//在日志表中查找相关记录
				  
				  flag = true;
			  }else if(user.getUserType().equals(IConstants.USER_TYPE_3)){//3-发改委各部门：主管单位将信息提交给发改委
				  //项目状态大于1且不=11（过期）
				  cond.add("pjbaseinfo.pjstatus", Operator.GT, 1);
				  cond.add("pjbaseinfo.pjstatus", Operator.NE, 11);
				  
				  /**项目审计日志表中发送单位ID[SENDDEPTID]=user.getSysDept().getDeptId()*/
				  Condition cond2=new Condition();
				  cond2.add("sysDeptBySenddeptid",Operator.ISNOTNULL,null);
				  cond2.add("sysDeptBySenddeptid.deptId",Operator.EQ,12);
				  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);
				  
				  this.addProjectIdsIn(projectIds, cond);//在日志表中查找相关记录
				  
				  flag = true;
			  }
		  }else if(params.getReportType().equalsIgnoreCase("3")){//3-审核通过报表
			  if(user.getUserType().equals(IConstants.USER_TYPE_2)){//2-主管单位：DIRECTORUNITSID 主管单位ID
				  cond.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId"
						   , Operator.EQ, user.getSysOrganization().getOrganizationId()); 
				  //项目状态大于1的项目计划报表
				  cond.add("pjbaseinfo.pjstatus", Operator.GT, 1);
				  
				  flag = true;
			  }else if(user.getUserType().equals(IConstants.USER_TYPE_3) && user.getSysDept().getDeptId()!=12){//3-发改委非投资处部门：SYS_DEPT 部门ID表
				  //项目状态大于5且不=11（过期）
				  cond.add("pjbaseinfo.pjstatus", Operator.GT, 5);
				  cond.add("pjbaseinfo.pjstatus", Operator.NE, 11);
				  
				  /**项目审计日志表中发送单位ID[SENDDEPTID]是12（发改委投资处）user.getSysDept().getDeptId()*/
				  Condition cond2=new Condition();
				  cond2.add("sysDeptBySenddeptid",Operator.ISNOTNULL,null);
				  cond2.add("sysDeptBySenddeptid.deptId",Operator.EQ,12);
				  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);

				  this.addProjectIdsIn(projectIds, cond);//在日志表中查找相关记录
				  
				  flag = true;
			  }
		  }else if(params.getReportType().equalsIgnoreCase("4")){//4-审核不通过报表
			  if(user.getUserType().equals(IConstants.USER_TYPE_2)){//2-主管单位：DIRECTORUNITSID 主管单位ID
				  cond.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId"
						   , Operator.EQ, user.getSysOrganization().getOrganizationId()); 
				  //PJSTATUS项目状态=12(审核不通过)；
				  cond.add("pjbaseinfo.pjstatus", Operator.EQ, 12);
				  
				  /**PJAUDITTYPE项目审核类型=1（主管审核）*/
				  Condition cond2=new Condition();
				  cond2.add("pjaudittype", Operator.EQ, 1);
				  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);

				  this.addProjectIdsIn(projectIds, cond);//在日志表中查找相关记录
				  
				  flag = true;				  
			  }else if(user.getUserType().equals(IConstants.USER_TYPE_3)){//3.发改委各部门：SYS_DEPT 部门ID表
				  //PJSTATUS项目状态=12(审核不通过)；
				  cond.add("pjbaseinfo.pjstatus", Operator.EQ, 12);
				  
				  /**PJAUDITTYPE项目审核类型=1（主管审核）或PJAUDITTYPE	项目审核类型=2（处室处长审核后）,
				   * senddeptid发送发改委处理部门不为空的项目计划报表。
				   */
				  Condition cond2=new Condition();
				  cond2.add("pjaudittype", Operator.IN, Arrays.asList(1,2));
				  cond2.add("sysDeptBySenddeptid",Operator.ISNOTNULL,null);
				  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);

				  this.addProjectIdsIn(projectIds, cond);//在日志表中查找相关记录
				
				  flag = true;
			  }
		  }else if(params.getReportType().equalsIgnoreCase("5")){//5-项目申请汇总报表
			  if(user.getUserType().equals(IConstants.USER_TYPE_2)){//2-主管单位
				  cond.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId"
						   , Operator.EQ, user.getSysOrganization().getOrganizationId()); 
				  //项目状态大于1且不=11（过期）
				  cond.add("pjbaseinfo.pjstatus", Operator.GT, 1);
				  cond.add("pjbaseinfo.pjstatus", Operator.NE, 11);
				  
				  /**PJAUDITTYPE项目审核类型=1（主管审核）*/
				  Condition cond2=new Condition();
				  cond2.add("pjaudittype", Operator.EQ, 1);
				  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);

				  this.addProjectIdsIn(projectIds, cond);//在日志表中查找相关记录
				  
				  flag = true;
			  }
			  
		  }
		  else if(params.getReportType().equalsIgnoreCase("6")){//6-项目管理：正式项目库
			 // if(user.getUserType().equals(IConstants.USER_TYPE_3) && user.getSysDept().getDeptId()!=12){//3-发改委非投资处部门：SYS_DEPT 部门ID表
				  //项目状态大于5且不=11（过期）【2014-8-7改为项目必须是已经审核通过的项目（pjStatus=10）】
				  //cond.add("pjbaseinfo.pjstatus", Operator.GT, 5);
				  //cond.add("pjbaseinfo.pjstatus", Operator.NE, 11);
				  cond.add("pjbaseinfo.pjstatus", Operator.EQ, 10);
				  
				  //项目的储备类别是”正式项目库“
				  cond.add("pjbaseinfo.xmcblb",Operator.ISNOTNULL,null);
				  cond.add("pjbaseinfo.xmcblb", Operator.EQ, "正式项目库");
				  
				  /**项目审计日志表中发送单位ID[SENDDEPTID]是12（发改委投资处）user.getSysDept().getDeptId()*/
				/*  Condition cond2=new Condition();
				  cond2.add("sysDeptBySenddeptid",Operator.ISNOTNULL,null);
				  cond2.add("sysDeptBySenddeptid.deptId",Operator.EQ,12);
				  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);
				 
				  this.addProjectIdsIn(projectIds, cond);//在日志表中查找相关记录  */				   
				  
				  flag = true;
			  //}
		  }
		  else if(params.getReportType().equalsIgnoreCase("7")){//7-项目管理：储备项目库
			  cond.add("pjbaseinfo.pjstatus", Operator.EQ, 10);
			  //项目的储备类别是”储备项目库“
			  cond.add("pjbaseinfo.xmcblb",Operator.ISNOTNULL,null);
			  cond.add("pjbaseinfo.xmcblb", Operator.EQ, "储备项目库");
			  
			  flag = true;
		  }
		  cond.add("pjbaseinfo.deleted", Operator.NE, 1);
		  
		  this.addParamsCond(cond);
		  Map<String, Object> map = new HashMap<String, Object>();
		  map.put("flag", flag);
		  map.put("cond", cond);
		  
		  return map;
	  }
	  
	  /**
	   * 在日志表中查找相关记录
	   * ORA-01795: 列表中的最大表达式数为 1000 解决办法
	   * java.sql.SQLException: ORA-01795: 
	   * 	maximum number of expressions in a list is 1000
	   * @param projectIds
	   * @param maxNum
	   * @return
	   */
	  private Condition addProjectIdsIn(Set<Integer> projectIds, Condition cond){
		  int maxNum = 900;//in(1,2,3,...)；in里边允许的最大值
		  
		  if(projectIds.size() == 0){//在日志表中没有找到相关记录
			  projectIds.add(0);
			  cond.add("pjbaseinfo.projectid", Operator.IN, projectIds);
		  }else if(projectIds.size() <= maxNum){//未超过允许的最大值时
			  cond.add("pjbaseinfo.projectid", Operator.IN, projectIds);
		  }else if(projectIds.size() > maxNum){//超过允许的最大值时
			  int j=0;
			  Condition cond3=new Condition();
			  StringBuffer sb = new StringBuffer();
			  for(Integer projectId:projectIds){
				  if(j>0 && (j+1)%maxNum==0){
					  sb.append(",").append(projectId);
					  cond3.add("pjbaseinfo.projectid", Operator.IN, Arrays.asList(sb.toString().substring(1).split(",")));
					  sb = new StringBuffer();
				  }else{
					  sb.append(",").append(projectId);
				  }
				  j++;
			  }
			  if(projectIds.size()%maxNum!=0){
				  cond3.add("pjbaseinfo.projectid", Operator.IN, Arrays.asList(sb.toString().substring(1).split(",")));
			  }
			  cond.add("pjbaseinfo.projectid", Operator.OR, cond3);
		  }
		  return cond;
	  }
	  
	  /**
	   * 将params加入到Condition中
	   * @param cond
	   * @return
	   */
	  private Condition addParamsCond(Condition cond){
		  if(params != null){
				//基本查询条件
				if(StringUtils.isNotEmpty(params.getProjectName())) {
					cond.add("pjbaseinfo.projectname", Operator.LIKE, params.getProjectName());
				} 
				if (StringUtils.isNotEmpty(params.getXmlx())) {//项目类型
					cond.add("pjbaseinfo.xmsbXmlx", Operator.ISNOTNULL, null);
					cond.add("pjbaseinfo.xmsbXmlx.xmlxId", Operator.IN, Arrays.asList(params.getXmlx().split(",")));
				}
				if(params.getStartyear()!=null && params.getStartyear()>0){
					cond.add("pjbaseinfo.startyear", Operator.GE, params.getStartyear());
				}
				if(params.getEndyear()!=null && params.getEndyear()>0){
					cond.add("pjbaseinfo.endyear", Operator.LE, params.getEndyear());
				}	
				if(StringUtils.isNotEmpty(params.getOrganizationName())) {
					cond.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationName", Operator.LIKE, params.getOrganizationName());
				}
				if(StringUtils.isNotEmpty(params.getManageUnitsName())) {
					cond.add("pjbaseinfo.manageunitsname", Operator.LIKE, params.getManageUnitsName());
				}
				if(StringUtils.isNotEmpty(params.getProjectContent())) {
					cond.add("pjbaseinfo.projectcontent", Operator.LIKE, params.getProjectContent());
				}
				if(StringUtils.isNotEmpty(params.getDeclaregist())) {
					cond.add("pjbaseinfo.declaregist", Operator.LIKE, params.getDeclaregist());
				}		
				if(StringUtils.isNotEmpty(params.getPjinvestAdvice())) {//市财政资金安排渠道建议
					cond.add("pjinvestadvice", Operator.LIKE, params.getPjinvestAdvice());
				}
				
				//2014-8-7新加入的查询条件
				if(StringUtils.isNotEmpty(params.getXqId())){//所属区域
					cond.add("pjbaseinfo.sysXq", Operator.ISNOTNULL, null);
					cond.add("pjbaseinfo.sysXq.xqId", Operator.IN, Arrays.asList(params.getXqId().split(",")));
				}
				if (StringUtils.isNotEmpty(params.getProjectcode())) {//项目编码
					cond.add("pjbaseinfo.projectcode", Operator.LIKE, params.getProjectcode());
				}
				if (StringUtils.isNotEmpty(params.getXmyz())) {//项目业主
					cond.add("pjbaseinfo.sysOrganizationByDeclareunitsid", Operator.ISNOTNULL,null);
					cond.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationName",Operator.LIKE, params.getXmyz());
				}
				if(StringUtils.isNotEmpty(params.getXmfl())){//项目分类（基本建设投资类项目、更新改造投资类项目、其他固定资产投资类项目）
					cond.add("pjbaseinfo.xmfl", Operator.ISNOTNULL, null);
					cond.add("pjbaseinfo.xmfl", Operator.EQ, params.getXmfl());
				}
				if (StringUtils.isNotEmpty(params.getHylb())) {//行业类别
					cond.add("pjbaseinfo.xmsbHylb", Operator.ISNOTNULL, null);
					cond.add("pjbaseinfo.xmsbHylb.hylbId", Operator.IN, Arrays.asList(params.getHylb().split(",")));
				}
				if (StringUtils.isNotEmpty(params.getZjxz())) {//资金性质
					cond.add("pjbaseinfo.xmsbZjxz", Operator.ISNOTNULL, null);
					cond.add("pjbaseinfo.xmsbZjxz.zjxzId", Operator.IN, Arrays.asList(params.getZjxz().split(",")));
				}
				if (StringUtils.isNotEmpty(params.getDeptname())) {//当前处理部门
					cond.add("pjbaseinfo.auditdeptname", Operator.LIKE, params.getDeptname());
				}
				if(StringUtils.isNotEmpty(params.getBeginModifiedTime())){//修改时间：开始时间
					cond.add("pjbaseinfo.modifiedTime", Operator.GE, DateUtil.parse(params.getBeginModifiedTime(),"yyyy-MM-dd"));
				}
				if(StringUtils.isNotEmpty(params.getEndModifiedTime())){//修改时间：结束时间
					cond.add("pjbaseinfo.modifiedTime", Operator.LE, DateUtil.parse(params.getEndModifiedTime(),"yyyy-MM-dd"));
				}
				
				//预计至XX年底累计完成投资
				if(params.getExpectFinishInvestYear()!=null && params.getExpectFinishInvestYear()>0){
					cond.add("pjbaseinfo.expectfinishinvestyear", Operator.EQ, params.getExpectFinishInvestYear());
				}
				if(this.checkInteger(params.getExpectFinishInvest())){
					cond.add("pjbaseinfo.expectfinishinvest", Operator.GE, params.getExpectFinishInvest());
				}
				if(this.checkInteger(params.getExpectFinishInvest2())){
					cond.add("pjbaseinfo.expectfinishinvest", Operator.LE, params.getExpectFinishInvest2());
				}
				if(this.checkInteger(params.getExpectFinishOtherInvest())){
					cond.add("pjbaseinfo.expectfinishotherinvest", Operator.GE, params.getExpectFinishOtherInvest());
				}
				if(this.checkInteger(params.getExpectFinishOtherInvest2())){
					cond.add("pjbaseinfo.expectfinishotherinvest", Operator.LE, params.getExpectFinishOtherInvest2());
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
		  cond.add("pjbaseinfo.deleted", Operator.NE,IConstants.DEL_FLAG_TRUE);
		  return cond;
	  }
	  
	  
	  
	  private Boolean checkInteger(Double gNum){
		  if(gNum!=null && gNum>=0){
				 return true;
		  }
		  return false;
	  }
	  
	  /*私有方法定义*/
		/**
		 * 取出在用户登录时通过方法{@link com.gzzn.fgw.action.sys.LoginAction.findDirectionaryitems()
		 * 存入的{@link CommonFiled.SESSION_DIRECTIONARYITEMS}状态表。并按照name取出对应的记录，
		 * 然后检索每一条记录，组成键值对
		 * 
		 * @param map 结果存放的集合
		 * @param name 筛选状态字段表的条件
		 * @return map 结果存放的集合
		 */
		private Map<Integer, String> getDictionaryitemsToMap(Map<Integer,String> map,String name){
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
		 
	  
	  
	  
	  
	  
	/*get、set方法*/
	public ReportPojo getParams() {
		return params;
	}

	public void setParams(ReportPojo params) {
		this.params = params;
	}

	public PageUtil<Projectinvest> getPage() {
		return page;
	}


	public void setPage(PageUtil<Projectinvest> page) {
		this.page = page;
	}


	public Map<Integer, String> getXmlxMap() {
		xmlxMap = getDictionaryitemsToMap(xmlxMap, IConstants.DICTIONARY_ITEM_XMLX);
		return xmlxMap;
	}

	public void setXmlxMap(Map<Integer, String> xmlxMap) {
		this.xmlxMap = xmlxMap;
	}
 
	public Map<Integer, String> getXmztMap() {
		xmztMap = getDictionaryitemsToMap(xmztMap, IConstants.DICTIONARY_ITEM_XMZT);
		return xmztMap;
	}

	public void setXmztMap(Map<Integer, String> xmztMap) {
		this.xmztMap = xmztMap;
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
 
}
