package com.gzzn.fgw.service.project.pojo;

import com.gzzn.fgw.model.SysXq;

/**
 * <p>Title: PjBulidInfoStatPojo</p>
 * <p>Description: 区县项目汇总统计POJO  </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-6-10 下午6:20:36 lhq  new
 */
public class PjBulidInfoStatPojo {
	
	
	 private SysXq sysXq;//区县信息
	 
	 private String sType1;//类别1
	 private String sType2;//类别2
	 
	 
	 
	 //2013年以来立项（审批、核准、备案）项目相关统计
	 private Integer lXNum;//立项——数量
	 private Double lXPjinvestsum;//立项——计划总投资(万元)
	 private Double lXExpectfinishinvest;//立项——到2013年底累计完成投资
	 
	 //完工项目:【实际完成总投资=到2013年底累计完成投资+2014年以来完成投资】
	 private Integer wGNum;//完工——数量
	 private Double wGExpectfinishinvest;//完工——到2013年底累计完成投资(万元)
	 private Double wGCurrentfinishinvest;//完工——2014年以来完成投资（万元）
	 
	 //在建项目
	 private Integer zJNum;//在建——数量
	 private Double zJPjinvestsum;//在建——计划总投资(万元)
	 private Double zJPlaninvestsum;//在建——2014年计划投资(万元)
	 private Double zJCurrentfinishinvest;//在建——2014年以来完成投资（万元）
	 
	 
	 
	 /**提供一个构造方法：指定地区；其它的值全为0*/ 
	public PjBulidInfoStatPojo(SysXq sysXq) {
		this.sysXq = sysXq;
		this.lXNum = 0;
		this.lXPjinvestsum = 0D;
		this.lXExpectfinishinvest = 0D;
		
		this.wGNum = 0;
		this.wGExpectfinishinvest = 0D;
		this.wGCurrentfinishinvest = 0D;
		
		this.zJNum = 0;
		this.zJPjinvestsum = 0D;
		this.zJPlaninvestsum = 0D;
		this.zJCurrentfinishinvest = 0D;
	}
	
	 /**提供一个构造方法：指定类别1、类别2；其它的值全为0*/ 
		public PjBulidInfoStatPojo(String sType1,String sType2) {
			this.sType1 = sType1;
			this.sType2 = sType2;
			this.lXNum = 0;
			this.lXPjinvestsum = 0D;
			this.lXExpectfinishinvest = 0D;
			
			this.wGNum = 0;
			this.wGExpectfinishinvest = 0D;
			this.wGCurrentfinishinvest = 0D;
			
			this.zJNum = 0;
			this.zJPjinvestsum = 0D;
			this.zJPlaninvestsum = 0D;
			this.zJCurrentfinishinvest = 0D;
		}
		
	

	public SysXq getSysXq() {
		return sysXq;
	}
	public void setSysXq(SysXq sysXq) {
		this.sysXq = sysXq;
	}
	public Integer getlXNum() {
		return lXNum;
	}
	public void setlXNum(Integer lXNum) {
		this.lXNum = lXNum;
	}
	public Double getlXPjinvestsum() {
		return lXPjinvestsum;
	}
	public void setlXPjinvestsum(Double lXPjinvestsum) {
		this.lXPjinvestsum = lXPjinvestsum;
	}
	public Double getlXExpectfinishinvest() {
		return lXExpectfinishinvest;
	}
	public void setlXExpectfinishinvest(Double lXExpectfinishinvest) {
		this.lXExpectfinishinvest = lXExpectfinishinvest;
	}
	public Integer getwGNum() {
		return wGNum;
	}
	public void setwGNum(Integer wGNum) {
		this.wGNum = wGNum;
	}
	public Double getwGExpectfinishinvest() {
		return wGExpectfinishinvest;
	}
	public void setwGExpectfinishinvest(Double wGExpectfinishinvest) {
		this.wGExpectfinishinvest = wGExpectfinishinvest;
	}
	public Double getwGCurrentfinishinvest() {
		return wGCurrentfinishinvest;
	}
	public void setwGCurrentfinishinvest(Double wGCurrentfinishinvest) {
		this.wGCurrentfinishinvest = wGCurrentfinishinvest;
	}
	public Integer getzJNum() {
		return zJNum;
	}
	public void setzJNum(Integer zJNum) {
		this.zJNum = zJNum;
	}
	public Double getzJPjinvestsum() {
		return zJPjinvestsum;
	}
	public void setzJPjinvestsum(Double zJPjinvestsum) {
		this.zJPjinvestsum = zJPjinvestsum;
	}
	public Double getzJPlaninvestsum() {
		return zJPlaninvestsum;
	}
	public void setzJPlaninvestsum(Double zJPlaninvestsum) {
		this.zJPlaninvestsum = zJPlaninvestsum;
	}
	public Double getzJCurrentfinishinvest() {
		return zJCurrentfinishinvest;
	}
	public void setzJCurrentfinishinvest(Double zJCurrentfinishinvest) {
		this.zJCurrentfinishinvest = zJCurrentfinishinvest;
	}

	public String getsType1() {
		return sType1;
	}

	public void setsType1(String sType1) {
		this.sType1 = sType1;
	}

	public String getsType2() {
		return sType2;
	}

	public void setsType2(String sType2) {
		this.sType2 = sType2;
	}
	 

}
