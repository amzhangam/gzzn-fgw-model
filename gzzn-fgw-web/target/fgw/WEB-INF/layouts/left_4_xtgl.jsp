<%@ page contentType="text/html;charset=UTF-8"%>
<div id="menu6">
	<table width="95%" border="0">
		<tr>
			<td class="lefta">系统管理</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<c:if test="${moduleMap['XMSB_XT_YHGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/sysUserQuery.ac" onClick="subModule('68');">用户管理</a></td>
		</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_DWGL']}">
		<%-- <tr>
			<td class="leftb"><a href="${ctx}/sys/organization/list.ac" onClick="subModule('68');">单位管理</a></td>
		</tr> --%>
		<tr>
			<td class="leftb"><a href="${ctx}/sys/organNew/list.ac" onClick="subModule('68');">单位管理</a></td>
		</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_BMGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/dept/list.ac" onClick="subModule('68');">部门管理</a></td>
		</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_ZCYZSHGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/userVerifyQuery.ac" onClick="subModule('68');">用户审核管理</a></td>
		</tr>
		<tr>
			<td class="leftb"><a href="${ctx}/sys/organNew/listOwnerVerify.ac" onClick="subModule('68');">注册业主审核管理</a></td>
		</tr>
		</c:if>

		<c:if test="${moduleMap['XMSB_XT_ZWGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/duty/list.ac" onClick="subModule('68');">职务管理</a></td>
		</tr>
		</c:if>
		
		<!-- 日志管理 -->
		<c:if test="${moduleMap['XMSB_XT_RZGL'] || moduleMap['XT_RZGL'] }">
			<td class="leftb"><a href="javascript:void(0);" onClick="subModule('66');">日志管理</a></td>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_RZGL']}">
		<tr>
			<td class="subMod66"><a href="${ctx}/sys/log/list.ac">用户操作日志</a></td>
		</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_RZGL']}">
		<tr>
			<td class="subMod66"><a href="${ctx}/sys/log/pjList.ac">项目日志</a></td>
		</tr>
		</c:if>
		
		<c:if test="${moduleMap['XMSB_XT_JSGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/role/list.ac" onClick="subModule('68');">角色管理</a></td>
		</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_XQGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/xq/list.ac" onClick="subModule('68');">辖区管理</a></td>
		</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_XMLBGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/hylb/list.ac" onClick="subModule('68');">行业分类管理</a></td>
		</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_YJMB']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/yjmb/list.ac" onClick="subModule('68');">意见模板管理</a></td>
		</tr>
		</c:if>
		<%-- <c:if test="${moduleMap['XMSB_XT_HYFLGL']}">
		<tr>
			<td class="leftb"><a href="${ctx}/sys/hylb/list.ac" onClick="subModule('68');">行业分类管理</a></td>
		</tr>
		</c:if> --%>
		<!--tr>
			<td class="leftb"><a href="${ctx}/sys/userModifyPassEdit.ac" onClick="subModule('68');">用户密码修改</a></td>
		</tr-->

	</table>
</div>