package net.risesoft.utils.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringX {
	public static String replaceRegex(String str,String regex,String toValue){
		if(str==null) return str;
		Pattern p=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(str);
		while(m.find()){
			str=m.replaceFirst(toValue);
			m=p.matcher(str);
		}
		return str;
	}
	
	public static boolean match(String str,String regex){
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(str);
		return m.find();
	}
	
	public static String getNullString(String value){
		if(value==null||"null".equals(value))return "";
		else return value;
	}
	
	public static String getObjectsString(List objects,String split){
		String str="";
		if(objects==null) return null;
		for(int i=0;i<objects.size();i++){
			Object object=objects.get(i);
			if(object!=null) str+=object;
		}
		return str;
	}
	
	public static String abbreviate(String text,int size,String tail){
		if(text==null) return "";
		if(text.length()<size) return text;
		else return text.substring(0,size)+tail;
	}
	
	public static boolean isBlank(Object value){
		return value==null||"".equals(value.toString().trim())||"null".equals(value);
	}

	/**
	 * 处理double类型数据的，精度为小数点后2位
	 * @param d
	 * @return
	 */
	public static String foramtDouble(double d){
		return formatDigitalNumber(""+d, 2);				
	}	

	/**
	 * 处理String字符串，添加小数点后两位
	 * @param str
	 * @return
	 */
	public static String foramtString(String str){
		return formatDigitalNumber(str, 2);
	}		

	/**
	 * 字符串的显示格式
	 * @param str 处理的字符串
	 * @param digIndex 小数点在字符串中的位置
	 * @param scale 需要小数的后的位数
	 * @param digital2End 字符串中小数点后的位数
	 * @return
	 */
	public static String doubleSTRAddzero(String str,int digIndex, int scale,int digital2End){
		if(digital2End>scale){
			return str.substring(0,digIndex+scale+1);
		}else{
			StringBuffer sb=new StringBuffer();
			sb.append(str);
			int num=scale-digital2End;
			for(int i=0;i<num;i++){
				sb.append("0");
			}
			return sb.toString();
		}
	}
	
	/**
	 * 整数处理
	 * @param str 要处理的字符窜
	 * @param zeroNum 要补零的个数
	 * @return
	 */
	public static String integerSTRAddzero(String str,int zeroNum){
		StringBuffer sb=new StringBuffer();
		sb.append(str);
		sb.append(".");
		for(int i=0;i<zeroNum;i++){
			sb.append("0");
		}
		return sb.toString();		
	}
	
	/**
	 * 格式数据的显示形式
	 * @param obj 要处理的对象
	 * @param scale 小数位数
	 * @return
	 */
	public static String formatDigitalNumber(Object obj,int scale){
		String tempStr=null;
		if(obj!=null&&!"".equals(obj)){
			tempStr=obj.toString();
			int digIndex=tempStr.indexOf(".");
			if(digIndex!=-1){
				int digital2End=tempStr.substring(digIndex).length()-1;
				return doubleSTRAddzero(tempStr,digIndex,scale,digital2End);
			}else{
				return integerSTRAddzero(tempStr,scale);
			}			
		}else{
			if(scale>0){
				return integerSTRAddzero("0",scale);
			}else{
				return "0";
			}
		}
	}
	
	public static double sub(String v1, String v2) {  
		  BigDecimal b1 = new BigDecimal(v1);  
		  BigDecimal b2 = new BigDecimal(v2);  
		  return b1.subtract(b2).doubleValue();  
	}
	
	public static String subToStr(String v1, String v2) {  
		  BigDecimal b1 = new BigDecimal(v1);  
		  BigDecimal b2 = new BigDecimal(v2);  
		  double val=b1.subtract(b2).doubleValue();  
		  Double number=new Double(val);
		  if(number==null) return "0";
		  NumberFormat format=DecimalFormat.getInstance();
		  return format.format(number).replaceAll(",","");
	}
	/**
	 * 精确减法  by jl
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double sub(double v1, double v2) {  
		  BigDecimal b1 = new BigDecimal(Double.toString(v1));  
		  BigDecimal b2 = new BigDecimal(Double.toString(v2));  
		  return b1.subtract(b2).doubleValue();  
		}  

	/**
	 * 精确加法  by jl
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {  
		  BigDecimal b1 = new BigDecimal(Double.toString(v1));  
		  BigDecimal b2 = new BigDecimal(Double.toString(v2));  
		  return b1.add(b2).doubleValue();  
		}  
}
