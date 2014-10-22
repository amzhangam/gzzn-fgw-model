package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * SysDxmb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_DXMB"
    
)

public class SysDxmb  implements java.io.Serializable {


    // Fields    

     private Integer dxmbId;
     private String mbmc;
     private String mb;


    // Constructors

    /** default constructor */
    public SysDxmb() {
    }

    
    /** full constructor */
    public SysDxmb(String mbmc, String mb) {
        this.mbmc = mbmc;
        this.mb = mb;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="DXMB_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getDxmbId() {
        return this.dxmbId;
    }
    
    public void setDxmbId(Integer dxmbId) {
        this.dxmbId = dxmbId;
    }
    
    @Column(name="MBMC", length=100)

    public String getMbmc() {
        return this.mbmc;
    }
    
    public void setMbmc(String mbmc) {
        this.mbmc = mbmc;
    }
    
    @Column(name="MB", length=100)

    public String getMb() {
        return this.mb;
    }
    
    public void setMb(String mb) {
        this.mb = mb;
    }
   








}