package com.gzzn.fgw.model;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * XmsbHylb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XMSB_HYLB"
    
)

public class XmsbHylb  implements java.io.Serializable {


    // Fields    

     private Integer hylbId;
     private XmsbHylb xmsbHylb;
     private String hylbmc;
     private String hylbdm;
     private Integer deleted;
     private Set<XmsbHylb> xmsbHylbs = new HashSet<XmsbHylb>(0);


    // Constructors

    /** default constructor */
    public XmsbHylb() {
    }

    
    /** full constructor */
    public XmsbHylb(XmsbHylb xmsbHylb, String hylbmc, String hylbdm, Integer deleted, Set<XmsbHylb> xmsbHylbs) {
        this.xmsbHylb = xmsbHylb;
        this.hylbmc = hylbmc;
        this.hylbdm = hylbdm;
        this.deleted = deleted;
        this.xmsbHylbs = xmsbHylbs;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="HYLB_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getHylbId() {
        return this.hylbId;
    }
    
    public void setHylbId(Integer hylbId) {
        this.hylbId = hylbId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="SJHYLB_ID")

    public XmsbHylb getXmsbHylb() {
        return this.xmsbHylb;
    }
    
    public void setXmsbHylb(XmsbHylb xmsbHylb) {
        this.xmsbHylb = xmsbHylb;
    }
    
    @Column(name="HYLBMC", length=50)

    public String getHylbmc() {
        return this.hylbmc;
    }
    
    public void setHylbmc(String hylbmc) {
        this.hylbmc = hylbmc;
    }
    
    @Column(name="HYLBDM", length=50)

    public String getHylbdm() {
        return this.hylbdm;
    }
    
    public void setHylbdm(String hylbdm) {
        this.hylbdm = hylbdm;
    }
    
    @Column(name="DELETED", precision=22, scale=0)

    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="xmsbHylb")

    public Set<XmsbHylb> getXmsbHylbs() {
        return this.xmsbHylbs;
    }
    
    public void setXmsbHylbs(Set<XmsbHylb> xmsbHylbs) {
        this.xmsbHylbs = xmsbHylbs;
    }
   








}