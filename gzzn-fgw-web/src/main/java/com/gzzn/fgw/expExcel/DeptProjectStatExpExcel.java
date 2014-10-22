package com.gzzn.fgw.expExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.pojo.ReportPojo;
import com.gzzn.fgw.service.sys.pojo.CsxmPojo;
import com.gzzn.fgw.webUtil.JxlExcelCellUtil;

/**
 * <p>Description: 导出发改委各处室项目情况统计报表的实现类，本类只适用于于发改委报表 </p>
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
public class DeptProjectStatExpExcel extends JxlExcelCellUtil<CsxmPojo> { 
	/**
	 * 写入数据，操作excel文件。
	 * @param obj 封装Model
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 */	
	private WritableSheet writableSheetCell(CsxmPojo obj,  int column,
			int row, WritableSheet sheet) {
		try {
			if(column==0){
				if(row==0){ 
					//合并单元格：sheet.mergeCells(columnNum1, rowNum1, columnNum2, rowNum2);
					
					sheet.addCell(new jxl.write.Label(0,0, "统计时间：2014年7月1日之后",topCellStyle())); sheet.setColumnView(0, 10);//列宽
					sheet.mergeCells(0,0, 10, 0);
					
					sheet.addCell(new jxl.write.Label(0,1, "发改委处室名称",topCellStyle()));	
					sheet.mergeCells(0,1, 0, 3);
					
					sheet.addCell(new jxl.write.Label(1,1, "待处理项目数",topCellStyle()));	
					sheet.mergeCells(1,1, 5, 1);
					
					sheet.addCell(new jxl.write.Label(2,1, "审核通过项目数",topCellStyle()));	
					sheet.mergeCells(2,1, 6, 1);
					
					sheet.addCell(new jxl.write.Label(1,2, "总数",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(2,2, "1000万元以下",topCellStyle()));	
					sheet.mergeCells(2,2, 3, 2);
					sheet.addCell(new jxl.write.Label(3,2, "1000万元以上",topCellStyle()));	
					sheet.mergeCells(3,2, 4, 2);
					
					sheet.addCell(new jxl.write.Label(4,2, "总数",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(5,2, "1000万元以下",topCellStyle()));	
					sheet.mergeCells(5,2, 6, 2);
					sheet.addCell(new jxl.write.Label(6,2, "1000万元以上",topCellStyle()));	
					sheet.mergeCells(6,2, 7, 2);
					
					sheet.addCell(new jxl.write.Label(1,3, "",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(2,3, "基本建设类",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(3,3, "更新改造类",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(4,3, "基本建设类",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(5,3, "更新改造类",topCellStyle()));	
					
					sheet.addCell(new jxl.write.Label(6,3, "",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(7,3, "基本建设类",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(8,3, "更新改造类",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(9,3, "基本建设类",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(10,3, "更新改造类",topCellStyle()));	
					
				}
				
				//标题占两行，故数据行需要增加4
				sheet.addCell(expDataType(0,row+4, obj.getDeptName()));
				sheet.addCell(expDataType(1,row+4, obj.getUnPassCount()));
				sheet.addCell(expDataType(2,row+4, obj.getUnPassUnderJbjsCount()));
				sheet.addCell(expDataType(3,row+4, obj.getUnPassUnderGxgzCount()));
				sheet.addCell(expDataType(4,row+4, obj.getUnPassUpJbjsCount()));
				sheet.addCell(expDataType(5,row+4, obj.getUnPassUpGxgzCount()));
				sheet.addCell(expDataType(6,row+4, obj.getPassCount()));
				sheet.addCell(expDataType(7,row+4, obj.getPassUnderJbjsCount()));
				sheet.addCell(expDataType(8,row+4, obj.getPassUnderGxgzCount()));
				sheet.addCell(expDataType(9,row+4, obj.getPassUpJbjsCount()));
				sheet.addCell(expDataType(10,row+4, obj.getPassUpGxgzCount()));
				
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
	public InputStream expExcelFile(List<CsxmPojo> expData){
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    
	    try {
	        workbook = Workbook.createWorkbook(os,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet("第一页", 0);
	        for (int row = 0; row < expData.size(); row++) {
				sheet.setRowView(row, 400);
				CsxmPojo obj = expData.get(row);
				writableSheetCell(obj, 0, row, sheet);
			}
	       // sheet.getSettings().setHorizontalFreeze(1);//冻结第一列
	        sheet.getSettings().setVerticalFreeze(2);//冻结第二行
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
	
	/**
	 * 写入数据，操作excel文件。是一个新建方法，导出发改委报表，必须调用这个方法，不能使用父类的
	 * @param expData 
	 * @param 需要写入的字节流
	 */	
    public void expExcelFile(List<CsxmPojo> expData,OutputStream out) {		
		WritableWorkbook workbook; 
	    try {
	        workbook = Workbook.createWorkbook(out,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet("第一页", 0);
	        for (int row = 0; row < expData.size(); row++) {
				sheet.setRowView(row, 400);
				CsxmPojo obj = expData.get(row);
				writableSheetCell(obj, 0, row, sheet);
			}
	        //sheet.getSettings().setVerticalFreeze(1);//冻结第一行
	        workbook.write();
	        workbook.close();	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    
    
	@Override
	public WritableSheet writableSheetCell(String fdName, CsxmPojo obj, int column, int row, WritableSheet sheet) {
		return null;
	}


}

 
