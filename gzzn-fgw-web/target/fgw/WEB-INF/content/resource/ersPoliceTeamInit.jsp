<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急公安队伍</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="应急队伍-查询结果">
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
				"dto.teamname" : {
					required : true,
					byteMaxLength : 50
				},
				"dto.nucode" : {
					byteMaxLength : 32
				},
				"dto.teamtype" : {
					byteMaxLength : 10
				},
				"dto.teamgrade" : {
					byteMaxLength : 10
				},
				"dto.superunit" : {
					byteMaxLength : 10
				},
				"dto.address" : {
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
				"dto.membernum" : {
					byteMaxLength : 5,
					digits : true
				},
				"dto.equipdesc" : {
					byteMaxLength : 2000
				},
				"dto.dutyarea" : {
					byteMaxLength : 500
				},
				"dto.notes" : {
					byteMaxLength : 200
				}
			},
			messages : {
				"dto.teamname" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.nucode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.teamtype" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.teamgrade" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.superunit" : {
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
				"dto.principaltel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.fax" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.membernum" : {
					digits : "请输入整数",
					byteMaxLength : "长度最多是 {0} 的数字"
				},
				"dto.equipdesc" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutyarea" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutytel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#notes").charCount({
			allowed : 200
		});
		//
		$("#equipdesc").charCount({
			allowed : 2000
		});
		$("#dutyarea").charCount({
			allowed : 500
		});
		$("#address").charCount({
			allowed : 200
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersPoliceTeamSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersPoliceTeamQuery.ac";
														} else {
															parent.mac
																	.alert(data.info);
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
			showMenu(2, 22);
		});
	</script>
	<form action="${ctx }/resource/ersPoliceTeamSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					应急公安队伍信息录入
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
					<td><input type="text" class="text" name="dto.teamname"
						value="${editObj.teamname }" />
					</td>
					<th>类别名称</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" readonly="readonly"
						id="classifyName" /></td>
				</tr>
				<tr>
					<th>行政区划</th>
					<td><input id="districtName" name='dto.districtName'
						type="text" readonly="readonly" value="${editObj.districtName }" /><input
						id="districtId" name='dto.districtId' type="hidden"
						value="${editObj.districtId }" />
					</td>
					<th>上级单位</th>
					<td><input type="text" class="text" name="dto.superunit"
						id='superunit' value="${editObj.superunit}" />
					</td>
				</tr>
				<tr>

					<th>唯一标识码</th>
					<td><input type="text" class="text" name="dto.nucode"
						id='nucode' value='${editObj.nucode }' />
					</td>
					<th>队伍级别</th>
					<td><input type="text" class="text" name="dto.teamgrade"
						id='teamgrade' value='${editObj.teamgrade }' />
					</td>
				</tr>
				<tr style="display: none;">
					<th>救援队伍类型</th>
					<td><input type="text" class="text" name="dto.teamtype"
						id='teamtype' value="${editObj.teamtype}" />
					</td>


				</tr>


				<tr>
					<th>负责人</th>
					<td><input name="dto.principal" id="principal" title="负责人"
						type="text" value="${editObj.principal }" />
					</td>
					<th>联系电话</th>
					<td><input name="dto.principaltel" id="principaltel"
						class="text" title="联系电话" type="text"
						value='${editObj.principaltel}'>
					</td>

				</tr>
				<tr>
					<th>地址</th>
					<td colspan="3"><textarea cols="100" rows="2" id='address'
							 name='dto.address'>${editObj.address }</textarea>
					</td>
				</tr>
				<tr>
					<th>应急值班电话</th>
					<td><input type="text" class="text" name="dto.dutytel"
						id='dutytel' value="${editObj.dutytel}" />
				</tr>
				<tr>
					<th>传真</th>
					<td><input name="dto.fax" id="fax" title="传真" type="text"
						value="${editObj.fax }" />
					</td>
					<th>总人数</th>
					<td><input name="dto.membernum" id="membernum" class="text"
						title="总人数" type="text" value='${editObj.membernum}'>
					</td>

				</tr>

				<tr>
					<th>主要装备描述</th>
					<td colspan="3"><textarea cols="100" rows="3" id='equipdesc'
							name='dto.equipdesc'>${editObj.equipdesc }</textarea>
					</td>
				</tr>
				<tr>
					<th>责任区范围描述</th>
					<td colspan="3"><textarea cols="100" rows="3" id='dutyarea'
							name='dto.dutyarea'>${editObj.dutyarea }</textarea>
					</td>
				</tr>
				<tr>
					<th>更新时间</th>
					<td><input type="text" name="dto.updatetime" id='updatetime'
						value="${editObj.updatetime }"
						onclick="WdatePicker({errDealMode:2})" class="Wdate" />
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="3"
							id='notes'>${editObj.notes}</textarea>
					</td>
				</tr>
		</table>
		<div class="ctrls_bar">
		 <input type="hidden" name='teamid'
				value='${editObj.teamid }' />
		</div>
	</form>
</body>
</html>