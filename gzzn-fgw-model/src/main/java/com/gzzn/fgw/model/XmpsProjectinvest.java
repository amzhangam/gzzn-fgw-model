package com.gzzn.fgw.model;

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

/**
 * XmpsProjectinvest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "XMPS_PROJECTINVEST")
public class XmpsProjectinvest implements java.io.Serializable {

	// Fields

	private Integer pjinvestid;
	private XmpsPjbaseinfo xmpsPjbaseinfo;
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

	// Constructors

	/** default constructor */
	public XmpsProjectinvest() {
	}

	/** full constructor */
	public XmpsProjectinvest(XmpsPjbaseinfo xmpsPjbaseinfo, Double pjinvestsum,
			Double pjinvestcenter, Double pjinvestprovince,
			Double pjinvestcity, Double pjinvesttown, Double pjinvestcompany,
			Double pjinvestbusiness, Double pjinvestbank, Double pjinvestother,
			String pjinvestadvice, Integer planinvestyear,
			Double planinvestsum, Double planinvestcenter,
			Double planinvestprovince, Double planinvestcity,
			Double planinvesttown, Double planinvestcompany,
			Double planinvestbusiness, Double planinvestbank,
			Double planinvestother, String planinvestadvice) {
		this.xmpsPjbaseinfo = xmpsPjbaseinfo;
		this.pjinvestsum = pjinvestsum;
		this.pjinvestcenter = pjinvestcenter;
		this.pjinvestprovince = pjinvestprovince;
		this.pjinvestcity = pjinvestcity;
		this.pjinvesttown = pjinvesttown;
		this.pjinvestcompany = pjinvestcompany;
		this.pjinvestbusiness = pjinvestbusiness;
		this.pjinvestbank = pjinvestbank;
		this.pjinvestother = pjinvestother;
		this.pjinvestadvice = pjinvestadvice;
		this.planinvestyear = planinvestyear;
		this.planinvestsum = planinvestsum;
		this.planinvestcenter = planinvestcenter;
		this.planinvestprovince = planinvestprovince;
		this.planinvestcity = planinvestcity;
		this.planinvesttown = planinvesttown;
		this.planinvestcompany = planinvestcompany;
		this.planinvestbusiness = planinvestbusiness;
		this.planinvestbank = planinvestbank;
		this.planinvestother = planinvestother;
		this.planinvestadvice = planinvestadvice;
	}

	// Property accessors
	@Id
	@Column(name = "PJINVESTID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getPjinvestid() {
		return this.pjinvestid;
	}

	public void setPjinvestid(Integer pjinvestid) {
		this.pjinvestid = pjinvestid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECTID")
	public XmpsPjbaseinfo getXmpsPjbaseinfo() {
		return this.xmpsPjbaseinfo;
	}

	public void setXmpsPjbaseinfo(XmpsPjbaseinfo xmpsPjbaseinfo) {
		this.xmpsPjbaseinfo = xmpsPjbaseinfo;
	}

	@Column(name = "PJINVESTSUM", precision = 18)
	public Double getPjinvestsum() {
		return this.pjinvestsum;
	}

	public void setPjinvestsum(Double pjinvestsum) {
		this.pjinvestsum = pjinvestsum;
	}

	@Column(name = "PJINVESTCENTER", precision = 18)
	public Double getPjinvestcenter() {
		return this.pjinvestcenter;
	}

	public void setPjinvestcenter(Double pjinvestcenter) {
		this.pjinvestcenter = pjinvestcenter;
	}

	@Column(name = "PJINVESTPROVINCE", precision = 18)
	public Double getPjinvestprovince() {
		return this.pjinvestprovince;
	}

	public void setPjinvestprovince(Double pjinvestprovince) {
		this.pjinvestprovince = pjinvestprovince;
	}

	@Column(name = "PJINVESTCITY", precision = 18)
	public Double getPjinvestcity() {
		return this.pjinvestcity;
	}

	public void setPjinvestcity(Double pjinvestcity) {
		this.pjinvestcity = pjinvestcity;
	}

	@Column(name = "PJINVESTTOWN", precision = 18)
	public Double getPjinvesttown() {
		return this.pjinvesttown;
	}

	public void setPjinvesttown(Double pjinvesttown) {
		this.pjinvesttown = pjinvesttown;
	}

	@Column(name = "PJINVESTCOMPANY", precision = 18)
	public Double getPjinvestcompany() {
		return this.pjinvestcompany;
	}

	public void setPjinvestcompany(Double pjinvestcompany) {
		this.pjinvestcompany = pjinvestcompany;
	}

	@Column(name = "PJINVESTBUSINESS", precision = 18)
	public Double getPjinvestbusiness() {
		return this.pjinvestbusiness;
	}

	public void setPjinvestbusiness(Double pjinvestbusiness) {
		this.pjinvestbusiness = pjinvestbusiness;
	}

	@Column(name = "PJINVESTBANK", precision = 18)
	public Double getPjinvestbank() {
		return this.pjinvestbank;
	}

	public void setPjinvestbank(Double pjinvestbank) {
		this.pjinvestbank = pjinvestbank;
	}

	@Column(name = "PJINVESTOTHER", precision = 18)
	public Double getPjinvestother() {
		return this.pjinvestother;
	}

	public void setPjinvestother(Double pjinvestother) {
		this.pjinvestother = pjinvestother;
	}

	@Column(name = "PJINVESTADVICE", length = 1000)
	public String getPjinvestadvice() {
		return this.pjinvestadvice;
	}

	public void setPjinvestadvice(String pjinvestadvice) {
		this.pjinvestadvice = pjinvestadvice;
	}

	@Column(name = "PLANINVESTYEAR", precision = 22, scale = 0)
	public Integer getPlaninvestyear() {
		return this.planinvestyear;
	}

	public void setPlaninvestyear(Integer planinvestyear) {
		this.planinvestyear = planinvestyear;
	}

	@Column(name = "PLANINVESTSUM", precision = 18)
	public Double getPlaninvestsum() {
		return this.planinvestsum;
	}

	public void setPlaninvestsum(Double planinvestsum) {
		this.planinvestsum = planinvestsum;
	}

	@Column(name = "PLANINVESTCENTER", precision = 18)
	public Double getPlaninvestcenter() {
		return this.planinvestcenter;
	}

	public void setPlaninvestcenter(Double planinvestcenter) {
		this.planinvestcenter = planinvestcenter;
	}

	@Column(name = "PLANINVESTPROVINCE", precision = 18)
	public Double getPlaninvestprovince() {
		return this.planinvestprovince;
	}

	public void setPlaninvestprovince(Double planinvestprovince) {
		this.planinvestprovince = planinvestprovince;
	}

	@Column(name = "PLANINVESTCITY", precision = 18)
	public Double getPlaninvestcity() {
		return this.planinvestcity;
	}

	public void setPlaninvestcity(Double planinvestcity) {
		this.planinvestcity = planinvestcity;
	}

	@Column(name = "PLANINVESTTOWN", precision = 18)
	public Double getPlaninvesttown() {
		return this.planinvesttown;
	}

	public void setPlaninvesttown(Double planinvesttown) {
		this.planinvesttown = planinvesttown;
	}

	@Column(name = "PLANINVESTCOMPANY", precision = 18)
	public Double getPlaninvestcompany() {
		return this.planinvestcompany;
	}

	public void setPlaninvestcompany(Double planinvestcompany) {
		this.planinvestcompany = planinvestcompany;
	}

	@Column(name = "PLANINVESTBUSINESS", precision = 18)
	public Double getPlaninvestbusiness() {
		return this.planinvestbusiness;
	}

	public void setPlaninvestbusiness(Double planinvestbusiness) {
		this.planinvestbusiness = planinvestbusiness;
	}

	@Column(name = "PLANINVESTBANK", precision = 18)
	public Double getPlaninvestbank() {
		return this.planinvestbank;
	}

	public void setPlaninvestbank(Double planinvestbank) {
		this.planinvestbank = planinvestbank;
	}

	@Column(name = "PLANINVESTOTHER", precision = 18)
	public Double getPlaninvestother() {
		return this.planinvestother;
	}

	public void setPlaninvestother(Double planinvestother) {
		this.planinvestother = planinvestother;
	}

	@Column(name = "PLANINVESTADVICE", length = 1000)
	public String getPlaninvestadvice() {
		return this.planinvestadvice;
	}

	public void setPlaninvestadvice(String planinvestadvice) {
		this.planinvestadvice = planinvestadvice;
	}

}