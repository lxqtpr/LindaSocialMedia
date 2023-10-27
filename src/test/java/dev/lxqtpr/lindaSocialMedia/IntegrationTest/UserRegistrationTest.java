package dev.lxqtpr.lindaSocialMedia.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void userTryToRegisterThenLogin(){
        //mockMvc.perform(post("/forums/{forumId}/register", 42L);
    }
}
