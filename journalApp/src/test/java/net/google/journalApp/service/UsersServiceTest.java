package net.google.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UsersRepository;

@SpringBootTest
public class UsersServiceTest {

	@Autowired
	private UsersRepository usersRepository;

	@BeforeEach
	void setUp() {
		// If you want to initialize something before executing the each test case, use the
		// @BeforeEach() annotation in JUnit testing.

	}
	
	@BeforeAll
	void setUp2() {
		  //if you want to initialize something before executing all test case use the 
		 // @BeforeAll Annotation In JUnit Testing
	}

	// Test 1
	@Disabled
	@Test
	public void testFindByUserName() {
		assertEquals(4, 2 + 2);

		assertNotNull(usersRepository.findUserByUserName("ajay_zalte"));

		Users user = usersRepository.findUserByUserName("ajay_zalte");

		assertTrue(user.getMobileNo().isEmpty());

	}

	// Test 2
	@Disabled
	@ParameterizedTest
	@CsvSource({ "1, 1, 2", "2, 10, 12", "3, 3, 6", "3, 3, 6" })
	public void test(int a, int b, int expected) {
		assertEquals(expected, a + b);
	}

	// Test 3
	@Disabled
	@ParameterizedTest
	@ValueSource(strings = { "ajay_zalte", "mr_stark", "mr_tony", "mr_tony1" })
	public void userNamePresentOrNot(String name) {

		assertNotNull(usersRepository.findUserByUserName(name), "failed for: " + name);
	}

	@ParameterizedTest
	@ArgumentsSource(UserArgumentsProvider.class)
	public void testFindByUserName(Users users) {

		assertNotNull(usersRepository.findUserByUserName(null));
	}

}
