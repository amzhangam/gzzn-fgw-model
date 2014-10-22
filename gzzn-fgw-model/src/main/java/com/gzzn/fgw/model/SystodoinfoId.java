package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SystodoinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class SystodoinfoId  implements java.io.Serializable {


    // Fields    

     private Integer todoinfoid;
     private String todoinfoname;
     private String todoinfotype;
     private String redirecturl;
     private Integer importancelevel;
     private Integer userid;
     private Integer status;
     private String description;
     private String processclass;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;


    // Constructors

    /** default constructor */
    public SystodoinfoId() {
    }

	/** minimal constructor */
    public SystodoinfoId(Integer todoinfoid, String todoinfoname, String todoinfotype, String redirecturl, Integer importancelevel, Integer userid, Integer status) {
        this.todoinfoid = todoinfoid;
        this.todoinfoname = todoinfoname;
        this.todoinfotype = todoinfotype;
        this.redirecturl = redirecturl;
        this.importancelevel = importancelevel;
        this.userid = userid;
        this.status = status;
    }
    
    /** full constructor */
    public SystodoinfoId(Integer todoinfoid, String todoinfoname, String todoinfotype, String redirecturl, Integer importancelevel, Integer userid, Integer status, String description, String processclass, Integer recorderid, String recordername, Date recordertime) {
        this.todoinfoid = todoinfoid;
        this.todoinfoname = todoinfoname;
        this.todoinfotype = todoinfotype;
        this.redirecturl = redirecturl;
        this.importancelevel = importancelevel;
        this.userid = userid;
        this.status = status;
        this.description = description;
        this.processclass = processclass;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
    }

   
    // Property accessors

    @Column(name="TODOINFOID", nullable=false, precision=22, scale=0)

    public Integer getTodoinfoid() {
        return this.todoinfoid;
    }
    
    public void setTodoinfoid(Integer todoinfoid) {
        this.todoinfoid = todoinfoid;
    }

    @Column(name="TODOINFONAME", nullable=false)

    public String getTodoinfoname() {
        return this.todoinfoname;
    }
    
    public void setTodoinfoname(String todoinfoname) {
        this.todoinfoname = todoinfoname;
    }

    @Column(name="TODOINFOTYPE", nullable=false)

    public String getTodoinfotype() {
        return this.todoinfotype;
    }
    
    public void setTodoinfotype(String todoinfotype) {
        this.todoinfotype = todoinfotype;
    }

    @Column(name="REDIRECTURL", nullable=false)

    public String getRedirecturl() {
        return this.redirecturl;
    }
    
    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    @Column(name="IMPORTANCELEVEL", nullable=false, precision=22, scale=0)

    public Integer getImportancelevel() {
        return this.importancelevel;
    }
    
    public void setImportancelevel(Integer importancelevel) {
        this.importancelevel = importancelevel;
    }

    @Column(name="USERID", nullable=false, precision=22, scale=0)

    public Integer getUserid() {
        return this.userid;
    }
    
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Column(name="STATUS", nullable=false, precision=22, scale=0)

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name="DESCRIPTION", length=500)

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="PROCESSCLASS", length=200)

    public String getProcessclass() {
        return this.processclass;
    }
    
    public void setProcessclass(String processclass) {
        this.processclass = processclass;
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
		 if ( !(other instanceof SystodoinfoId) ) return false;
		 SystodoinfoId castOther = ( SystodoinfoId ) other; 
         
		 return ( (this.getTodoinfoid()==castOther.getTodoinfoid()) || ( this.getTodoinfoid()!=null && castOther.getTodoinfoid()!=null && this.getTodoinfoid().equals(castOther.getTodoinfoid()) ) )
 && ( (this.getTodoinfoname()==castOther.getTodoinfoname()) || ( this.getTodoinfoname()!=null && castOther.getTodoinfoname()!=null && this.getTodoinfoname().equals(castOther.getTodoinfoname()) ) )
 && ( (this.getTodoinfotype()==castOther.getTodoinfotype()) || ( this.getTodoinfotype()!=null && castOther.getTodoinfotype()!=null && this.getTodoinfotype().equals(castOther.getTodoinfotype()) ) )
 && ( (this.getRedirecturl()==castOther.getRedirecturl()) || ( this.getRedirecturl()!=null && castOther.getRedirecturl()!=null && this.getRedirecturl().equals(castOther.getRedirecturl()) ) )
 && ( (this.getImportancelevel()==castOther.getImportancelevel()) || ( this.getImportancelevel()!=null && castOther.getImportancelevel()!=null && this.getImportancelevel().equals(castOther.getImportancelevel()) ) )
 && ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getDescription()==castOther.getDescription()) || ( this.getDescription()!=null && castOther.getDescription()!=null && this.getDescription().equals(castOther.getDescription()) ) )
 && ( (this.getProcessclass()==castOther.getProcessclass()) || ( this.getProcessclass()!=null && castOther.getProcessclass()!=null && this.getProcessclass().equals(castOther.getProcessclass()) ) )
 && ( (this.getRecorderid()==castOther.getRecorderid()) || ( this.getRecorderid()!=null && castOther.getRecorderid()!=null && this.getRecorderid().equals(castOther.getRecorderid()) ) )
 && ( (this.getRecordername()==castOther.getRecordername()) || ( this.getRecordername()!=null && castOther.getRecordername()!=null && this.getRecordername().equals(castOther.getRecordername()) ) )
 && ( (this.getRecordertime()==castOther.getRecordertime()) || ( this.getRecordertime()!=null && castOther.getRecordertime()!=null && this.getRecordertime().equals(castOther.getRecordertime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTodoinfoid() == null ? 0 : this.getTodoinfoid().hashCode() );
         result = 37 * result + ( getTodoinfoname() == null ? 0 : this.getTodoinfoname().hashCode() );
         result = 37 * result + ( getTodoinfotype() == null ? 0 : this.getTodoinfotype().hashCode() );
         result = 37 * result + ( getRedirecturl() == null ? 0 : this.getRedirecturl().hashCode() );
         result = 37 * result + ( getImportancelevel() == null ? 0 : this.getImportancelevel().hashCode() );
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getDescription() == null ? 0 : this.getDescription().hashCode() );
         result = 37 * result + ( getProcessclass() == null ? 0 : this.getProcessclass().hashCode() );
         result = 37 * result + ( getRecorderid() == null ? 0 : this.getRecorderid().hashCode() );
         result = 37 * result + ( getRecordername() == null ? 0 : this.getRecordername().hashCode() );
         result = 37 * result + ( getRecordertime() == null ? 0 : this.getRecordertime().hashCode() );
         return result;
   }   





}