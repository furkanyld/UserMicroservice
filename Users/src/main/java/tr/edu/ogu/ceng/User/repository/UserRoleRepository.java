package tr.edu.ogu.ceng.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.edu.ogu.ceng.User.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
