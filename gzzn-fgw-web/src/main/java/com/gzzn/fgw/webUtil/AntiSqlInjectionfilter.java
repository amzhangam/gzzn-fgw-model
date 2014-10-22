package com.gzzn.fgw.webUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
public class AntiSqlInjectionfilter implements Filter {
	private final static Logger log = Logger.getLogger(AntiSqlInjectionfilter.class);
    public void destroy() {
        // TODO Auto-generated method stub
    }
    public void init(FilterConfig arg0) throws ServletException {


    }

    public void doFilter(ServletRequest args0, ServletResponse args1,FilterChain chain) throws IOException, ServletException 
    {   
    	HttpServletRequest req=(HttpServletRequest)args0;
        //HttpServletRequest res=(HttpServletRequest)args1;
        //获得所有请求参数名         
        Enumeration params = req.getParameterNames();
        String sql = "";
        String strValue="";
        while (params.hasMoreElements()) {
            //得到参数名
            String name = params.nextElement().toString();
            //System.out.println("name===========================" + name + "--");
            //得到参数对应值
            String[] value = req.getParameterValues(name);
            for (int i = 0; i < value.length; i++) {
            	strValue=new String(value[i].getBytes("ISO-8859-1"),"UTF-8");
            	strValue=URLEncoder.encode(value[i],"UTF-8");
                sql = sql + strValue;
                
            }
        }
       //System.out.println("============================SQL"+sql);
        //有sql关键字，跳转到error.html
       // sql=java.net.URLEncoder.encode(sql, "utf-8");
        //String url = req.getRequestURL().toString();        
        //if (url.indexOf("/price/price.jspx") >0) {
        	if (sqlValidate(sql)) {
        		
        		log.info("该作者的Ip地址:"+req.getRemoteAddr());
        		log.info("该作者的主机:"+req.getRemoteHost());
        		log.info("该作者的端口:"+req.getRemotePort());
        		
                throw new IOException("谢谢您对我们网站的关注！");
                //String ip = req.getRemoteAddr();
            } else {
                chain.doFilter(args0,args1);
            }
       // }else{
        	//chain.doFilter(args0,args1);
        }
        
   // }

     

    //效验

    protected static boolean sqlValidate(String str) {

        str = str.toLowerCase();
        String badStr = "'|exec|execute|insert|select|delete|update|count|drop|ge*|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|or|like|exec|execute|insert|create|drop|" +
                "table|from|grant|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|" +
                "chr|mid|master|truncate|char|declare|--|or|like|#";
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
        if (str.indexOf(badStrs[i]) >= 0) {
        	  System.out.println(badStrs[i]);
              return true;
            }
        }
        return false;

    }

}
