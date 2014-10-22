<%@page import="com.gzzn.fgw.webUtil.PropertiesUtil"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.*"%>
<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
项目分派
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/1_event.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<script type="text/javascript" src="${ctx}/resources/js/tc.all.js"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/xmfp/assign.js" type="text/javascript"></script>
<script type="text/javascript">
//<!--
$(document).ready(function() {
		//返回
		//返回
		$("#backBtn").click(function() {
			window.go(-1);
		});
		$("#pjinvestcenter").blur(function(){
			getPjinvestsum();
		  });
		$("#pjinvestprovince").blur(function(){
			getPjinvestsum();
		  });
		$("#pjinvestcity").blur(function(){
			getPjinvestsum();
		  });
		$("#pjinvesttown").blur(function(){
			getPjinvestsum();
		  });
		$("#pjinvestcompany").blur(function(){
			getPjinvestsum();
		  });
		$("#pjinvestbank").blur(function(){
			getPjinvestsum();
		  });
		$("#pjinvestother").blur(function(){
			getPjinvestsum();
		  });
		
		$("#planinvestcenter").blur(function(){
			getPlaninvestsum();
		  });
		$("#planinvestprovince").blur(function(){
			getPlaninvestsum();
		  });
		$("#planinvestcity").blur(function(){
			getPlaninvestsum();
		  });
		$("#planinvesttown").blur(function(){
			getPlaninvestsum();
		  });
		$("#planinvestcompany").blur(function(){
			getPlaninvestsum();
		  });
		$("#planinvestbank").blur(function(){
			getPlaninvestsum();
		  });
		$("#planinvestother").blur(function(){
			getPlaninvestsum();
		  });
		<%
			SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			String deptCode = user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null?user.getSysDept().getFullcode():null;
			String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty("fgwtzc.fullcode");
		%>
});
//-->
function getPjinvestsum(){
	
	$("#pjinvestsum").attr("value",Number($("#pjinvestcenter").val())+Number($("#pjinvestprovince").val())+Number($("#pjinvestcity").val())+Number($("#pjinvesttown").val())
			+Number($("#pjinvestcompany").val())+Number($("#pjinvestbank").val())+Number($("#pjinvestother").val()));
	$("#pjinvestsum").attr("value",Math.round($("#pjinvestsum").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	
	$("#pjinvestcenter").attr("value",Math.round($("#pjinvestcenter").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#pjinvestprovince").attr("value",Math.round($("#pjinvestprovince").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#pjinvestcity").attr("value",Math.round($("#pjinvestcity").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#pjinvesttown").attr("value",Math.round($("#pjinvesttown").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#pjinvestcompany").attr("value",Math.round($("#pjinvestcompany").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#pjinvestbank").attr("value",Math.round($("#pjinvestbank").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#pjinvestother").attr("value",Math.round($("#pjinvestother").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
}
function getPlaninvestsum(){
	$("#planinvestsum").attr("value",Number($("#planinvestcenter").val())+Number($("#planinvestprovince").val())+Number($("#planinvestcity").val())+Number($("#planinvesttown").val())
			+Number($("#planinvestcompany").val())+Number($("#planinvestbank").val())+Number($("#planinvestother").val()));
	$("#planinvestsum").attr("value",Math.round($("#planinvestsum").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	
	$("#planinvestcenter").attr("value",Math.round($("#planinvestcenter").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#planinvestprovince").attr("value",Math.round($("#planinvestprovince").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#planinvestcity").attr("value",Math.round($("#planinvestcity").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#planinvesttown").attr("value",Math.round($("#planinvesttown").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#planinvestcompany").attr("value",Math.round($("#planinvestcompany").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#planinvestbank").attr("value",Math.round($("#planinvestbank").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
	$("#planinvestother").attr("value",Math.round($("#planinvestother").val()*Math.pow(10,2))/ Math.pow(10, 2));//四舍五入
}
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
	
	function saveProject(){
		var validator2 = $("#editForm").validate({
			 event:"submit",
			 rules: {
	             "pjauditlog.pjauditmind":{   
	            	 byteMaxLength: 200
	             }
		      },
		      messages:{
		    	  "pjauditlog.pjauditmind":{
		    		  byteMaxLength:"不能全为空格且字数最多是200"
		    	  }
		      },
		      submitHandler: function(form){
			    	form.submit();
			    }	
		});
		if($("#nextUserId").val()==null||$("#nextUserId").val()==""){
			alert("跟进人不能为空!");
			return;
		}
		$("#saveBtn2").attr("disabled","disabled");
		$("#editForm").submit();
	}
	</script>
	<form action="${ctx}/project/xmfp/save.ac" method="post"  enctype="multipart/form-data" id="editForm" >
		<input type="hidden" id="projectid" name="obj.projectid" value="${obj!=null&&obj.projectid!=null?obj.projectid:null}"/>
		<input type="hidden" id="deleted" name="obj.deleted" value="${obj!=null&&obj.deleted!=null?obj.deleted:null}"/>
		<input type="hidden" id="pjauditlogid" name="pjauditlog.pjauditlogid" value="${pjauditlog!=null&&pjauditlog.pjauditlogid!=null?pjauditlog.pjauditlogid:null}"/>
		<input type="hidden" id="pjaudittype" name="pjauditlog.pjaudittype" value="${pjauditlog!=null&&pjauditlog.pjaudittype!=null?pjauditlog.pjaudittype:null}"/>
		<input type="hidden" name="subObj.pjinvestid" value="${subObj!=null&&subObj.pjinvestid!=null?subObj.pjinvestid:null}"/>
		<input type="hidden" name="obj.sysUserByRecorderid.userId" value="${obj!=null&&obj.sysUserByRecorderid!=null&&obj.sysUserByRecorderid.userId!=null?obj.sysUserByRecorderid.userId:null}"/>
		<input type="hidden" name="obj.sysUserByNexttacheer.userId" value="${obj!=null&&obj.sysUserByNexttacheer!=null&&obj.sysUserByNexttacheer.userId!=null?obj.sysUserByNexttacheer.userId:null}"/>
		<input type="hidden" name="obj.sysOrganizationByRecordOrgan.organizationId" value="${obj!=null&&obj.sysOrganizationByRecordOrgan!=null&&obj.sysOrganizationByRecordOrgan.organizationId!=null?obj.sysOrganizationByRecordOrgan.organizationId:null}"/>
		<input type="hidden" name="obj.xmztz" value="${obj.xmztz}"/>
		<input type="hidden" name="obj.auditdept" value="${obj!=null&&obj.auditdept!=null?obj.auditdept:null}"/>
		<input type="hidden" name="obj.auditdeptname" value="${obj.auditdeptname}"/>
		<input type="hidden" name="obj.nextauditdept" value="${obj!=null&&obj.nextauditdept!=null?obj.nextauditdept:null}"/>
		<input type="hidden" name="obj.nextauditdeptname" value="${obj.nextauditdeptname}"/>
		<div class="editDiv">
			<fieldset style="width:80%;">
				<legend>项目基本信息</legend>
			<table class="editTab" id="jbTab">
				<tr>
					<th>项目分类</th>
					<td>
					<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
						<select id="xmfl" name="obj.xmfl" style="width:212px";background:#EEE">
							<option value="0">==请选择==</option>
							<option value="1" ${obj.xmfl==1?'selected':''}>基本建设投资类项目</option>
							<option value="2" ${obj.xmfl==2?'selected':''}>更新改造投资类项目</option>
							<option value="3" ${obj.xmfl==3?'selected':''}>其他固定资产投资类项目</option>
					</select>
					</span>
					</td>
				</tr>
				<tr>
					<th>项目类型</th>	
			         <td >
			        	<input type="text" name="obj.xmsbXmlx.xmlxmc" id="xmlxName"
				          value="<s:property value="obj.xmsbXmlx.xmlxmc"/>"  readonly="readonly" style="width:212px";background:#EEE"/>
				        <input type="hidden" name="obj.xmsbXmlx.xmlxId" id="xmlxId" value="<s:property value="obj.xmsbXmlx.xmlxId"/>"/>
				        <input type="hidden" name="obj.xmsbXmlx.xmlxdm" id="xmlxdm" value="<s:property value="obj.xmsbXmlx.xmlxdm"/>"/>
			      	</td>
				</tr>
				<tr>
					<th>项目编号</th>
					<td>
						<input type="text" id="projectcode" name="obj.projectcode" value="${obj.projectcode}" readonly="readonly" style="background:#EEE;width:200px" />
					</td>
				</tr>
				<tr>
					<th>项目名称</th>
					<td>
						<input type="text" id="projectname" name="obj.projectname" value="${obj.projectname}" readonly="readonly" style="background:#EEE;width:200px" />
					</td>
				</tr>
				<tr>
					<th>项目业主</th>
					<td>
						<input type="text" name="obj.sysOrganizationByDeclareunitsid.organizationName" id="sysOrganizationByDeclareunitsidName"
				          value="<s:property value="obj.sysOrganizationByDeclareunitsid.organizationName"/>"  readonly="readonly" style="background:#EEE;width:200px"/>
				        <input type="hidden" name="obj.sysOrganizationByDeclareunitsid.organizationId" id="sysOrganizationByDeclareunitsidId"  
				        value="<s:property value="obj.sysOrganizationByDeclareunitsid.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目负责人</th>
					<td>
						<input type="text" id="projectprincipal" name="obj.projectprincipal" value="${obj.projectprincipal}"  readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<tr>
					<th>申报日期</th>
					<td>
						<input type="text" id="declartime" name="obj.declartime" value="<fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/>"   readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<tr>
					<th>项目联系人</th>
					<td>
						<input type="text" id="declarerid" name="obj.declarerid" value="${obj.declarerid}"  readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<tr>
					<th>联系人手机号</th>
					<td>
						<input type="text" id="mobilePhone" name="obj.mobilePhone" value="${obj.mobilePhone}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td>
						<input type="text" id="contacttel" name="obj.contacttel" value="${obj.contacttel}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>联系地址</th>
					<td>
						<input type="text" id="contactaddress" name="obj.contactaddress" value="${obj.contactaddress}" readonly="readonly" style="background:#EEE;width:200px" />
					</td>
				</tr>
				<tr>
					<th>行业类别</th>
					<td>
						<input type="text" name="obj.xmsbHylb.hylbmc" id="hylbName"
				          value="<s:property value="obj.xmsbHylb.hylbmc"/>"  readonly="readonly" style="background:#EEE;width:200px"/>
				        <input type="hidden" name="obj.xmsbHylb.hylbId" id="hylbId"  
				        value="<s:property value="obj.xmsbHylb.hylbId"/>"/>
					</td>
				</tr>
				<tr>
					<th>邮政编码</th>
					<td>
						<input type="text" id="yzbm" name="obj.yzbm" value="${obj.yzbm}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>项目储备类别</th>
					<td>
						<input type="text" id="xmcblb" name="obj.xmcblb" value="${obj.xmcblb}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>是否重大项目</th>	
			         <td>
			         	<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
				        	<select id="obj.sfzdxm" name="obj.sfzdxm" style="width:100px;background:#EEE">
					        	<option value="9">==请选择==</option>
					        	<option value="0" ${obj.sfzdxm==0?'selected':''}>否</option>
					        	<option value="1" ${obj.sfzdxm==1?'selected':''}>是</option>
							</select>
						</span>
			      	</td>
				</tr>
			</table>
			</fieldset>
			
			<fieldset style="width:80%;">
				<legend>项目投资信息</legend>
			<table class="editTab" id="tzTab">
				<tr bgcolor="#D3E4FF">
					<td></td>
					<td>中央财政资金</td>	
			        <td>省财政资金</td>
			        <td>市财政资金 </td>
			        <td>区(县)财政资金</td>
			        <td>企业自有资金</td>
			        <td>融资(含银行贷款)</td>
			        <td>其它资金</td>
			        <td>合计</td>
			        <td>市财政资金安排渠道建议(注明资金来源类型或名称)</td>
				</tr>
				<tr>
					<th>项目总投资(万元)</th>
					<td>
						<input type="text" id="pjinvestcenter" name="subObj.pjinvestcenter" value="${subObj!=null?subObj.pjinvestcenter:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="pjinvestprovince" name="subObj.pjinvestprovince" value="${subObj!=null?subObj.pjinvestprovince:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="pjinvestcity" name="subObj.pjinvestcity" value="${subObj!=null?subObj.pjinvestcity:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="pjinvesttown" name="subObj.pjinvesttown" value="${subObj!=null?subObj.pjinvesttown:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="pjinvestcompany" name="subObj.pjinvestcompany" value="${subObj!=null?subObj.pjinvestcompany:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="pjinvestbank" name="subObj.pjinvestbank" value="${subObj!=null?subObj.pjinvestbank:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="pjinvestother" name="subObj.pjinvestother" value="${subObj!=null?subObj.pjinvestother:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="pjinvestsum" name="subObj.pjinvestsum" value="${subObj!=null?subObj.pjinvestsum:0.00}" readonly="true" style="background:#EEE;"/>
					</td>
					<td>
						<input type="text" id="pjinvestadvice" name="subObj.pjinvestadvice" value="${subObj.pjinvestadvice}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>
			        <select id="subObj.planinvestyear" name="subObj.planinvestyear" readonly="readonly" style="width:60px;background:#EEE">
						<c:choose>
							<c:when  test="${subObj.planinvestyear==null}">
		        				<c:forEach items="${session.sessionDirectionaryitmesNf}" var="dto">
									<option value="${dto.itemvalue}" ${dto.itemvalue==nextYear?'selected':''}>${dto.itemtext}</option>
								</c:forEach>
		        			</c:when>
		        			<c:otherwise>
								<c:forEach items="${session.sessionDirectionaryitmesNf}" var="dto">
									<c:choose>
									<c:when test="${dto.itemvalue==subObj.planinvestyear}">
										<option value="${dto.itemvalue}" selected>${dto.itemtext}</option>
									</c:when>
									<c:otherwise>
										<option value="${dto.itemvalue}">${dto.itemtext}</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
			        	</c:choose>
					</select>
					年投资计划(万元)
					</th>
					<td>
						<input type="text" id="planinvestcenter" name="subObj.planinvestcenter" value="${subObj!=null?subObj.planinvestcenter:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestprovince" name="subObj.planinvestprovince" value="${subObj!=null?subObj.planinvestprovince:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestcity" name="subObj.planinvestcity" value="${subObj!=null?subObj.planinvestcity:0.00}"  readonly="readonly" style="background:#EEE"/>
					</td>
					<td>
						<input type="text" id="planinvesttown" name="subObj.planinvesttown" value="${subObj!=null?subObj.planinvesttown:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestcompany" name="subObj.planinvestcompany" value="${subObj!=null?subObj.planinvestcompany:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestbank" name="subObj.planinvestbank" value="${subObj!=null?subObj.planinvestbank:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestother" name="subObj.planinvestother" value="${subObj!=null?subObj.planinvestother:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestsum" name="subObj.planinvestsum" value="${subObj!=null?subObj.planinvestsum:0.00}" readonly="true" style="background:#EEE;" />
					</td>
					<td>
						<input type="text" id="planinvestadvice" name="subObj.planinvestadvice" value="${subObj.planinvestadvice}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
			</table>
			</fieldset>
			
			
			<fieldset style="width:80%;">
				<legend>项目建设信息</legend>
			<table class="editTab" id="jbTab">
				<tr>
					<th>主管单位</th>
					<td>
						<input type="text" name="obj.sysOrganizationByDirectorunitsid.organizationName" id="sysOrganizationByDirectorunitsidName"
				          value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationName"/>"  readonly="readonly" style="background:#EEE"/>
				        <input type="hidden" name="obj.sysOrganizationByDirectorunitsid.organizationId" id="sysOrganizationByDirectorunitsid"  
				        value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目建设地址</th>
					<td>
						<input type="text" id="projectaddress" name="obj.projectaddress" value="${obj.projectaddress}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>所属区域</th>
					<td>
						<input type="text" name="obj.sysXq.xqmc" id="xqName"
				          value="<s:property value="obj.sysXq.xqmc"/>"  readonly="readonly" style="background:#EEE"/>
				        <input type="hidden" name="obj.sysXq.xqId" id="xqId"  value="<s:property value="obj.sysXq.xqId"/>"/>
				        <input type="hidden" name="obj.sysXq.xzqydm" id="xzqydm"  value="<s:property value="obj.sysXq.xzqydm"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目建设管理（代建）单位</th>
					<td>
						<input type="text" id="manageunitsname" name="obj.manageunitsname" value="${obj.manageunitsname}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>主要建设内容及规模</th>
					<td>
						<textarea  id="projectcontent" name="obj.projectcontent" readonly="readonly" style="background:#EEE">${obj.projectcontent}</textarea>
					</td>
				</tr>
				<tr>
					<th>其中
					<select id="finishcontentyear" name="obj.finishcontentyear">
						<c:choose>
							<c:when  test="${obj.finishcontentyear==null}">
		        				<c:forEach items="${session.sessionDirectionaryitmesNf}" var="dto">
									<option value="${dto.itemvalue}" ${dto.itemvalue==nextYear?'selected':''}>${dto.itemtext}</option>
								</c:forEach>
		        			</c:when>
		        			<c:otherwise>
								<c:forEach items="${session.sessionDirectionaryitmesNf}" var="dto">
									<c:choose>
									<c:when test="${dto.itemvalue==obj.finishcontentyear}">
										<option value="${dto.itemvalue}" selected>${dto.itemtext}</option>
									</c:when>
									<c:otherwise>
										<option value="${dto.itemvalue}">${dto.itemtext}</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
			        	</c:choose>
					</select>
					年度建设内容(字数限制在100字以内)
					<font class="msg">*</font></th>
					<td>
						<textarea  id="finishcontent" name="obj.finishcontent" readonly="readonly" style="background:#EEE">${obj.finishcontent}</textarea>
					</td>
				</tr>
				<tr>
					<th>预计至
					<select id="expectfinishinvestyear" name="obj.expectfinishinvestyear">
						<c:choose>
							<c:when  test="${obj.expectfinishinvestyear==null}">
		        				<c:forEach items="${session.sessionDirectionaryitmesNf}" var="dto">
									<option value="${dto.itemvalue}" ${dto.itemvalue==nextYear?'selected':''}>${dto.itemtext}</option>
								</c:forEach>
		        			</c:when>
		        			<c:otherwise>
								<c:forEach items="${session.sessionDirectionaryitmesNf}" var="dto">
									<c:choose>
									<c:when test="${dto.itemvalue==obj.expectfinishinvestyear}">
										<option value="${dto.itemvalue}" selected>${dto.itemtext}</option>
									</c:when>
									<c:otherwise>
										<option value="${dto.itemvalue}">${dto.itemtext}</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
			        	</c:choose>
					</select>
					年底累计完成投资
					</th>
					<td>
						合计：<input type="text" id="expectfinishinvest" name="obj.expectfinishinvest" value="${obj!=null?obj.expectfinishinvest:0.00}" readonly="readonly" style="background:#EEE" />
						万元，其中市本级财政资金：<input type="text" id="expectfinishotherinvest" name="obj.expectfinishotherinvest" value="${obj!=null?obj.expectfinishotherinvest:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>开工日期或计划开工日期</th>
					<td>
						<input type="text" id="workdate" name="obj.workdate" value="<fmt:formatDate value='${obj.workdate}' pattern='yyyy-MM-dd'/>" size="30"  readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<tr>
					<th>计划竣工日期</th>
					<td>
						<input type="text" id="finishdate" name="obj.finishdate" value="<fmt:formatDate value='${obj.finishdate}' pattern='yyyy-MM-dd'/>" size="30"   readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<tr>
					<th>建设起止年限</th>
					<td>
						<input type="text" id="startyear" name="obj.startyear" value="${obj.startyear}" readonly="readonly" style="background:#EEE"/>
					至
				        <input type="text" id="endyear" name="obj.endyear" value="${obj.endyear}" readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<tr>
					<th>项目进度</th>	
			         <td>
			         <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
			        	<select id="obj.xmjd" name="obj.xmjd" style="width:100px;background:#EEE">
			        		<option value="">==请选择==</option>
							<s:iterator value="#session.sessionXmjdMap" id="dto" status="st">
								<s:if test="#dto.itemvalue==#session.obj.xmjd">
									<option value="${dto.itemvalue}" selected>${dto.itemtext}</option>
								</s:if>
								<s:else>
									<option value="${dto.itemvalue}">${dto.itemtext}</option>
								</s:else>
							</s:iterator>
						</select>
					</span>
			      	</td>
				</tr>
			</table>
			</fieldset>
		<br/>
		<table  id="submitTab" style="width: 80%;">
			<tr>
				<td style="width: 100%;">
					<a href="${ctx}/project/pjbaseinfo/view.ac?id=${pjauditlog.pjbaseinfo.projectid}&type=0"><font color="blue">查看项目详细信息</font></a>
				</td>
			</tr>
		</table>
		<br/>
		<fieldset style="width:80%;height:100%;">
		<legend>处理历史信息</legend>
		<table width="80%" border="0" cellpadding="1" cellspacing="0" bordercolor="AEC2D5" class="list">
			<thead>
				<tr>
					<td width="2%">序号</td>
					<td width="8%">处理环节</td>
					<td width="8%">处理结果</td>
					<td width="8%">处理意见</td>
					<td width="8%">处理人</td>
					<td width="6%">处理时间</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pjauditlogs}" var="obj" varStatus="status">
				<tr>
					<td>${page.size*page.current+status.index+1}&nbsp;</td>
					<td><s:property value="pjaudittypeMap[#attr.obj.pjaudittype]"/>&nbsp;</td>
					<td>
						<c:choose>
							<c:when test="${obj.pjauditresult==1}">
								通过
							</c:when>
							<c:when test="${obj.pjauditresult==2}">
								审核不通过
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>
					</td>
					<td>${obj.pjauditmind}&nbsp;</td>
					<td>${obj.recordername}&nbsp;</td>
					<td><fmt:formatDate value='${obj.recordertime}' pattern='yyyy-MM-dd'/></td>
				</tr>
			    </c:forEach>
			</tbody>
		</table>
		</fieldset>
		<fieldset style="width:80%;height:100%">
				<legend>项目分派信息</legend>
		<table class="editTab" id="gcxxjdTab">
				<tr>
					<th width="200" align="right">
						跟进人：
					</th>
					<td>
						<input type="text" name="nextUser.userName" id="nextUserName" value="${nextUser.userName}" readonly="readonly"/>
						<input type="hidden" name="nextUser.UserId" id="nextUserId" value="${nextUser.userId }" />
					 </td>	
				</tr>
				<tr>
					<th width="200" align="right">
						分派意见(100字以内)：
					</th>
					<td>
				        <textarea  id="pjauditmind" name="pjauditlog.pjauditmind"></textarea>
					 </td>	
				</tr>
		</table>
		<table  id="submitTab" style="width: 80%;">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" id="saveBtn2" value="保存" onclick="saveProject()"> &nbsp;&nbsp;
						 <input type="reset" class="btn" id="resetBtn" value="重置"> &nbsp;&nbsp;
						 <input type="button" class="btn" id="backBtn" value="返回">
						</span>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>