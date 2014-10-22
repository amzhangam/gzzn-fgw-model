<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>通讯录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<script type="text/javascript">
<!--
	$(document).ready(function() {
		showMenu(1,11);
		$("input[type='radio']").click(function() {
			//alert($(this).val());
			if ($(this).val() == 1) {
				$("#subType").css("display", "");
			} else {
				$("#subType").css("display", "none");
			}
		});

		$("#checkAll").click(function() {
			var tag = $(this).attr("checked");
			if (tag) {
				$(".subcheck").attr("checked", "true");
			} else {
				$(".subcheck").removeAttr("checked");
			}
		});
		$("#queryBtn").click(function(){
			$("form")[0].submit();
		});

	});
//-->
</script>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="13"><img src="${ctx}/resources/images/k0.gif" width="13" height="37"></td>
			<td width="154" class="c4">电话系统人员查询</td>
			<td width="57" background="${ctx}/resources/images/bg_k2.gif">
			<img src="${ctx}/resources/images/k1.gif" width="39" height="37">
			</td>
			<td width="998" background="${ctx}/resources/images/bg_k2.gif">
				<div>
					<form action="${ctx}/login/welcome.ac" method="post">
					<input type="hidden" id="currentPage" name="page.current" value="${page.current}" />
					<input type="hidden" id="pageSize" name="page.size" value="${page.size}" />
					 
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="76%">
							<!-- 
								<input type="radio" name="rType" checked="checked" value="0" />全部 
								<input type="radio" name="rType" value="1" />分组
								<input type="radio" name="rType" value="2" />常用 
								<input type="radio" name="rType" value="3" />最近联系人
							 -->
								
								联系人:<input type="text" name="userName" value="${userName}" class="text" size="10">
								<!-- 
								<span id="subType" style="display: none;"> 分组名称：
									 <select>
											<option>分组1</option>
											<option>分组2</option>
											<option>分组3</option>
											<option>分组4</option>
									</select>
								</span>
								 -->
							 <input name="new4" type="button" class="btn" id="queryBtn" value="查询"">

							</td>

						</tr>
					</table>
					 
					</form>
				</div>
			</td>
			<td width="39" background="${ctx}/resources/images/bg_k2.gif">
				<div align="right">
					<img src="${ctx}/resources/images/k2.gif" width="17" height="37">
				</div>
			</td>
		</tr>
	</table>

	<table width="100%" border="0" cellpadding="1" cellspacing="0"
		bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="5%"><input type="checkbox" id="checkAll"></td>
				<td width="10%">序号</td>
				<td width="15%">姓名</td>
				<td width="15%">职务</td>
				<td width="25%">直属机构</td>
				<td width="15%">办公电话</td>
				<td width="15%">手机</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="obj" varStatus="status">
			<tr>
				<td>
					<input type="checkbox" value="${obj.userId}" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}&nbsp;</td>
				<td>${obj.userName}&nbsp;</td>
				<td>${obj.sysDuty.dutyName}&nbsp;</td>
				<td>${obj.sysDept.deptName}&nbsp;</td>
				<td>${obj.tel}&nbsp;</td>
				<td>${obj.telmobile}&nbsp;</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7" class="line2" style="text-align: right;">
				<%@include file="../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
	
	<script type="text/javascript">
		
	</script>
</body>
</html>
