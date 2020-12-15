package ro.agilehub.javacourse.car.hire.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenAddUserOk_thenFindById() throws Exception {
        final String email = "loredana_costea@gmail.com";
        final String username = "loredanacostea";
        var input = new UserDTO().email(email).username(username);

        var postResult = mvc.perform(post("/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        var createdDTO = objectMapper.readValue(postResult.getResponse().getContentAsString(), UserDTO.class);

        var getResult = mvc.perform(get("/user/" + createdDTO.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        var userDTO = objectMapper.readValue(getResult.getResponse().getContentAsString(), UserDTO.class);

        assertEquals(email, userDTO.getEmail());

        mvc.perform(delete("/user/" + createdDTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}