package com.konai.hsyang.konatoy.posts.controller;

import com.konai.hsyang.konatoy.location.service.LocationService;
import com.konai.hsyang.konatoy.login.config.auth.PrincipalDetails;
import com.konai.hsyang.konatoy.login.config.auth.SessionUser;
import com.konai.hsyang.konatoy.login.domain.Club;
import com.konai.hsyang.konatoy.login.domain.User;
import com.konai.hsyang.konatoy.login.etc.Role;
import com.konai.hsyang.konatoy.posts.domain.Posts;
import com.konai.hsyang.konatoy.posts.dto.PostsResponseDto;
import com.konai.hsyang.konatoy.posts.dto.PostsSaveRequestDto;
import com.konai.hsyang.konatoy.posts.service.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PostsApiController.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class PostsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostsService postsService;

    @MockBean
    private LocationService locationService;

    // 각 테스트 전에 SecurityContext에 직접 Authentication 주입
    @BeforeEach
    void init(){
        PrincipalDetails principalDetails = createPrincipalDetails();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(principalDetails, principalDetails.getPassword(), principalDetails.getAuthorities()));
    }

    private PrincipalDetails createPrincipalDetails(){
        return new PrincipalDetails(new SessionUser(User.builder()
                .userName("testName")
                .userPw("testPassword")
                .club(Club.builder()
                        .clubname("testClub")
                        .build())
                .role(Role.USER)
                .build()));
    }

    @DisplayName("Posts 조회 API 테스트")
    @WithUserDetails
    @Test
    void findTest() throws Exception{

        Posts posts = Posts.builder()
                .title("test title")
                .content("test content")
                .build();

        given(postsService.postsResponseDtoFindById(any())).willReturn(new PostsResponseDto(posts));

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("test title"))
                .andDo(print());

//        String content = "{\"title\": \"Posts 테이블 드랍.\"}";
//        mockMvc.perform(get("/api/posts/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("title").value("Posts 테이블 드랍."))
//                .andDo(print());
    }

    @DisplayName("Posts 저장 API 테스트")
    @WithUserDetails
    @Test
    void saveTest() throws Exception{


    }
}
