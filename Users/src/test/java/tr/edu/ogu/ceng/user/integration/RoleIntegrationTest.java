package tr.edu.ogu.ceng.user.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.repository.RoleRepository;

@Testcontainers
@SpringBootTest
public class RoleIntegrationTest {

	// PostgreSQL Container
	@Container
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:16-alpine"));

	// Spring Boot'un test ortamı için dinamik özellikleri ayarlıyoruz.
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	private RoleRepository rolesRepository;

	@Test
	public void testCreateRole() {
		// Yeni bir Role oluştur ve kaydet
		Role role = new Role();
		role.setRoleName("Admin");
		Role savedRole = rolesRepository.save(role);

		// Doğrulamalar
		assertThat(savedRole).isNotNull();
		assertThat(savedRole.getId()).isNotNull(); // Otomatik ID atanmış olmalı
		assertThat(savedRole.getRoleName()).isEqualTo("Admin");
	}

	@Test
	public void testUpdateRole() {
		// Önce bir Role kaydet
		Role role = new Role();
		role.setRoleName("User");
		Role savedRole = rolesRepository.save(role);

		// Role'ü güncelle
		savedRole.setRoleName("SuperUser");
		Role updatedRole = rolesRepository.save(savedRole);

		// Doğrulamalar
		assertThat(updatedRole).isNotNull();
		assertThat(updatedRole.getRoleName()).isEqualTo("SuperUser");
	}

	@Test
	public void testGetAllRoles() {
		// Bazı roller ekle
		Role role1 = new Role();
		role1.setRoleName("Role1");
		Role role2 = new Role();
		role2.setRoleName("Role2");

		rolesRepository.save(role1);
		rolesRepository.save(role2);

		// Tüm roller al ve doğrula
		List<Role> rolesList = rolesRepository.findAll();
		assertThat(rolesList).isNotEmpty();
		assertEquals(2, rolesList.size());
	}

	@Test
	public void testGetRoleById() {
		// Bir Role ekle ve ID'yi al
		Role role = new Role();
		role.setRoleName("Admin");
		Role savedRole = rolesRepository.save(role);

		// ID ile role al ve doğrula
		Role foundRole = rolesRepository.findById(savedRole.getId()).orElse(null);
		assertThat(foundRole).isNotNull();
		assertThat(foundRole.getRoleName()).isEqualTo("Admin");
	}

	@Test
	public void testDeleteRole() {
		// Bir Role ekle
		Role role = new Role();
		role.setRoleName("Admin");
		Role savedRole = rolesRepository.save(role);

		// Role'ü sil
		rolesRepository.deleteById(savedRole.getId());

		// Doğrula: Role artık mevcut olmamalı
		boolean exists = rolesRepository.existsById(savedRole.getId());
		assertThat(exists).isFalse();
	}
}
