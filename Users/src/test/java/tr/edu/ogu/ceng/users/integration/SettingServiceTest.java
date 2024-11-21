package tr.edu.ogu.ceng.users.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import tr.edu.ogu.ceng.Users.entity.Setting;
import tr.edu.ogu.ceng.Users.repository.SettingRepository;

@Testcontainers
@SpringBootTest
public class SettingServiceTest {

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
    private SettingRepository settingRepository;

    @Test
    public void testCreateSetting() {
        // Yeni bir Setting oluştur ve kaydet
        Setting setting = new Setting();
        setting.setKey("testKey");
        setting.setValue("Test Setting");

        Setting savedSetting = settingRepository.save(setting);

        // Doğrulamalar
        assertThat(savedSetting).isNotNull();
        assertThat(savedSetting.getId()).isNotNull(); // Otomatik ID atanmış olmalı
        assertThat(savedSetting.getKey()).isEqualTo("testKey");
        assertThat(savedSetting.getValue()).isEqualTo("Test Setting");
    }

    @Test
    public void testUpdateSetting() {
        // Önce bir Setting kaydet
        Setting setting = new Setting();
        setting.setKey("testKey");
        setting.setValue("Old Value");
        Setting savedSetting = settingRepository.save(setting);

        // Setting'i güncelle
        savedSetting.setValue("Updated Value");
        Setting updatedSetting = settingRepository.save(savedSetting);

        // Doğrulamalar
        assertThat(updatedSetting).isNotNull();
        assertThat(updatedSetting.getValue()).isEqualTo("Updated Value");
    }

    @Test
    public void testGetAllSettings() {
        // Bazı Settingler ekle
        Setting setting1 = new Setting();
        setting1.setKey("key1");
        setting1.setValue("Value1");

        Setting setting2 = new Setting();
        setting2.setKey("key2");
        setting2.setValue("Value2");

        settingRepository.save(setting1);
        settingRepository.save(setting2);

        // Tüm Settings al ve doğrula
        Iterable<Setting> settingsList = settingRepository.findAll();
        assertThat(settingsList).isNotEmpty();
    }

    @Test
    public void testGetSettingById() {
        // Bir Setting ekle ve ID'yi al
        Setting setting = new Setting();
        setting.setKey("testKey");
        setting.setValue("Test Setting");
        Setting savedSetting = settingRepository.save(setting);

        // ID ile Setting al ve doğrula
        Setting foundSetting = settingRepository.findById(savedSetting.getId()).orElse(null);
        assertThat(foundSetting).isNotNull();
        assertThat(foundSetting.getValue()).isEqualTo("Test Setting");
    }

    @Test
    public void testDeleteSetting() {
        // Bir Setting ekle
        Setting setting = new Setting();
        setting.setKey("testKey");
        setting.setValue("Test Setting");
        Setting savedSetting = settingRepository.save(setting);

        // Setting'i sil
        settingRepository.deleteById(savedSetting.getId());

        // Doğrula: Setting artık mevcut olmamalı
        boolean exists = settingRepository.existsById(savedSetting.getId());
        assertThat(exists).isFalse();
    }
}
