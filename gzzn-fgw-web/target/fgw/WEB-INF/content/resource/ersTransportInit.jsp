<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急运输资源</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="应急运输资源">
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
		//站场类型
		$("#stationtype").attr("value", "${editObj.stationtype}");
		//运输类型
		$("#transtype").attr("value", "${editObj.transtype}");
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.vehiclename" : {
					required : true,
					byteMaxLength : 50
				},
				"dto.nucode" : {
					byteMaxLength : 32
				},
				"dto.supervisadept" : {
					byteMaxLength : 60
				},
				"dto.address" : {
					byteMaxLength : 200
				},
				"dto.usuage" : {
					byteMaxLength : 2
				},
				"dto.description" : {
					byteMaxLength : 100
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
				"dto.vehiclename" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.nucode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.supervisadept" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.address" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.usuage" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.description" : {
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
		$("#address").charCount({
			allowed : 200
		});
		$("#description").charCount({
			allowed : 100
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
													url : '${ctx }/resource/ersTransportSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersTransportQuery.ac";
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
	<form action="${ctx }/resource/ersTransportSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					应急运输资源录入
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
					<th>名称<span style="color: red">&nbsp;*</span></th>
					<td><input type="text" class="text" name="dto.vehiclename"
						value="${editObj.vehiclename }" /></td>
					<th width="25%">唯一标志码</th>
					<td width="25%"><input autocomplete="off" name="dto.nucode"
						id="nucode" class="text" title="唯一标志码" type="text"
						value='${editObj.nucode }' />
				</tr>
				<tr>
					<th>行政区划</th>
					<td><input id="districtName" name='dto.districtName'
						type="text" readonly="readonly" value="${editObj.districtName }" /><input
						id="districtId" name='dto.districtId' type="hidden"
						value="${editObj.districtId }" />
					</td>
					<th>类别名称</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" readonly="readonly"
						id="classifyName" /></td>
				</tr>
				<tr>
					<th>主管部门</th>
					<td><input type="text" class="text" name="dto.supervisadept"
						id='supervisadept' value="${editObj.supervisadept}" /></td>
				</tr>
				<tr>
					<th>地址</th>
					<td colspan="3"><textarea name="dto.address" cols="100"
							rows="3" id='address'>${editObj.address}</textarea></td>
				</tr>
				<tr>
					<th>站场类型</th>
					<td><select id='stationtype' name='dto.stationtype'>
							<option value="">请选择</option>
							<c:forEach items="${stationtypeMap }" var='map'>
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
					</select></td>
					<th>运输类型</th>
					<td><select id='transtype' name='dto.transtype'>
							<option value="">请选择</option>
							<c:forEach items="${transtypeMap }" var='map'>
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>用途</th>
					<td><input type="text" class="text" name="dto.usuage"
						id='usuage' value="${editObj.usuage}" /></td>
				</tr>
				<tr>
					<th>情况描述</th>
					<td colspan="3"><textarea name="dto.description" cols="100"
							rows="2" id='description'>${editObj.description}</textarea></td>
				</tr>
				<tr>
					<th>负责人</th>
					<td><input type="text" class="text" name="dto.principal"
						id='principal' value="${editObj.principal}" /></td>
					<th>负责人电话</th>
					<td><input type="text" class="text" name="dto.principaltel"
						id='principaltel' value='${editObj.principaltel }' /></td>

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
			<input type="hidden" name='transportid'
				value='${editObj.transportid }' />
		</div>
	</form>
</body>
</html>