package com.gzzn.fgw.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SysProjectlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_PROJECTLOG"

)
public class SysProjectlog implements java.io.Serializable {

	// Fields

	private Integer operationlogRecId;
	private SysDept sysDept;
	private SysUser sysUser;
	private SysOrganization sysOrganization;
	private Pjbaseinfo pjbaseinfo;
	//private String operationContent;
	private byte[] operationContent;
	private Date operationDatetime;
	private Integer read;
	private Integer readerId;
	private Date readtime;
	private Integer readerOrgId;

	// Constructors

	/** default constructor */
	public SysProjectlog() {
	}

	/** full constructor */
	public SysProjectlog(SysDept sysDept, SysUser sysUser,
			SysOrganization sysOrganization, Pjbaseinfo pjbaseinfo,
			byte[] operationContent, Date operationDatetime, Integer read,
			Integer readerId, Date readtime, Integer readerOrgId) {
		this.sysDept = sysDept;
		this.sysUser = sysUser;
		this.sysOrganization = sysOrganization;
		this.pjbaseinfo = pjbaseinfo;
		this.operationContent = operationContent;
		this.operationDatetime = operationDatetime;
		this.read = read;
		this.readerId = readerId;
		this.readtime = readtime;
		this.readerOrgId = readerOrgId;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "xmsb",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "OPERATIONLOG_REC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getOperationlogRecId() {
		return this.operationlogRecId;
	}

	public void setOperationlogRecId(Integer operationlogRecId) {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECTID")
	public Pjbaseinfo getPjbaseinfo() {
		return this.pjbaseinfo;
	}

	public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
		this.pjbaseinfo = pjbaseinfo;
	}

	/**@Column(name = "OPERATION_CONTENT")
	public String getOperationContent() {
		return this.operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}*/
	@Column(name = "OPERATION_CONTENT")
	public  byte[] getOperationContent() {
		return this.operationContent;
	}

	public void setOperationContent( byte[] operationContent) {
		this.operationContent = operationContent;
	}

	@Column(name = "OPERATION_DATETIME", length = 19)
	public Date getOperationDatetime() {
		return this.operationDatetime;
	}

	public void setOperationDatetime(Date operationDatetime) {
		this.operationDatetime = operationDatetime;
	}

	@Column(name = "READ", precision = 22, scale = 0)
	public Integer getRead() {
		return this.read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	@Column(name = "READER_ID", scale = 0)
	public Integer getReaderId() {
		return this.readerId;
	}

	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}

	@Column(name = "READTIME", length = 19)
	public Date getReadtime() {
		return this.readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	@Column(name = "READER_ORG_ID", precision = 22, scale = 0)
	public Integer getReaderOrgId() {
		return this.readerOrgId;
	}

	public void setReaderOrgId(Integer readerOrgId) {
		this.readerOrgId = readerOrgId;
	}

}