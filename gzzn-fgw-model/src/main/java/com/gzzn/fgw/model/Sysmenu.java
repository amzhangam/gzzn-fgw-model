package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Sysmenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYSMENU"
    
)

public class Sysmenu  implements java.io.Serializable {


    // Fields    

     private SysmenuId id;


    // Constructors

    /** default constructor */
    public Sysmenu() {
    }

    
    /** full constructor */
    public Sysmenu(SysmenuId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="systemmenuid", column=@Column(name="SYSTEMMENUID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="systemmenuname", column=@Column(name="SYSTEMMENUNAME", nullable=false) ), 
        @AttributeOverride(name="redirecturl", column=@Column(name="REDIRECTURL", nullable=false) ), 
        @AttributeOverride(name="systemmenulevel", column=@Column(name="SYSTEMMENULEVEL", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="importancelevel", column=@Column(name="IMPORTANCELEVEL", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="enabled", column=@Column(name="ENABLED", nullable=false, length=1) ), 
        @AttributeOverride(name="actionid", column=@Column(name="ACTIONID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="parentsystemmenuid", column=@Column(name="PARENTSYSTEMMENUID", precision=22, scale=0) ), 
        @AttributeOverride(name="description", column=@Column(name="DESCRIPTION", length=200) ), 
        @AttributeOverride(name="keywords", column=@Column(name="KEYWORDS", length=100) ), 
        @AttributeOverride(name="cssclassname", column=@Column(name="CSSCLASSNAME", length=50) ), 
        @AttributeOverride(name="value1", column=@Column(name="VALUE1", length=100) ), 
        @AttributeOverride(name="value2", column=@Column(name="VALUE2", length=100) ), 
        @AttributeOverride(name="recorderid", column=@Column(name="RECORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="recordername", column=@Column(name="RECORDERNAME", length=50) ), 
        @AttributeOverride(name="recordertime", column=@Column(name="RECORDERTIME", length=7) ) } )

    public SysmenuId getId() {
        return this.id;
    }
    
    public void setId(SysmenuId id) {
        this.id = id;
    }
   








}