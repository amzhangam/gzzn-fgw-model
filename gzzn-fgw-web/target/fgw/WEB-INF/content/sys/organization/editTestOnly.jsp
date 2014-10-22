<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
<c:if test="${not empty obj.organizationId}" var="result">
	编辑机构信息
</c:if>
<c:if test="${!result}">
	新增机构信息
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>

<script type="text/javascript">
	<!--
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
						enable: true
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
				v = "";
				for (var i=0, l=nodes.length; i<l; i++) {
					v += nodes[i].name + ",";
				}
				if (v.length > 0 ) v = v.substring(0, v.length-1);
				//var cityObj = $("#citySel");
				//cityObj.attr("value", v);
			}
			
			
			
			function onBodyDown(event, clickId, treeSelDivId){
			    $("#msg").html($("#msg").html()+"==========="+clickId+"==="+treeSelDivId+"<br>");
				  var treeDiv = $("#"+treeSelDivId);
					$("#msg").html($("#msg").html()+"2、进入绑定区<br>");
					if (!(event.target.id == clickId || event.target.id == treeSelDivId || $(event.target).parents("#"+treeSelDivId).length>0)) {
						treeDiv.fadeOut("fast");
						$("#msg").html($("#msg").html()+"3、隐藏掉这个<br>");
						$("body").unbind("mousedown", function(){onBodyDown(event, clickId, treeSelDivId);});
						//alert("==隐藏掉这个==");
						$("#msg").html($("#msg").html()+"4、移除绑定<br>");
					}
					$("#msg").html($("#msg").html()+"5、绑定区末尾<br>");
			}
			
			
			function showTreeSel(clickId,treeSelDivId){
				var offset,h;
				var treeDiv = $("#"+treeSelDivId);
				var onBodyDown = function(event){//定义绑定事件
					 $("#msg").html($("#msg").html()+"==========="+clickId+"==="+treeSelDivId+"<br>");
						$("#msg").html($("#msg").html()+"2、进入绑定区<br>");
						if (!(event.target.id == clickId || event.target.id == treeSelDivId || $(event.target).parents("#"+treeSelDivId).length>0)) {
							treeDiv.fadeOut("fast");
							$("#msg").html($("#msg").html()+"3、隐藏掉这个<br>");
							$("body").unbind("mousedown", onBodyDown);
							//alert("==隐藏掉这个==");
							$("#msg").html($("#msg").html()+"4、移除绑定<br>");
						}
						$("#msg").html($("#msg").html()+"5、绑定区末尾<br>");
				};
				$("#"+clickId).bind("click",function(event){
					$("#msg").html($("#msg").html()+"<br>1、点击进来了<br>");
				
					offset = $(this).offset();
					h = $(this).outerHeight();
					treeDiv.css({"left":offset.left + "px" ,"top":offset.top + h + "px" }).slideDown("fast");
					
					//$("body").bind("mousedown", function(){onBodyDown(event, clickId, treeSelDivId);});
					$("body").bind("mousedown", onBodyDown);
				});
				/**$("body").bind("mousedown", function(event){
					$("#msg").html($("#msg").html()+"2、进入绑定区<br>");
					//alert("==进入绑定区==");
					if (!(event.target.id == clickId || event.target.id == treeSelDivId || $(event.target).parents("#"+treeSelDivId).length>0)) {
						treeDiv.fadeOut("fast");
						$("#msg").html($("#msg").html()+"3、隐藏掉这个<br>");
						//alert("==隐藏掉这个==");
						$("body").unbind("mousedown"); 
						$("#msg").html($("#msg").html()+"4、移除绑定<br>");
					}
					$("#msg").html($("#msg").html()+"5、绑定区末尾<br>");
				});*/
				
			}
			
			
			
			
			
			
		/**	function showTreeSel(id,treeSelDivId) {
				alert(id+"==SHOW=="+treeSelDivId);
				var obj = $("#"+id);
				var objOffset = $("#"+id).offset();
				$("#"+treeSelDivId).css({left:objOffset.left + "px", top:objOffset.top + obj.outerHeight() + "px"}).slideDown("fast");
				//$("body").bind("mousedown", onBodyDown);
				$("body").bind("mousedown", onBodyDown);
			}
			
			function hideTreeSel(id,treeSelDivId) {
				alert(id+"==HIDE=="+treeSelDivId);
				$("#"+treeSelDivId).fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
		
			function onBodyDown(event, id, treeSelDivId) {
				alert(id+"==onBodyDown=="+treeSelDivId);
				if (!(event.target.id == "menuBtn" || event.target.id == id || event.target.id == treeSelDivId || $(event.target).parents("#"+treeSelDivId).length>0)) {
					hideTreeSel(id,treeSelDivId);
				}
			}*/
			
			
			
			
			
			
		var zNodes =[
			{id:1, pId:0, name:"自定义事件类型", open:true, nocheck:true},
			{id:2, pId:1, name:"自然灾害", nocheck:true},
			{id:21, pId:2, name:"水旱灾害", nocheck:true},
			{id:2100, pId:21, name:"洪水"},
			{id:2101, pId:21, name:"内涝"},
			{id:2102, pId:21, name:"水库重大险情"},
			{id:2103, pId:21, name:"堤防重大险情"},
			{id:2104, pId:21, name:"凌汛灾害"},
			{id:2105, pId:21, name:"山洪灾害"},
			{id:2106, pId:21, name:"农业干旱"},
			{id:2107, pId:21, name:"城镇缺水"},
			{id:2108, pId:21, name:"生态干旱"},
			{id:2109, pId:21, name:"农村人畜饮水困难"},
			{id:2110, pId:21, name:"其它水旱灾害"},
			{id:22, pId:2, name:"气象灾害", nocheck:true},
			{id:2200, pId:22, name:"台风"},
			{id:2201, pId:22, name:"龙卷风"},
			{id:2202, pId:22, name:"暴雨"},
			{id:2203, pId:22, name:"雪灾"},
			{id:2204, pId:22, name:"寒潮"},
			{id:2205, pId:22, name:"大风"},
			{id:2206, pId:22, name:"沙尘暴"},
			{id:2207, pId:22, name:"低温冻害"},
			{id:2208, pId:22, name:"冻雨"},
			{id:2209, pId:22, name:"高温天气"},
			{id:2210, pId:22, name:"热浪"},
			{id:2211, pId:22, name:"干热风"},
			{id:2212, pId:22, name:"下击暴流"},
			{id:2213, pId:22, name:"雪崩"},
			{id:2214, pId:22, name:"雷电"},
			{id:2215, pId:22, name:"冰雹"},
			{id:2216, pId:22, name:"霜冻"},
			{id:2217, pId:22, name:"大雾"},
			{id:2218, pId:22, name:"灰霾"},
			{id:2219, pId:22, name:"低空风切变"},
			{id:2220, pId:22, name:"其它气象灾害"},
			{id:23, pId:2, name:"地震灾害", nocheck:true},
			{id:2300, pId:23, name:"人工地震"},
			{id:2301, pId:23, name:"天然地震"},
			{id:2302, pId:23, name:"其它地震灾害"},
			{id:24, pId:2, name:"地质灾害", nocheck:true},
			{id:2400, pId:24, name:"滑坡"},
			{id:2401, pId:24, name:"泥石流"},
			{id:2402, pId:24, name:"山体崩塌"},
			{id:2403, pId:24, name:"地裂缝"},
			{id:2404, pId:24, name:"地面沉降"},
			{id:2405, pId:24, name:"火山喷发"},
			{id:2406, pId:24, name:"其它地质灾害"},
			{id:25, pId:2, name:"海洋灾害", nocheck:true},
			{id:2501, pId:25, name:"海啸"},
			{id:2502, pId:25, name:"风暴潮"},
			{id:2503, pId:25, name:"海水"},
			{id:2504, pId:25, name:"巨浪"},
			{id:2505, pId:25, name:"赤潮"},
			{id:2506, pId:25, name:"其它海洋灾害"},
			{id:26, pId:2, name:"生物灾害", nocheck:true},
			{id:2601, pId:26, name:"农业病害"},
			{id:2602, pId:26, name:"农业虫害"},
			{id:2603, pId:26, name:"农业草害"},
			{id:2604, pId:26, name:"农业鼠害"},
			{id:2605, pId:26, name:"森林病害"},
			{id:2606, pId:26, name:"森林虫害"},
			{id:2607, pId:26, name:"森林鼠害"},
			{id:2608, pId:26, name:"农业转基因生物安全突发事件"},
			{id:2609, pId:26, name:"林业转基因生物安全突发事件"},
			{id:2610, pId:26, name:"林业有害植物事件"},
			{id:2611, pId:26, name:"外来有害动植物威胁农业生产事件"},
			{id:2612, pId:26, name:"外来有害动植物威胁林业生产事件"},
			{id:2613, pId:26, name:"其它生物灾害"},
			{id:27, pId:2, name:"森林草原火灾", nocheck:true},
			{id:2700, pId:27, name:"其它生物灾害"},
			{id:2701, pId:27, name:"森林草原火灾"},
			{id:28, pId:2, name:"其它自然灾害事件"},
			{id:3, pId:1, name:"事故灾难",nocheck:true},
			{id:4, pId:1, name:"公共卫生事件",nocheck:true},
			{id:5, pId:1, name:"社会安全事件",nocheck:true},
			{id:51, pId:5, name:"群体性事件",nocheck:true},
			{id:52, pId:5, name:"刑事案件",nocheck:true},
			{id:5100, pId:51, name:"非法集会游行",nocheck:true},
			{id:6, pId:1, name:"其它突发事件"}

		 ];
			
		
		
	   $(document).ready(function() {
	   		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	   		$.fn.zTree.init($("#treeDemo2"), setting, zNodes);
	   		showTreeSel("treeSel","treeSelDiv");
	   		showTreeSel("treeSel2","treeSelDiv2");
	   
	   
	   
			showMenu(6,62);
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.dutyName":{   
							             required: true,
							             byteMaxLength:50
							         },
							         "obj.dutyDesc":{   
							             required: false,
							             byteMaxLength:100
							         }
							    },
							    messages: {
							    	 "obj.dutyName":{   
							             byteMaxLength:"不能全为空格且字数最多是25"
							         },
							         "obj.dutyDesc":{
							             byteMaxLength:"不能全为空格且字数最多是50"
							         }
							    },
							    submitHandler: function(form){
							    	form.submit();
							    }		 	    
							});
			
			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
   			});
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/duty/list.ac";
			});
			
		});
		
	
	//-->
</script>
</head>
<body>
	<form id="editForm"  method="post">
		<input type="hidden" name="obj.organizationId" value="${obj.organizationId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.organizationId}" var="result">
						编辑机构信息
					</c:if>
					<c:if test="${!result}">
						新增机构信息
					</c:if>
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						 <input type="submit" class="btn" id="saveBtn" value="保存" /> 
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
		<div class="editDiv">
			<table class="editTab" id="editTab">
				<tr>
					<th>机构名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.organizationName" id="organizationName" value="${obj.organizationName}" />
					</td>
				</tr>
				<tr>
					<th>上级机构<font class="msg">*</font></th>
					<td>
						<input type="text" id="treeSel" value="" />
						<input type="text" name="obj.organUpId" id="organUpId" value="${obj.organUpId}" />
						
						<div id="treeSelDiv" class="menuContent" style="display:none;position: absolute;">
							<ul id="treeDemo" class="ztree" style="margin-top:0; width:250px; height: 400px;"></ul>
						</div>
					</td>
				<tr>
					<th>上级机构2<font class="msg">*</font></th>
					<td>
						<input type="text" id="treeSel2" value="" />
						<input type="text" name="obj.organUpId" id="organUpId" value="${obj.organUpId}" />
						
						<div id="treeSelDiv2" class="menuContent" style="display:none;position: absolute;">
							<ul id="treeDemo2" class="ztree" style="margin-top:0; width:250px; height: 400px;"></ul>
						</div>
					</td>
				</tr>
				<tr><td id="msg"></td></tr>
			</table>
		</div>
	</form>
</body>
</html>