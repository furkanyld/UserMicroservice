package tr.edu.ogu.ceng.User.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.repository.SettingRepository;
@RequiredArgsConstructor
@Service
public class SettingService {
 
	private final SettingRepository settingrepository;

	

	public  Object getSetting(Long id) {
		
		return settingrepository.getReferenceById(id);
	}

}
