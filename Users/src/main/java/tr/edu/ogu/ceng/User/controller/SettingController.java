package tr.edu.ogu.ceng.User.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.service.SettingService;

@RestController
@RequestMapping("/API/V1/Setting")
@RequiredArgsConstructor
public class SettingController {

	private final SettingService settingservice;

	@GetMapping("/{id}")
	public Object getSetting(@PathVariable Long id) {
	    Object setting = settingservice.getSetting(id);
	    if (setting == null) {
	        throw new RuntimeException("Setting not found for id: " + id);
	    }
	    return setting;
	}


}
