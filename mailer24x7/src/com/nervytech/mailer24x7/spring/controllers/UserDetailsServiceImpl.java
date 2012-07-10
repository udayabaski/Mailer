/**
 * 
 */
package com.nervytech.mailer24x7.spring.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nervytech.mailer24x7.common.enums.UserRoleEnum;
import com.nervytech.mailer24x7.common.enums.UserStatusEnum;
import com.nervytech.mailer24x7.model.dao.UsersDAO;
import com.nervytech.mailer24x7.model.domains.User;
import com.nervytech.mailer24x7.spring.auth.SessionUser;

/**
 * @author ADMIN
 * 
 */
@Service("userDetailsService")
@SessionAttributes("user")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersDAO usersDAO;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		SessionUser springUser = null;
		User user = null;
		try {
			// List<Users> users = usersDAO.checkAuthentication(userName,
			// password);
			List<User> users = usersDAO.getUser(username);
			if (users.size() <= 0) {
				throw new UsernameNotFoundException(
						"Oops!!! Invalid username or password.");
				// result.rejectValue("errorMessage",
				// "invalid.loginForm.errorMessage","Oops!!! Invalid username or password.");
			}
			user = (User) users.get(0);
			// modelMap.addAttribute("user", (Users) users.get(0));

			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			/*
			 * for (UserRoles role : user.getUserRoleses()) {
			 * authorities.add(new GrantedAuthorityImpl(role.getRoleName())); }
			 */
			authorities.add(new GrantedAuthorityImpl(UserRoleEnum.values()[user
					.getRole()].toString()));

			// User springUser = new User(userName, user.getPassword(), true,
			// true, true, true, authorities);
			
			boolean enabled = false;
			if(user.getStatus() == UserStatusEnum.ENABLED.getStatus()) {
				enabled = true;
			}
			
			System.out.println("ENABLEDDDDDDDDDDDDDDD "+enabled+"STATUSSSSSSS "+user.getStatus()+" ENNNNNN "+UserStatusEnum.ENABLED.getStatus());

			springUser = new SessionUser(username, user.getPassword(),
				     authorities, user.getOrgId(), user.getUserId(),enabled);
			
			System.out.println("RETURNING SPRING USERRRRRR");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Error Occured");
		}
		return springUser;
	}

	public static SessionUser currentUserDetails() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			return principal instanceof UserDetails ? (SessionUser) principal
					: null;
		}
		return null;
	}

}