package com.gzzn.fgw.service.sys.pojo;

import java.io.Serializable;

public class CsxmPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7907108532881386579L;
	private String deptName;
	private int unPassCount=0;
	private int unPassUnderJbjsCount=0;
	private int unPassUnderGxgzCount=0;
	private int unPassUpJbjsCount=0;
	private int unPassUpGxgzCount=0;
	
	private int passCount=0;
	private int passUnderJbjsCount=0;
	private int passUnderGxgzCount=0;
	private int passUpJbjsCount=0;
	private int passUpGxgzCount=0;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getUnPassCount() {
		return unPassCount;
	}
	public void setUnPassCount(int unPassCount) {
		this.unPassCount = unPassCount;
	}
	public int getUnPassUnderJbjsCount() {
		return unPassUnderJbjsCount;
	}
	public void setUnPassUnderJbjsCount(int unPassUnderJbjsCount) {
		this.unPassUnderJbjsCount = unPassUnderJbjsCount;
	}
	public int getUnPassUnderGxgzCount() {
		return unPassUnderGxgzCount;
	}
	public void setUnPassUnderGxgzCount(int unPassUnderGxgzCount) {
		this.unPassUnderGxgzCount = unPassUnderGxgzCount;
	}
	public int getUnPassUpJbjsCount() {
		return unPassUpJbjsCount;
	}
	public void setUnPassUpJbjsCount(int unPassUpJbjsCount) {
		this.unPassUpJbjsCount = unPassUpJbjsCount;
	}
	public int getUnPassUpGxgzCount() {
		return unPassUpGxgzCount;
	}
	public void setUnPassUpGxgzCount(int unPassUpGxgzCount) {
		this.unPassUpGxgzCount = unPassUpGxgzCount;
	}
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public int getPassUnderJbjsCount() {
		return passUnderJbjsCount;
	}
	public void setPassUnderJbjsCount(int passUnderJbjsCount) {
		this.passUnderJbjsCount = passUnderJbjsCount;
	}
	public int getPassUnderGxgzCount() {
		return passUnderGxgzCount;
	}
	public void setPassUnderGxgzCount(int passUnderGxgzCount) {
		this.passUnderGxgzCount = passUnderGxgzCount;
	}
	public int getPassUpJbjsCount() {
		return passUpJbjsCount;
	}
	public void setPassUpJbjsCount(int passUpJbjsCount) {
		this.passUpJbjsCount = passUpJbjsCount;
	}
	public int getPassUpGxgzCount() {
		return passUpGxgzCount;
	}
	public void setPassUpGxgzCount(int passUpGxgzCount) {
		this.passUpGxgzCount = passUpGxgzCount;
	}
	
	
	
}
