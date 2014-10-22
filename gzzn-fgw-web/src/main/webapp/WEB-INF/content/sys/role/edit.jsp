<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
<c:if test="${not empty obj.roleId}" var="result">
	编辑角色信息
</c:if>
<c:if test="${!result}">
	新增角色信息
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
</head>
<body>

<script type="text/javascript">
//<!--
	   $(document).ready(function() {
			showMenu(6,65);
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.roleName":{   
							             required: true,
							             byteMaxLength:20
							         },
							         "obj.roleDesc":{   
							             byteMaxLength:50
							         }
							    },
							   /** messages: {
							    	 "obj.roleName":{   
							             byteMaxLength:"不能全为空格且字数最多是10"
							         },
							         "obj.roleDesc":{
							             byteMaxLength:"不能全为空格且字数最多是25"
							         }
							    },*/
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
				window.location = "${ctx}/sys/role/list.ac";
			});
			
		});
		
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/role/save.ac" method="post">
		<input type="hidden" name="obj.roleId" value="${obj.roleId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.roleId}" var="result">
						编辑角色信息
					</c:if>
					<c:if test="${!result}">
						新增角色信息
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
					<th>角色名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.roleName" id="roleName" value="${obj.roleName}" />
					</td>
				</tr>
				<tr>
					<th>角色描述</th>
					<td>
						<textarea style="width:70%;height:auto;" rows="5" name="obj.roleDesc" id="roleDesc">${obj.roleDesc}</textarea>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>