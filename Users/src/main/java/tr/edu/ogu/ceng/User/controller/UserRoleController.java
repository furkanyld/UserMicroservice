package tr.edu.ogu.ceng.User.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.UserRoleDTO;
import tr.edu.ogu.ceng.User.service.UserRoleService;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class UserRoleController {
	private final UserRoleService userRoleService;
	
	@PostMapping("/createUserRole")
	public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRoleDTO) {
		try {
		    UserRoleDTO createdUserRoleDTO = userRoleService.createUserRole(userRoleDTO);
		    return ResponseEntity.ok(createdUserRoleDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@PutMapping("/updateUserRole{id}")
    public ResponseEntity<UserRoleDTO> updateUserRole(
            @PathVariable Long id,
            @RequestBody UserRoleDTO userRoleDTO,
            @RequestHeader("Updated-By") String updatedBy) {

        try {
        	UserRoleDTO updatedUserRoleDTO = userRoleService.updateUserRole(id, userRoleDTO, updatedBy);
            return ResponseEntity.ok(updatedUserRoleDTO);
        }catch(NoSuchElementException e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
        			.body(null);
        }catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
	
	@GetMapping("/getAllUserRoles")
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
		List<UserRoleDTO> userRoleDTOList = userRoleService.getAllUserRoles();
	    return ResponseEntity.ok(userRoleDTOList);
    }

	@DeleteMapping("/deleteUserRole{id}")
	public ResponseEntity<String> deleteUserRole(@PathVariable Long id) {
	    try {
	        userRoleService.deleteUserRole(id);
	        return ResponseEntity.ok("UserRole deleted successfully.");
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    }
	}
}
