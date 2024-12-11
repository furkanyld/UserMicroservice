package tr.edu.ogu.ceng.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import common.Parent;
import tr.edu.ogu.ceng.User.dto.UserRoleDTO;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.entity.UserRole;
import tr.edu.ogu.ceng.User.repository.RoleRepository;
import tr.edu.ogu.ceng.User.repository.UserRepository;
import tr.edu.ogu.ceng.User.repository.UserRoleRepository;
import tr.edu.ogu.ceng.User.service.RoleService;
import tr.edu.ogu.ceng.User.service.UserRoleService;
import tr.edu.ogu.ceng.User.service.UserService;

@SpringBootTest
class UserRoleServiceTest extends Parent {

	@MockBean
	private UserRoleRepository userRolesRepository;

	@Autowired
	private UserRoleService userRolesService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Test
	void testCreateUserRoles() {
	    // Test verileri oluştur
	    UserRoleDTO userRoleDTO = new UserRoleDTO();
	    userRoleDTO.setUserId(1L);
	    userRoleDTO.setRoleId(1L);

	    // User ve Role nesnelerini oluştur
	    User user = new User();
	    user.setId(1L);

	    Role role = new Role();
	    role.setId(1L);

	    UserRole userRole = new UserRole();
	    userRole.setUser(user);
	    userRole.setRole(role);
	    userRole.setAssignedAt(LocalDateTime.now());

	    // UserRole DTO'dan UserRole nesnesine dönüşüm
	    when(userRepository.findById(userRoleDTO.getUserId())).thenReturn(Optional.of(user));
	    when(roleRepository.findById(userRoleDTO.getRoleId())).thenReturn(Optional.of(role));
	    when(userRolesRepository.save(userRole)).thenReturn(userRole);

	    // Service metodunu çağır
	    UserRole createdUserRole = modelMapper.map(userRolesService.createUserRole(userRoleDTO), UserRole.class);

	    // Asserts
	    assertNotNull(createdUserRole);
	    assertEquals(user, createdUserRole.getUser());
	    assertEquals(role, createdUserRole.getRole());
	    verify(userRepository, times(1)).findById(userRoleDTO.getUserId());
	    verify(roleRepository, times(1)).findById(userRoleDTO.getRoleId());
	    verify(userRolesRepository, times(1)).save(userRole);
	}


	@Test
	void testUpdateUserRoles() {
	    // Test verisi olarak UserRoleDTO oluştur
	    UserRoleDTO userRoleDTO = new UserRoleDTO();
	    userRoleDTO.setId(1L);
	    userRoleDTO.setUserId(1L);  // User'ın ID'si
	    userRoleDTO.setRoleId(2L);  // Role'un ID'si

	    // User ve Role nesneleri oluştur
	    User user = new User();
	    user.setId(1L);

	    Role role = new Role();
	    role.setId(2L);

	    UserRole userRole = new UserRole();
	    userRole.setId(1L);
	    userRole.setUser(user);
	    userRole.setRole(role);
	    userRole.setAssignedAt(LocalDateTime.now());

	    // User ve Role nesnelerinin bulunması için mocklama
	    when(userRepository.findById(userRoleDTO.getUserId())).thenReturn(Optional.of(user));
	    when(roleRepository.findById(userRoleDTO.getRoleId())).thenReturn(Optional.of(role));
	    when(userRolesRepository.save(userRole)).thenReturn(userRole);

	    // Service metodunu çağır
	    UserRole updatedUserRole = modelMapper.map(userRolesService.updateUserRole(userRole.getId(), userRoleDTO, "system"), UserRole.class);

	    // Asserts
	    assertNotNull(updatedUserRole);
	    assertEquals(role, updatedUserRole.getRole());
	    assertEquals(user, updatedUserRole.getUser());
	    verify(userRepository, times(1)).findById(userRoleDTO.getUserId());
	    verify(roleRepository, times(1)).findById(userRoleDTO.getRoleId());
	    verify(userRolesRepository, times(1)).save(userRole);
	}


	@Test
	void testGetAllUserRoles() {
		User user1 = new User();
		user1.setId(1L);

		Role role1 = new Role();
		role1.setId(1L);

		UserRole userRole1 = new UserRole();
		userRole1.setId(1L);
		userRole1.setUser(user1);
		userRole1.setRole(role1);
		userRole1.setAssignedAt(LocalDateTime.now());

		User user2 = new User();
		user2.setId(2L);

		Role role2 = new Role();
		role2.setId(2L);

		UserRole userRole2 = new UserRole();
		userRole2.setId(2L);
		userRole2.setUser(user2);
		userRole2.setRole(role2);
		userRole2.setAssignedAt(LocalDateTime.now());

		when(userRolesRepository.findAll()).thenReturn(Arrays.asList(userRole1, userRole2));

		List<UserRoleDTO> userRolesList = userRolesService.getAllUserRoles();

		assertNotNull(userRolesList);
		assertEquals(2, userRolesList.size());
		verify(userRolesRepository, times(1)).findAll();
	}

	@Test
	void testDeleteUserRole() {
		Long userRoleId = 1L;

		doNothing().when(userRolesRepository).deleteById(userRoleId);

		userRolesService.deleteUserRole(userRoleId);

		verify(userRolesRepository, times(1)).deleteById(userRoleId);
	}

}