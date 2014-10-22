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
 * XmsbXmyb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XMSB_XMYB"
    
)

public class XmsbXmyb  implements java.io.Serializable {


    // Fields    

     private Integer xmybId;
     private Pjbaseinfo pjbaseinfo;
     private String nf;
     private String yf;
     private String nr;
     private Integer xmybzt;
     private Set<XmsbXmybfj> xmsbXmybfjs = new HashSet<XmsbXmybfj>(0);


    // Constructors

    /** default constructor */
    public XmsbXmyb() {
    }

    
    /** full constructor */
    public XmsbXmyb(Pjbaseinfo pjbaseinfo, String nf, String yf, String nr, Integer xmybzt, Set<XmsbXmybfj> xmsbXmybfjs) {
        this.pjbaseinfo = pjbaseinfo;
        this.nf = nf;
        this.yf = yf;
        this.nr = nr;
        this.xmybzt = xmybzt;
        this.xmsbXmybfjs = xmsbXmybfjs;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="XMYB_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getXmybId() {
        return this.xmybId;
    }
    
    public void setXmybId(Integer xmybId) {
        this.xmybId = xmybId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="PROJECTID")

    public Pjbaseinfo getPjbaseinfo() {
        return this.pjbaseinfo;
    }
    
    public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
        this.pjbaseinfo = pjbaseinfo;
    }
    
    @Column(name="NF", length=4)

    public String getNf() {
        return this.nf;
    }
    
    public void setNf(String nf) {
        this.nf = nf;
    }
    
    @Column(name="YF", length=2)

    public String getYf() {
        return this.yf;
    }
    
    public void setYf(String yf) {
        this.yf = yf;
    }
    
    @Column(name="NR", length=4000)

    public String getNr() {
        return this.nr;
    }
    
    public void setNr(String nr) {
        this.nr = nr;
    }
    
    @Column(name="XMYBZT", precision=1, scale=0)

    public Integer getXmybzt() {
        return this.xmybzt;
    }
    
    public void setXmybzt(Integer xmybzt) {
        this.xmybzt = xmybzt;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="xmsbXmyb")

    public Set<XmsbXmybfj> getXmsbXmybfjs() {
        return this.xmsbXmybfjs;
    }
    
    public void setXmsbXmybfjs(Set<XmsbXmybfj> xmsbXmybfjs) {
        this.xmsbXmybfjs = xmsbXmybfjs;
    }
   








}