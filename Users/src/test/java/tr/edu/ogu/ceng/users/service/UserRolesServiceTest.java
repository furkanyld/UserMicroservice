package tr.edu.ogu.ceng.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tr.edu.ogu.ceng.Users.entity.Roles;
import tr.edu.ogu.ceng.Users.entity.UserRoles;
import tr.edu.ogu.ceng.Users.entity.Users;
import tr.edu.ogu.ceng.Users.repository.UserRolesRepository;
import tr.edu.ogu.ceng.Users.service.UserRolesService;
@SpringBootTest
class UserRolesServiceTest {

    @MockBean
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserRolesService userRolesService;

  

    @Test
    void testCreateUserRoles() {
        Users user = new Users();
        user.setId(1L);

        Roles role = new Roles();
        role.setId(1L);

        UserRoles userRole = new UserRoles();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setAssignedAt(LocalDateTime.now());

        when(userRolesRepository.save(userRole)).thenReturn(userRole);

        UserRoles createdUserRole = userRolesService.createUserRoles(userRole);

        assertNotNull(createdUserRole);
        assertEquals(user, createdUserRole.getUser());
        assertEquals(role, createdUserRole.getRole());
        verify(userRolesRepository, times(1)).save(userRole);
    }

    @Test
    void testUpdateUserRoles() {
        Users user = new Users();
        user.setId(1L);

        Roles role = new Roles();
        role.setId(2L);

        UserRoles userRole = new UserRoles();
        userRole.setId(1L);
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setAssignedAt(LocalDateTime.now());

        when(userRolesRepository.save(userRole)).thenReturn(userRole);

        UserRoles updatedUserRole = userRolesService.updateUserRoles(userRole);

        assertNotNull(updatedUserRole);
        assertEquals(role, updatedUserRole.getRole());
        verify(userRolesRepository, times(1)).save(userRole);
    }

    @Test
    void testGetAllUserRoles() {
        Users user1 = new Users();
        user1.setId(1L);

        Roles role1 = new Roles();
        role1.setId(1L);

        UserRoles userRole1 = new UserRoles();
        userRole1.setId(1L);
        userRole1.setUser(user1);
        userRole1.setRole(role1);
        userRole1.setAssignedAt(LocalDateTime.now());

        Users user2 = new Users();
        user2.setId(2L);

        Roles role2 = new Roles();
        role2.setId(2L);

        UserRoles userRole2 = new UserRoles();
        userRole2.setId(2L);
        userRole2.setUser(user2);
        userRole2.setRole(role2);
        userRole2.setAssignedAt(LocalDateTime.now());

        when(userRolesRepository.findAll()).thenReturn(Arrays.asList(userRole1, userRole2));

        List<UserRoles> userRolesList = userRolesService.getAllUserRoles();

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

    @Test
    void testUpdateNonExistentUserRole() {
        UserRoles userRole = new UserRoles();
        userRole.setId(99L);

        when(userRolesRepository.save(userRole)).thenThrow(new RuntimeException("User role not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userRolesService.updateUserRoles(userRole);
        });

        assertEquals("User role not found", exception.getMessage());
        verify(userRolesRepository, times(1)).save(userRole);

}
}