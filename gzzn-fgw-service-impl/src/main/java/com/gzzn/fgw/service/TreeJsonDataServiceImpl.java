package com.gzzn.fgw.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzzn.fgw.model.SysDuty;

@Service
@Transactional(readOnly = true)
public class TreeJsonDataServiceImpl implements ITreeJsonDataService {
	private static Logger logger = LoggerFactory.getLogger(TreeJsonDataServiceImpl.class);

	@Resource
	private ICommonService commonService;

	private void appendNode(StringBuilder sb, Integer id, Integer pId, String name, boolean isRoot) {
		sb.append("{");
		sb.append("\"id\":");
		sb.append(id);
		sb.append(",\"pId\":");
		sb.append(pId);
		sb.append(",\"name\":\"");
		sb.append(name);
		sb.append("\"");
		if (isRoot) {
			sb.append(",\"nocheck\":true");
		}
		sb.append("},");
	}

	@Override
	public String getSysDutyTreeJson() {
		List<SysDuty> lists = commonService.findAll(SysDuty.class);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		// 根结点
		for (SysDuty c : lists) {
			appendNode(sb, c.getDutyId(), 0, c.getDutyName(), false);
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	

}
