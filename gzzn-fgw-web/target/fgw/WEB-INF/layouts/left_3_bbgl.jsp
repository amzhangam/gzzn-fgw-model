<%@ page contentType="text/html;charset=UTF-8"%>
<div id="menu3" class="hide">
	<table width="95%" border="0">
		<tr>
			<td class="lefta">报表管理</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>

		<!-- 申报项目计划报表 -->
		<c:if test="${moduleMap['XMSB_BB_SBXMJHBB']}">
		<tr>
			<td class="leftb"><a href="${ctx}/report/sbReport.ac">申报项目计划报表</a></td>
		</tr>
		</c:if>

		<!-- 业主/主管单位提交项目计划报表 -->
		<%-- <c:if test="${moduleMap['BB_YZZGDWTJXMJHBB']}">
		<tr>
			<td class="leftb"><a href="${ctx}/report/list.ac">业主/主管单位提交项目计划报表</a></td>
		</tr>
		</c:if> --%>
		<c:if test="${moduleMap['XMSB_BB_YZZGDWTJXMJHBB']}">
		<tr>
			<td class="leftb"><a href="${ctx}/report/tjReport.ac">业主/主管单位提交项目计划报表</a></td>
		</tr>
		</c:if>
		
		<!-- 审核通过报表 -->
		<c:if test="${moduleMap['XMSB_BB_YZZGDWTJXMJHBB']}">
		<tr>
			<td class="leftb"><a href="${ctx}/report/passReport.ac">审核通过报表</a></td>
		</tr>
		</c:if>
		
		<!-- 审核不通过报表 -->
		<c:if test="${moduleMap['XMSB_BB_YZZGDWTJXMJHBB']}">
		<tr>
			<td class="leftb"><a href="${ctx}/report/notPassReport.ac">审核不通过报表</a></td>
		</tr>
		</c:if>
		
		
		<!-- 项目申请汇总报表 -->
		<c:if test="${moduleMap['XMSB_BB_XMSQHZBB']}">
		<tr>
			<td class="leftb"><a href="${ctx}/report/sqhzReport.ac"" >项目申请汇总报表</a></td>
		</tr>
		</c:if>
		
	</table>
</div>