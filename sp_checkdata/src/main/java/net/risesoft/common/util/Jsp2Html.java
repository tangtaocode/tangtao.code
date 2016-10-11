package net.risesoft.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Jsp2Html {
	private String baseUrl = "D:\\doc";
	private String prefixFold = "";//"\\n";
	private String foldUrl;

	/* * 类的功能：封装对目录和文件的操作 * */

	/* * name:setUrl * function:设置目录和文件操作的时使用的根路径（ｗｅｂ应用程序的根文件夹的绝对路径） * */
	public void setUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private String getBaseUrl() {
		return baseUrl;
	};

	/* * name:setFoldUrl * function:设置文件目录结构字符串 * */
	public void setFoldUrl(String fileUrl) {
		this.foldUrl = fileUrl;
	}

	public String getFoldUrl() {
		return foldUrl;
	}

	private String getPrefixFold() {
		return prefixFold;
	}
	
	public String openSource(String sourceUrl){
		String sCurrentLine = "";
		StringBuffer temp = new StringBuffer();
		InputStream l_urlStream = null;
		try{					
			URL l_url = new URL(sourceUrl);
			HttpURLConnection l_conn = (HttpURLConnection) l_url.openConnection();
			l_conn.connect();
			l_urlStream = l_conn.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				temp.append(sCurrentLine + "\r\n");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(l_urlStream != null){
				try{
					l_urlStream.close();
				}catch(IOException ie){
					ie.printStackTrace();
				}
			}
		}
		return temp.toString();
	}
	
	public String openSource(String sourceUrl, String charsetName){
		String sCurrentLine = "";
		StringBuffer temp = new StringBuffer();
		InputStream l_urlStream = null;
		try{					
			URL l_url = new URL(sourceUrl);
			HttpURLConnection l_conn = (HttpURLConnection) l_url.openConnection();
			l_conn.connect();
			l_urlStream = l_conn.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, charsetName));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				if("".equals(sCurrentLine)){
					continue;
				}else if((sCurrentLine.replace("\t", "")).length() == 0){
					continue;
				}
				temp.append(sCurrentLine + "\r\n");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(l_urlStream != null){
				try{
					l_urlStream.close();
				}catch(IOException ie){
					ie.printStackTrace();
				}
			}
		}
		return temp.toString();
	}

	/* * name;createHtml * function:创建html文件，内容是content，名称是id.html * * */
	public boolean createHtml(String content, String id) {
		boolean bool = false;
		String cont = content;
//		String id1 = generateFileNameStr();
		String fileName = id + ".html";
//		FileWriter toFile;
		BufferedWriter out;
		OutputStreamWriter osw = null;
		try {
			if (formatUrl()) {
				osw = new OutputStreamWriter(new FileOutputStream(getFoldUrl() + "\\" + fileName), "UTF-8");
//				toFile = new FileWriter(getFoldUrl() + "\\" + fileName);
				out = new BufferedWriter(osw);//new BufferedWriter(toFile);
				out.write(cont, 0, cont.length());
				out.close();
//				toFile.close();
				osw.close();
				bool = true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bool;
	}
	
	public boolean createHtml(String content, String id, String charsetName) {
		boolean bool = false;
		String cont = content;
		if(charsetName == null){
			charsetName = "UTF-8";
		}
		String fileName = id + ".html";
		BufferedWriter out;
		OutputStreamWriter osw = null;
		try {
			if (formatUrl()) {
				osw = new OutputStreamWriter(new FileOutputStream(getFoldUrl() + "\\" + fileName), charsetName);
				out = new BufferedWriter(osw);
				out.write(cont, 0, cont.length());
				out.close();
				osw.close();
				bool = true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bool;
	}
	
	public boolean createFile(String content, String id, String fileType, String charsetName) {
		boolean bool = false;
		String cont = content;
		if(fileType == null){
			fileType = "html";
		}
		if(charsetName == null){
			charsetName = "UTF-8";
		}
		String fileName = id + "." + fileType;
		BufferedWriter out;
		OutputStreamWriter osw = null;
		try {
			if (formatUrl()) {
				osw = new OutputStreamWriter(new FileOutputStream(getFoldUrl() + "\\" + fileName), charsetName);
				out = new BufferedWriter(osw);
				out.write(cont, 0, cont.length());
				out.close();
				osw.close();
				bool = true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bool;
	}
	
	public boolean createFile(byte [] bg, String id, String fileType) {
		boolean bool = false;
//		String cont = content;
		String fileName = null;
		if(fileType != null){
			fileName = id + "." + fileType;
		}else
			fileName = id;
		
		
		OutputStream bos = null;
		try {
			if (formatUrl()) {
//				osw = new OutputStreamWriter(new FileOutputStream(getFoldUrl() + "\\" + fileName), charsetName);
//				out = new BufferedWriter(osw);
//				out.write(cont, 0, cont.length());
//				out.close();
//				osw.close();
				FileOutputStream fos = new FileOutputStream(getFoldUrl() + "\\" + fileName);
				bos = new BufferedOutputStream(fos);
				bos.write(bg);
				bos.close();
				bool = true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bool;
	}

	// createHtml
	/* * name:deleteHtml * function:删除指定ｉｄ（文件名称去除后缀）的html文件 * * */
	public boolean deleteHtml(String htmlId) {
		boolean bool = false;
//		String name = htmlId;
		if (htmlId == null) {
			return bool;
		}
//		String yearStr = htmlId.substring(0, 4);
//		String monthDayStr = htmlId.substring(4, 8);
//		String fileUrl = getBaseUrl() + getPrefixFold() + "\\" + yearStr + "\\"
//				+ monthDayStr + "\\" + htmlId + ".html";
		String fileUrl = getBaseUrl() + getPrefixFold() + "\\"  + htmlId + ".html";
		File file_del = new File(fileUrl);
		if (file_del.exists()) {
			file_del.delete();
			bool = true;// 删除成功标志
		}
		return bool;
	}
	
	public boolean deleteFile(String sPath) {
		boolean bool = false;
//		String name = htmlId;
		if (sPath == null) {
			return bool;
		}
//		String yearStr = htmlId.substring(0, 4);
//		String monthDayStr = htmlId.substring(4, 8);
//		String fileUrl = getBaseUrl() + getPrefixFold() + "\\" + yearStr + "\\"
//				+ monthDayStr + "\\" + htmlId + ".html";
		
		File file_del = new File(sPath);
		if (file_del.exists()) {
			file_del.delete();
			bool = true;// 删除成功标志
		}
		return bool;
	}
	
	/**  
	 * 删除目录（文件夹）以及目录下的文件  
	 * @param   sPath 被删除目录的文件路径  
	 * @return  目录删除成功返回true，否则返回false  
	 */  
	public boolean deleteDirectory(String sPath) {
		//如果sPath不以文件分隔符结尾，自动添加文件分隔符   
		if (!sPath.endsWith(File.separator)) {   
		     sPath = sPath + File.separator;   
		}   
		File dirFile = new File(sPath);   
		//如果dir对应的文件不存在，或者不是一个目录，则退出   
		if (!dirFile.exists() || !dirFile.isDirectory()) {   
		    return false;   
		}   
		boolean flag = true;   
		//删除文件夹下的所有文件(包括子目录)   
		File[] files = dirFile.listFiles();   
		for (int i = 0; i < files.length; i++) {   
		    //删除子文件   
		    if (files[i].isFile()) {   
		        flag = deleteFile(files[i].getAbsolutePath());   
		        if (!flag) break;   
		    }else {  //删除子目录 
		            flag = deleteDirectory(files[i].getAbsolutePath());   
		            if (!flag) break;   
		       }   
		    }   
		    if (!flag) return false;   
		    //删除当前目录   
		    if (dirFile.delete()) {   
		        return true;   
		    } else {   
		        return false;   
		    } 
	}
	
	public boolean renameFile(String fileName, String destName) {
		boolean bool = false;

		if (fileName == null) {
			return bool;
		}

		String fileUrl = getBaseUrl() + getPrefixFold();
		File file_new = new File(fileUrl + "\\" + destName);
		File file_old = new File(fileUrl + "\\" + fileName);
		if (file_old.exists()) {
//			file_del.delete();
			if(file_new.exists()){
				file_new.delete();
				
			}
			file_old.renameTo(file_new);
			bool = true;// 删除成功标志
		}
		return bool;
	}

	/*
	 *  * name:formatUrl * function:检查当前日期的文件目录结构是否存在，若不存在，则创建对应的目录结构，并且若目录结构存在，
	 * * 则保存该目录结构字符串 保存 * *
	 */
	private boolean formatUrl() {
		/* 如果存放当前日期文件的目录结构不存在，则创建对应的目录结构 * */
		boolean bool = false;
		File file = new File(getBaseUrl());
		if (file.exists() && file.isDirectory()) {
			String foldStr = getBaseUrl() + getPrefixFold() + "\\" + getFoldUrl();//getTodayFilePath();
			File filePath = new File(foldStr);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (filePath.isDirectory()) {
				bool = true;
				setFoldUrl(foldStr);
			}
		}
		return bool;
	}

	// formatUrl()
	// 存放*.html的目录结构： .../.../根目录/年/月日/*.html /* * name:getTodayFilePath *
	// function:该方法返回文件名之前的部分呢 * */
//	private String getTodayFilePath() {
//		String tPath = "";
//		String dateStr[];
//		dateStr = new String[2];
//		String patternStr[] = { "yyyy", "MMdd" };
//		dateStr[0] = new SimpleDateFormat(patternStr[0]).format(new Date());
//		dateStr[1] = new SimpleDateFormat(patternStr[1]).format(new Date());
//		tPath = getBaseUrl() + getPrefixFold() + "\\" + dateStr[0] + "\\"
//				+ dateStr[1];
//		return tPath;
//	}

//	private String generateFileNameStr() {
//		String fileName = "";
//		fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		return fileName;
//	}

	/* * main 方法测试类是否正常工作 * * */

	public static void main(String args[]) {
		Jsp2Html jspToHtml = new Jsp2Html();
		jspToHtml.createHtml("aa", "11");
		System.out.println("create html success!!");
		jspToHtml.deleteHtml("20091031191355");
	}

}
