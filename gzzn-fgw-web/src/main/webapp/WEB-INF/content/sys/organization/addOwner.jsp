<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<title>
业主注册
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script src="../../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../../resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="../../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
	<script src="../../resources/js/sys/ownerRegist.js"  type="text/javascript"></script>
	<script src="../../resources/js/sys/public.js" type="text/javascript"></script>
	<script src="../../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../../resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>


	<form id="editForm" action="./saveOwner.ac"  method="post">
		<input type="hidden" name="obj.organizationId"/>
		<input type="hidden" name="obj.orgId"/>
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
						<input type="text" name="obj.organizationName" id="organizationName" />
					</td>
				</tr>
				<tr>
					<th>企业工商注册号(机构代码)：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsregistercode" id="workunitsregistercode" />
					</td>
				</tr>
				<tr>
					<th>单位性质：<font class="msg">*</font></th></th>	
			         <td >
			        	<input type="text" name="unitNature.itemText" id="unitNatureName"
				          value="<s:property value="#attr.obj.unitNature.itemText"/>" readonly="readonly"/>
				        <input type="hidden" name="unitNature.id" id="unitNatureId"  
				        value="<s:property value="#attr.obj.unitNature.id"/>"/>
			      	</td>
				</tr>
				<tr>
					<th>所在区域：<font class="msg">*</font></th></th>	
			         <td >
			        	<input type="text" name="area.itemText" id="areaName"
				          value="<s:property value="#attr.obj.area.itemText"/>" readonly="readonly"/>
				        <input type="hidden" name="area.id" id="areaId"  
				        value="<s:property value="#attr.obj.area.id"/>"/>
			      	</td>
				</tr>
				<tr>
					<th>单位法人：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.corporation" id="corporation" />
					</td>
				</tr>
				<tr>
					<th>项目联系人：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.linkMan" id="linkMan" />
					</td>
				</tr>
				<tr>
					<th>项目联系人手机号码：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.mobile" id="mobile" />
					</td>
				</tr>
				<tr>
					<th>单位地址：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.adress" id="adress" />
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
					<th>用户名：<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.loginName" id="loginName" />
					</td>
				</tr>
				<tr>
					<th>密码：<font class="msg">*</font></th>
					<td>
						<input type="password" name="ownerUser.loginPwd" id=loginPwd />
					</td>
				</tr>
				<tr>
					<th>确认密码：<font class="msg">*</font></th>
					<td>
						<input type="password" name="confirmPwd" id="confirmPwd" />
					</td>
				</tr>
				<tr>
					<th>真实姓名：<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.userName" id="userName" />
					</td>
				</tr>
				<tr>
					<th >性别<font class="msg">*</font></th>
			     	<td>
			     		<input type="radio" name="ownerUser.sex" value="1" class="radio" checked="checked">男
						<input type="radio" name="ownerUser.sex" value="0" class="radio" >女
			     	</td>
				</tr>
				<tr>
					<th>手机号码：<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.telmobile" id="telmobile" />
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<table style="width:100%">
			<tr>
				<td align="center" style="width: 100%;">
						 <input type="submit" class="btn" value="确定"  id="saveBtn"  />
						 <input type="reset" class="btn" id="resetBtn" value="重置"> 
				</td>
			</tr>
		</table>
	</form>
</body>
</html>