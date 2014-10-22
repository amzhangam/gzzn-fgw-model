function getCurrCaseLocateXY(url){
	var parameter = "";
	var dlgFeatures = "dialogWidth:700px;dialogHeight:600px;help:0;resizable:yes;center:yes;location:no;status:no;";	
	var obj=window.showModalDialog(url,parameter,dlgFeatures);	 
	if(null!=obj){
		$("#longitude")[0].value=obj.longitude;
		$("#latitude")[0].value=obj.latitude;
	}  
	return;
}
