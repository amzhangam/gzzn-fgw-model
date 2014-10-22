package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SysDx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_DX"
    
)

public class SysDx  implements java.io.Serializable {


    // Fields    

     private Integer dxId;
     private SysDept sysDeptByDeptId;
     private SysUser sysUser;
     private SysOrganization sysOrganization;
     private SysDept sysDeptByReceiveDeptId;
     private String lxrmc;
     private String sjhm;
     private Date sfsj;
     private String sfnr;
     private Integer sfbj;


    // Constructors

    /** default constructor */
    public SysDx() {
    }

    
    /** full constructor */
    public SysDx(SysDept sysDeptByDeptId, SysUser sysUser, SysOrganization sysOrganization, SysDept sysDeptByReceiveDeptId, String lxrmc, String sjhm, Date sfsj, String sfnr, Integer sfbj) {
        this.sysDeptByDeptId = sysDeptByDeptId;
        this.sysUser = sysUser;
        this.sysOrganization = sysOrganization;
        this.sysDeptByReceiveDeptId = sysDeptByReceiveDeptId;
        this.lxrmc = lxrmc;
        this.sjhm = sjhm;
        this.sfsj = sfsj;
        this.sfnr = sfnr;
        this.sfbj = sfbj;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="DX_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getDxId() {
        return this.dxId;
    }
    
    public void setDxId(Integer dxId) {
        this.dxId = dxId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DEPT_ID")

    public SysDept getSysDeptByDeptId() {
        return this.sysDeptByDeptId;
    }
    
    public void setSysDeptByDeptId(SysDept sysDeptByDeptId) {
        this.sysDeptByDeptId = sysDeptByDeptId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="USER_ID")

    public SysUser getSysUser() {
        return this.sysUser;
    }
    
    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ORGANIZATION_ID")

    public SysOrganization getSysOrganization() {
        return this.sysOrganization;
    }
    
    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="RECEIVE_DEPT_ID")

    public SysDept getSysDeptByReceiveDeptId() {
        return this.sysDeptByReceiveDeptId;
    }
    
    public void setSysDeptByReceiveDeptId(SysDept sysDeptByReceiveDeptId) {
        this.sysDeptByReceiveDeptId = sysDeptByReceiveDeptId;
    }
    
    @Column(name="LXRMC", length=50)

    public String getLxrmc() {
        return this.lxrmc;
    }
    
    public void setLxrmc(String lxrmc) {
        this.lxrmc = lxrmc;
    }
    
    @Column(name="SJHM", length=20)

    public String getSjhm() {
        return this.sjhm;
    }
    
    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="SFSJ", length=7)

    public Date getSfsj() {
        return this.sfsj;
    }
    
    public void setSfsj(Date sfsj) {
        this.sfsj = sfsj;
    }
    
    @Column(name="SFNR", length=100)

    public String getSfnr() {
        return this.sfnr;
    }
    
    public void setSfnr(String sfnr) {
        this.sfnr = sfnr;
    }
    
    @Column(name="SFBJ", precision=1, scale=0)

    public Integer getSfbj() {
        return this.sfbj;
    }
    
    public void setSfbj(Integer sfbj) {
        this.sfbj = sfbj;
    }
   








}