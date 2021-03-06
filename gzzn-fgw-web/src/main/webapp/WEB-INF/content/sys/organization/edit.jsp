<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<title>
<c:if test="${not empty obj.organizationId}" var="result">
	编辑单位信息
</c:if>
<c:if test="${!result}">
	新增单位信息
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>

<script type="text/javascript">
//<!--
	   $(document).ready(function() {
	   		showMenu(6,63);
	   		
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.organizationName":{   
							             required: true,
							             byteMaxLength:50
							         },
							         "obj.adress":{   
							             required: true,
							             byteMaxLength:200
							         }
							    },
							    submitHandler: function(form){
							    	form.submit();
							    }		 	    
							});
			
			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
   			});
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/organization/list.ac";
			});
		});
		
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/organization/save.ac"  method="post">
		<input type="hidden" name="obj.organizationId" value="${obj.organizationId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.organizationId}" var="result">
						编辑单位信息
					</c:if>
					<c:if test="${!result}">
						新增单位信息
					</c:if>
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="submit" class="btn" id="saveBtn" value="保存" /> 
						 <input type="reset" class="btn" id="resetBtn" value="重置" /> 
						 <input type="button" class="btn" id="backBtn" value="返回" />
						</span>
					</div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="editDiv">
			<table class="editTab" id="editTab">
				<tr>
					<th>主管单位名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.organizationName" id="organizationName" value="${obj.organizationName}" />
					</td>
				</tr>
				<tr>
					<th>主管单位地址<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsaddress" id="workunitsaddress" value="${obj.workunitsaddress}" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>