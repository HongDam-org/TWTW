package com.twtw.backend.domain.member.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameDto;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("MemberController의")
@WebMvcTest(MemberController.class)
public class MemberControllerTest extends RestDocsTest {
    @MockBean private MemberService memberService;

    @Test
    @DisplayName("닉네임이 중복되었는가")
    void duplicate() throws Exception {
        final DuplicateNicknameDto expected =
                new DuplicateNicknameDto(false);
        given(memberService.duplicateNickname(any())).willReturn(expected);


        final ResultActions perform =
                mockMvc.perform(
                        get("/member/duplicate/{name}", "JinJooOne")
                                .contentType(MediaType.APPLICATION_JSON)
                );

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.isPresent").exists());
        // docs

        perform.andDo(print())
                .andDo(
                        document(
                                "get duplicate nickname",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
}
