package com.gzzn.fgw.action.sys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.expExcel.SysProjectlogExpExcel;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.SysProjectlogPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.report.IPjauditlogService;
import com.gzzn.fgw.service.sys.ISysProjectlogService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.util.UniqueCodeGenerator;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: SysOperationlogAction</p>
 * <p>Description: 项目日志维护  </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-13 上午9:40:54 lhq  new
 */

@Namespace(value = "/sys/log")
public class SysProjectlogAction extends BaseAction<SysProjectlogPojo> {
	
	
	private String id;//编辑或删除的id，多个间用@隔开
	private SysQueryParam sysParams;//系统管理查询参数
	private SysProjectlog obj;//项目日志对象
	private String message;//返回页面信息
	private PageUtil<SysProjectlogPojo> page = new PageUtil<SysProjectlogPojo>();
	
	private InputStream excelFile;//excel导出文件流
	private String downloadFileName;//excel导出文件名称
	private String filepath;
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysProjectlogService service;
	@Autowired
	private IPjauditlogService pjauditlogService;
	
	
	@Action("pjList")
	public String pjList(){
		long begin = System.currentTimeMillis();
		//Condition con = this.initCondition();
		//service.findList(page, con, new Sort(Direction.DESC,"operationDatetime"));
		
		List<SysProjectlog> list  = this.getSysProjectlogList();
		page.setCount(list.size());//总数量
		list = list.subList(page.getOffset(), page.getEndIndex());//当前页需要展示的数据
		page.setList(service.getLogPojoList(list));
		
		logger.info("查询项目日志列表，耗损时间为：{} 毫秒 ， 当前用户类型={} ",
				System.currentTimeMillis() - begin, getLoginUser().getUserType());
				
		return "success";
	}
	
	
	private List<SysProjectlog> getSysProjectlogList(){
		SysUser user = getLoginUser();//获取当前登录用户信息
		Condition con = this.initCondition();//页面查询条件初始化
		List<SysProjectlog> list  = new ArrayList<SysProjectlog>();
		
		if(user.getUserType().equals(IConstants.USER_TYPE_1)){//业主用户：申报单位ID为本单位ID
			con.add("pjbaseinfo", Operator.ISNOTNULL, null);
			con.add("pjbaseinfo.pjstatus", Operator.GT, 0);
		    con.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationId"
					   , Operator.EQ, user.getSysOrganization().getOrganizationId());
		    
		    list = commonService.find(SysProjectlog.class, con, new Sort(Direction.DESC,"operationDatetime"));
		}else if(user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位用户
			 //B类情况数据：B、主管单位申报项目的申报单位ID为本单位ID（当前登陆的主管单位）
			 con.add("pjbaseinfo", Operator.ISNOTNULL, null);
			 con.add("pjbaseinfo.pjstatus", Operator.GT, 0);
		     con.add("pjbaseinfo.sysOrganizationByDeclareunitsid.organizationId"
			 		   , Operator.EQ, user.getSysOrganization().getOrganizationId());
		     list = commonService.find(SysProjectlog.class, con);
		    
		     //A类情况数据：A、业主申报项目的主管单位ID为本单位ID（当前登陆的主管单位）
			 Condition cond2 = this.initCondition();
			 cond2.add("pjbaseinfo", Operator.ISNOTNULL, null);
			 cond2.add("pjbaseinfo.pjstatus", Operator.GT, 0);
			 cond2.add("pjbaseinfo.sysOrganizationByDirectorunitsid.organizationId"
					   , Operator.EQ, user.getSysOrganization().getOrganizationId()); 
			 list.addAll(commonService.find(SysProjectlog.class, cond2));
			 
			 //对list进行排序处理
			 Collections.sort(list, new Comparator<SysProjectlog>(){    
	                public int compare(SysProjectlog arg0, SysProjectlog arg1) { 
	                	return arg1.getOperationDatetime().compareTo(arg0.getOperationDatetime());
	                 }    
	         }); 
		}else if(user.getUserType().equals(IConstants.USER_TYPE_3)){//发改委用户
			  con.add("pjbaseinfo", Operator.ISNOTNULL, null);
			  //项目状态大于5且不=11（过期）
			  con.add("pjbaseinfo.pjstatus", Operator.GT, 5);
			  con.add("pjbaseinfo.pjstatus", Operator.NE, 11);
			  
			  /**项目审计日志表中发送单位ID[SENDDEPTID]是12（发改委投资处）user.getSysDept().getDeptId()*/
			  Condition cond2=new Condition();
			  cond2.add("sysDeptBySenddeptid",Operator.ISNOTNULL,null);
			  cond2.add("sysDeptBySenddeptid.deptId",Operator.EQ,12);
			  Set<Integer> projectIds = pjauditlogService.findProjectIdSet(cond2);
			  if(projectIds.size()==0){//在日志表中没有找到相关记录
				  projectIds.add(0);
			  }
			  con.add("pjbaseinfo.projectid", Operator.IN, projectIds);
			  
			  list = commonService.find(SysProjectlog.class, con, new Sort(Direction.DESC,"operationDatetime"));
		}else{
			 list = commonService.find(SysProjectlog.class, con, new Sort(Direction.DESC,"operationDatetime"));
		}
		
		return list;
	}
	
	
	
	
	/**
	 * 初始化日志查询条件
	 * @return
	 */
	private  Condition initCondition(){
		Condition con = new Condition();
		if(sysParams==null){
			sysParams = new SysQueryParam();
			sysParams.setStartTime(DateUtil.formatY_M_D(DateUtil.getNewDate(new Date(), "DATE", -7))+" 00:00");
			sysParams.setEndTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getUserName())){
				con.add("sysUser.userName", Operator.LIKE, sysParams.getUserName());
			}
			if(StringUtils.isNotEmpty(sysParams.getProjectName())){
				con.add("pjbaseinfo.projectname", Operator.LIKE, sysParams.getProjectName());
			}
			if(StringUtils.isNotEmpty(sysParams.getOrganName())){
				con.add("sysOrganization.organizationName", Operator.LIKE, sysParams.getOrganName());
			}
			if(StringUtils.isNotEmpty(sysParams.getDeptname())){
				con.add("sysDept.deptname", Operator.LIKE, sysParams.getDeptname());
			}
			if(StringUtils.isNotEmpty(sysParams.getStartTime())){
				con.add("operationDatetime", Operator.GE, DateUtil.parse(sysParams.getStartTime(),"yyyy-MM-dd HH:mm"));
			}
			if(StringUtils.isNotEmpty(sysParams.getEndTime())){
				con.add("operationDatetime", Operator.LE, DateUtil.parse(sysParams.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
			}
			/**if(StringUtils.isNotEmpty(sysParams.getLikeData())){
				con.add("operationContent", Operator.LIKE, sysParams.getLikeData());
			}*/
		}
		return con;
	}
	
	
	//导出Excel
	@GzznLog
	@Action(value = "exportExcelPJ", results = { @Result(name="excel", type="stream",
		  params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String exportExcelPJ(){
		 //Condition con = this.initCondition();
		 //List<SysProjectlog> list = commonService.find(SysProjectlog.class, con, new Sort(Direction.DESC,"operationDatetime"));
		 
		 List<SysProjectlog> list  = this.getSysProjectlogList();
		 //downloadFileName = JxlExcelCellUtil.setExcelFileName("项目日志信息__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 downloadFileName = downFileNameTranscode("项目日志信息__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 excelFile = new SysProjectlogExpExcel().expExcelFile(list);
		 logObject = new LogObject("导出项目日志信息。日期："+ sysParams.getStartTime() +" 至 "+ sysParams.getEndTime());
		 return "excel";
	}
	
	//打印日志功能
	@GzznLog
	@Action("print")
	public String printPj() {
		//Condition con = this.initCondition();
		//List<SysProjectlog> list = commonService.find(SysProjectlog.class, con, new Sort(Direction.DESC,"operationDatetime"));
		
		List<SysProjectlog> list  = this.getSysProjectlogList();
		excelFile = new SysProjectlogExpExcel().expExcelFile(list);
		filepath = UniqueCodeGenerator.getUniqueCode()+"Log.xls";
		try {
			FileUtils.copyInputStreamToFile(excelFile, new File(getTempPath() + filepath));
			logObject = new LogObject("项目日志信息。日期："+ sysParams.getStartTime() +" 至 "+ sysParams.getEndTime());
		} catch (IOException e) {
			logger.error("", e);
		}
		return "success";
	}

	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SysQueryParam getSysParams() {
		return sysParams;
	}
	public void setSysParams(SysQueryParam sysParams) {
		this.sysParams = sysParams;
	}
	public SysProjectlog getObj() {
		return obj;
	}
	public void setObj(SysProjectlog obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<SysProjectlogPojo> getPage() {
		return page;
	}
	public void setPage(PageUtil<SysProjectlogPojo> page) {
		this.page = page;
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
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	private String getTempPath() {
		return request.getServletContext().getRealPath("officetemp") + File.separator;
	}

}
