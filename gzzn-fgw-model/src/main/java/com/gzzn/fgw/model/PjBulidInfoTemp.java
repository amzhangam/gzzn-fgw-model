package com.gzzn.fgw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PjBulidInfoTemp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PJ_BULID_INFO_TEMP")
public class PjBulidInfoTemp implements java.io.Serializable {

	// Fields

	private Long bulidId;
	private SysUser sysUser;
	private SysOrganization sysOrganization;
	private String projectname;
	private String cylb;
	private String projectcontent;
	private String bulidQznx;
	private Double pjinvestsum;
	private Double expectfinishinvest;
	private Double currentfinishinvest;
	private Double planinvestsum;
	private String lxpfwh;
	private String bulidRate;
	private String remark;
	private Integer zdxmbz;
	private String tzlx;
	private String projectcontact;
	private String phone;
	private Date updateTime;
	private String sshy;
	private String xmdw;
	private String pfdw;
	private String pfsj;
	private String xmlx;
	private String tbdw;
	private String xmsd;

	// Constructors

	/** default constructor */
	public PjBulidInfoTemp() {
	}

	/** full constructor */
	public PjBulidInfoTemp(SysUser sysUser, SysOrganization sysOrganization,
			String projectname, String cylb, String projectcontent,
			String bulidQznx, Double pjinvestsum, Double expectfinishinvest,
			Double currentfinishinvest, Double planinvestsum, String lxpfwh,
			String bulidRate, String remark, Integer zdxmbz, String tzlx,
			String projectcontact, String phone, Date updateTime, String sshy,
			String xmdw, String pfdw, String pfsj, String xmlx, String tbdw,
			String xmsd) {
		this.sysUser = sysUser;
		this.sysOrganization = sysOrganization;
		this.projectname = projectname;
		this.cylb = cylb;
		this.projectcontent = projectcontent;
		this.bulidQznx = bulidQznx;
		this.pjinvestsum = pjinvestsum;
		this.expectfinishinvest = expectfinishinvest;
		this.currentfinishinvest = currentfinishinvest;
		this.planinvestsum = planinvestsum;
		this.lxpfwh = lxpfwh;
		this.bulidRate = bulidRate;
		this.remark = remark;
		this.zdxmbz = zdxmbz;
		this.tzlx = tzlx;
		this.projectcontact = projectcontact;
		this.phone = phone;
		this.updateTime = updateTime;
		this.sshy = sshy;
		this.xmdw = xmdw;
		this.pfdw = pfdw;
		this.pfsj = pfsj;
		this.xmlx = xmlx;
		this.tbdw = tbdw;
		this.xmsd = xmsd;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "xmsb")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "BULID_ID", unique = true, nullable = false, scale = 0)
	public Long getBulidId() {
		return this.bulidId;
	}

	public void setBulidId(Long bulidId) {
		this.bulidId = bulidId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATION_ID")
	public SysOrganization getSysOrganization() {
		return this.sysOrganization;
	}

	public void setSysOrganization(SysOrganization sysOrganization) {
		this.sysOrganization = sysOrganization;
	}

	@Column(name = "PROJECTNAME", length = 200)
	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	@Column(name = "CYLB", length = 50)
	public String getCylb() {
		return this.cylb;
	}

	public void setCylb(String cylb) {
		this.cylb = cylb;
	}

	@Column(name = "PROJECTCONTENT", length = 4000)
	public String getProjectcontent() {
		return this.projectcontent;
	}

	public void setProjectcontent(String projectcontent) {
		this.projectcontent = projectcontent;
	}

	@Column(name = "BULID_QZNX", length = 50)
	public String getBulidQznx() {
		return this.bulidQznx;
	}

	public void setBulidQznx(String bulidQznx) {
		this.bulidQznx = bulidQznx;
	}

	@Column(name = "PJINVESTSUM", precision = 18)
	public Double getPjinvestsum() {
		return this.pjinvestsum;
	}

	public void setPjinvestsum(Double pjinvestsum) {
		this.pjinvestsum = pjinvestsum;
	}

	@Column(name = "EXPECTFINISHINVEST", precision = 18)
	public Double getExpectfinishinvest() {
		return this.expectfinishinvest;
	}

	public void setExpectfinishinvest(Double expectfinishinvest) {
		this.expectfinishinvest = expectfinishinvest;
	}

	@Column(name = "CURRENTFINISHINVEST", precision = 18)
	public Double getCurrentfinishinvest() {
		return this.currentfinishinvest;
	}

	public void setCurrentfinishinvest(Double currentfinishinvest) {
		this.currentfinishinvest = currentfinishinvest;
	}

	@Column(name = "PLANINVESTSUM", precision = 18)
	public Double getPlaninvestsum() {
		return this.planinvestsum;
	}

	public void setPlaninvestsum(Double planinvestsum) {
		this.planinvestsum = planinvestsum;
	}

	@Column(name = "LXPFWH", length = 1000)
	public String getLxpfwh() {
		return this.lxpfwh;
	}

	public void setLxpfwh(String lxpfwh) {
		this.lxpfwh = lxpfwh;
	}

	@Column(name = "BULID_RATE", length = 50)
	public String getBulidRate() {
		return this.bulidRate;
	}

	public void setBulidRate(String bulidRate) {
		this.bulidRate = bulidRate;
	}

	@Column(name = "REMARK", length = 4000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ZDXMBZ", precision = 22, scale = 0)
	public Integer getZdxmbz() {
		return this.zdxmbz;
	}

	public void setZdxmbz(Integer zdxmbz) {
		this.zdxmbz = zdxmbz;
	}

	@Column(name = "TZLX", length = 50)
	public String getTzlx() {
		return this.tzlx;
	}

	public void setTzlx(String tzlx) {
		this.tzlx = tzlx;
	}

	@Column(name = "PROJECTCONTACT", length = 50)
	public String getProjectcontact() {
		return this.projectcontact;
	}

	public void setProjectcontact(String projectcontact) {
		this.projectcontact = projectcontact;
	}

	@Column(name = "PHONE", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "UPDATE_TIME", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	

	@Column(name = "SSHY", length = 50)
	public String getSshy() {
		return this.sshy;
	}

	public void setSshy(String sshy) {
		this.sshy = sshy;
	}

	@Column(name = "XMDW", length = 150)
	public String getXmdw() {
		return this.xmdw;
	}

	public void setXmdw(String xmdw) {
		this.xmdw = xmdw;
	}

	@Column(name = "PFDW", length = 150)
	public String getPfdw() {
		return this.pfdw;
	}

	public void setPfdw(String pfdw) {
		this.pfdw = pfdw;
	}

	@Column(name = "PFSJ", length = 20)
	public String getPfsj() {
		return this.pfsj;
	}

	public void setPfsj(String pfsj) {
		this.pfsj = pfsj;
	}

	@Column(name = "XMLX", length = 100)
	public String getXmlx() {
		return this.xmlx;
	}

	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}
	
	@Column(name = "TBDW", length = 150)
	public String getTbdw() {
		return this.tbdw;
	}

	public void setTbdw(String tbdw) {
		this.tbdw = tbdw;
	}

	@Column(name = "XMSD", length = 50)
	public String getXmsd() {
		return this.xmsd;
	}

	public void setXmsd(String xmsd) {
		this.xmsd = xmsd;
	}


}