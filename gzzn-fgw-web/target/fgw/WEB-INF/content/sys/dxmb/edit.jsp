<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
<c:if test="${not empty obj.dxmbId}" var="result">
	编辑短信模板信息
</c:if>
<c:if test="${!result}">
	新增短信模板信息
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
</head>
<body>

<script type="text/javascript">
//<!--
	   $(document).ready(function() {
			showMenu(1,65);
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.mbmc":{   
							             required: true,
							             byteMaxLength:100
							         },
							         "obj.mb":{   
							             byteMaxLength:100
							         }
							    },
							   /** messages: {
							    	 "obj.mbmc":{   
							             byteMaxLength:"不能全为空格且字数最多是50"
							         },
							         "obj.mb":{
							             byteMaxLength:"不能全为空格且字数最多是50"
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
				window.location = "${ctx}/sys/dxmb/list.ac";
			});
			
		});
		
	
	//-->
	
	function checkRepeat(){
		$.post(
		    	"${ctx}/sys/dxmb/checkRepeat.json",
		    	{"mbmc":$("#mbmc").val(),"id":$("#dxmbId").val()},
		    	function(data){
		    		var json = $.parseJSON(data);
					if(json.flag){
						$("#editForm").attr("action","${ctx}/sys/dxmb/save.ac");
						$("#editForm").submit();
					}
					else{
						alert(json.msg);
					}
		    	}
	    	);
	}
</script>

	<form id="editForm" action="${ctx}/sys/dxmb/save.ac" method="post">
		<input type="hidden" name="obj.dxmbId" id="dxmbId" value="${obj.dxmbId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.dxmbId}" var="result">
						编辑短信模板信息
					</c:if>
					<c:if test="${!result}">
						新增短信模板信息
					</c:if>
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" id="saveBtn" value="保存" onclick="checkRepeat()"/> 
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
					<th>短信模板名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.mbmc" id="mbmc" value="${obj.mbmc}" />
					</td>
				</tr>
				<tr>
					<th>短信模板内容</th>
					<td>
						<textarea style="width:70%;height:auto;" rows="5" name="obj.mb" id="mb">${obj.mb}</textarea>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>