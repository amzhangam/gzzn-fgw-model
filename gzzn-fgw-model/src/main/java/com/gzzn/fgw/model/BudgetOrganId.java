package com.gzzn.fgw.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * BudgetOrganId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class BudgetOrganId  implements java.io.Serializable {


    // Fields    

     private String organCode;
     private String organName;
     private String parentOrganCode;
     private String parentOrganName;
     private String organStatus;
     private Integer organizationId;
     private String organizationName;


    // Constructors

    /** default constructor */
    public BudgetOrganId() {
    }

    
    /** full constructor */
    public BudgetOrganId(String organCode, String organName, String parentOrganCode, String parentOrganName, String organStatus, Integer organizationId, String organizationName) {
        this.organCode = organCode;
        this.organName = organName;
        this.parentOrganCode = parentOrganCode;
        this.parentOrganName = parentOrganName;
        this.organStatus = organStatus;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

   
    // Property accessors

    @Column(name="ORGAN_CODE", length=10)

    public String getOrganCode() {
        return this.organCode;
    }
    
    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    @Column(name="ORGAN_NAME", length=150)

    public String getOrganName() {
        return this.organName;
    }
    
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    @Column(name="PARENT_ORGAN_CODE", length=10)

    public String getParentOrganCode() {
        return this.parentOrganCode;
    }
    
    public void setParentOrganCode(String parentOrganCode) {
        this.parentOrganCode = parentOrganCode;
    }

    @Column(name="PARENT_ORGAN_NAME", length=150)

    public String getParentOrganName() {
        return this.parentOrganName;
    }
    
    public void setParentOrganName(String parentOrganName) {
        this.parentOrganName = parentOrganName;
    }

    @Column(name="ORGAN_STATUS", length=10)

    public String getOrganStatus() {
        return this.organStatus;
    }
    
    public void setOrganStatus(String organStatus) {
        this.organStatus = organStatus;
    }

    @Column(name="ORGANIZATION_ID", precision=22, scale=0)

    public Integer getOrganizationId() {
        return this.organizationId;
    }
    
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    @Column(name="ORGANIZATION_NAME", length=150)

    public String getOrganizationName() {
        return this.organizationName;
    }
    
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BudgetOrganId) ) return false;
		 BudgetOrganId castOther = ( BudgetOrganId ) other; 
         
		 return ( (this.getOrganCode()==castOther.getOrganCode()) || ( this.getOrganCode()!=null && castOther.getOrganCode()!=null && this.getOrganCode().equals(castOther.getOrganCode()) ) )
 && ( (this.getOrganName()==castOther.getOrganName()) || ( this.getOrganName()!=null && castOther.getOrganName()!=null && this.getOrganName().equals(castOther.getOrganName()) ) )
 && ( (this.getParentOrganCode()==castOther.getParentOrganCode()) || ( this.getParentOrganCode()!=null && castOther.getParentOrganCode()!=null && this.getParentOrganCode().equals(castOther.getParentOrganCode()) ) )
 && ( (this.getParentOrganName()==castOther.getParentOrganName()) || ( this.getParentOrganName()!=null && castOther.getParentOrganName()!=null && this.getParentOrganName().equals(castOther.getParentOrganName()) ) )
 && ( (this.getOrganStatus()==castOther.getOrganStatus()) || ( this.getOrganStatus()!=null && castOther.getOrganStatus()!=null && this.getOrganStatus().equals(castOther.getOrganStatus()) ) )
 && ( (this.getOrganizationId()==castOther.getOrganizationId()) || ( this.getOrganizationId()!=null && castOther.getOrganizationId()!=null && this.getOrganizationId().equals(castOther.getOrganizationId()) ) )
 && ( (this.getOrganizationName()==castOther.getOrganizationName()) || ( this.getOrganizationName()!=null && castOther.getOrganizationName()!=null && this.getOrganizationName().equals(castOther.getOrganizationName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getOrganCode() == null ? 0 : this.getOrganCode().hashCode() );
         result = 37 * result + ( getOrganName() == null ? 0 : this.getOrganName().hashCode() );
         result = 37 * result + ( getParentOrganCode() == null ? 0 : this.getParentOrganCode().hashCode() );
         result = 37 * result + ( getParentOrganName() == null ? 0 : this.getParentOrganName().hashCode() );
         result = 37 * result + ( getOrganStatus() == null ? 0 : this.getOrganStatus().hashCode() );
         result = 37 * result + ( getOrganizationId() == null ? 0 : this.getOrganizationId().hashCode() );
         result = 37 * result + ( getOrganizationName() == null ? 0 : this.getOrganizationName().hashCode() );
         return result;
   }   





}