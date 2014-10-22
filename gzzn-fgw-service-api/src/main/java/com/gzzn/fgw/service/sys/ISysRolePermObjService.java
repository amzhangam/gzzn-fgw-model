package com.gzzn.fgw.service.sys;

import java.util.List;

import com.gzzn.fgw.model.pojo.TreeNodesPojo;

/**
 * <p>Title: ISysRolePermObjService</p>
 * <p>Description:  角色权限分配接口  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-11 下午4:18:52 amzhang  new
 */
public interface ISysRolePermObjService {
	
	/**
	 * 查找系统的全部操作权限
	 * S - 系统表、M - 系统模块表、  P - 权限对象
	 * @return
	 */
	public List<TreeNodesPojo> findAllPermission();
	
	
	/**
	 * 取出角色所具有的的权限节点
	 * S - 系统表、M - 系统模块表、  P - 权限对象
	 * @param actorId
	 * @param sType 类型：leafNodes只选出具有权限的叶子节点；allNodes选出具有权限的全部节点
	 * @return
	 */
	public List<String>  findActorPermissionNodes(String actorId,String sType);
	
	
	/**
	 * 保存角色的操作权限
	 * S - 系统表、M - 系统模块表、  P - 权限对象
	 * @param ids 页面上所选的叶子节点权限
	 * @param actorId
	 */
	public void saveChecked(String actorId,String ids);
	

}
