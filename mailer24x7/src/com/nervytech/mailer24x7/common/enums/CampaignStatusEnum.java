/**
 * 
 */
package com.nervytech.mailer24x7.common.enums;

/**
 * @author bsikkaya
 *
 */
public enum CampaignStatusEnum {
   DRAFT(0),NOW(1),SCHEDULED(2),COMPLETED(3);
   
   int status;
   
   private CampaignStatusEnum(int status) {
	 this.status = status;
   }
   
   public int getStatus() {
   	return status;
  }
}
