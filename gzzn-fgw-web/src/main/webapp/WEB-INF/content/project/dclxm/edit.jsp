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
申报项目审核
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<script type="text/javascript" src="${ctx}/resources/js/tc.all.js"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/dclxm/review.js" type="text/javascript"></script>
<script type="text/javascript">
//<!--
$(document).ready(function() {
		$("#dx").hide();
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
		
		$("#pjauditresult").change(function(){
			changeCsShow();
		});
		<%
			SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			String deptCode = user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null?user.getSysDept().getFullcode():null;
			String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty("fgwtzc.fullcode");
		%>
		var auditdeptDatas=getDatas("./getSysDeptJson.json");
		$("#auditdeptName").focus(function(){
			initZtree("auditdept",auditdeptDatas,150,200);
		});
		$("#auditdeptName").attr("value",'');
		$("#auditdeptId").attr("value",'');
		$("#fwxgcs").hide();
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

function view(projectId){
	var url = "${ctx}/project/pjbaseinfo/view.json?id="+projectId+"&type=0";
window.open (url, 'newwindow', 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
}

function changeCsShow(){
	
	if($("#pjauditresult").val()==''){
		$("#dx").hide();
		$("#send").removeAttr("checked");
		$("#auditdeptName").attr("value",'');
		$("#auditdeptId").attr("value",'');
		$("#fwxgcs").hide();
	}
	else if($("#pjauditresult").val()==3){
		$("#dx").hide();
		$("#send").removeAttr("checked");
		$("#fwxgcs").show();
	}
	else if($("#pjauditresult").val()==2){
		$("#dx").show();
		$("#send").attr("checked","checked");
		
		$("#auditdeptName").attr("value",'');
		$("#auditdeptId").attr("value",'');
		$("#fwxgcs").hide();
	}
	else if($("#pjauditresult").val()==1){
		<%
		if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){
			if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)&&deptCode!=null&&deptCode.equalsIgnoreCase(fgwtzc)){//投资处长
		%>
				$("#auditdeptName").attr("value",'');
				$("#auditdeptId").attr("value",'');
				$("#fwxgcs").hide();
				$("#dx").show();
				$("#send").attr("checked","checked");
		<%
			}
			else if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)&&deptCode!=null&&!deptCode.equalsIgnoreCase(fgwtzc)){//各处室处长
		%>
				$("#fwxgcs").show();
				$("#dx").show();
				$("#send").attr("checked","checked");
		<%
			}
		}
		else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
		%>	
			if($("#pjauditresult").val()==1){
				$("#fwxgcs").show();	
				$("#dx").show();
				$("#send").attr("checked","checked");
			}
		<%
		}
		%>
	}
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
		var result = $("#pjauditresult").val();
		if(result==null||result==''){
			alert("处理结果不能为空!");
			return;
		}
		<%
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)){
				if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)&&deptCode!=null&&deptCode.equalsIgnoreCase(fgwtzc)){//投资处长
		%>
					if($("#pjauditresult").val()==3){//转交其他处室
						if($("#auditdeptName").val()==null||$("#auditdeptName").val()==""){
							
							alert("转交其他处室不能为空!");
							
							return;
						}
						else if($("#auditdeptName").val()=='<%=user.getSysDept().getDeptname()%>'){
						
							alert("投资处不能转交给投资处!");
							
							return;
						}
					}
		<%
				}
				else if(user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)&&deptCode!=null&&!deptCode.equalsIgnoreCase(fgwtzc)){//各处室处长
		%>
					if($("#pjauditresult").val()==1){//通过
						
						if($("#auditdeptName").val()==null||$("#auditdeptName").val()==""){
						
							alert("提交处室不能为空!");
							
							return;
						}
						else if($("#auditdeptName").val()!="投资处"){
						
							alert("各处室审核后只能提交给投资处审核!");
							
							return;
						}
					}
					else if($("#pjauditresult").val()==3){//转交其他处室
						if($("#auditdeptName").val()==null||$("#auditdeptName").val()==""){
							
							alert("转交其他处室不能为空!");
							
							return;
						}
						else if($("#auditdeptName").val()=='<%=user.getSysDept().getDeptname()%>'){
						
							alert("各处室不能转交给自己的处室!");
							
							return;
						}
					}
		<%
				}
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
		%>
				if($("#pjauditresult").val()==1||$("#pjauditresult").val()==''){
					
					if($("#auditdeptName").val()==null||$("#auditdeptName").val()==""){
					
						alert("提交发改委的处室不能为空!");
						
						return;
					}
				}
		<%
			}
			else {
		%>	
				if($("#pjauditresult").val()==1||$("#pjauditresult").val()==''){
						if($("#auditdeptName").val()==null||$("#auditdeptName").val()==''){
							alert("发往相关处室不能为空!");
							return;
						}
				}
		<%
			}
		%>
		
		var flag = document.getElementById("send").checked;
		$("#saveBtn2").attr("disabled","disabled");
		
		$("#editForm").ajaxSubmit(
				{
					url : '${ctx }/project/dclxm/save.json?flag='+flag,
					type : "post",
					dataType : "json",
					success : function(data) {
						alert(data.msg);
						self.close();
						window.opener.location.reload();
					},
					error : function() {
						parent.mac
								.alert("系统响应失败");
					}
				});
	}
	</script>
	<form action="" method="post"  enctype="multipart/form-data" id="editForm" >
		
		<input type="hidden" id="pjauditlogid" name="pjauditlog.pjauditlogid" value="${pjauditlog!=null&&pjauditlog.pjauditlogid!=null?pjauditlog.pjauditlogid:null}"/>
		<input type="hidden" id="pjaudittype" name="pjauditlog.pjaudittype" value="${pjauditlog!=null&&pjauditlog.pjaudittype!=null?pjauditlog.pjaudittype:null}"/>
		<input type="hidden" id="projectid" name="obj.projectid" value="${obj!=null&&obj.projectid!=null?obj.projectid:null}"/>
		<input type="hidden" id="deleted" name="obj.deleted" value="${obj!=null&&obj.deleted!=null?obj.deleted:null}"/>
		<input type="hidden" name="subObj.pjinvestid" value="${subObj!=null&&subObj.pjinvestid!=null?subObj.pjinvestid:null}"/>
		<input type="hidden" name="obj.pjstatus" value="${obj!=null&&obj.pjstatus!=null?obj.pjstatus:null}"/>
		<input type="hidden" name="obj.recordername" value="${obj.recordername}"/>
		<input type="hidden" name="obj.auditdept" value="${obj!=null&&obj.auditdept!=null?obj.auditdept:null}"/>
		<input type="hidden" name="obj.auditdeptname" value="${obj.auditdeptname}"/>
		<input type="hidden" name="obj.nextauditdept" value="${obj!=null&&obj.nextauditdept!=null?obj.nextauditdept:null}"/>
		<input type="hidden" name="obj.nextauditdeptname" value="${obj.nextauditdeptname}"/>
		<input type="hidden" name="obj.nexttacheername" value="${obj.nexttacheername}"/>
		<input type="hidden" name="obj.xmcblb" value="${obj.xmcblb}"/>
		<input type="hidden" name="obj.declartime" value="${obj.declartime}"/>
		<div class="editDiv">
			<fieldset style="width:80%;">
				<legend>项目基本信息</legend>
			<table class="editTab" id="jbTab">
				<tr>
					<th>项目分类</th>
					<td>
					<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
						<select id="xmfl" name="obj.xmfl"  style="background:#EEE;width:203px">
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
				          value="<s:property value="obj.xmsbXmlx.xmlxmc"/>"  readonly="readonly" style="background:#EEE;width:200px"/>
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
						<input type="text" id="projectname" name="obj.projectname" value="${obj.projectname}" readonly="readonly" style="background:#EEE;width:400px" />
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
						<input type="text" id="projectprincipal" name="obj.projectprincipal" value="${obj.projectprincipal}"  readonly="readonly" style="background:#EEE;width:200px"/>
					</td>
				</tr>
				<tr>
					<th>申报日期</th>
					<td>
						<input type="text" id="declartime" name="obj.declartime" value="<fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/>"   readonly="readonly" style="background:#EEE;width:200px"/>
					</td>
				</tr>
				<tr>
					<th>项目联系人</th>
					<td>
						<input type="text" id="declarerid" name="obj.declarerid" value="${obj.declarerid}"  readonly="readonly" style="background:#EEE;width:200px"/>
					</td>
				</tr>
				<tr>
					<th>联系人手机号</th>
					<td>
						<input type="text" id="mobilePhone" name="obj.mobilePhone" value="${obj.mobilePhone}" readonly="readonly" style="background:#EEE;width:200px" />
					</td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td>
						<input type="text" id="contacttel" name="obj.contacttel" value="${obj.contacttel}" readonly="readonly" style="background:#EEE;width:200px" />
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
						<input type="text" id="yzbm" name="obj.yzbm" value="${obj.yzbm}" readonly="readonly" style="background:#EEE;width:200px" />
					</td>
				</tr>
				<tr>
					<th>项目储备类别</th>
					<td>
						<input type="text" id="xmcblb" name="obj.xmcblb" value="${obj.xmcblb}" readonly="readonly" style="background:#EEE;width:200px" />
					</td>
				</tr>
				<tr>
					<th>是否重大项目</th>	
			         <td>
			         	<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
				        	<select id="obj.sfzdxm" name="obj.sfzdxm" style="background:#EEE;width:203px">
					        	<option value="9">(无)</option>
					        	<option value="0" ${obj.sfzdxm==0?'selected':''}>否</option>
					        	<option value="1" ${obj.sfzdxm==1?'selected':''}>是</option>
							</select>
						</span>
			      	</td>
				</tr>
				<tr>
					<th>起草单位</th>
					<td>
						<input type="text" name="obj.sysOrganizationByRecordOrgan.organizationName" id="sysOrganizationByRecordOrganName"
				          value="<s:property value="obj.sysOrganizationByRecordOrgan.organizationName"/>"  readonly="readonly" style="background:#EEE;width:200px"/>
				        <input type="hidden" name="obj.sysOrganizationByRecordOrgan.organizationId" id="sysOrganizationByRecordOrganId"  />
					</td>
				</tr>
				<tr>
					<th>投资项目基本情况描述<br/>(对于更新改造类项目必填)</th>
					<td>
						<table class="editTab" id="jbqkTab">
							<tr>
								<td><textarea id="xmjbqkms" name="obj.xmjbqkms" readonly="readonly"  readonly="readonly" style="background:#EEE">${obj.xmjbqkms }</textarea></td>	
							</tr>
							<s:iterator value="#request.pjadjuncts" id="dto" status="st">
								<s:if test="#dto.pjadjuncttype==20">
									<tr id="czwt${st.index}">
										<td>
											<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
										</td>
									</tr>
								</s:if>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>
			</fieldset>
			
			<fieldset style="width:80%;height:100%;">
				<legend>项目投资信息</legend>
			<table class="editTab2" id="tzTab">
				<tr>
					<th></th>
					<th>中央财政资金</th>	
			        <th>省财政资金</th>
			        <th>市财政资金 </th>
			        <th>区(县)财政资金</th>
			        <th>自有资金</th>
			        <th>融资(含银行贷款)</th>
			        <th>其它资金</th>
			        <th>合计</th>
			        <th>市财政资金<br/>安排渠道建议<br/>(注明资金来源<br/>类型或名称)</th>
				</tr>
				<tr>
					<th>项目总投资(万元)</th>
					<td>
						<input type="text" id="pjinvestcenter" name="subObj.pjinvestcenter" value="${subObj!=null?subObj.pjinvestcenter:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestprovince" name="subObj.pjinvestprovince" value="${subObj!=null?subObj.pjinvestprovince:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestcity" name="subObj.pjinvestcity" value="${subObj!=null?subObj.pjinvestcity:0}" style="width:50px" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvesttown" name="subObj.pjinvesttown" value="${subObj!=null?subObj.pjinvesttown:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestcompany" name="subObj.pjinvestcompany" value="${subObj!=null?subObj.pjinvestcompany:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestbank" name="subObj.pjinvestbank" value="${subObj!=null?subObj.pjinvestbank:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestother" name="subObj.pjinvestother" value="${subObj!=null?subObj.pjinvestother:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="pjinvestsum" name="subObj.pjinvestsum" value="${subObj!=null?subObj.pjinvestsum:0}" readonly="true" style="background:#EEE;width:48px"/>
					</td>
					<td>
						<input type="text" id="pjinvestadvice" name="subObj.pjinvestadvice" value="${subObj.pjinvestadvice}" style="width:70px" />
					</td>
				</tr>
				<tr>
					<th>
			        <select id="subObj.planinvestyear" name="subObj.planinvestyear" style="width:60px">
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
						<input type="text" id="planinvestcenter" name="subObj.planinvestcenter" value="${subObj!=null?subObj.planinvestcenter:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestprovince" name="subObj.planinvestprovince" value="${subObj!=null?subObj.planinvestprovince:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestcity" name="subObj.planinvestcity" value="${subObj!=null?subObj.planinvestcity:0}" style="width:50px" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvesttown" name="subObj.planinvesttown" value="${subObj!=null?subObj.planinvesttown:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestcompany" name="subObj.planinvestcompany" value="${subObj!=null?subObj.planinvestcompany:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestbank" name="subObj.planinvestbank" value="${subObj!=null?subObj.planinvestbank:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestother" name="subObj.planinvestother" value="${subObj!=null?subObj.planinvestother:0}" /><font class="msg">*</font>
					</td>
					<td>
						<input type="text" id="planinvestsum" name="subObj.planinvestsum" value="${subObj!=null?subObj.planinvestsum:0}" readonly="true" style="background:#EEE;width:48px;" />
					</td>
					<td>
						<input type="text" id="planinvestadvice" name="subObj.planinvestadvice" value="${subObj.planinvestadvice}" style="width:70px" />
					</td>
				</tr>
			</table>
			</fieldset>
			
			<fieldset style="width:80%;">
				<legend>项目审批信息</legend>
			<table class="editTab" id="qqjzTab">
				<tr>
					<td>审批完成情况（注明文号）</td>	
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype<10||#dto.pjadjuncttype==17||#dto.pjadjuncttype==18">
						<tr id="qqjz${st.index}">
							<td colspan="3"">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;文号：【<s:property value="#dto.wh"/>】&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
				<tr>
					<td>申报依据</td>	
				</tr>
				<tr>
					<td>
						<table class="editTab" id="xmsbyjTab">
							<tr>
								<td>提示：请附含市领导批示、会议纪要等！ </td>	
							</tr>
							<tr>
								<td><textarea  id="declaregist" name="obj.declaregist" value="${obj.declaregist}" readonly="readonly"  readonly="readonly" style="background:#EEE"></textarea></td>	
							</tr>
							<s:iterator value="#request.pjadjuncts" id="dto" status="st">
								<s:if test="#dto.pjadjuncttype==10">
									<tr id="sbyj${st.index}">
										<td colspan="3">
											<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
										</td>
									</tr>
								</s:if>
							</s:iterator>
						</table>
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
					年度建设内容
					</th>
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
			        	<select id="obj.xmjd" name="obj.xmjd" style="width:212px;background:#EEE">
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
				<tr>
					<th>工程形象进度<br/>提示：请附有关项目形象进度的图片！</th>	
			         <td>
			         <textarea  id="declareplan" name="obj.declareplan" readonly="readonly" style="background:#EEE">${obj.declareplan }</textarea>
					</span>
			      	</td>
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==11">
						<tr id="xxjd${st.index}">
							<td>&nbsp;</td>
							<td>
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
				<tr>
					<th>需要补充的其它事项</th>	
			         <td>
			         <textarea  id="qtsx" name="obj.qtsx" readonly="readonly" style="background:#EEE" >${obj.qtsx }</textarea>
					</span>
			      	</td>
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==19">
						<tr id="xxjd${st.index}">
							<td>&nbsp;</td>
							<td>
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
				<tr>
					<th>存在问题</th>	
			         <td>
			         <textarea  id="declareproblem" name="obj.declareproblem"  readonly="readonly" style="background:#EEE">${obj.declareproblem }</textarea>
			      	</td>
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==12">
						<tr id="czwt${st.index}">
							<td>&nbsp;</td>
							<td>
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:download('<s:property value="#dto.pjadjunctid"/>')">下载</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</fieldset>
			
		</div>
		<br/>
		<!--table  id="submitTab" style="width: 80%;">
			<tr>
				<td style="width: 100%;">
					<a href="javascript:view(${pjauditlog.pjbaseinfo.projectid})"><font color="blue">查看项目详细信息</font></a>
				</td>
			</tr>
		</table-->
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
							<c:when test="${obj.pjauditresult==3}">
								转交其他处室
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
				<legend>项目审核信息</legend>
		<table class="editTab2" id="gcxxjdTab">
				<tr>
					<th width="200" align="right">
						处理结果：
					</th>
					<td width="200">
						<select id="pjauditresult" name="pjauditlog.pjauditresult" id="pjauditresult" style="width:200px"/>
							<option value="">==请选择==</option>
							<%
									if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)&&deptCode!=null&&fgwtzc!=null&&!deptCode.equalsIgnoreCase(fgwtzc)){//各处室处长审核
							%>
									<option value="1" ${pjauditlog.pjauditresult=='1'?'selected':''}>呈投资处汇总</option>
									<option value="3" ${pjauditlog.pjauditresult=='3'?'selected':''}>转交其他处室</option>
							<%
								}
								else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)&&deptCode!=null&&fgwtzc!=null&&deptCode.equalsIgnoreCase(fgwtzc)){//投资处长审核
							%>
									<option value="1" ${pjauditlog.pjauditresult=='1'?'selected':''}>审核通过</option>
									<option value="3" ${pjauditlog.pjauditresult=='3'?'selected':''}>转交其他处室</option>
							<%
								}
								else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){//主管单位审核
							%>
									<option value="1" ${pjauditlog.pjauditresult=='1'?'selected':''}>审核通过</option>
							<%
								}
								else{
							%>
									<option value="1" ${pjauditlog.pjauditresult=='1'?'selected':''}>审核通过</option>
							<%
								}
							%>
							<option value="2" ${pjauditlog.pjauditresult=='2'?'selected':''}>审核不通过(退回)</option>
						</select>
						<span id="dx">
							<input type="checkbox" name="send" id="send" value="是否发送短信" />是否发送短信
						</span>
					 </td>	
				</tr>
				<tr>
					<th width="200" align="right">
						处理意见(100字以内)：
					</th>
					<td>
				        <textarea  id="pjauditmind" name="pjauditlog.pjauditmind" id="pjauditmind"></textarea>
					 </td>	
				</tr>
				<tr>
					<%
						if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
					%>
							<span id="fwxgcs">
							<th width="200" align="right">
								提交市发改委相关处室：
							</th>
							<td>
								<input type="text" name="pjauditlog.sysDeptBySenddeptid.deptname" id="auditdeptName" style="width:120px"
						         value=""
								readonly="readonly"/>
						        <input type="hidden" name="pjauditlog.sysDeptBySenddeptid.deptId" id="auditdeptId"
						         value="" />
							 </td>	
							 </span>
					<%
						}
						else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&(user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_3))){//处室经办(包括投资处经办)
					%>
							<td>
						        <input type="hidden" name="pjauditlog.sysDeptBySenddeptid.deptId" id="auditdeptId" 
						         value="<%=user.getSysDept().getDeptId()%>" />
							 </td>	
					<%
						}
						else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_3)&&user.getRoleType()!=null&&user.getRoleType().equals(IConstants.ROLE_TYPE_FGW_CODE_1)){//处长(包括投资处长)
					%>
							<span id="fwxgcs">
							<th width="200" align="right">
								发往相关处室：
							</th>
							<td>
								<input type="text" name="pjauditlog.sysDeptBySenddeptid.deptname" id="auditdeptName"  style="width:120px"
						         value=""
								readonly="readonly"/>
						        <input type="hidden" name="pjauditlog.sysDeptBySenddeptid.deptId" id="auditdeptId" 
						         value="" />
							 </td>	
							 </span>
					<%
						}
					%>
				</tr>
		</table>
		<table  id="submitTab" style="width: 80%;">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" id="saveBtn2" value="提交" onclick="saveProject()"> &nbsp;&nbsp;
						 <input type="reset" class="btn" id="resetBtn" value="重置"> &nbsp;&nbsp;
						 <input type="button" class="btn" id="closeBtn" value="关闭" onclick="window.close()">
						</span>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>