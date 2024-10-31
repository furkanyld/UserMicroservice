package tr.edu.ogu.ceng.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

import tr.edu.ogu.ceng.Users.entity.Setting;
import tr.edu.ogu.ceng.Users.repository.SettingRepository;
import tr.edu.ogu.ceng.Users.service.SettingService;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class SettingServiceTest {
	@Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));

    @Mock
    private SettingRepository settingRepository;

    @InjectMocks
    private SettingService settingService;

    @Test
    public void testGetSetting() {
        // Örnek bir Setting nesnesi oluştur
        Setting setting = new Setting();
        setting.setId(1L);
        setting.setValue("Test Setting");

        // Mock davranışı tanımla
        when(settingRepository.getReferenceById(anyLong())).thenReturn(setting);

        // Metodu çağır ve sonucu doğrula
        Object result = settingService.getSetting(1L);

        assertThat(result).isNotNull();
        assertThat(((Setting) result).getValue()).isEqualTo("Test Setting");
        verify(settingRepository, times(1)).getReferenceById(1L);
    }
}
