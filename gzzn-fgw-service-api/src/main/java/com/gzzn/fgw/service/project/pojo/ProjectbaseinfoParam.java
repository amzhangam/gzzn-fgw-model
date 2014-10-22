package com.gzzn.fgw.service.project.pojo;

import java.io.Serializable;
import java.util.Date;

import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.XmsbHylb;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.XmsbZjxz;

public class ProjectbaseinfoParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5426634536851652657L;
	
    private String projectcode;
    
    private String projectname;
    
    private XmsbHylb xmsbHylb;
    
    private XmsbXmlx xmsbXmlx;
    
    private XmsbZjxz xmsbZjxz;
    
    private String projectTypeName;
    
    private String manageunitsname;
    
    private Integer pjstatus;
    
    private String pjstatusName;
    
    private Integer auditdept;
    
    private String auditdeptname;
    
    private Integer nextauditdept;
    
    private String nextauditdeptname;
    
    private SysOrganization sysOrganizationByDeclareunitsid;
    
    private String beginTime;
    
    private String endTime;
    
    private Integer sfzdxm;
    
    private String sfzdxmName; 
    
    private String zgdw;//主管单位
    
    private Integer xmfl;//项目分类
    
    private String xmcblb;//项目储备类别
    
    private String projectcontent;
    
    private String declaregist;
    
    private Integer deleted;
    
    private String beginModifiedTime;
    
    private String endModifiedTime;
    
    private Integer expectfinishinvestType;//项目总投资类型 1-1000万以下 2-1000万（含）以上
    
    private String sfctzchz;//是否呈投资处汇总
    
    private Double xmztzBegin;//项目总投资(万元)起点
    private Double xmztzEnd;//项目总投资(万元)终点
    
    
	public Double getXmztzBegin() {
		return xmztzBegin;
	}
	public void setXmztzBegin(Double xmztzBegin) {
		this.xmztzBegin = xmztzBegin;
	}
	public Double getXmztzEnd() {
		return xmztzEnd;
	}
	public void setXmztzEnd(Double xmztzEnd) {
		this.xmztzEnd = xmztzEnd;
	}
	public Integer getExpectfinishinvestType() {
		return expectfinishinvestType;
	}
	public void setExpectfinishinvestType(Integer expectfinishinvestType) {
		this.expectfinishinvestType = expectfinishinvestType;
	}
	public String getXmcblb() {
		return xmcblb;
	}
	public void setXmcblb(String xmcblb) {
		this.xmcblb = xmcblb;
	}
	public String getProjectcode() {
		return projectcode;
	}
	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	public XmsbHylb getXmsbHylb() {
		return xmsbHylb;
	}
	public void setXmsbHylb(XmsbHylb xmsbHylb) {
		this.xmsbHylb = xmsbHylb;
	}
	public XmsbXmlx getXmsbXmlx() {
		return xmsbXmlx;
	}
	public void setXmsbXmlx(XmsbXmlx xmsbXmlx) {
		this.xmsbXmlx = xmsbXmlx;
	}
	public XmsbZjxz getXmsbZjxz() {
		return xmsbZjxz;
	}
	public void setXmsbZjxz(XmsbZjxz xmsbZjxz) {
		this.xmsbZjxz = xmsbZjxz;
	}
	public String getManageunitsname() {
		return manageunitsname;
	}
	public void setManageunitsname(String manageunitsname) {
		this.manageunitsname = manageunitsname;
	}
	public Integer getPjstatus() {
		return pjstatus;
	}
	public void setPjstatus(Integer pjstatus) {
		this.pjstatus = pjstatus;
	}
	public String getAuditdeptname() {
		return auditdeptname;
	}
	public void setAuditdeptName(String auditdeptname) {
		this.auditdeptname = auditdeptname;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public String getPjStatusName() {
		return pjstatusName;
	}
	public void setPjStatusName(String pjstatusName) {
		this.pjstatusName = pjstatusName;
	}
	public Integer getAuditdept() {
		return auditdept;
	}
	public void setAuditdept(Integer auditdept) {
		this.auditdept = auditdept;
	}
	public SysOrganization getSysOrganizationByDeclareunitsid() {
		return sysOrganizationByDeclareunitsid;
	}
	public void setSysOrganizationByDeclareunitsid(
			SysOrganization sysOrganizationByDeclareunitsid) {
		this.sysOrganizationByDeclareunitsid = sysOrganizationByDeclareunitsid;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getSfzdxm() {
		return sfzdxm;
	}
	public void setSfzdxm(Integer sfzdxm) {
		this.sfzdxm = sfzdxm;
	}
	public Integer getNextauditdept() {
		return nextauditdept;
	}
	public void setNextauditdept(Integer nextauditdept) {
		this.nextauditdept = nextauditdept;
	}
	public String getNextauditdeptname() {
		return nextauditdeptname;
	}
	public void setNextauditdeptname(String nextauditdeptname) {
		this.nextauditdeptname = nextauditdeptname;
	}
	public String getSfzdxmName() {
		return sfzdxmName;
	}
	public void setSfzdxmName(String sfzdxmName) {
		this.sfzdxmName = sfzdxmName;
	}
	public String getPjstatusName() {
		return pjstatusName;
	}
	public void setPjstatusName(String pjstatusName) {
		this.pjstatusName = pjstatusName;
	}
	public void setAuditdeptname(String auditdeptname) {
		this.auditdeptname = auditdeptname;
	}
	public String getZgdw() {
		return zgdw;
	}
	public void setZgdw(String zgdw) {
		this.zgdw = zgdw;
	}
	public Integer getXmfl() {
		return xmfl;
	}
	public void setXmfl(Integer xmfl) {
		this.xmfl = xmfl;
	}
	public String getProjectcontent() {
		return projectcontent;
	}
	public void setProjectcontent(String projectcontent) {
		this.projectcontent = projectcontent;
	}
	public String getDeclaregist() {
		return declaregist;
	}
	public void setDeclaregist(String declaregist) {
		this.declaregist = declaregist;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public String getBeginModifiedTime() {
		return beginModifiedTime;
	}
	public void setBeginModifiedTime(String beginModifiedTime) {
		this.beginModifiedTime = beginModifiedTime;
	}
	public String getEndModifiedTime() {
		return endModifiedTime;
	}
	public void setEndModifiedTime(String endModifiedTime) {
		this.endModifiedTime = endModifiedTime;
	}
	public String getSfctzchz() {
		return sfctzchz;
	}
	public void setSfctzchz(String sfctzchz) {
		this.sfctzchz = sfctzchz;
	}
	
	
    
    
}
