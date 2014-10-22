package com.gzzn.fgw.service.sys;

import java.util.List;

import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysSystem;
import com.gzzn.fgw.model.SysUser;

/**
 * 
 * <p>Title: SysUserService</p>
 * <p>Description: sysUser service接口</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-20下午5:15:23  ChengZhi    new<p>
 */
public interface ISysUserService extends IPagedService<SysUser> {

	/**
	 * @Title: 登录验证
	 * @Description: 登录验证
	 * @param loginName-用户名
	 * @param loginPwd-密码
	 * @return SysUser
	 * @throws
	 */
	public SysUser login(String loginName, String loginPwd);

	/**
	 * 获取所有系统模块GUI等信息
	 * @return
	 */
	public List<SysSystem> getSystemModule();

	/**
	 * 获取用户居右权限的系统模块GUI等信息
	 * @param user 用户
	 * @return
	 */
	public List<SysSystem> getUserPermission(SysUser user);

	/**
	 * 根据用户信息,系统名称编码获取该用户所拥有的菜单
	 * @param user
	 * @param systemCode 为null时取出全部系统信息
	 * @return
	 */
	public List<TreeNodesPojo> getUserMenu(SysUser user, String systemCode);

	/**
	 * 获取所有角色
	 * @return
	 */
	public List<SysRole> getSysRoleAll();

	/**
	 * 根据角色id获取角色所具有的权限
	 * @param sysRoleId
	 * @return
	 */
	public List<SysSystem> getRolePermission(Integer sysRoleId);
	
	/**
	 * 获取用户类型信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	public String findUserTypeTreeJson(Boolean nocheck);
	
	/**
	 * 获取用户状态信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	public String findUseStatusTreeJson(Boolean nocheck);
	
	/**
	 * 获取角色类型信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	public String findRoleTypeTreeJson(Boolean nocheck);

	
	/**
	 * 获取指定处室的经办人员
	 * @param nocheck
	 * @param deptId
	 * @return
	 */
	public String findHandlerTreeJson(Boolean nocheck,Integer deptId);

}
