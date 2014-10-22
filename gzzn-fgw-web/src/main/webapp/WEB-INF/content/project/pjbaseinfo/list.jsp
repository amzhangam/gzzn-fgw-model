<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>项目信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/pjbaseinfo/list.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
</head>
<%
	SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
%>
<body>
<script type="text/javascript">
var windowview,windowsh;
<!--
	$(document).ready(function() {
		showMenu(2,17);
		if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/project/pjbaseinfo/edit.ac";
		});
		
		//主管单位
		var orgDatas1 = getJsonDatas("${ctx}/com/getSysOrgJson.json");//,""
		searchAutoComplete(orgDatas1, "zgdw", false);
		
		initMoreSearchInfo();//初始化：显示更多查询条件... 

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

	});
//-->
	
	function checkRepeatProjectName(){
		$.post(
	    	"${ctx}/project/pjbaseinfo/checkRepeatProjectName.json",
	    	{"xmmc":$("#projectname").val()},
	    	function(data){
	    		var json = $.parseJSON(data);
	    		if(json.flag){
					
				}
				else{
					alert(json.msg);
				}
	    	}
    	);
	}
	
	function resave(projectid){
		mac.confirm('<p>确认要重新申报这个项目吗?</p>', function(){
			$.post(
			    	"${ctx}/project/pjbaseinfo/checkRepeatProjectName.json",
			    	{"projectid":projectid},
			    	function(data){
			    		var json = $.parseJSON(data);
			    		if(json.flag){
			    			window.location = "${ctx}/project/pjbaseinfo/resave.ac?id=" + projectid;
						}
						else{
							alert(json.msg);
						}
			    	}
		    	);
		}, null);
		
	}
	
	function restoreProject(projectid){
		mac.confirm('<p>确认要恢复这个项目吗?</p>', function(){
			$.post(
			    	"${ctx}/project/pjbaseinfo/restoreProject.json",
			    	{"id":projectid},
			    	function(data){
			    		var json = $.parseJSON(data);
			    		if(json.flag){
			    			window.location = "${ctx}/project/pjbaseinfo/list.ac";
						}
						else{
							alert(json.msg);
						}
			    	}
		    	);
		}, null);
		
	}
	
	function view(projectId){
		var url = "${ctx}/project/pjbaseinfo/view.json?id="+projectId+"&type=0";
		windowview = window.open (url, 'newwindow9', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		windowview.focus();
		
	}
	
	function viewSh(projectId){
		var url = "${ctx}/project/pjbaseinfo/viewSh.json?id="+projectId+"&type=0";
		var iWidth = 1024;                          //弹出窗口的宽度;
	    var iHeight = 768;                         //弹出窗口的高度;
	    //获得窗口的垂直位置
	    var iTop = (window.screen.availHeight-30-iHeight)/2;        
	    //获得窗口的水平位置
	    var iLeft = (window.screen.availWidth-10-iWidth)/2;           
		windowsh = window.open (url, 'newwindow2', 'left='+iLeft+',top='+iTop+',width='+ iWidth +',height='+ iHeight +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		windowsh.focus();
		
		
	}

	function goxmps(projectcode,projectname,declarerid,contacttel,projectcontent){
		var url = "http://gsps.gzplan.gov.cn/project/edit.do?projectCode="+projectcode
				+"&projectName="+encodeURI(projectname)
				+"&projectContact="+encodeURI(declarerid)
				+"&projectContent="+encodeURI(projectcontent)
				+"&contactTel="+contacttel;
		window.open (url, 'newwindow', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		
		//$.get("http://gsps.gzplan.gov.cn/api/fgw/addProject.do", 
	    //        {
		//	"projectCode":projectcode,
		//		"projectName":URLEncoder.encode(URLEncoder.encode(projectname,"UTF-8")),
		//		"projectContact":URLEncoder.encode(URLEncoder.encode(declarerid,"UTF-8")),
		//		"contactTel":contacttel
		//	}, 
	   //         function (data) {
	   //             alert(data); 
	   //             
	   //     });
		
		
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
	
</script>

	<form action="${ctx}/project/pjbaseinfo/list.ac" method="post"  enctype="multipart/form-data" id="xmxx">
		<table style="margin-top: 10px;width:100%" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="10">
			<tr>
				<td>
				      <table class="topSearchTab" style="width:500" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="0">
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
							<th align="right">主管单位：</th>
							<td>
								<input type="text" class="text" name="projectParams.zgdw" id="zgdw" value="${projectParams.zgdw}"/>
							</td>
							<th align="right">
									   项目业主：
							</th>
							<td>
								   <input type="text" class="text" name="projectParams.sysOrganizationByDeclareunitsid.organizationName" value="${projectParams.sysOrganizationByDeclareunitsid.organizationName}" />
							</td>
							<th align="right">
									项目状态：
							</th>
							<td colspan="2">
							  		<input type="text" name="projectParams.pjstatusName" id="pjstatusName"
										          value="<s:property value="projectParams.pjstatusName"/>" readonly="readonly" class="text"/>
										        <input type="hidden" name="projectParams.pjstatus" id="pjstatusId"  
										        value="<s:property value="projectParams.pjstatus"/>"/>
							</td>
						</tr>
						<tr>
							<th align="right">申报时间：</th>
							<td>
								<input type="text"  class="text Wdate" style="width:80px;" id="beginTime" name="projectParams.beginTime" value="${projectParams.beginTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})" />
								 至 
								 <input type="text"  class="text Wdate" style="width:80px;" id="endTime" name="projectParams.endTime" value="${projectParams.endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'beginTime\')}'})" /> 
							</td>
							<th align="right">修改时间：</th>
							<td>
								<input type="text"  class="text Wdate" style="width:80px;" id="beginModifiedTime" name="projectParams.beginModifiedTime" value="${projectParams.beginModifiedTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endModifiedTime\')}'})" />
								 至 
								 <input type="text"  class="text Wdate" style="width:80px;" id="endModifiedTime" name="projectParams.endModifiedTime" value="${projectParams.endModifiedTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'beginModifiedTime\')}'})" /> 
							</td>
							<th align="right">项目总投资类型：</th>
							<td>
								<select id="expectfinishinvestType" name="projectParams.expectfinishinvestType" style="width:150px">
									<option value="">(无)</option>
									<option value="1" ${projectParams.expectfinishinvestType==1?'selected':''}>1000万元以下</option>
									<option value="2" ${projectParams.expectfinishinvestType==2?'selected':''}>1000万元（含）以上</option>
							</select>
							</td>
							<td colspan="1" align="right">
							</td>
						</tr>
						<tr>
							<th align="right">项目总投资：</th>
							<td colspan="6">
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
										当前处理部门：
							</th>
							<td>
										<input type="text" name="projectParams.nextauditdeptname" id="nextauditdeptName"
										           value="<s:property value="projectParams.nextauditdeptname"/>" readonly="readonly" class="text"/>
										        <input type="hidden" name="projectParams.nextauditdept" id="nextauditdeptId"  
										        value="<s:property value="projectParams.nextauditdept"/>"/>
										&nbsp;&nbsp;
								        
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
						<tr class="moreSearchInfo">
							<th align="right">
									   申报依据：
							</th>
							<td>
								   <input type="text" class="text" name="projectParams.declaregist" value="${projectParams.declaregist}" />
							</td>
							
							<td colspan="5">&nbsp;</td>
						</tr>
						<!--tr class="moreSearchInfo">
							<th>预计至
								<input type="text" class="text Wdate" style="width:45px" name="params.expectFinishInvestYear" id="expectFinishInvestYear" value="${params.expectFinishInvestYear}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true})"/>
							</th>
							<td colspan="6">
								<span style="color: #074E81;font-weight: bold;">年底累计完成投资</span>
								合计：
								<input type="text" class="text moneyText" name="params.expectFinishInvest" id="expectFinishInvest" value="<fmt:formatNumber value='${params.expectFinishInvest }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.expectFinishInvest2" id="expectFinishInvest2" value="<fmt:formatNumber value='${params.expectFinishInvest2 }' pattern='#0.##'/>" />
								万元，其中市本级财政资金：
								<input type="text" class="text moneyText" name="params.expectFinishOtherInvest" id="expectFinishOtherInvest" value="<fmt:formatNumber value='${params.expectFinishOtherInvest }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.expectFinishOtherInvest2" id="expectFinishOtherInvest2" value="<fmt:formatNumber value='${params.expectFinishOtherInvest2 }' pattern='#0.##'/>" />
							</td>
						</tr>
						<tr class="moreSearchInfo">
							<th style="color:red;text-align: left;" colspan="2">项目总投资(万元)</th>
							<td colspan="5"></td>
						</tr>
						<tr class="moreSearchInfo">
							<th>合计：</th>
							<td>
								<input type="text" class="text moneyText" name="params.pjinvestSum" id="pjinvestSum" value="<fmt:formatNumber value='${params.pjinvestSum }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.pjinvestSum2" id="pjinvestSum2" value="<fmt:formatNumber value='${params.pjinvestSum2 }' pattern='#0.##'/>" />
							</td>
							<th>市财政资金：</th>
							<td>
								<input type="text" class="text moneyText" name="params.pjinvestCity" id="pjinvestCity" value="<fmt:formatNumber value='${params.pjinvestCity }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.pjinvestCity2" id="pjinvestCity2" value="<fmt:formatNumber value='${params.pjinvestCity2 }' pattern='#0.##'/>" />
							</td>
							<th>融资(含银行贷款)：</th>
							<td colspan="2">
								<input type="text" class="text moneyText" name="params.pjinvestBank" id="pjinvestBank" value="<fmt:formatNumber value='${params.pjinvestBank }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.pjinvestBank2" id="pjinvestBank2" value="<fmt:formatNumber value='${params.pjinvestBank2 }' pattern='#0.##'/>" />
							</td>
						</tr>
						<tr class="moreSearchInfo">
							<th>自有资金：</th>
							<td>
								<input type="text" class="text moneyText" name="params.pjinvestCompany" id="pjinvestCompany" value="<fmt:formatNumber value='${params.pjinvestCompany }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.pjinvestCompany2" id="pjinvestCompany2" value="<fmt:formatNumber value='${params.pjinvestCompany2 }' pattern='#0.##'/>" />
							</td>
							<th>其它：</th>
							<td colspan="4">
								<input type="text" class="text moneyText" name="params.pjinvestOther" id="pjinvestOther" value="<fmt:formatNumber value='${params.pjinvestOther }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.pjinvestOther2" id="pjinvestOther2" value="<fmt:formatNumber value='${params.pjinvestOther2 }' pattern='#0.##'/>" />
							</td>
						</tr>
						<tr class="moreSearchInfo">
							<th style="color:red;text-align: left;" colspan="2">
								<input type="text" class="text Wdate" style="width:45px" name="params.planInvestYear" id="planInvestYear" value="${params.planInvestYear}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true})"/>
								年投资计划建议(万元) 
							</th>
							<td colspan="5"></td>
						</tr>
						<tr class="moreSearchInfo">
							<th>合计：</th>
							<td>
								<input type="text" class="text moneyText" name="params.planInvestSum" id="planInvestSum" value="<fmt:formatNumber value='${params.planInvestSum }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.planInvestSum2" id="planInvestSum2" value="<fmt:formatNumber value='${params.planInvestSum2 }' pattern='#0.##'/>" />
							</td>
							<th>市财政资金：</th>
							<td>
								<input type="text" class="text moneyText" name="params.planInvestCity" id="planInvestCity" value="<fmt:formatNumber value='${params.planInvestCity }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.planInvestCity2" id="planInvestCity2" value="<fmt:formatNumber value='${params.planInvestCity2 }' pattern='#0.##'/>" />
							</td>
							<th>融资(含银行贷款)：</th>
							<td colspan="2">
								<input type="text" class="text moneyText" name="params.planInvestBank" id="planInvestBank" value="<fmt:formatNumber value='${params.planInvestBank }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.planInvestBank2" id="planInvestBank2" value="<fmt:formatNumber value='${params.planInvestBank2 }' pattern='#0.##'/>" />
							</td>
						</tr>
						<tr class="moreSearchInfo">
							<th>自有资金：</th>
							<td>
								<input type="text" class="text moneyText" name="params.planInvestCompany" id="planInvestCompany" value="<fmt:formatNumber value='${params.planInvestCompany }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.planInvestCompany2" id="planInvestCompany2" value="<fmt:formatNumber value='${params.planInvestCompany2 }' pattern='#0.##'/>" />
							</td>
							<th>其它：</th>
							<td colspan="4">
								<input type="text" class="text moneyText" name="params.planInvestOther" id="planInvestOther" value="<fmt:formatNumber value='${params.planInvestOther }' pattern='#0.##'/>" />
								到&nbsp;
								<input type="text" class="text moneyText" name="params.planInvestOther2" id="planInvestOther2" value="<fmt:formatNumber value='${params.planInvestOther2 }' pattern='#0.##'/>" />
							</td>
						</tr-->
					</table>	
				</td>
			</tr>
		</table>
		<table style="width:100%;height:300">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">项目信息管理</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;height:300">
					 <span style="float: right;">  
					  		<input type="button" class="btn" id="exportBtn" value="导出项目汇总表" onclick="exportexcel('${ctx}/project/pjbaseinfo/expExcel.ac','tabList',2);"/>          
					  		
					  		<%
			            		if(user!=null&&user.getUserType()!=null&&!user.getUserType().equals(IConstants.USER_TYPE_5)){
			            	%>
							    	<c:if test="${objectMap['XMSB_XM_XMXXGL_ADD']}">
					            	 <input type="button" class="btn" id="addBtn" value="新增"/>
					            	</c:if>
			            	<%
			            		}
			            		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){
			            	%>
					            	<c:if test="${objectMap['XMSB_XM_XMXXGL_DEL']}">
					            	 <input type="button" class="btn" onClick="del('')" id="delBtn" value="批量删除"/> 
					            	</c:if>
			            	<%
			            		}
			            	%>
		               	</span>   
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
				<td width="3%">序号</td>
				<td width="10%">项目编码</td>
				<td width="10%">项目名称</td>
				<td width="10%">项目业主</td>
				<td width="6%">资金性质</td>
				<td width="8%">处理单位</td>
				<td width="4%">处理人</td>
				<td width="6%">处理时间</td>
				<td width="6%">当前处理部门</td>
				<td width="10%">项目状态</td>
				<td width="10%">修改时间</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="obj" varStatus="status">
			<tr ${obj.deleted==1?'bgcolor="#BDB6AD"':''}>
				<td align="center">
					<input type="checkbox"  name="id" value="${obj.projectid}" class="subcheck"/>
				</td>
				<td align="center">${page.size*page.current+status.index+1}&nbsp;</td>
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
				<!--td><s:property value="sblxMap[#attr.obj.declareType]"/>&nbsp;</td-->
				<td>${obj.sysOrganizationByDeclareunitsid!=null?obj.sysOrganizationByDeclareunitsid.organizationName:''}&nbsp;</td>
				<td>${obj.xmsbZjxz.zjxzmc }&nbsp;</td>
				<td>${obj.auditdeptname}&nbsp;</td>
				<td>${obj.recordername}&nbsp;</td>
				<td><fmt:formatDate value='${obj.recordertime}' pattern='yyyy-MM-dd'/></td>
				<td>${obj.nextauditdeptname}&nbsp;</td>
				<td><s:property value="xmztMap[#attr.obj.pjstatus]"/>&nbsp;</td>
				<td><fmt:formatDate value='${obj.modifiedTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>
					<a href="javascript:exportOneexcel(${obj.projectid})">导出</a>
					<c:if test="${objectMap['XMSB_XM_XMXXGL_MOD']}">
					<a href="javascript:resave(${obj.projectid})">${obj.projectcode!=null&&obj.projectcode.substring(0,2)=='PJ'?'&nbsp;&nbsp;重新申报':'' }</a>
					<%
						if(user!=null&&user.getUserType()!=null&&user.getUserType()==IConstants.USER_TYPE_5){
					%>
							<a href="${ctx}/project/pjbaseinfo/edit.ac?id=${obj.projectid}&from=2">&nbsp;&nbsp;编辑</a>
					<%
						}
						else{
					%>
							<a href="${ctx}/project/pjbaseinfo/edit.ac?id=${obj.projectid}&from=1">${obj.pjstatus==0||obj.pjstatus==12?'&nbsp;&nbsp;编辑':''}</a>
					<%
						}
					%>
					
					</c:if>
					<c:if test="${objectMap['XMSB_XM_XMXXGL_VIEW']}">
						<a href="javascript:viewSh(${obj.projectid})">${obj.pjstatus>1?'&nbsp;&nbsp;查看审核意见':''}</a>
					</c:if>
					<c:if test="${objectMap['XMSB_XM_XMXXGL_DEL']}">
					
					<%
						if(user!=null&&user.getUserType()!=null&&user.getUserType()==IConstants.USER_TYPE_5){
					%>
							<a href="javascript:del('${obj.projectid}')">${obj.deleted!=1?'&nbsp;&nbsp;删除':''}</a>
							<a href="javascript:restoreProject(${obj.projectid})">${obj.deleted==1?'&nbsp;&nbsp;恢复':''}</a>
					<%
						}
						else{
					%>
							<a href="javascript:del('${obj.projectid}')">${obj.pjstatus==0||obj.pjstatus==12?'&nbsp;&nbsp;删除':''}</a>
					<%
						}
					%>
					
					</c:if>
					<!-- 
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:goxmps('${obj.projectcode}','${obj.projectname}','${obj.declarerid}','${obj.contacttel}','${obj.projectcontent}')" ></a>${obj.pjstatus>0?'填写评审数据':''}
					 -->
				</td>
			</tr>
		    </c:forEach>
			<tr>
				<td colspan="13" class="line2" style="text-align: right;">
					<%@include file="../../changePage.jsp" %>
				</td>
		    </tr>
		</tbody>
	</table>
	
	</form>
</body>
</html>
