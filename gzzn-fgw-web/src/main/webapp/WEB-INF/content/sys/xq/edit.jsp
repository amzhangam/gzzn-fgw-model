<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
<c:if test="${not empty obj.xqId}" var="result">
	编辑辖区信息
</c:if>
<c:if test="${!result}">
	新增辖区信息
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
			var xqDatas = getJsonDatas("${ctx}/com/getSysXqJson.json");
	   		var radioTreeObj = initRadioTreeCheck("xqUp", setting, xqDatas, "${obj.xqId}", "${obj.sjxq.xqId}");//初始化单选的树形下拉框
			
			
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
							         "obj.xqmc":{   
							             required: true,
							             byteMaxLength:50
							         },
							         "obj.xzqydm":{  
							          	 required: true, 
							             byteMaxLength:50
							         },
							         "obj.xqxxmc":{  
							          	 required: true, 
							             byteMaxLength:200
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
       			 showNodesNameById(radioTreeObj, "xqUp", "${obj.sjxq.xqId}");
   			});
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/xq/list.ac";
			});
			
		});
		
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/xq/save.ac" method="post">
		<input type="hidden" name="obj.xqId" value="${obj.xqId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.xqId}" var="result">
						编辑辖区信息
					</c:if>
					<c:if test="${!result}">
						新增辖区信息
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
					<th>辖区名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.xqmc" id="xqmc" value="${obj.xqmc}" />
					</td>
				</tr>
				<tr>
					<th>行政区域代码<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.xzqydm" id="xzqydm" value="${obj.xzqydm}" />
					</td>
				</tr>
				<tr>
					<th>辖区详细名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.xqxxmc" id="xqxxmc" value="${obj.xqxxmc}" /> &nbsp;&nbsp;<font color="green">格式如：广东省-广州市-天河区</font>
					</td>
				</tr>
				<tr>
					<th>上级辖区</th>
					<td>
						<input type="text" id="xqUpSelName" value="" readonly="readonly" />
						<input type="hidden" name="obj.sjxq.xqId" id="xqUpSelID" value="${obj.sjxq.xqId}"/>
						<div id="xqUpDiv" class="menuContent" style="display:none; position: absolute;width:212px;height: 250px;">
							<ul id="xqUp" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>