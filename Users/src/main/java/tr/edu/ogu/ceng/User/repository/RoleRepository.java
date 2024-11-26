package tr.edu.ogu.ceng.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.User.entity.Role;



public interface RoleRepository extends JpaRepository<Role,Long> {
	
}
