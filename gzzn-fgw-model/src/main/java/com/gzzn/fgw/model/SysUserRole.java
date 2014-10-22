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
 * SysUserRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_USER_ROLE"
    
)

public class SysUserRole  implements java.io.Serializable {


    // Fields    

     private Integer sysUserRoleId;
     private SysUser sysUser;
     private SysRole sysRole;


    // Constructors

    /** default constructor */
    public SysUserRole() {
    }

    
    /** full constructor */
    public SysUserRole(SysUser sysUser, SysRole sysRole) {
        this.sysUser = sysUser;
        this.sysRole = sysRole;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="SYS_USER_ROLE_ID", unique=true, nullable=false, scale=0)

    public Integer getSysUserRoleId() {
        return this.sysUserRoleId;
    }
    
    public void setSysUserRoleId(Integer sysUserRoleId) {
        this.sysUserRoleId = sysUserRoleId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="USER_ID", nullable=false)

    public SysUser getSysUser() {
        return this.sysUser;
    }
    
    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ROLE_ID", nullable=false)

    public SysRole getSysRole() {
        return this.sysRole;
    }
    
    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }
   








}