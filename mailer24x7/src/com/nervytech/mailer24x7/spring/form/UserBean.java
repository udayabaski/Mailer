/**
 * 
 */
package com.nervytech.mailer24x7.spring.form;

import java.util.List;

import com.nervytech.mailer24x7.model.domains.User;

/**
 * @author bsikkaya
 *
 */
public class UserBean {
   
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
