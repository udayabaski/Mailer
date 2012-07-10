/**
 * 
 */
package com.nervytech.mailer24x7.common.enums;

/**
 * @author bsikkaya
 *
 */
public enum SubscriberStatusEnum {
   ACTIVE(0),BOUNCED(1),UNSUBSCRIBED(2);
   
   int status;
   
   private SubscriberStatusEnum(int status) {
	 this.status = status;
   }
   
   public int getStatus() {
   	return status;
  }
}
