<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急专家组</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
		<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#levelCode").attr('value', '${expert.levelCode}');
		$("#expertType").attr('value', '${expert.expertType}');
	}
</script>
<script type="text/javascript">
	$(function() {
		
		$("#remark").charCount({
			allowed : 500
		});
		$("#exppertGroupDesc").charCount({
			allowed : 2000
		});
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.expertGroupName" : {
					required : true,
					byteMaxLength : 50
				},
				"dto.nucode" : {
					byteMaxLength : 16
				},
				"dto.expertType" : {
					required : true
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
				"dto.buildDept" : {
					byteMaxLength : 50
				},
				"dto.buildDeptAddress" : {
					byteMaxLength : 200
				},
				"dto.buildDeptPostalcode" : {
					byteMaxLength : 6,
					digits : true
				},
				"dto.peopleNum" : {
					required : true,
					byteMaxLength : 7,
					digits : true
				},
				"dto.exppertGroupDesc" : {
					byteMaxLength : 2000
				},
				"dto.dataFromCode" : {
					byteMaxLength : 4
				},
				"dto.remark" : {
					byteMaxLength : 500
				}
			},
			messages : {
				"dto.expertGroupName" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.nucode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.tel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.fax" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.manager" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrOfficeTel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrMobile" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mgrHomeTel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contacts" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactsOfficeTel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactMobile" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactHomeTel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.contactEmail" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.buildDept" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.buildDeptAddress" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.expertType" : {
					required : "必选字段"
				},
				"dto.buildDeptPostalcode" : {
					byteMaxLength : "长度最多是 {0} 的字符串",
					digits : "数字类型"
				},
				"dto.peopleNum" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的数字",
					digits : "数字类型"
				},
				"dto.exppertGroupDesc" : {
					byteMaxLength : "长度最多是 {0} 的数字"
				},
				"dto.dataFromCode" : {
					byteMaxLength : "长度最多是 {0} 的数字"
				},
				"dto.remark" : {
					byteMaxLength : "长度最多是 {0} 的数字"
				}
			}
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersExpertGroupSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac.alert(data.info);
															window.location.href = "${ctx }/resource/ersExpertGroupQuery.ac";
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
<script type="text/javascript">
		$(function(){
				showMenu(2, 23);
		});
</script>
	<form action="${ctx }/resource/emrgShltrSave.ac" method="post"
		id='signupForm'>

		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					应急专家组信息录入
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
		<table align="center" class="editTab" border="0" width="100%"
			description="应急专家">
			<thead>
				<tr>
					<td colspan="4" align="center">基本信息</td>
				</tr>
			</thead>
			<tr>
				<th width="20%">专家组姓名<span style="color: red">&nbsp;*</span></th>
				<td width="20%"><input name="dto.expertGroupName"
					id="expertGroupName" title="专家姓名" type="text" class="text"
					cssRule="require3" value='${expert.expertGroupName }'>
				</td>
				<th width="20%">唯一标识码</th>
				<td width="20%"><input name="dto.nucode" id="nucode"
					class="text" title="唯一标识码" type="text" value='${expert.nucode}'>
				</td>
			</tr>
			<tr>
				<th>级别代码</th>
				<td><select name="dto.levelCode" id='levelCode'>
						<option value=''>请选择</option>
						<c:forEach var="map" items="${leveCodelMap }">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>

				</select></td>
				<th>专家组类型<span style="color: red">&nbsp;*</span></th>
				<td width="100"><select name="dto.expertType" id='expertType'>
						<option value=''>请选择</option>
						<c:forEach var="map" items="${expertTypeMap }">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th>专家组联系电话</th>
				<td><input name="dto.tel" id="tel" class="text" title="专家组联系电话"
					type="text" value='${expert.tel}'></td>
				<th width="100">传真号码</th>
				<td><input name="dto.fax" id="fax" class="text"
					title="专家组的联系传真号码。多个号码用英文逗号分隔。" type="text" value='${expert.fax}'>
				</td>
			</tr>
			<tr>
			<tr>
				<th>负责人姓名</th>
				<td><input name="dto.manager" id="manager" class="text"
					title="负责人姓名" type="text" value='${expert.manager}'>
				</td>
				<th>负责人办公电话</th>
				<td><input name="dto.mgrOfficeTel" id="mgrOfficeTel"
					class="text" title="负责人办公电话" type="text"
					value='${expert.mgrOfficeTel}'>
				</td>

			</tr>
			<tr>
				<th>负责人移动电话</th>
				<td><input name="dto.mgrMobile" id="mgrMobile" class="text"
					title="负责人移动电话。多个用,隔开" type="text" value='${expert.mgrMobile}'>
				</td>
				<th>负责人住宅电话</th>
				<td><input name="dto.mgrHomeTel" id="mgrHomeTel" class="text"
					title="负责人住宅电话" type="text" value='${expert.mgrHomeTel}'>
				</td>
			</tr>
			<tr>
				<th>联系人姓名</th>
				<td><input name="dto.contacts" id="contacts" class="text"
					title="负责人姓名" type="text" value='${expert.contacts}'>
				</td>
				<th>联系人办公电话</th>
				<td><input name="dto.contactsOfficeTel" id="contactsOfficeTel"
					class="text" title="联系人办公电话" type="text"
					value='${expert.contactsOfficeTel}'>
				</td>

			</tr>
			<tr>
				<th>联系人移动电话</th>
				<td><input name="dto.contactMobile" id="contactMobile"
					class="text" title="专家组联系人的移动电话。多个电话用英文逗号分隔。" type="text"
					value='${expert.contactMobile}'>
				</td>
				<th>联系人住宅电话</th>
				<td><input name="dto.contactHomeTel" id="contactHomeTel"
					class="text" title="专家组联系人的住宅电话。多个电话用英文逗号分隔。" type="text"
					value='${expert.contactHomeTel}'>
				</td>
			</tr>
			<tr>
				<th>联系人电子邮箱</th>
				<td><input name="dto.contactEmail" id="contactEmail"
					class="text" title="电子邮箱" type="text"
					value='${expert.contactEmail}'></td>
				<th>单位名称</th>
				<td><input name="dto.buildDept" id="buildDept" class="text"
					title="专家组组建单位的名称" type="text" value='${expert.buildDept}'>
				</td>
			</tr>
			<tr>
				<th>详细地址</th>
				<td><input name="dto.buildDeptAddress" id="buildDeptAddress"
					class="text" title="专家组组建单位的详细地址。" type="text"
					value='${expert.buildDeptAddress}'></td>
				<th>邮政编码</th>
				<td><input name="dto.buildDeptPostalcode"
					id="buildDeptPostalcode" class="text" title="邮政编码" type="text"
					value='${expert.buildDeptPostalcode}'></td>
			</tr>
			<tr>
				<th>人员数量<span style="color: red">&nbsp;*</span></th>
				<td colspan="3"><input name="dto.peopleNum" id="peopleNum"
					class="text" title="专家组的人员数量。单位为人" type="text"
					value='${expert.peopleNum}'></td>
			</tr>
			<tr>
				<th>专家组介绍</th>
				<td colspan="3"><textarea name="dto.exppertGroupDesc"
						id="exppertGroupDesc" title="专家组介绍" rows="3" cols="100">${expert.exppertGroupDesc}</textarea>
			</tr>
			<tr>
				<th>单位代码</th>
				<td><input name="dto.dataFromCode" id="dataFromCode"
					class="text" title="该数据的来源单位代码" type="text"
					value='${expert.dataFromCode}'></td>
				<th>最近更新时间</th>
				<td><input name="dto.updateTime" id="updateTime" 
					title="最近更新时间" type="text" onclick="WdatePicker({errDealMode:2 })" class="Wdate"
						value="${shltr.updateTime }" />
				</td>
			</tr>
				<tr>
				<th>补充说明</th>
				<td colspan="3"><textarea name="dto.remark"
						id="remark" title="专家组简短的文字描述，或者补充说明信息。" rows="3" cols="100">${expert.remark}</textarea>
			</tr>
		</table>
		<div class="ctrls_bar">
			 <input type="hidden"
				name='expertGroupId' value='${expert.expertGroupId }' />
		</div>
	</form>
</body>
</html>