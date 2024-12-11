package tr.edu.ogu.ceng.User.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.UserDTO;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	private final RestClient restClient;
	private final ModelMapper modelMapper;

	public User createUser(User user) {
		// farklı bir mikroservise istek atma		
		/*restClient.get().uri("http://192.168.137.149:8001/users/1")
				.accept(org.springframework.http.MediaType.APPLICATION_JSON)
				.retrieve()
				  	.body(User.class);
				  	*/
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setStatus("Active");
		return userRepository.save(user); 
	}

	public User updateUser(Long id, User updatedUser) {
	    Optional<User> existingUserOpt = userRepository.findById(id);
	    
	    if (existingUserOpt.isPresent()) {
	        User existingUser = existingUserOpt.get(); 
	        existingUser.setUsername(updatedUser.getUsername());
	        existingUser.setEmail(updatedUser.getEmail());
	        existingUser.setPasswordHash(updatedUser.getPasswordHash());
	        existingUser.setUpdatedAt(LocalDateTime.now());
	        existingUser.setUpdatedBy("system"); 

	        return userRepository.save(existingUser);
	    } else {
	        throw new RuntimeException("User not found with ID: " + id);
	    }
	}

	public Optional<User> getByUsername(String username) {
		return userRepository.getByUsername(username);

	}
	
	public Optional<User> getUserById(Long id){
		return userRepository.findById(id);
	}

	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public UserDTO updateUserStatus(Long id, String newStatus) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		user.setStatus(newStatus);
		userRepository.save(user);
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	public UserDTO updateUserPassword(Long id, String newPasswordHash) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		user.setPasswordHash(newPasswordHash);
		userRepository.save(user);
		UserDTO updatedUserDTO =  modelMapper.map(user, UserDTO.class);
		return updatedUserDTO;
	}

	public UserDTO updateUserEmail(Long id, String newEmail) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		user.setEmail(newEmail);
		userRepository.save(user);
		UserDTO updatedUserDTO =  modelMapper.map(user, UserDTO.class);
		return updatedUserDTO;
	}
}