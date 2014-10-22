package com.gzzn.fgw.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * SpoonLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SPOON_LOG"
    
)

public class SpoonLog  implements java.io.Serializable {


    // Fields    

     private SpoonLogId id;


    // Constructors

    /** default constructor */
    public SpoonLog() {
    }

    
    /** full constructor */
    public SpoonLog(SpoonLogId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="channelId", column=@Column(name="CHANNEL_ID") ), 
        @AttributeOverride(name="status", column=@Column(name="STATUS", length=15) ), 
        @AttributeOverride(name="linesRead", column=@Column(name="LINES_READ", precision=22, scale=0) ), 
        @AttributeOverride(name="linesWritten", column=@Column(name="LINES_WRITTEN", precision=22, scale=0) ), 
        @AttributeOverride(name="linesUpdated", column=@Column(name="LINES_UPDATED", precision=22, scale=0) ), 
        @AttributeOverride(name="linesInput", column=@Column(name="LINES_INPUT", precision=22, scale=0) ), 
        @AttributeOverride(name="linesOutput", column=@Column(name="LINES_OUTPUT", precision=22, scale=0) ), 
        @AttributeOverride(name="linesRejected", column=@Column(name="LINES_REJECTED", precision=22, scale=0) ), 
        @AttributeOverride(name="errors", column=@Column(name="ERRORS", precision=22, scale=0) ), 
        @AttributeOverride(name="startdate", column=@Column(name="STARTDATE", length=7) ), 
        @AttributeOverride(name="enddate", column=@Column(name="ENDDATE", length=7) ), 
        @AttributeOverride(name="logdate", column=@Column(name="LOGDATE", length=7) ), 
        @AttributeOverride(name="depdate", column=@Column(name="DEPDATE", length=7) ), 
        @AttributeOverride(name="replaydate", column=@Column(name="REPLAYDATE", length=7) ), 
        @AttributeOverride(name="logField", column=@Column(name="LOG_FIELD") ), 
        @AttributeOverride(name="idJob", column=@Column(name="ID_JOB", precision=22, scale=0) ), 
        @AttributeOverride(name="jobname", column=@Column(name="JOBNAME") ) } )

    public SpoonLogId getId() {
        return this.id;
    }
    
    public void setId(SpoonLogId id) {
        this.id = id;
    }
   








}