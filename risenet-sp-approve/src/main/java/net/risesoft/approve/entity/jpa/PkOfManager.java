/**
 * @Project Name:risenet-sp-approve
 * @File Name: PkOfManager.java
 * @Package Name: net.risesoft.approve.entity.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月17日 下午4:00:15
 */
package net.risesoft.approve.entity.jpa;
/**
 * @ClassName: PkOfManager.java
 * @Description: OrderManager的主键类
 *
 * @author chenbingni
 * @date 2016年3月17日 下午4:00:15
 * @version 
 * @since JDK 1.7
 */

public class PkOfManager implements java.io.Serializable {
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;

	private String type;
	
	private String approveitemguid;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	
	public String getApproveitemguid() {
		return approveitemguid;
	}

	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}

	@Override  
    public boolean equals(Object o) {  
        if(o instanceof PkOfManager){  
        	PkOfManager key = (PkOfManager)o ;  
            if(this.approveitemguid == key.getApproveitemguid() && this.type.equals(key.getType())){  
                return true ;  
            }  
        }  
        return false ;  
    }  
      
    @Override  
    public int hashCode() {  
        return this.type.hashCode();  
    } 
}

