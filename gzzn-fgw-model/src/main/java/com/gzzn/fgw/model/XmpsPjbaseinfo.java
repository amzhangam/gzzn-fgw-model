package com.gzzn.fgw.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * XmpsPjbaseinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "XMPS_PJBASEINFO")
public class XmpsPjbaseinfo implements java.io.Serializable {

	// Fields

	private Integer projectid;
	private XmsbZjxz xmsbZjxz;
	private SysOrganization sysOrganizationByDeclareunitsid;
	private SysOrganization sysOrganizationByDirectorunitsid;
	private SysUser sysUserByRecorderid;
	private SysUser sysUserByNexttacheer;
	private XmsbHylb xmsbHylb;
	private SysXq sysXq;
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
	private String xmly;
	private Set<XmpsPjadjunct> xmpsPjadjuncts = new HashSet<XmpsPjadjunct>(0);
	private Set<XmpsProjectinvest> xmpsProjectinvests = new HashSet<XmpsProjectinvest>(
			0);

	// Constructors

	/** default constructor */
	public XmpsPjbaseinfo() {
	}

	/** full constructor */
	public XmpsPjbaseinfo(XmsbZjxz xmsbZjxz,
			SysOrganization sysOrganizationByDeclareunitsid,
			SysOrganization sysOrganizationByDirectorunitsid,
			SysUser sysUserByRecorderid, SysUser sysUserByNexttacheer,
			XmsbHylb xmsbHylb, SysXq sysXq, XmsbXmlx xmsbXmlx,
			String projectcode, String projectname, Integer projecttype,
			String projectcontent, Integer finishcontentyear,
			String finishcontent, Integer expectfinishinvestyear,
			Double expectfinishinvest, Integer expectfinishotherinvestyear,
			Double expectfinishotherinvest, Date workdate, Date finishdate,
			Integer startyear, Integer endyear, String workunitsid,
			String projectprincipal, String contactaddress, String contacttel,
			String projectaddress, String declarerid, Date declartime,
			String manageunitsname, Integer declaretype, String declaregist,
			String declareplan, String declareproblem, Integer pjstatus,
			Integer viewstatus, String nexttacheername,
			String parentprojectcode, String recordername, Date recordertime,
			Integer auditdept, String auditdeptname,
			Integer nextauditdept, String nextauditdeptname,
			String xmjbqkms, String xmjd, Integer sfzdxm, String fgwcsyj,
			String yjtxr, String ypzxyj, String ypzxyjfkr, String xmly,
			Set<XmpsPjadjunct> xmpsPjadjuncts,
			Set<XmpsProjectinvest> xmpsProjectinvests) {
		this.xmsbZjxz = xmsbZjxz;
		this.sysOrganizationByDeclareunitsid = sysOrganizationByDeclareunitsid;
		this.sysOrganizationByDirectorunitsid = sysOrganizationByDirectorunitsid;
		this.sysUserByRecorderid = sysUserByRecorderid;
		this.sysUserByNexttacheer = sysUserByNexttacheer;
		this.xmsbHylb = xmsbHylb;
		this.sysXq = sysXq;
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
		this.xmly = xmly;
		this.xmpsPjadjuncts = xmpsPjadjuncts;
		this.xmpsProjectinvests = xmpsProjectinvests;
	}

	// Property accessors
	@Id
	@Column(name = "PROJECTID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getProjectid() {
		return this.projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZJXZ_ID")
	public XmsbZjxz getXmsbZjxz() {
		return this.xmsbZjxz;
	}

	public void setXmsbZjxz(XmsbZjxz xmsbZjxz) {
		this.xmsbZjxz = xmsbZjxz;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DECLAREUNITSID")
	public SysOrganization getSysOrganizationByDeclareunitsid() {
		return this.sysOrganizationByDeclareunitsid;
	}

	public void setSysOrganizationByDeclareunitsid(
			SysOrganization sysOrganizationByDeclareunitsid) {
		this.sysOrganizationByDeclareunitsid = sysOrganizationByDeclareunitsid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIRECTORUNITSID")
	public SysOrganization getSysOrganizationByDirectorunitsid() {
		return this.sysOrganizationByDirectorunitsid;
	}

	public void setSysOrganizationByDirectorunitsid(
			SysOrganization sysOrganizationByDirectorunitsid) {
		this.sysOrganizationByDirectorunitsid = sysOrganizationByDirectorunitsid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECORDERID")
	public SysUser getSysUserByRecorderid() {
		return this.sysUserByRecorderid;
	}

	public void setSysUserByRecorderid(SysUser sysUserByRecorderid) {
		this.sysUserByRecorderid = sysUserByRecorderid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NEXTTACHEER")
	public SysUser getSysUserByNexttacheer() {
		return this.sysUserByNexttacheer;
	}

	public void setSysUserByNexttacheer(SysUser sysUserByNexttacheer) {
		this.sysUserByNexttacheer = sysUserByNexttacheer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HYFL_ID")
	public XmsbHylb getXmsbHylb() {
		return this.xmsbHylb;
	}

	public void setXmsbHylb(XmsbHylb xmsbHylb) {
		this.xmsbHylb = xmsbHylb;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "XQ_ID")
	public SysXq getSysXq() {
		return this.sysXq;
	}

	public void setSysXq(SysXq sysXq) {
		this.sysXq = sysXq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "XMLX_ID")
	public XmsbXmlx getXmsbXmlx() {
		return this.xmsbXmlx;
	}

	public void setXmsbXmlx(XmsbXmlx xmsbXmlx) {
		this.xmsbXmlx = xmsbXmlx;
	}

	@Column(name = "PROJECTCODE", length = 50)
	public String getProjectcode() {
		return this.projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}

	@Column(name = "PROJECTNAME", length = 200)
	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	@Column(name = "PROJECTTYPE", precision = 22, scale = 0)
	public Integer getProjecttype() {
		return this.projecttype;
	}

	public void setProjecttype(Integer projecttype) {
		this.projecttype = projecttype;
	}

	@Column(name = "PROJECTCONTENT", length = 4000)
	public String getProjectcontent() {
		return this.projectcontent;
	}

	public void setProjectcontent(String projectcontent) {
		this.projectcontent = projectcontent;
	}

	@Column(name = "FINISHCONTENTYEAR", precision = 22, scale = 0)
	public Integer getFinishcontentyear() {
		return this.finishcontentyear;
	}

	public void setFinishcontentyear(Integer finishcontentyear) {
		this.finishcontentyear = finishcontentyear;
	}

	@Column(name = "FINISHCONTENT")
	public String getFinishcontent() {
		return this.finishcontent;
	}

	public void setFinishcontent(String finishcontent) {
		this.finishcontent = finishcontent;
	}

	@Column(name = "EXPECTFINISHINVESTYEAR", precision = 22, scale = 0)
	public Integer getExpectfinishinvestyear() {
		return this.expectfinishinvestyear;
	}

	public void setExpectfinishinvestyear(Integer expectfinishinvestyear) {
		this.expectfinishinvestyear = expectfinishinvestyear;
	}

	@Column(name = "EXPECTFINISHINVEST", precision = 18)
	public Double getExpectfinishinvest() {
		return this.expectfinishinvest;
	}

	public void setExpectfinishinvest(Double expectfinishinvest) {
		this.expectfinishinvest = expectfinishinvest;
	}

	@Column(name = "EXPECTFINISHOTHERINVESTYEAR", precision = 22, scale = 0)
	public Integer getExpectfinishotherinvestyear() {
		return this.expectfinishotherinvestyear;
	}

	public void setExpectfinishotherinvestyear(
			Integer expectfinishotherinvestyear) {
		this.expectfinishotherinvestyear = expectfinishotherinvestyear;
	}

	@Column(name = "EXPECTFINISHOTHERINVEST", precision = 18)
	public Double getExpectfinishotherinvest() {
		return this.expectfinishotherinvest;
	}

	public void setExpectfinishotherinvest(Double expectfinishotherinvest) {
		this.expectfinishotherinvest = expectfinishotherinvest;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "WORKDATE", length = 7)
	public Date getWorkdate() {
		return this.workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FINISHDATE", length = 7)
	public Date getFinishdate() {
		return this.finishdate;
	}

	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}

	@Column(name = "STARTYEAR", precision = 22, scale = 0)
	public Integer getStartyear() {
		return this.startyear;
	}

	public void setStartyear(Integer startyear) {
		this.startyear = startyear;
	}

	@Column(name = "ENDYEAR", precision = 22, scale = 0)
	public Integer getEndyear() {
		return this.endyear;
	}

	public void setEndyear(Integer endyear) {
		this.endyear = endyear;
	}

	@Column(name = "WORKUNITSID", length = 100)
	public String getWorkunitsid() {
		return this.workunitsid;
	}

	public void setWorkunitsid(String workunitsid) {
		this.workunitsid = workunitsid;
	}

	@Column(name = "PROJECTPRINCIPAL", length = 400)
	public String getProjectprincipal() {
		return this.projectprincipal;
	}

	public void setProjectprincipal(String projectprincipal) {
		this.projectprincipal = projectprincipal;
	}

	@Column(name = "CONTACTADDRESS", length = 400)
	public String getContactaddress() {
		return this.contactaddress;
	}

	public void setContactaddress(String contactaddress) {
		this.contactaddress = contactaddress;
	}

	@Column(name = "CONTACTTEL", length = 20)
	public String getContacttel() {
		return this.contacttel;
	}

	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}

	@Column(name = "PROJECTADDRESS", length = 400)
	public String getProjectaddress() {
		return this.projectaddress;
	}

	public void setProjectaddress(String projectaddress) {
		this.projectaddress = projectaddress;
	}

	@Column(name = "DECLARERID", length = 20)
	public String getDeclarerid() {
		return this.declarerid;
	}

	public void setDeclarerid(String declarerid) {
		this.declarerid = declarerid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DECLARTIME", length = 7)
	public Date getDeclartime() {
		return this.declartime;
	}

	public void setDeclartime(Date declartime) {
		this.declartime = declartime;
	}

	@Column(name = "MANAGEUNITSNAME", length = 200)
	public String getManageunitsname() {
		return this.manageunitsname;
	}

	public void setManageunitsname(String manageunitsname) {
		this.manageunitsname = manageunitsname;
	}

	@Column(name = "DECLARETYPE", precision = 22, scale = 0)
	public Integer getDeclaretype() {
		return this.declaretype;
	}

	public void setDeclaretype(Integer declaretype) {
		this.declaretype = declaretype;
	}

	@Column(name = "DECLAREGIST", length = 4000)
	public String getDeclaregist() {
		return this.declaregist;
	}

	public void setDeclaregist(String declaregist) {
		this.declaregist = declaregist;
	}

	@Column(name = "DECLAREPLAN")
	public String getDeclareplan() {
		return this.declareplan;
	}

	public void setDeclareplan(String declareplan) {
		this.declareplan = declareplan;
	}

	@Column(name = "DECLAREPROBLEM")
	public String getDeclareproblem() {
		return this.declareproblem;
	}

	public void setDeclareproblem(String declareproblem) {
		this.declareproblem = declareproblem;
	}

	@Column(name = "PJSTATUS", precision = 22, scale = 0)
	public Integer getPjstatus() {
		return this.pjstatus;
	}

	public void setPjstatus(Integer pjstatus) {
		this.pjstatus = pjstatus;
	}

	@Column(name = "VIEWSTATUS", precision = 22, scale = 0)
	public Integer getViewstatus() {
		return this.viewstatus;
	}

	public void setViewstatus(Integer viewstatus) {
		this.viewstatus = viewstatus;
	}

	@Column(name = "NEXTTACHEERNAME", length = 50)
	public String getNexttacheername() {
		return this.nexttacheername;
	}

	public void setNexttacheername(String nexttacheername) {
		this.nexttacheername = nexttacheername;
	}

	@Column(name = "PARENTPROJECTCODE", length = 50)
	public String getParentprojectcode() {
		return this.parentprojectcode;
	}

	public void setParentprojectcode(String parentprojectcode) {
		this.parentprojectcode = parentprojectcode;
	}

	@Column(name = "RECORDERNAME", length = 50)
	public String getRecordername() {
		return this.recordername;
	}

	public void setRecordername(String recordername) {
		this.recordername = recordername;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECORDERTIME", length = 7)
	public Date getRecordertime() {
		return this.recordertime;
	}

	public void setRecordertime(Date recordertime) {
		this.recordertime = recordertime;
	}

	@Column(name = "AUDITDEPT", precision = 22, scale = 0)
	public Integer getAuditdept() {
		return this.auditdept;
	}

	public void setAuditdept(Integer auditdept) {
		this.auditdept = auditdept;
	}

	@Column(name = "AUDITDEPTNAME", length = 100)
	public String getAuditdeptname() {
		return this.auditdeptname;
	}

	public void setAuditdeptname(String auditdeptname) {
		this.auditdeptname = auditdeptname;
	}

	@Column(name = "NEXTAUDITDEPT", precision = 22, scale = 0)
	public Integer getNextauditdept() {
		return this.nextauditdept;
	}

	public void setNextauditdept(Integer nextauditdept) {
		this.nextauditdept = nextauditdept;
	}

	@Column(name = "NEXTAUDITDEPTNAME", length = 50)
	public String getNextauditdeptname() {
		return this.nextauditdeptname;
	}

	public void setNextauditdeptname(String nextauditdeptname) {
		this.nextauditdeptname = nextauditdeptname;
	}

	@Column(name = "XMJBQKMS", length = 100)
	public String getXmjbqkms() {
		return this.xmjbqkms;
	}

	public void setXmjbqkms(String xmjbqkms) {
		this.xmjbqkms = xmjbqkms;
	}

	@Column(name = "XMJD", length = 50)
	public String getXmjd() {
		return this.xmjd;
	}

	public void setXmjd(String xmjd) {
		this.xmjd = xmjd;
	}

	@Column(name = "SFZDXM", precision = 22, scale = 0)
	public Integer getSfzdxm() {
		return this.sfzdxm;
	}

	public void setSfzdxm(Integer sfzdxm) {
		this.sfzdxm = sfzdxm;
	}

	@Column(name = "FGWCSYJ", length = 1000)
	public String getFgwcsyj() {
		return this.fgwcsyj;
	}

	public void setFgwcsyj(String fgwcsyj) {
		this.fgwcsyj = fgwcsyj;
	}

	@Column(name = "YJTXR", length = 100)
	public String getYjtxr() {
		return this.yjtxr;
	}

	public void setYjtxr(String yjtxr) {
		this.yjtxr = yjtxr;
	}

	@Column(name = "YPZXYJ", length = 1000)
	public String getYpzxyj() {
		return this.ypzxyj;
	}

	public void setYpzxyj(String ypzxyj) {
		this.ypzxyj = ypzxyj;
	}

	@Column(name = "YPZXYJFKR", length = 100)
	public String getYpzxyjfkr() {
		return this.ypzxyjfkr;
	}

	public void setYpzxyjfkr(String ypzxyjfkr) {
		this.ypzxyjfkr = ypzxyjfkr;
	}

	@Column(name = "XMLY", length = 100)
	public String getXmly() {
		return this.xmly;
	}

	public void setXmly(String xmly) {
		this.xmly = xmly;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "xmpsPjbaseinfo")
	public Set<XmpsPjadjunct> getXmpsPjadjuncts() {
		return this.xmpsPjadjuncts;
	}

	public void setXmpsPjadjuncts(Set<XmpsPjadjunct> xmpsPjadjuncts) {
		this.xmpsPjadjuncts = xmpsPjadjuncts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "xmpsPjbaseinfo")
	public Set<XmpsProjectinvest> getXmpsProjectinvests() {
		return this.xmpsProjectinvests;
	}

	public void setXmpsProjectinvests(Set<XmpsProjectinvest> xmpsProjectinvests) {
		this.xmpsProjectinvests = xmpsProjectinvests;
	}

}