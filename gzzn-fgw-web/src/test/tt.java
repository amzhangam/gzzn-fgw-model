import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javassist.expr.NewArray;

import net.sf.json.JSONObject;

import com.gzzn.fgw.model.Pjbaseinfo;
import com.gzzn.fgw.model.XmsbXmlx;
import com.gzzn.fgw.webUtil.PropertiesUtil;
import com.sun.xml.internal.rngom.digested.DChoicePattern;


public class tt {
	private static Properties prop = PropertiesUtil.getProperties("projectProperty.properties");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigInteger b = new BigInteger("44010120140711100108955555555555777777777777777777777777777777777777");
		String c = b.toString(2);
		System.out.println(c);
		List<String> aa = new ArrayList<String>();
		aa.add("1");
		aa.add("2");
		aa.add("3");
		aa.add("4");
		aa.add("5");
		List<String> bb = aa.subList(3,5);
		System.out.println(bb.size());
//		System.out.println(new Date().getTime());
//		Long.valueOf("44010120140711100108");
//		Pjbaseinfo d1 = new Pjbaseinfo();
//        d1.setProjectcode("abc");
//        d1.setProjectname("财务部");
//        d1.setAuditdeptname("北京");
//        XmsbXmlx xmsbXmlx1 = new XmsbXmlx();
//        xmsbXmlx1.setXmlxId(1);
//        xmsbXmlx1.setXmlxmc("你好");
//        d1.setXmsbXmlx(xmsbXmlx1);
//        d1.setRecordertime(new Date());
//        d1.setAuditdept(5);
//        try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        Pjbaseinfo d2 = new Pjbaseinfo();
//        d2.setProjectcode("abc");
//        d2.setProjectname("市场部");
//        d2.setAuditdeptname("南京");
//        d2.setAuditdept(8);
//        d2.setRecordertime(new Date());
//        XmsbXmlx xmsbXmlx2 = new XmsbXmlx();
//        xmsbXmlx2.setXmlxId(1);
//        xmsbXmlx2.setXmlxmc("害虫");
//        d2.setXmsbXmlx(xmsbXmlx2);
//        JSONObject obj1 = JSONObject.fromObject(d1);
//        JSONObject obj2 = JSONObject.fromObject(d2);
//        Map record = new HashMap();
//        for(int i=0;i<obj1.names().size();i++){
//            if(isTheSame(obj1, obj2, i)){
//                continue;
//            }
//            if(!isTheSame(obj1, obj2, i)){
//                loadRecord(obj1, obj2, record, i);
//            }
//        }
//        String contentString = "";
//        Iterator iterator = record.keySet().iterator();
//        while (iterator.hasNext()) {
//			Object key = (Object) iterator.next();
//			Object value = record.get(key);
////			System.out.println(prop.getProperty(key.toString())+" "+value);
//			contentString+="\n" + prop.getProperty(key.toString())+"\n";
//			contentString+=value+"\n";
//		}
//        System.out.println(contentString);	
    }
	private static void loadRecord(JSONObject obj1, JSONObject obj2,
            Map record, int i) {
		
        record.put(obj1.names().get(i), "修改前值为："+obj1.get(obj1.names().get(i).toString())+"\n修改后值为："+obj2.get(obj2.names().get(i).toString()));
    }
    private static boolean isTheSame(JSONObject obj1, JSONObject obj2, int i) {
        return obj1.get(obj1.names().get(i)).equals(obj2.get(obj2.names().get(i)));
	}

}
