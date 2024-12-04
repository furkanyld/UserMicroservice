package tr.edu.ogu.ceng.User.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.UserDTO;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/{username}")
	public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
		User user = userService.getByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found for name: " + username));

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("melih");

		return ResponseEntity.ok(userDTO);
	}
}
