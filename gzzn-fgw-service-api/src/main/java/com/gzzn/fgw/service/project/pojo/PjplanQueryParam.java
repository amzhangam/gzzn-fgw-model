package com.gzzn.fgw.service.project.pojo;


/** 
 * <p>Title: PjplanQueryParam</p>
 * <p>Description: 年度计划终稿管理、项目年度管理、项目建设情况页面查询条件  </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-5-12 下午5:05:01 lhq  new
 */
public class PjplanQueryParam {
	
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String investplanname;//投资计划终稿名称
	private String pfsj;//批复时间
	private String userId;//填报人，多个间使用,隔开
	
	private String projectname;//项目名称
	private String xqId;//辖区id，多个间使用,隔开
	private String xqmc;//辖区名称，多个间使用,隔开
	private String cylb;//产业类别，多个间使用,隔开
	private String zdxmbz;//重点项目标注（0-非重点项目；1为省重点项目；2为市重点项目），多个间使用,隔开
	private String remark;//备注
	private Double pjinvestSum;// 总投资
	private Double pjinvestSum2;// 总投资
	private String pjinvestSumType;// 总投资类型：空、全部；1、千万以上；2、千万以下
	
	private String  organizationId;//单位ID
	private String organizationName;//单位名称
	
	private Integer deleted;
	
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * 作用一 状态类型：null-导入审核数据，impl-导入数据，audit-审核数据，saveRepeart-保存重复数据；
	 * 作用二 删除类型：1-上报项目查询页面的删除，2-填报项目管理页面的删除；
	 * 作用三 统计类型：1-按区县，2-按产业及投资额
	 */
	private String stuType;
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getInvestplanname() {
		return investplanname;
	}
	public void setInvestplanname(String investplanname) {
		this.investplanname = investplanname;
	}
	public String getXqId() {
		return xqId;
	}
	public void setXqId(String xqId) {
		this.xqId = xqId;
	}
	public String getCylb() {
		return cylb;
	}
	public void setCylb(String cylb) {
		this.cylb = cylb;
	}
	public String getZdxmbz() {
		return zdxmbz;
	}
	public void setZdxmbz(String zdxmbz) {
		this.zdxmbz = zdxmbz;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStuType() {
		return stuType;
	}
	public void setStuType(String stuType) {
		this.stuType = stuType;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getXqmc() {
		return xqmc;
	}
	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getPfsj() {
		return pfsj;
	}
	public void setPfsj(String pfsj) {
		this.pfsj = pfsj;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getPjinvestSum() {
		return pjinvestSum;
	}
	public void setPjinvestSum(Double pjinvestSum) {
		this.pjinvestSum = pjinvestSum;
	}
	public Double getPjinvestSum2() {
		return pjinvestSum2;
	}
	public void setPjinvestSum2(Double pjinvestSum2) {
		this.pjinvestSum2 = pjinvestSum2;
	}
	public String getPjinvestSumType() {
		return pjinvestSumType;
	}
	public void setPjinvestSumType(String pjinvestSumType) {
		this.pjinvestSumType = pjinvestSumType;
	}
	
	
	
	
	
	
	

}
