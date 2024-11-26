package tr.edu.ogu.ceng.User.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.entity.Setting;
import tr.edu.ogu.ceng.User.repository.SettingRepository;

@RequiredArgsConstructor
@Service
public class SettingService {

	private final SettingRepository settingrepository;

	public Setting getSetting(Long id) {

		return settingrepository.getReferenceById(id);
	}

	public Setting createSetting(Setting setting) {
		return settingrepository.save(setting);
	}

	@Transactional
	public Setting updateSetting(Long id, Setting newSetting) {
		Setting setting = settingrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Setting not found"));

		setting.setValue(newSetting.getValue());

		return settingrepository.save(setting);
	}

	public void deleteSetting(Long id) {
		if (!settingrepository.existsById(id)) {
			throw new IllegalArgumentException("Setting not found");
		}
		settingrepository.deleteById(id);
	}

	public List<Setting> getAllSettings() {
		return settingrepository.findAll();
	}
	
	
}
