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
<title>
申报项目信息查看
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/2_event.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<script type="text/javascript" src="${ctx}/resources/js/tc.all.js"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
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
		alert(id);
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
	<form action="${ctx}/project/pjbaseinfo/save.ac" method="post"  enctype="multipart/form-data" id="editForm" >
		<input type="hidden" id="projectid" name="obj.projectid" value="${obj!=null&&obj.projectid!=null?obj.projectid:null}"/>
		<input type="hidden" name="subObj.pjinvestid" value="${subObj!=null&&subObj.pjinvestid!=null?subObj.pjinvestid:null}"/>
		<input type="hidden" name="obj.pjstatus" value="${obj!=null&&obj.pjstatus!=null?obj.pjstatus:null}"/>
		<input type="hidden" name="obj.xmjd" value="${obj!=null&&obj.xmjd!=null?obj.xmjd:null}"/>
		<input type="hidden" name="obj.recordername" value="${obj.recordername}"/>
		<input type="hidden" name="obj.nextauditdept" value="${obj!=null&&obj.nextauditdept!=null?obj.nextauditdept:null}"/>
		<input type="hidden" name="obj.nextauditdeptname" value="${obj.nextauditdeptname}"/>
		<input type="hidden" name="obj.nexttacheername" value="${obj.nexttacheername}"/>
		<input type="hidden" name="obj.sysUserByRecorderid.userId" value="${obj!=null&&obj.sysUserByRecorderid!=null?obj.sysUserByRecorderid.userId:null}"/>
		<input type="hidden" name="obj.sysUserByNexttacheer.userId" value="${obj!=null&&obj.sysUserByNexttacheer!=null?obj.sysUserByNexttacheer.userId:null}"/>
		<input type="hidden" name="obj.declartime" value="<fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/>" size="30"/>
		<div class="editDiv">
			<fieldset style="width:80%;height:100%">
				<legend>项目基本信息</legend>
			<table class="editTab" id="jbTab">
				<tr>
					<th>项目类型<font class="msg">*</font></th>	
			         <td >
			        	<input type="text" name="obj.xmsbXmlx.xmlxmc" id="xmlxName"
				          value="<s:property value="obj.xmsbXmlx.xmlxmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.xmsbXmlx.xmlxId" id="xmlxId" value="<s:property value="obj.xmsbXmlx.xmlxId"/>"/>
				        <input type="hidden" name="obj.xmsbXmlx.xmlxdm" id="xmlxdm" value="<s:property value="obj.xmsbXmlx.xmlxdm"/>"/>
			      	</td>
				</tr>
				<tr>
					<th>项目编号</th>
					<td>
						<input type="text" id="projectcode" name="obj.projectcode" value="${obj.projectcode}" readonly="readonly" style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>项目名称<font class="msg">*</font></th>
					<td>
						<input type="text" id="projectname" name="obj.projectname" value="${obj.projectname}" />
					</td>
				</tr>
				<tr>
					<th>项目基本情况描述(100字以内)</th>
					<td>
						<textarea  id="xmjbqkms" name="obj.xmjbqkms">${obj.xmjbqkms}</textarea>
					</td>
				</tr>
				<tr>
					<th>主要建设内容及规模(100字以内)<font class="msg">*</font></th>
					<td>
						<textarea  id="projectcontent" name="obj.projectcontent">${obj.projectcontent}</textarea>
					</td>
				</tr>
				<tr>
					<th>其中
					<select id="obj.finishcontentyear" name="obj.finishcontentyear">
						<s:iterator value="#session.sessionDirectionaryitmesNf" id="dto" status="st">
							<s:if test="#dto.itemvalue==#session.obj.finishcontentyear">
								<option value="#dto.itemvalue" selected><s:property value="#dto.itemtext"/></option>
							</s:if>
							<s:else>
								<option value="#dto.itemvalue"><s:property value="#dto.itemtext"/></option>
							</s:else>
						</s:iterator>
					</select>
					年度建设内容
					<font class="msg">*</font></th>
					<td>
						<textarea  id="finishcontent" name="obj.finishcontent">${obj.finishcontent}</textarea>
					</td>
				</tr>
				<tr>
					<th>预计至
					<select id="obj.expectfinishinvestyear" name="obj.expectfinishinvestyear">
						<s:iterator value="#session.sessionDirectionaryitmesNf" id="dto" status="st">
							<s:if test="#dto.itemvalue==#session.obj.expectfinishinvestyear">
								<option value="#dto.itemvalue" selected><s:property value="#dto.itemtext"/></option>
							</s:if>
							<s:else>
								<option value="#dto.itemvalue"><s:property value="#dto.itemtext"/></option>
							</s:else>
						</s:iterator>
					</select>
					年底累计完成投资
					</th>
					<td>
						合计：<input type="text" id="expectfinishinvest" name="obj.expectfinishinvest" value="${obj!=null?obj.expectfinishinvest:0.00}" />
						万元，其中市本级财政资金：<input type="text" id="expectfinishotherinvest" name="obj.expectfinishotherinvest" value="${obj!=null?obj.expectfinishotherinvest:0.00}" />
					</td>
				</tr>
				<tr>
					<th>开工日期或计划开工日期<font class="msg">*</font></th>
					<td>
						<input type="text" id="workdate" name="obj.workdate" value="<fmt:formatDate value='${obj.workdate}' pattern='yyyy-MM-dd'/>" size="30"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr>
					<th>计划竣工日期<font class="msg">*</font></th>
					<td>
						<input type="text" id="finishdate" name="obj.finishdate" value="<fmt:formatDate value='${obj.finishdate}' pattern='yyyy-MM-dd'/>" size="30"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr>
					<th>建设起止年限<font class="msg">*</font></th>
					<td>
						<select id="obj.startyear" name="obj.startyear">
						<s:iterator value="#session.sessionDirectionaryitmesNf" id="dto" status="st">
							<s:if test="#dto.itemvalue==#session.obj.startyear">
								<option value="#dto.itemvalue" selected><s:property value="#dto.itemtext"/></option>
							</s:if>
							<s:else>
								<option value="#dto.itemvalue"><s:property value="#dto.itemtext"/></option>
							</s:else>
						</s:iterator>
					</select>至
				        <select id="obj.endyear" name="obj.endyear">
						<s:iterator value="#session.sessionDirectionaryitmesNf" id="dto" status="st">
							<s:if test="#dto.itemvalue==#session.obj.endyear">
								<option value="#dto.itemvalue" selected><s:property value="#dto.itemtext"/></option>
							</s:if>
							<s:else>
								<option value="#dto.itemvalue"><s:property value="#dto.itemtext"/></option>
							</s:else>
						</s:iterator>
					</select>
					</td>
				</tr>
				<tr>
					<th>项目业主<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.sysOrganizationByDeclareunitsid.organizationName" id="sysOrganizationByDeclareunitsidName"
				          value="<s:property value="obj.sysOrganizationByDeclareunitsid.organizationName"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.sysOrganizationByDeclareunitsid.organizationId" id="sysOrganizationByDeclareunitsidId"  
				        value="<s:property value="obj.sysOrganizationByDeclareunitsid.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目负责人<font class="msg">*</font></th>
					<td>
						<input type="text" id="projectprincipal" name="obj.projectprincipal" value="${obj.projectprincipal}" />
					</td>
				</tr>
				<tr>
					<th>申报日期<font class="msg">*</font></th>
					<td>
						<input type="text" id="declartime" name="obj.declartime" value="<fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/>" size="30"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr>
					<th>项目联系人<font class="msg">*</font></th>
					<td>
						<input type="text" id="declarerid" name="obj.declarerid" value="${obj.declarerid}" />
					</td>
				</tr>
				<tr>
					<th>联系电话<font class="msg">*</font></th>
					<td>
						<input type="text" id="contacttel" name="obj.contacttel" value="${obj.contacttel}" />
					</td>
				</tr>
				<tr>
					<th>项目所在地<font class="msg">*</font></th>
					<td>
						<input type="text" id="projectaddress" name="obj.projectaddress" value="${obj.projectaddress}" />
					</td>
				</tr>
				<tr>
					<th>联系地址<font class="msg">*</font></th>
					<td>
						<input type="text" id="contactaddress" name="obj.contactaddress" value="${obj.contactaddress}" />
					</td>
				</tr>
				<tr>
					<th>项目建设管理（代建）单位<font class="msg">*</font></th>
					<td>
						<input type="text" id="manageunitsname" name="obj.manageunitsname" value="${obj.manageunitsname}" />
					</td>
				</tr>
				<tr>
					<th>主管单位<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.sysOrganizationByDirectorunitsid.organizationName" id="sysOrganizationByDirectorunitsidName"
				          value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationName"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.sysOrganizationByDirectorunitsid.organizationId" id="sysOrganizationByDirectorunitsid"  
				        value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>行业类别</th>
					<td>
						<input type="text" name="obj.xmsbHylb.hylbmc" id="hylbName"
				          value="<s:property value="obj.xmsbHylb.hylbmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.xmsbHylb.hylbId" id="hylbId"  
				        value="<s:property value="obj.xmsbHylb.hylbId"/>"/>
					</td>
				</tr>
				<tr>
					<th>所属区域</th>
					<td>
						<input type="text" name="obj.sysXq.xqmc" id="xqName"
				          value="<s:property value="obj.sysXq.xqmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.sysXq.xqId" id="xqId"  value="<s:property value="obj.sysXq.xqId"/>"/>
				        <input type="hidden" name="obj.sysXq.xzqydm" id="xzqydm"  value="<s:property value="obj.sysXq.xzqydm"/>"/>
					</td>
				</tr>
				<tr>
					<th>资金性质<font class="msg">*</font></th>	
			         <td>
			        	<input type="text" name="obj.xmsbZjxz.zjxzmc" id="zjxzName"
				          value="<s:property value="obj.xmsbZjxz.zjxzmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.xmsbZjxz.zjxzId" id="zjxzId"  value="<s:property value="obj.xmsbZjxz.zjxzId"/>"/>
				        <input type="hidden" name="obj.xmsbZjxz.zjxzdm" id="zjxzdm"  value="<s:property value="obj.xmsbZjxz.zjxzdm"/>"/>
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
			
			<fieldset style="width:80%;height:100%;">
				<legend>项目投资信息</legend>
			<table class="editTab2" id="tzTab">
				<tr>
					<td></td>
					<td>中央财政资金</td>	
			        <td>省财政资金</td>
			        <td>市财政资金 </td>
			        <td>县(区)财政资金</td>
			        <td>企业自有资金</td>
			        <td>pjinvestbusiness资金</td>
			        <td>融资(含银行贷款)</td>
			        <td>其它资金</td>
			        <td>合计</td>
			        <td>市财政资金安排渠道建议(注明资金来源类型或名称)</td>
				</tr>
				<tr>
					<th>项目总投资(万元)</th>
					<td>
						<input type="text" id="pjinvestcenter" name="subObj.pjinvestcenter" value="${subObj!=null?subObj.pjinvestcenter:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestprovince" name="subObj.pjinvestprovince" value="${subObj!=null?subObj.pjinvestprovince:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestcity" name="subObj.pjinvestcity" value="${subObj!=null?subObj.pjinvestcity:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvesttown" name="subObj.pjinvesttown" value="${subObj!=null?subObj.pjinvesttown:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestcompany" name="subObj.pjinvestcompany" value="${subObj!=null?subObj.pjinvestcompany:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestbusiness" name="subObj.pjinvestbusiness" value="${subObj!=null?subObj.pjinvestbusiness:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestbank" name="subObj.pjinvestbank" value="${subObj!=null?subObj.pjinvestbank:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestother" name="subObj.pjinvestother" value="${subObj!=null?subObj.pjinvestother:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestsum" name="subObj.pjinvestsum" value="${subObj!=null?subObj.pjinvestsum:0.00}" readonly="true" style="background:#EEE;"/><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestadvice" name="subObj.pjinvestadvice" value="${subObj.pjinvestadvice}" /><font class="msg">*</font>
					</td>
				</tr>
				<tr>
					<th>
			        <select id="subObj.planinvestyear" name="subObj.planinvestyear" style="width:60px">
						<s:iterator value="#session.sessionDirectionaryitmesNf" id="dto" status="st">
							<s:if test="#dto.itemvalue==#session.subObj.planinvestyear">
								<option value="#dto.itemvalue" selected><s:property value="#dto.itemtext"/></option>
							</s:if>
							<s:else>
								<option value="#dto.itemvalue"><s:property value="#dto.itemtext"/></option>
							</s:else>
						</s:iterator>
					</select>
					年投资计划(万元)
					</th>
					<td>
						<input type="text" id="planinvestcenter" name="subObj.planinvestcenter" value="${subObj!=null?subObj.planinvestcenter:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestprovince" name="subObj.planinvestprovince" value="${subObj!=null?subObj.planinvestprovince:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestcity" name="subObj.planinvestcity" value="${subObj!=null?subObj.planinvestcity:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvesttown" name="subObj.planinvesttown" value="${subObj!=null?subObj.planinvesttown:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestcompany" name="subObj.planinvestcompany" value="${subObj!=null?subObj.planinvestcompany:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestbusiness" name="subObj.planinvestbusiness" value="${subObj!=null?subObj.planinvestbusiness:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestbank" name="subObj.planinvestbank" value="${subObj!=null?subObj.planinvestbank:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestother" name="subObj.planinvestother" value="${subObj!=null?subObj.planinvestother:0.00}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestsum" name="subObj.planinvestsum" value="${subObj!=null?subObj.planinvestsum:0.00}" readonly="true" style="background:#EEE;" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestadvice" name="subObj.planinvestadvice" value="${subObj.planinvestadvice}" /><font class="msg">*</font>
					</td>
				</tr>
			</table>
			</fieldset>
			
			<fieldset style="width:80%;height:100%;">
				<legend>项目前期工作进展情况</legend>
			<table class="editTab" id="qqjzTab">
				<tr>
					<td>审批完成情况（注明文号）</td>	
				</tr>
				
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype<10">
						<tr id="qqjz${st.index}">
							<td colspan="3"">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.fileName"/>&nbsp;&nbsp;文号：【<s:property value="#dto.wh"/>】&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</fieldset>
			
			<fieldset style="width:80%;height:100%;">
				<legend>项目申报依据</legend>
			<table class="editTab" id="xmsbyjTab">
				<tr>
					<td>提示：请附含市领导批示、会议纪要等！ </td>	
				</tr>
				<tr>
					<td><textarea  style="width:80%" id="declaregist" name="obj.declaregist" value="${obj.declaregist}"></textarea></td>	
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==10">
						<tr id="sbyj${st.index}">
							<td colspan="3">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.fileName"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</fieldset>
			<fieldset style="width:80%;height:100%;">
				<legend>工程形象进度</legend>
			<table class="editTab" id="gcxxjdTab">
				<tr>
					<td>提示：请附有关项目形象进度的图片！ </td>	
				</tr>
				<tr>
					<td><textarea  style="width:80%" id="declareplan" name="obj.declareplan" value="${obj.declareplan }"></textarea></td>	
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==11">
						<tr id="xxjd${st.index}">
							<td>
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.fileName"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</fieldset>
			<fieldset style="width:80%;height:100%;">
				<legend>存在问题</legend>
			<table class="editTab" id="czwtTab">
				<tr>
					<td><textarea  style="width:80%" id="declareproblem" name="obj.declareproblem" value="${obj.declareproblem }"></textarea></td>	
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==12">
						<tr id="czwt${st.index}">
							<td>
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.fileName"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</fieldset>
		</div>
		<fieldset style="width:80%;height:100%">
				<legend></legend>
		<table class="editTab2" id="gcxxjdTab" width="80%">
				<tr>
					<td>
						提交市发改委相关处室：<input type="text" name="obj.auditdeptname" id="auditdeptName"
				          value="<s:property value="obj.auditdeptname"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.auditdept" id="auditdeptId"  
				        value="${obj!=null&&obj.auditdept!=null?obj.auditdept:null}"/>
					 </td>	
				</tr>
		</table>
		</fieldset>
		<table  id="submitTab" style="width: 80%;">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;">
					<div align="center">
						 <input type="button" class="btn" id="backBtn" value="返回" onclick="window.go(-1)">
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>