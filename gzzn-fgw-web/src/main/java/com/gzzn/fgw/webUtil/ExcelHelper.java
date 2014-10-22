package com.gzzn.fgw.webUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 * Excel数据操作
 * @author lhq
 * @date 2013.7.8
 * @version v1.0
 */
public abstract class ExcelHelper {

	/**
	 * 由二进制数据得到excel工作簿对象
	 * 
	 * @param content
	 * @return
	 */
	public static Workbook getWorkbook(byte[] content) {
		if (content == null) {
			return null;
		}
		Workbook workbook = null;
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
		try {
			workbook = Workbook.getWorkbook(inputStream);
			return workbook;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 由文件得到excel工作簿对象
	 * 
	 * @param file
	 * @return
	 */
	public static Workbook getWorkbook(File file) {
		Workbook workbook = null;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			workbook = Workbook.getWorkbook(inputStream);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			throw new BusinessException("找不到此文件");
		} catch (BiffException e) {
			throw new BusinessException("请导入正确的Excel文件");
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 根据文件路径取得工作簿
	 * 
	 * @param filePath
	 * @return
	 */
	public static Workbook getWorkbook(String filePath) {
		Workbook workbook = null;
		try {
			if (filePath.endsWith(".xls")) {
				FileInputStream inputStream = new FileInputStream(filePath);
				workbook = Workbook.getWorkbook(inputStream);
			} else {
				workbook = Workbook.getWorkbook(new  File(filePath));;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 获取某一单位格内容
	 * 
	 * @param cell
	 * @return
	 */
	public static Object getValue(Cell cell) {
		Object value = null;
		if (cell != null) {
			value = cell.getContents();
		}
		return value;
	}

	/**
	 * 获取某一单位格内容
	 * 
	 * @param cell
	 * @return
	 */
	public static String getStringValue(Cell cell) {
		String value = null;
		if (cell != null) {
			value = cell.getContents().trim();
			//CommonMethod.getLogger().debug("row="+cell.getRow()+" columu="+cell.getColumn()+" value="+value+" length="+value.length()+" 去除空格="+value.trim().length());
		}
		return value;
	}
	
	
}
