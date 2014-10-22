<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>业主单位信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="../../resources/js/sys/public.js" type="text/javascript"></script>
<script src="../../resources/js/sys/sys.js" type="text/javascript"></script>
<script src="../../resources/js/sys/listOwner.js" type="text/javascript"></script>
</head>
<body>


	<form action="./listOwner.ac" method="post">
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">业主单位信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
				       <b> 业主单位名称：</b>
					   <input type="text" class="text" name="sysParams.organName" value="${sysParams.organName}" />
					   <b>所在地区：</b>
				  		<input type="text" name="sysXq.xqmc" id="xqName"
							          value="<s:property value="#attr.sysXq.xqmc"/>" readonly="readonly" class="text"/>
							        <input type="hidden" name="sysXq.xqId" id="xqId"  
							        value="<s:property value="#attr.sysXq.xqId"/>"/>
							&nbsp;&nbsp;
							<b>单位性质：</b>
							<input type="text" name="workunitsqualityName" id="dwxzName"
							          value="<s:property value="xzMap[#attr.workunitsquality]"/>" readonly="readonly" class="text"/>
							        <input type="hidden" name="workunitsquality" id="dwxzId"  
							        value="<s:property value="#attr.workunitsquality"/>"/>
							&nbsp;&nbsp;
							<input type="button" class="btn" value="重置"  id="clearCon">
							<c:if test="${objectMap['XMSB_XT_BMGL_VIEW']}">
					   <input type="button" class="btn" id="queryBtn" value="查询" />
					   </c:if>
					  <!--  <input type="button" class="btn" id="clearbtn" value="清空" /> -->
					        
					    <span style="float: right;">   
					    	<c:if test="${objectMap['XMSB_XT_DWGL_ADD']}">  
			            	 <input type="button" class="btn" id="addBtn" onclick="editOrAddUrl('')" value="新增"/>
			            	 </c:if>
			            	 <c:if test="${objectMap['XMSB_XT_DWGL_DEL']}">
			            	 <input type="button" class="btn" id="delBtn" value="批量删除"/>     
			            	 </c:if>
		               	</span>     
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		
	<table width="100%" border="0" cellpadding="1" cellspacing="0" bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="5%"><input type="checkbox" id="checkAll" /></td>
				<td width="5%">序号</td>
				<td width="15%">业主单位名称</td>
				<td width="8%">企业工商注册号</td>
				<td width="8%">单位性质</td>
				<td width="8%">所在区域</td>
				<td width="8%">单位法人</td>
				<td width="8%">项目联系人</td>
				<td width="8%">项目联系人手机号码</td>
				<td width="15%">业主单位地址</td>
				<td width="12%">操作</td>
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
				<td>${obj.workunitsaddress}</td>
				<td>
					<c:if test="${objectMap['XMSB_XT_DWGL_MOD']}">
					<a href="./editOwner.ac?id=${obj.organizationId}">编辑</a>
					</c:if>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XT_DWGL_DEL']}">
					<a href="javascript:deleteAction('./deleteOwner.ac?id=${obj.organizationId}')">删除</a>
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
