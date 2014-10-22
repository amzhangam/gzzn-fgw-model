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
 * SysOperationlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_OPERATIONLOG")
public class SysOperationlog implements java.io.Serializable {

	// Fields

	private Long operationlogRecId;
	private SysDept sysDept;
	private SysUser sysUser;
	private SysOrganization sysOrganization;
	private String operationContent;
	private Date operationDatetime;

	// Constructors

	/** default constructor */
	public SysOperationlog() {
	}

	/** full constructor */
	public SysOperationlog(SysDept sysDept, SysUser sysUser,
			SysOrganization sysOrganization, String operationContent,
			Date operationDatetime) {
		this.sysDept = sysDept;
		this.sysUser = sysUser;
		this.sysOrganization = sysOrganization;
		this.operationContent = operationContent;
		this.operationDatetime = operationDatetime;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="operationlog_rec_id", unique=true, nullable=false)
	public Long getOperationlogRecId() {
		return this.operationlogRecId;
	}

	public void setOperationlogRecId(Long operationlogRecId) {
		this.operationlogRecId = operationlogRecId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT_ID")
	public SysDept getSysDept() {
		return this.sysDept;
	}

	public void setSysDept(SysDept sysDept) {
		this.sysDept = sysDept;
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

	@Column(name = "OPERATION_CONTENT", length = 1000)
	public String getOperationContent() {
		return this.operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	@Column(name = "OPERATION_DATETIME", length = 19)
	public Date getOperationDatetime() {
		return this.operationDatetime;
	}

	public void setOperationDatetime(Date operationDatetime) {
		this.operationDatetime = operationDatetime;
	}

}