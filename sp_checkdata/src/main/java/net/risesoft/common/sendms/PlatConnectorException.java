/**
 * Copyright 2005 risesoft
 * All right reserved.
 */
package net.risesoft.common.sendms;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: risesoft
 * </p>
 * 
 * @author liuq 2005.03
 * @version 1.0
 */
public class PlatConnectorException extends Exception {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -1648757477863461972L;

	public PlatConnectorException() {
	}

	public PlatConnectorException(String msg) {
		super(msg);
	}

	public PlatConnectorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PlatConnectorException(Throwable cause) {
		super(cause);
	}
}
