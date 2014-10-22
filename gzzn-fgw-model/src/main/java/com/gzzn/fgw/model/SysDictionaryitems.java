package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * SysDictionaryitemsId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="sys_dictionaryitems"
    
)

public class SysDictionaryitems  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String itemvalue;
     private String itemtext;
     private String name;
     private Integer status;
     private Integer sortorder;
     private String description;
     private Integer minvalue;
     private Integer maxvalue;


    // Constructors

    /** default constructor */
    public SysDictionaryitems() {
    }

	/** minimal constructor */
    public SysDictionaryitems(Integer id, String itemvalue, String itemtext, String name, Integer status) {
        this.id = id;
        this.itemvalue = itemvalue;
        this.itemtext = itemtext;
        this.name = name;
        this.status = status;
    }
    
    /** full constructor */
    public SysDictionaryitems(Integer id, String itemvalue, String itemtext, String name, Integer status, Integer sortorder, String description, Integer minvalue, Integer maxvalue) {
        this.id = id;
        this.itemvalue = itemvalue;
        this.itemtext = itemtext;
        this.name = name;
        this.status = status;
        this.sortorder = sortorder;
        this.description = description;
        this.minvalue = minvalue;
        this.maxvalue = maxvalue;
    }

   
    // Property accessors

    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="ITEMVALUE", nullable=false)

    public String getItemvalue() {
        return this.itemvalue;
    }
    
    public void setItemvalue(String itemvalue) {
        this.itemvalue = itemvalue;
    }

    @Column(name="ITEMTEXT", nullable=false)

    public String getItemtext() {
        return this.itemtext;
    }
    
    public void setItemtext(String itemtext) {
        this.itemtext = itemtext;
    }

    @Column(name="NAME", nullable=false)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="STATUS", nullable=false, precision=22, scale=0)

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name="SORTORDER", precision=22, scale=0)

    public Integer getSortorder() {
        return this.sortorder;
    }
    
    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }

    @Column(name="DESCRIPTION")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="MINVALUE", precision=22, scale=0)

    public Integer getMinvalue() {
        return this.minvalue;
    }
    
    public void setMinvalue(Integer minvalue) {
        this.minvalue = minvalue;
    }

    @Column(name="MAXVALUE", precision=22, scale=0)

    public Integer getMaxvalue() {
        return this.maxvalue;
    }
    
    public void setMaxvalue(Integer maxvalue) {
        this.maxvalue = maxvalue;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SysDictionaryitems) ) return false;
		 SysDictionaryitems castOther = ( SysDictionaryitems ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getItemvalue()==castOther.getItemvalue()) || ( this.getItemvalue()!=null && castOther.getItemvalue()!=null && this.getItemvalue().equals(castOther.getItemvalue()) ) )
 && ( (this.getItemtext()==castOther.getItemtext()) || ( this.getItemtext()!=null && castOther.getItemtext()!=null && this.getItemtext().equals(castOther.getItemtext()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getSortorder()==castOther.getSortorder()) || ( this.getSortorder()!=null && castOther.getSortorder()!=null && this.getSortorder().equals(castOther.getSortorder()) ) )
 && ( (this.getDescription()==castOther.getDescription()) || ( this.getDescription()!=null && castOther.getDescription()!=null && this.getDescription().equals(castOther.getDescription()) ) )
 && ( (this.getMinvalue()==castOther.getMinvalue()) || ( this.getMinvalue()!=null && castOther.getMinvalue()!=null && this.getMinvalue().equals(castOther.getMinvalue()) ) )
 && ( (this.getMaxvalue()==castOther.getMaxvalue()) || ( this.getMaxvalue()!=null && castOther.getMaxvalue()!=null && this.getMaxvalue().equals(castOther.getMaxvalue()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getItemvalue() == null ? 0 : this.getItemvalue().hashCode() );
         result = 37 * result + ( getItemtext() == null ? 0 : this.getItemtext().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getSortorder() == null ? 0 : this.getSortorder().hashCode() );
         result = 37 * result + ( getDescription() == null ? 0 : this.getDescription().hashCode() );
         result = 37 * result + ( getMinvalue() == null ? 0 : this.getMinvalue().hashCode() );
         result = 37 * result + ( getMaxvalue() == null ? 0 : this.getMaxvalue().hashCode() );
         return result;
   }   





}