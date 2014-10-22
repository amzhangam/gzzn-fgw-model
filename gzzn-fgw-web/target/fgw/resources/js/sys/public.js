/**
 * JS描述：用户通用
 */
$(function(){
	$("#backBtn").click(function(){
		window.history.go(-1);
	});
});

/**
 * 编辑界面返回到查询界面
 * @param url
 */
function backQuePage(url){
	window.location.href=url;
}
/**
 * 删除数据
 * @param id 删除指定的ID
 * @param url 删除指定的url
 */
function delData(id,url){
	var ids="";
	var isDelNum=0;//记录删除的条数
	var alertObj=parent.mac;
	if(alertObj==null){
		alertObj=parent.parent.mac;
	}
	if(id==""){//批量删除
		var id=document.getElementsByName("id");
		 for (var i=0; i<id.length; i++){
	   		if(id[i].checked){
	   			ids+=id[i].value+"@";
	   			isDelNum++;
			}
	   	 }
	   	 if(ids==""){
	   		alertObj.alert("请选择要删除的项！");
	   	 	return;
	   	 }
	   	 ids=ids.substring(0,ids.length-1);
	}else{//单条删除
		ids=id;
		isDelNum++;
	}
	alertObj.confirm("确认删除"+isDelNum +"项？", function(){
		$.ajax({
	    	type:"POST",
	    	url:url,
	    	data:{ids:ids},
	    	success:function(data){
	    		if(data.success){
	    			window.location.reload(true);
	    		}else{
		    		parent.alert(data.info);
	    		}
	    	},
	    	error:function() { 
	    		parent.alert("系统响应异常！");
			}, 
	    	dataType:'json'
		});
	});
	/*if(!confirm("确认删除"+ isDelNum +"项？")){
		return;
	}*/
}