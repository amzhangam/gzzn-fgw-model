<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>角色-权限分配</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
	 var setting = {
		 	 view: {  
			    dblClickExpand: false
			},  
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	   $(document).ready(function() {
			showMenu(6,62);
			
			var zNodes = eval('${jsonTreeNodes}');
			var zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			//zTree.expandAll(true); 
			
			//重置表单
			$("#resetBtn").click(function() {
				zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
   			});
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/role/list.ac";
			});
			
			$("#saveBtn").click(function() {
				if($("#roleId").val().length>0){
				var ckNodes = zTree.getCheckedNodes(true);
				
				var temp = "";
				for (var i=0, l=ckNodes.length; i<l; i++) { 
					if(!ckNodes[i].isParent){//当是父节点 不计入id
						temp += "@" + ckNodes[i].id;//获取选中节点的id值
					}
				}
				if(temp.length > 0){
					temp = temp.substring(1);
				}
				$("#treeIds").val(temp);
				$("#editForm").submit();
			}else{
				alert("请先指定角色！");
			}
			});
			
		});
		
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/role/saveRolePermission.ac" method="post">
		<input type="hidden" name="id" id="roleId" value="${sysRole.roleId}"/>
		<input type="hidden" name="treeIds" id="treeIds" value=""/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">角色-权限分配</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" id="saveBtn" value="保存" /> 
						 <input type="reset" class="btn" id="resetBtn" value="重置" /> 
						 <input type="button" class="btn" id="backBtn" value="返回" />
						</span>
					</div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="editDiv" style="margin-bottom: 15px;">
			<table class="editTab" id="editTab">
				<tr>
					<th>角色名称<font class="msg">*</font></th>
					<td>${sysRole.roleName}</td>
				</tr>
				<tr>
					<th>角色权限</th>
					<td>
						<div class="zTreeDemoBackground left" id="showData">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>