package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Workunits entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="WORKUNITS"
    
)

public class Workunits  implements java.io.Serializable {


    // Fields    

     private WorkunitsId id;


    // Constructors

    /** default constructor */
    public Workunits() {
    }

    
    /** full constructor */
    public Workunits(WorkunitsId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="workunitsid", column=@Column(name="WORKUNITSID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="workunitsname", column=@Column(name="WORKUNITSNAME", nullable=false) ), 
        @AttributeOverride(name="workunitstype", column=@Column(name="WORKUNITSTYPE", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="workunitsaddress", column=@Column(name="WORKUNITSADDRESS") ), 
        @AttributeOverride(name="workunitsregistercode", column=@Column(name="WORKUNITSREGISTERCODE", length=40) ), 
        @AttributeOverride(name="workunitsquality", column=@Column(name="WORKUNITSQUALITY", length=50) ), 
        @AttributeOverride(name="workunitsarea", column=@Column(name="WORKUNITSAREA") ), 
        @AttributeOverride(name="workunitsperson", column=@Column(name="WORKUNITSPERSON", length=50) ), 
        @AttributeOverride(name="workunitslinkman", column=@Column(name="WORKUNITSLINKMAN", length=50) ), 
        @AttributeOverride(name="workunitslinkmantel", column=@Column(name="WORKUNITSLINKMANTEL", length=50) ), 
        @AttributeOverride(name="workunitsstatus", column=@Column(name="WORKUNITSSTATUS", nullable=false, precision=22, scale=0) ) } )

    public WorkunitsId getId() {
        return this.id;
    }
    
    public void setId(WorkunitsId id) {
        this.id = id;
    }
   








}