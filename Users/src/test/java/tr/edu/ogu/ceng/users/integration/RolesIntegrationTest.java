package tr.edu.ogu.ceng.users.integration;

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

import tr.edu.ogu.ceng.Users.entity.Roles;
import tr.edu.ogu.ceng.Users.repository.RolesRepository;

@Testcontainers
@SpringBootTest
public class RolesIntegrationTest {

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
	private RolesRepository rolesRepository;

	@Test
	public void testCreateRole() {
		// Yeni bir Role oluştur ve kaydet
		Roles role = new Roles();
		role.setRoleName("Admin");
		Roles savedRole = rolesRepository.save(role);

		// Doğrulamalar
		assertThat(savedRole).isNotNull();
		assertThat(savedRole.getId()).isNotNull(); // Otomatik ID atanmış olmalı
		assertThat(savedRole.getRoleName()).isEqualTo("Admin");
	}

	@Test
	public void testUpdateRole() {
		// Önce bir Role kaydet
		Roles role = new Roles();
		role.setRoleName("User");
		Roles savedRole = rolesRepository.save(role);

		// Role'ü güncelle
		savedRole.setRoleName("SuperUser");
		Roles updatedRole = rolesRepository.save(savedRole);

		// Doğrulamalar
		assertThat(updatedRole).isNotNull();
		assertThat(updatedRole.getRoleName()).isEqualTo("SuperUser");
	}

	@Test
	public void testGetAllRoles() {
		// Bazı roller ekle
		Roles role1 = new Roles();
		role1.setRoleName("Role1");
		Roles role2 = new Roles();
		role2.setRoleName("Role2");

		rolesRepository.save(role1);
		rolesRepository.save(role2);

		// Tüm roller al ve doğrula
		List<Roles> rolesList = rolesRepository.findAll();
		assertThat(rolesList).isNotEmpty();
		assertEquals(2, rolesList.size());
	}

	@Test
	public void testGetRoleById() {
		// Bir Role ekle ve ID'yi al
		Roles role = new Roles();
		role.setRoleName("Admin");
		Roles savedRole = rolesRepository.save(role);

		// ID ile role al ve doğrula
		Roles foundRole = rolesRepository.findById(savedRole.getId()).orElse(null);
		assertThat(foundRole).isNotNull();
		assertThat(foundRole.getRoleName()).isEqualTo("Admin");
	}

	@Test
	public void testDeleteRole() {
		// Bir Role ekle
		Roles role = new Roles();
		role.setRoleName("Admin");
		Roles savedRole = rolesRepository.save(role);

		// Role'ü sil
		rolesRepository.deleteById(savedRole.getId());

		// Doğrula: Role artık mevcut olmamalı
		boolean exists = rolesRepository.existsById(savedRole.getId());
		assertThat(exists).isFalse();
	}
}
