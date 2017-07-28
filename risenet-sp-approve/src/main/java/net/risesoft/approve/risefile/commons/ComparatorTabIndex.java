package net.risesoft.approve.risefile.commons;

import java.util.Comparator;



public class ComparatorTabIndex implements Comparator {
  public ComparatorTabIndex() {
  }
  public int compare(Object o1, Object o2) {
    return ( (TabIndex) o1).getTabIndex() - ( (TabIndex) o2).getTabIndex();
  }

  public boolean equals(Object obj) {
    return compare(this, obj) == 0;
  }

}

