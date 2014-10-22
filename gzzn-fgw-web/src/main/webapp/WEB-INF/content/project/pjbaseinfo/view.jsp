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
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/1_event.css">
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
	var windowsh,windowlog;
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
	  /* ajax下载文件 
		@url: 文件url路径
		*/
		function download_file(url) {
			if (typeof (download_file.iframe) == "undefined") {
				var iframe = document.createElement("iframe");
				download_file.iframe = iframe;
				
				document.body.appendChild(download_file.iframe);
			}
			download_file.iframe.src = url;
			download_file.iframe.style.display = "none";
		}

		function downloadModel(){
			//var fileName = "2013年以来立项的项目建设情况明细表模板.xls";
			var fileName = "updateProjectTemp.xls";
			var fileUrl = "/upload/pjbaseinfo/updateProjectTemp.xls";
			download_file("${ctx}/project/pjplanyear/downloadTempFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
		}
		
		function viewSh(projectId){
			var url = "${ctx}/project/pjbaseinfo/viewSh.json?id="+projectId+"&type=0";
			var iWidth = 1024;                          //弹出窗口的宽度;
		    var iHeight = 768;                            //弹出窗口的高度;
		    //获得窗口的垂直位置
		    var iTop = (window.screen.availHeight-30-iHeight)/2;        
		    //获得窗口的水平位置
		    var iLeft = (window.screen.availWidth-10-iWidth)/2;           
		    windowsh = window.open (url, 'newwindow3', 'left='+iLeft+',top='+iTop+',width='+ iWidth +',height='+ iHeight +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
		    windowsh.focus();
		}
		
		function viewProjectlog(projectId){
			var url = "${ctx}/project/pjbaseinfo/viewProjectlog.json?id="+projectId+"&type=0";
			var iWidth = 1024;                          //弹出窗口的宽度;
		    var iHeight = 768;                         //弹出窗口的高度;
		    //获得窗口的垂直位置
		    var iTop = (window.screen.availHeight-30-iHeight)/2;        
		    //获得窗口的水平位置
		    var iLeft = (window.screen.availWidth-10-iWidth)/2;           
			windowlog = window.open (url, 'newwindow4', 'left='+iLeft+',top='+iTop+',width='+ iWidth +',height='+ iHeight +',toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no');
			windowlog.focus();
			
		}
	
	</script>
	<form action="" method="post"  enctype="multipart/form-data" id="editForm" >
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
						<input type="text" id="projectcode" name="obj.projectcode" value="${obj.projectcode}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>项目名称</th>
					<td>
						<input type="text" id="projectname" name="obj.projectname" value="${obj.projectname}" readonly="readonly" style="background:#EEE;width:424px" />
					</td>
				</tr>
				<tr>
					<th>项目业主</th>
					<td>
						<input type="text" name="obj.sysOrganizationByDeclareunitsid.organizationName" id="sysOrganizationByDeclareunitsidName"
				          value="<s:property value="obj.sysOrganizationByDeclareunitsid.organizationName"/>"  readonly="readonly" style="background:#EEE;width:212px"/>
				        <input type="hidden" name="obj.sysOrganizationByDeclareunitsid.organizationId" id="sysOrganizationByDeclareunitsidId"  
				        value="<s:property value="obj.sysOrganizationByDeclareunitsid.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目负责人</th>
					<td>
						<input type="text" id="projectprincipal" name="obj.projectprincipal" value="${obj.projectprincipal}"  readonly="readonly" style="background:#EEE;width:212px"/>
					</td>
				</tr>
				<tr>
					<th>申报日期</th>
					<td>
						<input type="text" id="declartime" name="obj.declartime" value="<fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/>"   readonly="readonly" style="background:#EEE;width:212px"/>
					</td>
				</tr>
				<tr>
					<th>项目联系人</th>
					<td>
						<input type="text" id="declarerid" name="obj.declarerid" value="${obj.declarerid}"  readonly="readonly" style="background:#EEE;width:212px"/>
					</td>
				</tr>
				<tr>
					<th>联系人手机号</th>
					<td>
						<input type="text" id="mobilePhone" name="obj.mobilePhone" value="${obj.mobilePhone}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td>
						<input type="text" id="contacttel" name="obj.contacttel" value="${obj.contacttel}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>联系地址</th>
					<td>
						<input type="text" id="contactaddress" name="obj.contactaddress" value="${obj.contactaddress}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>行业类别</th>
					<td>
						<input type="text" name="obj.xmsbHylb.hylbmc" id="hylbName"
				          value="<s:property value="obj.xmsbHylb.hylbmc"/>"  readonly="readonly" style="background:#EEE;width:212px"/>
				        <input type="hidden" name="obj.xmsbHylb.hylbId" id="hylbId"  
				        value="<s:property value="obj.xmsbHylb.hylbId"/>"/>
					</td>
				</tr>
				<tr>
					<th>邮政编码</th>
					<td>
						<input type="text" id="yzbm" name="obj.yzbm" value="${obj.yzbm}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>项目储备类别</th>
					<td>
						<input type="text" id="xmcblb" name="obj.xmcblb" value="${obj.xmcblb}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>是否重大项目</th>	
			         <td>
			         	<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
				        	<select id="obj.sfzdxm" name="obj.sfzdxm" style="background:#EEE;width:212px">
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
				          value="<s:property value="obj.sysOrganizationByRecordOrgan.organizationName"/>"  readonly="readonly" style="background:#EEE;width:212px"/>
				        <input type="hidden" name="obj.sysOrganizationByRecordOrgan.organizationId" id="sysOrganizationByRecordOrganId"  
				        value="<s:property value="obj.sysOrganizationByRecordOrgan.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>投资项目基本情况描述<br/>(对于更新改造类项目必填)</th>
					<td>
						<table class="editTab" id="jbqkTab">
							<tr>
								<td><textarea  style="width:80%" id="xmjbqkms" name="obj.xmjbqkms" readonly="readonly" style="background:#EEE;width:212px">${obj.xmjbqkms }</textarea></td>	
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
			
			<fieldset style="width:80%;">
				<legend>项目投资信息</legend>
			<table class="editTab2" id="tzTab">
				<tr>
					<th></th>
					<th>中央财政资金</th>	
			        <th>省财政资金</th>
			        <th>市财政资金 </th>
			        <th>区(县)财政资金</th>
			        <th>企业自有资金</th>
			        <th>融资(含银行贷款)</th>
			        <th>其它资金</th>
			        <th>合计</th>
			        <th>市财政资金安排渠道建议<br/>(注明资金来源类型或名称)</th>
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
						<input type="text" id="pjinvestcity" name="subObj.pjinvestcity" value="${subObj!=null?subObj.pjinvestcity:0.00}" readonly="readonly"  style="background:#EEE" />
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
						<input type="text" id="pjinvestsum" name="subObj.pjinvestsum" value="${subObj!=null?subObj.pjinvestsum:0.00}" readonly="true"  style="background:#EEE"/>
					</td>
					<td>
						<input type="text" id="pjinvestadvice" name="subObj.pjinvestadvice" value="${subObj.pjinvestadvice}" readonly="readonly"  style="background:#EEE" />
					</td>
				</tr>
				<tr>
					<th>
			        <select id="subObj.planinvestyear" name="subObj.planinvestyear" readonly="readonly" style="width:60px;background:#EEE">
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
						<input type="text" id="planinvestcenter" name="subObj.planinvestcenter" value="${subObj!=null?subObj.planinvestcenter:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestprovince" name="subObj.planinvestprovince" value="${subObj!=null?subObj.planinvestprovince:0.00}" readonly="readonly" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestcity" name="subObj.planinvestcity" value="${subObj!=null?subObj.planinvestcity:0.00}"  readonly="readonly"  style="background:#EEE"/>
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
						<input type="text" id="planinvestsum" name="subObj.planinvestsum" value="${subObj!=null?subObj.planinvestsum:0.00}" readonly="true"  style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestadvice" name="subObj.planinvestadvice" value="${subObj.planinvestadvice}" readonly="readonly"  style="background:#EEE" />
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
								<td><textarea  style="width:80%" id="declaregist" name="obj.declaregist" value="${obj.declaregist}" readonly="readonly" style="background:#EEE"></textarea></td>	
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
				          value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationName"/>"  readonly="readonly" style="background:#EEE;width:212px"/>
				        <input type="hidden" name="obj.sysOrganizationByDirectorunitsid.organizationId" id="sysOrganizationByDirectorunitsid"  
				        value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目建设地址</th>
					<td>
						<input type="text" id="projectaddress" name="obj.projectaddress" value="${obj.projectaddress}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>所属区域</th>
					<td>
						<input type="text" name="obj.sysXq.xqmc" id="xqName"
				          value="<s:property value="obj.sysXq.xqmc"/>"  readonly="readonly" style="background:#EEE;width:212px"/>
				        <input type="hidden" name="obj.sysXq.xqId" id="xqId"  value="<s:property value="obj.sysXq.xqId"/>"/>
				        <input type="hidden" name="obj.sysXq.xzqydm" id="xzqydm"  value="<s:property value="obj.sysXq.xzqydm"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目建设管理（代建）单位</th>
					<td>
						<input type="text" id="manageunitsname" name="obj.manageunitsname" value="${obj.manageunitsname}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>主要建设内容及规模</th>
					<td>
						<textarea  id="projectcontent" name="obj.projectcontent" readonly="readonly" style="background:#EEE;width:212px">${obj.projectcontent}</textarea>
					</td>
				</tr>
				<tr>
					<th>其中
					<select id="obj.finishcontentyear" name="obj.finishcontentyear" readonly="readonly" style="background:#EEE;width:212px">
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
					</th>
					<td>
						<textarea  id="finishcontent" name="obj.finishcontent" readonly="readonly" style="background:#EEE;width:212px">${obj.finishcontent}</textarea>
					</td>
				</tr>
				<tr>
					<th>预计至
					<select id="obj.expectfinishinvestyear" name="obj.expectfinishinvestyear" readonly="readonly" style="background:#EEE;width:212px">
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
						合计：<input type="text" id="expectfinishinvest" name="obj.expectfinishinvest" value="${obj!=null?obj.expectfinishinvest:0.00}" readonly="readonly" style="background:#EEE;width:212px" />
						万元，其中市本级财政资金：<input type="text" id="expectfinishotherinvest" name="obj.expectfinishotherinvest" value="${obj!=null?obj.expectfinishotherinvest:0.00}" readonly="readonly" style="background:#EEE;width:212px" />
					</td>
				</tr>
				<tr>
					<th>开工日期或计划开工日期</th>
					<td>
						<input type="text" id="workdate" name="obj.workdate" value="<fmt:formatDate value='${obj.workdate}' pattern='yyyy-MM-dd'/>" size="30"  readonly="readonly" style="background:#EEE;width:212px"/>
					</td>
				</tr>
				<tr>
					<th>计划竣工日期</th>
					<td>
						<input type="text" id="finishdate" name="obj.finishdate" value="<fmt:formatDate value='${obj.finishdate}' pattern='yyyy-MM-dd'/>" size="30"   readonly="readonly" style="background:#EEE;width:212px"/>
					</td>
				</tr>
				<tr>
					<th>建设起止年限</th>
					<td>
						<select id="obj.startyear" name="obj.startyear" readonly="readonly" style="background:#EEE;width:212px">
						<s:iterator value="#session.sessionDirectionaryitmesNf" id="dto" status="st">
							<s:if test="#dto.itemvalue==#session.obj.startyear">
								<option value="#dto.itemvalue" selected><s:property value="#dto.itemtext"/></option>
							</s:if>
							<s:else>
								<option value="#dto.itemvalue"><s:property value="#dto.itemtext"/></option>
							</s:else>
						</s:iterator>
					</select>至
				        <select id="obj.endyear" name="obj.endyear" readonly="readonly" style="background:#EEE;width:212px">
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
					<th>项目进度</th>	
			         <td>
			         <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
			        	<select id="obj.xmjd" name="obj.xmjd" style="width:100px;background:#EEE;width:212px">
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
			         <textarea  style="width:80%" id="declareplan" name="obj.declareplan" readonly="readonly" style="background:#EEE;width:212px">${obj.declareplan }</textarea>
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
			         <textarea  style="width:80%" id="qtsx" name="obj.qtsx" readonly="readonly" style="background:#EEE;width:212px" >${obj.qtsx }</textarea>
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
			         <textarea  style="width:80%" id="declareproblem" name="obj.declareproblem"  readonly="readonly" style="background:#EEE;width:212px">${obj.declareproblem }</textarea>
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
		<fieldset style="width:80%;">
				<legend></legend>
		<table class="editTab2" id="gcxxjdTab" width="80%">
				<tr>
					<td>
						当前处理单位或处室：<input type="text" name="obj.nextauditdeptname" id="auditdeptName"
				          value="<s:property value="obj.nextauditdeptname"/>"  readonly="readonly" style="background:#EEE;width:212px"/>
				        <input type="hidden" name="obj.nextauditdept" id="auditdeptId"  
				        value="${obj!=null&&obj.nextauditdept!=null?obj.nextauditdept:null}"/>
					 </td>	
				</tr>
		</table>
		</fieldset>
		<table  id="log" style="width: 80%;">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;">
					<div align="center">
						 <input type="button" class="btn" id="shrz" value="查看审核意见" onclick="viewSh(${obj.projectid})">
						 &nbsp;&nbsp;
						 <input type="button" class="btn" id="xmrz" value="查看项目日志" onclick="viewProjectlog(${obj.projectid})">
					</div>
				</td>
			</tr>
		</table>
		<br/>
		<table  id="submitTab" style="width: 80%;">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;">
					<div align="center">
						 <input type="button" class="btn" id="backBtn" value="关闭" onclick="window.close()">
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>