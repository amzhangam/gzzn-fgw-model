package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Roles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ROLES"
    
)

public class Roles  implements java.io.Serializable {


    // Fields    

     private RolesId id;


    // Constructors

    /** default constructor */
    public Roles() {
    }

    
    /** full constructor */
    public Roles(RolesId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="roleid", column=@Column(name="ROLEID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="rolename", column=@Column(name="ROLENAME", nullable=false, length=50) ), 
        @AttributeOverride(name="description", column=@Column(name="DESCRIPTION") ), 
        @AttributeOverride(name="issensitive", column=@Column(name="ISSENSITIVE", precision=22, scale=0) ), 
        @AttributeOverride(name="recorderid", column=@Column(name="RECORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="recordername", column=@Column(name="RECORDERNAME", length=50) ), 
        @AttributeOverride(name="recordertime", column=@Column(name="RECORDERTIME", length=7) ) } )

    public RolesId getId() {
        return this.id;
    }
    
    public void setId(RolesId id) {
        this.id = id;
    }
   








}