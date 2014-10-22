<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<style type="text/css">
.mainLeft {
	width: 50%;
	float: left;
}
.mainRight {
	width: 50%;
	float: right;
}
.divShowInfo{
	margin: 3px 8px 8px 18px;
	padding-bottom: 10px;
	border: 1px solid #85B0E3;
	_height: 600px;min-height: 80px;
}
.divShowInfo p{
	background: #D3E4FF;
	border-bottom: 1px solid #85B0E3;
	height: 28px;
	line-height: 28px;
	margin-bottom: 5px;
	padding-left: 10px;
	font-weight: bold;
	color: #002D5F;
}
.moreInfo {float: right;padding-right: 10px;}
.moreInfo a {font-weight: normal;font-size: 12px;text-decoration: none;}
.divShowInfo table{
	width: 100%;
}

.divShowInfo table tr{
	height: 24px;
	line-height: 24px;
	border-bottom: 1px dotted #d6d6d6;
}

.divShowInfo table th{
	padding-left: 10px;
	border-right: 1px dotted #d6d6d6;
}

.divShowInfo table td{
	padding-left: 10px;
	border-right: 1px dotted #d6d6d6;
}
.welComeDiv,.welComeFloatDiv{
	padding-left: 40px;
	font-size: 20px;
	font-weight: bold;
	font-family: '微软雅黑','宋体';
	letter-spacing:5px; 
	color: BLACK;
}
.welComeFloatDiv{
	z-index: 1; 
	position: relative;
	top: -40px;
	left:-2px;
	color: #33CCFF;
}
.welComeDiv2{
	padding-left: 40px;
	font-size: 15px;
	font-weight: bold;
	font-family: '微软雅黑','宋体';
	letter-spacing:5px; 
	color: #33CCFF;
}

</style>
</head>
<body id="mbody">
<script type="text/javascript">
//<!--
 	
	$(document).ready(function() {
		//showMenu(2,66);
		selectMenu(2);
		var img = document.getElementById("imgObj");
		var mainContainer = document.getElementById("mainContainer");
	    var marginTopNum = mainContainer.offsetHeight - img.height;
	    img.style.marginTop = (marginTopNum/2)  + "px";
	});

	//-->

function goxmps(projectcode,projectname,declarerid,contacttel,projectcontent,operationlogRecId){
	window.location = "${ctx}/index/toDoWork.ac?id="+operationlogRecId+"&pageName=main";
	var url = "http://gsps.gzplan.gov.cn/project/edit.do?projectCode="+projectcode
			+"&projectName="+encodeURI(projectname)
			+"&projectContact="+encodeURI(declarerid)
			+"&projectContent="+encodeURI(projectcontent)
			+"&contactTel="+contacttel;
	window.open (url, 'newwindow', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
	
	//$.get("http://gsps.gzplan.gov.cn/api/fgw/addProject.do", 
    //        {
	//	"projectCode":projectcode,
	//		"projectName":URLEncoder.encode(URLEncoder.encode(projectname,"UTF-8")),
	//		"projectContact":URLEncoder.encode(URLEncoder.encode(declarerid,"UTF-8")),
	//		"contactTel":contacttel
	//	}, 
   //         function (data) {
   //             alert(data); 
   //             
   //     });
	
	
}
	
</script>
<!--a href="javascript:goxmps('222','基础设施重建','石百川','22225555','在南沙基乙烯化工厂',9293)" >点我吧地方</a-->
		<%-- <c:if test="${sessionLoginUser.userType==1 || sessionLoginUser.userType==2}"  var="result">
			<div class="divShowInfo">
				<p>
					待办工作【标题、发送单位、发送时间】<span class="moreInfo"> <a href="${ctx}/index/list.ac?sysParams.read=0">更多&gt;&gt;</a> </span>
				</p>
				<table>
					 <c:forEach items="${listDoWork}" var="obj" varStatus="status"> 
						<tr>
							<td width="50%">${status.index+1}、<a href="javascript:goxmps('${obj.pjbaseinfo.projectcode}','${obj.pjbaseinfo.projectname}','${obj.pjbaseinfo.declarerid}','${obj.pjbaseinfo.contacttel}','${obj.pjbaseinfo.projectcontent}',${obj.operationlogRecId})" >${obj.operationContent}</a></td>
							<td width="35%">${obj.sysOrganization.organizationName}</td>
							<td width="15%"><fmt:formatDate value="${obj.operationDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="divShowInfo">
				<p>
					已办工作【标题、发送单位、发送时间、读取用户、读取时间】<span class="moreInfo"> <a href="${ctx}/index/list.ac?sysParams.read=1">更多&gt;&gt;</a> </span>
				</p>
				<table>
					 <c:forEach items="${listHasDoWork}" var="obj" varStatus="status"> 
						<tr>
							<td width="30%">${status.index+1}、<a href="javascript:goxmps('${obj.pjbaseinfo.projectcode}','${obj.pjbaseinfo.projectname}','${obj.pjbaseinfo.declarerid}','${obj.pjbaseinfo.contacttel}','${obj.pjbaseinfo.projectcontent}',${obj.operationlogRecId})" >${obj.operationContent}</a></td>
							<td width="20%">${obj.sysOrganization.organizationName}</td>
							<td width="15%"><fmt:formatDate value="${obj.operationDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td width="10%">${obj.readUserObj.userName}</td>
							<td width="15%"><fmt:formatDate value="${obj.readtime}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
		<c:if test="${!result}"> --%>
			    <div  id="imgDiv" align="center">
			        <img id="imgObj"   src="${ctx}/resources/images/welcome.png"  border="0">
			    </div>
		<%-- </c:if> --%>

</body>
</html>
