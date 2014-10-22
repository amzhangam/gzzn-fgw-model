package com.gzzn.fgw.service.sys;

import javax.annotation.Resource;

import org.junit.Test;

import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.SpringTest;
import com.gzzn.fgw.service.sys.ISysUserService;
import com.gzzn.util.security.Md5Encrypt;

/**
 * 
 * <p>Title: SysUserServiceImplTest</p>
 * <p>Description: SysUserServiceImpl测试类,继承 AbstractTransactionalJUnit4SpringContextTests:测试完毕后事物会回滚 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-20下午5:21:22  ChengZhi    new<p>
 */
public class SysUserServiceImplTest extends SpringTest {

	@Resource
	private ISysUserService sysUserService;
	@Resource
	private ICommonService commonService;

	@Test
	public void testLogin() {
		sysUserService.login("gbjd-01", Md5Encrypt.getPassMD5("123456"));
	}

	@Test
	public void test() {

		/**Set<SysSystem> systemSet = new HashSet<SysSystem>();
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

		List<SysSystem> systemList = sysUserService.getSystemModule();
		for (SysSystem sysSystem : systemList) {
			for (SysRolePermissionobject sysRolePermissionobject : sysSystem
					.getSysRolePermissionobjects()) {
				systemSet.add(sysRolePermissionobject.getSysSystem());
				moduleSet.add(sysRolePermissionobject.getSysModule());
				guiSet.add(sysRolePermissionobject.getSysGui());
				permissionSet.add(sysRolePermissionobject
						.getSysPermissionobject());
			}
		}

		Integer roleId = 525241l;
		SysRole role = commonService.findOne(SysRole.class, roleId);
		for (SysRolePermissionobject sysRolePermissionobject : role
				.getSysRolePermissionobjects()) {
			roleSystemSet.add(sysRolePermissionobject.getSysSystem());
			roleModuleSet.add(sysRolePermissionobject.getSysModule());
			roleGuiSet.add(sysRolePermissionobject.getSysGui());
			rolePermissionSet.add(sysRolePermissionobject
					.getSysPermissionobject());
		}

		for (SysSystem system : systemList) {
			dtoModuleSet = new HashSet<ModuleDTO>();
			for (SysModule module : system.getSysModules()) {
				dtoGuiSet = new HashSet<GuiDTO>();
				for (SysGui gui : module.getSysGuis()) {
					dtoPermissionSet = new HashSet<PermissionDTO>();
					for (SysPermissionobject permission : gui
							.getSysPermissionobjects()) {
						dtoPermissionSet.add(new PermissionDTO(permission,
								rolePermissionSet.contains(permission)));
					}
					dtoGuiSet.add(new GuiDTO(gui, dtoPermissionSet, roleGuiSet
							.contains(gui)));
				}
				dtoModuleSet.add(new ModuleDTO(module, dtoGuiSet, roleModuleSet
						.contains(module)));
			}
			dtoSystemSet.add(new SystemDTO(system, dtoModuleSet, roleSystemSet
					.contains(system)));
		}

		System.err.println("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");
		System.err.println("             system    role    dto");
		System.out.println("system    \t" + systemSet.size() + "\t"
				+ roleSystemSet.size() + "\t" + dtoSystemSet.size());
		System.out.println("module    \t" + moduleSet.size() + "\t"
				+ roleModuleSet.size() + "\t" + dtoModuleSet.size());
		System.out.println("gui       \t" + guiSet.size() + "\t"
				+ roleGuiSet.size() + "\t" + dtoGuiSet.size());
		System.out.println("permission\t" + permissionSet.size() + "\t"
				+ rolePermissionSet.size() + "\t" + dtoPermissionSet.size());
		System.err.println("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");

		// Collections.sort(dtoSystemSet);
		System.out
				.println(JSONUtil.getJSONArray(dtoSystemSet, null).toString());
		System.out.println(dtoSystemSet);

		*/
		
		/*
		 * List<Map<String,Object>> list = jdbc.find(
		 * "select distinct t.gui_id from SYS_PERMISSIONOBJECT t order by t.gui_id"
		 * ); for (Map<String, Object> map : list) {
		 * jdbc.update("insert into SYS_PERMISSIONOBJECT values(?,?,?,?)", new
		 * Object[]{Identities.getPkId(),map.get("GUI_ID"),4,4}); }
		 */
	}

}
