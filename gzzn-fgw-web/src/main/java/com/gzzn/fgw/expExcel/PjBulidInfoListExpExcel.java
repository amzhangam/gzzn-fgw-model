package com.gzzn.fgw.expExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.gzzn.fgw.webUtil.JxlExcelCellUtil;
import com.gzzn.fgw.model.PjBulidInfo;
import com.gzzn.fgw.model.SysXq;
/**
 * <p>Title: PjBulidInfoListExpExcel</p>
 * <p>Description: 上报项目查询导出Excel </p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-6-24 上午10:40:48 lhq  new
 */
public class PjBulidInfoListExpExcel extends JxlExcelCellUtil<PjBulidInfo> {
	
	/**
	 * 写入Excel的表头数据。
	 * @param map
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 * @return
	 */
	private WritableSheet writableSheetTopCell(int column,
			int row, WritableSheet sheet) {
		try {
			//sheet.addCell(new jxl.write.Label(0,row, "序号",topCellStyle())); sheet.setColumnView(0, 10);//列宽
			sheet.addCell(new jxl.write.Label(0,row, "项目名称",cellStyle2())); sheet.setColumnView(0, 15);//列宽
			sheet.addCell(new jxl.write.Label(1,row, "项目类型",cellStyle2())); sheet.setColumnView(1, 15);//列宽
			sheet.addCell(new jxl.write.Label(2,row, "所属地区",cellStyle2())); sheet.setColumnView(2, 15);//列宽
			sheet.addCell(new jxl.write.Label(3,row, "总投资",cellStyle2())); sheet.setColumnView(3, 15);//列宽
			sheet.addCell(new jxl.write.Label(4,row, "所属行业",cellStyle2())); sheet.setColumnView(4, 15);//列宽
			sheet.addCell(new jxl.write.Label(5,row, "批复单位",cellStyle2())); sheet.setColumnView(5, 15);//列宽
			sheet.addCell(new jxl.write.Label(6,row, "批复文号（备案证号）",cellStyle2())); sheet.setColumnView(6, 20);//列宽
			sheet.addCell(new jxl.write.Label(7,row, "建设规模及内容",cellStyle2())); sheet.setColumnView(7, 15);//列宽
			sheet.addCell(new jxl.write.Label(8,row, "项目法人单位",cellStyle2())); sheet.setColumnView(8, 15);//列宽
			sheet.addCell(new jxl.write.Label(9,row, "拟开工时间",cellStyle2())); sheet.setColumnView(9, 10);//列宽
			sheet.addCell(new jxl.write.Label(10,row, "拟建成时间",cellStyle2())); sheet.setColumnView(10, 10);//列宽
			sheet.addCell(new jxl.write.Label(11,row, "批复时间",cellStyle2())); sheet.setColumnView(11, 10);//列宽
			sheet.addCell(new jxl.write.Label(12,row, "报送单位",cellStyle2())); sheet.setColumnView(12, 15);//列宽
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	
	
	/**
	 * 写入数据，操作excel文件。
	 * @param obj 封装Model
	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等）
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 */	
	private WritableSheet writableSheetCell(PjBulidInfo obj,  Map<String,SysXq> xqMap, int column,
			int row, WritableSheet sheet) {
		try {
			if(column==0){
				//sheet.addCell(expDataType(0,row, Integer.valueOf(row)));
				String projectName = obj.getProjectname();
				if(obj.getZdxmbz()==1){
					projectName = "★" + projectName;
				}else if(obj.getZdxmbz()==2){
					projectName = "■" + projectName;
				}
				sheet.addCell(expDataType(0,row,projectName));
				sheet.addCell(expDataType(1,row,obj.getXmlx()));
				sheet.addCell(expDataType(2,row,xqMap.get(obj.getXmsd())==null?"":xqMap.get(obj.getXmsd()).getXqxxmc()));
				sheet.addCell(expDataType(3,row,obj.getPjinvestsum()));
				sheet.addCell(expDataType(4,row,obj.getSshy()));
				sheet.addCell(expDataType(5,row,obj.getPfdw()));
				sheet.addCell(expDataType(6,row,obj.getLxpfwh()));
				sheet.addCell(expDataType(7,row,obj.getProjectcontent()));
				sheet.addCell(expDataType(8,row,obj.getXmdw()));
				//拟开工时间、拟建成时间、批复时间【拟开工时间的年份不能小于批复时间。(自动替换) 】
				Integer startYear = 0, endYear = 0, pfsjYear = 0;
				String pfsj = obj.getPfsj();
				if(obj.getBulidQznx()!=null){
					if(obj.getBulidQznx().indexOf("-")>-1){
						startYear = Integer.valueOf(obj.getBulidQznx().split("-")[0]);
						endYear = Integer.valueOf(obj.getBulidQznx().split("-")[1]);
					}else{
						startYear = Integer.valueOf(obj.getBulidQznx());
						endYear = Integer.valueOf(obj.getBulidQznx());
					}
				}
				if(StringUtils.isNotEmpty(pfsj)){
					pfsjYear = Integer.valueOf(pfsj.split("-")[0]);//批复时间的年份
					if(startYear < pfsjYear){
						startYear = pfsjYear;
					}
					if(pfsj.length()==6){
						pfsj = pfsj.replace("-", "0")+"01";
					}else{
						pfsj = pfsj.replace("-", "")+"01";
					}
				}
				sheet.addCell(expDataType(9,row,startYear));
				sheet.addCell(expDataType(10,row,endYear));
				sheet.addCell(expDataType(11,row,pfsj));
				sheet.addCell(expDataType(12,row,"广东省"));//obj.getTbdw()
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	 /**
		 * 根据数据集合与数据查看权限,生成Excel输入流,交给struts2输出下载文件
		 * @param expData 导出数据
		 * @param xmztMap 项目状态
		 * @param params 页面查询条件
		 * @return
		 */
		public InputStream expExcelFile(List<PjBulidInfo> expData,  Map<String,SysXq> xqMap){
			InputStream excelFile=null;
			
			WritableWorkbook workbook;
		    ByteArrayOutputStream os = new ByteArrayOutputStream();
		    
		    try {
		        workbook = Workbook.createWorkbook(os,JxlExcelCellUtil.setEncode());
		        WritableSheet sheet = workbook.createSheet("按国家发改委格式导出上报项目列表数据", 0);
		        
		        //报表表头信息:表头从0行，共占有1行
		        writableSheetTopCell(0, 0, sheet);
		        sheet.setRowView(0, 400);
		        //报表数据
		        int row = 1;
		        for (; row < expData.size()+1; row++) {
					sheet.setRowView(row, 400);
					PjBulidInfo obj = expData.get(row-1);
					writableSheetCell(obj, xqMap, 0, row, sheet);
				}
		       // sheet.getSettings().setHorizontalFreeze(1);//冻结第一列
		        sheet.getSettings().setVerticalFreeze(1);//冻结第二行
		        workbook.write();
		        workbook.close();
		        excelFile = new ByteArrayInputStream(os.toByteArray());
		        if(os!=null){
		        	os.reset();
		    		os.close();
		    	}
		    } catch (Exception e) {
		    	//throw new BusinessException("导出文件出错");
		    	e.printStackTrace();
		    }
			return excelFile;
		}
		

		@Override
		public WritableSheet writableSheetCell(String fdName, PjBulidInfo obj, int column, int row, WritableSheet sheet) {
			return null;
		}

}
