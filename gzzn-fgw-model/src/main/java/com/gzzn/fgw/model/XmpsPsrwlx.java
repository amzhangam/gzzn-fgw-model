package com.gzzn.fgw.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * XmpsPsrwlx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "XMPS_PSRWLX")
public class XmpsPsrwlx implements java.io.Serializable {

	// Fields

	private Integer psrwlxid;
	private String psrwlxmc;
	private Integer scbj;
	private Set<XmpsPsrw> xmpsPsrws = new HashSet<XmpsPsrw>(0);

	// Constructors

	/** default constructor */
	public XmpsPsrwlx() {
	}

	/** full constructor */
	public XmpsPsrwlx(String psrwlxmc, Integer scbj, Set<XmpsPsrw> xmpsPsrws) {
		this.psrwlxmc = psrwlxmc;
		this.scbj = scbj;
		this.xmpsPsrws = xmpsPsrws;
	}

	// Property accessors
	@Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	@Column(name = "PSRWLXID", unique = true, nullable = false, scale = 0)
	public Integer getPsrwlxid() {
		return this.psrwlxid;
	}

	public void setPsrwlxid(Integer psrwlxid) {
		this.psrwlxid = psrwlxid;
	}

	@Column(name = "PSRWLXMC", length = 50)
	public String getPsrwlxmc() {
		return this.psrwlxmc;
	}

	public void setPsrwlxmc(String psrwlxmc) {
		this.psrwlxmc = psrwlxmc;
	}

	@Column(name = "SCBJ", precision = 22, scale = 0)
	public Integer getScbj() {
		return this.scbj;
	}

	public void setScbj(Integer scbj) {
		this.scbj = scbj;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "xmpsPsrwlx")
	public Set<XmpsPsrw> getXmpsPsrws() {
		return this.xmpsPsrws;
	}

	public void setXmpsPsrws(Set<XmpsPsrw> xmpsPsrws) {
		this.xmpsPsrws = xmpsPsrws;
	}

}