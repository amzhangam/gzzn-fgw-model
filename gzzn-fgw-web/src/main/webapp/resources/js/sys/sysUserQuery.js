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
			$("input[type='text'],input[type='hidden']").each(function() {
		    	this.value = "";
		    });
		});
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
	});
	
	/*添加或编辑链接*/
	function editOrAddUrl(val){
	   var url="./sysUserNewOrEdits.ac?";
	    if(val!=""){
	    	url+="status=edit&userId="+val;
	    }else{
	    	url+="status=add";
	    }
		location.href=url;
	}
	
	/*添加或编辑链接*/
	function shenheUrl(val){
		var url="./userVerify.ac?";
		if(val!=""){
			url+="userId="+val;
		}
		location.href=url;
	}
	
	/*查看详情*/
	function detail(id){
		window.location.href="./epsEventTypeDetail.ac?planid="+id;
	}
	
	/*删除*/
	function del(id){
		delData(id,"./sysUserDelete.ac");
	}
	
	/**
	 * Ztree数据
	 */
	$(function(){
		var cxt=$("#cxt").val();//得到工程名
		/*var userType = $("#yhlxId").val();*/
		var width = 210,height=200;//弹出树形选择框的宽、高
		
		var orgDatas = getJsonDatas(cxt+"/com/getSysOrgJson.json","");
		$("#organizationName").autocomplete(orgDatas, inputDefaultOptions);
		//inputAutoComplete(orgDatas, "organizationName", "organizationId", false);
		/**$("#organizationName").focus(function(){
			initZtree("organization", orgDatas, width, height);
		});*/
		var deptDatas = getJsonDatas(cxt+"/com/getSysDeptJson.json","");
		$("#deptName").focus(function(){
			initZtree("dept",deptDatas, width, height);
		});
		var yhlxDatas=getDatas(cxt+"/sys/getUserTypeTreeJson.json");
		$("#yhlxName").focus(function(){
			initZtree("yhlx",yhlxDatas, width, height);
		});
		var yhztDatas=getDatas(cxt+"/sys/getUseStatusTreeJson.json");
		$("#yhztName").focus(function(){
			initZtree("yhzt",yhztDatas, width, height);
		});
		
		/**var dutyDatas=getDatas(cxt+"/sys/duty/getSysDutyTreeJson.json");
		$("#dutyName").focus(function(){
			initZtree("duty",dutyDatas,150,200);
		});
		var jslxDatas=getDatas(cxt+"/sys/getRoleTypeTreeJson.json");
		$("#jslxName").focus(function(){
			initZtree("jslx",jslxDatas,150,200);
		});*/
	});
	
	function grentRole(id){
		window.location.href="./grantUserRole.ac?userId="+id;
	}
	
	function modifyPass(id){
		window.location.href="./adminModifyPassEdit.ac?userId="+id;
	}