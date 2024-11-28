package tr.edu.ogu.ceng.user.controller;

import org.springframework.boot.test.mock.mockito.MockBean;

import common.Parent;
import tr.edu.ogu.ceng.User.service.UserService;

public class UserControllerTest extends Parent {
	
	@MockBean
	private UserService userService;
	
	static String hostPath = "http://localhost:8007/api/users";
	
}
