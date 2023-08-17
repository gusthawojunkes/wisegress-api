package wo.it.controllers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import wo.it.services.AuthService;

@QuarkusTest
class AuthControllerTest {

    @InjectMock AuthService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}