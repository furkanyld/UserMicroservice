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

	private final UserRepository userrepository;

	public User createUser(User user) {
		return userrepository.save(user); // Yeni kullanıcıyı kaydet
	}

	public User updateUser(User user) {
		return userrepository.save(user); // Var olan kullanıcıyı güncelle
	}

	public Optional<User> getByUsername(String username) {
        return userrepository.getByUsername(username);
            
    }
	public Optional<User> getByEmail(String email) {
	    return userrepository.findByEmail(email);
	}

	public void deleteUserById(Long id) {
	    userrepository.deleteById(id);
	}
	
	public List<User> getAllUsers() {
	    return userrepository.findAll();
	}

	public User changeUserStatus(Long id, String newStatus) {
	    User user = userrepository.findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("User not found"));
	    user.setStatus(newStatus);
	    return userrepository.save(user);
	}

	public User updateUserPassword(Long id, String newPasswordHash) {
	    User user = userrepository.findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("User not found"));
	    user.setPasswordHash(newPasswordHash);
	    return userrepository.save(user);
	}



}