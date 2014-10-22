<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>应急机构</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
	<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getTree.js"
	type="text/javascript"></script>	
<script language="javascript">
	$(document).ready(function() {
		
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍

		initMyTree("classify","getClassifyTreeJson.json?catalogue=4");
		initMyTree("district","getDistrictTreeJson.json");
	});
	function setSelectValue(){
		$("#districtId").attr('value', '${editObj.districtId}');
		$("#classifyId").attr('value', '${editObj.classifyId}');
	}
	
</script>
<script type="text/javascript">
	$(function() {
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.orgname" : {
					required : true,
					byteMaxLength :60
				},
				"dto.nucode" : {
					byteMaxLength : 32
				},
				"dto.address" : {
					byteMaxLength : 2000
				},
				"dto.orgduty" : {
					byteMaxLength : 200
				},
				"dto.postcode" : {
					byteMaxLength : 6,
					zipCode:true
				},
				"dto.firstleader" : {
					byteMaxLength : 50
				},
				"dto.firsttel" : {
					byteMaxLength : 50
				},
				"dto.secondleader" : {
					byteMaxLength : 50
				},
				"dto.secondtel" : {
					byteMaxLength : 50
				},
				"dto.dutytel" : {
					byteMaxLength : 50
				},
				"dto.dutyfax" : {
					byteMaxLength : 20
				},
				"dto.notes" : {
					byteMaxLength : 200
				}
			},
			messages : {
				"dto.orgname" : {
					required : "必填字段",
					maxlength : "长度最多是 {0} 的字符串"
				},
				"dto.nucode" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.address" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.orgduty" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.postcode" : {
					byteMaxLength : "长度最多是 {0} 的字符串",
					zipCode:'请输入正确的邮编'
				},
				"dto.firstleader" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.firsttel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.secondleader" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.secondtel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutytel" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutyfax" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#orgduty").charCount({ 
			allowed: 2000
		}); 
		$("#address").charCount({ 
			allowed: 200
		}); 
		$("#notes").charCount({ 
			allowed: 200
		}); 
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersEmrgOrgSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac.alert(data.info);
															window.location.href = "${ctx }/resource/ersEmrgOrgQuery.ac";
														} else {
															parent.mac.alert(data.info);
														}
													},
													error : function() {
														parent.mac.alert("系统响应失败");
													}
												});
								return false; //此处必须返回false，阻止常规的form提交
							}
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
	<script type="text/javascript">
		$(function(){
				showMenu(2, 31);
		});
	</script>
	<form action="${ctx }/resource/ersEmrgOrgSave.ac" method="post"
		id='signupForm'>
		
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					应急机构录入
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
		<table align="center" class="editTab" border="0" width="100%">
			<tbody>
				<tr>
					<th>应急机构名称</th>
					<td><input type="text" class="text" name="dto.orgname"
						value="${editObj.orgname }" /><span>&nbsp;*</span>
					</td>
					<th>唯一标识码</th>
					<td><input type="text" class="text" name="dto.nucode"
						id='nucode' value='${editObj.nucode }' />
					</td>

				</tr>
				<tr>
							<th>类别名称</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" readonly="readonly" id="classifyName" /></td>
						<th>行政区划</th>
					<td><input id="districtName" name='dto.districtName'
						type="text" readonly="readonly" value="${editObj.districtName }" /><input
						id="districtId" name='dto.districtId' type="hidden"
						value="${editObj.districtId }" /></td>
				</tr>
				<tr>
					<th>机构职责</th>
					<td colspan="3"><textarea name="dto.orgduty" cols="100" rows="3"
							id='orgduty'>${editObj.orgduty}</textarea>
					</td>
					
				</tr>
						<tr>
					<th>地址</th>
					<td colspan="3"><textarea name="dto.address" cols="100" rows="2"
							id='address'>${editObj.address}</textarea>
					</td>
					
				</tr>
				<tr>
					<th>邮编</th>
					<td colspan="3"><input type="text" class="text"
						name="dto.postcode" id='postcode' value="${editObj.postcode}" />
					</td>
				</tr>
				<tr>
					<th>应急办领导</th>
					<td><input type="text" class="text" name="dto.firstleader"
						id='firstleader' value="${editObj.firstleader}" />
					</td>
					<th>应急办领导联系电话</th>
					<td><input type="text" class="text" name="dto.firsttel"
						id='firsttel' value="${editObj.firsttel}" />
					</td>

				</tr>
				<tr>
					<th>应急办分管领导</th>
					<td><input type="text" class="text" name="dto.secondleader"
						id='secondleader' value="${editObj.secondleader}" />
					</td>
					<th>应急办分管领导联系电话</th>
					<td><input type="text" class="text" name="dto.secondtel"
						id='secondtel' value="${editObj.secondtel}" />
					</td>
				</tr>
				<tr>
					<th>应急值班电话</th>
					<td><input type="text" class="text" name="dto.dutytel"
						id='dutytel' value="${editObj.dutytel}" />
					</td>
					<th>应急值班传真</th>
					<td><input type="text" class="text" name="dto.dutyfax"
						id='dutyfax' value="${editObj.dutyfax }" />
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="2"
							id='notes'>${editObj.notes}</textarea>
					</td>
				</tr> 
		</table>
		<div class="ctrls_bar">
			<input type="hidden"
				name='orgid' value='${editObj.orgid }' />
		</div>
	</form>
</body>
</html>