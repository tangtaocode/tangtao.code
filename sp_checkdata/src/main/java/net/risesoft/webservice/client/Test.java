package net.risesoft.webservice.client;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		PortalsServerPortType service = new PortalsServer().getPortalsServerHttpPort();
		ArrayOfDbsx arr = service.dbsxView("xx_rf");
		List<Dbsx> list = arr.getDbsx(); 
		System.out.println("====待办=======");
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getUsername().getValue());
			System.out.println(list.get(i).getUrl().getValue());
			System.out.println(list.get(i).getType().getValue());
			System.out.println(list.get(i).getTitle().getValue());
			System.out.println(list.get(i).getJjcd().getValue());
			System.out.println(list.get(i).getDept().getValue());
			System.out.println(list.get(i).getPreSubmitDate().getValue());
		}
	}
}
