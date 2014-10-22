package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Pjauditlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PJAUDITLOG"
    
)

public class Pjauditlog  implements java.io.Serializable {


    // Fields    

     private Integer pjauditlogid;
     private SysOrganization sysOrganizationByPjauditunits;
     private SysDept sysDeptByDeptId;
     private SysOrganization sysOrganizationByOrganizationId;
     private SysDept sysDeptBySenddeptid;
     private SysUser sysUser;
     private Pjbaseinfo pjbaseinfo;
     private Integer pjaudittype;
     private String pjauditresult;
     private String pjauditmind;
     private String currenttache;
     private String currenttacheer;
     private String recordername;
     private Date recordertime;
     private String projectcode;
     
     private Integer sfzj;


    // Constructors

    /** default constructor */
    public Pjauditlog() {
    }

    
    /** full constructor */
    public Pjauditlog(SysOrganization sysOrganizationByPjauditunits, SysDept sysDeptByDeptId, SysOrganization sysOrganizationByOrganizationId, SysDept sysDeptBySenddeptid, SysUser sysUser, Pjbaseinfo pjbaseinfo, Integer pjaudittype, String pjauditresult, String pjauditmind, String currenttache, String currenttacheer, String recordername, Date recordertime, String projectcode) {
        this.sysOrganizationByPjauditunits = sysOrganizationByPjauditunits;
        this.sysDeptByDeptId = sysDeptByDeptId;
        this.sysOrganizationByOrganizationId = sysOrganizationByOrganizationId;
        this.sysDeptBySenddeptid = sysDeptBySenddeptid;
        this.sysUser = sysUser;
        this.pjbaseinfo = pjbaseinfo;
        this.pjaudittype = pjaudittype;
        this.pjauditresult = pjauditresult;
        this.pjauditmind = pjauditmind;
        this.currenttache = currenttache;
        this.currenttacheer = currenttacheer;
        this.recordername = recordername;
        this.recordertime = recordertime;
        this.projectcode = projectcode;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="PJAUDITLOGID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getPjauditlogid() {
        return this.pjauditlogid;
    }
    
    public void setPjauditlogid(Integer pjauditlogid) {
        this.pjauditlogid = pjauditlogid;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="PJAUDITUNITS")

    public SysOrganization getSysOrganizationByPjauditunits() {
        return this.sysOrganizationByPjauditunits;
    }
    
    public void setSysOrganizationByPjauditunits(SysOrganization sysOrganizationByPjauditunits) {
        this.sysOrganizationByPjauditunits = sysOrganizationByPjauditunits;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="DEPT_ID")

    public SysDept getSysDeptByDeptId() {
        return this.sysDeptByDeptId;
    }
    
    public void setSysDeptByDeptId(SysDept sysDeptByDeptId) {
        this.sysDeptByDeptId = sysDeptByDeptId;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="ORGANIZATION_ID")

    public SysOrganization getSysOrganizationByOrganizationId() {
        return this.sysOrganizationByOrganizationId;
    }
    
    public void setSysOrganizationByOrganizationId(SysOrganization sysOrganizationByOrganizationId) {
        this.sysOrganizationByOrganizationId = sysOrganizationByOrganizationId;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="SENDDEPTID")

    public SysDept getSysDeptBySenddeptid() {
        return this.sysDeptBySenddeptid;
    }
    
    public void setSysDeptBySenddeptid(SysDept sysDeptBySenddeptid) {
        this.sysDeptBySenddeptid = sysDeptBySenddeptid;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="RECORDERID")

    public SysUser getSysUser() {
        return this.sysUser;
    }
    
    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="PROJECTID")

    public Pjbaseinfo getPjbaseinfo() {
        return this.pjbaseinfo;
    }
    
    public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
        this.pjbaseinfo = pjbaseinfo;
    }
    
    @Column(name="PJAUDITTYPE")

    public Integer getPjaudittype() {
        return this.pjaudittype;
    }
    
    public void setPjaudittype(Integer pjaudittype) {
        this.pjaudittype = pjaudittype;
    }
    
    @Column(name="PJAUDITRESULT")

    public String getPjauditresult() {
        return this.pjauditresult;
    }
    
    public void setPjauditresult(String pjauditresult) {
        this.pjauditresult = pjauditresult;
    }
    
    @Column(name="PJAUDITMIND")

    public String getPjauditmind() {
        return this.pjauditmind;
    }
    
    public void setPjauditmind(String pjauditmind) {
        this.pjauditmind = pjauditmind;
    }
    
    @Column(name="CURRENTTACHE")

    public String getCurrenttache() {
        return this.currenttache;
    }
    
    public void setCurrenttache(String currenttache) {
        this.currenttache = currenttache;
    }
    
    @Column(name="CURRENTTACHEER")

    public String getCurrenttacheer() {
        return this.currenttacheer;
    }
    
    public void setCurrenttacheer(String currenttacheer) {
        this.currenttacheer = currenttacheer;
    }
    
    @Column(name="RECORDERNAME", length=50)

    public String getRecordername() {
        return this.recordername;
    }
    
    public void setRecordername(String recordername) {
        this.recordername = recordername;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RECORDERTIME", length=7)

    public Date getRecordertime() {
        return this.recordertime;
    }
    
    public void setRecordertime(Date recordertime) {
        this.recordertime = recordertime;
    }
    
    @Column(name="PROJECTCODE")

    public String getProjectcode() {
        return this.projectcode;
    }
    
    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    @Column(name="SFZJ")
	public Integer getSfzj() {
		return sfzj;
	}


	public void setSfzj(Integer sfzj) {
		this.sfzj = sfzj;
	}
   








}