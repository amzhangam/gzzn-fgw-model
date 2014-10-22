package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Syscustomermenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYSCUSTOMERMENU"
    
)

public class Syscustomermenu  implements java.io.Serializable {


    // Fields    

     private SyscustomermenuId id;


    // Constructors

    /** default constructor */
    public Syscustomermenu() {
    }

    
    /** full constructor */
    public Syscustomermenu(SyscustomermenuId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="syscustomermenuid", column=@Column(name="SYSCUSTOMERMENUID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="systemmenuid", column=@Column(name="SYSTEMMENUID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="userid", column=@Column(name="USERID", nullable=false, length=50) ), 
        @AttributeOverride(name="issystemdefault", column=@Column(name="ISSYSTEMDEFAULT", nullable=false, length=1) ), 
        @AttributeOverride(name="status", column=@Column(name="STATUS", nullable=false, length=1) ), 
        @AttributeOverride(name="importancelevel", column=@Column(name="IMPORTANCELEVEL", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="cssclassname", column=@Column(name="CSSCLASSNAME", length=50) ), 
        @AttributeOverride(name="value1", column=@Column(name="VALUE1", length=100) ), 
        @AttributeOverride(name="value2", column=@Column(name="VALUE2", length=200) ), 
        @AttributeOverride(name="recorderid", column=@Column(name="RECORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="recordername", column=@Column(name="RECORDERNAME", length=50) ), 
        @AttributeOverride(name="recordertime", column=@Column(name="RECORDERTIME", length=7) ) } )

    public SyscustomermenuId getId() {
        return this.id;
    }
    
    public void setId(SyscustomermenuId id) {
        this.id = id;
    }
   








}