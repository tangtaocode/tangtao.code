/**
 * @Project Name:risenet-sp-approve
 * @File Name: FileUtil.java
 * @Package Name: net.risesoft.util
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月28日 下午3:36:46
 */
package net.risesoft.util;

import java.io.File;

/**
 * @ClassName: FileUtil.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月28日 下午3:36:46
 * @version 
 * @since JDK 1.7
 */
public class FileUtil {
	
	public static final String WINDOWSPREFIX = "E:\\";
	public static final String LINUXPREFIX = "/share/";
	
	public static final String FILEPATH_STUFF = "risefile\\gmsp\\risefile\\stuff\\";
	public static final String FILEPATH_WSSB = "risefile\\gmsp\\risefile\\wssb\\";
	
	//共享材料完整路径：前缀+FILEPATH_STUFF+stuffdataguid+文件名
	//网上申报材料完整路径：前缀+FILEPATH_WSSB+instanceguid+材料guid+文件名
	
	public static String getBasePath(String operate){
    	String basePath = "";
    	if (RisesoftCommonUtil.print_banjieDan.equals(operate)) {//
			basePath =  "banjie";
		} else if (RisesoftCommonUtil.print_buQiBuZhengDan.equals(operate)) {
			basePath = "buqibuzheng";
		} else if(RisesoftCommonUtil.print_shouliDan.equals(operate)){
			basePath = "shouli";
		}else{
			basePath = "zhengzhao";
		}
    	return basePath;
    }
	
	public static void createNewFile(String path) {  
		try {  
			String[] lists=path.split("\\.");  
			int lastLength=lists[0].lastIndexOf("\\");  
			//得到文件夹目录  
			String dir=lists[0].substring(0, lastLength);  
			//得到文件名称  
			String fileName=lists[0].substring(lastLength);  
			//得到路径e:\a\b之后,先创建文件夹  
			if(CreateMultilayerFile(dir)==true){  
				File filePath = new File(path);  
				if (!filePath.exists()) {  
					filePath.createNewFile();  
				}  
			}  
		} catch (Exception e) {  
			System.out.println("新建文件操作出错: "+e.getMessage());  
			e.printStackTrace();  
		}  
		}  

	//创建文件夹--多层  
	private static boolean CreateMultilayerFile(String dir){  
	    try {  
	           File dirPath = new File(dir);  
	           if (!dirPath.exists()) {  
	               dirPath.mkdirs();  
	           }  
	       } catch (Exception e) {  
	          System.out.println("创建多层目录操作出错: "+e.getMessage());  
	           e.printStackTrace();  
	           return false;  
	       }  
	    return true;  
	} 

}

