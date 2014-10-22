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
<title>各处室审核通过项目</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/dclxm/review.js" type="text/javascript"></script>
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
		
		$("#setxmcblb").click(function(){
			var ids = "";
			$(".subcheck").each(function(){
				if($(this).attr("checked")){
					ids += "@" + $(this).val();
				}
			});
			if(ids.length == 0){
				mac.alert("请选择要设置储备类别的项目");
				return;
			}
			ids = ids.substring(1);
			setXmcblb(ids);
		});
		
		var pjstatusDatas=getDatas("./getProjectStatusJson.json");
		$("#pjstatusName").focus(function(){
			initZtree("pjstatus",pjstatusDatas,150,200);
		});
		var auditdeptDatas=getDatas("./getSysDeptJson.json");
		$("#nextauditdeptName").focus(function(){
			initZtree("nextauditdept",auditdeptDatas,150,200);
		});
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/project/pjbaseinfo/edit.ac";
		});
		
		$("#delBtn").click(function(){
			var ids = "";
			$(".subcheck").each(function(){
				if($(this).attr("checked")){
					ids += "@" + $(this).val();
				}
			});
			if(ids.length == 0){
				mac.alert("请选择要删除的记录");
				return;
			}
			ids = ids.substring(1);
			//alert(ids);
			mac.confirm('<p>确认要删除已选中的记录吗?</p>', function(){
				window.location = "${ctx}/project/pjbaseinfo/delete.ac?id=" + ids;
			}, null);
		});
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
		<%
			SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			String deptCode = user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null?user.getSysDept().getFullcode():null;
			String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty("fgwtzc.fullcode");
		%>
	});
//-->

	function toxmps(projectid){
		
		if (window.confirm("确定要委托投资评审吗？")) {
			window.location = "${ctx}/project/dclxm/mind.ac?id="+projectid;
		}
	}
	
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
	
	function setXmcblb(ids){
		var url = "${ctx}/project/zdxm/xmcblb.json?id=" + ids + "&pageFlag=2";
		var w = 500;     //宽度
		 var h = 310;   //高度
		 var t = (screen.height-h)/2; //离顶部距离
		 var l = (screen.width-w)/2; //离左边距离
		var w = window.open (url, 'newwindow', 'width='+ w +',height='+ h+',top='+t+',left='+l +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		w.focus();
	}
	
	function pjauditmind(projectId){
		var url = "${ctx}/project/ysdwxm/pjauditmind.json?id="+projectId;
		var pjauditmind = window.open (url, 'newwindow17', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		pjauditmind.focus();
		
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

	<form action="${ctx}/project/csshtgxm/list.ac" method="post"  enctype="multipart/form-data">
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
								<c:if test="${objectMap['XMSB_XM_DCLXMGL_VIEW']}">
								&nbsp;&nbsp;<input type="button" class="btn" id="queryBtn" value="查询" />
								<input type="button" class="btn" id="clearBtn" value="清空" />&nbsp;&nbsp;
							</c:if>
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
					</table>	
				</td>
			</tr>
		</table>
		<table style="width:100%;height:300">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">各处室审核通过项目</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;height:300">
					<span style="float: right;">  
					  	<input type="button" class="btn" id="exportBtn" value="导出项目汇总表" onclick="exportexcel('${ctx}/project/csshtgxm/expExcel.ac','tabList',2);"/>  
					  	<input type="button" class="btn" id="setxmcblb" value="批量设置项目储备类别"/>        
					</span>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
	

	<table width="100%" id="tabList" border="0" cellpadding="1" cellspacing="0"
		bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="2%"><input type="checkbox" id="checkAll"></td>
				<td width="2%">序号</td>
				<td width="8%">项目编码</td>
				<td width="8%">项目名称</td>
				<td width="8%">项目管理单位</td>
				<td width="8%">项目类型</td>
				<td width="8%">业主单位</td>
				<td width="6%">申报时间</td>
				<td width="6%">项目状态</td>
				<td width="6%">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="obj" varStatus="status">
			<tr>
				<td align="center">
					<input type="checkbox" value="${obj.projectid}" class="subcheck"/>
				</td>
				<td align="center">${page.size*page.current+status.index+1}&nbsp;</td>
				<td>${obj.projectcode}&nbsp;</td>
				<td>
				<c:choose>
					<c:when test="${objectMap['XMSB_XM_DCLXMGL_VIEW']}">
						<a href="javascript:view(${obj.projectid})">${obj.projectname}</a>
					</c:when>
					<c:otherwise>
						${obj.projectname}
					</c:otherwise>
				</c:choose>
				</td>
				<td>${obj.manageunitsname}&nbsp;</td>
				<td>${obj.xmsbXmlx.xmlxmc }&nbsp;</td>
				<td>${obj.sysOrganizationByDeclareunitsid!=null?obj.sysOrganizationByDeclareunitsid.organizationName:''}&nbsp;</td>
				<td><fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/></td>
				<td><s:property value="xmztMap[#attr.obj.pjstatus]"/>&nbsp;</td>
				<td>
					<a href="javascript:exportOneexcel(${obj.projectid})">导出</a>&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XM_ZDXMGL_SHYJ']}">
					<a href="javascript:pjauditmind(${obj.projectid})">审核结论录入</a>&nbsp;&nbsp;
					</c:if>
					<c:if test="${objectMap['XMSB_XM_DCLXMGL_XMPS']}">
						<a href="javascript:toxmps(${obj.projectid})"></a>${obj.pjstatus==6?'委托投资评审':''}
					</c:if>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		    </c:forEach>
			<tr>
				<td colspan="10" class="line2" style="text-align: right;">
					<%@include file="../../changePage.jsp" %>
				</td>
		    </tr>
		</tbody>
	</table>
	
	</form>
</body>
</html>
