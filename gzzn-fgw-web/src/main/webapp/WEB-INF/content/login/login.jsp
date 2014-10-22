<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="UTF-8" />
<script src="${ctx}/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<link href="${ctx}/resources/css/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/css/login.css" rel="stylesheet" type="text/css" />
<title>XX管理系统登录</title>
	<script type="text/javascript">
	//<![CDATA[
		$(document).ready(function(){
			$("#loginName")[0].focus();
			$("#ActionMessage").addClass("hide");
			
			var obj = document.getElementById("ActionMessage");
			if("${not empty message}" == "true"){
				$("#ActionMessage").html("${message}").removeClass("hide");
			}else{
				$("#ActionMessage").html("").addClass("hide");
			}
			
			$("#submitbtn").click(function(){
				dosubmit();
			});
			
			$("#loginName,#password").keydown(function(event){
				if(event.keyCode == 13){
					dosubmit();
				}
			});
			
			function dosubmit(){
				if(check(0) && check(1)){
					document.forms[0].submit();
				}
			}
			
			function check(type){
				if(type == 0){
					if($("#loginName").val().length == 0){
						$("#ActionMessage").html("用户名不允许为空").removeClass("hide");
						$("#loginName")[0].focus();
						return false;
					}else{
						$("#ActionMessage").html("").addClass("hide");
					}
				}else if(type == 1){
					if($("#password").val().length == 0){
						$("#ActionMessage").html("密码不允许为空").removeClass("hide");
						$("#password")[0].focus();
						return false;
					}else{
						$("#ActionMessage").html("").addClass("hide");
					}
				}
				return true;
			}
		});
	//]]>
	</script>
</head>
<body>
<div id="LoginContainer">
    <img src="${ctx}/resources/image/login/login_bg_left.gif" class="loginBgImage"/>
    <div id="LoginMain">
        <div id="LoginLogo">
        	<span id="SystemTitle">广州智能XXXX系统v1.0</span>
        </div>
        <div id="LoginFormContainer">
            <form id="LoginForm" action="${ctx}/login" method="post">
            <p>
                <label for="TestUserName"><strong id="ForTestUserName">用户名:</strong></label>
                <input type="text" name="loginName" class="TxtInput" id="loginName"  />
            </p>
            <p>
                <label for="TestUserPWD"><strong id="ForTestUserPWD">密&nbsp;&nbsp;&nbsp;码:</strong></label>
                <input type="password" name="password" class="TxtInput" id="password" />
            </p>
            <p>
                <input type="button" id="submitbtn" value="登录" class="loginSubmit"/>
            </p>
            <br class="clear" />
            	<div id="ActionMessage" class="Error"></div>
            </form>
        </div>
    </div>
    <img src="${ctx}/resources/image/login/login_bg_right.gif" class="loginBgImage"/>
     <br class="clear" />
    <div id="shadow">
      <img src="${ctx}/resources/image/login/login_shadow_left.gif" class="loginBgImage"/>
      <div id="ShadowCenter">
        <center>
        <br/>
        </center>
        <center>
        <br/>
        </center>
      </div>
      <img src="${ctx}/resources/image/login/login_shadow_right.gif" class="loginBgImage"/>
    </div>
</div>
</body>
</html>