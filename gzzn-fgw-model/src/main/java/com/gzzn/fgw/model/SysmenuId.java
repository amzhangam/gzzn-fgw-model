package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SysmenuId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class SysmenuId  implements java.io.Serializable {


    // Fields    

     private Integer systemmenuid;
     private String systemmenuname;
     private String redirecturl;
     private Integer systemmenulevel;
     private Integer importancelevel;
     private String enabled;
     private Integer actionid;
     private Integer parentsystemmenuid;
     private String description;
     private String keywords;
     private String cssclassname;
     private String value1;
     private String value2;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;


    // Constructors

    /** default constructor */
    public SysmenuId() {
    }

	/** minimal constructor */
    public SysmenuId(Integer systemmenuid, String systemmenuname, String redirecturl, Integer systemmenulevel, Integer importancelevel, String enabled, Integer actionid) {
        this.systemmenuid = systemmenuid;
        this.systemmenuname = systemmenuname;
        this.redirecturl = redirecturl;
        this.systemmenulevel = systemmenulevel;
        this.importancelevel = importancelevel;
        this.enabled = enabled;
        this.actionid = actionid;
    }
    
    /** full constructor */
    public SysmenuId(Integer systemmenuid, String systemmenuname, String redirecturl, Integer systemmenulevel, Integer importancelevel, String enabled, Integer actionid, Integer parentsystemmenuid, String description, String keywords, String cssclassname, String value1, String value2, Integer recorderid, String recordername, Date recordertime) {
        this.systemmenuid = systemmenuid;
        this.systemmenuname = systemmenuname;
        this.redirecturl = redirecturl;
        this.systemmenulevel = systemmenulevel;
        this.importancelevel = importancelevel;
        this.enabled = enabled;
        this.actionid = actionid;
        this.parentsystemmenuid = parentsystemmenuid;
        this.description = description;
        this.keywords = keywords;
        this.cssclassname = cssclassname;
        this.value1 = value1;
        this.value2 = value2;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
    }

   
    // Property accessors

    @Column(name="SYSTEMMENUID", nullable=false, precision=22, scale=0)

    public Integer getSystemmenuid() {
        return this.systemmenuid;
    }
    
    public void setSystemmenuid(Integer systemmenuid) {
        this.systemmenuid = systemmenuid;
    }

    @Column(name="SYSTEMMENUNAME", nullable=false)

    public String getSystemmenuname() {
        return this.systemmenuname;
    }
    
    public void setSystemmenuname(String systemmenuname) {
        this.systemmenuname = systemmenuname;
    }

    @Column(name="REDIRECTURL", nullable=false)

    public String getRedirecturl() {
        return this.redirecturl;
    }
    
    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    @Column(name="SYSTEMMENULEVEL", nullable=false, precision=22, scale=0)

    public Integer getSystemmenulevel() {
        return this.systemmenulevel;
    }
    
    public void setSystemmenulevel(Integer systemmenulevel) {
        this.systemmenulevel = systemmenulevel;
    }

    @Column(name="IMPORTANCELEVEL", nullable=false, precision=22, scale=0)

    public Integer getImportancelevel() {
        return this.importancelevel;
    }
    
    public void setImportancelevel(Integer importancelevel) {
        this.importancelevel = importancelevel;
    }

    @Column(name="ENABLED", nullable=false, length=1)

    public String getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Column(name="ACTIONID", nullable=false, precision=22, scale=0)

    public Integer getActionid() {
        return this.actionid;
    }
    
    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    @Column(name="PARENTSYSTEMMENUID", precision=22, scale=0)

    public Integer getParentsystemmenuid() {
        return this.parentsystemmenuid;
    }
    
    public void setParentsystemmenuid(Integer parentsystemmenuid) {
        this.parentsystemmenuid = parentsystemmenuid;
    }

    @Column(name="DESCRIPTION", length=200)

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="KEYWORDS", length=100)

    public String getKeywords() {
        return this.keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name="CSSCLASSNAME", length=50)

    public String getCssclassname() {
        return this.cssclassname;
    }
    
    public void setCssclassname(String cssclassname) {
        this.cssclassname = cssclassname;
    }

    @Column(name="VALUE1", length=100)

    public String getValue1() {
        return this.value1;
    }
    
    public void setValue1(String value1) {
        this.value1 = value1;
    }

    @Column(name="VALUE2", length=100)

    public String getValue2() {
        return this.value2;
    }
    
    public void setValue2(String value2) {
        this.value2 = value2;
    }

    @Column(name="RECORDERID", precision=22, scale=0)

    public Integer getRecorderid() {
        return this.recorderid;
    }
    
    public void setRecorderid(Integer recorderid) {
        this.recorderid = recorderid;
    }

    @Column(name="RECORDERNAME", length=50)

    public String getRecordername() {
        return this.recordername;
    }
    
    public void setRecordername(String recordername) {
        this.recordername = recordername;
    }
@Temporal(TemporalType.DATE)
    @Column(name="RECORDERTIME", length=7)

    public Date getRecordertime() {
        return this.recordertime;
    }
    
    public void setRecordertime(Date recordertime) {
        this.recordertime = recordertime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SysmenuId) ) return false;
		 SysmenuId castOther = ( SysmenuId ) other; 
         
		 return ( (this.getSystemmenuid()==castOther.getSystemmenuid()) || ( this.getSystemmenuid()!=null && castOther.getSystemmenuid()!=null && this.getSystemmenuid().equals(castOther.getSystemmenuid()) ) )
 && ( (this.getSystemmenuname()==castOther.getSystemmenuname()) || ( this.getSystemmenuname()!=null && castOther.getSystemmenuname()!=null && this.getSystemmenuname().equals(castOther.getSystemmenuname()) ) )
 && ( (this.getRedirecturl()==castOther.getRedirecturl()) || ( this.getRedirecturl()!=null && castOther.getRedirecturl()!=null && this.getRedirecturl().equals(castOther.getRedirecturl()) ) )
 && ( (this.getSystemmenulevel()==castOther.getSystemmenulevel()) || ( this.getSystemmenulevel()!=null && castOther.getSystemmenulevel()!=null && this.getSystemmenulevel().equals(castOther.getSystemmenulevel()) ) )
 && ( (this.getImportancelevel()==castOther.getImportancelevel()) || ( this.getImportancelevel()!=null && castOther.getImportancelevel()!=null && this.getImportancelevel().equals(castOther.getImportancelevel()) ) )
 && ( (this.getEnabled()==castOther.getEnabled()) || ( this.getEnabled()!=null && castOther.getEnabled()!=null && this.getEnabled().equals(castOther.getEnabled()) ) )
 && ( (this.getActionid()==castOther.getActionid()) || ( this.getActionid()!=null && castOther.getActionid()!=null && this.getActionid().equals(castOther.getActionid()) ) )
 && ( (this.getParentsystemmenuid()==castOther.getParentsystemmenuid()) || ( this.getParentsystemmenuid()!=null && castOther.getParentsystemmenuid()!=null && this.getParentsystemmenuid().equals(castOther.getParentsystemmenuid()) ) )
 && ( (this.getDescription()==castOther.getDescription()) || ( this.getDescription()!=null && castOther.getDescription()!=null && this.getDescription().equals(castOther.getDescription()) ) )
 && ( (this.getKeywords()==castOther.getKeywords()) || ( this.getKeywords()!=null && castOther.getKeywords()!=null && this.getKeywords().equals(castOther.getKeywords()) ) )
 && ( (this.getCssclassname()==castOther.getCssclassname()) || ( this.getCssclassname()!=null && castOther.getCssclassname()!=null && this.getCssclassname().equals(castOther.getCssclassname()) ) )
 && ( (this.getValue1()==castOther.getValue1()) || ( this.getValue1()!=null && castOther.getValue1()!=null && this.getValue1().equals(castOther.getValue1()) ) )
 && ( (this.getValue2()==castOther.getValue2()) || ( this.getValue2()!=null && castOther.getValue2()!=null && this.getValue2().equals(castOther.getValue2()) ) )
 && ( (this.getRecorderid()==castOther.getRecorderid()) || ( this.getRecorderid()!=null && castOther.getRecorderid()!=null && this.getRecorderid().equals(castOther.getRecorderid()) ) )
 && ( (this.getRecordername()==castOther.getRecordername()) || ( this.getRecordername()!=null && castOther.getRecordername()!=null && this.getRecordername().equals(castOther.getRecordername()) ) )
 && ( (this.getRecordertime()==castOther.getRecordertime()) || ( this.getRecordertime()!=null && castOther.getRecordertime()!=null && this.getRecordertime().equals(castOther.getRecordertime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSystemmenuid() == null ? 0 : this.getSystemmenuid().hashCode() );
         result = 37 * result + ( getSystemmenuname() == null ? 0 : this.getSystemmenuname().hashCode() );
         result = 37 * result + ( getRedirecturl() == null ? 0 : this.getRedirecturl().hashCode() );
         result = 37 * result + ( getSystemmenulevel() == null ? 0 : this.getSystemmenulevel().hashCode() );
         result = 37 * result + ( getImportancelevel() == null ? 0 : this.getImportancelevel().hashCode() );
         result = 37 * result + ( getEnabled() == null ? 0 : this.getEnabled().hashCode() );
         result = 37 * result + ( getActionid() == null ? 0 : this.getActionid().hashCode() );
         result = 37 * result + ( getParentsystemmenuid() == null ? 0 : this.getParentsystemmenuid().hashCode() );
         result = 37 * result + ( getDescription() == null ? 0 : this.getDescription().hashCode() );
         result = 37 * result + ( getKeywords() == null ? 0 : this.getKeywords().hashCode() );
         result = 37 * result + ( getCssclassname() == null ? 0 : this.getCssclassname().hashCode() );
         result = 37 * result + ( getValue1() == null ? 0 : this.getValue1().hashCode() );
         result = 37 * result + ( getValue2() == null ? 0 : this.getValue2().hashCode() );
         result = 37 * result + ( getRecorderid() == null ? 0 : this.getRecorderid().hashCode() );
         result = 37 * result + ( getRecordername() == null ? 0 : this.getRecordername().hashCode() );
         result = 37 * result + ( getRecordertime() == null ? 0 : this.getRecordertime().hashCode() );
         return result;
   }   





}