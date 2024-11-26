package tr.edu.ogu.ceng.User.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.save(user); // Yeni kullanıcıyı kaydet
	}

	public User updateUser(User user) {
		return userRepository.save(user); // Var olan kullanıcıyı güncelle
	}

	public Optional<User> getByUsername(String username) {
        return userRepository.getByUsername(username);
            
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

	public User changeUserStatus(Long id, String newStatus) {
	    User user = userRepository.findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("User not found"));
	    user.setStatus(newStatus);
	    return userRepository.save(user);
	}

	public User updateUserPassword(Long id, String newPasswordHash) {
	    User user = userRepository.findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("User not found"));
	    user.setPasswordHash(newPasswordHash);
	    return userRepository.save(user);
	}



}