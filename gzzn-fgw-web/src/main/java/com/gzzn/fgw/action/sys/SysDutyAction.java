package com.gzzn.fgw.action.sys;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.SysDuty;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.ITreeJsonDataService;
import com.gzzn.fgw.service.sys.ISysDutyService;
import com.gzzn.util.web.PageUtil;

/**
 * <p>Title: SysDutyAction</p>
 * <p>Description:  职务信息维护 </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-3 下午3:47:44 lhq  new
 */
@Namespace(value = "/sys/duty")
public class SysDutyAction extends BaseAction<SysDuty> {
	
	private String id;//编辑或删除的id，多个间用@隔开
	private String dutyName;//职务名称
	private SysDuty obj;//职务对象
	private String message;//返回页面信息
	private PageUtil<SysDuty> page = new PageUtil<SysDuty>();

	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysDutyService service;
	
	@Autowired
	private ITreeJsonDataService treeJsonDataService;
	
	//进入职务信息列表界面
	@Action("list")
	public String list(){
		Condition con = null;
		//Order order1 = new Order(Direction.ASC, "dutyName");
		Order order2 = new Order(Direction.DESC, "dutyId");
		//Sort sort = new Sort(order1, order2);
		Sort sort = new Sort(order2);
		
		if (StringUtils.isNotEmpty(dutyName)) {
			con = new Condition("dutyName", Operator.LIKE, dutyName);
		}
		service.loadData(SysDuty.class, page, con , sort);
		
		return "success";
	}
	
	//进入职务信息编辑界面
	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysDuty.class, Integer.parseInt(id));
		}
		else{
			obj = new SysDuty();
		}
		
		return "success";
	}
	
	//保存职务信息
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			commonService.saveOrUpdate(obj);
			logObject = new LogObject("职务信息",obj.getDutyId(),obj.getDutyName(),null);
		} catch (Exception e) {
			message = "保存数据失败";
		}

		return "success";
	}
	
	//删除职务信息
	@GzznLog
	@Action(value = "delete", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					//1、通过修改数据库的删除动作完成数据的级联删除功能
					//2、删除记录本身的信息
					commonService.delete(SysDuty.class, Integer.parseInt(i));
				}
				logObject = new LogObject("删除职务信息，ids=" + id);
			}
		} catch (Exception e) {
			message = "删除数据失败";
		}

		return "success";
	}
	
	@Action("getSysDutyTreeJson")
	public void getSysDutyTreeJson(){
		outJsonString(treeJsonDataService.getSysDutyTreeJson());
	}
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public SysDuty getObj() {
		return obj;
	}
	public void setObj(SysDuty obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<SysDuty> getPage() {
		return page;
	}
	public void setPage(PageUtil<SysDuty> page) {
		this.page = page;
	}


}
