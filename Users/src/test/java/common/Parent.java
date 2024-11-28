package common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class Parent {
	  @Container
	    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
	            DockerImageName.parse("postgres:16-alpine"));

	    static {
	        postgres.start();
	    }

	    @DynamicPropertySource
	    static void configureProperties(DynamicPropertyRegistry registry) {
	        registry.add("spring.datasource.url", postgres::getJdbcUrl);
	        registry.add("spring.datasource.username", postgres::getUsername);
	        registry.add("spring.datasource.password", postgres::getPassword);
	    }

}
