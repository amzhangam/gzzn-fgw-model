<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>项目月报填报</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="validator-0.6.8/jquery.validator.css" rel="stylesheet" type="text/css" />
<script src="validator-0.6.8/jquery.validator.js" type="text/javascript"></script>
<script src="validator-0.6.8/local/zh_CN.js" type="text/javascript"></script>
<script type="text/javascript">
var validator1;
var validator2;
//<!--
$(document).ready(function() {
	showMenu(2,17);
		//初始化自动搜索框
		var projectInfoJson = getDatas("${ctx}/com/getProjectInfoJson.json");
		inputAutoComplete(projectInfoJson, "projectname", "projectid", true);
		
		//返回
		$("#backBtn").click(function() {
			window.location = "${ctx}/project/xmyb/list.ac";
		});
		
		//添加附件
		var fileCount = 1;
		$("#fileBtn").click(function(){
			fileCount++;
			$("#fileInfo").append('<p class="fileP" id="' + fileCount +'"><input type="file" name="uploadXmyb" style="width:85%;"> <input type="button" class="fileBtn" value="删&nbsp;&nbsp;除" onClick="deleteFile(' + fileCount + ');"/></p>');
		});
		tempvalidate();
});

	function deleteFile(id){
		$("#"+id).remove();
	}

	function add(){
		//var length = $("#jbTab tr").length;
		//var trHTML = "<tr id='fj'"+length+1+">";
		var trHTML = "<tr>"
			+"<th>月报附件</th>"
			+"<td><input style='width:210px;' type='file' name='uploadXmyb'   />"
			+ "&nbsp;&nbsp;<input type='button' value='添加' onclick='add()'/></td>"
			+"</tr>";
		$("#jbTab").append(trHTML);//在table最后面添加一行
	}
	
	function deleteFj(fjId,trId){
		$.post(
		    	"${ctx}/project/xmyb/deleteFj.json",
		    	{"fjId":fjId},
		    	function(data){
		    		var json = $.parseJSON(data);
					if(json.flag){
						//$("#"+trId).hide();
						var tr = document.getElementById(trId);
						var table = tr.parentNode;
						table.removeChild(tr);
					}
					else{
						mac.alert(json.msg);
					}
		    	}
	    	);
	}
	//文件的下载	
	function download(id){
		options={id:id};
	    $.ajax({
	    	type:"POST",
	    	url:'checkFile.ac',
	    	data:options,
	    	success:function(result){
	    		if(result.info=="true"){
	    		  document.location.href="download.ac?id="+id;
				}else{
					mac.alert("该文件不存在!");
				}
	    	},
	    	error:function() { 
	    		mac.alert("出错了!");
			}, 
	    	dataType:'json'
		});
	}
	
	function tempSaveProject(){
		$("#editForm").validate('setField','obj.pjbaseinfo.projectname','');
		$("#editForm").validate('hideMsg','obj.pjbaseinfo.projectname');
		$("#editForm").validate('setField','obj.nr','');
		$("#editForm").validate('hideMsg','obj.nr');
		$("#editForm").validate('setField','obj.nf','');
		$("#editForm").validate('hideMsg','obj.nf');
		$("#editForm").validate('setField','obj.yf','');
		$("#editForm").validate('hideMsg','obj.yf');
		tempvalidate();
		//alert($("#projectid").val());
		if($("#projectid").val()=="0" || $("#projectname").val()==""){
			mac.alert("项目不能为空！");
			return;
		}
		$.post(
		    	"${ctx}/project/xmyb/checkRepeat.json",
		    	{"projectid":$("#projectid").val(),"id":$("#xmybId").val(),"nf":$("#nf").val(),"yf":$("#yf").val()},
		    	function(data){
		    		var json = $.parseJSON(data);
					if(json.flag){
						//var fileuploadarray = document.getElementsByName("uploadXmyb");
						// for(var i=0;i<fileuploadarray.length;i++){
						//	 var fileupload = fileuploadarray[i];
						//	 if(!checkFileSize(fileupload)){
					   	//			return;
						//	 }
						//}
						$("#editForm").attr("action","${ctx}/project/xmyb/tempSave.ac");
						$("#editForm").submit();
					}
					else{
						mac.alert(json.msg);
					}
		    	}
	    	);
	}
	
	function saveProject(){
		var validator2 = $("#editForm").validate({
			 event:"submit",
			 rules: {
				 "obj.pjbaseinfo.projectname":{
				 	 required: true,
				 },
	             "obj.nr":{   
	            	 required: true,
	                 byteMaxLength:4000
	             },
	             "obj.nf":{   
	            	 required: true
	             },
	             "obj.yf":{   
	            	 required: true
	             }
		      },
		      messages:{
		    	  "obj.nr":{
		    		  required:"不能为空",
		    		  byteMaxLength:"不能全为空格且字数最多是2000"
		    	  }
		      },
		      submitHandler: function(form){
			    	form.submit();
			    }	
		});
		if($("#projectid").val()=="0" || $("#projectname").val()==""){
			mac.alert("项目不能为空！");
			return;
		}
		$.post(
		    	"${ctx}/project/xmyb/checkRepeat.json",
		    	{"projectid":$("#projectid").val(),"id":$("#xmybId").val(),"nf":$("#nf").val(),"yf":$("#yf").val()},
		    	function(data){
		    		var json = $.parseJSON(data);
					if(json.flag){
						 //var fileuploadarray = document.getElementsByName("uploadXmyb");
						 //for(var i=0;i<fileuploadarray.length;i++){
						//	 var fileupload = fileuploadarray[i];
						//	 if(!checkFileSize(fileupload)){
						//		return;
						//	 }
						// }
						$("#editForm").attr("action","${ctx}/project/xmyb/save.ac");
						$("#editForm").submit();
					}
					else{
						mac.alert(json.msg);
					}
		    	}
	    	);
	}
//-->

	function tempvalidate(){
		var validator1 = $("#editForm").validate({
			 event:"submit",
			 rules: {
			 	"obj.pjbaseinfo.projectname":{
				 	 required: true,
				 },
				 "obj.nr":{   
	                 byteMaxLength:4000
	             }
		      },
		      messages:{
		    	  "obj.nr":{
		    		  byteMaxLength:"不能全为空格且字数最多是2000"
		    	  }
		      },
		      submitHandler: function(form){
			    	form.submit();
			  }	
		});
	}
	
	function checkFileSize(fileupload){
	       var agent      = window.navigator.userAgent;
	       var isIE7 = agent.indexOf('MSIE 7.0') != -1;
	       var isIE8 = agent.indexOf('MSIE 8.0') != -1;
	       var maxSize = 20*1024*1024;
	       try{
	       //火狐获取路径 
	           if (agent.indexOf("Firefox") >= 1) {
	               var fileSize = fileupload.files[0].fileSize;   
	           } 
	           else if (isIE7 || isIE8) {  //IE7和IE8获得文件路径
	               fileupload.select();
	               filepath = document.selection.createRange().text;
	               if(filepath==null||filepath==''){
	           			return true;
	           		}
	               var aa = new ActiveXObject("Scripting.FileSystemObject"); 
	               var fileSize = aa.GetFile(filepath).size;              
	           } 
	           else { //IE6获得文件路径
	               filepath = fileupload.value; 
	           		if(filepath==null||filepath==''){
	           			return true;
	           		}
	               var aa = new ActiveXObject("Scripting.FileSystemObject"); 
	               var fileSize = aa.GetFile(filepath).size;  
	           } 
	           if(fileSize > maxSize) {
	              alert("上传文件不可超过20MB!");
	              return false;
	           }
	       }
	       catch(e){
	           alert("请修改IE浏览器ActiveX安全设置为启用~！");
	           return false;
	       }    
	       return true;   
	}
</script>
</head>
<body>
	<form id="editForm" action="${ctx}/project/xmyb/save.ac" method="post"  enctype="multipart/form-data" >
		<input type="hidden" id="xmybId" name="obj.xmybId" value="${obj!=null&&obj.xmybId!=null?obj.xmybId:null}"/>
		<input type="hidden" name="obj.xmybzt" value="${obj!=null&&obj.xmybzt!=null?obj.xmybzt:null}"/>
		<div class="editDiv">
			<fieldset style="width:80%;height:100%">
				<legend>项目月报填报信息</legend>
			<table class="editTab" id="jbTab">
				<tr>
					<th>项目<font class="msg">*</font></th>	
			         <td >
			         	<input type="hidden" name="obj.pjbaseinfo.projectid" id="projectid" value="${obj.pjbaseinfo.projectid }" />
				       	<input type="text" style="width: 400px;" name="obj.pjbaseinfo.projectname" id="projectname" value="${obj.pjbaseinfo.projectname}" />
			      	</td>
				</tr>
				<tr>
					<th>月报年份<font class="msg">*</font></th>
					<td>
						<input type="text" id="nf" name="obj.nf" value="${obj.nf}" size="30"  onfocus="WdatePicker({dateFmt:'yyyy'})"/>
					</td>
				</tr>
				<tr>
					<th>月报月份<font class="msg">*</font></th>
					<td>
						<select id="yf" name="obj.yf">
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
					</td>
				</tr>
				<tr>
					<th>月报内容(2000字以内)<font class="msg">*</font></th>
					<td>
						<textarea  id="nr" name="obj.nr">${obj.nr}</textarea>
					</td>
				</tr>
				<tr>
					<th>月报附件</th>
					<td style="padding-top: 5px;padding-bottom: 5px;">
						<s:iterator value="obj.xmsbXmybfjs" id="dto" status="st">
							<p class="fileP" id="xmsb${st.index}" >
								${st.index+1}、<s:property value="#dto.fjmc"/>&nbsp;&nbsp;
								<a href="javascript:download('<s:property value="#dto.xmybfjId"/>')">下载</a> &nbsp;&nbsp;
								<a href="javascript:deleteFj('<s:property value="#dto.xmybfjId"/>','xmsb${st.index}')">删除</a>
							</p>
						</s:iterator>
						
						<div id="fileInfo">
							<p class="fileP" id="1">
								<input type="file" name="uploadXmyb" style="width:85%;">
								<input type="button" class="fileBtn" value="删&nbsp;&nbsp;除" onClick="deleteFile(1);"/>
							</p>
						</div>
						<input type="button" class="fileBtn" id="fileBtn" value="添加附件"/>
					</td>
				</tr>
			</table>
			</fieldset>
			<table  id="submitTab" style="width: 80%;">
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td style="width: 100%;">
						<div>
							<span style="float: right;">
							 <input type="button" class="btn" id="saveBtn1" value="暂存" onclick="tempSaveProject()"> &nbsp;&nbsp;
							 <input type="button" class="btn" id="saveBtn2" value="提交" onclick="saveProject()"> &nbsp;&nbsp;
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