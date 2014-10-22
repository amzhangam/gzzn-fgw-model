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
import com.gzzn.fgw.webUtil.JxlExcelCellUtil;

/**
 * <p>Description: 导出发改委报表的实现类，本类只适用于于发改委报表 </p>
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
public class ReportStatExpExcel extends JxlExcelCellUtil<Projectinvest> { 
	/**
	 * 写入数据，操作excel文件。
	 * @param obj 封装Model
	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等）
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 */	
	private WritableSheet writableSheetCell(Projectinvest obj,Map<Integer,String> xmztMap, ReportPojo params, int column,
			int row, WritableSheet sheet) {
		try {
			if(column==0){
				if(row==0){ 
					//合并单元格：sheet.mergeCells(columnNum1, rowNum1, columnNum2, rowNum2);
					//项目基本信息
					sheet.addCell(new jxl.write.Label(0,0, "序号",topCellStyle())); sheet.setColumnView(0, 10);//列宽
					sheet.addCell(new jxl.write.Label(1,0, "项目名称",topCellStyle())); sheet.setColumnView(1, 30);//列宽
					sheet.addCell(new jxl.write.Label(2,0, "主要建设内容",topCellStyle())); sheet.setColumnView(2, 50);//列宽
					sheet.addCell(new jxl.write.Label(3,0, "建设起止年限",topCellStyle())); sheet.setColumnView(3, 15);//列宽
					sheet.addCell(new jxl.write.Label(4,0, "总投资(万元)",topCellStyle())); sheet.setColumnView(4, 15);//列宽		
					for(int i=0;i<=4;i++){
						sheet.mergeCells(i, 0, i, 1);//合并单元格
					}
					
					//资金来源(万元)
					sheet.addCell(new jxl.write.Label(5,0, "资金来源(万元)",topCellStyle()));	
					sheet.addCell(new jxl.write.Label(5,1, "市财政资金",topCellStyle())); sheet.setColumnView(5, 15);//列宽					
					sheet.addCell(new jxl.write.Label(6,1, "自有资金",topCellStyle())); sheet.setColumnView(6, 15);//列宽
					sheet.addCell(new jxl.write.Label(7,1, "融资(含银行贷款)",topCellStyle())); sheet.setColumnView(7, 21);//列宽
					sheet.addCell(new jxl.write.Label(8,1, "其他",topCellStyle())); sheet.setColumnView(8, 15);//列宽
					sheet.mergeCells(5, 0, 8, 0);//合并单元格
					
					//累计完成资金(万元)
					if(params!=null && params.getExpectFinishInvestYear()!=null){
						sheet.addCell(new jxl.write.Label(9,0, "预计至"+ params.getExpectFinishInvestYear() +"年底累计完成投资 (万元)",topCellStyle()));	
					}else{
						sheet.addCell(new jxl.write.Label(9,0, "预计至XX年底累计完成投资 (万元)",topCellStyle()));	
					}
					sheet.addCell(new jxl.write.Label(9,1, "截止年份",topCellStyle())); sheet.setColumnView(9, 15);//列宽
					sheet.addCell(new jxl.write.Label(10,1, "合计",topCellStyle())); sheet.setColumnView(10, 15);//列宽					
					sheet.addCell(new jxl.write.Label(11,1, "其中市财政资金",topCellStyle())); sheet.setColumnView(11, 18);//列宽
					sheet.mergeCells(9, 0, 11, 0);//合并单元格
					
					//计划投资资金(万元)
					if(params!=null && params.getPlanInvestYear()!=null){
						sheet.addCell(new jxl.write.Label(12,0, params.getPlanInvestYear() +"年投资计划建议(万元)",topCellStyle()));
					}else{
						sheet.addCell(new jxl.write.Label(12,0,"XX年投资计划建议(万元)",topCellStyle()));
					}
					sheet.addCell(new jxl.write.Label(12,1, "预计年份",topCellStyle())); sheet.setColumnView(12, 15);//列宽
					sheet.addCell(new jxl.write.Label(13,1, "计划投资合计",topCellStyle())); sheet.setColumnView(13, 15);//列宽					
					sheet.addCell(new jxl.write.Label(14,1, "市财政资金",topCellStyle())); sheet.setColumnView(14, 15);//列宽
					sheet.addCell(new jxl.write.Label(15,1, "自有资金",topCellStyle())); sheet.setColumnView(15, 15);//列宽
					sheet.addCell(new jxl.write.Label(16,1, "融资(含银行贷款)",topCellStyle())); sheet.setColumnView(16, 21);//列宽
					sheet.addCell(new jxl.write.Label(17,1, "其他",topCellStyle())); sheet.setColumnView(17, 15);//列宽
					sheet.mergeCells(12, 0, 17, 0);//合并单元格
					
					//项目基本信息
					sheet.addCell(new jxl.write.Label(18,0, "市财政资金安排渠道建议",topCellStyle())); sheet.setColumnView(18, 28);//列宽					
					sheet.addCell(new jxl.write.Label(19,0, "申报依据",topCellStyle())); sheet.setColumnView(19, 20);//列宽
					sheet.addCell(new jxl.write.Label(20,0, "项目主管单位",topCellStyle())); sheet.setColumnView(20, 20);//列宽
					sheet.addCell(new jxl.write.Label(21,0, "建设管理单位",topCellStyle())); sheet.setColumnView(21, 20);//列宽
					sheet.addCell(new jxl.write.Label(22,0, "备注",topCellStyle())); sheet.setColumnView(22, 20);//列宽
					for(int i=18;i<=22;i++){
						sheet.mergeCells(i, 0, i, 1);//合并单元格
					}
				}
				
				//标题占两行，故数据行需要增加2
				sheet.addCell(expDataType(0,row+2, Integer.valueOf(row+1)));
				sheet.addCell(expDataType(1,row+2,obj.getPjbaseinfo().getProjectname()));
				sheet.addCell(expDataType(2,row+2,obj.getPjbaseinfo().getProjectcontent()));
				sheet.addCell(expDataType(3,row+2,obj.getPjbaseinfo().getStartyear()+"~"+obj.getPjbaseinfo().getEndyear())); 
				sheet.addCell(expDataType(4,row+2,obj.getPjinvestsum()));
				//总投资
				sheet.addCell(expDataType(5,row+2,obj.getPjinvestcity()));
				sheet.addCell(expDataType(6,row+2,obj.getPjinvestcompany()));	
				sheet.addCell(expDataType(7,row+2,obj.getPjinvestbank()));	
				sheet.addCell(expDataType(8,row+2,obj.getPjinvestother()));					
				//XXXX年累计完成
				sheet.addCell(expDataType(9,row+2,obj.getPjbaseinfo().getExpectfinishinvestyear()));	
				sheet.addCell(expDataType(10,row+2,obj.getPjbaseinfo().getExpectfinishinvest()));	
				sheet.addCell(expDataType(11,row+2,obj.getPjbaseinfo().getExpectfinishotherinvest()));	
				//XXXX年计划投资	
				sheet.addCell(expDataType(12,row+2,obj.getPlaninvestyear()));
				sheet.addCell(expDataType(13,row+2,obj.getPlaninvestsum()));
				sheet.addCell(expDataType(14,row+2,obj.getPlaninvestcity()));
				sheet.addCell(expDataType(15,row+2,obj.getPlaninvestcompany()));	
				sheet.addCell(expDataType(16,row+2,obj.getPlaninvestbank()));	
				sheet.addCell(expDataType(17,row+2,obj.getPlaninvestother()));	
				//建议
				sheet.addCell(expDataType(18,row+2,obj.getPjinvestadvice()));
				sheet.addCell(expDataType(19,row+2,obj.getPjbaseinfo().getDeclaregist()));	
				if(obj.getPjbaseinfo()!=null && obj.getPjbaseinfo().getSysOrganizationByDirectorunitsid()!=null){
					sheet.addCell(expDataType(20,row+2,obj.getPjbaseinfo().getSysOrganizationByDirectorunitsid().getOrganizationName()));	
				}else{
					sheet.addCell(expDataType(20,row+2,""));	
				}
				sheet.addCell(expDataType(21,row+2,obj.getPjbaseinfo().getManageunitsname()));	
				//备注			 
				sheet.addCell(expDataType(22,row+2,xmztMap.get(obj.getPjbaseinfo().getPjstatus()))); 
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
	public InputStream expExcelFile(List<Projectinvest> expData,Map<Integer,String> xmztMap,ReportPojo params){
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    
	    try {
	        workbook = Workbook.createWorkbook(os,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet("第一页", 0);
	        for (int row = 0; row < expData.size(); row++) {
				sheet.setRowView(row, 400);
				Projectinvest obj = expData.get(row);
				writableSheetCell(obj,xmztMap, params, 0, row, sheet);
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
	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等） 
	 * @param 需要写入的字节流
	 */	
    public void expExcelFile(List<Projectinvest> expData,Map<Integer,String> xmztMap,OutputStream out) {		
		WritableWorkbook workbook; 
	    try {
	        workbook = Workbook.createWorkbook(out,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet("第一页", 0);
	        for (int row = 0; row < expData.size(); row++) {
				sheet.setRowView(row, 400);
				Projectinvest obj = expData.get(row);
				writableSheetCell(obj,xmztMap, null, 0, row, sheet);
			}
	        //sheet.getSettings().setVerticalFreeze(1);//冻结第一行
	        workbook.write();
	        workbook.close();	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    
    
	@Override
	public WritableSheet writableSheetCell(String fdName, Projectinvest obj, int column, int row, WritableSheet sheet) {
		return null;
	}


}

 
