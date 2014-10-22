package com.gzzn.fgw.service.sys.pojo;

import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysOrganization;

public class SysDeptPojo {
	
	
	private Integer deptId;
	private SysOrganization sysOrganization;
	private String deptname;
	private Integer deleted;
	private String description;
	private String fullcode;
	private SysDept sysDept;//上级部门信息
	private Integer deptType;
	private Integer parentdeptid;
	private Integer levelnumber;
	private Integer sftzc;
	 private Integer sfxs;
	
	
	public Integer getParentdeptid() {
		return parentdeptid;
	}
	public void setParentdeptid(Integer parentdeptid) {
		this.parentdeptid = parentdeptid;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public SysOrganization getSysOrganization() {
		return sysOrganization;
	}
	public void setSysOrganization(SysOrganization sysOrganization) {
		this.sysOrganization = sysOrganization;
	}

	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFullcode() {
		return fullcode;
	}
	public void setFullcode(String fullcode) {
		this.fullcode = fullcode;
	}

	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public SysDept getSysDept() {
		return sysDept;
	}
	public void setSysDept(SysDept sysDept) {
		this.sysDept = sysDept;
	}
	public Integer getDeptType() {
		return deptType;
	}
	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}
	public Integer getLevelnumber() {
		return levelnumber;
	}
	public void setLevelnumber(Integer levelnumber) {
		this.levelnumber = levelnumber;
	}
	public Integer getSftzc() {
		return sftzc;
	}
	public void setSftzc(Integer sftzc) {
		this.sftzc = sftzc;
	}
	public Integer getSfxs() {
		return sfxs;
	}
	public void setSfxs(Integer sfxs) {
		this.sfxs = sfxs;
	}
	

}
