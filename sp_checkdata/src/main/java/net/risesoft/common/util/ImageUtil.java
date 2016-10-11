package net.risesoft.common.util;

public class ImageUtil {
	
	public final static boolean isPictrure(String fileName){
		if(fileName == null){
			return false;
		}
		String fn = fileName.toUpperCase();		
		return (fn.endsWith(".BMP") || fn.endsWith(".JPEG") || fn.endsWith(".JPG") || fn.endsWith(".GIF") || fn.endsWith(".PNG") || fn.endsWith(".TIFF") || fn.endsWith(".TIF"));
	}

}
