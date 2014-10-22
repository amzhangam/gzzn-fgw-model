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
 * XmpsPsrw entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "XMPS_PSRW")
public class XmpsPsrw implements java.io.Serializable {

	// Fields

	private Integer psrwid;
	private XmpsPsrwlx xmpsPsrwlx;
	private Integer projectid;
	private Integer bjljid;
	private String xmbh;
	private String xmmc;
	private String ssdw;
	private Date kspssj;
	private Date jspssj;
	private Integer psrwzt;
	private Boolean psjg;
	private String psyj;
	private Integer cjrId;
	private String cjrMc;
	private Date rwcjsj;
	private Date pshzsj;
	private String zzdw;
	private String psdd;
	private Integer zjzzId;
	private Integer hzrId;
	private String hzrMc;
	private String sbdw;
	private Date pssj;
	private Date sbrq;

	// Constructors

	/** default constructor */
	public XmpsPsrw() {
	}

	/** full constructor */
	public XmpsPsrw(XmpsPsrwlx xmpsPsrwlx, Integer projectid,
			Integer bjljid, String xmbh, String xmmc, String ssdw,
			Date kspssj, Date jspssj, Integer psrwzt, Boolean psjg,
			String psyj, Integer cjrId, String cjrMc, Date rwcjsj,
			Date pshzsj, String zzdw, String psdd, Integer zjzzId,
			Integer hzrId, String hzrMc, String sbdw, Date pssj, Date sbrq) {
		this.xmpsPsrwlx = xmpsPsrwlx;
		this.projectid = projectid;
		this.bjljid = bjljid;
		this.xmbh = xmbh;
		this.xmmc = xmmc;
		this.ssdw = ssdw;
		this.kspssj = kspssj;
		this.jspssj = jspssj;
		this.psrwzt = psrwzt;
		this.psjg = psjg;
		this.psyj = psyj;
		this.cjrId = cjrId;
		this.cjrMc = cjrMc;
		this.rwcjsj = rwcjsj;
		this.pshzsj = pshzsj;
		this.zzdw = zzdw;
		this.psdd = psdd;
		this.zjzzId = zjzzId;
		this.hzrId = hzrId;
		this.hzrMc = hzrMc;
		this.sbdw = sbdw;
		this.pssj = pssj;
		this.sbrq = sbrq;
	}

	// Property accessors
	@Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	@Column(name = "PSRWID", unique = true, nullable = false, scale = 0)
	public Integer getPsrwid() {
		return this.psrwid;
	}

	public void setPsrwid(Integer psrwid) {
		this.psrwid = psrwid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PSRWLXID")
	public XmpsPsrwlx getXmpsPsrwlx() {
		return this.xmpsPsrwlx;
	}

	public void setXmpsPsrwlx(XmpsPsrwlx xmpsPsrwlx) {
		this.xmpsPsrwlx = xmpsPsrwlx;
	}

	
	@Column(name = "PROJECTID", scale = 0)
	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	@Column(name = "BJLJID", scale = 0)
	public Integer getBjljid() {
		return this.bjljid;
	}

	public void setBjljid(Integer bjljid) {
		this.bjljid = bjljid;
	}

	@Column(name = "XMBH", length = 50)
	public String getXmbh() {
		return this.xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	@Column(name = "XMMC", length = 100)
	public String getXmmc() {
		return this.xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	@Column(name = "SSDW", length = 50)
	public String getSsdw() {
		return this.ssdw;
	}

	public void setSsdw(String ssdw) {
		this.ssdw = ssdw;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "KSPSSJ", length = 7)
	public Date getKspssj() {
		return this.kspssj;
	}

	public void setKspssj(Date kspssj) {
		this.kspssj = kspssj;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "JSPSSJ", length = 7)
	public Date getJspssj() {
		return this.jspssj;
	}

	public void setJspssj(Date jspssj) {
		this.jspssj = jspssj;
	}

	@Column(name = "PSRWZT", precision = 22, scale = 0)
	public Integer getPsrwzt() {
		return this.psrwzt;
	}

	public void setPsrwzt(Integer psrwzt) {
		this.psrwzt = psrwzt;
	}

	@Column(name = "PSJG", precision = 1, scale = 0)
	public Boolean getPsjg() {
		return this.psjg;
	}

	public void setPsjg(Boolean psjg) {
		this.psjg = psjg;
	}

	@Column(name = "PSYJ", length = 500)
	public String getPsyj() {
		return this.psyj;
	}

	public void setPsyj(String psyj) {
		this.psyj = psyj;
	}

	@Column(name = "CJR_ID", scale = 0)
	public Integer getCjrId() {
		return this.cjrId;
	}

	public void setCjrId(Integer cjrId) {
		this.cjrId = cjrId;
	}

	@Column(name = "CJR_MC", length = 50)
	public String getCjrMc() {
		return this.cjrMc;
	}

	public void setCjrMc(String cjrMc) {
		this.cjrMc = cjrMc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RWCJSJ", length = 7)
	public Date getRwcjsj() {
		return this.rwcjsj;
	}

	public void setRwcjsj(Date rwcjsj) {
		this.rwcjsj = rwcjsj;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PSHZSJ", length = 7)
	public Date getPshzsj() {
		return this.pshzsj;
	}

	public void setPshzsj(Date pshzsj) {
		this.pshzsj = pshzsj;
	}

	@Column(name = "ZZDW", length = 50)
	public String getZzdw() {
		return this.zzdw;
	}

	public void setZzdw(String zzdw) {
		this.zzdw = zzdw;
	}

	@Column(name = "PSDD", length = 100)
	public String getPsdd() {
		return this.psdd;
	}

	public void setPsdd(String psdd) {
		this.psdd = psdd;
	}

	@Column(name = "ZJZZ_ID", scale = 0)
	public Integer getZjzzId() {
		return this.zjzzId;
	}

	public void setZjzzId(Integer zjzzId) {
		this.zjzzId = zjzzId;
	}

	@Column(name = "HZR_ID", scale = 0)
	public Integer getHzrId() {
		return this.hzrId;
	}

	public void setHzrId(Integer hzrId) {
		this.hzrId = hzrId;
	}

	@Column(name = "HZR_MC", length = 50)
	public String getHzrMc() {
		return this.hzrMc;
	}

	public void setHzrMc(String hzrMc) {
		this.hzrMc = hzrMc;
	}

	@Column(name = "SBDW", length = 50)
	public String getSbdw() {
		return this.sbdw;
	}

	public void setSbdw(String sbdw) {
		this.sbdw = sbdw;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PSSJ", length = 7)
	public Date getPssj() {
		return this.pssj;
	}

	public void setPssj(Date pssj) {
		this.pssj = pssj;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SBRQ", length = 7)
	public Date getSbrq() {
		return this.sbrq;
	}

	public void setSbrq(Date sbrq) {
		this.sbrq = sbrq;
	}

}