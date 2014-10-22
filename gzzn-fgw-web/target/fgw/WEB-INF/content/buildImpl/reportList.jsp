<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/myFunctions" prefix="Custom" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>填报项目管理 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<style type="text/css">
/**2014-07-17：LHQ加入，jquery dialog 样式*/
.ui-widget-header {
	border: 1px solid #85B0E3;background:#D3E4FF;color: #002D5F;font-weight: bold;text-align: center;
}
.ui-widget-overlay {
	background: #0A0A0A;opacity: .5;filter: Alpha(Opacity=50);
}
.ui-dialog-titlebar-close{
	display: none;
}
</style>
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
	$("#mainContainer").width(document.body.clientWidth-$("#leftDiv").width()-10);//改变页面主体的宽度
	$(".printDivStyle").niceScroll({touchbehavior:false,cursorcolor:"#808080",cursoropacitymax:0.6,cursorwidth:8});
	showMenu(12,21);//,#auditData
	if("${not empty message}" == "true"){
	 	mac.alert(Url.decode("${message}"));
	 	//alert(Url.decode("${message}"))
	}
	
	//区域
	var xqIdDatas = getJsonDatas("${ctx}/com/getSysXqJson.json","");
	var xqIdTree = initCheckBoxTree("xqId", setting, xqIdDatas, "${params.xqId}");//初始化多选的树形下拉框
	//填报单位
	//var orgDatas3 = getJsonDatas("${ctx}/com/getSysOrgJson.json","params.workunitstype=3,4");
	var orgDatas3 = $.parseJSON('${reportOrgJson}');
	var orgIdTree = initCheckBoxTree("orgId", setting, orgDatas3, "${params.organizationId}");//初始化多选的树形下拉框
	//填报人
	//var userDatas3 = getJsonDatas("${ctx}/com/getReportUserJson.json","params.userType=3,4");
	var userDatas3 = $.parseJSON('${reportUserJson}');
	var userIdTree = initCheckBoxTree("userId", setting, userDatas3, "${params.userId}");//初始化多选的树形下拉框
	//产业类别
	var cylbDatas = getJsonDatas("${ctx}/com/getDictItemsJson.json","params.dictName=产业类别");
	var cylbTree = initCheckBoxTree("cylb", setting, cylbDatas, "${params.cylb}");
	//重点项目
	var zdxmbzDatas = getJsonDatas("${ctx}/com/getZDXMBZJson.json");
	var zdxmbzTree = initCheckBoxTree("zdxmbz", setting, zdxmbzDatas, "${params.zdxmbz}");
	
	$("#queryBtn").click(function(){
		//$("#currentPage").val("0");
		//$("form")[0].submit();
		var str = "";
		//判断项目总投资查询输入值的合法性：DECIMAL(18,2)
		var reg = new RegExp("^([1-9]{1}[0-9]{0,15}|0|0\.{1}[0-9]{0,2}|[1-9]{1}[0-9]{0,15}\.{1}[0-9]{0,2})$");
		$(".moneyText").each(function(index) {
			if($(this).val()!="" && !reg.test($(this).val())){
				 str += "总投资必须为数值且小数点后可保留两位小数！";//必须符合金额格式
				 $(this).focus();//将光标订到指定位置
				 return false;
			}
		});
		if(str==""){
			$("#currentPage").val("0");
			$("form")[0].submit();
		}else{
			mac.alert(str);
		}
	});
	
	//清空事件
	$("#clearBtn").click(function(){
		$(".topSearchTab input[type='text']").each(function(index) {
			$(this).val("");
		});
		clearCheckNodes(xqIdTree);
		clearCheckNodes(orgIdTree);
		clearCheckNodes(userIdTree);
		clearCheckNodes(cylbTree);
		clearCheckNodes(zdxmbzTree);
	});
	
	//勾选复选框，进行删除操作
	checkAllBox();
	delBtnClick("${ctx}/buildImpl/delete.ac?params.stuType=2&id=");
	
	
	/**展示“按上报用户统计”的相关信息*/	
	winInit($("#yhtjWin"), "按上报用户统计", 550, 450);
	$("#yhtjBtn").click(function(){
		$("#yhtjWin").dialog("option","closeOnEscape",false);
		//$("#yhtjWin").dialog("option","title","按上报用户统计");
		$("#yhtjWin").dialog("option","buttons",{
			"关闭":function(){
				closeDialog($(this));
			}
		});
		$("#yhtjWin").dialog("open");
	});
	mergerTable("yhtjTable");//合并表格
});

/**
 * 合并表格
 * @param tableId
 */
function mergerTable(tableId){
	var rows = $("#" + tableId).find("tr");
	var allCount = 0;
	var firstPointer=1, nextPointer=2;//移动的指针游标
	while(firstPointer < rows.length){
		//alert(firstPointer +"====111哈哈哈====");
		var cell = $(rows[firstPointer]).find("td:first");
		
		var rowCount = 1;
		var rowNum = parseInt($(rows[firstPointer]).find("td:eq(2)").text());
		nextPointer = firstPointer+1;
		while(nextPointer < rows.length){
			//alert(firstPointer +"哈哈哈哈====="+ nextPointer);
			var cell2 = $(rows[nextPointer]).find("td:first");
			if(cell.text() == cell2.text()){
				rowCount++;
				rowNum += parseInt($(rows[nextPointer]).find("td:eq(2)").text());
				cell2.hide();
			}else{
				break;
			}
			nextPointer++;
		}
		firstPointer = nextPointer;
		if(cell.text()!=""){
			cell.text(cell.text()+"【"+ rowNum +"】");
			cell[0].rowSpan = rowCount;
		}
		allCount += rowNum;
  	}
	$("#" + tableId).append("<tr><th colspan='2'>合计</th><th>"+ allCount +"</th></tr>");
}

</script>

	<form id="report" action="${ctx}/buildImpl/reportList.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv">
		  <table class="topSearchTab">
			<tr>
				<th>项目名称：</th>
				<td>
					<input type="text" class="text" name="params.projectname" id="projectname" value="${params.projectname }"/>
				</td>
				<th>产业类别：</th>
				<td>
					<input type="text" id="cylbSelName" class="text" value="" readonly="readonly" />
					<input type="hidden" name="params.cylb" id="cylbSelID" value="${params.cylb}"/>
					<div id="cylbDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
						<ul id="cylb" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>项目类别：</th>
				<td>
					<input type="text" class="text" name="params.remark" id="remark" value="${params.remark }"/>
				</td>
				<th>批复时间：</th>
				<td>
					<input type="text" class="text" name="params.pfsj" id="pfsj" value="${params.pfsj }"/>
				</td>
				<th>总投资：</th>
				<td>
					<input type="text" class="text moneyText" name="params.pjinvestSum" id="pjinvestSum" value="<fmt:formatNumber value='${params.pjinvestSum }' pattern='#0.##'/>" />
					到&nbsp;
					<input type="text" class="text moneyText" name="params.pjinvestSum2" id="pjinvestSum2" value="<fmt:formatNumber value='${params.pjinvestSum2 }' pattern='#0.##'/>" />
					万元
				</td>
			</tr>
			<tr>
				<th>重点项目：</th>
				<td>
					<input type="text" id="zdxmbzSelName" class="text" value="" readonly="readonly" />
					<input type="hidden" name="params.zdxmbz" id="zdxmbzSelID" value="${params.zdxmbz}"/>
					<div id="zdxmbzDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
						<ul id="zdxmbz" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>项目属地：</th>
				<td>
					<input type="text" name="params.xqmc" id="xqIdSelName" class="text" value="" readonly="readonly" />
					<input type="hidden" name="params.xqId" id="xqIdSelID" value="${params.xqId}"/>
					<div id="xqIdDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
						<ul id="xqId" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
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
				<td colspan="2" style="text-align: center;">
					<input type="button" class="btn" id="queryBtn" value="查询" />&nbsp;&nbsp;
					<input type="button" class="btn" id="clearBtn" value="清空" />  
				</td>
			</tr>
		  </table>
		</div>
		<!-- 相关数据展示 -->	
		<table style="width: 98%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:128px;">填报项目管理</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg">
					 <div>
					 	<font color="red">注：省重点项目请于项目名称前标注★ 市重点项目请于项目名称前标注■</font>
					    <span style="float: right;">  
					     	<input type="button" class="btn" id="delBtn" value="批量删除"/>&nbsp;&nbsp;   
					     	<input type="button" class="btn" id="yhtjBtn" value="按上报用户统计"/>&nbsp;&nbsp;  
					     	<input type="button" class="btn" id="expBtn" value="按国家发改委格式导出" onclick="exportexcel('${ctx}/buildImpl/exportListExcel.ac','tabList');"/>        
		               	</span>      
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="printDivStyle" id="exp">
			<table class="printTab" id="tabList" style="width:3580px">
			<!-- <table class="list" style="width: 98%;margin: 0 auto;"> -->
				<thead> 
					<tr>
						<th width="40px"><input type="checkbox" id="checkAll" /></th>
						<th width="50px">序号</th>
						<th width="190px">项目名称</th>
						<th width="250px">建设内容及规模</th>	
						<th width="150px">建设起止年限 </th>
						<th width="150px">总投资（万元）</th>	
						<th width="150px">到上年底累计完成投资（万元）<!-- 到2013年底累计完成投资（万元） --></th>	
						<th width="150px">本年度投资计划（万元）<!-- 2014年投资计划（万元） --></th>	
						<th width="150px">本年度投资计划完成情况（万元）<!-- 2014年以来完成投资（万元） --></th>	
						<th width="250px">立项批复文号</th>	
						<th width="250px">批复单位</th>	
						<th width="100px">批复时间</th>	
						<th width="250px">项目类型</th>	
						<th width="250px">项目类别</th>	
						<th width="150px">产业类别</th>
						<th width="150px">投资类型</th>	
						<th width="250px">所属行业</th>
						<th width="250px">项目(法人)单位</th>
						<th width="100px">项目联系人</th>	
						<th width="100px">联系电话</th>	
						<th width="100px">项目属地</th>
						<th width="250px">填报单位</th>	
						<th width="100px">填报人</th>	
						<th width="100px">操作</th>
					</tr>				
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="obj" varStatus="status">
						<tr>
							<td>
								<input type="checkbox" value="${obj.bulidId}" class="subcheck"/>
							</td>
							<td>${page.size*page.current+status.index+1}</td> 
							<td>
								<c:if test="${obj.zdxmbz eq 1 }"><font color="red">★</font></c:if>
								<c:if test="${obj.zdxmbz eq 2 }"><font color="red">■</font></c:if>
								${obj.projectname}&nbsp;
							</td>
							<td>${obj.projectcontent}&nbsp;</td> 	
							<td>${obj.bulidQznx}&nbsp;</td> 	
							<td>${obj.pjinvestsum}&nbsp;</td> 	
							<td>${obj.expectfinishinvest}&nbsp;</td> 	
							<td>${obj.planinvestsum}&nbsp;</td> 	
							<td>${obj.currentfinishinvest}&nbsp;</td> 	
							<td>${obj.lxpfwh}&nbsp;</td> 
							<td>${obj.pfdw}&nbsp;</td> 
							<td>${obj.pfsj}&nbsp;</td> 
							<td>${obj.xmlx}&nbsp;</td> 
							<td>${obj.remark}&nbsp;</td> 
							<td>${obj.cylb}&nbsp;</td> 	
							<td>${obj.tzlx}&nbsp;</td> 	
							<td>${obj.sshy}&nbsp;</td> 
							<td>${obj.xmdw}&nbsp;</td> 
							<td>${obj.projectcontact}&nbsp;</td> 	
							<td>${obj.phone}&nbsp;</td> 
							<td>${obj.xmsd}&nbsp;</td> 	
							<%-- <td>${obj.sysOrganization.sysXq.xqmc}&nbsp;</td>  --%>
							<td>${obj.sysOrganization.organizationName}&nbsp;</td>
							<%-- <td>${obj.tbdw}&nbsp;</td>  --%>
							<td>${obj.sysUser.userName}&nbsp;</td>	
							<td>
								<a href="javascript:deleteAction('${ctx}/buildImpl/delete.ac?params.stuType=2&id=${obj.bulidId}')">删除</a>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="10" class="line2"><%@include file="../changePage.jsp" %></td>
						<td colspan="14" class="line2">&nbsp;</td>
				   </tr>
				</tbody>
			</table>	
		</div>
	</form>
	
	
	<div id="yhtjWin" style="display: none;">
		<table class="printTab" id="yhtjTable" style="width: 98%;margin: 0 auto;">
		  <thead>
			<tr>
				<th width="50%">填报单位</th>
				<th width="25%">填报人</th>
				<th width="25%">上报数据条数</th>
			</tr>
		 </thead>	
		 <tbody>
		 	<c:forEach items="${mapLinkStat}" var="item" varStatus="status">			
				<tr>
					<td>${item.value.sType1}</td>
					<td>${item.value.sType2}</td>
					<td>${item.value.lXNum}</td>
				</tr>
			</c:forEach>
		 </tbody>				
		</table>
	</div>
	
</body>
</html>
