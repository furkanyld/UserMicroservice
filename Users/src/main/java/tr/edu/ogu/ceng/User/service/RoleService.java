package tr.edu.ogu.ceng.User.service;

import java.time.LocalDateTime;
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

	private final RoleRepository rolesRepository;

	// Yeni bir rol oluşturma
	@Transactional
	public Role createRole(Role role) {
		role.setCreatedAt(LocalDateTime.now());
		role.setUpdatedAt(LocalDateTime.now());
		role.setCreatedBy("system"); // Gelecekte, kimlik doğrulama kullanılırsa güncellenebilir
		return rolesRepository.save(role);
	}

	// Var olan bir rolü güncelleme
	@Transactional
	public Role updateRole(Long id, Role updatedRole) {
		return rolesRepository.findById(id).map(role -> {
			role.setRoleName(updatedRole.getRoleName());
			role.setDescription(updatedRole.getDescription());
			role.setUpdatedBy("system");
			role.setUpdatedAt(LocalDateTime.now());
			return rolesRepository.save(role);
		}).orElseThrow(() -> new RuntimeException("Role not found"));
	}

	// Tüm rolleri listeleme
	public List<Role> getAllRoles() {
		return rolesRepository.findAll(); // Tüm rolleri döndür
	}

	// ID ile rolü bulma
	public Role getRoleById(Long id) {
		return rolesRepository.findById(id).orElse(null); // ID ile rolü bul
	}
	
    // Role'ü isme göre bulma metodu
    public Role findByRoleName(String name) {
        return rolesRepository.findByRoleName(name)
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
    }

	// Rolü silme
	@Transactional
	public void hardDeleteRole(Long id) {
		rolesRepository.deleteById(id); // Rolü sil
	}

	@Transactional
	public void softDeleteRole(Long id) {
		rolesRepository.findById(id).ifPresent(role -> {
			role.setDeletedAt(LocalDateTime.now());
			role.setDeletedBy("system");
			rolesRepository.save(role);
		});
	}
}
