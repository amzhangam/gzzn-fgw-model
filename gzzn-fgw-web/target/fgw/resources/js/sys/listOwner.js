/**
 * JS描述：预案等级查询
 */

$(function() {
		showMenu(6, 61);
		$("#checkAll").click(function() {
			var tag = $(this).attr("checked");
			if (tag) {
				$(".subcheck").attr("checked", "true");
			} else {
				$(".subcheck").removeAttr("checked");
			}
		});
		$("#clearCon").click(function(){
			/**$("#xqName").val("");
			$("#dwxzName").val("");
			$("#dwztName").val("");
			$("#xqId").val("");
			$("#dwxzId").val("");
			$("#dwztId").val("");*/
			
			var cxt=$("#cxt").val();//得到工程名
			$("input[type='text'],input[type='hidden']").each(function() {
		    	this.value = "";
		    });
			$("#cxt").val(cxt);
		});
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("./deleteOwner.ac?id=");
	});
	
	/*添加业主*/
	function editOrAddUrl(val){
	   var url="./addOwner.ac?";
	    if(val!=""){
	    	url+="status=edit&organizationId="+val;
	    }else{
	    	url+="status=add";
	    }
		location.href=url;
	}
	
	/*删除*/
	function del(id){
		delData(id,"./deleteOwner.ac");
	}
	
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
		var unitStatusDatas=getDatas("./getUnitStatusJson.json");
		$("#dwztName").focus(function(){
			initZtree("dwzt",unitStatusDatas,150,200);
		});
	});
	
	function grentRole(id){
		window.location.href="./grantUserRole.ac?userId="+id;
	}
	
	function modifyPass(id){
		window.location.href="./userModifyPassEdit.ac?userId="+id;
	}