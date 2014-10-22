<%@ page contentType="text/html;charset=UTF-8"%>

<div style="text-align: center;" class="form-actions">
	总记录[${pagePjInfo.count}]条 ${pagePjInfo.current+1}/${pagePjInfo.pages}页
	<c:if test="${pagePjInfo.current == 0}">
		<a href="javascript:changePage(0,'${pagePjInfo.size}')">首页</a>
	</c:if>
	<c:if test="${pagePjInfo.current > 0}">
		<a href="javascript:changePage(0,'${pagePjInfo.size}')">首页</a>
		<a href="javascript:changePage('${pagePjInfo.current-1}','${pagePjInfo.size}')">上一页</a>
	</c:if>
	<c:if test="${pagePjInfo.next < pagePjInfo.pages}">
		<a href="javascript:changePage('${pagePjInfo.current+1}','${pagePjInfo.size}')">下一页</a>
		<a href="javascript:changePage('${pagePjInfo.pages-1}','${pagePjInfo.size}')">尾页</a>
	</c:if>
	<c:if test="${pagePjInfo.current+1 == pagePjInfo.pages}">
		<a href="javascript:changePage('${pagePjInfo.pages-1}','${pagePjInfo.size}')">尾页</a>
	</c:if>
	转到第 <select style="width: 50px;" id="gotoselect" size="1" onchange="changePage(this.value-1,'${pagePjInfo.size}');">
		<c:forEach var="p" begin="1" end="${pagePjInfo.pages}" step="1">
			<option value="${p}">${p}</option>
		</c:forEach>
	</select>页 &nbsp;&nbsp;&nbsp;&nbsp; 
	每页显示记录数 <select style="width: 50px;" id="pagesize" onchange="changePage(0,this.value);">
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="50">50</option>
	</select>条
	
	<script type="text/javascript">
		$("#gotoselect").val("${pagePjInfo.current+1}");
		$("#pagesize").val("${pagePjInfo.size}");
	</script>
	<input type="hidden" id="currentPage" name="pagePjInfo.current"
		value="${pagePjInfo.current}" class='page'/> 
	<input type="hidden" id="pageSize" class='page'
		name="pagePjInfo.size" value="${pagePjInfo.size}" />
</div>
