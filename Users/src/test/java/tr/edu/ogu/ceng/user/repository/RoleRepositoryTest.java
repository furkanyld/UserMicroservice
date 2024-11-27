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
import tr.edu.ogu.ceng.User.repository.RoleRepository;

@SpringBootTest
public class RoleRepositoryTest {

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
    private RoleRepository roleRepository;

    @Transactional
    @Test
    public void TestSaveRole() {
        Role role = new Role();
        role.setRoleName("ADMIN");
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        role.setDescription("Administration role");
        Role savedRole = roleRepository.save(role);

        assertNotNull(savedRole.getId()); 
        assertEquals("ADMIN", savedRole.getRoleName()); 
    }

    @Transactional
    @Test
    public void testFindByName() {
        Role role = new Role();
        role.setRoleName("USER");
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        role.setDescription("Standard user role");
        roleRepository.save(role);

        Optional<Role> foundRole = roleRepository.findByRoleName("USER");

        assertTrue(foundRole.isPresent());
        assertEquals("USER", foundRole.get().getRoleName());
    }
    
    @Transactional
    @Test
    void testLargeNumberOfRecords() {
        for (int i = 0; i < 1000; i++) {
            Role role = new Role();
            role.setRoleName("Role" + i);
            role.setCreatedAt(LocalDateTime.now());
            role.setUpdatedAt(LocalDateTime.now());
            role.setDescription("Standard user role" + i);
            roleRepository.save(role);
        }

        long count = roleRepository.count();
        assertEquals(1000, count);
    }
    
    @Transactional
    @Test
    void testDataConsistency() {
        Role role = new Role();
        role.setRoleName("MANAGER");
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        role.setDescription("Management user role");
        roleRepository.save(role);

        Role retrievedRole = roleRepository.findById(role.getId()).orElse(null);
        assertEquals(role.getRoleName(), retrievedRole.getRoleName());
    }
    
    @Transactional
    @Test
	void testCreateRole() {
		Role role = new Role();
		role.setRoleName("Admin");
		role.setDescription("Administration role");
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());

		Role createdRole = roleRepository.save(role);

		assertNotNull(createdRole.getId());
		assertEquals("Admin", createdRole.getRoleName());
	}

    @Transactional
	@Test
	void testUpdateRole() {
		Role existingRole = new Role();
		existingRole.setRoleName("User");
		existingRole.setDescription("User role");
		existingRole.setCreatedAt(LocalDateTime.now());
		existingRole.setUpdatedAt(LocalDateTime.now());
		
		roleRepository.save(existingRole);
		
		existingRole.setRoleName("Moderator");
		Role updatedRole = roleRepository.save(existingRole);
		
		assertEquals("Moderator", updatedRole.getRoleName());
	}

    @Transactional
	@Test
	void testGetAllRoles() {
		Role role1 = new Role();
	    role1.setRoleName("Admin");
	    role1.setCreatedAt(LocalDateTime.now());
	    role1.setUpdatedAt(LocalDateTime.now());
	    role1.setDescription("Administration role");
	    roleRepository.save(role1);

	    Role role2 = new Role();
	    role2.setRoleName("User");
	    role2.setCreatedAt(LocalDateTime.now());
	    role2.setUpdatedAt(LocalDateTime.now());
	    role2.setDescription("User role");
	    roleRepository.save(role2);

	    List<Role> roles = roleRepository.findAll();
	    
	    assertNotNull(roles);
	    assertEquals(2, roles.size());
	    assertTrue(roles.stream().anyMatch(r -> "Admin".equals(r.getRoleName())));
	    assertTrue(roles.stream().anyMatch(r -> "User".equals(r.getRoleName())));
	}

    @Transactional
	@Test
	void testGetRoleById_NotFound() {
		Optional<Role> retrievedRole = roleRepository.findById(1500L);
	    assertFalse(retrievedRole.isPresent());
	}

    @Transactional
	@Test
	void testHardDeleteRole() {
	    Role role = new Role();
	    role.setRoleName("Admin");
	    role.setCreatedAt(LocalDateTime.now());
	    role.setUpdatedAt(LocalDateTime.now());
	    role.setDescription("Administration role");
	    Role savedRole = roleRepository.save(role);

	    roleRepository.deleteById(savedRole.getId());

	    Optional<Role> deletedRole = roleRepository.findById(savedRole.getId());
	    assertFalse(deletedRole.isPresent());
	}
    
    @Transactional
	@Test
	public void testSoftDeleteRole() {
	    Role role = new Role();
	    role.setRoleName("Admin");
	    role.setCreatedAt(LocalDateTime.now());
	    role.setUpdatedAt(LocalDateTime.now());
	    role.setDescription("Administration role");
	    Role savedRole = roleRepository.save(role);

	    savedRole.setDeletedAt(LocalDateTime.now());
	    savedRole.setDeletedBy("system");
	    roleRepository.save(savedRole);

	    Role softDeletedRole = roleRepository.findById(savedRole.getId()).orElse(null);
	    assertNotNull(softDeletedRole);
	    assertNotNull(softDeletedRole.getDeletedAt());
	    assertEquals("system", softDeletedRole.getDeletedBy());
	}
}