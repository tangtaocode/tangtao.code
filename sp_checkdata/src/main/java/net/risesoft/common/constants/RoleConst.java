

/**
 * Copyright (c) 2003 RiseSoft Co.,Ltd
 * $Header$
 */

package net.risesoft.common.constants;
/**
 * @author 赵斌 2003-7-29
 * @version $Revision$
 */

public interface RoleConst {
  /**
   全局用户组 	110
   平台用户组   115
   流程用户组 	120
   本部门       130
   所有子部门 	140
   人员		150
   内置用户	160
   规则函数 	170
   Java脚本 	180
   子类接口 	190
   */
  public static final int ROLE_GROUP_GLOBAL = 110;
  public static final int ROLE_GROUP_PLATFORM = 115;
  public static final int ROLE_GROUP_WORKFLOW = 120;
  public static final int ROLE_DEPARTMENT = 130;
  public static final int ROLE_DEPARTMENT_RECURSION = 140;
  public static final int ROLE_EMPLOYEE = 150;
  public static final int ROLE_INNER = 160;
  public static final int ROLE_FUNCTION = 170;
  public static final int ROLE_SCRIPT = 180;
  public static final int ROLE_SUBCLASS = 190;
  

  public final static String ROLE_INNER_SENDER_GUID = "{WORKFLOW-ROLE-0000-0000-SENDER000000}";
  public final static String ROLE_INNER_SENDER_NAME = "Sender";
  public final static String ROLE_INNER_STARTER_GUID = "{WORKFLOW-ROLE-0000-0000-STARTER00000}";
  public final static String ROLE_INNER_STARTER_NAME = "Starter";
  public final static String ROLE_INNER_CURRENTUSER_GUID = "{WORKFLOW-ROLE-0000-0000-CURRENTUSER0}";
  public final static String ROLE_INNER_CURRENTUSER_NAME = "CurrentUser";
  public final static String ROLE_INNER_CURRENTRISEUSER_NAME = "CurrentRiseUser";
  public final static String ROLE_INNER_ANYUSER_GUID = "{WORKFLOW-ROLE-0000-0000-ANYUSER00000}";
  public final static String ROLE_INNER_ANYUSER_NAME = "AnyUser";
}