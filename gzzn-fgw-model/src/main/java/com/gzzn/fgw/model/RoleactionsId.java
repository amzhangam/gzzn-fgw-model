package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * RoleactionsId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class RoleactionsId  implements java.io.Serializable {


    // Fields    

     private Integer roleactionsid;
     private Integer roleid;
     private Integer actionid;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;


    // Constructors

    /** default constructor */
    public RoleactionsId() {
    }

	/** minimal constructor */
    public RoleactionsId(Integer roleactionsid, Integer roleid, Integer actionid) {
        this.roleactionsid = roleactionsid;
        this.roleid = roleid;
        this.actionid = actionid;
    }
    
    /** full constructor */
    public RoleactionsId(Integer roleactionsid, Integer roleid, Integer actionid, Integer recorderid, String recordername, Date recordertime) {
        this.roleactionsid = roleactionsid;
        this.roleid = roleid;
        this.actionid = actionid;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
    }

   
    // Property accessors

    @Column(name="ROLEACTIONSID", nullable=false, precision=22, scale=0)

    public Integer getRoleactionsid() {
        return this.roleactionsid;
    }
    
    public void setRoleactionsid(Integer roleactionsid) {
        this.roleactionsid = roleactionsid;
    }

    @Column(name="ROLEID", nullable=false, precision=22, scale=0)

    public Integer getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    @Column(name="ACTIONID", nullable=false, precision=22, scale=0)

    public Integer getActionid() {
        return this.actionid;
    }
    
    public void setActionid(Integer actionid) {
        this.actionid = actionid;
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
		 if ( !(other instanceof RoleactionsId) ) return false;
		 RoleactionsId castOther = ( RoleactionsId ) other; 
         
		 return ( (this.getRoleactionsid()==castOther.getRoleactionsid()) || ( this.getRoleactionsid()!=null && castOther.getRoleactionsid()!=null && this.getRoleactionsid().equals(castOther.getRoleactionsid()) ) )
 && ( (this.getRoleid()==castOther.getRoleid()) || ( this.getRoleid()!=null && castOther.getRoleid()!=null && this.getRoleid().equals(castOther.getRoleid()) ) )
 && ( (this.getActionid()==castOther.getActionid()) || ( this.getActionid()!=null && castOther.getActionid()!=null && this.getActionid().equals(castOther.getActionid()) ) )
 && ( (this.getRecorderid()==castOther.getRecorderid()) || ( this.getRecorderid()!=null && castOther.getRecorderid()!=null && this.getRecorderid().equals(castOther.getRecorderid()) ) )
 && ( (this.getRecordername()==castOther.getRecordername()) || ( this.getRecordername()!=null && castOther.getRecordername()!=null && this.getRecordername().equals(castOther.getRecordername()) ) )
 && ( (this.getRecordertime()==castOther.getRecordertime()) || ( this.getRecordertime()!=null && castOther.getRecordertime()!=null && this.getRecordertime().equals(castOther.getRecordertime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleactionsid() == null ? 0 : this.getRoleactionsid().hashCode() );
         result = 37 * result + ( getRoleid() == null ? 0 : this.getRoleid().hashCode() );
         result = 37 * result + ( getActionid() == null ? 0 : this.getActionid().hashCode() );
         result = 37 * result + ( getRecorderid() == null ? 0 : this.getRecorderid().hashCode() );
         result = 37 * result + ( getRecordername() == null ? 0 : this.getRecordername().hashCode() );
         result = 37 * result + ( getRecordertime() == null ? 0 : this.getRecordertime().hashCode() );
         return result;
   }   





}