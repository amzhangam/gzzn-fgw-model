package com.gzzn.fgw.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SYS_USER"
    
)

public class SysUser  implements java.io.Serializable {


    // Fields    

     private Integer userId;
     private SysDuty sysDuty;
     private SysDept sysDept;
     private SysOrganization sysOrganization;
     private String userName;
     private Integer sex;
     private String loginName;
     private String loginPwd;
     private String tel;
     private String fax;
     private String telmobile;
     private Date createTime;
     private Date validFromDate;
     private Date validToDate;
     private Integer useStatus;
     private Integer loginStatus;
     private Integer userType;
     private String email;
     private Integer roleType;
     private Integer messageRemind;
     private String userduty;
     private Set<SysDx> sysDxes = new HashSet<SysDx>(0);
     private Set<SysProjectlog> sysProjectlogs = new HashSet<SysProjectlog>(0);
     private Set<SysUserRole> sysUserRoles = new HashSet<SysUserRole>(0);
     private Set<SysOperationlog> sysOperationlogs = new HashSet<SysOperationlog>(0);
     private Set<Pjbaseinfo> pjbaseinfosForNexttacheer = new HashSet<Pjbaseinfo>(0);
     private Set<Pjbaseinfo> pjbaseinfosForRecorderid = new HashSet<Pjbaseinfo>(0);
     private Set<Pjauditlog> pjauditlogs = new HashSet<Pjauditlog>(0);
     private Date modifiedTime;

    // Constructors

    /** default constructor */
    public SysUser() {
    }

    
    /** full constructor */
    public SysUser(SysDuty sysDuty, SysDept sysDept, SysOrganization sysOrganization, String userName, 
    		Integer sex, String loginName, String loginPwd, String tel, String fax, String telmobile,
    		Date createTime, Date validFromDate, Date validToDate, Integer useStatus, Integer loginStatus,
    		Integer userType, String email, Integer roleType, Integer messageRemind, String userduty,Date modifiedTime,
    		Set<SysDx> sysDxes, Set<SysProjectlog> sysProjectlogs, Set<SysUserRole> sysUserRoles, Set<SysOperationlog> sysOperationlogs, Set<Pjbaseinfo> pjbaseinfosForNexttacheer, Set<Pjbaseinfo> pjbaseinfosForRecorderid, Set<Pjauditlog> pjauditlogs) {
        this.sysDuty = sysDuty;
        this.sysDept = sysDept;
        this.sysOrganization = sysOrganization;
        this.userName = userName;
        this.sex = sex;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.tel = tel;
        this.fax = fax;
        this.telmobile = telmobile;
        this.createTime = createTime;
        this.validFromDate = validFromDate;
        this.validToDate = validToDate;
        this.useStatus = useStatus;
        this.loginStatus = loginStatus;
        this.userType = userType;
        this.email = email;
        this.roleType = roleType;
        this.messageRemind = messageRemind;
        this.userduty = userduty;
        this.sysDxes = sysDxes;
        this.modifiedTime = modifiedTime;
        this.sysProjectlogs = sysProjectlogs;
        this.sysUserRoles = sysUserRoles;
        this.sysOperationlogs = sysOperationlogs;
        this.pjbaseinfosForNexttacheer = pjbaseinfosForNexttacheer;
        this.pjbaseinfosForRecorderid = pjbaseinfosForRecorderid;
        this.pjauditlogs = pjauditlogs;
    }

   
    // Property accessors
    @Id@SequenceGenerator(name="generator", sequenceName="xmsb",allocationSize = 1) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    
    @Column(name="USER_ID", unique=true, nullable=false, scale=0)

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DUTY_ID")

    public SysDuty getSysDuty() {
        return this.sysDuty;
    }
    
    public void setSysDuty(SysDuty sysDuty) {
        this.sysDuty = sysDuty;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFIED_TIME", length=7)
    public Date getModifiedTime() {
		return modifiedTime;
	}


	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="DEPT_ID")

    public SysDept getSysDept() {
        return this.sysDept;
    }
    
    public void setSysDept(SysDept sysDept) {
        this.sysDept = sysDept;
    }
	@ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="ORGANIZATION_ID")

    public SysOrganization getSysOrganization() {
        return this.sysOrganization;
    }
    
    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }
    
    @Column(name="USER_NAME")

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name="SEX", precision=22, scale=0)

    public Integer getSex() {
        return this.sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    @Column(name="LOGIN_NAME")

    public String getLoginName() {
        return this.loginName;
    }
    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    
    @Column(name="LOGIN_PWD")

    public String getLoginPwd() {
        return this.loginPwd;
    }
    
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
    
    @Column(name="TEL")

    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="FAX")

    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    @Column(name="TELMOBILE")

    public String getTelmobile() {
        return this.telmobile;
    }
    
    public void setTelmobile(String telmobile) {
        this.telmobile = telmobile;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_TIME", length=7)

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="VALID_FROM_DATE", length=7)

    public Date getValidFromDate() {
        return this.validFromDate;
    }
    
    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="VALID_TO_DATE", length=7)

    public Date getValidToDate() {
        return this.validToDate;
    }
    
    public void setValidToDate(Date validToDate) {
        this.validToDate = validToDate;
    }
    
    @Column(name="USE_STATUS", precision=22, scale=0)

    public Integer getUseStatus() {
        return this.useStatus;
    }
    
    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }
    
    @Column(name="LOGIN_STATUS", precision=22, scale=0)

    public Integer getLoginStatus() {
        return this.loginStatus;
    }
    
    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }
    
    @Column(name="USER_TYPE", precision=22, scale=0)

    public Integer getUserType() {
        return this.userType;
    }
    
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    
    @Column(name="EMAIL")

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="ROLE_TYPE", precision=1, scale=0)

    public Integer getRoleType() {
        return this.roleType;
    }
    
    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
    
    @Column(name="MESSAGE_REMIND", precision=1, scale=0)

    public Integer getMessageRemind() {
        return this.messageRemind;
    }
    
    public void setMessageRemind(Integer messageRemind) {
        this.messageRemind = messageRemind;
    }
    
    @Column(name="USERDUTY", length=60)

    public String getUserduty() {
        return this.userduty;
    }
    
    public void setUserduty(String userduty) {
        this.userduty = userduty;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysUser")

    public Set<SysDx> getSysDxes() {
        return this.sysDxes;
    }
    
    public void setSysDxes(Set<SysDx> sysDxes) {
        this.sysDxes = sysDxes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysUser")

    public Set<SysProjectlog> getSysProjectlogs() {
        return this.sysProjectlogs;
    }
    
    public void setSysProjectlogs(Set<SysProjectlog> sysProjectlogs) {
        this.sysProjectlogs = sysProjectlogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysUser")

    public Set<SysUserRole> getSysUserRoles() {
        return this.sysUserRoles;
    }
    
    public void setSysUserRoles(Set<SysUserRole> sysUserRoles) {
        this.sysUserRoles = sysUserRoles;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysUser")

    public Set<SysOperationlog> getSysOperationlogs() {
        return this.sysOperationlogs;
    }
    
    public void setSysOperationlogs(Set<SysOperationlog> sysOperationlogs) {
        this.sysOperationlogs = sysOperationlogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysUserByNexttacheer")

    public Set<Pjbaseinfo> getPjbaseinfosForNexttacheer() {
        return this.pjbaseinfosForNexttacheer;
    }
    
    public void setPjbaseinfosForNexttacheer(Set<Pjbaseinfo> pjbaseinfosForNexttacheer) {
        this.pjbaseinfosForNexttacheer = pjbaseinfosForNexttacheer;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysUserByRecorderid")

    public Set<Pjbaseinfo> getPjbaseinfosForRecorderid() {
        return this.pjbaseinfosForRecorderid;
    }
    
    public void setPjbaseinfosForRecorderid(Set<Pjbaseinfo> pjbaseinfosForRecorderid) {
        this.pjbaseinfosForRecorderid = pjbaseinfosForRecorderid;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysUser")

    public Set<Pjauditlog> getPjauditlogs() {
        return this.pjauditlogs;
    }
    
    public void setPjauditlogs(Set<Pjauditlog> pjauditlogs) {
        this.pjauditlogs = pjauditlogs;
    }
   








}