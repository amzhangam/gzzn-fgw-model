package com.gzzn.fgw.expExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.service.sys.pojo.CsxmPojo;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.JxlExcelCellUtil;

/**
 * <p>Description: 导出2015年广州市政府投资项目基本情况表20140704.xls</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author lhq
 * @version 1.0
 * 
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2014-07-07 17:53:25pm
 */
public class DeptProjectListExpExcel extends JxlExcelCellUtil<PjbaseinfoPojo> {

	/**
	 * 写入数据，操作excel文件。
	 * @param obj 封装Model
	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等）
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 * @return
	 */
	private WritableSheet writableSheetCell(CsxmPojo obj,int column,
			int row, int dataStartIndex, WritableSheet sheet) {
		try {
			sheet.addCell(expDataType(0,row + dataStartIndex, obj.getDeptName()));
			sheet.addCell(expDataType(1,row + dataStartIndex, obj.getUnPassCount()));
			sheet.addCell(expDataType(2,row + dataStartIndex, obj.getUnPassUnderJbjsCount()));
			sheet.addCell(expDataType(3,row + dataStartIndex, obj.getUnPassUnderGxgzCount()));
			sheet.addCell(expDataType(4,row + dataStartIndex, obj.getUnPassUpJbjsCount()));
			sheet.addCell(expDataType(5,row + dataStartIndex, obj.getUnPassUpGxgzCount()));
			sheet.addCell(expDataType(6,row + dataStartIndex, obj.getPassCount()));
			sheet.addCell(expDataType(7,row + dataStartIndex, obj.getPassUnderJbjsCount()));
			sheet.addCell(expDataType(8,row + dataStartIndex, obj.getPassUnderGxgzCount()));
			sheet.addCell(expDataType(9,row + dataStartIndex, obj.getPassUpJbjsCount()));
			sheet.addCell(expDataType(10,row + dataStartIndex, obj.getPassUpGxgzCount()));
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	
	/**
	 * 根据数据集合,生成Excel输入流,交给struts2输出下载文件
	 * @param expData 导出数据
	 * @param tempPathName 导出数据模板：含表头数据
	 * @param dataStartIndex 数据从第几行行数写入，之前的行为表头字段
	 * @return
	 */
	public InputStream expExcelTempFile(SysUser sysUser, List<CsxmPojo> expData, String tempFilePath,
			int dataStartIndex) {
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	    	workbook = Workbook.createWorkbook(os, Workbook.getWorkbook(new File(tempFilePath)), JxlExcelCellUtil.setEncode());
	    	WritableSheet sheet = workbook.getSheet(0);
//	    	sheet.addCell(expDataType(3, 1, sysUser.getSysOrganization()==null?"":sysUser.getSysOrganization().getOrganizationName()));//填报单位（盖章）：
	    	// 查询结果写入excel文件
	    	for (int i = 0; i < expData.size(); i++) {
	    		sheet.setRowView(dataStartIndex + i, 400);
	    		CsxmPojo obj = expData.get(i);
	    		writableSheetCell(obj, 0, i, dataStartIndex, sheet);
	    	}
//		    sheet.getSettings().setHorizontalFreeze(2);//冻结第2列
//	        sheet.getSettings().setVerticalFreeze(6);//冻结第5行
	        
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

	
	@Override
	public WritableSheet writableSheetCell(String fdName, PjbaseinfoPojo obj,
			int column, int row, WritableSheet sheet) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
