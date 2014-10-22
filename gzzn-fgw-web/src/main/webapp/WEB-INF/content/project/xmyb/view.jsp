<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>项目月报查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/2_event.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<link href="${ctx}/resources/css/style.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/event.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-ztree/jquery.ztree.all-3.5.min.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/cn.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/core.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/global.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/dialog.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/mousewheel.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-grid_3.0b/pager.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-grid_3.0b/grid.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-ui/js/jquery-ui.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/1.10.0/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/RBAlertMSG.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery.nicescroll.js" type="text/javascript"></script>
<script type="text/javascript">
//<!--
$(document).ready(function() {
		<%
			SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		%>
});
//-->

</script>
</head>
<body>

	<script type="text/javascript">
	
	
	//文件的下载	
	  function download(id)
	   {
		options={id:id};
	    $.ajax({
	    	type:"POST",
	    	url:'checkFile.ac',
	    	data:options,
	    	success:function(result){
	    		if(result.info=="true"){
	    		  document.location.href="download.ac?id="+id;
				}
				else{
					alert("该文件不存在!");
				}
	    	},
	    	error:function() { 
	    		alert("出错了!");
			}, 
	    	dataType:'json'
		});
	  }
	
	
	</script>
	<form action="${ctx}/project/xmyb/save.ac" method="post"  enctype="multipart/form-data" id="editForm" >
		<input type="hidden" id="xmybId" name="obj.xmybId" value="${obj!=null&&obj.xmybId!=null?obj.xmybId:null}"/>
		<input type="hidden" name="obj.xmybzt" value="${obj!=null&&obj.xmybzt!=null?obj.xmybzt:null}"/>
		<div class="editDiv">
			<fieldset style="width:80%;height:100%">
				<legend>项目月报信息</legend>
			<table class="editTab" id="jbTab">
				<tr>
					<th>项目</th>	
			         <td >
				      <input type="text" id="projectname" name="obj.pjbaseinfo.projectname" value="${obj.pjbaseinfo.projectname}" readonly="readonly" style="background:#EEE;width:400px" />
			      	</td>
				</tr>
				<tr>
					<th>月报年份</th>
					<td>
						<input type="text" id="nf" name="obj.nf" value="${obj.nf}" size="30" readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<tr>
					<th>月报月份</th>
					<td>
					<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
						<select id="yf" name="obj.yf" readonly="readonly" style="background:#EEE">
							<option value="">==请选择==</option>
							<option value="01" ${obj.yf=='01'?'selected':''}>1月</option>
							<option value="02" ${obj.yf=='02'?'selected':''}>2月</option>
							<option value="03" ${obj.yf=='03'?'selected':''}>3月</option>
							<option value="04" ${obj.yf=='04'?'selected':''}>4月</option>
							<option value="05" ${obj.yf=='05'?'selected':''}>5月</option>
							<option value="06" ${obj.yf=='06'?'selected':''}>6月</option>
							<option value="07" ${obj.yf=='07'?'selected':''}>7月</option>
							<option value="08" ${obj.yf=='08'?'selected':''}>8月</option>
							<option value="09" ${obj.yf=='09'?'selected':''}>9月</option>
							<option value="10" ${obj.yf=='10'?'selected':''}>10月</option>
							<option value="11" ${obj.yf=='11'?'selected':''}>11月</option>
							<option value="12" ${obj.yf=='12'?'selected':''}>12月</option>
						</select>
						</span>
					</td>
				</tr>
				<tr>
					<th>月报内容(2000字以内)</th>
					<td>
						<textarea  id="nr" name="obj.nr" readonly="readonly" style="background:#EEE">${obj.nr}</textarea>
					</td>
				</tr>
				<tr>
					<th>月报附件</th>
					<td>
						<s:iterator value="obj.xmsbXmybfjs" id="dto" status="st">
							<p class="fileP" id="xmsb${st.index}" >
								${st.index+1}、<s:property value="#dto.fjmc"/>&nbsp;&nbsp;
								<a href="javascript:download('<s:property value="#dto.xmybfjId"/>')">下载</a>
							</p>
						</s:iterator>
					</td>
				</tr>
			</table>
		</fieldset>
			<%-- <br/>
			<br/>
			<fieldset style="width:99%;height:100%;">
				<legend>已上传的月报附件</legend>
			<table class="editTab" id="qqjzTab">
				<s:iterator value="obj.xmsbXmybfjs" id="dto" status="st">
					<tr id="xmsb${st.index}">
						<td>
							<s:property value="#dto.fjmc"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.xmybfjId"/>')">下载</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			</fieldset> --%>
		
		<table  id="submitTab" style="width: 80%;">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;" align="center">
					<div>
						 <input type="button" class="btn" id="backBtn" value="关闭" onclick="window.close()">
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>