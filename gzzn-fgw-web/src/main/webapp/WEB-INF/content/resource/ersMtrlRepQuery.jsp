<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>应急物资单位信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="应急物资单位信息">
<script src="${ctx}/resources/js/ers/public.js"　type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript">

	function infoToDelete(objTD) {
		$.ajax({
			type : "POST",
			url : "${ctx }/resource/ersMtrlRepDelete.json",
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
			window.location.href = "${ctx }/resource/ersMtrlRepQuery.ac";
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
				showMenu(2, 31);
		});
	</script>
	<form action="${ctx }/resource/ersMtrlRepQuery.ac" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37"></td>
				<td width="154" class="c4">应急物资单位信息</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37"></td>
				<td width="78%" background="../resources/images/bg_k2.gif">
					<div align="right">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="70%" align="left"> 应急物资库名称:<input
									type="text" name="dto.repertoryName" class="text" size="14"
									value='${dto.repertoryName}'> &nbsp;应急保障物资名称:<input
									type="text" name="dto.materialName" class="text" size="14"
									value='${dto.materialName }'>&nbsp;行政机构名称:<input type="text"
									name="dto.orgname" class="text" size="14"
									value='${dto.orgname }'> &nbsp;</td>
								<td>&nbsp;<input name="new4" type="button"   id='queryBtn'  class="btn"
									value="查询"> &nbsp;<input name="new4" type="button"
									class="btn" value="新增"
									onClick="javascrip:window.location.href='ersMtrlRepInit.ac'">
									&nbsp;<input type="button" value="批量删除" id="deleteBtn"
									class="btn"></td>

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
					<td><input type="checkbox" id="checkAll" />
					</td>
					<td>序号</td>
					<td>应急物资库名称</td>
					<td>应急保障物资名称</td>
					<td>行政机构名称</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" name='checkedId'
							value='${obj.mtrlRepId}' id='checkedId' /></td>
						<td>${status.count }</td>
						<td>${obj.repertoryName}</td>
						<td>${obj.materialName}</td>
						<td>${obj.orgname }</td>
						<td><a
							href='${ctx }/resource/ersMtrlRepInit.ac?mtrlRepId=${obj.mtrlRepId}'
							style="cursor: pointer; text-decoration: underline;">修改</a>&nbsp;&nbsp;||&nbsp;&nbsp;
							<a onclick="javascript:deleteOnClick(${obj.mtrlRepId});"
							style='cursor: pointer;'>删除</a></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="10" class="line2" style="text-align: right;"><%@include
							file="../changePage.jsp"%></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
