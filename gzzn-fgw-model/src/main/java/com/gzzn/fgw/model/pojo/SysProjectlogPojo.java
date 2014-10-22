package com.gzzn.fgw.model.pojo;

import java.io.Serializable;
import java.util.Date;

import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;

/***
 * 项目日志POJO
 * @author lhq
 * @date 2014-5-22
 * @version v1.0
 */
public class SysProjectlogPojo implements Serializable {
	
	private static final long serialVersionUID = 7623030663779716513L;
	private Integer operationlogRecId;
	private SysDept sysDept;
	private SysUser sysUser;
	private SysOrganization sysOrganization;
	private Pjbaseinfo pjbaseinfo;
	private String operationContent;
	private Date operationDatetime;
	private Integer read;
	private Integer readerId;//读取用户ID
	private SysUser readUserObj;//读取用户名称
	private Date readtime;
	private Integer readerOrgId;//读取用户单位ID号
	private SysOrganization readOrgObj;//读取用户单位名称
	
	
	  
	public Integer getOperationlogRecId() {
		return operationlogRecId;
	}
	public void setOperationlogRecId(Integer operationlogRecId) {
		this.operationlogRecId = operationlogRecId;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public SysDept getSysDept() {
		return sysDept;
	}
	public void setSysDept(SysDept sysDept) {
		this.sysDept = sysDept;
	}
	public SysOrganization getSysOrganization() {
		return sysOrganization;
	}
	public void setSysOrganization(SysOrganization sysOrganization) {
		this.sysOrganization = sysOrganization;
	}
	public Pjbaseinfo getPjbaseinfo() {
		return pjbaseinfo;
	}
	public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
		this.pjbaseinfo = pjbaseinfo;
	}
	public String getOperationContent() {
		return operationContent;
	}
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	public Date getOperationDatetime() {
		return operationDatetime;
	}
	public void setOperationDatetime(Date operationDatetime) {
		this.operationDatetime = operationDatetime;
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	public Date getReadtime() {
		return readtime;
	}
	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}
	public Integer getReaderOrgId() {
		return readerOrgId;
	}
	public void setReaderOrgId(Integer readerOrgId) {
		this.readerOrgId = readerOrgId;
	}
	public SysUser getReadUserObj() {
		return readUserObj;
	}
	public void setReadUserObj(SysUser readUserObj) {
		this.readUserObj = readUserObj;
	}
	public SysOrganization getReadOrgObj() {
		return readOrgObj;
	}
	public void setReadOrgObj(SysOrganization readOrgObj) {
		this.readOrgObj = readOrgObj;
	}
	
	

}
