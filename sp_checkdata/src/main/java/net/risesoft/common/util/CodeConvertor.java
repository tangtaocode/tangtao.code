package net.risesoft.common.util;

/***
 * 
 * @author wjm
 *
 */
public class CodeConvertor {
	
	private static String hexStr =  "0123456789ABCDEF";   
	
	/**  
     *   
     * @param hexString  
     * @return 将十六进制转换为字节数组  
     */  
    public static byte[] HexStringToBinary(String hexString){   
        //hexString的长度对2取整，作为bytes的长度   
        int len = hexString.length()/2;   
        byte[] bytes = new byte[len];   
        byte high = 0;//字节高四位   
        byte low = 0;//字节低四位   
  
        for(int i=0;i<len;i++){   
             //右移四位得到高位   
             high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);   
             low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));   
             bytes[i] = (byte) (high|low);//高地位做或运算   
        }   
        return bytes;   
    }
    
    /**  
     *   
     * @param bytes  
     * @return 将二进制转换为十六进制字符输出  
     */  
    public static String BinaryToHexString(byte[] bytes){   
           
        String result = "";   
        String hex = "";   
        for(int i=0;i<bytes.length;i++){   
            //字节高4位   
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));   
            //字节低4位   
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));   
            result +=hex;   
        }   
        return result;   
    } 
    
    public static byte[] base64ToBinary(byte[] bytes){
    	return Base64.decode(bytes);
    }
    
    public static String binaryToBase64(byte[] bytes){
    	return new String(Base64.encode(bytes));
    }
    
    public static String base64ToHex(String base64){
    	if(base64 == null)
    		return null;    	
    	return CodeConvertor.BinaryToHexString(CodeConvertor.base64ToBinary(base64.getBytes()));
    }
    
    public static String hexToBase64(String hex){
    	if(hex == null){
    		return null;
    	}
    	return CodeConvertor.binaryToBase64(CodeConvertor.HexStringToBinary(hex));
    }
    
    public static boolean isUID(String id){
    	if(id == null){
    		return false;
    	}
    	if(id.length() == 23 && id.startsWith("_")){
    		return true;
    	}else
    		return false;
    }
    
    public static boolean isRiseGUID(String id){
    	if(id == null){
    		return false;
    	}
    	if(id.length() == 38 && id.startsWith("{") && id.endsWith("}")){
    		return true;
    	}else
    		return false;
    }
    
    public static String UID2RiseGUID(String UID){
    	if(CodeConvertor.isUID(UID)){
    		//'_'不属于64进制字符，要去除；长度要补足24位，在前面加两个0
    		String base64 = "00" + UID.substring(1);
    		//得到长度36位的16进度字符串，在前后加花括号补足38位
    		String riseGUID = "{" + CodeConvertor.base64ToHex(base64) + "}";
    		return riseGUID;
    	}else{
    		return UID;
    	}
    }
    
    public static String riseGUID2UID(String riseGUID){
    	if(CodeConvertor.isRiseGUID(riseGUID)){
    		//去除前后的花括号
    		String hex = riseGUID.substring(1,riseGUID.length() - 1);
    		String UID = CodeConvertor.hexToBase64(hex);
    		//去除前两位0再加上一位_，得到UID
    		return "_" + UID.substring(2);
    	}else{
    		return riseGUID;
    	}
    }

}
