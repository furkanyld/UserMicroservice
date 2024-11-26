package tr.edu.ogu.ceng.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tr.edu.ogu.ceng.User.UsersApplication;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.repository.UserRepository;
import tr.edu.ogu.ceng.User.service.UserService;

@SpringBootTest(classes = {UsersApplication.class})
class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userservice;

	
	@Test
    void testCreateUser() {
        // Hazırlık
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");

        when(userRepository.save(newUser)).thenReturn(newUser);

        // Çalıştır
        User result = userservice.createUser(newUser);

        // Doğrulama
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
	
}
