<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>重大危险源</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="重点防护目标查询结果">
<script src="${ctx}/resources/js/ers/public.js"　type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getTree.js" type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		$("#classifyId").attr('value', '${dto.classifyId}');
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍
		initMyTree("classify","getClassifyTreeJson.json?catalogue=2");
	});
</script>
<SCRIPT LANGUAGE="JavaScript">

	function infoToDelete(objTD) {
		$.ajax({
			type : "POST",
			url : "${ctx }/resource/ersDangerDelete.json",
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
			window.location.href = "${ctx }/resource/ersDangerQuery.ac";
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
	<form action="${ctx }/resource/ersDangerQuery.ac" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37"></td>
				<td width="154" class="c4">重大危险源</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37"></td>
				<td width="80%" background="../resources/images/bg_k2.gif">
					<div align="right">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="66%" align="left"><strong>类别名称:</strong> <input type="hidden"
									class="text" name="dto.classifyId"
									value="${dto.classifyId }" id='classifyId' /> <input
									type="text" class="text" name="dto.classifyName"
									value="${dto.classifyName}" readonly="readonly"
									id="classifyName" /><strong>危险源名称:</strong> <input type="text" class="text"
					name="dto.dangerName" value="${dto.dangerName }" /></td>
								
								<td>&nbsp;<input name="new4" type="button" class="btn" id='queryBtn'
									value="查询"> &nbsp;<input name="new4" type="button"
									class="btn" value="新增"
									onClick="javascrip:window.location.href='ersDangerInit.ac'">
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
					<td>危险源名称</td>
					<td>类别名称</td>
					<td>行政区域名称</td>
					<td>高程基准名称</td>
					<td>数据来源名称</td>
					<td>级别代码</td>
					<td>密级代码</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" name='checkedId'
							value='${obj.dnagerId}' id='checkedId' /></td>
						<td>${status.count }</td>
						<td>${obj.dangerName}</td>
						<td>${obj.classifyName }</td>
						<td>${obj.districtName}</td>
						<td>${obj.elevadatumName}</td>
						<td>${obj.sourcedeptname}</td>
						<td>${dangerLevelMap[obj.dangerLevel]}</td>
						<td>${secretCodeMap[obj.secretCode]}</td>
						<td><a
							href='${ctx }/resource/ersDangerInit.ac?dnagerId=${obj.dnagerId}'
							style="cursor: pointer; text-decoration: underline;">修改</a>&nbsp;&nbsp;||&nbsp;&nbsp;
							<a onclick="javascript:deleteOnClick(${obj.dnagerId});"
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
