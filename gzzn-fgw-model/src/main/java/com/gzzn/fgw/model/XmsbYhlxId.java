package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * XmsbYhlxId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class XmsbYhlxId  implements java.io.Serializable {


    // Fields    

     private Integer xmlxId;
     private String xmlxmc;
     private String xmlxdm;
     private Integer deleted;
     private Integer sjxmlxId;


    // Constructors

    /** default constructor */
    public XmsbYhlxId() {
    }

	/** minimal constructor */
    public XmsbYhlxId(Integer xmlxId) {
        this.xmlxId = xmlxId;
    }
    
    /** full constructor */
    public XmsbYhlxId(Integer xmlxId, String xmlxmc, String xmlxdm, Integer deleted, Integer sjxmlxId) {
        this.xmlxId = xmlxId;
        this.xmlxmc = xmlxmc;
        this.xmlxdm = xmlxdm;
        this.deleted = deleted;
        this.sjxmlxId = sjxmlxId;
    }

   
    // Property accessors

    @Column(name="XMLX_ID", nullable=false, precision=22, scale=0)

    public Integer getXmlxId() {
        return this.xmlxId;
    }
    
    public void setXmlxId(Integer xmlxId) {
        this.xmlxId = xmlxId;
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

    @Column(name="SJXMLX_ID", precision=22, scale=0)

    public Integer getSjxmlxId() {
        return this.sjxmlxId;
    }
    
    public void setSjxmlxId(Integer sjxmlxId) {
        this.sjxmlxId = sjxmlxId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof XmsbYhlxId) ) return false;
		 XmsbYhlxId castOther = ( XmsbYhlxId ) other; 
         
		 return ( (this.getXmlxId()==castOther.getXmlxId()) || ( this.getXmlxId()!=null && castOther.getXmlxId()!=null && this.getXmlxId().equals(castOther.getXmlxId()) ) )
 && ( (this.getXmlxmc()==castOther.getXmlxmc()) || ( this.getXmlxmc()!=null && castOther.getXmlxmc()!=null && this.getXmlxmc().equals(castOther.getXmlxmc()) ) )
 && ( (this.getXmlxdm()==castOther.getXmlxdm()) || ( this.getXmlxdm()!=null && castOther.getXmlxdm()!=null && this.getXmlxdm().equals(castOther.getXmlxdm()) ) )
 && ( (this.getDeleted()==castOther.getDeleted()) || ( this.getDeleted()!=null && castOther.getDeleted()!=null && this.getDeleted().equals(castOther.getDeleted()) ) )
 && ( (this.getSjxmlxId()==castOther.getSjxmlxId()) || ( this.getSjxmlxId()!=null && castOther.getSjxmlxId()!=null && this.getSjxmlxId().equals(castOther.getSjxmlxId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getXmlxId() == null ? 0 : this.getXmlxId().hashCode() );
         result = 37 * result + ( getXmlxmc() == null ? 0 : this.getXmlxmc().hashCode() );
         result = 37 * result + ( getXmlxdm() == null ? 0 : this.getXmlxdm().hashCode() );
         result = 37 * result + ( getDeleted() == null ? 0 : this.getDeleted().hashCode() );
         result = 37 * result + ( getSjxmlxId() == null ? 0 : this.getSjxmlxId().hashCode() );
         return result;
   }   





}