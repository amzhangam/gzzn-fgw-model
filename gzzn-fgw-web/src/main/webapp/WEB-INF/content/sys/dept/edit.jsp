<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<title>
<c:if test="${not empty obj.deptId}" var="result">
	编辑部门信息
</c:if>
<c:if test="${!result}">
	新增部门信息
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
	   		showMenu(6,64);
	   		//上级部门
	   		var deptDatas = getJsonDatas("${ctx}/com/getOrgDeptTreeJson.json","params.workunitstype=3&params.nocheckOrg=false");
	   		var radioTreeObj = initRadioTreeCheck("deptTree", setting, deptDatas, "${obj.deptId}", "${orgOrUpDept}");//初始化单选的树形下拉框
			
			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
       			  //重置树形下拉框的相关选项：先清空【clearCheckNodes】，再设置对应的值【showNodesNameById】
       			 clearCheckNodes(radioTreeObj);
       			 showNodesNameById(radioTreeObj, "deptTree", "${orgOrUpDept}");
   			});
   			
		
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.deptname":{   
							             required: true,
							             remote:{
							                 	type:"POST",
							                 	dataType:"JSON",
							                 	url:"./checkDeptRepeat.json",
							                 	data:{
							                 		"obj.deptId":function(){
							                 			return $("#deptId").val();
							                 		},
							                 		time:new Date().getTime()
							                 	}
							             },
							             byteMaxLength:50
							         },
							          "obj.fullcode":{   
							          	 required: true,
							          	 remote:{
							                 	type:"POST",
							                 	dataType:"JSON",
							                 	url:"./checkDeptRepeat.json",
							                 	data:{
							                 		"obj.deptId":function(){
							                 			return $("#deptId").val();
							                 		},
							                 		time:new Date().getTime()
							                 	}
							             },
							             byteMaxLength:20
							         },
							          "parentdeptname":{   
							          	 required: true
							         },
							          "obj.description":{   
							             byteMaxLength:500
							         }
							    },
							   messages: {
							    	 "obj.deptname":{   
							            remote:"该部门名称已存在，请重新输入"
							         },
							         "obj.fullcode":{
							            remote:"该部门代码已存在，请重新输入"
							         }
							    },
							    submitHandler: function(form){
							    	form.submit();
							    }		 	    
							});
			
   			//返回
			$("#backBtn").click(function() {
				//window.location = "${ctx}/sys/dept/list.ac";
				history.back(-1);
			});
			
		});
	
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/dept/save.ac"  method="post">
		<input type="hidden" name="obj.deptId" id="deptId" value="${obj.deptId}"/>
		<input type="hidden" name="obj.levelnumber" id="levelnumber" value="${obj.levelnumber}"/>
		<input type="hidden" name="obj.deleted" value="${obj.deleted==null?0:obj.deleted}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.deptId}" var="result">
						编辑部门信息
					</c:if>
					<c:if test="${!result}">
						新增部门信息
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
					<th>部门名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.deptname" id="deptname" value="${obj.deptname}" />
					</td>
				</tr>
				<tr>
					<th>部门代码<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.fullcode" id="fullcode" value="${obj.fullcode}" />
					</td>
				</tr>
				<tr>
					<th>所属单位或上级部门<font class="msg">*</font></th>
					<td><!-- 所属单位或上级部门字段进行特殊的处理：如果当前记录的上级部门为空的话，那就设置单位信息给该字段 -->
						<input type="text" name="parentdeptname" id="deptTreeSelName" value="" readonly="readonly" /> 
						<input type="hidden" name="orgOrUpDept" id="deptTreeSelID" value="${orgOrUpDept}"/>
						<div id="deptTreeDiv" class="menuContent" style="display:none; position: absolute; width:260px; height: 300px;">
							<ul id="deptTree" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>是否投资处</th>
					<td>
						<c:if test="${obj.sftzc eq 1}" var="result">
							<input type="radio" name="obj.sftzc" value="1" class="radio" checked="checked">&nbsp;是
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="obj.sftzc" value="0" class="radio">&nbsp;否
						</c:if>
						<c:if test="${!result}">
							<input type="radio" name="obj.sftzc" value="1" class="radio">&nbsp;是
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="obj.sftzc" value="0" class="radio" checked="checked">&nbsp;否
						</c:if>
					</td>
				</tr>
				<tr>
					<th>是否显示</th>
					<td>
						<c:if test="${obj.sfxs eq 0}" var="result">
							<input type="radio" name="obj.sfxs" value="1" class="radio">&nbsp;显示
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="obj.sfxs" value="0" class="radio" checked="checked">&nbsp;不显示
						</c:if>
						<c:if test="${!result}">
							<input type="radio" name="obj.sfxs" value="1" class="radio" checked="checked">&nbsp;显示
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="obj.sfxs" value="0" class="radio">&nbsp;不显示
						</c:if>
					</td>
				</tr>
				<tr>
					<th>部门描述</th>
					<td>
						<textarea style="width:70%;height:auto;" rows="5" name="obj.description" id="description">${obj.description}</textarea>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>