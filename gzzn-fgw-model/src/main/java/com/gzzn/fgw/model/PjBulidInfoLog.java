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
 * PjBulidInfoLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PJ_BULID_INFO_LOG")
public class PjBulidInfoLog implements java.io.Serializable {

	// Fields

	private Long logId;
	private SysDept sysDept;
	private SysUser sysUser;
	private SysOrganization sysOrganization;
	private Integer operationType;
	private String operationContent;
	private Date operationDatetime;
	private String tbdw;

	// Constructors

	/** default constructor */
	public PjBulidInfoLog() {
	}

	/** full constructor */
	public PjBulidInfoLog(SysDept sysDept, SysUser sysUser,
			SysOrganization sysOrganization, Integer operationType,
			String operationContent, Date operationDatetime, String tbdw) {
		this.sysDept = sysDept;
		this.sysUser = sysUser;
		this.sysOrganization = sysOrganization;
		this.operationType = operationType;
		this.operationContent = operationContent;
		this.operationDatetime = operationDatetime;
		this.tbdw = tbdw;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "xmsb")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "LOG_ID", unique = true, nullable = false, scale = 0)
	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
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

	@Column(name = "OPERATION_TYPE", precision = 22, scale = 0)
	public Integer getOperationType() {
		return this.operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
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
	
	@Column(name = "TBDW", length = 150)
	public String getTbdw() {
		return this.tbdw;
	}

	public void setTbdw(String tbdw) {
		this.tbdw = tbdw;
	}

}