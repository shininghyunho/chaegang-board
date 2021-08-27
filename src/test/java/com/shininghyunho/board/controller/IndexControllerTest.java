package com.shininghyunho.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shininghyunho.board.config.auth.dto.SessionUser;
import com.shininghyunho.board.controller.dto.PostsSaveRequestDto;
import com.shininghyunho.board.domain.post.Posts;
import com.shininghyunho.board.domain.post.PostsRepository;
import com.shininghyunho.board.domain.user.Role;
import com.shininghyunho.board.domain.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockHttpSession session;
    private MockMvc mvc;
    private User user;

    @Before
    public void setup(){
        mvc= MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        user=User.builder()
                .name("name")
                .email("email")
                .picture("picture")
                .role(Role.USER)
                .build();
        session=new MockHttpSession();
        session.setAttribute("user",new SessionUser(user));
    }

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void 메인페이지_로딩(){
        // when
        String body = this.restTemplate.getForObject("/",String.class);

        // then
        assertThat(body).contains("Chaegang");
    }

    @Test
    @WithMockUser(roles="USER")
    public void 조회수증가() throws Exception{
        // given
        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();
        String url="http://localhost:"+port+"/api/v1/posts";

        // when
        // 글 1개 작성
        mvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        List<Posts> all = postsRepository.findAll();
        Long beforeViews = all.get(0).getViews();
        // 1번째 글 get
        String body = this.restTemplate.getForObject("/posts/1",String.class);

        // then
        all = postsRepository.findAll();
        Long afterViews = all.get(0).getViews();
        assertThat(beforeViews+1).as("조회수 증가 확인").isEqualTo(afterViews);
        System.out.println("before views : "+beforeViews);
        System.out.println("after views : "+afterViews);
    }
}
