package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * WorkunitsId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class WorkunitsId  implements java.io.Serializable {


    // Fields    

     private Integer workunitsid;
     private String workunitsname;
     private Integer workunitstype;
     private String workunitsaddress;
     private String workunitsregistercode;
     private String workunitsquality;
     private String workunitsarea;
     private String workunitsperson;
     private String workunitslinkman;
     private String workunitslinkmantel;
     private Integer workunitsstatus;


    // Constructors

    /** default constructor */
    public WorkunitsId() {
    }

	/** minimal constructor */
    public WorkunitsId(Integer workunitsid, String workunitsname, Integer workunitstype, Integer workunitsstatus) {
        this.workunitsid = workunitsid;
        this.workunitsname = workunitsname;
        this.workunitstype = workunitstype;
        this.workunitsstatus = workunitsstatus;
    }
    
    /** full constructor */
    public WorkunitsId(Integer workunitsid, String workunitsname, Integer workunitstype, String workunitsaddress, String workunitsregistercode, String workunitsquality, String workunitsarea, String workunitsperson, String workunitslinkman, String workunitslinkmantel, Integer workunitsstatus) {
        this.workunitsid = workunitsid;
        this.workunitsname = workunitsname;
        this.workunitstype = workunitstype;
        this.workunitsaddress = workunitsaddress;
        this.workunitsregistercode = workunitsregistercode;
        this.workunitsquality = workunitsquality;
        this.workunitsarea = workunitsarea;
        this.workunitsperson = workunitsperson;
        this.workunitslinkman = workunitslinkman;
        this.workunitslinkmantel = workunitslinkmantel;
        this.workunitsstatus = workunitsstatus;
    }

   
    // Property accessors

    @Column(name="WORKUNITSID", nullable=false, precision=22, scale=0)

    public Integer getWorkunitsid() {
        return this.workunitsid;
    }
    
    public void setWorkunitsid(Integer workunitsid) {
        this.workunitsid = workunitsid;
    }

    @Column(name="WORKUNITSNAME", nullable=false)

    public String getWorkunitsname() {
        return this.workunitsname;
    }
    
    public void setWorkunitsname(String workunitsname) {
        this.workunitsname = workunitsname;
    }

    @Column(name="WORKUNITSTYPE", nullable=false, precision=22, scale=0)

    public Integer getWorkunitstype() {
        return this.workunitstype;
    }
    
    public void setWorkunitstype(Integer workunitstype) {
        this.workunitstype = workunitstype;
    }

    @Column(name="WORKUNITSADDRESS")

    public String getWorkunitsaddress() {
        return this.workunitsaddress;
    }
    
    public void setWorkunitsaddress(String workunitsaddress) {
        this.workunitsaddress = workunitsaddress;
    }

    @Column(name="WORKUNITSREGISTERCODE", length=40)

    public String getWorkunitsregistercode() {
        return this.workunitsregistercode;
    }
    
    public void setWorkunitsregistercode(String workunitsregistercode) {
        this.workunitsregistercode = workunitsregistercode;
    }

    @Column(name="WORKUNITSQUALITY", length=50)

    public String getWorkunitsquality() {
        return this.workunitsquality;
    }
    
    public void setWorkunitsquality(String workunitsquality) {
        this.workunitsquality = workunitsquality;
    }

    @Column(name="WORKUNITSAREA")

    public String getWorkunitsarea() {
        return this.workunitsarea;
    }
    
    public void setWorkunitsarea(String workunitsarea) {
        this.workunitsarea = workunitsarea;
    }

    @Column(name="WORKUNITSPERSON", length=50)

    public String getWorkunitsperson() {
        return this.workunitsperson;
    }
    
    public void setWorkunitsperson(String workunitsperson) {
        this.workunitsperson = workunitsperson;
    }

    @Column(name="WORKUNITSLINKMAN", length=50)

    public String getWorkunitslinkman() {
        return this.workunitslinkman;
    }
    
    public void setWorkunitslinkman(String workunitslinkman) {
        this.workunitslinkman = workunitslinkman;
    }

    @Column(name="WORKUNITSLINKMANTEL", length=50)

    public String getWorkunitslinkmantel() {
        return this.workunitslinkmantel;
    }
    
    public void setWorkunitslinkmantel(String workunitslinkmantel) {
        this.workunitslinkmantel = workunitslinkmantel;
    }

    @Column(name="WORKUNITSSTATUS", nullable=false, precision=22, scale=0)

    public Integer getWorkunitsstatus() {
        return this.workunitsstatus;
    }
    
    public void setWorkunitsstatus(Integer workunitsstatus) {
        this.workunitsstatus = workunitsstatus;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof WorkunitsId) ) return false;
		 WorkunitsId castOther = ( WorkunitsId ) other; 
         
		 return ( (this.getWorkunitsid()==castOther.getWorkunitsid()) || ( this.getWorkunitsid()!=null && castOther.getWorkunitsid()!=null && this.getWorkunitsid().equals(castOther.getWorkunitsid()) ) )
 && ( (this.getWorkunitsname()==castOther.getWorkunitsname()) || ( this.getWorkunitsname()!=null && castOther.getWorkunitsname()!=null && this.getWorkunitsname().equals(castOther.getWorkunitsname()) ) )
 && ( (this.getWorkunitstype()==castOther.getWorkunitstype()) || ( this.getWorkunitstype()!=null && castOther.getWorkunitstype()!=null && this.getWorkunitstype().equals(castOther.getWorkunitstype()) ) )
 && ( (this.getWorkunitsaddress()==castOther.getWorkunitsaddress()) || ( this.getWorkunitsaddress()!=null && castOther.getWorkunitsaddress()!=null && this.getWorkunitsaddress().equals(castOther.getWorkunitsaddress()) ) )
 && ( (this.getWorkunitsregistercode()==castOther.getWorkunitsregistercode()) || ( this.getWorkunitsregistercode()!=null && castOther.getWorkunitsregistercode()!=null && this.getWorkunitsregistercode().equals(castOther.getWorkunitsregistercode()) ) )
 && ( (this.getWorkunitsquality()==castOther.getWorkunitsquality()) || ( this.getWorkunitsquality()!=null && castOther.getWorkunitsquality()!=null && this.getWorkunitsquality().equals(castOther.getWorkunitsquality()) ) )
 && ( (this.getWorkunitsarea()==castOther.getWorkunitsarea()) || ( this.getWorkunitsarea()!=null && castOther.getWorkunitsarea()!=null && this.getWorkunitsarea().equals(castOther.getWorkunitsarea()) ) )
 && ( (this.getWorkunitsperson()==castOther.getWorkunitsperson()) || ( this.getWorkunitsperson()!=null && castOther.getWorkunitsperson()!=null && this.getWorkunitsperson().equals(castOther.getWorkunitsperson()) ) )
 && ( (this.getWorkunitslinkman()==castOther.getWorkunitslinkman()) || ( this.getWorkunitslinkman()!=null && castOther.getWorkunitslinkman()!=null && this.getWorkunitslinkman().equals(castOther.getWorkunitslinkman()) ) )
 && ( (this.getWorkunitslinkmantel()==castOther.getWorkunitslinkmantel()) || ( this.getWorkunitslinkmantel()!=null && castOther.getWorkunitslinkmantel()!=null && this.getWorkunitslinkmantel().equals(castOther.getWorkunitslinkmantel()) ) )
 && ( (this.getWorkunitsstatus()==castOther.getWorkunitsstatus()) || ( this.getWorkunitsstatus()!=null && castOther.getWorkunitsstatus()!=null && this.getWorkunitsstatus().equals(castOther.getWorkunitsstatus()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getWorkunitsid() == null ? 0 : this.getWorkunitsid().hashCode() );
         result = 37 * result + ( getWorkunitsname() == null ? 0 : this.getWorkunitsname().hashCode() );
         result = 37 * result + ( getWorkunitstype() == null ? 0 : this.getWorkunitstype().hashCode() );
         result = 37 * result + ( getWorkunitsaddress() == null ? 0 : this.getWorkunitsaddress().hashCode() );
         result = 37 * result + ( getWorkunitsregistercode() == null ? 0 : this.getWorkunitsregistercode().hashCode() );
         result = 37 * result + ( getWorkunitsquality() == null ? 0 : this.getWorkunitsquality().hashCode() );
         result = 37 * result + ( getWorkunitsarea() == null ? 0 : this.getWorkunitsarea().hashCode() );
         result = 37 * result + ( getWorkunitsperson() == null ? 0 : this.getWorkunitsperson().hashCode() );
         result = 37 * result + ( getWorkunitslinkman() == null ? 0 : this.getWorkunitslinkman().hashCode() );
         result = 37 * result + ( getWorkunitslinkmantel() == null ? 0 : this.getWorkunitslinkmantel().hashCode() );
         result = 37 * result + ( getWorkunitsstatus() == null ? 0 : this.getWorkunitsstatus().hashCode() );
         return result;
   }   





}