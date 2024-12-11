package tr.edu.ogu.ceng.User.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.JavaServiceLoadable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.RoleDTO;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.repository.RoleRepository;

@RequiredArgsConstructor
@JavaServiceLoadable
@Service
public class RoleService {

	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public RoleDTO createRole(RoleDTO roleDTO) {
		Role role = modelMapper.map(roleDTO, Role.class);
		role.setCreatedAt(LocalDateTime.now());
		role.setUpdatedAt(LocalDateTime.now());
		role.setCreatedBy("system"); // Gelecekte, kimlik doğrulama kullanılırsa güncellenebilir
		Role savedRole = roleRepository.save(role);
		return modelMapper.map(savedRole, RoleDTO.class);
	}

	@Transactional
	public RoleDTO updateRole(Long id, RoleDTO updatedRoleDTO) {
		Role updatedRole = modelMapper.map(updatedRoleDTO, Role.class);
		return roleRepository.findById(id).map(role -> {
			role.setRoleName(updatedRole.getRoleName());
			role.setDescription(updatedRole.getDescription());
			role.setUpdatedBy("system");
			role.setUpdatedAt(LocalDateTime.now());
			Role savedRole = roleRepository.save(role);
			return modelMapper.map(savedRole, RoleDTO.class);
		}).orElseThrow(() -> new RuntimeException("Role not found"));
	}
	
	public List<RoleDTO> getAllRoles() {
		return roleRepository.findAll()
				.stream()
				.map(role -> modelMapper.map(role, RoleDTO.class))
				.collect(Collectors.toList());
	}

	public RoleDTO getRoleById(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Role not found"));
		return modelMapper.map(role, RoleDTO.class);
	}

	public RoleDTO findByRoleName(String name) {
		Role role = roleRepository.findByRoleName(name)
				.orElseThrow(() -> new RuntimeException("Role not found: " + name));
		return modelMapper.map(role, RoleDTO.class);
	}

	@Transactional
	public void hardDeleteRole(Long id) {
		roleRepository.deleteById(id);
	}

	@Transactional
	public void softDeleteRole(Long id) {
		roleRepository.findById(id).ifPresent(role -> {
			role.setDeletedAt(LocalDateTime.now());
			role.setDeletedBy("system");
			roleRepository.save(role);
		});
	}
}