package wo.it.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import wo.it.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AuthServiceTest {

    @InjectMock UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}