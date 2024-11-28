package tr.edu.ogu.ceng.User.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.SettingDTO;
import tr.edu.ogu.ceng.User.entity.Setting;
import tr.edu.ogu.ceng.User.service.SettingService;

@RestController
@RequestMapping("/api/v1/setting")
@RequiredArgsConstructor
public class SettingController {

	private final SettingService settingservice;

	@GetMapping("/{id}")
	public ResponseEntity<SettingDTO> getSetting(@PathVariable Long id) {
	    Setting setting = settingservice.getSetting(id);
	    if (setting == null) {
	        throw new RuntimeException("Setting not found for id: " + id);
	    }
	    SettingDTO settingDTO =new SettingDTO();
	    settingDTO.setId(5);
	    return ResponseEntity.ok(settingDTO);
	}


}
