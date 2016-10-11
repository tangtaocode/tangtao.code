package net.risesoft.common.util;

import java.util.List;

import egov.appservice.ac.model.Resource;

public class Resources {
	private Resource resource;
	private List<Resources> list;
	private boolean hasChild;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<Resources> getList() {
		return list;
	}

	public void setList(List<Resources> list) {
		this.list = list;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

}
