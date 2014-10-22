<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>专家组与专家信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/ers/public.js"　type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript">

	function infoToDelete(objTD) {
		$.ajax({
			type : "POST",
			url : "${ctx }/resource/ersExpertGroupMapDelete.json",
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
				showMenu(2, 23);
		});
</script>
	<form action="${ctx }/resource/ersExpertGroupMapQuery.ac" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37">
				</td>
				<td width="154" class="c4">专家组与专家信息</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37">
				</td>
				<td width="79%" background="../resources/images/bg_k2.gif">
					<div align="right">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="66%"><strong>专家组名称:</strong> <input type="text"
									 name="dto.expertGroupName" id='expertGroupName'
									value='${dto.expertGroupName }' size="12px;"/> &nbsp;&nbsp;<strong>专家名称:</strong>
									<input type="text" name="dto.experName"
									id='experName' value='${dto.experName }' size="12px;"/></td>
								<td>&nbsp;<input name="new4" type="button"   id='queryBtn'  class="btn"
									value="查询"> &nbsp;<input name="new4" type="button"
									class="btn" value="新增"
									onClick="javascrip:window.location.href='ersExpertGroupMapInit.ac'">
									&nbsp;<input type="button" value="批量删除" id="deleteBtn"
									class="btn">
								</td>

							</tr>
						</table>
					</div></td>
				<td width="39" background="../resources/images/bg_k2.gif">
					<div align="right">
						<img src="../resources/images/k2.gif" width="17" height="37">
					</div></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="list">
			<thead>

				<tr>
					<td><input type="checkbox" id="checkAll" /></td>
					<td>序号</td>
					<td>专家名称</td>
					<td>专家组名称</td>
					<td>职务代码</td>
					<td>数据来源单位编码</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" name='checkedId'
							value='${obj.expertGroupMapId}' id='checkedId' />
						</td>
						<td>${status.count }</td>
						<td>${obj.experName}</td>
						<td>${obj.expertGroupName}</td>
						<td>${dutyCodeMap[obj.dutyCode] }</td>
						<td>${obj.dataFromCode}</td>
						<td>${obj.updateTime}</td>
						<td><a
							href='${ctx }/resource/ersExpertGroupMapInit.ac?expertGroupMapId=${obj.expertGroupMapId}'
							style="cursor: pointer; text-decoration: underline;">修改</a>&nbsp;&nbsp;||&nbsp;&nbsp;
							<a onclick="javascript:deleteOnClick(${obj.expertGroupMapId});"
							style='cursor: pointer;'>删除</a>
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
