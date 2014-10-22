package com.gzzn.fgw.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SysXq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_XQ"

)
public class SysXq implements java.io.Serializable {

	// Fields

	private Integer xqId;
	private String xqmc;
	private SysXq sjxq;
	private Integer xqlx;
	private String xzqydm;
	private Integer deleted;
	private String xqxxmc;
	private Set<Pjbaseinfo> pjbaseinfos = new HashSet<Pjbaseinfo>(0);
	private Set<SysOrganization> sysOrganizations = new HashSet<SysOrganization>(
			0);

	// Constructors

	/** default constructor */
	public SysXq() {
	}

	/** full constructor */
	public SysXq(String xqmc, SysXq sjxq, Integer xqlx, String xzqydm,
			Integer deleted,String xqxxmc, Set<Pjbaseinfo> pjbaseinfos,
			Set<SysOrganization> sysOrganizations) {
		this.xqmc = xqmc;
		this.sjxq = sjxq;
		this.xqlx = xqlx;
		this.xzqydm = xzqydm;
		this.deleted = deleted;
		this.xqxxmc = xqxxmc;
		this.pjbaseinfos = pjbaseinfos;
		this.sysOrganizations = sysOrganizations;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "xmsb")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "XQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getXqId() {
		return this.xqId;
	}

	public void setXqId(Integer xqId) {
		this.xqId = xqId;
	}

	@Column(name = "XQMC", length = 50)
	public String getXqmc() {
		return this.xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SJXQ_ID")
	public SysXq getSjxq() {
		return this.sjxq;
	}

	public void setSjxq(SysXq sjxq) {
		this.sjxq = sjxq;
	}

	@Column(name = "XQLX", precision = 1, scale = 0)
	public Integer getXqlx() {
		return this.xqlx;
	}

	public void setXqlx(Integer xqlx) {
		this.xqlx = xqlx;
	}

	@Column(name = "XZQYDM", length = 50)
	public String getXzqydm() {
		return this.xzqydm;
	}

	public void setXzqydm(String xzqydm) {
		this.xzqydm = xzqydm;
	}

	@Column(name = "DELETED", precision = 1, scale = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	
	@Column(name = "XQXXMC", length = 200)
	public String getXqxxmc() {
		return this.xqxxmc;
	}

	public void setXqxxmc(String xqxxmc) {
		this.xqxxmc = xqxxmc;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysXq")
	public Set<Pjbaseinfo> getPjbaseinfos() {
		return this.pjbaseinfos;
	}

	public void setPjbaseinfos(Set<Pjbaseinfo> pjbaseinfos) {
		this.pjbaseinfos = pjbaseinfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysXq")
	public Set<SysOrganization> getSysOrganizations() {
		return this.sysOrganizations;
	}

	public void setSysOrganizations(Set<SysOrganization> sysOrganizations) {
		this.sysOrganizations = sysOrganizations;
	}

}