package tr.edu.ogu.ceng.Users.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.Users.entity.UserRoles;
import tr.edu.ogu.ceng.Users.repository.UserRolesRepository;

@RequiredArgsConstructor
@Service
public class UserRolesService {

	private final UserRolesRepository userrolesrepository;

	// Yeni bir kullanıcı rolü oluşturma
	@Transactional
	public UserRoles createUserRole(UserRoles userRole) {
		return userrolesrepository.save(userRole); // Yeni kullanıcı rolünü kaydet
	}

	// Var olan bir kullanıcı rolünü güncelleme
	@Transactional
	public UserRoles updateUserRole(UserRoles userRole) {
		return userrolesrepository.save(userRole); // Var olan kullanıcı rolünü güncelle
	}

	// Tüm kullanıcı rollerini listeleme
	public List<UserRoles> getAllUserRoles() {
		return userrolesrepository.findAll(); // Tüm kullanıcı rollerini döndür
	}

	// Kullanıcı rolünü silme
	@Transactional
	public void deleteUserRole(Long id) {
		userrolesrepository.deleteById(id); // Kullanıcı rolünü sil
	}

}