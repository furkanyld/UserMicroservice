package tr.edu.ogu.ceng.users.service;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tr.edu.ogu.ceng.Users.entity.Roles;
import tr.edu.ogu.ceng.Users.repository.RolesRepository;
import tr.edu.ogu.ceng.Users.service.RolesService;

@SpringBootTest
class RolesServiceTest {

    @MockBean
    private RolesRepository rolesRepository;

    @Autowired
    private RolesService rolesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRole() {
        Roles role = new Roles();
        role.setId(1L);
        role.setRoleName("Admin");

        when(rolesRepository.save(role)).thenReturn(role);

        Roles createdRole = rolesService.createRole(role);

        assertNotNull(createdRole);
        assertEquals("Admin", createdRole.getRoleName());
        verify(rolesRepository, times(1)).save(role);
    }

    @Test
    void testUpdateRole() {
        Roles role = new Roles();
        role.setId(1L);
        role.setRoleName("User");

        when(rolesRepository.save(role)).thenReturn(role);

        Roles updatedRole = rolesService.updateRole(role);

        assertNotNull(updatedRole);
        assertEquals("User", updatedRole.getRoleName());
        verify(rolesRepository, times(1)).save(role);
    }

    @Test
    void testGetAllRoles() {
        Roles role1 = new Roles();
        role1.setId(1L);
        role1.setRoleName("Admin");

        Roles role2 = new Roles();
        role2.setId(2L);
        role2.setRoleName("User");

        when(rolesRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<Roles> roles = rolesService.getAllRoles();

        assertNotNull(roles); // Verify the list is not null
        assertEquals(2, roles.size()); // Verify the size of the list
        verify(rolesRepository, times(1)).findAll(); // Ensure repository method is called once
    }

    @Test
    void testGetRoleById() {
        Roles role = new Roles();
        role.setId(1L);
        role.setRoleName("Admin");

        when(rolesRepository.findById(1L)).thenReturn(Optional.of(role));

        Roles foundRole = rolesService.getRoleById(1L);

        assertNotNull(foundRole); // Verify the object is not null
        assertEquals("Admin", foundRole.getRoleName()); // Verify the role name matches
        verify(rolesRepository, times(1)).findById(1L); // Ensure repository method is called once
    }

    @Test
    void testGetRoleById_NotFound() {
        when(rolesRepository.findById(1L)).thenReturn(Optional.empty());

        Roles foundRole = rolesService.getRoleById(1L);

        assertNull(foundRole); // Verify the result is null
        verify(rolesRepository, times(1)).findById(1L); // Ensure repository method is called once
    }

    @Test
    void testDeleteRole() {
        Long roleId = 1L;

        doNothing().when(rolesRepository).deleteById(roleId);

        rolesService.deleteRole(roleId);

        verify(rolesRepository, times(1)).deleteById(roleId); // Ensure repository method is called once
    }
}
