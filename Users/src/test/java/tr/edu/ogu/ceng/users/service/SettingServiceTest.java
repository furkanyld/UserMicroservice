package tr.edu.ogu.ceng.users.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tr.edu.ogu.ceng.Users.entity.Setting;
import tr.edu.ogu.ceng.Users.repository.SettingRepository;
import tr.edu.ogu.ceng.Users.service.SettingService;

@SpringBootTest
class SettingServiceTest {

	@MockBean
	private SettingRepository settingRepository;

	@Autowired
	private SettingService settingService;

	@Test
	void testGetSettingById() {
		// Arrange
		Long id = 1L;
		Setting mockSetting = new Setting();
		mockSetting.setId(id);
		mockSetting.setKey("theme");
		mockSetting.setValue("dark");

		when(settingRepository.getReferenceById(id)).thenReturn(mockSetting);

		// Act
		Object result = settingService.getSetting(id);

		// Assert
		assertThat(result).isNotNull();
		assertThat(((Setting) result).getKey()).isEqualTo("theme");
		assertThat(((Setting) result).getValue()).isEqualTo("dark");
		verify(settingRepository, times(1)).getReferenceById(id);
	}
}
