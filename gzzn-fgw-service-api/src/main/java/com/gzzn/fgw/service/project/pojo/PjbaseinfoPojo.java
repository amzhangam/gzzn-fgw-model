package com.gzzn.fgw.service.project.pojo;

import java.io.Serializable;

import java.util.Date;

import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbHylb;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.XmsbZjxz;

public class PjbaseinfoPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1474360875072895282L;
	
	/**项目基本信息*/
	private int projectid;
    private SysOrganization sysOrganizationByDeclareunitsid;
    private SysOrganization sysOrganizationByDirectorunitsId;
    private SysOrganization sysOrganizationByRecordOrgan;
    private SysUser sysUserByRecorderid;
    private SysUser sysUserByNexttacheer;
    private SysXq sysXq;
    private XmsbHylb xmsbHylb;
    private XmsbXmlx xmsbXmlx;
    private XmsbZjxz xmsbZjxz;
    private String xmjbqkms;
    private String projectcode;
    private String projectname;
    private int projecttype;
    private String projectcontent;
    private Integer finishcontentyear;
    private String finishcontent;
    private Integer expectfinishinvestyear;
    private Double expectfinishinvest;
    private Integer expectfinishotherinvestyear;
    private Double expectfinishotherinvest;
    private Integer xmfl;//项目分类
    
    private String xmcblb;//项目储备类别
    private String organOrDept;//审核单位与部门
    
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
    private String xmjd;
    private Integer sfzdxm;
    private String fgwcsyj;
    private String yjtxr;
    private String ypzxyj;
    private String ypzxyjfkr;
    private String mobilePhone;
    
    private Date modifiedTime;
    
    private Integer deleted;
    
    /**投资信息*/
    private Integer pjinvestid;
    private Pjbaseinfo pjbaseinfo;
    private Double pjinvestsum;
    private Double pjinvestcenter;
    private Double pjinvestprovince;
    private Double pjinvestcity;
    private Double pjinvesttown;
    private Double pjinvestcompany;
    private Double pjinvestbusiness;
    private Double pjinvestbank;
    private Double pjinvestother;
    private String pjinvestadvice;
    private Integer planinvestyear;
    private Double planinvestsum;
    private Double planinvestcenter;
    private Double planinvestprovince;
    private Double planinvestcity;
    private Double planinvesttown;
    private Double planinvestcompany;
    private Double planinvestbusiness;
    private Double planinvestbank;
    private Double planinvestother;
    private String planinvestadvice;
    
    /**审核信息*/
    private Double pjinvestcitySh;
    private Double planinvestcitySh;
    private String pjauditmind;
    private String pjauditdept;
    private String pjaudituser;
    private Date pjaudittime;
    
    private String budgetDeclareOrgan;
    private String budgetDirectorOrgan;
    
	public SysOrganization getSysOrganizationByRecordOrgan() {
		return sysOrganizationByRecordOrgan;
	}
	public void setSysOrganizationByRecordOrgan(
			SysOrganization sysOrganizationByRecordOrgan) {
		this.sysOrganizationByRecordOrgan = sysOrganizationByRecordOrgan;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public SysOrganization getSysOrganizationByDeclareunitsid() {
		return sysOrganizationByDeclareunitsid;
	}
	public void setSysOrganizationByDeclareunitsid(
			SysOrganization sysOrganizationByDeclareunitsid) {
		this.sysOrganizationByDeclareunitsid = sysOrganizationByDeclareunitsid;
	}
	public SysOrganization getSysOrganizationByDirectorunitsId() {
		return sysOrganizationByDirectorunitsId;
	}
	public void setSysOrganizationByDirectorunitsId(
			SysOrganization sysOrganizationByDirectorunitsId) {
		this.sysOrganizationByDirectorunitsId = sysOrganizationByDirectorunitsId;
	}
	public SysUser getSysUserByRecorderid() {
		return sysUserByRecorderid;
	}
	public void setSysUserByRecorderid(SysUser sysUserByRecorderid) {
		this.sysUserByRecorderid = sysUserByRecorderid;
	}
	public SysUser getSysUserByNexttacheer() {
		return sysUserByNexttacheer;
	}
	public void setSysUserByNexttacheer(SysUser sysUserByNexttacheer) {
		this.sysUserByNexttacheer = sysUserByNexttacheer;
	}
	public SysXq getSysXq() {
		return sysXq;
	}
	public void setSysXq(SysXq sysXq) {
		this.sysXq = sysXq;
	}
	public XmsbHylb getXmsbHylb() {
		return xmsbHylb;
	}
	public void setXmsbHylb(XmsbHylb xmsbHylb) {
		this.xmsbHylb = xmsbHylb;
	}
	public XmsbXmlx getXmsbXmlx() {
		return xmsbXmlx;
	}
	public void setXmsbXmlx(XmsbXmlx xmsbXmlx) {
		this.xmsbXmlx = xmsbXmlx;
	}
	public XmsbZjxz getXmsbZjxz() {
		return xmsbZjxz;
	}
	public void setXmsbZjxz(XmsbZjxz xmsbZjxz) {
		this.xmsbZjxz = xmsbZjxz;
	}
	public String getXmjbqkms() {
		return xmjbqkms;
	}
	public void setXmjbqkms(String xmjbqkms) {
		this.xmjbqkms = xmjbqkms;
	}
	public String getProjectcode() {
		return projectcode;
	}
	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public int getProjecttype() {
		return projecttype;
	}
	public void setProjecttype(int projecttype) {
		this.projecttype = projecttype;
	}
	public String getProjectcontent() {
		return projectcontent;
	}
	public void setProjectcontent(String projectcontent) {
		this.projectcontent = projectcontent;
	}
	public Integer getFinishcontentyear() {
		return finishcontentyear;
	}
	public void setFinishcontentyear(Integer finishcontentyear) {
		this.finishcontentyear = finishcontentyear;
	}
	public String getFinishcontent() {
		return finishcontent;
	}
	public void setFinishcontent(String finishcontent) {
		this.finishcontent = finishcontent;
	}
	public Integer getExpectfinishinvestyear() {
		return expectfinishinvestyear;
	}
	public void setExpectfinishinvestyear(Integer expectfinishinvestyear) {
		this.expectfinishinvestyear = expectfinishinvestyear;
	}
	public Double getExpectfinishinvest() {
		return expectfinishinvest;
	}
	public void setExpectfinishinvest(Double expectfinishinvest) {
		this.expectfinishinvest = expectfinishinvest;
	}
	public Integer getExpectfinishotherinvestyear() {
		return expectfinishotherinvestyear;
	}
	public void setExpectfinishotherinvestyear(Integer expectfinishotherinvestyear) {
		this.expectfinishotherinvestyear = expectfinishotherinvestyear;
	}
	public Double getExpectfinishotherinvest() {
		return expectfinishotherinvest;
	}
	public void setExpectfinishotherinvest(Double expectfinishotherinvest) {
		this.expectfinishotherinvest = expectfinishotherinvest;
	}
	public Date getWorkdate() {
		return workdate;
	}
	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}
	public Date getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}
	public Integer getStartyear() {
		return startyear;
	}
	public void setStartyear(Integer startyear) {
		this.startyear = startyear;
	}
	public Integer getEndyear() {
		return endyear;
	}
	public void setEndyear(Integer endyear) {
		this.endyear = endyear;
	}
	public String getWorkunitsid() {
		return workunitsid;
	}
	public void setWorkunitsid(String workunitsid) {
		this.workunitsid = workunitsid;
	}
	public String getProjectprincipal() {
		return projectprincipal;
	}
	public void setProjectprincipal(String projectprincipal) {
		this.projectprincipal = projectprincipal;
	}
	public String getContactaddress() {
		return contactaddress;
	}
	public void setContactaddress(String contactaddress) {
		this.contactaddress = contactaddress;
	}
	public String getContacttel() {
		return contacttel;
	}
	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}
	public String getProjectaddress() {
		return projectaddress;
	}
	public void setProjectaddress(String projectaddress) {
		this.projectaddress = projectaddress;
	}
	public String getDeclarerid() {
		return declarerid;
	}
	public void setDeclarerid(String declarerid) {
		this.declarerid = declarerid;
	}
	public Date getDeclartime() {
		return declartime;
	}
	public void setDeclartime(Date declartime) {
		this.declartime = declartime;
	}
	public String getManageunitsname() {
		return manageunitsname;
	}
	public void setManageunitsname(String manageunitsname) {
		this.manageunitsname = manageunitsname;
	}
	public Integer getDeclaretype() {
		return declaretype;
	}
	public void setDeclaretype(Integer declaretype) {
		this.declaretype = declaretype;
	}
	public String getDeclaregist() {
		return declaregist;
	}
	public void setDeclaregist(String declaregist) {
		this.declaregist = declaregist;
	}
	public String getDeclareplan() {
		return declareplan;
	}
	public void setDeclareplan(String declareplan) {
		this.declareplan = declareplan;
	}
	public String getDeclareproblem() {
		return declareproblem;
	}
	public void setDeclareproblem(String declareproblem) {
		this.declareproblem = declareproblem;
	}
	public Integer getPjstatus() {
		return pjstatus;
	}
	public void setPjstatus(Integer pjstatus) {
		this.pjstatus = pjstatus;
	}
	public Integer getViewstatus() {
		return viewstatus;
	}
	public void setViewstatus(Integer viewstatus) {
		this.viewstatus = viewstatus;
	}
	public String getNexttacheername() {
		return nexttacheername;
	}
	public void setNexttacheername(String nexttacheername) {
		this.nexttacheername = nexttacheername;
	}
	public String getParentprojectcode() {
		return parentprojectcode;
	}
	public void setParentprojectcode(String parentprojectcode) {
		this.parentprojectcode = parentprojectcode;
	}
	public String getRecordername() {
		return recordername;
	}
	public void setRecordername(String recordername) {
		this.recordername = recordername;
	}
	public Date getRecordertime() {
		return recordertime;
	}
	public void setRecordertime(Date recordertime) {
		this.recordertime = recordertime;
	}
	public Integer getAuditdept() {
		return auditdept;
	}
	public void setAuditdept(Integer auditdept) {
		this.auditdept = auditdept;
	}
	public String getAuditdeptname() {
		return auditdeptname;
	}
	public void setAuditdeptname(String auditdeptname) {
		this.auditdeptname = auditdeptname;
	}
	public Integer getNextauditdept() {
		return nextauditdept;
	}
	public void setNextauditdept(Integer nextauditdept) {
		this.nextauditdept = nextauditdept;
	}
	public String getNextauditdeptname() {
		return nextauditdeptname;
	}
	public void setNextauditdeptname(String nextauditdeptname) {
		this.nextauditdeptname = nextauditdeptname;
	}
	public String getXmjd() {
		return xmjd;
	}
	public void setXmjd(String xmjd) {
		this.xmjd = xmjd;
	}
	public Integer getSfzdxm() {
		return sfzdxm;
	}
	public void setSfzdxm(Integer sfzdxm) {
		this.sfzdxm = sfzdxm;
	}
	public String getFgwcsyj() {
		return fgwcsyj;
	}
	public void setFgwcsyj(String fgwcsyj) {
		this.fgwcsyj = fgwcsyj;
	}
	public String getYjtxr() {
		return yjtxr;
	}
	public void setYjtxr(String yjtxr) {
		this.yjtxr = yjtxr;
	}
	public String getYpzxyj() {
		return ypzxyj;
	}
	public void setYpzxyj(String ypzxyj) {
		this.ypzxyj = ypzxyj;
	}
	public String getYpzxyjfkr() {
		return ypzxyjfkr;
	}
	public void setYpzxyjfkr(String ypzxyjfkr) {
		this.ypzxyjfkr = ypzxyjfkr;
	}
	public Integer getPjinvestid() {
		return pjinvestid;
	}
	public void setPjinvestid(Integer pjinvestid) {
		this.pjinvestid = pjinvestid;
	}
	public Pjbaseinfo getPjbaseinfo() {
		return pjbaseinfo;
	}
	public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
		this.pjbaseinfo = pjbaseinfo;
	}
	public Double getPjinvestsum() {
		return pjinvestsum;
	}
	public void setPjinvestsum(Double pjinvestsum) {
		this.pjinvestsum = pjinvestsum;
	}
	public Double getPjinvestcenter() {
		return pjinvestcenter;
	}
	public void setPjinvestcenter(Double pjinvestcenter) {
		this.pjinvestcenter = pjinvestcenter;
	}
	public Double getPjinvestprovince() {
		return pjinvestprovince;
	}
	public void setPjinvestprovince(Double pjinvestprovince) {
		this.pjinvestprovince = pjinvestprovince;
	}
	public Double getPjinvestcity() {
		return pjinvestcity;
	}
	public void setPjinvestcity(Double pjinvestcity) {
		this.pjinvestcity = pjinvestcity;
	}
	public Double getPjinvesttown() {
		return pjinvesttown;
	}
	public void setPjinvesttown(Double pjinvesttown) {
		this.pjinvesttown = pjinvesttown;
	}
	public Double getPjinvestcompany() {
		return pjinvestcompany;
	}
	public void setPjinvestcompany(Double pjinvestcompany) {
		this.pjinvestcompany = pjinvestcompany;
	}
	public Double getPjinvestbusiness() {
		return pjinvestbusiness;
	}
	public void setPjinvestbusiness(Double pjinvestbusiness) {
		this.pjinvestbusiness = pjinvestbusiness;
	}
	public Double getPjinvestbank() {
		return pjinvestbank;
	}
	public void setPjinvestbank(Double pjinvestbank) {
		this.pjinvestbank = pjinvestbank;
	}
	public Double getPjinvestother() {
		return pjinvestother;
	}
	public void setPjinvestother(Double pjinvestother) {
		this.pjinvestother = pjinvestother;
	}
	public String getPjinvestadvice() {
		return pjinvestadvice;
	}
	public void setPjinvestadvice(String pjinvestadvice) {
		this.pjinvestadvice = pjinvestadvice;
	}
	public Integer getPlaninvestyear() {
		return planinvestyear;
	}
	public void setPlaninvestyear(Integer planinvestyear) {
		this.planinvestyear = planinvestyear;
	}
	public Double getPlaninvestsum() {
		return planinvestsum;
	}
	public void setPlaninvestsum(Double planinvestsum) {
		this.planinvestsum = planinvestsum;
	}
	public Double getPlaninvestcenter() {
		return planinvestcenter;
	}
	public void setPlaninvestcenter(Double planinvestcenter) {
		this.planinvestcenter = planinvestcenter;
	}
	public Double getPlaninvestprovince() {
		return planinvestprovince;
	}
	public void setPlaninvestprovince(Double planinvestprovince) {
		this.planinvestprovince = planinvestprovince;
	}
	public Double getPlaninvestcity() {
		return planinvestcity;
	}
	public void setPlaninvestcity(Double planinvestcity) {
		this.planinvestcity = planinvestcity;
	}
	public Double getPlaninvesttown() {
		return planinvesttown;
	}
	public void setPlaninvesttown(Double planinvesttown) {
		this.planinvesttown = planinvesttown;
	}
	public Double getPlaninvestcompany() {
		return planinvestcompany;
	}
	public void setPlaninvestcompany(Double planinvestcompany) {
		this.planinvestcompany = planinvestcompany;
	}
	public Double getPlaninvestbusiness() {
		return planinvestbusiness;
	}
	public void setPlaninvestbusiness(Double planinvestbusiness) {
		this.planinvestbusiness = planinvestbusiness;
	}
	public Double getPlaninvestbank() {
		return planinvestbank;
	}
	public void setPlaninvestbank(Double planinvestbank) {
		this.planinvestbank = planinvestbank;
	}
	public Double getPlaninvestother() {
		return planinvestother;
	}
	public void setPlaninvestother(Double planinvestother) {
		this.planinvestother = planinvestother;
	}
	public String getPlaninvestadvice() {
		return planinvestadvice;
	}
	public void setPlaninvestadvice(String planinvestadvice) {
		this.planinvestadvice = planinvestadvice;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getXmcblb() {
		return xmcblb;
	}
	public void setXmcblb(String xmcblb) {
		this.xmcblb = xmcblb;
	}
	public String getOrganOrDept() {
		return organOrDept;
	}
	public void setOrganOrDept(String organOrDept) {
		this.organOrDept = organOrDept;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Double getPjinvestcitySh() {
		return pjinvestcitySh;
	}
	public void setPjinvestcitySh(Double pjinvestcitySh) {
		this.pjinvestcitySh = pjinvestcitySh;
	}
	public Double getPlaninvestcitySh() {
		return planinvestcitySh;
	}
	public void setPlaninvestcitySh(Double planinvestcitySh) {
		this.planinvestcitySh = planinvestcitySh;
	}
	public String getPjauditmind() {
		return pjauditmind;
	}
	public void setPjauditmind(String pjauditmind) {
		this.pjauditmind = pjauditmind;
	}
	public Integer getXmfl() {
		return xmfl;
	}
	public void setXmfl(Integer xmfl) {
		this.xmfl = xmfl;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getPjauditdept() {
		return pjauditdept;
	}
	public void setPjauditdept(String pjauditdept) {
		this.pjauditdept = pjauditdept;
	}
	public String getPjaudituser() {
		return pjaudituser;
	}
	public void setPjaudituser(String pjaudituser) {
		this.pjaudituser = pjaudituser;
	}
	public Date getPjaudittime() {
		return pjaudittime;
	}
	public void setPjaudittime(Date pjaudittime) {
		this.pjaudittime = pjaudittime;
	}
	public String getBudgetDeclareOrgan() {
		return budgetDeclareOrgan;
	}
	public void setBudgetDeclareOrgan(String budgetDeclareOrgan) {
		this.budgetDeclareOrgan = budgetDeclareOrgan;
	}
	public String getBudgetDirectorOrgan() {
		return budgetDirectorOrgan;
	}
	public void setBudgetDirectorOrgan(String budgetDirectorOrgan) {
		this.budgetDirectorOrgan = budgetDirectorOrgan;
	}
    
    
	
    

}
