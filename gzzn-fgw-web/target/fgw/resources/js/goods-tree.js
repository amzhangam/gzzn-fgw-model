var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
					rootPId: 0
				}
			},
			callback: {
				onClick: onClick,
				onCheck: onCheck
			}
		};

		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			name = "";
			value = ""
			for (var i=0, l=nodes.length; i<l; i++) {
				name += nodes[i].name + ",";
				value += nodes[i].id + ",";
			}
			if (name.length > 0 ) name = name.substring(0, name.length-1);
			if (value.length > 0 ) value = value.substring(0, value.length-1);
			$("#goodsName").val(name);
			$("#goodsId").val(value);
		}

		function showMenu(inputId) {
			var obj = $("#goodsName");
			var offset = $("#goodsName").offset();
			$("#menuContent").css({left:offset.left + "px", top:offset.top + obj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "goodsName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		function defaultSelect(tag){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			if(tag){
				var node = zTree.getNodeByParam("name", $("#goodsName").val(), null);
				if(node != null){
					zTree.expandNode(node.getParentNode());
					zTree.checkNode(node,true,null,true);
				}
			}else{
				var nodes = zTree.getNodes();
				if(nodes.length > 0){
					zTree.expandNode(nodes[0]);
				}
			}
		}