package tr.edu.ogu.ceng.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import tr.edu.ogu.ceng.Users.entity.UserRoles;
import tr.edu.ogu.ceng.Users.repository.UserRolesRepository;
import tr.edu.ogu.ceng.Users.service.UserRolesService;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class UserRolesServiceTest {

	@Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
	
    @Mock
    private UserRolesRepository userRolesRepository;

    @InjectMocks
    private UserRolesService userRolesService;

    private UserRoles userRole;

    @BeforeEach
    void setUp() {
        userRole = new UserRoles();
        userRole.setId(1L);
        
    }

    @Test
    void testCreateUserRoles() {
        when(userRolesRepository.save(userRole)).thenReturn(userRole);

        UserRoles createdRole = userRolesService.createUserRoles(userRole);

        assertNotNull(createdRole);
        assertEquals("Admin", createdRole.getRole());
        verify(userRolesRepository, times(1)).save(userRole);
    }

    @Test
    void testUpdateUserRoles() {
        when(userRolesRepository.save(userRole)).thenReturn(userRole);

        UserRoles updatedRole = userRolesService.updateUserRoles(userRole);

        assertNotNull(updatedRole);
        assertEquals("Admin", updatedRole.getRole());
        verify(userRolesRepository, times(1)).save(userRole);
    }

    @Test
    void testGetAllUserRoles() {
        List<UserRoles> rolesList = new ArrayList<>();
        rolesList.add(userRole);

        when(userRolesRepository.findAll()).thenReturn(rolesList);

        List<UserRoles> allRoles = userRolesService.getAllUserRoles();

        assertNotNull(allRoles);
        assertEquals(1, allRoles.size());
        assertEquals("Admin", allRoles.get(0).getRole());
        verify(userRolesRepository, times(1)).findAll();
    }

    @Test
    void testDeleteUserRole() {
        doNothing().when(userRolesRepository).deleteById(anyLong());

        // Silme işlemi için ID'yi kullanarak metodu çağırın
        userRolesService.deleteUserRole(1L);

        // deleteById metodunun çağrıldığını doğrula
        verify(userRolesRepository, times(1)).deleteById(1L);
    }
}
