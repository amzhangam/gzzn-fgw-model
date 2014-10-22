package com.gzzn.fgw.service.sys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysRolePermissionobject;
import com.gzzn.fgw.model.SysSystem;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.pojo.TreeNodesPojo;
import com.gzzn.fgw.persist.ISysUserDao;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.util.common.LogUtil;

/**
 * 
 * <p>Title: SysUserServiceImpl</p>
 * <p>Description: sysUser 接口实现</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-20下午5:18:51  ChengZhi    new<p>
 */
@Service
@Transactional(readOnly = true)
public class SysUserServiceImpl extends AbstractService<SysUser> implements ISysUserService {

	@Resource
	private ISysUserDao userDao;
	@Resource
	private IEntityDao entityDao;
	@Resource
	private ICommonService commonService;

	public SysUser login(String loginName, String loginPwd) {
		List<SysUser> list = userDao.findByLoginNameAndLoginPwd(loginName, loginPwd);
		SysUser user = null;
		try {
			if (CollectionUtils.isNotEmpty(list)) {
				user = list.get(0);
			} else {
//				LogUtil.getLogger().debug("查询结果为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public List<SysSystem> getSystemModule() {
		return entityDao.findAll(SysSystem.class);
	}

	@Override
	public List<SysSystem> getUserPermission(SysUser user) {
		/**Set<SysRole> sysRoleSet = new HashSet<SysRole>();

		Set<SysSystem> sysSystemSet = new HashSet<SysSystem>();
		Set<SysModule> sysModuleSet = new HashSet<SysModule>();
		Set<SysGui> sysGuiSet = new HashSet<SysGui>();
		Set<SysPermissionobject> sysPermissionobjectSet = new HashSet<SysPermissionobject>();

		// 获取用户权限
		for (SysRole sysUserRole : user.getSysRoles()) {
			for (SysRolePermissionobject sysRolePermissionobject : sysUserRole
					.getSysRolePermissionobjects()) {
				sysRoleSet.add(sysRolePermissionobject.getSysRole());
				sysSystemSet.add(sysRolePermissionobject.getSysSystem());
				sysModuleSet.add(sysRolePermissionobject.getSysModule());
				sysGuiSet.add(sysRolePermissionobject.getSysGui());
				sysPermissionobjectSet.add(sysRolePermissionobject.getSysPermissionobject());
			}
		}

		for (SysSystem sysSystem : sysSystemSet) {
			for (Iterator<SysModule> systemIterator = sysSystem.getSysModules().iterator(); systemIterator
					.hasNext();) {
				SysModule sysModule = (SysModule) systemIterator.next();
				if (!sysModuleSet.contains(sysModule)) {
					systemIterator.remove();
				} else {
					for (Iterator<SysGui> guiIterator = sysModule.getSysGuis().iterator(); guiIterator
							.hasNext();) {
						SysGui sysGui = (SysGui) guiIterator.next();
						if (!sysGuiSet.contains(sysGui)) {
							guiIterator.remove();
						} else {
							for (Iterator<SysPermissionobject> permissionIterator = sysGui
									.getSysPermissionobjects().iterator(); permissionIterator
									.hasNext();) {
								if (!sysPermissionobjectSet
										.contains((SysPermissionobject) permissionIterator.next())) {
									permissionIterator.remove();
								}
							}
						}
					}
				}
			}
		}
		return CollectionUtils.isNotEmpty(sysSystemSet) ? new ArrayList<SysSystem>(sysSystemSet)
				: new ArrayList<SysSystem>();*/
		return null;
	}

	@Override
	public List<TreeNodesPojo> getUserMenu(SysUser user, String systemCode) {
		/**List<TreeNodesPojo> list = new ArrayList<TreeNodesPojo>();
		TreeNodesPojo obj = null;
		List<SysSystem> listSys = getUserPermission(user);// 获取该用户所具备的的全部权限

		for (SysSystem sysSystem : listSys) {
			// 系统信息
			if ((systemCode != null && sysSystem.getSystemCode().equalsIgnoreCase(systemCode))
					|| systemCode == null) {
				obj = new TreeNodesPojo();
				obj.setId("s" + sysSystem.getSystemId().toString());
				obj.setpId("0");
				obj.setName(sysSystem.getSystemName());
				obj.setOpen(true);
				list.add(obj);

				for (Iterator<SysModule> systemIterator = sysSystem.getSysModules().iterator(); systemIterator
						.hasNext();) {
					SysModule sysModule = (SysModule) systemIterator.next();
					// 模块信息
					obj = new TreeNodesPojo();
					obj.setId("m" + sysModule.getModuleId().toString());
					obj.setpId("s" + sysSystem.getSystemId().toString());
					obj.setName(sysModule.getModuleName());
					obj.setIcon(sysModule.getImgPath2());
					list.add(obj);

					for (Iterator<SysGui> guiIterator = sysModule.getSysGuis().iterator(); guiIterator
							.hasNext();) {
						SysGui sysGui = (SysGui) guiIterator.next();
						// 界面信息
						obj = new TreeNodesPojo();
						obj.setId("g" + sysGui.getGuiId().toString());
						obj.setpId("m" + sysModule.getModuleId().toString());
						obj.setName(sysGui.getGuiName());
						obj.setIcon(sysGui.getImgPath());
						obj.setFile(sysGui.getGuiPath());
						list.add(obj);
					}
				}
			}
		}
		java.util.Collections.sort(list);

		return list;*/
		return null;
	}

	@Override
	public List<SysRole> getSysRoleAll() {
		return entityDao.findAll(SysRole.class);
	}

	@Override
	public List<SysSystem> getRolePermission(Integer sysRoleId) {
		Set<SysSystem> sysSystemSet = new HashSet<SysSystem>();

		SysRole role = entityDao.findOne(SysRole.class, sysRoleId);
		for (SysRolePermissionobject rolePermissionobject : role.getSysRolePermissionobjects()) {
			sysSystemSet.add(rolePermissionobject.getSysSystem());
		}
		return CollectionUtils.isNotEmpty(sysSystemSet) ? new ArrayList<SysSystem>(sysSystemSet)
				: new ArrayList<SysSystem>();
	}
	
	/**
	 * 获取指定处室的经办人员
	 * @param nocheck
	 * @param deptId
	 * @return
	 */
	@Override
	public String findHandlerTreeJson(Boolean nocheck,Integer deptId){
		Condition con = new Condition();
		con.add("sysDept", Operator.ISNOTNULL,null);
		con.add("sysDept.deptId", Operator.EQ,deptId);
		con.add("roleType", Operator.ISNOTNULL,null);
		con.add("roleType", Operator.EQ,IConstants.ROLE_TYPE_FGW_CODE_3);
		List<SysUser> list = commonService.find(SysUser.class, con);
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		if(list!=null&&!list.isEmpty()){
			for(SysUser user:list){
				TreeNodesPojo objTree = new TreeNodesPojo();
				objTree.setId(String.valueOf(user.getUserId()));
				objTree.setName(user.getUserName());
				listTree.add(objTree);
			}
		}
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 获取用户类型信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	@Override
	public String findUserTypeTreeJson(Boolean nocheck){
		Condition con = new Condition();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		TreeNodesPojo objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.USER_TYPE_1));
		objTree.setName(IConstants.USER_TYPE_NAME_1);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.USER_TYPE_2));
		objTree.setName(IConstants.USER_TYPE_NAME_2);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.USER_TYPE_3));
		objTree.setName(IConstants.USER_TYPE_NAME_3);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.USER_TYPE_4));
		objTree.setName(IConstants.USER_TYPE_NAME_4);
		listTree.add(objTree);
		
		/**objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.USER_TYPE_5));
		objTree.setName(IConstants.USER_TYPE_NAME_5);
		listTree.add(objTree);*/
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 获取用户状态信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	@Override
	public String findUseStatusTreeJson(Boolean nocheck){
		Condition con = new Condition();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		TreeNodesPojo objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.SHENHE_STATUS_1));
		objTree.setName(IConstants.SHENHE_STATUS_NAME_1);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.SHENHE_STATUS_2));
		objTree.setName(IConstants.SHENHE_STATUS_NAME_2);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.SHENHE_STATUS_3));
		objTree.setName(IConstants.SHENHE_STATUS_NAME_3);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.SHENHE_STATUS_4));
		objTree.setName(IConstants.SHENHE_STATUS_NAME_4);
		listTree.add(objTree);
		
		return new Gson().toJson(listTree);
	}
	
	/**
	 * 获取角色类型信息的树形list：按照ID排序
	 * @param nocheck 父节点的单选或复选按钮是否显示
	 * @return
	 */
	@Override
	public String findRoleTypeTreeJson(Boolean nocheck){
		Condition con = new Condition();
		
		List<TreeNodesPojo>  listTree = new ArrayList<TreeNodesPojo>();
		TreeNodesPojo objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.ROLE_TYPE_FGW_CODE_1));
		objTree.setName(IConstants.ROLE_TYPE_FGW_NAME_1);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.ROLE_TYPE_FGW_CODE_2));
		objTree.setName(IConstants.ROLE_TYPE_FGW_NAME_2);
		listTree.add(objTree);
		
		objTree = new TreeNodesPojo();
		objTree.setId(String.valueOf(IConstants.ROLE_TYPE_FGW_CODE_3));
		objTree.setName(IConstants.ROLE_TYPE_FGW_NAME_3);
		listTree.add(objTree);
		
		return new Gson().toJson(listTree);
	}
	

	//@Override
	//public Set<SystemDTO> getRolePermissionDTO(Integer sysRoleId) {

		/**SysRole role = entityDao.findOne(SysRole.class, sysRoleId);
		if (role != null && role.getSysRolePermissionobjects().size() > 0) {
			Set<SysSystem> systemSet = new HashSet<SysSystem>();
			Set<SysModule> moduleSet = new HashSet<SysModule>();
			Set<SysGui> guiSet = new HashSet<SysGui>();
			Set<SysPermissionobject> permissionSet = new HashSet<SysPermissionobject>();

			Set<SysSystem> roleSystemSet = new HashSet<SysSystem>();
			Set<SysModule> roleModuleSet = new HashSet<SysModule>();
			Set<SysGui> roleGuiSet = new HashSet<SysGui>();
			Set<SysPermissionobject> rolePermissionSet = new HashSet<SysPermissionobject>();

			Set<SystemDTO> dtoSystemSet = new TreeSet<SystemDTO>();
			Set<ModuleDTO> dtoModuleSet = null;
			Set<GuiDTO> dtoGuiSet = null;
			Set<PermissionDTO> dtoPermissionSet = null;

			List<SysSystem> systemList = getSystemModule();
			for (SysSystem sysSystem : systemList) {
				for (SysRolePermissionobject sysRolePermissionobject : sysSystem
						.getSysRolePermissionobjects()) {
					systemSet.add(sysRolePermissionobject.getSysSystem());
					moduleSet.add(sysRolePermissionobject.getSysModule());
					guiSet.add(sysRolePermissionobject.getSysGui());
					permissionSet.add(sysRolePermissionobject.getSysPermissionobject());
				}
			}

			for (SysRolePermissionobject sysRolePermissionobject : role
					.getSysRolePermissionobjects()) {
				roleSystemSet.add(sysRolePermissionobject.getSysSystem());
				roleModuleSet.add(sysRolePermissionobject.getSysModule());
				roleGuiSet.add(sysRolePermissionobject.getSysGui());
				rolePermissionSet.add(sysRolePermissionobject.getSysPermissionobject());
			}

			for (SysSystem system : systemList) {
				dtoModuleSet = new TreeSet<ModuleDTO>();
				for (SysModule module : system.getSysModules()) {
					dtoGuiSet = new TreeSet<GuiDTO>();
					for (SysGui gui : module.getSysGuis()) {
						dtoPermissionSet = new TreeSet<PermissionDTO>();
						for (SysPermissionobject permission : gui.getSysPermissionobjects()) {
							dtoPermissionSet.add(new PermissionDTO(permission, rolePermissionSet
									.contains(permission)));
						}
						dtoGuiSet.add(new GuiDTO(gui, dtoPermissionSet, roleGuiSet.contains(gui)));
					}
					dtoModuleSet.add(new ModuleDTO(module, dtoGuiSet, roleModuleSet
							.contains(module)));
				}
				dtoSystemSet
						.add(new SystemDTO(system, dtoModuleSet, roleSystemSet.contains(system)));
			}
			return dtoSystemSet;
		}*/
		//return null;
	//}

//	@Override
//	public String getCheckLevel(Integer userId) {
//		// TODO Auto-generated method stub
//		Condition con=null;
//		con=new Condition("sysUser.userId", Operator.EQ, userId);
//		List<SysUserRole> sysUserRoles=commonService.find(SysUserRole.class, con);
//		String levelStr="";
//		for(SysUserRole info:sysUserRoles){
//			if(info.getSysRole()!=null){
//				con=new Condition("roleId", Operator.EQ, info.getSysRole().getRoleId());
//				SysRole sysRole=commonService.findOne(SysRole.class, con);
//				System.out.println(sysRole.getSysDataLevel().getDataLevel());
//				if(sysRole.getSysDataLevel()!=null){
//					levelStr=String.valueOf(sysRole.getSysDataLevel().getDataLevel());
//					levelStr+="@";
//				}
//			}
//		}
//		//levelStr.length()>0?levelStr.substring(0, levelStr.length()):"";
//		return levelStr;
//	}

}
