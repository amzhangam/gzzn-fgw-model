package com.gzzn.fgw.model;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * SysPermissionobject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_PERMISSIONOBJECT"
    
)

public class SysPermissionobject  implements java.io.Serializable {


    // Fields    

     private Integer permissionobjectId;
     private SysModule sysModule;
     private String permissionobjectName;
     private String permissionobjectDesc;
     private String url;
     private String permissionobjectCode;
     private Set<SysRolePermissionobject> sysRolePermissionobjects = new HashSet<SysRolePermissionobject>(0);


    // Constructors

    /** default constructor */
    public SysPermissionobject() {
    }

    
    /** full constructor */
    public SysPermissionobject(SysModule sysModule, String permissionobjectName, String permissionobjectDesc, String url, String permissionobjectCode, Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.sysModule = sysModule;
        this.permissionobjectName = permissionobjectName;
        this.permissionobjectDesc = permissionobjectDesc;
        this.url = url;
        this.permissionobjectCode = permissionobjectCode;
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="PERMISSIONOBJECT_ID", unique=true, nullable=false, scale=0)

    public Integer getPermissionobjectId() {
        return this.permissionobjectId;
    }
    
    public void setPermissionobjectId(Integer permissionobjectId) {
        this.permissionobjectId = permissionobjectId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="MODULE_ID")

    public SysModule getSysModule() {
        return this.sysModule;
    }
    
    public void setSysModule(SysModule sysModule) {
        this.sysModule = sysModule;
    }
    
    @Column(name="PERMISSIONOBJECT_NAME")

    public String getPermissionobjectName() {
        return this.permissionobjectName;
    }
    
    public void setPermissionobjectName(String permissionobjectName) {
        this.permissionobjectName = permissionobjectName;
    }
    
    @Column(name="PERMISSIONOBJECT_DESC")

    public String getPermissionobjectDesc() {
        return this.permissionobjectDesc;
    }
    
    public void setPermissionobjectDesc(String permissionobjectDesc) {
        this.permissionobjectDesc = permissionobjectDesc;
    }
    
    @Column(name="URL")

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="PERMISSIONOBJECT_CODE")

    public String getPermissionobjectCode() {
        return this.permissionobjectCode;
    }
    
    public void setPermissionobjectCode(String permissionobjectCode) {
        this.permissionobjectCode = permissionobjectCode;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysPermissionobject")

    public Set<SysRolePermissionobject> getSysRolePermissionobjects() {
        return this.sysRolePermissionobjects;
    }
    
    public void setSysRolePermissionobjects(Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }
   








}