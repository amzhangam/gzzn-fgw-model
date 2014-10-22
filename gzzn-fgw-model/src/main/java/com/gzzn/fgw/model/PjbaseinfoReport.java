package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * PjbaseinfoReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PJBASEINFO_REPORT"
    
)

public class PjbaseinfoReport  implements java.io.Serializable {


    // Fields    

     private PjbaseinfoReportId id;


    // Constructors

    /** default constructor */
    public PjbaseinfoReport() {
    }

    
    /** full constructor */
    public PjbaseinfoReport(PjbaseinfoReportId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="projecttype", column=@Column(name="PROJECTTYPE", precision=22, scale=0) ), 
        @AttributeOverride(name="projectname", column=@Column(name="PROJECTNAME") ), 
        @AttributeOverride(name="startendyear", column=@Column(name="STARTENDYEAR") ), 
        @AttributeOverride(name="pjinvestsum", column=@Column(name="PJINVESTSUM", precision=18) ), 
        @AttributeOverride(name="pjinvestcity", column=@Column(name="PJINVESTCITY", precision=18) ), 
        @AttributeOverride(name="pjinvestcompany", column=@Column(name="PJINVESTCOMPANY", precision=18) ), 
        @AttributeOverride(name="pjinvestbank", column=@Column(name="PJINVESTBANK", precision=18) ), 
        @AttributeOverride(name="pjinvestothers", column=@Column(name="PJINVESTOTHERS", precision=18) ), 
        @AttributeOverride(name="expectfinishinvest", column=@Column(name="EXPECTFINISHINVEST", precision=18) ), 
        @AttributeOverride(name="expectfinishotherinvest", column=@Column(name="EXPECTFINISHOTHERINVEST", precision=18) ), 
        @AttributeOverride(name="planinvestsum", column=@Column(name="PLANINVESTSUM", precision=18) ), 
        @AttributeOverride(name="planinvestcity", column=@Column(name="PLANINVESTCITY", precision=18) ), 
        @AttributeOverride(name="planinvestcompany", column=@Column(name="PLANINVESTCOMPANY", precision=18) ), 
        @AttributeOverride(name="planinvestbank", column=@Column(name="PLANINVESTBANK", precision=18) ), 
        @AttributeOverride(name="planinvestothers", column=@Column(name="PLANINVESTOTHERS", precision=18) ), 
        @AttributeOverride(name="projectcontent", column=@Column(name="PROJECTCONTENT") ), 
        @AttributeOverride(name="pjinvestadvice", column=@Column(name="PJINVESTADVICE") ), 
        @AttributeOverride(name="declaregist", column=@Column(name="DECLAREGIST") ), 
        @AttributeOverride(name="directorunitsid", column=@Column(name="DIRECTORUNITSID") ), 
        @AttributeOverride(name="manageunitsname", column=@Column(name="MANAGEUNITSNAME") ), 
        @AttributeOverride(name="memo", column=@Column(name="MEMO") ), 
        @AttributeOverride(name="reporttimes", column=@Column(name="REPORTTIMES") ), 
        @AttributeOverride(name="projectid", column=@Column(name="PROJECTID", precision=22, scale=0) ), 
        @AttributeOverride(name="orderid", column=@Column(name="ORDERID", precision=22, scale=0) ), 
        @AttributeOverride(name="totalproject", column=@Column(name="TOTALPROJECT", precision=22, scale=0) ) } )

    public PjbaseinfoReportId getId() {
        return this.id;
    }
    
    public void setId(PjbaseinfoReportId id) {
        this.id = id;
    }
   








}