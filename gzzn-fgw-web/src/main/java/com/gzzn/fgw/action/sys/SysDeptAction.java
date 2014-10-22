package com.gzzn.fgw.action.sys;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysDeptService;
import com.gzzn.fgw.service.sys.pojo.SysDeptPojo;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.util.web.PageUtil;

/**
 * 
 * <p>Title: SysDeptAction</p>
 * <p>Description:  部门信息维护 </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-4 下午7:37:54 lhq  new
 */
@Namespace(value = "/sys/dept")
public class SysDeptAction extends BaseAction<SysDeptPojo> {
	protected static Logger logger = LoggerFactory.getLogger(SysDeptAction.class);
	
	private String id;//编辑或删除的id，多个间用@隔开
	private SysQueryParam sysParams;//系统管理查询参数
	private SysDept obj;//机构对象
	private String message;//返回页面信息
	private String orgOrUpDept;//机构或上级部门信息：用于编辑信息
	private PageUtil<SysDeptPojo> page = new PageUtil<SysDeptPojo>();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysDeptService service;
	
	//进入部门信息列表界面
	@Action("list")
	public String list(){
		Condition con = new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//删除标记：0-	非删除 1-	逻辑删除
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getParentDeptId())){
				con.add("parentdeptid", Operator.ISNOTNULL, null);
				con.add("parentdeptid", Operator.IN, Arrays.asList(sysParams.getParentDeptId().split(",")));
			}
			if(StringUtils.isNotEmpty(sysParams.getDeptname())) {
				con.add("deptname", Operator.LIKE, sysParams.getDeptname());
			}
		}
		
		//con.add("sfxs", Operator.ISNOTNULL,null);
		//con.add("sfxs", Operator.EQ,1);
		Order order1 = new Order(Direction.ASC, "levelnumber");
		Order order2 = new Order(Direction.ASC, "parentdeptid");
		Sort sort = new Sort(order1, order2);
		service.findList(page, con, sort);
		return "success";
	}
	
	
	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysDept.class, Integer.parseInt(id));
			//所属单位或上级部门字段进行特殊的处理：如果当前记录的上级部门为空的话，那就设置单位信息给该字段
			if(obj.getParentdeptid()!=null){
				orgOrUpDept = obj.getParentdeptid().toString();
			}else if(obj.getSysOrganization()!=null){
				orgOrUpDept = "g"+obj.getSysOrganization().getOrganizationId().toString();
			}
		}
		else{
			obj = new SysDept();
		}
		return "success";
	}
	
	
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			//所属单位或上级部门字段进行特殊的处理：如果当前记录的上级部门为空的话，那就设置单位信息给该字段
			SysOrganization orgObj = null;
			if(StringUtils.isNotEmpty(orgOrUpDept)){
				if(orgOrUpDept.startsWith("g")){//为单位信息
					orgObj = commonService.findOne(SysOrganization.class, Integer.valueOf(orgOrUpDept.substring(1)));
				}else{//为上级部门
					SysDept deptUp = commonService.findOne(SysDept.class, Integer.valueOf(orgOrUpDept));
					obj.setParentdeptid(deptUp.getDeptId());
					orgObj = deptUp.getSysOrganization();
				}
			}
			obj.setSysOrganization(orgObj);
			commonService.saveOrUpdate(obj);
			
			logObject = new LogObject("部门信息",obj.getDeptId(),obj.getDeptname(),null);
		} catch (Exception e) {
			message = "保存数据失败";
		}

		return "success";
	}
	
	//删除部门信息
	@GzznLog
	@Action(value = "delete", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					//1、通过修改数据库的删除动作完成数据的级联删除功能
					//2、SysDept查找对应的上级部门的id，并将其上级部门置空
					/**Condition con = new Condition("parentdeptid", Operator.EQ, Integer.parseInt(i));
					List<SysDept> listSysDept =  commonService.find(SysDept.class, con);
					for(SysDept obj:listSysDept){
						obj.setParentDeptId(null);
						commonService.update(obj);
					}
					//3、删除记录本身的信息
					commonService.delete(SysDept.class, Integer.parseInt(i));*/
					
					//部门信息：逻辑删除
					SysDept obj = commonService.findOne(SysDept.class, Integer.parseInt(i));
					if(obj!=null){
						obj.setDeleted(IConstants.DEL_FLAG_TRUE);//删除标记：0-非删除 1-逻辑删除
						commonService.update(obj);
						logicDelSysDept(obj);//删除全部的下级部门信息
					}
				}
				logObject = new LogObject("删除部门信息，ids=" + id);
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		return "success";
	}
	
	/**
	 * 逻辑删除部门信息的下级部门的信息
	 * @param listDel
	 * @param objDel
	 */
	private void logicDelSysDept(SysDept objDel){
		Condition con = new Condition();
		con.add("parentdeptid", Operator.ISNOTNULL, null);
		con.add("parentdeptid", Operator.EQ, objDel.getDeptId());
		List<SysDept> listChild = commonService.find(SysDept.class, con);
		for(SysDept objChild : listChild){
			if(objChild.getDeleted().equals(IConstants.DEL_FLAG_FALSE)){
				objChild.setDeleted(IConstants.DEL_FLAG_TRUE);
				commonService.update(objChild);
				logicDelSysDept(objChild);//递归调用
			}
		}
	}
	
	
	/**编辑部门信息时：验证部门名称、部门代码是否已经重复*/
	@Action("checkDeptRepeat")
	public void checkDeptRepeat(){
		Condition con=new Condition();
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//删除标记：0-	非删除 1-	逻辑删除
		if(obj.getDeptId()!=null && obj.getDeptId()>0){
			con.add("deptId", Operator.NE, obj.getDeptId());
		}
		if(StringUtils.isNotEmpty(obj.getDeptname())){
			con.add("deptname", Operator.EQ, obj.getDeptname());
		}
		if(StringUtils.isNotEmpty(obj.getFullcode())){
			con.add("fullcode", Operator.EQ, obj.getFullcode());
		}
		
		List<SysDept> deptList = commonService.find(SysDept.class, con);
		if(deptList!=null && deptList.size()>0){
			outPutString("false");
		}else{
			outPutString("true");
		}
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
	public SysDept getObj() {
		return obj;
	}
	public void setObj(SysDept obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<SysDeptPojo> getPage() {
		return page;
	}
	public void setPage(PageUtil<SysDeptPojo> page) {
		this.page = page;
	}


	public String getOrgOrUpDept() {
		return orgOrUpDept;
	}


	public void setOrgOrUpDept(String orgOrUpDept) {
		this.orgOrUpDept = orgOrUpDept;
	}

}
