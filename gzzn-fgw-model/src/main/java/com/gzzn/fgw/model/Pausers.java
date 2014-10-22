package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Pausers entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PAUSERS"
    
)

public class Pausers  implements java.io.Serializable {


    // Fields    

     private PausersId id;


    // Constructors

    /** default constructor */
    public Pausers() {
    }

    
    /** full constructor */
    public Pausers(PausersId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="userid", column=@Column(name="USERID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="workunitsid", column=@Column(name="WORKUNITSID", precision=22, scale=0) ), 
        @AttributeOverride(name="usertype", column=@Column(name="USERTYPE", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="userdept", column=@Column(name="USERDEPT", precision=22, scale=0) ), 
        @AttributeOverride(name="userduty", column=@Column(name="USERDUTY") ), 
        @AttributeOverride(name="userrole", column=@Column(name="USERROLE", precision=22, scale=0) ), 
        @AttributeOverride(name="loginid", column=@Column(name="LOGINID", nullable=false, length=50) ), 
        @AttributeOverride(name="username", column=@Column(name="USERNAME", nullable=false) ), 
        @AttributeOverride(name="upassword", column=@Column(name="UPASSWORD", nullable=false, length=50) ), 
        @AttributeOverride(name="callphone", column=@Column(name="CALLPHONE", length=50) ), 
        @AttributeOverride(name="status", column=@Column(name="STATUS", precision=22, scale=0) ), 
        @AttributeOverride(name="recorderid", column=@Column(name="RECORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="recordername", column=@Column(name="RECORDERNAME", length=50) ), 
        @AttributeOverride(name="recordertime", column=@Column(name="RECORDERTIME", length=7) ) } )

    public PausersId getId() {
        return this.id;
    }
    
    public void setId(PausersId id) {
        this.id = id;
    }
   








}