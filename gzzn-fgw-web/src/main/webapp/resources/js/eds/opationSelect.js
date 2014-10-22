/** 
 * @功能 select左右移动实现js 
 * @作者 md_java 
 * @时间 2010-6-20  
 */  
  
var fromsob,toobj,froms,arry,nowIndex,newoption;  
/** 
 * @功能 分批添加或删除 
 * @输入  formselect 要移动的select;toselect 要移给的select,postionstr 移动指向left OR right 
 * @返回 无 
 */  
function  moveoption(formselect,toselect,postionstr){  
 fromsobj=document.getElementById(formselect);  
 toobj=document.getElementById(toselect);  
if(postionstr=='left'&&fromsobj.options.length==0){//判断是否还有权限  
	alert('没有可选择人员!');   
 return;  
}else if(postionstr=='right'&&fromsobj.options.length==0){//判断是否还有权限  
	alert('没有可选择人员!');  
 return;  
}  
  
if(postionstr=='left'&&fromsobj.options.selectedIndex==-1){//判断是否选中  
alert('请选择人员!');  
return;  
}else if(postionstr=='right'&&fromsobj.options.selectedIndex==-1){//判断是否选中  
alert('请选择人员!');  
return;  
}  
froms=fromsobj.options;//获得要移动select的opations  
 arry=new Array();  
var arr=0;  
for(var i=0;i<froms.length;i++){  
 if(froms[i].selected==true){  
     nowIndex   = toobj.options.length;            //获取将要移动到toselect的options长度     
     newoption = new Option(froms[i].text, froms[i].value,false,false);//copy option  
     toobj.options[nowIndex] = newoption;  
     fromsobj  
     arry[arr]=froms[i];//将选中对象缓存如数组 等下删除  
     arr++;  
      
 }  
}  
//删除已被移动的option  
for(var a=0;a<arry.length;a++){  
   fromsobj.options[arry[a].index]=null;  
  }  
}  
  
  
/** 
 * @功能 整体移动option全部添加或全部删除 
 * @输入  formselect 要移动的select;toselect 要移给的select,postionstr 移动指向left OR right 
 * @返回 无 
 */  
function giveOrUndoAll(formselect,toselect,postionstr){  
fromsobj=document.getElementById(formselect);  
 toobj=document.getElementById(toselect);  
if(postionstr=='left'&&fromsobj.options.length==0){  
 alert('没有可选择人员!');  
 return;  
}  
if(postionstr=='right'&&fromsobj.options.length==0){  
 alert('没有可选择人员!');  
 return;  
}  
  
 froms=fromsobj.options;  
for(var i=0;i<froms.length;i++){  
      nowIndex   = toobj.options.length;                
     newoption = new Option(froms[i].text, froms[i].value,false,false);  
     toobj.options[nowIndex] = newoption;  
     }  
  fromsobj.innerHTML=null;  
  
  
}  
/**单元素向底部移动*/  
function mTop(sid){     
    selectRight=document.getElementById(sid);  
    var i = selectRight.options.selectedIndex;  
    if(i > 0){     
        Temp_Text=selectRight.options(i).text;     
        Temp_ID=selectRight.options(i).value;     
        for(j=i;j>0;j--){     
            selectRight.options(j).text=selectRight.options(j-1).text;     
            selectRight.options(j).value=selectRight.options(j-1).value;     
        }     
        selectRight.options(0).value=Temp_ID;     
        selectRight.options(0).text=Temp_Text;         
        selectRight.selectedIndex=0;     
    }     
}     
 /**单元素向上移动*/  
function  mUp(sid){     
    selectRight=document.getElementById(sid);  
    var i = selectRight.options.selectedIndex;     
    var j = i-1     
    if(i>0){     
        Temp_Text = selectRight.options(j).text;     
        Temp_ID = selectRight.options(j).value;     
      
        selectRight.options(j).text = selectRight.options(i).text;     
        selectRight.options(j).value = selectRight.options(i).value;     
      
        selectRight.options(i).text = Temp_Text;     
        selectRight.options(i).value = Temp_ID;     
      
        selectRight.selectedIndex=j;     
    }     
}     
    /**单元素向下移动*/  
function  mDown(sid){     
    selectRight=document.getElementById(sid);  
    var i = selectRight.options.selectedIndex;  
    if (i != selectRight.length-1){     
        var j = i+1;     
        if(i < selectRight.length){     
            Temp_Text = selectRight.options(j).text;     
            Temp_ID = selectRight.options(j).value;     
      
            selectRight.options(j).text = selectRight.options(i).text;     
            selectRight.options(j).value = selectRight.options(i).value;     
      
            selectRight.options(i).text = Temp_Text;     
            selectRight.options(i).value = Temp_ID;     
      
            selectRight.selectedIndex=j;     
        }     
    }     
}     
    /**单元素向底部移动*/  
function  mBottom(sid){     
    selectRight=document.getElementById(sid);  
    var i = selectRight.selectedIndex;     
    var j = selectRight.length-1     
    if(i < j){     
        Temp_Text = selectRight.options(i).text;     
        Temp_ID = selectRight.options(i).value;     
        for(var k=i+1;k<=j;k++){     
            selectRight.options(k-1).text=selectRight.options(k).text;     
            selectRight.options(k-1).value=selectRight.options(k).value;     
        }     
      
        selectRight.options(j).text=Temp_Text;     
        selectRight.options(j).value=Temp_ID;     
      
        selectRight.selectedIndex=j;     
    }     
} 