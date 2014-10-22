<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>医疗卫生单位</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
	<script src="${ctx}/resources/js/ers/getTree.js"
	type="text/javascript"></script>

<script language="javascript">
	$(document).ready(function() {
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		initMyTree("district","getDistrictTreeJson.json");
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#districtId").attr('value', '${editObj.districtId}');
	}
</script>

<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.name" : {
					required : true,
					byteMaxLength : 60
				},
				"dto.nucode" : {
					byteMaxLength : 32
				},
				"dto.deptcode" : {
					byteMaxLength : 32
				},
				"dto.alias" : {
					byteMaxLength : 50
				},
				"dto.depttype" : {
					byteMaxLength : 10
				},
				"dto.address3" : {
					byteMaxLength : 200
				},
				"dto.princinal" : {
					byteMaxLength : 50
				},
				"dto.princinaltel" : {
					byteMaxLength : 50
				},
				"dto.dutytel" : {
					byteMaxLength : 50
				},
				"dto.fax" : {
					byteMaxLength : 20
				},
				"dto.notes" : {
					byteMaxLength : 200
				}
			},
			messages : {
				"dto.name" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.nucode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.deptcode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.alias" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.depttype" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.address3" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.princinal" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.princinaltel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutytel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.fax" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#address3").charCount({
			allowed : 200
		});
		$("#notes").charCount({
			allowed : 200
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersHospDeptSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac.alert(data.info);
															window.location.href = "${ctx }/resource/ersHospDeptQuery.ac";
														} else {
															parent.mac.alert('保存失败');
														}
													},
													error : function() {
														parent.mac.alert("系统响应失败");
													}
												});
								return false; //此处必须返回false，阻止常规的form提交
							}
						});
	});
</script>
<script src="${ctx}/resources/js/ers/getTree.js"
	type="text/javascript"></script>
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
			showMenu(2, 30);
		});
	</script>

	<form action="${ctx }/resource/ersHospDeptSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				医疗卫生单位信息录入
			</td>
			<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
			<td class="tbHeadBg" style="width: 79%;">
				<div>
					<span style="float: right;">
					 <input type="button" class="btn" value="保存"  id="save" />
					 <input type="reset" class="btn" id="resetBtn" value="重置"> 
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
					<th>名称<span style="color: red">&nbsp;*</span></th>
					<td><input type="text" class="text" name="dto.name"
						value="${editObj.name }" /></td>
					<th>类别名称</th>
					<td><input type='text' name="dto.depttype"
						value="${editObj.depttype }"></td>
				</tr>
				<tr>

					<th>行政区划</th>
					<td><input id="districtName" name='dto.districtName'
						type="text" readonly="readonly" value="${editObj.districtName }" /><input
						id="districtId" name='dto.districtId' type="hidden"
						value="${editObj.districtId }" /></td>
				</tr>
				<tr>
					<th>单位编号</th>
					<td><input type="text" class="text" name="dto.deptcode"
						id='deptcode' value="${editObj.deptcode}" /></td>
					<th>唯一标识码</th>
					<td><input type="text" class="text" name="dto.nucode"
						id='nucode' value='${editObj.nucode }' /></td>

				</tr>
				<tr>
					<th>别名或简称</th>
					<td><input type="text" class="text" name="dto.alias"
						id='alias' value="${editObj.alias}" /></td>


				</tr>
				<tr>
					<th>地址</th>
					<td colspan="3"><textarea name="dto.address3" cols="100"
							rows="2" id='address3'>${editObj.address3}</textarea></td>

				</tr>
				<tr>
					<th>负责人</th>
					<td><input type="text" class="text" name="dto.princinal"
						id='princinal' value="${editObj.princinal}" /></td>
					<th>负责人电话</th>
					<td><input type="text" class="text" name="dto.princinaltel"
						id='princinaltel' value='${editObj.princinaltel }' /></td>

				</tr>
				<tr>
					<th>应急值班电话</th>
					<td><input type="text" class="text" name="dto.dutytel"
						id='dutytel' value="${editObj.dutytel}" /></td>
					<th>传真</th>
					<td><input type="text" class="text" name="dto.fax" id='fax'
						value='${editObj.fax }' /></td>

				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="2"
							id='notes'>${editObj.notes}</textarea></td>
				</tr>
		</table>
		<div class="ctrls_bar">
			<input type="hidden"
				name='hospDeptId' value='${editObj.hospDeptId }' />
		</div>
	</form>
</body>
</html>