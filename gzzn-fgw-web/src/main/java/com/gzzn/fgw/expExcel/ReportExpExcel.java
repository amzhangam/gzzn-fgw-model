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
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;

import com.gzzn.common.persist.Condition;
import com.gzzn.common.persist.Condition.Operator;
import com.gzzn.common.persist.IEntityDao;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.model.pojo.ReportPojo;
import com.gzzn.fgw.model.pojo.ReportPojoOld;
import com.gzzn.fgw.service.CommonServiceImpl;
import com.gzzn.fgw.service.ICommonService;
import com.gzzn.fgw.webUtil.JxlExcelCellUtil;

/**
 * <p>Description: 导出发改委报表的实现类，本类只适用于于发改委报表 </p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lzq
 * @version 1.0
 * 
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-02-24 17:53:25pm
 */
public class ReportExpExcel extends JxlExcelCellUtil<ReportPojo> { 
	/**
	 * 写入数据，操作excel文件。
	 * @param obj 封装Model
	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等）
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 */	
	private WritableSheet writableSheetCell(ReportPojoOld obj,Map<Integer,String> xmztMap, int column,
			int row, WritableSheet sheet) {
		try {
			if(column==0){
				if(row==0){ 
					//指定两行标题行 
					//增加第一行
					sheet.addCell(new jxl.write.Label(5,0, "资金来源(万元)",topCellStyle())); sheet.setColumnView(5, 15);//列宽	
					sheet.addCell(new jxl.write.Label(9,0, "累计完成投资(万元)",topCellStyle())); sheet.setColumnView(5, 15);//列宽	
					sheet.addCell(new jxl.write.Label(12,0,"计划投资",topCellStyle())); sheet.setColumnView(11, 15);//列宽
					//增加第二行
					sheet.addCell(new jxl.write.Label(0,1, "序号",topCellStyle())); sheet.setColumnView(0, 10);//列宽
					sheet.addCell(new jxl.write.Label(1,1, "项目名称",topCellStyle())); sheet.setColumnView(1, 20);//列宽
					sheet.addCell(new jxl.write.Label(2,1, "主要建设内容",topCellStyle())); sheet.setColumnView(2, 60);//列宽
					sheet.addCell(new jxl.write.Label(3,1, "建设起止年限",topCellStyle())); sheet.setColumnView(3, 15);//列宽
					sheet.addCell(new jxl.write.Label(4,1, "总投资(万元)",topCellStyle())); sheet.setColumnView(4, 15);//列宽		
					
					sheet.addCell(new jxl.write.Label(5,1, "市财政资金",topCellStyle())); sheet.setColumnView(5, 15);//列宽					
					sheet.addCell(new jxl.write.Label(6,1, "自有资金",topCellStyle())); sheet.setColumnView(6, 15);//列宽
					sheet.addCell(new jxl.write.Label(7,1, "融资(含银行贷款)",topCellStyle())); sheet.setColumnView(7, 15);//列宽
					sheet.addCell(new jxl.write.Label(8,1, "其他",topCellStyle())); sheet.setColumnView(8, 15);//列宽

					sheet.addCell(new jxl.write.Label(9,1, "截止年份",topCellStyle())); sheet.setColumnView(9, 15);//列宽
					sheet.addCell(new jxl.write.Label(10,1, "合计",topCellStyle())); sheet.setColumnView(10, 15);//列宽					
					sheet.addCell(new jxl.write.Label(11,1, "其中市财政资金",topCellStyle())); sheet.setColumnView(11, 15);//列宽

					sheet.addCell(new jxl.write.Label(12,1, "预计年份",topCellStyle())); sheet.setColumnView(12, 15);//列宽
					sheet.addCell(new jxl.write.Label(13,1, "计划投资合计",topCellStyle())); sheet.setColumnView(13, 15);//列宽					
					sheet.addCell(new jxl.write.Label(14,1, "市财政资金",topCellStyle())); sheet.setColumnView(14, 15);//列宽
					sheet.addCell(new jxl.write.Label(15,1, "自有资金",topCellStyle())); sheet.setColumnView(15, 15);//列宽
					sheet.addCell(new jxl.write.Label(16,1, "融资(含银行贷款)",topCellStyle())); sheet.setColumnView(16, 15);//列宽
					sheet.addCell(new jxl.write.Label(17,1, "其他",topCellStyle())); sheet.setColumnView(17, 15);//列宽
					//建议
					sheet.addCell(new jxl.write.Label(18,1, "市财政资金安排渠道建议",topCellStyle())); sheet.setColumnView(18, 20);//列宽					
					sheet.addCell(new jxl.write.Label(19,1, "申报依据",topCellStyle())); sheet.setColumnView(19, 20);//列宽
					sheet.addCell(new jxl.write.Label(20,1, "项目主管单位",topCellStyle())); sheet.setColumnView(20, 20);//列宽
					sheet.addCell(new jxl.write.Label(21,1, "建设管理单位",topCellStyle())); sheet.setColumnView(21, 20);//列宽
					sheet.addCell(new jxl.write.Label(22,1, "备注",topCellStyle())); sheet.setColumnView(22, 20);//列宽
					
					//合并第一行
					sheet.mergeCells(5, 0, 8, 0);
					sheet.mergeCells(9, 0, 11, 0);
					sheet.mergeCells(12, 0, 17, 0);					
				}
				
				//标题占两行，故数据行需要增加2
				sheet.addCell(expDataType(0,row+2, Integer.valueOf(row+1)));
				sheet.addCell(expDataType(1,row+2,obj.getProjectName()));
				sheet.addCell(expDataType(2,row+2,obj.getProjectContent()));
				sheet.addCell(expDataType(3,row+2,obj.getStartYear()+"~"+obj.getEndYear())); 
				sheet.addCell(expDataType(4,row+2,obj.getPjinvestSum()));
				//总投资
				sheet.addCell(expDataType(5,row+2,obj.getPjinvestCity()));
				sheet.addCell(expDataType(6,row+2,obj.getPjinvestCompany()));	
				sheet.addCell(expDataType(7,row+2,obj.getPjinvestBank()));	
				sheet.addCell(expDataType(8,row+2,obj.getPjinvestOther()));					
				//XXXX年累计完成
				sheet.addCell(expDataType(9,row+2,obj.getExpectFinishInvestYear()));	
				sheet.addCell(expDataType(10,row+2,obj.getExpectFinishInvest()));	
				sheet.addCell(expDataType(11,row+2,obj.getExpectFinishOtherInvest()));	
				//XXXX年计划投资	
				sheet.addCell(expDataType(12,row+2,obj.getPlanInvestYear()));
				sheet.addCell(expDataType(13,row+2,obj.getPlanInvestSum()));
				sheet.addCell(expDataType(14,row+2,obj.getPlanInvestCity()));
				sheet.addCell(expDataType(15,row+2,obj.getPlanInvestCompany()));	
				sheet.addCell(expDataType(16,row+2,obj.getPlanInvestBank()));	
				sheet.addCell(expDataType(17,row+2,obj.getPlanInvestOther()));	
				//建议
				sheet.addCell(expDataType(18,row+2,obj.getPjinvestAdvice()));
				sheet.addCell(expDataType(19,row+2,obj.getDeclareGist()));				
				sheet.addCell(expDataType(20,row+2,obj.getOrganizationName()==null?"":obj.getOrganizationName()));	
				sheet.addCell(expDataType(21,row+2,obj.getManageUnitsName()));	
				//备注			 
				sheet.addCell(expDataType(22,row+2,xmztMap.get(obj.getPjStatus()))); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
	/**
	 * 写入数据，操作excel文件。是一个新建方法，导出发改委报表，必须调用这个方法，不能使用父类的
	 * @param expData 
	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等） 
	 * @param 需要写入的字节流
	 */	
    public void expExcelFile(List<ReportPojoOld> expData,Map<Integer,String> xmztMap,OutputStream out) {		
		WritableWorkbook workbook; 
	    try {
	        workbook = Workbook.createWorkbook(out,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet("第一页", 0);
	        for (int row = 0; row < expData.size(); row++) {
				sheet.setRowView(row, 400);
				ReportPojoOld obj = expData.get(row);
				writableSheetCell(obj,xmztMap, 0, row, sheet);
			}
	        sheet.getSettings().setVerticalFreeze(1);//冻结第一行
	        workbook.write();
	        workbook.close();	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
	@Override
	public WritableSheet writableSheetCell(String fdName, ReportPojo obj,
			int column, int row, WritableSheet sheet) {
		return null;
	}
	}

 
