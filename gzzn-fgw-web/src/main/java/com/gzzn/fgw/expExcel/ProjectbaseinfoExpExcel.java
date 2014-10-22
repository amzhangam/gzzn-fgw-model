package com.gzzn.fgw.expExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.gzzn.fgw.model.Pjadjunct;
import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.Projectinvest;
import com.gzzn.fgw.model.SysDictionaryitems;
import com.gzzn.fgw.service.project.pojo.PjbaseinfoPojo;
import com.gzzn.fgw.util.IConstants;
import com.gzzn.fgw.webUtil.CommonFiled;
import com.gzzn.fgw.webUtil.JxlExcelCellUtil;

/**
 * <p>Description: 导出2015年广州市政府投资项目申报表(20140707).xls</p>
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
public class ProjectbaseinfoExpExcel extends JxlExcelCellUtil<PjbaseinfoPojo> {
	
	/**
	 * 根据数据集合,生成Excel输入流,交给struts2输出下载文件
	 * @param expData 导出数据
	 * @param tempPathName 导出数据模板：含表头数据
	 * @param dataStartIndex 数据从第几行行数写入，之前的行为表头字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public InputStream expExcelTempFile(Pjbaseinfo obj, Projectinvest subObj,HttpSession session,List<Pjadjunct> pjadjuncts, String tempFilePath) {
		InputStream excelFile=null;
		
		WritableWorkbook workbook;
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	    	workbook = Workbook.createWorkbook(os, Workbook.getWorkbook(new File(tempFilePath)), JxlExcelCellUtil.setEncode());
	    	WritableSheet sheet = workbook.getSheet(0);
	    	
	    	if(obj!=null){
	    		sheet.addCell(expDataType(2, 1, obj.getSysOrganizationByDeclareunitsid()==null?"":obj.getSysOrganizationByDeclareunitsid().getOrganizationName()));//申报单位
		    	sheet.addCell(expDataType(9, 1, obj.getDeclartime()));//申报日期
		    	//一、项目基本信息
		    	if(obj.getXmfl()!=null){
		    		if(obj.getXmfl().equals(1)){//项目分类
			    		sheet.addCell(expDataType(3, 3, "基本建设投资类项目"));
			    	}else if(obj.getXmfl().equals(2)){
			    		sheet.addCell(expDataType(3, 3, "更新改造投资类项目"));
			    	}else if(obj.getXmfl().equals(3)){
			    		sheet.addCell(expDataType(3, 3, "其他固定资产投资类项目"));
			    	}
		    	}
		    	sheet.addCell(expDataType(3, 4, obj.getProjectname()));//项目名称
		    	sheet.addCell(expDataType(3, 5, obj.getSysOrganizationByDeclareunitsid()==null?"":obj.getSysOrganizationByDeclareunitsid().getOrganizationName()));//项目业主
		    	sheet.addCell(expDataType(3, 6, obj.getXmsbXmlx()==null?"":obj.getXmsbXmlx().getXmlxmc()));//项目类型
		    	sheet.addCell(expDataType(8, 6, obj.getProjectcode()));//项目编号
		    	sheet.addCell(expDataType(3, 7, obj.getXmsbHylb()==null?"":obj.getXmsbHylb().getHylbmc()));//行业类别
		    	sheet.addCell(expDataType(8, 7, obj.getProjectprincipal()));//项目负责人
		    	sheet.addCell(expDataType(3, 8, obj.getDeclarerid()));//项目联系人
		    	sheet.addCell(expDataType(8, 8, obj.getMobilePhone()));//联系人手机号
		    	sheet.addCell(expDataType(3, 9, obj.getContacttel()));//联系电话
		    	sheet.addCell(expDataType(8, 9, obj.getYzbm()));//邮政编码
		    	sheet.addCell(expDataType(3, 10, obj.getContactaddress()));//联系地址
		    	
		    	StringBuilder fjType0 = new StringBuilder();//立项批复   
		    	StringBuilder fjType1 = new StringBuilder();//规划选址   
		    	StringBuilder fjType2 = new StringBuilder();//用地预审   
		    	StringBuilder fjType3 = new StringBuilder();//环境影响评价 
		    	StringBuilder fjType4 = new StringBuilder();//节能评估审查 
		    	StringBuilder fjType5 = new StringBuilder();//可研批复   
		    	StringBuilder fjType6 = new StringBuilder();//初步设计及概算
		    	StringBuilder fjType7 = new StringBuilder();//招标投标情况 
		    	StringBuilder fjType8 = new StringBuilder();//征地拆迁   
		    	StringBuilder fjType9 = new StringBuilder();//其他前期工作 
		    	StringBuilder fjType10 = new StringBuilder();//项目申报依据                 
		    	StringBuilder fjType11 = new StringBuilder();//工程形象进度                 
		    	StringBuilder fjType12 = new StringBuilder();//存在的问题                  
		    	StringBuilder fjType13 = new StringBuilder();//项目书面材料                 
		    	StringBuilder fjType14 = new StringBuilder();//项目月报                   
		    	StringBuilder fjType15 = new StringBuilder();//项目现场图片缩略图              
		    	StringBuilder fjType16 = new StringBuilder();//项目现场图片                 
		    	StringBuilder fjType17 = new StringBuilder();//(重大项目)社会风险评估)          
		    	StringBuilder fjType18 = new StringBuilder();//施工图设计与预算               
		    	StringBuilder fjType19 = new StringBuilder();//需要补充的其它事项              
		    	StringBuilder fjType20 = new StringBuilder();//投资项目基本情况描述(对于更新改造类项目必填)
		    	String tempName = "";
		    	if(pjadjuncts!=null){
		    		for(Pjadjunct objFj : pjadjuncts){
		    			tempName = StringUtils.isNotEmpty(objFj.getWh())?("文号：【"+objFj.getWh() +"】"):objFj.getFilename();
			    		if(objFj.getPjadjuncttype()!=null){
			    			if(objFj.getPjadjuncttype().equals(IConstants.FJLX_0)){
			    				fjType0.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_1)){
			    				fjType1.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_2)){
			    				fjType2.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_3)){
			    				fjType3.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_4)){
			    				fjType4.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_5)){
			    				fjType5.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_6)){
			    				fjType6.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_7)){
			    				fjType7.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_8)){
			    				fjType8.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_9)){
			    				fjType9.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_10)){
			    				fjType10.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_11)){
			    				fjType11.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_12)){
			    				fjType12.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_13)){
			    				fjType13.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_14)){
			    				fjType14.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_15)){
			    				fjType15.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_16)){
			    				fjType16.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_17)){
			    				fjType17.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_18)){
			    				fjType18.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_19)){
			    				fjType19.append(tempName).append("\n");
			    			}else if(objFj.getPjadjuncttype().equals(IConstants.FJLX_20)){
			    				fjType20.append(tempName).append("\n");
			    			}																		    			
			    		}
			    	}
		    	}
		    	sheet.addCell(expDataType(3, 11, obj.getXmjbqkms()));//投资项目基本情况描述(对于更新改造类项目必填 )
		    	sheet.addCell(expDataType(3, 12, fjType20.toString()));
		    	
		    	
		    	//二、项目投资信息(资金单位：万元)：项目总投资、xxxx年投资计划
		    	//sheet.addCell(expDataType(0, 12, "预计至"+ obj.getExpectfinishinvestyear() +"年底累计完成投资"));//预计至 (xxxx -1)年底累计完成投资
		    	sheet.addCell(new jxl.write.Label(0, 14, "预计至"+ obj.getExpectfinishinvestyear() +"年底累计完成投资", cellStyle11()));
		    	sheet.addCell(expDataType(9, 14, obj.getExpectfinishinvest()));//合计
		    	sheet.addCell(expDataType(9, 15, obj.getExpectfinishotherinvest()));//其中市本级财政资金
		    	
		    	if(subObj!=null){
		    		//项目总投资
			    	sheet.addCell(expDataType(1, 17, subObj.getPjinvestcenter()));
			    	sheet.addCell(expDataType(2, 17, subObj.getPjinvestprovince()));
			    	sheet.addCell(expDataType(4, 17, subObj.getPjinvestcity()));
			    	sheet.addCell(expDataType(5, 17, subObj.getPjinvesttown()));
			    	sheet.addCell(expDataType(6, 17, subObj.getPjinvestcompany()));
			    	sheet.addCell(expDataType(7, 17, subObj.getPjinvestbank()));
			    	sheet.addCell(expDataType(8, 17, subObj.getPjinvestother()));
			    	sheet.addCell(expDataType(9, 17, subObj.getPjinvestsum()));
			    	//xxxx年投资计划
			    	//sheet.addCell(expDataType(0, 16, subObj.getPlaninvestyear() +"年投资计划"));//xxxx年投资计划
			    	sheet.addCell(new jxl.write.Label(0, 18, subObj.getPlaninvestyear() +"年投资计划", cellStyle11()));
			    	sheet.addCell(expDataType(1, 18, subObj.getPlaninvestcenter()));
			    	sheet.addCell(expDataType(2, 18, subObj.getPlaninvestprovince()));
			    	sheet.addCell(expDataType(4, 18, subObj.getPlaninvestcity()));
			    	sheet.addCell(expDataType(5, 18, subObj.getPlaninvesttown()));
			    	sheet.addCell(expDataType(6, 18, subObj.getPlaninvestcompany()));
			    	sheet.addCell(expDataType(7, 18, subObj.getPlaninvestbank()));
			    	sheet.addCell(expDataType(8, 18, subObj.getPlaninvestother()));
			    	sheet.addCell(expDataType(9, 18, subObj.getPlaninvestsum()));
		    	}
		    	
		    	//三、项目审批信息
		    	sheet.addCell(expDataType(6, 21, obj.getDeclaregist()));//项目申报依据(提示：请附含市领导批示、会议纪要等！)
		    	sheet.addCell(expDataType(6, 22, fjType10.toString()));
		    	sheet.addCell(expDataType(6, 23, fjType0.toString()));//立项批复
		    	sheet.addCell(expDataType(6, 24, fjType2.toString()));//用地预审
		    	sheet.addCell(expDataType(6, 25, fjType5.toString()));//可研批复
		    	sheet.addCell(expDataType(6, 26, fjType6.toString()));//初步设计及概算
		    	sheet.addCell(expDataType(6, 27, fjType8.toString()));//征地拆迁
		    	sheet.addCell(expDataType(6, 28, fjType1.toString()));//规划选址
		    	sheet.addCell(expDataType(6, 29, fjType3.toString()));//环境影响评价
		    	sheet.addCell(expDataType(6, 30, fjType4.toString()));//节能评估审查
		    	sheet.addCell(expDataType(6, 31, fjType7.toString()));//招标投标情况
		    	sheet.addCell(expDataType(6, 32, fjType18.toString()));//施工图设计与预算
		    	sheet.addCell(expDataType(6, 33, fjType17.toString()));//(重大项目)社会风险评估
		    	sheet.addCell(expDataType(6, 34, fjType9.toString()));//其他前期工作
		    	
		    	//四、项目建设信息
		    	sheet.addCell(expDataType(3, 36, obj.getSysOrganizationByDirectorunitsid()==null?"":obj.getSysOrganizationByDirectorunitsid().getOrganizationName()));//主管单位
		    	sheet.addCell(expDataType(3, 37, obj.getProjectaddress()));//项目建设地址
		    	sheet.addCell(expDataType(3, 38, obj.getManageunitsname()));//项目建设管理（代建）单位
		    	sheet.addCell(expDataType(3, 39, obj.getProjectcontent()));//主要建设内容及规模
		    	List<SysDictionaryitems> xmjdList = (List<SysDictionaryitems>) session.getAttribute(CommonFiled.SESSION_XMJD_MAP);
		    	if(xmjdList.size()>0 && obj.getXmjd()!=null){
		    		String xmjdName = "";
		    		for(SysDictionaryitems xmjdObj : xmjdList){
		    			if(xmjdObj.getItemvalue().equals(obj.getXmjd())){
		    				xmjdName = xmjdObj.getItemtext();
		    			}
		    		}
		    		sheet.addCell(expDataType(3, 40, xmjdName));//项目进度
		    	}
		    	//sheet.addCell(expDataType(0, 37, "其中"+obj.getFinishcontentyear()+"年度建设内容"));//其中xxxx年度建设内容
		    	sheet.addCell(new jxl.write.Label(0, 41, "其中"+obj.getFinishcontentyear()+"年度建设内容", cellStyle11()));
		    	sheet.addCell(expDataType(3, 41, obj.getFinishcontent()));//其中xxxx年度建设内容
		    	
		    	sheet.addCell(expDataType(3, 42, obj.getSysXq()==null?"":obj.getSysXq().getXqmc()));//所属区域
		    	sheet.addCell(expDataType(8, 42, obj.getStartyear()+ "-" +obj.getEndyear()));//建设起止年限
		    	sheet.addCell(expDataType(3, 43, obj.getWorkdate()));//开工日期或计划开工日期
		    	sheet.addCell(expDataType(8, 43, obj.getFinishdate()));//竣工日期或计划竣工日期
		    	
		    	
		    
		    	sheet.addCell(expDataType(3, 44, obj.getQtsx()));//需要补充的其它事项
		    	sheet.addCell(expDataType(8, 44, fjType19.toString()));
		    	sheet.addCell(expDataType(3, 45, obj.getDeclareproblem()));//存在问题
		    	sheet.addCell(expDataType(8, 45, fjType12.toString()));
		    	sheet.addCell(expDataType(3, 46, obj.getDeclareplan()));//工程形象进度
		    	sheet.addCell(expDataType(8, 46, fjType11.toString()));
		    	
	    	}
	    	

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
