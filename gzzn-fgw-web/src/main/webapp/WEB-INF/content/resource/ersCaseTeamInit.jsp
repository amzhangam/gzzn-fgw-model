<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>案例救援力量信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery-ui/js/jquery-ui-combobox-pro.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
 
<script language="javascript">
	$(document).ready(function() {
		$("#caseid").combobox();
		$("#notes").charCount({
			allowed : 200
		});
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#caseid").attr("value","${editObj.caseid}");
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.caseid" : {
					required : true
				},
				"dto.teamname" : {
					required : true,
					byteMaxLength : 50
				},
				"dto.notes" : {
					byteMaxLength : 200
				},
				"dto.teamtypecode" : {
					byteMaxLength: 1
				}
			},
			messages : {
				"dto.caseid" : {
					required : "必选字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.teamname" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.teamtypecode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#save")
				.click(
						function() {
							if($("#caseid").val()==""){
								$("#caseidSpan").toggle();	
								return;
							}
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersCaseTeamSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersCaseTeamQuery.ac";
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
	<form action="${ctx }/resource/ersCaseTeamSave.ac" method="post"
		id='signupForm'>
		
		<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				案例救援力量信息
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
					<th>案例名称<span style="color: red">&nbsp;*</span></th>
					<td><select name="dto.caseid" id='caseid'>
							<option value="">请选择</option>
							<c:forEach var="emrgCase" items="${emrgCaseList }">
								<option value="${emrgCase.caseid }" ${emrgCase.caseid==editObj.caseid?'selected':'' }>${emrgCase.casename
									}</option>
							</c:forEach>
					</select> &nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;display: none;" id='caseidSpan'>&nbsp;&nbsp;&nbsp;&nbsp;必选字段</span>
					</td>
					<th>救援力量类型编码</th>
					<td><input type="text" class="text" name="dto.teamtypecode"
						id="teamtypecode" value="${editObj.teamtypecode }" />
					</td>
				</tr>
				<tr>

					<th>队伍名称</th>
					<td><input type="text" class="text" name="dto.teamname"
						id="teamname" value="${editObj.teamname }" />
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="3"
							id='notes' >${editObj.notes}</textarea>
					</td>
				</tr>

			</tbody>
		</table>


		<div class="ctrls_bar">
			<input type="hidden" name='teamid'
				value='${editObj.teamid }' />
		</div>
	</form>
</body>
</html>