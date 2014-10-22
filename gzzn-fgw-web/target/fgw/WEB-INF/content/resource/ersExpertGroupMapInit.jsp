<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急专家组与专家</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
	<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
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
		$("#dutyCode").attr("value", "${editObj.dutyCode }");
		$("#expertGroupId").attr("value", "${editObj.expertGroupId }");
		$("#experid").attr("value", "${editObj.experid }");
	}
</script>
<script type="text/javascript">
	$(function() {
	
		$("#remark").charCount({ 
			allowed: 500
		}); 
		$("#signupForm").validate({
			event : "blur",
			rules : {
			
				"dto.dataFromCode" : {
					byteMaxLength :9
				},
				"dto.experid" : {
					required : true
				},
				"dto.expertGroupId" : {
					required : true
				},
				"dto.remark" : {
					byteMaxLength : 500
				}
			},
			messages : {
				"dto.dataFromCode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.remark" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.experid" : {
					required : "必选字段"
				},
				"dto.expertGroupId" : {
					required : "必选字段"
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
													url : '${ctx }/resource/ersExpertGroupMapSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac.alert(data.info);
															window.location.href = "${ctx }/resource/ersExpertGroupMapQuery.ac";
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
	<form action="${ctx }/resource/ersExpertGroupMapSave.ac" method="post"
		id='signupForm'>

		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					专家组与专家信息录入
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
			<tr>
				<th>专家名称</th>
				<td><select id='experid' name='dto.experid'>
						<option value="">请选择</option>
						<c:forEach items="${expertList }" var='expert'>
							<option value="${expert.experid }">${expert.name }</option>
						</c:forEach>
				</select><span>&nbsp;*</span></td>
					<th>专家组名称</th>
				<td><select id='expertGroupId' name='dto.expertGroupId'>
						<option value="">请选择</option>
						<c:forEach items="${expertGroupList }" var='expertGroup'>
							<option value="${expertGroup.expertGroupId }">${expertGroup.expertGroupName
								}</option>
						</c:forEach>
				</select><span>&nbsp;*</span></td>
			</tr>
			<tr>
				<th>职务代码</th>
				<td><select id='dutyCode' name='dto.dutyCode'>
						<option value="">请选择</option>
						<c:forEach items="${dutyCodeMap }" var='map'>
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
				</select></td>
				<th>单位编码</th>
				<td><input type="text" class="text" name="dto.dataFromCode"
					id='dataFromCode' value='${editObj.dataFromCode }' />
				</td>
			</tr>
			<tr>
				<th>发布时间</th>
				<td><input type="text" value="${editObj.updateTime }"
					name="dto.updateTime" id='updateTime'
					onclick="WdatePicker({errDealMode:2})" class="Wdate" />
				</td>
			</tr>

			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="dto.remark" cols="100" rows="3"
						id='remark'>${editObj.remark}</textarea><span>&nbsp;*</span></td>
			</tr>
		</table>
		<div class="ctrls_bar">
			 <input type="hidden"
				name='expertGroupMapId' value='${editObj.expertGroupMapId }' />
		</div>
	</form>


</body>
</html>