package net.risesoft.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Dbsx complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Dbsx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="blsx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dept" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handle_Status" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="instanceGUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jjcd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preSubmitDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dbsx", propOrder = { "blsx", "cb", "date", "dept", "folder",
		"handleStatus", "instanceGUID", "jjcd", "preSubmitDate", "title",
		"type", "url", "username" })
public class Dbsx {

	@XmlElementRef(name = "blsx", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> blsx;
	@XmlElementRef(name = "cb", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> cb;
	@XmlElementRef(name = "date", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> date;
	@XmlElementRef(name = "dept", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> dept;
	@XmlElementRef(name = "folder", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> folder;
	@XmlElement(name = "handle_Status")
	protected Boolean handleStatus;
	@XmlElementRef(name = "instanceGUID", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> instanceGUID;
	@XmlElementRef(name = "jjcd", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> jjcd;
	@XmlElementRef(name = "preSubmitDate", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> preSubmitDate;
	@XmlElementRef(name = "title", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> title;
	@XmlElementRef(name = "type", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> type;
	@XmlElementRef(name = "url", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> url;
	@XmlElementRef(name = "username", namespace = "http://Synchronization.platform.risesoft.net", type = JAXBElement.class)
	protected JAXBElement<String> username;

	/**
	 * Gets the value of the blsx property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getBlsx() {
		return blsx;
	}

	/**
	 * Sets the value of the blsx property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setBlsx(JAXBElement<String> value) {
		this.blsx = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the cb property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getCb() {
		return cb;
	}

	/**
	 * Sets the value of the cb property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setCb(JAXBElement<String> value) {
		this.cb = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the date property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getDate() {
		return date;
	}

	/**
	 * Sets the value of the date property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setDate(JAXBElement<String> value) {
		this.date = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the dept property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getDept() {
		return dept;
	}

	/**
	 * Sets the value of the dept property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setDept(JAXBElement<String> value) {
		this.dept = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the folder property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getFolder() {
		return folder;
	}

	/**
	 * Sets the value of the folder property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setFolder(JAXBElement<String> value) {
		this.folder = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the handleStatus property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isHandleStatus() {
		return handleStatus;
	}

	/**
	 * Sets the value of the handleStatus property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setHandleStatus(Boolean value) {
		this.handleStatus = value;
	}

	/**
	 * Gets the value of the instanceGUID property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getInstanceGUID() {
		return instanceGUID;
	}

	/**
	 * Sets the value of the instanceGUID property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setInstanceGUID(JAXBElement<String> value) {
		this.instanceGUID = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the jjcd property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getJjcd() {
		return jjcd;
	}

	/**
	 * Sets the value of the jjcd property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setJjcd(JAXBElement<String> value) {
		this.jjcd = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the preSubmitDate property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getPreSubmitDate() {
		return preSubmitDate;
	}

	/**
	 * Sets the value of the preSubmitDate property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setPreSubmitDate(JAXBElement<String> value) {
		this.preSubmitDate = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the title property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getTitle() {
		return title;
	}

	/**
	 * Sets the value of the title property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setTitle(JAXBElement<String> value) {
		this.title = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setType(JAXBElement<String> value) {
		this.type = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the url property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getUrl() {
		return url;
	}

	/**
	 * Sets the value of the url property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setUrl(JAXBElement<String> value) {
		this.url = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the username property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getUsername() {
		return username;
	}

	/**
	 * Sets the value of the username property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setUsername(JAXBElement<String> value) {
		this.username = ((JAXBElement<String>) value);
	}

}
