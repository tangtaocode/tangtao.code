package net.risesoft.approve.risefile.filter;

public abstract class AbstractFileFilter implements FileFilter{
	
	public int getTabIndex(){
		if(getIndex()>1000){
			throw new IllegalArgumentException("FileFilter:"
					+this.getClass().getName()+"的getIndex()的值不能超过1000");
		}
		if(getFilterIndex()>1000){
			throw new IllegalArgumentException("FileFilter:"
					+this.getClass().getName()+"的getFilterIndex()的值不能超过1000");
		}
		return getFilterIndex()*1000+getIndex();
	}
}
