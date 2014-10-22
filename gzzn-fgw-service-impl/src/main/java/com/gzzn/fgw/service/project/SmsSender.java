package com.gzzn.fgw.service.project;
//package com.gzzn.util;
//
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import javax.xml.namespace.QName;
//import org.apache.axis.client.Call;
//import org.apache.axis.client.Service;
//import com.gzzn.entity.SmsConfig;
//
//public class SmsSender {
//	
//	public String Login(SmsConfig config) {
//		String ret = "";
//		System.out.println("Login开始!");
//
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new URL(config.getSmsUrl()));
//			call.setOperationName(new QName(config.getSmsNamespace(),"LoginNew"));
//			ret = (String) call.invoke(new Object[] {config.getSmsUserName(),config.getSmsPassword()}); // 参数，可以多个
//			System.out.println(ret);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("Login结束！");
//		return ret;
//	}
//	
//	public String Active(SmsConfig config) {
//		String ret = "";
//		System.out.println("Active开始!");
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(config.getSmsUrl()));
//			call.setOperationName(new QName(config.getSmsNamespace(),"ActiveNew"));
//			ret = (String) call.invoke(new Object[] { config.getSmsUserName() }); // 参数，可以多个
//			System.out.println(ret);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("Active结束！");
//		return ret;
//	}
//	
//	public String GetDeliver(SmsConfig config,String serialno) {
//		String ret = "";
//		System.out.println("GetResponse开始!");
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(config.getSmsUrl()));
//			call.setOperationName(new QName(config.getSmsNamespace(), "GetDeliver5"));
//			ret = (String) call.invoke(new Object[] { config.getSmsserialno(serialno), config.getSmsSendNo() }); // 参数，可以多个
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		System.out.println(ret);
//		System.out.println("GetResponse结束!");
//		return ret;
//	}
//
//	public String getRecv(SmsConfig config) {
//		String ret = "";
//		System.out.println("Recv开始!");
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(config.getSmsUrl()));
//			call.setOperationName(new QName(config.getSmsNamespace(), "GetRecves2"));
//			ret = (String) call.invoke(new Object[] {}); // 参数，可以多个
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(ret);
//		System.out.println("Recv结束!");
//		return ret;
//	}
//
//	public String Logout(SmsConfig config) {
//		String ret = "";
//		System.out.println("Logout开始!");
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(config.getSmsUrl()));
//			call.setOperationName(new QName(config.getSmsNamespace(), "LogoutNew"));
//			ret = (String) call.invoke(new Object[] { config.getSmsUserName() }); // 参数，可以多个
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(ret);
//		System.out.println("Logout结束!");
//		return ret;
//	}
//	
//	public String Send(SmsConfig config,String serialno,String mobileno, String content, String mesgtype, String srr) {
//		String ret = "";
//
//		System.out.println("Send开始!");
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(config.getSmsUrl()));
//			call.setOperationName(new QName(config.getSmsNamespace(), "SendNew"));
//			ret = (String) call.invoke(new Object[] { config.getSmsUserName(), config.getSmsserialno(serialno), config.getSmsSendNo(), mobileno, content, mesgtype, srr }); // 参数，可以多个
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(ret);
//		System.out.println("Send结束!");
//		return ret;
//	}
//
//	public String BatchSend(SmsConfig config,String serialno, String sendport, String mobileno, String content, String mesgtype, String srr) {
//		String ret = "";
//		System.out.println("BatchSend开始!");
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(config.getSmsUrl()));
//			call.setOperationName(new QName(config.getSmsNamespace(), "BatchSend"));
//			ret = (String) call.invoke(new Object[] { config.getSmsserialno(serialno), config.getSmsSendNo(), mobileno, content, mesgtype, srr }); // 参数，可以多个
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		System.out.println(ret);
//		System.out.println("BatchSend结束!");
//		return ret;
//	}
//	
//	public void smsSend(SmsConfig config,String mobileno,String content) throws Exception{
//        String mesgtype=config.getSmsMesgtype();
//        String srr=config.getSmsSrr();
//        
//        if(content.length()>66){
//        	mesgtype="4";
//        }
//        
//       // Login(config);
//        List<String> al=splitUserData(content,mesgtype);
//        
//        for(int j=0;j<al.size();j++){
//        	String tempStr=(String)al.get(j);
//        	tempStr=(tempStr==null)?"":tempStr.trim();
//        	if(!"".equals(tempStr)){
//        	//	Send(config,j+"",mobileno, tempStr,  mesgtype, srr);
//        		
//        	    Object ret = "";
//
//                System.out.println("Send开始!");
//                try {
//                    Service service = new Service();
//                    Call call = (Call) service.createCall();
//                    call.setTargetEndpointAddress(new java.net.URL(config.getSmsUrl()));
//                    call.setOperationName(new QName(config.getSmsNamespace(), "sendSM"));
//                    ret = call.invoke(new Object[] { "tzc711", config.getSmsUserName(), config.getSmsPassword(), mobileno, tempStr, 88L }); // 参数，可以多个
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println(ret);
//                System.out.println("Send结束!");
//        	}
//        }
//        
////		String ret = Active(config);
////		String retno = ret.substring(ret.indexOf("<retno>") + 7, ret.indexOf("</retno>"));
////		if (!"0".equals(retno)) {
////			Logout(config);
////		}
//	
//    }
//    /**
//     * 根据手机号码区分运营商
//     * @param str
//     * @return
//     */
//    public static int isRightOperators(String str)
//    {
//        int msg = 0;//错误
//        if (str.length() == 11){
//            String temp = str.substring(0, 3);
//            if (temp.equals("134") || temp.equals("135") || temp.equals("136") ||
//                temp.equals("137") || temp.equals("138") || temp.equals("139")|| 
//                temp.equals("147")|| temp.equals("150")|| temp.equals("151")|| 
//                temp.equals("152")|| temp.equals("157")|| temp.equals("158")||
//                temp.equals("159")|| temp.equals("187")|| temp.equals("188")){
//                msg = 1;//移动
//            }else if (temp.equals("130") || temp.equals("131") || temp.equals("132") || temp.equals("156")){
//            	msg = 2;//联通
//            }else if (temp.equals("133") || temp.equals("153") ||temp.equals("189")){
//            	msg = 3;//电信
//            }
//        }
//        return msg;
//    }
//   
//    private static List<String> splitUserData(String userDataAll,String msgType) {
//        List<String> userDataPart=new ArrayList<String>();
//
//        String tempStr[]=userDataAll.trim().split("\\|");
//        String temp="";
//        int userdata_length;
//        String continueStr[]={
//            "（续一）",
//            "（续二）",
//            "（续三）",
//            "（续四）",
//            "（续五）",
//            "（续六）",
//            "（续七）",
//            "（续八）",
//            "（续九）"};
//
//        for(int i=0;i<tempStr.length;i++){
//            temp=tempStr[i];
//            temp=(temp==null)?"":temp;
//            if(!temp.equals("")){
//                String userData=temp.trim();
//                userdata_length=userData.length();
//                int max_SMSBYTES=66;
//                if(userdata_length>max_SMSBYTES) { //内容超长处理
//                    try{
//                        String strPart="";
//                        msgType=(msgType==null)?"":msgType;
//                        if(msgType.equals("4")){
//                            int allNum=((userData.length()+65)/66); //总个数
//                            int sn=0;
//                            while((userdata_length=userData.length())>60){
//                                strPart="<1>"+allNum+"</1><2>"+(sn+1)+"</2><3>"+userData.substring(0,60)+"</3>";
//                                userDataPart.add(strPart);
//                                userData=userData.substring(60,userdata_length);
//                                sn++;
//                            }
//                            userDataPart.add("<1>"+allNum+"</1><2>"+(sn+1)+"</2><3>"+userData+"</3>");
//                        }else{
//                            strPart=userData.substring(0,max_SMSBYTES);
//                            // 在GB2312下分割
//                            userDataPart.add(strPart);
//                            userData=userData.substring(max_SMSBYTES,userdata_length);
//
//                            int sn=0;
//                            while((userdata_length=userData.length())>(max_SMSBYTES-4)) {
//                                strPart=userData.substring(0,max_SMSBYTES-4);
//                                userDataPart.add(continueStr[sn]+strPart);
//                                userData=userData.substring(max_SMSBYTES-4,userdata_length);
//                                sn++;
//                            }
//                            userDataPart.add(continueStr[sn]+userData);
//                        }
//                    }
//                    catch(Exception e) {
//                        System.out.println("splitUserData(..) error :"+e.toString());
//                    }
//                }else{
//                    userDataPart.add(userData);
//                }
//            }
//        }
//        return userDataPart;
//    }
//	
//}
