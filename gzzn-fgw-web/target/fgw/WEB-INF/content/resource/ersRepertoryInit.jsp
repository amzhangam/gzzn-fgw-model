<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急物资储备库</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="应急物资储备库-应急物资储备库查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getLocateCoordinates.js"
	　type="text/javascript"></script>

<script type="text/javascript">
	function setSelectValue() {
		$("#coordSys").attr('value', '${editObj.coordSys}');
		$("#grade").attr('value', '${editObj.grade}');
	}
	$(function() {
		$("#resetBtn").click(function() {
			$("form")[0].reset();
			setSelectValue();
		});
		setSelectValue();
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
				"dto.repertorytype" : {
					byteMaxLength : 10
				},
				"dto.grade" : {
					byteMaxLength : 10
				},
				"dto.supervisaldpet" : {
					byteMaxLength : 32
				},
				"dto.address" : {
					byteMaxLength : 200
				},
				"dto.grade" : {
					byteMaxLength : 10
				},
				"dto.longitude" : {
					isDecimal : [ 0, 100000000, 15 ]
				},
				"dto.latitude" : {
					isDecimal : [ 0, 100000000, 15 ]
				},
				"dto.principal" : {
					byteMaxLength : 50
				},
				"dto.fax" : {
					byteMaxLength : 20
				},
				"dto.material" : {
					byteMaxLength : 100
				},
				"dto.capacity" : {
					byteMaxLength : 20,
					digits : true
				},
				"dto.measure" : {
					byteMaxLength : 20
				},
				"dto.tel" : {
					byteMaxLength : 50
				},
				"dto.dutytel" : {
					byteMaxLength : 50
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
				"dto.repertorytype" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.grade" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.supervisaldpet" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.address" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.principal" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.longitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				},
				"dto.latitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				},
				"dto.fax" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.material" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.capacity" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的整数",
					digits : "请输入整数"
				},
				"dto.measure" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.tel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutytel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#notes").charCount({
			allowed : 200
		});
		$("#material").charCount({
			allowed : 100
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersRepertorySave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersRepertoryQuery.ac";
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
			showMenu(2, 31);
		});
	</script>
	<form action="${ctx }/resource/ersRepertorySave.ac" method="post"
		id='signupForm'>
		<table style="width: 100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">物资储备库信息录入</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div>
				</td>
				<td class="tbHeadBg" style="width: 78%;">
					<div>
						<span style="float: right;"> <input type="button"
							class="btn" value="保存" id="save" /> <input type="button"
							class="btn" id="resetBtn" value="重置"> <input
							type="button" class="btn" value="返回" id="return" name="return"
							onclick="history.go(-1)" /> </span>
					</div></td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div></td>
			</tr>
		</table>
		<table align="center" class="editTab" border="0"
			cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<th width="25%">名称<span style="color: red">&nbsp;*</span>
				</th>
				<td width="25%"><input name="dto.name" id="name"
					title="应急物资储备库名称" type="text" class="text" value='${editObj.name }'>
				</td>
				<th width="25%">唯一标志码</th>
				<td width="25%"><input autocomplete="off" name="dto.nucode"
					id="nucode" class="text" title="唯一标志码" type="text"
					value='${editObj.nucode }' /></td>
			</tr>
			<tr>
				<th>类型</th>
				<td><input name="dto.repertorytype" id="repertorytype"
					value='${editObj.repertorytype}' class="text" title="类型"
					type="text" />
				<th>级别</th>
				<td><select name='dto.grade' id='grade'>
						<option value=''>请选择</option>
						<option value='1'>国家级</option>
						<option value='2'>省级</option>
						<option value='3'>市级</option>
						<option value='4'>县级</option>
				</select></td>
			</tr>
			<tr>
				<th>主管部门</th>
				<td><input name="dto.supervisaldpet" id="supervisaldpet"
					value='${editObj.supervisaldpet}' class="text" title="类型"
					type="text" />
				<th>地址</th>
				<td><input name="dto.address" id="address"
					value='${editObj.address}' class="text" title="地址" type="text" />
				</td>
			</tr>
			<tr>
				<th>坐标体系</th>
				<td><select id='coordSys' name='dto.coordSys'>
						<option value="">请选择</option>
						<c:forEach items="${coordSysMap }" var='coordSys'>
							<option value="${coordSys.key }">${coordSys.value }</option>
						</c:forEach>
				</select></td>
				<th>经纬度</th>
				<td><input name="dto.longitude" id="longitude" class="text"
					size='12' title="经度" type="text" value='${editObj.longitude}'>&nbsp;
					<input name="dto.latitude" id="latitude" class="text" size='12'
					title="纬度" type="text" value='${editObj.latitude}'> <a
					onclick="getCurrCaseLocateXY('${ctx}/gis/getLocateCoordinates2.json');"
					target="_blank" id="posBtn"
					style="text-align: center; padding-top: 7px;"> <img alt="选择坐标"
						src="${ctx}/resources/images/gis/place.gif" style="border: none;">
				</a></td>
			</tr>
			<tr>
				<th>负责人</th>
				<td><input name="dto.principal" id="principal" title="负责人"
					type="text" value="${editObj.principal }" /></td>
				<th>联系电话</th>
				<td><input name="dto.tel" id="tel" class="text" title="联系电话"
					type="text" value='${editObj.tel}'></td>

			</tr>
			<tr>
				<th>传真</th>
				<td><input name="dto.fax" id="fax" title="传真" type="text"
					value="${editObj.fax }" /></td>

			</tr>
			<tr>
				<th>储备物资</th>
				<td colspan="3"><textarea name="dto.material" id="material"
						title="储备物资" rows="2" cols="100">${editObj.material}</textarea></td>

			</tr>
			<tr>
				<th>库容</th>
				<td><input name="dto.capacity" id="capacity" title="库容"
					type="text" value="${editObj.capacity }" /></td>
				<th>库容单位</th>
				<td><input name="dto.measure" id="measure" class="text"
					title="库容单位" type="text" value='${editObj.measure}'></td>

			</tr>
			<tr>
				<th>应急值班电话</th>
				<td><input name="dto.dutytel" id="dutytel" class="text"
					title="应急值班电话" type="text" value='${editObj.dutytel}'></td>

			</tr>

			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="dto.notes" id="notes"
						title="备注" rows="2" cols="100">${editObj.notes}</textarea></td>
			</tr>
		</table>
		<div class="ctrls_bar">
			<input type="hidden" value='${editObj.repertoryid }'
				name='repertoryid'>
		</div>
	</form>
</body>
</html>