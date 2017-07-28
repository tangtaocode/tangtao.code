/**
 * Copyright (c) 2003 RiseSoft Co.,Ltd
 * $Header$
 */

package net.risesoft.util;

import java.io.BufferedReader;
import java.sql.Clob;
import java.util.*;

import org.apache.commons.lang.StringUtils;


/**
 * �����ַ�֮��ĳ����滻�Ͳ���
 * �����滻����ʽ��html��ʽ�ȵ�
 *
 * @author ���
 * @version $Revision$  of RiseOffice5.1
 */

public class StringUtil {

	private final static String QUOTE_ENCODE = "&quot;";

	private final static String AMP_ENCODE = "&amp;";

	private final static String LT_ENCODE = "&lt;";

	/**
	 * ��ԭ���ַ������oldString��newString�����ִ�Сд
	 *
	 * @param line ԭ�е��ַ�
	 * @param oldString ��Ҫ���滻���ַ�
	 * @param newString ���ַ�
	 *
	 * @return �滻����ַ�
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * ��ԭ���ַ������oldString��newString�����ִ�Сд
	 *
	 * @param line ԭ�е��ַ�
	 * @param oldString ��Ҫ���滻���ַ�
	 * @param newString ���ַ�
	 *
	 * @return �滻����ַ�
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * �����ַ��ʺ�IE��ʾ
	 *
	 * @param inputStr �����ַ�
	 * @return �滻����ַ�
	 */
	public static final String escapeHTMLTag(String inputStr) {
		if (inputStr == null || inputStr.length() == 0) {
			return "&nbsp;";
		}
		int strLen = inputStr.length();
		StringBuffer buf = new StringBuffer(strLen);
		char ch;
		for (int i = 0; i < inputStr.length(); i++) {
			ch = inputStr.charAt(i);
			switch (ch) {
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
				if (i != inputStr.length()) {
					if (inputStr.charAt(i + 1) == '\n') {
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

	public static final String escapeXMLTag(String inputStr) { //���ؿ� ���
		if (inputStr == null || inputStr.length() == 0) {
			return inputStr;
		}
		int strLen = inputStr.length();
		StringBuffer buf = new StringBuffer();
		char ch;
		for (int i = 0; i < inputStr.length(); i++) {
			ch = inputStr.charAt(i);
			switch (ch) {
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
			case '\'':
				buf.append("&apos;");
				break;
			case ' ':
				buf.append("&#32;");
				break;
			case '\r':
				buf.append("&#13;");
				break;
			case '\n':
				buf.append("&#10;");
				break;
			case '\t':
				buf.append("&#9;");
				break;
			default:
				buf.append(ch);
				break;
			}
		}
		return buf.toString();
	}

	/**
	 * �����ַ��ʺ�IE��JavaScript����
	 * @param inputStr �����ַ�
	 * @return �滻����ַ�
	 */
	public static final String convToJScript(String inputStr) {
		if (inputStr == null || inputStr.length() == 0) {
			return inputStr;
		}
		String result = replace(inputStr, "\"", "\"" + " + \"\\\"\" + " + "\"");
		result = replace(result, "\r\n", "\"" + " + \"\\r\\n\" + " + "\"");
		result = replace(result, "\r", "\"" + " + \"\\r\\n\" + " + "\"");
		return replace(result, "\n", "\"" + " + \"\\r\\n\" + " + "\"");

	}

	/**
	 * �����ַ��ʺ�IE��VBScript����
	 * @param inputStr �����ַ�
	 * @return �滻����ַ�
	 */
	public static final String convToVBScript(String inputStr) {
		if (inputStr == null || inputStr.length() == 0) {
			return inputStr;
		}
		String result = replace(inputStr, "\"", "\"" + " + chr(34) + " + "\"");
		result = replace(result, "\r\n", "\"" + " + chr(13) + chr(10) + "
				+ "\"");
		return replace(result, "\n", "\"" + " + chr(13) + chr(10) + " + "\"");
	}

	/**
	 * �����ַ��ʺ�xml��ʾ
	 * @param string
	 * @return String
	 */
	public static final String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * ����ַ� �Էָ�����
	 * @param str
	 * @param sign
	 * @return String[]
	 */
	public final static String[] split(String str, String sign) {
		StringTokenizer stok = new StringTokenizer(str, sign);
		Vector v = new Vector();
		String[] strArr;
		while (stok.hasMoreTokens()) {
			String tempST = stok.nextToken();
			v.addElement(tempST);
		}
		if (v != null) {
			strArr = new String[v.size()];
			v.copyInto(strArr);
			return strArr;
		}
		return null;
	}

	/**
	 * <p>���ַ��÷ָ��ָ�(��һ�ַ�)
	 * ����"aaa|bbb|ccc|ddd",�ָ��'|'
	 * �γ�Vector����
	 * @param str ԭ�ַ�
	 * @param c �ָ��ַ�
	 * @return Vector
	 */
	public static final Vector split(String str, char c) {
		Vector vec = new Vector();
		if (str == null || str.trim().length() == 0) {
			return vec;
		}
		if (!str.endsWith(String.valueOf(c))) {
			str = str + c;
		}
		int i = 0;
		int j = 0;
		while ((j = str.indexOf(c, i)) != -1) {
			vec.add(str.substring(i, j));
			i = j + 1;
		}
		return vec;
	}

	/**
	 * ����ַ� �����ַ���
	 * @param str
	 * @param sign
	 * @return String[]
	 */
	public final static String[] splitWithSubstring(String str, String sign) {
		//		StringTokenizer stok = new StringTokenizer(str, sign);
		Vector v = new Vector();
		String[] strArr;
		String strtmp;
		int tt;
		while (str.indexOf(sign) != -1) {
			tt = str.indexOf(sign);
			strtmp = str.substring(0, tt);
			v.addElement(strtmp);
			str = str.substring(tt + sign.length());
			if (str.indexOf(sign) == -1)
				v.addElement(str);
		}
		if (v != null) {
			strArr = new String[v.size()];
			v.copyInto(strArr);
			return strArr;
		}
		return null;
	}

	/**
	 * ���ַ�null���жϼ�ת��
	 * @param str
	 * @return String
	 */
	public final static String toNoNull(String str) {
		if (str == null || str.equals("") || str.equalsIgnoreCase("null")) {
			return "";
		}
		return str;
	}

	/**
	 * ɾ���ַ��еĿո�
	 * @param str
	 * @return String
	 */
	public final static String delBlank(String str) {
		if (str == null || str.equals(""))
			return null;
		//		int len = str.length();
		int tmp = 0;
		String frontStr = "";
		String lastStr = "";
		while ((tmp = str.indexOf(" ")) != -1) {
			frontStr = str.substring(0, tmp);
			lastStr = str.substring(tmp + 1);
			str = frontStr + lastStr;
		}
		return str;
	}

	/**���ڽ�StringתΪint
	 * @param str
	 * @return int
	 */
	public static final int getStr2int(String str) {
		int str_int = 0;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0;
		}
		try {
			str_int = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : ��ݸ�ʽת�����ִ��� " + str);
		}
		return str_int;
	}

	public static final float getStr2float(String str) {
		float str_float = 0.0F;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0.0F;
		}
		try {
			str_float = Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : ��ݸ�ʽת��float���ִ��� " + str);
		}
		return str_float;
	}

	public static final double getStr2double(String str) {
		double str_double = 0.0D;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0.0D;
		}
		try {
			str_double = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : ��ݸ�ʽת��double���ִ��� " + str);
		}
		return str_double;
	}

	public static final boolean getStr2boolean(String str) {
		if (str == null) {
			return false;
		}
		if (str.equals("1")) {
			return true;
		}
		return false;

	}

	public static final long getStr2long(String str) {
		long str_long = 0;
		if (str == null || str.equals("") || str.equals("null")) {
			return 0;
		}
		try {
			str_long = Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			System.out.print("ERROR : ��ݸ�ʽת��long���ִ��� " + str);
		}
		return str_long;
	}

	public static final String trimLeft(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return "";
		}
		String tmpstr = str;
		while ((tmpstr.substring(0, 1)).equals(" ")) {
			tmpstr = tmpstr.substring(1);
			if (tmpstr == null || tmpstr.equals("")) {
				break;
			}
		}
		return tmpstr;
	}

	public static final String trimRight(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return "";
		}
		String tmpstr = str;
		while ((tmpstr.length() - 1) == (tmpstr.lastIndexOf(" "))) {
			tmpstr = tmpstr.substring(0, tmpstr.length() - 1);
			if (tmpstr == null || tmpstr.equals("")) {
				break;
			}
		}
		return tmpstr;
	}

	public static final String trim(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return "";
		}
		String tmpstr = str;
		tmpstr = trimLeft(tmpstr);
		tmpstr = trimRight(tmpstr);
		return tmpstr;
	}

	public static final String getBlankStr(int num) {
		String str = " ";
		for (int i = 0; i < num; i++) {
			str = str + "  ";
		}
		return str;
	}

	public static final String getBlankStrHtml(int num) {
		String str = "&nbsp;&nbsp;";
		for (int i = 0; i < num; i++) {
			str = str + "&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		return str;
	}

	public static final String substringLast(String string, String token) {
		String str = "";
		StringTokenizer toStr = new StringTokenizer(string, token);
		while (toStr.hasMoreTokens()) {
			str = toStr.nextToken().trim();
		}

		return str;
	}

	/**
	 * ��ȡ�ַ� str ��һ�� c ǰ���ַ�
	 *
	 * @param str �����ַ�
	 * @param c ���ҵ��ַ�
	 * @return String
	 */
	public static String getFirstSplitStr(String str, char c) {
		if (str == null || str.length() == 0) {
			return null;
		}
		int i = 0;
		if ((i = str.indexOf(c)) != -1) {
			return str.substring(0, i);
		} else {
			return null;
		}
	}

	/**
	 * <p>��Clob������ֵת��ΪString����
	 * @param clob ����Colb���Ͳ���
	 * @return String
	 */
	public static final String convClobToString(Clob clob) {
		BufferedReader bis = null;
		try {
			bis = new BufferedReader(clob.getCharacterStream());
			String str;
			StringBuffer buf = new StringBuffer();
			while ((str = bis.readLine()) != null) {
				buf.append(str);
				buf.append("\r\n");
			}
			return buf.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * �ַ�ǰ��0��
	 *
	 * @param str �����ַ�
	 * @param len Ҫת�����ַ���
	 * @return String
	 */
	public static String fillNum0(String str, int len) {
		if (str == null || str.length() == 0 || str.length() >= len) {
			return str;
		}
		String tempStr = "";
		int i = 0;
		for (i = 0; i < (len - str.length()); i++) {
			tempStr = tempStr + "0";
		}
		return tempStr + str;
	}


	/**
	 * ������Ŀ̧ͷ��Ϣ
	 * ����
	 * @param width ���
	 * @param except ǰ��������
	 * @param titles ����String����
	 * @return
	 */
	public static String createHeader(int width,int except,String[] titles) {
		String stars = StringUtils.repeat("*", width);
		String[] trans = new String[titles.length+2];
		trans[0] = stars;
		for(int i=0;i<except;i++){
			trans[1+i] = " "+titles[i];
		}
		for(int i=except+0;i<titles.length;i++){
			trans[i+1] = StringUtils.center(titles[i], width, " ");
		}
		trans[titles.length+1] = stars;
		String heading = StringUtils.join(
				trans, "\n");
		return heading;
	}
	
	 /**
	  * 
	  * @param str
	  * @return
	  */
	 public static int getStringsRealLength(String[] str){
		 if(str==null)
			 return 0;
		 
		 int i=0;
		 for(i=0;i<str.length;i++){
			 if(str[i]==null||str[i].equals(""))
				 return i;
		 }
		 
		 return i;
			 
	 }
	 
	 public static String replaceStr(String str)
	 {
	       String ret="";
	       if(str!=null)
	       {
	           ret=str.replaceAll(""+'"',"");
	           ret=ret.replaceAll("'","");
	           ret=ret.replaceAll("&","");
	       }
	       return ret;
	  }
	 
	 /***
	 * @author wjm
	 * @param source
	 * @param subString
	 * @return �ַ�source���Ӵ��ĸ���
	 */
	public final static int includeCount(String source, String subString){
		if(source == null || subString == null || "".equals(subString)){
			return 0;
		}
		String str = source.replaceAll(subString, "");
		return source.length() - str.length();
	}
	
	public final static String substring(String source, int length){
		byte bt[] = source.getBytes();
        int count = 0;
        length = length < bt.length ? length : bt.length;
        for (int i = 0; i < length; i++) {
            if (bt[i] < 0)
                count++;
        }
        if (count % 2 != 0)
        	length--;
        if (length < bt.length) {
        	source = new String(bt, 0, length) + "...";
        } else {
        	source = new String(bt, 0, length);
        }
        return source;
	}
}