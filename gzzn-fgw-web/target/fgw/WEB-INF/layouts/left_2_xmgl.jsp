<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<div id="menu2">
	<table width="95%" border="0">
		<tr>
			<td class="lefta">项目库申报</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>

		<!-- 项目申报 -->
		<%
        	if(user!=null&&user.getUserType()!=null&&!user.getUserType().equals(IConstants.USER_TYPE_5)){
        %>
		<tr>
			<td class="leftb"><a
				href="${ctx}/project/pjbaseinfo/edit.ac?id=0">项目申报</a>
			</td>
		</tr>
		<%
        	}
		%>



		<!-- 项目信息管理 -->
		<tr>
			<td class="leftb"><a href="${ctx}/project/pjbaseinfo/list.ac">项目信息管理</a>
			</td>
		</tr>
		<%
        	if(user!=null&&user.getUserType()!=null&&!user.getUserType().equals(IConstants.USER_TYPE_5)){
        %>
		<c:if test="${moduleMap['XMSB_XM_DCLXMGL']}">
		<tr>
			<td class="leftb"><a
				href="${ctx}/project/dclxm/list.ac">待处理项目管理</a>
			</td>
		</tr>
		<tr>
			<td class="leftb"><a
				href="${ctx}/project/csshtgxm/list.ac">各处室审核通过项目</a>
			</td>
		</tr>
		</c:if>
		<%
        	}
		%>
		<c:if test="${moduleMap['XMSB_XM_ZDXMGL']}">
		<tr>
			<td class="leftb"><a
				href="${ctx}/project/zdxm/list.ac">已通过审核项目</a>
			</td>
		</tr>
		</c:if>
		<!--
		<tr>
			<td class="leftb"><a
				href="${ctx}/project/zdxm/list.ac">项目图片长廊</a>
			</td>
		</tr>
		<c:if test="${moduleMap['XMSB_XM_NDJHZGGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/project/zdxm/list.ac">项目年度管理</a></td>
		</tr>
		</c:if>
		-->
		
		<!-- 项目计划报表管理 -->
		<c:if test="${moduleMap['XMSB_XM_XMJHBBGL']}">
		<tr>
			<td class="leftb"><a
				href="${ctx}/report/pjPlanReport.ac">正式项目库<!-- 项目计划报表管理 --></a>
			</td>
		</tr>
		</c:if>
		
		<c:if test="${moduleMap['XMSB_XM_XMZTGL']}">
		<tr>
			<td class="leftb"><a
				href="${ctx}/project/xmyb/list.ac">项目月报管理</a>
			</td>
		</tr>
		</c:if>
		
		<!-- 年度计划终稿管理 -->
		<c:if test="${moduleMap['XMSB_XM_NDJHZGGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/project/pjplanyear/list.ac">年度计划终稿管理</a></td>
		</tr>
		</c:if>
		
		
		
	</table>
</div>