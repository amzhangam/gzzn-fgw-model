package com.gzzn.fgw.implExcel;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jxl.Sheet;

import com.gzzn.fgw.model.PjBulidInfoTemp;
import com.gzzn.fgw.model.SysOrganization;
import com.gzzn.fgw.model.SysUser;
import com.gzzn.fgw.webUtil.ExcelHelper;
import com.gzzn.fgw.webUtil.ValidateUtil;

@Service
@Transactional
public class PjBulidInfoImpExcel extends ExcelImport<PjBulidInfoTemp, Long> {

	@Override
	public PjBulidInfoTemp readRow(Sheet sheet, int row, SysUser user, SysOrganization sysOrgn) {
		PjBulidInfoTemp obj = new PjBulidInfoTemp();
		String value = ExcelHelper.getStringValue(sheet.getCell(1, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 200)) {
				//重点项目标注（0-非重点项目；★1为省重点项目；■2为市重点项目）
				if(value.startsWith("★")){
					obj.setZdxmbz(1);
					obj.setProjectname(value.replaceFirst("★", ""));
				}else if(value.startsWith("■")){
					obj.setZdxmbz(2);
					obj.setProjectname(value.replaceFirst("■", ""));
				}else{
					obj.setZdxmbz(0);
					obj.setProjectname(value);
				}
			} else {
				msg.add("B" + (row + 1) + "：项目名称超过100个字");
				return null;
			}
		} else {
			msg.add("B" + (row + 1) + "：项目名称不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(2, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 4000)) {
				obj.setProjectcontent(value);
			} else {
				msg.add("C" + (row + 1) + "：建设内容及规模超过2000个字");
				return null;
			}
		} else {
			msg.add("C" + (row + 1) + "：建设内容及规模不能为空");
			return null;
		}
		
		Integer startYear=0,endYear=0;
		value = ExcelHelper.getStringValue(sheet.getCell(3, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 50)) {
				//检查建设起止年限的格式是否正确：年份必须在1900-2099间
				if(ValidateUtil.Regular(value, "^(19|20)\\d{2}-(19|20)\\d{2}$")){//[1-9]\\d{3}\\-[1-9]\\d{3}
					startYear = Integer.valueOf(value.split("-")[0]);
					endYear = Integer.valueOf(value.split("-")[1]);
					if(startYear>endYear){
						msg.add("D" + (row + 1) + "：建设起止年限中“拟开工时间”不能大于“拟建成时间”");
						return null;

					}else{
						obj.setBulidQznx(value);
					}
				}else{
					msg.add("D" + (row + 1) + "：建设起止年限格式错误，其值到年【年份在1900-2099间】中间用“-”隔开，格式如：2013-2015");
					return null;
				}
			} else {
				msg.add("D" + (row + 1) + "：建设起止年限超过25个字");
				return null;
			}
		} else {
			msg.add("D" + (row + 1) + "：建设起止年限不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(4, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.Regular(value, ValidateUtil.DOUBLE_NEGATIVE)) {
				obj.setPjinvestsum(Double.valueOf(value)); 
			} else {
				msg.add("E" + (row + 1) + "：总投资（万元）格式错误");
				return null;
			}
		} else {
			msg.add("E" + (row + 1) + "：总投资（万元）不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(5, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.Regular(value, ValidateUtil.DOUBLE_NEGATIVE)) {
				obj.setExpectfinishinvest(Double.valueOf(value)); 
			} else {
				msg.add("F" + (row + 1) + "：到上年底累计完成投资（万元）格式错误");
				return null;
			}
		} else {
			msg.add("F" + (row + 1) + "：到上年底累计完成投资（万元）不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(6, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.Regular(value, ValidateUtil.DOUBLE_NEGATIVE)) {
				obj.setPlaninvestsum(Double.valueOf(value)); 
			} else {
				msg.add("G" + (row + 1) + "：本年度投资计划（万元）格式错误");
				return null;
			}
		} else {
			msg.add("G" + (row + 1) + "：本年度投资计划（万元）不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(7, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.Regular(value, ValidateUtil.DOUBLE_NEGATIVE)) {
				obj.setCurrentfinishinvest(Double.valueOf(value)); 
			} else {
				msg.add("H" + (row + 1) + "：本年度投资计划完成情况（万元）格式错误");
				return null;
			}
		} else {
			msg.add("H" + (row + 1) + "：本年度投资计划完成情况（万元）不能为空");
			return null;
		}
		
		String valuePfwh = ExcelHelper.getStringValue(sheet.getCell(8, row));
		if (ValidateUtil.StrNotNull(valuePfwh)) {
			if (ValidateUtil.isStrByteLenNotOut(valuePfwh, 1000)) {
				obj.setLxpfwh(valuePfwh);
			} else {
				msg.add("I" + (row + 1) + "：立项批复文号超过500个字");
				return null;
			}
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(9, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 150)) {
				obj.setPfdw(value);
			} else {
				msg.add("J" + (row + 1) + "：批复单位超过75个字");
				return null;
			}
		} 
		
		/**标注：1、拟开工时间的年份不能小于批复时间；2、有批复时间就必须有文号*/
		Integer pfsjYear=0;
		value = ExcelHelper.getStringValue(sheet.getCell(10, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 20)) {
				//检查建设起止年限的格式是否正确：年份必须在1900-2099间，月份在1-12或01-12之间
				if(ValidateUtil.Regular(value, "^(19|20)\\d{2}-(0[1-9]|1[0-2])$")){//[1-9]\\d{3}\\-\\d{1,2}
					pfsjYear = Integer.valueOf(value.split("-")[0]);
					if(startYear < pfsjYear){
						msg.add("D" + (row + 1) + "：建设起止年限中的“拟开工时间”不能小于“批复时间”中的年份");
						return null;
					}
					if(ValidateUtil.StrNotNull(valuePfwh)){
						obj.setPfsj(value);
					}else{
						msg.add("I" + (row + 1) + "：有批复时间就必须有立项批复文号");
						return null;
					}
				}else{
					msg.add("K" + (row + 1) + "：批复时间格式错误，其值到月【其中年份在1900-2099间，月份在01-12或1-12间】，格式如：2014-03 或 2014-3");
					return null;
				}
				
			} else {
				msg.add("K" + (row + 1) + "：批复时间超过20个字符");
				return null;
			}
		} 

		value = ExcelHelper.getStringValue(sheet.getCell(11, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 100)) {
				obj.setXmlx(value);
			} else {
				msg.add("L" + (row + 1) + "：项目类型超过50个字");
				return null;
			}
		} else {
			msg.add("L" + (row + 1) + "：项目类型不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(12, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 100)) {
				obj.setRemark(value);
			} else {
				msg.add("M" + (row + 1) + "：项目类别超过50个字");
				return null;
			}
		} else {
			msg.add("M" + (row + 1) + "：项目类别不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(13, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 50)) {
				obj.setCylb(value);
			} else {
				msg.add("N" + (row + 1) + "：产业类别超过25个字");
				return null;
			}
		} else {
			msg.add("N" + (row + 1) + "：产业类别不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(14, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 50)) {
				obj.setTzlx(value);
			} else {
				msg.add("O" + (row + 1) + "：投资类型超过25个字");
				return null;
			}
		} else {
			msg.add("O" + (row + 1) + "：投资类型不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(15, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 50)) {
				obj.setSshy(value);
			} else {
				msg.add("P" + (row + 1) + "：所属行业超过25个字");
				return null;
			}
		} else {
			msg.add("P" + (row + 1) + "：所属行业不能为空");
			return null;
		}
		
		value = ExcelHelper.getStringValue(sheet.getCell(16, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 150)) {
				obj.setXmdw(value);
			} else {
				msg.add("Q" + (row + 1) + "：项目(法人)单位超过75个字");
				return null;
			}
		} else {
			msg.add("Q" + (row + 1) + "：项目(法人)单位不能为空");
			return null;
		}

		value = ExcelHelper.getStringValue(sheet.getCell(17, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 50)) {
				obj.setProjectcontact(value);
			} else {
				msg.add("R" + (row + 1) + "：项目联系人超过25个字");
				return null;
			}
		} else {
			msg.add("R" + (row + 1) + "：项目联系人不能为空");
			return null;
		} 
		
		value = ExcelHelper.getStringValue(sheet.getCell(18, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 50) && ValidateUtil.Regular(value, ValidateUtil.PHONE)) {
				obj.setPhone(value);
			} else {
				msg.add("S" + (row + 1) + "：联系电话长度超过50或电话格式错误");
				return null;
			}
		} else {
			msg.add("S" + (row + 1) + "：联系电话不能为空");
			return null;
		} 
		
		value = ExcelHelper.getStringValue(sheet.getCell(19, row));
		if (ValidateUtil.StrNotNull(value)) {
			if (ValidateUtil.isStrByteLenNotOut(value, 50)) {
				obj.setXmsd(value);
			} else {
				msg.add("T" + (row + 1) + "：项目属地超过25个字");
				return null;
			}
		} else {
			msg.add("T" + (row + 1) + "：项目属地不能为空");
			return null;
		}
		
		//单位ID （仅限各区县发改局单位用户）
		//obj.setSysOrganization(user.getSysOrganization());
		//obj.setSysOrganization(sysOrgn);
		obj.setTbdw(sysOrgn.getOrganizationName());
		//上传数据的用户信息
		obj.setSysUser(user);
		//上传数据时间
		obj.setUpdateTime(new Date());
		
		return obj;
	}

	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PjBulidInfoTemp> doFilter(List<PjBulidInfoTemp> datas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PjBulidInfoTemp readRow(Sheet sheet, int row) {
		// TODO Auto-generated method stub
		return null;
	}

}
