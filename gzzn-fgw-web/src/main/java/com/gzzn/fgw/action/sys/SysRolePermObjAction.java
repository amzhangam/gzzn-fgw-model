package com.gzzn.fgw.action.sys;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysRolePermissionobject;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysRolePermObjService;

/**
 * <p>Title: SysRolePermObjAction</p>
 * <p>Description:  角色授权信息维护 </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-11 下午5:18:36 lhq  new
 */
@Namespace(value = "/sys/role")
public class SysRolePermObjAction extends BaseAction<SysRolePermissionobject> {
	
	protected static Logger logger = LoggerFactory.getLogger(SysRolePermObjAction.class);
	
	
	private String id;//编辑或删除的id，多个间用@隔开
	private String treeIds;//接收用户选择的叶子节点id
	private String jsonTreeNodes;//权限树以JSON格式存储
	private SysRole sysRole;//角色信息
	private String message;//返回页面信息
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysRolePermObjService service;
	
	
	@Action("rolePermission")
	public String rolePermission(){
		//开始的时候
		long begin=System.currentTimeMillis();
		sysRole = commonService.findOne(SysRole.class, Integer.parseInt(id));
		//第一步：查询正常使用的全部系统的相关权限,S - 系统表、M - 系统模块表、  P - 权限对象
		List<TreeNodesPojo> list = service.findAllPermission();
		//第二步：查询角色所具有的相关权限
		List<String> listNodes = service.findActorPermissionNodes(id, "allNodes");
		//第三步：在全部权限信息中将角色具有的权限设置成checked
		for(TreeNodesPojo obj : list){
			if(listNodes.size()>0){
				if(listNodes.contains(obj.getId())){//该角色具有该权限，编辑Checked为True
					obj.setChecked(true);
					listNodes.remove(obj.getId());
				}
			}else{
				break;
			}
		}
		//第四步：返回权限树
		Gson gson = new Gson();
		jsonTreeNodes = gson.toJson(list);
		
		logger.info("角色操作权限授权查询，耗损时间为："+(System.currentTimeMillis()-begin)+" 毫秒");
		
		return "success";
	}
	
	
	@GzznLog
	@Action(value = "saveRolePermission", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String saveRolePermission(){
		message = "角色授权成功";
		try {
			sysRole = commonService.findOne(SysRole.class, Integer.parseInt(id));
			service.saveChecked(id, treeIds);
			
			logObject = new LogObject("角色授权。角色id："+ Integer.parseInt(id) +"，角色名称："+ sysRole.getRoleName());
		} catch (Exception e) {
			message = "角色授权失败";
		}

		return "success";
	}
	
	
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTreeIds() {
		return treeIds;
	}
	public void setTreeIds(String treeIds) {
		this.treeIds = treeIds;
	}
	public String getJsonTreeNodes() {
		return jsonTreeNodes;
	}
	public void setJsonTreeNodes(String jsonTreeNodes) {
		this.jsonTreeNodes = jsonTreeNodes;
	}
	public SysRole getSysRole() {
		return sysRole;
	}
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
