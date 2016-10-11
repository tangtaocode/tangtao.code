package net.risesoft.common.util;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003 RiseSoft Co.,Ltd</p>
 * <p>Company: </p>
 * @author not attributable
 * @version $Revision: 1.1 $
 */

import java.io.BufferedReader;


import java.io.IOException;

import java.sql.Clob;
import java.util.StringTokenizer;
import java.util.Vector;

public class UtilString {
  public UtilString() {
  }
     /**
      * <p>处理字符串，适合IE显示
      * 如回车<BR>,空格&nbsp;
      * @param inputStr 输入字符串
      * @return String
      */
    public static final String escapeHTMLTag(String inputStr){
        if (inputStr==null || inputStr.length()==0){
            return inputStr;
        }
        int strLen=inputStr.length();
        StringBuffer buf=new StringBuffer(strLen);
        char ch;
        for(int i=0;i<inputStr.length();i++){
            ch=inputStr.charAt(i);
            switch(ch)
            {
                case '<':
                    buf.append("&lt;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                case '"':
                    buf.append("&quot;");
                    break;
                case '&':
                    buf.append("&amp;");
                    break;
                case ' ':
                    buf.append("&nbsp;");
                    break;
                case '\r':
                    if (i!=inputStr.length()){
                        if (inputStr.charAt(i+1)=='\n'){
                             buf.append("<BR>");
                             i++;
                        }
                    }
                    break;
                case '\n':
                    buf.append("<BR>");
                    break;
                default:
                    buf.append(ch);
                    break;
            }
        }
        return buf.toString();
    }

     /**
      * <p>将Clob类型数值转化为String类型
      * @param clob 输入Colb类型参数
      * @return String
      */
    public static final String convClobToString(Clob clob){
      if(clob==null){
    		return "";
      }else{
        BufferedReader bis=null;
        try{
          bis = new BufferedReader(clob.getCharacterStream());
          String str;
          StringBuffer buf;
          buf=new StringBuffer();
          while((str=bis.readLine())!=null){
               buf.append(str);
               buf.append("\r\n");
          }
          return buf.toString();
        }catch(Exception ex){
          ex.printStackTrace();
          return "";
        }
      }
        
    }
    public static final String escapeHTMLTagNewNoSign(String inputStr){
        if (inputStr==null || inputStr.length()==0){
            return inputStr;
        }
        int strLen=inputStr.length();
        StringBuffer buf=new StringBuffer(strLen);
        char ch;
        for(int i=0;i<inputStr.length();i++){
            ch=inputStr.charAt(i);
            switch(ch)
            {
                case '"':
                    buf.append("&quot;");
                    break;
                case ' ':
                    buf.append("&nbsp;");
                    break;
                case '\r':
                    if (i!=inputStr.length()){
                        if (inputStr.charAt(i+1)=='\n'){
                             buf.append("<BR>");
                             i++;
                        }
                    }
                    break;
                case '\n':
                    buf.append("<BR>");
                    break;
                default:
                    buf.append(ch);
                    break;
            }
        }
        String tempstr = buf.toString();
        tempstr = replace(tempstr,"<center>","<div algin=center>");
        tempstr = replace(tempstr,"</center>","</div>");
        return buf.toString();
    }
    /**
      * <p>将Unicode字符串数值转化为ASCII
      * @param s 输入字符串
      * @return String
      */
    public static final String u2a(String s)
    {
        if(s==null || s.length()==0){
            return s;
        }
        String sRet="";
        try{
            byte []b=s.getBytes("GBK");
            return new String(b,"ISO8859_1");
           }catch(Exception e){
           	  e.printStackTrace();
              return s;
           }
    }
    /**
      * <p>将ASCII字符串数值转化为Unicode
      * @param s 输入字符串
      * @return String
      */
    public static final String a2u(String s)
    {
      return toGBK(s);
    }

    public synchronized static String toGBK(String s){
       if(s==null || s.length()==0) return s;

       byte[] b;
       try {
         b=s.getBytes("ISO8859_1");
         for(int i=0;i<b.length;i++)
           if (b[i]+0<0) return new String(b,"GBK");

         b=s.getBytes("GBK");
         for(int i=0;i<b.length;i++)
           if (b[i]+0<0) return new String(b,"GBK");
       }
       catch(Exception e){ }

       return s;
     }

    /**
      * <p>字符串替换
      * @param str 原字符串
      * @param oldstr 要替换字符串
      * @param oldstr 替换为新字符串
      * @return String
      */
    public static String replace(String str,String oldstr,String newstr){
        if (str==null || str.length()==0) return str;
        if (oldstr==null || oldstr.length()==0) return str;
        if (newstr==null) newstr="";
        int oldLength=oldstr.length();
        int newLength=newstr.length();
        oldstr=oldstr.toLowerCase();
        String tempStr=str.toLowerCase();
        int firstPosition=tempStr.indexOf(oldstr);
        while ( firstPosition != -1){
            tempStr=tempStr.substring(0,firstPosition) + newstr + tempStr.substring(firstPosition+oldLength);
            str=str.substring(0,firstPosition) + newstr + str.substring(firstPosition+oldLength);
            firstPosition=tempStr.indexOf(oldstr,firstPosition+newLength);
        }
        return str;
    }
    
    /**
     * <p>日期字符串替换
     * @param str 原字符串mm/dd/yyyy hh:mm:ss
     * @return String  yyyy-mm-dd hh:mm:ss
     */
   public static String dateansi(String str){
    int iPosition1 =-1;
    int iPosition2 =-1;
    int iPosition3 =-1;
    String syear="";
    String smonth="";
    String sday="";
    iPosition1 =str.indexOf("/");
    if(iPosition1!=-1 && iPosition1>=1 ){ 
       smonth=str.substring(0,iPosition1);
       if(smonth.length()<=1){
       	smonth="0"+smonth;       	
       }
    }
    iPosition2 =str.lastIndexOf("/");
    if(iPosition2!=-1 && iPosition2>=iPosition1 ){          
      sday=str.substring(iPosition1+1,iPosition2);
      if(sday.length()<=1){
      	sday="0"+sday;       	
       }
    }
    iPosition3 =str.indexOf(" ");
    if(iPosition3!=-1 && iPosition3>iPosition2 ){
      syear=str.substring(iPosition2+1,iPosition3);
    }
    str=syear+"-"+smonth+"-"+sday+" "+str.substring(iPosition3+1);

       return str;
   }
   /**
    * <p>日期字符串替换
    * @param str 原字符串mm/dd/yyyy hh:mm:ss
    * @return String  yyyy-mm-dd 
    */
  public static String dateansishort(String str){
   int iPosition1 =-1;
   int iPosition2 =-1;
   int iPosition3 =-1;
   String syear="";
   String smonth="";
   String sday="";
   iPosition1 =str.indexOf("/");
   if(iPosition1!=-1 && iPosition1>=1 ){ 
      smonth=str.substring(0,iPosition1);
      if(smonth.length()<=1){
      	smonth="0"+smonth;       	
      }
   }
   iPosition2 =str.lastIndexOf("/");
   if(iPosition2!=-1 && iPosition2>=iPosition1 ){          
     sday=str.substring(iPosition1+1,iPosition2);
     if(sday.length()<=1){
     	sday="0"+sday;       	
      }
   }
   iPosition3 =str.indexOf(" ");
   if(iPosition3!=-1 && iPosition3>iPosition2 ){
     syear=str.substring(iPosition2+1,iPosition3);
   }
   str=syear+"-"+smonth+"-"+sday;

      return str;
  }
    /**
      * <p>将字符串用分割符分割(单一字符)
      * 例如"aaa|bbb|ccc|ddd",分割符'|'
      * 形成Vector类型
      * @param str 原字符串
      * @param c 分割字符
      * @return Vector
      */
    public static final Vector split(String str,char c){
       Vector vec=new Vector();
       if(str==null || str.length()==0){
           return vec;
       }
       if(!str.endsWith(String.valueOf(c))){
           str=str + c;
       }
       int i=0;
       int j=0;
       while((j=str.indexOf(c,i))!=-1){
//          System.out.println(str.substring(i,j));
          vec.add(str.substring(i,j));
          i=j+1;
       }
       return vec;
    }
    /**
      * <p>将字符串用分割符分割(首尾字符)
      * 例如"[aaa][bbb][ccc][ddd]",首分割符'[',尾分割符']'
      * 形成Vector类型
      * @param str 原字符串
      * @param first 首分割符
      * @param last 尾分割符
      * @return Vector
      */
    public static final Vector split(String str,char first,char last){
       Vector vec=new Vector();
       if(str==null || str.length()==0){
           return vec;
       }
       String tempStr=str;
       while(tempStr.indexOf(first)!=-1 && tempStr.indexOf(last)!=-1){
          vec.add(tempStr.substring(tempStr.indexOf(first)+1,tempStr.indexOf(last)));
          tempStr=tempStr.substring(tempStr.indexOf(last)+1);
       }
       return vec;
    }

/**
 * 用','连接字符串
 * @param num
 * @return
 */
   public static String num(String[] num){
   	String Str="";
   	for(int i=0;i<num.length;i++){
   		Str+=num[i];
   		Str+=",";
    	}
   	return Str;
   }
   //汉字名问题.
   public static String formatFileName(String fileName2) {
   String fileName="";
   try {
          fileName = toUtf8String(fileName2);
   /*
   * see http://support.microsoft.com/default.aspx?kbid=816868
   */
       if (fileName.length() > 150) {
           String guessCharset = "gb2312"; /*根据request的locale 得出可能的编码，中文操作系统通常是                                      gb2312*/
           fileName = new String(fileName2.getBytes(guessCharset), "ISO-8859-1"); 
       }
   } catch (Exception e) {
      // TODO Auto-generated catch block
       e.printStackTrace();
   }

   return fileName;
   }
   
   public static int time(String str){
    int iPosition3 =-1;
    int iPosition4 =-1;
    String shour="";
    iPosition3 =str.indexOf(" ");
    iPosition4 =str.indexOf(":");
    if(iPosition4!=-1 && iPosition4>iPosition3 ){
    	shour=str.substring(iPosition3+1,iPosition4);
      }
    int time=Integer.parseInt(shour);
    	return time;
    }
   
   /**
    * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
    * @param s 原文件名
    * @return 重新编码后的文件名
    */
   public static String toUtf8String(String s) {
	StringBuffer sb = new StringBuffer();
	for (int i=0;i<s.length();i++) {
	    char c = s.charAt(i);
	    if (c >= 0 && c <= 255) {
		sb.append(c);
	    } else {
		byte[] b;
		try {
		    b = Character.toString(c).getBytes("UTF-8");
		} catch (Exception ex) {
		    System.out.println(ex);
		    b = new byte[0];
		}
		for (int j = 0; j < b.length; j++) {
		    int k = b[j];
		    if (k < 0) k += 256;
		    sb.append("%" + Integer.toHexString(k).
		    toUpperCase());
		}
	    }
	}
	return sb.toString();
   }
   
   /**
    * 拆分字符串到数组中，
    * orStr原始字符串
    * letterLimit每行字符个数
    * 返回结果数据
    * */
   public static String[] splitString(String orStr,int letterLimit){
	   
	   String[] str;
	   String leftStr=orStr;
	   int rowNum;
	   if(letterLimit==0){
		   str=new String[1];
		   str[0]=orStr;
		   return str;
	   }
	   if(orStr.length()%letterLimit==0){
		   rowNum=orStr.length()/letterLimit;
	   }else
	      rowNum=orStr.length()/letterLimit+1;
	   
	   str=new String[rowNum];
	   
	   try{
		   for(int i=0;i<rowNum;i++){
			   int len =(i+1)*letterLimit;
			   if(len>=orStr.length()) len=orStr.length();
			   str[i]=leftStr.substring(i*letterLimit,len);
			   //System.out.println(str[i]);
		   }
	   }catch(Exception ex){
		   ex.printStackTrace();
	   }
	   
	   return str;
   }
   
   /**
    * 拆分字符串到数组中，
    * orStr原始字符串
    * letterLimit每行字符个数
    * 返回结果数据
    * add by wqb
    * */
   public static String[] splitStringForHandZi(String orStr,int letterLimit){
	   int j=0;
	   if(orStr==null) return null;
	   if(letterLimit<=0) return null;
	   
	   String[] str=null;
	   String bb="";
       //首先计算个数：一个汉字算一个字符，两个英文字符算一个字符
	   for(int i=0;i<orStr.length();i++){
		   
		   char a =orStr.charAt(i);
		   bb+=a;
		   boolean ifChina =IsChinese(a);
		   if(ifChina) {j++; if(j%letterLimit==0) bb+=";"; }
		   else{
			   if(i==orStr.length()-1){j++;break;}
			   //判断下一个字符是否是汉字：
			   if(i+1==orStr.length()-1||i+1>orStr.length()-1||IsChinese(orStr.charAt(i+1))){
				   j++;
				   
				   if(j%letterLimit==0) bb+=";";
			   }else{
				   i++;
				   j++;
				   if(j%letterLimit==0) bb+=";";
				   bb+=orStr.charAt(i);
			   }
		   }
	   }

       //System.out.println(bb);
	   return bb.split(";");
   }   
  
   //判断是否是中文字符
   public static boolean IsChinese(char c){   
       return   (int)c   >=   0x4E00   &&   (int)c   <=   0x9FA5;   
   }      


    /**
     * 取得本次的栏目的信息
     * **/ 
     public static String  getTitleFromDn(String footDN){  	
 	    StringTokenizer stz = new StringTokenizer(footDN,",");
 		String result="";

 		while(stz.hasMoreElements()){
 			//String str =stz.toString();
 			
 			String str=(String)stz.nextElement();
 			
 			String tmpstr=str.substring(str.indexOf("=")+1);
 			if(str.equals("RSN=政务公开")){
 				break;
 			}			
 			if(tmpstr!=null&&!tmpstr.equals(""))
 			  result="->"+tmpstr+result;			

 			
 		}
 		
 		return result;
     }
     
     /**
      * 取得本次的栏目的信息
      * **/ 
      public static String  getShortDnFromDn(String footDN){  	
  	    StringTokenizer stz = new StringTokenizer(footDN,",");
  		String result="";

  		while(stz.hasMoreElements()){
  			//String str =stz.toString();
  			
  			String str=(String)stz.nextElement();
  			
  			String tmpstr=str.substring(str.indexOf("=")+1);
  					
  			if(tmpstr!=null&&!tmpstr.equals(""))
  			  result="-"+tmpstr+result;			
  			if(str.equals("RSN=政务公开")){
  				result=result.substring(1);
  				break;
  			}	
  			
  		}
  		
  		return result;
      }     
     
     /**
      * 将空字符串转换成""
      * */
     public static String nvl(String p){
    	 if(p==null){
    		 return "";
    	 }
    	 return p;
     }
     
     /**
      * 将字符串格式化
      * */
     public static String toHtmlString(String sourceStr)
     {
        if(sourceStr == null || sourceStr.equals("")) return "";
        return sourceStr.replaceAll("\r", "").replaceAll("\n", "<br>").replaceAll("\\s", "&nbsp;");
        //return sourceStr.replaceAll("\r", "").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n", "<br>").replaceAll("\\s", "&nbsp;")
     
     }    
     
   
     
     /*
      * 分割字符串:主要拆成两部分：content_1,content_2
      * 参数说明：
      * 1.content 要分割的内容
      * 2.m       每行的个数：两个英文算一个汉字
      * 3.n       要显示的行数
      * 返回值：数组
      * */
     public static String[]  splitString(String content, int m, int n){
    	 String[] resultArray = new String[2];
    	 //差错处理
    	 if(content==null||content.equals("")) return resultArray;
    	 if(m<=0||n<=0){
    		 resultArray[0]="";
    		 resultArray[1]=content;
    		 return resultArray;
    	 }
    	 //用此存储结果数据
    	 StringBuffer content_1 = new StringBuffer();
    	 StringBuffer content_2 = new StringBuffer();
    	 
    	 
    	 //进行单一处理：汉字和英文，回车符，换行符，断行的处理
    	 char[] contentChar = content.toCharArray();
    	 //当前行的字符数
    	 int j=0;
    	 //当前行数
    	 int k=0;
    	 for(int i=0;i<contentChar.length;i++){
    		 //当前的字符
    		 char c0= contentChar[i];
    		 //后一个字符
    		 char c1=' ';
    		 if(i<contentChar.length-1)
    		   c1=contentChar[i+1];
    		 if(c0=='\r'){
    			 k++;
    			 j=0;
    			 content_1.append(c0);
    			if(c1=='\n'){
    				i++;
    				content_1.append(c1);
    			}
    			
	    		 if(k>=n){
	    			 
	    			 for(int jj=i+1;jj<contentChar.length;jj++){
	    				 content_2.append(contentChar[jj]);  
	    			 }
	    			 
	    			 break;
	    		 }    			
    			continue;
    		 }
    		 if(c0=='\n'){
    			 k++;
    			 j=0;
    			 content_1.append(c0);
    			if(c1=='\r'){
    				i++;
    				content_1.append(c1);
    			}
	    		 if(k>=n){
	    			 
	    			 for(int jj=i+1;jj<contentChar.length;jj++){
	    				 content_2.append(contentChar[jj]);  
	    			 }
	    			 
	    			 break;
	    		 }       			
    			continue;
    		 }    		 
    		 
    		 if(IsChinese(c0)){
    			 j++;
    			 content_1.append(c0);
    		 }else{
    			 j++;
    			 content_1.append(c0);
    			 if(!IsChinese(c1)){
    				 content_1.append(c1); 
    				 //j++;
    				 i++;
    			 }
    		 }
    		 if(j>=m){
    			 j=0;
    			 //content_1.append("\r\n"); 
    			 k++;
    		 }
    		 
    		 //System.out.println("j=="+j);
    		 //第一部分结束,第二部分开始
    		 if(k>=n-1&&j>=m-1){
    			 
    			 for(int jj=i+1;jj<contentChar.length;jj++){
    				 content_2.append(contentChar[jj]);  
    			 }
    			 
    			 break;
    		 }
    	 }
    	 
    	 resultArray[0]=content_1.toString();
    	 resultArray[1]=content_2.toString();
    	 return resultArray;
     }
     
     /*
      * 分割字符串:主要拆成两部分：content_1,content_2
      * 参数说明：
      * 1.content 要分割的内容
      * 2.m       每行的个数：两个英文算一个汉字
      * 3.n       要显示的行数
      * 返回值：数组
      * */
     public static String[] splitString1(String content, int m, int n, double rate)
     {
    	 String[] values = new String[2];
    	 int lines = 0;    //行数
    	 StringBuffer newContent = new StringBuffer();
    	 int asciiCount = 0;
    	 int chineseCount = 0;
    	 int i=0;
    	 
    	 if(m * n>content.length()) return new String[]{content, null};
    	 for(i=0; i<content.length(); i++)
    	 {
    		 if(lines >= n)
    		 {//如果到达指定行
    			 values[0] = newContent.toString();
    			 values[1] = content.substring(i);
    			 i=-1;
    			 break;
    		 }
    		 if(content.substring(i, i+1).equals("\r"))
    		 {
    			 lines++;
    			 asciiCount=0;
    			 chineseCount=0;
    			 if((i+1)<content.length() && content.substring(i+1, i+2).equals("\n"))
    			 {//跳过
    				 i++;
    			 }
    			 newContent.append("\n");
    		 }
    		 else if(content.substring(i, i+1).equals("\n"))
    		 {
    			 lines++;
    			 asciiCount=0;
    			 chineseCount=0;
    			 newContent.append("\n");
    		 }
    		 else
    		 {
    			 if(isChinese(content.substring(i, i+1)))
    			 {
    				 chineseCount++;
    			 }
    			 else
    			 {
    				 asciiCount++;
    			 }
    			 newContent.append(content.substring(i, i+1));
    			 if((chineseCount + (int)(asciiCount*rate + 1))>=m)
    			 {
    				 //System.out.print(chineseCount + "," + asciiCount + "," + rate + "," + content.substring(i, i+1)+ ":");
    				 //System.out.println(chineseCount + (int)(asciiCount+1)*rate);
    				 lines++;
        			 asciiCount=0;
        			 chineseCount=0;
        			 newContent.append("\n");
    			 }
    		 }
    	 }
    	 if(i != -1)
    	 {
    		 if(lines >= n)
    		 {//如果到达指定行
    			 values[0] = newContent.toString();
    			 if(i<content.length())
    			 {
    				 values[1] = content.substring(i);
    			 }
    			 else
    			 {
    				 values[1] = "";
    			 }
    		 }
    		 else
    		 {
    			 values[0] = content;
    			 values[1] = null;
    		 }
    	 }
    	 //System.out.println(values[0]);
    	 return values;
     }
     
     private static boolean isChinese(String str)
     {
         return java.util.regex.Pattern.matches("[^\u0000-\u00FF]", str);
     }
     
 	//补齐函数
 	public static String leftCopy(int data,int num,char c){
 		String result="";
 		String dataString = new Integer(data).toString();
 		if(dataString.length()>=num) return dataString;
 		if(dataString.length()>=num-1){
 			result= c+dataString;
 		}else{
 			result = c+leftCopy(data,num-1,c);
 		}
 		
 		return result;
 	}     
     
     public static void main(String args[]) throws IOException{
      	String[] strArray = splitString1("你好好得很斯蒂芬2222的社会上是\r\nsdfsfsafasdfs",3,5, 1/2);
      	System.out.println(strArray[0]);
      	System.out.println(strArray[1]);
      }       

}


