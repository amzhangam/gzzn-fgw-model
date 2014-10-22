
package com.gzzn.fgw.model.pojo;





/**
 * 报表统计页面查询条件
 * @author lhq
 * @date 2014-05-06
 */
public class ReportPojo {
	
	private String reportType;//当前报表类型：1 申报项目计划报表  ；2 业主/主管单位提交项目计划报表；3 审核通过报表；4、审核不通过报表
	
	private String projectName;// 项目名称
	private String xmlx;//项目类型，多个间使用,隔开
	private Integer startyear;// 建设开始年份
	private Integer endyear;// 建设终止年份
	
	private String organizationName;//项目主管单位
	private String manageUnitsName;//建设管理单位
	private String projectContent;// 主要内容
	
	private String declaregist;//申报依据
	private String pjinvestAdvice;//市财政资金安排渠道建议
	private Integer pjstatus;//项目状态

	private Double pjinvestSum;// 总投资
	private Double pjinvestCity;// 市财政资金
	private Double pjinvestCompany;// 自有资金
	private Double pjinvestBank;// 银行融资
	private Double pjinvestOther;// 其他资金
	
	private Double pjinvestSum2;// 总投资
	private Double pjinvestCity2;// 市财政资金
	private Double pjinvestCompany2;// 自有资金
	private Double pjinvestBank2;// 银行融资
	private Double pjinvestOther2;// 其他资金
	
	
	private Integer expectFinishInvestYear;//【预计完工投资年份】 累计完成
	private Double expectFinishInvest;//【预计完工总投资】 累计完成投资
	private Double expectFinishOtherInvest;//【本级财政资金】 累计完成其他投资
	private Double expectFinishInvest2;//【预计完工总投资】 累计完成投资2
	private Double expectFinishOtherInvest2;//【本级财政资金】 累计完成其他投资2
	
	private Integer planInvestYear;//计划投资年份
	private Double planInvestSum;//计划投资合计
	private Double planInvestCity;//计划市财政资金
	private Double planInvestCompany;//计划自有资金
	private Double planInvestBank;//计划银行融资
	private Double planInvestOther;//计划其他资金
	
	private Double planInvestSum2;//计划投资合计
	private Double planInvestCity2;//计划市财政资金
	private Double planInvestCompany2;//计划自有资金
	private Double planInvestBank2;//计划银行融资
	private Double planInvestOther2;//计划其他资金
	
	/***2014-8-7 加入的相关参数*/
	private String xqId;//辖区ID，多个间使用,隔开
    private String projectcode;//项目编码
    private String xmyz;//项目业主
    private String hylb;//行业类别，多个间使用,隔开
    private String zjxz;//资金性质，多个间使用,隔开
    private String deptname;//当前处理部门
    private String xmfl;//项目分类，多个间使用,隔开
    private String beginModifiedTime;//开始修改时间
    private String endModifiedTime;//开始修改时间
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getStartyear() {
		return startyear;
	}
	public void setStartyear(Integer startyear) {
		this.startyear = startyear;
	}
	public Integer getEndyear() {
		return endyear;
	}
	public void setEndyear(Integer endyear) {
		this.endyear = endyear;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getManageUnitsName() {
		return manageUnitsName;
	}
	public void setManageUnitsName(String manageUnitsName) {
		this.manageUnitsName = manageUnitsName;
	}
	public String getProjectContent() {
		return projectContent;
	}
	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}
	
	public String getDeclaregist() {
		return declaregist;
	}
	public void setDeclaregist(String declaregist) {
		this.declaregist = declaregist;
	}
	public String getPjinvestAdvice() {
		return pjinvestAdvice;
	}
	public void setPjinvestAdvice(String pjinvestAdvice) {
		this.pjinvestAdvice = pjinvestAdvice;
	}
	public Integer getPjStatus() {
		return pjstatus;
	}
	public void setPjStatus(Integer pjstatus) {
		this.pjstatus = pjstatus;
	}
	public Double getPjinvestSum() {
		return pjinvestSum;
	}
	public void setPjinvestSum(Double pjinvestSum) {
		this.pjinvestSum = pjinvestSum;
	}
	public Double getPjinvestCity() {
		return pjinvestCity;
	}
	public void setPjinvestCity(Double pjinvestCity) {
		this.pjinvestCity = pjinvestCity;
	}
	public Double getPjinvestCompany() {
		return pjinvestCompany;
	}
	public void setPjinvestCompany(Double pjinvestCompany) {
		this.pjinvestCompany = pjinvestCompany;
	}
	public Double getPjinvestBank() {
		return pjinvestBank;
	}
	public void setPjinvestBank(Double pjinvestBank) {
		this.pjinvestBank = pjinvestBank;
	}
	public Double getPjinvestOther() {
		return pjinvestOther;
	}
	public void setPjinvestOther(Double pjinvestOther) {
		this.pjinvestOther = pjinvestOther;
	}
	public Double getPjinvestSum2() {
		return pjinvestSum2;
	}
	public void setPjinvestSum2(Double pjinvestSum2) {
		this.pjinvestSum2 = pjinvestSum2;
	}
	public Double getPjinvestCity2() {
		return pjinvestCity2;
	}
	public void setPjinvestCity2(Double pjinvestCity2) {
		this.pjinvestCity2 = pjinvestCity2;
	}
	public Double getPjinvestCompany2() {
		return pjinvestCompany2;
	}
	public void setPjinvestCompany2(Double pjinvestCompany2) {
		this.pjinvestCompany2 = pjinvestCompany2;
	}
	public Double getPjinvestBank2() {
		return pjinvestBank2;
	}
	public void setPjinvestBank2(Double pjinvestBank2) {
		this.pjinvestBank2 = pjinvestBank2;
	}
	public Double getPjinvestOther2() {
		return pjinvestOther2;
	}
	public void setPjinvestOther2(Double pjinvestOther2) {
		this.pjinvestOther2 = pjinvestOther2;
	}
	public Integer getExpectFinishInvestYear() {
		return expectFinishInvestYear;
	}
	public void setExpectFinishInvestYear(Integer expectFinishInvestYear) {
		this.expectFinishInvestYear = expectFinishInvestYear;
	}
	
	public Double getExpectFinishInvest() {
		return expectFinishInvest;
	}
	public void setExpectFinishInvest(Double expectFinishInvest) {
		this.expectFinishInvest = expectFinishInvest;
	}
	public Double getExpectFinishOtherInvest() {
		return expectFinishOtherInvest;
	}
	public void setExpectFinishOtherInvest(Double expectFinishOtherInvest) {
		this.expectFinishOtherInvest = expectFinishOtherInvest;
	}
	public Double getExpectFinishInvest2() {
		return expectFinishInvest2;
	}
	public void setExpectFinishInvest2(Double expectFinishInvest2) {
		this.expectFinishInvest2 = expectFinishInvest2;
	}
	public Double getExpectFinishOtherInvest2() {
		return expectFinishOtherInvest2;
	}
	public void setExpectFinishOtherInvest2(Double expectFinishOtherInvest2) {
		this.expectFinishOtherInvest2 = expectFinishOtherInvest2;
	}
	public Integer getPlanInvestYear() {
		return planInvestYear;
	}
	public void setPlanInvestYear(Integer planInvestYear) {
		this.planInvestYear = planInvestYear;
	}
	public Double getPlanInvestSum() {
		return planInvestSum;
	}
	public void setPlanInvestSum(Double planInvestSum) {
		this.planInvestSum = planInvestSum;
	}
	public Double getPlanInvestCity() {
		return planInvestCity;
	}
	public void setPlanInvestCity(Double planInvestCity) {
		this.planInvestCity = planInvestCity;
	}
	public Double getPlanInvestCompany() {
		return planInvestCompany;
	}
	public void setPlanInvestCompany(Double planInvestCompany) {
		this.planInvestCompany = planInvestCompany;
	}
	public Double getPlanInvestBank() {
		return planInvestBank;
	}
	public void setPlanInvestBank(Double planInvestBank) {
		this.planInvestBank = planInvestBank;
	}
	public Double getPlanInvestOther() {
		return planInvestOther;
	}
	public void setPlanInvestOther(Double planInvestOther) {
		this.planInvestOther = planInvestOther;
	}
	public Double getPlanInvestSum2() {
		return planInvestSum2;
	}
	public void setPlanInvestSum2(Double planInvestSum2) {
		this.planInvestSum2 = planInvestSum2;
	}
	public Double getPlanInvestCity2() {
		return planInvestCity2;
	}
	public void setPlanInvestCity2(Double planInvestCity2) {
		this.planInvestCity2 = planInvestCity2;
	}
	public Double getPlanInvestCompany2() {
		return planInvestCompany2;
	}
	public void setPlanInvestCompany2(Double planInvestCompany2) {
		this.planInvestCompany2 = planInvestCompany2;
	}
	public Double getPlanInvestBank2() {
		return planInvestBank2;
	}
	public void setPlanInvestBank2(Double planInvestBank2) {
		this.planInvestBank2 = planInvestBank2;
	}
	public Double getPlanInvestOther2() {
		return planInvestOther2;
	}
	public void setPlanInvestOther2(Double planInvestOther2) {
		this.planInvestOther2 = planInvestOther2;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getProjectcode() {
		return projectcode;
	}
	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	public String getXmyz() {
		return xmyz;
	}
	public void setXmyz(String xmyz) {
		this.xmyz = xmyz;
	}
	public String getHylb() {
		return hylb;
	}
	public void setHylb(String hylb) {
		this.hylb = hylb;
	}
	public String getZjxz() {
		return zjxz;
	}
	public void setZjxz(String zjxz) {
		this.zjxz = zjxz;
	}
	public String getXmfl() {
		return xmfl;
	}
	public void setXmfl(String xmfl) {
		this.xmfl = xmfl;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getXmlx() {
		return xmlx;
	}
	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}
	public String getXqId() {
		return xqId;
	}
	public void setXqId(String xqId) {
		this.xqId = xqId;
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

	
}
