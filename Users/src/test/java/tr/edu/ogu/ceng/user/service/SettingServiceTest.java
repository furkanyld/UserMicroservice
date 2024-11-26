package tr.edu.ogu.ceng.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tr.edu.ogu.ceng.User.UserApplication;
import tr.edu.ogu.ceng.User.entity.Setting;
import tr.edu.ogu.ceng.User.repository.SettingRepository;
import tr.edu.ogu.ceng.User.service.SettingService;

@SpringBootTest(classes = { UserApplication.class })
class SettingServiceTest {

	@MockBean
	private SettingRepository settingRepository;

	@Autowired
	private SettingService settingservice;

	@Test
	void testGetSetting() {
		Long id = 1L;
		Setting mockSetting = new Setting();
		mockSetting.setId(id);
		mockSetting.setValue("Test Value");

		when(settingRepository.getReferenceById(id)).thenReturn(mockSetting);

		Setting result = settingservice.getSetting(id);

		assertNotNull(result);
		assertEquals(id, result.getId());
		assertEquals("Test Value", result.getValue());
	}

	@Test
	void testCreateSetting() {
		Setting settingToCreate = new Setting();
		settingToCreate.setValue("New Setting");

		Setting mockSetting = new Setting();
		mockSetting.setId(1L);
		mockSetting.setValue("New Setting");

		when(settingRepository.save(any(Setting.class))).thenReturn(mockSetting);

		Setting createdSetting = settingservice.createSetting(settingToCreate);

		assertNotNull(createdSetting);
		assertEquals("New Setting", createdSetting.getValue());
	}
	@Test
	void testUpdateSetting() {
	    Long id = 1L;
	    Setting existingSetting = new Setting();
	    existingSetting.setId(id);
	    existingSetting.setValue("Old Value");

	    Setting updatedSetting = new Setting();
	    updatedSetting.setValue("Updated Value");

	    when(settingRepository.findById(id)).thenReturn(Optional.of(existingSetting));
	    when(settingRepository.save(any(Setting.class))).thenReturn(existingSetting);

	    Setting result = settingservice.updateSetting(id, updatedSetting);

	    assertNotNull(result);
	    assertEquals("Updated Value", result.getValue());
	}

	@Test
	void testDeleteSetting() {
	    Long id = 1L;

	    when(settingRepository.existsById(id)).thenReturn(true);

	    settingservice.deleteSetting(id);

	    verify(settingRepository, times(1)).deleteById(id);
	}
	
	@Test
	void testGetAllSettings() {
	    Setting setting1 = new Setting();
	    setting1.setValue("Setting 1");
	    Setting setting2 = new Setting();
	    setting2.setValue("Setting 2");

	    List<Setting> mockSettings = Arrays.asList(setting1, setting2);

	    when(settingRepository.findAll()).thenReturn(mockSettings);

	    List<Setting> result = settingservice.getAllSettings();

	    assertNotNull(result);
	    assertEquals(2, result.size());
	    assertEquals("Setting 1", result.get(0).getValue());
	    assertEquals("Setting 2", result.get(1).getValue());
	}
	
}
