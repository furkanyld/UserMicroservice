package tr.edu.ogu.ceng.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import tr.edu.ogu.ceng.Users.entity.Users;
import tr.edu.ogu.ceng.Users.repository.UsersRepository;
import tr.edu.ogu.ceng.Users.service.UsersService;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

	@Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
	
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setEmail("email@example.com");
    }

    @Test
    void testCreateUser() {
        // Mock davranışı: usersRepository.save(user) çağrıldığında user döndür
        when(usersRepository.save(user)).thenReturn(user);

        // Metodu çağır
        Users createdUser = usersService.createUser(user);

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
        Users updatedUser = usersService.updateUser(user);

        // Sonucun null olmadığını doğrula ve özelliklerini kontrol et
        assertNotNull(updatedUser);
        assertEquals("testuser", updatedUser.getUsername());
        verify(usersRepository, times(1)).save(user);
    }
}
