package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Padepts entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PADEPTS"
    
)

public class Padepts  implements java.io.Serializable {


    // Fields    

     private PadeptsId id;


    // Constructors

    /** default constructor */
    public Padepts() {
    }

    
    /** full constructor */
    public Padepts(PadeptsId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="deptid", column=@Column(name="DEPTID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="parentdeptid", column=@Column(name="PARENTDEPTID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="deptname", column=@Column(name="DEPTNAME", nullable=false) ), 
        @AttributeOverride(name="levelnumber", column=@Column(name="LEVELNUMBER", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="fullcode", column=@Column(name="FULLCODE", nullable=false, length=20) ), 
        @AttributeOverride(name="description", column=@Column(name="DESCRIPTION") ) } )

    public PadeptsId getId() {
        return this.id;
    }
    
    public void setId(PadeptsId id) {
        this.id = id;
    }
   








}