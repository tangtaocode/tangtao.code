package net.risesoft.approve.risefile.filter;

import net.risesoft.approve.risefile.FileBox;
import net.risesoft.approve.risefile.commons.TabIndex;

/**
 * 队获得的File级进行二次过滤或填充
 */

public abstract class QueryFilter extends AbstractFileFilter {
	public int getFilterIndex() {
		return 6;
	}
}
