/**
 * 
 */
package com.nervytech.mailer24x7.common.enums;

/**
 * @author bsikkaya
 *
 */
public enum OrgStatusEnum {
	DISABLED(0),ENABLED(1);   
   
   int status;
   
   private OrgStatusEnum(int status) {
	 this.status = status;
   }
   
   public int getStatus() {
   	return status;
  }
}
