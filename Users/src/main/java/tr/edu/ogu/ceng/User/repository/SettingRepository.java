package tr.edu.ogu.ceng.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.User.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting,Long> {
	
	
}
