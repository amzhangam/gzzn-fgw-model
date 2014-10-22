<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>避难场所</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Path" content="zybz/bncs/zybz_bncs_cscx_query.htm">
<meta name="Description" content="避难场所-避难场所查询结果">
<link href="${ctx }/resources/css/style.css" type="text/css" rel="stylesheet" />
<script src="${ctx }/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
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
	<form action="${ctx }/resource/epsEmrgShltrQuery.json">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		style="word-break: keep-all">
		<tr>
			<td width="13"><img src="../resources/images/k0.gif" width="13"
				height="37"></td>
			<td width="154" class="c4">应急避难场所查询</td>
			<td width="57" background="../resources/images/bg_k2.gif"><img
				src="../resources/images/k1.gif" width="39" height="37"></td>
			<td width="998" background="../resources/images/bg_k2.gif">
				<div align="right">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="76%">
								避难场所名称:<input type="text" name="sheltername" class="text"
								size="20" value='${param.sheltername }'> &nbsp;
								<input name="new4" type="button" class="btn" value="查询">&nbsp;
								<input type="button" class="btn" value="确定" onclick="getName()">
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
	<table width="100%" border="0" cellspacing="0" cellpadding="3"
		align="center" class="list">
		<thead>
			<tr>
				<td><input type="checkbox" id="checkAll" /></td>
				<td>名称</td>
				<td>类型</td>
				<td>地址</td>
				<td>面积</td>
				<td>建设时间</td>
				<td>可容纳人数</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="obj" varStatus="status">
				<tr>
					<td><input type="checkbox" name='checkedId'
							value='${obj.shelterid};${obj.sheltername}' />
					</td>
					<td>${obj.sheltername }-${obj.sheltername }</td>
					<td>${obj.sheltertype }</td>
					<td>${obj.address }</td>
					<td>${obj.area }</td>
					<td>${obj.builddate }</td>
					<td>${obj.capacity }</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="7" class="line2" style="text-align: right;"><%@include
						file="../changePage.jsp"%></td>
			</tr>
		</tbody>
	</table>

</form>
</body>
</html>
