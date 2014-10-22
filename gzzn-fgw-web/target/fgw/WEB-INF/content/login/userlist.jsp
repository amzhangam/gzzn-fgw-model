<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<style type="text/css">
	.viewPort{margin:3px 0 0 3px;text-align:center; height:100%}
</style>
</head>
<body>
<script type="text/javascript">
$(function(){
	var w = $(".viewPort").width()-30;
	$('.view').mac('grid', {
		cols : [{
			field: 'userName', title : '姓名 ', width: w*0.2
		},{
			field: 'sex', title : '性别', width: w*0.3
		},{
			field: 'loginName', title : '登录名', width: w*0.2, sort: true,
			render: function(r, tr){
				return '<div class="money">$'+r.loginName+'</div>';
			}
		},{
			field: 'tel', title : '手机号', width: w*0.3, sort: true,
			render: function(r, tr){
				return '<div class="money">$'+r.tel+'</div>';
			}
		}],
		sortLocally: true,
		loader: {
			url: '${ctx}/login/getUserList.json',
			params: { pageNo: 1, pageSize: 2 },
			autoLoad: true
		},
		afterLoad: function(dd, po){
			//$(".body").height($(".main").height()-$(".head").height());
			//$(".pager").width($(".head").width());
			//$(".ybar").height($(".main").height()-$(".head").height());
			//$(".ybar").css("overflow-y","hidden").css("display","none");

		},
		pagerLength: 10
	});
});
</script>

<div class="viewPort">
	<div class="view grid" ></div>
</div>

</body>

</html>
