package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Sysloginlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYSLOGINLOG"
    
)

public class Sysloginlog  implements java.io.Serializable {


    // Fields    

     private SysloginlogId id;


    // Constructors

    /** default constructor */
    public Sysloginlog() {
    }

    
    /** full constructor */
    public Sysloginlog(SysloginlogId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="loginid", column=@Column(name="LOGINID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="loginuser", column=@Column(name="LOGINUSER") ), 
        @AttributeOverride(name="loginip", column=@Column(name="LOGINIP") ), 
        @AttributeOverride(name="loginbrowse", column=@Column(name="LOGINBROWSE") ), 
        @AttributeOverride(name="loginbrowsever", column=@Column(name="LOGINBROWSEVER") ), 
        @AttributeOverride(name="loginos", column=@Column(name="LOGINOS") ), 
        @AttributeOverride(name="loginresult", column=@Column(name="LOGINRESULT", precision=22, scale=0) ), 
        @AttributeOverride(name="logintime", column=@Column(name="LOGINTIME", length=7) ) } )

    public SysloginlogId getId() {
        return this.id;
    }
    
    public void setId(SysloginlogId id) {
        this.id = id;
    }
   








}