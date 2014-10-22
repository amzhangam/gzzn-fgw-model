<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null){
		ex = exception;
	}
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		
	String exMsg = ex.getMessage();

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(exMsg, ex);
%>
<c:set var="exMsg2" value="<%=exMsg%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>系统出错提示页面</title> 
    <style type="text/css">
     html{
     	font: .75em/1.5 arial, sans-serif;
     }
     body { 
	    background-color:#E6E6E6;
     }
     h1 {
		font-size: 1.5em;
		margin: 1.083em 0 0;
		color: #333;
	}
	h1 img {
		float: right;
		margin: -30px 0 0 40px;
		border: 0;
		border-image: initial;
	}
     h2 {
		font-size: 1.083em;
		margin: 1.083em 0 0;
	}
	ul {
		font-size: 1.083em;
		line-height: 1.47em;
		margin: 0 0 0 1.5em;
		padding: 0;
	} 
    #shadow{
       	margin:120px auto;  
        background-color:#FFF;
        width:60%;
        border-top: 2px solid #CCC;
        border-left: 2px solid #CCC;
        -webkit-box-shadow: 3px 3px 4px #333232;
        box-shadow: 3px 3px 4px #333232;
		padding: 1.083em 2em 3.5em;
      }
   </style>
     
  </head>
 <body>
   	<div id="shadow">
		<h1>
			<img src="${ctx}/resources/images/error.jpg" />
			 很抱歉，系统内部出错了！将于&nbsp;<span id="timer" style="font-size:20pt;color:green">5</span>&nbsp;秒后自动返回上一页面
		</h1>
		<h2 style="color:red;">原因：</h2>
		<ul>
			<li style="color:red;">
				<c:choose>
					<c:when test="${exMsg2 ne ''}"> ${exMsg2} </c:when>
					<c:otherwise>未知错误</c:otherwise>
				</c:choose>
			</li>
		</ul>
		
		<h2>建议：</h2>
		<ul>
			 <li>稍侯片刻再重新操作</li>
			 <li>请联系系统管理员</li>
			 <li>
			 	<a href='javascript:history.go(-1)'>返回上一页面</a>
			 </li>
		</ul>
    </div> 
</body>
<script language="javascript">
   var maxtime;
   maxtime = 4;
  /*if(window.name == '' || window.name == -1){
     maxtime = 60*60;
   }else{
      maxtime = window.name;
   }*/
   
   setInterval('CountDown()',1000);
   
   function CountDown(){
     if(maxtime>=0){
          //minutes = Math.floor(maxtime/60);
           seconds = Math.floor(maxtime%60);
           //msg = "还有"+minutes+"分"+seconds+"秒";
           msg =  seconds;
           document.all["timer"].innerHTML = msg;
           --maxtime;
          //window.name =  maxtime; 
      }else{
           clearInterval(timer);
           //alert("time over");
           //页面重定向至前一个页面
           history.go(-1); 
      }
   }

</script>
</html>

