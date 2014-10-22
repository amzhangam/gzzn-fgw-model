<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>短信信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/2_event.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<script type="text/javascript" src="${ctx}/resources/js/tc.all.js"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/dx/lxrlist.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<!-- 工程名 -->
<input type="hidden" value="<%=request.getContextPath()%>" id="ctx" />
</head>
<body>
	<script type="text/javascript">
		//<!--
		$(document).ready(function() {

			showMenu(1, 13);

			$("#queryBtn").click(function() {
				$("#currentPage").val("0");
				$("form")[0].submit();
			});

			$("#sendBtn").click(function() {
				window.location = "${ctx}/sys/dx/edit.ac";
			});

			$("#dxmb").change(function() {
				if ($("#dxmb").val() != "") {
					$.post("${ctx}/sys/dx/getSfnr.json", {
						"dxmbId" : $("#dxmb").val()
					}, function(data) {
						var json = $.parseJSON(data);
						$("#sfnr").val(json.msg);
					});
				}
			});
			checkAllBox();
			var validator1 = $("#sendForm").validate({
				 event:"submit",
				 rules: {
					 "sfnr":{   
		                byteMaxLength:100
		            }
			      },
			      messages:{
			    	  "sfnr":{
			    		  byteMaxLength:"不超过50个汉字"
			    	  }
			      },
			      submitHandler: function(form){
			    	form.submit();
			      }	
			});
		});
		//-->   

		function checkLxr() {
			var ids = "";
			$(".subcheck").each(function() {
				if ($(this).attr("checked")) {
					ids += "@" + $(this).val();
				}
			});
			if (ids.length == 0) {
				mac.alert("请选择要发送的联系人");
				return;
			}
			var sfnr = $("#sfnr").val();
			if (sfnr == null || sfnr == '') {
				alert("请输入短信内容");
				return;
			}
			ids = ids.substring(1);
			//alert(ids);
			mac.confirm('<p>确认要发送短信给已选中的联系人吗?</p>', function() {
				$.post("${ctx}/sys/dx/send.json", {
					"id" : ids,
					"sfnr" : sfnr
				}, function(data) {
					var json = $.parseJSON(data);
					if (json.flag) {
						alert(json.msg);
						window.location = "${ctx}/sys/dx/list.ac";
					} else {
						alert(json.msg);
					}
				});
			}, null);
		}
	</script>

	<form action="${ctx}/sys/dx/lxrlist.ac" method="post" id="sendForm">
		<table style="width:100%" bgcolor="#EDF6FF" align="center" border="0"
			cellpadding="0" cellspacing="10">
			<tr>
				<td>
					<table style="width:500" bgcolor="#EDF6FF" align="center"
						border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">接收单位：</td>
							<td><input type="text"
								name="sysParams.receiveOrganizationname"
								id="receiveOrganizationName"
								value="<s:property value="sysParams.receiveOrganizationname"/>" />
								<input type="hidden" name="sysParams.receiveOrganizationid"
								id="receiveOrganizationId"
								value="<s:property value="sysParams.receiveOrganizationid"/>" />
							</td>
							<td align="right">接收部门：</td>
							<td><input type="text" name="sysParams.receiveDeptname"
								id="receiveDeptName"
								value="<s:property value="sysParams.receiveDeptname"/>"
								readonly="readonly" /> <input type="hidden"
								name="sysParams.receiveDeptid" id="receiveDeptId"
								value="<s:property value="sysParams.receiveDeptid"/>" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right">联系人：</td>
							<td><input type="text" class="text" name="sysParams.lxrmc"
								value="${sysParams.lxrmc}" /></td>
							<td align="right">联系人手机号码：</td>
							<td><input type="text" class="text" name="sysParams.sjhm"
								value="${sysParams.sjhm}" /></td>
							<td align="right"><c:if
									test="${objectMap['XMSB_DX_DXCX_VIEW']}">
								&nbsp;&nbsp;<input type="button" class="btn" id="queryBtn"
										value="查询" />
								</c:if></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table></td>
			</tr>
		</table>

		<div class="editDiv">
			<table class="editTab" id="editTab">
				<tr>
					<td align="right" width="200">短信内容：</td>
					<td bgcolor="#EDF6FF">请选择短信模板： <select name="dxmb" id="dxmb">
							<option value="">==请选择==</option>
							<c:forEach items="${list}" var="obj" varStatus="status">
								<option value="${obj.dxmbId}">${obj.mbmc}</option>
							</c:forEach>
					</select><br/> <textarea id="sfnr" name="sfnr" style="width:95%"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<table style="width:100%">
			<tr>
				<td align="center">
					<div>
						<input type="button" class="btn" id="saveBtn" value="发送"
							onclick="checkLxr()" /> <input type="reset" class="btn"
							id="resetBtn" value="重置" /> <input type="button" class="btn"
							id="backBtn" value="返回" />
					</div></td>
			</tr>
		</table>

		<table width="100%" border="0" cellpadding="1" cellspacing="0"
			bordercolor="AEC2D5" class="list">
			<thead>
				<tr>
					<td width="5%"><input type="checkbox" id="checkAll" />
					</td>
					<td width="5%">序号</td>
					<td width="10%">接收单位</td>
					<td width="10%">接收部门</td>
					<td width="10%">联系人</td>
					<td width="10%">联系人手机号</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" value="${obj.userId}"
							class="subcheck" /></td>
						<td>${page.size*page.current+status.index+1}</td>
						<td>${obj.sysOrganization!=null?obj.sysOrganization.organizationName:''}</td>
						<td>${obj.sysDept!=null?obj.sysDept.deptname:''}</td>
						<td>${obj.userName}</td>
						<td>${obj.telmobile}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</body>
</html>
