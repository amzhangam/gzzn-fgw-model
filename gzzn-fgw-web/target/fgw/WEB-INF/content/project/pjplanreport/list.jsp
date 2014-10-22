<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/myFunctions" prefix="Custom" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>正式项目库</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<script type="text/javascript">
$(document).ready(function() {
	 showMenu(2);
	 //alert( document.body.clientWidth+"===="+$("#mainContainer").width());
	 $("#mainContainer").width(document.body.clientWidth-$("#leftDiv").width()-10);//改变页面主体的宽度
	 $(".printDivStyle").niceScroll({touchbehavior:false,cursorcolor:"#808080",cursoropacitymax:0.6,cursorwidth:8});
	
	 /**报表统计时，相关信息的处理。此方法在存储在reportQuery.jsp页面*/
	 reportStaInfo();
});

/**
 * 导出数据到excel
 * @param url
 * @param tableid
 * @param rowNum 该参数不传是，默认=2
 */
function exportOneexcel(projectid){
	window.location = "${ctx}/project/pjbaseinfo/expOneExcel.ac?id=" + projectid;
/* 
	var oldurl = document.forms[0].action;
	document.forms[0].action = url;
	document.forms[0].submit();
	document.forms[0].action = oldurl; */
}

</script>

	<form id="report" action="${ctx}/report/pjPlanReport.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv">
			<%-- <%@include file="../../report/reportQuery.jsp" %> --%>
			<%@include file="reportQuery.jsp" %>
		</div>
		<!-- 相关数据展示 -->	
		<table style="width:98%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:128px;">正式项目库</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg">
					 <div>
					   <span style="float: right;">     
					   		<c:if test="${objectMap['XMSB_XM_XMJHBBGL_EXP']}">
			            	<input type="button" class="btn" id="exportBtn" value="导出" onclick="exportexcel('${ctx}/report/expExcel.ac','tabList',3);"/>    
			            	<input type="button" class="btn" id="setxmcblb" value="批量设置项目储备类别"/>
			            	</c:if>
		               	</span>     
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
 		<div class="printDivStyle" id="exp">
			<table class="printTab" id="tabList">
				<thead> 
					<tr>
						<th width="65px" rowspan="2">序号</th>
						<th width="150px" rowspan="2">项目名称</th>
						
						<!-- <th width="150px" rowspan="2">项目编码</th>
						<th width="150px" rowspan="2">项目业主</th>
						<th width="150px" rowspan="2">项目分类</th>
						<th width="150px" rowspan="2">行业类型</th>
						<th width="150px" rowspan="2">资金性质</th>
						<th width="150px" rowspan="2">当前处理部门</th> -->
						
						<th width="250px" rowspan="2">主要建设内容</th>	
						<th width="125px" rowspan="2">建设起止年份</th>
						<th width="125px" rowspan="2">总投资(万元）</th>
						 
						<th colspan="4">资金来源(万元)</th>
						<th colspan="3">
							预计至&nbsp;${params.expectFinishInvestYear==null?"XX":params.expectFinishInvestYear}&nbsp;年底累计完成投资 (万元)
						</th>	
						<th colspan="6">
							${params.planInvestYear==null?"XX":params.planInvestYear}&nbsp;年投资计划建议(万元)
						</th>		
						 
						<th width="250px" rowspan="2">市财政资金安排渠道建议</th>
						<th width="250px" rowspan="2">申报依据</th>
						<th width="250px" rowspan="2">项目主管单位</th>
						<th width="250px" rowspan="2">建设管理单位</th>
						<th width="250px" rowspan="2">备注</th>
						<th width="180px" rowspan="2">修改时间</th>
						<th width="100px" rowspan="2">操作</th>
					</tr>
					<tr>
						<th width="125px">市财政资金</th>
						<th width="125px">自有资金</th>
						<th width="125px">融资(含银行贷款)</th>	
						<th width="125px">其它 </th>
						
						<th width="125px">截止年份</th>		
						<th width="125px">合计</th>					
						<th width="125px">其中市财政资金</th>	
							
						<th width="125px">计划年份</th>		
						<th width="125px">计划投资合计</th>	
						<th width="125px">市财政资金</th>
						<th width="125px">自有资金</th>
						<th width="125px">融资(含银行贷款)</th>	
						<th width="125px">其它 </th>
					</tr>				
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="obj" varStatus="status">
						<tr>
							<td>${page.size*page.current+status.index+1}&nbsp;</td> 
							<td><a href="${ctx}/project/pjbaseinfo/view.ac?id=${obj.pjbaseinfo.projectid}&type=0">${obj.pjbaseinfo.projectname}</a></td>
							
							<%-- <td>${obj.pjbaseinfo.projectcode}</td> 	
							<td>${obj.pjbaseinfo.sysOrganizationByDeclareunitsid.organizationName}</td> 	
							<td>${obj.pjbaseinfo.xmfl}</td> 	
							<td>${obj.pjbaseinfo.xmsbHylb.hylbmc}</td> 	
							<td>${obj.pjbaseinfo.xmsbZjxz.zjxzmc}</td> 	
							<td>${obj.pjbaseinfo.nextauditdeptname}</td> 	 --%>
							
							<td title="${obj.pjbaseinfo.projectcontent}">${Custom:subString(obj.pjbaseinfo.projectcontent, 36, '...')}</td> 
							<td>${obj.pjbaseinfo.startyear}~${obj.pjbaseinfo.endyear}</td> 						
							<td>${obj.pjinvestsum}</td> 
													
							<td>${obj.pjinvestcity}</td> 						
							<td>${obj.pjinvestcompany}</td> 						
							<td>${obj.pjinvestbank}</td> 						
							<td>${obj.pjinvestother}</td> 	
												
							<td>${obj.pjbaseinfo.expectfinishinvestyear}</td> 	
							<td>${obj.pjbaseinfo.expectfinishinvest}</td> 						
							<td>${obj.pjbaseinfo.expectfinishotherinvest}</td> 	
												
							<td>${obj.planinvestyear}</td> 	
							<td>${obj.planinvestsum}</td> 						
							<td>${obj.planinvestcity}</td> 						
							<td>${obj.planinvestcompany}</td> 						
							<td>${obj.planinvestbank}</td> 						
							<td>${obj.planinvestother}</td> 	
												
							<td>${obj.pjinvestadvice}</td> 						
							<td title="${obj.pjbaseinfo.declaregist}">${Custom:subString(obj.pjbaseinfo.declaregist, 36, '...')}</td> 						
							<td>${obj.pjbaseinfo.sysOrganizationByDirectorunitsid.organizationName}</td> 					
							<td>${obj.pjbaseinfo.manageunitsname}</td> 						
							<td>${xmztMap[obj.pjbaseinfo.pjstatus]}</td> 	
							<td><fmt:formatDate value='${obj.pjbaseinfo.modifiedTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>	
							<td><a href="javascript:exportOneexcel(${obj.pjbaseinfo.projectid})">导出</a></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="10" class="line2" style="text-align: left;"><%@include file="../../changePage.jsp" %></td>
						<td colspan="15" class="line2"></td>     
					</tr>
				</tbody>
			</table>	
		</div>			
	</form>
</body>
</html>
