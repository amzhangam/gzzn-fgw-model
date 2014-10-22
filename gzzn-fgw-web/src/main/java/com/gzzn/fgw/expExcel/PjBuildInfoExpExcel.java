package com.gzzn.fgw.expExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.gzzn.fgw.service.project.pojo.PjBulidInfoStatPojo;
import com.gzzn.fgw.webUtil.JxlExcelCellUtil;

/**
 * <p>Description: 区县项目汇总统计 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 * 
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-05-06 17:53:25pm
 */
public class PjBuildInfoExpExcel extends JxlExcelCellUtil<PjBulidInfoStatPojo> { 
	
	/**
	 * 写入Excel的表头数据。
	 * @param map
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 * @return
	 */
	private WritableSheet writableSheetTopCell(Map<String,String> map, int column,
			int row, WritableSheet sheet) {
		try {
			//合并单元格：sheet.mergeCells(columnNum1, rowNum1, columnNum2, rowNum2);
			//0行 设置表头名称1、1行设置表头名称2
	        sheet.addCell(new jxl.write.Label(0,0, map.get("title1"),topCellStyleTitle1()));
	        sheet.addCell(new jxl.write.Label(0,1, map.get("title2"),topCellStyleTitle2()));
	        sheet.setRowView(0, 800);
	        sheet.setRowView(1, 500);
	        sheet.setRowView(2, 500);
			sheet.setRowView(3, 800);//设置标题第3行的高度
	        
	        if(map.get("stuType").equalsIgnoreCase("1")){//按区县统计
				sheet.addCell(new jxl.write.Label(0,2, "地区",topCellStyle())); sheet.setColumnView(0, 20);//列宽
				sheet.mergeCells(0, 0, 9, 0);//合并单元格
	        	sheet.mergeCells(0, 1, 9, 1);//合并单元格
				sheet.mergeCells(0, 2, 0, 3);//合并单元格
				column =0;
			}else if(map.get("stuType").equalsIgnoreCase("2")){//按产业及投资额统计
				sheet.addCell(new jxl.write.Label(0,2, "类别",topCellStyle()));
				sheet.setColumnView(0, 18);//列宽
				sheet.setColumnView(1, 18);//列宽
				sheet.mergeCells(0, 0, 10, 0);//合并单元格
	        	sheet.mergeCells(0, 1, 10, 1);//合并单元格
				sheet.mergeCells(0, 2, 1, 3);//合并单元格
				column =1;
			}
			
			//未开工项目 【替换 2013年以来立项（审批、核准、备案）项目】【到2013年底累计完成投资(亿元)】
			sheet.addCell(new jxl.write.Label(column+1, 2, "未开工项目 ",topCellStyle()));	
			sheet.addCell(new jxl.write.Label(column+1, 3, "数量(个)",topCellStyle())); sheet.setColumnView(column+1, 12);//列宽					
			sheet.addCell(new jxl.write.Label(column+2, 3, "计划总投资(亿元)",topCellStyle())); sheet.setColumnView(column+2, 15);//列宽
			sheet.addCell(new jxl.write.Label(column+3, 3, "到上年底累计完成投资(亿元)",topCellStyle())); sheet.setColumnView(column+3, 18);//列宽
			sheet.mergeCells(column+1, 2, column+3, 2);//合并单元格
			
			//完工项目
			sheet.addCell(new jxl.write.Label(column+4, 2, "完工项目",topCellStyle()));	
			sheet.addCell(new jxl.write.Label(column+4, 3, "数量(个)",topCellStyle())); sheet.setColumnView(column+4, 12);//列宽					
			sheet.addCell(new jxl.write.Label(column+5, 3, "实际完成总投资(亿元)",topCellStyle())); sheet.setColumnView(column+5, 15);//列宽
			sheet.mergeCells(column+4, 2, column+5, 2);//合并单元格
			
			//在建项目【2014年计划投资(万元)】【2014年以来完成投资(亿元)】
			sheet.addCell(new jxl.write.Label(column+6, 2, "在建项目",topCellStyle()));	
			sheet.addCell(new jxl.write.Label(column+6, 3, "数量(个)",topCellStyle())); sheet.setColumnView(column+6, 12);//列宽					
			sheet.addCell(new jxl.write.Label(column+7, 3, "计划总投资(亿元)",topCellStyle())); sheet.setColumnView(column+7, 15);//列宽
			sheet.addCell(new jxl.write.Label(column+8, 3, "本年度投资计划(亿元)",topCellStyle())); sheet.setColumnView(column+8, 15);//列宽
			sheet.addCell(new jxl.write.Label(column+9, 3, "本年度投资计划完成情况(亿元)",topCellStyle())); sheet.setColumnView(column+9, 18);//列宽
			sheet.mergeCells(column+6, 2, column+9, 2);//合并单元格
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
	private WritableSheet writableSheetCell(PjBulidInfoStatPojo obj, Map<String,String> map, int column,
			int row, WritableSheet sheet) {
		try {
			if(column==0){
				//标题占两行，故数据行需要增加2
				if(map.get("stuType").equalsIgnoreCase("1")){//按区县统计
					sheet.addCell(expDataType(0,row, obj.getSysXq().getXqmc().equalsIgnoreCase("广州市")?"市本级": obj.getSysXq().getXqmc()));
					column =0;
				}else if(map.get("stuType").equalsIgnoreCase("2")){//按产业及投资额统计
					if(row==4){//合计
						sheet.addCell(new jxl.write.Label( 0,row, obj.getsType1(), cellCenterStyle()));	
						sheet.mergeCells(0, row, 1, row);
					}else if(row==5){//按产业分类
						sheet.addCell(new jxl.write.Label( 0,row, obj.getsType1(), cellCenterStyle()));	
						sheet.mergeCells(0, row, 0, row+Integer.valueOf(map.get("按产业分类"))-1);//合并单元格
					}else if(row==(5+Integer.valueOf(map.get("按产业分类")))){
						sheet.addCell(new jxl.write.Label( 0,row, obj.getsType1(), cellCenterStyle()));	
						sheet.mergeCells(0, row, 0, row+Integer.valueOf(map.get("按投资额大小分类"))-1);//合并单元格
					}
					sheet.addCell(expDataType(1,row, obj.getsType2()));
					column =1;
				}
				
				//2013年以来立项（审批、核准、备案）项目
				sheet.addCell(expDataType(column+1,row,obj.getlXNum()));
				sheet.addCell(expDataType(column+2,row,obj.getlXPjinvestsum()));
				sheet.addCell(expDataType(column+3,row,obj.getlXExpectfinishinvest())); 
				
				//完工项目
				sheet.addCell(expDataType(column+4,row,obj.getwGNum()));
				sheet.addCell(expDataType(column+5,row,obj.getwGExpectfinishinvest()+obj.getwGCurrentfinishinvest()));
				
				//在建项目
				sheet.addCell(expDataType(column+6,row,obj.getzJNum()));	
				sheet.addCell(expDataType(column+7,row,obj.getzJPjinvestsum()));	
				sheet.addCell(expDataType(column+8,row,obj.getzJPlaninvestsum()));	
				sheet.addCell(expDataType(column+9,row,obj.getzJCurrentfinishinvest()));	
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
	public InputStream expExcelFile(List<PjBulidInfoStatPojo> expData, Map<String,String> map){
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    
	    try {
	        workbook = Workbook.createWorkbook(os,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet(map.get("stuType").equalsIgnoreCase("1")?"总表（按区县）":"总表（按产业及投资额）", 0);
	        
	        //报表表头信息:表头从0-3行，共占有4行
	        writableSheetTopCell(map, 0, 0, sheet);
	        //报表数据
	        int row = 4;
	        for (; row < expData.size()+4; row++) {
				sheet.setRowView(row, 400);
				PjBulidInfoStatPojo obj = expData.get(row-4);
				writableSheetCell(obj, map, 0, row, sheet);
			}
	        //sheet.setRowView(row, 400);
	        //sheet.setRowView(row+1, 400);
	       // sheet.getSettings().setHorizontalFreeze(1);//冻结第一列
	       // sheet.getSettings().setVerticalFreeze(4);//冻结第二行
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
	public WritableSheet writableSheetCell(String fdName, PjBulidInfoStatPojo obj, int column, int row, WritableSheet sheet) {
		return null;
	}


}

 
