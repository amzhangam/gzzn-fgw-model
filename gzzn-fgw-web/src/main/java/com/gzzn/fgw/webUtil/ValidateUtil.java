package com.gzzn.fgw.webUtil;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

import org.apache.log4j.Logger;


/**
 * 90%的验证都调用了Regular方法 但是本类也可删除大部分方法 涉及到正则的判断都直接穿参数和正则表达式 
 * 但是为了方便业务类调用和有更直观的含义 建议不要这么做 
 * Pattern的matcher已经被同步synchronized 所以 此类的任何使用正则验证的方法都不需要同步
 * @author lhq
 * @date 2013.7.10
 * @version v1.0
 */
public class ValidateUtil {
	 
	    /***
	     * ------------------基本常量定义------------------------
	     * PHONE:电话号码【国际长途代号(区号) + 国内长途代码(区号) + 电话号码 】;<p>
	     * MOBILE：移动电话；TELPHONE：固定电话移电话均可验证 <p>
	     * URL：匹配 http www ftp<p>
	     * DATE_ALL：日期支持 YYYY-MM-DD YYYY/MM/DD YYYY_MM_DD YYYYMMDD YYYY.MM.DD的形式<p>
	     * AGE：年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁 <p>
	     * CODE：邮编正则表达式  [1-9]\d{5}(?!\d) 国内6位邮编 <p>
	     * COMPLEXPWD：验证用户登录密码的复杂，   密码最少长度为 6位 ，并至少包含2种复杂类别的字符 （如 Abc21334 或者 abcd1234 符合 如12345678 abcdefg就不符合）
	     *        要求对一字符串的复杂度 进行检查         * 
		 *        密码符合以下长度/复杂性要求才可以接受
		 *        一   密码最少长度为 6位 ，并至少包含2种复杂类别的字符 （如 Abc21334 或者 abcd1234 符合 如12345678 abcdefg就不符合）
		 *        二， 密码的复杂类别由拉丁、西里尔或希腊字母组成
		 *        复杂类别说明
		 *              1 大写字母 如:A B C....Z
		 *              2 小写字母 如:a b c....z
		 *              3 西文阿拉伯数据 如:0 1 2...9
		 *              4 其他字符(“特殊字符”、标点、符号 如：{ } [] , . < > @ $ % & ^ ( ) _ + =
	     * ------------------基本常量定义------------------------
	     */
	    //public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";  
	    public static final String EMAIL = "^(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*,)*(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)$";  
	   // public static final String PHONE = "^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$" ;  
	    public static final String PHONE = "^([+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+[,/ ])*[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$" ;  
	    public static final String MOBILE ="^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$"; 
	    public static final String TELPHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$)" ;  
	    public static final String IPADDRESS = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";  
	    public static final String AGE="^(?:[1-9][0-9]?|1[01][0-9]|120)$";  
	    public static final String CODE="[1-9]\\d{5}(?!\\d)"; 
	    public static final String BOOLEAN_TYPE = "[1|Y|y|是|女]|(?i)true";
	    public static final String URL = "^(http|www|ftp|)?(://)?(//w+(-//w+)*)(//.(//w+(-//w+)*))*((://d+)?)(/(//w+(-//w+)*))*(//.?(//w)*)(//?)?" +  
	                                    "(((//w*%)*(//w*//?)*(//w*:)*(//w*//+)*(//w*//.)*(//w*&)*(//w*-)*(//w*=)*(//w*%)*(//w*//?)*" +  
	                                    "(//w*:)*(//w*//+)*(//w*//.)*" +  
	                                    "(//w*&)*(//w*-)*(//w*=)*)*(//w*)*)$"; 
	    
	    public static final String IDCARD="((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +  
	                                        "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +  
	                                        "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))"; 
	    
	    public static final String DATE_ALL="((^((1[8-9]//d{2})|([2-9]//d{3}))([-/////._]?)(10|12|0?[13578])([-/////._]?)(3[01]|[12][0-9]|0?[1-9])$)" +  
	            "|(^((1[8-9]//d{2})|([2-9]//d{3}))([-/////._]?)(11|0?[469])([-/////._]?)(30|[12][0-9]|0?[1-9])$)" +  
	            "|(^((1[8-9]//d{2})|([2-9]//d{3}))([-/////._]?)(0?2)([-/////._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-/////._]?)(0?2)([-/////._]?)(29)$)|(^([3579][26]00)" +  
	            "([-/////._]?)(0?2)([-/////._]?)(29)$)" +  
	            "|(^([1][89][0][48])([-/////._]?)(0?2)([-/////._]?)(29)$)|(^([2-9][0-9][0][48])([-/////._]?)" +  
	            "(0?2)([-/////._]?)(29)$)" +  
	            "|(^([1][89][2468][048])([-/////._]?)(0?2)([-/////._]?)(29)$)|(^([2-9][0-9][2468][048])([-/////._]?)(0?2)" +  
	            "([-/////._]?)(29)$)|(^([1][89][13579][26])([-/////._]?)(0?2)([-/////._]?)(29)$)|" +  
	            "(^([2-9][0-9][13579][26])([-/////._]?)(0?2)([-/////._]?)(29)$))"; 
	    
	    public static final String COMPLEXPWD = "^(?![!{}\\[\\],.<>@$%&^()_+=]+$)(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[!{}\\[\\],.<>@$%&^()_+=0-9a-zA-Z]{6,}$";
	    
	    
	    
	    /***
	     * ------------------数值常量定义------------------------
	     * INTEGER： 整数<p>
	     * INTEGER_NEGATIVE：正整数>=0 <p>
	     * INTEGER_POSITIVE:负整数<=0<p>
	     * DOUBLE:长整型<p>
	     * DOUBLE_NEGATIVE：正DOUBLE>=0<p>
	     * DOUBLE_POSITIVE：负DOUBLE<=0<p>
	     * ------------------数值常量定义------------------------
	     */
	    public static final String  INTEGER = "^-?(([1-9]\\d*$)|0)";  
	    public static final String  INTEGER_NEGATIVE = "^[1-9]\\d*|0$";  
	    public static final String  INTEGER_POSITIVE = "^-[1-9]\\d*|0$";      
	    public static final String  DOUBLE ="^-?([1-9]\\d*|[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";  
	    //public static final String  DOUBLE_NEGATIVE ="^[1-9]//d*//.//d*|0//.//d*[1-9]//d*|0?//.0+|0$";  
	    public static final String  DOUBLE_NEGATIVE ="^[1-9]\\d*|[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";  
	    public static final String  DOUBLE_POSITIVE ="^(-([1-9]//d*//.//d*|0//.//d*[1-9]//d*))|0?//.0+|0$";   
	     
	   
	    /***
	     * ------------------字符串常量定义------------------------
	     * STR_ENG_NUM_： 数字、26个英文字母或下划线<p>
	     * STR_ENG_NUM： 数字、26个英文字母<p>
	     * STR_ENG：26个英文字母<p>
	     * STR_CHINA：中文字符<p>
	     * STR_SPECIAL：过滤特殊字符[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]<p>
	     * STR_ENG_CHA_NUM：只能输英文、数值、中文<p>
	     * ------------------字符串常量定义------------------------
	     */
	    public static final String STR_ENG_NUM_="^\\w+$";  
	    public static final String STR_ENG_NUM="^\\w+$";  
	    public static final String STR_ENG="^[A-Za-z]+$";  
	    public static final String STR_CHINA="^[\u0391-\uFFE5]+$";   
	    public static final String STR_SPECIAL="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
	    public static final String STR_ENG_CHA_NUM="^[a-zA-Z0-9/u4e00-/u9fa5]+$";  
	   
	  
	    /***
	     * ------------------科学计数法常量定义------------------------
	     * SCIENTIFIC_A：匹配科学计数 e或者E必须出现有且只有一次 不含Dd<p>
	     * SCIENTIFIC_B：匹配科学计数 e或者E必须出现有且只有一次 结尾包含Dd <p>
	     * SCIENTIFIC_C：匹配科学计数 是否含有E或者e都通过 结尾含有Dd的也通过（针对Double类型）<p>
	     * SCIENTIFIC_D：匹配科学计数 是否含有E或者e都通过 结尾不含Dd<p>
	     * ------------------科学计数法常量定义------------------------
	     */
	    public final static String SCIENTIFIC_A ="^[-+]?(//d+(//.//d*)?|//.//d+)([eE]([-+]?([012]?//d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$";  
	    public final static String SCIENTIFIC_B ="^[-+]?(//d+(//.//d*)?|//.//d+)([eE]([-+]?([012]?//d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$";  
	    public final static String SCIENTIFIC_C ="^[-+]?(//d+(//.//d*)?|//.//d+)([eE]([-+]?([012]?//d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$";  
	    public final static String SCIENTIFIC_D ="^[-+]?(//d+(//.//d*)?|//.//d+)([eE]([-+]?([012]?//d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$";  
		
	    //------------------验证方法 ----------------------------  
	    //------------------验证方法 ----------------------------
	    //------------------验证方法 ----------------------------
	    //------------------验证方法 ----------------------------
	    /** 
	     * 判断字段是否为空 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static synchronized boolean StrIsNull(String str) {  
	        return null == str || str.trim().length() <= 0 ? true : false ;  
	    }  
	    /** 
	     * 判断字段是非空 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean StrNotNull(String str) {  
	        return !StrIsNull(str) ;  
	    }  
	    /** 
	     * 字符串null转空 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  String nulltoStr(String str) {  
	        return StrIsNull(str)?"":str;  
	    }     
	    /** 
	     * 字符串null赋值默认值  
	     * @param str    目标字符串 
	     * @param defaut 默认值 
	     * @return String 
	     */  
	    public static  String nulltoStr(String str,String defaut) {  
	        return StrIsNull(str)?defaut:str;  
	    }  
	    /** 
	     * 判断字段是否为Email 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isEmail(String str) {  
	        return Regular(str,EMAIL);  
	    }  
	    /** 
	     * 判断是否为电话号码 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isPhone(String str) {  
	        return Regular(str,PHONE);  
	    }  
	    /** 
	     * 判断是否为手机号码 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isMobile(String str) {  
	        return Regular(str,MOBILE);  
	    }  
	    
	    /** 
	     * 判断是否为手机号码或电话号码 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isTELPHONE(String str) {  
	        return Regular(str,TELPHONE);  
	    }  
	    /** 
	     * 判断是否为Url 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isUrl(String str) {  
	        return Regular(str,URL);  
	    }     
	    /** 
	     * 判断是否为IP地址 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isIpaddress(String str) {  
	        return Regular(str,IPADDRESS);  
	    } 
	    /** 
	     * 判断字段是否为日期 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isDate(String str) {  
	        return Regular(str,DATE_ALL);  
	    }     
	    /** 
	     * 判断字段是否为年龄 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isAge(String str) {  
	        return Regular(str,AGE) ;  
	    }  
	    
	    /**
	     * 判断是否为boolean类型
	     * @param str
	     * @return
	     */
	    public static  boolean isBooleanType(String str) {  
	        return Regular(str,BOOLEAN_TYPE) ;  
	    }  
	    
	    
	    /** 
	     * 判断字段是否为身份证 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isIdCard(String str) {  
	        if(StrIsNull(str)) return false ;  
	        if(str.trim().length() == 15 || str.trim().length() == 18) {  
	                return Regular(str,IDCARD);  
	        }else {  
	            return false ;  
	        }  
	          
	    }  
	    /** 
	     * 判断字段是否为邮编 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isCode(String str) {  
	        return Regular(str,CODE) ;  
	    }  
	    /**  
	     * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isNumber(String str) {  
	        return Regular(str,DOUBLE);  
	    }  
	    /** 
	     * 判断字段是否为INTEGER  符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isInteger(String str) {  
	        return Regular(str,INTEGER);  
	    }  
	    /** 
	     * 判断字段是否为正整数正则表达式 >=0 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isINTEGER_NEGATIVE(String str) {  
	        return Regular(str,INTEGER_NEGATIVE);  
	    }  
	    /** 
	     * 判断字段是否为负整数正则表达式 <=0 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isINTEGER_POSITIVE(String str) {  
	        return Regular(str,INTEGER_POSITIVE);  
	    }     
	    /** 
	     * 判断字段是否为DOUBLE 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isDouble(String str) {  
	        return Regular(str,DOUBLE);  
	    }  
	    /**  
	     * 判断字段是否为正浮点数正则表达式 >=0 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isDOUBLE_NEGATIVE(String str) {  
	        return Regular(str,DOUBLE_NEGATIVE);  
	    }  
	    /** 
	     * 判断字段是否为负浮点数正则表达式 <=0 符合返回ture 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  boolean isDOUBLE_POSITIVE(String str) {  
	        return Regular(str,DOUBLE_POSITIVE);  
	    }     
	   
	    /** 
	     * 判断字段是否超长 
	     * 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false 
	     * @param str 
	     * @param leng 
	     * @return boolean 
	     */  
	    public static  boolean isLengOut(String str,int leng) {       
	        return StrIsNull(str)?false:str.trim().length() > leng ;  
	    } 
	    
	    /** 
	     * 判断字段没有超出限定的字节长度
	     * @param str 
	     * @return int 
	     */  
	    public static  boolean isStrByteLenNotOut(String str,int leng) {
	    	//System.out.println(strByteLen(str)+"===哈哈哈哈==="+leng);
	        return  strByteLen(str)>leng?false:true;
	    }  
	    
	    /** 
	     * 判断字符串的字节长度【一个汉字相当于两个字节】
	     * 判断一个串是否全为空格
	     * @param str Regular(str,"^\\s*$")
	     * @return int 
	     */  
	    public static int strByteLen(String str) {
	    	str = str.replaceAll("[^\\x00-\\xff]", "**");
	    	//str = str.replaceAll("[\\u0391-\\uFFE5]/g", "***");
	    	//System.out.print("进来==="+str.trim().length());
	        return StrIsNull(str)?0:str.trim().length();
	    }
	    
	    
	   
	    /** 
	     * 判断字符串是不是全部是汉字 
	     * @param str 
	     * @return boolean 
	     */  
	    public static boolean isChina(String str) {  
	        return Regular(str,STR_CHINA) ;  
	    }  
	    /** 
	     * 判断字符串是不是全部是英文字母 
	     * @param str 
	     * @return boolean 
	     */  
	    public static boolean isEnglish(String str) {  
	        return Regular(str,STR_ENG) ;  
	    }  
	    /** 
	     * 判断字符串是不是全部是英文字母+数字 
	     * @param str 
	     * @return boolean 
	     */  
	    public static boolean isENG_NUM(String str) {  
	        return Regular(str,STR_ENG_NUM) ;  
	    }  
	    /** 
	     * 判断字符串是不是全部是英文字母+数字+下划线 
	     * @param str 
	     * @return boolean 
	     */  
	    public static boolean isENG_NUM_(String str) {  
	        return Regular(str,STR_ENG_NUM_) ;  
	    }  
	    /** 
	     * 过滤特殊字符串 返回过滤后的字符串 
	     * @param str 
	     * @return boolean 
	     */  
	    public static  String filterStr(String str) {  
	        Pattern p = Pattern.compile(STR_SPECIAL);  
	        Matcher m = p.matcher(str);  
	        return   m.replaceAll("").trim();  
	    }     
	    /** 
	     * 匹配是否符合正则表达式pattern 匹配返回true 
	     * @param str 匹配的字符串 
	     * @param pattern 匹配模式 
	     * @return boolean 
	     */  
	    public static  boolean Regular(String str,String pattern){  
	        //CommonMethod.getLogger().debug("pattern="+pattern);  
	        if(null == str || str.trim().length()<=0)  
	            return false;           
	        Pattern p = Pattern.compile(pattern);  
	        Matcher m = p.matcher(str);  
	        return m.matches();  
	    }  
	    /** 
	     * 判断是不是科学计数法 如果是 返回true 
	     * 匹配科学计数 e或者E必须出现有且只有一次 结尾不含D 
	     * 匹配模式可参考本类定义的 SCIENTIFIC_A，SCIENTIFIC_B,SCIENTIFIC_C,SCIENTIFIC_D 
	     * 若判断为其他模式可调用 Regular(String str,String pattern)方法 
	     * @param str 科学计数字符串 
	     * @return boolean 
	     */  
	    public static  boolean isScientific(String str){  
	        if(StrIsNull(str))  
	            return false;   
	        return Regular(str,ValidateUtil.SCIENTIFIC_A);  
	    }  
	    
		public static void main(String[] args) {  
	        // TODO Auto-generated method stub  
			/*CommonMethod.getLogger().debug(ValidateUtil.isEmail("luohaiqun1204@163.com")); 
			CommonMethod.getLogger().debug(ValidateUtil.isPhone("0792-7")); 
	    	CommonMethod.getLogger().debug(ValidateUtil.isMobile("15979704192")); 
	    	CommonMethod.getLogger().debug(ValidateUtil.isIpaddress("130.12.2.0")); 
	    	CommonMethod.getLogger().debug(ValidateUtil.isEnglish("www")); 
	    	CommonMethod.getLogger().debug(ValidateUtil.isChina("rnh"));*/
			
			//CommonMethod.getLogger().debug(ValidateUtil.Regular("   ", "^\\S+$")); 
			
			//CommonMethod.getLogger().debug(ValidateUtil.isBooleanType("TRUe"));  
			
			/**System.out.println("13809782345:"+ValidateUtil.Regular("13809782345", ValidateUtil.PHONE));
			System.out.println("013809782345:"+ValidateUtil.Regular("013809782345", ValidateUtil.PHONE));
			System.out.println("13809782345,13809782345:"+ValidateUtil.Regular("13809782345,13809782345", ValidateUtil.PHONE));
			
			System.out.println("38773950:"+ValidateUtil.Regular("38773950", ValidateUtil.PHONE));
			System.out.println("38773950,38773950:"+ValidateUtil.Regular("38773950,38773950", ValidateUtil.PHONE));
			
			System.out.println("02038773950:"+ValidateUtil.Regular("02038773950", ValidateUtil.PHONE));
			System.out.println("02038773950,02038773950,02038773950:"+ValidateUtil.Regular("02038773950,02038773950,02038773950", ValidateUtil.PHONE));
			
			System.out.println("020-38773950:"+ValidateUtil.Regular("020-38773950", ValidateUtil.PHONE));
			System.out.println("020-38773950,020-38773950:"+ValidateUtil.Regular("020-38773950,020-38773950", ValidateUtil.PHONE));
			
			System.out.println("076983231234:"+ValidateUtil.Regular("076983231234", ValidateUtil.PHONE));
			System.out.println("076983231234,076983231234:"+ValidateUtil.Regular("076983231234,076983231234", ValidateUtil.PHONE));
			
			System.out.println("0731-56350159:"+ValidateUtil.Regular("0731-56350159", ValidateUtil.PHONE));
			System.out.println("0731-56350159,073156350159:"+ValidateUtil.Regular("0731-56350159,073156350159", ValidateUtil.PHONE));
			
			System.out.println("020-38773950:"+ValidateUtil.Regular("020-38773950", ValidateUtil.PHONE));
			System.out.println("020-38773950,020-38773950:"+ValidateUtil.Regular("020-38773950,020-38773950", ValidateUtil.PHONE));
			
			System.out.println("8602038773950:"+ValidateUtil.Regular("8602038773950", ValidateUtil.PHONE));
			System.out.println("+8602038773950:"+ValidateUtil.Regular("+8602038773950", ValidateUtil.PHONE));
			System.out.println("86-020-38773950:"+ValidateUtil.Regular("86-020-38773950", ValidateUtil.PHONE));
			System.out.println("086-020-38773950:"+ValidateUtil.Regular("086-020-38773950", ValidateUtil.PHONE));
			
			System.err.println("(020)38773950:"+ValidateUtil.Regular("(020)38773950", ValidateUtil.PHONE));*/
			
			System.err.println("2800000:"+ValidateUtil.Regular("2800000", ValidateUtil.DOUBLE));
			String[] array = { "A2wee", "###123", "!!isok!!234","525867lhq@qq.com", "ijk#A","ijk@$A", "QWERtyuiop", "ABCD123", "12345678", "66666666" };
			for(int i=0;i<array.length;i++){
				//System.err.println(array[i]);
				 System.err.println(array[i]+"："+ValidateUtil.Regular(array[i], ValidateUtil.COMPLEXPWD));
			}
			
			String value="13112290300#18925142590^13500003804";
			System.err.println(ValidateUtil.Regular(value, ValidateUtil.PHONE));
			
	    }
	    	
	  

}
