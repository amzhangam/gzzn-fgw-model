package com.gzzn.fgw.service.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.fgw.service.AbstractService;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysUserRoleService;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysUserRole;


@Service
@Transactional(readOnly = true)
public class SysUserRoleServiceImpl extends AbstractService<SysUserRole> implements ISysUserRoleService {

	@Autowired
	private IEntityDao dao;
	
	@Resource
	private ICommonService commonService;
	
	@Override
	public void saveOrDelete(String roleIds, Integer userId) {
		// TODO Auto-generated method stub
		SysUserRole sysUserRole=null;
		SysRole sysRole=null;
		SysUser sysUser=null;
		String ids[]=roleIds.split("@");
		/*查询以前的权限记录*/
		Condition con=new Condition("sysUser.userId", Operator.EQ, userId);
		List<SysUserRole> sysUserRoles=commonService.find(SysUserRole.class,con);
		//有就删除
		if(null!=sysUserRoles){
			for(SysUserRole info:sysUserRoles){
				commonService.delete(SysUserRole.class, info.getSysUserRoleId());
			}
		}
		//添加
		for(String info:ids){
			if(StringUtils.isNotEmpty(info)){
				sysUserRole=new SysUserRole();
				sysRole=new SysRole();
				sysRole.setRoleId(Integer.parseInt(info));
				sysUserRole.setSysRole(sysRole);
				sysUser=new SysUser();
				sysUser.setUserId(userId);
				sysUserRole.setSysUser(sysUser);
				commonService.saveOrUpdate(sysUserRole);
			}
		}
	}

	

}
