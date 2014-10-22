<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>应急资源类别查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/ers/public.js"　type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		
		$("#catalogue").attr('value', '${dto.catalogue}');
	});
</script>
<SCRIPT LANGUAGE="JavaScript">

	function infoToDelete(objTD) {
	
		$.ajax({
			type : "POST",
			url : "${ctx }/resource/codeEventResClassifyDelete.json",
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
	<form action="${ctx }/resource/codeEventResClassifyQuery.ac" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37">
				</td>
				<td width="154" class="c4">应急资源类别查询</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37">
				</td>
				<td width="80%" background="../resources/images/bg_k2.gif">
					<div align="right">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="66%" align="left">
								<strong>名称:</strong> <input type="text"
									name="dto.classifyName" value="${dto.classifyName }" size="10" class="text">&nbsp;&nbsp;
								<strong>分类目录:</strong> <select
									id='catalogue' name='dto.catalogue' id='catalogue'>
										<option value="">请选择</option>
										<c:forEach items="${catalogueMap }" var='items'>
											<option value="${items.key }">
												${items.value}</option>
										</c:forEach>
								</select>&nbsp;&nbsp;
								<strong>分类编码:</strong> <input type="text" size="10"
									name="dto.classifyCode" value="${dto.classifyCode }"  class="text">
								</td>
								<td>&nbsp;<input name="new4" type="button" id='queryBtn' class="btn"
									value="查询"> &nbsp;<input name="new4" type="button"
									class="btn" value="新增"
									onClick="javascrip:window.location.href='codeEventResClassifyInit.ac'">
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
		<table width="108%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="list">
			<thead>

				<tr>
					<td><input type="checkbox" id="checkAll" /></td>
					<td>序号</td>
					<td>分类目录</td>
					<td>名称</td>
					<td>分类编码</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" name='checkedId'
							value='${obj.classifyId}' id='checkedId' />
						</td>
						<td>${status.count }</td>
						<td>${catalogueMap[obj.catalogue] }</td>
						<td>${obj.classifyName}</td>
						<td>${obj.classifyCode }</td>
						<td>${obj.remark}</td>
						<td><a
							href='${ctx }/resource/codeEventResClassifyInit.ac?classifyId=${obj.classifyId}'
							style="cursor: pointer; text-decoration: underline;">修改</a>&nbsp;&nbsp;||&nbsp;&nbsp;
							<a onclick="javascript:deleteOnClick(${obj.classifyId});"
							style='cursor: pointer;'>删除</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="7" class="line2" style="text-align: right;"><%@include
							file="../changePage.jsp"%> </td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
