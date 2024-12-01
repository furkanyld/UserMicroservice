package tr.edu.ogu.ceng.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import common.Parent;
import tr.edu.ogu.ceng.User.controller.RoleController;

@SpringBootTest
public class RoleControllerTest extends Parent {


    @Autowired
    private RoleController roleController;

    @Test
    public void getRoleId(long id) {
    	
    }
}
