package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * RolesId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class RolesId  implements java.io.Serializable {


    // Fields    

     private Integer roleid;
     private String rolename;
     private String description;
     private Integer issensitive;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;


    // Constructors

    /** default constructor */
    public RolesId() {
    }

	/** minimal constructor */
    public RolesId(Integer roleid, String rolename) {
        this.roleid = roleid;
        this.rolename = rolename;
    }
    
    /** full constructor */
    public RolesId(Integer roleid, String rolename, String description, Integer issensitive, Integer recorderid, String recordername, Date recordertime) {
        this.roleid = roleid;
        this.rolename = rolename;
        this.description = description;
        this.issensitive = issensitive;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
    }

   
    // Property accessors

    @Column(name="ROLEID", nullable=false, precision=22, scale=0)

    public Integer getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    @Column(name="ROLENAME", nullable=false, length=50)

    public String getRolename() {
        return this.rolename;
    }
    
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Column(name="DESCRIPTION")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="ISSENSITIVE", precision=22, scale=0)

    public Integer getIssensitive() {
        return this.issensitive;
    }
    
    public void setIssensitive(Integer issensitive) {
        this.issensitive = issensitive;
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
		 if ( !(other instanceof RolesId) ) return false;
		 RolesId castOther = ( RolesId ) other; 
         
		 return ( (this.getRoleid()==castOther.getRoleid()) || ( this.getRoleid()!=null && castOther.getRoleid()!=null && this.getRoleid().equals(castOther.getRoleid()) ) )
 && ( (this.getRolename()==castOther.getRolename()) || ( this.getRolename()!=null && castOther.getRolename()!=null && this.getRolename().equals(castOther.getRolename()) ) )
 && ( (this.getDescription()==castOther.getDescription()) || ( this.getDescription()!=null && castOther.getDescription()!=null && this.getDescription().equals(castOther.getDescription()) ) )
 && ( (this.getIssensitive()==castOther.getIssensitive()) || ( this.getIssensitive()!=null && castOther.getIssensitive()!=null && this.getIssensitive().equals(castOther.getIssensitive()) ) )
 && ( (this.getRecorderid()==castOther.getRecorderid()) || ( this.getRecorderid()!=null && castOther.getRecorderid()!=null && this.getRecorderid().equals(castOther.getRecorderid()) ) )
 && ( (this.getRecordername()==castOther.getRecordername()) || ( this.getRecordername()!=null && castOther.getRecordername()!=null && this.getRecordername().equals(castOther.getRecordername()) ) )
 && ( (this.getRecordertime()==castOther.getRecordertime()) || ( this.getRecordertime()!=null && castOther.getRecordertime()!=null && this.getRecordertime().equals(castOther.getRecordertime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleid() == null ? 0 : this.getRoleid().hashCode() );
         result = 37 * result + ( getRolename() == null ? 0 : this.getRolename().hashCode() );
         result = 37 * result + ( getDescription() == null ? 0 : this.getDescription().hashCode() );
         result = 37 * result + ( getIssensitive() == null ? 0 : this.getIssensitive().hashCode() );
         result = 37 * result + ( getRecorderid() == null ? 0 : this.getRecorderid().hashCode() );
         result = 37 * result + ( getRecordername() == null ? 0 : this.getRecordername().hashCode() );
         result = 37 * result + ( getRecordertime() == null ? 0 : this.getRecordertime().hashCode() );
         return result;
   }   





}