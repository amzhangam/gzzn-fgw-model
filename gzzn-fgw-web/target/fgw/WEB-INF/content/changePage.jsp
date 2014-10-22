<%@ page contentType="text/html;charset=UTF-8"%>

<div style="text-align: center;" class="form-actions">
	总记录[${page.count}]条 ${page.current+1}/${page.pages}页
	<c:if test="${page.current == 0}">
		<a href="javascript:changePage(0,'${page.size}')">首页</a>
	</c:if>
	<c:if test="${page.current > 0}">
		<a href="javascript:changePage(0,'${page.size}')">首页</a>
		<a href="javascript:changePage('${page.current-1}','${page.size}')">上一页</a>
	</c:if>
	<c:if test="${page.next < page.pages}">
		<a href="javascript:changePage('${page.current+1}','${page.size}')">下一页</a>
		<a href="javascript:changePage('${page.pages-1}','${page.size}')">尾页</a>
	</c:if>
	<c:if test="${page.current+1 == page.pages}">
		<a href="javascript:changePage('${page.pages-1}','${page.size}')">尾页</a>
	</c:if>
	转到第 <select style="width: 50px;" id="gotoselect" size="1" onchange="changePage(this.value-1,'${page.size}');">
		<c:forEach var="p" begin="1" end="${page.pages}" step="1">
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
		$("#gotoselect").val("${page.current+1}");
		$("#pagesize").val("${page.size}");
	</script>
	<input type="hidden" id="currentPage" name="page.current"
		value="${page.current}" class='page'/> 
	<input type="hidden" id="pageSize" class='page'
		name="page.size" value="${page.size}" />
</div>
