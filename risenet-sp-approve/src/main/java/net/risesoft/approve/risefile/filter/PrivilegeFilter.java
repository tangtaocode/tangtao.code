package net.risesoft.approve.risefile.filter;

/**
 * 向FileBox填充权限的Filter
 * 包括box级权限
 * File级权限
 */

public abstract class PrivilegeFilter extends AbstractFileFilter {
	public int getFilterIndex() {
		return 5;
	}
}

