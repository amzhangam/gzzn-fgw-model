<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>物资保障信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="避难场所-避难场所查询结果">
<link href="${ctx }/resources/css/style.css" type="text/css" rel="stylesheet" />
<script src="../resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		$("#checkAll").click(function() {
			if($(this).attr("checked")){
				$("input[name='checkedId']").attr("checked",true);
			}else{
				$("input[name='checkedId']").attr("checked",false);
			}
		});
	});
	function getName(){
		var names="";
		var name=document.getElementsByName("checkedId");
			 for (var i=0; i<name.length; i++){
		   		if(name[i].checked){
		   			names+=name[i].value+"@";
			}
		 }
		 if(names!=null&&names.length>0){
		 	names=names.substring(0, names.length-1);
		 }
		 window.returnValue=names;
		 window.close();
	}
</script>
</head>
<style type="text/css">
body {
	margin: 0px;
	margin-left: 0px;
	padding: 0px;
}
</style>
<body style="overflow: hidden">
	<form action="${ctx }/resource/epsEmrgMtrlQuery.json" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="word-break: keep-all" class="list">
			<tr>
				<td width="13"><img src="../resources/images/k0.gif" width="13"
					height="37">
				</td>
				<td width="154" class="c4">物资保障信息</td>
				<td width="57" background="../resources/images/bg_k2.gif"><img
					src="../resources/images/k1.gif" width="39" height="37">
				</td>
				<td width="998" background="../resources/images/bg_k2.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
								<strong>名称:</strong> 
									<input type='text' id="name" value='${dto.name }' name='dto.name'>
									&nbsp;<input name="new4" type="submit" class="btn" value="查询">
									&nbsp;<input type="button" class="btn" value="确定" onclick="getName()">
								</td>
							</tr>
						</table>
				</td>
				<td width="39" background="../resources/images/bg_k2.gif">
					<div align="right">
						<img src="../resources/images/k2.gif" width="17" height="37">
					</div></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
			<thead>
				<tr>
					<td><input type="checkbox" id="checkAll" /></td>
					<td>序号</td>
					<td>名称</td>
					<td>类别名称</td>
					<td>主管部门</td>
					<td>负责人</td>
					<td>负责人电话</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" name='checkedId'
							value='${obj.materialId};${obj.name}'  id='checkedId' />
						</td>
						<td>${status.count }</td>
						<td>${obj.name}</td>
						<td>${obj.classifyName }</td>
						<td>${obj.supervisaldpet}</td>
						<td>${obj.principal}</td>
						<td>${obj.principaltel}</td>
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
