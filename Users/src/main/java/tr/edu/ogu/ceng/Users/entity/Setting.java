package tr.edu.ogu.ceng.Users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "settings", schema = "users_application")
public class Setting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	@Column(name = "key", nullable = false, length = 255)
	private String key; 

	@Column(name = "value", nullable = false, length = 255)
	private String value; 

	

}
