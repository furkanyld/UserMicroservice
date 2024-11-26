package tr.edu.ogu.ceng.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tr.edu.ogu.ceng.User.UserApplication;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.repository.UserRepository;
import tr.edu.ogu.ceng.User.service.UserService;

@SpringBootTest
class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userservice;

	@Test
	void testCreateUser() {

		User newUser = new User();
		newUser.setUsername("testUser");
		newUser.setEmail("test@example.com");

		when(userRepository.save(newUser)).thenReturn(newUser);

		User result = userservice.createUser(newUser);

		assertEquals("testUser", result.getUsername());
		assertEquals("test@example.com", result.getEmail());
	}

	@Test
	void testUpdateUser() {

		User existingUser = new User();
		existingUser.setId(1L);
		existingUser.setUsername("updatedUser");
		existingUser.setEmail("updated@example.com");

		when(userRepository.save(existingUser)).thenReturn(existingUser);

		User result = userservice.updateUser(existingUser);

		assertEquals(1L, result.getId());
		assertEquals("updatedUser", result.getUsername());
		assertEquals("updated@example.com", result.getEmail());
	}

	@Test
	void testGetByUsername() {

		String username = "testuser";
		User mockUser = new User();
		mockUser.setUsername(username);
		when(userRepository.getByUsername(username)).thenReturn(Optional.of(mockUser));

		Optional<User> result = userservice.getByUsername(username);

		assertTrue(result.isPresent());
		assertEquals(username, result.get().getUsername());
	}

	@Test
	void testGetByEmail_UserExists() {

		String email = "test@example.com";
		User mockUser = new User();
		mockUser.setEmail(email);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

		Optional<User> result = userservice.getByEmail(email);

		assertTrue(result.isPresent());
		assertEquals(email, result.get().getEmail());
	}

	@Test
	void testDeleteUserById() {

		Long userId = 1L;

		userservice.deleteUserById(userId);

		verify(userRepository, times(1)).deleteById(userId);
	}

	@Test
	void testGetAllUsers() {

		List<User> mockUsers = Arrays.asList(new User(), new User());
		when(userRepository.findAll()).thenReturn(mockUsers);

		List<User> result = userservice.getAllUsers();

		assertEquals(2, result.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void testChangeUserStatus_UserExists() {

		Long userId = 1L;
		String newStatus = "inactive";
		User mockUser = new User();
		mockUser.setId(userId);
		mockUser.setStatus("active");

		when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
		when(userRepository.save(mockUser)).thenReturn(mockUser);

		User result = userservice.changeUserStatus(userId, newStatus);

		assertEquals(newStatus, result.getStatus());
		verify(userRepository, times(1)).save(mockUser);
	}

	@Test
	void testUpdateUserPassword_UserExists() {

		Long userId = 1L;
		String newPasswordHash = "newHash";
		User mockUser = new User();
		mockUser.setId(userId);
		mockUser.setPasswordHash("oldHash");

		when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
		when(userRepository.save(mockUser)).thenReturn(mockUser);

		User result = userservice.updateUserPassword(userId, newPasswordHash);

		assertEquals(newPasswordHash, result.getPasswordHash());
		verify(userRepository, times(1)).save(mockUser);
	}

}
