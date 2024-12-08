package tr.edu.ogu.ceng.User.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.RoleDTO;
import tr.edu.ogu.ceng.User.dto.UserDTO;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;
	private final ModelMapper modelMapper;

	@GetMapping("/getRoleById{id}")
	public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
		RoleDTO roleDTO = roleService.getRoleById(id);
    	return ResponseEntity.ok(roleDTO);
	}
	
	@PostMapping("/createRole")
	public ResponseEntity<RoleDTO> createRole(RoleDTO roleDTO){
		RoleDTO createdRoleDTO = roleService.createRole(roleDTO);
		return ResponseEntity.ok(createdRoleDTO);
	}
	
	@GetMapping("/getAllRoles")
	public ResponseEntity<List<RoleDTO>> getAllRoles() {
		List<RoleDTO> roles = roleService.getAllRoles();
		return ResponseEntity.ok(roles);
	}
	
	// Rol güncelleme
	@PutMapping("/{id}")
	public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO updatedRoleDTO) {
		RoleDTO updatedRole = roleService.updateRole(id, updatedRoleDTO);
		return ResponseEntity.ok(updatedRole);
	}

	// Rol silme
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
		roleService.hardDeleteRole(id);
		return ResponseEntity.noContent().build();
	}
}
