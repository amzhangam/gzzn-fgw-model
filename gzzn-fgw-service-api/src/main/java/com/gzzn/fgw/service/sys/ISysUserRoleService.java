package com.gzzn.fgw.service.sys;

import com.gzzn.fgw.service.IPagedService;
import com.gzzn.fgw.model.SysUserRole;

/**
 * 
 * <p>Title: ISysUserRoleService</p>
 * <p>Description: TODO 用户对应角色</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lxb
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-19 下午3:11:51  lxb    new
 */
public interface ISysUserRoleService extends IPagedService<SysUserRole> {

	public void saveOrDelete(String roleIds,Integer userId);

}
