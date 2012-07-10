/**
 * 
 */
package com.nervytech.mailer24x7.spring.form;

import org.springframework.stereotype.Repository;

/**
 * @author ADMIN
 *
 */
@Repository("loginForm")
public class LoginForm {
  
    private String userName;

    private String password;
	
    private String userType;
    
    private String errorMessage;
    
    private String generalMessage;
    

	public String getGeneralMessage() {
		return generalMessage;
	}
	public void setGeneralMessage(String generalMessage) {
		this.generalMessage = generalMessage;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setUserName(String userName) {
			this.userName = userName;
    }
    public String getUserName() {
            return userName;
    }
    /**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setPassword(String password) {
            this.password = password;
    }
    public String getPassword() {
            return password;
    }
}
