<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>项目报表</title>
<s:head/>
</head>
<body>
<!-- 自定义 js -->
<!-- My97DatePicker js START -->
<script src="${ctx}/resources/js/My97DatePicker/WdatePicker.js"></script>
<!-- My97DatePicker js END -->
<!-- Ztree js START -->
<script src="${ctx}/resources/js/eps/ztreePublic.js"></script>
<script type="text/javascript">
/**
 * Ztree
 */
$(function(){
	//项目类型下拉框
	var projectTypeDatas=getDatas("${ctx}/project/pjbaseinfo/getProjectTypeJson.json");
	$("#projectTypeName").focus(function(){
		initZtree("projectType",projectTypeDatas,150,200);
	});
	//清空事件
	$("#clearBtn").click(function(){
		$("#projectName").val("");
		$("#projectTypeName").val("");
		$("#projectTypeId").val("");
		$("#startyear").val("");
		$("#endyear").val("");
		$("#organizationName").val("");
		$("#manageUnitsName").val("");
		$("#projectContent").val("");
	});
	//提交查询
	$("#queryBtn").click(function(){	
		$("#report").attr("action","${ctx}/report/list.ac").submit();
	}); 
	//提交导出
	$("#exportBtn").click(function(){	
		$("#report").attr("action","${ctx}/report/exportExcel.json").submit();
	}); 
	showMenu(3,64);
});
</script>
<!-- Ztree js END-->

<form id="report"  method="post" enctype="multipart/form-data">
		<table width="100%">
			<tr>
				<td class="tbHeadTitle" id="headTitle">申报计划项目报表</td>
				<td class="tbHeadBg" style="width: 1%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 99%;">
					<div>　　
					　<!-- 项目名称、项目类型（新开工项目、续建项目、预备项目）、
					建设起止年份、项目管理单位、主管单位、主要建设内容、申报依据、市财政资金安排渠道建议 -->
					
						项目名称：
							<input type="text" class="text" name="params.projectName" id="projectName" 
							       value="${params.projectName }"/>	
						项目类型：
							<input type="text" id="projectTypeName"
							       value="${xmlxMap[params.projectType]}"
							       readonly="readonly" class="text"/>
							<input type="hidden" name="params.projectType" id="projectTypeId"
								   value="${params.projectType}"/>
						建设起止年份：
							<input type="text" name="params.startyear" id="startyear" style="width:40px"
							       class="text" readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy'})"
							       value="<c:if test="${params.startyear!=0}">${params.startyear}</c:if>"/>至
							<input type="text" name="params.endyear" id="endyear" style="width:40px" 
							 	   class="text" readonly="readonly" onFocus="WdatePicker({dateFmt:'yyyy'})"
							 	   value="<c:if test="${params.endyear!=0}">${params.endyear}</c:if>"/>
						项目主管单位：
							<input type="text" class="text" name="params.organizationName" id="organizationName"
								   value="${params.organizationName }"/>	
						建设管理单位： 
							<input type="text" class="text" name="params.manageUnitsName" id="manageUnitsName"
								   value="${params.manageUnitsName }"/>	
						主要建设内容： 
							<input type="text" class="text" name="params.projectContent" id="projectContent"
							       value="${params.projectContent }"/>							
						&nbsp;&nbsp; 
						<c:if test="${objectMap['XMSB_BB_SBXMJHBB_VIEW']}">
						<input type="button" class="btn" id="queryBtn" value="查询" />
						<input type="button" class="btn" id="clearBtn" value="清空" />  
						</c:if>
						<c:if test="${objectMap['XMSB_BB_SBXMJHBB_EXP']}">
						<input type="button" class="btn" id="exportBtn" value="导出" /> 
						</c:if>
					</div></td>
				<td class="tbHeadBg" style="width: 3.5%;"><div class="tbHeadRight"></div></td>
			</tr>
		</table>
 		
		<table  width="100%" border="1" cellpadding="1" cellspacing="1" bordercolor="AEC2D5">
			<thead> 
				<tr>
					<td width="120" rowspan="2">序号</td>
					<td width="120" rowspan="2">项目名称</td>
					<td width="1620" rowspan="2">主要建设内容</td>	
					<td width="120" rowspan="2">建设起止年份</td>
					<td width="120" rowspan="2">总投资（万元）</td>
					
					<td width="240" colspan="4" style="text-align: center">资金来源（万元）</td>
					<td width="180" colspan="3" style="text-align: center">累计完成资金（万元）</td>					
					<td width="360" colspan="6" style="text-align: center">计划投资资金（万元）</td>
					
					<td width="120" rowspan="2">市财政资金安排渠道建议</td>
					<td width="1520" rowspan="2">申报依据</td>
					<td width="120" rowspan="2">项目主管单位</td>
					<td width="120" rowspan="2">建设管理单位</td>
					<td width="120" rowspan="2">备注</td>
				</tr>
				<tr>
					<td width="120">市财政资金</td>
					<td width="120">自有资金</td>
					<td width="120">融资（含银行贷款）</td>	
					<td width="120">其他</td>
					
					<td width="120">截止年份</td>		
					<td width="120">合计</td>					
					<td width="120">其中市财政资金</td>	
								
					<td width="120">计划年份</td>		
					<td width="120">计划投资合计</td>	
					<td width="120">市财政资金</td>
					<td width="120">自有资金</td>
					<td width="120">融资（含银行贷款）</td>	
					<td width="120">其他</td>
				</tr>				
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td>${page.size*page.current+status.index+1}&nbsp;</td> 
						<td>${obj.projectName}</td>
						<td>${obj.projectContent}</td> 
						<td>${obj.startyear}~${obj.endyear}</td> 						
						<td>${obj.pjinvestSum}</td> 
												
						<td>${obj.pjinvestCity}</td> 						
						<td>${obj.pjinvestCompany}</td> 						
						<td>${obj.pjinvestBank}</td> 						
						<td>${obj.pjinvestOther}</td> 	
											
						<td>${obj.expectFinishInvestYear}</td> 	
						<td>${obj.expectFinishInvest}</td> 						
						<td>${obj.expectFinishOtherInvest}</td> 	
											
						<td>${obj.planInvestYear}</td> 	
						<td>${obj.planInvestSum}</td> 						
						<td>${obj.planInvestCity}</td> 						
						<td>${obj.planInvestCompany}</td> 						
						<td>${obj.planInvestBank}</td> 						
						<td>${obj.planInvestOther}</td> 	
											
						<td>${obj.pjinvestAdvice}</td> 						
						<td>${obj.declaregist}</td> 						
						<td>${obj.organizationName}</td> 					
						<td>${obj.manageUnitsName}</td> 						
						<td>${xmztMap[obj.pjstatus]}</td> 		
					</tr>
				</c:forEach>
				<tr>
					<td colspan="23" class="line2" style="text-align: right;">
					<%@include file="../changePage.jsp" %></td>
				</tr>
			</tbody>
		</table>			
	</form>
</body>
</html>
