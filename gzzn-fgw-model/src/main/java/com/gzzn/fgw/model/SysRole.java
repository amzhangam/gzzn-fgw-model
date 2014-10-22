package com.gzzn.fgw.model;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_ROLE"
    
)

public class SysRole  implements java.io.Serializable {


    // Fields    

     private Integer roleId;
     private Integer dataViewId;
     private String roleName;
     private String roleDesc;
     private Set<SysUserRole> sysUserRoles = new HashSet<SysUserRole>(0);
     private Set<SysRolePermissionobject> sysRolePermissionobjects = new HashSet<SysRolePermissionobject>(0);


    // Constructors

    /** default constructor */
    public SysRole() {
    }

    
    /** full constructor */
    public SysRole(Integer dataViewId, String roleName, String roleDesc, Set<SysUserRole> sysUserRoles, Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.dataViewId = dataViewId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.sysUserRoles = sysUserRoles;
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="ROLE_ID", unique=true, nullable=false, scale=0)

    public Integer getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    
    @Column(name="DATA_VIEW_ID", precision=22, scale=0)

    public Integer getDataViewId() {
        return this.dataViewId;
    }
    
    public void setDataViewId(Integer dataViewId) {
        this.dataViewId = dataViewId;
    }
    
    @Column(name="ROLE_NAME")

    public String getRoleName() {
        return this.roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @Column(name="ROLE_DESC")

    public String getRoleDesc() {
        return this.roleDesc;
    }
    
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysRole")

    public Set<SysUserRole> getSysUserRoles() {
        return this.sysUserRoles;
    }
    
    public void setSysUserRoles(Set<SysUserRole> sysUserRoles) {
        this.sysUserRoles = sysUserRoles;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysRole")

    public Set<SysRolePermissionobject> getSysRolePermissionobjects() {
        return this.sysRolePermissionobjects;
    }
    
    public void setSysRolePermissionobjects(Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }
   








}