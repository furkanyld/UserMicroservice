package tr.edu.ogu.ceng.User.service;

import java.util.List;

import org.hibernate.service.JavaServiceLoadable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.repository.RoleRepository;

@RequiredArgsConstructor
@JavaServiceLoadable
@Service
public class RoleService {

	private final RoleRepository rolesrepository;

	// Yeni bir rol oluşturma
	@Transactional
	public Role createRole(Role role) {
		return rolesrepository.save(role); // Yeni rolü kaydet
	}

	// Var olan bir rolü güncelleme
	@Transactional
	public Role updateRole(Role role) {
		return rolesrepository.save(role); // Var olan rolü güncelle
	}

	// Tüm rolleri listeleme
	public List<Role> getAllRoles() {
		return rolesrepository.findAll(); // Tüm rolleri döndür
	}

	// ID ile rolü bulma
	public Role getRoleById(Long id) {
		return rolesrepository.findById(id).orElse(null); // ID ile rolü bul
	}

	// Rolü silme
	@Transactional
	public void deleteRole(Long id) {
		rolesrepository.deleteById(id); // Rolü sil
	}
}
