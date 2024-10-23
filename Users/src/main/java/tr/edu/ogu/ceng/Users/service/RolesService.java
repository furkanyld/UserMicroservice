package tr.edu.ogu.ceng.Users.service;

import java.util.List;

import org.hibernate.service.JavaServiceLoadable;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.Users.entity.Roles;
import tr.edu.ogu.ceng.Users.repository.RolesRepository;

@RequiredArgsConstructor
@JavaServiceLoadable
public class RolesService {

	private final RolesRepository rolesrepository;

	// Yeni bir rol oluşturma
	@Transactional
	public Roles createRole(Roles role) {
		return rolesrepository.save(role); // Yeni rolü kaydet
	}

	// Var olan bir rolü güncelleme
	@Transactional
	public Roles updateRole(Roles role) {
		return rolesrepository.save(role); // Var olan rolü güncelle
	}

	// Tüm rolleri listeleme
	public List<Roles> getAllRoles() {
		return rolesrepository.findAll(); // Tüm rolleri döndür
	}

	// ID ile rolü bulma
	public Roles getRoleById(Long id) {
		return rolesrepository.findById(id).orElse(null); // ID ile rolü bul
	}

	// Rolü silme
	@Transactional
	public void deleteRole(Long id) {
		rolesrepository.deleteById(id); // Rolü sil
	}
}
