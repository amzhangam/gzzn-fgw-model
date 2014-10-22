package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Pjadjunct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PJADJUNCT"
    
)

public class Pjadjunct  implements java.io.Serializable {


    // Fields    

     private Integer pjadjunctid;
     private Pjbaseinfo pjbaseinfo;
     private Integer pjadjuncttype;
     private String filename;
     private String fileurl;
     private Integer objectid;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;
     private String wh;


    // Constructors

    /** default constructor */
    public Pjadjunct() {
    }

	/** minimal constructor */
    public Pjadjunct(Integer pjadjuncttype, String filename, String fileurl, 
    		Integer objectid) {
        this.pjadjuncttype = pjadjuncttype;
        this.filename = filename;
        this.fileurl = fileurl;
        this.objectid = objectid;
    }
    
    /** full constructor */
    public Pjadjunct(Pjbaseinfo pjbaseinfo, Integer pjadjuncttype, 
    		String filename, String fileurl, Integer objectid, 
    		Integer recorderid, String recordername, 
    		Date recordertime,String wh) {
        this.pjbaseinfo = pjbaseinfo;
        this.pjadjuncttype = pjadjuncttype;
        this.filename = filename;
        this.fileurl = fileurl;
        this.objectid = objectid;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
        this.wh = wh;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="PJADJUNCTID", unique=true, nullable=false, precision=22, scale=0)

    public Integer getPjadjunctid() {
        return this.pjadjunctid;
    }
    
    public void setPjadjunctid(Integer pjadjunctid) {
        this.pjadjunctid = pjadjunctid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="PROJECTID")

    public Pjbaseinfo getPjbaseinfo() {
        return this.pjbaseinfo;
    }
    
    public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
        this.pjbaseinfo = pjbaseinfo;
    }
    
    @Column(name="PJADJUNCTTYPE", nullable=false, precision=22, scale=0)

    public Integer getPjadjuncttype() {
        return this.pjadjuncttype;
    }
    
    public void setPjadjuncttype(Integer pjadjuncttype) {
        this.pjadjuncttype = pjadjuncttype;
    }
    
    @Column(name="FILENAME", nullable=false)

    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    @Column(name="FILEURL", nullable=false)

    public String getFileurl() {
        return this.fileurl;
    }
    
    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
    
    @Column(name="OBJECTID", nullable=false, precision=22, scale=0)

    public Integer getObjectid() {
        return this.objectid;
    }
    
    public void setObjectid(Integer objectid) {
        this.objectid = objectid;
    }
    
    @Column(name="RECORDERID", precision=22, scale=0)

    public Integer getRecorderid() {
        return this.recorderid;
    }
    
    public void setRecorderid(Integer recorderid) {
        this.recorderid = recorderid;
    }
    
    @Column(name="RECORDERNAME", length=50)

    public String getRecordername() {
        return this.recordername;
    }
    
    public void setRecordername(String recordername) {
        this.recordername = recordername;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="RECORDERTIME", length=7)

    public Date getRecordertime() {
        return this.recordertime;
    }
    
    public void setRecordertime(Date recordertime) {
        this.recordertime = recordertime;
    }

    @Column(name="WH")
	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}
   








}