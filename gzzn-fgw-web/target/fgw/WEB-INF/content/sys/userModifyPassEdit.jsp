<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>用户-修改密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function(){
		jQuery.validator.addMethod("checkPass",function(value){
			if(value==$("#oldPass").val()){
				return false;
			}
			return true;
		},"新密码无效");
		$("#myform").validate({
			event:"blur",
			onkeyup:false,
			rules:{           //定义验证规则,其中属性名为表单的name属性  
				oldPass:{   
		            required: true,
		            remote:{
	                 	type:"POST",
	                 	dataType:"JSON",
	                 	url:"./userCheckPass.json",
	                 	data:{
	                 		userId:function(){
	                 			return $("#userId").val();
	                 		},
	                 		time:new Date().getTime()
	                 	}
                 	}
		         },
		         newPass:{
		         	required: true,
		         	checkPass:true,
		         	byteRangeLength: [6,20],
	            	complexPwd:true
		         },
		         confirmPass:{
		         	required: true,
		         	equalTo: "#newPass"
		         }
		     },  
		     messages: {       //自定义验证消息  
		     	oldPass:{
		        	remote:"旧密码输入错误！"
		     	}
		     }   	
		});
	});
	   $(document).ready(function() {
	   		var status=$("#status").val();
			if(status=="7"){
				showMenu(7, 71);
			}else{
				showMenu(6, 61);
			}
			//重置表单
			$("#resetBtn").click(function() {
   			});
   			//返回
			$("#backBtn").click(function() {
				window.location = "./sysUserQuery.ac";
			});
			$("#saveBtn").click(function() {
				if($("#myform").valid()){
					$.ajax({
				    	type:"POST",
				    	url:"userSavePassword.json",
				    	data:{
				    		newPass:$("#newPass").val(),
				    		loginPwd:$("#confirmPass").val(),
				    		userId:$("#userId").val()
				    	},
				    	success:function(data){
				    		if(data.success){
				    			alert(data.info);
				    		}else{
				    			alert(data.info);
				    		}
				    	},
				    	error:function() { 
				    		alert("系统响应异常！");
						}, 
				    	dataType:'json'
					});
				}
			});
		});
		
	
	//-->
</script>
</head>
<body>
	<form id="myform" method="post">
		<input type="hidden" name="userId" id="userId" value="${dto.userId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">用户-修改密码<br></td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" id="saveBtn" value="保存" /> 
						 <input type="reset" class="btn" id="resetBtn" value="重置" /> 
						 <input type="hidden" id="status" name="status" 
						 disabled="disabled" value="${status}">
						</span>
					</div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="editDiv" style="margin-bottom: 15px;">
			<table class="editTab" id="editTab">
				<tr>
					<th>旧密码<font class="msg">*</font></th>
					<td><input type="password" name="oldPass" id="oldPass"/></td>
				</tr>
				<tr>
					<th>新密码<font class="msg">*</font></th>
					<td>
						<input type="password" name="newPass" id="newPass"/>
						<font color="green">密码长度不少于6位，且由A-Z、a-z、0-9、特殊字符【!{}[],.<>@$%&^()_+=】中的至少两项组成</font>
					</td>
				</tr>
				<tr>
					<th>确认密码<font class="msg">*</font></th>
					<td>
						<input type="password" name="confirmPass" id="confirmPass"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>