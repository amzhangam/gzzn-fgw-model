<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>短信管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/dx/dx.js"  type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<!-- 工程名 -->
	<input type="hidden" value="<%=request.getContextPath() %>" id="ctx"/>
</head>
<body>
<script type="text/javascript">
//<!--
	$(document).ready(function() {

		showMenu(1,13);
		
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#sendBtn").click(function(){
			window.location = "${ctx}/sys/dx/lxrlist.ac";
		});
	});
//-->


</script>

	<form action="${ctx}/sys/dx/list.ac" method="post">
		<table style="width:100%" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="10">
			<tr>
				<td>
				      <table style="width:500" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">
							       发送用户名称：
							</td>
							<td>
								   <input type="text" class="text" name="sysParams.sendUsername" value="${sysParams.sendUsername}" />
							</td>
							<td align="right">
								    发送部门：
							</td>
							<td>
								<input type="text" name="sysParams.sendDeptname" id="sendDeptName" value="<s:property value="sysParams.sendDeptname"/>" readonly="readonly"/>
						        <input type="hidden" name="sysParams.sendDeptid" id="sendDeptId"  value="<s:property value="sysParams.sendDeptid"/>"/>
							</td>
							<td align="right">
								接收单位：
							</td>
							<td>
								<input type="text" name="sysParams.receiveOrganizationname" id="receiveOrganizationName" value="<s:property value="sysParams.receiveOrganizationname"/>"/>
						        <input type="hidden" name="sysParams.receiveOrganizationid" id="receiveOrganizationId"  value="<s:property value="sysParams.receiveOrganizationid"/>"/>
							</td>
							<td>
								   &nbsp;
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td align="right">
								    接收部门：
							</td>
							<td>
								<input type="text" name="sysParams.receiveDeptname" id="receiveDeptName" value="<s:property value="sysParams.receiveDeptname"/>" readonly="readonly"/>
						        <input type="hidden" name="sysParams.receiveDeptid" id="receiveDeptId"  value="<s:property value="sysParams.receiveDeptid"/>"/>
							</td>
							<td align="right">
							       联系人：
							</td>
							<td>
								   <input type="text" class="text" name="sysParams.lxrmc" value="${sysParams.lxrmc}" />
							</td>
							<td align="right">
								联系人手机号码：
							</td>
							<td>
								<input type="text" class="text" name="sysParams.sjhm" value="${sysParams.sjhm}" />
							</td>
							<td>
								   &nbsp;
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td align="right">
									发送时间：
							</td>
							<td colspan="3">
							  		<input type="text" id="sfsjBegin" name="sysParams.sfsjBegin" value="${sysParams.sfsjBegin}" size="30"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
							至
										<input type="text" id="sfsjEnd" name="sysParams.sfsjEnd" value="${sysParams.sfsjEnd}" size="30"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
										&nbsp;&nbsp;
								        
							</td>
							<td align="right">
								  发送内容：
							</td>
							<td>
								<input type="text" class="text" name="sysParams.sfnr" value="${sysParams.sfnr}" />
							</td>
							<td align="right">
							<c:if test="${objectMap['XMSB_DX_DXCX_VIEW']}">
								&nbsp;&nbsp;<input type="button" class="btn" id="queryBtn" value="查询" />
							</c:if>
							<c:if test="${moduleMap['XMSB_DX_DXFS']}">
								&nbsp;&nbsp;<input type="button" class="btn" id="sendBtn" value="发送短信" />
							</c:if>
								<!--  <input type="button" class="btn" id="clearbtn" value="清空" /> -->
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
					</table>	
				</td>
			</tr>
		</table>
		
	<table width="100%" border="0" cellpadding="1" cellspacing="0" bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="5%"><input type="checkbox" id="checkAll" /></td>
				<td width="5%">序号</td>
				<td width="10%">发送用户</td>
				<td width="10%">发送部门</td>
				<td width="10%">接收单位</td>
				<td width="10%">接收部门</td>
				<td width="10%">联系人</td>
				<td width="10%">联系人手机号</td>
				<td width="10%">发送时间</td>
				<td width="10%">发送内容</td>
				<td width="10%">状态</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.dxId}" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.sysUser.userName}</td>
				<td>${obj.sysDeptByDeptId!=null?obj.sysDeptByDeptId.deptname:''}</td>
				<td>${obj.sysOrganization!=null?obj.sysOrganization.organizationName:''}</td>
				<td>${obj.sysDeptByReceiveDeptId!=null?obj.sysDeptByReceiveDeptId.deptname:''}</td>
				<td>${obj.lxrmc}</td>
				<td>${obj.sjhm}</td>
				<td><fmt:formatDate value='${obj.sfsj}' pattern='yyyy-MM-dd'/></td>
				<td>${obj.sfnr}</td>
				<td>
					<c:if test="${obj.sfbj==0}">
						无法发送
					</c:if>
					<c:if test="${obj.sfbj==1}">
						待发送
					</c:if>
					<c:if test="${obj.sfbj==2}">
						已发送
					</c:if>
					<c:if test="${obj.sfbj==10}">
						故障短信
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="11" class="line2" style="text-align: right;">
				<%@include file="../../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
