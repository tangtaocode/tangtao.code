package net.risesoft.approve.risefile.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.risesoft.approve.risefile.exception.RiseFileSystemException;
import net.risesoft.approve.risefile.filter.FileFilter;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangdong RiseFileConfigManager为获得RiseFileConfig的API。
 */
public class RiseFileConfigManager implements RiseConfigConst {

	protected static Logger log = LoggerFactory.getLogger(RiseFileConfigManager.class);
	private static RiseFileConfig root;

	private static HashMap appRiseFileConfigs=new HashMap();

	private static HashMap classesMap=new HashMap();
	
	private static int threadYealdSize=50*1024*1024;
	
	private static int threadMemorySize=10240;
	
	static{
		loadConfig();
	}
	
	public static String getTempPath(){
		return root.getFileRoot();
	}
	
	/**
	 * 根据Appname返回config， 如果app配置config则返回两级config 否则返回单级root,config
	 * 
	 * @param appName
	 * @return net.risesoft.component.risefile.config.RiseFileConfig
	 * @roseuid 45012C910177
	 */
	public static RiseFileConfig getAppFileConfig(String appName) {
		RiseFileConfig config = (RiseFileConfig) appRiseFileConfigs
				.get(appName);
		if (config == null) {
			config = new RiseFileConfig(appName);
			config.setParentConfig(root);
			appRiseFileConfigs.put(appName, config);
		}
		return config;
	}

	/**
	 * 通过appname自动装载ParentConfig，就自动形成了一个三级的config
	 */
	public static RiseFileConfig getExtendedFileConfig(String appName) {
		RiseFileConfig config = new RiseFileConfig(appName);
		config.setParentConfig(getAppFileConfig(appName));
		return config;
	}

	// 通过名字从配置文件中读取对应的类
	public static FileFilter getFilter(String name)
			throws RiseFileSystemException {
		return (FileFilter) getMapObject(FILTER + "." + name);
	}

	// 通过名字从配置文件中读取对应的类
//	public static RiseFileHandle getFileHandle(String name)
//			throws RiseFileSystemException {
//		return (RiseFileHandle) getMapObject(FILEHANDLE + "." + name);
//	}

	// 通过名字从配置文件中读取对应的类
//	public static RiseInputStreamHandle getInputstreamHandle(String name)
//			throws RiseFileSystemException {
//		return (RiseInputStreamHandle) getMapObject(INPUTSTREAMHANDLE + "."
//				+ name);
//	}

	// 通过名字从配置文件中读取对应的类
//	public static RiseOutputStreamHandle getOutputstreamHandle(String name)
//			throws RiseFileSystemException {
//		return (RiseOutputStreamHandle) getMapObject(OUTPUTSTREAMHANDLE
//				+ "." + name);
//	}
	/**
	 * 批量装载类：
	 * @param nameList：类索引的String集合
	 * @param prex：前缀，表明是
	 * 				FILTER
	 * 				FILEHANDLE
	 * 				INPUTSTREAMHANDLE
	 * 				OUTPUTSTREAMHANDLE、
	 * @return
	 */
	public static List getMapObjectBatch(List nameList,String prex)throws RiseFileSystemException {
		ArrayList l=new ArrayList();
		for (Iterator iter = nameList.iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			l.add(getMapObject(prex+"."+name));
		}
		return l;
	}
	
	private static Object getMapObject(String fullname)
			throws RiseFileSystemException {
		Object object = null;
		String className = (String) classesMap.get(fullname);
		if (className == null) {
			throw new RiseFileSystemException(
					"此二次开发类没有在risefile.config.xml做过配置：" + fullname);
		}

		try {
			object = (Class.forName(className)
					.newInstance());
		} catch (Exception e) {
			log.error("装载过滤器异常", e);
			throw new RiseFileSystemException("装载过滤器异常：" + fullname);
		}
		return object;
	}
	

	/**
	 * 读入配置文件信息，首先创建Global，然后逐个创建AppRiseFileConfig，并将Global设置
	 * 为每一个app的parent，再将所有的app保存在一个Hash表中。
	 * 
	 * @roseuid 45012CFA0290
	 */
	private static void loadConfig() {
		DocumentFactory factory = DocumentFactory.getInstance();
		SAXReader saxReader = new SAXReader(factory);
		Document dment = null;
			try {
				InputStream inputStream = RiseFileConfig.class.getResourceAsStream("/risefile.config.xml");
				if(inputStream==null){
					log.error("无法找到riseFile配置文件：risefile.config.xml");
					return;
				}

			
				//SAXReader builder = new SAXReader("org.apache.xerces.parsers.SAXParser");
				//Document doc = builder.build(inputStream);
				//Element docroot = doc.getRootElement();
				dment = saxReader.read(inputStream);
				Element docroot = dment.getRootElement();
				List<Element> elementsList=null;
				Element element=null;
				Element elementChild=null;
				
				String mapKey=null;
				String mapValue=null;
				//装载map
				/*element= docroot.element("filterMap");
				elementsList=element.attribute("fileFilter");
				for (Iterator iter = elementsList.iterator(); iter.hasNext();) {
					 elementChild = (Element) iter.next();
					 mapKey=RiseFileConfigManager.FILEHANDLE+"."+elementChild.getAttribute("name").getValue();
					 mapValue=elementChild.getAttribute("class").getValue();
					 classesMap.put(mapKey,mapValue);
				}*/
				elementsList = docroot.elements("filterMap");
				for (Iterator it = elementsList.iterator(); it.hasNext();) {
				   Element elm = (Element) it.next();
				   mapKey=RiseFileConfigManager.FILEHANDLE+"."+elm.element("fileFilter").attributeValue("name");
				   mapValue=elm.element("fileFilter").attributeValue("class");
					 classesMap.put(mapKey,mapValue);
				}
				
				/*element= docroot.getChild("streamHandleMap");
				elementsList=element.getChildren("streamHandler");
				for (Iterator iter = elementsList.iterator(); iter.hasNext();) {
					 elementChild = (Element) iter.next();
					 mapKey=RiseFileConfigManager.OUTPUTSTREAMHANDLE+"."+elementChild.getAttribute("name").getValue();
					 mapValue=elementChild.getAttribute("outputclass").getValue();
					 classesMap.put(mapKey,mapValue);
					 mapKey=RiseFileConfigManager.INPUTSTREAMHANDLE+"."+elementChild.getAttribute("name").getValue();
					 mapValue=elementChild.getAttribute("inputclass").getValue();
					 classesMap.put(mapKey,mapValue);
				}*/
				elementsList = docroot.elements("streamHandleMap");
				for (Iterator it = elementsList.iterator(); it.hasNext();) {
				   Element elm = (Element) it.next();
				     mapKey=RiseFileConfigManager.OUTPUTSTREAMHANDLE+"."+elm.element("streamHandler").attributeValue("name");
					 mapValue=elm.element("streamHandler").attributeValue("name");
					 classesMap.put(mapKey,mapValue);
					 mapKey=RiseFileConfigManager.INPUTSTREAMHANDLE+"."+elm.element("streamHandler").attributeValue("name");
					 mapValue=elm.element("streamHandler").attributeValue("name");
					 classesMap.put(mapKey,mapValue);
				}
				
				//装载rootconfig
				root=parseRiseFileConfig(docroot);
				//装载appconfig
				elementsList=docroot.elements("app");
			    for (Iterator iter = elementsList.iterator(); iter.hasNext();) {
					 elementChild = (Element) iter.next();
					 RiseFileConfig config= parseRiseFileConfig(elementChild);
					 config.setParentConfig(root);
					 appRiseFileConfigs.put(elementChild.attributeValue("name"),config);
				}

				inputStream.close();
				log.info("装载riseFile配置文件：risefile.config.xml完成！");
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (DocumentException e) {
				log.error("装载riseFile配置文件：risefile.config.xml加载异常", e);
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
			}
	}
	
	
	private static RiseFileConfig parseRiseFileConfig(Element configElement){
		
		List elementsList=null;
		Element element=null;
		Element elementChild=null;
		List list=null;
		
		RiseFileConfig config =new RiseFileConfig();
		
		element=configElement.element("saveMode");
		if(element!=null){
			config.setSaveMode(element.getText());
		}
		element=configElement.element("fileRoot");
		if(element!=null){
			config.setFileRoot(element.getText());
		}
		element=configElement.element("uploadMode");
		if(element!=null){
			config.setUploadMode(element.getText());
		}
		element=configElement.element("fileFilters");
		if(element!=null){
			if(element.attributeValue("isExtends")!=null&&element.attributeValue("isExtends").equals("true")){
				config.setFilterExtends(true);
			}
			elementsList=element.elements("fileFilter");
			list=new ArrayList();
			for (Iterator iter = elementsList.iterator(); iter.hasNext();) {
				elementChild=(Element)iter.next();
				list.add(elementChild.attributeValue("name"));
			}
			config.setFilters(list);
		}
		element=configElement.element("fileHandlers");
		if(element!=null){
			if(element.attribute("isExtends")!=null&&element.attribute("isExtends").equals("true")){
				config.setHandleExtends(true);
			}
			elementsList=element.elements("fileHandler");
			list=new ArrayList();
			for (Iterator iter = elementsList.iterator(); iter.hasNext();) {
				elementChild=(Element)iter.next();
				list.add(elementChild.attribute("name").getValue());
			}
			config.setFileHandles(list);
		}
		element=configElement.element("streamHandles");
		if(element!=null){
			if(element.attribute("isExtends")!=null&&element.attribute("isExtends").getValue().equals("true")){
				config.setHandleExtends(true);
			}
			elementsList=element.elements("streamHandle");
			list=new ArrayList();
			for (Iterator iter = elementsList.iterator(); iter.hasNext();) {
				elementChild=(Element)iter.next();
				list.add(elementChild.attribute("name").getValue());
			}
			config.setStreamHandles(list);
		}
		
		return config;
	}
	
	public static void main(String[] args){
		loadConfig();
	}

	public static int getThreadMemorySize() {
		return threadMemorySize;
	}

	public static void setThreadMemorySize(int threadMemorySize) {
		RiseFileConfigManager.threadMemorySize = threadMemorySize;
	}

	public static int getThreadYealdSize() {
		return threadYealdSize;
	}

	public static void setThreadYealdSize(int threadYealdSize) {
		RiseFileConfigManager.threadYealdSize = threadYealdSize;
	}
	
	public static void main(){
		RiseFileConfigManager.getThreadYealdSize();
	}
}
