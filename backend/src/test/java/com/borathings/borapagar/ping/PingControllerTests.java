package com.borathings.borapagar.ping;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class PingControllerTests {

    @Autowired private MockMvc mockMvc;

    @Test
    public void shouldReturnPong() throws Exception {
        this.mockMvc
                .perform(get("/api/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pong")));
    }

    @Test
    public void shouldReturnNPongs() throws Exception {
        this.mockMvc
                .perform(get("/api/pings").param("quantity", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[\"pong\",\"pong\",\"pong\"]")));
    }
}
