package com.gzzn.fgw.webUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public abstract class JxlExcelCellUtil<T> {

	private WritableCellFormat topCellFormat = null;
	private WritableCellFormat topCellFormatTitle1 = null;
	private WritableCellFormat topCellFormatTitle2 = null;
	private WritableCellFormat cellFormat = null;
	private WritableCellFormat cellFormat2 = null;
	private WritableCellFormat cellFormat11 = null;//字体大小为11，字体为宋体
	private WritableCellFormat cellCenterFormat = null;
	private WritableCellFormat dateCllFormat_yM = null;
	private WritableCellFormat dateCllFormat_yMd = null;
	private WritableCellFormat defaultCellFormat = null;
	private WritableCellFormat dateCllFormat_yMdHm = null;
	
	
	/**
	 * 根据数据集合与数据查看权限,生成Excel输入流,交给struts2输出下载文件
	 * @param expData 导出数据
	 * @param dataActor 数据权限
	 * @return
	 */
	public InputStream expExcelFile(List<T> expData,List<String> dataActor){
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    
	    try {
	        workbook = Workbook.createWorkbook(os,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet("第一页", 0);
	        for (int row = 0; row < expData.size(); row++) {
				sheet.setRowView(row, 400);
				T obj = expData.get(row);
				for (int column = 0; column < dataActor.size(); column++) {
					writableSheetCell(dataActor.get(column), obj, column, row, sheet);
				}
			}
	       // sheet.getSettings().setHorizontalFreeze(1);//冻结第一列
	        sheet.getSettings().setVerticalFreeze(1);//冻结第一行
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
	 * 根据数据集合,生成Excel输入流,交给struts2输出下载文件
	 * @param expData 导出数据
	 * @return
	 */
	public InputStream expExcelFile(List<T> expData){
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    
	    try {
	        workbook = Workbook.createWorkbook(os,JxlExcelCellUtil.setEncode());
	        WritableSheet sheet = workbook.createSheet("第一页", 0);
	        for (int row = 0; row < expData.size(); row++) {
				sheet.setRowView(row, 400);
				T obj = expData.get(row);
				writableSheetCell(null, obj, 0, row, sheet);
			}
	        sheet.getSettings().setVerticalFreeze(1);//冻结第一行
	        workbook.write();
	        workbook.close();
	        excelFile = new ByteArrayInputStream(os.toByteArray());
	        if(os!=null){
	        	os.reset();
	    		os.close();
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return excelFile;
	}
	
	/**
	 * 根据数据集合,生成Excel输入流,交给struts2输出下载文件
	 * @param expData 导出数据
	 * @param tempPathName 导出数据模板：含表头数据
	 * @param dataStartIndex 数据从第几行行数写入，之前的行为表头字段
	 * @return
	 */
	public InputStream expExcelTempFile(List<T> expData, Map<Integer,String> xmztMap, String tempFilePath, int dataStartIndex){
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	    	
	    	workbook = Workbook.createWorkbook(os, Workbook.getWorkbook(new File(tempFilePath)), JxlExcelCellUtil.setEncode());
	    	WritableSheet sheet = workbook.getSheet(0);
	    	// 查询结果写入excel文件
	    	for (int i = 0; i < expData.size(); i++) {
	    		sheet.setRowView(dataStartIndex + i, 400);
	    		T obj = expData.get(i);
	    		writableSheetCell(null, obj, 0, dataStartIndex + i, sheet);
	    	}
		    //sheet.getSettings().setVerticalFreeze(1);//冻结第一行
		    workbook.write();
		    workbook.close();
		    excelFile = new ByteArrayInputStream(os.toByteArray());
		    if(os!=null){
		    	os.reset();
		    	os.close();
		    }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return excelFile;
	}
	
	
	/**
	 * 将obj对象中的属性写入sheet
	 * @param fdName 属性名称
	 * @param obj 值
	 * @param column 列
	 * @param row 行
	 * @param sheet excel的页
	 * @return excel的页
	 */
	public abstract WritableSheet writableSheetCell(String fdName,T obj,int column,int row,WritableSheet sheet);
	
	/**
	 * 导出excel文件名,防止乱码
	 * @param fileName 文件名
	 * @return 文件名
	 */
	public static String setExcelFileName(String fileName){
		try {
			return URLEncoder.encode(fileName,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return fileName;
	}
	
	/**
	 * 避免乱码的设置
	 * @return
	 */
	public static WorkbookSettings setEncode() {
		WorkbookSettings setting = new WorkbookSettings();
		java.util.Locale locale = new java.util.Locale("zh", "CN");
		setting.setLocale(locale);
		setting.setEncoding("ISO-8859-1");
		return setting;
	}

	/**
	 * 检测数据格式,返回导出Excel的单元格
	 * @param column 列数
	 * @param row 行数
	 * @param obj 值
	 * @return
	 */
	public WritableCell expDataType(int column,int row,Object obj){
		WritableCell cell = null ;
		if(obj==null){
				cell = new jxl.write.Blank(column,row,cellStyle());
		}else {
			String className  = obj.getClass().getName();
			if(className.equals("java.lang.String")){
				cell = new jxl.write.Label( column,row, obj.toString(),cellStyle());
			}else if(className.equals("short")
				   ||className.equals("int")
				   ||className.equals("double")
				   ||className.equals("float")
				   ||className.equals("java.lang.Short")
				   ||className.equals("java.lang.Integer")
				   ||className.equals("java.lang.Float")
				   ||className.equals("java.lang.Double")){
				   cell = new jxl.write.Number( column,row,new Double(obj.toString()),cellStyle());
			}else if(className.equals("long")
					   ||className.equals("java.lang.Long")){
					   cell = new jxl.write.Label( column,row,obj.toString(),cellStyle());
			}else if(className.equals("boolean")
					   ||className.equals("java.lang.Boolean")){
					   cell=new  jxl.write.Boolean(column,row, (Boolean) obj,cellStyle());
			}else if(className.equals("java.sql.Timestamp")
					   ||className.equals("java.util.Date")){
					   cell = new jxl.write.DateTime( column,row,(Timestamp)obj,defaultCellDateStyle());
			}else{//剩下的全部弄成字符串
				cell = new jxl.write.Label( column,row, obj.toString(),cellStyle());
			}
		}
		return cell;
	}
	
	/**
	 * 检测数据格式,返回导出Excel的单元格
	 * @param column 列数
	 * @param row 行数
	 * @param obj 值
	 * @return
	 */
	public WritableCell expDataTypeDate(int column,int row,Object obj,String dataFormate){
		WritableCell cell = null ;
		if(obj==null){
				cell = new jxl.write.Blank(column,row,cellStyle());
		}else {
			String className  = obj.getClass().getName();
			if(className.equals("java.sql.Timestamp")||
					className.equals("java.util.Date")){
				if(dataFormate.equalsIgnoreCase("yyyy-MM-dd")){
					cell = new jxl.write.DateTime(column,row,(Timestamp)obj,cellDateStyle_yMd());
				}else if(dataFormate.equalsIgnoreCase("yyyy年MM月")){
					cell = new jxl.write.DateTime(column,row,(Timestamp)obj,cellDateStyle_yM());
				}else if(dataFormate.equalsIgnoreCase("yyyy-MM-dd HH:mm")){
					cell = new jxl.write.DateTime(column,row,(Timestamp)obj,cellDateStyle_yMdHm());
				}else{
					cell = new jxl.write.DateTime(column,row,(Timestamp)obj,defaultCellDateStyle());
				}
			}
			
		}
		return cell;
	}

	/**
	 * 表头单元格样式
	 * @return
	 */
	public WritableCellFormat topCellStyle(){
		if(topCellFormat==null){
			topCellFormat = new WritableCellFormat();
		    try {
		    	topCellFormat.setWrap(true);
		    	topCellFormat.setAlignment(Alignment.CENTRE); //设置对齐方式 
		    	topCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);//设置对齐方式
		    	//topCellFormat.setBackground(Colour.SKY_BLUE); //背静色  Colour.GREEN
		    	topCellFormat.setFont(new WritableFont(WritableFont.COURIER, 11, WritableFont.BOLD,false ,UnderlineStyle.NO_UNDERLINE, Colour.BLACK));//Colour.VIOLET
		    	topCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);//Colour.YELLOW
		    } catch (WriteException e) {
				e.printStackTrace();
			} 
		}
		
		return topCellFormat;
	}
	
	/**
	 * 表头单元格样式
	 * @return
	 */
	public WritableCellFormat topCellStyleTitle1(){
		if(topCellFormatTitle1==null){
			topCellFormatTitle1 = new WritableCellFormat();
		    try {
		    	topCellFormatTitle1.setWrap(true);
		    	topCellFormatTitle1.setAlignment(Alignment.CENTRE); //设置对齐方式 
		    	topCellFormatTitle1.setVerticalAlignment(VerticalAlignment.CENTRE);//设置对齐方式
		    	//topCellFormatTitle1.setBackground(Colour.SKY_BLUE); //背静色  Colour.GREEN
		    	topCellFormatTitle1.setFont(new WritableFont(WritableFont.COURIER, 17, WritableFont.BOLD,false ,UnderlineStyle.NO_UNDERLINE, Colour.BLACK));//Colour.RED
		    	//topCellFormatTitle1.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);//Colour.YELLOW
		    } catch (WriteException e) {
				e.printStackTrace();
			} 
		}
		
		return topCellFormatTitle1;
	}
	
	/**
	 * 表头单元格样式
	 * @return
	 */
	public WritableCellFormat topCellStyleTitle2(){
		if(topCellFormatTitle2==null){
			topCellFormatTitle2 = new WritableCellFormat();
		    try {
		    	topCellFormatTitle2.setWrap(true);
		    	topCellFormatTitle2.setAlignment(Alignment.CENTRE); //设置对齐方式 
		    	topCellFormatTitle2.setVerticalAlignment(VerticalAlignment.CENTRE);//设置对齐方式
		    	//topCellFormatTitle2.setBackground(Colour.SKY_BLUE); //背静色  Colour.GREEN
		    	topCellFormatTitle2.setFont(new WritableFont(WritableFont.COURIER, 13, WritableFont.BOLD,false ,UnderlineStyle.NO_UNDERLINE, Colour.BLACK));//Colour.RED
		    	//topCellFormatTitle2.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);//Colour.YELLOW
		    } catch (WriteException e) {
				e.printStackTrace();
			} 
		}
		
		return topCellFormatTitle2;
	}

	/**
	 * 单元格样式
	 * @return
	 */
	public WritableCellFormat cellStyle(){
		if(cellFormat==null){
			cellFormat = new WritableCellFormat();
			try {
				//cellFormat.setBackground(Colour.YELLOW); //背静色  Colour.GREEN
				//cellFormat.setBackground(Colour.VERY_LIGHT_YELLOW);
				cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
		}
		return cellFormat;
	}
	
	/**
	 * 单元格样式
	 * @return
	 */
	public WritableCellFormat cellStyle2(){
		if(cellFormat2==null){
			cellFormat2 = new WritableCellFormat();
			try {
				cellFormat2.setBackground(Colour.YELLOW); //背静色  Colour.GREEN
				//cellFormat2.setBackground(Colour.VERY_LIGHT_YELLOW);
				cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
		}
		return cellFormat2;
	}
	
	/**
	 * 表格字体样式：字体大小为11、宋体，自动换行、垂直居中
	 * @return
	 */
	public WritableCellFormat cellStyle11(){
		if(cellFormat11==null){
			cellFormat11 = new WritableCellFormat();
		    try {
		    	cellFormat11.setWrap(true);
		    	cellFormat11.setAlignment(Alignment.CENTRE); //设置对齐方式 
		    	cellFormat11.setVerticalAlignment(VerticalAlignment.CENTRE);//设置对齐方式
		    	cellFormat11.setFont(new WritableFont(WritableFont.COURIER, 11, WritableFont.NO_BOLD,false ,UnderlineStyle.NO_UNDERLINE, Colour.BLACK));//Colour.VIOLET
		    	cellFormat11.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);//Colour.YELLOW
		    } catch (WriteException e) {
				e.printStackTrace();
			} 
		}
		
		return cellFormat11;
	}
	
	/**
	 * 单元格样式
	 * @return
	 */
	public WritableCellFormat cellCenterStyle(){
		if(cellCenterFormat==null){
			cellCenterFormat = new WritableCellFormat();
			try {
				//cellFormat.setBackground(Colour.YELLOW); //背静色  Colour.GREEN
				cellCenterFormat.setAlignment(jxl.format.Alignment.CENTRE);//把水平对齐方式指定为居中
				cellCenterFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//把垂直对齐方式指定为居中
				//cellCenterFormat.setBackground(Colour.VERY_LIGHT_YELLOW);
				cellCenterFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
		}
		return cellCenterFormat;
	}
	
	/**
	 * 时间单元格样式
	 * yyyy-MM-dd
	 * @return
	 */
	public WritableCellFormat cellDateStyle_yMd(){
		if(dateCllFormat_yMd==null){
			dateCllFormat_yMd = new WritableCellFormat(new DateFormat("yyyy-MM-dd"));
			try {
				dateCllFormat_yMd.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
		}
		return dateCllFormat_yMd;
	}
	
	/**
	 * 时间单元格样式
	 * yyyy年MM月
	 * @return
	 */
	public WritableCellFormat cellDateStyle_yM(){
		if(dateCllFormat_yM==null){
			dateCllFormat_yM = new WritableCellFormat(new DateFormat("yyyy年MM月"));
			try {
				dateCllFormat_yM.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
		}
		return dateCllFormat_yM;
	}
	
	/**
	 * 时间单元格样式
	 * yyyy-MM-dd HH:mm
	 * @return
	 */
	public WritableCellFormat cellDateStyle_yMdHm(){
		if(dateCllFormat_yMdHm==null){
			dateCllFormat_yMdHm = new WritableCellFormat(new DateFormat("yyyy-MM-dd HH:mm"));
			try {
				dateCllFormat_yMdHm.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
		}
		return dateCllFormat_yMdHm;
	}
	
	
	/**
	 * 时间单元格样式
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public WritableCellFormat defaultCellDateStyle(){
		if(defaultCellFormat==null){
			defaultCellFormat = new WritableCellFormat(new DateFormat("yyyy-MM-dd HH:mm:ss"));
			try {
				//defaultCellFormat.setBackground(Colour.VERY_LIGHT_YELLOW);
				defaultCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
		}
		return defaultCellFormat;
	}

	

}
