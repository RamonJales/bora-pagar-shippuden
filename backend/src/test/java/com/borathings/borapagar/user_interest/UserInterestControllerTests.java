package com.borathings.borapagar.user_interest;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(UserInterestController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserInterestControllerTests {
    
}
