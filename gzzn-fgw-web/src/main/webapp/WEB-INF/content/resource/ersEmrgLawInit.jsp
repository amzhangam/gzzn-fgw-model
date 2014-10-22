<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>法律法规</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getTree.js"
	type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍
		initMyTree("classify","getClassifyTreeJson.json?catalogue=5");
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#classifyId").attr('value', '${editObj.classifyId}');
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#keyword").charCount({ 
			allowed: 200
		}); 
		$("#summary").charCount({ 
			allowed: 500
		}); 
		$("#eventdesc2").charCount({ 
			allowed: 200
		}); 
		$("#appliedrange").charCount({ 
			allowed: 200
		}); 
		$("#content").charCount({ 
			allowed: 4000
		}); 
		$("#notes").charCount({ 
			allowed: 200
		}); 
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.name" : {
					required : true,
					byteMaxLength : 60
				},
				"dto.nucode" : {
					byteMaxLength : 32
				},
				"dto.keyword" : {
					byteMaxLength : 200
				},
				"dto.summary" : {
					byteMaxLength : 500
				},
				"dto.dounit" : {
					byteMaxLength : 60
				},
				"dto.eventdesc2" : {
					byteMaxLength : 200
				},
				"dto.appliedrange" : {
					byteMaxLength : 200
				},
				"dto.content" : {
					byteMaxLength : 4000
				},
				"dto.notes" : {
					byteMaxLength : 200
				}
			},
			messages : {
				"dto.name" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.nucode" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.keyword" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.summary" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dounit" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.eventdesc2" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.appliedrange" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.content" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersEmrgLawSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac.alert(data.info);
															window.location.href = "${ctx }/resource/ersEmrgLawQuery.ac";
														} else {
															parent.mac.alert(data.info);
														}
													},
													error : function() {
														parent.mac.alert("系统响应失败");
													}
												});
								return false; //此处必须返回false，阻止常规的form提交
							}
						});
	});
</script>

</head>
<style type="text/css">
body {
	margin: 0px;
	margin-left: 0px;
	padding: 0px;
}
</style>
<body style="overflow: hidden">
	<form action="${ctx }/resource/ersEmrgLawSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					法律信息录入
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" value="保存"  id="save" />
						 <input type="button" class="btn" id="resetBtn" value="重置"> 
					     <input type="button" class="btn" value="返回" id="return" name="return" onclick="history.go(-1)" />
						</span>
					</div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<table align="center" class="editTab" border="0" width="100%">
			<tbody>
				<tr>
					<th>法律法规名称<span style="color: red">&nbsp;*</span></th>
					<td><input type="text" class="text" name="dto.name" value="${editObj.name }"/>
					</td>
					<th>类别名称</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" readonly="readonly" id="classifyName" />
					</td>
				</tr>
				<tr>
					<th>行政区划</th>
					<td><input id="districtName" name='dto.districtName' type="text" readonly="readonly"
					value="${editObj.districtName }" /><input id="districtId"
					name='dto.districtId' type="hidden" value="${editObj.districtId }" /></td>
					
				</tr>
				<tr>
					<th>指定单位</th>
					<td><input type="text" class="text" name="dto.dounit"
						id='dounit' value="${editObj.dounit}" />
					</td>
					<th>编码</th>
					<td><input type="text" class="text" name="dto.nucode"
						id='nucode' value='${editObj.nucode }' />
					</td>
				</tr>
				<tr>
					<th>关键字</th>
					<td colspan="3"><textarea name="dto.keyword" cols="100" rows="2"
							id='keyword'>${editObj.keyword}</textarea>
					</td>
				</tr>
				<tr>
					<th>摘要</th>
					<td colspan="3"><textarea name="dto.summary" cols="100" rows="2"
							id='summary'>${editObj.summary}</textarea>
					</td>
				</tr>
					<tr>
					<th>适用事件描述</th>
					<td colspan="3"><textarea name="dto.eventdesc2" cols="100" rows="2"
							id='eventdesc2'>${editObj.eventdesc2}</textarea>
					</td>
				</tr>
					<tr>
					<th>适用范围</th>
					<td colspan="3"><textarea name="dto.appliedrange" cols="100" rows="2"
							id='appliedrange'>${editObj.appliedrange}</textarea>
					</td>
				</tr>
				<tr>
					<th>发布时间</th>
					<td><input type="text" value="${editObj.pubdate }"
						name="dto.pubdate" id='pubdate'
						onclick="WdatePicker({errDealMode:2 ,maxDate:'#F{$dp.$D(\'inuredate\')}'})"
						class="Wdate" />
					</td>
					<th>有效时间</th>
					<td><input type="text" name="dto.inuredate" id='inuredate'
						value="${editObj.inuredate }"
						onclick="WdatePicker({errDealMode:2 ,minDate:'#F{$dp.$D(\'pubdate\')}'})"
						class="Wdate" />
					</td>
				</tr>
				<tr>
					<th>内容</th>
					<td colspan="3"><textarea cols="100" rows="3" id='content'
							name='dto.content'>${editObj.content }</textarea>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="2"
							id='notes'>${editObj.notes}</textarea>
					</td>
				</tr>
		</table>
		<div class="ctrls_bar">
			<input type="hidden"
				name='lawid' value='${editObj.lawid }' />
		</div>
	</form>
	<script type="text/javascript">
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

		//行政区域树
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
		$(function() {
			$.getJSON("${ctx }/resource/getDistrictTreeJson.json", {
				"times" : Date.parse(new Date())
			}, function(results) {
				$.fn.zTree.init($("#districtTree"), setting, results);
				//$.fn.zTree.init($("#treeDemo"), setting, results);
				//$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
			});
			//$.fn.zTree.init($("#districtTree"), setting,$.parseJSON('${districtJson}'));
			$("#districtName").click(function() {
				showMenu("districtName", "districtContent", function(event) {
					checkMenu("districtName", "districtContent", event);
				});
				defaultSelect("districtTree", "districtId");
			});
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
				var node = zTree.getNodeByParam("id", $("#" + valueId).val(),
						null);
				if (node != null) {
					zTree.expandNode(node.getParentNode());
					zTree.checkNode(node, true, null, true);
				}
			}
		}
	</script>
	<div id="districtContent" class="menuContent"
		style="display: none; position: absolute;">
		<ul id="districtTree" class="ztree"
			style="margin-top: 0; width: 180px; height: 300px;"></ul>
	</div>
</body>
</html>