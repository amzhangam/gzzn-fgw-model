<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>行业分类信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
		var setting = {
				check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onCheckBoxCheck
				}
			};
			
	$(document).ready(function() {
		showMenu(6,65);
		//$("#dataViewId").val("${sysParams.dataViewId}");
		
		var hylbDatas = getJsonDatas("${ctx}/com/getXmsbHylbJson.json","");
		initCheckBoxTree("hylbUp", setting, hylbDatas, "${id}");//初始化多选的树形下拉框
		
	    if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}

		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/sys/hylb/edit.ac";
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("${ctx}/sys/hylb/delete.ac?id=");
	});
//-->
</script>

	<form action="${ctx}/sys/hylb/list.ac" method="post">
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">行业分类信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					  上级行业名称：
						<input type="text" id="hylbUpSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="id" id="hylbUpSelID" value="${id}"/>
						<div id="hylbUpDiv" class="menuContent" style="display:none; position: absolute;width:250px;">
							<ul id="hylbUp" class="ztree"  style="margin-top:0; width:200px; height: 250px;"></ul>
						</div>&nbsp;&nbsp;
					       行业名称：
					   <input type="text" class="text" name="hylbmc" value="${hylbmc}" />  
					   <c:if test="${objectMap['XMSB_XT_XMLBGL_VIEW']}">
					   <input type="button" class="btn" id="queryBtn" value="查询" />
					   </c:if>
					        
					    <span style="float: right;">     
					    	<c:if test="${objectMap['XMSB_XT_XMLBGL_ADD']}">
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
			            	 </c:if>
			            	 <c:if test="${objectMap['XMSB_XT_XMLBGL_DEL']}">
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
				<td width="30%">行业名称</td>
				<td width="15%">行业代码</td>
				<td width="30%">上级行业名称</td>
				<td width="15%">操作</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.hylbId}" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.hylbmc}</td>
				<td>${obj.hylbdm}</td>
				<td>${obj.xmsbHylb.hylbmc}</td>
				<td>
					<c:if test="${objectMap['XMSB_XT_XMLBGL_MOD']}">
					<a href="${ctx}/sys/hylb/edit.ac?id=${obj.hylbId}">编辑</a>
					</c:if>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XT_XMLBGL_DEL']}">
					<a href="javascript:deleteAction('${ctx}/sys/hylb/delete.ac?id=${obj.hylbId}')">删除</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6" class="line2" style="text-align: right;">
				<%@include file="../../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
