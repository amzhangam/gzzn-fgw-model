<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择行业分类</title>
<style type="text/css">
#tab {
	width: 350px;
	height: 200px;
	border-collapse: collapse;
	margin: 10px;
}

#tab td {
	padding: 5px;
}

td,th {
	border: 1px #7f94be solid;
}

th {
	background-color: #D2D6E1;
}

ul.ztree {
	border: 1px solid #617775;
	background: #f0f6e4;
	width: 320px;
	height: 500px;
	overflow-y: scroll;
	overflow-x: auto;
}
</style>
<style type="text/css">

/* ------------- 图标按钮 -----------------  */
button.ico {
	width: 26px;
	height: 26px;
	padding: 0;
	margin: -5px 10px 0 0;
	vertical-align: middle;
	border: 0 none;
	background-color: transparent;
	background-repeat: no-repeat;
	background-position: 0 0;
}

button.ico.moveNode {
	background:
		url("${ctx}/resources/js/jquery-ztree/zTreeStyle/img/moveNode.png")
		no-repeat scroll 0 0 transparent;
}

button.ico.moveNodeR {
	background:
		url("${ctx}/resources/js/jquery-ztree/zTreeStyle/img/moveNodeR.png")
		no-repeat scroll 0 0 transparent;
}

button.ico.refresh {
	background:
		url("${ctx}/resources/js/jquery-ztree/zTreeStyle/img/refresh.png")
		no-repeat scroll 0 0 transparent;
}
</style>
<script src="${ctx}/resources/js/jquery-ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script type="text/javascript">
var setting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				prev: true,
				next: true,
				inner: false
			}
		},
		data: {
			simpleData: {
				enable: true
			},
			keep: {
				parent: true
			}

		},
		callback: {
			beforeDrag: beforeDrag,
			beforeDrop: beforeDrop
		}
	};
	
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		return targetNode ? targetNode.drop !== false : true;
	}

	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting,$.parseJSON('${treeJson}'));
		$.getJSON("ersExpertGroupMapTree.json?expertGroupId=${expertGroupId}", {
			"times" : Date.parse(new Date())
		}, function(results) {
			$.fn.zTree.init($("#treeDemo2"), setting, results);
		});
		
		
		$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
		$("#confirm").click(function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var arr = zTree.getCheckedNodes(true);
			if(arr == ""){
				$("#msg").html("请先选一个行业分类!");
				$("#dialog-message").dialog("open");
			} else {
				//window.opener.init(arr[0].id);
				$("#hylbid").val(arr[0].id);
				$("#hylbmc").val(arr[0].name);
				$("#myForm").submit();
			}
		});
	});
</script>
</head>
<body>
	<center>
		<form action="/htms/hiddenTypeMainAction_queryTopTwoClassField.do"
			method="post" id="myForm">
			<table id="tab" cellpadding="0" cellspacing="0">
				<tr>
					<th colspan="3" height="30">选择行业分类</th>
				</tr>
				<tr>
					<td width="100%" align="center" valign="top">
						<div class="zTreeDemoBackground left">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</td>
					<td><button class="ico moveNode" onfocus="this.blur();"
							title="移动节点 右->左" onclick="moveTreeL2R();"></button> <br /> <br />
						<br /> <br /> <br /> <br /> <br /> <br />
						<button class="ico moveNodeR" onfocus="this.blur();"
							title="移动节点 左->右" onclick="moveTreeR2L();"></button> <br /> <br />
						<br /> <br /> <br /> <br /> <br /> <br />
						<button class="ico refresh" onfocus="this.blur();" title="恢复初始状态"
							onclick="reloadTree();"></button> <br /> <br /> <br /> <br />
						<br /> <br /> <br /> <br /></td>
					<td width="100%" align="center" valign="top">
						<div class="zTreeDemoBackground left" style="height: 30%;">
							<ul id="treeDemo2" class="ztree"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3" height="30" align="center">
						<!-- <input type="button" value="上一步" onclick="window.close();" class="btn2"/> -->
						<input type="button" value="下一步" id="confirm" class="btn" /> <input
						type="hidden" value="" name="hylbids" id="hylbids" /> <input
						type="hidden" value="true" name="multipleTree" id="multipleTree" />
						<input type="hidden" value="" name="hylbmc" id="hylbmc" /></td>
				</tr>
			</table>
			
		</form>
	</center>
</body>
</html>
