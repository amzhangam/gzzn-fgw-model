/**
 * 判断闰年 
 * @return {Boolean}
 *  * @author lhq
 */
Date.prototype.isLeapYear = function() {
	return (0 == this.getYear() % 4 && ((this.getYear() % 100 != 0) || (this
			.getYear() % 400 == 0)));
};

/**
 * 日期格式化
 * @param {String} formatStr 格式化后的日期格式;<p>
 * 年份：YYYY/yyyy/YY/yy; 月份：MM/M <p>
 * 星期：W/w ;日期：dd/DD/d/D<p>
 * 时间：hh/HH/h/H; 分钟：mm/m; 秒：ss/SS/s/S<p>
 * 如：yyyy-MM-dd、yyyy-MM-dd hh:mm<p>
 * @return {String}
 * @author lhq
 */
Date.prototype.Format = function(formatStr) {
	var str = formatStr;
	var Week = [ '日', '一', '二', '三', '四', '五', '六' ];
	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/yy|YY/,
			(this.getYear() % 100) > 9 ? (this.getYear() % 100).toString()
					: '0' + (this.getYear() % 100));
	var new_MM = this.getMonth(); // 月份需要进行特殊处理，JS月份需要+1
	new_MM++;
	str = str.replace(/MM/, new_MM > 9 ? new_MM.toString() : '0' + new_MM);
	str = str.replace(/M/g, new_MM);
	str = str.replace(/w|W/g, Week[this.getDay()]);
	str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString()
			: '0' + this.getDate());
	str = str.replace(/d|D/g, this.getDate());
	str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString()
			: '0' + this.getHours());
	str = str.replace(/h|H/g, this.getHours());
	str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes()
			.toString() : '0' + this.getMinutes());
	str = str.replace(/m/g, this.getMinutes());
	str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds()
			.toString() : '0' + this.getSeconds());
	str = str.replace(/s|S/g, this.getSeconds());
	
	return str;
};

/**
 *  求两个时间的天数差 日期格式为 YYYY-MM-dd
 * @param {String} DateOne
 * @param {String} DateTwo
 * @return {Number}
 * @author lhq
 */
function daysBetween(DateOne, DateTwo) {
	var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
	
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
	
	var OneDay = DateOne.substring(DateOne.length, DateOne.lastIndexOf('-') + 1);
	var TwoDay = DateTwo.substring(DateTwo.length, DateTwo.lastIndexOf('-') + 1);
	
	var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
			.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
	return Math.abs(cha);
}

/**
 * 日期计算
 * @param {String} strInterval
 * @param {Number} Number
 * @return {Date}
 * @author lhq
 */
Date.prototype.DateAdd = function(strInterval, Number) {
	var dtTmp = this;
	switch (strInterval) {
	case 's':
		return new Date(Date.parse(dtTmp) + (1000 * Number));
	case 'n':
		return new Date(Date.parse(dtTmp) + (60000 * Number));
	case 'h':
		return new Date(Date.parse(dtTmp) + (3600000 * Number));
	case 'd':
		return new Date(Date.parse(dtTmp) + (86400000 * Number));
	case 'w':
		return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
	case 'q':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3,
				dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
						.getSeconds());
	case 'm':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp
				.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
				.getSeconds());
	case 'y':
		return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp
				.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
				.getSeconds());
	}
};


/**
 * 比较日期差 
 * @param {String} strInterval
 * @param {String,Date} dtEnd 格式为日期型或者 有效日期格式字符串
 * @return {Number}
 * @author lhq
 */
Date.prototype.DateDiff = function(strInterval, dtEnd) {
	var dtStart = this;
	if (typeof dtEnd == 'string') {// 如果是字符串转换为日期型
		dtEnd = StringToDate(dtEnd);
	}
	switch (strInterval) {
	case 's':
		return parseInt((dtEnd - dtStart) / 1000);
	case 'n':
		return parseInt((dtEnd - dtStart) / 60000);
	case 'h':
		return parseInt((dtEnd - dtStart) / 3600000);
	case 'd':
		return parseInt((dtEnd - dtStart) / 86400000);
	case 'w':
		return parseInt((dtEnd - dtStart) / (86400000 * 7));
	case 'm':
		return (dtEnd.getMonth() + 1)
				+ ((dtEnd.getFullYear() - dtStart.getFullYear()) * 12)
				- (dtStart.getMonth() + 1);
	case 'y':
		return dtEnd.getFullYear() - dtStart.getFullYear();
	}
};


/**
 * 日期输出字符串，重载了系统的toString方法
 * @param {Boolean} showWeek 是否显示周
 * @return {String}
 * @author lhq
 */
Date.prototype.toString = function(showWeek) {
	var myDate = this;
	var str = myDate.toLocaleDateString();
	if (showWeek) {
		var Week = [ '日', '一', '二', '三', '四', '五', '六' ];
		str += ' 星期' + Week[myDate.getDay()];
	}
	return str;
};

/**
 * 日期合法性验证,格式为：YYYY-MM-DD或YYYY/MM/DD<p>
 * 如果格式满足YYYY-(/)MM-(/)DD或YYYY-(/)M-(/)DD或YYYY-(/)M-(/)D或YYYY-(/)MM-(/)D就替换为''<p>
 * 数据库中，合法日期可以是:YYYY-MM/DD(2003-3/21),数据库会自动转换为YYYY-MM-DD格式
 * @param {String} DateStr 
 * @return {Boolean}
 * @author lhq
 */
function IsValidDate(DateStr) {
	var sDate = DateStr.replace(/(^\s+|\s+$)/g, ''); // 去两边空格;
	if (sDate == '')
		return true;
	var s = sDate.replace(
			'/[\d]{ 4,4 }[\-/]{ 1 }[\d]{ 1,2 }[\-/]{ 1 }[\d]{ 1,2 }/g', '');
	if (s == '') { // 说明格式满足YYYY-MM-DD或YYYY-M-DD或YYYY-M-D或YYYY-MM-D
		var t = new Date(sDate.replace(/\-/g, '/'));
		var ar = sDate.split('/[-/:]/');
		if (ar[0] != t.getYear() || ar[1] != t.getMonth() + 1
				|| ar[2] != t.getDate()) {
			// alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。');
			return false;
		}
	} else {
		// alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。');
		return false;
	}
	return true;
}

/**
 * 日期时间检查
 * @param {String} str 格式为：YYYY-MM-DD HH:MM:SS
 * @return {Boolean}
 * @author lhq
 */
function CheckDateTime(str) {
	var reg = "/^(\d+)-(\d{ 1,2 })-(\d{ 1,2 }) (\d{ 1,2 }):(\d{ 1,2 }):(\d{ 1,2 })$/";
	var r = str.match(reg);
	if (r == null)
		return false;
	r[2] = r[2] - 1;
	var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);
	if (d.getFullYear() != r[1])
		return false;
	if (d.getMonth() != r[2])
		return false;
	if (d.getDate() != r[3])
		return false;
	if (d.getHours() != r[4])
		return false;
	if (d.getMinutes() != r[5])
		return false;
	if (d.getSeconds() != r[6])
		return false;
	return true;
}


/**
 * 把日期分割成数组
 * @return {Array}
 * @author lhq
 */
Date.prototype.toArray = function() {
	var myDate = this;
	var myArray = Array();
	myArray[0] = myDate.getFullYear();
	myArray[1] = myDate.getMonth();
	myArray[2] = myDate.getDate();
	myArray[3] = myDate.getHours();
	myArray[4] = myDate.getMinutes();
	myArray[5] = myDate.getSeconds();
	return myArray;
};
// +---------------------------------------------------
// | 取得日期数据信息
// | 参数 interval 表示数据类型
// | y 年 m月 d日 w星期 ww周 h时 n分 s秒
// +---------------------------------------------------

/**
 * 取得日期数据信息
 * @param {String} interval 表示数据类型,y 年 m月 d日 w星期 ww周 h时 n分 s秒
 * @return {Number}
 * @author lhq
 */
Date.prototype.DatePart = function(interval) {
	var myDate = this;
	var partStr = 0;
	var Week = [ '日', '一', '二', '三', '四', '五', '六' ];
	switch (interval) {
	case 'y':
		partStr = myDate.getFullYear();
		break;
	case 'm':
		partStr = myDate.getMonth() + 1;
		break;
	case 'd':
		partStr = myDate.getDate();
		break;
	case 'w':
		partStr = Week[myDate.getDay()];
		break;
	case 'ww':
		partStr = myDate.WeekNumOfYear();
		break;
	case 'h':
		partStr = myDate.getHours();
		break;
	case 'n':
		partStr = myDate.getMinutes();
		break;
	case 's':
		partStr = myDate.getSeconds();
		break;
	}
	return partStr;
};


/**
 * 取得当前日期所在月的最大天数
 * @return {Number}
 * @author lhq
 */
Date.prototype.MaxDayOfDate = function() {
	var myDate = this;
	var ary = myDate.toArray();
	var date1 = (new Date(ary[0], ary[1] + 1, 1));
	var date2 = date1.dateAdd(1, 'm', 1);
	var result = dateDiff(date1.Format('yyyy-MM-dd'), date2
			.Format('yyyy-MM-dd'));
	return result;
}


/**
 * 取得当前日期所在周是一年中的第几周
 * @return {Number}
 */
Date.prototype.WeekNumOfYear = function() {
	var myDate = this;
	var ary = myDate.toArray();
	var year = ary[0];
	var month = ary[1] + 1;
	var day = ary[2];
	document.write("< script language=VBScript\> \n");
	document.write("myDate = DateValue(''+month+'-'+day+'-'+year+'') \n");
	document.write("result = DatePart('ww', myDate) \n");
	document.write("\n");
	return result;
}


/**
 * 字符串转成日期类型,
 * @param {String} DateStr 格式 MM/dd/YYYY MM-dd-YYYY YYYY/MM/dd YYYY-MM-dd
 * @return {Date}
 * @author lhq
 */
function StringToDate(DateStr) {
	var converted = Date.parse(DateStr);
	var myDate = new Date(converted);
	//alert(isNaN(myDate)+" "+DateStr+" "+myDate.toString(false)+" "+converted);
	if (isNaN(myDate)) {
		// var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';
		var arys = DateStr.split('-');
		myDate = new Date(arys[0], --arys[1], arys[2]);
	}
	return myDate;
};



/**
 * 时间加减
 * @param {String} addType 计算类型:年,yyyy,月,MM,日,dd,小时,hh,分钟,mm
 * @param {Number} addNum 负数是减,正数是加
 * @param {String} formatStr 格式字符串
 * @return {String}
 * @author lhq
 */
Date.prototype.AddTime = function( addType,addNum, formatStr) {
	var uom = new Date(this.getFullYear(), this.getMonth(), this.getDate(), this
			.getHours(), this.getMinutes(), this.getSeconds()); // 生成日期格式
	switch (true) {
		case addType=="年"||addType=="yyyy": uom.setFullYear(uom.getFullYear() + addNum);return uom.Format(formatStr);break;
		case addType=="月"||addType=="MM": uom.setMonth(uom.getMonth() + addNum);return uom.Format(formatStr);break;
		case addType=="日"||addType=="dd": uom.setDate(uom.getDate() + addNum);return uom.Format(formatStr);break;
		case addType=="小时"||addType=="hh": uom.setHours(uom.getHours() + addNum);return uom.Format(formatStr);break;
		case addType=="分钟"||addType=="mm": uom.setMinutes(uom.getMinutes() + addNum);return uom.Format(formatStr);break;
	}
};

/**
 * 获取指定月份的最大日期
 * 获得该月的第一天
 * @param {String} dStr 支持类型:yyyy-MM、yyyy-MM-dd
 * @param {String} formatStr 格式字符串
 * @return {String}
 * @author lhq
 */
function getMonthMinDate(dStr, formatStr){
	var arys = dStr.split("-");
	var myDate = new Date(arys[0], arys[1]-1, 1);
	return myDate.Format(formatStr);
}

/**
 * 获取指定月份的最大日期
 * 获得该月的第一天、月+1、日-1
 * @param {String} dStr 支持类型:yyyy-MM、yyyy-MM-dd
 * @param {String} formatStr 格式字符串
 * @return {String}
 * @author lhq
 */
function getMonthMaxDate(dStr, formatStr){
	var arys = dStr.split("-");
	var myDate = new Date(arys[0], arys[1]-1, 1);
	myDate.setMonth(myDate.getMonth()+1);
	myDate.setDate(myDate.getDate()-1);
	return myDate.Format(formatStr);
}


/**
 * 将117秒转换成 XX时XX分XX秒的格式
 * @param {Number} ssNum
 * @return {String}
 * @author lhq
 */
function ssToHMS(ssNum){
	  var hh=0,mm=0,ss=0;
	  mm =  parseInt(ssNum/60);
	  ss = ssNum-mm*60;
	  hh =  parseInt(mm/60);
	  mm = mm-hh*60;
	  return hh+" 小时 "+mm+" 分 "+ss+" 秒";
}
	

// +---------------------------------------------------
// | 
// | 
// +---------------------------------------------------
/**
 * error<p>
 * 取得系统时间的前num年,yyyy-MM-dd年,重点在这里,负数是前几年,正数是后几年
 * @param {} date
 * @param {} yNum
 * @param {} mNum
 * @param {} dNum
 * @param {} pattern
 * @author lhq
 */
function getAddYMD(date, yNum, mNum, dNum, pattern) {
	var d_Year = date.getYear();
	var d_Month = date.getMonth();
	var d_Date = date.getDate();
	var uom = new Date(d_Year, d_Month, d_Date); // 生成日期格式
	uom.setDate(uom.getDate() + dNum);// 日期增加num
	uom.setMonth(uom.getMonth() + mNum);// 月份增加哦num
	uom.setYear(uom.getYear() + yNum);// 年份增加哦num
	var new_MM = uom.getMonth(); // 格式化新日期：yyyy-MM-dd格式
	new_MM++;
	var new_MM = new_MM > 10 ? new_MM : ("0" + new_MM);
	var new_DD = uom.getDate();
	var new_DD = new_DD > 10 ? new_DD : ("0" + new_DD);
	// 得到最终结果
	uom = uom.getFullYear() + "-" + new_MM + "-" + new_DD;
	alert(uom)
}


/**
 * 返回时间范围内的第一天与最后一天
 * @param {Date} date 时间
 * @param {String} type 类型 年:Y;季:Q;月:M;周:W;
 * @param {String} formatStr 格式化字符串
 * @return {Object.startTime,Object.endTime}
 * @author czhi
 */
function getDateScope(date,type,formatStr){
	var obj = new Object();
	if(date==undefined){
		obj.startTime="0";
		obj.endTime="0";
	}else{
		switch (type) {
			case 'Y':
				date.setMonth(0);
				date.setDate(1);
				obj.startTime = date.Format(formatStr);
				date.setFullYear(date.getFullYear()+1);
				date.setDate(0);
				obj.endTime=date.Format(formatStr);
				break;
				
			case 'Q':
				var m = date.getMonth() + 1;
				var q = parseInt((m + 2 ) / 3 ); //得到第几季
				m = q * 3 - 2;  //得到季的首月份
				date.setMonth(m-1);
				date.setDate(1);
				obj.startTime = date.Format(formatStr);
				date.setMonth(date.getMonth() + 3);
				date.setDate(0);
				obj.endTime=date.Format(formatStr);
				break;
				
			case 'M':
				date.setDate(1);
				obj.startTime = date.Format(formatStr);
				date.setMonth(date.getMonth()+1);
				date.setDate(0);
				obj.endTime=date.Format(formatStr);
				break;
				
			case 'W':
				var day  = date.getDay();
				date.setDate(date.getDate() - (day -1) );
				obj.startTime = date.Format(formatStr);
				date.setDate(date.getDate() + 6);
				obj.endTime=date.Format(formatStr);
				break;
			default:
				break;
			}
		}
	return obj;
}
