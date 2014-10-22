<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急物资单位信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="应急物资储备库-应急物资单位信息">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#repertoryid").attr('value', '${editObj.repertoryid}');
		$("#materialId").attr('value', '${editObj.materialId}');
		$("#orgid").attr('value', '${editObj.orgid}');
	}
</script>
<script type="text/javascript">
	$(function() {
		
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.repertoryid" : {
					required : true
				},
				"dto.materialId" : {
					required : true
				},
				"dto.orgid" : {
					required : true
				},
				"dto.notes" : {
					byteMaxLength : 200
				}
			},
			messages : {
				"dto.repertoryid" : {
					required : "必选字段"
				},
				"dto.materialId" : {
					required : "必选字段"
				},
				"dto.orgid" : {
					required : "必选字段"
				},
				"dto.notes" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
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
													url : '${ctx }/resource/ersMtrlRepSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/ersMtrlRepQuery.ac";
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
	<form action="${ctx }/resource/ersMtrlRepSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					应急物资单位信息录入
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
				<th width="30%">应急物资储备库<span style="color: red">&nbsp;*</span></th>
				<td width="70%" colspan="3"><select id='repertoryid'
					name='dto.repertoryid'>
						<option value="">请选择</option>
						<c:forEach items="${repertoryList }" var='repertory'>
							<option value="${repertory.repertoryid }">${repertory.name
								}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th>应急物资保障<span style="color: red">&nbsp;*</span></th>
				<td colspan="3"><select id='materialId' name='dto.materialId'>
						<option value="">请选择</option>
						<c:forEach items="${emrgMtrlList }" var='emrgMtr'>
							<option value="${emrgMtr.materialId }">${emrgMtr.name }</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th>应急机构<span style="color: red">&nbsp;*</span></th>
				<td colspan="3"><select id='orgid' name='dto.orgid'>
						<option value="">请选择</option>
						<c:forEach items="${emrgOrgList }" var='emrgOrg'>
							<option value="${emrgOrg.orgid }">${emrgOrg.orgname }</option>
						</c:forEach>
				</select>
				</td>
			</tr>

			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="dto.notes" id="notes"
						title="备注" rows="2" cols="80">${editObj.notes}</textarea>
			</tr>
		</table>
		<div class="ctrls_bar">
		<input type="hidden" value='${editObj.mtrlRepId }'
				name='mtrlRepId'>
		</div>
	</form>
</body>
</html>