<%@ page contentType="text/html;charset=UTF-8"%>
　<!-- 报表查询条件：项目名称、项目类型（新开工项目、续建项目、预备项目）、
		建设起止年份、项目管理单位、主管单位、主要建设内容、申报依据、市财政资金安排渠道建议 、	
		项目总投资(合计、市财政资金、融资(含银行贷款)、自有资金、其它)、
		项目总投资(合计、市财政资金、融资(含银行贷款)、自有资金、其它)
		
	  2014-8-7新增查询条件【项目必须为审核通过项目（10）、项目储备类型为‘正式项目库’】
		项目业主、项目分类（基本建设投资类项目、更新改造投资类项目、其他固定资产投资类项目）、行业类别、
		资金性质、项目编码、当前处理部门
		
	-->
	<table class="topSearchTab" style="margin-top: -10px;">
		<tr>
			<th>项目名称：</th>
			<td><input type="text" class="text" name="params.projectName" id="projectName" value="${params.projectName }"/>	</td>
			<th>项目分类：</th>
			<td>
				<select id="xmfl" name="params.xmfl"  style="border: 1px solid #be9a4d;width: 136px;">
						<option value="">(无)</option>
						<option value="1">基本建设投资类项目</option>
						<option value="2">更新改造投资类项目</option>
						<option value="3">其他固定资产投资类项目</option>
				</select>
			</td>
			<th>项目主管单位：</th>
			<td><input type="text" class="text" name="params.organizationName" id="organizationName" value="${params.organizationName }"/>	</td>
			<th>
				<input type="button" class="btn" id="queryBtn" value="查询" />&nbsp;&nbsp;
				<input type="button" class="btn" id="clearBtn" value="清空" />  
			</th>
		</tr>
		<tr>
			<th>项目业主：</th>
			<td>
				<input type="text" class="text" name="params.xmyz" id="xmyz" value="${params.xmyz}" />
			</td>
			<th>项目类型：</th>
			<td>
				<input type="text" class="text" id="xmlxTreeSelName" value="" readonly="readonly" />
				<input type="hidden" name="params.xmlx" id="xmlxTreeSelID" value="${params.xmlx}"/>
				<div id="xmlxTreeDiv" class="menuContent" style="display:none; position: absolute; width:200px; height: 300px;">
					<ul id="xmlxTree" class="ztree"  style="margin-top:0;"></ul>
				</div>    
			</td>
			<th>所属区域：</th>
			<td colspan="2">
				<input type="text" id="xqIdSelName" class="text" value="" readonly="readonly" />
				<input type="hidden" name="params.xqId" id="xqIdSelID" value="${params.xqId}"/>
				<div id="xqIdDiv" class="menuContent" style="display:none; position: absolute;width:150px;height: 250px;">
					<ul id="xqId" class="ztree"  style="margin-top:0;"></ul>
				</div>
			</td>
		</tr>
		<tr>
			<th>修改时间：</th>
			<td colspan="6">
				<input type="text"  class="text Wdate" style="width:80px;" id="beginModifiedTime" name="params.beginModifiedTime" value="${params.beginModifiedTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endModifiedTime\')}'})" />
				 至 
				<input type="text"  class="text Wdate" style="width:80px;" id="endModifiedTime" name="params.endModifiedTime" value="${params.endModifiedTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'beginModifiedTime\')}'})" /> 
			</td>
		</tr>
		
		<tr>
			<th></th>
			<th colspan="4" style="color: red;text-align: left;" id="checkFormErr"></th>
			<td colspan="2">
				<input type="hidden"  name="moreSearchBool" id="moreSearchBool"  value="${moreSearchBool}"/>
				<input type="hidden"  name="params.reportType" id="reportType"  value="${params.reportType}"/>
				<a href="javaScript:showMoreSearchInfo();" id="moreSearch">显示更多查询条件...</a>
			</td>
		</tr>
		
		<tr class="moreSearchInfo">
			<th>项目编码：</th>
			<td><input type="text" class="text" name="params.projectcode" id="projectcode" value="${params.projectcode}" />	</td>
			<th>资金性质：</th>
			<td>
				<input type="text" class="text" id="zjxzTreeSelName" value="" readonly="readonly" />
				<input type="hidden" name="params.zjxz" id="zjxzTreeSelID" value="${params.zjxz}"/>
				<div id="zjxzTreeDiv" class="menuContent" style="display:none; position: absolute; width:200px; height: 300px;">
					<ul id="zjxzTree" class="ztree"  style="margin-top:0;"></ul>
				</div>  
			</td>
			<th>建设起止年份：</th>
			<td colspan="2">
				<input type="text" class="text Wdate" style="width:55px" name="params.startyear" id="startyear" value="${params.startyear}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'endyear\')}'})"/>
				至&nbsp;
				<input type="text" class="text Wdate" style="width:55px" name="params.endyear" id="endyear" value="${params.endyear}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'startyear\')}'})"/>
			</td>
		</tr>
		<tr class="moreSearchInfo">
			<th>行业类别：</th>
			<td>
				<input type="text" class="text" id="hylbTreeSelName" value="" readonly="readonly" />
				<input type="hidden" name="params.hylb" id="hylbTreeSelID" value="${params.hylb}"/>
				<div id="hylbTreeDiv" class="menuContent" style="display:none; position: absolute; width:200px; height: 300px;">
					<ul id="hylbTree" class="ztree"  style="margin-top:0;"></ul>
				</div>    
			</td>
			<th>处理部门：</th>
			<td>
				<input type="text" class="text" name="params.deptname" value="${params.deptname}" />
			</td>
			<th>建设管理单位：</th>
			<td  colspan="2">
				<input type="text" class="text" name="params.manageUnitsName" id="manageUnitsName" value="${params.manageUnitsName }"/>	
			</td>
		</tr>
		<tr class="moreSearchInfo">
			<th>申报依据：</th>
			<td><input type="text" class="text" name="params.declaregist" id="declaregist" value="${params.declaregist }"/>	</td>
			<th>主要建设内容：</th>
			<td>
				<input type="text" class="text" name="params.projectContent" id="projectContent" value="${params.projectContent }"/>
			</td>
			<th>市财政资金安排渠道建议：</th>
			<td  colspan="2"><input type="text" class="text" name="params.pjinvestAdvice" id="pjinvestAdvice" value="${params.pjinvestAdvice }"/> </td>
		</tr>
		<tr class="moreSearchInfo">
			<th>预计至
				<input type="text" class="text Wdate" style="width:45px" name="params.expectFinishInvestYear" id="expectFinishInvestYear" value="${params.expectFinishInvestYear}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true})"/>
			</th>
			<td colspan="6">
				<span style="color: #074E81;font-weight: bold;">年底累计完成投资</span>
				合计：
				<input type="text" class="text moneyText" name="params.expectFinishInvest" id="expectFinishInvest" value="<fmt:formatNumber value='${params.expectFinishInvest }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.expectFinishInvest2" id="expectFinishInvest2" value="<fmt:formatNumber value='${params.expectFinishInvest2 }' pattern='#0.##'/>" />
				万元，其中市本级财政资金：
				<input type="text" class="text moneyText" name="params.expectFinishOtherInvest" id="expectFinishOtherInvest" value="<fmt:formatNumber value='${params.expectFinishOtherInvest }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.expectFinishOtherInvest2" id="expectFinishOtherInvest2" value="<fmt:formatNumber value='${params.expectFinishOtherInvest2 }' pattern='#0.##'/>" />
			</td>
		</tr>
		<tr class="moreSearchInfo">
			<th style="color:red;text-align: left;" colspan="2">项目总投资(万元)</th>
			<td colspan="5"></td>
		</tr>
		<tr class="moreSearchInfo">
			<th>合计：</th>
			<td>
				<input type="text" class="text moneyText" name="params.pjinvestSum" id="pjinvestSum" value="<fmt:formatNumber value='${params.pjinvestSum }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.pjinvestSum2" id="pjinvestSum2" value="<fmt:formatNumber value='${params.pjinvestSum2 }' pattern='#0.##'/>" />
			</td>
			<th>市财政资金：</th>
			<td>
				<input type="text" class="text moneyText" name="params.pjinvestCity" id="pjinvestCity" value="<fmt:formatNumber value='${params.pjinvestCity }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.pjinvestCity2" id="pjinvestCity2" value="<fmt:formatNumber value='${params.pjinvestCity2 }' pattern='#0.##'/>" />
			</td>
			<th>融资(含银行贷款)：</th>
			<td colspan="2">
				<input type="text" class="text moneyText" name="params.pjinvestBank" id="pjinvestBank" value="<fmt:formatNumber value='${params.pjinvestBank }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.pjinvestBank2" id="pjinvestBank2" value="<fmt:formatNumber value='${params.pjinvestBank2 }' pattern='#0.##'/>" />
			</td>
		</tr>
		<tr class="moreSearchInfo">
			<th>自有资金：</th>
			<td>
				<input type="text" class="text moneyText" name="params.pjinvestCompany" id="pjinvestCompany" value="<fmt:formatNumber value='${params.pjinvestCompany }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.pjinvestCompany2" id="pjinvestCompany2" value="<fmt:formatNumber value='${params.pjinvestCompany2 }' pattern='#0.##'/>" />
			</td>
			<th>其它：</th>
			<td colspan="4">
				<input type="text" class="text moneyText" name="params.pjinvestOther" id="pjinvestOther" value="<fmt:formatNumber value='${params.pjinvestOther }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.pjinvestOther2" id="pjinvestOther2" value="<fmt:formatNumber value='${params.pjinvestOther2 }' pattern='#0.##'/>" />
			</td>
		</tr>
		<tr class="moreSearchInfo">
			<th style="color:red;text-align: left;" colspan="2">
				<input type="text" class="text Wdate" style="width:45px" name="params.planInvestYear" id="planInvestYear" value="${params.planInvestYear}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true})"/>
				年投资计划建议(万元) 
			</th>
			<td colspan="5"></td>
		</tr>
		<tr class="moreSearchInfo">
			<th>合计：</th>
			<td>
				<input type="text" class="text moneyText" name="params.planInvestSum" id="planInvestSum" value="<fmt:formatNumber value='${params.planInvestSum }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.planInvestSum2" id="planInvestSum2" value="<fmt:formatNumber value='${params.planInvestSum2 }' pattern='#0.##'/>" />
			</td>
			<th>市财政资金：</th>
			<td>
				<input type="text" class="text moneyText" name="params.planInvestCity" id="planInvestCity" value="<fmt:formatNumber value='${params.planInvestCity }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.planInvestCity2" id="planInvestCity2" value="<fmt:formatNumber value='${params.planInvestCity2 }' pattern='#0.##'/>" />
			</td>
			<th>融资(含银行贷款)：</th>
			<td colspan="2">
				<input type="text" class="text moneyText" name="params.planInvestBank" id="planInvestBank" value="<fmt:formatNumber value='${params.planInvestBank }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.planInvestBank2" id="planInvestBank2" value="<fmt:formatNumber value='${params.planInvestBank2 }' pattern='#0.##'/>" />
			</td>
		</tr>
		<tr class="moreSearchInfo">
			<th>自有资金：</th>
			<td>
				<input type="text" class="text moneyText" name="params.planInvestCompany" id="planInvestCompany" value="<fmt:formatNumber value='${params.planInvestCompany }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.planInvestCompany2" id="planInvestCompany2" value="<fmt:formatNumber value='${params.planInvestCompany2 }' pattern='#0.##'/>" />
			</td>
			<th>其它：</th>
			<td colspan="4">
				<input type="text" class="text moneyText" name="params.planInvestOther" id="planInvestOther" value="<fmt:formatNumber value='${params.planInvestOther }' pattern='#0.##'/>" />
				到&nbsp;
				<input type="text" class="text moneyText" name="params.planInvestOther2" id="planInvestOther2" value="<fmt:formatNumber value='${params.planInvestOther2 }' pattern='#0.##'/>" />
			</td>
		</tr>
	</table>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var setting = {
				check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onCheckBoxCheck
				}
			};
/**报表统计时，相关信息的处理*/
function reportStaInfo(){
		initMoreSearchInfo();//初始化：显示更多查询条件...
		
		$("#xmfl").val("${params.xmfl}");//项目分类下拉选择框
		//项目类型
		var xmlxDatas = getJsonDatas("${ctx}/com/getXmsbXmlxJson.json","");
		var xmlxTree = initCheckBoxTree("xmlxTree", setting, xmlxDatas, "${params.xmlx}");//初始化多选的树形下拉框
		//区域
		var xqIdDatas = getJsonDatas("${ctx}/com/getSysXqJson.json","");
		var xqIdTree = initCheckBoxTree("xqId", setting, xqIdDatas, "${params.xqId}");//初始化多选的树形下拉框
		//行业类别
		var hylbDatas = getJsonDatas("${ctx}/com/getXmsbHylbJson.json","");
		var hylbTree = initCheckBoxTree("hylbTree", setting, hylbDatas, "${params.hylb}");//初始化多选的树形下拉框
		//资金性质
		var zjxzDatas = getJsonDatas("${ctx}/com/getXmsbZjxzJson.json");
		var t1 = initCheckBoxTree("zjxzTree", setting, zjxzDatas, "${params.zjxz}");//初始化多选的树形下拉框
		
		//清空事件
		$("#clearBtn").click(function(){
			clearCheckNodes(xmlxTree,"xmlxTree");//清空树中被选中的节点
			clearCheckNodes(xqIdTree,"xqId");
			clearCheckNodes(t1,"zjxzTree");
			clearCheckNodes(hylbTree,"hylbTree");
			
			$(".topSearchTab input[type='text'],.topSearchTab select").each(function(index) {
				$(this).val("");
			});
		});
		
		//提交查询
		$("#queryBtn").click(function(){
			var str = "";
			//判断项目总投资查询输入值的合法性：DECIMAL(18,2)
			//var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
			var reg = new RegExp("^([1-9]{1}[0-9]{0,15}|0|0\.{1}[0-9]{0,2}|[1-9]{1}[0-9]{0,15}\.{1}[0-9]{0,2})$");
			$(".moneyText").each(function(index) {
				if($(this).val()!="" && !reg.test($(this).val())){
					 str += "累计完成投资、项目总投资(万元)、投资计划建议(万元)中的输入条件，必须为数值且小数点后可保留两位小数！";//必须符合金额格式
					 $(this).focus();//将光标订到指定位置
					 return false;
				}
			});
			$("#checkFormErr").html(str);
			$("#currentPage").val("0");
			if(str==""){
				//$("#report").submit();
				$("form")[0].submit();
			}
		}); 
}
	
	
/**初始化：显示更多查询条件... */
function initMoreSearchInfo(){
	if($("#moreSearchBool").val()=="true"){
		$(".moreSearchInfo").css("display","table-row");
		//$(".moreSearchInfo").css("display","inline-block");
		$("#moreSearch").html("隐藏更多查询条件...");
	}else{
		$(".moreSearchInfo").css("display","none");
		$("#moreSearch").html("显示更多查询条件...");
	}
}


/**显示更多查询条件... */
function showMoreSearchInfo(){
	//alert("===="+$(".moreSearchInfo").css("display"));
	if($(".moreSearchInfo").css("display")=="none"){//更多查询内容被隐藏，现在需要展示它
		$(".moreSearchInfo").css("display","table-row");
		//$(".moreSearchInfo").css("display","inline-block");
		$("#moreSearchBool").val(true);
		$("#moreSearch").html("隐藏更多查询条件...");
	}else{
		$(".moreSearchInfo").css("display","none");
		$("#moreSearchBool").val(false);
		$("#moreSearch").html("显示更多查询条件...");
	}
	//$(".moreSearchInfo").slideToggle("fast");
}
	
//-->
</script>		
