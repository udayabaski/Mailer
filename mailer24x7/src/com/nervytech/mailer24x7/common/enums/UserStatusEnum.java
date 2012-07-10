/**
 * 
 */
package com.nervytech.mailer24x7.common.enums;

/**
 * @author bsikkaya
 *
 */
public enum UserStatusEnum {
	DISABLED(0),ENABLED(1);
   
   int status;
   
   private UserStatusEnum(int status) {
	 this.status = status;
   }
   
   public int getStatus() {
   	return status;
  }
   
}
