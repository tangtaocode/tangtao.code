package net.risesoft.util;

import java.net.*;

/**
 * Title:        产生GUID
 * Description:
 * Copyright:    Copyright (c) 2000-2001
 * Company:
 * @author       赵斌  (zbok@163.net)
 * @version $Revision: 1.1 $
 */

/**
 * GUIDs are Globally Unique IDentifiers.
 * 
 * A GUID is usually used for a unique handle for an object on a network. The
 * typical GUID is constructed from a unique machine identifier (like the IP
 * address or ENET address), the system time, and a counter. All three are
 * required for uniqueness.
 * 
 * As such, GUIDs are 16 byte arrays with a convenient GUID class wrapper.
 * 
 * 前4个字节是IP地址，接着8个字节是系统时间，最后4个字节是计数器。 在数据库中存储可以使用 char(38) 类型，保存如下字符串
 * {bfa78274-0000-0000-6661-bea400000003}，可以由 toString()方法得到。 也可以用getData() 获得
 * byte[16] 进行处理。
 */

public class GUID extends Object {
	static int counter = 0;
	byte[] guts;
	
	/**
	 * Create a raw GUID.
	 * 
	 * @return the GUID byte array
	 */
	public synchronized static byte[] nextGUID() {
		try {
			// 前4字节为ip
			byte[] ip = InetAddress.getLocalHost().getAddress(); // 192.168.0.14
			counter++;
			byte[] guid = new byte[16];
			for (int i = 0; i < 4; i++) {
				guid[i] = ip[i];
			}
			// 然后8字节为系统时间
			byte[] timeAry = GUID.long2bytes(System.currentTimeMillis());
			for (int i = 4; i < 12; i++) {
				guid[i] = timeAry[i - 4];
			}
			// 然后4字节为计数器
			byte[] counterAry = int2bytes(counter);
			for (int i = 12; i < 16; i++) {
				guid[i] = counterAry[i - 12];
			}

			return guid;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create a new GUID.
	 */
	public GUID() {
		guts = nextGUID();
	}

	/**
	 * Create a GUID given a byte array. Typically this should only be called
	 * when restoring externalized objects.
	 * 
	 * @param guts
	 *            the contents
	 */
	public GUID(byte[] guts) throws IllegalArgumentException {
		if (guts == null || guts.length != 16)
			throw new IllegalArgumentException("格式错误，必须传入16字节长的树组");

		this.guts = guts;
	}

	/**
	 * Compare two GUIDs for equality.
	 * 
	 * @param obj
	 *            GUID to compare to
	 * @return true if equal, false if not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GUID) {
			if (this != obj)
				for (int i = 0; i < 16; i++)
					if (((GUID) obj).guts[i] != this.guts[i])
						return false;
			return true;
		}
		return false;
	}

	/**
	 * {80b38931-ebe2-6c48-8e46-6f8bc984bbb2} 此为SQL Server标准格式，RiseNet
	 * 工作流系统所有的GUID均采用此格式 Convert this GUID to a hex string.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		StringBuffer sb = toStringBuffer(); // 32位格式
		str.append("{");
		str.append(sb.substring(0, 8));
		str.append("-");
		str.append(sb.substring(8, 12));
		str.append("-");
		str.append(sb.substring(12, 16));
		str.append("-");
		str.append(sb.substring(16, 20));
		str.append("-");
		str.append(sb.substring(20, 32));
		str.append("}");
		return (new String(str)).toUpperCase();
	}

	/**
	 * Convert this GUID to a hex string
	 * 
	 * @return the string，长度32，，中间没有分隔符
	 */
	private StringBuffer toStringBuffer() {
		StringBuffer str = new StringBuffer();
		String s;
		int ii;

		byte ip[] = new byte[4];
		for (int i = 0; i < 4; i++) {
			ip[i] = guts[i];
		}
		s = Integer.toHexString(bytes2int(ip));
		ii = 8 - s.length();
		for (int i = 0; i < ii; i++) {
			s = "0" + s;
		}
		str.append(s);

		byte time[] = new byte[8];
		for (int i = 4; i < 12; i++) {
			time[i - 4] = guts[i];
		}
		s = Long.toHexString(bytes2long(time));
		ii = 16 - s.length();
		for (int i = 0; i < ii; i++) {
			s = "0" + s;
		}
		str.append(s);

		byte count[] = new byte[4];
		for (int i = 12; i < 16; i++) {
			count[i - 12] = guts[i];
		}
		s = Integer.toHexString(bytes2int(count));
		ii = 8 - s.length();
		for (int i = 0; i < ii; i++) {
			s = "0" + s;
		}
		str.append(s);

		return str;
	}

	public String getUUID(){
		StringBuffer str = new StringBuffer();
		String s;
		int ii;

		byte ip[] = new byte[4];
		for (int i = 0; i < 4; i++) {
			ip[i] = guts[i];
		}
		s = Integer.toHexString(bytes2int(ip));
		ii = 8 - s.length();
		for (int i = 0; i < ii; i++) {
			s = "0" + s;
		}
		str.append(s);

		byte time[] = new byte[8];
		for (int i = 4; i < 12; i++) {
			time[i - 4] = guts[i];
		}
		s = Long.toHexString(bytes2long(time));
		ii = 16 - s.length();
		for (int i = 0; i < ii; i++) {
			s = "0" + s;
		}
		str.append(s);

		byte count[] = new byte[4];
		for (int i = 12; i < 16; i++) {
			count[i - 12] = guts[i];
		}
		s = Integer.toHexString(bytes2int(count));
		ii = 8 - s.length();
		for (int i = 0; i < ii; i++) {
			s = "0" + s;
		}
		str.append(s);

		return str.toString();
	}
	/**
	 * Get the raw GUID.
	 * 
	 * @return the raw GUID
	 */
	public byte[] getData() {
		return guts;
	}

	private synchronized static byte[] long2bytes(long lParam) {
		byte[] byteAry = new byte[8];
		for (int i = 0; i < 8; i++) {
			byteAry[i] = (byte) (lParam >> ((7 - i) * 8));
		}
		return byteAry;
	}

	private synchronized static byte[] int2bytes(int iParam) {
		byte[] byteAry = new byte[4];
		for (int i = 0; i < 4; i++) {
			byteAry[i] = (byte) (iParam >> ((3 - i) * 8));
		}
		return byteAry;
	}

	private synchronized static long bytes2long(byte[] byteAry) {
		if (byteAry == null || byteAry.length != 8) {
			return 0;
		}
		long l = 0;
		for (int i = 0; i < byteAry.length; i++) {
			l += byteAry[i] << ((7 - i) * 8);
		}
		return l;
	}

	private synchronized static int bytes2int(byte[] byteAry) {
		if (byteAry == null || byteAry.length != 4) {
			return 0;
		}
		int ii = 0;
		for (int i = 0; i < byteAry.length; i++) {
			ii += byteAry[i] << ((3 - i) * 8);
		}
		return ii;

	}

	/**
	 * <p>
	 * Get a table name ,this table holds the data of the according archive.
	 * 
	 * @return java.lang.String add in 2002年1月18日17点03分
	 */

	public String getArchiveFormTableName() {

		String tableName = "ARC_FILE_"
				+ (new String(toStringBuffer())).toUpperCase().substring(11);
		// String tableName = (new
		// String(toStringBuffer())).toUpperCase().substring(2,32);
		return tableName;
	}

	/* end add */

	public static String getGUID(){
		return new GUID().toString();
	}
}
