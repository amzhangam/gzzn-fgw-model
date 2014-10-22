package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Roleactions entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ROLEACTIONS"
    
)

public class Roleactions  implements java.io.Serializable {


    // Fields    

     private RoleactionsId id;


    // Constructors

    /** default constructor */
    public Roleactions() {
    }

    
    /** full constructor */
    public Roleactions(RoleactionsId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="roleactionsid", column=@Column(name="ROLEACTIONSID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="roleid", column=@Column(name="ROLEID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="actionid", column=@Column(name="ACTIONID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="recorderid", column=@Column(name="RECORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="recordername", column=@Column(name="RECORDERNAME", length=50) ), 
        @AttributeOverride(name="recordertime", column=@Column(name="RECORDERTIME", length=7) ) } )

    public RoleactionsId getId() {
        return this.id;
    }
    
    public void setId(RoleactionsId id) {
        this.id = id;
    }
   








}