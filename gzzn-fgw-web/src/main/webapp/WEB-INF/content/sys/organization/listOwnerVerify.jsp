<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>注册业主审核管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="../../resources/js/sys/public.js" type="text/javascript"></script>
<script src="../../resources/js/sys/sys.js" type="text/javascript"></script>
<script src="../../resources/js/sys/listOwner.js" type="text/javascript"></script>
</head>
<body>
	<!-- 加载相关的查询条件 -->
	<form action="./listOwnerVerify.ac" method="post">
		<div class="topSearchDiv" style="width: 100%;">
		  	<input type="hidden" value="<%=request.getContextPath() %>" id="cxt"/>
			  <table class="topSearchTab">
				<tr>
					<th>单位名称：</th>
					<td><input type="text" class="text" name="sysParams.organName" value="${sysParams.organName}" /></td>
					<th>所在区域：</th>
					<td>
						<input type="text" name="sysXq.xqmc" id="xqName"
							          value="<s:property value="#attr.sysXq.xqmc"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="sysXq.xqId" id="xqId" value="<s:property value="#attr.sysXq.xqId"/>"/>
					</td>
					<th>单位性质：</th>
					<td>
						<input type="text" name="workunitsqualityName" id="dwxzName"
							          value="<s:property value="xzMap[#attr.workunitsquality]"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="workunitsquality" id="dwxzId" value="<s:property value="#attr.workunitsquality"/>"/>
					</td>
					<th>单位状态：</th>
					<td>
						<input type="text" name="sysParams.workunitsstatusName" id="dwztName"
							          value="<s:property value="ztMap[sysParams.organStatus]"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="sysParams.organStatus" id="dwztId" value="<s:property value="#attr.sysParams.organStatus"/>"/>
					</td>
					<td>
						<input type="button" class="btn" value="查询"  id="queryBtn" />&nbsp;&nbsp;
						<input type="button" class="btn" value="清空"  id="clearCon" />
					</td>
				</tr>
			  </table>
		</div>
		<!-- 相关数据展示 -->	
		<table style="width: 100%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:128px;">注册业主审核管理</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg">
					 <div>
					    <span style="float: right;">     
			            	 <input type="button" class="btn" id="addBtn" onclick="editOrAddUrl('')" value="新增"/>
			            	 <input type="button" class="btn" id="delBtn" value="批量删除"/>  
		               	</span>      
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<table class="list" style="width: 100%;margin: 0 auto;">
			<thead>
				<tr>
					<td width="3%"><input type="checkbox" id="checkAll" /></td>
					<td width="5%">序号</td>
					<td width="15%">业主单位名称</td>
					<td width="8%">企业工商注册号</td>
					<td width="8%">单位性质</td>
					<td width="8%">所在区域</td>
					<td width="8%">单位法人</td>
					<td width="8%">项目联系人</td>
					<td width="8%">项目联系人手机号码</td>
					<td width="19%">业主单位地址</td>
					<td width="10%">操作</td>
				</tr>
			</thead>
			<tbody>
		  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
				<tr>
					<td>
						<input type="checkbox" value="${obj.organizationId}" name="id" class="subcheck"/>
					</td>
					<td>${page.size*page.current+status.index+1}</td>
					<td>${obj.organizationName}</td>
					<td>${obj.workunitsregistercode}</td>
					<td>
						<s:property value="xzMap[#attr.obj.workunitsquality]"/>
					</td>
					<td>
						${obj.sysXq==null?'':obj.sysXq.xqmc}
					</td>
					<td>${obj.workunitsperson}</td>
					<td>${obj.workunitslinkman}</td>
					<td>${obj.workunitslinkmantel}</td>
					<td>${obj.workunitsaddress}&nbsp;</td>
					<td>
						<a href="./ownerVerify.ac?id=${obj.organizationId}">审核</a>
						&nbsp;&nbsp;|&nbsp;&nbsp;
						<a href="./editOwnerVerify.ac?id=${obj.organizationId}">编辑</a>
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
