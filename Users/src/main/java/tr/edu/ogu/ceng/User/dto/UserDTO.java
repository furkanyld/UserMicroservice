package tr.edu.ogu.ceng.User.dto;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String passwordHash;
}
