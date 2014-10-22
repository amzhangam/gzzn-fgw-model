<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>已发送短信</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
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
		showMenu(1,13);
		
		//发送部门、接收部门
		var deptDatas = getJsonDatas("${ctx}/com/getOrgDeptTreeJson.json","params.workunitstype=3");
		var t1 = initCheckBoxTree("sendDeptTree", setting, deptDatas, "${sysParams.sendDeptid}");//初始化多选的树形下拉框
		var t2 = initCheckBoxTree("receiveDeptTree", setting, deptDatas, "${sysParams.receiveDeptid}");//初始化多选的树形下拉框
		//接收单位
		var orgDatas1 = getJsonDatas("${ctx}/com/getSysOrgJson.json","");
		//inputAutoComplete(orgDatas1, "receiveOrganizationName", "receiveOrganizationId", true);
		searchAutoComplete(orgDatas1, "receiveOrganizationName", false);
	
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#clearbtn").click(function(){
			clearCheckNodes(t1,"sendDeptTree");//清空树中被选中的节点
			clearCheckNodes(t2,"receiveDeptTree");//清空树中被选中的节点
			$("input[type='text'],input[type='hidden']").each(function() {
		    	this.value = "";
		    });
		});
		
			
		$("#sendBtn").click(function(){
			window.location = "${ctx}/sys/dx/lxrlist.ac";
		});
		
	});
//-->
</script>

	<form action="${ctx}/sys/dx/list.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
		  <table class="topSearchTab">
			<tr>
				<th>发送用户：</th>
				<td>
					<input type="text" class="text" name="sysParams.sendUsername" value="${sysParams.sendUsername}" />
				</td>
				<th>发送部门：</th>
				<td>
				  	<input type="text" class="text" id="sendDeptTreeSelName" value="" readonly="readonly" />
					<input type="hidden" name="sysParams.sendDeptid" id="sendDeptTreeSelID" value="${sysParams.sendDeptid}"/>
					<div id="sendDeptTreeDiv" class="menuContent" style="display:none; position: absolute; width:260px; height: 300px;">
						<ul id="sendDeptTree" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>接收单位：</th>
				<td>
					<input type="text" class="text" name="sysParams.receiveOrganizationname" id="receiveOrganizationName" value="${sysParams.receiveOrganizationname}"/>
				   <%--  <input type="hidden" name="sysParams.receiveOrganizationid" id="receiveOrganizationId"  value="${sysParams.receiveOrganizationid}"/> --%>
				</td>
				<th>发送内容：</th>
				<td>
					<input type="text" class="text" style="width:182px;" name="sysParams.sfnr" value="${sysParams.sfnr}" />
				</td>
				<th>
					<c:if test="${objectMap['XMSB_DX_DXCX_VIEW']}">
						<input type="button" class="btn" id="queryBtn" value="查询" />
						<input type="button" class="btn" id="clearbtn" value="清空" />
					</c:if> 
				</th>
			</tr>
			<tr>
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
					<input type="text" class="text" name="sysParams.lxrmc" value="${sysParams.lxrmc}" />
				</td>
				<th>联系人手机号：</th>
				<td>
					<input type="text" class="text" name="sysParams.sjhm" value="${sysParams.sjhm}" />
				</td>
				<th>发送时间：</th>
				<td colspan="2">
					<input type="text"  class="text Wdate" style="width:80px;" id="sfsjBegin" name="sysParams.sfsjBegin" value="${sysParams.sfsjBegin}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'sfsjEnd\')}'})" />
					 至 
					<input type="text"  class="text Wdate" style="width:80px;" id="sfsjEnd" name="sysParams.sfsjEnd" value="${sysParams.sfsjEnd}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'sfsjBegin\')}'})" /> 
				</td>
			</tr>
		 </table>
		</div>
		
		<!-- 相关数据展示 -->	
		<table style="width: 100%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">已发送短信</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					    <span style="float: right;">    
						   <%--  <c:if test="${moduleMap['XMSB_DX_DXFS']}"> 
								<input type="button" class="btn" id="sendBtn" value="按单位发送短信" />
								&nbsp;&nbsp;
								<input type="button" class="btn" id="sendBtn" value="按项目发送短信" />
						   </c:if>   --%>
		               	</span>     
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<table class="list" style="width: 100%;margin: 0 auto;">
			<thead>
				<tr>
					<td width="5%"><input type="checkbox" id="checkAll" /></td>
					<td width="5%">序号</td>
					<td width="8%">发送用户</td>
					<td width="8%">发送部门</td>
					<td width="14%">接收单位</td>
					<td width="8%">接收部门</td>
					<td width="8%">联系人</td>
					<td width="8%">联系人手机号</td>
					<td width="20%">发送内容</td>
					<td width="8%">发送时间</td>
					<td width="8%">状态</td>
				</tr>
			</thead>
			<tbody>
		  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
				<tr>
					<td>
						<input type="checkbox" value="${obj.dxId}" class="subcheck"/>
					</td>
					<td>${page.size*page.current+status.index+1}</td>
					<td>${obj.sysUser.userName}</td>
					<td>${obj.sysDeptByDeptId!=null?obj.sysDeptByDeptId.deptname:''}</td>
					<td>${obj.sysOrganization!=null?obj.sysOrganization.organizationName:''}</td>
					<td>${obj.sysDeptByReceiveDeptId!=null?obj.sysDeptByReceiveDeptId.deptname:''}</td>
					<td>${obj.lxrmc}</td>
					<td>${obj.sjhm}</td>
					<td>${obj.sfnr}</td>
					<td><fmt:formatDate value='${obj.sfsj}' pattern='yyyy-MM-dd'/></td>
					<td>
						<c:if test="${obj.sfbj==0}">
							无法发送
						</c:if>
						<c:if test="${obj.sfbj==1}">
							待发送
						</c:if>
						<c:if test="${obj.sfbj==2}">
							已发送
						</c:if>
						<c:if test="${obj.sfbj==10}">
							故障短信
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="11" class="line2" style="text-align: right;">
					<%@include file="../../changePage.jsp" %>
				</td>
			</tr>
			</tbody>
		</table>
 </form>
</body>
</html>
