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
 * SysModule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_MODULE"
    
)

public class SysModule  implements java.io.Serializable {


    // Fields    

     private Integer moduleId;
     private SysSystem sysSystem;
     private SysModule parentModule;
     private String moduleName;
     private String moduleDesc;
     private String imgPath2;
     private String mnemonick2;
     private String toolImgPath;
     private String toolTip;
     private String url;
     private String target;
     private Set<SysPermissionobject> sysPermissionobjects = new HashSet<SysPermissionobject>(0);
     private Set<SysRolePermissionobject> sysRolePermissionobjects = new HashSet<SysRolePermissionobject>(0);


    // Constructors

    /** default constructor */
    public SysModule() {
    }

    
    /** full constructor */
    public SysModule(SysSystem sysSystem, SysModule parentModule, String moduleName, String moduleDesc, String imgPath2, String mnemonick2, String toolImgPath, String toolTip, String url, String target, Set<SysPermissionobject> sysPermissionobjects, Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.sysSystem = sysSystem;
        this.parentModule = parentModule;
        this.moduleName = moduleName;
        this.moduleDesc = moduleDesc;
        this.imgPath2 = imgPath2;
        this.mnemonick2 = mnemonick2;
        this.toolImgPath = toolImgPath;
        this.toolTip = toolTip;
        this.url = url;
        this.target = target;
        this.sysPermissionobjects = sysPermissionobjects;
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="MODULE_ID", unique=true, nullable=false, scale=0)

    public Integer getModuleId() {
        return this.moduleId;
    }
    
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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
    @JoinColumn(name="PARENT_MODULE_ID")
    public SysModule getParentModule() {
        return this.parentModule;
    }
    
    public void setParentModule(SysModule parentModule) {
        this.parentModule = parentModule;
    }
    
    @Column(name="MODULE_NAME")

    public String getModuleName() {
        return this.moduleName;
    }
    
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    @Column(name="MODULE_DESC")

    public String getModuleDesc() {
        return this.moduleDesc;
    }
    
    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }
    
    @Column(name="IMG_PATH2")

    public String getImgPath2() {
        return this.imgPath2;
    }
    
    public void setImgPath2(String imgPath2) {
        this.imgPath2 = imgPath2;
    }
    
    @Column(name="MNEMONICK2")

    public String getMnemonick2() {
        return this.mnemonick2;
    }
    
    public void setMnemonick2(String mnemonick2) {
        this.mnemonick2 = mnemonick2;
    }
    
    @Column(name="TOOL_IMG_PATH")

    public String getToolImgPath() {
        return this.toolImgPath;
    }
    
    public void setToolImgPath(String toolImgPath) {
        this.toolImgPath = toolImgPath;
    }
    
    @Column(name="TOOL_TIP")

    public String getToolTip() {
        return this.toolTip;
    }
    
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
    
    @Column(name="URL")

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="TARGET")

    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysModule")

    public Set<SysPermissionobject> getSysPermissionobjects() {
        return this.sysPermissionobjects;
    }
    
    public void setSysPermissionobjects(Set<SysPermissionobject> sysPermissionobjects) {
        this.sysPermissionobjects = sysPermissionobjects;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysModule")

    public Set<SysRolePermissionobject> getSysRolePermissionobjects() {
        return this.sysRolePermissionobjects;
    }
    
    public void setSysRolePermissionobjects(Set<SysRolePermissionobject> sysRolePermissionobjects) {
        this.sysRolePermissionobjects = sysRolePermissionobjects;
    }
   








}