package com.gzzn.fgw.action.sys;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.cfg.beanvalidation.GroupsPerOperation.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysXqService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.util.web.PageUtil;
/**
 * <p>Title: SysXqAction</p>
 * <p>Description: 辖区信息维护  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-18 下午4:47:41 amzhang  new
 */
@Namespace(value = "/sys/xq")
public class SysXqAction extends BaseAction<SysXq> {
	
	private String id;//编辑或删除的id，多个间用@隔开
	private String xqmc;//辖区名称
	private String xzqydm;//行政区域代码
	private SysXq obj;//对象
	private String message;//返回页面信息
	private PageUtil<SysXq> page = new PageUtil<SysXq>();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysXqService service;
	
	
	@Action("list")
	public String list(){
		Condition con = new Condition();
		//con.add("deleted", Operator.ISNOTNULL, null);//删除标记：0-	非删除 1-	逻辑删除
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//删除标记：0-	非删除 1-	逻辑删除
		if(StringUtils.isNotEmpty(xqmc)){
			con.add("xqmc", Operator.LIKE, xqmc);
		}
		if(StringUtils.isNotEmpty(xzqydm)){
			con.add("xzqydm", Operator.LIKE, xzqydm);
		}
		service.loadData(SysXq.class, page, con);
		
		return "success";
	}
	
	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysXq.class, Integer.parseInt(id));
		}
		else{
			obj = new SysXq();
		}
		
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			SysXq objUp = null;
			if(obj.getSjxq().getXqId()!=null && obj.getSjxq().getXqId()>0){
				objUp = commonService.findOne(SysXq.class, obj.getSjxq().getXqId());
			}
			obj.setSjxq(objUp);
			obj.setDeleted(obj.getDeleted()==null?IConstants.DEL_FLAG_FALSE:obj.getDeleted());
			commonService.saveOrUpdate(obj);
			logObject = new LogObject("辖区信息",obj.getXqId(),obj.getXqmc(),null);
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
				SysXq objDel = null;
				for (String i : id.split("@")) {
					//commonService.delete(SysXq.class, Integer.parseInt(i)); 
					//逻辑删除行业信息、下级行业的信息也进行逻辑删除
					objDel = commonService.findOne(SysXq.class, Integer.parseInt(i)); 
					if(objDel!=null){
						objDel.setDeleted(IConstants.DEL_FLAG_TRUE);//标记删除
						commonService.update(objDel);
						logicDelSysXq(objDel);//删除全部的下级辖区信息
					}
				}
				logObject = new LogObject("删除辖区信息，ids=" + id);
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		return "success";
	}
	
	/**
	 * 逻辑删除辖区信息的下级辖区的信息
	 * @param listDel
	 * @param objDel
	 */
	private void logicDelSysXq(SysXq objDel){
		//Set<SysXq> setChild = objDel.getSysXqs();
		//for(SysXq objChild : setChild){
		Condition con = new Condition();
		con.add("sjxq", Operator.ISNOTNULL, null);
		con.add("sjxq.xqId", Operator.EQ, objDel.getXqId());
		List<SysXq> listChild = commonService.find(SysXq.class, con);
		for(SysXq objChild : listChild){
			if(objChild.getDeleted().equals(IConstants.DEL_FLAG_FALSE)){
				objChild.setDeleted(IConstants.DEL_FLAG_TRUE);
				commonService.update(objChild);
				logicDelSysXq(objChild);//递归调用
			}
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SysXq getObj() {
		return obj;
	}
	public void setObj(SysXq obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<SysXq> getPage() {
		return page;
	}
	public void setPage(PageUtil<SysXq> page) {
		this.page = page;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXzqydm() {
		return xzqydm;
	}

	public void setXzqydm(String xzqydm) {
		this.xzqydm = xzqydm;
	}

}
