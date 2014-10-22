<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<div id="menu12">
	<table width="95%" border="0">
		<tr>
			<td class="lefta">项目信息报送</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>

		<!-- 项目建设情况导入统计功能 -->
		<c:if test="${moduleMap['XMSB_XM_QXXMQK']}">
			<c:if test="${moduleMap['XMSB_XM_QXXMQK_DOWN']}">
			<tr>
				<td class="leftb"><a href="${ctx}/buildImpl/downTemp.ac">下载模板</a></td>
			</tr>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_QXXMQK_IMPORT']}">
			<tr>
				<td class="leftb"><a href="${ctx}/buildImpl/dataImpl.ac">导入数据</a></td>
			</tr>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_QXXMQK_TBLOG']}">
			<tr>
				<td class="leftb"><a href="${ctx}/buildImpl/logList.ac">填报日志</a></td>
			</tr>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_QXXMQK_QUERY']}">
			<tr>
				<td class="leftb"><a href="${ctx}/buildImpl/list.ac">上报项目查询</a></td>
			</tr>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_QXXMQK_REPORTMGT']}">
			<tr>
				<td class="leftb"><a href="${ctx}/buildImpl/reportList.ac">填报项目管理</a></td>
			</tr>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_QXXMQK_STATISTICS']}">
			<tr>
				<td class="leftb"><a href="${ctx}/buildImpl/sumStat.ac">按产业投资额汇总</a></td> 
			</tr>
			<%-- <tr>
				<td class="leftb"><a href="${ctx}/buildImpl/sumStat.ac">上报项目汇总统计</a></td> 
			</tr> --%>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_QXXMQK_XQSTAT']}">
			<tr>
				<td class="leftb"><a href="${ctx}/buildImpl/xqStat.ac">按区县汇总统计</a></td> 
			</tr>
			</c:if>
			
			
			
		</c:if>
		
		
	</table>
</div>