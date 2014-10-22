package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SysloginlogId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class SysloginlogId  implements java.io.Serializable {


    // Fields    

     private Integer loginid;
     private String loginuser;
     private String loginip;
     private String loginbrowse;
     private String loginbrowsever;
     private String loginos;
     private Integer loginresult;
     private Date logintime;


    // Constructors

    /** default constructor */
    public SysloginlogId() {
    }

	/** minimal constructor */
    public SysloginlogId(Integer loginid) {
        this.loginid = loginid;
    }
    
    /** full constructor */
    public SysloginlogId(Integer loginid, String loginuser, String loginip, String loginbrowse, String loginbrowsever, String loginos, Integer loginresult, Date logintime) {
        this.loginid = loginid;
        this.loginuser = loginuser;
        this.loginip = loginip;
        this.loginbrowse = loginbrowse;
        this.loginbrowsever = loginbrowsever;
        this.loginos = loginos;
        this.loginresult = loginresult;
        this.logintime = logintime;
    }

   
    // Property accessors

    @Column(name="LOGINID", nullable=false, precision=22, scale=0)

    public Integer getLoginid() {
        return this.loginid;
    }
    
    public void setLoginid(Integer loginid) {
        this.loginid = loginid;
    }

    @Column(name="LOGINUSER")

    public String getLoginuser() {
        return this.loginuser;
    }
    
    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    @Column(name="LOGINIP")

    public String getLoginip() {
        return this.loginip;
    }
    
    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    @Column(name="LOGINBROWSE")

    public String getLoginbrowse() {
        return this.loginbrowse;
    }
    
    public void setLoginbrowse(String loginbrowse) {
        this.loginbrowse = loginbrowse;
    }

    @Column(name="LOGINBROWSEVER")

    public String getLoginbrowsever() {
        return this.loginbrowsever;
    }
    
    public void setLoginbrowsever(String loginbrowsever) {
        this.loginbrowsever = loginbrowsever;
    }

    @Column(name="LOGINOS")

    public String getLoginos() {
        return this.loginos;
    }
    
    public void setLoginos(String loginos) {
        this.loginos = loginos;
    }

    @Column(name="LOGINRESULT", precision=22, scale=0)

    public Integer getLoginresult() {
        return this.loginresult;
    }
    
    public void setLoginresult(Integer loginresult) {
        this.loginresult = loginresult;
    }
@Temporal(TemporalType.DATE)
    @Column(name="LOGINTIME", length=7)

    public Date getLogintime() {
        return this.logintime;
    }
    
    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SysloginlogId) ) return false;
		 SysloginlogId castOther = ( SysloginlogId ) other; 
         
		 return ( (this.getLoginid()==castOther.getLoginid()) || ( this.getLoginid()!=null && castOther.getLoginid()!=null && this.getLoginid().equals(castOther.getLoginid()) ) )
 && ( (this.getLoginuser()==castOther.getLoginuser()) || ( this.getLoginuser()!=null && castOther.getLoginuser()!=null && this.getLoginuser().equals(castOther.getLoginuser()) ) )
 && ( (this.getLoginip()==castOther.getLoginip()) || ( this.getLoginip()!=null && castOther.getLoginip()!=null && this.getLoginip().equals(castOther.getLoginip()) ) )
 && ( (this.getLoginbrowse()==castOther.getLoginbrowse()) || ( this.getLoginbrowse()!=null && castOther.getLoginbrowse()!=null && this.getLoginbrowse().equals(castOther.getLoginbrowse()) ) )
 && ( (this.getLoginbrowsever()==castOther.getLoginbrowsever()) || ( this.getLoginbrowsever()!=null && castOther.getLoginbrowsever()!=null && this.getLoginbrowsever().equals(castOther.getLoginbrowsever()) ) )
 && ( (this.getLoginos()==castOther.getLoginos()) || ( this.getLoginos()!=null && castOther.getLoginos()!=null && this.getLoginos().equals(castOther.getLoginos()) ) )
 && ( (this.getLoginresult()==castOther.getLoginresult()) || ( this.getLoginresult()!=null && castOther.getLoginresult()!=null && this.getLoginresult().equals(castOther.getLoginresult()) ) )
 && ( (this.getLogintime()==castOther.getLogintime()) || ( this.getLogintime()!=null && castOther.getLogintime()!=null && this.getLogintime().equals(castOther.getLogintime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLoginid() == null ? 0 : this.getLoginid().hashCode() );
         result = 37 * result + ( getLoginuser() == null ? 0 : this.getLoginuser().hashCode() );
         result = 37 * result + ( getLoginip() == null ? 0 : this.getLoginip().hashCode() );
         result = 37 * result + ( getLoginbrowse() == null ? 0 : this.getLoginbrowse().hashCode() );
         result = 37 * result + ( getLoginbrowsever() == null ? 0 : this.getLoginbrowsever().hashCode() );
         result = 37 * result + ( getLoginos() == null ? 0 : this.getLoginos().hashCode() );
         result = 37 * result + ( getLoginresult() == null ? 0 : this.getLoginresult().hashCode() );
         result = 37 * result + ( getLogintime() == null ? 0 : this.getLogintime().hashCode() );
         return result;
   }   





}