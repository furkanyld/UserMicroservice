package tr.edu.ogu.ceng.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import tr.edu.ogu.ceng.Users.entity.Roles;
import tr.edu.ogu.ceng.Users.repository.RolesRepository;
import tr.edu.ogu.ceng.Users.service.RolesService;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class RolesServiceTest {
	@Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));

    @Mock
    private RolesRepository rolesRepository;

    @InjectMocks
    private RolesService rolesService;

    @Test
    public void testCreateRole() {
        Roles role = new Roles();
        role.setRoleName("Admin");

        when(rolesRepository.save(any(Roles.class))).thenReturn(role);

        Roles savedRole = rolesService.createRole(role);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getRoleName()).isEqualTo("Admin");
        verify(rolesRepository, times(1)).save(role);
    }

    @Test
    public void testUpdateRole() {
        Roles role = new Roles();
        role.setId(1L);
        role.setRoleName("User");

        when(rolesRepository.save(any(Roles.class))).thenReturn(role);

        Roles updatedRole = rolesService.updateRole(role);

        assertThat(updatedRole).isNotNull();
        assertThat(updatedRole.getRoleName()).isEqualTo("User");
        verify(rolesRepository, times(1)).save(role);
    }

    @Test
    void testGetAllRoles() {
        // Mock verisi
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(new Roles()); // 1. rol
        rolesList.add(new Roles()); // 2. rol

        // Mock davranışı: rolesRepository.findAll() çağrıldığında rolesList döndür
        when(rolesRepository.findAll()).thenReturn(rolesList);

        // Metodu çağırın
        List<Roles> allRoles = rolesService.getAllRoles();

        // Sonucun boyutunu kontrol edin
        assertEquals(2, allRoles.size()); // Beklenen boyut
        verify(rolesRepository, times(1)).findAll(); // Methodun bir kez çağrıldığını doğrula
    }


    @Test
    public void testGetRoleById() {
        Roles role = new Roles();
        role.setId(1L);
        role.setRoleName("Admin");

        when(rolesRepository.findById(anyLong())).thenReturn(Optional.of(role));

        Roles foundRole = rolesService.getRoleById(1L);

        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getRoleName()).isEqualTo("Admin");
        verify(rolesRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteRole() {
        Long roleId = 1L;

        doNothing().when(rolesRepository).deleteById(roleId);

        rolesService.deleteRole(roleId);

        verify(rolesRepository, times(1)).deleteById(roleId);
    }
}
