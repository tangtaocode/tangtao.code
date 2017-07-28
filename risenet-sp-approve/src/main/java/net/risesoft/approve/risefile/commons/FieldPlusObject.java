package net.risesoft.approve.risefile.commons;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;




import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.exception.RiseFileSystemException;


/**
 * @author wangdong 
 * 属性叠加对象：对象实现对父对象属性的叠加 普通属性(Object)如果为null，则返回父对象的相应属性，依次向上推。
 * 集合属性(Collection)如果为如果为继承，则向上叠加；如果为覆盖则返回当前层集合。
 * 
 */
public class FieldPlusObject {

	public FieldPlusObject parent;

	/**
	 * 对于非Collection的对象 传入字段名称，如果为null，则返回父对象的相应属性，依次向上推。
	 * 
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Object getPlusObject(String fieldName) {
		Object fieldValue = null;
		
		try {
			//Apache的PropertyUtils的方法必须，有get、set方法，在此处不使用
			//TODO:是否有合适的开源作此工作？
			//fieldValue =PropertyUtils.getProperty(this,fieldName);
			fieldValue=this.getClass().getField(fieldName).get(this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("getPlusObject失败,字段请用public");
		}
		if (fieldValue == null&&parent!=null) {
			fieldValue = parent.getPlusObject(fieldName);
		}
		return fieldValue;
	}
	
	/**
	 * 
	 * 对于List的对象 传入字段名称，和继承方式.
	 * 如果为继承，则向上叠加；
	 * 如果为覆盖则返回当前层集合。
	 * @param fieldName
	 * @param isExtendsField
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public List getPlusList(String fieldName, String isExtendsField){
		List l = new ArrayList();
		
		
		boolean b = false;
		
		Object isExtends = null;
		try {
			//Apache的PropertyUtils的方法必须，有get、set方法，在此处不使用
			//TODO:是否有合适的开源作此工作？
			isExtends = this.getClass().getField(isExtendsField).get(this);
			List fieldValue = (List)this.getClass().getField(fieldName).get(this);
			if(fieldValue!=null){
				l.addAll(fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("getPlusList失败");
		}
		if (isExtends instanceof Boolean) {
			b = ((Boolean) isExtends).booleanValue();
		} else {
			throw new IllegalArgumentException(
					"是否继承对应的字段必须为boolean,isExtendsField:" + isExtendsField);
		}
		
		
		if (b&&parent!=null) {
			l.addAll(parent.getPlusList(fieldName,isExtendsField));
			return l;
		} 
		return l;

	}

	public FieldPlusObject getParent() {
		return parent;
	}

	public void setParent(FieldPlusObject parent) {
		this.parent = parent;
	}
	
	
	public static void main(String[] args){
		RiseFileConfig config =new RiseFileConfig();
		//parent
		RiseFileConfig parentConfig =new RiseFileConfig();
		List list =new ArrayList();
		list.add("hellow");
		list.add("world");
		parentConfig.setFilters(list);
		parentConfig.setSaveMode("fs");
		
		config.setParent(parentConfig);
		config.setFilterExtends(true);
		//extends field
		try {
			System.out.println(config.getFilters());
		} catch (RiseFileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(config.getSaveMode()); 
	}

}
