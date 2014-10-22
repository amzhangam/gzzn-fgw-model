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
			$("#projectTypeName").val("");
			$("#pjstatusName").val("");
			$("#auditdeptName").val("");
			$("#projectTypeId").val("");
			$("#pjstatusId").val("");
			$("#auditdeptId").val("");
			$("#zjxzId").val("");
			$("#zjxzName").val("");
		});
		$("#queryBtn").click(function(){
			var str = "";
			//判断项目总投资查询输入值的合法性：DECIMAL(18,2)
			//var reg = new RegExp("^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
			var reg = new RegExp("^([1-9]{1}[0-9]{0,15}|0|0\.{1}[0-9]{0,2}|[1-9]{1}[0-9]{0,15}\.{1}[0-9]{0,2})$");
			$(".moneyText").each(function(index) {
				if($(this).val()!="" && !reg.test($(this).val())){
					 str += "累计完成投资、项目总投资(万元)、投资计划建议(万元)中的输入条件，必须为数值且小数点后可保留两位小数！";//必须符合金额格式
					 $(this).focus();//将光标订到指定位置
					 return false;
				}
			});
			$("#checkFormErr").html(str);
			$("#currentPage").val("0");
			if(str==""){
				//$("#report").submit();
				$("form")[0].submit();
			}
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		//delBtnClick("./delete.ac?id=");
	});
	
	/*添加业主*/
	function editOrAddUrl(val){
	   var url="./edit.ac?";
	    if(val!=""){
	    	url+="status=edit&projectId="+val;
	    }else{
	    	url+="status=add";
	    }
		location.href=url;
	}
	
	/*删除*/
	function del(id){
		delData(id,"./delete.ac");
	}
	
	/**
	 * Ztree数据
	 */
	$(function(){
		var xmlxDatas=getDatas("./getXmlxTreeJson.json");
		$("#xmlxName").focus(function(){
			initZtree("xmlx",xmlxDatas,150,200);
		});
		var hylbDatas=getDatas("./getHylbTreeJson.json");
		$("#hylbName").focus(function(){
			initZtree("hylb",hylbDatas,150,200);
		});
		var zjxzDatas=getDatas("./getZjxzTreeJson.json");
		$("#zjxzName").focus(function(){
			initZtree("zjxz",zjxzDatas,150,200);
		});
		var pjstatusDatas=getDatas("./getProjectStatusJson.json");
		$("#pjstatusName").focus(function(){
			initZtree("pjstatus",pjstatusDatas,200,320);
		});
		var auditdeptDatas=getDatas("./getSysDeptJson.json");
		$("#nextauditdeptName").focus(function(){
			initZtree("nextauditdept",auditdeptDatas,150,200);
		});
	});
	
