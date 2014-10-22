<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>发送项目短信</title>
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
			
			//资金性质
			var zjxzDatas = getJsonDatas("${ctx}/com/getXmsbZjxzJson.json");
			var t1 = initCheckBoxTree("zjxzTree", setting, zjxzDatas, "${sysParams.zjxz}");//初始化多选的树形下拉框
			//项目状态
			var xmztDatas = getJsonDatas("${ctx}/com/getDictItemsJson.json","params.dictName=项目状态");
			var t2 = initCheckBoxTree("xmztTree", setting, xmztDatas, "${sysParams.xmzt}");//初始化多选的树形下拉框
			//区域
			var xqIdDatas = getJsonDatas("${ctx}/com/getSysXqJson.json","");
			var xqIdTree = initCheckBoxTree("xqId", setting, xqIdDatas, "${sysParams.xqId}");//初始化多选的树形下拉框
			//项目类型
			var xmlxDatas = getJsonDatas("${ctx}/com/getXmsbXmlxJson.json","");
			var xmlxTree = initCheckBoxTree("xmlxTree", setting, xmlxDatas, "${sysParams.xmlx}");//初始化多选的树形下拉框
			//行业类别
			var hylbDatas = getJsonDatas("${ctx}/com/getXmsbHylbJson.json","");
			var hylbTree = initCheckBoxTree("hylbTree", setting, hylbDatas, "${sysParams.hylb}");//初始化多选的树形下拉框
			//主管单位
			var orgDatas1 = getJsonDatas("${ctx}/com/getSysOrgJson.json");//,""
			searchAutoComplete(orgDatas1, "zgdw", false);

			$("#queryBtn").click(function() {
				$("#currentPage").val("0");
				$("form")[0].submit();
			});
			
			$("#clearbtn").click(function(){
				clearCheckNodes(t1,"zjxzTree");//清空树中被选中的节点
				clearCheckNodes(t2,"xmztTree");
				clearCheckNodes(xqIdTree,"xqId");
				clearCheckNodes(xmlxTree,"xmlxTree");
				clearCheckNodes(hylbTree,"hylbTree");
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
		
		/**发送短信方法：联系人不能为空，短信内容不能为空且不能超出100个字符*/
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
			if(length>600){
				mac.alert("短信内容不能超过300个汉字");
				return;
			}
			ids = ids.substring(1);
			//alert(ids);
			mac.confirm('<p>确认要发送短信给已选中的项目联系人吗?</p>', function() {
				$.post("${ctx}/sys/dx/fsxmSend.json", {
					"id" : ids,
					"dxsfnr" : sfnr
				}, function(data) {
					var json = $.parseJSON(data);
					if (json.flag) {
						mac.confirm('<p style=font-weight:normal;>'+ json.msg +'</p><br/><p><font color=red>是否需要查看已成功发送的短信？</font></p>', function() {
							window.location = "${ctx}/sys/dx/list.ac";
						});
					} else {
						mac.alert(json.msg);
					}
				});
			}, null);
		}
		//-->   
	</script>

	<form action="${ctx}/sys/dx/fsxmlist.ac" method="post" id="sendForm">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
			<table class="editTab" id="editTab">
				<tr>
					<td style="background-color:#EDF6FF;width: 15%;">短信内容<font color="red">(限制在300字以内)</font>：</td>
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
						<!-- &nbsp;&nbsp;<input type="button" class="btn" id="backBtn" value="返回" /> -->
					</td>
				</tr>
			</table>
			
		  <table class="topSearchTab">
		  	<tr>
		  		<th colspan="2" style="color: red;">请根据以下条件，筛选出需要接收短信的相关项目</th>
		  		<th>项目类型：</th>
				<td>
					<input type="text" class="text" id="xmlxTreeSelName" value="" readonly="readonly" />
					<input type="hidden" name="sysParams.xmlx" id="xmlxTreeSelID" value="${sysParams.xmlx}"/>
					<div id="xmlxTreeDiv" class="menuContent" style="display:none; position: absolute; width:200px; height: 300px;">
						<ul id="xmlxTree" class="ztree"  style="margin-top:0;"></ul>
					</div>    
				</td>
				<th>行业类别：</th>
				<td>
					<input type="text" class="text" id="hylbTreeSelName" value="" readonly="readonly" />
					<input type="hidden" name="sysParams.hylb" id="hylbTreeSelID" value="${sysParams.hylb}"/>
					<div id="hylbTreeDiv" class="menuContent" style="display:none; position: absolute; width:200px; height: 300px;">
						<ul id="hylbTree" class="ztree"  style="margin-top:0;"></ul>
					</div>    
				</td>
		  		<th>项目状态：</th>
				<td>
					<input type="text" class="text" id="xmztTreeSelName" value="" readonly="readonly" />
					<input type="hidden" name="sysParams.xmzt" id="xmztTreeSelID" value="${sysParams.xmzt}"/>
					<div id="xmztTreeDiv" class="menuContent" style="display:none; position: absolute; width:200px; height: 300px;">
						<ul id="xmztTree" class="ztree"  style="margin-top:0;"></ul>
					</div>    
				</td>
		  	</tr>
		  	<tr>
				<th>项目名称：</th>
				<td>
					<input type="text" class="text" name="sysParams.projectName" value="${sysParams.projectName}" />
				</td>
				<th>项目编码：</th>
				<td>
					<input type="text" class="text" name="sysParams.projectcode" value="${sysParams.projectcode}" />
				</td>
				<th>项目业主：</th>
				<td>
					<input type="text" class="text" name="sysParams.xmyz" value="${sysParams.xmyz}" />
				</td>
				<th>资金性质：</th>
				<td>
					<input type="text" class="text" id="zjxzTreeSelName" value="" readonly="readonly" />
					<input type="hidden" name="sysParams.zjxz" id="zjxzTreeSelID" value="${sysParams.zjxz}"/>
					<div id="zjxzTreeDiv" class="menuContent" style="display:none; position: absolute; width:200px; height: 300px;">
						<ul id="zjxzTree" class="ztree"  style="margin-top:0;"></ul>
					</div>        
				</td>
			</tr>
			<tr>
				<th>申报时间：</th>
				<td>
					<input type="text"  class="text Wdate" style="width:80px;" id="startTime" name="sysParams.startTime" value="${sysParams.startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})" />
					 至 
					 <input type="text"  class="text Wdate" style="width:80px;" id="endTime" name="sysParams.endTime" value="${sysParams.endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startTime\')}'})" /> 
				</td>
				<th>主管单位：</th>
				<td>
					<input type="text" class="text" name="sysParams.zgdw" id="zgdw" value="${sysParams.zgdw}"/>
				</td>
				<th>所属区域：</th>
				<td>
					<input type="text" id="xqIdSelName" class="text" value="" readonly="readonly" />
					<input type="hidden" name="sysParams.xqId" id="xqIdSelID" value="${sysParams.xqId}"/>
					<div id="xqIdDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
						<ul id="xqId" class="ztree"  style="margin-top:0;"></ul>
					</div>
				</td>
				<th>当前处理部门：</th>
				<td>
					<input type="text" class="text" name="sysParams.deptname" value="${sysParams.deptname}" />
				</td>
				<td>
					<input type="button" class="btn" id="queryBtn" value="查询" />
					<input type="button" class="btn" id="clearbtn" value="清空" />
				</td>
			</tr>
		 </table>
		</div>

		<!-- 相关数据展示 -->	
		<table style="width: 100%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">发送项目短信</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					    <span style="float: right;">    
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
					<td width="2%"><input type="checkbox" id="checkAll"></td>
					<td width="3%">序号</td>
					<td width="10%">项目名称</td>
					<td width="8%">项目编码</td>
					<td width="9%">项目业主</td>
					<td width="5%">资金性质</td>
					<td width="7%">申报时间</td>
					<td width="5%">项目联系人</td>
					<td width="7%">联系人手机号</td>
					<td width="8%">主管单位</td>
					<td width="6%">所属区域</td>
					<td width="8%">当前处理部门</td>
					<td width="8%">项目状态</td>
					<td width="7%">项目类型</td>
					<td width="7%">行业类别</td>
				</tr>
			</thead>
			<tbody>
				 <c:forEach items="${pagePjInfo.list}" var="obj" varStatus="status">
					<tr>
						<td><input type="checkbox" value="${obj.projectid}" class="subcheck"/></td>
						<td>${pagePjInfo.size*pagePjInfo.current+status.index+1}</td>
						<td>${obj.projectname}</td>
						<td>${obj.projectcode}</td>
						<td>${obj.sysOrganizationByDeclareunitsid.organizationName }</td>
						<td>${obj.xmsbZjxz.zjxzmc }</td>
						<td><fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/></td>
						<td>${obj.declarerid}</td>
						<td>${obj.mobilePhone}</td>
						<td>${obj.sysOrganizationByDirectorunitsid.organizationName }</td>
						<td>${obj.sysXq.xqmc }</td>
						<td>${obj.nextauditdeptname}</td>
						<td>${xmztMap[obj.pjstatus]}&nbsp;</td>
						<td>${obj.xmsbXmlx.xmlxmc}&nbsp;&nbsp;</td>
						<td>${obj.xmsbHylb.hylbmc}</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="15" class="line2" style="text-align: right;">
						<%@include file="changePage2.jsp" %>
					</td>
			    </tr>
			</tbody>
		</table>
	</form>
</body>
</html>
