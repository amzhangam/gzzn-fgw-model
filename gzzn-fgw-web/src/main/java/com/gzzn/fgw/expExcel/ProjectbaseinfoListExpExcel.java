package com.gzzn.fgw.expExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
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
public class ProjectbaseinfoListExpExcel extends JxlExcelCellUtil<PjbaseinfoPojo> {

//	/**
//	 * 写入数据，操作excel文件。
//	 * @param obj 封装Model
//	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等）
//	 * @param column 开始列
//	 * @param row 开始行
//	 * @param sheet 工作薄对象
//	 * @return
//	 */
//	private WritableSheet writableSheetCell(PjbaseinfoPojo obj,Map<Integer,String> xmztMap, int column,
//			int row, int dataStartIndex, WritableSheet sheet) {
//		try {
//			sheet.addCell(expDataType(0,row + dataStartIndex, Integer.valueOf(row+1)));
//			sheet.addCell(expDataType(1,row + dataStartIndex, obj.getProjectname()));
//			sheet.addCell(expDataType(2,row + dataStartIndex, obj.getProjectcontent()));
//			sheet.addCell(expDataType(3,row + dataStartIndex, obj.getStartyear()+"-"+obj.getEndyear()));
//			sheet.addCell(expDataType(4,row + dataStartIndex, obj.getPjinvestsum()));
//			//资金来源
//			sheet.addCell(expDataType(5,row + dataStartIndex, obj.getPjinvestcenter()));
//			sheet.addCell(expDataType(6,row + dataStartIndex, obj.getPjinvestprovince()));
//			sheet.addCell(expDataType(7,row + dataStartIndex, obj.getPjinvestcity()));
//			sheet.addCell(expDataType(8,row + dataStartIndex, obj.getPjinvesttown()));
//			sheet.addCell(expDataType(9,row + dataStartIndex, obj.getPjinvestcompany()));
//			sheet.addCell(expDataType(10,row + dataStartIndex, obj.getPjinvestbank()));
//			sheet.addCell(expDataType(11,row + dataStartIndex, obj.getPjinvestother()));
//			//预计2014年底累计完成投资
//			sheet.addCell(expDataType(12,row + dataStartIndex, obj.getExpectfinishinvest()));
//			sheet.addCell(expDataType(13,row + dataStartIndex, obj.getExpectfinishotherinvest()));
//			//2015年投资计划建议
//			sheet.addCell(expDataType(14,row + dataStartIndex, obj.getPlaninvestsum()));
//			sheet.addCell(expDataType(15,row + dataStartIndex, obj.getPlaninvestcenter()));
//			sheet.addCell(expDataType(16,row + dataStartIndex, obj.getPlaninvestprovince()));
//			sheet.addCell(expDataType(17,row + dataStartIndex, obj.getPlaninvestcity()));
//			sheet.addCell(expDataType(18,row + dataStartIndex, obj.getPlaninvesttown()));
//			sheet.addCell(expDataType(19,row + dataStartIndex, obj.getPlaninvestcompany()));
//			sheet.addCell(expDataType(20,row + dataStartIndex, obj.getPlaninvestbank()));
//			sheet.addCell(expDataType(21,row + dataStartIndex, obj.getPlaninvestother()));  
//			sheet.addCell(expDataType(22,row + dataStartIndex,  obj.getFinishcontent()));   
//			
//			sheet.addCell(expDataType(23,row + dataStartIndex, obj.getPjinvestadvice()));
//			sheet.addCell(expDataType(24,row + dataStartIndex, obj.getDeclaregist()));
//			sheet.addCell(expDataType(25,row + dataStartIndex, obj.getManageunitsname()));
//			sheet.addCell(expDataType(26,row + dataStartIndex, obj.getSysOrganizationByDirectorunitsId()==null?"":obj.getSysOrganizationByDirectorunitsId().getOrganizationName()));
//			//sheet.addCell(expDataType(27,row + dataStartIndex, obj.getNextauditdeptname()));
//			sheet.addCell(expDataType(27,row + dataStartIndex, obj.getAuditdeptname()));
//			sheet.addCell(expDataType(28,row + dataStartIndex, obj.getPjstatus()==null?"":xmztMap.get(obj.getPjstatus())));
//		} catch (RowsExceededException e) {
//			e.printStackTrace();
//		} catch (WriteException e) {
//			e.printStackTrace();
//		}
//		return sheet;
//	}
//	
	/**
	 * 写入数据，操作excel文件。
	 * @param obj 封装Model
	 * @param xmztMap 项目状态（键：1,2,3等;值：草稿、待处长审核等）
	 * @param column 开始列
	 * @param row 开始行
	 * @param sheet 工作薄对象
	 * @return
	 */
	private WritableSheet writableSheetCell(PjbaseinfoPojo obj,Map<Integer,String> xmztMap, int column,
			int row, int dataStartIndex, WritableSheet sheet) {
		try {
			sheet.addCell(expDataType(0,row + dataStartIndex, Integer.valueOf(row+1)));
			sheet.addCell(expDataType(1,row + dataStartIndex, obj.getProjectname()));
			sheet.addCell(expDataType(2,row + dataStartIndex, obj.getProjectcontent()));
			sheet.addCell(expDataType(3,row + dataStartIndex, obj.getStartyear()+"-"+obj.getEndyear()));
			sheet.addCell(expDataType(4,row + dataStartIndex, obj.getSysOrganizationByDeclareunitsid()!=null?obj.getSysOrganizationByDeclareunitsid().getOrganizationName():""));
			sheet.addCell(expDataType(5,row + dataStartIndex, obj.getPjinvestsum()));
			//资金来源
			sheet.addCell(expDataType(6,row + dataStartIndex, obj.getPjinvestcenter()));
			sheet.addCell(expDataType(7,row + dataStartIndex, obj.getPjinvestprovince()));
			sheet.addCell(expDataType(8,row + dataStartIndex, obj.getPjinvestcity()));
			sheet.addCell(expDataType(9,row + dataStartIndex, obj.getPjinvesttown()));
			sheet.addCell(expDataType(10,row + dataStartIndex, obj.getPjinvestcompany()));
			sheet.addCell(expDataType(11,row + dataStartIndex, obj.getPjinvestbank()));
			sheet.addCell(expDataType(12,row + dataStartIndex, obj.getPjinvestother()));
			//预计2014年底累计完成投资
			sheet.addCell(expDataType(13,row + dataStartIndex, obj.getExpectfinishinvest()));
			sheet.addCell(expDataType(14,row + dataStartIndex, obj.getExpectfinishotherinvest()));
			//2015年投资计划建议
			sheet.addCell(expDataType(15,row + dataStartIndex, obj.getPlaninvestsum()));
			sheet.addCell(expDataType(16,row + dataStartIndex, obj.getPlaninvestcenter()));
			sheet.addCell(expDataType(17,row + dataStartIndex, obj.getPlaninvestprovince()));
			sheet.addCell(expDataType(18,row + dataStartIndex, obj.getPlaninvestcity()));
			sheet.addCell(expDataType(19,row + dataStartIndex, obj.getPlaninvesttown()));
			sheet.addCell(expDataType(20,row + dataStartIndex, obj.getPlaninvestcompany()));
			sheet.addCell(expDataType(21,row + dataStartIndex, obj.getPlaninvestbank()));
			sheet.addCell(expDataType(22,row + dataStartIndex, obj.getPlaninvestother()));  
			sheet.addCell(expDataType(23,row + dataStartIndex,  obj.getFinishcontent()));   
			
			sheet.addCell(expDataType(24,row + dataStartIndex, obj.getPjinvestadvice()));
			sheet.addCell(expDataType(25,row + dataStartIndex, obj.getDeclaregist()));
			sheet.addCell(expDataType(26,row + dataStartIndex, obj.getManageunitsname()));
			sheet.addCell(expDataType(27,row + dataStartIndex, obj.getSysOrganizationByDirectorunitsId()==null?"":obj.getSysOrganizationByDirectorunitsId().getOrganizationName()));
			//sheet.addCell(expDataType(27,row + dataStartIndex, obj.getNextauditdeptname()));
			sheet.addCell(expDataType(28,row + dataStartIndex, obj.getAuditdeptname()));
			sheet.addCell(expDataType(29,row + dataStartIndex, obj.getPjstatus()==null?"":xmztMap.get(obj.getPjstatus())));
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
	public InputStream expExcelTempFile(SysUser sysUser, List<PjbaseinfoPojo> expData,
			Map<Integer, String> xmztMap, String tempFilePath,
			int dataStartIndex) {
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	    	workbook = Workbook.createWorkbook(os, Workbook.getWorkbook(new File(tempFilePath)), JxlExcelCellUtil.setEncode());
	    	WritableSheet sheet = workbook.getSheet(0);
	    	sheet.addCell(expDataType(3, 1, sysUser.getSysOrganization()==null?"":sysUser.getSysOrganization().getOrganizationName()));//填报单位（盖章）：
	    	// 查询结果写入excel文件
	    	for (int i = 0; i < expData.size(); i++) {
	    		sheet.setRowView(dataStartIndex + i, 400);
	    		PjbaseinfoPojo obj = expData.get(i);
	    		writableSheetCell(obj, xmztMap, 0, i, dataStartIndex, sheet);
	    	}
		    sheet.getSettings().setHorizontalFreeze(2);//冻结第2列
	        sheet.getSettings().setVerticalFreeze(6);//冻结第5行
	        
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
