<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>广州市政府投资项目库申报管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="UTF-8" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/login.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/style.css"/>
	<script src="${ctx}/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">

	$(document).ready(function(){
		if (window != top){
			top.location.href = location.href; 
		}  
		$("#loginName,#loginPwd,#verfCode").keydown(function(event){
			if(event.keyCode == 13){
				dosubmit();
			}
		});
		resetTxtInput();
	});
	
	function dosubmit() {
		if (checkUserInfo("loginName", "用户名不允许为空！")
				&& checkUserInfo("loginPwd", "密码不允许为空！") 
				&& checkUserInfo("verfCode", "验证码不允许为空！")) {
			$.post(
					"${ctx}/login/login.json",
					{"loginName":$("#loginName").val(),"loginPwd":$("#loginPwd").val(),"verfCode":$("#verfCode").val()},
					function(data){
				var json = $.parseJSON(data);
				if(json.flag){
					if(json.msg=="密码不符合复杂度规范，请修改"){
						window.location.href="${ctx}/sys/genUserModPassEdit.ac?status=7";
					}else{
						//window.location.href="${ctx}/eds/info/main.ac";
						window.location.href="${ctx}/index/main.ac";
					}
				}else{
					$("#ActionMessage").removeClass("hide").html(json.msg);
					resetTxtInput();
				}
			});
		}
	}
	
	function regist() {
		window.location="${ctx}/sys/organization/ownerRegist.json";
		//var url = "${ctx}/sys/organization/ownerRegist.json";
		//var iWidth = 800;                          //弹出窗口的宽度;
	   // var iHeight = 600;                         //弹出窗口的高度;
	   // //获得窗口的垂直位置
	   // var iTop = (window.screen.availHeight-30-iHeight)/2;        
	  //  //获得窗口的水平位置
	  //  var iLeft = (window.screen.availWidth-10-iWidth)/2;           
		//window.open (url, 'newwindow', 'left='+iLeft+',top='+iTop+',width='+ iWidth +',height='+ iHeight +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
	}

	function doreset(){
		resetTxtInput();
		resetErrMsg();
	}
	function resetTxtInput(){
		$("#loginPwd,#verfCode").val("");
		$("#loginName").focus();
		refresh();
	}
	function resetErrMsg(){
		$("#ActionMessage").val("").addClass("hide");
	}

	//验证用户输入的信息是否为空，info：显示错误信息的对象；
	//userInfoId：用户需要验证的信息Id;errMsgTxt：信息错误时显示的文字
	function checkUserInfo(userInfoId, errMsgTxt) {
		var obj = document.getElementById(userInfoId);
		if (obj.value == null || obj.value == "") {
			$("#ActionMessage").html(errMsgTxt).removeClass("hide");
			obj.focus();
			return false;
		} else {
			$("#ActionMessage").html("").addClass("hide");
			return true;
		}
	}
	
	//刷新验证码
	function refresh() {
		//document.getElementById("imgCode").src= "./codeImage.ac?"+Math.random();
		document.getElementById("imgCode").src = "${ctx}/servlet/VerfCodeImageServlet?"+Math.random();
		//$("#imgCode").attr("src", "${ctx}/sys/organization/genVerfCodeImage.json?"+Math.random());
	}
</script>
</head>
  
<body>
	<div id="LoginContainer">
		<div class="leftCon"></div>
		<div class="rightCon">
			<ul>
				<li class="title1">${configObj["system.name.main"]}</li>
			</ul>
			<form id="LoginForm" >
			<ul>
				<li class="userInfo">用户名：<input type="text" class="TxtInput" id="loginName" name="loginName" value="" /></li>
				<li class="userInfo">密　码：<input type="password" class="TxtInput" id="loginPwd"  name="loginPwd" value="" /></li>
				<li class="userInfo">验证码：
					<input type="text"  class="TxtInput" style="width: 41px;vertical-align: top;height: 25px;" id="verfCode" name="verfCode"  value=""/>
					<img id="imgCode" title="点击更换" onclick="javascript:refresh();"  src="${ctx}/servlet/VerfCodeImageServlet" height="25" width="80"/>
				</li>
				
				<li class="userInfo">
					<input type="button" class="LoginBtnBg" value="确定" onclick="dosubmit();"/> 
					<input type="button" class="LoginBtnBg" value="取消" onclick="doreset()"/>&nbsp;&nbsp;
					<a href="#" onclick="javascript:regist()"/><font color="yellow">业主注册</font></a>
				</li>
				<li class="errMsg hide" id="ActionMessage"></li>
			</ul>
			</form>
			<ul>
				<li class="footTxt">${configObj["system.copyright"]}</li>
			</ul>
			<br/>
			<br/>
			<br/>
			<p><font color="red">下午5:30~6:00是系统维护期。</font></p>
		</div>
	</div>
</body>
</html>
