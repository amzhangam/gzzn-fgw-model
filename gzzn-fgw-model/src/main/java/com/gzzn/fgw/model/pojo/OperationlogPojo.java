package com.gzzn.fgw.model.pojo;

import java.io.Serializable;

public class OperationlogPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7623030663779716513L;
	private Integer operationlogRecId;
	private String userName;
	private String deptname;
    private String organizationName;
    private String operationContent;
    private String operationDatetime;
    private String projectName;
    
    
    
	public Integer getOperationlogRecId() {
		return operationlogRecId;
	}
	public void setOperationlogRecId(Integer operationlogRecId) {
		this.operationlogRecId = operationlogRecId;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	public String getOperationContent() {
		return operationContent;
	}
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	public String getOperationDatetime() {
		return operationDatetime;
	}
	public void setOperationDatetime(String operationDatetime) {
		this.operationDatetime = operationDatetime;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    
    
}
