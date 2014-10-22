<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>单位信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
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
		
		var areaDatas=getDatas("./getAreaJson.json");
		$("#xqName").focus(function(){
			initZtree("xq",areaDatas,150,200);
		});
		var unitNatureDatas=getDatas("./getUnitNatureJson.json");
		$("#dwxzName").focus(function(){
			initZtree("dwxz",unitNatureDatas,150,200);
		});
		var dwlxDatas=getDatas("./getUnitTypeJson.json");
		$("#dwlxName").focus(function(){
			initZtree("dwlx",dwlxDatas,150,200);
		});
	    if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}
		
		//var zNodes1 = eval('${sysOrgTreeJson}');//树的相关数据
	    //var radioTreeObj = initRadioTreeCheck("tree01", setting, zNodes1, "0", null);//初始化单选的树形下拉框
			
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		$("#addBtn").click(function(){
			window.location = "${ctx}/sys/organization/edit.ac";
		});
		
		//$("#clearbtn").click(function(){
		//	clearCheckNodes(radioTreeObj,"tree01");//清空树中被选中的节点
		//	$("input[type='text'],input[type='hidden']").each(function() {
		//    	this.value = "";
		//    });
		//});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("${ctx}/sys/organization/delete.ac?id=");
	});
//-->
</script>

	<form action="${ctx}/sys/organization/list.ac" method="post">
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">单位信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
				       单位名称：
					   <input type="text" class="text" name="sysParams.organName" value="${sysParams.organName}" />
					    <b>单位类型：</b>
							<input type="text" name="sysParams.workunitstypeName" id="dwlxName"
							          value="<s:property value="lxMap[sysParams.workunitstype]"/>" readonly="readonly" class="text"/>
							        <input type="hidden" name="sysParams.workunitstype" id="dwlxId"  
							        value="<s:property value="#attr.sysParams.workunitstype"/>"/>
						<b>所在地区：</b>
				  		<input type="text" name="sysParams.sysXq.xqmc" id="xqName"
							          value="<s:property value="#attr.sysXq.xqmc"/>" readonly="readonly" class="text"/>
							        <input type="hidden" name="sysXq.xqId" id="xqId"  
							        value="<s:property value="#attr.sysXq.xqId"/>"/>
							&nbsp;&nbsp;
							<b>单位性质：</b>
							<input type="text" name="sysParams.workunitsqualityName" id="dwxzName"
							          value="<s:property value="xzMap[#attr.workunitsquality]"/>" readonly="readonly" class="text"/>
							        <input type="hidden" name="workunitsquality" id="dwxzId"  
							        value="<s:property value="#attr.workunitsquality"/>"/>
							&nbsp;&nbsp;
					   <input type="button" class="btn" id="queryBtn" value="查询" />
					  <!--  <input type="button" class="btn" id="clearbtn" value="清空" /> -->
					        
					    <span style="float: right;">     
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
			            	 <input type="button" class="btn" id="delBtn" value="批量删除"/>     
		               	</span>     
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		
	<table width="100%" border="0" cellpadding="1" cellspacing="0" bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="5%"><input type="checkbox" id="checkAll" /></td>
				<td width="5%">序号</td>
				<td width="12%">单位名称</td>
				<td width="8%">单位类型</td>
				<td width="8%">企业工商注册号</td>
				<td width="8%">单位性质</td>
				<td width="8%">所在区域</td>
				<td width="8%">单位法人</td>
				<td width="8%">项目联系人</td>
				<td width="8%">手机号码</td>
				<td width="12%">单位地址</td>
				<td width="12%">操作</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.organizationId}" name="id" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.organizationName}</td>
				<td>
					<c:choose>
						<c:when test="${obj.workunitstype==1}">业主单位</c:when>
						<c:when test="${obj.workunitstype==2}">主管单位</c:when>
						<c:when test="${obj.workunitstype==3}">发改委单位</c:when>
					</c:choose>
				</td> 
				<td>${obj.workunitsregistercode}</td>
				<td>
					<s:property value="xzMap[#attr.obj.workunitsquality]"/>
				</td>
				<td>
					${obj.sysXq==null?'':obj.sysXq.xqmc}
				</td>
				<td>${obj.workunitsperson}</td>
				<td>${obj.workunitslinkman}</td>
				<td>${obj.workunitslinkmantel}</td>
				<td>${obj.workunitsaddress}</td>
				<td>
					<c:choose>
						<c:when test="${obj.workunitstype==1}">
							<a href="./editOwner.ac?id=${obj.organizationId}">编辑业主单位</a>&nbsp;&nbsp;|&nbsp;&nbsp;
							<a href="javascript:deleteAction('${ctx}/sys/organization/delete.ac?id=${obj.organizationId}')">删除</a>
						</c:when>
						<c:when test="${obj.workunitstype==2}">
							<a href="${ctx}/sys/organization/edit.ac?id=${obj.organizationId}">编辑主管单位</a>&nbsp;&nbsp;|&nbsp;&nbsp;
							<a href="javascript:deleteAction('${ctx}/sys/organization/delete.ac?id=${obj.organizationId}')">删除</a>
						</c:when>
						<c:when test="${obj.workunitstype==3}">
						</c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="12" class="line2" style="text-align: right;">
				<%@include file="../../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
