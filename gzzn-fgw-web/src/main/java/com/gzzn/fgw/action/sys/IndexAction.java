package com.gzzn.fgw.action.sys;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.SysProjectlogPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysProjectlogService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: IndexAction</p>
 * <p>Description: 首页相关信息维护  </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-5-26 上午9:40:54 lhq  new
 */

@Namespace(value = "/index")
public class IndexAction extends BaseAction<SysProjectlogPojo> {
	
	
	private String id;//编辑或删除的id，多个间用@隔开
	private String pageName;//页面名称
	private SysQueryParam sysParams;//系统管理查询参数
	private List<SysProjectlogPojo> listDoWork;//待办工作
	private List<SysProjectlogPojo> listHasDoWork;//已办工作
	private PageUtil<SysProjectlogPojo> page = new PageUtil<SysProjectlogPojo>();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysProjectlogService service;
	
	
	@Action("main")
	public String main() {
		long begin = System.currentTimeMillis();
		if (getLoginUser().getUserType().equals(IConstants.USER_TYPE_1)
				|| getLoginUser().getUserType().equals(IConstants.USER_TYPE_2)) {
			if(sysParams==null){
				sysParams = new SysQueryParam();
				sysParams.setNum(10);//显示10条记录
				sysParams.setRead(0);//待办工作
			}
			Order order1 = new Order(Direction.DESC, "operationDatetime");
			Order order2 = new Order(Direction.DESC, "readtime");
			Sort  sort = new Sort(order2, order1);
			Condition con = this.initCondition();
			//待办工作
			listDoWork = service.getLogPojoList(commonService.find(
					SysProjectlog.class, con, sort, 0, sysParams.getNum()));
			
			//已办工作
			sysParams.setRead(1);
			con = this.initCondition();
			listHasDoWork = service.getLogPojoList(commonService.find(
					SysProjectlog.class, con, sort, 0, sysParams.getNum()));
		}
		logger.info("首页已办待办工作查询，耗损时间为：{} 毫秒  ", System.currentTimeMillis() - begin);
		
		return "success";
	}
	

	@Action(value = "list") 
	public String list() {
		long begin = System.currentTimeMillis();
		
		if (getLoginUser().getUserType().equals(IConstants.USER_TYPE_1)
				|| getLoginUser().getUserType().equals(IConstants.USER_TYPE_2)) {
			Condition con = this.initCondition();
			Order order1 = new Order(Direction.DESC, "operationDatetime");
			Order order2 = new Order(Direction.DESC, "readtime");
			Sort  sort = new Sort(order2, order1);
			service.findList(page, con, sort);
		}

		logger.info("已办待办工作查询，耗损时间为：{} 毫秒  ", System.currentTimeMillis() - begin);
		return "success";
	}
	
	@Action(value = "toDoWork",results={@Result(name="main",location="main.ac", type = "redirectAction")
									   ,@Result(name="list",location="list.ac", type = "redirectAction",params = {
												"sysParams.read", "${sysParams.read}", "encode", "true" })})
	public String toDoWork() {
		if (StringUtils.isNotEmpty(id)) {
			SysProjectlog obj = commonService.findOne(SysProjectlog.class, Integer.valueOf(id));
			if(obj!=null){
				obj.setRead(1);//标记为已读
				obj.setReaderId(getLoginUser().getUserId());
				if(getLoginUser().getSysOrganization()!=null){
					obj.setReaderOrgId(getLoginUser().getSysOrganization().getOrganizationId());
				}
				obj.setReadtime(new Date());
				commonService.update(obj);
			}
		}
		if(pageName!=null && pageName.equalsIgnoreCase("main")){
			return "main";
		}else{
			return "list";
		}
	}
	
	
	private  Condition initCondition(){
		Condition con = new Condition();
		if(sysParams != null){
			if(sysParams.getRead()!=null){
				con.add("read", Operator.EQ, sysParams.getRead());
			}
			if(StringUtils.isNotEmpty(sysParams.getProjectName())){
				con.add("pjbaseinfo.projectname", Operator.LIKE, sysParams.getProjectName());
			}
			if(StringUtils.isNotEmpty(sysParams.getStartTime())){
				con.add("operationDatetime", Operator.GE, DateUtil.parse(sysParams.getStartTime(),"yyyy-MM-dd HH:mm"));
			}
			if(StringUtils.isNotEmpty(sysParams.getEndTime())){
				con.add("operationDatetime", Operator.LE, DateUtil.parse(sysParams.getEndTime(),"yyyy-MM-dd HH:mm"));
			}
			if(StringUtils.isNotEmpty(sysParams.getReadStartTime())){
				con.add("readtime", Operator.GE, DateUtil.parse(sysParams.getReadStartTime(),"yyyy-MM-dd HH:mm"));
			}
			if(StringUtils.isNotEmpty(sysParams.getReadEndTime())){
				con.add("readtime", Operator.LE, DateUtil.parse(sysParams.getReadEndTime(),"yyyy-MM-dd HH:mm"));
			}
		}
		//系统判别该用户是否属于业主或主管部门，是则在系统工作台的待办栏中显示属于该单位的项目操作日志表中
		SysUser sysUser = getLoginUser();
		if(sysUser!=null && sysUser.getSysOrganization()!=null){
			con.add("sysOrganization.organizationId", Operator.ISNOTNULL, null);
			con.add("sysOrganization.organizationId", Operator.EQ, sysUser.getSysOrganization().getOrganizationId());
		}
		return con;
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

	public List<SysProjectlogPojo> getListDoWork() {
		return listDoWork;
	}

	public void setListDoWork(List<SysProjectlogPojo> listDoWork) {
		this.listDoWork = listDoWork;
	}

	public List<SysProjectlogPojo> getListHasDoWork() {
		return listHasDoWork;
	}

	public void setListHasDoWork(List<SysProjectlogPojo> listHasDoWork) {
		this.listHasDoWork = listHasDoWork;
	}

	public PageUtil<SysProjectlogPojo> getPage() {
		return page;
	}

	public void setPage(PageUtil<SysProjectlogPojo> page) {
		this.page = page;
	}


	public String getPageName() {
		return pageName;
	}


	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


}
