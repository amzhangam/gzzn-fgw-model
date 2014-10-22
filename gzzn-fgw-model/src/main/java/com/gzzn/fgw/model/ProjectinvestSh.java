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
 * ProjectinvestSh entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PROJECTINVEST_SH"
    
)

public class ProjectinvestSh  implements java.io.Serializable {


    // Fields    

     private Integer pjinvestidSh;
     private Pjbaseinfo pjbaseinfo;
     private Double pjinvestsumSh;
     private Double pjinvestcenter;
     private Double pjinvestprovince;
     private Double pjinvestcitySh;
     private Double pjinvesttown;
     private Double pjinvestcompany;
     private Double pjinvestbusiness;
     private Double pjinvestbank;
     private Double pjinvestother;
     private String pjinvestadvice;
     private Integer planinvestyear;
     private Double planinvestsumSh;
     private Double planinvestcenter;
     private Double planinvestprovince;
     private Double planinvestcitySh;
     private Double planinvesttown;
     private Double planinvestcompany;
     private Double planinvestbusiness;
     private Double planinvestbank;
     private Double planinvestother;
     private String planinvestadvice;
     private String pjauditmind;
     private String pjauditdept;
     private String pjaudituser;
     private Date pjaudittime;


    // Constructors

    /** default constructor */
    public ProjectinvestSh() {
    }

    
    /** full constructor */
    public ProjectinvestSh(Pjbaseinfo pjbaseinfo, Double pjinvestsumSh, Double pjinvestcenter,
    		Double pjinvestprovince, Double pjinvestcitySh, Double pjinvesttown, 
    		Double pjinvestcompany, Double pjinvestbusiness, Double pjinvestbank, 
    		Double pjinvestother, String pjinvestadvice, Integer planinvestyear, 
    		Double planinvestsumSh, Double planinvestcenter, Double planinvestprovince,
    		Double planinvestcitySh, Double planinvesttown, Double planinvestcompany,
    		Double planinvestbusiness, Double planinvestbank, Double planinvestother,
    		String planinvestadvice,String pjauditmind) {
        this.pjbaseinfo = pjbaseinfo;
        this.pjinvestsumSh = pjinvestsumSh;
        this.pjinvestcenter = pjinvestcenter;
        this.pjinvestprovince = pjinvestprovince;
        this.pjinvestcitySh = pjinvestcitySh;
        this.pjinvesttown = pjinvesttown;
        this.pjinvestcompany = pjinvestcompany;
        this.pjinvestbusiness = pjinvestbusiness;
        this.pjinvestbank = pjinvestbank;
        this.pjinvestother = pjinvestother;
        this.pjinvestadvice = pjinvestadvice;
        this.planinvestyear = planinvestyear;
        this.planinvestsumSh = planinvestsumSh;
        this.planinvestcenter = planinvestcenter;
        this.planinvestprovince = planinvestprovince;
        this.planinvestcitySh = planinvestcitySh;
        this.planinvesttown = planinvesttown;
        this.planinvestcompany = planinvestcompany;
        this.planinvestbusiness = planinvestbusiness;
        this.planinvestbank = planinvestbank;
        this.planinvestother = planinvestother;
        this.planinvestadvice = planinvestadvice;
        this.pjauditmind = pjauditmind;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="PJINVESTID_SH", unique=true, nullable=false, precision=22, scale=0)

    public Integer getPjinvestidSh() {
        return this.pjinvestidSh;
    }
    
    public void setPjinvestidSh(Integer pjinvestidSh) {
        this.pjinvestidSh = pjinvestidSh;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="PROJECTID")

    public Pjbaseinfo getPjbaseinfo() {
        return this.pjbaseinfo;
    }
    
    public void setPjbaseinfo(Pjbaseinfo pjbaseinfo) {
        this.pjbaseinfo = pjbaseinfo;
    }
    
    @Column(name="PJINVESTSUM_SH", precision=18)

    public Double getPjinvestsumSh() {
        return this.pjinvestsumSh;
    }
    
    public void setPjinvestsumSh(Double pjinvestsumSh) {
        this.pjinvestsumSh = pjinvestsumSh;
    }
    
    @Column(name="PJINVESTCENTER", precision=18)

    public Double getPjinvestcenter() {
        return this.pjinvestcenter;
    }
    
    public void setPjinvestcenter(Double pjinvestcenter) {
        this.pjinvestcenter = pjinvestcenter;
    }
    
    @Column(name="PJINVESTPROVINCE", precision=18)

    public Double getPjinvestprovince() {
        return this.pjinvestprovince;
    }
    
    public void setPjinvestprovince(Double pjinvestprovince) {
        this.pjinvestprovince = pjinvestprovince;
    }
    
    @Column(name="PJINVESTCITY_SH", precision=18)

    public Double getPjinvestcitySh() {
        return this.pjinvestcitySh;
    }
    
    public void setPjinvestcitySh(Double pjinvestcitySh) {
        this.pjinvestcitySh = pjinvestcitySh;
    }
    
    @Column(name="PJINVESTTOWN", precision=18)

    public Double getPjinvesttown() {
        return this.pjinvesttown;
    }
    
    public void setPjinvesttown(Double pjinvesttown) {
        this.pjinvesttown = pjinvesttown;
    }
    
    @Column(name="PJINVESTCOMPANY", precision=18)

    public Double getPjinvestcompany() {
        return this.pjinvestcompany;
    }
    
    public void setPjinvestcompany(Double pjinvestcompany) {
        this.pjinvestcompany = pjinvestcompany;
    }
    
    @Column(name="PJINVESTBUSINESS", precision=18)

    public Double getPjinvestbusiness() {
        return this.pjinvestbusiness;
    }
    
    public void setPjinvestbusiness(Double pjinvestbusiness) {
        this.pjinvestbusiness = pjinvestbusiness;
    }
    
    @Column(name="PJINVESTBANK", precision=18)

    public Double getPjinvestbank() {
        return this.pjinvestbank;
    }
    
    public void setPjinvestbank(Double pjinvestbank) {
        this.pjinvestbank = pjinvestbank;
    }
    
    @Column(name="PJINVESTOTHER", precision=18)

    public Double getPjinvestother() {
        return this.pjinvestother;
    }
    
    public void setPjinvestother(Double pjinvestother) {
        this.pjinvestother = pjinvestother;
    }
    
    @Column(name="PJINVESTADVICE")

    public String getPjinvestadvice() {
        return this.pjinvestadvice;
    }
    
    public void setPjinvestadvice(String pjinvestadvice) {
        this.pjinvestadvice = pjinvestadvice;
    }
    
    @Column(name="PLANINVESTYEAR", precision=22, scale=0)

    public Integer getPlaninvestyear() {
        return this.planinvestyear;
    }
    
    public void setPlaninvestyear(Integer planinvestyear) {
        this.planinvestyear = planinvestyear;
    }
    
    @Column(name="PLANINVESTSUM_SH", precision=18)

    public Double getPlaninvestsumSh() {
        return this.planinvestsumSh;
    }
    
    public void setPlaninvestsumSh(Double planinvestsumSh) {
        this.planinvestsumSh = planinvestsumSh;
    }
    
    @Column(name="PLANINVESTCENTER", precision=18)

    public Double getPlaninvestcenter() {
        return this.planinvestcenter;
    }
    
    public void setPlaninvestcenter(Double planinvestcenter) {
        this.planinvestcenter = planinvestcenter;
    }
    
    @Column(name="PLANINVESTPROVINCE", precision=18)

    public Double getPlaninvestprovince() {
        return this.planinvestprovince;
    }
    
    public void setPlaninvestprovince(Double planinvestprovince) {
        this.planinvestprovince = planinvestprovince;
    }
    
    @Column(name="PLANINVESTCITY_SH", precision=18)

    public Double getPlaninvestcitySh() {
        return this.planinvestcitySh;
    }
    
    public void setPlaninvestcitySh(Double planinvestcitySh) {
        this.planinvestcitySh = planinvestcitySh;
    }
    
    @Column(name="PLANINVESTTOWN", precision=18)

    public Double getPlaninvesttown() {
        return this.planinvesttown;
    }
    
    public void setPlaninvesttown(Double planinvesttown) {
        this.planinvesttown = planinvesttown;
    }
    
    @Column(name="PLANINVESTCOMPANY", precision=18)

    public Double getPlaninvestcompany() {
        return this.planinvestcompany;
    }
    
    public void setPlaninvestcompany(Double planinvestcompany) {
        this.planinvestcompany = planinvestcompany;
    }
    
    @Column(name="PLANINVESTBUSINESS", precision=18)

    public Double getPlaninvestbusiness() {
        return this.planinvestbusiness;
    }
    
    public void setPlaninvestbusiness(Double planinvestbusiness) {
        this.planinvestbusiness = planinvestbusiness;
    }
    
    @Column(name="PLANINVESTBANK", precision=18)

    public Double getPlaninvestbank() {
        return this.planinvestbank;
    }
    
    public void setPlaninvestbank(Double planinvestbank) {
        this.planinvestbank = planinvestbank;
    }
    
    @Column(name="PLANINVESTOTHER", precision=18)

    public Double getPlaninvestother() {
        return this.planinvestother;
    }
    
    public void setPlaninvestother(Double planinvestother) {
        this.planinvestother = planinvestother;
    }
    
    @Column(name="PLANINVESTADVICE")

    public String getPlaninvestadvice() {
        return this.planinvestadvice;
    }
    
    public void setPlaninvestadvice(String planinvestadvice) {
        this.planinvestadvice = planinvestadvice;
    }
   
    @Column(name="PJAUDITMIND")

    public String getPjauditmind() {
        return this.pjauditmind;
    }
    
    public void setPjauditmind(String pjauditmind) {
        this.pjauditmind = pjauditmind;
    }

    @Column(name="PJAUDITDEPT")
	public String getPjauditdept() {
		return pjauditdept;
	}


	public void setPjauditdept(String pjauditdept) {
		this.pjauditdept = pjauditdept;
	}

	@Column(name="PJAUDITUSER")
	public String getPjaudituser() {
		return pjaudituser;
	}


	public void setPjaudituser(String pjaudituser) {
		this.pjaudituser = pjaudituser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PJAUDITTIME")
	public Date getPjaudittime() {
		return pjaudittime;
	}


	public void setPjaudittime(Date pjaudittime) {
		this.pjaudittime = pjaudittime;
	}







}