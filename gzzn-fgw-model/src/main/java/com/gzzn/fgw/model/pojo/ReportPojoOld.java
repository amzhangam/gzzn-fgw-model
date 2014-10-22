package com.gzzn.fgw.model.pojo;



/**
 * 封装报表视图的信息，综合的实体类有 {@link com.gzzn.fgw.model.Pjbaseinfo}、
 * {@link com.gzzn.fgw.model.Projectinvest}、
 * {@link com.gzzn.fgw.model.SysDictionaryitems}
 * <p/>
 * <i>Copyright (c) 2014 ITDCL All right reserved.</i>
 * 
 * @author lzq
 * @version 1.0 <br/>
 *          修改记录（日期+内容）:<br/>
 *          ——2014-02-28 10:40:48 lzq new
 */
public class ReportPojoOld {
	private String projectName;// 项目名称
	private int projectType;
	private String projectContent;// 主要内容

	private int startyear;// 开始年份
	private int endyear;// 终止年份

	private Integer pjinvestSum;// 总投资
	private Integer pjinvestCity;// 市财政资金
	private Integer pjinvestCompany;// 自有资金
	private Integer pjinvestBank;// 银行贷款
	private Integer pjinvestOther;// 其他

	private int expectFinishInvestYear;// 累计完成
	private Integer expectFinishInvest;// 累计完成投资
	private Integer expectFinishOtherInvest;// 累计完成其他投资

	private int planInvestYear;
	private Integer planInvestSum;
	private Integer planInvestCity;
	private Integer planInvestCompany;
	private Integer planInvestBank;
	private Integer planInvestOther;

	private String pjinvestAdvice;
	private String declareGist;
	private String organizationName;
	private String manageUnitsName;
	private int pjstatus;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public void setProjectType(String projectType) {
		// System.out.println("projectType:"+projectType);
		try {
			if (!projectType.equals("")) {
				this.projectType = Integer.parseInt(projectType);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public String getProjectContent() {
		return projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}

	public int getStartYear() {
		return startyear;
	}

	public void setStartYear(int startyear) {
		this.startyear = startyear;
	}

	// 页面参数字符串转化而来
	public void setStartYear(String startyear) {
		// System.out.println("startyear"+startyear);
		try {
			if (!startyear.equals("")) {
				this.startyear = Integer.parseInt(startyear);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void setStartYear(String[] startyear) {
		try {
			if (!startyear[0].equals("")) {
				this.startyear = Integer.parseInt(startyear[0]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public int getEndYear() {
		return endyear;
	}

	public void setEndYear(int endyear) {
		this.endyear = endyear;
	}

	public void setEndYear(String endyear) {
		// System.out.println("endyear"+endyear);
		try {
			this.endyear = Integer.parseInt(endyear);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void setEndYear(String[] endyear) {
		try {
			if (!endyear[0].equals("")) {
				this.endyear = Integer.parseInt(endyear[0]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public Integer getPjinvestSum() {
		return pjinvestSum;
	}

	public void setPjinvestSum(Integer pjinvestSum) {
		this.pjinvestSum = pjinvestSum;
	}

	public Integer getPjinvestCity() {
		return pjinvestCity;
	}

	public void setPjinvestCity(Integer pjinvestCity) {
		this.pjinvestCity = pjinvestCity;
	}

	public Integer getPjinvestCompany() {
		return pjinvestCompany;
	}

	public void setPjinvestCompany(Integer pjinvestCompany) {
		this.pjinvestCompany = pjinvestCompany;
	}

	public Integer getPjinvestBank() {
		return pjinvestBank;
	}

	public void setPjinvestBank(Integer pjinvestBank) {
		this.pjinvestBank = pjinvestBank;
	}

	public Integer getPjinvestOther() {
		return pjinvestOther;
	}

	public void setPjinvestOther(Integer pjinvestOther) {
		this.pjinvestOther = pjinvestOther;
	}

	public int getExpectFinishInvestYear() {
		return expectFinishInvestYear;
	}

	public void setExpectFinishInvestYear(int expectFinishInvestYear) {
		this.expectFinishInvestYear = expectFinishInvestYear;
	}

	public Integer getExpectFinishInvest() {
		return expectFinishInvest;
	}

	public void setExpectFinishInvest(Integer expectFinishInvest) {
		this.expectFinishInvest = expectFinishInvest;
	}

	public Integer getExpectFinishOtherInvest() {
		return expectFinishOtherInvest;
	}

	public void setExpectFinishOtherInvest(Integer expectFinishOtherInvest) {
		this.expectFinishOtherInvest = expectFinishOtherInvest;
	}

	public int getPlanInvestYear() {
		return planInvestYear;
	}

	public void setPlanInvestYear(int planInvestYear) {
		this.planInvestYear = planInvestYear;
	}

	public Integer getPlanInvestSum() {
		return planInvestSum;
	}

	public void setPlanInvestSum(Integer planInvestSum) {
		this.planInvestSum = planInvestSum;
	}

	public Integer getPlanInvestCity() {
		return planInvestCity;
	}

	public void setPlanInvestCity(Integer planInvestCity) {
		this.planInvestCity = planInvestCity;
	}

	public Integer getPlanInvestCompany() {
		return planInvestCompany;
	}

	public void setPlanInvestCompany(Integer planInvestCompany) {
		this.planInvestCompany = planInvestCompany;
	}

	public Integer getPlanInvestBank() {
		return planInvestBank;
	}

	public void setPlanInvestBank(Integer planInvestBank) {
		this.planInvestBank = planInvestBank;
	}

	public Integer getPlanInvestOther() {
		return planInvestOther;
	}

	public void setPlanInvestOther(Integer planInvestOther) {
		this.planInvestOther = planInvestOther;
	}

	public String getPjinvestAdvice() {
		return pjinvestAdvice;
	}

	public void setPjinvestAdvice(String pjinvestAdvice) {
		this.pjinvestAdvice = pjinvestAdvice;
	}

	public String getDeclareGist() {
		return declareGist;
	}

	public void setDeclareGist(String declareGist) {
		this.declareGist = declareGist;
	}

	public String getManageUnitsName() {
		return manageUnitsName;
	}

	public void setManageUnitsName(String manageUnitsName) {
		this.manageUnitsName = manageUnitsName;
	}

	public int getPjStatus() {
		return pjstatus;
	}

	public void setPjStatus(int pjstatus) {
		this.pjstatus = pjstatus;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

}
