package net.google.journalApp.service;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UsersRepository;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

@SpringBootTest
public class UsersDetailsServiceImplTest {

	private UsersDetailsServiceImpl usersDetailsService;

	@Mock
	private UsersRepository userRepository;

	@Test
	void loadUserNameTest() {

		//when(userRepository.findUserByUserName(ArgumentMatchers.anyString()))
				//.thenReturn(new Users("ajay_zalte", "klkl"));
		UserDetails user = usersDetailsService.loadUserByUsername("ajay_zalte");
	}
}
