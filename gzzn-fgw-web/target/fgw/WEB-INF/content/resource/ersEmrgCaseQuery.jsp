<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>应急案例库</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="避难场所-避难场所查询结果">
<link href="${ctx}/resources/css/2_event.css" type="text/css" rel="stylesheet">
<link href="${ctx}/resources/css/style.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/public.js"　type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript">

	function infoToDelete(objTD) {
		$.ajax({
			type : "POST",
			url : "${ctx }/resource/ersEmrgCaseDelete.json",
			data : {
				ids : objTD
			},
			success : function(msg) {
				if (msg.success) {
					//说明删除成
					window.location.reload();
				} else {
					parent.mac.alert(msg.info);
				}
			},
			error : function() {
				parent.mac.alert("系统响应异常");
			},
			dataType : 'json'
		});
	}
	function showResponse(data) {
		if (data.success) {
			parent.mac.alert(data.info);
			window.location.href = "${ctx }/resersEmrgCaseExpertQuery.ac";
		} else {
			parent.mac.alert('失败');
		}
	}
</SCRIPT>
</head>
<style type="text/css">
body {
	margin: 0px;
	margin-left: 0px;
	padding: 0px;
}
</style>
<body style="overflow: hidden">
<script type="text/javascript">	
		$(function(){
			if("${not empty isShowAlone}" == "true"){
				showalone();
			}else{
				showMenu(2, 25);
			}
		});</script>
	<form action="${ctx }/resource/ersEmrgCaseQuery.ac" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37"></td>
				<td width="154" class="c4">案例库信息查询</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37"></td>
				<td width="79%" background="../resources/images/bg_k2.gif">
					<div align="right">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="66%" align="left">案例名称:<input type="text" name="dto.casename" id='casename'
									class="text" size="20"  value='${dto.casename }'>
								</td>
								<td> <input
									name="new4" type="button" id='queryBtn' class="btn" value="查询"> <input
									name="new4" type="button" class="btn isShowAlone" value="新增"
									onClick="javascrip:window.location.href='ersEmrgCaseInit.ac'">
									<input type="button" value="批量删除" id="deleteBtn" class="btn isShowAlone"></td>

							</tr>
						</table>
					</div>
				</td>
				<td width="39" background="../resources/images/bg_k2.gif">
					<div align="right">
						<img src="../resources/images/k2.gif" width="17" height="37">
					</div>
				</td>
			</tr>
		</table>
		<table class="list" onload="initView();iframeSelfAdapt();"
			bizobj="gov.emergency.emercore.emergencyvo.report.BsBsxxZbVO"
			name="BsBsxxZbVOList" id="BsBsxxZbVOList" width="100%">
			<thead>
				<tr>
					<td><input type="checkbox" id="checkAll" /></td>
					<td>序号</td>
					<td>案例名称</td>
					<td>案列来源</td>
					<td>事件级别</td>
					<td>事发地点</td>
					<td style="display: none;">事发时间</td>
					<td style="display: none;">责任单位</td>
					<td style="display: none;">结束日期</td>
					<td class="isShowAlone">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">

					<tr>
						<td><input type="checkbox" name='checkedId' value='${obj.caseid}' id='checkedId'/></td>
						<td>${status.count }</td>
						<td>${obj.casename}</td>
						<td>${obj.source}</td>
						<td>${eventlevelcodeMap[obj.eventlevelcode2]}
						</td>
						<td>${obj.address}</td>
						<td class="isShowAlone"><a
							href='${ctx }/resource/ersEmrgCaseInit.ac?caseid=${obj.caseid}'
							style="cursor: pointer; text-decoration: underline;">修改</a>&nbsp;&nbsp;||&nbsp;&nbsp;
							<a onclick="javascript:deleteOnClick(${obj.caseid});"
							style="cursor: pointer; text-decoration: underline;">删除</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="8" class="line2" style="text-align: right;"><%@include
							file="../changePage.jsp"%></td>
				</tr>
			</tbody>
		</table>

	</form>
</body>
</html>
