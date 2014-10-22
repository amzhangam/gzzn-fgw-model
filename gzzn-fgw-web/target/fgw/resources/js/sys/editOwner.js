/**
 * JS描述：时间类型操作
 */

$(function(){
	var validator = $("#editForm").validate({
		event:"blur",
		onkeyup:false,
		 rules: {
             "obj.organizationName":{   
                 required: true,
                 byteMaxLength:50
             },
             "obj.workunitsregistercode":{   
                 required: true,
                 byteMaxLength:100
             },
             "obj.workunitslinkman":{   
                 required: true,
                 byteMaxLength:100
             },
             "obj.workunitslinkmantel":{   
            	 Mobile: true,
                 byteMaxLength:11
             }, 
             "obj.workunitsperson":{   
            	 required:true,
                 byteMaxLength:100
             },
             "obj.workunitsaddress":{   
            	 required:true,
                 byteMaxLength:200
             },
             "sysXq.xqmc":{   
            	 required:true
             },
             "workunitsquality":{   
            	 required:true
             }
	      },
	      messages:{
	    	  "mobile":{
	    		  Mobile:"请输入一个合法的传真"
	    	  }
	      }
	});
});

//function addOrEdit(){
//	if($("#form1").valid()){
//		var options = {
//			url:'./saveOwner.json',
//			type : "POST",
//			dataType : "json",
//			success:function(data){
//				  if(data.success){
//					 mac.alert(data.info);
//					 backQuePage("./listOwner.ac");
//				  }else{
//				  	mac.alert("保存失败!");
//				  }
//			}  
//		};
//		$("#form1").ajaxSubmit(options);
////		$("#form1").submit();
//	}
//	return false;
//}

/**
 * Ztree数据
 */
$(function(){
	var cxt=$("#cxt").val();//得到工程名
	var areaDatas=getDatas("./getAreaJson.json");
	$("#xqName").focus(function(){
		initZtree("xq",areaDatas,150,200);
	});
	var unitNatureDatas=getDatas("./getUnitNatureJson.json");
	$("#dwxzName").focus(function(){
		initZtree("dwxz",unitNatureDatas,150,200);
	});
});

