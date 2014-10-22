package com.gzzn.fgw.service.sys.pojo;

import com.gzzn.fgw.model.SysXq;

/** 
 * <p>Title: SysQueryParam</p>
 * <p>Description: 系统管理查询参数类  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author amzhang
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-6 下午5:05:01 amzhang  new
 */
public class SysQueryParam {
	
	
	private String likeData;//模糊查询数据条件
	private String projectName;//项目名称
	private String userName;//用户名称
	private String dutyName;//职务名称
	private String organName;//机构名称
	private String deptname;//部门名称
	private String parentdeptid;//上级部门
	private String parentDeptName;//上级部门名称
	private Integer deptType;//部门类型
	private Integer read;//项目日志表：事件读取状态
	private Integer num;//页面需要展示的数据条数
	private String readStartTime;//开始时间
	private String readEndTime;//结束时间
	
	
	private String roleName;//角色名称
	
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String xqId;//辖区ID，多个间使用,隔开
	
	private String registeNo;
    private String linkMan;
    private String mobile;
    private String corporation;
    private String adress;
    private String workunitstype;//工作单位类型，多个间使用,隔开
    private String workunitstypeName;
    private Integer organStatus;
    private SysXq sysXq;
    private String workunitsquality;//工作单位性质，多个间使用,隔开
    private String workunitsqualityName;
    private String workunitsstatus;//工作单位状态，多个间使用,隔开
    private String workunitsstatusName;
	
    private String mbmc;
    
    /**短信管理中的相关查询参数*/
    private String sendDeptname;//发送部门名称
    private String sendUsername;//发送用户名称
    private String receiveOrganizationname;//接收单位
    private String receiveDeptname;//接收部门名称
    private String sendDeptid;//发送部门ID，多个间使用,隔开
    private String sendUserid;//发送用户ID，多个间使用,隔开
    private String receiveOrganizationid;//接收单位ID，多个间使用,隔开
    private String receiveDeptid;//接收部门ID，多个间使用,隔开
    private String lxrmc;//联系人
    private String sjhm;//联系人手机号
    private String sfsjBegin;//发送时间：开始时间
    private String sfsjEnd;//发送时间：结束时间
    private String sfnr;//发送内容
    
    /***短信管理中【发送项目短信】的相关参数*/
    private String projectcode;//项目编码
    private String xmyz;//项目业主
    private String zjxz;//资金性质，多个间使用,隔开
    private String zgdw;//主管单位，多个间使用,隔开
    private String deptId;//部门ID，多个间使用,隔开
    private String xmlx;//项目类型，多个间使用,隔开
    private String hylb;//行业类别，多个间使用,隔开
    private String xmzt;//项目状态，多个间使用,隔开
    
    /***2014-8-11加入：填报日志查询的相关参数*/
    private String tbdw;//填报单位（盖章）
    private Integer operationType;//操作类型：1上传导入数据；2 自检上报数据；3 覆盖重复数据
    
	
	public String getMbmc() {
		return mbmc;
	}
	public void setMbmc(String mbmc) {
		this.mbmc = mbmc;
	}
	public String getWorkunitsstatusName() {
		return workunitsstatusName;
	}
	public void setWorkunitsstatusName(String workunitsstatusName) {
		this.workunitsstatusName = workunitsstatusName;
	}
	public String getWorkunitsqualityName() {
		return workunitsqualityName;
	}
	public void setWorkunitsqualityName(String workunitsqualityName) {
		this.workunitsqualityName = workunitsqualityName;
	}
	public String getWorkunitstypeName() {
		return workunitstypeName;
	}
	public void setWorkunitstypeName(String workunitstypeName) {
		this.workunitstypeName = workunitstypeName;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getParentDeptId() {
		return parentdeptid;
	}
	public void setParentDeptId(String parentdeptid) {
		this.parentdeptid = parentdeptid;
	}
	public Integer getDeptType() {
		return deptType;
	}
	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getParentDeptName() {
		return parentDeptName;
	}
	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	public String getRegisteNo() {
		return registeNo;
	}
	public void setRegisteNo(String registeNo) {
		this.registeNo = registeNo;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCorporation() {
		return corporation;
	}
	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public String getWorkunitstype() {
		return workunitstype;
	}
	public void setWorkunitstype(String workunitstype) {
		this.workunitstype = workunitstype;
	}
	public Integer getOrganStatus() {
		return organStatus;
	}
	public void setOrganStatus(Integer organStatus) {
		this.organStatus = organStatus;
	}
	public String getLikeData() {
		return likeData;
	}
	public void setLikeData(String likeData) {
		this.likeData = likeData;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
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
	public SysXq getSysXq() {
		return sysXq;
	}
	public void setSysXq(SysXq sysXq) {
		this.sysXq = sysXq;
	}
	public String getWorkunitsquality() {
		return workunitsquality;
	}
	public void setWorkunitsquality(String workunitsquality) {
		this.workunitsquality = workunitsquality;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Integer getNum() {
		return num;
	}
	public String getParentdeptid() {
		return parentdeptid;
	}
	public void setParentdeptid(String parentdeptid) {
		this.parentdeptid = parentdeptid;
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getReadStartTime() {
		return readStartTime;
	}
	public void setReadStartTime(String readStartTime) {
		this.readStartTime = readStartTime;
	}
	public String getReadEndTime() {
		return readEndTime;
	}
	public void setReadEndTime(String readEndTime) {
		this.readEndTime = readEndTime;
	}
	
	public String getSendDeptname() {
		return sendDeptname;
	}
	public void setSendDeptname(String sendDeptname) {
		this.sendDeptname = sendDeptname;
	}
	public String getSendUsername() {
		return sendUsername;
	}
	public void setSendUsername(String sendUsername) {
		this.sendUsername = sendUsername;
	}
	public String getReceiveOrganizationname() {
		return receiveOrganizationname;
	}
	public void setReceiveOrganizationname(String receiveOrganizationname) {
		this.receiveOrganizationname = receiveOrganizationname;
	}
	public String getReceiveDeptname() {
		return receiveDeptname;
	}
	public void setReceiveDeptname(String receiveDeptname) {
		this.receiveDeptname = receiveDeptname;
	}
	public String getLxrmc() {
		return lxrmc;
	}
	public void setLxrmc(String lxrmc) {
		this.lxrmc = lxrmc;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	
	public String getSfsjBegin() {
		return sfsjBegin;
	}
	public void setSfsjBegin(String sfsjBegin) {
		this.sfsjBegin = sfsjBegin;
	}
	public String getSfsjEnd() {
		return sfsjEnd;
	}
	public void setSfsjEnd(String sfsjEnd) {
		this.sfsjEnd = sfsjEnd;
	}
	public String getSfnr() {
		return sfnr;
	}
	public void setSfnr(String sfnr) {
		this.sfnr = sfnr;
	}
	public String getSendDeptid() {
		return sendDeptid;
	}
	public void setSendDeptid(String sendDeptid) {
		this.sendDeptid = sendDeptid;
	}
	public String getSendUserid() {
		return sendUserid;
	}
	public void setSendUserid(String sendUserid) {
		this.sendUserid = sendUserid;
	}
	public String getReceiveOrganizationid() {
		return receiveOrganizationid;
	}
	public void setReceiveOrganizationid(String receiveOrganizationid) {
		this.receiveOrganizationid = receiveOrganizationid;
	}
	public String getReceiveDeptid() {
		return receiveDeptid;
	}
	public void setReceiveDeptid(String receiveDeptid) {
		this.receiveDeptid = receiveDeptid;
	}
	public String getXqId() {
		return xqId;
	}
	public void setXqId(String xqId) {
		this.xqId = xqId;
	}
	public String getWorkunitsstatus() {
		return workunitsstatus;
	}
	public void setWorkunitsstatus(String workunitsstatus) {
		this.workunitsstatus = workunitsstatus;
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
	public String getZjxz() {
		return zjxz;
	}
	public void setZjxz(String zjxz) {
		this.zjxz = zjxz;
	}
	public String getZgdw() {
		return zgdw;
	}
	public void setZgdw(String zgdw) {
		this.zgdw = zgdw;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getXmzt() {
		return xmzt;
	}
	public void setXmzt(String xmzt) {
		this.xmzt = xmzt;
	}
	public String getXmlx() {
		return xmlx;
	}
	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}
	public String getHylb() {
		return hylb;
	}
	public void setHylb(String hylb) {
		this.hylb = hylb;
	}
	public String getTbdw() {
		return tbdw;
	}
	public void setTbdw(String tbdw) {
		this.tbdw = tbdw;
	}
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	
	
	
	
}
