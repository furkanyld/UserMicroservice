package controller;
package tr.edu.ogu.ceng.user.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import common.parent;
import jakarta.transaction.Transactional;
import tr.edu.ogu.ceng.User.controller.RoleController;
import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.repository.RoleRepository;

@SpringBootTest
public class RoleControllerTest extends parent {


    @Autowired
    private RoleController roleController;

    @Test
    public void getRoleId(long id) {
    	
    }
}
