package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SyscustomermenuId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class SyscustomermenuId  implements java.io.Serializable {


    // Fields    

     private Integer syscustomermenuid;
     private Integer systemmenuid;
     private String userid;
     private String issystemdefault;
     private String status;
     private Integer importancelevel;
     private String cssclassname;
     private String value1;
     private String value2;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;


    // Constructors

    /** default constructor */
    public SyscustomermenuId() {
    }

	/** minimal constructor */
    public SyscustomermenuId(Integer syscustomermenuid, Integer systemmenuid, String userid, String issystemdefault, String status, Integer importancelevel) {
        this.syscustomermenuid = syscustomermenuid;
        this.systemmenuid = systemmenuid;
        this.userid = userid;
        this.issystemdefault = issystemdefault;
        this.status = status;
        this.importancelevel = importancelevel;
    }
    
    /** full constructor */
    public SyscustomermenuId(Integer syscustomermenuid, Integer systemmenuid, String userid, String issystemdefault, String status, Integer importancelevel, String cssclassname, String value1, String value2, Integer recorderid, String recordername, Date recordertime) {
        this.syscustomermenuid = syscustomermenuid;
        this.systemmenuid = systemmenuid;
        this.userid = userid;
        this.issystemdefault = issystemdefault;
        this.status = status;
        this.importancelevel = importancelevel;
        this.cssclassname = cssclassname;
        this.value1 = value1;
        this.value2 = value2;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
    }

   
    // Property accessors

    @Column(name="SYSCUSTOMERMENUID", nullable=false, precision=22, scale=0)

    public Integer getSyscustomermenuid() {
        return this.syscustomermenuid;
    }
    
    public void setSyscustomermenuid(Integer syscustomermenuid) {
        this.syscustomermenuid = syscustomermenuid;
    }

    @Column(name="SYSTEMMENUID", nullable=false, precision=22, scale=0)

    public Integer getSystemmenuid() {
        return this.systemmenuid;
    }
    
    public void setSystemmenuid(Integer systemmenuid) {
        this.systemmenuid = systemmenuid;
    }

    @Column(name="USERID", nullable=false, length=50)

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name="ISSYSTEMDEFAULT", nullable=false, length=1)

    public String getIssystemdefault() {
        return this.issystemdefault;
    }
    
    public void setIssystemdefault(String issystemdefault) {
        this.issystemdefault = issystemdefault;
    }

    @Column(name="STATUS", nullable=false, length=1)

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name="IMPORTANCELEVEL", nullable=false, precision=22, scale=0)

    public Integer getImportancelevel() {
        return this.importancelevel;
    }
    
    public void setImportancelevel(Integer importancelevel) {
        this.importancelevel = importancelevel;
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

    @Column(name="VALUE2", length=200)

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
		 if ( !(other instanceof SyscustomermenuId) ) return false;
		 SyscustomermenuId castOther = ( SyscustomermenuId ) other; 
         
		 return ( (this.getSyscustomermenuid()==castOther.getSyscustomermenuid()) || ( this.getSyscustomermenuid()!=null && castOther.getSyscustomermenuid()!=null && this.getSyscustomermenuid().equals(castOther.getSyscustomermenuid()) ) )
 && ( (this.getSystemmenuid()==castOther.getSystemmenuid()) || ( this.getSystemmenuid()!=null && castOther.getSystemmenuid()!=null && this.getSystemmenuid().equals(castOther.getSystemmenuid()) ) )
 && ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getIssystemdefault()==castOther.getIssystemdefault()) || ( this.getIssystemdefault()!=null && castOther.getIssystemdefault()!=null && this.getIssystemdefault().equals(castOther.getIssystemdefault()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getImportancelevel()==castOther.getImportancelevel()) || ( this.getImportancelevel()!=null && castOther.getImportancelevel()!=null && this.getImportancelevel().equals(castOther.getImportancelevel()) ) )
 && ( (this.getCssclassname()==castOther.getCssclassname()) || ( this.getCssclassname()!=null && castOther.getCssclassname()!=null && this.getCssclassname().equals(castOther.getCssclassname()) ) )
 && ( (this.getValue1()==castOther.getValue1()) || ( this.getValue1()!=null && castOther.getValue1()!=null && this.getValue1().equals(castOther.getValue1()) ) )
 && ( (this.getValue2()==castOther.getValue2()) || ( this.getValue2()!=null && castOther.getValue2()!=null && this.getValue2().equals(castOther.getValue2()) ) )
 && ( (this.getRecorderid()==castOther.getRecorderid()) || ( this.getRecorderid()!=null && castOther.getRecorderid()!=null && this.getRecorderid().equals(castOther.getRecorderid()) ) )
 && ( (this.getRecordername()==castOther.getRecordername()) || ( this.getRecordername()!=null && castOther.getRecordername()!=null && this.getRecordername().equals(castOther.getRecordername()) ) )
 && ( (this.getRecordertime()==castOther.getRecordertime()) || ( this.getRecordertime()!=null && castOther.getRecordertime()!=null && this.getRecordertime().equals(castOther.getRecordertime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSyscustomermenuid() == null ? 0 : this.getSyscustomermenuid().hashCode() );
         result = 37 * result + ( getSystemmenuid() == null ? 0 : this.getSystemmenuid().hashCode() );
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getIssystemdefault() == null ? 0 : this.getIssystemdefault().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getImportancelevel() == null ? 0 : this.getImportancelevel().hashCode() );
         result = 37 * result + ( getCssclassname() == null ? 0 : this.getCssclassname().hashCode() );
         result = 37 * result + ( getValue1() == null ? 0 : this.getValue1().hashCode() );
         result = 37 * result + ( getValue2() == null ? 0 : this.getValue2().hashCode() );
         result = 37 * result + ( getRecorderid() == null ? 0 : this.getRecorderid().hashCode() );
         result = 37 * result + ( getRecordername() == null ? 0 : this.getRecordername().hashCode() );
         result = 37 * result + ( getRecordertime() == null ? 0 : this.getRecordertime().hashCode() );
         return result;
   }   





}