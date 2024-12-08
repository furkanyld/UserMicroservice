package tr.edu.ogu.ceng.User.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
import tr.edu.ogu.ceng.User.dto.UserDTO;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final ModelMapper modelMapper;

	@GetMapping("/getUserByUsername{username}")
	public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
	    User user = userService.getByUsername(username)
	            .orElseThrow(() -> new IllegalArgumentException("User not found"));
	    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return ResponseEntity.ok(userDTO);
	}

    // Create a new user
    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User createdUser = userService.createUser(user);
        UserDTO createdUserDTO = modelMapper.map(createdUser, UserDTO.class);
        return ResponseEntity.ok(createdUserDTO);
    }

    // Get user by ID
    @GetMapping("/getUserById{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    	Optional<User> userOpt = userService.getUserById(id); // Optional<User> döndürüyor

        if (userOpt.isEmpty()) { // Kullanıcı bulunamadıysa
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = userOpt.get(); // Kullanıcıyı al
        UserDTO userDTO = modelMapper.map(user, UserDTO.class); // UserDTO'ya dönüştür
        return ResponseEntity.ok(userDTO);
    }

    // Get all users
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    // Update an existing user
    @PutMapping("/updateUser{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        // ID ile kullanıcıyı alıyoruz
        Optional<User> existingUserOpt = userService.getUserById(id);
        
        return existingUserOpt
                .map(existingUser -> {
                    // DTO'dan User nesnesine dönüştürme
                    existingUser.setUsername(userDTO.getUsername());
                    existingUser.setEmail(userDTO.getEmail());
                    existingUser.setPasswordHash(userDTO.getPasswordHash());
                    existingUser.setUpdatedAt(LocalDateTime.now());
                    existingUser.setUpdatedBy("system");

                    // Kullanıcıyı güncelle
                    User updatedUser = userService.updateUser(id, existingUser);

                    // Güncellenmiş kullanıcıyı DTO'ya dönüştür
                    UserDTO updatedUserDTO = modelMapper.map(updatedUser, UserDTO.class);

                    return ResponseEntity.ok(updatedUserDTO);
                })
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
    
    // Delete a user
    @DeleteMapping("/deleteUser{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
