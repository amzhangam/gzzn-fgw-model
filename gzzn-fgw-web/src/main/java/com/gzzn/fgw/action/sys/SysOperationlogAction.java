package com.gzzn.fgw.action.sys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

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
import com.gzzn.fgw.expExcel.SysOperationlogExpExcel;
import com.gzzn.fgw.model.SysOperationlog;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysUserRole;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysOperationlogService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.util.UniqueCodeGenerator;
import com.gzzn.fgw.webUtil.JxlExcelCellUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: SysOperationlogAction</p>
 * <p>Description: 用户操作日志维护  </p>
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
public class SysOperationlogAction extends BaseAction<SysOperationlog> {
	
	
	private String id;//编辑或删除的id，多个间用@隔开
	private SysQueryParam sysParams;//系统管理查询参数
	private SysProjectlog obj;//日志对象
	private String message;//返回页面信息
	private PageUtil<SysOperationlog> page = new PageUtil<SysOperationlog>();
	
	private InputStream excelFile;//excel导出文件流
	private String downloadFileName;//excel导出文件名称
	private String filepath;
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysOperationlogService service;
	
	
	@Action("list")
	public String list(){
		long begin = System.currentTimeMillis();
		
		Condition con = this.initCondition();
		service.loadData(SysOperationlog.class, page, con, new Sort(Direction.DESC,"operationDatetime"));
		
		logger.info("查询用户操作日志列表，耗损时间为：{} 毫秒 ", System.currentTimeMillis() - begin);
		return "success";
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
			if(StringUtils.isNotEmpty(sysParams.getLikeData())){
				con.add("operationContent", Operator.LIKE, sysParams.getLikeData());
			}
		}
		
		//查找用户是否具有查看全部日志的权限
		SysUser user = getLoginUser();//获取当前登录用户信息
		Boolean flag = checkUserPermiss(user);
		if(flag.equals(false)){//只可以查看所属单位、部门的用户操作日志
			if(user.getSysOrganization() != null){
				con.add("sysOrganization.organizationId", Operator.ISNOTNULL, null);
				con.add("sysOrganization.organizationId", Operator.EQ, user.getSysOrganization().getOrganizationId());
			}
			if(user.getSysDept() != null){
				con.add("sysDept.deptId", Operator.ISNOTNULL, null);
				con.add("sysDept.deptId", Operator.EQ, user.getSysDept().getDeptId());
			}
		}

		return con;
	}
	
	/**
	 * 查找用户是否具有查看全部日志的权限：【通过用户角色信息判读】
	 * 		flag = true 具有，flag = false 不具有
	 * 
	 * 普通用户只可以查看所属单位、部门的用户操作日志；
	 * 发改委投资处、超级管理员用户可以查询所有用户的操作日志
	 */
	private Boolean checkUserPermiss(SysUser user){
		Boolean flag = false;
		Condition con2 = new Condition();
		con2.add("sysUser", Operator.EQ, user);
		List<SysUserRole> userRoleList = commonService.find(SysUserRole.class, con2);
		for(SysUserRole userRoleObj : userRoleList){
			if(userRoleObj.getSysRole()!=null && ( userRoleObj.getSysRole().getRoleName().indexOf("投资处")!=-1 ||
					userRoleObj.getSysRole().getRoleName().indexOf("管理员")!=-1 )){
				flag = true;
			}
		}
		return flag;
	}
	
	
	//导出Excel
	@GzznLog
	@Action(value = "exportExcel", results = { @Result(name="excel", type="stream",
		  params = {
			"bufferSize", "1024",
			"inputName", "excelFile",
			"contentType","application/vnd.ms-excel",
			"contentDisposition","attachment;filename=${downloadFileName}"})})
	public String exportExcel(){
		 Condition con = this.initCondition();
		 List<SysOperationlog> list = commonService.find(SysOperationlog.class, con, new Sort(Direction.DESC,"operationDatetime"));
		 //downloadFileName = JxlExcelCellUtil.setExcelFileName("用户操作日志信息__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 downloadFileName = downFileNameTranscode("用户操作日志信息__"+DateUtil.format(new Date(), "yyyyMMddHHmm")+".xls");
		 excelFile = new SysOperationlogExpExcel().expExcelFile(list);
		 logObject = new LogObject("导出用户操作日志信息。日期："+ sysParams.getStartTime() +" 至 "+ sysParams.getEndTime());
		 return "excel";
	}
	
	//打印日志功能
	@GzznLog
	@Action("print")
	public String print() {
		Condition con = this.initCondition();
		List<SysOperationlog> list = commonService.find(SysOperationlog.class, con, new Sort(Direction.DESC,"operationDatetime"));
		excelFile = new SysOperationlogExpExcel().expExcelFile(list);
		filepath = UniqueCodeGenerator.getUniqueCode()+"Log.xls";
		try {
			FileUtils.copyInputStreamToFile(excelFile, new File(getTempPath() + filepath));
			logObject = new LogObject("打印用户操作日志信息。日期："+ sysParams.getStartTime() +" 至 "+ sysParams.getEndTime());
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

	public PageUtil<SysOperationlog> getPage() {
		return page;
	}

	public void setPage(PageUtil<SysOperationlog> page) {
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
