package com.twtw.backend.domain.member.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.request.TokenRequest;
import com.twtw.backend.domain.member.dto.response.AfterLoginResponse;
import com.twtw.backend.domain.member.dto.response.TokenDto;
import com.twtw.backend.domain.member.entity.AuthStatus;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("AuthController의")
@WebMvcTest(AuthController.class)
class AuthControllerTest extends RestDocsTest {
    @MockBean private AuthService authService;

    @Test
    @DisplayName("토큰이 만료되었는가")
    void validate() throws Exception {

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/auth/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isNoContent());
        // docs

        perform.andDo(print())
                .andDo(document("get validate token", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("JWT 리프레시 API가 수행되는가")
    void authorize() throws Exception {
        // given
        final TokenDto expected = new TokenDto("access.token.value", "refresh.token.value");
        given(authService.refreshToken(any(), any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/auth/refresh")
                                .content(
                                        toRequestBody(
                                                new TokenRequest(
                                                        "access.token.value",
                                                        "refresh.token.value")))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.refreshToken").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post refresh token", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("첫 로그인 API가 수행되는가")
    void saveMember() throws Exception {
        // given
        final AfterLoginResponse expected =
                new AfterLoginResponse(
                        AuthStatus.SIGNIN,
                        new TokenDto("access.token.value", "refresh.token.value"));
        given(authService.saveMember(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/auth/save")
                                .content(
                                        toRequestBody(
                                                new MemberSaveRequest(
                                                        "정해진",
                                                        "http://some-url-to-profile-image",
                                                        new OAuthRequest(
                                                                "client-id", AuthType.APPLE))))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenDto.accessToken").isString())
                .andExpect(jsonPath("$.tokenDto.refreshToken").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post save member", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("멤버가 저장된 상태에서의 로그인 API가 수행되는가")
    void afterSocialLogin() throws Exception {
        // given
        final AfterLoginResponse expected =
                new AfterLoginResponse(
                        AuthStatus.SIGNIN,
                        new TokenDto("access.token.value", "refresh.token.value"));
        given(authService.getTokenByOAuth(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/auth/login")
                                .content(
                                        toRequestBody(
                                                new OAuthRequest("client-id", AuthType.KAKAO)))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenDto.accessToken").isString())
                .andExpect(jsonPath("$.tokenDto.refreshToken").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post login", getDocumentRequest(), getDocumentResponse()));
    }
}
