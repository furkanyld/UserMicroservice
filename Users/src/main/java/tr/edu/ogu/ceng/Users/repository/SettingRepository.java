package tr.edu.ogu.ceng.Users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.Users.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting,Long> {
	
	
}
