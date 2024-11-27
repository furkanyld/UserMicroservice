package tr.edu.ogu.ceng.user.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import jakarta.transaction.Transactional;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.entity.UserRole;
import tr.edu.ogu.ceng.User.repository.RoleRepository;
import tr.edu.ogu.ceng.User.repository.UserRepository;
import tr.edu.ogu.ceng.User.repository.UserRoleRepository;

@SpringBootTest
public class UserRoleRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16-alpine"));

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Test
    public void testSaveUserRole() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        User savedUser = userRepository.save(user);

        Role role = new Role();
        role.setRoleName("ADMIN");
        role.setDescription("Administration role");
        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(savedRole);
        userRole.setAssignedAt(LocalDateTime.now());
        userRole.setUpdatedAt(LocalDateTime.now());
        userRole.setCreatedBy("system");

        UserRole savedUserRole = userRoleRepository.save(userRole);

        assertNotNull(savedUserRole.getId());
        assertEquals("testuser", savedUserRole.getUser().getUsername());
        assertEquals("ADMIN", savedUserRole.getRole().getRoleName());
    }

    @Transactional
    @Test
    void testFindUserRoleById() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        User savedUser = userRepository.save(user);

        Role role = new Role();
        role.setRoleName("USER");
        role.setDescription("User role");
        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(savedRole);
        userRole.setAssignedAt(LocalDateTime.now());
        userRole.setUpdatedAt(LocalDateTime.now());
        userRole.setCreatedBy("system");

        UserRole savedUserRole = userRoleRepository.save(userRole);

        Optional<UserRole> foundUserRole = userRoleRepository.findById(savedUserRole.getId());

        assertTrue(foundUserRole.isPresent());
        assertEquals(savedUserRole.getId(), foundUserRole.get().getId());
    }

    @Transactional
    @Test
    void testUpdateUserRole() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        User savedUser = userRepository.save(user);

        Role role = new Role();
        role.setRoleName("USER");
        role.setDescription("User role");
        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(savedRole);
        userRole.setAssignedAt(LocalDateTime.now());
        userRole.setUpdatedAt(LocalDateTime.now());
        userRole.setCreatedBy("system");

        UserRole savedUserRole = userRoleRepository.save(userRole);

        savedUserRole.setUpdatedAt(LocalDateTime.now().plusDays(1));
        savedUserRole.setUpdatedBy("admin");

        UserRole updatedUserRole = userRoleRepository.save(savedUserRole);

        assertEquals("admin", updatedUserRole.getUpdatedBy());
        assertNotNull(updatedUserRole.getUpdatedAt());
    }

    @Transactional
    @Test
    void testDeleteUserRole() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        User savedUser = userRepository.save(user);

        Role role = new Role();
        role.setRoleName("MODERATOR");
        role.setDescription("Moderator role");
        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(savedRole);
        userRole.setAssignedAt(LocalDateTime.now());
        userRole.setUpdatedAt(LocalDateTime.now());
      

        UserRole savedUserRole = userRoleRepository.save(userRole);

        userRoleRepository.deleteById(savedUserRole.getId());

        Optional<UserRole> deletedUserRole = userRoleRepository.findById(savedUserRole.getId());

        assertFalse(deletedUserRole.isPresent());
    }

    @Transactional
    @Test
    void testGetAllUserRoles() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        User savedUser1 = userRepository.save(user1);

        Role role1 = new Role();
        role1.setRoleName("ADMIN");
        role1.setDescription("Admin role");
        Role savedRole1 = roleRepository.save(role1);

        UserRole userRole1 = new UserRole();
        userRole1.setUser(savedUser1);
        userRole1.setRole(savedRole1);
        userRole1.setAssignedAt(LocalDateTime.now());
        userRole1.setUpdatedAt(LocalDateTime.now());
        userRoleRepository.save(userRole1);

        List<UserRole> userRoles = userRoleRepository.findAll();

        assertNotNull(userRoles);
        assertEquals(1, userRoles.size());
    }
}
