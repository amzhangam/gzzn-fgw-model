
/**
 * JS描述：应急预案通用
 */
$(function(){
	$("#backBtn").click(function(){
		window.history.go(-1);
	});
});

/**
 * 预案基本信息状态检测，session中是否有值
 */
function yaStatusCheck(data){
	if(data.status!=undefined){
		  if(data.status=="add"){
			  parent.mac.alert("请先添加预案信息！");
		  }else{
			  parent.mac.alert("未选择预案信息！");
		  }
		  //window.parent.location.reload(true);
	  }else{
		  parent.mac.alert(data.info);
	  }
}

/**
 * 对话窗口的弹出
 * @param {} url
 * @param {} dWidth
 * @param {} dHeight
 */
function openDialog(url,dWidth,dHeight){
 	var result=window.showModalDialog(url,window,
		"status=no;dialogWidth="+dWidth+"px;dialogHeight="+ dHeight +"px;menu=no;scroll=no;center=yes;edge=raise");
	return result;
}

/*单位集合*/
function unitData(){
	var unitArr=[{"unitName":"个"},{"unitName":"批"},{"unitName":"件"}];
	return unitArr;
}

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

function add(formObj,addUrl,queryUrl){
	var options = {
			url:url,
			type : "POST",
			dataType : "json",
			success:function(data){
				  if(data.success){
					  parent.mac.alert(data.info);
				  	backQuePage(queryUrl);
				  }else{
					  parent.mac.alert("保存失败!");
				  }
			}  
		};
	formObj.ajaxSubmit(options);
}

/**
 * 文件的下载
 * @param fileName
 * @param fileStatus
 * @return
 */
function fileDownload(fileName,fileStatus){
	$.ajax({
    	type:"POST",
    	url:'./fileExist.json',
    	data:{
			fileName:fileName,
			fileStatus:fileStatus
		},
    	success:function(data){
    		if(data.success){
    			window.location.href="./fileDownload.json?fileName="+fileName+"&fileStatus="+fileStatus;
    		}else{
    			alert(data.info);
    		}
    	},
    	error:function() { 
    		alert("系统响应异常！");
		}, 
    	dataType:'json'
	});
}
