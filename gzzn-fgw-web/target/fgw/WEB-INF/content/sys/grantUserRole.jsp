<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>角色-权限分配</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>

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
			
			var zNodes = eval('${requestScope.treeNodesList}');
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			//zTree.expandAll(true); 
			
			
			//重置表单
			$("#resetBtn").click(function() {
				zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
   			});
   			//返回
			$("#backBtn").click(function() {
				window.location = "./sysUserQuery.ac";
			});
			
			$("#saveBtn").click(function() {
				if($("#userId").val()!=""){
					var ckNodes = zTree.getCheckedNodes(true);
					var ids = "";
					for (var i=0, l=ckNodes.length; i<l; i++) { 
						if(!ckNodes[i].isParent){//当是父节点 不计入id
							ids += "@" + ckNodes[i].id;//获取选中节点的id值
						}
					}
					if(ids.length > 0){
						ids = ids.substring(1);
					}
					$.ajax({
				    	type:"POST",
				    	url:"sysUserRoleAdd.json",
				    	data:{
				    		ids:ids,
				    		"sysUser.userId":$("#userId").val()
				    	},
				    	success:function(data){
				    		if(data.success){
				    			mac.alert("角色分配成功！");
				    		}else{
				    			mac.alert("角色分配失败!");
				    		}
				    	},
				    	error:function() { 
				    		mac.alert("系统响应异常！");
						}, 
				    	dataType:'json'
					});
				}else{
					mac.alert("请先指定用户！");
				}
			});
			
		});
		
	
	//-->
</script>
</head>
<body>
	<form id="editForm" method="post">
		<input type="hidden" name="userId" id="userId" value="${dto.userId}"/>
		<input type="hidden" name="treeIds" id="treeIds" value=""/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">用户-角色分配<br></td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" id="saveBtn" value="保存" /> 
						 <input type="reset" class="btn" id="resetBtn" value="重置" /> 
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
					<th>用户名称<font class="msg">*</font></th>
					<td>${dto.userName}</td>
				</tr>
				<tr>
					<th>登录名<font class="msg">*</font></th>
					<td>${dto.loginName}</td>
				</tr>
				<tr>
					<th>用户角色</th>
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