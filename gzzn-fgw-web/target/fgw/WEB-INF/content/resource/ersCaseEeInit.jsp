<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>案例衍生、次生信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<style>
button.comboboxButton {
	margin-left: -1px;
}
button.comboboxButton,.ui-button-text {
	line-height: 1.35;
}
ul.ui-autocomplete {
	max-height: 250px;
	overflow-x: hidden;
	overflow-y: auto;
	padding: 2px;
}
</style>
<script src="${ctx}/resources/js/jquery-ui/js/jquery-ui-combobox-pro.js"
	type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		$("#caseid").combobox();
		$("#eventdesc").charCount({
			allowed : 500
		});
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
		});
	});
	
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.caseid" : {
					required : true
				},
				"dto.eventtypecode" : {
					required : true,
					byteMaxLength : 10
				},
				"dto.eventdesc" : {
					byteMaxLength : 500
				}
			},
			messages : {
				"dto.caseid" : {
					required : "必选字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.eventtypecode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.eventdesc" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#save")
				.click(
						function() {
							if ($("#caseid").val() == "") {
								$("#caseidSpan").toggle();
								return;
							}
							if ($("#signupForm").valid()) {

								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersCaseEeSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersCaseEeQuery.ac";
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
	<script type="text/javascript">
		$(function() {
			showMenu(2, 25);
		});
	</script>
	<form action="${ctx }/resource/ersCaseEeSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				案例衍生、次生信息
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
					<th>案例名称<span style="color: red">&nbsp;*</span>
					</th>
					<td><div class="ui-widget">
							<select name="dto.caseid" id='caseid'>
								<option value="">请选择</option>
								<c:forEach var="emrgCase" items="${emrgCaseList }">
									<option value="${emrgCase.caseid }"
										${emrgCase.caseid==editObj.caseid?'selected':'' }>${emrgCase.casename
										}</option>
								</c:forEach>
							</select> <span style="color: red; display: none;" id='caseidSpan'>&nbsp;&nbsp;&nbsp;&nbsp;必选字段</span>
						</div></td>
					<th>事件分类编码</th>
					<td><input type="text"  name="dto.eventtypecode"
						id="eventtypecode" value="${editObj.eventtypecode }" /><span>&nbsp;*</span>
					</td>
				</tr>
				<tr>
					<th>事件分类描述</th>
					<td colspan="3"><textarea name="dto.eventdesc" cols="100"
							rows="3" id='eventdesc'>${editObj.eventdesc}</textarea><span>&nbsp;*</span>
					</td>
				</tr>

			</tbody>
		</table>


		<div class="ctrls_bar">
			<input type="hidden" name='eeid'
				value='${editObj.eeid }' />
		</div>
	</form>
</body>
</html>