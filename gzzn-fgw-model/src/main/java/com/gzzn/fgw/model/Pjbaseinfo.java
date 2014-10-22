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
 * Pjbaseinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PJBASEINFO"
    
)

public class Pjbaseinfo  implements java.io.Serializable {


    // Fields    

     private Integer projectid;
     private XmsbZjxz xmsbZjxz;
     private SysOrganization sysOrganizationByDeclareunitsid;
     private SysOrganization sysOrganizationByDirectorunitsid;
     private SysOrganization sysOrganizationByRecordOrgan;
     private SysUser sysUserByRecorderid;
     private SysUser sysUserByNexttacheer;
     private SysXq sysXq;
     private XmsbHylb xmsbHylb;
     private XmsbXmlx xmsbXmlx;
     private String projectcode;
     private String projectname;
     private Integer projecttype;
     private String projectcontent;
     private Integer finishcontentyear;
     private String finishcontent;
     private Integer expectfinishinvestyear;
     private Double expectfinishinvest;
     private Integer expectfinishotherinvestyear;
     private Double expectfinishotherinvest;
     private Date workdate;
     private Date finishdate;
     private Integer startyear;
     private Integer endyear;
     private String workunitsid;
     private String projectprincipal;
     private String contactaddress;
     private String contacttel;
     private String projectaddress;
     private String declarerid;
     private Date declartime;
     private String manageunitsname;
     private Integer declaretype;
     private String declaregist;
     private String declareplan;
     private String declareproblem;
     private Integer pjstatus;
     private Integer viewstatus;
     private String nexttacheername;
     private String parentprojectcode;
     private String recordername;
     private Date recordertime;
     private Integer auditdept;
     private String auditdeptname;
     private Integer nextauditdept;
     private String nextauditdeptname;
     private String xmjbqkms;
     private String xmjd;
     private Integer sfzdxm;
     private String fgwcsyj;
     private String yjtxr;
     private String ypzxyj;
     private String ypzxyjfkr;
     private String mobilePhone;
     private String xmcblb;
     private String qtsx;
     private String lxpf;
 	private String ghxz;
 	private String ydys;
 	private String hjyx;
 	private String jnpg;
 	private String kypf;
 	private String cbsj;
 	private String zbtb;
 	private String zdcq;
 	private String qtqq;
 	private String fxpg;
 	private String sgys;
     private Date modifiedTime;
     private Integer deleted;
     private Double xmztz;
     
     private Set<XmsbXmyb> xmsbXmybs = new HashSet<XmsbXmyb>(0);
     private Set<Projectinvest> projectinvests = new HashSet<Projectinvest>(0);
     private Set<ProjectinvestSh> projectinvestShs = new HashSet<ProjectinvestSh>(0);
     private Set<Pjauditlog> pjauditlogs = new HashSet<Pjauditlog>(0);
     private Set<Pjadjunct> pjadjuncts = new HashSet<Pjadjunct>(0);
     private Set<SysProjectlog> sysProjectlogs = new HashSet<SysProjectlog>(0);
     private Integer xmfl;
     private Integer yzbm;
     
     private Double pjinvestcitySh;
     private String pjauditdept;
     private String pjaudituser;
     private Date pjaudittime;

    // Constructors

    /** default constructor */
    public Pjbaseinfo() {
    }

    
    /** full constructor */
    public Pjbaseinfo(XmsbZjxz xmsbZjxz, SysOrganization sysOrganizationByDeclareunitsid, SysOrganization sysOrganizationByDirectorunitsid, SysUser sysUserByRecorderid, 
    		SysUser sysUserByNexttacheer, SysXq sysXq, XmsbHylb xmsbHylb, XmsbXmlx xmsbXmlx, String projectcode, String projectname, Integer projecttype,
    		String projectcontent, Integer finishcontentyear, String finishcontent, Integer expectfinishinvestyear, Double expectfinishinvest, 
    		Integer expectfinishotherinvestyear, Double expectfinishotherinvest, Date workdate, Date finishdate, Integer startyear, Integer endyear, 
    		String workunitsid, String projectprincipal, String contactaddress, String contacttel, String projectaddress, String declarerid, Date declartime,
    		String manageunitsname, Integer declaretype, String declaregist, String declareplan, String declareproblem, Integer pjstatus, Integer viewstatus,
    		String nexttacheername, String parentprojectcode, String recordername, Date recordertime, Integer auditdept, String auditdeptname, Integer nextauditdept,
    		String nextauditdeptname, String xmjbqkms, String xmjd, Integer sfzdxm, String fgwcsyj, String yjtxr, String ypzxyj, String ypzxyjfkr,
    		String mobilePhone,String xmcblb,Integer xmfl,Integer yzbm,SysOrganization sysOrganizationByRecordOrgan,Date modifiedTime,
    		Integer deleted,Double xmztz,Double pjinvestcitySh,
    		Set<XmsbXmyb> xmsbXmybs, Set<Projectinvest> projectinvests,Set<ProjectinvestSh> projectinvestShs,  Set<Pjauditlog> pjauditlogs, Set<Pjadjunct> pjadjuncts, Set<SysProjectlog> sysProjectlogs) {
        this.xmsbZjxz = xmsbZjxz;
        this.sysOrganizationByDeclareunitsid = sysOrganizationByDeclareunitsid;
        this.sysOrganizationByDirectorunitsid = sysOrganizationByDirectorunitsid;
        this.sysUserByRecorderid = sysUserByRecorderid;
        this.sysUserByNexttacheer = sysUserByNexttacheer;
        this.sysXq = sysXq;
        this.xmsbHylb = xmsbHylb;
        this.xmsbXmlx = xmsbXmlx;
        this.projectcode = projectcode;
        this.projectname = projectname;
        this.projecttype = projecttype;
        this.projectcontent = projectcontent;
        this.finishcontentyear = finishcontentyear;
        this.finishcontent = finishcontent;
        this.expectfinishinvestyear = expectfinishinvestyear;
        this.expectfinishinvest = expectfinishinvest;
        this.expectfinishotherinvestyear = expectfinishotherinvestyear;
        this.expectfinishotherinvest = expectfinishotherinvest;
        this.workdate = workdate;
        this.finishdate = finishdate;
        this.startyear = startyear;
        this.endyear = endyear;
        this.workunitsid = workunitsid;
        this.projectprincipal = projectprincipal;
        this.contactaddress = contactaddress;
        this.contacttel = contacttel;
        this.projectaddress = projectaddress;
        this.declarerid = declarerid;
        this.declartime = declartime;
        this.manageunitsname = manageunitsname;
        this.declaretype = declaretype;
        this.declaregist = declaregist;
        this.declareplan = declareplan;
        this.declareproblem = declareproblem;
        this.pjstatus = pjstatus;
        this.viewstatus = viewstatus;
        this.nexttacheername = nexttacheername;
        this.parentprojectcode = parentprojectcode;
        this.recordername = recordername;
        this.recordertime = recordertime;
        this.auditdept = auditdept;
        this.auditdeptname = auditdeptname;
        this.nextauditdept = nextauditdept;
        this.nextauditdeptname = nextauditdeptname;
        this.xmjbqkms = xmjbqkms;
        this.xmjd = xmjd;
        this.sfzdxm = sfzdxm;
        this.fgwcsyj = fgwcsyj;
        this.yjtxr = yjtxr;
        this.ypzxyj = ypzxyj;
        this.ypzxyjfkr = ypzxyjfkr;
        this.mobilePhone = mobilePhone;
        this.xmcblb = xmcblb;
        this.xmfl = xmfl;
        this.yzbm = yzbm;
        this.modifiedTime = modifiedTime;
        this.deleted = deleted;
        this.xmztz = xmztz;
        this.pjinvestcitySh = pjinvestcitySh;
        
        this.sysOrganizationByRecordOrgan = sysOrganizationByRecordOrgan;
        this.xmsbXmybs = xmsbXmybs;
        this.projectinvests = projectinvests;
        this.projectinvestShs = projectinvestShs;
        this.pjauditlogs = pjauditlogs;
        this.pjadjuncts = pjadjuncts;
        this.sysProjectlogs = sysProjectlogs;
    }

   
    @Column(name="MOBILE_PHONE")
    public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name="XMCBLB")
	public String getXmcblb() {
		return xmcblb;
	}


	public void setXmcblb(String xmcblb) {
		this.xmcblb = xmcblb;
	}


	// Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="PROJECTID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getProjectid() {
        return this.projectid;
    }
    
    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ZJXZ_ID")

    public XmsbZjxz getXmsbZjxz() {
        return this.xmsbZjxz;
    }
    
    public void setXmsbZjxz(XmsbZjxz xmsbZjxz) {
        this.xmsbZjxz = xmsbZjxz;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DECLAREUNITSID")

    public SysOrganization getSysOrganizationByDeclareunitsid() {
        return this.sysOrganizationByDeclareunitsid;
    }
    
    public void setSysOrganizationByDeclareunitsid(SysOrganization sysOrganizationByDeclareunitsid) {
        this.sysOrganizationByDeclareunitsid = sysOrganizationByDeclareunitsid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DIRECTORUNITSID")

    public SysOrganization getSysOrganizationByDirectorunitsid() {
        return this.sysOrganizationByDirectorunitsid;
    }
    
    public void setSysOrganizationByDirectorunitsid(SysOrganization sysOrganizationByDirectorunitsid) {
        this.sysOrganizationByDirectorunitsid = sysOrganizationByDirectorunitsid;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RECORD_ORGAN")
	public SysOrganization getSysOrganizationByRecordOrgan() {
		return sysOrganizationByRecordOrgan;
	}


	public void setSysOrganizationByRecordOrgan(
			SysOrganization sysOrganizationByRecordOrgan) {
		this.sysOrganizationByRecordOrgan = sysOrganizationByRecordOrgan;
	}


	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="RECORDERID")

    public SysUser getSysUserByRecorderid() {
        return this.sysUserByRecorderid;
    }
    
    public void setSysUserByRecorderid(SysUser sysUserByRecorderid) {
        this.sysUserByRecorderid = sysUserByRecorderid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="NEXTTACHEER")

    public SysUser getSysUserByNexttacheer() {
        return this.sysUserByNexttacheer;
    }
    
    public void setSysUserByNexttacheer(SysUser sysUserByNexttacheer) {
        this.sysUserByNexttacheer = sysUserByNexttacheer;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="XQ_ID")

    public SysXq getSysXq() {
        return this.sysXq;
    }
    
    public void setSysXq(SysXq sysXq) {
        this.sysXq = sysXq;
    }

	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="XMLX_ID")

    public XmsbXmlx getXmsbXmlx() {
        return this.xmsbXmlx;
    }
    
    public void setXmsbXmlx(XmsbXmlx xmsbXmlx) {
        this.xmsbXmlx = xmsbXmlx;
    }
    
    @Column(name="PROJECTCODE", length=50)

    public String getProjectcode() {
        return this.projectcode;
    }
    
    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }
    
    @Column(name="PROJECTNAME", length=200)

    public String getProjectname() {
        return this.projectname;
    }
    
    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
    
    @Column(name="PROJECTTYPE", precision=22, scale=0)

    public Integer getProjecttype() {
        return this.projecttype;
    }
    
    public void setProjecttype(Integer projecttype) {
        this.projecttype = projecttype;
    }
    
    @Column(name="PROJECTCONTENT", length=4000)

    public String getProjectcontent() {
        return this.projectcontent;
    }
    
    public void setProjectcontent(String projectcontent) {
        this.projectcontent = projectcontent;
    }
    
    @Column(name="FINISHCONTENTYEAR", precision=22, scale=0)

    public Integer getFinishcontentyear() {
        return this.finishcontentyear;
    }
    
    public void setFinishcontentyear(Integer finishcontentyear) {
        this.finishcontentyear = finishcontentyear;
    }
    
    @Column(name="FINISHCONTENT")

    public String getFinishcontent() {
        return this.finishcontent;
    }
    
    public void setFinishcontent(String finishcontent) {
        this.finishcontent = finishcontent;
    }
    
    @Column(name="EXPECTFINISHINVESTYEAR", precision=22, scale=0)

    public Integer getExpectfinishinvestyear() {
        return this.expectfinishinvestyear;
    }
    
    public void setExpectfinishinvestyear(Integer expectfinishinvestyear) {
        this.expectfinishinvestyear = expectfinishinvestyear;
    }
    
    @Column(name="EXPECTFINISHINVEST", precision=18)

    public Double getExpectfinishinvest() {
        return this.expectfinishinvest;
    }
    
    public void setExpectfinishinvest(Double expectfinishinvest) {
        this.expectfinishinvest = expectfinishinvest;
    }
    
    @Column(name="XMZTZ", precision=18)
    public Double getXmztz() {
		return xmztz;
	}


	public void setXmztz(Double xmztz) {
		this.xmztz = xmztz;
	}


	@Column(name="EXPECTFINISHOTHERINVESTYEAR", precision=22, scale=0)

    public Integer getExpectfinishotherinvestyear() {
        return this.expectfinishotherinvestyear;
    }
    
    public void setExpectfinishotherinvestyear(Integer expectfinishotherinvestyear) {
        this.expectfinishotherinvestyear = expectfinishotherinvestyear;
    }
    
    @Column(name="EXPECTFINISHOTHERINVEST", precision=18)

    public Double getExpectfinishotherinvest() {
        return this.expectfinishotherinvest;
    }
    
    public void setExpectfinishotherinvest(Double expectfinishotherinvest) {
        this.expectfinishotherinvest = expectfinishotherinvest;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="WORKDATE", length=7)

    public Date getWorkdate() {
        return this.workdate;
    }
    
    public void setWorkdate(Date workdate) {
        this.workdate = workdate;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="FINISHDATE", length=7)

    public Date getFinishdate() {
        return this.finishdate;
    }
    
    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }
    
    @Column(name="STARTYEAR", precision=22, scale=0)

    public Integer getStartyear() {
        return this.startyear;
    }
    
    public void setStartyear(Integer startyear) {
        this.startyear = startyear;
    }
    
    @Column(name="ENDYEAR", precision=22, scale=0)

    public Integer getEndyear() {
        return this.endyear;
    }
    
    public void setEndyear(Integer endyear) {
        this.endyear = endyear;
    }
    
    @Column(name="WORKUNITSID", length=100)

    public String getWorkunitsid() {
        return this.workunitsid;
    }
    
    public void setWorkunitsid(String workunitsid) {
        this.workunitsid = workunitsid;
    }
    
    @Column(name="PROJECTPRINCIPAL", length=400)

    public String getProjectprincipal() {
        return this.projectprincipal;
    }
    
    public void setProjectprincipal(String projectprincipal) {
        this.projectprincipal = projectprincipal;
    }
    
    @Column(name="CONTACTADDRESS", length=400)

    public String getContactaddress() {
        return this.contactaddress;
    }
    
    public void setContactaddress(String contactaddress) {
        this.contactaddress = contactaddress;
    }
    
    @Column(name="CONTACTTEL", length=20)

    public String getContacttel() {
        return this.contacttel;
    }
    
    public void setContacttel(String contacttel) {
        this.contacttel = contacttel;
    }
    
    @Column(name="PROJECTADDRESS", length=400)

    public String getProjectaddress() {
        return this.projectaddress;
    }
    
    public void setProjectaddress(String projectaddress) {
        this.projectaddress = projectaddress;
    }
    
    @Column(name="DECLARERID", length=20)

    public String getDeclarerid() {
        return this.declarerid;
    }
    
    public void setDeclarerid(String declarerid) {
        this.declarerid = declarerid;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="DECLARTIME", length=7)

    public Date getDeclartime() {
        return this.declartime;
    }
    
    public void setDeclartime(Date declartime) {
        this.declartime = declartime;
    }
    
    @Column(name="MANAGEUNITSNAME", length=200)

    public String getManageunitsname() {
        return this.manageunitsname;
    }
    
    public void setManageunitsname(String manageunitsname) {
        this.manageunitsname = manageunitsname;
    }
    
    @Column(name="DECLARETYPE", precision=22, scale=0)

    public Integer getDeclaretype() {
        return this.declaretype;
    }
    
    public void setDeclaretype(Integer declaretype) {
        this.declaretype = declaretype;
    }
    
    @Column(name="DECLAREGIST", length=4000)

    public String getDeclaregist() {
        return this.declaregist;
    }
    
    public void setDeclaregist(String declaregist) {
        this.declaregist = declaregist;
    }
    
    @Column(name="DECLAREPLAN")

    public String getDeclareplan() {
        return this.declareplan;
    }
    
    public void setDeclareplan(String declareplan) {
        this.declareplan = declareplan;
    }
    
    @Column(name="DECLAREPROBLEM")

    public String getDeclareproblem() {
        return this.declareproblem;
    }
    
    public void setDeclareproblem(String declareproblem) {
        this.declareproblem = declareproblem;
    }
    
    @Column(name="PJSTATUS", precision=22, scale=0)

    public Integer getPjstatus() {
        return this.pjstatus;
    }
    
    public void setPjstatus(Integer pjstatus) {
        this.pjstatus = pjstatus;
    }
    
    @Column(name="VIEWSTATUS", precision=22, scale=0)

    public Integer getViewstatus() {
        return this.viewstatus;
    }
    
    public void setViewstatus(Integer viewstatus) {
        this.viewstatus = viewstatus;
    }
    
    @Column(name="NEXTTACHEERNAME", length=50)

    public String getNexttacheername() {
        return this.nexttacheername;
    }
    
    public void setNexttacheername(String nexttacheername) {
        this.nexttacheername = nexttacheername;
    }
    
    @Column(name="PARENTPROJECTCODE", length=50)

    public String getParentprojectcode() {
        return this.parentprojectcode;
    }
    
    public void setParentprojectcode(String parentprojectcode) {
        this.parentprojectcode = parentprojectcode;
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
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFIED_TIME", length=7)
    public Date getModifiedTime() {
		return modifiedTime;
	}


	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}


	@Column(name="AUDITDEPT", precision=22, scale=0)

    public Integer getAuditdept() {
        return this.auditdept;
    }
    
    public void setAuditdept(Integer auditdept) {
        this.auditdept = auditdept;
    }
    
    @Column(name="AUDITDEPTNAME", length=100)

    public String getAuditdeptname() {
        return this.auditdeptname;
    }
    
    public void setAuditdeptname(String auditdeptname) {
        this.auditdeptname = auditdeptname;
    }
    
    @Column(name="NEXTAUDITDEPT", precision=22, scale=0)

    public Integer getNextauditdept() {
        return this.nextauditdept;
    }
    
    public void setNextauditdept(Integer nextauditdept) {
        this.nextauditdept = nextauditdept;
    }
    
    @Column(name="NEXTAUDITDEPTNAME", length=50)

    public String getNextauditdeptname() {
        return this.nextauditdeptname;
    }
    
    public void setNextauditdeptname(String nextauditdeptname) {
        this.nextauditdeptname = nextauditdeptname;
    }
    
    @Column(name="XMJBQKMS", length=100)

    public String getXmjbqkms() {
        return this.xmjbqkms;
    }
    
    public void setXmjbqkms(String xmjbqkms) {
        this.xmjbqkms = xmjbqkms;
    }
    
    @Column(name="XMJD", length=50)

    public String getXmjd() {
        return this.xmjd;
    }
    
    public void setXmjd(String xmjd) {
        this.xmjd = xmjd;
    }
    
    @Column(name="SFZDXM", precision=22, scale=0)

    public Integer getSfzdxm() {
        return this.sfzdxm;
    }
    
    public void setSfzdxm(Integer sfzdxm) {
        this.sfzdxm = sfzdxm;
    }
    
    @Column(name="FGWCSYJ", length=1000)

    public String getFgwcsyj() {
        return this.fgwcsyj;
    }
    
    public void setFgwcsyj(String fgwcsyj) {
        this.fgwcsyj = fgwcsyj;
    }
    
    @Column(name="YJTXR", length=100)

    public String getYjtxr() {
        return this.yjtxr;
    }
    
    public void setYjtxr(String yjtxr) {
        this.yjtxr = yjtxr;
    }
    
    @Column(name="YPZXYJ", length=1000)

    public String getYpzxyj() {
        return this.ypzxyj;
    }
    
    public void setYpzxyj(String ypzxyj) {
        this.ypzxyj = ypzxyj;
    }
    
    @Column(name="YPZXYJFKR", length=100)

    public String getYpzxyjfkr() {
        return this.ypzxyjfkr;
    }
    
    public void setYpzxyjfkr(String ypzxyjfkr) {
        this.ypzxyjfkr = ypzxyjfkr;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pjbaseinfo")

    public Set<XmsbXmyb> getXmsbXmybs() {
        return this.xmsbXmybs;
    }
    
    public void setXmsbXmybs(Set<XmsbXmyb> xmsbXmybs) {
        this.xmsbXmybs = xmsbXmybs;
    }

@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pjbaseinfo")

    public Set<Projectinvest> getProjectinvests() {
        return this.projectinvests;
    }
    
    public void setProjectinvests(Set<Projectinvest> projectinvests) {
        this.projectinvests = projectinvests;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pjbaseinfo")
    
    public Set<ProjectinvestSh> getProjectinvestShs() {
    	return this.projectinvestShs;
    }
    
    public void setProjectinvestShs(Set<ProjectinvestSh> projectinvestShs) {
    	this.projectinvestShs = projectinvestShs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pjbaseinfo")

    public Set<Pjauditlog> getPjauditlogs() {
        return this.pjauditlogs;
    }
    
    public void setPjauditlogs(Set<Pjauditlog> pjauditlogs) {
        this.pjauditlogs = pjauditlogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pjbaseinfo")

    public Set<Pjadjunct> getPjadjuncts() {
        return this.pjadjuncts;
    }
    
    public void setPjadjuncts(Set<Pjadjunct> pjadjuncts) {
        this.pjadjuncts = pjadjuncts;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="pjbaseinfo")

    public Set<SysProjectlog> getSysProjectlogs() {
        return this.sysProjectlogs;
    }
    
    public void setSysProjectlogs(Set<SysProjectlog> sysProjectlogs) {
        this.sysProjectlogs = sysProjectlogs;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="HYFL_ID")
	public XmsbHylb getXmsbHylb() {
		return xmsbHylb;
	}


	public void setXmsbHylb(XmsbHylb xmsbHylb) {
		this.xmsbHylb = xmsbHylb;
	}

	@Column(name="XMFL")
	public Integer getXmfl() {
		return xmfl;
	}


	public void setXmfl(Integer xmfl) {
		this.xmfl = xmfl;
	}

	@Column(name="YZBM")
	public Integer getYzbm() {
		return yzbm;
	}


	public void setYzbm(Integer yzbm) {
		this.yzbm = yzbm;
	}

	@Column(name="QTSX")
	public String getQtsx() {
		return qtsx;
	}


	public void setQtsx(String qtsx) {
		this.qtsx = qtsx;
	}

	@Column(name="SGYS")
	public String getSgys() {
		return sgys;
	}


	public void setSgys(String sgys) {
		this.sgys = sgys;
	}

	@Column(name="FXPG")
	public String getFxpg() {
		return fxpg;
	}


	public void setFxpg(String fxpg) {
		this.fxpg = fxpg;
	}

	@Column(name="LXPF")
	public String getLxpf() {
		return lxpf;
	}


	public void setLxpf(String lxpf) {
		this.lxpf = lxpf;
	}

	@Column(name="GHXZ")
	public String getGhxz() {
		return ghxz;
	}


	public void setGhxz(String ghxz) {
		this.ghxz = ghxz;
	}

	@Column(name="YDYS")
	public String getYdys() {
		return ydys;
	}


	public void setYdys(String ydys) {
		this.ydys = ydys;
	}

	@Column(name="HJYX")
	public String getHjyx() {
		return hjyx;
	}

	
	public void setHjyx(String hjyx) {
		this.hjyx = hjyx;
	}

	@Column(name="JNPG")
	public String getJnpg() {
		return jnpg;
	}


	public void setJnpg(String jnpg) {
		this.jnpg = jnpg;
	}

	@Column(name="KYPF")
	public String getKypf() {
		return kypf;
	}


	public void setKypf(String kypf) {
		this.kypf = kypf;
	}

	@Column(name="CBSJ")
	public String getCbsj() {
		return cbsj;
	}


	public void setCbsj(String cbsj) {
		this.cbsj = cbsj;
	}

	@Column(name="ZBTB")
	public String getZbtb() {
		return zbtb;
	}


	public void setZbtb(String zbtb) {
		this.zbtb = zbtb;
	}

	@Column(name="ZDCQ")
	public String getZdcq() {
		return zdcq;
	}


	public void setZdcq(String zdcq) {
		this.zdcq = zdcq;
	}

	@Column(name="QTQQ")
	public String getQtqq() {
		return qtqq;
	}


	public void setQtqq(String qtqq) {
		this.qtqq = qtqq;
	}

	@Column(name="DELETED", precision=22, scale=0)

    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Column(name="PJINVESTCITY_SH", precision=18)

    public Double getPjinvestcitySh() {
        return this.pjinvestcitySh;
    }
    
    public void setPjinvestcitySh(Double pjinvestcitySh) {
        this.pjinvestcitySh = pjinvestcitySh;
    }

    @Column(name="PJAUDITDEPT")
   	public String getPjauditdept() {
   		return pjauditdept;
   	}


   	public void setPjauditdept(String pjauditdept) {
   		this.pjauditdept = pjauditdept;
   	}

   	@Column(name="PJAUDITUSER")
   	public String getPjaudituser() {
   		return pjaudituser;
   	}


   	public void setPjaudituser(String pjaudituser) {
   		this.pjaudituser = pjaudituser;
   	}

   	@Temporal(TemporalType.TIMESTAMP)
   	@Column(name="PJAUDITTIME")
   	public Date getPjaudittime() {
   		return pjaudittime;
   	}


   	public void setPjaudittime(Date pjaudittime) {
   		this.pjaudittime = pjaudittime;
   	}


}