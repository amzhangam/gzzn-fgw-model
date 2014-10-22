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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * IndusClassGbcode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INDUS_CLASS_GBCODE")
public class IndusClassGbcode implements java.io.Serializable {

	// Fields

	private Long indusId;
	private IndusClassGbcode indusClassGbcode;
	private String indusName;
	private String indusCode;
	private Boolean indusLevel;
	private String indusDetail;
	private Set<IndusClassGbcode> indusClassGbcodes = new HashSet<IndusClassGbcode>(
			0);

	// Constructors

	/** default constructor */
	public IndusClassGbcode() {
	}

	/** full constructor */
	public IndusClassGbcode(IndusClassGbcode indusClassGbcode,
			String indusName, String indusCode, Boolean indusLevel,
			String indusDetail, Set<IndusClassGbcode> indusClassGbcodes) {
		this.indusClassGbcode = indusClassGbcode;
		this.indusName = indusName;
		this.indusCode = indusCode;
		this.indusLevel = indusLevel;
		this.indusDetail = indusDetail;
		this.indusClassGbcodes = indusClassGbcodes;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "xmsb")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "INDUS_ID", unique = true, nullable = false, scale = 0)
	public Long getIndusId() {
		return this.indusId;
	}

	public void setIndusId(Long indusId) {
		this.indusId = indusId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_INDUS_ID")
	public IndusClassGbcode getIndusClassGbcode() {
		return this.indusClassGbcode;
	}

	public void setIndusClassGbcode(IndusClassGbcode indusClassGbcode) {
		this.indusClassGbcode = indusClassGbcode;
	}

	@Column(name = "INDUS_NAME", length = 50)
	public String getIndusName() {
		return this.indusName;
	}

	public void setIndusName(String indusName) {
		this.indusName = indusName;
	}

	@Column(name = "INDUS_CODE", length = 50)
	public String getIndusCode() {
		return this.indusCode;
	}

	public void setIndusCode(String indusCode) {
		this.indusCode = indusCode;
	}

	@Column(name = "INDUS_LEVEL", precision = 1, scale = 0)
	public Boolean getIndusLevel() {
		return this.indusLevel;
	}

	public void setIndusLevel(Boolean indusLevel) {
		this.indusLevel = indusLevel;
	}

	@Column(name = "INDUS_DETAIL", length = 1000)
	public String getIndusDetail() {
		return this.indusDetail;
	}

	public void setIndusDetail(String indusDetail) {
		this.indusDetail = indusDetail;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "indusClassGbcode")
	public Set<IndusClassGbcode> getIndusClassGbcodes() {
		return this.indusClassGbcodes;
	}

	public void setIndusClassGbcodes(Set<IndusClassGbcode> indusClassGbcodes) {
		this.indusClassGbcodes = indusClassGbcodes;
	}

}