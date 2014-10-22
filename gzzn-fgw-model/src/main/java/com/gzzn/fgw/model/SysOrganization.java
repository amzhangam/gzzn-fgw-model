package com.gzzn.fgw.model;


import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SysOrganization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_ORGANIZATION"
    
)

public class SysOrganization  implements java.io.Serializable {


    // Fields    

     private Integer organizationId;
     private SysXq sysXq;
     private String organizationName;
     private Integer workunitstype;
     private String workunitsaddress;
     private String workunitsregistercode;
     private Integer workunitsquality;
     private String workunitsperson;
     private String workunitslinkman;
     private String workunitslinkmantel;
     private Integer workunitsstatus;
     private Integer parentOrganizationId;
     private Set<Pjbaseinfo> pjbaseinfosForDeclareunitsid = new HashSet<Pjbaseinfo>(0);
     private Set<SysProjectlog> sysProjectlogs = new HashSet<SysProjectlog>(0);
     private Set<SysDept> sysDepts = new HashSet<SysDept>(0);
     private Set<Pjbaseinfo> pjbaseinfosForDirectorunitsid = new HashSet<Pjbaseinfo>(0);
     private Set<SysDx> sysDxes = new HashSet<SysDx>(0);
     private Set<SysOperationlog> sysOperationlogs = new HashSet<SysOperationlog>(0);
     private Set<Pjauditlog> pjauditlogsForPjauditunits = new HashSet<Pjauditlog>(0);
     private Set<Pjauditlog> pjauditlogsForOrganizationId = new HashSet<Pjauditlog>(0);
     private Set<SysUser> sysUsers = new HashSet<SysUser>(0);
     private Date modifiedTime;

    // Constructors

    /** default constructor */
    public SysOrganization() {
    }

    
    /** full constructor */
    public SysOrganization(SysXq sysXq, String organizationName, Integer workunitstype, 
    		String workunitsaddress, String workunitsregistercode, Integer workunitsquality,
    		String workunitsperson, String workunitslinkman, String workunitslinkmantel, 
    		Integer workunitsstatus, Integer parentOrganizationId, Date modifiedTime,
    		Set<Pjbaseinfo> pjbaseinfosForDeclareunitsid,  Set<SysProjectlog> sysProjectlogs, Set<SysDept> sysDepts, Set<Pjbaseinfo> pjbaseinfosForDirectorunitsid, Set<SysDx> sysDxes, Set<SysOperationlog> sysOperationlogs, Set<Pjauditlog> pjauditlogsForPjauditunits, Set<Pjauditlog> pjauditlogsForOrganizationId, Set<SysUser> sysUsers) {
        this.sysXq = sysXq;
        this.organizationName = organizationName;
        this.workunitstype = workunitstype;
        this.workunitsaddress = workunitsaddress;
        this.workunitsregistercode = workunitsregistercode;
        this.workunitsquality = workunitsquality;
        this.workunitsperson = workunitsperson;
        this.workunitslinkman = workunitslinkman;
        this.workunitslinkmantel = workunitslinkmantel;
        this.workunitsstatus = workunitsstatus;
        this.parentOrganizationId = parentOrganizationId;
        this.pjbaseinfosForDeclareunitsid = pjbaseinfosForDeclareunitsid;
        this.sysProjectlogs = sysProjectlogs;
        this.sysDepts = sysDepts;
        this.pjbaseinfosForDirectorunitsid = pjbaseinfosForDirectorunitsid;
        this.sysDxes = sysDxes;
        this.modifiedTime = modifiedTime;
        this.sysOperationlogs = sysOperationlogs;
        this.pjauditlogsForPjauditunits = pjauditlogsForPjauditunits;
        this.pjauditlogsForOrganizationId = pjauditlogsForOrganizationId;
        this.sysUsers = sysUsers;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="ORGANIZATION_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getOrganizationId() {
        return this.organizationId;
    }
    
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }
	@ManyToOne(fetch=FetchType.EAGER)//LAZY
        @JoinColumn(name="XQ_ID")

    public SysXq getSysXq() {
        return this.sysXq;
    }
    
    public void setSysXq(SysXq sysXq) {
        this.sysXq = sysXq;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFIED_TIME", length=7)
    public Date getModifiedTime() {
		return modifiedTime;
	}


	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
    @Column(name="ORGANIZATION_NAME", length=150)

    public String getOrganizationName() {
        return this.organizationName;
    }
    
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    
    @Column(name="WORKUNITSTYPE", precision=1, scale=0)

    public Integer getWorkunitstype() {
        return this.workunitstype;
    }
    
    public void setWorkunitstype(Integer workunitstype) {
        this.workunitstype = workunitstype;
    }
    
    @Column(name="WORKUNITSADDRESS", length=300)

    public String getWorkunitsaddress() {
        return this.workunitsaddress;
    }
    
    public void setWorkunitsaddress(String workunitsaddress) {
        this.workunitsaddress = workunitsaddress;
    }
    
    @Column(name="WORKUNITSREGISTERCODE", length=40)

    public String getWorkunitsregistercode() {
        return this.workunitsregistercode;
    }
    
    public void setWorkunitsregistercode(String workunitsregistercode) {
        this.workunitsregistercode = workunitsregistercode;
    }
    
    @Column(name="WORKUNITSQUALITY", precision=22, scale=0)

    public Integer getWorkunitsquality() {
        return this.workunitsquality;
    }
    
    public void setWorkunitsquality(Integer workunitsquality) {
        this.workunitsquality = workunitsquality;
    }
    
    @Column(name="WORKUNITSPERSON", length=50)

    public String getWorkunitsperson() {
        return this.workunitsperson;
    }
    
    public void setWorkunitsperson(String workunitsperson) {
        this.workunitsperson = workunitsperson;
    }
    
    @Column(name="WORKUNITSLINKMAN", length=50)

    public String getWorkunitslinkman() {
        return this.workunitslinkman;
    }
    
    public void setWorkunitslinkman(String workunitslinkman) {
        this.workunitslinkman = workunitslinkman;
    }
    
    @Column(name="WORKUNITSLINKMANTEL", length=50)

    public String getWorkunitslinkmantel() {
        return this.workunitslinkmantel;
    }
    
    public void setWorkunitslinkmantel(String workunitslinkmantel) {
        this.workunitslinkmantel = workunitslinkmantel;
    }
    
    @Column(name="WORKUNITSSTATUS", precision=22, scale=0)

    public Integer getWorkunitsstatus() {
        return this.workunitsstatus;
    }
    
    public void setWorkunitsstatus(Integer workunitsstatus) {
        this.workunitsstatus = workunitsstatus;
    }
    
    @Column(name="PARENT_ORGANIZATION_ID", precision=22, scale=0)

    public Integer getParentOrganizationId() {
        return this.parentOrganizationId;
    }
    
    public void setParentOrganizationId(Integer parentOrganizationId) {
        this.parentOrganizationId = parentOrganizationId;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganizationByDeclareunitsid")

    public Set<Pjbaseinfo> getPjbaseinfosForDeclareunitsid() {
        return this.pjbaseinfosForDeclareunitsid;
    }
    
    public void setPjbaseinfosForDeclareunitsid(Set<Pjbaseinfo> pjbaseinfosForDeclareunitsid) {
        this.pjbaseinfosForDeclareunitsid = pjbaseinfosForDeclareunitsid;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganization")

    public Set<SysProjectlog> getSysProjectlogs() {
        return this.sysProjectlogs;
    }
    
    public void setSysProjectlogs(Set<SysProjectlog> sysProjectlogs) {
        this.sysProjectlogs = sysProjectlogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganization")

    public Set<SysDept> getSysDepts() {
        return this.sysDepts;
    }
    
    public void setSysDepts(Set<SysDept> sysDepts) {
        this.sysDepts = sysDepts;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganizationByDirectorunitsid")

    public Set<Pjbaseinfo> getPjbaseinfosForDirectorunitsid() {
        return this.pjbaseinfosForDirectorunitsid;
    }
    
    public void setPjbaseinfosForDirectorunitsid(Set<Pjbaseinfo> pjbaseinfosForDirectorunitsid) {
        this.pjbaseinfosForDirectorunitsid = pjbaseinfosForDirectorunitsid;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganization")

    public Set<SysDx> getSysDxes() {
        return this.sysDxes;
    }
    
    public void setSysDxes(Set<SysDx> sysDxes) {
        this.sysDxes = sysDxes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganization")

    public Set<SysOperationlog> getSysOperationlogs() {
        return this.sysOperationlogs;
    }
    
    public void setSysOperationlogs(Set<SysOperationlog> sysOperationlogs) {
        this.sysOperationlogs = sysOperationlogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganizationByPjauditunits")

    public Set<Pjauditlog> getPjauditlogsForPjauditunits() {
        return this.pjauditlogsForPjauditunits;
    }
    
    public void setPjauditlogsForPjauditunits(Set<Pjauditlog> pjauditlogsForPjauditunits) {
        this.pjauditlogsForPjauditunits = pjauditlogsForPjauditunits;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganizationByOrganizationId")

    public Set<Pjauditlog> getPjauditlogsForOrganizationId() {
        return this.pjauditlogsForOrganizationId;
    }
    
    public void setPjauditlogsForOrganizationId(Set<Pjauditlog> pjauditlogsForOrganizationId) {
        this.pjauditlogsForOrganizationId = pjauditlogsForOrganizationId;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysOrganization")

    public Set<SysUser> getSysUsers() {
        return this.sysUsers;
    }
    
    public void setSysUsers(Set<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }
   








}