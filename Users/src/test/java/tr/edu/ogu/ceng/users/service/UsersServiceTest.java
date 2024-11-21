package tr.edu.ogu.ceng.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tr.edu.ogu.ceng.Users.entity.Users;
import tr.edu.ogu.ceng.Users.repository.UsersRepository;
import tr.edu.ogu.ceng.Users.service.UsersService;

@SpringBootTest
class UsersServiceTest {

	@MockBean
	private UsersRepository usersRepository;

	@Autowired
	private UsersService usersService;

	@Test
	void testCreateUser() {
		Users user = new Users();
		user.setId(1L);
		user.setUsername("testUser");
		user.setEmail("test@example.com");
		user.setPasswordHash("hashedPassword");
		user.setStatus("ACTIVE");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		when(usersRepository.save(user)).thenReturn(user);

		Users createdUser = usersService.createUser(user);

		assertNotNull(createdUser);
		assertEquals("testUser", createdUser.getUsername());
		verify(usersRepository, times(1)).save(user);
	}

	@Test
	void testUpdateUser() {
		Users user = new Users();
		user.setId(1L);
		user.setUsername("updatedUser");
		user.setEmail("updated@example.com");
		user.setPasswordHash("updatedHash");
		user.setStatus("ACTIVE");
		user.setUpdatedAt(LocalDateTime.now());

		when(usersRepository.save(user)).thenReturn(user);

		Users updatedUser = usersService.updateUser(user);

		assertNotNull(updatedUser);
		assertEquals("updatedUser", updatedUser.getUsername());
		assertEquals("updated@example.com", updatedUser.getEmail());
		verify(usersRepository, times(1)).save(user);
	}

	@Test
	void testFindUserById() {
		Users user = new Users();
		user.setId(1L);
		user.setUsername("testUser");

		when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

		Optional<Users> foundUser = usersRepository.findById(1L);

		assertTrue(foundUser.isPresent());
		assertEquals("testUser", foundUser.get().getUsername());
		verify(usersRepository, times(1)).findById(1L);
	}

	@Test
	void testFindAllUsers() {
		Users user1 = new Users();
		user1.setId(1L);
		user1.setUsername("user1");

		Users user2 = new Users();
		user2.setId(2L);
		user2.setUsername("user2");

		when(usersRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

		Iterable<Users> usersList = usersRepository.findAll();

		assertNotNull(usersList);
		assertEquals(2, ((List<Users>) usersList).size());
		verify(usersRepository, times(1)).findAll();
	}

	@Test
	void testDeleteUser() {
		Long userId = 1L;

		doNothing().when(usersRepository).deleteById(userId);

		usersRepository.deleteById(userId);

		verify(usersRepository, times(1)).deleteById(userId);
	}

	@Test
	void testUpdateNonExistentUser() {
		Users user = new Users();
		user.setId(99L);

		when(usersRepository.save(user)).thenThrow(new RuntimeException("User not found"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			usersService.updateUser(user);
		});

		assertEquals("User not found", exception.getMessage());
		verify(usersRepository, times(1)).save(user);

	}
}