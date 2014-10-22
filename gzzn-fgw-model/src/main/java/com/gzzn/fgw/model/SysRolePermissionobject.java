package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * SysRolePermissionobject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_ROLE_PERMISSIONOBJECT"
    
)

public class SysRolePermissionobject  implements java.io.Serializable {


    // Fields    

     private Integer rolePermissionId;
     private SysRole sysRole;
     private SysModule sysModule;
     private SysSystem sysSystem;
     private SysPermissionobject sysPermissionobject;


    // Constructors

    /** default constructor */
    public SysRolePermissionobject() {
    }

    
    /** full constructor */
    public SysRolePermissionobject(SysRole sysRole, SysModule sysModule, SysSystem sysSystem, SysPermissionobject sysPermissionobject) {
        this.sysRole = sysRole;
        this.sysModule = sysModule;
        this.sysSystem = sysSystem;
        this.sysPermissionobject = sysPermissionobject;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="ROLE_PERMISSION_ID", unique=true, nullable=false, scale=0)

    public Integer getRolePermissionId() {
        return this.rolePermissionId;
    }
    
    public void setRolePermissionId(Integer rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="ROLE_ID")

    public SysRole getSysRole() {
        return this.sysRole;
    }
    
    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="MODULE_ID")

    public SysModule getSysModule() {
        return this.sysModule;
    }
    
    public void setSysModule(SysModule sysModule) {
        this.sysModule = sysModule;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="SYSTEM_ID")

    public SysSystem getSysSystem() {
        return this.sysSystem;
    }
    
    public void setSysSystem(SysSystem sysSystem) {
        this.sysSystem = sysSystem;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="PERMISSIONOBJECT_ID")

    public SysPermissionobject getSysPermissionobject() {
        return this.sysPermissionobject;
    }
    
    public void setSysPermissionobject(SysPermissionobject sysPermissionobject) {
        this.sysPermissionobject = sysPermissionobject;
    }
   








}