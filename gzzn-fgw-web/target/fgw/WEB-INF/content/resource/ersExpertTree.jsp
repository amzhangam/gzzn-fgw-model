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
	var zTree1, zTree2;
	var setting = {
		check : {
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			},
			enable : true,
			autoCheckTrigger : false
		},
		edit : {
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false,
			drag : {
				isCopy:false,
				prev : true,
				next : true,
				inner : dropInner
			}
		},
		data : {
			simpleData : {
				enable : true
			},
			keep : {
				parent : true
			}

		},
		callback : {
			beforeDrag: beforeDrag
		}
	};
	function dropInner(treeId, nodes, targetNode) {
		if (targetNode && !(targetNode.isParent)) {
			return false;
		} 
		return true;
	}
	var curDragNodes;
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].isParent) {
				curDragNodes = null;
				return false;
			} 
		}
		curDragNodes = treeNodes;
		return true;
	}

	$(document).ready(
			function() {
				Tree1 = $.fn.zTree.init($("#treeDemo"), setting, $
						.parseJSON('${treeJson}'));
				$.getJSON("ersExpertGroupMapTree.json", {
					"expertGroupId" : '${expertGroupId}',
					"times" : Date.parse(new Date())
				},
						function(results) {
							Tree2 = $.fn.zTree.init($("#treeDemo2"), setting,
									results);
							Tree2.expandAll(true);
							//reloadTree('${treeJson}',results);
						});
				//$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
				$("#confirm").click(function() {
					//var hylbids=$("#hylbids").val();
					Tree2.checkAllNodes(true);
					var arr = Tree2.getCheckedNodes(true);
					var ids = new Array();
					for ( var i = 0; i < arr.length; i++) {
						if (!arr[i].isParent) {
							var pid = "";
							if (arr[i].pId != null) {
								pid = arr[i].pId;
							}
							var data = pid + "#" + arr[i].id;
							ids.push(data);
						}
					}
					Tree2.checkAllNodes(false);
					if (ids.length == 0) {
						if (!window.confirm("您没有配置任何数据，保存后将清空原有数据!")) {
							return;
						}
					}
					$.ajax({
						type : "Post",
						url : "ersExpertGroupMapSaveTree.json",
						data : {
							"expertGroupId" : '${expertGroupId}',
							'idsStr' : ids.toString(),
							time : new Date().getTime()
						},
						dataType : "json",
						success : function(data) {
							if (data.success) {
								parent.mac.alert("保存成功！");
								//$("#lookSpan").css("display", "");
							} else {
								parent.mac.alert("保存失败！");
							}
						},
						error : function(err) {
							alert("系统响应异常！");
						}
					});
				});
				$("#lookBtn").click(function() {
					window.location.href = "";
				});
			});
	//数据右移动
	function addRole() {
		//移动方法
		//右移时Tree1 在第一个参数,Tree2第二个参数
		//表示Tree1移动致Tree2
		moveTreeNode(Tree1, Tree2);
	}
	//数据左移动
	function delRole() {
		//移动方法 参数相反
		moveTreeNode(Tree2, Tree1);
	}

	function moveTreeNode(srcTree, targetTree) {
		var nodes = srcTree.getCheckedNodes(); //获取选中需要移动的数据
		for ( var i = 0; i < nodes.length; i++) { //把选中的数据从根开始一条一条往右添加
			var node = nodes[i];
			if (!node.isParent) {
				var strs = {}; //新建一个JSON 格式数据,表示为一个节点,可以是根也可以是叶
				strs.id = node.id;
				strs.name = node.name;
				strs.checked = true;
				zTreeDataAddNode(strs, targetTree);
			}
			zTreeDataDelete(nodes[nodes.length - (i + 1)], srcTree);
		}
		//把选中状态改为未选择
		//targetTree.checkAllNodes(false);
		//srcTree.checkAllNodes(false);
		//刷新
		targetTree.refresh();
		srcTree.refresh();
	}

	//树数据移动方法
	function zTreeDataAddNode(strs, zTree2) {
		var nodes = zTree2.getCheckedNodes();
		//如果有多个数据需要遍历,找到strs 属于那个父亲节点下面的元素.然后把自己添加进去
		if (nodes.length > 0) {
			var j = 0;
			for (; j < nodes.length; j++) {
				if (nodes[j].isParent) {
					//已经得到选中父的数据
					break;
				}
			}
			var treeNode1 = nodes[j];
			zTree2.addNodes(treeNode1, strs);
		} else {
			//目标数没有选中,直接插入根目录
			zTree2.addNodes(null, strs);
		}
	}
	//
	//数据移除
	function zTreeDataDelete(node, zTree1) {
		if (!node.isParent) {
			zTree1.removeNode(node);
		}
	}
</script>
</head>
<body>
	<center>
		<script type="text/javascript">
			$(function() {
				showMenu(2, 23);
					$( document ).tooltip();
			});
		</script>

		<table id="tab" cellpadding="0" cellspacing="0">
			<tr>
				<th colspan="3" height="30"><span title="您可以进行移动或拖动子节点">选择进行专家组与专家职务分配-您当前选择的是'${ersExpertGroup.expertGroupName
						}'</span>
				</th>
			</tr>
			<tr>
				<td width="100%" align="center" valign="top">
					<div class="zTreeDemoBackground left">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</td>
				<td><button class="ico moveNode" title="移动节点 左->右"
						onclick="addRole();"></button> <br /> <br /> <br /> <br /> <br />
					<br /> <br /> <br />
					<button class="ico moveNodeR" title="移动节点 右->左"
						onclick="delRole();"></button> <br /> <br /> <br /> <br /> <br />
					<br /> <br /> <br />
					<button class="ico refresh" title="刷新"
						onclick="window.location.reload()"></button> <br /> <br /> <br />
					<br /> <br /> <br /> <br /> <br /></td>
				<td width="100%" align="center" valign="top">
					<div class="zTreeDemoBackground left" style="height: 30%;">
						<ul id="treeDemo2" class="ztree"></ul>
					</div>
				</td>
			</tr>
			<tr>

				<td colspan="3" height="30" align="center"><form
						action="${ctx }/resource/ersExpertGroupMapQuery.ac" method="post">
						<input type="button" value="后退" onclick="history.go(-1)"
							class="btn" /> <input type="button" value="配置" id="confirm"
							class="btn" /> <input type="hidden"
							value="${ersExpertGroup.expertGroupName }"
							name="dto.expertGroupName"></input> <input type="submit"
							style="display: none;" id='lookSpan' value="查看配置结果" class="btn" />
					</form>
				</td>

			</tr>
		</table>
	</center>
</body>
</html>
