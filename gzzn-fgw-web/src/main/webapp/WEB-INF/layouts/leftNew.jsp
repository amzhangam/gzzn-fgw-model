<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<% String flag=(String)request.getAttribute("flag"); %>
<% String num=(String)request.getAttribute("num"); %>
<%
	SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
%>
<html>
<link href="${ctx}/resources/css/leftNew.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function(){
		//给子菜单定义样式
		/**$("dl[id^=menuImg] dd").css({
			"display":"none"
		});*/
		
		/**$("dd[class^=subMod]").css({
			"width":"135px",
			"padding-left":"35px",
			"background-position":"25px center",
			"display":"none"
		});*/
		
		$(".leftbg").height($("#mainContainer").height());
	});
	
	function showMenu(menu,subMenu){
		selectMenu(menu);
		subModule(subMenu);
	}

	function selectMenu(order){
		$("dl[id^=menuImg] dd,dd[class^=subMod]").css({
			"display":"none"
		});
		$("#menuImg"+ order +" dd").css("display","block");
		$("dd[class^=subMod]").css({
			"display":"none"
		});
	}
	
	function subModule(num){
		var className = "subMod"+ num;
		if($("."+className).css("display")=="none"){
			$("."+className).css("display","block");
		}else{
			$("."+className).css("display","none");
		}
	}
	

</script>
</head>
<body>
<!--左边-->
<div class="leftbg">
<!--  <div class="leftbt"></div> -->
  <div class="leftgh">
  
   <dl id="menuImg2">
	    <dt onclick="selectMenu(2);"><img src="${ctx}/resources/images/leftNew/xmksb.png" />项目库申报</dt>
	   <c:if test="${sessionLoginUser!=null && sessionLoginUser.userType!=null && sessionLoginUser.userType!=5}">
	   		<dd><a href="${ctx}/project/pjbaseinfo/edit.ac?id=0">项目申报</a></dd>
	   </c:if>
		<dd><a href="${ctx}/project/pjbaseinfo/list.ac">项目信息管理</a></dd>
		<c:if test="${sessionLoginUser!=null && sessionLoginUser.userType!=null && sessionLoginUser.userType!=5}">
			<c:if test="${moduleMap['XMSB_XM_DCLXMGL']}">
				 <dd><a href="${ctx}/project/dclxm/list.ac">待处理项目管理</a></dd>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_ZGDWSHTG']}">
				 <dd><a href="${ctx}/project/zgdwshtgxm/list.ac">主管单位审核通过项目</a></dd>
			</c:if>
			<c:if test="${moduleMap['XMSB_XM_GCSSHTG']}">
				 <dd><a href="${ctx}/project/csshtgxm/list.ac">各处室审核通过项目</a></dd>
			</c:if>
		</c:if>	
		<c:if test="${moduleMap['XMSB_XM_ZDXMGL']}">
			<dd><a href="${ctx}/project/zdxm/list.ac">已通过审核项目</a></dd>
			<dd><a href="${ctx}/project/zdxm/nopasslist.ac">审核不通过项目</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_YSDWXMHZ']}">
			<dd><a href="${ctx}/project/ysdwxm/list.ac">预算单位项目汇总</a></dd>
		</c:if>
		<%-- <dd> <a href="${ctx}/project/zdxm/list.ac">项目图片长廊</a></dd>
		<c:if test="${moduleMap['XMSB_XM_NDJHZGGL']}"><dd> <a href="${ctx}/project/zdxm/list.ac">项目年度管理</a></dd> </c:if> --%>
		<c:if test="${moduleMap['XMSB_XM_XMJHBBGL']}">
			<dd><a href="${ctx}/report/zbxmk.ac">预备项目库</a></dd>
			<dd><a href="${ctx}/report/zsxmk.ac">正式项目库</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_XMZTGL']}">
			<dd><a href="${ctx}/project/xmyb/list.ac">项目月报管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_NDJHZGGL']}">
			<dd><a href="${ctx}/project/pjplanyear/list.ac">年度计划终稿管理</a></dd>
		</c:if>
	</dl>
	
	
	<dl id="menuImg12">
		<c:if test="${moduleMap['XMSB_XM_QXXMQK']}">
	   		<dt onclick="selectMenu(12);"><img src="${ctx}/resources/images/leftNew/xmxxbs.png" />项目信息报送</dt>
	    </c:if>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK_DOWN']}">
			<dd><a href="${ctx}/buildImpl/downTemp.ac">下载模板</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK_IMPORT']}">
			<dd><a href="${ctx}/buildImpl/dataImpl.ac">导入数据</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK_TBLOG']}">
			<dd><a href="${ctx}/buildImpl/logList.ac">填报日志</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK_QUERY']}">
			<dd><a href="${ctx}/buildImpl/list.ac">上报项目查询</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK_REPORTMGT']}">
			<dd><a href="${ctx}/buildImpl/reportList.ac">填报项目管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK_STATISTICS']}">
			<dd><a href="${ctx}/buildImpl/sumStat.ac">按产业投资额汇总</a></dd> 
		<%-- <dd><a href="${ctx}/buildImpl/sumStat.ac">上报项目汇总统计</a></dd> --%>
		</c:if>
		<c:if test="${moduleMap['XMSB_XM_QXXMQK_XQSTAT']}">
			<dd><a href="${ctx}/buildImpl/xqStat.ac">按区县汇总统计</a></dd> 
		</c:if>
	</dl>
	
	
	<dl id="menuImg3">
		<c:if test="${moduleMap['XMSB_BB']}">
	    	<dt onclick="selectMenu(3);"><img src="${ctx}/resources/images/leftNew/bbgl.png" />报表管理</dt>
	    </c:if>	
		<c:if test="${moduleMap['XMSB_BB_SBXMJHBB']}">
			<dd><a href="${ctx}/report/sbReport.ac">申报项目计划报表</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_BB_YZZGDWTJXMJHBB']}">
			<dd><a href="${ctx}/report/tjReport.ac">业主/主管单位提交项目计划报表</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_BB_YZZGDWTJXMJHBB']}">
			<dd><a href="${ctx}/report/passReport.ac">审核通过报表</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_BB_YZZGDWTJXMJHBB']}">
			<dd><a href="${ctx}/report/notPassReport.ac">审核不通过报表</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_BB_XMSQHZBB']}">
			<dd><a href="${ctx}/report/sqhzReport.ac" >项目申请汇总报表</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_BB_GCSXMQKTJBB']}">
			<dd><a href="${ctx}/report/gcsxmtjReport.ac" >各处室项目情况统计报表</a></dd>
		</c:if>
	</dl>
	
	
	<dl id="menuImg6">
		 <c:if test="${moduleMap['XMSB_XT']}">
	    	<dt onclick="selectMenu(6);"><img src="${ctx}/resources/images/leftNew/xtgl.png" />系统管理</dt>
	    </c:if>
		<c:if test="${moduleMap['XMSB_XT_YHGL']}">
			<dd><a href="${ctx}/sys/sysUserQuery.ac">用户管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_DWGL']}">
			<dd><a href="${ctx}/sys/organNew/list.ac">单位管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_BMGL']}">
			<dd><a href="${ctx}/sys/dept/list.ac">部门管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_ZCYZSHGL']}">
			<dd><a href="${ctx}/sys/userVerifyQuery.ac">用户审核管理</a></dd>
			<dd><a href="${ctx}/sys/organNew/listOwnerVerify.ac">注册业主审核管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_ZWGL']}">
			<dd><a href="${ctx}/sys/duty/list.ac">职务管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_RZGL'] || moduleMap['XT_RZGL'] }">
			<dd><a href="javascript:void(0);" onClick="subModule('66');">日志管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_RZGL']}">
			<dd class="subMod66"><a href="${ctx}/sys/log/list.ac">用户操作日志</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_RZGL']}">
			<dd class="subMod66"><a href="${ctx}/sys/log/pjList.ac">项目日志</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_JSGL']}">
			<dd><a href="${ctx}/sys/role/list.ac">角色管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_XQGL']}">
			<dd><a href="${ctx}/sys/xq/list.ac">辖区管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_XMLBGL']}">
			<dd><a href="${ctx}/sys/hylb/list.ac">行业分类管理</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_XT_YJMB']}">
			<dd><a href="${ctx}/sys/yjmb/list.ac">意见模板管理</a></dd>
		</c:if>
	</dl>
	
	
	<dl  id="menuImg1">
	    <c:if test="${moduleMap['XMSB_DX']}">
			<dt onclick="selectMenu(1);"><img src="${ctx}/resources/images/leftNew/dxgl.png" />短信管理</dt>
		</c:if>
		<c:if test="${moduleMap['XMSB_DX_DXFS']}"> 
	    	<dd><a href="${ctx}/sys/dx/lxrlist.ac">发送短信</a></dd> 
	    </c:if>
		<c:if test="${moduleMap['XMSB_DX_XMDXFS']}"> 
			<dd><a href="${ctx}/sys/dx/fsxmlist.ac">发送项目短信</a></dd> 
		</c:if>
		<c:if test="${moduleMap['XMSB_DX_DXFS']}"> 
	    	<dd><a href="${ctx}/sys/dx/freesendlist.ac">直接发送短信</a></dd> 
	    </c:if>
		<c:if test="${moduleMap['XMSB_DX_DXCX']}"> 
			<dd><a href="${ctx}/sys/dx/list.ac">已发送短信</a></dd>
		</c:if>
		<c:if test="${moduleMap['XMSB_DX_DXMB'] }">
			<dd><a href="${ctx}/sys/dxmb/list.ac">短信模板</a></dd> 
		</c:if>
	</dl>
	
	
	<dl  id="menuImg7">
		<dt onclick="selectMenu(7);"><img src="${ctx}/resources/images/leftNew/yhxxgl.png" />用户信息管理</dt>
		<!-- 用户信息管理设置子菜单 -->
		<dd><a href="${ctx}/sys/genUserEdit.ac?status=7">用户基本信息</a></dd>
		<!-- 用户信息管理子菜单 -->
		<dd><a href="${ctx}/sys/genUserModPassEdit.ac?status=7">修改密码</a></dd>
   </dl>
 </div>
 
 
</div>
<!--左边-->





</body>

</html>
