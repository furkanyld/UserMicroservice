package tr.edu.ogu.ceng.Users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.Users.entity.UserRoles;



public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {
	
	public UserRoles createUserRole(UserRoles userRole);

	
	public UserRoles updateUserRole(UserRoles userRole);

	
	public List<UserRoles> getAllUserRoles();

	
	public void deleteUserRole(Long id);
}
