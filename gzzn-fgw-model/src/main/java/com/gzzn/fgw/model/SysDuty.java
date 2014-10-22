package com.gzzn.fgw.model;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * SysDuty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_DUTY"
    
)

public class SysDuty  implements java.io.Serializable {


    // Fields    

     private Integer dutyId;
     private String dutyName;
     private String dutyDesc;
     private Integer deleted;
     private Set<SysUser> sysUsers = new HashSet<SysUser>(0);


    // Constructors

    /** default constructor */
    public SysDuty() {
    }

    
    /** full constructor */
    public SysDuty(String dutyName, String dutyDesc, Integer deleted, Set<SysUser> sysUsers) {
        this.dutyName = dutyName;
        this.dutyDesc = dutyDesc;
        this.deleted = deleted;
        this.sysUsers = sysUsers;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="DUTY_ID", unique=true, nullable=false, scale=0)

    public Integer getDutyId() {
        return this.dutyId;
    }
    
    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }
    
    @Column(name="DUTY_NAME")

    public String getDutyName() {
        return this.dutyName;
    }
    
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }
    
    @Column(name="DUTY_DESC")

    public String getDutyDesc() {
        return this.dutyDesc;
    }
    
    public void setDutyDesc(String dutyDesc) {
        this.dutyDesc = dutyDesc;
    }
    
    @Column(name="DELETED", precision=1, scale=0)

    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDuty")

    public Set<SysUser> getSysUsers() {
        return this.sysUsers;
    }
    
    public void setSysUsers(Set<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }
   








}