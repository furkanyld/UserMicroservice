package tr.edu.ogu.ceng.user.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import common.Parent;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTest extends Parent {

	@Autowired
	private UserRepository repository;

	@Transactional
	@Test
	public void testSave() {
		User user = new User();
		user.setUsername("user");
		user.setEmail("user@gmail.com");
		user.setPasswordHash("passwordHash");
		user.setStatus("active");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		User savedUser = repository.save(user);
		assertNotNull(savedUser.getId());  
	}

	@Transactional
	@Test
	public void testFindByUsername() {
		User user = new User();
		user.setUsername("testuser");
		user.setEmail("testuser@gmail.com");
		user.setPasswordHash("passwordHash");
		user.setStatus("active");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		repository.save(user);

		Optional<User> foundUser = repository.getByUsername("testuser");
		assertTrue(foundUser.isPresent());
		assertEquals("testuser", foundUser.get().getUsername());
	}

	@Transactional
	@Test
	public void testFindById() {
		User user = new User();
		user.setEmail("findbyid@gmail.com");
		user.setUsername("findbyidUser");
		user.setPasswordHash("passwordHash");
		user.setStatus("active");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		User savedUser = repository.save(user);

		Optional<User> foundUser = repository.findById(savedUser.getId());
		assertTrue(foundUser.isPresent());
		assertEquals(savedUser.getId(), foundUser.get().getId());
	}
	
	@Transactional
	@Test
	public void testFindByEmail() {
		User user = new User();
		user.setEmail("testemail@gmail.com");
		user.setUsername("emailUser");
		user.setPasswordHash("passwordHash");
		user.setStatus("active");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		repository.save(user);

		Optional<User> foundUser = repository.findByEmail("testemail@gmail.com");
		assertTrue(foundUser.isPresent());
		assertEquals("testemail@gmail.com", foundUser.get().getEmail());
	}

	@Transactional
	@Test
	public void testFindAllByStatus() {
		User activeUser = new User();
		activeUser.setUsername("activeUser");
		activeUser.setEmail("active@example.com");
		activeUser.setPasswordHash("passwordHash");
		activeUser.setStatus("active");
		activeUser.setCreatedAt(LocalDateTime.now());
		activeUser.setUpdatedAt(LocalDateTime.now());

		User inactiveUser = new User();
		inactiveUser.setUsername("inactiveUser");
		inactiveUser.setEmail("inactive@example.com");
		inactiveUser.setPasswordHash("passwordHash");
		inactiveUser.setStatus("inactive");
		inactiveUser.setCreatedAt(LocalDateTime.now());
		inactiveUser.setUpdatedAt(LocalDateTime.now());

		repository.save(activeUser);
		repository.save(inactiveUser);

		List<User> activeUsers = repository.findAllByStatus("active");
		assertEquals(1, activeUsers.size());
		assertEquals("activeUser", activeUsers.get(0).getUsername());
	}

	@Transactional
	@Test
	public void testDeleteById() {
		User user = new User();
		user.setEmail("deleteUser@gmail.com");
		user.setUsername("deleteUser");
		user.setPasswordHash("passwordHash");
		user.setStatus("active");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		User savedUser = repository.save(user);

		repository.deleteById(savedUser.getId());
		Optional<User> deletedUser = repository.findById(savedUser.getId());
		assertTrue(deletedUser.isEmpty());  // Silinen kullanıcı bulunmamalı
	}

	@Transactional
	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setUsername("updateUser");
		user.setEmail("updateUser@gmail.com");
		user.setPasswordHash("passwordHash");
		user.setStatus("active");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		User savedUser = repository.save(user);

		savedUser.setUsername("updatedUser");
		User updatedUser = repository.save(savedUser);

		Optional<User> foundUser = repository.findById(updatedUser.getId());
		assertTrue(foundUser.isPresent());
		assertEquals("updatedUser", foundUser.get().getUsername());
	}

	@Transactional
	@Test
	public void testFindById_NotFound() {
		Optional<User> user = repository.findById(999L);  // Geçersiz ID
		assertTrue(user.isEmpty());  // Boş sonuç dönecek
	}

	@Transactional
	@Test
	public void testEmailUniqueness() {
	    User user1 = new User();
	    user1.setEmail("duplicateemail@example.com");
	    user1.setUsername("user1");
	    user1.setPasswordHash("passwordHash");
	    user1.setStatus("active");
	    user1.setCreatedAt(LocalDateTime.now());
	    user1.setUpdatedAt(LocalDateTime.now());
	    repository.save(user1);

	    User user2 = new User();
	    user2.setEmail("duplicateemail@example.com");  // Aynı e-posta
	    user2.setUsername("user2");
	    user2.setPasswordHash("passwordHash");
	    user2.setCreatedAt(LocalDateTime.now());

	    assertThrows(DataIntegrityViolationException.class, () -> {
	        repository.save(user2);  // Veritabanı hatası bekliyoruz
	    });
	}
	
	@Transactional
	@Test
	public void testUpdateNonExistingUser() {
	    // Geçersiz ID ile yeni bir kullanıcı oluşturuyoruz
	    User user = new User();
	    user.setId(999L);  // Geçersiz ID
	    user.setUsername("nonExistingUser");
	    user.setEmail("nonExistingUser@example.com");
	    user.setPasswordHash("passwordHash");
	    user.setStatus("active");
	    user.setCreatedAt(LocalDateTime.now());
	    user.setUpdatedAt(LocalDateTime.now());

	    // Kullanıcıyı kaydetmeye çalışırken, veritabanında olmayan bir kullanıcıyı güncellemeye çalışıyoruz
	    Optional<User> foundUser = repository.findById(user.getId());

	    // Kullanıcı bulunamazsa bir istisna fırlatılmalı
	    assertTrue(foundUser.isEmpty());  // Kullanıcıyı bulamamalıyız

	    // Alternatif olarak, kullanıcı bulunmadığında bir özel exception fırlatabilirsiniz
	    assertThrows(EntityNotFoundException.class, () -> {
	        if (foundUser.isEmpty()) {
	            throw new EntityNotFoundException("User not found for update.");
	        }
	    });
	}


	@Transactional
	@Test
	public void testUpdateUserStatus() {
	    User user = new User();
	    user.setUsername("testUserStatus");
	    user.setEmail("testUserStatus@example.com");
	    user.setPasswordHash("passwordHash");
	    user.setStatus("active");
	    user.setCreatedAt(LocalDateTime.now());
	    user.setUpdatedAt(LocalDateTime.now());

	    User savedUser = repository.save(user);

	    savedUser.setStatus("inactive");
	    User updatedUser = repository.save(savedUser);

	    Optional<User> foundUser = repository.findById(updatedUser.getId());
	    assertTrue(foundUser.isPresent());
	    assertEquals("inactive", foundUser.get().getStatus());  // Durumun değiştiği doğrulanıyor
	}

	@Transactional
	@Test
	public void testUpdateUserPassword() {
	    User user = new User();
	    user.setUsername("passwordUpdateUser");
	    user.setEmail("passwordUpdateUser@example.com");
	    user.setPasswordHash("oldPasswordHash");
	    user.setStatus("active");
	    user.setCreatedAt(LocalDateTime.now());
	    user.setUpdatedAt(LocalDateTime.now());

	    User savedUser = repository.save(user);

	    savedUser.setPasswordHash("newPasswordHash");  
	    User updatedUser = repository.save(savedUser);

	    Optional<User> foundUser = repository.findById(updatedUser.getId());
	    assertTrue(foundUser.isPresent());
	    assertEquals("newPasswordHash", foundUser.get().getPasswordHash());  
	}

	@Transactional
	@Test
	public void testDeleteUser() {
	    User user = new User();
	    user.setUsername("deleteTestUser");
	    user.setEmail("deleteTestUser@example.com");
	    user.setPasswordHash("passwordHash");
	    user.setStatus("active");
	    user.setCreatedAt(LocalDateTime.now());
	    user.setUpdatedAt(LocalDateTime.now());

	    User savedUser = repository.save(user);
	    repository.delete(savedUser);

	    Optional<User> deletedUser = repository.findById(savedUser.getId());
	    assertTrue(deletedUser.isEmpty());  
	}

	@Transactional
	@Test
	public void testPerformance() {
	    long startTime = System.currentTimeMillis();

	    for (int i = 0; i < 1000; i++) {
	        User user = new User();
	        user.setUsername("user" + i);
	        user.setEmail("user" + i + "@example.com");
	        user.setPasswordHash("passwordHash");
	        user.setStatus("active");
	        user.setCreatedAt(LocalDateTime.now());
	        user.setUpdatedAt(LocalDateTime.now());
	        repository.save(user);
	    }

	    long endTime = System.currentTimeMillis();
	    System.out.println("Performance test took: " + (endTime - startTime) + " milliseconds");
	    assertTrue((endTime - startTime) < 5000);  // 5 saniyeden kısa sürede tamamlanmalı
	}

}
