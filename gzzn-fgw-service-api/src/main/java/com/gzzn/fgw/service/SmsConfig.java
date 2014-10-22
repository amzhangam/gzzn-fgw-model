package com.gzzn.fgw.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
/**
	* <p>Title: SmsConfig</p>
 	* <p>Description: 短信设置</p>
 	* <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 	* <p>Company: ITDCL</p>
 	* <p>@author ChengZhi</p>
 	* <p>@version 1.0</p>
 	* <p>修改记录:下面填写修改的内容以及修改的日期</p>
 	* <p>1.2014-2-24下午4:12:31  ChengZhi    new</p>
 	* <p>2.2014-2-24下午4:12:31  userName    update</p>
 */
public class SmsConfig implements Serializable{
	//短信接口服务地址
	@Value("${sms.url}")
	private String smsUrl;
	//
	@Value("${sms.namespace}")
	private String smsNamespace;
	//发送人号码
	@Value("${sms.sendNo}")
	private String smsSendNo;
	//登录用户
	@Value("${sms.userName}")
	private String smsUserName;
	//登录用户密码
	@Value("${sms.password}")
	private String smsPassword;
	
	//
	@Value("${sms.mesgtype}")
	private String smsMesgtype;
	//
	@Value("${sms.srr}")
	private String smsSrr;
	
	
	public String getSmsserialno(String serialno){
		return smsUserName + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+serialno;
	}
	
	public SmsConfig() {
		// TODO Auto-generated constructor stub
	}

	public SmsConfig(String smsUrl, String smsNamespace, String smsSendNo,
			String smsUserName, String smsPassword, String smsMesgtype,
			String smsSrr) {
		super();
		this.smsUrl = smsUrl;
		this.smsNamespace = smsNamespace;
		this.smsSendNo = smsSendNo;
		this.smsUserName = smsUserName;
		this.smsPassword = smsPassword;
		this.smsMesgtype = smsMesgtype;
		this.smsSrr = smsSrr;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getSmsNamespace() {
		return smsNamespace;
	}

	public void setSmsNamespace(String smsNamespace) {
		this.smsNamespace = smsNamespace;
	}

	public String getSmsSendNo() {
		return smsSendNo;
	}

	public void setSmsSendNo(String smsSendNo) {
		this.smsSendNo = smsSendNo;
	}

	public String getSmsUserName() {
		return smsUserName;
	}

	public void setSmsUserName(String smsUserName) {
		this.smsUserName = smsUserName;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsMesgtype() {
		return smsMesgtype;
	}

	public void setSmsMesgtype(String smsMesgtype) {
		this.smsMesgtype = smsMesgtype;
	}

	public String getSmsSrr() {
		return smsSrr;
	}

	public void setSmsSrr(String smsSrr) {
		this.smsSrr = smsSrr;
	}

	@Override
	public String toString() {
		return "SmsConfig [smsUrl=" + smsUrl + ", smsNamespace=" + smsNamespace
				+ ", smsSendNo=" + smsSendNo + ", smsUserName=" + smsUserName
				+ ", smsPassword=" + smsPassword + ", smsMesgtype="
				+ smsMesgtype + ", smsSrr=" + smsSrr + "]";
	}

	

}
