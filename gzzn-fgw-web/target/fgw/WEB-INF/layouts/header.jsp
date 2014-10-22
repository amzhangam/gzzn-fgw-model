<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String ssologin = (String)request.getSession().getAttribute("ssologin");

	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>${configObj["system.name.main"]}</title>
<script type="text/javascript">
<!--
	var pw = 30;

	$(function(){
		$(window).bind("resize",function(){
			//自动调整页面宽度
			$("#mainContainer").width($(this).width()-$("#leftDiv").width()-pw);
		});
		//自动调整页面宽度
		$("#mainContainer").width($(this).width()-$("#leftDiv").width()-pw);
		
		$('.dlgDemoBtn').click(function(){
			var el = $(this), act = el.attr('action');
			switch(act){
				case 'basic':
					$('<div></div>').mac('dialog',{
						title: 'Hello!',
						model: true,
						autoOpen: true,
						width: 200,
						draggable: true,
						el: '<p>Hello world!</p><p>I\'m MagicDialog!</p>'
					});
					break;
				case 'extended':
					mac.opendlg({
						title: 'Hello!',
						message: 'Press {0} can close me.',
						params: ['<b>ESC/Enter</b>'],
						buttons: [{
							text: 'OK',
							click: function(d){
								d.close();
							}
						}]
					});
					break;
				case 'wait': 
					var h = mac.wait('Count 3 sec to close.');
					window.setTimeout(function(){
						h.close();
					}, 3000);
					break;
				case 'alert': 
					mac.alert('Hello!');
					break;
				case 'confirm': 
					mac.confirm('<p>Are you sure?</p>', function(){
						alert('You clicked yes!');
					}, null);
					break;
				case 'customize': 
					mac.confirm('<p>Are you sure?</p>', null, null, [{
						text: 'Close',
						click: function(d){
							d.close();
						}
					},{
						text: 'Yes, I\'m sure?',
						click: function(d){
							d.close();
						}
					}]);
					break;
			}
		});
	});
	
	function loginOut() {
		mac.confirm("<p>确定退出系统？</p>",function(){
			$.post("${ctx}/login/logout.json",function(data){
					window.location.href="${ctx}/";
					//window.location.href="<%=ssologin%>?spath=<%=basePath%>";
			});
		},null);
	}
	
	function hiddenTop(){
		var obj = $("#topImg");
		if(obj.parent().attr("title").search("显示") == -1){
			obj.attr("src","${ctx}/resources/images/show_top.gif");
			obj.parent().attr("title","显示上帧");
			$("#topDiv").css("display","none");
		}else{
			obj.attr("src","${ctx}/resources/images/hide_top.gif");
			obj.parent().attr("title","隐藏上帧");
			$("#topDiv").css("display","");
		}
	}
	
	function hiddenLeft(){
		var obj = $("#leftImg");
		if(obj.parent().attr("title").search("显示") == -1){
			obj.attr("src","${ctx}/resources/images/show_left.gif");
			obj.parent().attr("title","显示左帧");
			$("#leftDiv").css("display","none");
			//自动调整页面宽度
			$("#mainContainer").width($(document).width()-pw);
		}else{
			obj.attr("src","${ctx}/resources/images/hide_left.gif");
			obj.parent().attr("title","隐藏左帧");
			$("#leftDiv").css("display","");
			//自动调整页面宽度
			$("#mainContainer").width($(document).width()-$("#leftDiv").width()-pw);
		}
	}
	
	function openDialog(url,cfg){
		return $('<div></div>').mac('dialog',{
			title: cfg.title || '弹出窗口',
			model: true,
			autoOpen: true,
			width: cfg.width || $(document).width()-200,
			height: cfg.height || document.documentElement.clientHeight-20,
			draggable: true,
			el:'<div><iframe src="'+url+'" frameborder="0"  border="0" marginwidth="0" marginheight="0" scrolling="auto" style="width:100%;height:100%;min-height:700px;"></iframe></div>'
		});
	}
	
 -->
</script>
</head>

<body>
	<div id="topDiv">
		<img src="${ctx}/resources/images/bg_logo.png" width="100%" height="100%"/>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr class="search">
			<td width="0.5%">&nbsp;&nbsp;</td>
			<td width="2%" height="30"><a
				href="javascript:hiddenLeft();" title="隐藏左帧"><img id="leftImg"
					src="${ctx}/resources/images/hide_left.gif" border="0" alt="隐藏左帧"></a>
			</td>
			<td width="46%">
				欢迎您：<font color="red" size="5">${sessionLoginUser.userName}</font>
				<font color="blue">【${sessionLoginUser.sysOrganization!=null?sessionLoginUser.sysOrganization.organizationName:''}&nbsp;&nbsp;${sessionLoginUser.sysDept!=null?(sessionLoginUser.sysDept.deptname):''}】</font>&nbsp;
			</td>

			<td width="45%" style="text-align: right">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >

								<tr height="21" valign="middle">
									<td align="right" >
											<a href="${ctx}/index/main.ac"><img
												src="${ctx}/resources/images/msys_home.gif" width="57"
												height="21" border="0"></a> 
												<!--a href="main.htm"><img
												src="${ctx}/resources/images/msys_help.gif" width="57"
												height="21" border="0"></a--> 
												<a href="${ctx}/project/pjbaseinfo/downloadfile.ac"><img src="${ctx}/resources/images/msys_download.gif" width="82" height="21" border="0"</a>
												<a href="javascript:loginOut();"><img
												src="${ctx}/resources/images/msys_out.gif" width="57"
												height="21" border="0"></a>
									</td>
								</tr>
							
				</table>
			</td>
			<td width="3%">&nbsp;&nbsp;</td>
			<td width="3%"><a href="javascript:hiddenTop();" title="隐藏上帧"> <img id="topImg"
					src="${ctx}/resources/images/hide_top.gif" border="0" alt="隐藏上帧"></a>
			</td>
		</tr>
	</table>
</body>
</html>
