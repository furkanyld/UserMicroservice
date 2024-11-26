package tr.edu.ogu.ceng.user.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.repository.UserRepository;
import tr.edu.ogu.ceng.User.service.UserService;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class UserIntegrationTest {

	@Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
	
	@Autowired
    private UserRepository usersRepository;

	@Autowired
    private UserService usersService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setEmail("email@example.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus("admin");
    }
    
    @Test
    void SaveUserToDatabase() {
        // Act: User nesnesini veri tabanına kaydet
        User savedUser = usersRepository.save(user);

        // Assert: Kaydedilen kullanıcı geri dönmeli ve id otomatik olarak atanmalı
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull(); // id otomatik olarak atanmalı
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("email@example.com");
    }
    @Test
    void testCreateUser() {
        // Mock davranışı: usersRepository.save(user) çağrıldığında user döndür
        when(usersRepository.save(user)).thenReturn(user);

        // Metodu çağır
        User createdUser = usersService.createUser(user);

        // Sonucun null olmadığını doğrula ve özelliklerini kontrol et
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        // Mock davranışı: usersRepository.save(user) çağrıldığında user döndür
        when(usersRepository.save(user)).thenReturn(user);

        // Metodu çağır
        User updatedUser = usersService.updateUser(user);

        // Sonucun null olmadığını doğrula ve özelliklerini kontrol et
        assertNotNull(updatedUser);
        assertEquals("testuser", updatedUser.getUsername());
        verify(usersRepository, times(1)).save(user);
    }
}
