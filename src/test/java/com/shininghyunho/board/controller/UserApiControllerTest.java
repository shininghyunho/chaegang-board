package com.shininghyunho.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shininghyunho.board.controller.dto.UserUpdateRequestDto;
import com.shininghyunho.board.domain.user.Role;
import com.shininghyunho.board.domain.user.User;
import com.shininghyunho.board.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc= MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void User_수정된다() throws Exception{
        // given
        User savedUser=userRepository.save(User.builder()
                .name("name")
                .email("email")
                .picture("picture")
                .role(Role.GUEST)
                .build());

        Long updateId = savedUser.getId();
        Role expectedRole = Role.USER;

        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .role(expectedRole)
                .build();

        // when
        String url = "http://localhost:"+port+"/api/v1/user/"+updateId;

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getRole()).isEqualTo(expectedRole);
    }
}
