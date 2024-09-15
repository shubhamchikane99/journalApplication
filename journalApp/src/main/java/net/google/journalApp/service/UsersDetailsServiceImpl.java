package net.google.journalApp.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UsersRepository;

@Component
public class UsersDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) {

		System.err.println("userName");
		Users users = usersRepository.findUserByUserName(userName);

		if (users != null) {
			
			return org.springframework.security.core.userdetails
					.User.builder()
					.username(users.getUserName())
					.password(users.getPassword())
					.roles(users.getRole()).build();
		
		}
		throw new UsernameNotFoundException("User Not Found With UserName " + userName);
	}

}
