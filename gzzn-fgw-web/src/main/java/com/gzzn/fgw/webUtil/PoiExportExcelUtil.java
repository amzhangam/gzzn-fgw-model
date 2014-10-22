package com.gzzn.fgw.webUtil;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

//import com.gzzn.fgw.model.EasDutySchem;

public class PoiExportExcelUtil<T> {

	public HSSFWorkbook exportExcel(List<T> dataList, String headers[]) throws Exception {
		HSSFWorkbook workbook = null;
		try {
			// 这里的数据即时你要从后台取得的数据
			// 创建工作簿实例
			workbook = new HSSFWorkbook();
			// 创建工作表实例
			HSSFSheet sheet = workbook.createSheet("TscExcel");

			// 设置列宽
			this.setSheetColumnWidth(sheet, headers.length);
			// 获取样式
			HSSFCellStyle style = this.createTitleStyle(workbook);
			// 生成并设置另一个样式
			HSSFCellStyle style2 = this.createContentStyle(workbook);

			//   
			if (dataList != null && dataList.size() > 0) {

				// 创建第一行标题,标题名字的本地信息通过resources从资源文件中获取

				HSSFRow row = sheet.createRow(0);// 建立新行
				for (int i = 0; i < headers.length; i++) {
					HSSFCell cell = row.createCell(i);
					row.setHeight((short) (2 * 256));
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}

//				// 给excel填充数据
//				for (int i = 0; i < dataList.size(); i++) {
//					// 将dataList里面的数据取出来，假设这里取出来的是Model,也就是某个javaBean的意思啦
//					EasDutySchem dutySchem = (EasDutySchem) dataList.get(i);
//					row = sheet.createRow(i + 1);
//					row.setHeight((short) (2 * 256));
//					HSSFCell cell = row.createCell(0);
//					cell.setCellStyle(style2);
//					this.createCell(row, 0, style2, HSSFCell.CELL_TYPE_NUMERIC, i + 1);//序号
//					this.createCell(row, 1, style2, HSSFCell.CELL_TYPE_STRING,
//							dutySchem.getDutyDate() + (dutySchem.getDutyWeek()));//日期
//					this.createCell(row, 2, style2, HSSFCell.CELL_TYPE_STRING, dutySchem
//							.getEasDutyPersonByDutyMain()!=null?dutySchem.getEasDutyPersonByDutyMain().getDutyPersonName():"");//主班
//					this.createCell(row, 3, style2, HSSFCell.CELL_TYPE_STRING, dutySchem
//							.getEasDutyPersonByDutyLeader()!=null?dutySchem.getEasDutyPersonByDutyLeader().getDutyPersonName():"");//值班领导
//					this.createCell(row, 4, style2, HSSFCell.CELL_TYPE_STRING, dutySchem
//							.getEasDutyPersonByDutyDriver()!=null?dutySchem.getEasDutyPersonByDutyDriver().getDutyPersonName():"");//值班司机
//					this.createCell(row, 5, style2, HSSFCell.CELL_TYPE_STRING, dutySchem
//							.getEasDutyPersonByDutyServices()!=null?dutySchem.getEasDutyPersonByDutyServices().getDutyPersonName():"");//值班后勤
//					this.createCell(row, 6, style2, HSSFCell.CELL_TYPE_STRING,
//							new Integer(dutySchem.getHolidayflag()) == 0 ? "工作日" : new Integer(
//									dutySchem.getHolidayflag()) == 1 ? "节假日" : "");//备注
//				}
			} else {
				this.createCell(sheet.createRow(0), 0, style, HSSFCell.CELL_TYPE_STRING, "查无资料");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private void setSheetColumnWidth(HSSFSheet sheet, int cellNumber) {
		// 根据你数据里面的记录有多少列，就设置多少列
		for (int i = 0; i < cellNumber; i++) {
			if (i == 0) {
				sheet.setColumnWidth(i, 2000);
			} else {
				sheet.setColumnWidth(i, 6000);
			}
		}
	}

	// 设置excel的title样式
	private HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
		//		HSSFFont boldFont = wb.createFont();
		//		boldFont.setFontHeight((short) 200);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}

	//创建内容样式
	private HSSFCellStyle createContentStyle(HSSFWorkbook wb) {
		HSSFFont boldFont = wb.createFont();
		boldFont.setFontHeight((short) 200);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = wb.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style.setFont(font2);
		return style;
	}

	// 创建Excel单元格
	private void createCell(HSSFRow row, int column, HSSFCellStyle style, int cellType, Object value) {
		HSSFCell cell = row.createCell(column);
		if (style != null) {
			cell.setCellStyle(style);
		}
		switch (cellType) {
		case HSSFCell.CELL_TYPE_BLANK: {
		}
			break;
		case HSSFCell.CELL_TYPE_STRING: {
			cell.setCellValue(value.toString());
		}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(value.toString()));
		}
			break;
		default:
			break;
		}

	}

}
