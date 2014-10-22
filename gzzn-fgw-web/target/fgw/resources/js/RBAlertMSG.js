/**
 * 提供类似MSN消息框
 * @param {} id
 * @param {} width
 * @param {} height
 * @param {} caption
 * @param {} title
 * @param {} message
 * @param {} target
 * @param {} action
 */
function CLASS_MSN_MESSAGE(id, width, height, caption, title, message, target,
		action) {
	this.id = id;
	this.title = title;
	this.caption = caption;
	this.message = message;
	this.target = target;
	this.action = action;
	this.width = width ? width : 200;
	this.height = height ? height : 120;
	this.timeout = 150;
	this.speed = 20;
	this.step = 1;
	this.right = screen.width - 1;
	this.bottom = screen.height;
	this.left = this.right - this.width;
	this.top = this.bottom - this.height;
	this.timer = 0;
	this.pause = false;
	this.close = false;
	this.autoHide = true;
	this.url = "";
}

/**
 * 隐藏消息方法
 */
CLASS_MSN_MESSAGE.prototype.hide = function() {
	if (this.onunload()) {
		var offset = this.height > this.bottom - this.top
				? this.height
				: this.bottom - this.top;
		var me = this;
		if (this.timer > 0) {
			window.clearInterval(me.timer);
		}
		var fun = function() {
			if (me.pause == false || me.close) {
				var x = me.left;
				var y = 0;
				var width = me.width;
				var height = 0;
				if (me.offset > 0) {
					height = me.offset;
				}

				y = me.bottom - height;

				if (y >= me.bottom) {
					window.clearInterval(me.timer);
					me.Pop.hide();
				} else {
					me.offset = me.offset - me.step;
				}
				me.Pop.show(x, y, width, height);
			}
		}
		this.timer = window.setInterval(fun, this.speed);
	}
	//window.location.href=this.url;
}

/**
 * 消息卸载事件，可以重写
 * @return {Boolean}
 */
CLASS_MSN_MESSAGE.prototype.onunload = function() {
	//消息关闭或隐藏
	return true;
}
/**
 * 消息命令事件，要实现自己的连接，请重写它
 */
CLASS_MSN_MESSAGE.prototype.oncommand = function() {
	$.ajax({url:this.url,type : "POST",success : function(returnedData) {
		var json = returnedData;
	}});
	this.close = true;
	this.hide();
}
/**
 * 消息显示方法
 */
CLASS_MSN_MESSAGE.prototype.show = function() {
	var oPopup = window.createPopup(); // IE5.5+

	this.Pop = oPopup;

	var w = this.width;
	var h = this.height;

	/*var str = "<DIV style='BORDER-RIGHT: #455690 1px solid; BORDER-TOP: #a6b4cf 1px solid; Z-INDEX: 99999; LEFT: 0px; BORDER-LEFT: #a6b4cf 1px solid; WIDTH: "
			+ w
			+ "px; BORDER-BOTTOM: #455690 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: "
			+ h + "px; BACKGROUND-COLOR: #c9d3f3'>"
	str += "<TABLE style='BORDER-TOP: #ffffff 1px solid; BORDER-LEFT: #ffffff 1px solid' cellSpacing=0 cellPadding=0 width='100%' bgColor=#cfdef4 border=0>"
	str += "<TR>"
	str += "<TD style='FONT-SIZE: 12px;COLOR: #0f2c8c' height=24></TD>"
	str += "<TD style='PADDING-LEFT: 4px; FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #1f336b;' vAlign=center width='100%'>"
			+ this.caption + "</TD>"
	str += "<TD style='PADDING-RIGHT: 2px;' vAlign=center align=right width=19>"
	str += "<SPAN title='关闭' style='FONT-WEIGHT: bold; FONT-SIZE: 12px; CURSOR: pointer; COLOR: red; MARGIN-RIGHT: 4px' id='btSysClose' >×</SPAN></TD>"
	str += "</TR>"
	str += "<TR>"
	str += "<TD style='PADDING-RIGHT: 1px;PADDING-BOTTOM: 1px' colSpan=3 height="+ (h - 28) + ">"
	str += "<DIV id='btCommand' style='BORDER-RIGHT: #b9c9ef 1px solid; PADDING-RIGHT: 8px; BORDER-TOP: #728eb8 1px solid; PADDING-LEFT: 8px; FONT-SIZE: 12px; PADDING-BOTTOM: 8px; BORDER-LEFT: #728eb8 1px solid; WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 8px; BORDER-BOTTOM: #b9c9ef 1px solid; HEIGHT: 100%'>"
			// + "<BR><BR>"//message
	str += "<DIV style='WORD-BREAK: break-all' align=left><FONT color=#ff0000 size=3>"
			+ this.message + "</FONT></DIV>"
	str += "</DIV>"
	str += "</TD>"
	str += "</TR>"
	str += "</TABLE>"
	str += "</DIV>"*/
	var CW_Body = oPopup.document.body;
	var CSStext = "margin:1px;color:black; border:2px outset;background-color:buttonface;width:16px;height:14px;font-size:12px;line-height:11px;cursor:pointer;";
	var popTop=50;
	CW_Body.style.overflow = "hidden";
	CW_Body.style.backgroundColor = "white";
	CW_Body.style.border = "solid black 1px";
	var str="";
	str += "<table width=100% height=100% cellpadding=0 cellspacing=0 border=0 >";
	str += "<tr style=';font-size:12px;background:#0099CC;height:20;cursor:default'>";
	str += "<td style='color:white;padding-left:5px;height:24px'>"+ this.caption +"</td>";
	str += "<td style='color:#ffffff;padding-right:5px;' align=right>";
	str += "<span  id=\"btSysClose\" style=\""+CSStext+"font-family:System;padding:4px 2px 0px 0px;\" title='关闭'>×</span>";
	str += "</td></tr><tr><td colspan=2>";
	str += "<div id='btCommand' style='overflow:scroll;overflow-x:hidden;overflow-y:auto;HEIGHT:100%;padding-left:5px;padding-top:5px;font-size:13px;'>";
	str += this.message;
	str += "</div>";
	str += "</td></tr></table>";

	oPopup.document.body.innerHTML = str;

	this.offset = 0;
	var me = this;
	oPopup.document.body.onmouseover = function() {
		me.pause = true;
	}
	oPopup.document.body.onmouseout = function() {
		me.pause = false;
	}
	var fun = function() {
		var x = me.left;
		var y = 0;
		var width = me.width;
		var height = me.height;
		if (me.offset > me.height) {
			height = me.height;
		} else {
			height = me.offset;
		}
		y = me.bottom - me.offset;
		if (y <= me.top) {
			me.timeout--;
			if (me.timeout == 0) {
				window.clearInterval(me.timer);
				if (me.autoHide) {
					me.hide();
				}
			}
		} else {
			me.offset = me.offset + me.step;
		}
		me.Pop.show(x, y, width, height);
	}

	this.timer = window.setInterval(fun, this.speed)

	var btClose = oPopup.document.getElementById("btSysClose");

	btClose.onclick = function() {
		me.close = true;
		me.hide();
	}

	/*var btCommand = oPopup.document.getElementById("btCommand");
	btCommand.onclick = function() {
		me.oncommand();
	}*/
	var showNotes = oPopup.document.getElementById("showNotes");
	showNotes.onclick = function() {
		// this.close = true;
		parent.showNotes();
		me.hide();
		//window.open(ommand.href);
	}
}
/**
 * 设置速度方法
 * @param {} s
 */
CLASS_MSN_MESSAGE.prototype.speed = function(s) {
	var t = 20;
	try {
		t = praseInt(s);
	} catch (e) {
	}
	this.speed = t;
}
CLASS_MSN_MESSAGE.prototype.url = function(url) {
	this.url = url;
}
/**
 * 设置步长方法
 * @param {} s
 */
CLASS_MSN_MESSAGE.prototype.step = function(s) {
	var t = 1;
	try {
		t = praseInt(s);
	} catch (e) {
	}
	this.step = t;
}

CLASS_MSN_MESSAGE.prototype.rect = function(left, right, top, bottom) {
	try {
		this.left = left != null ? left : this.right - this.width;
		this.right = right != null ? right : this.left + this.width;
		this.bottom = bottom != null ? (bottom > screen.height
				? screen.height
				: bottom) : screen.height;
		this.top = top != null ? top : this.bottom - this.height;
	} catch (e) {
	}
}