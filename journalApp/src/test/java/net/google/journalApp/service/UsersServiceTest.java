package net.google.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UsersRepository;

@SpringBootTest
public class UsersServiceTest {

	@Autowired
	private UsersRepository usersRepository;
	
	@Test
	public void testFindByUserName() {
		  assertEquals(4, 2 + 2); 
		  
		  assertNotNull(usersRepository.findUserByUserName("ajay_zalte"));
		  
		  Users user = usersRepository.findUserByUserName("ajay_zalte");
		  
		  assertTrue(user.getMobileNo().isEmpty());
		  
		
	}
}
