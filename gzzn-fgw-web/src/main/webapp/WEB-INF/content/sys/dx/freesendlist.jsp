<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>直接发送短信</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
</head>
<body>
	<script type="text/javascript">
		//<!--
		var setting = {
				check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onCheckBoxCheck
				}
			};
			
		$(document).ready(function() {
			showMenu(1, 13);
			checkAllBox();//复选框的选中问题
			$("#dxmb").val("${dxmb}");
			

			$("#dxmb").change(function() {
				if ($("#dxmb").val() != "") {
					$.post("${ctx}/sys/dx/getSfnr.json", {
						"dxmbId" : $("#dxmb").val()
					}, function(data) {
						var json = $.parseJSON(data);
						$("#dxsfnr").val(json.msg);
					});
				}else{
					$("#dxsfnr").val("");
				}
			});
		});
		//-->   

		function checkLxr() {
			var ids = $("#telmobile").val();
			
			if (ids.length == 0) {
				mac.alert("请输入要发送的手机号码");
				return;
			}
			var sfnr = $("#dxsfnr").val();
			if(sfnr==""||(sfnr.length>0 && /^\s+$/.test(sfnr))){//验证字符不能全为空格
				mac.alert("短信内容不能为空且不能全为空格");
				return;
			}
			var length = sfnr.replace(/[^\x00-\xff]/g, "**").length;
			if(length>600){
				mac.alert("短信内容不能超过300个汉字");
				return;
			}
			//alert(ids);
			mac.confirm('<p>确认要发送短信给上述号码吗?</p>', function() {
				$.post("${ctx}/sys/dx/freeSend.json", {
					"id" : ids,
					"sfnr" : sfnr
				}, function(data) {
					var json = $.parseJSON(data);
					if (json.flag) {
						alert(json.msg);
						window.location = "${ctx}/sys/dx/list.ac";
					} else {
						alert(json.msg);
					}
				});
			}, null);
		}
	</script>

	<form action="" method="post" id="sendForm">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
			<table class="editTab" id="editTab">
				<tr>
					<td style="background-color:#EDF6FF;width: 15%;">短信内容<font color="red">(限制在300字以内)</font>：</td>
					<td style="background-color:#EDF6FF;width: 65%;">
						请选择短信模板： 
						<select style="width:150px;margin-top:5px;" name="dxmb" id="dxmb">
							<option value="">==请选择==</option>
							<c:forEach items="${list}" var="obj" varStatus="status">
								<option value="${obj.dxmbId}">${obj.mbmc}</option>
							</c:forEach>
						</select>
						<br/> 
						<textarea name="dxsfnr" id="dxsfnr" style="width:95%">${dxsfnr}</textarea>
					</td>
				</tr>
				<tr>
					<td style="background-color:#EDF6FF;width: 15%;">接收的手机号码<font color="red">(号码之间用逗号隔开)</font>：</td>
					<td style="background-color:#EDF6FF;width: 65%;">
						<textarea name="telmobile" id="telmobile" style="width:95%">${telmobile}</textarea>
					</td>
				</tr>
			</table>
			<p align="center" style="background-color:#EDF6FF">
			<br/>
				<input type="button" class="btn" id="saveBtn" value="发送" onclick="checkLxr()" />
						&nbsp;&nbsp;<input type="reset" class="btn" id="resetBtn" value="重置" />
			<br/>
			<br/>
			</p>
		 </div>
	</form>
</body>
</html>
