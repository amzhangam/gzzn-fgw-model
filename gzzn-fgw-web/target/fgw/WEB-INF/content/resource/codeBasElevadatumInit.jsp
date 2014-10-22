<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>高层基准维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getTree.js" type="text/javascript"></script>

<script language="javascript">
	$(document)
			.ready(
					function() {
						selectMenu(2);
					<%--	//if ('${editObj.elevadatumId}' != '') {
						//	initMyTree("parentClassify",
						//			"getClassifyTreeJson.json?catalogue=${editObj.catalogue}");
					//	}--%>

						$("#remark").charCount({
							allowed : 100
						});
						$("#resetBtn").click(function(){
							window.location.reload(true);
						});
					});
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.elevadatumName" : {
					required : true,
					byteMaxLength : 20
				},
				"dto.elevadatumCode" : {
					byteMaxLength : 1
				},
				"dto.remark" : {
					byteMaxLength : 100
				}

			},
			messages : {
				"dto.elevadatumName" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.elevadatumCode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.remark" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/codeBasElevadatumSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/codeBasElevadatumQuery.ac";
														} else {
															parent.mac
																	.alert('保存失败');
														}
													},
													error : function() {
														parent.mac
																.alert("系统响应失败");
													}
												});
								return false; //此处必须返回false，阻止常规的form提交
							}
						});
	});
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
	<form action="${ctx }/resource/codeBasElevadatumSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					高层基准维护
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" value="保存"  id="save" />
						 <input type="button" class="btn" id="resetBtn" value="重置"> 
					     <input type="button" class="btn" value="返回" id="return" name="return" onclick="history.go(-1)" />
						</span>
					</div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		
		<table align="center" class="editTab" border="0" width="100%">
			<tbody>
				<tr>
					<th>高层基准名称<span style="color: red">&nbsp;*</span></th>
					<td><input type="text" class="text" name="dto.elevadatumName"
						value="${editObj.elevadatumName}" />
					</td>
					<th>高层基准编码</th>
					<td><input type="text" class="text" name="dto.elevadatumCode"
						id='elevadatumCode' value='${editObj.elevadatumCode  }' />
					</td>
				</tr>
			
		</table>
		<div class="ctrls_bar">
			<input type="hidden"
				name='elevadatumId' value='${editObj.elevadatumId }' />
		</div>
	</form>
</body>
</html>