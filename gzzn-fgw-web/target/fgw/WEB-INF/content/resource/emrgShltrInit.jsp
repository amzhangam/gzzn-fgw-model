<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>避难场所</title>
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
<script src="${ctx}/resources/js/ers/getLocateCoordinates.js"
	　type="text/javascript"></script>

<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍
		initMyTree("classify", "getClassifyTreeJson.json?catalogue=4");
		initMyTree("district","getDistrictTreeJson.json");
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#coordSys").attr('value', '${shltr.coordSys}');
		$("#districtId").attr('value', '${shltr.districtId}');
		$("#classifyId").attr('value', '${shltr.classifyId}');
	}
</script>
<script type="text/javascript">
	$(function() {
		setSelectValue();
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"sheltername" : {
					required : true,
					byteMaxLength : 50
				},
				"nucode" : {
					byteMaxLength : 32
				},
				"address" : {
					byteMaxLength : 200
				},
				"tel" : {
					byteMaxLength : 50
				},
				"area" : {
					isDecimal : [ 0, 10000000, 3 ]
				},
				"capacity" : {
					byteMaxLength : 10,
					digits : true
				},
				"notes" : {
					byteMaxLength : 200
				},
				"longitude" : {
					isDecimal : [ 0, 100000000, 15 ]
				},
				"latitude" : {
					isDecimal : [ 0, 100000000, 15 ]
				}
			},
			messages : {
				"sheltername" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"nucode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"address" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"tel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"area" : {
					isDecimal : "请正确输入最多7位数，最多只保留小数点后{2}的数值"
				},
				"capacity" : {
					digits : "请输入整数",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"notes" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"longitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				},
				"latitude" : {
					isDecimal : "请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"
				}
			}
		});
		$("#address").charCount({
			allowed : 200
		});
		//
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
													url : '${ctx }/resource/emrgShltrSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/emrgShltrQuery.ac";
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

	<form action="${ctx }/resource/save.ac" method="post" id='signupForm'>
	<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				应急避难场所信息录入
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
				<th width="25%">避难场所名称<span style="color: red">&nbsp;*</span>
				</th>
				<td width="25%"><input name="sheltername" id="sheltername"
					title="避难场所名称必填" type="text" class="text"
					value='${shltr.sheltername }'>
				</td>
				<th width="25%">唯一标志码</th>
				<td width="25%"><input autocomplete="off" name="nucode"
					id="nucode" class="text" title="唯一标志码" type="text"
					value='${shltr.nucode }' /></td>
			</tr>
			<tr>
				<th>类别名称</th>
				<td colspan="3"><input type="hidden" class="text"
					name="dto.classifyId" value="${shltr.classifyId }"
					id='classifyId' /> <input type="text" class="text"
					name="dto.classifyName" value="${shltr.classifyName}"
					readonly="readonly" id="classifyName" />
				</td>
			</tr>
			<tr>
				<th>地址</th>
				<td colspan="3"><textarea name="address" id="address"
						title="地址" rows="1" cols="100">${shltr.address}</textarea></td>
			</tr>
			<tr>
				<th>联系电话</th>
				<td><input name="tel" id="tel" class="text" title="联系电话"
					type="text" value='${shltr.tel}'></td>
				<th>建设时间</th>
				<td><input name="builddate" id="builddate" title="建设时间"
					type="text" onclick="WdatePicker({errDealMode:2 })" class="Wdate"
					value="${shltr.builddate }" /></td>
			</tr>
			<tr>
				<th>行政区划</th>
				<td colspan="3"><input id="districtName" name='districtName' type="text"
					readonly="readonly" value="${shltr.districtName }" /><input
					id="districtId" name='districtId' type="hidden"
					value="${shltr.districtId }" />
				</td>

			</tr>
			<tr>
				<th>面积（万M²）</th>
				<td><input name="area" id="area" class="text" title="面积"
					type="text" value='${shltr.area}'></td>
				<th>容纳人数</th>
				<td><input name="capacity" id="capacity" class="text"
					title="容纳人数" type="text" value='${shltr.capacity}'></td>
			</tr>
			<tr>
				<th>坐标系</th>
				<td colspan="3"><select id='coordSys' name='coordSys'>
						<option value="">请选择</option>
						<c:forEach items="${coordSysMap }" var='map'>
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
				</select>
				</td>
				
			</tr>
			<tr>
				<th>经纬度</th>
				<td colspan="3"><input name="longitude" id="longitude" 
					size='12' title="经度" type="text" value='${shltr.longitude}'>&nbsp;
					<input name="latitude" id="latitude" class="text" size='12'
					title="纬度" type="text" value='${shltr.latitude}'> <a
					onclick="getCurrCaseLocateXY('${ctx}/gis/getLocateCoordinates2.json');"
					target="_blank" id="posBtn"
					style="text-align: center; padding-top: 7px;"> <img alt="选择坐标"
						src="${ctx}/resources/images/gis/place.gif" style="border: none;">
				</a></td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="notes" id="notes" title="备注"
						rows="2" cols="100">${shltr.notes}</textarea></td>
			</tr>
		</table>

		<div class="ctrls_bar">
			<input type="hidden"
				value='${shltr.shelterid }' name='shelterid'>
		</div>
	</form>
</body>
</html>