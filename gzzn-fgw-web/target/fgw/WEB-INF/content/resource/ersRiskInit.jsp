<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>风险隐患信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="应急物资储备库-应急物资储备库查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getTree.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getLocateCoordinates.js"　type="text/javascript"></script>

<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		initMyTree("district", "getDistrictTreeJson.json");
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#coordSys").attr('value', '${editObj.coordSys}');
		$("#riskStatus").attr('value', '${editObj.riskStatus}');
		$("#districtId").attr('value', '${editObj.districtId}');
		$("#hospDeptId").attr('value', '${editObj.hospDeptId}');
		$("#hospId").attr('value', '${editObj.hospId}');
		$("#plaBaseId").attr('value', '${editObj.plaBaseId}');
		$("#teamid").attr('value', '${editObj.teamid}');
		$("#riskLevel").attr('value', '${editObj.riskLevel}');
	}
</script>
<script type="text/javascript">

	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.riskSource" : {
					required : true,
					byteMaxLength : 50
				},
				"dto.controlStatus" : {
					byteMaxLength : 30
				},
				"dto.riskNum" : {
					digits : true,
					byteMaxLength:8
				},
				"dto.contactPerson" : {
					byteMaxLength : 50
				},
				"dto.contactTel" : {
					byteMaxLength : 50
				},
				"dto.mgrDept" : {
					byteMaxLength : 100
				},
				"dto.address" : {
					byteMaxLength : 100
				},
				"dto.rectifyContent" : {
					byteMaxLength : 1000
				},
				"dto.rectifyStep" : {
					byteMaxLength : 1000
				},
				"dto.dutyDeptStep" : {
					byteMaxLength : 1000
				},
				"dto.handlingCapacity" : {
					byteMaxLength : 1000
				},
				"dto.longitude" : {
					isDecimal : [ 0, 10000000, 13 ]
				},
				"dto.latitude" : {
					isDecimal : [ 0, 10000000, 13 ]
				}
			},
			messages : {
				"dto.riskSource" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.controlStatus" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.longitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				},
				"dto.latitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				},
				"dto.riskNum" : {
					digits : "请输入整数",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactTel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrDept" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactPerson" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.address" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.rectifyContent" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.rectifyStep" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutyDeptStep" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.handlingCapacity" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#address").charCount({
			allowed : 100
		});
		$("#rectifyContent").charCount({
			allowed : 1000
		});
		$("#rectifyStep").charCount({
			allowed : 1000
		});
		$("#dutyDeptStep").charCount({
			allowed : 1000
		});
		$("#handlingCapacity").charCount({
			allowed : 1000
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersRiskSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersRiskQuery.ac";
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
	<form action="${ctx }/resource/ersRiskSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					风险隐患信息录入
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
		<table align="center" class="editTab" border="0"
			cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<th>行政区划</th>
				<td><input id="districtName" name='dto.districtName'
					type="text" readonly="readonly" value="${editObj.districtName }" /><input
					id="districtId" name='dto.districtId' type="hidden"
					value="${editObj.districtId }" /></td>

				<th width="15%">医疗卫生单位</th>
				<td width="25%"><select id='hospDeptId' name='dto.hospDeptId'>
						<option value="">请选择</option>
						<c:forEach items="${hospDeptList }" var='hospDept'>
							<option value="${hospDept.hospDeptId}">${hospDept.name}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>


				<th>预案信息</th>
				<td><select id='plaBaseId' name='dto.plaBaseId'>
						<option value="">请选择</option>
						<c:forEach items="${plaBaseList }" var='plaBase'>
							<option value="${plaBase.plaBaseId}">${plaBase.plaName}</option>
						</c:forEach>
				</select>
				</td>
				<th>风险隐患源<span style="color: red">&nbsp;*</span></th>
				<td><input name="dto.riskSource" id="riskSource"
					value='${editObj.riskSource}' class="text" title="风险隐患源"
					type="text" />
				</td>
			</tr>
			<tr>
				<th>医院信息</th>
				<td><select id='hospId' name='dto.hospId'>
						<option value="">请选择</option>
						<c:forEach items="${hospList }" var='hosp'>
							<option value="${hosp.hospId }">${hosp.name }</option>
						</c:forEach>
				</select>
				</td>
				<th>应急救援队伍</th>
				<td><select id='teamid' name='dto.teamid'>
						<option value="">请选择</option>
						<c:forEach items="${emrgTeamList }" var='team'>
							<option value="${team.teamid}">${team.name }</option>
						</c:forEach>
				</select>
				</td>

			</tr>
			<tr>
				<th>级别</th>
				<td><select id='riskLevel' name='dto.riskLevel'>
						<option value="">请选择</option>
						<c:forEach items="${riskLevelMap }" var='map'>
							<option value="${map.key}">${map.value }</option>
						</c:forEach>
				</select>
				</td>
				<th>可控程度</th>
				<td><input name="dto.controlStatus" id="controlStatus"
					value='${editObj.controlStatus}' class="text" title="可控程度"
					type="text" /></td>
			</tr>
			<tr>
				<th>限期整改时间</th>
				<td><input class="Wdate" type="text"
					onclick="WdatePicker({errDealMode:2 })" name="dto.deadlineDate"
					id='deadlineDate' value="${editObj.deadlineDate}" /></td>
				<th>整顿时间</th>
				<td><input class="Wdate" type="text"
					onclick="WdatePicker({errDealMode:2 })" name="dto.rectifyDate"
					id='rectifyDate' value="${editObj.rectifyDate}" /></td>

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
				<td><input name="dto.longitude" id="longitude" class="text" size='8'
					title="经度" type="text" value='${editObj.longitude}'>&nbsp;
					<input name="dto.latitude" id="latitude" class="text" size='8'
					title="纬度" type="text" value='${editObj.latitude}'>
					<a onclick="getCurrCaseLocateXY('${ctx}/gis/getLocateCoordinates2.json');" target="_blank" id="posBtn" style="text-align:center; padding-top:7px;">
					<img alt="选择坐标" src="${ctx}/resources/images/gis/place.gif" style="border:none;">
					</a>
				</td>
			</tr>
			<tr>
				<th>风险隐患统计</th>
				<td><input name="dto.riskNum" id="riskNum" title="风险隐患统计"
					type="text" class="text" value="${editObj.riskNum }" />
				</td>
				<th>联络人员</th>
				<td><input name="dto.contactPerson" id="contactPerson"
					class="text" title="联络人员" type="text"
					value='${editObj.contactPerson}'>
				</td>

			</tr>
			<tr>
				<th>联络电话</th>
				<td><input name="dto.contactTel" id="contactTel" title="联络电话"
					class="text" type="text" value="${editObj.contactTel }" />
				</td>
				<th>主管单位</th>
				<td><input name="dto.mgrDept" id="mgrDept" class="text"
					title="主管单位" type="text" value='${editObj.mgrDept}'>
				</td>

			</tr>
			<tr >
				<th>状态</th>
				<td><select id='riskStatus' name='dto.riskStatus'>
						<option value="">请选择</option>
						<c:forEach items="${riskStatusMap }" var='map'>
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
				</select>
				</td>


			</tr>
			<tr>
				<th>地址</th>
				<td colspan="3"><textarea name="dto.address" id="address"
						title="地址" rows="1" cols="100">${editObj.address}</textarea>
				</td>
			</tr>
			<tr>
				<th>整顿内容</th>
				<td colspan="3"><textarea name="dto.rectifyContent"
						id="rectifyContent" title="整顿内容" rows="2" cols="100">${editObj.rectifyContent}</textarea>
				</td>
			</tr>
			<tr>
				<th>整治措施</th>
				<td colspan="3"><textarea name="dto.rectifyStep"
						id="rectifyStep" title="整治措施" rows="2" cols="100">${editObj.rectifyStep}</textarea>
				</td>
			</tr>
			<tr>
				<th>责任单位和措施</th>
				<td colspan="3"><textarea name="dto.dutyDeptStep"
						id="dutyDeptStep" title="责任单位和措施" cols="100">${editObj.dutyDeptStep}</textarea>
				</td>
			</tr>
			<tr>
				<th>处理能力和资源储备</th>
				<td colspan="3"><textarea name="dto.handlingCapacity"
						id="handlingCapacity" title="处理能力和资源储备"  cols="100">${editObj.handlingCapacity}</textarea>
				</td>
			</tr>
		</table>
		<div class="ctrls_bar">
			<input type="hidden"
				value='${editObj.riskId }' name='riskId'>
		</div>
	</form>
</body>
</html>