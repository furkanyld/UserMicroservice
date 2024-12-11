package tr.edu.ogu.ceng.User.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.edu.ogu.ceng.User.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
	Optional<Role> findByRoleName(String roleName);
}
