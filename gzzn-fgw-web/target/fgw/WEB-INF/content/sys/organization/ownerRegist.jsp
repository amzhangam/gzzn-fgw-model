<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>广州市政府投资项目库申报管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="UTF-8" />
	
<link href="../../resources/css/2_event.css" type="text/css" rel="stylesheet">
<link href="../../resources/css/style.css" type="text/css" rel="stylesheet" />
<link href="../../resources/js/jquery-ztree/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<link href="../../resources/js/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />

<script src="../../resources/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="../../resources/js/jquery-ztree/jquery.ztree.all-3.5.js" type="text/javascript"></script>
<script src="../../resources/js/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="../../resources/js/jquery-validation/1.10.0/messages_cn.js" type="text/javascript"></script>
<script src="../../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="../../resources/js/sys/ownerRegist.js"  type="text/javascript"></script>
<script src="../../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="../../resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript" language="javascript">
	var organizationId;

	$(document).ready(function() {
		$("#returnBtn").click(function() {
			window.location = "${ctx}/";
		});
		
		//alert("${operType}===哈哈哈哈===${operType eq 1}");
		
		if("${operType eq 1}"=="true"){//1-新增注册
			$("#saveBtn").attr("disabled","disabled");
			//得到焦点
			$("#organizationName").focus(function() {
				getOrganFocus();
			});
			//失去焦点
			$("#organizationName").blur(function() {
				lostOrganFocus();
			});
		}
	});

		function regist(){
			//$("#editForm").attr("action","${ctx}/sys/organNew/registOwner.ac");
			$("#editForm").attr("action","${ctx}/sys/organization/registOwner.ac");
			$("#editForm").submit();
			
			// $.ajax({
           //      type: "POST",
           //      dataType: "json",
           //      url: "${ctx}/sys/organNew/registOwner.json",
           //      data: $('#editForm').serialize(),
           //      success: function (data) {
 		   // 		if(data.success){
	       //              alert(data.info);
	      //               window.close();
 		  //  		}
 		  //  		else{
 		  //  			alert(data.info);
 		  //  			window.location = "${ctx}/sys/organization/ownerRegist.json";
 		 //   		}
         //        },
         //        error: function(data) {
        //             alert("error:"+data.responseText);
        //          }

         //    });
		}
		
		function getOrganFocus(){
			$("#saveBtn").attr("disabled","disabled");
		}
		
		function lostOrganFocus(){
			$("#saveBtn").removeAttr("disabled");
			$.post(
		    	"${ctx}/sys/organNew/checkOrganName.json",
		    	{"organizationName":$("#organizationName").val()},
		    	function(data){
		    		var json = JSON.parse(data);
		    		if(json.flag=='true'){//如果已经有这个单位
						//隐藏该单位的信息
						organizationId=json.organizationId;
						$("#dwxz").hide();
						$("#szqy").hide();
						$("#jgdm").hide();
						$("#dwfr").hide();
						$("#dwlxr").hide();
						$("#lxrsjhm").hide();
						$("#dwdz").hide();
						$("#dwxzId").val(json.workunitsquality);
						$("#xqId").val(json.xqId);
						$("#workunitsregistercode").val(json.workunitsregistercode);
						$("#corporation").val(json.workunitsperson);
						$("#linkMan").val(json.workunitslinkman);
						$("#mobile").val(json.workunitslinkmantel);
						$("#adress").val(json.workunitsaddress);
						$("#organizationId").val(organizationId);
					}
					else{//如果还没有有这个单位
						organizationId=null;
						$("#dwxz").show();
						$("#szqy").show();
						$("#jgdm").show();
						$("#dwfr").show();
						$("#dwlxr").show();
						$("#lxrsjhm").show();
						$("#dwdz").show();
						
						$("#dwxzId").val('');
						$("#xqId").val('');
						$("#workunitsregistercode").val('');
						$("#corporation").val('');
						$("#linkMan").val('');
						$("#mobile").val('');
						$("#adress").val('');
						$("#organizationId").val('');
					}
		    	}
	    	);
		}
		
		//刷新验证码
		function refresh() {
	    	//document.getElementById("imgCode").src= "./codeImage.ac?"+Math.random();
	    	document.getElementById("imgCode").src = "${ctx}/servlet/VerfCodeImageServlet?"+Math.random();
	    	//$("#imgCode").attr("src", "./genVerfCodeImage.json?time=<=System.currentTimeMillis()%>");
	    }
	</script>
	
	<form id="editForm" action="./registOwner.ac"  method="post">
		<s:token/>
		<input type="hidden" name="obj.organizationId" id="organizationId" value="${obj.organizationId}"/>
		<input type="hidden" name="obj.workunitsstatus" id="workunitsstatus" value="${obj.workunitsstatus}"/>
		<input type="hidden" name="ownerUser.userId" id="userId"  value="${ownerUser.userId}"/>
		<input type="hidden" name="operType" id="operType" value="${operType}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					单位信息
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="editDiv">
			<table class="editTab" id="editTab">
				<tr>
					<th>业主单位名称：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.organizationName" id="organizationName" value="${obj.organizationName}" />
					</td>
				</tr>
				<tr id="jgdm">
					<th>企业工商注册号(机构代码)：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsregistercode" id="workunitsregistercode" value="${obj.workunitsregistercode}" />
					</td>
				</tr>
				<tr id="dwxz">
					<th>单位性质：</th></th>	
			         <td >
			        	<input type="text" name="workunitsqualityName" id="dwxzName"
				          value="<s:property value="xzMap[obj.workunitsquality]"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.workunitsquality" id="dwxzId"  
				        value="<s:property value="obj.workunitsquality"/>"/>
			      	</td>
				</tr>
				<tr id="szqy">
					<th>所在区域：</th>	<!-- #sysXq.xqmc -->
			         <td >
			        	<input type="text" name="obj.sysXq.xqmc" id="xqName"
				          value="<s:property value="obj.sysXq.xqmc"/>" readonly="readonly"/>
				        <input type="hidden" name="obj.sysXq.xqId" id="xqId"  
				        value="<s:property value="obj.sysXq.xqId"/>"/>
			      	</td>
				</tr>
				<tr id="dwfr">
					<th>单位法人：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsperson" id="corporation" value="${obj.workunitsperson }" />
					</td>
				</tr>
				<tr id="dwlxr">
					<th>单位联系人：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitslinkman" id="linkMan" value="${obj.workunitslinkman }"/>
					</td>
				</tr>
				<tr id="lxrsjhm">
					<th>联系人手机号码：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitslinkmantel" id="mobile" value="${obj.workunitslinkmantel }" />
					</td>
				</tr>
				<tr id="dwdz">
					<th>单位地址：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsaddress" id="adress" value="${obj.workunitsaddress }" />
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					个人信息
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		
		<div class="editDiv">
			<table class="editTab" id="editTab2">
				<tr>
					<th>登录账号<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.loginName" id="loginName" value="${ownerUser.loginName }" />
					</td>
				</tr>
				<c:if test="${empty ownerUser}">
					<tr>
						<th>登录密码<font class="msg">*</font></th>
						<td>
							<input type="password" name="ownerUser.loginPwd" id="loginPwd"  value="${ownerUser.loginPwd }"/>
							<font color="green">密码长度不少于6位，且由A-Z、a-z、0-9、特殊字符【!{}[],.<>@$%&^()_+=】中的至少两项组成</font>
						</td>
					</tr>
					<tr>
						<th>确认密码<font class="msg">*</font></th>
						<td>
							<input type="password" name="confirmPwd" id="confirmPwd"  value="${ownerUser.loginPwd }"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<th>真实姓名<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.userName" id="userName"  value="${ownerUser.userName }"/>
					</td>
				</tr>
				<tr>
					<th >性别<font class="msg">*</font></th>
			     	<td>
				     	<c:choose>
							<c:when test="${ownerUser.sex==1}">
								<input type="radio" name="ownerUser.sex" value="1" class="radio" checked="checked">男  &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="ownerUser.sex" value="0" class="radio" >女
							</c:when>
							<c:otherwise>
								<input type="radio" name="ownerUser.sex" value="1" class="radio">男  &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="ownerUser.sex" value="0" class="radio" checked="checked">女
							</c:otherwise>
						</c:choose>
			     	</td>
				</tr>
				<tr>
					<th>手机号码<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.telmobile" id="telmobile"  value="${ownerUser.telmobile }"/>
					</td>
				</tr>
				<c:if test="${empty ownerUser}">
					<tr>
						<th>验证码<font class="msg">*</font></th>
						<td>
							<input type="text" name="verfCode" id="verfCode"  value=""/>
							<!-- title="点击更换" onclick="javascript:refresh();"  -->
							<img id="imgCode" src="${ctx}/servlet/VerfCodeImageServlet" />
							<a href="javascript:void(0);" onclick="javascript:refresh();">看不清？换一张</a> 
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		<br/>
		<table style="width:100%">
			<tr>
				<td align="center" style="width: 100%;">
						 <input type="button" class="btn" value="${ownerUser==null?'立即注册':'保存'}" onclick="regist()" id="saveBtn"  />&nbsp;&nbsp;
						 <input type="reset" class="btn" id="resetBtn" value="重置"> 
						 <input type="reset" class="btn" id="returnBtn" value="取消"> 
				</td>
			</tr>
		</table>
	</form>
</body>
</html>