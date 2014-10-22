$(function() {
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
			onClick : onClick
		}
	};

	// 行政区域树
	setting.callback.onCheck = function(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		nodes = zTree.getCheckedNodes(true);
		if (nodes.length != 0) {
			$("#districtId").val(nodes[0].id);
			$("#districtName").val(nodes[0].name);
		} else {
			$("#districtId").val("");
			$("#districtName").val("");
		}

	};

	$("body")
			.append(
					'<div id="districtContent" class="menuContent" style="display: none; position: absolute;"> <ul id="districtTree" class="ztree" style="margin-top: 0; width: 180px; height: 300px;"></ul> </div>"');
	$.getJSON("getDistrictTreeJson.json", {
		"times" : Date.parse(new Date())
	}, function(results) {
		$.fn.zTree.init($("#districtTree"), setting, results);
		// $.fn.zTree.init($("#treeDemo"), setting, results);
		// $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
	});
	// $.fn.zTree.init($("#districtTree"),
	// setting,$.parseJSON('${districtJson}'));
	$("#districtName").click(function() {
		showMenu("districtName", "districtContent", function(event) {
			checkMenu("districtName", "districtContent", event);
		});
		defaultSelect("districtTree", "districtId");
	});

	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function showMenu(targetId, contentId, onBodyDown) {
		var obj = $("#" + targetId);
		var offset = obj.offset();
		$("#" + contentId).css({
			left : offset.left + "px",
			top : offset.top + obj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu(targetId, contentId) {
		$("#" + contentId).fadeOut("fast");
	}
	function checkMenu(targetId, contentId, event) {
		if (!(event.target.id == "menuBtn" || event.target.id == targetId
				|| event.target.id == contentId || $(event.target).parents(
				"#" + contentId).length > 0)) {
			hideMenu(targetId, contentId);
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
});