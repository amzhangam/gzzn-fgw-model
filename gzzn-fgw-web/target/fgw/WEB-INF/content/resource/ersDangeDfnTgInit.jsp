<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>避难场所</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#city").attr('value', '${eidtObj.districtId}');
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"sheltername" : {
					required : true,
					maxlength : 25
				},
				"nucode" : {
					required : true,
					maxlength : 16
				},
				"tel" : {
					required : true,
					maxlength : 16
				}
			},
			messages : {
				"sheltername" : {
					required : "必填字段",
					maxlength : "长度最多是 {0} 的字符串"
				},
				"nucode" : {
					required : "必填字段",
					maxlength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#save").click(function() {
			if (!$("#signupForm").valid()){ return;}
			$("#signupForm").ajaxSubmit({
				url : '${ctx }/resource/ersDangeDfnTgSave.json',
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.success) {
						parent.mac.alert(data.info);
						window.location.href = "${ctx }/resource/ersDangeDfnTgQuery.ac";
					} else {
						parent.mac.alert('保存失败');
					}
				},
				error : function() {
					parent.mac.alert("系统响应失败");
				}
			});
			return false; //此处必须返回false，阻止常规的form提交
		});
	});
</script>

</head>
<style type="text/css">
body {
	margin: 0px;
	margin-left: 0px;
	padding: 0px;
}
</style>
<body style="overflow: hidden">
	<form action="${ctx }/resource/ersDangeDfnTgSave.ac" method="post"
		id='signupForm'>
		<table style="width:100%">
		<tr>
			<td class="tbHeadLeft"></td>
			<td class="tbHeadTitle" id="headTitle">
				应急避难场所信息录入
			</td>
			<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
			<td class="tbHeadBg" style="width: 79%;">
				<div>
					<span style="float: right;">
					 <input type="button" class="btn" value="保存"  id="save" />
					 <input type="button" class="btn" id="resetBtn" value="重置"> 
					 <input type="button" class="btn" value="返回" id="return" name="return" onclick="history.go(-1)" />
					</span>
				</div>
			</td>
			<td class="tbHeadBg" style="width: 3.5%;">
				<div class="tbHeadRight"></div>
			</td>
		</tr>
	</table>
		<table align="center" class="data_form_search" border="0"
			cellpadding="0" cellspacing="0" width="100%">
			
			<tr>
				<th width="25%">名称<span>&nbsp;*</span>
				</th>
				<td width="25%"><input name="dto.objectname" id="objectname"
					title="避难场所名称必填" type="text" class="text"
					value='${eidtObj.objectname }'>
				</td>
				<th width="25%">唯一标志码<span>&nbsp;*</span>
				</th>
				<td width="25%"><input autocomplete="off" name="dto.nucode"
					id="nucode" class="text" title="唯一标志码" type="text"
					value='${eidtObj.nucode }' /></td>
			</tr>
			<tr>
				<th>类型<span>&nbsp;*</span>
				</th>
				<td><select name='dto.objecttype' id="objecttype">
						<option value=''>请选择</option>
						<option value='1'>人防工事</option>
						<option value='2'>避难场所</option>
				</select>
					<th>行政区划</th>
				<td><select id='city' name='city'>
						<option value="">请选择</option>
						<c:forEach items="${districtList }" var='district'>
							<option value="${district.districtId }">${district.districtName
								}</option>
						</c:forEach>
				</select></td>
				
			</tr>
			<tr>
				<th>所属部门</th>
				<td><input name="dto.supervisaldept2" id="supervisaldept2" value='${eidtObj.supervisaldept2}'
					class="text" title="所属部门" type="text" /><span>&nbsp;*</span></td>
				<th>负责人</th>
				<td>
				<input name="dto.principal" id="principal" value='${eidtObj.principal}'
					class="text" title="负责人" type="text" /><span>&nbsp;*</span></td>
			
			</tr>
			<tr>
				<th>联系电话</th>
				<td><input name="dto.tel" id="tel" class="text" title="联系电话"
					type="text" value='${eidtObj.tel}'></td>
				<th>地址</th>
				<td><input name="dto.address" id="address" value='${eidtObj.address}'
					class="text" title="地址" type="text" /><span>&nbsp;*</span></td>
			</tr>
			<tr>
				<th>传真</th>
				<td><input name="dto.fax" id="fax" class="text" title="传真"
					type="text" value='${eidtObj.fax}'></td>
				<th>邮编</th>
				<td><input name="dto.postcode" id="postcode" class="text"
					title="容纳人数" type="text" value='${eidtObj.postcode}'></td>
			</tr>
			<tr class="content">
				<th>经度</th>
				<td><input name="dto.longitude" id="longitude" class="text"
					title="经度" type="text" value='${eidtObj.longitude}'></td>
				<th>纬度</th>
				<td><input name="dto.latitude" id="latitude" class="text"
					title="纬度" type="text" value='${eidtObj.latitude}'>
					<div id="latitudeTip" style="width: 280px"></div></td>
			</tr>
			<tr>
			<th>修改时间</th><td>
					<input name="dto.builddate" id="builddate " class="Wdate"
					title="修改时间" type="text" onclick="WdatePicker({errDealMode:2 })"
					value="${eidtObj.builddate }" />
				<th>备注</th>
				<td colspan="3"><textarea name="dto.notes" id="notes" title="备注"
						rows="3" cols="40">${eidtObj.notes}</textarea>
			</tr>
		</table>
		<div class="ctrls_bar">
		<input type="hidden" value='${eidtObj.objectid }'
				name='objectid'>
		</div>
	</form>
</body>
</html>