package com.gzzn.fgw.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * PjbaseinfoReport2Id entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class PjbaseinfoReport2Id  implements java.io.Serializable {


    // Fields    

     private String numberid;
     private Integer projecttype;
     private String projectname;
     private String startendyear;
     private Double pjinvestsum;
     private Double pjinvestcity;
     private Double pjinvestcompany;
     private Double pjinvestbank;
     private Double pjinvestothers;
     private Double expectfinishinvest;
     private Double expectfinishotherinvest;
     private Double planinvestsum;
     private Double planinvestcity;
     private Double planinvestcompany;
     private Double planinvestbank;
     private Double planinvestothers;
     private String projectcontent;
     private String pjinvestadvice;
     private String declaregist;
     private String directorunitsid;
     private String manageunitsname;
     private String memo;
     private String reporttimes;
     private Integer projectid;
     private Integer orderid;
     private Integer totalproject;


    // Constructors

    /** default constructor */
    public PjbaseinfoReport2Id() {
    }

    
    /** full constructor */
    public PjbaseinfoReport2Id(String numberid, Integer projecttype, String projectname, String startendyear, Double pjinvestsum, Double pjinvestcity, Double pjinvestcompany, Double pjinvestbank, Double pjinvestothers, Double expectfinishinvest, Double expectfinishotherinvest, Double planinvestsum, Double planinvestcity, Double planinvestcompany, Double planinvestbank, Double planinvestothers, String projectcontent, String pjinvestadvice, String declaregist, String directorunitsid, String manageunitsname, String memo, String reporttimes, Integer projectid, Integer orderid, Integer totalproject) {
        this.numberid = numberid;
        this.projecttype = projecttype;
        this.projectname = projectname;
        this.startendyear = startendyear;
        this.pjinvestsum = pjinvestsum;
        this.pjinvestcity = pjinvestcity;
        this.pjinvestcompany = pjinvestcompany;
        this.pjinvestbank = pjinvestbank;
        this.pjinvestothers = pjinvestothers;
        this.expectfinishinvest = expectfinishinvest;
        this.expectfinishotherinvest = expectfinishotherinvest;
        this.planinvestsum = planinvestsum;
        this.planinvestcity = planinvestcity;
        this.planinvestcompany = planinvestcompany;
        this.planinvestbank = planinvestbank;
        this.planinvestothers = planinvestothers;
        this.projectcontent = projectcontent;
        this.pjinvestadvice = pjinvestadvice;
        this.declaregist = declaregist;
        this.directorunitsid = directorunitsid;
        this.manageunitsname = manageunitsname;
        this.memo = memo;
        this.reporttimes = reporttimes;
        this.projectid = projectid;
        this.orderid = orderid;
        this.totalproject = totalproject;
    }

   
    // Property accessors

    @Column(name="NUMBERID", length=20)

    public String getNumberid() {
        return this.numberid;
    }
    
    public void setNumberid(String numberid) {
        this.numberid = numberid;
    }

    @Column(name="PROJECTTYPE", precision=22, scale=0)

    public Integer getProjecttype() {
        return this.projecttype;
    }
    
    public void setProjecttype(Integer projecttype) {
        this.projecttype = projecttype;
    }

    @Column(name="PROJECTNAME")

    public String getProjectname() {
        return this.projectname;
    }
    
    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    @Column(name="STARTENDYEAR")

    public String getStartendyear() {
        return this.startendyear;
    }
    
    public void setStartendyear(String startendyear) {
        this.startendyear = startendyear;
    }

    @Column(name="PJINVESTSUM", precision=18)

    public Double getPjinvestsum() {
        return this.pjinvestsum;
    }
    
    public void setPjinvestsum(Double pjinvestsum) {
        this.pjinvestsum = pjinvestsum;
    }

    @Column(name="PJINVESTCITY", precision=18)

    public Double getPjinvestcity() {
        return this.pjinvestcity;
    }
    
    public void setPjinvestcity(Double pjinvestcity) {
        this.pjinvestcity = pjinvestcity;
    }

    @Column(name="PJINVESTCOMPANY", precision=18)

    public Double getPjinvestcompany() {
        return this.pjinvestcompany;
    }
    
    public void setPjinvestcompany(Double pjinvestcompany) {
        this.pjinvestcompany = pjinvestcompany;
    }

    @Column(name="PJINVESTBANK", precision=18)

    public Double getPjinvestbank() {
        return this.pjinvestbank;
    }
    
    public void setPjinvestbank(Double pjinvestbank) {
        this.pjinvestbank = pjinvestbank;
    }

    @Column(name="PJINVESTOTHERS", precision=18)

    public Double getPjinvestothers() {
        return this.pjinvestothers;
    }
    
    public void setPjinvestothers(Double pjinvestothers) {
        this.pjinvestothers = pjinvestothers;
    }

    @Column(name="EXPECTFINISHINVEST", precision=18)

    public Double getExpectfinishinvest() {
        return this.expectfinishinvest;
    }
    
    public void setExpectfinishinvest(Double expectfinishinvest) {
        this.expectfinishinvest = expectfinishinvest;
    }

    @Column(name="EXPECTFINISHOTHERINVEST", precision=18)

    public Double getExpectfinishotherinvest() {
        return this.expectfinishotherinvest;
    }
    
    public void setExpectfinishotherinvest(Double expectfinishotherinvest) {
        this.expectfinishotherinvest = expectfinishotherinvest;
    }

    @Column(name="PLANINVESTSUM", precision=18)

    public Double getPlaninvestsum() {
        return this.planinvestsum;
    }
    
    public void setPlaninvestsum(Double planinvestsum) {
        this.planinvestsum = planinvestsum;
    }

    @Column(name="PLANINVESTCITY", precision=18)

    public Double getPlaninvestcity() {
        return this.planinvestcity;
    }
    
    public void setPlaninvestcity(Double planinvestcity) {
        this.planinvestcity = planinvestcity;
    }

    @Column(name="PLANINVESTCOMPANY", precision=18)

    public Double getPlaninvestcompany() {
        return this.planinvestcompany;
    }
    
    public void setPlaninvestcompany(Double planinvestcompany) {
        this.planinvestcompany = planinvestcompany;
    }

    @Column(name="PLANINVESTBANK", precision=18)

    public Double getPlaninvestbank() {
        return this.planinvestbank;
    }
    
    public void setPlaninvestbank(Double planinvestbank) {
        this.planinvestbank = planinvestbank;
    }

    @Column(name="PLANINVESTOTHERS", precision=18)

    public Double getPlaninvestothers() {
        return this.planinvestothers;
    }
    
    public void setPlaninvestothers(Double planinvestothers) {
        this.planinvestothers = planinvestothers;
    }

    @Column(name="PROJECTCONTENT")

    public String getProjectcontent() {
        return this.projectcontent;
    }
    
    public void setProjectcontent(String projectcontent) {
        this.projectcontent = projectcontent;
    }

    @Column(name="PJINVESTADVICE")

    public String getPjinvestadvice() {
        return this.pjinvestadvice;
    }
    
    public void setPjinvestadvice(String pjinvestadvice) {
        this.pjinvestadvice = pjinvestadvice;
    }

    @Column(name="DECLAREGIST")

    public String getDeclaregist() {
        return this.declaregist;
    }
    
    public void setDeclaregist(String declaregist) {
        this.declaregist = declaregist;
    }

    @Column(name="DIRECTORUNITSID")

    public String getDirectorunitsid() {
        return this.directorunitsid;
    }
    
    public void setDirectorunitsid(String directorunitsid) {
        this.directorunitsid = directorunitsid;
    }

    @Column(name="MANAGEUNITSNAME")

    public String getManageunitsname() {
        return this.manageunitsname;
    }
    
    public void setManageunitsname(String manageunitsname) {
        this.manageunitsname = manageunitsname;
    }

    @Column(name="MEMO")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name="REPORTTIMES")

    public String getReporttimes() {
        return this.reporttimes;
    }
    
    public void setReporttimes(String reporttimes) {
        this.reporttimes = reporttimes;
    }

    @Column(name="PROJECTID", precision=22, scale=0)

    public Integer getProjectid() {
        return this.projectid;
    }
    
    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    @Column(name="ORDERID", precision=22, scale=0)

    public Integer getOrderid() {
        return this.orderid;
    }
    
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Column(name="TOTALPROJECT", precision=22, scale=0)

    public Integer getTotalproject() {
        return this.totalproject;
    }
    
    public void setTotalproject(Integer totalproject) {
        this.totalproject = totalproject;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PjbaseinfoReport2Id) ) return false;
		 PjbaseinfoReport2Id castOther = ( PjbaseinfoReport2Id ) other; 
         
		 return ( (this.getNumberid()==castOther.getNumberid()) || ( this.getNumberid()!=null && castOther.getNumberid()!=null && this.getNumberid().equals(castOther.getNumberid()) ) )
 && ( (this.getProjecttype()==castOther.getProjecttype()) || ( this.getProjecttype()!=null && castOther.getProjecttype()!=null && this.getProjecttype().equals(castOther.getProjecttype()) ) )
 && ( (this.getProjectname()==castOther.getProjectname()) || ( this.getProjectname()!=null && castOther.getProjectname()!=null && this.getProjectname().equals(castOther.getProjectname()) ) )
 && ( (this.getStartendyear()==castOther.getStartendyear()) || ( this.getStartendyear()!=null && castOther.getStartendyear()!=null && this.getStartendyear().equals(castOther.getStartendyear()) ) )
 && ( (this.getPjinvestsum()==castOther.getPjinvestsum()) || ( this.getPjinvestsum()!=null && castOther.getPjinvestsum()!=null && this.getPjinvestsum().equals(castOther.getPjinvestsum()) ) )
 && ( (this.getPjinvestcity()==castOther.getPjinvestcity()) || ( this.getPjinvestcity()!=null && castOther.getPjinvestcity()!=null && this.getPjinvestcity().equals(castOther.getPjinvestcity()) ) )
 && ( (this.getPjinvestcompany()==castOther.getPjinvestcompany()) || ( this.getPjinvestcompany()!=null && castOther.getPjinvestcompany()!=null && this.getPjinvestcompany().equals(castOther.getPjinvestcompany()) ) )
 && ( (this.getPjinvestbank()==castOther.getPjinvestbank()) || ( this.getPjinvestbank()!=null && castOther.getPjinvestbank()!=null && this.getPjinvestbank().equals(castOther.getPjinvestbank()) ) )
 && ( (this.getPjinvestothers()==castOther.getPjinvestothers()) || ( this.getPjinvestothers()!=null && castOther.getPjinvestothers()!=null && this.getPjinvestothers().equals(castOther.getPjinvestothers()) ) )
 && ( (this.getExpectfinishinvest()==castOther.getExpectfinishinvest()) || ( this.getExpectfinishinvest()!=null && castOther.getExpectfinishinvest()!=null && this.getExpectfinishinvest().equals(castOther.getExpectfinishinvest()) ) )
 && ( (this.getExpectfinishotherinvest()==castOther.getExpectfinishotherinvest()) || ( this.getExpectfinishotherinvest()!=null && castOther.getExpectfinishotherinvest()!=null && this.getExpectfinishotherinvest().equals(castOther.getExpectfinishotherinvest()) ) )
 && ( (this.getPlaninvestsum()==castOther.getPlaninvestsum()) || ( this.getPlaninvestsum()!=null && castOther.getPlaninvestsum()!=null && this.getPlaninvestsum().equals(castOther.getPlaninvestsum()) ) )
 && ( (this.getPlaninvestcity()==castOther.getPlaninvestcity()) || ( this.getPlaninvestcity()!=null && castOther.getPlaninvestcity()!=null && this.getPlaninvestcity().equals(castOther.getPlaninvestcity()) ) )
 && ( (this.getPlaninvestcompany()==castOther.getPlaninvestcompany()) || ( this.getPlaninvestcompany()!=null && castOther.getPlaninvestcompany()!=null && this.getPlaninvestcompany().equals(castOther.getPlaninvestcompany()) ) )
 && ( (this.getPlaninvestbank()==castOther.getPlaninvestbank()) || ( this.getPlaninvestbank()!=null && castOther.getPlaninvestbank()!=null && this.getPlaninvestbank().equals(castOther.getPlaninvestbank()) ) )
 && ( (this.getPlaninvestothers()==castOther.getPlaninvestothers()) || ( this.getPlaninvestothers()!=null && castOther.getPlaninvestothers()!=null && this.getPlaninvestothers().equals(castOther.getPlaninvestothers()) ) )
 && ( (this.getProjectcontent()==castOther.getProjectcontent()) || ( this.getProjectcontent()!=null && castOther.getProjectcontent()!=null && this.getProjectcontent().equals(castOther.getProjectcontent()) ) )
 && ( (this.getPjinvestadvice()==castOther.getPjinvestadvice()) || ( this.getPjinvestadvice()!=null && castOther.getPjinvestadvice()!=null && this.getPjinvestadvice().equals(castOther.getPjinvestadvice()) ) )
 && ( (this.getDeclaregist()==castOther.getDeclaregist()) || ( this.getDeclaregist()!=null && castOther.getDeclaregist()!=null && this.getDeclaregist().equals(castOther.getDeclaregist()) ) )
 && ( (this.getDirectorunitsid()==castOther.getDirectorunitsid()) || ( this.getDirectorunitsid()!=null && castOther.getDirectorunitsid()!=null && this.getDirectorunitsid().equals(castOther.getDirectorunitsid()) ) )
 && ( (this.getManageunitsname()==castOther.getManageunitsname()) || ( this.getManageunitsname()!=null && castOther.getManageunitsname()!=null && this.getManageunitsname().equals(castOther.getManageunitsname()) ) )
 && ( (this.getMemo()==castOther.getMemo()) || ( this.getMemo()!=null && castOther.getMemo()!=null && this.getMemo().equals(castOther.getMemo()) ) )
 && ( (this.getReporttimes()==castOther.getReporttimes()) || ( this.getReporttimes()!=null && castOther.getReporttimes()!=null && this.getReporttimes().equals(castOther.getReporttimes()) ) )
 && ( (this.getProjectid()==castOther.getProjectid()) || ( this.getProjectid()!=null && castOther.getProjectid()!=null && this.getProjectid().equals(castOther.getProjectid()) ) )
 && ( (this.getOrderid()==castOther.getOrderid()) || ( this.getOrderid()!=null && castOther.getOrderid()!=null && this.getOrderid().equals(castOther.getOrderid()) ) )
 && ( (this.getTotalproject()==castOther.getTotalproject()) || ( this.getTotalproject()!=null && castOther.getTotalproject()!=null && this.getTotalproject().equals(castOther.getTotalproject()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNumberid() == null ? 0 : this.getNumberid().hashCode() );
         result = 37 * result + ( getProjecttype() == null ? 0 : this.getProjecttype().hashCode() );
         result = 37 * result + ( getProjectname() == null ? 0 : this.getProjectname().hashCode() );
         result = 37 * result + ( getStartendyear() == null ? 0 : this.getStartendyear().hashCode() );
         result = 37 * result + ( getPjinvestsum() == null ? 0 : this.getPjinvestsum().hashCode() );
         result = 37 * result + ( getPjinvestcity() == null ? 0 : this.getPjinvestcity().hashCode() );
         result = 37 * result + ( getPjinvestcompany() == null ? 0 : this.getPjinvestcompany().hashCode() );
         result = 37 * result + ( getPjinvestbank() == null ? 0 : this.getPjinvestbank().hashCode() );
         result = 37 * result + ( getPjinvestothers() == null ? 0 : this.getPjinvestothers().hashCode() );
         result = 37 * result + ( getExpectfinishinvest() == null ? 0 : this.getExpectfinishinvest().hashCode() );
         result = 37 * result + ( getExpectfinishotherinvest() == null ? 0 : this.getExpectfinishotherinvest().hashCode() );
         result = 37 * result + ( getPlaninvestsum() == null ? 0 : this.getPlaninvestsum().hashCode() );
         result = 37 * result + ( getPlaninvestcity() == null ? 0 : this.getPlaninvestcity().hashCode() );
         result = 37 * result + ( getPlaninvestcompany() == null ? 0 : this.getPlaninvestcompany().hashCode() );
         result = 37 * result + ( getPlaninvestbank() == null ? 0 : this.getPlaninvestbank().hashCode() );
         result = 37 * result + ( getPlaninvestothers() == null ? 0 : this.getPlaninvestothers().hashCode() );
         result = 37 * result + ( getProjectcontent() == null ? 0 : this.getProjectcontent().hashCode() );
         result = 37 * result + ( getPjinvestadvice() == null ? 0 : this.getPjinvestadvice().hashCode() );
         result = 37 * result + ( getDeclaregist() == null ? 0 : this.getDeclaregist().hashCode() );
         result = 37 * result + ( getDirectorunitsid() == null ? 0 : this.getDirectorunitsid().hashCode() );
         result = 37 * result + ( getManageunitsname() == null ? 0 : this.getManageunitsname().hashCode() );
         result = 37 * result + ( getMemo() == null ? 0 : this.getMemo().hashCode() );
         result = 37 * result + ( getReporttimes() == null ? 0 : this.getReporttimes().hashCode() );
         result = 37 * result + ( getProjectid() == null ? 0 : this.getProjectid().hashCode() );
         result = 37 * result + ( getOrderid() == null ? 0 : this.getOrderid().hashCode() );
         result = 37 * result + ( getTotalproject() == null ? 0 : this.getTotalproject().hashCode() );
         return result;
   }   





}