package com.gzzn.fgw.aop;

import java.io.Serializable;

import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysUser;

public class LogObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5209233793798293930L;
	private String entityName;//增删改的实体名称，如公文归档，排班人员，值班类别等
	private Integer objectId;//操作对象的Id
	private String objectName;//操作对象的名称或标题，如人员名称，公文标题
	private Pjbaseinfo pjbaseinfo;//项目
	private SysUser sysUser;//用户信息，将用户注册信息保存至用户操作日志中
	private String operationContent;//操作内容(增删改以外的操作，这种情况不要在注解上添加任何值，但是要填写operationConent,比如operationConent=”生成了排班计划，从2014-02-01到2014-02-28")
	private String logType;//用户操作日志-opLog【为用户操作日志时该字段可以为空】；项目日志-pjLog【为项目日志时该字段不能为空】；
	private Integer read;//读取标记【只用于项目日志】；为空——普通的项目日志信息；非空——待办或已办的项目日志信息：0-未读；1-已读
	private Pjbaseinfo beforePjbaseinfo;//修改之前的项目
	
	
	/**
	 * 增删改日志用
	 * @param entityName
	 * @param objectId
	 * @param objectName
	 */
	public LogObject(String entityName,Integer objectId,String objectName){
		this.entityName = entityName;
		this.objectId = objectId;
		this.objectName = objectName;
	}
	
	/**
	 * 增删改日志用
	 * @param entityName
	 * @param objectId
	 * @param objectName
	 * @param pjbaseinfo
	 */
	public LogObject(String entityName,Integer objectId,String objectName,Pjbaseinfo pjbaseinfo){
		this.entityName = entityName;
		this.objectId = objectId;
		this.objectName = objectName;
		this.pjbaseinfo = pjbaseinfo;
	}
	
	/**
	 * 增删改日志用
	 * @param entityName
	 * @param objectId
	 * @param objectName
	 */
	public LogObject(String entityName,Integer objectId,String objectName,Pjbaseinfo beforePjbaseinfo,Pjbaseinfo pjbaseinfo,String logType){
		this.entityName = entityName;
		this.objectId = objectId;
		this.objectName = objectName;
		this.pjbaseinfo = pjbaseinfo;
		this.logType = logType;
		this.beforePjbaseinfo = beforePjbaseinfo;
	}
	
	/**
	 * 增删改日志用
	 * @param entityName
	 * @param objectId
	 * @param objectName
	 * @param pjbaseinfo
	 * @param logType
	 * @param read
	 */
	public LogObject(String entityName,Integer objectId,String objectName,Pjbaseinfo pjbaseinfo,String logType,Integer read){
		this.entityName = entityName;
		this.objectId = objectId;
		this.objectName = objectName;
		this.pjbaseinfo = pjbaseinfo;
		this.logType = logType;
		this.read = read;
	}
	
	/**
	 * 其他需要记录日志的方法用
	 * @param operationContent
	 */
	public LogObject(String operationContent){
		this.operationContent = operationContent;
	}
	
	/**
	 * 其他需要记录日志的方法用
	 * @param operationContent
	 * @param logType
	 */
	public LogObject(String operationContent,String logType){
		this.operationContent = operationContent;
		this.logType = logType;
	}
	
	/**
	 * 其他需要记录日志的方法用
	 * @param operationContent
	 * @param logType
	 * @param read
	 */
	public LogObject(String operationContent,String logType,Integer read){
		this.operationContent = operationContent;
		this.logType = logType;
		this.read = read;
	}
	
	/**
	 * 其他需要记录日志的方法用
	 * @param operationContent
	 * @param logType
	 */
	public LogObject(String operationContent,SysUser sysUser){
		this.operationContent = operationContent;
		this.sysUser = sysUser;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public Pjbaseinfo getPjbaseinfo() {
		return pjbaseinfo;
	}

	public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
		this.pjbaseinfo = pjbaseinfo;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Pjbaseinfo getBeforePjbaseinfo() {
		return beforePjbaseinfo;
	}

	public void setBeforePjbaseinfo(Pjbaseinfo beforePjbaseinfo) {
		this.beforePjbaseinfo = beforePjbaseinfo;
	}

	

	
}
