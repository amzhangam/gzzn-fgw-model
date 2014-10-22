<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>案例信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Description" content="避难场所-避难场所查询结果">
<script src="${ctx}/resources/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/charCount.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/ers/getTree.js" type="text/javascript"></script>
<style type="text/css">
.tabswrap {
	width: 50%;
	position: relative;
}

.tabs {
	list-style: none;
	position: relative;
	top: 1px;
	z-index: 1;
	height: 25px;
	margin-top: 0px;
	margin-bottom: 0px;
	padding-left: 0px;
}

.tabs li {
	width: 140px;
	height: 25px;
	line-height: 25px;
	background: url('${ctx}/resources/images/jQueryTabs/tabs.png') no-repeat;
	float: left;
	display: block;
	text-align: center;
	color: #fff;
	cursor: pointer;
	font-size: 12px;
	text-shadow: -1px -1px 0 #039;
}

.tabs li.select {
	color: #4382b7;
	font-weight: bold;
	background: url('${ctx}/resources/images/jQueryTabs/tabs_v.png')
		no-repeat;
	text-shadow: 1px 1px 0 #fff;
}
</style>
<script language="javascript">
	$(document).ready(function() {
		selectMenu(2);
		//来至getTree.js----classify初始化的是 classifyName的选择，classifyID的值
		initMyTree("district","getDistrictTreeJson.json");
		//分类目录1:突发事件，2:危险源与风险隐患区3::防护目标4:应急保障资源5::应急知识6:应急预案7:应急平台8法律法规9医疗信息分类10通信类别11运输保障资源12应急物资机构13应急救援队伍14应急公安队伍
		initMyTree("classify","getClassifyTreeJson.json?catalogue=1");
		
		$("#resetBtn").click(function(){
			 $("form")[0].reset();
			 setSelectValue();
		});
		setSelectValue();
	});
	function setSelectValue(){
		$("#eventlevelcode2").attr('value',"${editObj.eventlevelcode2}");
		$("#evaluaterate").attr('value',"${editObj.evaluaterate}");
	}
</script>
<script src="${ctx}/resources/js/ers/getTree.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#lession").charCount({ 
			allowed: 2000
		}); 
		$("#treatstate").charCount({ 
			allowed: 2000
		}); 
		$("#dutyorg").charCount({ 
			allowed: 2000
		}); 
		$("#cause").charCount({ 
			allowed: 500
		}); 
		$("#influencerange").charCount({ 
			allowed: 200
		}); 
		$("#content").charCount({ 
			allowed: 4000
		}); 
		$("#notes").charCount({ 
			allowed: 200
		}); 
		$("#signupForm").validate({
			event : "blur",
			rules : {
				"dto.casename" : {
					required : true,
					byteMaxLength : 200
				},
				"dto.source" : {
					byteMaxLength : 100
				},
				"dto.keyword" : {
					byteMaxLength : 200
				},
				"dto.address" : {
					byteMaxLength : 200
				},
				"dto.lession" : {
					byteMaxLength : 2000
				},
				"dto.treatstate" : {
					byteMaxLength : 2000
				},
				"dto.dutyorg" : {
					byteMaxLength : 2000
				},
				"dto.economyloss" : {
					byteMaxLength : 200
				},
				"dto.casualty" : {
					byteMaxLength : 200
				},
				"dto.influencerange" : {
					byteMaxLength : 200
				},
				"dto.cause" : {
					byteMaxLength : 500
				},
				"dto.influencerange" : {
					byteMaxLength : 200
				},
				"dto.content" : {
					byteMaxLength : 4000
				},
				"dto.notes" : {
					byteMaxLength : 200
				}
			},
			messages : {
				"dto.casename" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.source" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.keyword" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.address" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.lession" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.treatstate" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.dutyorg" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.economyloss" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.casualty" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.influencerange" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.cause" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.influencerange" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.content" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				},
				"dto.notes" : {
					required : "必填字段",
					byteMaxLength : "长度最多是 {0} 的字符串"
				}
			}
		});
		$("#save")
				.click(
						function() {
							if ($("#signupForm").valid()) {
								$("#signupForm")
										.ajaxSubmit(
												{
													url : '${ctx }/resource/ersEmrgCaseSave.json',
													type : "post",
													dataType : "json",
													success : function(data) {
														if (data.success) {
															parent.mac.alert(data.info);
															window.location.href = "${ctx }/resource/ersEmrgCaseQuery.ac";
														} else {
															parent.mac.alert('保存失败');
														}
													},
													error : function() {
														parent.mac.alert("系统响应失败");
													}
												});
								return false; //此处必须返回false，阻止常规的form提交
							}
						});
		$("#newTr")
				.click(
						function() {
							var current = $(this).parent().parent();
							var prev = '<tr><td colspan="2"><input name="upload" type="file"></td></tr>';
							current.parent().append(prev);
						});
		
	});
	function deleteFile(id){
		$.ajax({
	    	type:"POST",
	    	url:"${ctx }/resource/ersCaseAttachDeleteFile.json",
	    	data:{fileId:id},
	    	success:function(msg){
	    		if(msg.success){
		    		parent.mac.alert("删除成功");
		    		$("#delete_"+id).parent().parent().remove();
	    		}else{
	    			parent.mac.alert(msg.info);
		    	}
	    	},
	    	error:function() { 
	    		parent.mac.alert("系统响应异常");
			}, 
	    	dataType:'json'
		});
	}
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
	<script type="text/javascript">$(function(){
				showMenu(2, 25);
		});</script>
	<form action="${ctx }/resource/ersEmrgCaseSave.ac" method="post"
		id='signupForm'>

		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					案例信息录入
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
					<th>标题<span style="color: red">&nbsp;*</span></th>
					<td><input name="dto.casename" id="casename" type="text"
						class="text" cssRule="require3" value="${editObj.casename }" size='40px;'>
					</td>
					<th>案列来源</th>
					<td><input type="text" class="text" name="dto.source"
						id="source" value="${editObj.source }" /></td>
				</tr>
				<tr>
					<th>行政区划</th>
					<td><input id="districtName" name='dto.districtName'
						type="text" readonly="readonly" value="${editObj.districtName }" /><input
						id="districtId" name='dto.districtId' type="hidden"
						value="${editObj.districtId }" /></td>
					<th>类别名称</th>
					<td><input type="hidden" class="text" name="dto.classifyId"
						value="${editObj.classifyId }" id='classifyId' /> <input
						type="text" class="text" name="dto.classifyName"
						value="${editObj.classifyName}" readonly="readonly"
						id="classifyName" />
					</td>

				</tr>
				<tr>
					<th>主题词</th>
					<td><input name="dto.keyword" id=keyword type="text"
						class="text" cssRule="require3" value="${editObj.keyword }">
					</td>
					<th>事件级别</th>
					<td><select name="dto.eventlevelcode2" style="width: 150px"
						id='eventlevelcode2'>
							<option value=''>请选择</option>
							<c:forEach var="map" items="${eventlevelcodeMap }">
								<option value='${map.key }'>${map.value }</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<th>评估级别</th>
					<td><select name="dto.evaluaterate" style="width: 150px"
						id='evaluaterate'>
							<option value=''>请选择</option>
							<c:forEach var="map" items="${eventlevelcodeMap }">
								<option value='${map.key }'>${map.value }</option>
							</c:forEach>
					</select>
					</td>
					<th>地址</th>
					<td><input type="text" class="text" name="dto.address"
						id='address' value='${editObj.address }' /></td>
				</tr>

				<tr>

					<th>事发时间</th>
					<td><input type="text" value="${editObj.starttime }"
						name="dto.starttime" id='starttime'
						onclick="WdatePicker({errDealMode:2 ,maxDate:'#F{$dp.$D(\'endtime\')}'})"
						class="Wdate" /></td>
					<th>结束时间</th>
					<td><input type="text" name="dto.endtime" id='endtime'
						value="${editObj.endtime }"
						onclick="WdatePicker({errDealMode:2 ,minDate:'#F{$dp.$D(\'starttime\')}'})"
						class="Wdate" /></td>
				</tr>
	<tr>
					<th>财产损失情况</th>
					<td><input name="dto.economyloss" id="economyloss" type="text"
						class="text" cssRule="require3" value="${editObj.economyloss }">
					</td>
					<th>人员伤亡情况</th>
					<td><input type="text" class="text" name="dto.casualty"
						id="casualty" value="${editObj.casualty }" /></td>
				</tr>
				<tr>
					<th>经验教训</th>
					<td colspan="3"><textarea name="dto.lession" cols="100"
							rows="3" id='lession'>${editObj.lession}</textarea></td>
				</tr>
				<tr>
					<th>处置情况</th>
					<td colspan="3"><textarea name="dto.treatstate" cols="100"
							rows="3" id='treatstate'>${editObj.treatstate}</textarea></td>
				</tr>
				<tr>
					<th>责任单位</th>
					<td colspan="3"><textarea name="dto.dutyorg" cols="100"
							rows="3" id='dutyorg'>${editObj.dutyorg}</textarea></td>
				</tr>
			
				<tr>
					<th>影响范围</th>

					<td colspan="3"><textarea name="dto.influencerange" cols="100"
							rows="1" id='influencerange'>${editObj.influencerange}</textarea>
					</td>
				</tr>
				<tr>
					<th>事发原因</th>
					<td colspan="3"><textarea name="dto.cause" cols="100" rows="3"
							id='cause'>${editObj.cause}</textarea></td>
				</tr>
				<tr>
					<th>预案版本内容</th>
					<td colspan="3"><textarea name="dto.content" cols="100"
							rows="3" id='content'>${editObj.content}</textarea></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="dto.notes" cols="100" rows="1"
							id='notes'>${editObj.notes}</textarea></td>
				</tr>
				<tr>
					<th>相关文件</th>
					<td style="border-right-width: 0px;" valign="top">
						<table width="50%" class="data_grid">
							<tr>
								<td><input type="button" value='添加' class="btn" id='newTr'>
								</td>
							</tr>
							<tr>
								<td><input type='file' name='upload'>
								</td>
							</tr>
						</table></td>
					<td colspan="2" valign="top">
						<table width="50%" class="data_grid" border="0">
							<tbody>
								<c:forEach var="emrgCaseFile" items="${caseAttachSet }">


									<tr>
										<td><a target="_blank"
											href="${ctx }/resource/ersCaseAttachGetFile.ac?fileId=${emrgCaseFile.attachid}">${emrgCaseFile.attachname
												}</a>
										</td>
										<td><a id="delete_${emrgCaseFile.attachid}"
											onclick="deleteFile(${emrgCaseFile.attachid})"
											href="javascript:;">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>


		<div class="ctrls_bar">
		<input type="hidden"
				name='caseid' value='${editObj.caseid }' />
		</div>
	</form>

</body>
</html>