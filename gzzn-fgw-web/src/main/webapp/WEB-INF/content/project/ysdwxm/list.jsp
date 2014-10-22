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
<title>预算单位项目</title>
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
		
		var auditdeptDatas=getDatas("./getSysDeptJson.json");
		$("#nextauditdeptName").focus(function(){
			initZtree("nextauditdept",auditdeptDatas,150,200);
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
	
</script>

	<form action="${ctx}/project/ysdwxm/list.ac" method="post"  enctype="multipart/form-data" id="zdxm">
		<table style="margin-top: 10px;width:100%" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="10">
			<tr>
				<td>
				      <table  class="topSearchTab" style="width:500" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="right">
							       项目名称：
							</th>
							<td>
								   <input type="text" style="width:148px" class="text" name="projectParams.projectname" value="${projectParams.projectname}" />
							</td>
							<th align="right">项目分类：</th>
							<td>
								<select id="xmfl" name="projectParams.xmfl" style="width:130px">
									<option value="">(无)</option>
									<option value="1" ${projectParams.xmfl==1?'selected':''}>基本建设投资类项目</option>
									<option value="2" ${projectParams.xmfl==2?'selected':''}>更新改造投资类项目</option>
							</select>
							</td>
							<th align="right">项目类型：</th>
							<td>
								<select id="xmlxId" name="projectParams.xmsbXmlx.xmlxId" style="width:130px">
									<option value="">(无)</option>
									<option value="2" ${projectParams.xmsbXmlx.xmlxId==2?'selected':''}>新建项目</option>
									<option value="6" ${projectParams.xmsbXmlx.xmlxId==6?'selected':''}>续建项目</option>
							</select>
							</td>
							<th align="right">
										审核结论录入部门：
							</th>
							<td>
										<input type="text" name="projectParams.nextauditdeptname" id="nextauditdeptName"
										           value="<s:property value="projectParams.nextauditdeptname"/>" readonly="readonly" class="text"/>
										        <input type="hidden" name="projectParams.nextauditdept" id="nextauditdeptId"  
										        value="<s:property value="projectParams.nextauditdept"/>"/>
										&nbsp;&nbsp;
								        
							</td>
							<th align="left" colspan="1">
								<c:if test="${objectMap['XMSB_XM_XMXXGL_VIEW']}">
								<input type="button" class="btn" id="queryBtn" value="查询" />&nbsp;&nbsp;
								<input type="button" class="btn" id="clearBtn" value="清空" />&nbsp;&nbsp;
								</c:if>
								<!--  <input type="button" class="btn" id="clearbtn" value="清空" /> -->
							</th>
						</tr>
						<tr>
							<th align="right">审核项目总投资：</th>
							<td colspan="1">
								<select id="expectfinishinvestType" name="projectParams.expectfinishinvestType" style="width:150px">
									<option value="">全部</option>
									<option value="1" ${projectParams.expectfinishinvestType==1?'selected':''}>等于零</option>
									<option value="2" ${projectParams.expectfinishinvestType==2?'selected':''}>大于零</option>
									<option value="3" ${projectParams.expectfinishinvestType==3?'selected':''}>1000万元以下</option>
									<option value="4" ${projectParams.expectfinishinvestType==4?'selected':''}>1000万元（含）以上</option>
							</select>
							</td>
							<th align="right">主管单位：</th>
							<td>
								<input type="text" class="text" name="projectParams.zgdw" id="zgdw" value="${projectParams.zgdw}"/>
							</td>
							<th align="right">
									   项目业主：
							</th>
							<td colspan="1">
								   <input type="text" class="text" name="projectParams.sysOrganizationByDeclareunitsid.organizationName" value="${projectParams.sysOrganizationByDeclareunitsid.organizationName}" />
							</td>
							<th align="right">
									   项目编码：
							</th>
							<td colspan="1">
								   <input type="text" class="text" name="projectParams.projectcode" value="${projectParams.projectcode}" />
							</td>
							<td></td>
						</tr>
					</table>	
				</td>
			</tr>
		</table>
		<table style="width:100%;height:300">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">预算单位项目</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;height:300">
					 <div>
					    <span style="float: right;">  
					  		<input type="button" class="btn" id="exportBtn" value="导出预算单位项目汇总表" onclick="exportexcel('${ctx}/project/ysdwxm/expExcel.ac','tabList',2);"/>          
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
				<td width="3%">序号</td>
				<td width="6%">项目分类</td>
				<td width="7%">项目类型</td>
				<td width="6%">项目编码</td>
				<td width="8%">项目名称</td>
				<td width="8%">项目库业主单位</td>
				<td width="8%">预算单位业主单位</td>
				<td width="4%">项目联系人</td>
				<td width="6%">联系人手机号</td>
				<td width="8%">投资项目基本情况描述</td>
				<td width="5%">项目库主管单位</td>
				<td width="6%">预算单位主管单位</td>
				<td width="6%">审核结论录入部门</td>
				<td width="8%">审核意见</td>
				<td width="6%">审核项目总投资（万元）</td>
				<td width="6%">审核年度计划投资（万元）</td>
			</tr>
		</thead>
			<c:forEach items="${page.list}" var="obj" varStatus="status">
			<tr>
				<td align="center">${page.size*page.current+status.index+1}&nbsp;</td>
				<td>${xmflMap[obj.xmfl]}&nbsp;</td>
				<td>${obj.xmsbXmlx!=null?obj.xmsbXmlx.xmlxmc:'' }&nbsp;</td>
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
				<td>${obj.sysOrganizationByDeclareunitsid!=null?obj.sysOrganizationByDeclareunitsid.organizationName:''}&nbsp;</td>
				<td>${obj.budgetDeclareOrgan}&nbsp;</td>
				<td>${obj.declarerid}&nbsp;</td>
				<td>${obj.mobilePhone}&nbsp;</td>
				<td>${obj.xmjbqkms}&nbsp;</td>
				<td>${obj.sysOrganizationByDirectorunitsId!=null?obj.sysOrganizationByDirectorunitsId.organizationName:''}&nbsp;</td>
				<td>${obj.budgetDirectorOrgan}&nbsp;</td>
				<td>${obj.pjauditdept}&nbsp;</td>
				<td>${obj.pjauditmind}&nbsp;</td>
				<td>${obj.pjinvestcitySh}&nbsp;</td>
				<td>${obj.planinvestcitySh}&nbsp;</td>
			</tr>
		    </c:forEach>
			<tr>
				<td colspan="16" class="line2" style="text-align: right;">
					<%@include file="../../changePage.jsp" %>
				</td>
		    </tr>
	</table>
	
	</form>
</body>
</html>
