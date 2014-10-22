package com.gzzn.fgw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * XmpsXmfjnr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "XMPS_XMFJNR")
public class XmpsXmfjnr implements java.io.Serializable {

	private Integer pjadjunctid;
	private byte[] fjnr;
	private Integer dqbj;

	// Constructors

	/** default constructor */
	public XmpsXmfjnr() {
	}

	/** full constructor */
	public XmpsXmfjnr(Integer pjadjunctid, byte[] fjnr, Integer dqbj) {
		this.pjadjunctid = pjadjunctid;
		this.fjnr = fjnr;
		this.dqbj = dqbj;
	}

	// Property accessors

	@Id
	@Column(name="PJADJUNCTID", unique=true, nullable=false, scale=0)
	public Integer getPjadjunctid() {
		return this.pjadjunctid;
	}

	public void setPjadjunctid(Integer pjadjunctid) {
		this.pjadjunctid = pjadjunctid;
	}

	@Column(name = "FJNR", columnDefinition = "BLOB", nullable = true)
	public byte[] getFjnr() {
		return this.fjnr;
	}

	public void setFjnr(byte[] fjnr) {
		this.fjnr = fjnr;
	}

	@Column(name = "DQBJ", precision = 1, scale = 0)
	public Integer getDqbj() {
		return this.dqbj;
	}

	public void setDqbj(Integer dqbj) {
		this.dqbj = dqbj;
	}

}