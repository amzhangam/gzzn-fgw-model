package com.gzzn.fgw.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * SysDictionariesId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class SysDictionariesId  implements java.io.Serializable {


    // Fields    

     private String name;
     private String description;


    // Constructors

    /** default constructor */
    public SysDictionariesId() {
    }

	/** minimal constructor */
    public SysDictionariesId(String name) {
        this.name = name;
    }
    
    /** full constructor */
    public SysDictionariesId(String name, String description) {
        this.name = name;
        this.description = description;
    }

   
    // Property accessors

    @Column(name="NAME", nullable=false)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="DESCRIPTION")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SysDictionariesId) ) return false;
		 SysDictionariesId castOther = ( SysDictionariesId ) other; 
         
		 return ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getDescription()==castOther.getDescription()) || ( this.getDescription()!=null && castOther.getDescription()!=null && this.getDescription().equals(castOther.getDescription()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getDescription() == null ? 0 : this.getDescription().hashCode() );
         return result;
   }   





}