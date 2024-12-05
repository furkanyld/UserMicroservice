package tr.edu.ogu.ceng.User.dto;

import lombok.Data;

@Data
public class UserDTO {
	Long id;
	String username;
	String firstname;
	String lastname;
	String email;
	String passwordHash;
}
