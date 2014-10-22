<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急资源类别维护</title>
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

<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		$("#catalogue").change(function(){
			if($(this).val()!=""){
				$("#parentClassifyName").attr("disabled",false);
				$("#displayId").css("display","");
				$("#parentClassifyId").attr("value","");
				$("#parentClassifyName").attr("value","");
				initMyTree("parentClassify","getParentClassifyTree.json?classifyId=${editObj.classifyId }&catalogue="+$(this).val());
			}else{
				$("#displayId").css("display","none");
				$("#parentClassifyId").attr("value","");
				$("#parentClassifyName").attr("value","");
				$("#parentClassifyName").attr("disabled",true);
			}
		});
		if('${editObj.catalogue}'!=''){
			$("#displayId").css("display","");
			initMyTree("parentClassify","getParentClassifyTree.json?classifyId=${editObj.classifyId }&catalogue=${editObj.catalogue}");
		}

		$("#remark").charCount({
			allowed : 100
		});
		$("#resetBtn").click(function(){
			window.location.reload(true);
		});
	});
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.classifyName" : {
					required : true,
					byteMaxLength : 100
				},
				"dto.catalogue" : {
					required : true
				},
				"dto.classifyCode" : {
					byteMaxLength : 10
				},
				"dto.remark" : {
					byteMaxLength : 100
				}

			},
			messages : {
				"dto.classifyName" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.catalogue" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.classifyCode" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.remark" : {
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
													url : '${ctx }/resource/codeEventResClassifySave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac
																	.alert(data.info);
															window.location.href = "${ctx }/resource/codeEventResClassifyQuery.ac";
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
		$("#catalogue").attr('value', '${editObj.catalogue}');
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
	<form action="${ctx }/resource/codeEventResClassifySave.ac"
		method="post" id='signupForm'>
		<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				应急资源类别维护
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
					<th>分类名称<span style="color: red">&nbsp;*</span></th>
					<td><input type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" /></td>
					<th>分类目录<span style="color: red">&nbsp;*</span></th>
					<td><select id='catalogue' name='dto.catalogue' id='catalogue'>
							<option value="">请选择</option>
							<c:forEach items="${catalogueMap }" var='items'>
								<option value="${items.key }">${items.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr style="display: none;" id='displayId'>
					<th>上级分类名称</th>
					<td colspan="3"><input type="hidden" class="text" name="dto.parentClassifyId" 
						value="${editObj.parentClassifyId }" id='parentClassifyId' /> <input
						type="text" class="text" name="dto.parentClassifyName"
						value="${editObj.parentClassifyName}" readonly="readonly" title="由分类目录影响选择内容"
						id="parentClassifyName" />
					</td>
					

				</tr>
				<tr>
				<th>分类编码</th>
					<td colspan="3"><input type="text" class="text" name="dto.classifyCode"
						id='classifyCode' value='${editObj.classifyCode  }' /></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.remark" cols="100"
							rows="3" id='remark'>${editObj.remark}</textarea></td>
				</tr>
		</table>
		<div class="ctrls_bar">
			<input type="hidden"
				name='classifyId' value='${editObj.classifyId }' />
		</div>
	</form>
</body>
</html>