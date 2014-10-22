function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function showMenuTree(targetId, contentId, onBodyDown) {
	var obj = $("#" + targetId);
	var offset = obj.offset();
	$("#" + contentId).css({
		left : offset.left + "px",
		top : offset.top + obj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenuTree(targetId, contentId) {
	$("#" + contentId).fadeOut("fast");
}
function checkMenu(targetId, contentId, event) {
	if (!(event.target.id == "menuBtn" || event.target.id == targetId
			|| event.target.id == contentId || $(event.target).parents(
			"#" + contentId).length > 0)) {
		hideMenuTree(targetId, contentId);
	}
}
function defaultSelect(treeId, valueId) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	if (zTree) {
		var node = zTree.getNodeByParam("id", $("#" + valueId).val(), null);
		if (node != null) {
			zTree.expandNode(node.getParentNode());
			zTree.checkNode(node, true, null, true);
		}
	}
}
// 初始化树图
// id，请求地址，参数
function initMyTree(tg, url) {
	var setting = {
		check : {
			enable : true,
			chkStyle : "radio",
			radioType : "all"
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			},
			onCheck : function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				nodes = zTree.getCheckedNodes(true);
				if (nodes.length != 0) {
					$("#" + tg + "Id").val(nodes[0].id);
					$("#" + tg + "Name").val(nodes[0].name);
				} else {
					$("#" + tg + "Id").val("");
					$("#" + tg + "Name").val("");
				}
			}

		}
	};

	$("body")
			.append(
					'<div id="'
							+ tg
							+ 'Content" class="menuContent" style="display: none; position: absolute;"> <ul id="'
							+ tg
							+ 'Tree" class="ztree" style="margin-top: 0; width: 180px; height: 300px;"></ul> </div>"');
	$.getJSON(url, {
		"times" : Date.parse(new Date())
	}, function(results) {
		if(results.length==0){
			parent.mac.alert("类别树图无数据，请在应急资源类别中初始化");
		}
		$.fn.zTree.init($("#"+tg+"Tree"), setting, results);
		// $.fn.zTree.init($("#treeDemo"), setting, results);
		// $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
	});
	// $.fn.zTree.init($("#districtTree"),
	// setting,$.parseJSON('${districtJson}'));
	$("#" + tg + "Name").click(function() {
		showMenuTree(tg + "Name", tg + "Content", function(event) {
			checkMenu(tg + "Name", tg + "Content", event);
		});
		defaultSelect(tg + "Tree", tg + "Id");
	});

}
