<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>重大危险源信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="避难场所-重大危险源信息">
<script src="${ctx}/resources/js/ers/public.js"　type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getTree.js" type="text/javascript"></script>

<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
	});
</script>
<SCRIPT LANGUAGE="JavaScript">
	function infoToDelete(objTD) {
		$.ajax({
			type : "POST",
			url : "${ctx }/resource/ersDangeDfnTgDelete.json",
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
			window.location.href = "${ctx }/resource/ersDangeDfnTgQuery.ac";
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
	<form action="${ctx }/resource/ersDangeDfnTgQuery.ac" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37"></td>
				<td width="154" class="c4">重大危险源信息查询</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37"></td>
				<td width="998" background="../resources/images/bg_k2.gif">
					<div align="right">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="76%"><strong>名称:</strong> <input type="text"
									name="dto.objectname" value='${dto.objectname}' class="text">&nbsp;&nbsp;<strong>类别名称:</strong><input
									type="hidden" class="text" name="dto.classifyId"
									value="${dto.classifyId }" id='classifyId' /> <input
									type="text" class="text" name="dto.classifyName"
									id="classifyName" value="${dto.classifyName}"
									readonly="readonly" /></td>
								<td><input name="new4" type="button" class="btn" value="查询"
									id='queryBtn'> <input name="new4" type="button"
									class="btn" value="新增"
									onClick="javascript:window.location.href='${ctx }/resource/ersDangeDfnTgInit.ac'">
									<input type="button" value="批量删除" id="deleteBtn" class="btn">
								</td>
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
		<table width="108%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="list">
			<thead>

				<tr>
					<td><input type="checkbox" id="checkAll" /></td>
					<td>序号</td>
					<td>名称</td>
					<td>行政区划</td>
					<td>最近更新时间</td>
					<td>资源状态</td>
					<td>是否整改</td>
					<td>操作</td>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" name='checkedId'
							value='${obj.objectid}' id='checkedId' />
						</td>
						<td>${status.count }</td>
						<td>${obj.objectname }</td>
						<td>${obj.districtName }</td>
						<td>${obj.builddate }</td>
						<td>${obj.builddate }</td>
						<td>${obj.builddate }</td>
						<td><a
							href='${ctx }/resource/ersDangeDfnTgInit.ac?objectid=${obj.objectid}'
							style="cursor: pointer; text-decoration: underline;">修改</a>&nbsp;&nbsp;||&nbsp;&nbsp;
							<a onclick="javascript:deleteOnClick(${obj.objectid});"
							style="cursor: pointer; text-decoration: underline;">删除</a></td>
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
