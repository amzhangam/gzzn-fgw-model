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
 * SysSystem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_SYSTEM"
    
)

public class SysSystem  implements java.io.Serializable {


    // Fields    

     private Integer systemId;
     private String systemCode;
     private String systemName;
     private Integer useStatus;
     private String memo;
     private Set<SysModule> sysModules = new HashSet<SysModule>(0);
     private Set<SysRolePermissionobject> sysRolePermissionobjects = new HashSet<SysRolePermissionobject>(0);


    // Constructors

    /** default constructor */
    public SysSystem() {
    }

    
    /** full constructor */
    public SysSystem(String systemCode, String systemName, Integer useStatus, String memo, Set<SysModule> sysModules, Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.systemCode = systemCode;
        this.systemName = systemName;
        this.useStatus = useStatus;
        this.memo = memo;
        this.sysModules = sysModules;
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="SYSTEM_ID", unique=true, nullable=false, scale=0)

    public Integer getSystemId() {
        return this.systemId;
    }
    
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
    
    @Column(name="SYSTEM_CODE")

    public String getSystemCode() {
        return this.systemCode;
    }
    
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
    
    @Column(name="SYSTEM_NAME")

    public String getSystemName() {
        return this.systemName;
    }
    
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    @Column(name="USE_STATUS", precision=22, scale=0)

    public Integer getUseStatus() {
        return this.useStatus;
    }
    
    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }
    
    @Column(name="MEMO")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysSystem")

    public Set<SysModule> getSysModules() {
        return this.sysModules;
    }
    
    public void setSysModules(Set<SysModule> sysModules) {
        this.sysModules = sysModules;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysSystem")

    public Set<SysRolePermissionobject> getSysRolePermissionobjects() {
        return this.sysRolePermissionobjects;
    }
    
    public void setSysRolePermissionobjects(Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }
   








}