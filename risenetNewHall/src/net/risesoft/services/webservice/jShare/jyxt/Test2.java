package net.risesoft.services.webservice.jShare.jyxt;


public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
//			JyxtDeal.getRYRZQK_XML("44030020110009001","512324740225735");
			
			String xml = "<![CDATA[<DataSet>"+
			"<DataTable>"+
			"<ORG_CODE>T1923463631</ORG_CODE>"+
			"<CORP_NAME>深圳市施友建设监理有限公司</CORP_NAME>"+
			"<XMBH>44030020100393</XMBH>"+
			"<XMMC>深南电路高端通讯设备电子装联项目</XMMC>"+
			"<IsBig>0</IsBig>"+
			"<GCBH>44030020100393001</GCBH>"+
			"<GCMC>深南电路龙岗二厂区（厂房办公及宿舍）</GCMC>"+
			"<EMP_ID>430681197003035018</EMP_ID>"+
			"<EMP_NAME>周福新</EMP_NAME>"+
			"<EMP_TYPE>2</EMP_TYPE>"+
			"<EMP_CERT_NO>0097446</EMP_CERT_NO>"+
			"<START_DATE>2010-08-31</START_DATE>"+
			"<END_DATE>2011-01-08</END_DATE>"+
			"<IS_Complete>0</IS_Complete>"+
			"<REMARK>1</REMARK>"+
			"<Modifier>1</Modifier>"+
			"<ModifyGovernNO>1</ModifyGovernNO>"+
			"<ModifyTime>2012-08-14 13:44:19</ModifyTime>"+
			"</DataTable>"+
		  	"</DataSet>]]>"; 
//			JyxtDeal.editRYRZQK_XML(xml);
			
			JyxtDeal.checkIsCanRZ("1","1","1","1");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
}
