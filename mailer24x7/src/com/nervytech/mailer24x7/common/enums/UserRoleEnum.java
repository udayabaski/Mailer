/**
 * 
 */
package com.nervytech.mailer24x7.common.enums;

/**
 * @author bsikkaya
 *
 */
public enum UserRoleEnum {
   USER(0),ADMIN(1);  
   
   int role;
   
   private UserRoleEnum(int role) {
	 this.role = role;
   }
   
   public int getRole() {
   	return role;
  }
}
