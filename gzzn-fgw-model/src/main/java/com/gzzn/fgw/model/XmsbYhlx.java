package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * XmsbYhlx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XMSB_YHLX"
    
)

public class XmsbYhlx  implements java.io.Serializable {


    // Fields    

     private XmsbYhlxId id;


    // Constructors

    /** default constructor */
    public XmsbYhlx() {
    }

    
    /** full constructor */
    public XmsbYhlx(XmsbYhlxId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="xmlxId", column=@Column(name="XMLX_ID", nullable=false, precision=22, scale=0) ), 
        @AttributeOverride(name="xmlxmc", column=@Column(name="XMLXMC", length=50) ), 
        @AttributeOverride(name="xmlxdm", column=@Column(name="XMLXDM", length=50) ), 
        @AttributeOverride(name="deleted", column=@Column(name="DELETED", precision=22, scale=0) ), 
        @AttributeOverride(name="sjxmlxId", column=@Column(name="SJXMLX_ID", precision=22, scale=0) ) } )

    public XmsbYhlxId getId() {
        return this.id;
    }
    
    public void setId(XmsbYhlxId id) {
        this.id = id;
    }
   








}