package tr.edu.ogu.ceng.Users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.Users.service.SettingService;

@RestController
@RequestMapping("/API/V1/Setting")
@RequiredArgsConstructor
public class SettingController {

	private final SettingService settingservice;

	@GetMapping("/{id}")
	public Object getSetting(@PathVariable Long id) {
		return settingservice.getSetting(id);

	}

}
