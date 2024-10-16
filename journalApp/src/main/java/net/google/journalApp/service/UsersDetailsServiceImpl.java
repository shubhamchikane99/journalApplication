package net.google.journalApp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import net.google.journalApp.entity.AccessRole;
import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UsersRepository;

@Component
public class UsersDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) {

		System.err.println("userName");
		Users users = usersRepository.findByUserName(userName);

		if (users != null) {
			
			List<AccessRole> accessRoles = users.getAccessRole(); // Ensure the return type is correct
			
			List<String> roles = accessRoles.stream()
			                                .map(AccessRole::getAccessRoleName) // Use method reference for clarity
			                                .collect(Collectors.toList());
			
			return org.springframework.security.core.userdetails
					.User.builder()
					.username(users.getUserName())
					.password(users.getPassword())
					.roles(roles.toArray(new String[0]))
		            .build();
		
		}
		throw new UsernameNotFoundException("User Not Found With UserName : " + userName);
	}

}
