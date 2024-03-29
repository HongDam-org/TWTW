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

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

@DisplayName("MemberController의")
@WebMvcTest(MemberController.class)
public class MemberControllerTest extends RestDocsTest {
    @MockBean private MemberService memberService;

    @Test
    @DisplayName("닉네임이 중복되었는가")
    void duplicate() throws Exception {
        final DuplicateNicknameResponse expected = new DuplicateNicknameResponse(false);
        given(memberService.duplicateNickname(any())).willReturn(expected);

        final ResultActions perform =
                mockMvc.perform(
                        get("/member/duplicate/{name}", "JinJooOne")
                                .contentType(MediaType.APPLICATION_JSON));

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

    @Test
    @DisplayName("Member가 제대로 검색되는가")
    void searchMemberByNickname() throws Exception {
        // given
        final List<MemberResponse> response =
                List.of(
                        new MemberResponse(UUID.randomUUID(), "hojin", "http://HJ39"),
                        new MemberResponse(UUID.randomUUID(), "JIN_JOO", "http://HJ39"));
        given(memberService.getMemberByNickname(any())).willReturn(response);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/member")
                                .param("nickname", "jin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(
                        document(
                                "get member nickname",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("Member id가 조회되는가")
    void getMemberId() throws Exception {
        // given
        final MemberResponse expected =
                new MemberResponse(UUID.randomUUID(), "JeongHaeJin39", "http://profileimage");
        given(memberService.getMemberId()).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/member/me")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("get member id", getDocumentRequest(), getDocumentResponse()));
    }
}
