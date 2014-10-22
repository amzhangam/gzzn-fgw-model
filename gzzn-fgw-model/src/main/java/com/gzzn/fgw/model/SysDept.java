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
 * SysDept entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_DEPT"
    
)

public class SysDept  implements java.io.Serializable {


    // Fields    

     private Integer deptId;
     private SysOrganization sysOrganization;
     private String deptname;
     private Integer deleted;
     private String description;
     private String fullcode;
     private Integer parentdeptid;
     private Integer levelnumber;
     private Integer sftzc;
     private Integer sfxs;
     private Set<SysUser> sysUsers = new HashSet<SysUser>(0);
     private Set<SysDx> sysDxesForReceiveDeptId = new HashSet<SysDx>(0);
     private Set<SysDx> sysDxesForDeptId = new HashSet<SysDx>(0);
     private Set<Pjauditlog> pjauditlogsForSenddeptid = new HashSet<Pjauditlog>(0);
     private Set<SysProjectlog> sysProjectlogs = new HashSet<SysProjectlog>(0);
     private Set<SysOperationlog> sysOperationlogs = new HashSet<SysOperationlog>(0);
     private Set<Pjauditlog> pjauditlogsForDeptId = new HashSet<Pjauditlog>(0);


    // Constructors

    /** default constructor */
    public SysDept() {
    }

    
    /** full constructor */
    public SysDept(SysOrganization sysOrganization, String deptname, Integer deleted, 
    		String description, String fullcode, Integer parentdeptid, 
    		Integer levelnumber, Integer sftzc,Integer sfxs,
    		Set<SysUser> sysUsers, Set<SysDx> sysDxesForReceiveDeptId, Set<SysDx> sysDxesForDeptId, Set<Pjauditlog> pjauditlogsForSenddeptid, Set<SysProjectlog> sysProjectlogs, Set<SysOperationlog> sysOperationlogs, Set<Pjauditlog> pjauditlogsForDeptId) {
        this.sysOrganization = sysOrganization;
        this.deptname = deptname;
        this.deleted = deleted;
        this.description = description;
        this.fullcode = fullcode;
        this.parentdeptid = parentdeptid;
        this.levelnumber = levelnumber;
        this.sftzc = sftzc;
        this.sfxs = sfxs;
        this.sysUsers = sysUsers;
        this.sysDxesForReceiveDeptId = sysDxesForReceiveDeptId;
        this.sysDxesForDeptId = sysDxesForDeptId;
        this.pjauditlogsForSenddeptid = pjauditlogsForSenddeptid;
        this.sysProjectlogs = sysProjectlogs;
        this.sysOperationlogs = sysOperationlogs;
        this.pjauditlogsForDeptId = pjauditlogsForDeptId;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="DEPT_ID", unique=true, nullable=false, scale=0)

    public Integer getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ORGANIZATION_ID")

    public SysOrganization getSysOrganization() {
        return this.sysOrganization;
    }
    
    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }
    
    @Column(name="DEPTNAME")

    public String getDeptname() {
        return this.deptname;
    }
    
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
    
    @Column(name="DELETED", precision=22, scale=0)

    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    
    @Column(name="DESCRIPTION")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="FULLCODE", length=20)

    public String getFullcode() {
        return this.fullcode;
    }
    
    public void setFullcode(String fullcode) {
        this.fullcode = fullcode;
    }
    
    @Column(name="PARENTDEPTID", scale=0)

    public Integer getParentdeptid() {
        return this.parentdeptid;
    }
    
    public void setParentdeptid(Integer parentdeptid) {
        this.parentdeptid = parentdeptid;
    }
    
    @Column(name="LEVELNUMBER", precision=1, scale=0)

    public Integer getLevelnumber() {
        return this.levelnumber;
    }
    
    public void setLevelnumber(Integer levelnumber) {
        this.levelnumber = levelnumber;
    }
    
    @Column(name="SFTZC", precision=1, scale=0)

    public Integer getSftzc() {
        return this.sftzc;
    }
    
    public void setSftzc(Integer sftzc) {
        this.sftzc = sftzc;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDept")

    public Set<SysUser> getSysUsers() {
        return this.sysUsers;
    }
    
    public void setSysUsers(Set<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDeptByReceiveDeptId")

    public Set<SysDx> getSysDxesForReceiveDeptId() {
        return this.sysDxesForReceiveDeptId;
    }
    
    public void setSysDxesForReceiveDeptId(Set<SysDx> sysDxesForReceiveDeptId) {
        this.sysDxesForReceiveDeptId = sysDxesForReceiveDeptId;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDeptByDeptId")

    public Set<SysDx> getSysDxesForDeptId() {
        return this.sysDxesForDeptId;
    }
    
    public void setSysDxesForDeptId(Set<SysDx> sysDxesForDeptId) {
        this.sysDxesForDeptId = sysDxesForDeptId;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDeptBySenddeptid")

    public Set<Pjauditlog> getPjauditlogsForSenddeptid() {
        return this.pjauditlogsForSenddeptid;
    }
    
    public void setPjauditlogsForSenddeptid(Set<Pjauditlog> pjauditlogsForSenddeptid) {
        this.pjauditlogsForSenddeptid = pjauditlogsForSenddeptid;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDept")

    public Set<SysProjectlog> getSysProjectlogs() {
        return this.sysProjectlogs;
    }
    
    public void setSysProjectlogs(Set<SysProjectlog> sysProjectlogs) {
        this.sysProjectlogs = sysProjectlogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDept")

    public Set<SysOperationlog> getSysOperationlogs() {
        return this.sysOperationlogs;
    }
    
    public void setSysOperationlogs(Set<SysOperationlog> sysOperationlogs) {
        this.sysOperationlogs = sysOperationlogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDeptByDeptId")

    public Set<Pjauditlog> getPjauditlogsForDeptId() {
        return this.pjauditlogsForDeptId;
    }
    
    public void setPjauditlogsForDeptId(Set<Pjauditlog> pjauditlogsForDeptId) {
        this.pjauditlogsForDeptId = pjauditlogsForDeptId;
    }
   

    @Column(name="SFXS", precision=1)
	public Integer getSfxs() {
		return sfxs;
	}


	public void setSfxs(Integer sfxs) {
		this.sfxs = sfxs;
	}
   







}