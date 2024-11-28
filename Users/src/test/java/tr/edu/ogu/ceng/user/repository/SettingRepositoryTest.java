package tr.edu.ogu.ceng.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import common.Parent;
import tr.edu.ogu.ceng.User.entity.Setting;
import tr.edu.ogu.ceng.User.repository.SettingRepository;

@SpringBootTest
public class SettingRepositoryTest extends Parent {

    @Autowired
    private SettingRepository settingRepository;

    @Test
    public void testSaveSetting() {
        // Arrange
        Setting setting = new Setting();
        setting.setKey("theme");
        setting.setValue("dark");
        setting.setCreatedBy("admin");
        setting.setCreatedAt(LocalDateTime.now());
		setting.setUpdatedAt(LocalDateTime.now());


        // Act
        Setting savedSetting = settingRepository.save(setting);

        // Assert
        assertThat(savedSetting).isNotNull();
        assertThat(savedSetting.getId()).isNotNull();
        assertThat(savedSetting.getKey()).isEqualTo("theme");
        assertThat(savedSetting.getValue()).isEqualTo("dark");
        assertThat(savedSetting.getCreatedBy()).isEqualTo("admin");
        assertThat(savedSetting.getCreatedAt()).isNotNull();
    }

    @Test
    public void testUpdateSetting() {
        // Arrange
        Setting setting = new Setting();
        setting.setKey("language");
        setting.setValue("en");
        setting.setCreatedBy("admin");
        setting.setCreatedAt(LocalDateTime.now());
		setting.setUpdatedAt(LocalDateTime.now());
        Setting savedSetting = settingRepository.save(setting);

        // Act
        savedSetting.setValue("fr");
        savedSetting.setUpdatedBy("editor");
        savedSetting.setUpdatedAt(LocalDateTime.now());
        Setting updatedSetting = settingRepository.save(savedSetting);

        // Assert
        assertThat(updatedSetting).isNotNull();
        assertThat(updatedSetting.getValue()).isEqualTo("fr");
        assertThat(updatedSetting.getUpdatedBy()).isEqualTo("editor");
        assertThat(updatedSetting.getUpdatedAt()).isNotNull();
    }

    @Test
    public void testFindSettingById() {
        // Arrange
        Setting setting = new Setting();
        setting.setKey("timezone");
        setting.setValue("UTC");
        setting.setCreatedBy("admin");
        setting.setCreatedAt(LocalDateTime.now());
		setting.setUpdatedAt(LocalDateTime.now());

        Setting savedSetting = settingRepository.save(setting);

        // Act
        Optional<Setting> foundSetting = settingRepository.findById(savedSetting.getId());

        // Assert
        assertThat(foundSetting).isPresent();
        assertThat(foundSetting.get().getKey()).isEqualTo("timezone");
        assertThat(foundSetting.get().getValue()).isEqualTo("UTC");
        assertThat(foundSetting.get().getCreatedBy()).isEqualTo("admin");
    }

    @Test
    public void testDeleteSetting() {
        // Arrange
        Setting setting = new Setting();
        setting.setKey("featureFlag");
        setting.setValue("enabled");
        setting.setCreatedBy("admin");
        setting.setCreatedAt(LocalDateTime.now());
		setting.setUpdatedAt(LocalDateTime.now());

        Setting savedSetting = settingRepository.save(setting);

        // Act
        settingRepository.deleteById(savedSetting.getId());
        Optional<Setting> deletedSetting = settingRepository.findById(savedSetting.getId());

        // Assert
        assertThat(deletedSetting).isNotPresent();
    }
}
