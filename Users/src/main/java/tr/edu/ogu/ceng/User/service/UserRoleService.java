package tr.edu.ogu.ceng.User.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.entity.UserRole;
import tr.edu.ogu.ceng.User.repository.UserRoleRepository;

@RequiredArgsConstructor
@Service
public class UserRoleService {

	private final UserRoleRepository userrolesrepository;

	// Yeni bir kullanıcı rolü oluşturma
	@Transactional
	public UserRole createUserRoles(UserRole userRole) {
		return userrolesrepository.save(userRole); // Yeni kullanıcı rolünü kaydet
	}

	// Var olan bir kullanıcı rolünü güncelleme
	@Transactional
	public UserRole updateUserRoles(UserRole userRole) {
		return userrolesrepository.save(userRole); // Var olan kullanıcı rolünü güncelle
	}

	// Tüm kullanıcı rollerini listeleme
	public List<UserRole> getAllUserRoles() {
		return userrolesrepository.findAll(); // Tüm kullanıcı rollerini döndür
	}

	// Kullanıcı rolünü silme
	@Transactional
	public void deleteUserRole(Long id) {
		userrolesrepository.deleteById(id); // Kullanıcı rolünü sil
	}

}