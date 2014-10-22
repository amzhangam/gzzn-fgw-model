package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * BudgetOrgan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BUDGET_ORGAN"
)

public class BudgetOrgan  implements java.io.Serializable {


    // Fields    

     private BudgetOrganId id;
     private SysOrganization sysOrganization;


    // Constructors

    /** default constructor */
    public BudgetOrgan() {
    }

	/** minimal constructor */
    public BudgetOrgan(BudgetOrganId id) {
        this.id = id;
    }
    
    /** full constructor */
    public BudgetOrgan(BudgetOrganId id, SysOrganization sysOrganization) {
        this.id = id;
        this.sysOrganization = sysOrganization;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="organCode", column=@Column(name="ORGAN_CODE", length=10) ), 
        @AttributeOverride(name="organName", column=@Column(name="ORGAN_NAME", length=150) ), 
        @AttributeOverride(name="parentOrganCode", column=@Column(name="PARENT_ORGAN_CODE", length=10) ), 
        @AttributeOverride(name="parentOrganName", column=@Column(name="PARENT_ORGAN_NAME", length=150) ), 
        @AttributeOverride(name="organStatus", column=@Column(name="ORGAN_STATUS", length=10) ), 
        @AttributeOverride(name="organizationId", column=@Column(name="ORGANIZATION_ID", precision=22, scale=0) ), 
        @AttributeOverride(name="organizationName", column=@Column(name="ORGANIZATION_NAME", length=150) ) } )

    public BudgetOrganId getId() {
        return this.id;
    }
    
    public void setId(BudgetOrganId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ORGANIZATION_ID", insertable=false, updatable=false)

    public SysOrganization getSysOrganization() {
        return this.sysOrganization;
    }
    
    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }
   








}