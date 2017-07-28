package net.risesoft.approve.risefile.exchange;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;
import net.risesoft.approve.risefile.content.RiseFileItemImp;
import net.risesoft.model.Person;




import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
/*import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RiseFileExchange {
	protected static Logger log = LoggerFactory.getLogger(RiseFileExchange.class);
	
	
	public static boolean isExchanging=false; 
	
	public static String exchangingAppName="";
	
	public static int exchangingGuage=0;;//80代表80%.
	
	public static String errormsg="";
	
	private static Map exchangeMap=new HashMap();
	
	private static String getExchangeMap(String key){
		return (String)exchangeMap.get(key);
	}
	
	public static void excute(String fullfileName,Person user){
		//start reset
		isExchanging=true;
		exchangingGuage=0;
		errormsg="";
		
		
		String appname=null;
		String table=null;
		String whereSql=null;
		exchangeMap=new HashMap();
		
		
		try {
			InputStream inputStream =  new FileInputStream(fullfileName);
			
			DocumentFactory factory = DocumentFactory.getInstance();
			SAXReader saxReader = new SAXReader(factory);
			Document doc = saxReader.read(inputStream);
			Element docroot = doc.getRootElement();
			
			List elementsList=null;
			Element elementChild=null;
			
			appname=docroot.attributeValue("name");
			exchangingAppName=appname;
			table=docroot.attributeValue("table");
			whereSql=docroot.attributeValue("wheresql");
			
			elementsList= docroot.elements();
			for (Iterator iter = elementsList.iterator(); iter.hasNext();) {
				 elementChild = (Element) iter.next();
				 exchangeMap.put(elementChild.getName(),elementChild.getText());
			}
			inputStream.close();
			log.info("装载配置文件："+fullfileName+"完成！");
			
		} catch (Exception e) {
			//end reset
			isExchanging=false;
			exchangingGuage=0;
			log.error(e.getMessage());
			
			return;
		}
		
		//Session session = HibernateFactory.getSessionFactoty().openSession();
		//Transaction tx = session.beginTransaction();
		// 保存文件属性
		Connection con=null;
		Statement stmt=null;
		int count=0;
		int num=0;
		int multiple=0;
		String tempFileName=RiseFileConfigManager.getTempPath()+File.separator+"doc"+File.separator+"temp.update";
		try {
//			con=session.connction();
			stmt= con.createStatement();
			ResultSet rs=stmt.executeQuery("select count(*) count from "+table+" where "+" "+whereSql);
			if(rs.next()){
				count=rs.getInt("count");
			}
			rs.close();
			rs=stmt.executeQuery("select * from "+table+" where "+" "+whereSql);
			
			RiseFileConfig config = null;
			RiseFileItemImp fileItem = null;
			InputStream is = null;
			OutputStream os = null;
			double d;
			
			while(rs.next()){
				num++;
				config=RiseFileConfigManager.getExtendedFileConfig(appname);
/*				config.setFileboxName(rs.getString(getExchangeMap("FILEBOXNAME")));
				config.setFileRoot(rs.getString(getExchangeMap("SUBAPPNAME")));
				config.setMajorVersion(rs.getString(getExchangeMap("MAJORVERSION")));
				config.setAppInstGUID(rs.getString(getExchangeMap("APPINSTGUID")));
				config.setMajorVersionName(rs.getString(getExchangeMap("MAJORNAME")));*/
				config.setFileboxName(getExchangeMap("FILEBOXNAME"));
				config.setFileRoot(getExchangeMap("SUBAPPNAME"));
				config.setMajorVersion(getExchangeMap("MAJORVERSION"));
				config.setAppInstGUID(rs.getString(getExchangeMap("APPINSTGUID")));
				config.setMajorVersionName(getExchangeMap("MAJORNAME"));
				
				fileItem = new RiseFileItemImp();
				fileItem.setCurrentUser(rs.getString(getExchangeMap("CREATORGUID")));
				fileItem.setFileGUID(rs.getString(getExchangeMap("FILEGUID")));
				fileItem.setFileName(rs.getString(getExchangeMap("FILENAME")));
				fileItem.setFileNameExt(rs.getString(getExchangeMap("FILENAMEEXT")));
				fileItem.setTitile(rs.getString(getExchangeMap("TITILE")));
				fileItem.setFileType(rs.getString(getExchangeMap("FILETYPE")));
				is = rs.getBlob(getExchangeMap("CONTENT")).getBinaryStream();
				
				//在Oracle环境下is返回为OracleBlobInputStream，返回的is.available()为零，不是正确的数据。
				//又不支持set正确的数据，所以只好，缓存到文件系统中。
				//是否有更好的方式？？
				os = new FileOutputStream(tempFileName);
				int bytesRead = 0;
			    byte[] buffer = new byte[1026];
			    while ((bytesRead = is.read(buffer, 0, 1026)) != -1) {
			    	os.write(buffer, 0, bytesRead);
			    }
			    is.close();
			    is = new FileInputStream(tempFileName);
			    //Oracle问题end
			    File tFile = new File(tempFileName);
			    d = Double.valueOf(tFile.length()+"").doubleValue();
				d = d/1024/1024;
				fileItem.setFileSize(d);
				config.getFileManager().save(fileItem,config,is,user);
				is.close();
				os.close();
				if(num==100){
					num=0;
					multiple++;
					exchangingGuage=multiple*100/count;
					//tx.commit();
				}
			}
			exchangingGuage=100;
			//tx.commit();
		} catch (Exception e) {
			//tx.rollback();
			log.error(e.getMessage());
		} finally{
			//over reset
			isExchanging=false;
			exchangingGuage=0;
			
			File tempFile = new File(tempFileName);
			tempFile.delete();
		}
	}
}
