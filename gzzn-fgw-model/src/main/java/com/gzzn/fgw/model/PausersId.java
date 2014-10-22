package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * PausersId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class PausersId  implements java.io.Serializable {


    // Fields    

     private Integer userid;
     private Integer workunitsid;
     private Integer usertype;
     private Integer userdept;
     private String userduty;
     private Integer userrole;
     private String loginid;
     private String username;
     private String upassword;
     private String callphone;
     private Integer status;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;


    // Constructors

    /** default constructor */
    public PausersId() {
    }

	/** minimal constructor */
    public PausersId(Integer userid, Integer usertype, String loginid, String username, String upassword) {
        this.userid = userid;
        this.usertype = usertype;
        this.loginid = loginid;
        this.username = username;
        this.upassword = upassword;
    }
    
    /** full constructor */
    public PausersId(Integer userid, Integer workunitsid, Integer usertype, Integer userdept, String userduty, Integer userrole, String loginid, String username, String upassword, String callphone, Integer status, Integer recorderid, String recordername, Date recordertime) {
        this.userid = userid;
        this.workunitsid = workunitsid;
        this.usertype = usertype;
        this.userdept = userdept;
        this.userduty = userduty;
        this.userrole = userrole;
        this.loginid = loginid;
        this.username = username;
        this.upassword = upassword;
        this.callphone = callphone;
        this.status = status;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
    }

   
    // Property accessors

    @Column(name="USERID", nullable=false, precision=22, scale=0)

    public Integer getUserid() {
        return this.userid;
    }
    
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Column(name="WORKUNITSID", precision=22, scale=0)

    public Integer getWorkunitsid() {
        return this.workunitsid;
    }
    
    public void setWorkunitsid(Integer workunitsid) {
        this.workunitsid = workunitsid;
    }

    @Column(name="USERTYPE", nullable=false, precision=22, scale=0)

    public Integer getUsertype() {
        return this.usertype;
    }
    
    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    @Column(name="USERDEPT", precision=22, scale=0)

    public Integer getUserdept() {
        return this.userdept;
    }
    
    public void setUserdept(Integer userdept) {
        this.userdept = userdept;
    }

    @Column(name="USERDUTY")

    public String getUserduty() {
        return this.userduty;
    }
    
    public void setUserduty(String userduty) {
        this.userduty = userduty;
    }

    @Column(name="USERROLE", precision=22, scale=0)

    public Integer getUserrole() {
        return this.userrole;
    }
    
    public void setUserrole(Integer userrole) {
        this.userrole = userrole;
    }

    @Column(name="LOGINID", nullable=false, length=50)

    public String getLoginid() {
        return this.loginid;
    }
    
    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    @Column(name="USERNAME", nullable=false)

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="UPASSWORD", nullable=false, length=50)

    public String getUpassword() {
        return this.upassword;
    }
    
    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    @Column(name="CALLPHONE", length=50)

    public String getCallphone() {
        return this.callphone;
    }
    
    public void setCallphone(String callphone) {
        this.callphone = callphone;
    }

    @Column(name="STATUS", precision=22, scale=0)

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
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
		 if ( !(other instanceof PausersId) ) return false;
		 PausersId castOther = ( PausersId ) other; 
         
		 return ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getWorkunitsid()==castOther.getWorkunitsid()) || ( this.getWorkunitsid()!=null && castOther.getWorkunitsid()!=null && this.getWorkunitsid().equals(castOther.getWorkunitsid()) ) )
 && ( (this.getUsertype()==castOther.getUsertype()) || ( this.getUsertype()!=null && castOther.getUsertype()!=null && this.getUsertype().equals(castOther.getUsertype()) ) )
 && ( (this.getUserdept()==castOther.getUserdept()) || ( this.getUserdept()!=null && castOther.getUserdept()!=null && this.getUserdept().equals(castOther.getUserdept()) ) )
 && ( (this.getUserduty()==castOther.getUserduty()) || ( this.getUserduty()!=null && castOther.getUserduty()!=null && this.getUserduty().equals(castOther.getUserduty()) ) )
 && ( (this.getUserrole()==castOther.getUserrole()) || ( this.getUserrole()!=null && castOther.getUserrole()!=null && this.getUserrole().equals(castOther.getUserrole()) ) )
 && ( (this.getLoginid()==castOther.getLoginid()) || ( this.getLoginid()!=null && castOther.getLoginid()!=null && this.getLoginid().equals(castOther.getLoginid()) ) )
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getUpassword()==castOther.getUpassword()) || ( this.getUpassword()!=null && castOther.getUpassword()!=null && this.getUpassword().equals(castOther.getUpassword()) ) )
 && ( (this.getCallphone()==castOther.getCallphone()) || ( this.getCallphone()!=null && castOther.getCallphone()!=null && this.getCallphone().equals(castOther.getCallphone()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getRecorderid()==castOther.getRecorderid()) || ( this.getRecorderid()!=null && castOther.getRecorderid()!=null && this.getRecorderid().equals(castOther.getRecorderid()) ) )
 && ( (this.getRecordername()==castOther.getRecordername()) || ( this.getRecordername()!=null && castOther.getRecordername()!=null && this.getRecordername().equals(castOther.getRecordername()) ) )
 && ( (this.getRecordertime()==castOther.getRecordertime()) || ( this.getRecordertime()!=null && castOther.getRecordertime()!=null && this.getRecordertime().equals(castOther.getRecordertime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getWorkunitsid() == null ? 0 : this.getWorkunitsid().hashCode() );
         result = 37 * result + ( getUsertype() == null ? 0 : this.getUsertype().hashCode() );
         result = 37 * result + ( getUserdept() == null ? 0 : this.getUserdept().hashCode() );
         result = 37 * result + ( getUserduty() == null ? 0 : this.getUserduty().hashCode() );
         result = 37 * result + ( getUserrole() == null ? 0 : this.getUserrole().hashCode() );
         result = 37 * result + ( getLoginid() == null ? 0 : this.getLoginid().hashCode() );
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getUpassword() == null ? 0 : this.getUpassword().hashCode() );
         result = 37 * result + ( getCallphone() == null ? 0 : this.getCallphone().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getRecorderid() == null ? 0 : this.getRecorderid().hashCode() );
         result = 37 * result + ( getRecordername() == null ? 0 : this.getRecordername().hashCode() );
         result = 37 * result + ( getRecordertime() == null ? 0 : this.getRecordertime().hashCode() );
         return result;
   }   





}