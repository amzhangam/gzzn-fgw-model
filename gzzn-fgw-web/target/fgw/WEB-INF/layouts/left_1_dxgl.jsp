<%@ page contentType="text/html;charset=UTF-8"%>
<div id="menu1" class="hide">
	<table width="95%" border="0">
		<tr>
			<td class="lefta">短信管理</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<c:if test="${moduleMap['XMSB_DX_DXFS']}">
			<tr>
				<td class="leftb"><a href="${ctx}/sys/dx/lxrlist.ac">发送短信</a></td>
			</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_DX_XMDXFS']}">
			<tr>
				<td class="leftb"><a href="${ctx}/sys/dx/fsxmlist.ac">发送项目短信</a></td>
			</tr>
		</c:if>
		<c:if test="${moduleMap['XMSB_DX_DXCX']}">
			<tr>
				<td class="leftb"><a href="${ctx}/sys/dx/list.ac">已发送短信</a></td>
			</tr>
		</c:if>
	
		<c:if test="${moduleMap['XMSB_DX_DXMB'] }">
			<tr>
				<td class="leftb"><a href="${ctx}/sys/dxmb/list.ac">短信模板</a></td>
			</tr>
		</c:if>
	</table>
</div>