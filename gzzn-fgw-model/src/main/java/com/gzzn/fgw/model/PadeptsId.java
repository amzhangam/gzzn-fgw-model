package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * PadeptsId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class PadeptsId  implements java.io.Serializable {


    // Fields    

     private Integer deptid;
     private Integer parentdeptid;
     private String deptname;
     private Integer levelnumber;
     private String fullcode;
     private String description;


    // Constructors

    /** default constructor */
    public PadeptsId() {
    }

	/** minimal constructor */
    public PadeptsId(Integer deptid, Integer parentdeptid, String deptname, Integer levelnumber, String fullcode) {
        this.deptid = deptid;
        this.parentdeptid = parentdeptid;
        this.deptname = deptname;
        this.levelnumber = levelnumber;
        this.fullcode = fullcode;
    }
    
    /** full constructor */
    public PadeptsId(Integer deptid, Integer parentdeptid, String deptname, Integer levelnumber, String fullcode, String description) {
        this.deptid = deptid;
        this.parentdeptid = parentdeptid;
        this.deptname = deptname;
        this.levelnumber = levelnumber;
        this.fullcode = fullcode;
        this.description = description;
    }

   
    // Property accessors

    @Column(name="DEPTID", nullable=false, precision=22, scale=0)

    public Integer getDeptid() {
        return this.deptid;
    }
    
    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Column(name="PARENTDEPTID", nullable=false, precision=22, scale=0)

    public Integer getParentdeptid() {
        return this.parentdeptid;
    }
    
    public void setParentdeptid(Integer parentdeptid) {
        this.parentdeptid = parentdeptid;
    }

    @Column(name="DEPTNAME", nullable=false)

    public String getDeptname() {
        return this.deptname;
    }
    
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Column(name="LEVELNUMBER", nullable=false, precision=22, scale=0)

    public Integer getLevelnumber() {
        return this.levelnumber;
    }
    
    public void setLevelnumber(Integer levelnumber) {
        this.levelnumber = levelnumber;
    }

    @Column(name="FULLCODE", nullable=false, length=20)

    public String getFullcode() {
        return this.fullcode;
    }
    
    public void setFullcode(String fullcode) {
        this.fullcode = fullcode;
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
		 if ( !(other instanceof PadeptsId) ) return false;
		 PadeptsId castOther = ( PadeptsId ) other; 
         
		 return ( (this.getDeptid()==castOther.getDeptid()) || ( this.getDeptid()!=null && castOther.getDeptid()!=null && this.getDeptid().equals(castOther.getDeptid()) ) )
 && ( (this.getParentdeptid()==castOther.getParentdeptid()) || ( this.getParentdeptid()!=null && castOther.getParentdeptid()!=null && this.getParentdeptid().equals(castOther.getParentdeptid()) ) )
 && ( (this.getDeptname()==castOther.getDeptname()) || ( this.getDeptname()!=null && castOther.getDeptname()!=null && this.getDeptname().equals(castOther.getDeptname()) ) )
 && ( (this.getLevelnumber()==castOther.getLevelnumber()) || ( this.getLevelnumber()!=null && castOther.getLevelnumber()!=null && this.getLevelnumber().equals(castOther.getLevelnumber()) ) )
 && ( (this.getFullcode()==castOther.getFullcode()) || ( this.getFullcode()!=null && castOther.getFullcode()!=null && this.getFullcode().equals(castOther.getFullcode()) ) )
 && ( (this.getDescription()==castOther.getDescription()) || ( this.getDescription()!=null && castOther.getDescription()!=null && this.getDescription().equals(castOther.getDescription()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDeptid() == null ? 0 : this.getDeptid().hashCode() );
         result = 37 * result + ( getParentdeptid() == null ? 0 : this.getParentdeptid().hashCode() );
         result = 37 * result + ( getDeptname() == null ? 0 : this.getDeptname().hashCode() );
         result = 37 * result + ( getLevelnumber() == null ? 0 : this.getLevelnumber().hashCode() );
         result = 37 * result + ( getFullcode() == null ? 0 : this.getFullcode().hashCode() );
         result = 37 * result + ( getDescription() == null ? 0 : this.getDescription().hashCode() );
         return result;
   }   





}