package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Systodoinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYSTODOINFO"
    
)

public class Systodoinfo  implements java.io.Serializable {


    // Fields    

     private SystodoinfoId id;


    // Constructors

    /** default constructor */
    public Systodoinfo() {
    }

    
    /** full constructor */
    public Systodoinfo(SystodoinfoId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="todoinfoid", column=@Column(name="TODOINFOID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="todoinfoname", column=@Column(name="TODOINFONAME", nullable=false) ), 
        @AttributeOverride(name="todoinfotype", column=@Column(name="TODOINFOTYPE", nullable=false) ), 
        @AttributeOverride(name="redirecturl", column=@Column(name="REDIRECTURL", nullable=false) ), 
        @AttributeOverride(name="importancelevel", column=@Column(name="IMPORTANCELEVEL", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="userid", column=@Column(name="USERID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="status", column=@Column(name="STATUS", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="description", column=@Column(name="DESCRIPTION", length=500) ), 
        @AttributeOverride(name="processclass", column=@Column(name="PROCESSCLASS", length=200) ), 
        @AttributeOverride(name="recorderid", column=@Column(name="RECORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="recordername", column=@Column(name="RECORDERNAME", length=50) ), 
        @AttributeOverride(name="recordertime", column=@Column(name="RECORDERTIME", length=7) ) } )

    public SystodoinfoId getId() {
        return this.id;
    }
    
    public void setId(SystodoinfoId id) {
        this.id = id;
    }
   








}