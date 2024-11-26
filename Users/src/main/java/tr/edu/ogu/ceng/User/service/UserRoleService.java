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

	
	@Transactional
	public UserRole createUserRoles(UserRole userRole) {
		return userrolesrepository.save(userRole); 
	}

	
	@Transactional
	public UserRole updateUserRoles(UserRole userRole) {
		return userrolesrepository.save(userRole); 
	}

	
	public List<UserRole> getAllUserRoles() {
		return userrolesrepository.findAll(); 
	}

	
	@Transactional
	public void deleteUserRole(Long id) {
		userrolesrepository.deleteById(id); 
	}

}