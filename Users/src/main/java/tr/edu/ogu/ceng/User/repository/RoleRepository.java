package tr.edu.ogu.ceng.User.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.User.entity.Role;



public interface RoleRepository extends JpaRepository<Role,Long> {
	Optional<Role> findByRoleName(String roleName);
}
