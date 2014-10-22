<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>
<c:if test="${not empty obj.organizationId}" var="result">
	编辑单位信息
</c:if>
<c:if test="${!result}">
	新增单位信息
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
	   		showMenu(6,63);
	   		//如果当前为编辑单位信息的话，“单位类型”将不可编辑
	   		if("${not empty obj.organizationId}"=="true"){
	   			$("#workunitstypeSelName").attr("disabled",true);
				$("#workunitstypeSelName").css("background","#EEEEEE");
	   		}
	   		
	   		//单位类型
			var workunitstypeDatas = getJsonDatas("${ctx}/com/getOrganTypeJson.json","");
			var workunitstypeTree = initRadioTreeCheck("workunitstype", setting, workunitstypeDatas, "", "${obj.workunitstype}");
			//单位性质
			var dwxzDatas = getJsonDatas("${ctx}/com/getDictItemsJson.json","params.dictName=单位性质");
			var dwxzTree = initRadioTreeCheck("dwxz", setting, dwxzDatas, "", "${obj.workunitsquality}");
			//区域
			var xqIdDatas = getJsonDatas("${ctx}/com/getSysXqJson.json","");
			var xqIdTree = initRadioTreeCheck("xqId", setting, xqIdDatas, "", "${obj.sysXq.xqId}");
		
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
									 "workunitstypeName":{   
						                 required: true
						             },
						             "obj.organizationName":{   
						                 required: true,
						                 byteMaxLength: 150
						             },
						              "dwxzName":{   
						                 required: true
						             },
						              "xqIdName":{   
						                 required: true
						             },
						             "obj.workunitsregistercode":{   
						                 remote:{
							                 	type:"POST",
							                 	dataType:"JSON",
							                 	url:"./checkRegistCode.json",
							                 	data:{
							                 		"obj.organizationId":function(){
							                 			return $("#organizationId").val();
							                 		},
							                 		time:new Date().getTime()
							                 	}
							             },
						                 byteMaxLength:40
						             },
						             "obj.workunitsperson":{   
						                 byteMaxLength:50
						             },
						             "obj.workunitslinkman":{   
						                 byteMaxLength:50
						             },
						             "obj.workunitslinkmantel":{   
						                 byteMaxLength:50
						             }, 
						             "obj.workunitsaddress":{   
						                 byteMaxLength:300
						             }, 
						             "verfCode":{   
						                 required: true,
						                 remote:{
						                 	type:"POST",
						                 	dataType:"JSON",
						                 	url:"./checkVerfCodeImage.json",
						                 	data:{
						                 		time:new Date().getTime()
						                 	}
						                 }
						             }
						        },
							    messages:{
							      "obj.workunitsregistercode":{
							    	  remote:"该企业工商注册号已注册，请不要重复注册"
							      }
							    },
							    submitHandler: function(form){
							    	form.submit();
							    }		 	    
							});
			
			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
       			 clearCheckNodes(workunitstypeTree);
       			 showNodesNameById(workunitstypeTree, "workunitstype", "${obj.workunitstype}");
       			 clearCheckNodes(dwxzTree);
       			 showNodesNameById(dwxzTree, "dwxz", "${obj.workunitsquality}");
       			 clearCheckNodes(xqIdTree);
       			 showNodesNameById(xqIdTree, "xqId", "${obj.sysXq.xqId}");
       			 
   			});
   			//返回
			$("#backBtn").click(function() {
				//window.location = "${ctx}/sys/organNew/list.ac";
				history.back(-1);
			});
   			
		});
		
	
	//-->
	
	////刷新验证码
	//function refresh() {
	//	document.getElementById("imgCode").src = "${ctx}/servlet/VerfCodeImageServlet?"+Math.random();
	//}
	
</script>

	<form id="editForm" action="${ctx}/sys/organNew/save.ac"  method="post">
		<s:token/>
		<input type="hidden" name="obj.organizationId" id="organizationId" value="${obj.organizationId}"/>
		<input type="hidden" name="obj.workunitsstatus" id="workunitsstatus" value="${obj.workunitsstatus}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.organizationId}" var="result">
						编辑单位信息
					</c:if>
					<c:if test="${!result}">
						新增单位信息
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
					<th>单位类型<font class="msg">*</font></th>
					<td>
						<input type="text" name="workunitstypeName" id="workunitstypeSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="obj.workunitstype" id="workunitstypeSelID" value="${obj.workunitstype}"/>
						<div id="workunitstypeDiv" class="menuContent" style="display:none; position: absolute;width:211px;height: 250px;">
							<ul id="workunitstype" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>单位名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.organizationName" id="organizationName" value="${obj.organizationName}" />
					</td>
				</tr>
				<tr>
					<th>单位性质<font class="msg">*</font></th>
					<td>
						<input type="text" name="dwxzName" id="dwxzSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="obj.workunitsquality" id="dwxzSelID" value="${obj.workunitsquality}"/>
						<div id="dwxzDiv" class="menuContent" style="display:none; position: absolute;width:210px;height: 250px;">
							<ul id="dwxz" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>所在区域<font class="msg">*</font></th>
					<td>
						<input type="text" name="xqIdName" id="xqIdSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="obj.sysXq.xqId" id="xqIdSelID" value="${obj.sysXq.xqId}"/>
						<div id="xqIdDiv" class="menuContent" style="display:none; position: absolute;width:210px;height: 250px;">
							<ul id="xqId" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>企业工商注册号(机构代码)</th>
					<td>
						<input type="text" name="obj.workunitsregistercode" id="workunitsregistercode" value="${obj.workunitsregistercode}" />
					</td>
				</tr>
				<tr>
					<th>单位法人</th>
					<td>
						<input type="text" name="obj.workunitsperson" id="workunitsperson" value="${obj.workunitsperson}" />
					</td>
				</tr>
				<tr>
					<th>单位联系人</th>
					<td>
						<input type="text" name="obj.workunitslinkman" id="workunitslinkman" value="${obj.workunitslinkman}" />
					</td>
				</tr>
				<tr>
					<th>联系人电话</th>
					<td>
						<input type="text" name="obj.workunitslinkmantel" id="workunitslinkmantel" value="${obj.workunitslinkmantel}" />
					</td>
				</tr>
				<tr>
					<th>单位地址</th>
					<td>
						<input type="text" name="obj.workunitsaddress" id="workunitsaddress" value="${obj.workunitsaddress}" />
					</td>
				</tr>
				<!--tr>
					<th>验证码：</th>
					<td>
						<input type="text"  class="TxtInput" style="width: 61px;vertical-align: top;height: 29px;" id="verfCode" name="verfCode"  value=""/>
						<img id="imgCode" title="点击更换" onclick="javascript:refresh();"  src="${ctx}/servlet/VerfCodeImageServlet"/>
					</td>
				</tr-->
			</table>
		</div>
	</form>
</body>
</html>