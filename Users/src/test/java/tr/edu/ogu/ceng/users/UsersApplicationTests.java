package tr.edu.ogu.ceng.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersApplicationTests {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));

    @BeforeEach
    void setup() {
        postgres.start();
        
        
    }
    @Test
    void testPrintMessage() {
        // Bu metot test çalıştırıldığında "Test yapıldı" mesajını konsola yazdıracak
        System.out.println("Test yapıldı");
    }
}
   