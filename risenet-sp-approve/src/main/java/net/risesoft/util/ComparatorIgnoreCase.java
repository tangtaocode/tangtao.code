/**
 * Copyright (c) 2003 RiseSoft Co.,Ltd
 * $Header$
 */
package net.risesoft.util;

import java.util.Comparator;
import java.math.BigDecimal;
import java.io.*;
/**
 * Title:        Java版工作流系统
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      国家信息中心国家计委学术委员会软件评测研究中心
 *
 * 实现比较器接口，用于对String进行不区分大小写的比较
 * 配合TreeMap实现不区分大小写的哈西表。
 *
 * @author 赵斌 2003-6-23
 * @version $Revision$
 */
public class ComparatorIgnoreCase implements Comparator, Serializable {
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