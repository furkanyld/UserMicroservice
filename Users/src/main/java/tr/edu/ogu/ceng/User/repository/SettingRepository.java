package tr.edu.ogu.ceng.User.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.edu.ogu.ceng.User.entity.Setting;

@Repository
public interface SettingRepository extends JpaRepository<Setting,Long> {
	
	Optional<Setting> findById(Long id);
	
}
