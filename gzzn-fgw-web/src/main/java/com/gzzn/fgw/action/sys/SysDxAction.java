package com.gzzn.fgw.action.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.SysDept;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.SysDx;
import com.gzzn.fgw.model.SysDxmb;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.ISmsService;
import com.gzzn.fgw.service.sys.ISysDxService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.ValidateUtil;
import com.gzzn.util.common.DateUtil;
import com.gzzn.util.web.PageUtil;
/**
 * <p>Title: SysDxAction</p>
 * <p>Description: 信息维护  </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-12-10 下午2:47:41 lhq  new
 */
@Namespace(value = "/sys/dx")
public class SysDxAction extends BaseAction<SysDx> {

	private String id;//编辑或删除的id，多个间用@隔开
	private SysQueryParam sysParams;//系统管理查询参数
	private SysDx obj;//对象
	private String dxmb;//用户选择的短信模板
	private String dxsfnr;//短信的收发内容
	private String message;//返回页面信息
	private Map<Integer,String> xmztMap = new HashMap<Integer, String>();//项目状态（值：草稿、待处长审核等）
	private PageUtil<Pjbaseinfo> pagePjInfo = new PageUtil<Pjbaseinfo>();
	private PageUtil<SysDx> page = new PageUtil<SysDx>();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysDxService service;
	@Resource
	private ISmsService smsService;
	@Autowired
	private JdbcTemplate jdbcTemplate2;
	
	
	/**已发送短信*/
	@Action("list")
	public String list(){
		Condition con = this.initCon();//初始化相关的查询条件
		Order order1 = new Order(Direction.DESC,"sfsj");
		Order order2 = new Order(Direction.DESC,"dxId");
		service.loadData(SysDx.class, page, con, new Sort(order1,order2));
		return "success";
	}
	
	/**
	 * 初始化短信管理的相关查询条件
	 * @return
	 */
	private Condition initCon(){
		Condition con = new Condition();
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getSendUsername())){//发送用户
				con.add("sysUser.userName", Operator.LIKE, sysParams.getSendUsername());
			}
			if(StringUtils.isNotEmpty(sysParams.getSendDeptid())){//发送部门ID，多个间使用,隔开
				con.add("sysDeptByDeptId.deptId", Operator.ISNOTNULL, null);
				con.add("sysDeptByDeptId.deptId", Operator.IN, Arrays.asList(sysParams.getSendDeptid().split(",")));
			}
			/**if(StringUtils.isNotEmpty(sysParams.getSendDeptname())){//发送部门
				con.add("sysDeptByDeptId.deptname", Operator.LIKE, sysParams.getSendDeptname());
			}*/
			if(StringUtils.isNotEmpty(sysParams.getReceiveOrganizationname())){//接收单位
				con.add("sysOrganization.organizationName", Operator.LIKE, sysParams.getReceiveOrganizationname());
			}
			if(StringUtils.isNotEmpty(sysParams.getReceiveDeptid())){//接收部门ID，多个间使用,隔开
				con.add("sysDeptByReceiveDeptId.deptId", Operator.ISNOTNULL, null);
				con.add("sysDeptByReceiveDeptId.deptId", Operator.IN, Arrays.asList(sysParams.getReceiveDeptid().split(",")));
			}
			/**if(StringUtils.isNotEmpty(sysParams.getReceiveDeptname())){//接收部门
				con.add("sysDeptByReceiveDeptId.deptname", Operator.LIKE, sysParams.getReceiveDeptname());
			}*/
			if(StringUtils.isNotEmpty(sysParams.getLxrmc())){//联系人
				con.add("lxrmc", Operator.LIKE, sysParams.getLxrmc());
			}
			if(StringUtils.isNotEmpty(sysParams.getSjhm())){//联系人手机号
				con.add("sjhm", Operator.LIKE, sysParams.getSjhm());
			}
			if(StringUtils.isNotEmpty(sysParams.getSfsjBegin())){//发送时间：开始时间
				con.add("sfsj", Operator.GE, DateUtil.parse(sysParams.getSfsjBegin(),"yyyy-MM-dd"));
			}
			if(StringUtils.isNotEmpty(sysParams.getSfsjEnd())){//发送时间：结束时间
				con.add("sfsj", Operator.LE, DateUtil.parse(sysParams.getSfsjEnd(),"yyyy-MM-dd"));
			}
			if(StringUtils.isNotEmpty(sysParams.getSfnr())){//发送内容
				con.add("sfnr", Operator.LIKE, sysParams.getSfnr());
			}
		}
		
		return con;
	}
	
	/**自由发送短信界面*/
	@Action("freesendlist")
	public String freesendlist(){
		
		List<SysDxmb> list = commonService.findAll(SysDxmb.class);
		request.setAttribute("list",list);
		
		return "success";
	}
	
	/**发送短信*/
	@Action("lxrlist")
	public String lxrlist(){
		Condition con = new Condition();
		con.add("telmobile",Operator.ISNOTNULL,null);
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getWorkunitstype())){
				con.add("sysOrganization",Operator.ISNOTNULL,null);
				con.add("sysOrganization.workunitstype",Operator.ISNOTNULL,null);
				con.add("sysOrganization.workunitstype",  Operator.IN, Arrays.asList(sysParams.getWorkunitstype().split(",")));
			}
			if(StringUtils.isNotEmpty(sysParams.getReceiveOrganizationname())){
				con.add("sysOrganization",Operator.ISNOTNULL,null);
				con.add("sysOrganization.organizationName", Operator.LIKE, sysParams.getReceiveOrganizationname());
			}
			if(StringUtils.isNotEmpty(sysParams.getReceiveDeptid())){//接收部门ID，多个间使用,隔开
				con.add("sysDept", Operator.ISNOTNULL, null);
				con.add("sysDept.deptId", Operator.IN, Arrays.asList(sysParams.getReceiveDeptid().split(",")));
			}
			/**if(StringUtils.isNotEmpty(sysParams.getReceiveDeptname())){//接收部门名称
				con.add("sysDept",Operator.ISNOTNULL,null);
				con.add("sysDept.deptname", Operator.LIKE, sysParams.getReceiveDeptname());
			}*/
			if(StringUtils.isNotEmpty(sysParams.getLxrmc())){
				con.add("userName",Operator.ISNOTNULL,null);
				con.add("userName", Operator.LIKE, sysParams.getLxrmc());
			}
			if(StringUtils.isNotEmpty(sysParams.getSjhm())){
				con.add("telmobile", Operator.LIKE, sysParams.getSjhm());
			}
		}
		Order order1 = new Order(Direction.ASC, "sysOrganization.organizationName");
//		Order order2 = new Order(Direction.ASC, "sysDept.deptname");
//		List<SysUser> userList = commonService.find(SysUser.class, con, new Sort(order1,order2));
		List<SysUser> userList = commonService.find(SysUser.class, con, new Sort(order1));
		List<SysUser> orderUserList = new ArrayList<SysUser>();
		int index = -1;
		if(userList!=null&&!userList.isEmpty()){
			for(int i=0;i<userList.size();i++){
				SysUser sysUser = userList.get(i);
				if(sysUser.getSysDept()!=null&&sysUser.getSysDept().getDeptname()!=null&&!sysUser.getSysDept().getDeptname().trim().equals("")){
					if(index==-1){
						index = i;
					}
					orderUserList.add(sysUser);
				}
			}
		}
		
		Collections.sort(orderUserList, new SysUserComparator());
		
		if(orderUserList.size()>0){
			userList.removeAll(orderUserList);
			userList.addAll(index,orderUserList);
		}
		
		request.setAttribute("userList",userList);
		
		List<SysDxmb> list = commonService.findAll(SysDxmb.class);
		request.setAttribute("list",list);
		
		return "success";
	}
	
	/**发送项目短信*/
	@Action("fsxmlist")
	public String fsxmlist(){
		service.findPjbaseinfoList(pagePjInfo, sysParams, getLoginUser());
		
		List<SysDxmb> list = commonService.findAll(SysDxmb.class);
		request.setAttribute("list",list);
		
		return "success";
	}
	
	
	@Action("getSfnr")
	public String getSfnr() {
		String dxmbId = request.getParameter("dxmbId");
		if(StringUtils.isNotEmpty(dxmbId)){
			/**List<SysDxmb> list = commonService.findAll(SysDxmb.class);
			if(list!=null&&!list.isEmpty()){
				for(SysDxmb dxmb:list){
					if(Integer.valueOf(dxmbId).equals(dxmb.getDxmbId())){
						String mb = dxmb.getMb();
						outPutMsg(true, mb);
						break;
					}
				}
			}*/
			SysDxmb obj = commonService.findOne(SysDxmb.class, Integer.valueOf(dxmbId));
			if(obj!=null){
				outPutMsg(true, obj.getMb());
			}
		}
		return null;
	}
	
//	@GzznLog
//	@Action("send")
//	public String send() {
//		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
//		id = request.getParameter("id");
//		String sfnr = request.getParameter("sfnr");
//		if (StringUtils.isNotEmpty(id)) {
//			List<String> sjhList = new ArrayList<String>();
//			List<SysDx> dxList = new ArrayList<SysDx>();
//			for (String i : id.split("@")) {
//				SysUser myUser = commonService.findOne(SysUser.class, Integer.parseInt(i)); 
//				if(myUser!=null&&myUser.getTelmobile()!=null){
//					//调用发送短信接口，发送短信
//					SysDx dx = new SysDx();
//					dx.setLxrmc(myUser.getUserName());
//					dx.setSfbj(2);
//					dx.setSfnr(sfnr);
//					dx.setSfsj(new Date());
//					dx.setSjhm(myUser.getTelmobile());
//					dx.setSysDeptByDeptId(user.getSysDept());
//					dx.setSysDeptByReceiveDeptId(myUser.getSysDept());
//					dx.setSysOrganization(myUser.getSysOrganization());
//					dx.setSysUser(user);
////					commonService.save(dx);
//					sjhList.add(myUser.getTelmobile());
//					dxList.add(dx);
//				}
//			}
//			if(sjhList.size()>0){
//				int size = sjhList.size();
//				String[] strs = new String[size];
//				for(int i=0;i<size;i++){
//					strs[i] = sjhList.get(i);
//				}
//				try {
//					smsService.invoke(strs,sfnr);
//					outPutMsg(true, "短信发送成功");
//					logObject = new LogObject("短信发送成功");
//					commonService.save(dxList);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		else{
//			logger.info("没找到联系人或没有联系人手机号");
//			outPutError("没找到联系人或没有联系人手机号，因此短信发送失败");
//		}
//
//		return null;
//	}
	
	@GzznLog
	@Action("freeSend")
	public String freeSend() {
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		id = request.getParameter("id");
		String sfnr = request.getParameter("sfnr");
		if (StringUtils.isNotEmpty(id)) {
			List<String> sjhList = new ArrayList<String>();
			List<SysDx> dxList = new ArrayList<SysDx>();
			for (String i : id.split(",")) {
				String telmobile = i;
				if(telmobile!=null&&!telmobile.trim().equals("")){
					//调用发送短信接口，发送短信
					SysDx dx = new SysDx();
					dx.setLxrmc(null);
					dx.setSfbj(2);
					dx.setSfnr(sfnr);
					dx.setSfsj(new Date());
					dx.setSjhm(telmobile);
					dx.setSysDeptByDeptId(user.getSysDept());
					dx.setSysDeptByReceiveDeptId(null);
					dx.setSysOrganization(null);
					dx.setSysUser(user);
//					commonService.save(dx);
					sjhList.add(telmobile);
					dxList.add(dx);
				}
			}
			if(sjhList.size()>0){
				int size = sjhList.size();
				String strs = "";
				for(int i=0;i<size;i++){
					if(strs.equals("")){
						strs = sjhList.get(i);
					}
					else{
						strs +=","+sjhList.get(i);
					}
				}
				try {
					String sql = " insert into api_mt_FGWGovInfo(SM_ID,SRC_ID,MOBILES,CONTENT) values(0,8888,'" + strs + "','" + sfnr +"')";
					logger.info(sql);
					jdbcTemplate2.update(sql);
					outPutMsg(true, "短信发送成功");
					logObject = new LogObject("短信发送成功");
					commonService.save(dxList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			logger.info("没找到联系人或没有联系人手机号");
			outPutError("没找到联系人或没有联系人手机号，因此短信发送失败");
		}

		return null;
	}
	
	@GzznLog
	@Action("send")
	public String send() {
		SysUser user = (SysUser) request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		id = request.getParameter("id");
		String sfnr = request.getParameter("sfnr");
		if (StringUtils.isNotEmpty(id)) {
			List<String> sjhList = new ArrayList<String>();
			List<SysDx> dxList = new ArrayList<SysDx>();
			for (String i : id.split("@")) {
				SysUser myUser = commonService.findOne(SysUser.class, Integer.parseInt(i)); 
				if(myUser!=null&&myUser.getTelmobile()!=null&&!myUser.getTelmobile().trim().equals("")){
					//调用发送短信接口，发送短信
					SysDx dx = new SysDx();
					dx.setLxrmc(myUser.getUserName());
					dx.setSfbj(2);
					dx.setSfnr(sfnr);
					dx.setSfsj(new Date());
					dx.setSjhm(myUser.getTelmobile());
					dx.setSysDeptByDeptId(user.getSysDept());
					dx.setSysDeptByReceiveDeptId(myUser.getSysDept());
					dx.setSysOrganization(myUser.getSysOrganization());
					dx.setSysUser(user);
//					commonService.save(dx);
					sjhList.add(myUser.getTelmobile());
					dxList.add(dx);
				}
			}
			if(sjhList.size()>0){
				int size = sjhList.size();
				String strs = "";
				for(int i=0;i<size;i++){
					if(strs.equals("")){
						strs = sjhList.get(i);
					}
					else{
						strs +=","+sjhList.get(i);
					}
				}
				try {
					String sql = " insert into api_mt_FGWGovInfo(SM_ID,SRC_ID,MOBILES,CONTENT) values(0,8888,'" + strs + "','" + sfnr +"')";
					logger.info(sql);
					jdbcTemplate2.update(sql);
					outPutMsg(true, "短信发送成功");
					logObject = new LogObject("短信发送成功");
					commonService.save(dxList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			logger.info("没找到联系人或没有联系人手机号");
			outPutError("没找到联系人或没有联系人手机号，因此短信发送失败");
		}
		
		return null;
	}
	
	/**
	 * 发送项目短信
	 * @return
	 */
	@GzznLog
	@Action("fsxmSend")
	public String fsxmSend() {
		StringBuilder msg = new StringBuilder();
		int[] msgCount = new int[2];//msgCount[0]成功条数、msgCount[1]失败条数;
		Boolean flag = false;
		String saveSfnr = "";//保存到库表中的发送内容
		SysUser user = getLoginUser();//当前操作用户
		Pjbaseinfo objPj = null;
		String[] mobilePhone = new String[1];
		if (StringUtils.isNotEmpty(id)) {
			for (String i : id.split("@")) {//项目ID
				objPj = commonService.findOne(Pjbaseinfo.class, Integer.parseInt(i)); 
				if(objPj!=null){
					if(objPj.getMobilePhone()!=null){
						saveSfnr = this.getSaveSfnr(dxsfnr, objPj);
						if(ValidateUtil.isStrByteLenNotOut(saveSfnr, 600)){//验证短信内容是否超过600个字符
							SysDx dx = new SysDx();
							dx.setSysUser(user);//发送用户
							dx.setSysDeptByDeptId(user.getSysDept());//发送部门
							//dx.setSysDeptByReceiveDeptId();//接收部门
							dx.setSysOrganization(objPj.getSysOrganizationByDeclareunitsid());//短信接收单位：项目业主
							dx.setLxrmc(objPj.getDeclarerid());//联系人名称
							dx.setSjhm(objPj.getMobilePhone());//手机号码
							dx.setSfsj(new Date());//发送时间
							dx.setSfnr(saveSfnr);//发送内容
							
							//发送短信信息：只能一条一条的发
							try {
								mobilePhone[0] = objPj.getMobilePhone();
//								smsService.invoke(mobilePhone, saveSfnr);
								String sql = " insert into api_mt_FGWGovInfo(SM_ID,SRC_ID,MOBILES,CONTENT) values(0,8888,'" + mobilePhone[0] + "','" + saveSfnr +"')";
								logger.info(sql);
								jdbcTemplate2.update(sql);
								msgCount[0]++;
								flag = true;
								dx.setSfbj(2);//发送标记：已发送
								commonService.save(dx);//将该信息保存至短信表中
							} catch (Exception e) {
								e.printStackTrace();
								msg.append(objPj.getProjectname()).append("：发送短信失败<br>");
								msgCount[1]++;
							}
						}else{
							msg.append(objPj.getProjectname()).append("：的短信内容超过300个字<br>");
							msgCount[1]++;
						}
					}else{
						msg.append(objPj.getProjectname()).append("：的项目联系人手机号不存在<br>");
						msgCount[1]++;
					}
				}else{
					msg.append("没有找到相关项目信息<br>");
					msgCount[1]++;
				}
			}
		}
		msg.append("<font color='red'>发送项目短信完成：其中成功发送 " + msgCount[0]+ " 条，失败发送 "+ msgCount[1] + " 条</font>");
		outPutMsg(flag, msg.toString());
		logObject = new LogObject("发送项目短信完成：其中成功发送 " + msgCount[0]+ " 条，失败发送 "+ msgCount[1] + " 条");

		return null;
	}
	
	/**
	 * 关键字替换：<项目业主>，贵单位报送的“<项目名称>”存在投资额(万元)错误的问题，现退回请改正后再次申报。<发送日期>
	 * @param sfnr
	 * @param objPj
	 * @return
	 */
	private String getSaveSfnr(String dxsfnr,Pjbaseinfo objPj){
		String saveSfnr = dxsfnr;
		if(objPj.getSysOrganizationByDeclareunitsid()!=null){//项目业主不为空
			saveSfnr = saveSfnr.replace("<项目业主>", objPj.getSysOrganizationByDeclareunitsid().getOrganizationName());
		}
		saveSfnr = saveSfnr.replace("<项目名称>", objPj.getProjectname());
		saveSfnr = saveSfnr.replace("<发送日期>", DateUtil.format(new Date(), "yyyy-MM-dd"));
		
		return saveSfnr;
	}
	
	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysDx.class, Integer.parseInt(id));
		}
		else{
			obj = new SysDx();
		}
		return "success";
	}
	
	@Action("view")
	public String view(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysDx.class, Integer.parseInt(id));
		}
		
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			commonService.save(obj);
			logObject = new LogObject("信息",obj.getDxId(),"接收单位："+obj.getSysOrganization().getOrganizationName()+",接收部门："+(obj.getSysDeptByReceiveDeptId()!=null?obj.getSysDeptByReceiveDeptId().getDeptname():"")
					+",接收人："+obj.getLxrmc()+",联系人手机号码："+obj.getSjhm(),null);
		} catch (Exception e) {
			message = "保存数据失败";
		}

		return "success";
	}
	
	@GzznLog
	@Action(value = "delete", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String delete(){
		message = "删除数据成功";
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					commonService.delete(SysDx.class, Integer.parseInt(i)); 
				}
				logObject = new LogObject("删除短信，ids=" + id);
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		return "success";
	}
	
	
/**	@Action("rolePermission")
	public String rolePermission(){
		
		
		return "success";
	}*/
	
	
	
	
	
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SysQueryParam getSysParams() {
		return sysParams;
	}
	public void setSysParams(SysQueryParam sysParams) {
		this.sysParams = sysParams;
	}
	public SysDx getObj() {
		return obj;
	}
	public void setObj(SysDx obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<SysDx> getPage() {
		return page;
	}
	public void setPage(PageUtil<SysDx> page) {
		this.page = page;
	}
	

	public PageUtil<Pjbaseinfo> getPagePjInfo() {
		return pagePjInfo;
	}

	public void setPagePjInfo(PageUtil<Pjbaseinfo> pagePjInfo) {
		this.pagePjInfo = pagePjInfo;
	}

	public Map<Integer, String> getXmztMap() {
		xmztMap = putDictionaryitemsToMap(xmztMap, IConstants.DICTIONARY_ITEM_XMZT);
		return xmztMap;
	}

	public void setXmztMap(Map<Integer, String> xmztMap) {
		this.xmztMap = xmztMap;
	}
	public String getDxmb() {
		return dxmb;
	}
	public void setDxmb(String dxmb) {
		this.dxmb = dxmb;
	}

	/**
	 * 取出在用户登录时通过方法{@link com.gzzn.fgw.action.sys.LoginAction.findDirectionaryitems()
	 * 存入的{@link CommonFiled.SESSION_DIRECTIONARYITEMS}状态表。并按照name取出对应的记录，
	 * 然后检索每一条记录，组成键值对
	 * 
	 * @param map 结果存放的集合
	 * @param name 筛选状态字段表的条件
	 * @return map 结果存放的集合
	 */
	private Map<Integer, String> putDictionaryitemsToMap(Map<Integer,String> map,String name){
		@SuppressWarnings("unchecked")
		List<SysDictionaryitems> list = (List<SysDictionaryitems>) getHttpSession().getAttribute(CommonFiled.SESSION_DIRECTIONARYITEMS);
		if(list!=null&&!list.isEmpty()){
			for(SysDictionaryitems dictionaryitems:list){
				if(dictionaryitems.getName()!=null&&dictionaryitems.getName().equals(name)){
					map.put(Integer.valueOf(dictionaryitems.getItemvalue()),dictionaryitems.getItemtext());
				}
			}
		}
		return map;
	}

	public String getDxsfnr() {
		return dxsfnr;
	}

	public void setDxsfnr(String dxsfnr) {
		this.dxsfnr = dxsfnr;
	}

	public static class SysUserComparator implements Comparator<SysUser>{
		
		public int compare(SysUser o1, SysUser o2) {
			SysUser object1 = (SysUser) o1;
			SysUser object2 = (SysUser) o2;
			
			if(object1.getSysDept()==null&&object2.getSysDept()!=null){
				return 1;
			}
			else if(object1.getSysDept()!=null&&object2.getSysDept()==null){
				return -1;
			}
			else if(object1.getSysDept()==null&&object2.getSysDept()==null){
				return 1;
			}
			
			int result = object1.getSysDept().getLevelnumber().compareTo(object2.getSysDept().getLevelnumber());
			return result;
		}
	}
	

	

}
