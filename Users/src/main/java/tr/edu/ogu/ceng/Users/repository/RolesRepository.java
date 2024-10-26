package tr.edu.ogu.ceng.Users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.Users.entity.Roles;



public interface RolesRepository extends JpaRepository<Roles,Long> {
	
}
