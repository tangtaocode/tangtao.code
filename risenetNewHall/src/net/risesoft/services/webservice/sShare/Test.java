package net.risesoft.services.webservice.sShare;

import java.util.Iterator;
import java.util.Map;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Request request =new Request();
		String ID="BA-2010-0121";
//		Map map = request.GetJSYDGHXKZDataById(ID);	//��ȡ
		
		ID="LG-2013-00361";		
		Map map = request.GetJSGCGHXKZDataById(ID);
		Iterator it = map.entrySet().iterator();  
        while (it.hasNext()) {  
            Map.Entry e = (Map.Entry) it.next();  
            System.out.println("Key: " + e.getKey() + "; Value: " + e.getValue());  
        }
//		CompareOthers compare=new CompareOthers();
//		compare.compare("�����н��ٵ���ó�����޹�˾","192247904");//�ȶ�
	}

}
