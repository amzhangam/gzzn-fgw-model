<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>单位信息</title>
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
		showMenu(6,63);
		 if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}
		
		//单位类型
		var workunitstypeDatas = getJsonDatas("${ctx}/com/getOrganTypeJson.json","");
		var workunitstypeTree = initCheckBoxTree("workunitstype", setting, workunitstypeDatas, "${sysParams.workunitstype}");//初始化多选的树形下拉框
		//单位性质
		var dwxzDatas = getJsonDatas("${ctx}/com/getDictItemsJson.json","params.dictName=单位性质");
		var dwxzTree = initCheckBoxTree("dwxz", setting, dwxzDatas, "${sysParams.workunitsquality}");//初始化多选的树形下拉框
		//区域
		var xqIdDatas = getJsonDatas("${ctx}/com/getSysXqJson.json","");
		var xqIdTree = initCheckBoxTree("xqId", setting, xqIdDatas, "${sysParams.xqId}");//初始化多选的树形下拉框
		
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		$("#addBtn").click(function(){
			window.location = "${ctx}/sys/organNew/edit.ac";
		});
		
		//清空事件
		$("#clearBtn").click(function(){
			$(".topSearchTab input[type='text']").each(function(index) {
				$(this).val("");
			});
			clearCheckNodes(workunitstypeTree);
			clearCheckNodes(dwxzTree);
			clearCheckNodes(xqIdTree);
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("${ctx}/sys/organNew/delete.ac?id=");
	});
//-->
</script>

	<form action="${ctx}/sys/organNew/list.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
			  <table class="topSearchTab">
				<tr>
					<th>单位名称：</th>
					<td><input type="text" class="text" name="sysParams.organName" id="organName" value="${sysParams.organName}" /> </td>
					<th>单位类型：</th>
					<td>
						<input type="text" id="workunitstypeSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="sysParams.workunitstype" id="workunitstypeSelID" value="${sysParams.workunitstype}"/>
						<div id="workunitstypeDiv" class="menuContent" style="display:none; position: absolute;width:180px;height: 250px;">
							<ul id="workunitstype" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
					<th>单位性质：</th>
					<td>
						<input type="text" id="dwxzSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="sysParams.workunitsquality" id="dwxzSelID" value="${sysParams.workunitsquality}"/>
						<div id="dwxzDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
							<ul id="dwxz" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
					<th>所在地区：</th>
					<td>
						<input type="text" id="xqIdSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="sysParams.xqId" id="xqIdSelID" value="${sysParams.xqId}"/>
						<div id="xqIdDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
							<ul id="xqId" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
					<th>
						<input type="button" class="btn" value="查询"  id="queryBtn" />&nbsp;&nbsp;
						<input type="button" class="btn" value="清空"  id="clearBtn" />
					</th>
				</tr>
			  </table>
		</div>
		
		
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">单位信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
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
				<td width="3%"><input type="checkbox" id="checkAll" /></td>
				<td width="5%">序号</td>
				<td width="15%">单位名称</td>
				<td width="8%">单位类型</td>
				<td width="8%">单位性质</td>
				<td width="8%">所在区域</td>
				<td width="8%">企业工商注册号</td>
				<td width="8%">单位法人</td>
				<td width="8%">单位联系人</td>
				<td width="8%">联系人电话</td>
				<td width="12%">单位地址</td>
				<td width="9%">操作</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.organizationId}" name="id" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.organizationName}&nbsp;</td>
				<td>${obj.workunitstypeName}</td> 
				<td>${obj.workunitsqualityName}</td>
				<td>${obj.sysXq.xqmc}</td>
				<td>${obj.workunitsregistercode}</td>
				<td>${obj.workunitsperson}</td>
				<td>${obj.workunitslinkman}</td>
				<td>${obj.workunitslinkmantel}</td>
				<td>${obj.workunitsaddress}&nbsp;</td>
				<td>
					<a href="${ctx}/sys/organNew/edit.ac?id=${obj.organizationId}">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;
					<a href="javascript:deleteAction('${ctx}/sys/organNew/delete.ac?id=${obj.organizationId}')">删除</a>
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
