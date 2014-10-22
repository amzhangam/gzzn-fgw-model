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
 * XmsbZjxz entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XMSB_ZJXZ"
    
)

public class XmsbZjxz  implements java.io.Serializable {


    // Fields    

     private Integer zjxzId;
     private XmsbZjxz xmsbZjxz;
     private String zjxzmc;
     private String zjxzdm;
     private Integer deleted;
     private Set<Pjbaseinfo> pjbaseinfos = new HashSet<Pjbaseinfo>(0);
     private Set<XmsbZjxz> xmsbZjxzs = new HashSet<XmsbZjxz>(0);


    // Constructors

    /** default constructor */
    public XmsbZjxz() {
    }

    
    /** full constructor */
    public XmsbZjxz(XmsbZjxz xmsbZjxz, String zjxzmc, String zjxzdm, Integer deleted, Set<Pjbaseinfo> pjbaseinfos, Set<XmsbZjxz> xmsbZjxzs) {
        this.xmsbZjxz = xmsbZjxz;
        this.zjxzmc = zjxzmc;
        this.zjxzdm = zjxzdm;
        this.deleted = deleted;
        this.pjbaseinfos = pjbaseinfos;
        this.xmsbZjxzs = xmsbZjxzs;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="ZJXZ_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getZjxzId() {
        return this.zjxzId;
    }
    
    public void setZjxzId(Integer zjxzId) {
        this.zjxzId = zjxzId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="SJZJXZ_ID")

    public XmsbZjxz getXmsbZjxz() {
        return this.xmsbZjxz;
    }
    
    public void setXmsbZjxz(XmsbZjxz xmsbZjxz) {
        this.xmsbZjxz = xmsbZjxz;
    }
    
    @Column(name="ZJXZMC", length=50)

    public String getZjxzmc() {
        return this.zjxzmc;
    }
    
    public void setZjxzmc(String zjxzmc) {
        this.zjxzmc = zjxzmc;
    }
    
    @Column(name="ZJXZDM", length=50)

    public String getZjxzdm() {
        return this.zjxzdm;
    }
    
    public void setZjxzdm(String zjxzdm) {
        this.zjxzdm = zjxzdm;
    }
    
    @Column(name="DELETED", precision=22, scale=0)

    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="xmsbZjxz")

    public Set<Pjbaseinfo> getPjbaseinfos() {
        return this.pjbaseinfos;
    }
    
    public void setPjbaseinfos(Set<Pjbaseinfo> pjbaseinfos) {
        this.pjbaseinfos = pjbaseinfos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="xmsbZjxz")

    public Set<XmsbZjxz> getXmsbZjxzs() {
        return this.xmsbZjxzs;
    }
    
    public void setXmsbZjxzs(Set<XmsbZjxz> xmsbZjxzs) {
        this.xmsbZjxzs = xmsbZjxzs;
    }
   








}