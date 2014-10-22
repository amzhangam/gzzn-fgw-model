package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Userroles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="USERROLES"
    
)

public class Userroles  implements java.io.Serializable {


    // Fields    

     private UserrolesId id;


    // Constructors

    /** default constructor */
    public Userroles() {
    }

    
    /** full constructor */
    public Userroles(UserrolesId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="roleid", column=@Column(name="ROLEID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="userid", column=@Column(name="USERID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="recorderid", column=@Column(name="RECORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="recordername", column=@Column(name="RECORDERNAME", length=50) ), 
        @AttributeOverride(name="recordertime", column=@Column(name="RECORDERTIME", length=7) ) } )

    public UserrolesId getId() {
        return this.id;
    }
    
    public void setId(UserrolesId id) {
        this.id = id;
    }
   








}