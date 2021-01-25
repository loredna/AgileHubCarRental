package ro.agilehub.javacourse.car.hire.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    @WithMockUser("jack")
    public void whenAddUserOk_thenFindById() throws Exception {
        final String email = "test@gmail.com";
        final String username = "test";
        var input = new UserDTO().email(email).username(username);

        var postResult = mvc.perform(post("/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn();

        var createdDTO = objectMapper.readValue(postResult.getResponse().getContentAsString(), UserDTO.class);

        var getResult = mvc.perform(get("/user/" + createdDTO.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var userDTO = objectMapper.readValue(getResult.getResponse().getContentAsString(), UserDTO.class);

        assertEquals(email, userDTO.getEmail());

        mvc.perform(delete("/user/" + createdDTO.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @WithMockUser("jack")
    public void whenAddUser_thenGetAllUsers_AndExpectAtLeastOneToBeFound() throws Exception {
        final String email = "test@gmail.com";
        final String username = "test";
        var input = new UserDTO().email(email).username(username);

        var postResult = mvc.perform(post("/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn();

        var getResult = mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andReturn();

        var listOfUsers = objectMapper.readValue(getResult.getResponse().getContentAsString(), List.class);

        System.out.println(listOfUsers);
        assertTrue(listOfUsers.size() > 0);

        var createdDTO = objectMapper.readValue(postResult.getResponse().getContentAsString(), UserDTO.class);

        mvc.perform(delete("/user/" + createdDTO.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @WithMockUser("jack")
    public void whenAddUser_thenUpdateTestUserEmail() throws Exception {
        final String email = "test@gmail.com";
        final String username = "test";
        var newUser = new UserDTO().email(email).username(username);

        var postResult = mvc.perform(post("/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andReturn();

        var createdDTO = objectMapper.readValue(postResult.getResponse().getContentAsString(), UserDTO.class);

        final String newEmail = "test2@gmail.com";
        var userToUpdate = new UserDTO().id(createdDTO.getId()).email(newEmail);

        var patchResult = mvc.perform(patch("/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToUpdate)))
                .andExpect(status().isOk())
                .andReturn();
        var updatedDTO = objectMapper.readValue(patchResult.getResponse().getContentAsString(), UserDTO.class);

        assertEquals(newEmail, updatedDTO.getEmail());

        mvc.perform(delete("/user/" + createdDTO.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}
