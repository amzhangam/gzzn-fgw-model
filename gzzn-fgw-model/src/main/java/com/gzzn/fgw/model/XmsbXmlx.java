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
 * XmsbXmlx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XMSB_XMLX"
    
)

public class XmsbXmlx  implements java.io.Serializable {


    // Fields    

     private Integer xmlxId;
     private XmsbXmlx xmsbXmlx;
     private String xmlxmc;
     private String xmlxdm;
     private Integer deleted;
     private Set<Pjbaseinfo> pjbaseinfos = new HashSet<Pjbaseinfo>(0);
     private Set<XmsbXmlx> xmsbXmlxes = new HashSet<XmsbXmlx>(0);


    // Constructors

    /** default constructor */
    public XmsbXmlx() {
    }

    
    /** full constructor */
    public XmsbXmlx(XmsbXmlx xmsbXmlx, String xmlxmc, String xmlxdm, Integer deleted, Set<Pjbaseinfo> pjbaseinfos, Set<XmsbXmlx> xmsbXmlxes) {
        this.xmsbXmlx = xmsbXmlx;
        this.xmlxmc = xmlxmc;
        this.xmlxdm = xmlxdm;
        this.deleted = deleted;
        this.pjbaseinfos = pjbaseinfos;
        this.xmsbXmlxes = xmsbXmlxes;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="XMLX_ID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getXmlxId() {
        return this.xmlxId;
    }
    
    public void setXmlxId(Integer xmlxId) {
        this.xmlxId = xmlxId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="SJXMLX_ID")

    public XmsbXmlx getXmsbXmlx() {
        return this.xmsbXmlx;
    }
    
    public void setXmsbXmlx(XmsbXmlx xmsbXmlx) {
        this.xmsbXmlx = xmsbXmlx;
    }
    
    @Column(name="XMLXMC", length=50)

    public String getXmlxmc() {
        return this.xmlxmc;
    }
    
    public void setXmlxmc(String xmlxmc) {
        this.xmlxmc = xmlxmc;
    }
    
    @Column(name="XMLXDM", length=50)

    public String getXmlxdm() {
        return this.xmlxdm;
    }
    
    public void setXmlxdm(String xmlxdm) {
        this.xmlxdm = xmlxdm;
    }
    
    @Column(name="DELETED", precision=22, scale=0)

    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="xmsbXmlx")

    public Set<Pjbaseinfo> getPjbaseinfos() {
        return this.pjbaseinfos;
    }
    
    public void setPjbaseinfos(Set<Pjbaseinfo> pjbaseinfos) {
        this.pjbaseinfos = pjbaseinfos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="xmsbXmlx")

    public Set<XmsbXmlx> getXmsbXmlxes() {
        return this.xmsbXmlxes;
    }
    
    public void setXmsbXmlxes(Set<XmsbXmlx> xmsbXmlxes) {
        this.xmsbXmlxes = xmsbXmlxes;
    }
   








}