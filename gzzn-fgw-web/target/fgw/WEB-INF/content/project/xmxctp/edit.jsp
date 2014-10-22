<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
<c:if test="${not empty obj.investPlanid}" var="result">
	编辑项目图片长廊
</c:if>
<c:if test="${!result}">
	新增项目图片长廊
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
	   $(document).ready(function() {
			showMenu(2);
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.investPlanYear":{   
							             required: true
							         },
							         "obj.investPlanName":{   
							             required: true,
							             maxlength: 25
							         },
							         "files":{   
							             required: true,
							             byteMaxFileNameLength:50
							         }
							    },
							    messages: {
							    	"files" : {
										byteMaxFileNameLength : "请选择一个文件名长度最多是 {0} 的文件 "
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
				window.location = "${ctx}/project/pjplanyear/list.ac";
			});
			
		});
	
	//-->
</script>

	<form id="editForm" action="${ctx}/project/pjplanyear/save.ac" method="post" enctype="multipart/form-data">
		<input type="hidden" name="obj.investPlanid" value="${obj.investPlanid}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.investPlanid}" var="result">
						编辑项目图片长廊
					</c:if>
					<c:if test="${!result}">
						新增项目图片长廊
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
					<th>投资计划终稿年度<font class="msg">*</font></th>
					<td>
						<input type="text" class="text Wdate" style="width:55px" name="obj.investPlanYear" id="investPlanYear" value="${obj.investPlanYear}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:false,readOnly:true})"/>
					</td>
				</tr>
				<tr>
					<th>投资计划终稿名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.investPlanName" id="investPlanName" value="${obj.investPlanName}" />
					</td>
				</tr>
				<tr>
					<th>投资计划终稿文件地址<font class="msg">*</font></th>
					<td>
						<input type="file" style="width:450px;" name="files" id="files" value=""/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>