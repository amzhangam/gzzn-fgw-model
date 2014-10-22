package com.gzzn.fgw.implExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.common.persist.IEntityDao;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.webUtil.BusinessException;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.ExcelHelper;
import com.gzzn.fgw.webUtil.ValidateUtil;
import com.gzzn.util.common.DateUtil;

/**
 * Excel数据操作
 * @author lhq
 * @date 2013.7.8
 * @version v1.0
 */
@Transactional
public abstract class ExcelImport<T,PK extends Serializable>  {
	
	@Resource
	private IEntityDao entityDao;
		
		protected SysUser user;
		protected SysOrganization sysOrgn;//单位信息
		protected Boolean flag = true;
		protected List<String> msg = new ArrayList<String>();
		protected List<T> list = new ArrayList<T>();
		protected int[] msgCount = new int[3];
		protected String tbdw;//填报单位（盖章）
		protected Map<String,Object> retnMap = new HashMap<String,Object>();
		
		/**
		 * 上传数据总控制方法
		 * @param file
		 */
		public Map<String,Object> parseFileMap(File file,HttpSession session,String impType,String fileSavePath){
			//System.out.println("msg"+(msg==null?"null":msg.toString()));
			init(session);
			
			int beginRow = 0;//第0行存放字段名称，从第二行开始存放的是数据
			Workbook workbook = null;
			try {
				FileInputStream inputStream = new FileInputStream(file);
				workbook = Workbook.getWorkbook(inputStream);
				
				Sheet sheet = workbook.getSheet(0);
				if (impType.equals("PjBulidInfoTemp")) {
					Boolean flagExl = this.checkPjBulidInfoTempExcel(sheet);
					if (flagExl) {
						beginRow = 3;
						msg.add("从第4行开始解析.");
						this.saveTempFile(file, fileSavePath);//保存临时上传的文件
					} else {
						flag = false;
						return getReturnMap();
					}
				}
				list = readDatas(sheet, beginRow, user, sysOrgn);//获取Excel中的全部相关数据
			} catch (FileNotFoundException e) {
				flag = false;
				msg.add("找不到此文件.");
				return getReturnMap();
			} catch (BiffException e) {
				flag = false;
				msg.add("请导入正确的Excel文件.");
				return getReturnMap();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return getReturnMap();//返回相关对象
		}
		
		/**
		 * 获取返回的相关数据
		 */
		private Map<String,Object> getReturnMap(){
			retnMap.put("flag", flag);
			retnMap.put("list", list);
			retnMap.put("msg", msg);
			retnMap.put("msgCount", msgCount);
			retnMap.put("tbdw", tbdw);
			
			clearCache();//清空Cache的相关数据
			return retnMap;
		}
		
		/**
		 * 上传数据总控制方法
		 * @param file
		 */
		@SuppressWarnings("finally")
		public List<String> parseFile(File file,HttpSession session,String impType,String fileSavePath){
			//System.out.println("msg"+(msg==null?"null":msg.toString()));
			long begin = System.currentTimeMillis();
			init(session);
			
			int beginRow = 0;//第0行存放字段名称，从第二行开始存放的是数据
			
			Workbook workbook = null;
			try {
				FileInputStream inputStream = new FileInputStream(file);
				workbook = Workbook.getWorkbook(inputStream);
			} catch (FileNotFoundException e) {
				msg.add("找不到此文件.");
				return msg;
			} catch (BiffException e) {
				msg.add("请导入正确的Excel文件.");
				return msg;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		    Sheet sheet = workbook.getSheet(0);
			if (impType.equals("PjBulidInfoTemp")) {
				Boolean flag = this.checkPjBulidInfoTempExcel(sheet);
				if (flag) {
					beginRow = 3;
					msg.add("从第4行开始解析.");
	                this.saveTempFile(file, fileSavePath);
				} else {
					clearCache();// 清空Cache的相关数据
					return msg;
				}
			}
			//List<T> datas = readDatas(sheet, beginRow, user, sysOrgn);//获取Excel中的全部相关数据
			list = readDatas(sheet, beginRow, user, sysOrgn);//获取Excel中的全部相关数据
			try {
				for (T t : list) {
					entityDao.save(t);//保存数据
					//dao.merge(t);
					msgCount[2]++;
				}
				//msg.add("导入数据完成,其中错误数据<b>"+msgCount[0]+"</b>条,重复数据</b>"+msgCount[1]+"</b>条,成功导入<b>"+msgCount[2]+"</b>条,耗时"+((System.currentTimeMillis()-begin)/1000)+"秒");
				msg.add("导入数据完成,其中错误数据&nbsp;&nbsp;<b>"+msgCount[0]+"</b>&nbsp;&nbsp;条,成功导入&nbsp;&nbsp;<b>"+msgCount[2]+"</b>&nbsp;&nbsp;条,耗时&nbsp;&nbsp;"+((System.currentTimeMillis()-begin)/1000)+"&nbsp;&nbsp;秒");
			} catch (Exception e) {
				e.printStackTrace();
				msg.add("导入异常");
				throw new BusinessException("导入异常");
			}finally{
				clearCache();//清空Cache的相关数据
				return msg;
			}
		}
		
		
		
		private void init(HttpSession session){
			user = (SysUser) session.getAttribute(CommonFiled.SESSION_LOGIN_USER);
			flag = true;
			list = new ArrayList<T>();
			msg = new ArrayList<String>();
			msgCount = new int[3];
			tbdw = "";
			retnMap = new HashMap<String,Object>();
		}
		
		/**
		 * 将上传的文件保存到临时文件目录中
		 * @param file
		 * @param fileSavePath
		 */
		private void saveTempFile(File file, String fileSavePath){
			if(file!=null){
				// 判断路径是否已经存在
                File savedir=new File(fileSavePath);
                if(!savedir.exists()) {
                	savedir.mkdirs(); 
                }
                // 创建文件对象
				File saveFile = new File(savedir, user.getLoginName() + "【"
						+ user.getUserId() + "】_"
						+ DateUtil.format(new Date(), "yyyyMMddHHmm")
						+ ".xls");
				try {
					FileUtils.copyFile(file, saveFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/***
		 * Excel表中第三行：填报单位（盖章）、项目属地（按市本级，各区、县级市填写）不允许为空；
		 * 填报单位需要在库表中能查询到，填写的属地应该=单位所在的属地 第四行：1-名称、2-产业类别、5-总投资（万元）、10-项目类别
		 * 不允许为空
		 */
		private Boolean checkPjBulidInfoTempExcel(Sheet sheet){
			Boolean flag = true;
			if (ExcelHelper.getStringValue(sheet.getCell(0, 1)).indexOf("填报单位") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(1, 2)).equals("项目名称")
					&& ExcelHelper.getStringValue(sheet.getCell(4, 2)).equals("总投资（万元）")
					&& ExcelHelper.getStringValue(sheet.getCell(11, 2)).indexOf("项目类型") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(12, 2)).indexOf("项目类别") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(13, 2)).indexOf("产业类别") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(15, 2)).indexOf("所属行业") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(19, 2)).indexOf("项目属地") > -1) {
				// 格式符合相关的要求，验证单位信息
				String value = ExcelHelper.getStringValue(sheet.getCell(2, 1));
				if (ValidateUtil.StrNotNull(value)) {
					if (ValidateUtil.isStrByteLenNotOut(value, 150)) {
						//Condition con = new Condition();
						//con.add("organizationName", Operator.ISNOTNULL, null);
						//con.add("organizationName", Operator.EQ, value);
						//sysOrgn = entityDao.findOne(SysOrganization.class, con);
						//if (sysOrgn == null) {
							//msg.add("您所填写的单位不存在");
							//flag = false;
							sysOrgn = new SysOrganization();
							sysOrgn.setOrganizationName(value);
							tbdw = value;
						//}
					} else {
						msg.add("C2填报单位超过75个字");
						flag = false;
					}
				} else {
					msg.add("C2填报单位不能为空");
					flag = false;
				}
				/**value = ExcelHelper.getStringValue(sheet.getCell(9, 2));
				if (ValidateUtil.StrNotNull(value)) {
					if (!sysOrgn.getSysXq().getXqmc().equals(value)) {
						msg.add("你所填写的项目属地【"+ value +"】与填报单位所在属地【"+ sysOrgn.getSysXq().getXqmc() +"】不符");
						flag = false;
					}
				} else {
					msg.add("J3项目属地不能为空");
					flag = false;
				}*/
			} else {
				msg.add("请导入正确格式的上报项目情况数据文件.");
				flag = false;
			}
			return flag;
		}
		
		

		/**
		 * 将Excel的数据读入list集合中
		 * @param file
		 * @return
		 */
		protected List<T> readDatas(Sheet sheet,int beginRow) {
	    	List<T> datas = new ArrayList<T>();
	    	
	    	int[] nullArray=null;
	    	if (ExcelHelper.getStringValue(sheet.getCell(1, 2)).equals("项目名称")
				&& ExcelHelper.getStringValue(sheet.getCell(4, 2)).equals("总投资（万元）")
				&& ExcelHelper.getStringValue(sheet.getCell(11, 2)).indexOf("项目类型") > -1
				&& ExcelHelper.getStringValue(sheet.getCell(12, 2)).indexOf("项目类别") > -1
				&& ExcelHelper.getStringValue(sheet.getCell(13, 2)).indexOf("产业类别") > -1
				&& ExcelHelper.getStringValue(sheet.getCell(15, 2)).indexOf("所属行业") > -1
				&& ExcelHelper.getStringValue(sheet.getCell(19, 2)).indexOf("项目属地") > -1) {
        		nullArray = new int[]{1,4,11,12,13,15,19};
			}
        	
	        for(int row = beginRow;row < sheet.getRows(); row++){//行信息
	        	if(checkExcelRowNull(sheet, row, nullArray)){
	        		row++;
	        	}else{
	        		T obj = readRow(sheet, row);
		        	if(obj != null){
		        		datas.add(obj);
		        	}else{
		        		msgCount[0]++;
		        	}
	        	}
	        }
			return datas;
		}
		
		/**
		 * 将Excel的数据读入list集合中
		 * @param file
		 * @return
		 */
		protected List<T> readDatas(Sheet sheet,int beginRow,SysUser user, SysOrganization sysOrgn) {
	    	List<T> datas = new ArrayList<T>();
	    	
	    	int[] nullArray=null;
	    	if (ExcelHelper.getStringValue(sheet.getCell(1, 2)).equals("项目名称")
					&& ExcelHelper.getStringValue(sheet.getCell(4, 2)).equals("总投资（万元）")
					&& ExcelHelper.getStringValue(sheet.getCell(11, 2)).indexOf("项目类型") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(12, 2)).indexOf("项目类别") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(13, 2)).indexOf("产业类别") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(15, 2)).indexOf("所属行业") > -1
					&& ExcelHelper.getStringValue(sheet.getCell(19, 2)).indexOf("项目属地") > -1) {
	        		nullArray = new int[]{1,4,11,12,13,15,19};
			}
        	
	        for(int row = beginRow;row < sheet.getRows(); row++){//行信息
	        	if(checkExcelRowNull(sheet, row, nullArray)){
	        		row++;
	        	}else{
	        		T obj = readRow(sheet, row, user, sysOrgn);
		        	if(obj != null){
		        		datas.add(obj);
		        	}else{
		        		msgCount[0]++;
		        	}
	        	}
	        }
			return datas;
		}
		
		/**
		 * 检查excel是否为空行
		 * @param sheet
		 * @param row
		 * @param column
		 * @return true为是
		 */
		private boolean checkExcelRowNull(Sheet sheet, int row,int[]column){
			int nullCount = 0;
			for (int i : column) {
				if(StringUtils.isBlank(ExcelHelper.getStringValue(sheet.getCell(i, row)))){
					nullCount++;
				}
			}
			return nullCount==column.length;
		}
		
		
		/**
		 * 读取Excel的每一行的相关数据,并检查数据合法性
		 * @param sheet
		 * @param row
		 * @return
		 */
		protected abstract T readRow(Sheet sheet, int row);
		
		/**
		 * 读取Excel的每一行的相关数据,并检查数据合法性
		 * @param sheet
		 * @param row
		 * @return
		 */
		protected abstract T readRow(Sheet sheet, int row,SysUser user, SysOrganization sysOrgn);
	    
	    
	    /**
	     * 清空Cache的相关数据,主要为外键
	     */
	    protected abstract void clearCache();
	    	
	    
	    /**
	     * 过滤掉从Excel中读入list集合中的相关数据重复数据,并返回重复数据
	     * @param datas
	     */
	    protected abstract List<T> doFilter(List<T> datas);
	    
}
