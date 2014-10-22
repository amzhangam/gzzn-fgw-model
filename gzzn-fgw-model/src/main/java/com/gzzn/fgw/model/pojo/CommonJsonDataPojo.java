package com.gzzn.fgw.model.pojo;


/**
 * 
 * <p>Title: CommonJsonDataPojo</p>
 * <p>Description:  查询通用JSON数据参数 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-05-29 lhq  new
 */
public class CommonJsonDataPojo {
	
	private String keyWord;//关键字：用于模糊查询
	private Boolean nocheck; //上级相关信息的单选或复选按钮是否显示: true不显示 false显示
	
	/**单位相关查询信息*/
	private String workunitstype;//工作单位类型:1-业主；2-主管单位；3-发改委（多个值间使用逗号分隔如：1,2）
	private String workunitsstatus;//工作单位状态:1-待审批；2-审批通过；3-审批不通过；4-销户（多个值间使用逗号分隔如：1,2）
	private String xqId;//辖区ID号（多个值间使用逗号分隔如：1,2）
	private Boolean nocheckOrg; //上级机构信息的单选或复选按钮是否显示: true不显示 false显示
	
	/**填报人相关查询信息*/
	private String userType;//用户类型：1-业主用户；2-主管单位用户；3-市发改委用户；4-区县发展和改革局用户（多个值间使用逗号分隔如：1,2）
	private String useStatus;//用户状态：1-	待审批；2-审批通过；3-审批不通过；4-销户；（多个值间使用逗号分隔如：1,2）
	private String userId;//用户ID

	/**部门相关查询信息*/
	private Boolean nocheckDept;//上级部门信息的单选或复选按钮是否显示: true不显示 false显示
	private String  organizationId;//单位id
	
	/**辖区相关查询信息*/
	private String xqlx;//辖区类型：0-国家；1-省；2-市；3-区；4-街；5-社区（多个值间使用逗号分隔如：1,2）

	/**查询数据字典相关信息*/
	private String dictName;//数据字典名称
	
	
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Boolean getNocheckOrg() {
		return nocheckOrg;
	}
	public void setNocheckOrg(Boolean nocheckOrg) {
		this.nocheckOrg = nocheckOrg;
	}
	public Boolean getNocheckDept() {
		return nocheckDept;
	}
	public void setNocheckDept(Boolean nocheckDept) {
		this.nocheckDept = nocheckDept;
	}
	public String getWorkunitstype() {
		return workunitstype;
	}
	public void setWorkunitstype(String workunitstype) {
		this.workunitstype = workunitstype;
	}
	public String getWorkunitsstatus() {
		return workunitsstatus;
	}
	public void setWorkunitsstatus(String workunitsstatus) {
		this.workunitsstatus = workunitsstatus;
	}
	public String getXqId() {
		return xqId;
	}
	public void setXqId(String xqId) {
		this.xqId = xqId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public Boolean getNocheck() {
		return nocheck;
	}
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}
	public String getXqlx() {
		return xqlx;
	}
	public void setXqlx(String xqlx) {
		this.xqlx = xqlx;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	

}
