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
申报项目
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<script type="text/javascript" src="${ctx}/resources/js/tc.all.js"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/pjbaseinfo/addPjbaseinfo.js" type="text/javascript"></script>
<script type="text/javascript">
var validator1;
var validator2;
//<!--
$(document).ready(function() {
	showMenu(2,17);
		//返回
		$("#backBtn").click(function() {
			if('${from}'=='3'){
				window.location = "${ctx}/project/dclxm/list.ac";
			}
			else{
				window.location = "${ctx}/project/pjbaseinfo/list.ac";
			}
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
			if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_1)){
		%>
				$("#fgwcsTab").hide();
				if(${obj==null||obj.projectid==null}){
					$("#sysOrganizationByDeclareunitsidId").val(<%=user.getSysOrganization().getOrganizationId()%>);
					$("#sysOrganizationByDeclareunitsidName").val('<%=user.getSysOrganization().getOrganizationName()%>');
				}
		<%
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_2)){
		%>
				if(${obj==null||obj.projectid==null}){
					$("#sysOrganizationByDeclareunitsidId").val(<%=user.getSysOrganization().getOrganizationId()%>);
					$("#sysOrganizationByDeclareunitsidName").val('<%=user.getSysOrganization().getOrganizationName()%>');
					$("#sysOrganizationByDirectorunitsidId").val(<%=user.getSysOrganization().getOrganizationId()%>);
					$("#sysOrganizationByDirectorunitsidName").val('<%=user.getSysOrganization().getOrganizationName()%>');
				}
		<%
			}
			else if(user!=null&&user.getUserType()!=null&&user.getUserType().equals(IConstants.USER_TYPE_5)){
		%>
				$("#saveBtn2").attr("value","保存");
				$("#saveBtn1").hide();
				$("#fgwcsTab").hide();
		<%
			}
		%>
		//tempvalidate();
		if('${from}'=='2'||'${from}'=='3'){
			$("#saveBtn1").hide();
			$("#saveBtn2").attr('value','保存');
		}
		$("#auditdeptName").focus(function(){
			initZtree("auditdept",nextauditdeptDatas,150,200);
		});
});
//-->

function getnx(){
	var workdate = $("#workdate").val();
	var finishdate = $("#finishdate").val();
	if(workdate!=null&&workdate!=''){
		$("#startyear").val(workdate.substring(0,4));
	}
	if(finishdate!=null&&finishdate!=''){
		$("#endyear").val(finishdate.substring(0,4));
	}
}

function tempvalidate(){
	validator1 = $("#editForm").validate({
		 event:"submit",
		 rules: {
			 "obj.projectname":{   
                byteMaxLength:100
            },
            "obj.xmjbqkms":{   
                byteMaxLength:200
            },
            "obj.projectcontent":{   
                byteMaxLength:200
            },
            "obj.finishcontent":{   
                byteMaxLength:200
            },
            "obj.projectprincipal":{   
                byteMaxLength:400
            },
            "obj.declarerid":{   
                byteMaxLength:20
            },
            "obj.contacttel":{   
                byteMaxLength:20
            },
            "obj.projectaddress":{   
                byteMaxLength:400
            },
            "obj.contactaddress":{   
                byteMaxLength:400
            },
            "obj.manageunitsname":{   
                byteMaxLength:200
            },
            "subObj.pjinvestadvice":{   
                byteMaxLength:1000
            },
            "subObj.planinvestadvice":{   
                byteMaxLength:1000
            },
            "obj.declaregist":{   
                byteMaxLength:2000
            },
            "obj.declareplan":{   
                byteMaxLength:2000
            },
            "obj.declareproblem":{   
                byteMaxLength:2000
            },
            "subObj.pjinvestcenter":{   
           	 number: true,
           	byteMaxLength:10
            },
            "subObj.pjinvestprovince":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.pjinvestcity":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.pjinvesttown":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.pjinvestcompany":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.pjinvestbank":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.pjinvestbank":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.planinvestcenter":{   
           	 number: true
            },
            "subObj.planinvestprovince":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.planinvestcity":{   
           	 number: true
            },
            "subObj.planinvesttown":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.planinvestcompany":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.planinvestbank":{   
           	 number: true,
            	byteMaxLength:10
            },
            "subObj.planinvestother":{   
           	 number: true,
            	byteMaxLength:10
            },
            "obj.expectfinishinvest":{   
           	 number: true,
            	byteMaxLength:10
            },
            "obj.expectfinishotherinvest":{   
           	 number: true,
            	byteMaxLength:10
            },
            "obj.yzbm":{   
            	digits: true,
              	byteMaxLength:6
               },
            "lxpf":{   
                byteMaxLength:50
            }
	      },
	      messages:{
	    	  "obj.projectcontent":{
	    		  byteMaxLength:"不超过100个汉字"
	    	  },
			  "obj.finishcontent":{
    			byteMaxLength:"不超过100个汉字"
    		  },
			 "obj.xmjbqkms":{
    		  byteMaxLength:"不超过100个汉字"
    		 }
	      },
	      submitHandler: function(form){
	    	form.submit();
	      }	
	});
}

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

function checkTzgx(){
	if(Number($("#pjinvestsum").val())<Number($("#planinvestsum").val())){
		return false;
	}
	else if(Number($("#pjinvestcenter").val())<Number($("#planinvestcenter").val())){
		return false;
	}
	else if(Number($("#pjinvestprovince").val())<Number($("#planinvestprovince").val())){
		return false;
	}
	else if(Number($("#pjinvestcity").val())<Number($("#planinvestcity").val())){
		return false;
	}
	else if(Number($("#pjinvesttown").val())<Number($("#planinvesttown").val())){
		return false;
	}
	else if(Number($("#pjinvestcompany").val())<Number($("#planinvestcompany").val())){
		return false;
	}
	else if(Number($("#pjinvestbank").val())<Number($("#planinvestbank").val())){
		return false;
	}
	else if(Number($("#pjinvestother").val())<Number($("#planinvestother").val())){
		return false;
	}
	return true;
}
</script>
</head>
<body>

	<script type="text/javascript">
	
	function deleteFj(fjId,trId){
		$.post(
		    	"${ctx}/project/pjbaseinfo/deleteFj.json",
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
						alert(json.msg);
					}
		    	}
	    	);
	}
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
	
	function fjsc(){
		document.forms[0].action = "${ctx}/project/pjbaseinfo/fjsc.ac"
		document.forms[0].submit();
	}
	
	function checkFieldLength(){
		if(getLength($("#projectname").val())>100){
			$("#projectname").val($("#projectname").val().substring(0,50));
		}
		if(getLength($("#xmjbqkms").val())>200){
			$("#xmjbqkms").val($("#xmjbqkms").val().substring(0,100));
		}
		if(getLength($("#projectcontent").val())>200){
			$("#projectcontent").val($("#projectcontent").val().substring(0,100));
		}
		if(getLength($("#finishcontent").val())>200){
			$("#finishcontent").val($("#finishcontent").val().substring(0,100));
		}
		if(getLength($("#projectprincipal").val())>400){
			$("#projectprincipal").val($("#projectprincipal").val().substring(0,200));
		}
		if(getLength($("#declarerid").val())>20){
			$("#declarerid").val($("#declarerid").val().substring(0,10));
		}
		if(getLength($("#contacttel").val())>20){
			$("#contacttel").val($("#contacttel").val().substring(0,10));
		}
		if(getLength($("#mobilePhone").val())>11){
			$("#mobilePhone").val($("#mobilePhone").val().substring(0,11));
		}
		if(getLength($("#projectaddress").val())>400){
			$("#projectaddress").val($("#projectaddress").val().substring(0,200));
		}
		if(getLength($("#contactaddress").val())>400){
			$("#contactaddress").val($("#contactaddress").val().substring(0,200));
		}
		if(getLength($("#manageunitsname").val())>200){
			$("#manageunitsname").val($("#manageunitsname").val().substring(0,100));
		}
		if(getLength($("#pjinvestadvice").val())>1000){
			$("#pjinvestadvice").val($("#pjinvestadvice").val().substring(0,500));
		}
		if(getLength($("#planinvestadvice").val())>1000){
			$("#planinvestadvice").val($("#planinvestadvice").val().substring(0,500));
		}
		if(getLength($("#declaregist").val())>2000){
			$("#declaregist").val($("#declaregist").val().substring(0,1000));
		}
		if(getLength($("#declareplan").val())>2000){
			$("#declareplan").val($("#declareplan").val().substring(0,1000));
		}
		if(getLength($("#declareproblem").val())>2000){
			$("#declareproblem").val($("#declareproblem").val().substring(0,1000));
		}
		if(getLength($("#pjinvestcenter").val())>10){
			$("#pjinvestcenter").val($("#pjinvestcenter").val().substring(0,10));
		}
		if(getLength($("#pjinvestprovince").val())>10){
			$("#pjinvestprovince").val($("#pjinvestprovince").val().substring(0,10));
		}
		if(getLength($("#pjinvestcity").val())>10){
			$("#pjinvestcity").val($("#pjinvestcity").val().substring(0,10));
		}
		if(getLength($("#pjinvesttown").val())>10){
			$("#pjinvesttown").val($("#pjinvesttown").val().substring(0,10));
		}
		if(getLength($("#pjinvestcompany").val())>10){
			$("#pjinvestcompany").val($("#pjinvestcompany").val().substring(0,10));
		}
		if(getLength($("#pjinvestbank").val())>10){
			$("#pjinvestbank").val($("#pjinvestbank").val().substring(0,10));
		}
		if(getLength($("#pjinvestother").val())>10){
			$("#pjinvestother").val($("#pjinvestother").val().substring(0,10));
		}
		if(getLength($("#planinvestcenter").val())>10){
			$("#planinvestcenter").val($("#planinvestcenter").val().substring(0,10));
		}
		if(getLength($("#planinvestprovince").val())>10){
			$("#planinvestprovince").val($("#planinvestprovince").val().substring(0,10));
		}
		if(getLength($("#planinvestcity").val())>10){
			$("#planinvestcity").val($("#planinvestcity").val().substring(0,10));
		}
		if(getLength($("#planinvesttown").val())>10){
			$("#planinvesttown").val($("#planinvesttown").val().substring(0,10));
		}
		if(getLength($("#planinvestcompany").val())>10){
			$("#planinvestcompany").val($("#planinvestcompany").val().substring(0,10));
		}
		if(getLength($("#planinvestbank").val())>10){
			$("#planinvestbank").val($("#planinvestbank").val().substring(0,10));
		}
		if(getLength($("#planinvestother").val())>10){
			$("#planinvestother").val($("#planinvestother").val().substring(0,10));
		}
		if(getLength($("#yzbm").val())>6){
			$("#yzbm").val($("#yzbm").val().substring(0,6));
		}
		if(getLength($("#lxpf").val())>50){
			$("#lxpf").val($("#lxpf").val().substring(0,25));
		}
	}
	
	function getLength(str){
	　　var byteLen=0,len=str.length;
	　　if(str){
	　　　　for(var i=0; i<len; i++){
	　　　　　　if(str.charCodeAt(i)>255){
	　　　　　　　　byteLen += 2;
	　　　　　　}
	　　　　　　else{
	　　　　　　　　byteLen++;
	　　　　　　}
	　　　　}
	　　　　return byteLen;
	　　}
	　　else{
	　　　　return 0;
	　　}
	}
	
	function removeRules(){
		if(validator2!=null){
			$("#projectname").rules("remove");
			$("#projectcontent").rules("remove");
			$("#xmlxName").rules("remove");
			//$("#zjxzName").rules("remove");
			$("#xqName").rules("remove");
			$("#xmjbqkms").rules("remove");
			$("#workdate").rules("remove");
			$("#finishdate").rules("remove");
			$("#sysOrganizationByDeclareunitsidId").rules("remove");
			$("#sysOrganizationByDirectorunitsidId").rules("remove");
			$("#projectprincipal").rules("remove");
			//$("#declartime").rules("remove");
			$("#declarerid").rules("remove");
			$("#contacttel").rules("remove");
			$("#mobilePhone").rules("remove");
			$("#projectaddress").rules("remove");
			$("#contactaddress").rules("remove");
			$("#manageunitsname").rules("remove");
			$("#xmjd").rules("remove");
			$("#pjinvestadvice").rules("remove");
			$("#planinvestadvice").rules("remove");
			$("#declaregist").rules("remove");
			$("#declareplan").rules("remove");
			$("#declareproblem").rules("remove");
			$("#lxpf").rules("remove");
			$("#finishcontent").rules("remove");
			$("#pjinvestcenter").rules("remove");
			$("#pjinvestprovince").rules("remove");
			$("#pjinvestcity").rules("remove");
			$("#pjinvesttown").rules("remove");
			$("#pjinvestcompany").rules("remove");
			$("#pjinvestbank").rules("remove");
			$("#pjinvestother").rules("remove");
			$("#planinvestcenter").rules("remove");
			$("#planinvestprovince").rules("remove");
			$("#planinvestcity").rules("remove");
			$("#planinvesttown").rules("remove");
			$("#planinvestcompany").rules("remove");
			$("#planinvestbank").rules("remove");
			$("#planinvestother").rules("remove");	
			$("#xmfl").rules("remove");	
			$("#yzbm").rules("remove");	
			$("#startyear").rules("remove");	
			$("#endyear").rules("remove");	
			$("#hylbName").rules("remove");	

		}
	}
	
	function tempSaveProject(){
		tempvalidate();
		removeRules();
		checkFieldLength();
		
		if(Number($("#pjinvestsum").val())>10000000000){
			alert('合计总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestcenter").val())>10000000000){
			alert('中央财政资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestprovince").val())>10000000000){
			alert('省财政资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestcity").val())>10000000000){
			alert('市财政资金总投资 金额超过了100亿!');
		}
		else if(Number($("#pjinvesttown").val())>10000000000){
			alert('区(县)财政资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestcompany").val())>10000000000){
			alert('自有资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestbank").val())>10000000000){
			alert('融资(含银行贷款)总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestother").val())>10000000000){
			alert('其它资金总投资金额超过了100亿!');
		}
		
		if(Number($("#planinvestsum").val())>10000000000){
			alert('合计年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestcenter").val())>10000000000){
			alert('中央财政资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestprovince").val())>10000000000){
			alert('省财政资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestcity").val())>10000000000){
			alert('市财政资金年度投资 金额超过了100亿!');
		}
		else if(Number($("#planinvesttown").val())>10000000000){
			alert('区(县)财政资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestcompany").val())>10000000000){
			alert('自有资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestbank").val())>10000000000){
			alert('融资(含银行贷款)年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestother").val())>10000000000){
			alert('其它资金年度投资金额超过了100亿!');
		}
		<%
		if(user!=null&&user.getUserType()!=null&&user.getUserType()==IConstants.USER_TYPE_5){
		%>
				$("#editForm").attr("action","${ctx}/project/pjbaseinfo/adminTempSave.ac");
		<%
			}
			else{
		%>
				$("#editForm").attr("action","${ctx}/project/pjbaseinfo/tempSave.ac");
		<%
			}
		%>
		
		$("#editForm").submit();
		$("#saveBtn1").attr("disabled","disabled");
		$("#saveBtn2").attr("disabled","disabled");
		$("#saveBtn1").delay(10000).queue(function(){
			$(this).removeAttr("disabled").dequeue();
		}) ;
		$("#saveBtn2").delay(10000).queue(function(){
			$(this).removeAttr("disabled").dequeue();
		}) ;
	}
	
	function checkFileSize(id){
	       var agent      = window.navigator.userAgent;
	       var fileupload = document.getElementById(id);
	       var isIE6 = agent.indexOf('MSIE 6.0') != -1;
	       var isIE7 = agent.indexOf('MSIE 7.0') != -1;
	       var isIE8 = agent.indexOf('MSIE 8.0') != -1;
	       var isIE9 = agent.indexOf('MSIE 9.0') != -1;
	       var isIE10 = agent.indexOf('MSIE 10.0') != -1;
	       var isIE11 = agent.indexOf('MSIE 11.0') != -1;
	       var maxSize = 20*1024*1024;
	       var fileSize=0;
	       try{
	       //火狐获取路径 
	           if (agent.indexOf("Firefox") >= 1) {
	        	   if(fileupload.files.length>0){
		               fileSize = fileupload.files[0].size;
	        	   }
	           } 
	           else if (isIE7 || isIE8) {  //IE7和IE8获得文件路径
	               fileupload.select();
	               filepath = document.selection.createRange().text;
	               if(filepath==null||filepath==''){
	           			return true;
	           		}
	               var aa = new ActiveXObject("Scripting.FileSystemObject"); 
	               fileSize = aa.GetFile(filepath).size;              
	           } 
	           else if (isIE6) { //IE6获得文件路径
	               filepath = fileupload.value; 
	           		if(filepath==null||filepath==''){
	           			return true;
	           		}
	               var aa = new ActiveXObject("Scripting.FileSystemObject"); 
	               fileSize = aa.GetFile(filepath).size;  
	           } 
	           if(fileSize > maxSize&&!isIE9&&!isIE10&&!isIE11) {
	              alert("上传文件不可超过20MB!");
	              return false;
	           }
	       }
	       catch(e){
	    	   alert(e);
	           alert("请修改IE浏览器ActiveX安全设置为启用~！");
	           return false;
	       }    
	       return true;   
	}
	
	function saveProject(){
		validator2 = $("#editForm").validate({
			 event:"submit",
			 rules: {
				 "obj.xmsbXmlx.xmlxmc":{   
					 required: true
	             },
				 "obj.sysXq.xqmc":{   
					 required: true
	             },
				 "obj.projectname":{   
					 required: true,
	                 byteMaxLength:100
	             },
	             "obj.xmjbqkms":{   
	                 byteMaxLength:200
	             },
	             "obj.projectcontent":{   
	            	 required: true,
	                 byteMaxLength:200
	             },
	             "obj.finishcontent":{   
	            	 required: true,
	                 byteMaxLength:200
	             },
	             "obj.workdate":{   
	            	 required: true
	             },
	             "obj.finishdate":{   
	            	 required: true
	             },
	             "obj.startyear":{   
	            	 required: true
	             },
	             "obj.endyear":{   
	            	 required: true
	             },
	             "obj.sysOrganizationByDeclareunitsid.organizationId":{   
	            	 required: true
	             },
	             "obj.projectprincipal":{   
	            	 required: true,
	                 byteMaxLength:400
	             },
	             "obj.declarerid":{   
	            	 required: true,
	                 byteMaxLength:20
	             },
	             "obj.contacttel":{   
	                 byteMaxLength:20
	             },
	             "obj.mobilePhone":{   
	            	 required: true,
	                 byteMaxLength:11
	             },
	             "obj.projectaddress":{   
	                 byteMaxLength:400
	             },
	             "obj.contactaddress":{   
	                 byteMaxLength:400
	             },
	             "obj.manageunitsname":{   
	                 byteMaxLength:200
	             },
	             "obj.xmjd":{   
	            	 required: true
	             },
	             "obj.xmfl":{   
	            	 required: true
	             },
	             "obj.zjxz":{   
	            	 required: true
	             },
	             "obj.xmsbHylb.hylbmc":{   
	            	 required: true
	             },
	             "obj.sysXq.xqmc":{   
	            	 required: true
	             },
	             "subObj.pjinvestadvice":{   
	                 byteMaxLength:1000
	             },
	             "subObj.planinvestadvice":{   
	                 byteMaxLength:1000
	             },
	             "obj.declaregist":{   
	                 byteMaxLength:2000
	             },
	             "obj.declareplan":{   
	                 byteMaxLength:2000
	             },
	             "obj.declareproblem":{   
	                 byteMaxLength:2000
	             },
	             "subObj.pjinvestcenter":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.pjinvestprovince":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.pjinvestcity":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.pjinvesttown":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.pjinvestcompany":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.pjinvestbank":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.pjinvestbank":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.planinvestcenter":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.planinvestprovince":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.planinvestcity":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.planinvesttown":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.planinvestcompany":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.planinvestbank":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             "subObj.planinvestother":{   
	            	 number: true,
	                	byteMaxLength:10
	             },
	             //"obj.mobilePhone": {// 验证手机号码
	             //    validator: function (value) {
	             //        return /^(13|15|18)\d{9}$/i.test(value)||/^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
	            //     },
	             //    message: '格式不正确.'
	            // },
	            "obj.yzbm":{   
              	 digits: true,
              	byteMaxLength:6
               },
	             "lxpf":{   
	                 byteMaxLength:50
	             }
		      },
		      messages:{
		    	  "obj.projectcontent":{
		    		  byteMaxLength:"不超过100个汉字"
		    	  },
				  "obj.finishcontent":{
	    			byteMaxLength:"不超过100个汉字"
	    		  },
				 "obj.xmjbqkms":{
	    		  byteMaxLength:"不超过100个汉字"
	    		 }
		      },
		      submitHandler: function(form){
			    	form.submit();
			    }	
		});
		if($("#sysOrganizationByDeclareunitsidId").val()==null||$("#sysOrganizationByDeclareunitsidId").val()==""){
			
			alert("项目业主不能为空!");
			
			return;
		}
		if($("#sysOrganizationByDirectorunitsidId").val()==null||$("#sysOrganizationByDirectorunitsidId").val()==""){
			
			alert("主管单位不能为空!");
			
			return;
		}
		
		<%
		if(user!=null&&user.getUserType()!=null&&user.getUserType()==IConstants.USER_TYPE_2){
		%>
			if($("#sysOrganizationByDirectorunitsidId").val()==<%=user.getSysOrganization().getOrganizationId()%>){
				
				if($("#nextauditdeptName").val()==null||$("#nextauditdeptName").val()==""||$("#nextauditdeptId").val()==null||$("#nextauditdeptId").val()==""||$("#nextauditdeptId").val()=="0"){
					
					alert("提交发改委部门不能为空!");
					
					return;
				}
			}
		<%
		}
		%>

		if(Number($("#pjinvestsum").val())>10000000000){
			alert('合计总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestcenter").val())>10000000000){
			alert('中央财政资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestprovince").val())>10000000000){
			alert('省财政资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestcity").val())>10000000000){
			alert('市财政资金总投资 金额超过了100亿!');
		}
		else if(Number($("#pjinvesttown").val())>10000000000){
			alert('区(县)财政资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestcompany").val())>10000000000){
			alert('自有资金总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestbank").val())>10000000000){
			alert('融资(含银行贷款)总投资金额超过了100亿!');
		}
		else if(Number($("#pjinvestother").val())>10000000000){
			alert('其它资金总投资金额超过了100亿!');
		}
		
		if(Number($("#planinvestsum").val())>10000000000){
			alert('合计年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestcenter").val())>10000000000){
			alert('中央财政资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestprovince").val())>10000000000){
			alert('省财政资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestcity").val())>10000000000){
			alert('市财政资金年度投资 金额超过了100亿!');
		}
		else if(Number($("#planinvesttown").val())>10000000000){
			alert('区(县)财政资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestcompany").val())>10000000000){
			alert('自有资金年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestbank").val())>10000000000){
			alert('融资(含银行贷款)年度投资金额超过了100亿!');
		}
		else if(Number($("#planinvestother").val())>10000000000){
			alert('其它资金年度投资金额超过了100亿!');
		}
		//$.post(
		//    	"${ctx}/project/pjbaseinfo/checkRepeat.json",
		//    	{"xmmc":$("#projectname").val(),"id":$("#projectid").val()},
	//	    	function(data){
	//	    		var json = $.parseJSON(data);
	//	    		if(json.flag){
						if($("#finishdate").val()!=''&&$("#workdate").val()!=''&&$("#finishdate").val()<=$("#workdate").val()){
							alert('竣工日期应在开工日期之后!');
							return;
						}
						if($("#startyear").val()!=null&&$("#startyear").val()!=''&&
								$("#endyear").val()!=null&&$("#endyear").val()!=''&&$("#endyear").val()<$("#startyear").val()){
							alert('建设截止年限应不能大于开始年限!');
							return;
						}
						if(!checkTzgx()){
							alert('年度投资不应该大于项目总投资!');
							return;
						}
						<%
						if(user!=null&&user.getUserType()!=null&&user.getUserType()==IConstants.USER_TYPE_5){
						%>
								$("#editForm").attr("action","${ctx}/project/pjbaseinfo/adminSave.ac");
						<%
							}
							else{
						%>
								if('${from}'=='1'){
									$("#editForm").attr("action","${ctx}/project/pjbaseinfo/save.ac");
								}
								else{
									$("#editForm").attr("action","${ctx}/project/pjbaseinfo/dclSave.ac");
								}
						<%
							}
						%>
						
						$("#editForm").submit();
						$("#saveBtn1").attr("disabled","disabled");
						$("#saveBtn2").attr("disabled","disabled");
						$("#saveBtn2").delay(10000).queue(function(){
							$(this).removeAttr("disabled").dequeue();
							$("#saveBtn1").removeAttr("disabled").dequeue();
						}) ;
						$("#saveBtn1").delay(10000).queue(function(){
							$(this).removeAttr("disabled").dequeue();
						}) ;
						
		//			}
		//			else{
		//				alert(json.msg);
		//			}
		//    	}
	   // 	);
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

	function downloadModel(id){
		//var fileName = "2013年以来立项的项目建设情况明细表模板.xls";
		if(id==1){
			var fileName = "广州市政府投资项目基本情况（更新改造类）.doc";
			var fileUrl = "/upload/pjbaseinfo/fj9.doc";
			download_file("${ctx}/project/pjplanyear/downloadTempFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
		}
		else if(id==2){
			var fileName = "广州市政府投资项目基本情况（基本建设类）（范本）.doc";
			var fileUrl = "/upload/pjbaseinfo/fj10.doc";
			download_file("${ctx}/project/pjplanyear/downloadTempFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
		}
	}
	
	</script>
	<form action="${ctx}/project/pjbaseinfo/save.ac" method="post"  enctype="multipart/form-data" id="editForm" >
		<s:token/>
		<input type="hidden" id="projectid" name="obj.projectid" value="${obj!=null&&obj.projectid!=null?obj.projectid:null}"/>
		<input type="hidden" id="deleted" name="obj.deleted" value="${obj!=null&&obj.deleted!=null?obj.deleted:null}"/>
		<input type="hidden" name="subObj.pjinvestid" value="${subObj!=null&&subObj.pjinvestid!=null?subObj.pjinvestid:null}"/>
		<input type="hidden" name="obj.pjstatus" value="${obj!=null&&obj.pjstatus!=null?obj.pjstatus:null}"/>
		<input type="hidden" name="obj.recordername" value="${obj.recordername}"/>
		<input type="hidden" name="obj.auditdept" value="${obj!=null&&obj.auditdept!=null?obj.auditdept:null}"/>
		<input type="hidden" name="obj.auditdeptname" value="${obj.auditdeptname}"/>
		<input type="hidden" name="obj.nexttacheername" value="${obj.nexttacheername}"/>
		<input type="hidden" name="obj.xmcblb" value="${obj.xmcblb}"/>
		<input type="hidden" name="obj.sfzdxm" value="${obj.sfzdxm}"/>
		<input type="hidden" name="obj.declartime" value="${obj.declartime}"/>
		<input type="hidden" name="obj.xmztz" value="${obj.xmztz}"/>
		<input type="hidden" name="obj.xmsbZjxz.zjxzId" value="1"/>
		<input type="hidden" name="obj.sysUserByRecorderid.userId" value="${obj!=null&&obj.sysUserByRecorderid!=null&&obj.sysUserByRecorderid.userId!=null?obj.sysUserByRecorderid.userId:null}"/>
		<input type="hidden" name="obj.sysUserByNexttacheer.userId" value="${obj!=null&&obj.sysUserByNexttacheer!=null&&obj.sysUserByNexttacheer.userId!=null?obj.sysUserByNexttacheer.userId:null}"/>
		<input type="hidden" name="obj.sysOrganizationByRecordOrgan.organizationId" value="${obj!=null&&obj.sysOrganizationByRecordOrgan!=null&&obj.sysOrganizationByRecordOrgan.organizationId!=null?obj.sysOrganizationByRecordOrgan.organizationId:null}"/>
		<div class="editDiv">
			<fieldset style="width:80%;height:100%">
				<legend>项目基本信息</legend>
			<table class="editTab" id="jbTab">
				<tr>
					<th>项目分类<font class="msg">*</font></th>
					<td>
						<select id="xmfl" name="obj.xmfl" style="width:212px">
							<option value="">==请选择==</option>
							<option value="1" ${obj.xmfl==1?'selected':''}>基本建设投资类项目</option>
							<option value="2" ${obj.xmfl==2?'selected':''}>更新改造投资类项目</option>
							<option value="3" ${obj.xmfl==3?'selected':''}>其他固定资产投资类项目</option>
					</select>
					</td>
				</tr>
				<tr>
					<th>项目类型<font class="msg">*</font></th>	
			         <td >
			        	<input type="text" name="obj.xmsbXmlx.xmlxmc" id="xmlxName" style="width:212px"
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
					<th>项目业主<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.sysOrganizationByDeclareunitsid.organizationName" id="sysOrganizationByDeclareunitsidName"
				          value="<s:property value="obj.sysOrganizationByDeclareunitsid.organizationName"/>" /> <!-- readonly="readonly" -->
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
					<th>项目联系人<font class="msg">*</font></th>
					<td>
						<input type="text" id="declarerid" name="obj.declarerid" value="${obj.declarerid}" />
					</td>
				</tr>
				<tr>
					<th>联系人手机号<font class="msg">*</font></th>
					<td>
						<input type="text" id="mobilePhone" name="obj.mobilePhone" value="${obj.mobilePhone}" />
					</td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td>
						<input type="text" id="contacttel" name="obj.contacttel" value="${obj.contacttel}" />
					</td>
				</tr>
				<tr>
					<th>联系地址</th>
					<td>
						<input type="text" id="contactaddress" name="obj.contactaddress" value="${obj.contactaddress}" />
					</td>
				</tr>
				<tr>
					<th>行业类别<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.xmsbHylb.hylbmc" id="hylbName"
				          value="<s:property value="obj.xmsbHylb.hylbmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.xmsbHylb.hylbId" id="hylbId"  
				        value="<s:property value="obj.xmsbHylb.hylbId"/>"/>
					</td>
				</tr>
				<!--tr>
					<th>资金性质</th>	
			         <td>
			        	<input type="hidden" name="obj.xmsbZjxz.zjxzmc" id="zjxzName"
				          value="<s:property value="obj.xmsbZjxz.zjxzmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.xmsbZjxz.zjxzId" id="zjxzId"  value="<s:property value="obj.xmsbZjxz.zjxzId"/>"/>
				        <input type="hidden" name="obj.xmsbZjxz.zjxzdm" id="zjxzdm"  value="<s:property value="obj.xmsbZjxz.zjxzdm"/>"/>
			      	</td>
				</tr-->
				<tr>
					<th>邮政编码</th>
					<td>
						<input type="text" id="yzbm" name="obj.yzbm" value="${obj.yzbm}" />
					</td>
				</tr>
				<tr>
					<th>投资项目基本情况描述<br/>(对于更新改造类项目必填)</th>
					<td>
						<table class="editTab" id="jbqkTab">
							<tr>
								<td>
									<input type="button" class="btn" id="downloadBtn" value="更新改造类项目基本情况模板下载"  onclick="downloadModel(1)"/>
									<input type="button" class="btn" id="downloadBtn2" value="基本建设类项目基本情况模板下载"  onclick="downloadModel(2)"/> &nbsp;&nbsp;
									</td>	
							</tr>
							<tr>
								<td>
									<textarea  style="width:70%" id="xmjbqkms" name="obj.xmjbqkms">${obj.xmjbqkms }</textarea>
									<input title="文件应小于10M" style="width:20%;" type="file" name="uploadJbqk" id="uploadJbqk"   />
								</td>
							</tr>
							<s:iterator value="#request.pjadjuncts" id="dto" status="st">
								<s:if test="#dto.pjadjuncttype==20">
									<tr id="jbqk${st.index}">
										<td>
											<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:deleteFj('<s:property value="#dto.pjadjunctid"/>','jbqk${st.index}')">删除</a>
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
						<input type="text" id="pjinvestcity" name="subObj.pjinvestcity" value="${subObj!=null?subObj.pjinvestcity:0}" /><font class="msg">*</font>
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
						<input type="text" id="pjinvestsum" name="subObj.pjinvestsum" value="${subObj!=null?subObj.pjinvestsum:0}" readonly="true" style="background:#EEE"/>
					</td>
					<td>
						<input type="text" id="pjinvestadvice" name="subObj.pjinvestadvice" value="${subObj.pjinvestadvice}"  />
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
						<input type="text" id="planinvestcity" name="subObj.planinvestcity" value="${subObj!=null?subObj.planinvestcity:0}" /><font class="msg">*</font>
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
						<input type="text" id="planinvestsum" name="subObj.planinvestsum" value="${subObj!=null?subObj.planinvestsum:0}" readonly="true" style="background:#EEE" />
					</td>
					<td>
						<input type="text" id="planinvestadvice" name="subObj.planinvestadvice" value="${subObj.planinvestadvice}" />
					</td>
				</tr>
			</table>
			</fieldset>
			
			<fieldset style="width:80%;height:100%;">
				<legend>项目审批信息</legend>
			<table class="editTab" id="qqjzTab">
				<tr>
					<td></td>
					<td>审批完成情况（注明文号）</td>	
			        <td>附件</td>
				</tr>
				<tr>
					<th>项目申报依据<br/>(提示：请附含市领导批示、会议纪要等！)</th>
					<td colspan="1">
						<textarea  style="width:99%" id="declaregist" name="obj.declaregist">${obj.declaregist}</textarea>
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadSbyj" id="uploadSbyj"   />
					</td>	
				</tr>
				<tr>
					<th>立项批复</th>
					<td>
						<input type="text" id="lxpf" name="lxpf" value="" size="200"/>
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadLxpf" id="uploadLxpf"   />
					</td>
				</tr>
				<tr>
					<th>规划选址</th>
					<td>
						<input type="text" id="ghxz" name="ghxz" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadGhxz" id="uploadGhxz"   />
					</td>
				</tr>
				<tr>
					<th>用地预审</th>
					<td>
						<input type="text" id="ydys" name="ydys" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadYdys" id="uploadYdys"   />
					</td>
				</tr>
				<tr>
					<th>环境影响评价 </th>
					<td>
						<input type="text" id="hjyx" name="hjyx" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadHjyx" id="uploadHjyx"   />
					</td>
				</tr>
				<tr>
					<th>(重大项目)社会风险评估 </th>
					<td>
						<input type="text" id="fxpg" name="fxpg" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadFxpg" id="uploadFxpg"   />
					</td>
				</tr>
				<tr>
					<th>节能评估审查</th>
					<td>
						<input type="text" id="jnpg" name="jnpg" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadJnpg" id="uploadJnpg"   />
					</td>
				</tr>
				<tr>
					<th>可研批复 </th>
					<td>
						<input type="text" id="kypf" name="kypf" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadKypf" id="uploadKypf"   />
					</td>
				</tr>
				<tr>
					<th>初步设计及概算 </th>
					<td>
						<input type="text" id="cbsj" name="cbsj" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadCbsj" id="uploadCbsj"   />
					</td>
				</tr>
				<tr>
					<th>施工图设计与预算 </th>
					<td>
						<input type="text" id="sgys" name="sgys" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadSgys" id="uploadSgys"   />
					</td>
				</tr>
				<tr>
					<th>招标投标情况 </th>
					<td>
						<input type="text" id="zbtb" name="zbtb" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadZbtb" id="uploadZbtb" />
					</td>
				</tr>
				<tr>
					<th>征地拆迁 </th>
					<td>
						<input type="text" id="zdcq" name="zdcq" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadZdcq" id="uploadZdcq"/>
					</td>
				</tr>
				<tr>
					<th>其他前期工作</th>
					<td>
						<input type="text" id="qtqq" name="qtqq" value="" />
					</td>
					<td>
						<input title="文件应小于10M" style="width:210px;" type="file" name="uploadQtqq" id="uploadQtqq"   />
					</td>
				</tr>
				
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype<10||#dto.pjadjuncttype==17||#dto.pjadjuncttype==18">
						<tr id="qqjz${st.index}">
							<td colspan="3">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;文号：【<s:property value="#dto.wh"/>】&nbsp;&nbsp;<a href="javascript:deleteFj('<s:property value="#dto.pjadjunctid"/>','qqjz${st.index}')">删除</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==10">
						<tr id="sbyj${st.index}">
							<td colspan="3">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:deleteFj('<s:property value="#dto.pjadjunctid"/>','sbyj${st.index}')">删除</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</fieldset>
			<fieldset style="width:80%;height:100%">
				<legend>项目建设信息</legend>
			<table class="editTab" id="jsTab">
				<tr>
					<th>主管单位<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.sysOrganizationByDirectorunitsid.organizationName" id="sysOrganizationByDirectorunitsidName"
				          value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationName"/>" /> <!-- readonly="readonly" -->
				        <input type="hidden" name="obj.sysOrganizationByDirectorunitsid.organizationId" id="sysOrganizationByDirectorunitsidId"  
				        value="<s:property value="obj.sysOrganizationByDirectorunitsid.organizationId"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目建设地址</th>
					<td>
						<input type="text" id="projectaddress" name="obj.projectaddress" value="${obj.projectaddress}" />
					</td>
				</tr>
				<tr>
					<th>所属区域<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.sysXq.xqmc" id="xqName"
				          value="<s:property value="obj.sysXq.xqmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.sysXq.xqId" id="xqId"  value="<s:property value="obj.sysXq.xqId"/>"/>
				        <input type="hidden" name="obj.sysXq.xzqydm" id="xzqydm"  value="<s:property value="obj.sysXq.xzqydm"/>"/>
					</td>
				</tr>
				<tr>
					<th>项目建设管理（代建）单位</th>
					<td>
						<input type="text" id="manageunitsname" name="obj.manageunitsname" value="${obj.manageunitsname}" />
					</td>
				</tr>
				<tr>
					<th>主要建设内容及规模(字数限制在100字以内)<font class="msg">*</font></th>
					<td>
						<textarea  id="projectcontent" name="obj.projectcontent" style="width:70%">${obj.projectcontent}</textarea>
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
					<font class="msg">*</font></th>
					<td>
						<textarea  id="finishcontent" name="obj.finishcontent" style="width:70%">${obj.finishcontent}</textarea>
					</td>
				</tr>
				<tr>
					<th>预计至
					<select id="expectfinishinvestyear" name="obj.expectfinishinvestyear">
						<c:choose>
							<c:when  test="${obj.expectfinishinvestyear==null}">
		        				<c:forEach items="${session.sessionDirectionaryitmesNf}" var="dto">
									<option value="${dto.itemvalue}" ${dto.itemvalue==nextYear-1?'selected':''}>${dto.itemtext}</option>
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
						合计：<input type="text" id="expectfinishinvest" name="obj.expectfinishinvest" value="${obj!=null?obj.expectfinishinvest:0}" />
						万元，其中市本级财政资金：<input type="text" id="expectfinishotherinvest" name="obj.expectfinishotherinvest" value="${obj!=null?obj.expectfinishotherinvest:0}" />
					</td>
				</tr>
				<tr>
					<th>开工日期或计划开工日期<font class="msg">*</font></th>
					<td>
						<input type="text" id="workdate" name="obj.workdate" value="<fmt:formatDate value='${obj.workdate}' pattern='yyyy-MM-dd'/>" size="30" onChange="getnx()"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr>
					<th>竣工日期或计划竣工日期<font class="msg">*</font></th>
					<td>
						<input type="text" id="finishdate" name="obj.finishdate" value="<fmt:formatDate value='${obj.finishdate}' pattern='yyyy-MM-dd'/>" size="30" onChange="getnx()"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr>
					<th>建设起止年限<font class="msg">*</font></th>
					<td>
						<input type="text" id="startyear" name="obj.startyear" value="${obj.startyear}" readonly="readonly" style="background:#EEE"/>
					至
				        <input type="text" id="endyear" name="obj.endyear" value="${obj.endyear}" readonly="readonly" style="background:#EEE"/>
					</td>
				</tr>
				<!--tr>
					<th>申报日期<font class="msg">*</font></th>
					<td>
						<input type="text" id="declartime" name="obj.declartime" value="<fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/>" size="30"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr-->
				
				<tr>
					<th>项目进度<font class="msg">*</font></th>	
			         <td>
			        	<select id="xmjd" name="obj.xmjd" style="width:200px">
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
			      	</td>
				</tr>
				<tr>
					<th>工程形象进度<br/>(提示：请附有关项目形象进度的图片！)</th>	
			         <td>
			        	<textarea  style="width:70%" id="declareplan" name="obj.declareplan" >${obj.declareplan }</textarea>
			        	<input title="文件应小于10M" style="width:20%;" type="file" name="uploadXxjd" id="uploadXxjd"   />
			      	</td>
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==11">
						<tr id="xxjd${st.index}">
							<td colspan="2">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:deleteFj('<s:property value="#dto.pjadjunctid"/>','xxjd${st.index}')">删除</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
				<tr>
					<th>需要补充的其它事项</th>	
			         <td>
			        	<textarea  style="width:70%" id="qtsx" name="obj.qtsx" >${obj.qtsx }</textarea>
			        	<input title="文件应小于10M" style="width:20%;" type="file" name="uploadQtsx" id="uploadQtsx"   />
			      	</td>
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==19">
						<tr id="xxjd${st.index}">
							<td colspan="2">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:deleteFj('<s:property value="#dto.pjadjunctid"/>','qtsx${st.index}')">删除</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
				<tr>
					<th>存在问题</th>	
			         <td>
			        	<textarea  style="width:70%" id="declareproblem" name="obj.declareproblem">${obj.declareproblem }</textarea>
			        	<input title="文件应小于10M" style="width:20%;" type="file" name="uploadCzwt" id="uploadCzwt"   />
			      	</td>
				</tr>
				<s:iterator value="#request.pjadjuncts" id="dto" status="st">
					<s:if test="#dto.pjadjuncttype==12">
						<tr id="czwt${st.index}">
							<td colspan="2">
								<s:property value="#session.sessionFjlxMap[#dto.pjadjuncttype]"/>&nbsp;&nbsp;<s:property value="#dto.filename"/>&nbsp;&nbsp;<a href="javascript:deleteFj('<s:property value="#dto.pjadjunctid"/>','czwt${st.index}')">删除</a>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
		</div>
		<fieldset style="width:80%;height:100%">
				<legend></legend>
		<table class="editTab2" id="fgwcsTab" width="80%">
				<tr>
					<td>
						提交市发改委相关处室：<input type="text" name="obj.nextauditdeptname" id="nextauditdeptName" style="width:150px"
				          value="<s:property value="obj.nextauditdeptname"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.nextauditdept"  value="<s:property value="obj.nextauditdept"/>" id="nextauditdeptId"/>
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
						 <input type="button" class="btn" id="saveBtn1" value="保存草稿" onclick="tempSaveProject()" /> &nbsp;&nbsp;
						 <input type="button" class="btn" id="saveBtn2" value="提交申报" onclick="saveProject()" /> &nbsp;&nbsp;
						 <input type="reset" class="btn" id="resetBtn" value="重置" /> &nbsp;&nbsp;
						 <input type="button" class="btn" id="backBtn" value="返回"/>
						</span>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>