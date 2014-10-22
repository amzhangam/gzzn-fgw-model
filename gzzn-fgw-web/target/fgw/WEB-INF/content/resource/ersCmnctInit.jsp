<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急通信</title>
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
	$(document).ready(function() {
		selectMenu(2);
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍
		initMyTree("classify", "getClassifyTreeJson.json?catalogue=4");
		initMyTree("district", "getDistrictTreeJson.json");
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#districtId").attr('value', '${editObj.districtId}');
		$("#classifyId").attr('value', '${editObj.classifyId}');
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
				"dto.address4" : {
					byteMaxLength : 200
				},
				"dto.principal" : {
					byteMaxLength : 50
				},
				"dto.principaltel" : {
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
				"dto.address4" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.principal" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.principaltel" : {
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
		$("#address4").charCount({
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
													url : '${ctx }/resource/ersCmnctSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersCmnctQuery.ac";
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
	<form action="${ctx }/resource/ersCmnctSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				通信保障信息录入
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
					<th>应急通信名称<span style="color: red">&nbsp;*</span></th>
					<td><input type="text" class="text" name="dto.name"
						value="${editObj.name }" /></td>
					<th>通信类别</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName" id="classifyName"
						value="${editObj.classifyName}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>行政区划</th>
					<td><input id="districtName" name='dto.districtName'
						type="text" readonly="readonly" value="${editObj.districtName }" /><input
						id="districtId" name='dto.districtId' type="hidden"
						value="${editObj.districtId }" /></td>
					<th>统一标识</th>
					<td><input type="text" class="text" name="dto.nucode"
						id='nucode' value='${editObj.nucode }' /></td>
				</tr>
				<tr>
					<th>覆盖位置</th>
					<td colspan="3"><textarea name="dto.address4" cols="100"
							rows="2" id='address4'>${editObj.address4}</textarea>
					</td>
				</tr>


				<tr>
					<th>负责人</th>
					<td><input type="text" class="text" name="dto.principal"
						id='principal' value="${editObj.principal}" />
					</td>
					<th>负责人电话</th>
					<td><input type="text" class="text" name="dto.principaltel"
						id='principaltel' value='${editObj.principaltel }' />
					</td>

				</tr>
				<tr>
					<th>应急值班电话</th>
					<td><input type="text" class="text" name="dto.dutytel"
						id='dutytel' value="${editObj.dutytel}" /></td>
					<th>传真</th>
					<td><input type="text" class="text" name="dto.fax" id='fax'
						value="${editObj.fax}" /></td>

				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="3"
							id='notes'>${editObj.notes}</textarea></td>
				</tr>
		</table>
		<div class="ctrls_bar">
			 <input type="hidden"
				name='communicationid' value='${editObj.communicationid }' />
		</div>
	</form>
</body>
</html>