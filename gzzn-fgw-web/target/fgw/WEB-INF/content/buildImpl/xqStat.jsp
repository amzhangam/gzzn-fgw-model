<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/myFunctions" prefix="Custom" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>按区县汇总统计 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/DateUtils.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
		var setting = {
				check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onCheckBoxCheck
				}
			};

$(document).ready(function() {
	showMenu(12,21);//,#auditData
	$("#pjinvestSumType").val("${params.pjinvestSumType}");
	
	//所属单位
	//var orgDatas3 = getJsonDatas("${ctx}/com/getSysOrgJson.json","params.workunitstype=3,4");
	var orgDatas3 = $.parseJSON('${reportOrgJson}');
	var orgIdTree = initCheckBoxTree("orgId", setting, orgDatas3, "${params.organizationId}");//初始化多选的树形下拉框
	//填报人
	//var userDatas3 = getJsonDatas("${ctx}/com/getReportUserJson.json","params.userType=3,4");
	var userDatas3 = $.parseJSON('${reportUserJson}');
	var userIdTree = initCheckBoxTree("userId", setting, userDatas3, "${params.userId}");//初始化多选的树形下拉框
	
	$("#queryBtn").click(function(){
		$("#currentPage").val("0");
		$("form")[0].submit();
	});
	//格式化table中的数值
	formatTabData("tabList",4);
	
	//导出报表时表头的名称
	var timeStr = new Date().AddTime("MM",-1,"yyyy年MM月");
	$("#title1").val("广州市各区（县级市）2013年以来（截至"+ timeStr +"）立项的项目建设情况汇总表");
});


/**
 * 格式化table中的数值
 * @param tableId
 * @param xsMaxLen 小数点后保留的最大长度
 */
function formatTabData(tableId,xsMaxLen){
	xsMaxLen = (xsMaxLen==null||xsMaxLen=="")?4:xsMaxLen;//小数点后最多保留的位数
	var val = "", index = -1, count = 0 ;
	$("#"+ tableId +" td").each(function(){
		val = $(this).text();//当前单元格的数据
		index = val.indexOf('.');//当前数值小数点所在位置，-1代表不存在小数点
		if(index >= 0){
			//获取新数据：小数点后最多保留xsMaxLen位
			val = val.substring(0, (val.length-index-1>xsMaxLen?(index+1+xsMaxLen):val.length));
			count = val.substring(index,val.length);
			for(var i=0;i<count.length;i++){
				val = (val.charAt(val.length-1)=="0")? val.substring(0,val.length-1):val;
			}
			//处理小数点
			val = (val.charAt(val.length-1)==".")? val.substring(0,val.length-1):val;
			$(this).text(val);
		}
	});
}

</script>

	<form id="report" action="${ctx}/buildImpl/xqStat.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv">
		  <input type="hidden" name="params.stuType" id="stuType" value="${params.stuType}" />
		  <input type="hidden" name="title1" id="title1" value="广州市各区（县级市）2013年以来（截至2014年XX月）立项的项目建设情况汇总表"/>
		  <input type="hidden" name="title2" id="title2" value="(单个项目总投资额达1千万元人民币以上,此表数据由广州市各区县提供）"/>
		  <table class="topSearchTab">
			<tr>
				<th>填报单位：</th>
				<td>
					<input type="text" id="orgIdSelName" class="text" value="" readonly="readonly" />
					<input type="hidden" name="params.organizationId" id="orgIdSelID" value="${params.organizationId}"/>
					<div id="orgIdDiv" class="menuContent" style="display:none; position: absolute;width:230px;height: 250px;">
						<ul id="orgId" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>填报人：</th>
				<td>
					<input type="text" id="userIdSelName" class="text" value="" readonly="readonly" />
					<input type="hidden" name="params.userId" id="userIdSelID" value="${params.userId}"/>
					<div id="userIdDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
						<ul id="userId" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>总投资：</th>
				<td>
					<select name="params.pjinvestSumType" id="pjinvestSumType" style="border: 1px solid #be9a4d;width: 180px;">
						<option value="">全部</option>
						<option value="1" ${params.pjinvestSumType==1?'selected':''}>1千万以下</option>
						<option value="2" ${params.pjinvestSumType==2?'selected':''}>1千万（含）以上5千万以下</option>
						<option value="3" ${params.pjinvestSumType==3?'selected':''}>5千万（含）以上</option>
					</select>
				</td>
				<th>月报年份:</th>
					<td>
						<input type="text" id="pfsj" name="params.pfsj" value="${params.pfsj}" size="10"  onfocus="WdatePicker({dateFmt:'yyyy'})"/>
					</td>
				<th>
					<input type="button" class="btn" id="queryBtn" value="查询" />
				</th>
			</tr>
		  </table>
		</div>
		<!-- 相关数据展示 -->	
		<table style="width: 98%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:128px;">按区县汇总统计</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg">
					 <div>
					    <span style="float: right;">    
					     	<input type="button" class="btn" id="expBtn" value="导出" onclick="exportexcel('${ctx}/buildImpl/exportExcel.ac','tabList');"/>      
		               	</span>      
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<table class="list" style="width: 98%;margin: 0 auto;" id="tabList">
			<thead> 
				<tr>
					<td rowspan="2" width="19%" style="text-align: center;">地区</td>
					<td colspan="3" style="text-align: center;">未开工项目</td>
					<td colspan="2" style="text-align: center;">完工项目</td>
					<td colspan="4" style="text-align: center;">在建项目</td>
				</tr>	
				<tr>
					<td width="9%">数量(个)</td>
					<td width="9%">计划总投资(亿元)</td>
					<td width="9%">到上年底累计完成投资(亿元)<!-- 到2013年底累计完成投资(亿元) --></td>
					
					<td width="9%">数量(个)</td>
					<td width="9%">实际完成总投资(亿元)</td>
					
					<td width="9%">数量(个)</td>
					<td width="9%">计划总投资(亿元)</td>
					<td width="9%">本年度投资计划(亿元)<!-- 2014年计划投资(万元) --></td>
					<td width="9%">本年度投资计划完成情况(亿元)<!-- 2014年以来完成投资(亿元) --></td>
				</tr>							
			</thead>
			<tbody>
				<c:forEach items="${listStat}" var="obj" varStatus="status">
					<tr>
						<td style="text-align: center;">
							<c:if test="${obj.sysXq.xqmc eq '广州市'}" var="result">
								市本级
							</c:if>
							<c:if test="${!result}">
								${obj.sysXq.xqmc}
							</c:if>
						</td> 
						
						<td>${obj.lXNum}</td> 	
						<%-- <td><fmt:formatNumber value="${obj.lXPjinvestsum}" pattern="0.0000" /></td>
						<td><fmt:formatNumber value="${obj.lXExpectfinishinvest}" pattern="0.0000" /></td>  --%>
						<td>${obj.lXPjinvestsum}</td>
						<td>${obj.lXExpectfinishinvest}</td> 
							
						<td>${obj.wGNum}</td> 	
						<%-- <td><fmt:formatNumber value="${obj.wGExpectfinishinvest+obj.wGCurrentfinishinvest}" pattern="0.0000" /></td> 	 --%>
						<td>${obj.wGExpectfinishinvest+obj.wGCurrentfinishinvest}</td> 	
						
						<td>${obj.zJNum}</td> 	
					<%-- 	<td><fmt:formatNumber value="${obj.zJPjinvestsum}" pattern="0.0000" /></td> 	
						<td><fmt:formatNumber value="${obj.zJPlaninvestsum}" pattern="0.0000" /></td> 	
						<td><fmt:formatNumber value="${obj.zJCurrentfinishinvest}" pattern="0.0000" /></td> --%>
						<td>${obj.zJPjinvestsum}</td> 	
						<td>${obj.zJPlaninvestsum}</td> 	
						<td>${obj.zJCurrentfinishinvest}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</form>
</body>
</html>
