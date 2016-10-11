package net.risesoft.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the webservice.client.portalgtasks.oa package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _DbsxDept_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "dept");
	private final static QName _DbsxUrl_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "url");
	private final static QName _DbsxType_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "type");
	private final static QName _DbsxDate_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "date");
	private final static QName _DbsxUsername_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "username");
	private final static QName _DbsxTitle_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "title");
	private final static QName _DbsxCb_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "cb");
	private final static QName _DbsxJjcd_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "jjcd");
	private final static QName _DbsxFolder_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "folder");
	private final static QName _DbsxPreSubmitDate_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "preSubmitDate");
	private final static QName _DbsxBlsx_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "blsx");
	private final static QName _DbsxInstanceGUID_QNAME = new QName(
			"http://Synchronization.platform.risesoft.net", "instanceGUID");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: webservice.client.portalgtasks.oa
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Dbsx }
	 * 
	 */
	public Dbsx createDbsx() {
		return new Dbsx();
	}

	/**
	 * Create an instance of {@link DbsxViewResponse }
	 * 
	 */
	public DbsxViewResponse createDbsxViewResponse() {
		return new DbsxViewResponse();
	}

	/**
	 * Create an instance of {@link DbsxView }
	 * 
	 */
	public DbsxView createDbsxView() {
		return new DbsxView();
	}

	/**
	 * Create an instance of {@link ArrayOfDbsx }
	 * 
	 */
	public ArrayOfDbsx createArrayOfDbsx() {
		return new ArrayOfDbsx();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "dept", scope = Dbsx.class)
	public JAXBElement<String> createDbsxDept(String value) {
		return new JAXBElement<String>(_DbsxDept_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "url", scope = Dbsx.class)
	public JAXBElement<String> createDbsxUrl(String value) {
		return new JAXBElement<String>(_DbsxUrl_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "type", scope = Dbsx.class)
	public JAXBElement<String> createDbsxType(String value) {
		return new JAXBElement<String>(_DbsxType_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "date", scope = Dbsx.class)
	public JAXBElement<String> createDbsxDate(String value) {
		return new JAXBElement<String>(_DbsxDate_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "username", scope = Dbsx.class)
	public JAXBElement<String> createDbsxUsername(String value) {
		return new JAXBElement<String>(_DbsxUsername_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "title", scope = Dbsx.class)
	public JAXBElement<String> createDbsxTitle(String value) {
		return new JAXBElement<String>(_DbsxTitle_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "cb", scope = Dbsx.class)
	public JAXBElement<String> createDbsxCb(String value) {
		return new JAXBElement<String>(_DbsxCb_QNAME, String.class, Dbsx.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "jjcd", scope = Dbsx.class)
	public JAXBElement<String> createDbsxJjcd(String value) {
		return new JAXBElement<String>(_DbsxJjcd_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "folder", scope = Dbsx.class)
	public JAXBElement<String> createDbsxFolder(String value) {
		return new JAXBElement<String>(_DbsxFolder_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "preSubmitDate", scope = Dbsx.class)
	public JAXBElement<String> createDbsxPreSubmitDate(String value) {
		return new JAXBElement<String>(_DbsxPreSubmitDate_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "blsx", scope = Dbsx.class)
	public JAXBElement<String> createDbsxBlsx(String value) {
		return new JAXBElement<String>(_DbsxBlsx_QNAME, String.class,
				Dbsx.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://Synchronization.platform.risesoft.net", name = "instanceGUID", scope = Dbsx.class)
	public JAXBElement<String> createDbsxInstanceGUID(String value) {
		return new JAXBElement<String>(_DbsxInstanceGUID_QNAME, String.class,
				Dbsx.class, value);
	}

}
