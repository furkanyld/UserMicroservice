package tr.edu.ogu.ceng.User.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tr.edu.ogu.ceng.User.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query
	List<User> findAllByStatus(String status);
	
	Optional<User> getByUsername(String username);

	Optional<User> findByEmail(String email);

	

	
	
}
