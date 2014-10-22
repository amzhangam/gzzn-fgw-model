<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>重大危险源</title>
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
<script src="${ctx}/resources/js/ers/getLocateCoordinates.js"　type="text/javascript"></script>
	
<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍
		initMyTree("classify","getClassifyTreeJson.json?catalogue=2");
		initMyTree("district","getDistrictTreeJson.json");
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#districtId").attr('value', '${editObj.districtId}');
		$("#classifyId").attr('value', '${editObj.classifyId}');
		$("#elevadatumId").attr('value', '${editObj.elevadatumId}');
		$("#sourcedeptid").attr('value', '${editObj.sourcedeptid}');
		$("#dangerLevel").attr('value', '${editObj.dangerLevel}');
		$("#secretCode").attr('value', '${editObj.secretCode}');
		$("#dangerLevelCode").attr('value', '${editObj.dangerLevelCode}');
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.dangerName" : {
					required : true,
					byteMaxLength : 100
				},
				"dto.nucode":{
					byteMaxLength : 32
				},
				"dto.dangerDesignerYear":{
					byteMaxLength : 4,
					digits : true
				},
				"dto.dnagerAddress" : {
					byteMaxLength : 200
				},
				"dto.tel" : {
					byteMaxLength : 50
				},
				"dto.fax" : {
					byteMaxLength : 50
				},
				"dto.manager" : {
					byteMaxLength : 200
				},
				"dto.mgrOfficeTel" : {
					byteMaxLength : 200
				},
				"dto.mgrMobile" : {
					byteMaxLength : 200
				},
				"dto.mgrHomeTel" : {
					byteMaxLength : 200
				},
				"dto.contacts" : {
					byteMaxLength : 200
				},
				"dto.contactsOfficeTel" : {
					byteMaxLength : 200
				},
				"dto.contactMobile" : {
					byteMaxLength : 200
				},
				"dto.contactHomeTel" : {
					byteMaxLength : 200
				},
				"dto.contactEmail" : {
					byteMaxLength : 200
				},
				"dto.mgrDept" : {
					byteMaxLength : 100
				},
				"dto.mgrDeptAddress" : {
					byteMaxLength : 200
				},
				"dto.elevadatumNum" : {
					isDecimal : [ 0, 10000, 3 ]
				},
				"dto.dnagerArea" : {
					isDecimal : [ 0, 10000000, 3 ]
				},
				"dto.dangerPersonNum" : {
					digits : true,
					byteMaxLength : 10
				},
				"dto.dangerDesc" : {
					byteMaxLength : 2000
				},
				"dto.dangerTrafficDesc" : {
					byteMaxLength : 200
				},
				"dto.emgrCommMode" : {
					byteMaxLength : 500
				},
				"dto.possibleDamage" : {
					byteMaxLength : 100
				},
				"dto.affectScope" : {
					byteMaxLength : 200
				},
				"dto.treatenPersonNum" : {
					byteMaxLength : 10,
					digits : true
				},
				"dto.monitorMode" : {
					byteMaxLength : 200
				},
				"dto.manageMeasure" : {
					byteMaxLength : 500
				},
				"dto.remark" : {
					byteMaxLength : 500
				},
				"dto.longitude" : {
					isDecimal : [ 0, 10000000, 13 ]
				},
				"dto.latitude" : {
					isDecimal : [ 0, 10000000, 13 ]
				}
			},
			messages : {
				"dto.dangerName" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dnagerAddress" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.tel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.fax" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrOfficeTel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrMobile" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrHomeTel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contacts" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactsOfficeTel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactMobile" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactHomeTel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactEmail" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrDept" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrDeptAddress" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.elevadatumNum" : {
					isDecimal : "请输入在{0}到{1}之间，最多保留小数点后{2}数"
				},
				"dto.dnagerArea" : {
					isDecimal : "请输入在{0}到{1}之间，最多保留小数点后{2}数"
				},
				"dto.dangerPersonNum" : {
					digits : "请输入正整数",
					byteMaxLength : "长度最多是 {0} 的数字"
				},
				"dto.dangerDesc" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dangerTrafficDesc" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.emgrCommMode" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.possibleDamage" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.affectScope" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.treatenPersonNum" : {
					byteMaxLength : "长度最多是 {0} 的数值",
					digits : "请输入整数"
				},
				"dto.monitorMode" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.manageMeasure" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.remark" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.nucode":{
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.manager":{
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dangerDesignerYear":{
					byteMaxLength : "长度最多是 {0} 的数字",
					digits :"请输入整数"
				},
				"dto.longitude" : {
					isDecimal : "请输入在{0}到{1}之间，最多保留小数点后{2}的数"
				},
				"dto.latitude" : {
					isDecimal : "请输入在{0}到{1}之间，最多保留小数点后{2}的数"
				}
			}
		});
		//对地址
		$("#dnagerAddress").charCount({
			allowed : 200
		});
		//
		$("#dangerDesc").charCount({
			allowed : 2000
		});
		//
		$("#dangerTrafficDesc").charCount({
			allowed : 200
		});
		$("#emgrCommMode").charCount({
			allowed : 500
		});
		$("#possibleDamage").charCount({
			allowed : 100
		});
		$("#affectScope").charCount({
			allowed : 200
		});
		$("#monitorMode").charCount({
			allowed : 200
		});
		$("#manageMeasure").charCount({
			allowed : 500
		});
		$("#remark").charCount({
			allowed : 500
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersDangerSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac.alert(data.info);
															window.location.href = "${ctx }/resource/ersDangerQuery.ac";
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

</head>
<style type="text/css">
body {
	margin: 0px;
	margin-left: 0px;
	padding: 0px;
}
</style>
<body style="overflow: hidden">
	<form action="${ctx }/resource/emrgShltrSave.ac" method="post"
		id='signupForm'>

		<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				重大危险源信息录入
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
			<thead>
				<tr>
					<td colspan="4" align="center">基本信息</td>
				</tr>
			</thead>
			<tr>
				<th>危险源名称<span style="color: red">&nbsp;*</span></th>
				<td colspan="3"><input type="text" class="text"
					name="dto.dangerName" value="${editObj.dangerName }" />
				</td>

			</tr>
			<tr>
				<th>唯一标识码</th>
				<td><input type="text" class="text" name="dto.nucode"
					value="${editObj.nucode }" />
				</td>
						<th>类别名称</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" readonly="readonly" id="classifyName" /></td>
			</tr>
			<tr>
				<th>行政区划</th>
				<td><input id="districtName" name='dto.districtName'
					type="text" readonly="readonly" value="${editObj.districtName }" /><input
					id="districtId" name='dto.districtId' type="hidden"
					value="${editObj.districtId }" />
				<!-- 数据来源getTree.js -->
				</td>
				<th>高程基准代码</th>
				<td><select id='elevadatumId' name='dto.elevadatumId'>
						<option value="">请选择</option>
						<c:forEach items="${elevadatumList }" var='elevadatum'>
							<option value="${elevadatum.elevadatumId }">${elevadatum.elevadatumName
								}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th>数据来源单位</th>
				<td><select id='sourcedeptid' name='dto.sourcedeptid'>
						<option value="">请选择</option>
						<c:forEach items="${sourcedeptList }" var='sourcedept'>
							<option value="${sourcedept.sourcedeptid }">${sourcedept.sourcedeptname
								}</option>
						</c:forEach>
				</select>
				</td>
				<th>级别代码</th>
				<td><select id='dangerLevel' name='dto.dangerLevel'>
						<option value="">请选择</option>
						<c:forEach items="${dangerLevelMap }" var='map'>
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th>密级代码</th>
				<td><select id='secretCode' name='dto.secretCode'>
						<option value="">请选择</option>
						<c:forEach items="${secretCodeMap }" var='map'>
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
				</select>
				</td>
			</tr>
				<tr>
				<th>地址</th>
				<td colspan="3"><textarea name="dto.dnagerAddress" id="dnagerAddress"
						title="地址" rows="1" cols="100">${editObj.dnagerAddress}</textarea>
				</td>
			</tr>

			<thead>
				<tr>
					<td colspan="4" align="center">联系方式</td>
				</tr>
			</thead>
			<tr>
				<th>值班电话</th>
				<td><input name="dto.tel" id="tel" title="值班电话" type="text"
					class="text" value='${editObj.tel}'></td>
				<th>传真</th>
				<td><input name="dto.fax" id="fax" class="text" title="传真"
					type="text" value='${editObj.fax}'></td>

			</tr>
			<tr>
				<th>负责人姓名</th>
				<td><input name="dto.manager" id="manager" class="text"
					title="负责人姓名" type="text" value='${editObj.manager}'></td>
				<th>负责人办公电话</th>
				<td><input name="dto.mgrOfficeTel" id="mgrOfficeTel"
					class="text" title="负责人办公电话" type="text"
					value='${editObj.mgrOfficeTel}'></td>

			</tr>
			<tr>
				<th>负责人移动电话</th>
				<td><input name="dto.mgrMobile" id="mgrMobile" class="text"
					title="负责人移动电话。多个用,隔开" type="text" value='${editObj.mgrMobile}'>
				</td>
				<th>负责人住宅电话</th>
				<td><input name="dto.mgrHomeTel" id="mgrHomeTel" class="text"
					title="负责人住宅电话" type="text" value='${editObj.mgrHomeTel}'>
				</td>
			</tr>
			<tr>
				<th>联系人姓名</th>
				<td><input name="dto.contacts" id="contacts" class="text"
					title="负责人姓名" type="text" value='${editObj.contacts}'></td>
				<th>联系人办公电话</th>
				<td><input name="dto.contactsOfficeTel" id="contactsOfficeTel"
					class="text" title="联系人办公电话" type="text"
					value='${editObj.contactsOfficeTel}'></td>

			</tr>
			<tr>
				<th>联系人移动电话</th>
				<td><input name="dto.contactMobile" id="contactMobile"
					class="text" title="联系人移动电话。多个用,隔开" type="text"
					value='${editObj.contactMobile}'></td>
				<th>联系人住宅电话</th>
				<td><input name="dto.contactHomeTel" id="contactHomeTel"
					class="text" title="联系人住宅电话" type="text"
					value='${editObj.contactHomeTel}'></td>
			</tr>
			<thead>
				<tr>
					<td colspan="4" align="center">其他信息</td>
				</tr>
			</thead>


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
					<input name="dto.latitude" id="latitude" class="text" 
					title="纬度" type="text" value='${editObj.latitude}'>
					<a onclick="getCurrCaseLocateXY('${ctx}/gis/getLocateCoordinates2.json');" target="_blank" id="posBtn" style="text-align:center; padding-top:7px;">
					<img alt="选择坐标" src="${ctx}/resources/images/gis/place.gif" style="border:none;">
					</a>
				</td>
			</tr>
			<tr>
				<th>主管单位信息</th>
				<td><input name="dto.mgrDept" id="mgrDept" class="text"
					title="主管单位信息" type="text" value='${editObj.mgrDept}'></td>
				<th>主管单位地址</th>
				<td><input name="dto.mgrDeptAddress" id="mgrDeptAddress"
					class="text" title="主管单位地址信息" type="text"
					value='${editObj.mgrDeptAddress}'></td>

			</tr>
			<tr>
				<th>主要部位或中心点的高程</th>
				<td><input name="dto.elevadatumNum" id="elevadatumNum"
					class="text" title="主要部位或中心点的高程。单位为米。" type="text"
					value='${editObj.elevadatumNum}'></td>
				<th>占地面积</th>
				<td><input name="dto.dnagerArea" id="dnagerArea" class="text"
					title="危险源的占地面积。单位为平方米。" type="text" value='${editObj.dnagerArea}'>
				</td>

			</tr>
			<tr>
				<th>危险源人员数量</th>
				<td colspan="3"><input name="dto.dangerPersonNum" class="text"
					id="dangerPersonNum" title="危险源的人员数量。如实验室的工作人员数量、影剧院的工作人员数量等。单位为人。"
					type="text" value='${editObj.dangerPersonNum}'></td>


			</tr>
			<tr>
				<th>危险源基本情况描述</th>
				<td colspan="3"><textarea name="dto.dangerDesc" id="dangerDesc"
						title="危险源的基本情况描述，包括危险源的功能、用途、恢复能力等。" rows="3" cols="100">${editObj.dangerDesc}</textarea>
				</td>
			</tr>
			<tr>
				<th>危险源周边交通状况</th>
				<td colspan="3"><textarea name="dto.dangerTrafficDesc"
						id="dangerTrafficDesc" title="危险源周边的交通状况，包括公路、铁路、水运、航空等。。"
						rows="3" cols="100">${editObj.dangerTrafficDesc}</textarea>
				</td>
			</tr>
			<tr>
				<th>投入使用时间。</th>
				<td><input name="dto.dangerUseDate" class="text"
					id="dangerUseDate" title="危险源的投入使用时间" type="text"
					value='${editObj.dangerUseDate}' class="Wdate" type="text"
					onclick="WdatePicker({errDealMode:2 })">
				<th>设计使用年限</th>
				<td><input name="dto.dangerDesignerYear"
					id="dangerDesignerYear" class="text" title="危险源的设计使用年限。单位为年。"
					type="text" value='${editObj.dangerDesignerYear}'></td>
			</tr>

			<tr>
				<th>通信方式</th>
				<td colspan="3"><textarea name="dto.emgrCommMode"
						id="emgrCommMode"
						title="在突发事件发生后常规通信方式无效时的通信方式。如卫星通信、短波通信等，以及人员到达时间、启用手段等。"
						rows="3" cols="100">${editObj.emgrCommMode}</textarea>
				</td>
			</tr>
			<tr>
				<th>可能灾害形式</th>
				<td colspan="3"><textarea name="dto.possibleDamage"
						id="possibleDamage" title="可能造成的灾害形式。" rows="3" cols="100">${editObj.possibleDamage}</textarea>
				</td>
			</tr>
			<tr>
				<th>危险源的危险等级代码</th>
				<td colspan="3"><select id='dangerLevelCode'
					name='dto.dangerLevelCode'>
						<option value="">请选择</option>
						<c:forEach items="${dangerLevelCodeMap }" var='map'>
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th>危险源可能影响的范围</th>
				<td colspan="3"><textarea name="dto.affectScope"
						id="affectScope" title="危险源可能影响的范围。如影响面积、影响区域等。" rows="3"
						cols="100">${editObj.affectScope}</textarea></td>
			</tr>
			<tr>
				<th>最多能容纳的人数</th>
				<td colspan="3">
				<input name="dto.treatenPersonNum" id="treatenPersonNum" class="text"
					 title="危险源最多能容纳的人数。单位为人" type="text" value='${editObj.treatenPersonNum}'>
				</td>
			</tr>
			<tr>
				<th>已采取的监测方式</th>
				<td colspan="3"><textarea name="dto.monitorMode"
						id="monitorMode" title="已采取的监测方式" rows="3" cols="100">${editObj.monitorMode}</textarea>
				</td>
			</tr>
			<tr>
				<th>已采取的监测方式</th>
				<td colspan="3"><textarea name="dto.manageMeasure"
						id="manageMeasure" title="已采取的监测方式" rows="3" cols="100">${editObj.manageMeasure}</textarea>
				</td>
			</tr>

			<tr>
				<th>最近更新时间</th>
				<td colspan="3"><input name="dto.updateTime" class="text"
					id="updateTime" title="最近更新时间" type="text"
					value='${editObj.updateTime}' class="Wdate" type="text"
					onclick="WdatePicker({errDealMode:2 })">
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="dto.remark" id="remark"
						title="备注" rows="3" cols="100">${editObj.remark}</textarea>
				</td>
			</tr>
		</table>
		<div class="ctrls_bar">
		   <input type="hidden" name='dnagerId'
				value='${editObj.dnagerId }' />
		</div>
	</form>
</body>
</html>