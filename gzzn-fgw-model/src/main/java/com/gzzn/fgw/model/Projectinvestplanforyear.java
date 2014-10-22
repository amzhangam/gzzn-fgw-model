package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * ProjectinvestplanforyearId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="projectinvestplanforyear"
    
)
public class Projectinvestplanforyear  implements java.io.Serializable {


    // Fields    

     private Integer investplanid;
     private Integer investplanyear;
     private String investplanname;
     private String filename;
     private String fileurl;
     private Integer recorderid;
     private String recordername;
     private Date recordertime;


    // Constructors

    /** default constructor */
    public Projectinvestplanforyear() {
    }

	/** minimal constructor */
    public Projectinvestplanforyear(Integer investplanid, Integer investplanyear, String investplanname, String filename, String fileurl) {
        this.investplanid = investplanid;
        this.investplanyear = investplanyear;
        this.investplanname = investplanname;
        this.filename = filename;
        this.fileurl = fileurl;
    }
    
    /** full constructor */
    public Projectinvestplanforyear(Integer investplanid, Integer investplanyear, String investplanname, String filename, String fileurl, Integer recorderid, String recordername, Date recordertime) {
        this.investplanid = investplanid;
        this.investplanyear = investplanyear;
        this.investplanname = investplanname;
        this.filename = filename;
        this.fileurl = fileurl;
        this.recorderid = recorderid;
        this.recordername = recordername;
        this.recordertime = recordertime;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="INVESTPLANID", nullable=false, precision=22, scale=0)

    public Integer getInvestplanid() {
        return this.investplanid;
    }
    
    public void setInvestplanid(Integer investplanid) {
        this.investplanid = investplanid;
    }

    @Column(name="INVESTPLANYEAR", nullable=false, precision=22, scale=0)

    public Integer getInvestplanyear() {
        return this.investplanyear;
    }
    
    public void setInvestplanyear(Integer investplanyear) {
        this.investplanyear = investplanyear;
    }

    @Column(name="INVESTPLANNAME", nullable=false)

    public String getInvestplanname() {
        return this.investplanname;
    }
    
    public void setInvestplanname(String investplanname) {
        this.investplanname = investplanname;
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
    
    @Column(name="RECORDERTIME", length=19)
    public Date getRecordertime() {
        return this.recordertime;
    }
    
    public void setRecordertime(Date recordertime) {
        this.recordertime = recordertime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Projectinvestplanforyear) ) return false;
		 Projectinvestplanforyear castOther = ( Projectinvestplanforyear ) other; 
         
		 return ( (this.getInvestplanid()==castOther.getInvestplanid()) || ( this.getInvestplanid()!=null && castOther.getInvestplanid()!=null && this.getInvestplanid().equals(castOther.getInvestplanid()) ) )
 && ( (this.getInvestplanyear()==castOther.getInvestplanyear()) || ( this.getInvestplanyear()!=null && castOther.getInvestplanyear()!=null && this.getInvestplanyear().equals(castOther.getInvestplanyear()) ) )
 && ( (this.getInvestplanname()==castOther.getInvestplanname()) || ( this.getInvestplanname()!=null && castOther.getInvestplanname()!=null && this.getInvestplanname().equals(castOther.getInvestplanname()) ) )
 && ( (this.getFilename()==castOther.getFilename()) || ( this.getFilename()!=null && castOther.getFilename()!=null && this.getFilename().equals(castOther.getFilename()) ) )
 && ( (this.getFileurl()==castOther.getFileurl()) || ( this.getFileurl()!=null && castOther.getFileurl()!=null && this.getFileurl().equals(castOther.getFileurl()) ) )
 && ( (this.getRecorderid()==castOther.getRecorderid()) || ( this.getRecorderid()!=null && castOther.getRecorderid()!=null && this.getRecorderid().equals(castOther.getRecorderid()) ) )
 && ( (this.getRecordername()==castOther.getRecordername()) || ( this.getRecordername()!=null && castOther.getRecordername()!=null && this.getRecordername().equals(castOther.getRecordername()) ) )
 && ( (this.getRecordertime()==castOther.getRecordertime()) || ( this.getRecordertime()!=null && castOther.getRecordertime()!=null && this.getRecordertime().equals(castOther.getRecordertime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getInvestplanid() == null ? 0 : this.getInvestplanid().hashCode() );
         result = 37 * result + ( getInvestplanyear() == null ? 0 : this.getInvestplanyear().hashCode() );
         result = 37 * result + ( getInvestplanname() == null ? 0 : this.getInvestplanname().hashCode() );
         result = 37 * result + ( getFilename() == null ? 0 : this.getFilename().hashCode() );
         result = 37 * result + ( getFileurl() == null ? 0 : this.getFileurl().hashCode() );
         result = 37 * result + ( getRecorderid() == null ? 0 : this.getRecorderid().hashCode() );
         result = 37 * result + ( getRecordername() == null ? 0 : this.getRecordername().hashCode() );
         result = 37 * result + ( getRecordertime() == null ? 0 : this.getRecordertime().hashCode() );
         return result;
   }   





}