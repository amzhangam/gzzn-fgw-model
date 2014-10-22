<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<% String flag=(String)request.getAttribute("flag"); %>
<% String num=(String)request.getAttribute("num"); %>
<html>
  <head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<script type="text/javascript">
	$(document).ready(function(){
		//给子菜单定义样式
		$("td[class^=subMod]").css({
			"padding-left":"23px",
			"border-bottom":"#ACB8D6 1px solid",
			"display":"none"
		});
		
		$("#pgzj").click(function(){
			 $("[id^='pgzj_']").toggle();
		});
		$("#pggl").click(function(){
			 $("[id^='pggl_']").toggle();
		});
	});

	function selectMenu(order){
		for(var i=1;i<9;i++){
			if(i == order){
				$("#menuImg" +i).attr("src","${ctx}/resources/images/lefticon/" + i + "_select.gif");
				$("#menu" +i).removeClass("hide");
			}else{
				$("#menuImg" +i).attr("src","${ctx}/resources/images/lefticon/" + i + "_unselect.gif");
				$("#menu" +i).addClass("hide");
			}
		}
		if(order+1<9){
			$("#menuImg" +(order+1)).attr("src","${ctx}/resources/images/lefticon/" + (order+1) + "_next.gif");
		}
	}
	
	function subModule(num){
		var className = "subMod"+ num;
		if($("."+className).css("display")=="none"){
			$("."+className).css("display","block");
		}else{
			$("."+className).css("display","none");
		}
	}
	
	function leftc1Click(){
		var leftc1 = $(".leftc1");
		if($(leftc1).css("display")=="none"){
			$(leftc1).css("display","block");
		}else if($(leftc1).css("display")=="block"){
			$(leftc1).css("display","none");
		}
	}

</script>

<script type="text/javascript">
$(document).ready(function(){
var flag=<%=flag%>;
var num=<%=num%>;
if(flag!=""){
subModule(num);
}
});
</script>
</head>
<body>
<table width="167" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="33" valign="top" bgcolor="E3E8F8">
		<div class="lefts" style=" width:100%;height:700px;">
		<div>
			<a href="javascript:selectMenu(1);" title="应急管理值守"><img id="menuImg1" src="${ctx}/resources/images/lefticon/1_select.gif"  alt="应急管理值守" width="33" height="43" border="0"></a>
		</div>
		<div>		  
			<a href="javascript:selectMenu(2);" title="应急事件管理"><img id="menuImg2" src="${ctx}/resources/images/lefticon/2_next.gif" alt="应急事件管理" width="33" height="43" border="0"></a>
		</div>
		<div>
			<a href="javascript:selectMenu(3);" title="应急预案管理" ><img id="menuImg3" src="${ctx}/resources/images/lefticon/3_unselect.gif"  alt="应急预案管理" width="33" height="43" border="0"></a>
		</div>
		<div>
			<a href="javascript:selectMenu(4);" title="应急资源管理"><img id="menuImg4" src="${ctx}/resources/images/lefticon/4_unselect.gif" alt="应急资源管理" width="33" height="43" border="0" ></a>
		</div>
		<div>
			<a href="javascript:selectMenu(5);" title="应急演练管理"><img id="menuImg5" src="${ctx}/resources/images/lefticon/5_unselect.gif" alt="应急演练管理" width="33" height="43" border="0"></a>
		</div>
		<div>
			<a href="javascript:selectMenu(6);" title="危险源管理" ><img id="menuImg6" src="${ctx}/resources/images/lefticon/6_unselect.gif" alt="危险源管理" width="33" height="43" border="0" ></a>
		</div>
		<div>
			<a href="javascript:selectMenu(7);" title="风险隐患" ><img id="menuImg7" src="${ctx}/resources/images/lefticon/7_unselect.gif" alt="风险隐患" width="33" height="43" border="0" ></a>
		</div>
		<div>
			<a  href="javascript:selectMenu(8);" title="系统管理"><img id="menuImg8" src="${ctx}/resources/images/lefticon/8_unselect.gif" alt="系统管理" width="33" height="55" border="0"></a>
		</div>
		</div>
    </td>
    <td valign="top" class="left">
    	<%@include file="left_1_yjzs.jsp"%>
    	<%@include file="left_2_yjzy.jsp"%>
    	<%@include file="left_3_yjya.jsp"%>
    	<%@include file="left_4_yjzm.jsp"%>
    	<%@include file="left_5_sjsb.jsp"%>
    	<%@include file="left_6_xtgl.jsp"%>
    
    <div id="menu2" class="hide">
    	<table width="95%"  border="0">
	      <tr>
	        <td class="lefta">应急事件管理</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_2_event/list.htm" onClick="selectSubModule('10');">事件分类、分级管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_2_event/listSearch.htm" onClick="selectSubModule('11');">事件查询统计</a></td>
	      </tr>
		  <!--
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="basedata/place/list.htm" onClick="selectSubModule('6');">监测场所管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="basedata/area/list.htm" onClick="selectSubModule('8');">地理信息管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="basedata/sensortype/list.htm">传感器类别管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="basedata/sensor/list.htm">传感器节点管理</a><a href="module/module_list.jsp"></a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="basedata/equipment/list.htm">采集设备管理</a><a href="bmgl/bmglyg_list.jsp" onClick="selectSubModule('9');"></a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="basedata/equipmentlog/list.htm" onClick="selectSubModule('9');">采集日志管理</a><a href="bmgl/yhma.jsp" onClick="selectSubModule('10');"></a></td>
	      </tr>-->
	    </table>
    </div>
    
    <div id="menu3" class="hide">
    	<table width="95%"  border="0">
	      <tr>
	        <td class="lefta">应急预案管理</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr><td class="leftb"><a href="../eps/plaQuery.ac">基本信息管理</a></td> </tr>
	     <!--  <tr><td class="leftb"><a href="page_3_plan/browse.html">预案管理</a></td> </tr> -->
	      <tr><td class="leftb"><a href="javascript:subModule(3);">预案基础设置</a></td> </tr>
		      <tr><td class="subMod3"><a href="../eps/eventTypeQuery.ac">事件类型管理</a></td> </tr>
		      <tr><td class="subMod3"><a href="page_3_plan/reviewList.html">预案分级管理</a></td> </tr>
		      <tr><td class="subMod3"><a href="page_3_plan/filingList.html">预案报备</a></td> </tr>
		      <tr><td class="subMod3"><a href="page_3_plan/trainList.html">预案培训</a></td> </tr>
		      <tr><td class="subMod3"><a href="page_3_plan/manoeuvreList.html">预案演练</a></td> </tr>
	      <tr> <td class="leftb"><a href="page_3_plan/publish.html">预案发布</a></td> </tr>
	    <!--  <tr><td class="leftb"><a href="page_3_plan/type.html">预案事件类型关联</a></td> </tr> -->
	      <tr><td class="leftb"><a href="page_3_plan/jurisdiction.html">预案权限控制</a></td> </tr>
	      <tr><td class="leftb"><a href="page_3_plan/planTotalCounty.html">预案统计分析</a></td> </tr>
	      
	      <!-- <tr>
	        <td class="leftb"><a href="collect/data/list.htm" onClick="selectSubModule('6');">传感器数据管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="collect/jiankong/list.htm" onClick="selectSubModule('8');">传感器监控</a></td>
	      </tr> -->
	    </table>
    </div>
    
    <div id="menu4" class="hide">
    	<table width="95%"  border="0">
	      <tr>
	        <td class="lefta">应急资源管理</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	
	      <tr>
	        <td class="leftb"><a href="page_4_resource/resourceManage/expert/zybz_zjxx_zhcx_init.html" onClick="selectSubModule('8');">应急专家管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_4_resource/resourceManage/rescueSupplies/rescueSupplies_search.htm" onClick="selectSubModule('8');">应急物资管理</a></td>
	      </tr>
		  <!--
	      <tr>
	        <td class="leftb"><a href="page_4_resource/resourceManage/rescueSupplies/warehouse_search.htm" onClick="selectSubModule('8');">应急物资储备库管理</a></td>
	      </tr>
		  -->
	      <tr>
	        <td class="leftb"><a href="page_4_resource/resourceManage/communication_resource/zybz_txzy_txzycx_init.htm">应急通信资源管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_4_resource/resourceManage/medicalResources/medicalInstitutions_search.htm">应急医疗资源管理</a></td>
	      </tr>
		  <tr>
	        <td class="leftb"><a href="page_4_resource/resourceManage/transport/zybz_yswz_yswzcx_init.htm">应急运输资源管理</a></td>
	      </tr>
		  <tr>
	        <td class="leftb"><a href="page_4_resource/resourceManage/rescue_power/zybz_jyll_jyllcx_init.htm">应急救援力量管理</a></td>
	      </tr>
		  <tr>
	        <td class="leftb"><a href="${ctx}/resource/emrgShltrQuery.ac">应急避难场所管理</a></td>
	      </tr>
		  <!--
		  <tr>
	        <td class="leftb"><a href="page_4_resource/zhcx/zhcx_init.html">综合查询</a></td>
	      </tr>
		  -->
		  <tr>
	        <td class="leftb"><a href="page_4_resource/resourceTypesManage/resourceTypes_view.html">应急资源类型维护</a></td>
	      </tr>
	    </table>
    </div>
    
    <div id="menu5" class="hide">
    	<table width="98%" border="0">
			<tr>
				<td class="lefta">
					应急演练管理
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					

				</td>
			</tr>


			<!-- 	<tr>
				<td class="leftb">
					<a href="page_5_drill/drillingPlan.htm"
						onClick="selectSubModule('8');">演练计划制定</a>
				</td>
			</tr>
			<tr>
				<td class="leftb">
					<a href="page_5_drill/drillingProject.htm"
						onClick="selectSubModule('8');">演练场景构建</a>
				</td>
			</tr>
			<tr>
				<td class="leftb">
					<a href="page_5_drill/drillingEvent.htm"
						onClick="selectSubModule('8');">演练事件</a>
				</td>
			</tr>
			<tr>
				<td class="leftb">
					<a href="page_5_drill/drillingProcesses.htm"
						onClick="selectSubModule('6');">演练计划实施</a>
				</td>
			</tr> 
			<tr>
				<td class="leftb">
					<a href="#" id="pgzj">计划总结</a>
				</td>
			</tr>

			<tr id="pgzj_id1" style="display: none">
				<td class="leftb">
					<a href="page_5_drill/drillingPlanSummary.htm"
						target="mainFrame">&nbsp;&nbsp;计划总结</a>&nbsp;&nbsp;
				</td>
			<tr id="pgzj_id2" style="display: none">
				<td class="leftb">
					<a href="page_5_drill/drillingProjectSummary.htm"
						target="mainFrame">&nbsp;&nbsp;项目总结&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</a>
				</td>
			</tr>
			-->

			<tr>
				<td class="leftb">
					<a href="page_5_drill/drillingPlanManager.htm"
						onClick="selectSubModule('8');">演练计划管理</a>
				</td>
			</tr>
			<!--<tr>
				<td class="leftb">
					<a href="page_5_drill/rehearsalScenes.htm"
						onClick="selectSubModule('8');">演练场景</a>
				</td>
			</tr>-->
			<tr>
				<td class="leftb">
					<a href="page_5_drill/start.htm"
						onClick="selectSubModule('8');">演练执行</a>
				</td>
			</tr>
			<tr>
				<td class="leftb">
					<a href="page_5_drill/drillingPlan.htm"
						onClick="selectSubModule('8');">演练过程记录与回放</a>
				</td>
			</tr>
			<tr>
				<td class="leftb">
					<a href="#" id="pggl">演练评估</a>
				</td>
			</tr>

			<tr id="pggl_id1" style="display: none">
				<td class="leftb">
					<a href="page_5_drill/appraiseResultStatistic.htm"
						target="mainFrame">&nbsp;&nbsp;评估结果统计</a>&nbsp;&nbsp;
				</td>
			<tr id="pggl_id2" style="display: none">
				<td class="leftb">
					<a href="page_5_drill/appraiseResultFile.htm">&nbsp;&nbsp;评估结果归档&nbsp;
					</a>
				</td>
			</tr>

		</table>
    </div>
    
    
    
    <div id="menu6" class="hide">
    	<table width="95%"  border="0">
	      <tr>
	        <td class="lefta">危险源管理</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr><td class="leftb"><a href="page_7_hazard/submission.html">危险源信息报送</a></td> </tr>
	      <tr><td class="leftb"><a href="page_7_hazard/dangerAudit.html">危险源信息审核</a></td> </tr>
	      <tr><td class="leftb"><a href="page_7_hazard/dangerNoAudit.html">危险源信息审核退回</a></td> </tr>
	      <tr><td class="leftb"><a href="page_7_hazard/dangerPush.html">危险源信息发布</a></td> </tr>
	      <tr><td class="leftb"><a href="page_7_hazard/dangerTotal.html">危险源统计报表</a></td> </tr>
	      
	    </table>
    </div>
    
    <div id="menu7" class="hide">
    	<table width="95%"  border="0">
	      <tr>
	        <td class="lefta">风险隐患</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr><td class="leftb"><a href="page_8_risk/rectifyList.html">未整顿</a></td> </tr>
	      <tr><td class="leftb"><a href="page_8_risk/noRectifyList.html">已整顿</a></td> </tr>
	      <tr><td class="leftb"><a href="page_8_risk/overdueNoRectifyList.html">过期未整顿</a></td> </tr>
	      
	    </table>
    </div>
    
    <div id="menu8" class="hide">
    	<table width="95%"  border="0">
	      <tr>
	        <td class="lefta">系统管理平台</td>
	      </tr>
	      <tr><td>&nbsp;</td></tr>
	      <tr><td class="leftb"><a href="javascript:void(0);" onClick="subModule('1');">系统管理</a></td></tr>
	      	  <tr><td class="subMod1"><a href="page_6_system/a_orgamization/list.htm">组织结构管理</a></td></tr>
	          <tr><td class="subMod1"><a href="page_6_system/a_dept/list.htm">部门管理</a></td></tr>
	          <tr><td class="subMod1"><a href="page_6_system/a_module/list.htm">系统功能管理</a></td></tr>
	          <tr><td class="subMod1"><a href="page_6_system/a_user/editSelfInfo.htm">系统配置</a></td></tr>
	      
	       	
	      <tr><td class="leftb"><a href="javascript:void(0);" onClick="subModule('2');">权限管理</a></td></tr>
	      	  <tr><td class="subMod2"><a href="page_6_system/a_user/list.htm">用户管理</a></td></tr>
	          <tr><td class="subMod2"><a href="page_6_system/a_role/list.htm">角色管理</a></td></tr>
	      
	      <tr><td class="leftb"><a href="javascript:void(0);" onClick="subModule('3');">数据维护</a></td></tr>
	          <tr><td class="subMod3"><a href="page_6_system/a_dataBackup/backupSet.htm">数据备份设置</a></td></tr>
	          <tr><td class="subMod3"><a href="page_6_system/a_dataBackup/list.htm">数据备份</a></td></tr>
	          <tr><td class="subMod3"><a href="page_6_system/a_dataBackup/listRecovery.htm">数据恢复</a></td></tr>
	       
	      <tr><td class="leftb"><a href="javascript:void(0);" onClick="subModule('4');">运行管理</a></td></tr>
	          <tr><td class="subMod4"><a href="page_6_system/a_log/runControl.htm">运行监控</a></td></tr>
	          <tr><td class="subMod4"><a href="page_6_system/a_log/list.htm">日志管理</a></td></tr>
	      
	      
	    
	      
	      
	     <!--  <tr>
	        <td class="leftb"><a href="page_6_system/user/user_list.htm" onClick="selectSubModule('6');">用户管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/role/role_list.htm" onClick="selectSubModule('8');">角色管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/orgamization/list.htm">组织结构管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/catalog/list.htm">目录管理</a><a href="module/module_list.jsp"></a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/module/list.htm">模块管理</a><a href="bmgl/bmglyg_list.jsp" onClick="selectSubModule('9');"></a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/function/list.htm">模块权限管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/dictionary/list.htm" onClick="selectSubModule('9');">数据字典管理</a><a href="bmgl/yhma.jsp" onClick="selectSubModule('10');"></a></td>
	      </tr>
	      
	      <tr>
	        <td class="leftb"><a href="page_6_system/trade/list.htm">行业管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/system/list.htm">子系统管理</a></td>
	      </tr>
	      <tr>
	        <td class="leftb"><a href="page_6_system/log/list.htm">操作日志管理</a></td>
	      </tr>
		  <tr>
	        <td class="leftb"><a href="page_6_system/log/loginlist.htm" onClick="selectSubModule('10');">登陆日志管理</a><a href="expressinform/affiche_add.jsp"></a></td>
	      </tr>
		  <tr>
	        <td class="leftb"><a href="page_6_system/user/editpw.htm" onClick="selectSubModule('10');">修改登陆密码</a><a href="expressinform/affiche_list.jsp"></a></td>
	      </tr>  -->
	    </table>
    </div>
    
    </td>
  </tr>
</table>
</body>

</html>
