package net.risesoft.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "PortalsServerPortType", targetNamespace = "http://Synchronization.platform.risesoft.net")
public interface PortalsServerPortType {

	/**
	 * 
	 * @param in0
	 * @return returns webservice.client.portalgtasks.oa.ArrayOfDbsx
	 */
	@WebMethod
	@WebResult(name = "out", targetNamespace = "http://Synchronization.platform.risesoft.net")
	@RequestWrapper(localName = "dbsxView", targetNamespace = "http://Synchronization.platform.risesoft.net", className = "net.risesoft.webservice.client.DbsxView")
	@ResponseWrapper(localName = "dbsxViewResponse", targetNamespace = "http://Synchronization.platform.risesoft.net", className = "net.risesoft.webservice.client.DbsxViewResponse")
	public ArrayOfDbsx dbsxView(
			@WebParam(name = "in0", targetNamespace = "http://Synchronization.platform.risesoft.net") String in0);

}
