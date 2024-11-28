package tr.edu.ogu.ceng.User.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.RoleDTO;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;

	@GetMapping("/{id}")
	public ResponseEntity<RoleDTO> getRoleId(@PathVariable Long id) {
	    Role role = roleService.getRoleById(id);
	    if (role == null) {
	        throw new RuntimeException("Setting not found for id: " + id);
	    }
	    RoleDTO roleDTO =new RoleDTO();
	    roleDTO.setId(5);
	    return ResponseEntity.ok(roleDTO);
	}
}
