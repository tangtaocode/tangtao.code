package net.risesoft.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SuppressWarnings("rawtypes")
public class PropertiesUtil implements InitializingBean {

	/** Properties to be set */
	private Map systemProperties;

	/*
	 * 设置修改后的properties的属�?
	 */
	public void savePropertiy(String resourceUrl, String key, String value)
	{
		Properties prop = getProperties(resourceUrl);
		prop.setProperty(key, value);
		save(resourceUrl, prop);
	}

	/*
	 * 保存
	 */
	public void save(String resourceUrl, Properties prop)
	{
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(resourceUrl);
		try {
			File file = resource.getFile();
			FileOutputStream fos = new FileOutputStream(file);
			prop.store(fos, "");
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 获取properties文件中的�?��内容
	 */
	public Map<String, String> getAllProperties(String resourceUrl, List<String> list) {
		Properties prop = getProperties(resourceUrl);
		Map<String, String> result = new HashMap<String, String>();
		Enumeration e = prop.propertyNames();
		while (e.hasMoreElements())
		{
			String key = (String) e.nextElement();
			result.put(key, prop.getProperty(key));
		}
		return result;
	}

	/*
	 * 获取关键�?
	 */
	public List<String> getKeys(String resourceUrl)
	{
		Properties prop = getProperties(resourceUrl);
		List<String> list = new ArrayList<String>();
		Enumeration e = prop.propertyNames();
		while (e.hasMoreElements())
		{
			String key = (String) e.nextElement();
			list.add(key);
		}
		return list;
	}

	/*
	 * 获取资源
	 */
	public String getProperty(String resourceUrl, String source) {
		Properties prop = getProperties(resourceUrl);
		return prop.getProperty(source);
	}

	/*
	 * 获取Properties文件
	 */
	public Properties getProperties(String resourceUrl)
	{
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(resourceUrl);
		Properties prop = new Properties();
		try {
			InputStream in = resource.getInputStream();
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/** Sets the system properties */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (systemProperties == null || systemProperties.isEmpty()) {
			// No properties to initialize
			return;
		}

		Iterator i = systemProperties.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String value = (String) systemProperties.get(key);

			System.setProperty(key, value);
		}
	}

	public void setSystemProperties(Map systemProperties) {
		this.systemProperties = systemProperties;
	}
}
