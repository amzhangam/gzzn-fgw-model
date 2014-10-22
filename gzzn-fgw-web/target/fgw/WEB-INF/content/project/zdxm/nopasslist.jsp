<%@page import="com.gzzn.fgw.webUtil.PropertiesUtil"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.*"%>
<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>审核不通过项目</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/zdxm/list.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
var windowview;
<!--
	$(document).ready(function() {
		showMenu(2,17);
		if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}

		$("#checkAll").click(function() {
			var tag = $(this).attr("checked");
			if (tag) {
				$(".subcheck").attr("checked", "true");
			} else {
				$(".subcheck").removeAttr("checked");
			}
		});
		
		$("#queryBtn").click(function(){
			var str = "";
			//判断项目总投资查询输入值的合法性：DECIMAL(18,2)
			//var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
			var reg = new RegExp("^([1-9]{1}[0-9]{0,15}|0|0\.{1}[0-9]{0,2}|[1-9]{1}[0-9]{0,15}\.{1}[0-9]{0,2})$");
			$(".moneyText").each(function(index) {
				if($(this).val()!="" && !reg.test($(this).val())){
					 str += "累计完成投资、项目总投资(万元)、投资计划建议(万元)中的输入条件，必须为数值且小数点后可保留两位小数！";//必须符合金额格式
					 $(this).focus();//将光标订到指定位置
					 return false;
				}
			});
			$("#checkFormErr").html(str);
			$("#currentPage").val("0");
			if(str==""){
				//$("#report").submit();
				$("form")[0].submit();
			}
		});

		//清空事件
		$("#clearBtn").click(function(){
			$(".topSearchTab input[type='text']").each(function(index) {
				$(this).val("");
			});
			$(".topSearchTab input[type='hidden']").each(function(index) {
				$(this).val("");
			});
			$(".topSearchTab select").each(function(index) {
				$(this).val("");
			});
			//$("#projectTypeId").val("");
			//clearCheckNodes(radioTreeObj);
		});
		
		initMoreSearchInfo();//初始化：显示更多查询条件... 
		
		<%
		SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		%>
	});
//-->

	function view(projectId){
		var url = "${ctx}/project/pjbaseinfo/view.json?id="+projectId+"&type=0";
		windowview = window.open (url, 'newwindow9', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		windowview.focus();
	}

	/**初始化：显示更多查询条件... */
	function initMoreSearchInfo(){
		if($("#moreSearchBool").val()=="true"){
			$(".moreSearchInfo").css("display","table-row");
			//$(".moreSearchInfo").css("display","inline-block");
			$("#moreSearch").html("隐藏更多查询条件...");
		}else{
			$(".moreSearchInfo").css("display","none");
			$("#moreSearch").html("显示更多查询条件...");
		}
	}

	/**显示更多查询条件... */
	function showMoreSearchInfo(){
		//alert("===="+$(".moreSearchInfo").css("display"));
		if($(".moreSearchInfo").css("display")=="none"){//更多查询内容被隐藏，现在需要展示它
			$(".moreSearchInfo").css("display","table-row");
			//$(".moreSearchInfo").css("display","inline-block");
			$("#moreSearchBool").val(true);
			$("#moreSearch").html("隐藏更多查询条件...");
		}else{
			$(".moreSearchInfo").css("display","none");
			$("#moreSearchBool").val(false);
			$("#moreSearch").html("显示更多查询条件...");
		}
		//$(".moreSearchInfo").slideToggle("fast");
	}
	
	function viewSh(projectId){
		var url = "${ctx}/project/pjbaseinfo/viewSh.json?id="+projectId+"&type=0";
		var iWidth = 800;                          //弹出窗口的宽度;
	    var iHeight = 600;                         //弹出窗口的高度;
	    //获得窗口的垂直位置
	    var iTop = (window.screen.availHeight-30-iHeight)/2;        
	    //获得窗口的水平位置
	    var iLeft = (window.screen.availWidth-10-iWidth)/2;           
	window.open (url, 'newwindow', 'left='+iLeft+',top='+iTop+',width='+ iWidth +',height='+ iHeight +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
	}
	
	/**
	 * 导出数据到excel
	 * @param url
	 * @param tableid
	 * @param rowNum 该参数不传是，默认=2
	 */
	function exportOneexcel(projectid){
		window.location = "${ctx}/project/pjbaseinfo/expOneExcel.ac?id=" + projectid;
	/* 
		var oldurl = document.forms[0].action;
		document.forms[0].action = url;
		document.forms[0].submit();
		document.forms[0].action = oldurl; */
	}
</script>

	<form action="${ctx}/project/zdxm/nopasslist.ac" method="post"  enctype="multipart/form-data" id="zdxm">
		<table style="margin-top: 10px;width:100%" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="10">
			<tr>
				<td>
				      <table  class="topSearchTab" style="width:500" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="right">
							       项目名称：
							</th>
							<td>
								   <input type="text" class="text" name="projectParams.projectname" value="${projectParams.projectname}" />
							</td>
							<th align="right">
								    项目类型：
							</th>
							<td>
								<input type="text" class="text" name="projectParams.xmsbXmlx.xmlxmc" id="xmlxName" value="<s:property value="projectParams.xmsbXmlx.xmlxmc"/>" readonly="readonly"/>
						        <input type="hidden" name="projectParams.xmsbXmlx.xmlxId" id="xmlxId"  value="<s:property value="projectParams.xmsbXmlx.xmlxId"/>"/>
							</td>
							<th align="right">项目分类：</th>
							<td>
								<select id="xmfl" name="projectParams.xmfl" style="width:150px">
									<option value="">(无)</option>
									<option value="1" ${projectParams.xmfl==1?'selected':''}>基本建设投资类项目</option>
									<option value="2" ${projectParams.xmfl==2?'selected':''}>更新改造投资类项目</option>
									<option value="3" ${projectParams.xmfl==3?'selected':''}>其他固定资产投资类项目</option>
							</select>
							</td>
							<th align="left">
								<c:if test="${objectMap['XMSB_XM_XMXXGL_VIEW']}">
								<input type="button" class="btn" id="queryBtn" value="查询" />&nbsp;&nbsp;
								<input type="button" class="btn" id="clearBtn" value="清空" />&nbsp;&nbsp;
								</c:if>
								<!--  <input type="button" class="btn" id="clearbtn" value="清空" /> -->
							</th>
						</tr>
						<tr>
							<th align="right">
								   是否重大项目：
							</th>
							<td>
								<input type="text" class="text" name="projectParams.sfzdxmName" id="sfzdxmName" value="<s:property value="projectParams.sfzdxmName"/>" readonly="readonly"/>
						        <input type="hidden" name="projectParams.sfzdxm" id="sfzdxmId"  value="<s:property value="projectParams.sfzdxm"/>"/>
							</td>
							<th align="right">主管单位：</th>
							<td>
								<input type="text" class="text" name="projectParams.zgdw" id="zgdw" value="${projectParams.zgdw}"/>
							</td>
							<th align="right">
									   项目业主：
							</th>
							<td colspan="2">
								   <input type="text" class="text" name="projectParams.sysOrganizationByDeclareunitsid.organizationName" value="${projectParams.sysOrganizationByDeclareunitsid.organizationName}" />
							</td>
						</tr>
						<tr>
							<th align="right">申报时间：</th>
							<td>
								<input type="text"  class="text Wdate" style="width:80px;" id="beginTime" name="projectParams.beginTime" value="${projectParams.beginTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})" />
								 至 
								 <input type="text"  class="text Wdate" style="width:80px;" id="endTime" name="projectParams.endTime" value="${projectParams.endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'beginTime\')}'})" /> 
							</td>
							<th align="right">项目储备类别：</th>
							<td>
								<select id="xmcblb" name="projectParams.xmcblb"/>
									<option value="">(无)</option>
									<option value="储备项目库" ${projectParams.xmcblb=='储备项目库'?'selected':''}>预备项目库</option>
									<option value="正式项目库" ${projectParams.xmcblb=='正式项目库'?'selected':''}>正式项目库</option>
								</select>
							</td>
							<th align="right">
										处理部门：
							</th>
							<td>
										<input type="text" name="projectParams.auditdeptname" id="auditdeptName"
										           value="<s:property value="projectParams.auditdeptname"/>" readonly="readonly" class="text"/>
										        <input type="hidden" name="projectParams.auditdept" id="auditdeptId"  
										        value="<s:property value="projectParams.auditdept"/>"/>
										&nbsp;&nbsp;
								        
							</td>
						</tr>
						<tr>
							<th align="right">修改时间：</th>
							<td>
								<input type="text"  class="text Wdate" style="width:80px;" id="beginModifiedTime" name="projectParams.beginModifiedTime" value="${projectParams.beginModifiedTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endModifiedTime\')}'})" />
								 至 
								 <input type="text"  class="text Wdate" style="width:80px;" id="endModifiedTime" name="projectParams.endModifiedTime" value="${projectParams.endModifiedTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'beginModifiedTime\')}'})" /> 
							</td>
							<th align="right">项目总投资类型：</th>
							<td colspan="1">
								<select id="expectfinishinvestType" name="projectParams.expectfinishinvestType" style="width:150px">
									<option value="">(无)</option>
									<option value="1" ${projectParams.expectfinishinvestType==1?'selected':''}>1000万元以下</option>
									<option value="2" ${projectParams.expectfinishinvestType==2?'selected':''}>1000万元（含）以上</option>
							</select>
							</td>
							<th align="right">项目总投资：</th>
							<td colspan="2">
								<input type="text"  class="text" style="width:80px;" id="xmztzBegin" name="projectParams.xmztzBegin" value="${projectParams.xmztzBegin}" />
								(万元) 至 
								 <input type="text"  class="text" style="width:80px;" id="xmztzEnd" name="projectParams.xmztzEnd" value="${projectParams.xmztzEnd}" /> 
								 (万元)
							</td>
						</tr>
						<tr>
							<th colspan="4" style="color: red;text-align: left;" id="checkFormErr"></th>
							<td colspan="1">
								<input type="hidden"  name="moreSearchBool" id="moreSearchBool"  value="${moreSearchBool}"/>
							</td>
							<th colspan="2"><a href="javaScript:showMoreSearchInfo();" id="moreSearch">显示更多查询条件...</a></th>
						</tr>
						<tr class="moreSearchInfo">
							<th align="right">
								    行业类别：
							</th>
							<td>
								<input type="text" class="text" name="projectParams.xmsbHylb.hylbmc" id="hylbName" value="<s:property value="projectParams.xmsbHylb.hylbmc"/>" readonly="readonly"/>
						        <input type="hidden" name="projectParams.xmsbHylb.hylbId" id="hylbId"  value="<s:property value="projectParams.xmsbHylb.hylbId"/>"/>
							</td>
							<th align="right">
								    资金性质：
							</th>
							<td>
								<input type="text" class="text" name="projectParams.xmsbZjxz.zjxzmc" id="zjxzName" value="<s:property value="projectParams.xmsbZjxz.zjxzmc"/>" readonly="readonly"/>
						        <input type="hidden" name="projectParams.xmsbZjxz.zjxzId" id="zjxzId"  value="<s:property value="projectParams.xmsbZjxz.zjxzId"/>"/>
							</td>
							<th align="right">
									   项目编码：
							</th>
							<td colspan="2">
								   <input type="text" class="text" name="projectParams.projectcode" value="${projectParams.projectcode}" />
							</td>
						</tr>
						<tr class="moreSearchInfo">
							<th align="right">
									   申报依据：
							</th>
							<td>
								   <input type="text" class="text" name="projectParams.declaregist" value="${projectParams.declaregist}" />
							</td>
							<th align="right">
								    项目管理单位：
							</th>
							<td>
								<input type="text" class="text" name="projectParams.manageunitsname" id="manageunitsname" value="${projectParams.manageunitsname}"/>
							</td>
							<th align="right">
								    主要建设内容：
							</th>
							<td colspan="2">
								<input type="text" class="text" name="projectParams.projectcontent" id="projectcontent" value="${projectParams.projectcontent}" />
							</td>
						</tr>
					</table>	
				</td>
			</tr>
		</table>
		<table style="width:100%;height:300">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">审核不通过项目</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;height:300">
					 <div>
					    <span style="float: right;">  
					  		<input type="button" class="btn" id="exportBtn" value="导出项目汇总表" onclick="exportexcel('${ctx}/project/zdxm/expNoPassExcel.ac','tabList',2);"/>          
		               	</span>   
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
	

	<table width="100%" id="tabList" border="0" cellpadding="1" cellspacing="0" bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="2%"><input type="checkbox" id="checkAll"></td>
				<td width="4%">序号</td>
				<td width="8%">项目类型</td>
				<td width="12%">项目编码</td>
				<td width="12%">项目名称</td>
				<td width="8%">申报日期</td>
				<td width="12%">项目业主</td>
				<td width="8%">主管单位</td>
				<td width="6%">处理部门</td>
				<td width="6%">项目储备类别</td>
				<td width="6%">是否重大项目</td>
				<td>操作</td>
			</tr>
		</thead>
			<c:forEach items="${page.list}" var="obj" varStatus="status">
			<tr>
				<td align="center">
					<input type="checkbox" value="${obj.projectid}" class="subcheck"/>
				</td>
				<td align="center">${page.size*page.current+status.index+1}&nbsp;</td>
				<td>${obj.xmsbXmlx.xmlxmc }&nbsp;</td>
				<td>${obj.projectcode}&nbsp;</td>
				<td>
				<c:choose>
					<c:when test="${objectMap['XMSB_XM_XMXXGL_VIEW']}">
						<a href="javascript:view(${obj.projectid})">${obj.projectname}</a>
					</c:when>
					<c:otherwise>
						${obj.projectname}
					</c:otherwise>
				</c:choose>
				</td>
				<td><fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/></td>
				<td>${obj.sysOrganizationByDeclareunitsid!=null?obj.sysOrganizationByDeclareunitsid.organizationName:''}&nbsp;</td>
				<td>${obj.sysOrganizationByDirectorunitsId!=null?obj.sysOrganizationByDirectorunitsId.organizationName:''}&nbsp;</td>
				<td>${obj.auditdeptname}&nbsp;</td>
				<td>${obj.xmcblb}&nbsp;</td>
				<td>${obj.sfzdxm!=null&&obj.sfzdxm==1?'是':''}&nbsp;</td>
				<td>
					<a href="javascript:exportOneexcel(${obj.projectid})">导出</a>&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XM_XMXXGL_VIEW']}">
						<a href="javascript:viewSh(${obj.projectid})">${obj.pjstatus>1?'&nbsp;&nbsp;查看审核意见':''}</a>
					</c:if>
				</td>
			</tr>
		    </c:forEach>
			<tr>
				<td colspan="12" class="line2" style="text-align: right;">
					<%@include file="../../changePage.jsp" %>
				</td>
		    </tr>
	</table>
	
	</form>
</body>
</html>
