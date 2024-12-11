package tr.edu.ogu.ceng.User.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.dto.UserRoleDTO;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.entity.UserRole;
import tr.edu.ogu.ceng.User.repository.RoleRepository;
import tr.edu.ogu.ceng.User.repository.UserRepository;
import tr.edu.ogu.ceng.User.repository.UserRoleRepository;

@RequiredArgsConstructor
@Service
public class UserRoleService {

	private final UserRoleRepository userRoleRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public UserRoleDTO createUserRole(UserRoleDTO userRoleDTO) {
	
	    User user = userRepository.findById(userRoleDTO.getUserId())
	            .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userRoleDTO.getUserId()));
	    
	    Role role = roleRepository.findById(userRoleDTO.getRoleId())
	            .orElseThrow(() -> new NoSuchElementException("Role not found with id: " + userRoleDTO.getRoleId()));
	    
	    
	    UserRole userRole = new UserRole();
	    userRole.setUser(user);
	    userRole.setRole(role);
	    userRole.setAssignedAt(LocalDateTime.now());
	    userRole.setCreatedAt(LocalDateTime.now());
	    userRole.setUpdatedAt(LocalDateTime.now());

	    userRole = userRoleRepository.save(userRole);

	    UserRoleDTO responseDTO = modelMapper.map(userRole, UserRoleDTO.class);
	    responseDTO.setRoleId(role.getId());
	    responseDTO.setUserId(user.getId());
	    
	    return responseDTO;
	}

	@Transactional
	public UserRoleDTO updateUserRole(Long id, UserRoleDTO userRoleDTO, String updatedBy) {
		
	    UserRole existingUserRole = userRoleRepository.findById(id)
	            .orElseThrow(() -> new NoSuchElementException("UserRole not found with id: " + id));
	    
	    User user = userRepository.findById(userRoleDTO.getUserId())
	            .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userRoleDTO.getUserId()));
	    
	    Role role = roleRepository.findById(userRoleDTO.getRoleId())
	            .orElseThrow(() -> new NoSuchElementException("Role not found with id: " + userRoleDTO.getRoleId()));

	    existingUserRole.setUser(user);
	    existingUserRole.setRole(role);
	    existingUserRole.setUpdatedAt(LocalDateTime.now());
	    existingUserRole.setUpdatedBy(updatedBy);

	    existingUserRole = userRoleRepository.save(existingUserRole);

	    UserRoleDTO responseDTO = modelMapper.map(existingUserRole, UserRoleDTO.class);
	    responseDTO.setRoleId(role.getId());
	    responseDTO.setUserId(user.getId());

	    return responseDTO;
	}

	@Transactional
	public List<UserRoleDTO> getAllUserRoles() {
	    List<UserRole> userRoles = userRoleRepository.findAll();

	    return userRoles.stream()
	                    .map(userRole -> {
	                        UserRoleDTO userRoleDTO = new UserRoleDTO();
	                        userRoleDTO.setId(userRole.getId());
	                        userRoleDTO.setUserId(userRole.getUser().getId());
	                        userRoleDTO.setRoleId(userRole.getRole().getId());
	                        return userRoleDTO;
	                    })
	                    .collect(Collectors.toList());
	}

    
	@Transactional
	public void deleteUserRole(Long id) {

	    UserRole existingUserRole = userRoleRepository.findById(id)
	            .orElseThrow(() -> new NoSuchElementException("UserRole not found with id: " + id));

	    userRoleRepository.delete(existingUserRole);
	}


}