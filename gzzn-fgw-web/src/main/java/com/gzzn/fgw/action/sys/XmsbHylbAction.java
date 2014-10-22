package com.gzzn.fgw.action.sys;

import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Sort;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.Sort.Direction;
import com.gzzn.common.persist.Sort.Order;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.XmsbHylb;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.IXmsbHylbService;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.util.web.PageUtil;
/**
 * <p>Title: XmsbHylbAction</p>
 * <p>Description: 行业类型信息维护  </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-2-18 下午4:47:41 amzhang  new
 */
@Namespace(value = "/sys/hylb")
public class XmsbHylbAction extends BaseAction<XmsbHylb> {
	
	private String id;//编辑或删除的id，多个间用@隔开
	private XmsbHylb obj;//对象
	private String message;//返回页面信息
	private PageUtil<XmsbHylb> page = new PageUtil<XmsbHylb>();
	private String hylbmc;
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IXmsbHylbService service;
	
	
	@Action("list")
	public String list(){
		Condition con = new Condition();
		//con.add("deleted", Operator.ISNOTNULL, null);//删除标记：0-	非删除 1-	逻辑删除
		con.add("deleted", Operator.EQ, IConstants.DEL_FLAG_FALSE);//删除标记：0-	非删除 1-	逻辑删除
		if(StringUtils.isNotEmpty(id)){
			con.add("xmsbHylb.hylbId",Operator.ISNOTNULL,null);
			con.add("xmsbHylb.hylbId", Operator.IN, Arrays.asList(id.split(",")));
		}
		if(StringUtils.isNotEmpty(hylbmc)){
			con.add("hylbmc", Operator.LIKE, hylbmc);
		}
		Order order1 =  new Order(Direction.ASC, "xmsbHylb.hylbId");
		Order order2 =  new Order(Direction.ASC, "hylbId");
		Sort sort = new Sort(order1, order2);
		service.loadData(XmsbHylb.class, page, con, sort);
		
		return "success";
	}
	
	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(XmsbHylb.class, Integer.parseInt(id));
		}
		else{
			obj = new XmsbHylb();
		}
		
		return "success";
	}
	
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			XmsbHylb objUp = null;
			if( obj.getXmsbHylb().getHylbId()!=null && obj.getXmsbHylb().getHylbId()>0){
				objUp = commonService.findOne(XmsbHylb.class, obj.getXmsbHylb().getHylbId());
			}
			obj.setXmsbHylb(objUp);
			obj.setDeleted(obj.getDeleted()==null?IConstants.DEL_FLAG_FALSE:obj.getDeleted());
			commonService.saveOrUpdate(obj);
			
			logObject = new LogObject("行业分类信息", obj.getHylbId(), obj.getHylbmc());
		} catch (Exception e) {
			e.printStackTrace();
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
			XmsbHylb objDel = null;
			if (StringUtils.isNotEmpty(id)) {
				for (String i : id.split("@")) {
					//commonService.delete(XmsbHylb.class, Integer.parseInt(i)); 
					//逻辑删除行业信息、下级行业的信息也进行逻辑删除
					objDel = commonService.findOne(XmsbHylb.class, Integer.parseInt(i)); 
					if(objDel!=null){
						objDel.setDeleted(IConstants.DEL_FLAG_TRUE);//标记删除
						commonService.update(objDel);
						logicDelXmsbHylb(objDel);//删除全部的下级行业信息
					}
				}
				logObject = new LogObject("删除行业分类信息，ids=" + id);
			}
		} catch (Exception e) {
			logger.error("",e);
			message = "删除数据失败";
		}

		return "success";
	}
	
	/**
	 * 逻辑删除行业信息的下级行业的信息
	 * @param listDel
	 * @param objDel
	 */
	private void logicDelXmsbHylb(XmsbHylb objDel){
		Set<XmsbHylb> setChild = objDel.getXmsbHylbs();
		for(XmsbHylb objChild : setChild){
			if(objChild.getDeleted().equals(IConstants.DEL_FLAG_FALSE)){
				objChild.setDeleted(IConstants.DEL_FLAG_TRUE);
				commonService.update(objChild);
				logicDelXmsbHylb(objChild);//递归调用
			}
		}
	}
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public XmsbHylb getObj() {
		return obj;
	}
	public void setObj(XmsbHylb obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<XmsbHylb> getPage() {
		return page;
	}
	public void setPage(PageUtil<XmsbHylb> page) {
		this.page = page;
	}

	public String getHylbmc() {
		return hylbmc;
	}

	public void setHylbmc(String hylbmc) {
		this.hylbmc = hylbmc;
	}

}
