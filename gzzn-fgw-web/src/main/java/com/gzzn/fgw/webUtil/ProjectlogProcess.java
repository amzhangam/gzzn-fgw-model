package com.gzzn.fgw.webUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysProjectlog;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.model.SysXq;
import com.gzzn.fgw.model.XmsbHylb;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.model.XmsbZjxz;
import com.gzzn.fgw.service.ICommonService;

public class ProjectlogProcess {
	
	private static Properties prop = PropertiesUtil.getProperties("projectProperty.properties");
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void createProjectLog(Pjbaseinfo beforeSourceObj,Pjbaseinfo afterSourceObj,
			Projectinvest beforeProjectinvest,Projectinvest afterProjectinvest,
			SysUser user,ICommonService commonService)throws Exception{
		SysProjectlog projectlog = new SysProjectlog();
		if(user.getSysOrganization()!=null){
			projectlog.setSysOrganization(user.getSysOrganization());
		}
		if (user.getSysDept() != null) {
			projectlog.setSysDept(user.getSysDept());
		}
			Method[] srcMethods = beforeSourceObj.getClass().getMethods();
			Method[] srcMethods2 = beforeProjectinvest.getClass().getMethods();
			Map record = new HashMap();
			for (Method mm : srcMethods) {
				String name = mm.getName();// 方法名称
				// 过滤掉目标对象的set方法
				if (name.startsWith("set")) {
					continue;
				}
				// 找到源对象中对应的get方法
				if (name.startsWith("get") && prop.containsKey(name.substring(3).toUpperCase())) {
					try {
						
						Object o1 = mm.invoke(beforeSourceObj);
						Object o2 = mm.invoke(afterSourceObj);
						if(!isEqualObject(o1,o2)){
							if(o1!=null&&o2!=null){
								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
										String value = "修改前值为："+o1+" \n修改后值为："+o2;;
										record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof Date){
									String value = "修改前值为："+sdf.format((Date)o1)+" \n修改后值为："+sdf.format((Date)o2);
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysOrganization){
									String value = "修改前值为："+((SysOrganization)o1).getOrganizationName()+" \n修改后值为："+((SysOrganization)o2).getOrganizationName();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysUser){
									String value = "修改前值为："+((SysUser)o1).getUserName()+" \n修改后值为："+((SysUser)o2).getUserName();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysXq){
									String value = "修改前值为："+((SysXq)o1).getXqmc()+" \n修改后值为："+((SysXq)o2).getXqmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbHylb){
									String value = "修改前值为："+((XmsbHylb)o1).getHylbmc()+" \n修改后值为："+((XmsbHylb)o2).getHylbmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbXmlx){
									String value = "修改前值为："+((XmsbXmlx)o1).getXmlxmc()+" \n修改后值为："+((XmsbXmlx)o2).getXmlxmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbZjxz){
									String value = "修改前值为："+((XmsbZjxz)o1).getZjxzmc()+" \n修改后值为："+((XmsbZjxz)o2).getZjxzmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
							}
							else if(o1!=null&&o2==null){
								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
									String value = "修改前值为："+o1+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof Date){
									String value = "修改前值为："+sdf.format((Date)o1)+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysOrganization){
									String value = "修改前值为："+((SysOrganization)o1).getOrganizationName()+" \n修改后值为："+((SysOrganization)o2).getOrganizationName();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysUser){
									String value = "修改前值为："+((SysUser)o1).getUserName()+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysXq){
									String value = "修改前值为："+((SysXq)o1).getXqmc()+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbHylb){
									String value = "修改前值为："+((XmsbHylb)o1).getHylbmc()+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbXmlx){
									String value = "修改前值为："+((XmsbXmlx)o1).getXmlxmc()+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbZjxz){
									String value = "修改前值为："+((XmsbZjxz)o1).getZjxzmc()+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
							}
							else if(o1==null&&o2!=null){
								if(o2 instanceof String || o2 instanceof Integer || o2 instanceof Double){
									String value = "修改前值为：  \n修改后值为："+o2;
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof Date){
									String value = "修改前值为：  \n修改后值为："+sdf.format((Date)o2);
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysOrganization){
									String value = "修改前值为：  \n修改后值为："+((SysOrganization)o2).getOrganizationName();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysUser){
									String value = "修改前值为：  \n修改后值为："+((SysUser)o2).getUserName();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof SysXq){
									String value = "修改前值为：  \n修改后值为："+((SysXq)o2).getXqmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbHylb){
									String value = "修改前值为：  \n修改后值为："+((XmsbHylb)o2).getHylbmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbXmlx){
									String value = "修改前值为：  \n修改后值为："+((XmsbXmlx)o2).getXmlxmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
								else if(o1 instanceof XmsbZjxz){
									String value = "修改前值为：  \n修改后值为："+((XmsbZjxz)o2).getZjxzmc();
									record.put(name.substring(3).toUpperCase(), value);
								}
							}
							
						}
						
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			}
	        
			for (Method mm : srcMethods2) {
				String name = mm.getName();// 方法名称
				// 过滤掉目标对象的set方法
				if (name.startsWith("set")) {
					continue;
				}
				// 找到源对象中对应的get方法
				if (name.startsWith("get") && prop.containsKey(name.substring(3).toUpperCase())) {
					try {
						
						Object o1 = mm.invoke(beforeProjectinvest);
						Object o2 = mm.invoke(afterProjectinvest);
						if(!isEqualObject(o1,o2)){
							if(o1!=null&&o2!=null){
								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
										String value = "修改前值为："+o1+" \n修改后值为："+o2;;
										record.put(name.substring(3).toUpperCase(), value);
								}
							}
							else if(o1!=null&&o2==null){
								if(o1 instanceof String || o1 instanceof Integer || o1 instanceof Double){
									String value = "修改前值为："+o1+" \n修改后值为：";
									record.put(name.substring(3).toUpperCase(), value);
								}
							}
							else if(o1==null&&o2!=null){
								if(o2 instanceof String || o2 instanceof Integer || o2 instanceof Double){
									String value = "修改前值为：  \n修改后值为："+o2;
									record.put(name.substring(3).toUpperCase(), value);
								}
							}
							
						}
						
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			}
			
	        String contentString = "申报项目基本信息的修改情况：\n";
	        Iterator iterator = record.keySet().iterator();
	        while (iterator.hasNext()) {
				Object key = (Object) iterator.next();
				Object value = record.get(key);
//				System.out.println(prop.getProperty(key.toString())+" "+value);
				contentString+="\n" + prop.getProperty(key.toString())+"\n";
				contentString+=value+"\n";
			}
	        
	        try {
				projectlog.setOperationContent(contentString.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		projectlog.setSysUser(user);
		projectlog.setOperationDatetime(new Date());//new Timestamp(System.currentTimeMillis())
		projectlog.setPjbaseinfo(beforeSourceObj);
		commonService.save(projectlog);
	}

	private static boolean isEqualObject(Object obj1,Object obj2){
		boolean b = false;
		if((obj1==null||obj1.equals(""))&&(obj2!=null&&!obj2.equals(""))){
			return b;
		}
		else if(obj1!=null&&!obj1.equals("")&&(obj2==null||obj2.equals(""))){
			return b;
		}
		else if((obj1==null||obj1.equals(""))&&(obj2==null||obj2.equals(""))){
			b = true;
		}
		else{
			if(obj1 instanceof SysOrganization){
				
				if(((SysOrganization)obj1).getOrganizationId().equals(((SysOrganization)obj2).getOrganizationId())){
					b = true;
				}
			}
			else if(obj1 instanceof SysUser){
				if(((SysUser)obj1).getUserId().equals(((SysUser)obj2).getUserId())){
					b = true;
				}
			}
			else if(obj1 instanceof SysXq){
				if(((SysXq)obj1).getXqId().equals(((SysXq)obj2).getXqId())){
					b = true;
				}
			}
			else if(obj1 instanceof XmsbHylb){
				if(((XmsbHylb)obj1).getHylbId().equals(((XmsbHylb)obj2).getHylbId())){
					b = true;
				}
			}
			else if(obj1 instanceof XmsbXmlx){
				if(((XmsbXmlx)obj1).getXmlxId().equals(((XmsbXmlx)obj2).getXmlxId())){
					b = true;
				}
			}
			else if(obj1 instanceof XmsbZjxz){
				if(((XmsbZjxz)obj1).getZjxzId().equals(((XmsbZjxz)obj2).getZjxzId())){
					b = true;
				}
			}
			else if(obj1 instanceof String){
				if(((String)obj1).trim().equals(((String)obj2).trim())){
					b = true;
				}
			}
			else{
				if(obj1.equals(obj2)){
					b = true;
				}
			}
		}
		return b;
	}
}
