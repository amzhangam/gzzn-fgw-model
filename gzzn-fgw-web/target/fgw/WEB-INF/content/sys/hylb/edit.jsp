<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
<c:if test="${not empty obj.hylbId}" var="result">
	编辑行业分类信息
</c:if>
<c:if test="${!result}">
	新增行业分类信息
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>

<script type="text/javascript">
//<!--
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
					onCheck: onRadioCheck
				}
			};
			
	   $(document).ready(function() {
			showMenu(6,65);
			
			var hylbDatas = getJsonDatas("${ctx}/com/getXmsbHylbJson.json","");
	   		var radioTreeObj = initRadioTreeCheck("hylbUp", setting, hylbDatas, "${obj.hylbId}", "${obj.xmsbHylb.hylbId}");//初始化单选的树形下拉框
			
			
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.hylbmc":{   
							             required: true,
							             byteMaxLength:50
							         },
							         "obj.hylbdm":{   
							             byteMaxLength:50
							         }
							    },
							    submitHandler: function(form){
							    	form.submit();
							    }		 	    
							});
			
   			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
       			 //重置树形下拉框的相关选项：先清空【clearCheckNodes】，再设置对应的值【showNodesNameById】
       			 clearCheckNodes(radioTreeObj);
       			 showNodesNameById(radioTreeObj, "hylbUp", "${obj.xmsbHylb.hylbId}");
   			});
   			
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/hylb/list.ac";
			});
			
		});
		
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/hylb/save.ac" method="post">
		<input type="hidden" name="obj.hylbId" value="${obj.hylbId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.hylbId}" var="result">
						编辑行业分类信息
					</c:if>
					<c:if test="${!result}">
						新增行业分类信息
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
					<th>行业名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.hylbmc" id="hylbmc" value="${obj.hylbmc}" />
					</td>
				</tr>
				<tr>
					<th>行业代码</th>
					<td>
						<input type="text" name="obj.hylbdm" id="hylbdm" value="${obj.hylbdm}" />
					</td>
				</tr>
				<tr>
					<th>上级行业名称</th>
					<td>
						<input type="text" id="hylbUpSelName" value="" readonly="readonly" />
						<input type="hidden" name="obj.xmsbHylb.hylbId" id="hylbUpSelID" value="${obj.xmsbHylb.hylbId}"/>
						<div id="hylbUpDiv" class="menuContent" style="display:none; position: absolute;width:250px;">
							<ul id="hylbUp" class="ztree"  style="margin-top:0; width:200px; height: 250px;"></ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>