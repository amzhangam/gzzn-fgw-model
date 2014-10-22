<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>医院信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/ers/getTree.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getLocateCoordinates.js"　type="text/javascript"></script>

<script language="javascript">
	$(document).ready(function() {
	
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍
		initMyTree("classify", "getClassifyTreeJson.json?catalogue=4");
	
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#classifyId").attr('value', '${editObj.classifyId}');
		$("#grade").attr('value', '${editObj.grade}');
		$("#hospDeptId").attr('value', '${editObj.hospDeptId}');
		//设置坐标体系
		$("#coordSys").attr('value', '${editObj.coordSys}');
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.name" : {
					required : true,
					byteMaxLength : 50
				},
				"dto.alias" : {
					byteMaxLength : 50
				},
				"dto.nucode" : {
					byteMaxLength : 32
				},
				"dto.grade" : {
					byteMaxLength : 10
				},
				"dto.charal" : {
					byteMaxLength : 50
				},
				"dto.notes" : {
					byteMaxLength : 200
				},
				"dto.bednum" : {
					byteMaxLength : 4,
					digits : true
				},
				"dto.doctornum" : {
					byteMaxLength : 4,
					digits : true
				},
				"dto.nursenum" : {
					byteMaxLength : 4,
					digits : true
				},
				"dto.ambulancenum" : {
					byteMaxLength : 4,
					digits : true
				},
				"dto.blood" : {
					byteMaxLength : 8,
					digits : true
				},
				"dto.longitude" : {
					isDecimal : [ 0, 10000000, 13 ]
				},
				"dto.latitude" : {
					isDecimal : [ 0, 10000000, 13 ]
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
				"dto.alias" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.grade" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.charal" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.bednum" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的数字",
					digits : "请输入整数"
				},
				"dto.doctornum" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的数字",
					digits : "请输入整数"
				},
				"dto.nursenum" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的数字",
					digits : "请输入整数"
				},
				"dto.ambulancenum" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的数字",
					digits : "请输入整数"
				},
				"dto.blood" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的数字",
					digits : "请输入整数"
				},
				"dto.notes" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的数字"
				},
				"dto.dutyDeptStep" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.handlingCapacity" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.longitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				},
				"dto.latitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				}
			}
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
													url : '${ctx }/resource/ersHospSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersHospQuery.ac";
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
			showMenu(2, 30);
		});
	</script>
	<form action="${ctx }/resource/ersHospSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					医院信息录入
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
					<td><input type="text" class="text" name="dto.name"
						value="${editObj.name }" /></td>
					<th>别名</th>
					<td><input type="text" class="text" name="dto.alias"
						value="${editObj.alias }" /></td>
				</tr>
				<tr>
					<th>医疗卫生单位</th>
					<td><select id='hospDeptId' name='dto.hospDeptId'>
							<option value="">请选择</option>
							<c:forEach items="${hospDeptList }" var='hospDept'>
								<option value="${hospDept.hospDeptId }">${hospDept.name
									}</option>
							</c:forEach>
					</select></td>
					<th>类别名称</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" readonly="readonly"
						id="classifyName" />
					</td>
				</tr>

				<tr>
					<th>唯一标识码</th>
					<td><input type="text" class="text" name="dto.nucode"
						id='nucode' value='${editObj.nucode }' /></td>
					<th>级别</th>
					<td><select id='grade' name='dto.grade'>
							<option value="">请选择</option>
							<c:forEach items="${gradeMap }" var='map'>
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>坐标体系</th>
					<td><select id='coordSys' name='dto.coordSys'>
							<option value="">请选择</option>
							<c:forEach items="${coordSysMap }" var='coordSys'>
								<option value="${coordSys.key }">${coordSys.value }</option>
							</c:forEach>
					</select>
					</td>
					<th>经纬度</th>
					<td><input name="dto.longitude" id="longitude" class="text"
						title="经度" type="text" value='${editObj.longitude}'>&nbsp;
						<input name="dto.latitude" id="latitude" class="text" title="纬度"
						type="text" value='${editObj.latitude}'> <a
						onclick="getCurrCaseLocateXY('${ctx}/gis/getLocateCoordinates2.json');" target="_blank"
						id="posBtn" style="text-align: center; padding-top: 7px;"> <img
							alt="选择坐标" src="${ctx}/resources/images/gis/place.gif"
							style="border: none;"> </a></td>
				</tr>
				<tr>
					<th>特色</th>
					<td><input type="text" class="text" name="dto.charal"
						id='charal' value="${editObj.charal}" /></td>
					<th>病床数</th>
					<td><input type="text" class="text" name="dto.bednum"
						id='bednum' value='${editObj.bednum }' /></td>

				</tr>
				<tr>
					<th>医生数</th>
					<td><input type="text" class="text" name="dto.doctornum"
						id='doctornum' value="${editObj.doctornum}" /></td>
					<th>护士数</th>
					<td><input type="text" class="text" name="dto.nursenum"
						id='nursenum' value='${editObj.nursenum }' /></td>

				</tr>
				<tr>
					<th>急救车辆</th>
					<td><input type="text" class="text" name="dto.ambulancenum"
						id='ambulancenum' value="${editObj.ambulancenum}" /></td>
					<th>库存血浆(单位亳升)</th>
					<td><input type="text" class="text" name="dto.blood"
						id='blood' value='${editObj.blood }' /></td>
				</tr>
				<tr>
					<th>更新时间</th>
					<td><input name="dto.updatetime" id='updatetime'
						value="${editObj.updatetime}" class="Wdate" type="text"
						onclick="WdatePicker({errDealMode:2 })" /></td>

				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="2"
							id='notes'>${editObj.notes}</textarea></td>
				</tr>
		</table>
		<div class="ctrls_bar">
		<input type="hidden" name='hospId'
				value='${editObj.hospId }' />
		</div>
	</form>
</body>
</html>