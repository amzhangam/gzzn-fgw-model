
/** 
 * 上传附件时，检测附件的类型及附件大小
 * @param inputFilesName  当前上传附件的字段的中文名称
 * @param filesObj  上传附件对象，可上传多个附件
 * @param isNotEmpty 附件不允许为空：true-不允许为空，false-可以为空
 * @param fileMaxSize 一次性上传附件的大小，单位为M
 * @param fileTypes 允许上传的附件类型：格式为数组如：[ ".jpg", ".png" ]
 * 
 * @author lhq
 * @date  2014-06-26
 * @version v1.0
 */
function checkFileSize(inputFilesName, allFilesObj, isNotEmpty, allFilesSize, fileTypes) {
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;//是否为IE浏览器
	var defFileTypes = [ ".jpg", ".png", ".rar", ".txt", ".zip", ".doc",".ppt", ".xls", ".pdf", ".docx", ".xlsx" ];//允许上传文件的类型
	var fileTypes = (fileTypes==undefined||fileTypes==null)?defFileTypes:fileTypes;
	var isNotEmpty = (isNotEmpty==undefined||isNotEmpty==null)?true:isNotEmpty;
	var allFilesSize = 1024*((allFilesSize==undefined||allFilesSize==null)?2:allFilesSize);//全部上传文件的大小，默认2M， 已转换成 KB
	var fileSize = 0;//单个文件大小，单位byte
	var nowFilesSize = 0;//当前累计文件大小，换成 KB
	var filePath = "";//文件的路径
	
	for(var i=0;i<allFilesObj.length;i++){
		filePath = allFilesObj[i].value;
		if(filePath.length>0 && !(/^\s+$/.test(filePath))){
			//1、检测附件的类型是否符合要求
			var isNext = false;
			var fileEnd = filePath.substring(filePath.lastIndexOf("."));//filePath.substring(filePath.indexOf("."));
			if (fileTypes && fileTypes.length > 0) {
				for ( var it = 0; it < fileTypes.length; it++) {
					if (fileTypes[it] == fileEnd) {
						isNext = true;
						break;
					}
				}
			}
			if (!isNext) {
				alert(inputFilesName +"附件【"+ (i+1) +"】：不接受此文件类型！"); //不接受此文件类型
				allFilesObj[i].value = "";
				return false;
			}
			
			//2、检测附件是否存在，及附件的大小是否超出了指定的范围
			if (isIE && !allFilesObj[i].files) {
				//alert("我是IE浏览器！！！");
				 try{
					var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
					if (!fileSystem.FileExists(filePath)) {
						alert(inputFilesName +"附件【"+ (i+1) +"】：附件不存在，请重新输入！"); //附件不存在，请重新输入
						return false;
					}
					var file = fileSystem.GetFile(filePath);
					fileSize = file.Size;
				}catch(e){
	            	alert("浏览器不支持，请设置浏览器 工具 -> Internet选项 -> 安全 -> 自定义级别 -> 找到“其他”中的“将本地文件上载至服务器时包含本地目录路径”！"); 
					return false;
	        	}
			} else {
				fileSize = allFilesObj[i].files[0].size;
				//alert("我是其它的浏览器！！！"+fileSize);
			}
			
			nowFilesSize = nowFilesSize + Math.round(fileSize/1024,2);//转换成 KB
			if (nowFilesSize > allFilesSize) {
				alert(inputFilesName +"附件累计至第【"+ (i+1) +"】个时：附件大小不能大于" + allFilesSize / 1024 + "M！"); //附件大小不能大于
				return false;
			}
			/*if (nowFilesSize <= 0) {
				alert(inputFilesName +"附件【"+ (i+1) +"】：附件大小不能为0M！"); //附件大小不能为0M
				target.value = "";
				return false;
			}*/
		}else if(isNotEmpty){
			alert(inputFilesName +"附件【"+ (i+1) +"】：附件不能为空！");
			return false;
		}
	}
	return true;
}