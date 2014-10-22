$(function(){
	$("#checkAll").click(function() {
		if($(this).attr("checked")){
			$("input[name='checkedId']").attr("checked",true);
			$(".subcheck").each(function(){
				alert($(this).attr("disabled"));
				if($(this).attr("disabled")=='disabled'){
					$(this).attr("checked",false);
				}
			});
		}else{
			$("input[name='checkedId']").attr("checked",false);
		}
	});
	$("#deleteBtn").click(function(){
		var ids = new Array(); 
		$("input[name='checkedId']:checked").each(function(){
			ids.push($(this).val()); 
		});
		if(ids.length==0){
			parent.mac.alert("请选择要删除的记录!");
			return;
		}
		deleteOnClick(ids);
		
	});
	$("#queryBtn").click(function(){
		$("#currentPage").val("0");
		$("form")[0].submit();
	});
});
function deleteOnClick(ids){
	mac.confirm('<p>确认要删除已选中的记录吗?</p>', function(){
		infoToDelete(ids.toString());
	}, null);
}