package tr.edu.ogu.ceng.Users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.Users.entity.Users;



public interface UsersRepository extends JpaRepository<Users,Long> {
	public Users createUser(Users user);
	public Users updateUser(Users user);
	
}
