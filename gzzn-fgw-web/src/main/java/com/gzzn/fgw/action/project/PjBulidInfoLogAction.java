package com.gzzn.fgw.action.project;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.model.PjBulidInfoLog;
import com.gzzn.fgw.service.project.IPjBulidInfoLogService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: PjBulidInfoLogAction</p>
 * <p>Description:  上报项目情况日志记录</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-08-08 lhq  new
 */
@Namespace(value = "/buildImpl")
public class PjBulidInfoLogAction extends BaseAction<PjBulidInfoLog> {
	
	protected static Logger logger = LoggerFactory.getLogger(PjBulidInfoLogAction.class);
	
	private SysQueryParam sysParams;//系统管理查询参数
	private PageUtil<PjBulidInfoLog> page=new PageUtil<PjBulidInfoLog>();//页面数据集合
	
	@Autowired
	private IPjBulidInfoLogService service;
	
	
	/**
	 * 查询上报项目情况日志记录
	 * @return
	 */
	@Action("logList")
	public String logList(){
		long begin = System.currentTimeMillis();
		
		Condition con = this.initCondition();
		service.loadData(PjBulidInfoLog.class, page, con, new Sort(Direction.DESC,"operationDatetime"));
		
		logger.info("查询上报项目情况日志记录，耗损时间为：{} 毫秒", System.currentTimeMillis() - begin);
		return "success";
	}

	/**
	 * 初始化日志查询条件
	 * @return
	 */
	private  Condition initCondition(){
		Condition con = new Condition();
		/**if(sysParams==null){
			sysParams = new SysQueryParam();
			sysParams.setStartTime(DateUtil.formatY_M_D(DateUtil.getNewDate(new Date(), "DATE", -7))+" 00:00");
			sysParams.setEndTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm"));
		}*/
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
			if(StringUtils.isNotEmpty(sysParams.getTbdw())){
				con.add("tbdw", Operator.LIKE, sysParams.getTbdw());
			}
			if(StringUtils.isNotEmpty(sysParams.getStartTime())){
				con.add("operationDatetime", Operator.GE, DateUtil.parse(sysParams.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotEmpty(sysParams.getEndTime())){
				con.add("operationDatetime", Operator.LE, DateUtil.parse(sysParams.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(sysParams.getOperationType()!=null && sysParams.getOperationType()>0){
				con.add("operationType", Operator.EQ, sysParams.getOperationType());
			}
			if(StringUtils.isNotEmpty(sysParams.getLikeData())){
				con.add("operationContent", Operator.LIKE, sysParams.getLikeData());
			}
		}

		return con;
	}
	
	
	
	
	public SysQueryParam getSysParams() {
		return sysParams;
	}
	public void setSysParams(SysQueryParam sysParams) {
		this.sysParams = sysParams;
	}
	public PageUtil<PjBulidInfoLog> getPage() {
		return page;
	}
	public void setPage(PageUtil<PjBulidInfoLog> page) {
		this.page = page;
	}
	
	
	

}
