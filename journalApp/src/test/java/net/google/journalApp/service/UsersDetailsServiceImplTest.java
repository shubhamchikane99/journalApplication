package net.google.journalApp.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UsersRepository;



@SpringBootTest
public class UsersDetailsServiceImplTest {

	private UsersDetailsServiceImpl usersDetailsService;

	@Mock
	private UsersRepository userRepository;

	@Test
	void loadUserNameTest() {

		when(userRepository.findUserByUserName(ArgumentMatchers.anyString()))
				.thenReturn(Users.builder().userName("ram").password("ram").accessRole(new ArrayList<>()).build());
		UserDetails user = usersDetailsService.loadUserByUsername("ram");
		Assertions.assertNotNull(user);
		
	}
	
}
