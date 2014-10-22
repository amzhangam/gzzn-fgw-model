<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急专家</title>
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
		$("#major").attr("value", "${expert.major}");
		$("#expertype").attr('value', '${expert.expertype}');
		$("#sex2").attr('value', '${expert.sex2}');
	}
</script>
<script type="text/javascript">
	$(function() {
		
		$("#resume").charCount({
			allowed : 1000
		});
		$("#proficient").charCount({
			allowed : 100
		});
		$("#notes").charCount({
			allowed : 1000
		});
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.name" : {
					required : true,
					byteMaxLength : 60
				},
				"dto.expertype" : {
					required : true
				},
				"dto.nationality" : {
					byteMaxLength : 16
				},
				"dto.workunit" : {
					byteMaxLength : 60
				},
				"dto.post" : {
					byteMaxLength : 16
				},
				"dto.headship" : {
					byteMaxLength : 16
				},
				"dto.proficient" : {
					byteMaxLength : 100
				},
				"dto.mobiletel" : {
					byteMaxLength : 50
				},
				"dto.officetel" : {
					byteMaxLength : 50
				},
				"dto.fax" : {
					byteMaxLength : 10
				},
				"dto.email" : {
					byteMaxLength : 30
				},
				"dto.hometel" : {
					byteMaxLength : 50
				},
				"dto.homeaddr" : {
					byteMaxLength : 100
				},
				"dto.idno" : {
					byteMaxLength : 18
				},
				"dto.native_" : {
					byteMaxLength : 10
				},
				"dto.party" : {
					byteMaxLength : 10
				},
				"dto.health" : {
					byteMaxLength : 10
				},
				"dto.qualification" : {
					byteMaxLength : 10
				},
				"dto.graduate" : {
					byteMaxLength : 50
				},
				"dto.supervisaldept" : {
					byteMaxLength : 32
				},
				"dto.corresponding" : {
					byteMaxLength : 100
				},
				"dto.postcode" : {
					byteMaxLength : 6,
					digits : true
				},
				"dto.residentregistration" : {
					byteMaxLength : 100
				},
				"dto.resume" : {
					byteMaxLength : 1000
				},
				"dto.notes" : {
					byteMaxLength : 1000
				}
			},
			messages : {
				"dto.name" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.expertype" : {
					required : "必选字段"
				},
				"dto.nationality" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.workunit" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.post" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.headship" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.proficient" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.mobiletel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.officetel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.fax" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.email" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.hometel" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.homeaddr" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.idno" : {
					byteMaxLength : "身份证最多是 {0} 的字符串"
				},
				"dto.native_" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.party" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.health" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.qualification" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.graduate" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.supervisaldept" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.corresponding" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.postcode" : {
					byteMaxLength : "长度最多是 {0} 的字符串",
					digits : "邮编输入错误"
				},
				"dto.residentregistration" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.resume" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
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
													url : '${ctx }/resource/ersExpertSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersExpertQuery.ac";
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
			showMenu(2, 23);
		});
	</script>
	<form action="${ctx }/resource/emrgShltrSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					应急专家信息录入
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
				<th width="20%">专家姓名<span style="color: red">&nbsp;*</span></th>
				<td width="20%"><input name="dto.name" id="name" title="专家姓名"
					type="text" class="text" cssRule="require3" value='${expert.name }'>
				</td>
				<th width="20%">出生日期</th>
				<td width="20%"><input name="dto.birth" id="birth" title="出生日期"
					class="Wdate" value='${expert.birth }' type="text"
					onclick="WdatePicker({errDealMode:2 })">
				</td>
			</tr>
			<tr>
				<th>职称</th>
				<td><input name="dto.post" id="post" class="text" title="职称"
					type="text" value='${expert.post}'>
				</td>
				<th>性别</th>
				<td><select name='dto.sex2' id='sex2'>
						<option value=''>请选择</option>
						<option value='1'>男</option>
						<option value='0'>女</option>
				</select>
			</tr>
			<tr>
				<th>专家类别<span style="color: red">&nbsp;*</span></th>
				<td width="100"><select name="dto.expertype" id='expertype'>
						<option value=''>请选择</option>
						<c:forEach items="${expertTypeMap }" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
				</select>
				</td>
				<th>工作单位</th>
				<td><input name="dto.workunit" id="workunit" class="text"
					title="工作单位" type="text" value='${expert.workunit}'>
				</td>
			</tr>
			<tr>
				<th width="100">专业类别</th>
				<td colspan="3" width="100"><select name="dto.major" id='major'>
						<option value=''>请选择</option>
						<option value='1'>应急资源消防</option>
						<option value='2'>临床医学</option>
				</select></td>
			</tr>
			<tr>
				<th>专业特长描述</th>
				<td colspan="3"><textarea name="dto.proficient" id="proficient"
						title="专业特长描述" rows="2" cols="100">${expert.proficient}</textarea>
				</td>
			</tr>
			<thead>
				<tr>
					<td colspan="4" align="center">联系方式</td>
				</tr>
			</thead>
			<tr>
				<th>移动电话</th>
				<td><input name="dto.mobiletel" id="mobiletel" title="移动电话"
					type="text" class="text" value='${expert.mobiletel}'>
				</td>
				<th>电子邮箱</th>
				<td><input name="dto.email" id="email" class="text"
					title="电子邮箱" type="text" value='${expert.email}'>
				</td>
			</tr>
			<tr>
				<th>办公电话</th>
				<td><input name="dto.officetel" id="officetel" class="text"
					title="办公电话" type="text" value='${expert.officetel}'>
				</td>
				<th>传真</th>
				<td><input name="dto.fax" id="fax" class="text" title="传真"
					type="text" value='${expert.fax}'>
				</td>
			</tr>
			<tr>
				<th>家庭电话</th>
				<td><input name="dto.hometel" id="hometel" class="text"
					title="家庭电话" type="text" value='${expert.hometel}'>
				</td>
				<th>家庭住址</th>
				<td><input name="dto.homeaddr" id="homeaddr" class="text"
					title="家庭住址" type="text" size="40" style="width: 50%; HEIGHT: 20px"
					value='${expert.homeaddr}'>
				</td>
			</tr>
			<thead>
				<tr>
					<td colspan="4" align="center">工作信息</td>
				</tr>
			</thead>
			<tr>
				<th>身份证</th>
				<td><input name="dto.idno" id="idno" title="身份证" type="text"
					class="text" value='${expert.idno}'>
				</td>
				<th>健康状况</th>
				<td><input name="dto.health" id="health" class="text"
					title="健康状况" type="text" value='${expert.health}'>
				</td>
			</tr>
			<tr>
				<th>民族</th>
				<td><input name="dto.nationality" id="nationality" class="text"
					title="民族" type="text" value='${expert.nationality}'>
				</td>
				<th>籍贯</th>
				<td><input name="dto.native_" id="native_" class="text"
					title="籍贯" type="text" value='${expert.native_}'>
				</td>
			</tr>
			<tr>
				<th>政治面貌</th>
				<td><input name="dto.party" class="text" id="party"
					title="政治面貌" type="text" value='${expert.party}'>
				</td>

				<th>行政职务</th>
				<td width="120px"><input name="dto.headship" id='headship'
					class="text" type="text" value='${expert.headship}'>
				</td>

			</tr>
			<tr>
				<th>主管部门</th>
				<td><input name="dto.supervisaldept" id="supervisaldept"
					class="text" title="主管部门" type="text"
					value='${expert.supervisaldept}'>
				</td>
				<th>户籍所在地</th>
				<td><input name="dto.residentregistration"
					id="residentregistration" class="text" title="户籍所在地" type="text"
					value='${expert.residentregistration}'>
				</td>
			</tr>

			<tr>
				<th>通讯住址</th>
				<td colspan="3"><input name="dto.corresponding"
					style="width: 50%; HEIGHT: 20px" id="corresponding" class="text"
					title="通讯住址" type="text" value='${expert.corresponding}'>
				</td>
			</tr>
			<tr>
				<th>最高学历</th>
				<td><input name="dto.qualification" id="qualification"
					class="text" title="最高学历" type="text"
					value='${expert.qualification}'>
				</td>
				<th>毕业院校</th>
				<td><input name="dto.graduate" id="graduate" class="text"
					title="毕业院校" type="text" value='${expert.graduate}'>
				</td>
			</tr>
			<tr>
				<th>工作单位邮编</th>
				<td><input name="dto.postcode" class="text" id="postcode"
					title="工作单位邮编" type="text" value='${expert.postcode}'>
				</td>
				<th>参加工作时间</th>
				<td><input name="dto.jointime" class="text" id="jointime"
					title="参加工作时间" type="text" value='${expert.jointime}' class="Wdate"
					type="text" onclick="WdatePicker({errDealMode:2 })">
			</tr>
			<tr>
				<th>工作经历概述</th>
				<td colspan="3"><textarea name="dto.resume" id="resume"
						title="工作经历概述" rows="3" cols="100">${expert.resume}</textarea></td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="dto.notes" id="notes"
						title="备注" rows="3" cols="100">${expert.notes}</textarea></td>
			</tr>
		</table>
		<div class="ctrls_bar">
			<input type="hidden"
				name='experid' value='${expert.experid }' />
		</div>
	</form>
</body>
</html>