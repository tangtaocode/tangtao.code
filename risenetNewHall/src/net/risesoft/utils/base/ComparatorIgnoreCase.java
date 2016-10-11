/**
 * Copyright (c) 2003 RiseSoft Co.,Ltd
 * $Header: \cvsroot\risesoft/risenetftw/src/net/risesoft/utilx/ComparatorIgnoreCase.java,v 1.1 2010/12/28 04:36:54 lj Exp $
 */
package net.risesoft.utils.base;

import java.util.Comparator;
import java.math.BigDecimal;
import java.io.*;
/**
 * 实现比较器接口，用于对String进行不区分大小写的比较
 * 配合TreeMap实现不区分大小写的哈西表。
 */
public class ComparatorIgnoreCase implements Comparator<Object>, Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = -1072218577464299473L;

public ComparatorIgnoreCase() {
  }
  public int compare(Object o1, Object o2) {
    if (o1==null && o2==null){
      return 0;
    }
    if (o1==null){
      return -1;
    }
    if (o2==null){
      return 1;
    }

    if (o1 instanceof BigDecimal && o2 instanceof BigDecimal){
      return ((BigDecimal)o1).compareTo((BigDecimal)o2);
    }
    else if (o1 instanceof Integer && o2 instanceof Integer){
      return ((Integer)o1).compareTo((Integer)o2);
    }
    else{
      String s1 = o1.toString();
      String s2 = o2.toString();
      return s1.toLowerCase().compareTo(s2.toLowerCase());
    }
  }

  public boolean equals(Object obj) {
    return compare(this, obj.toString()) == 0;
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

}