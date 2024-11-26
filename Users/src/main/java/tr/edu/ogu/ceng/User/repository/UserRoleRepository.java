package tr.edu.ogu.ceng.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.User.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
