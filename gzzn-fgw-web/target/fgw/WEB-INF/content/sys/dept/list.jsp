<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>部门信息</title>
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
		showMenu(6,64);
		
	    if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}
		
		//上级部门
		var deptDatas = getJsonDatas("${ctx}/com/getOrgDeptTreeJson.json","params.workunitstype=3");
		initCheckBoxTree("deptTree", setting, deptDatas, "${sysParams.parentDeptId}");//初始化多选的树形下拉框

		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/sys/dept/edit.ac";
		});
		
		$("#clearbtn").click(function(){
			clearCheckNodes(t1,"orgTree");//清空树中被选中的节点
			clearCheckNodes(t2,"deptTree");//清空树中被选中的节点
			$("input[type='text'],input[type='hidden']").each(function() {
		    	this.value = "";
		    });
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("${ctx}/sys/dept/delete.ac?id=");
	});
//-->
</script>

	<form action="${ctx}/sys/dept/list.ac" method="post">
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">部门信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					         上级部门：
					    <input type="text" class="text widthPct14" name="sysParams.parentDeptName" id="deptTreeSelName" value="${sysParams.parentDeptName}" readonly="readonly" />
						<input type="hidden" name="sysParams.parentDeptId" id="deptTreeSelID" value="${sysParams.parentDeptId}"/>
						<div id="deptTreeDiv" class="menuContent" style="display:none; position: absolute; width:260px; height: 300px;">
							<ul id="deptTree" class="ztree"  style="margin-top:0;"></ul>
						</div>
						
					       部门名称：
					   <input type="text" class="text widthPct14" size="15" name="sysParams.deptname" value="${sysParams.deptname}" />
					  
						<c:if test="${objectMap['XMSB_XT_YHGL_ROLE']}">
					   <input type="button" class="btn" id="queryBtn" value="查询" />
					   </c:if>
					   <!-- <input type="button" class="btn" id="clearbtn" value="清空" /> -->
					      
					    <span style="float: right;">     
					    	<c:if test="${objectMap['XMSB_XT_BMGL_ADD']}">
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
			            	 </c:if>
			            	 <c:if test="${objectMap['XMSB_XT_BMGL_DEL']}">
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
				<td width="3%"><input type="checkbox" id="checkAll" /></td>
				<td width="5%">序号</td>
				<td width="15%">部门名称</td>
				<td width="8%">部门代码</td>
				<td width="15%">上级部门</td>
				<td width="15%">所属单位</td>
				<td width="15%">部门描述</td>
				<td width="6%">是否投资处</td>
				<td width="8%">是否显示</td>
				<td width="8%">操作</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.deptId}" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.deptname}</td>
				<td>${obj.fullcode}</td>
				<td>${obj.sysDept.deptname}</td>
				<td>${obj.sysOrganization.organizationName}</td>
				<td>${obj.description}</td>
				<td>
					<c:if test="${obj.sftzc eq 1}" var="result">是</c:if>
					<c:if test="${!result}">否</c:if>
				</td>
				<td>
					<c:if test="${obj.sfxs eq 0}" var="result">不显示</c:if>
					<c:if test="${!result}">显示</c:if>
				</td>
				<td>
					<c:if test="${objectMap['XMSB_XT_BMGL_MOD']}">
					<a href="${ctx}/sys/dept/edit.ac?id=${obj.deptId}">编辑</a>
					</c:if>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XT_BMGL_DEL']}">
					<a href="javascript:deleteAction('${ctx}/sys/dept/delete.ac?id=${obj.deptId}')">删除</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10" class="line2" style="text-align: right;">
				<%@include file="../../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
