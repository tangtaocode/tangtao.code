package net.risesoft.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
 * PortalsServer service = new PortalsServer();
 * PortalsServerPortType portType = service.getPortalsServerHttpPort();
 * portType.dbsxView(...);
 * </pre>
 * 
 * </p>
 * 
 */
@WebServiceClient(name = "PortalsServer", targetNamespace = "http://Synchronization.platform.risesoft.net", wsdlLocation = "http://192.168.53.89:7001/ws/PortalsServer?wsdl")
public class PortalsServer extends Service {

	private final static URL PORTALSSERVER_WSDL_LOCATION;
	private final static Logger logger = Logger.getLogger(net.risesoft.webservice.client.PortalsServer.class.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = net.risesoft.webservice.client.PortalsServer.class.getResource(".");
			url = new URL(baseUrl,"http://192.168.53.89:7001/ws/PortalsServer?wsdl");
		} catch (MalformedURLException e) {
			logger.warning("Failed to create URL for the wsdl Location: 'http://192.168.53.89:7001/ws/PortalsServer?wsdl', retrying as a local file");
			logger.warning(e.getMessage());
		}
		PORTALSSERVER_WSDL_LOCATION = url;
	}

	public PortalsServer(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public PortalsServer() {
		super(PORTALSSERVER_WSDL_LOCATION,
				new QName("http://Synchronization.platform.risesoft.net","PortalsServer"));
	}

	/**
	 * 
	 * @return returns PortalsServerPortType
	 */
	@WebEndpoint(name = "PortalsServerHttpPort")
	public PortalsServerPortType getPortalsServerHttpPort() {
		return super.getPort(new QName(
				"http://Synchronization.platform.risesoft.net","PortalsServerHttpPort"), PortalsServerPortType.class);
	}

}
