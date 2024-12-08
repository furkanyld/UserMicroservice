package tr.edu.ogu.ceng.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import common.Parent;
import tr.edu.ogu.ceng.User.dto.RoleDTO;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.repository.RoleRepository;
import tr.edu.ogu.ceng.User.service.RoleService;

@SpringBootTest
class RoleServiceTest extends Parent{

	@MockBean
	private RoleRepository rolesRepository;

	@Autowired
	private RoleService rolesService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Test
	void testCreateRole() {
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("Admin");

		when(rolesRepository.save(role)).thenReturn(role);

		RoleDTO createdRoleDTO = rolesService.createRole(modelMapper.map(role, RoleDTO.class));

		assertNotNull(createdRoleDTO);
		assertEquals("Admin", createdRoleDTO.getRoleName());
		verify(rolesRepository, times(1)).save(role);
	}

	@Test
	void testUpdateRole() {
		Role existingRole = new Role();
		existingRole.setId(1L);
		existingRole.setRoleName("User");
		existingRole.setDescription("User role");

		Role updatedRole = new Role();
		updatedRole.setRoleName("Moderator");
		updatedRole.setDescription("Moderator role");

		when(rolesRepository.findById(1L)).thenReturn(Optional.of(existingRole));
		when(rolesRepository.save(existingRole)).thenReturn(updatedRole);

		RoleDTO resultDTO = rolesService.updateRole(1L, modelMapper.map(updatedRole, RoleDTO.class));

		assertNotNull(resultDTO);
		assertEquals("Moderator", resultDTO.getRoleName());
		assertEquals("Moderator role", resultDTO.getDescription());
		verify(rolesRepository, times(1)).save(existingRole);
	}

	@Test
	void testGetAllRoles() {
		Role role1 = new Role();
		role1.setId(1L);
		role1.setRoleName("Admin");

		Role role2 = new Role();
		role2.setId(2L);
		role2.setRoleName("User");

		when(rolesRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

		List<RoleDTO> rolesDTO = rolesService.getAllRoles();

		assertNotNull(rolesDTO); // Verify the list is not null
		assertEquals(2, rolesDTO.size()); // Verify the size of the list
		verify(rolesRepository, times(1)).findAll(); // Ensure repository method is called once
	}

	@Test
	void testFindByRoleName() {
	    Role role = new Role();
	    role.setId(1L);
	    role.setRoleName("Admin");

	    // Mock davranışını ayarla
	    when(rolesRepository.findByRoleName("Admin")).thenReturn(Optional.of(role));

	    // Servis metodunu çağır
	    RoleDTO foundRoleDTO = rolesService.findByRoleName("Admin");

	    // Doğrulamalar
	    assertNotNull(foundRoleDTO);
	    assertEquals("Admin", foundRoleDTO.getRoleName());
	    verify(rolesRepository, times(1)).findByRoleName("Admin"); // Repository'nin çağrıldığını doğrula
	}

	@Test
	void testGetRoleById_NotFound() {
		when(rolesRepository.findById(1L)).thenReturn(Optional.empty());

		RoleDTO foundRoleDTO = rolesService.getRoleById(1L);

		assertNull(foundRoleDTO); // Verify the result is null
		verify(rolesRepository, times(1)).findById(1L); // Ensure repository method is called once
	}

	@Test
	void testHardDeleteRole() {
		Long roleId = 1L;

		doNothing().when(rolesRepository).deleteById(roleId);

		rolesService.hardDeleteRole(roleId);

		verify(rolesRepository, times(1)).deleteById(roleId); // Ensure repository method is called once
	}

	@Test
	void testSoftDeleteRole() {
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("Admin");

		when(rolesRepository.findById(1L)).thenReturn(Optional.of(role));
		when(rolesRepository.save(role)).thenReturn(role);

		rolesService.softDeleteRole(1L);

		assertNotNull(role.getDeletedAt());
		assertEquals("system", role.getDeletedBy());
		verify(rolesRepository, times(1)).save(role);
	}
}
