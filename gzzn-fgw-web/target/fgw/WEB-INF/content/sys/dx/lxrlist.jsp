<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>发送短信</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
</head>
<body>
	<script type="text/javascript">
		//<!--
		var setting = {
				check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
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
					onCheck: onCheckBoxCheck
				}
			};
			
		$(document).ready(function() {
			showMenu(1, 13);
			checkAllBox();//复选框的选中问题
			$("#dxmb").val("${dxmb}");
			
			//接收部门
			var deptDatas = getJsonDatas("${ctx}/com/getOrgDeptTreeJson.json","params.workunitstype=3");
			var t2 = initCheckBoxTree("receiveDeptTree", setting, deptDatas, "${sysParams.receiveDeptid}");//初始化多选的树形下拉框
			//接收单位
			var orgDatas1 = getJsonDatas("${ctx}/com/getSysOrgJson.json","");
			//inputAutoComplete(orgDatas1, "receiveOrganizationName", "receiveOrganizationId", true);
			searchAutoComplete(orgDatas1, "receiveOrganizationName", false);
			
			//单位类型
			var workunitstypeDatas = getJsonDatas("${ctx}/com/getOrganTypeJson.json","");
			var workunitstypeTree = initCheckBoxTree("workunitstype", setting, workunitstypeDatas, "${sysParams.workunitstype}");

			$("#queryBtn").click(function() {
				$("#currentPage").val("0");
				$("form")[0].submit();
			});
			
			$("#clearbtn").click(function(){
				clearCheckNodes(t2,"receiveDeptTree");//清空树中被选中的节点
				$(".topSearchTab input[type='text'],.topSearchTab input[type='hidden']").each(function() {
			    	this.value = "";
			    });
			});

			$("#dxmb").change(function() {
				if ($("#dxmb").val() != "") {
					$.post("${ctx}/sys/dx/getSfnr.json", {
						"dxmbId" : $("#dxmb").val()
					}, function(data) {
						var json = $.parseJSON(data);
						$("#dxsfnr").val(json.msg);
					});
				}else{
					$("#dxsfnr").val("");
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
			var sfnr = $("#dxsfnr").val();
			if(sfnr==""||(sfnr.length>0 && /^\s+$/.test(sfnr))){//验证字符不能全为空格
				mac.alert("短信内容不能为空且不能全为空格");
				return;
			}
			var length = sfnr.replace(/[^\x00-\xff]/g, "**").length;
			if(length>400){
				mac.alert("短信内容不能超过200个汉字");
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
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
			<table class="editTab" id="editTab">
				<tr>
					<td style="background-color:#EDF6FF;width: 15%;">短信内容<font color="red">(限制在200字以内)</font>：</td>
					<td style="background-color:#EDF6FF;width: 65%;">
						请选择短信模板： 
						<select style="width:150px;margin-top:5px;" name="dxmb" id="dxmb">
							<option value="">==请选择==</option>
							<c:forEach items="${list}" var="obj" varStatus="status">
								<option value="${obj.dxmbId}">${obj.mbmc}</option>
							</c:forEach>
						</select>
						<br/> 
						<textarea name="dxsfnr" id="dxsfnr" style="width:95%">${dxsfnr}</textarea>
					</td>
					<td style="background-color:#EDF6FF;width: 20%;">
						&nbsp;&nbsp;<input type="button" class="btn" id="saveBtn" value="发送" onclick="checkLxr()" />
						&nbsp;&nbsp;<input type="reset" class="btn" id="resetBtn" value="重置" />
					</td>
				</tr>
			</table>
			
		  <table class="topSearchTab">
		  	<tr>
		  		<th colspan="3" style="color: red;">请根据以下条件，筛选出需要接收短信的相关单位</th>
		  		<td>&nbsp;</td>
		  	</tr>
			<tr>
				<th>单位类型:</th>
				<td>
					<input type="text" name="sysParams.workunitstypeName" id="workunitstypeSelName" class="text" value="${sysParams.workunitstypeName}" readonly="readonly" />
					<input type="hidden" name="sysParams.workunitstype" id="workunitstypeSelID" value="${sysParams.workunitstype}"/>
					<div id="workunitstypeDiv" class="menuContent" style="display:none; position: absolute;width:211px;height: 250px;">
						<ul id="workunitstype" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>接收单位：</th>
				<td>
					<input type="text" class="text" name="sysParams.receiveOrganizationname" id="receiveOrganizationName" value="${sysParams.receiveOrganizationname}"/>
				   <%--  <input type="hidden" name="sysParams.receiveOrganizationid" id="receiveOrganizationId"  value="${sysParams.receiveOrganizationid}"/> --%>
				</td>
				<th>接收部门：</th>
				<td>
					<input type="text" class="text" id="receiveDeptTreeSelName" value="" readonly="readonly" />
					<input type="hidden" name="sysParams.receiveDeptid" id="receiveDeptTreeSelID" value="${sysParams.receiveDeptid}"/>
					<div id="receiveDeptTreeDiv" class="menuContent" style="display:none; position: absolute; width:260px; height: 300px;">
						<ul id="receiveDeptTree" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>联系人：</th>
				<td>
					<input type="text" class="text" name="sysParams.lxrmc" size="10" value="${sysParams.lxrmc}" />
				</td>
				<th>联系人手机号：</th>
				<td>
					<input type="text" class="text" name="sysParams.sjhm" size="10" value="${sysParams.sjhm}" />
				</td>
				<td>
					<c:if test="${objectMap['XMSB_DX_DXCX_VIEW']}">
						<input type="button" class="btn" id="queryBtn" value="查询" />
						<input type="button" class="btn" id="clearbtn" value="清空" />
					</c:if>
				</td>
			</tr>
		 </table>
		</div>

		<table class="list" style="width: 100%;margin: 0 auto;">
			<thead>
				<tr>
					<td width="5%"><input type="checkbox" id="checkAll" /></td>
					<td width="5%">序号</td>
					<td width="30%">接收单位</td>
					<td width="25%">接收部门</td>
					<td width="20%">联系人</td>
					<td width="15%">联系人手机号</td>
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
