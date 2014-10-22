<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>项目月报管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<%
	SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
%>

<body>
<script type="text/javascript">
var windowview;
<!--
	$(document).ready(function() {
		showMenu(2,17);
		$("#xmybzt").val("${queryObj.xmybzt}");
		
		if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}

		$("#checkAll").click(function() {
			var tag = $(this).attr("checked");
			if (tag) {
				$(".subcheck").attr("checked", "true");
			} else {
				$(".subcheck").removeAttr("checked");
			}
		});
		
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/project/xmyb/edit.ac";
		});
		
		$("#delBtn").click(function(){
			var ids = "";
			$(".subcheck").each(function(){
				if($(this).attr("checked")){
					ids += "@" + $(this).val();
				}
			});
			if(ids.length == 0){
				mac.alert("请选择要删除的记录");
				return;
			}
			ids = ids.substring(1);
			//alert(ids);
			mac.confirm('<p>确认要删除已选中的记录吗?</p>', function(){
				window.location = "${ctx}/project/xmyb/delete.ac?id=" + ids;
			}, null);
		});

	});
//-->

function view(projectId){
	var url = "${ctx}/project/pjbaseinfo/view.json?id="+projectId+"&type=0";
	windowview = window.open (url, 'newwindow9', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
	windowview.focus();
}

function viewxmyb(id){
		var url = "${ctx}/project/xmyb/view.json?id="+id+"&type=0";
		var windowyb = window.open (url, 'newwindow11', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		windowyb.focus();
	}
</script>
	<form action="${ctx}/project/xmyb/list.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
			  <table class="topSearchTab">
				<tr>
					<th>项目名称：</th>
					<td><input type="text" class="text" name="queryObj.pjbaseinfo.projectname" value="${queryObj.pjbaseinfo.projectname}" /></td>
					<th>项目编码：</th>
					<td><input type="text" class="text" name="queryObj.pjbaseinfo.projectcode" value="${queryObj.pjbaseinfo.projectcode}" /></td>
					<th>月报内容：</th>
					<td><input type="text" class="text" name="queryObj.nr" value="${queryObj.nr}" /></td>
				</tr>
				<tr>
					<th>月报年份：</th>
					<td><input type="text" class="text" name="queryObj.nf" value="${queryObj.nf}" /></td>
					<th>月报月份：</th>
					<td><input type="text" class="text" name="queryObj.yf" value="${queryObj.yf}" /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
					<th>
						<c:if test="${objectMap['XMSB_XM_XMZTGL_VIEW']}">
						<input type="button" class="btn" id="queryBtn" value="查询" />
						</c:if>
					</th>
				</tr>
			  </table>
		</div>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">项目月报管理</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					    <span style="float: right;">     
					    	<c:if test="${objectMap['XMSB_XM_XMZTGL_ADD']}">
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
			            	</c:if>
			            	<%
			            		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){
			            	%>
			            	<c:if test="${objectMap['XMSB_XM_XMZTGL_DEL']}">
			            	 <input type="button" class="btn" id="delBtn" value="批量删除"/>     
			            	</c:if>
			            	<%
			            		}
			            	%>
		               	</span>   
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>

		<table class="list">
			<thead>
				<tr>
					<td width="5%"><input type="checkbox" id="checkAll"></td>
					<td width="5%">序号</td>
					<td width="20%">项目名称</td>
					<td width="17%">项目编码</td>
					<td width="20%">月报内容</td>
					<td width="7%">月报年份</td>
					<td width="7%">月报月份</td>
					<td width="7%">项目状态</td>
					<td width="12%">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" value="${obj.xmybId}" class="subcheck" /></td>
						<td>${page.size*page.current+status.index+1}&nbsp;</td>
						<td>
							<c:choose>
								<c:when test="${objectMap['XMSB_XM_XMZTGL_VIEW']}">
									<a href="javascript:view(${obj.pjbaseinfo.projectid})">${obj.pjbaseinfo.projectname}</a>
								</c:when>
								<c:otherwise>
									${obj.pjbaseinfo.projectname}
								</c:otherwise>
							</c:choose>
							
						</td>
						<td>${obj.pjbaseinfo.projectcode}&nbsp;</td>
						<td>${obj.nr}&nbsp;</td>
						<td>${obj.nf}&nbsp;</td>
						<td>${obj.yf}&nbsp;</td>
						<td>
							<c:choose>
								<c:when test="${obj.pjbaseinfo.pjstatus==0}">
									草稿
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==1}">
									待主管单位审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==2}">
									待各处室处长审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==3}">
									待各处室经办审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==4}">
									待各处室副处长审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==5}">
									待各处室处长确认审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==6}">
									待投资处处长审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==7}">
									待投资处经办审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==8}">
									待投资处副处长审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==9}">
									待投资处处长确认审核
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==10}">
									正常项目
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==11}">
									过期
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==12}">
									审核不通过
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==13}">
									转评中心评审
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==14}">
									研评中心评审中
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==15}">
									研评中心已评审通过
								</c:when>
								<c:when test="${obj.pjbaseinfo.pjstatus==16}">
									研评中心评审不通过
								</c:when>
							</c:choose>
						&nbsp;
						</td>
						<td>
							<c:if test="${objectMap['XMSB_XM_XMZTGL_VIEW']}">
							<a href="javascript:viewxmyb(${obj.xmybId})">查看</a>&nbsp;&nbsp;
							</c:if>
							<c:if test="${obj.xmybzt==0}">
								<c:if test="${objectMap['XMSB_XM_XMZTGL_MOD']}">
									<a href="${ctx}/project/xmyb/edit.ac?id=${obj.xmybId}">编辑</a>&nbsp;&nbsp;
								</c:if>
								<c:if test="${objectMap['XMSB_XM_XMZTGL_DEL']}">
									<a href="javascript:deleteAction('${ctx}/project/xmyb/delete.ac?id=${obj.xmybId}')">删除</a>
								</c:if>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="9" class="line2" style="text-align: right;"><%@include
							file="../../changePage.jsp"%></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
