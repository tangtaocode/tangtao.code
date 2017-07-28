package net.risesoft.approve.entity.jpa;

import java.util.List;

public class TBdexDocTypeInfoModel {
	private String guid;

	private String doctypeguid;

	private String infocode;

	private String infomemo;
	
	private String infocoment;

	private String glguid;
	
	private List<TBdexDocTypeInfo> doclist;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDoctypeguid() {
		return doctypeguid;
	}

	public void setDoctypeguid(String doctypeguid) {
		this.doctypeguid = doctypeguid;
	}

	public String getInfocode() {
		return infocode;
	}

	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}

	public String getInfomemo() {
		return infomemo;
	}

	public void setInfomemo(String infomemo) {
		this.infomemo = infomemo;
	}

	public String getInfocoment() {
		return infocoment;
	}

	public void setInfocoment(String infocoment) {
		this.infocoment = infocoment;
	}

	public String getGlguid() {
		return glguid;
	}

	public void setGlguid(String glguid) {
		this.glguid = glguid;
	}

	public List<TBdexDocTypeInfo> getDoclist() {
		return doclist;
	}

	public void setDoclist(List<TBdexDocTypeInfo> doclist) {
		this.doclist = doclist;
	}

	public TBdexDocTypeInfoModel() {
		super();
	}

	public TBdexDocTypeInfoModel(String guid, String doctypeguid,
			String infocode, String infomemo, String infocoment, String glguid,
			List<TBdexDocTypeInfo> doclist) {
		super();
		this.guid = guid;
		this.doctypeguid = doctypeguid;
		this.infocode = infocode;
		this.infomemo = infomemo;
		this.infocoment = infocoment;
		this.glguid = glguid;
		this.doclist = doclist;
	}
	
}
