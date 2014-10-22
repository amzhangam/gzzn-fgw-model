<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>应急专家组</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="专家组">
<script src="${ctx}/resources/js/ers/public.js"　type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript">
	function infoToDelete(objTD) {
		$.ajax({
			type : "POST",
			url : "${ctx }/resource/ersExpertGroupDelete.json",
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
			window.location.href = "${ctx }/resersExpertGroupExpertQuery.ac";
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
				showMenu(2, 23);
		});
</script>
	<form action="${ctx }/resource/ersExpertGroupQuery.ac" method="post">  
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37">
				</td>
				<td width="154" class="c4">应急专家组信息列表</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37">
				</td>
				<td width="998" background="../resources/images/bg_k2.gif">
					<div align="right">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="66%">专家组名称:<input type="text" name="dto.expertGroupName" 
									class="text" size="10" value='${dto.expertGroupName }'> 
								
								</td>
								<td>
									 <input name="new4"
									type="button"   id='queryBtn'  class="btn" value="查询"><input name="new4"
									type="button" class="btn" value="新增"
									onClick="javascrip:window.location.href='ersExpertGroupInit.ac'">
									&nbsp;<input type="button" value="批量删除" id="deleteBtn"
									class="btn"></td>

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
					<td>&nbsp;专家组名&nbsp;</td>
					<td>唯一标识码</td>
					<td>级别代码</td>
					<td>专家组类型</td>
					<td>操作</td>
				</tr>

			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">

					<tr>
						<td><input type="checkbox" name='checkedId'
							value='${obj.expertGroupId}' id='checkedId' />
						</td>
						<td>${status.count }</td>
						<td>${obj.expertGroupName}</td>
						<td>${obj.nucode }</td>
						<td>${levelCodeMap[obj.levelCode] }</td>
						<td>${expertTypeMap[obj.expertType] }</td>
						<td><a
							href='${ctx }/resource/ersExpertGroupInit.ac?expertGroupId=${obj.expertGroupId}'
							style="cursor: pointer; ">修改</a>&nbsp;&nbsp;||&nbsp;&nbsp;
							<a onclick="javascript:deleteOnClick(${obj.expertGroupId});"
							style="cursor: pointer; text-decoration: underline;">删除</a>
							
							<a
							href='${ctx }/resource/ersExpertTree.ac?expertGroupId=${obj.expertGroupId}'
							style="cursor: pointer; ">成员管理</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="7" class="line2" style="text-align: right;"><%@include file="../changePage.jsp"%></td>
				</tr>
			</tbody>
		</table>

	</form>
</body>
</html>
