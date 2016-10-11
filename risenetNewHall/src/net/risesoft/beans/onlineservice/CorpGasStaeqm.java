package net.risesoft.beans.onlineservice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="CORP_GAS_STA_EQM")
public class CorpGasStaeqm implements Serializable{
	
	private String guid;
	private String name;
	private String plc_of_product;
	private String product_model;
	private String val_date_of_test;
	private String total_product;
	private String  station_guid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getPlc_of_product() {
		return plc_of_product;
	}
	public void setPlc_of_product(String plcOfProduct) {
		plc_of_product = plcOfProduct;
	}
	@Column
	public String getProduct_model() {
		return product_model;
	}
	public void setProduct_model(String productModel) {
		product_model = productModel;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getVal_date_of_test() {
		return val_date_of_test;
	}
	
	public void setVal_date_of_test(String valDateOfTest) {
		val_date_of_test = valDateOfTest;
	}
	@Column
	public String getTotal_product() {
		return total_product;
	}
	public void setTotal_product(String totalProduct) {
		total_product = totalProduct;
	}
	@Column
	public String getStation_guid() {
		return station_guid;
	}
	public void setStation_guid(String stationGuid) {
		station_guid = stationGuid;
	}
	
}
