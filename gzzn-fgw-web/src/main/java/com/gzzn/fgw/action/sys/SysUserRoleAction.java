package com.gzzn.fgw.action.sys;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.model.SysRole;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysUserRole;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysUserRoleService;

@Action("/sys")
public class SysUserRoleAction extends BaseAction<SysUserRole> {
	
	private SysUserRole dto=new SysUserRole();
	
	@Autowired
	private ICommonService commonService;
	
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	private String ids;
	
	@Action("sysUserRoleAdd")
	public void sysUserRoleAdd(){
		SysUserRole sysUserRole=null;
		SysRole sysRole=null;
		SysUser sysUser=null;
		try{
			if(StringUtils.isNotEmpty(this.ids)){
				//sysUserRoleService.saveOrDelete(this.ids, this.dto.getSysUser().getUserId());
				String ids[]=this.ids.split("@");
				/*查询以前的权限记录*/
				Condition con=new Condition("sysUser.userId", Operator.EQ, this.dto.getSysUser().getUserId());
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
						sysUser.setUserId(this.dto.getSysUser().getUserId());
						sysUserRole.setSysUser(sysUser);
						commonService.saveOrUpdate(sysUserRole);
					}
				}
			}
			outJsonString("{\"success\":true,\"info\":\"添加成功\"}");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			outJsonString("{\"success\":false,\"info\":\"添加失败\"}");
		}
		
	}
	
	@Override
	public SysUserRole getModel() {
		// TODO Auto-generated method stub
		return dto;
	}
	
	public SysUserRole getDto() {
		return dto;
	}

	public void setDto(SysUserRole dto) {
		this.dto = dto;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
