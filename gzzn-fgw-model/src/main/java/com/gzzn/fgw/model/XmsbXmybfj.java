package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * XmsbXmybfj entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XMSB_XMYBFJ"
    
)

public class XmsbXmybfj  implements java.io.Serializable {


    // Fields    

     private Integer xmybfjId;
     private XmsbXmyb xmsbXmyb;
     private String fjmc;
     private String fjlj;


    // Constructors

    /** default constructor */
    public XmsbXmybfj() {
    }

    
    /** full constructor */
    public XmsbXmybfj(XmsbXmyb xmsbXmyb, String fjmc, String fjlj) {
        this.xmsbXmyb = xmsbXmyb;
        this.fjmc = fjmc;
        this.fjlj = fjlj;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="XMYBFJ_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getXmybfjId() {
        return this.xmybfjId;
    }
    
    public void setXmybfjId(Integer xmybfjId) {
        this.xmybfjId = xmybfjId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="XMYB_ID")

    public XmsbXmyb getXmsbXmyb() {
        return this.xmsbXmyb;
    }
    
    public void setXmsbXmyb(XmsbXmyb xmsbXmyb) {
        this.xmsbXmyb = xmsbXmyb;
    }
    
    @Column(name="FJMC", length=50)

    public String getFjmc() {
        return this.fjmc;
    }
    
    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }
    
    @Column(name="FJLJ", length=100)

    public String getFjlj() {
        return this.fjlj;
    }
    
    public void setFjlj(String fjlj) {
        this.fjlj = fjlj;
    }
   








}