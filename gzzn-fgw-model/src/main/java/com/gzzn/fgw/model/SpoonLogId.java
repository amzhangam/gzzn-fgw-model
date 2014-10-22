package com.gzzn.fgw.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SpoonLogId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class SpoonLogId  implements java.io.Serializable {


    // Fields    

     private String channelId;
     private String status;
     private Integer linesRead;
     private Integer linesWritten;
     private Integer linesUpdated;
     private Integer linesInput;
     private Integer linesOutput;
     private Integer linesRejected;
     private Integer errors;
     private Date startdate;
     private Date enddate;
     private Date logdate;
     private Date depdate;
     private Date replaydate;
     private String logField;
     private Integer idJob;
     private String jobname;


    // Constructors

    /** default constructor */
    public SpoonLogId() {
    }

    
    /** full constructor */
    public SpoonLogId(String channelId, String status, Integer linesRead, Integer linesWritten, Integer linesUpdated, Integer linesInput, Integer linesOutput, Integer linesRejected, Integer errors, Date startdate, Date enddate, Date logdate, Date depdate, Date replaydate, String logField, Integer idJob, String jobname) {
        this.channelId = channelId;
        this.status = status;
        this.linesRead = linesRead;
        this.linesWritten = linesWritten;
        this.linesUpdated = linesUpdated;
        this.linesInput = linesInput;
        this.linesOutput = linesOutput;
        this.linesRejected = linesRejected;
        this.errors = errors;
        this.startdate = startdate;
        this.enddate = enddate;
        this.logdate = logdate;
        this.depdate = depdate;
        this.replaydate = replaydate;
        this.logField = logField;
        this.idJob = idJob;
        this.jobname = jobname;
    }

   
    // Property accessors

    @Column(name="CHANNEL_ID")

    public String getChannelId() {
        return this.channelId;
    }
    
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Column(name="STATUS", length=15)

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name="LINES_READ", precision=22, scale=0)

    public Integer getLinesRead() {
        return this.linesRead;
    }
    
    public void setLinesRead(Integer linesRead) {
        this.linesRead = linesRead;
    }

    @Column(name="LINES_WRITTEN", precision=22, scale=0)

    public Integer getLinesWritten() {
        return this.linesWritten;
    }
    
    public void setLinesWritten(Integer linesWritten) {
        this.linesWritten = linesWritten;
    }

    @Column(name="LINES_UPDATED", precision=22, scale=0)

    public Integer getLinesUpdated() {
        return this.linesUpdated;
    }
    
    public void setLinesUpdated(Integer linesUpdated) {
        this.linesUpdated = linesUpdated;
    }

    @Column(name="LINES_INPUT", precision=22, scale=0)

    public Integer getLinesInput() {
        return this.linesInput;
    }
    
    public void setLinesInput(Integer linesInput) {
        this.linesInput = linesInput;
    }

    @Column(name="LINES_OUTPUT", precision=22, scale=0)

    public Integer getLinesOutput() {
        return this.linesOutput;
    }
    
    public void setLinesOutput(Integer linesOutput) {
        this.linesOutput = linesOutput;
    }

    @Column(name="LINES_REJECTED", precision=22, scale=0)

    public Integer getLinesRejected() {
        return this.linesRejected;
    }
    
    public void setLinesRejected(Integer linesRejected) {
        this.linesRejected = linesRejected;
    }

    @Column(name="ERRORS", precision=22, scale=0)

    public Integer getErrors() {
        return this.errors;
    }
    
    public void setErrors(Integer errors) {
        this.errors = errors;
    }
@Temporal(TemporalType.DATE)
    @Column(name="STARTDATE", length=7)

    public Date getStartdate() {
        return this.startdate;
    }
    
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
@Temporal(TemporalType.DATE)
    @Column(name="ENDDATE", length=7)

    public Date getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
@Temporal(TemporalType.DATE)
    @Column(name="LOGDATE", length=7)

    public Date getLogdate() {
        return this.logdate;
    }
    
    public void setLogdate(Date logdate) {
        this.logdate = logdate;
    }
@Temporal(TemporalType.DATE)
    @Column(name="DEPDATE", length=7)

    public Date getDepdate() {
        return this.depdate;
    }
    
    public void setDepdate(Date depdate) {
        this.depdate = depdate;
    }
@Temporal(TemporalType.DATE)
    @Column(name="REPLAYDATE", length=7)

    public Date getReplaydate() {
        return this.replaydate;
    }
    
    public void setReplaydate(Date replaydate) {
        this.replaydate = replaydate;
    }

    @Column(name="LOG_FIELD")

    public String getLogField() {
        return this.logField;
    }
    
    public void setLogField(String logField) {
        this.logField = logField;
    }

    @Column(name="ID_JOB", precision=22, scale=0)

    public Integer getIdJob() {
        return this.idJob;
    }
    
    public void setIdJob(Integer idJob) {
        this.idJob = idJob;
    }

    @Column(name="JOBNAME")

    public String getJobname() {
        return this.jobname;
    }
    
    public void setJobname(String jobname) {
        this.jobname = jobname;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SpoonLogId) ) return false;
		 SpoonLogId castOther = ( SpoonLogId ) other; 
         
		 return ( (this.getChannelId()==castOther.getChannelId()) || ( this.getChannelId()!=null && castOther.getChannelId()!=null && this.getChannelId().equals(castOther.getChannelId()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getLinesRead()==castOther.getLinesRead()) || ( this.getLinesRead()!=null && castOther.getLinesRead()!=null && this.getLinesRead().equals(castOther.getLinesRead()) ) )
 && ( (this.getLinesWritten()==castOther.getLinesWritten()) || ( this.getLinesWritten()!=null && castOther.getLinesWritten()!=null && this.getLinesWritten().equals(castOther.getLinesWritten()) ) )
 && ( (this.getLinesUpdated()==castOther.getLinesUpdated()) || ( this.getLinesUpdated()!=null && castOther.getLinesUpdated()!=null && this.getLinesUpdated().equals(castOther.getLinesUpdated()) ) )
 && ( (this.getLinesInput()==castOther.getLinesInput()) || ( this.getLinesInput()!=null && castOther.getLinesInput()!=null && this.getLinesInput().equals(castOther.getLinesInput()) ) )
 && ( (this.getLinesOutput()==castOther.getLinesOutput()) || ( this.getLinesOutput()!=null && castOther.getLinesOutput()!=null && this.getLinesOutput().equals(castOther.getLinesOutput()) ) )
 && ( (this.getLinesRejected()==castOther.getLinesRejected()) || ( this.getLinesRejected()!=null && castOther.getLinesRejected()!=null && this.getLinesRejected().equals(castOther.getLinesRejected()) ) )
 && ( (this.getErrors()==castOther.getErrors()) || ( this.getErrors()!=null && castOther.getErrors()!=null && this.getErrors().equals(castOther.getErrors()) ) )
 && ( (this.getStartdate()==castOther.getStartdate()) || ( this.getStartdate()!=null && castOther.getStartdate()!=null && this.getStartdate().equals(castOther.getStartdate()) ) )
 && ( (this.getEnddate()==castOther.getEnddate()) || ( this.getEnddate()!=null && castOther.getEnddate()!=null && this.getEnddate().equals(castOther.getEnddate()) ) )
 && ( (this.getLogdate()==castOther.getLogdate()) || ( this.getLogdate()!=null && castOther.getLogdate()!=null && this.getLogdate().equals(castOther.getLogdate()) ) )
 && ( (this.getDepdate()==castOther.getDepdate()) || ( this.getDepdate()!=null && castOther.getDepdate()!=null && this.getDepdate().equals(castOther.getDepdate()) ) )
 && ( (this.getReplaydate()==castOther.getReplaydate()) || ( this.getReplaydate()!=null && castOther.getReplaydate()!=null && this.getReplaydate().equals(castOther.getReplaydate()) ) )
 && ( (this.getLogField()==castOther.getLogField()) || ( this.getLogField()!=null && castOther.getLogField()!=null && this.getLogField().equals(castOther.getLogField()) ) )
 && ( (this.getIdJob()==castOther.getIdJob()) || ( this.getIdJob()!=null && castOther.getIdJob()!=null && this.getIdJob().equals(castOther.getIdJob()) ) )
 && ( (this.getJobname()==castOther.getJobname()) || ( this.getJobname()!=null && castOther.getJobname()!=null && this.getJobname().equals(castOther.getJobname()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getChannelId() == null ? 0 : this.getChannelId().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getLinesRead() == null ? 0 : this.getLinesRead().hashCode() );
         result = 37 * result + ( getLinesWritten() == null ? 0 : this.getLinesWritten().hashCode() );
         result = 37 * result + ( getLinesUpdated() == null ? 0 : this.getLinesUpdated().hashCode() );
         result = 37 * result + ( getLinesInput() == null ? 0 : this.getLinesInput().hashCode() );
         result = 37 * result + ( getLinesOutput() == null ? 0 : this.getLinesOutput().hashCode() );
         result = 37 * result + ( getLinesRejected() == null ? 0 : this.getLinesRejected().hashCode() );
         result = 37 * result + ( getErrors() == null ? 0 : this.getErrors().hashCode() );
         result = 37 * result + ( getStartdate() == null ? 0 : this.getStartdate().hashCode() );
         result = 37 * result + ( getEnddate() == null ? 0 : this.getEnddate().hashCode() );
         result = 37 * result + ( getLogdate() == null ? 0 : this.getLogdate().hashCode() );
         result = 37 * result + ( getDepdate() == null ? 0 : this.getDepdate().hashCode() );
         result = 37 * result + ( getReplaydate() == null ? 0 : this.getReplaydate().hashCode() );
         result = 37 * result + ( getLogField() == null ? 0 : this.getLogField().hashCode() );
         result = 37 * result + ( getIdJob() == null ? 0 : this.getIdJob().hashCode() );
         result = 37 * result + ( getJobname() == null ? 0 : this.getJobname().hashCode() );
         return result;
   }   





}