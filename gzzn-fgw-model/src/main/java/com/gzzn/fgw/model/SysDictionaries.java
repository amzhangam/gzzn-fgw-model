package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * SysDictionaries entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_DICTIONARIES"
    
)

public class SysDictionaries  implements java.io.Serializable {


    // Fields    

     private SysDictionariesId id;


    // Constructors

    /** default constructor */
    public SysDictionaries() {
    }

    
    /** full constructor */
    public SysDictionaries(SysDictionariesId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="name", column=@Column(name="NAME", nullable=false) ), 
        @AttributeOverride(name="description", column=@Column(name="DESCRIPTION") ) } )

    public SysDictionariesId getId() {
        return this.id;
    }
    
    public void setId(SysDictionariesId id) {
        this.id = id;
    }
   








}