package com.gzzn.fgw.action.sys;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.fgw.action.BaseAction;
import com.gzzn.fgw.aop.GzznLog;
import com.gzzn.fgw.aop.LogObject;
import com.gzzn.fgw.aop.LogType;
import com.gzzn.fgw.model.SysDxmb;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.service.sys.ISysDxmbService;
import com.gzzn.fgw.service.sys.ISysRoleService;
import com.gzzn.fgw.service.sys.pojo.SysQueryParam;
import com.gzzn.util.web.PageUtil;
/**
 * <p>Title: SysDxmbAction</p>
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
@Namespace(value = "/sys/dxmb")
public class SysDxmbAction extends BaseAction<SysDxmb> {
	
	private String id;//编辑或删除的id，多个间用@隔开
	private SysQueryParam sysParams;//系统管理查询参数
	private SysDxmb obj;//对象
	private String message;//返回页面信息
	private PageUtil<SysDxmb> page = new PageUtil<SysDxmb>();
	
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ISysRoleService roleService;
	@Autowired
	private ISysDxmbService service;
	
	
	@Action("list")
	public String list(){
		Condition con = new Condition();
		if(sysParams != null){
			if(StringUtils.isNotEmpty(sysParams.getMbmc())){
				con.add("mbmc", Operator.LIKE, sysParams.getMbmc());
			}
		}
		service.loadData(SysDxmb.class, page, con);
		
		return "success";
	}
	
	@Action("edit")
	public String edit(){
		if(StringUtils.isNotEmpty(id)){
			obj = commonService.findOne(SysDxmb.class, Integer.parseInt(id));
		}
		else{
			obj = new SysDxmb();
		}
		
		return "success";
	}
	
	/**
     * 验证模板名称是否重复
     * @return
     */
	@Action("checkRepeat")
    public String checkRepeat(){
    	
    	String mbmc = request.getParameter("mbmc");
    	
    	if(id!=null&&!id.trim().equals("")){
    		
    		Condition con = new Condition();
    		
    		con.add("mbmc", Operator.ISNOTNULL,null);
    		con.add("mbmc", Operator.EQ,mbmc);
    		con.add("dxmbId", Operator.NE,Integer.valueOf(id));
			
			List<SysDxmb> entitys = (List<SysDxmb>) commonService.find(SysDxmb.class,con);
			
			if(entitys!=null&&!entitys.isEmpty()){
				
				outPutError("模板名称重复");
				
			}
			else{
				
				outPutMsg(true,"OK");
			}
			
    	}
    	else{
    		
    		Condition con = new Condition();
    		
    		con.add("mbmc", Operator.ISNOTNULL,null);
    		con.add("mbmc", Operator.EQ,mbmc);
			
			List<SysDxmb> entitys = (List<SysDxmb>) commonService.find(SysDxmb.class,con);
			
			
			if(entitys!=null&&!entitys.isEmpty()){
				
				outPutError("模板名称重复");
				
			}
			else{
				outPutMsg(true,"OK");
			}
    	}
    	return null;
    }
	
	@GzznLog(LogType.SAVE)
	@Action(value = "save", results = { @Result(location = "list.ac", type = "redirectAction", params = {
			"message", "${message}", "encode", "true" }) })
	public String save(){
		message = "保存数据成功";
		try {
			commonService.saveOrUpdate(obj);
			logObject = new LogObject("信息",obj.getDxmbId(),obj.getMbmc(),null);
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
					commonService.delete(SysDxmb.class, Integer.parseInt(i)); 
				}
				logObject = new LogObject("删除短信模板，ids=" + id);
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
	public SysDxmb getObj() {
		return obj;
	}
	public void setObj(SysDxmb obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PageUtil<SysDxmb> getPage() {
		return page;
	}
	public void setPage(PageUtil<SysDxmb> page) {
		this.page = page;
	}

}
