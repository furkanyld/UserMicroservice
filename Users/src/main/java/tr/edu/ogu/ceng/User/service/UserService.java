package tr.edu.ogu.ceng.User.service;

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
	
	


}