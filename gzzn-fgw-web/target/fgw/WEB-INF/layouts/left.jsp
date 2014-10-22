<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<% String flag=(String)request.getAttribute("flag"); %>
<% String num=(String)request.getAttribute("num"); %>
<%
			SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		%>
<html>
<script type="text/javascript">
	$(document).ready(function(){
		//给子菜单定义样式
		$("td[class^=subMod]").css({
			"padding-left":"23px",
			"border-bottom":"#ACB8D6 1px solid",
			"display":"none"
		});
		
		subModule("210");
	});
	
	function showMenu(menu,subMenu){
		selectMenu(menu);
		subModule(subMenu);
	}

	function selectMenu(order){
		for(var i=1;i<13;i++){
			if(i == order){
				$("#menuImg" +i).attr("src","${ctx}/resources/images/lefticon/" + i + "_select.gif");
				$("#menu" +i).removeClass("hide");
			}else{
				$("#menuImg" +i).attr("src","${ctx}/resources/images/lefticon/" + i + "_unselect.gif");
				$("#menu" +i).addClass("hide");
			}
		}
		if(order+1<7){
			$("#menuImg" +(order+1)).attr("src","${ctx}/resources/images/lefticon/" + (order+1) + "_next.gif");
		}
	}
	
	function subModule(num){
		var className = "subMod"+ num;
		if($("."+className).css("display")=="none"){
			$("."+className).css("display","block");
		}else{
			$("."+className).css("display","none");
		}
	}
	

</script>
</head>
<body>
<table width="187px" border="0" cellspacing="0" cellpadding="0" height="100%">
<%
if(user!=null){
%>
  <tr>
    <td width="33" valign="top" bgcolor="#9FD2FF">
		<div class="lefts" style=" width:100%;height:750px;">
			<div>		  
				<a href="javascript:selectMenu(2);" title="项目库申报"><img id="menuImg2" src="${ctx}/resources/images/lefticon/2_next.gif" alt="项目库申报" width="44" height="55" border="0"></a>
			</div>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK']}">
			<div>
				<a href="javascript:selectMenu(12);" title="项目信息报送" ><img id="menuImg12" src="${ctx}/resources/images/lefticon/12_unselect.gif"  alt="项目信息报送" width="44" height="55" border="0"></a>
			</div>
		</c:if>
		<c:if test="${moduleMap['XMSB_BB']}">
			<div>
				<a href="javascript:selectMenu(3);" title="报表管理" ><img id="menuImg3" src="${ctx}/resources/images/lefticon/3_unselect.gif"  alt="报表管理" width="44" height="55" border="0"></a>
			</div>
		</c:if>
		
		 <c:if test="${moduleMap['XMSB_XT']}">
			<div>
				<a href="javascript:selectMenu(6);" title="系统管理" ><img id="menuImg6" src="${ctx}/resources/images/lefticon/6_unselect.gif" alt="系统管理" width="44" height="55" border="0" ></a>
			</div>
		</c:if>
		<c:if test="${moduleMap['XMSB_DX']}">
			<div>
				<a href="javascript:selectMenu(1);" title="短信管理"><img id="menuImg1" src="${ctx}/resources/images/lefticon/1_select.gif"  alt="短信管理" width="44" height="55" border="0"></a>
			</div>
		</c:if>
		<div>
			<a href="javascript:selectMenu(7);" title="用户信息管理" ><img id="menuImg7" src="${ctx}/resources/images/lefticon/7_unselect.gif" alt="用户信息管理" width="44" height="55" border="0" ></a>
		</div>
		</div>
    </td>
    <td valign="top"  bgcolor="#9FD2FF">
    <c:if test="${moduleMap['XMSB_DX']}">
    	<%@include file="left_1_dxgl.jsp"%>
    </c:if>
    	<%@include file="left_2_xmgl.jsp"%>
    <c:if test="${moduleMap['XMSB_BB']}">
    	<%@include file="left_3_bbgl.jsp"%>
    </c:if>
     <c:if test="${moduleMap['XMSB_XT']}">
    	<%@include file="left_4_xtgl.jsp"%>
    </c:if>
     <c:if test="${moduleMap['XMSB_XM_QXXMQK']}">
    	<%@include file="left_6_xxbs.jsp"%>
    </c:if>
    <%@include file="left_5_user.jsp"%>
    </td>
  </tr>
<%
}
%>
</table>
</body>

</html>
