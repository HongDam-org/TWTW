package com.twtw.backend.domain.friend.controller;

import com.twtw.backend.domain.friend.dto.request.FriendRequest;
import com.twtw.backend.domain.friend.dto.request.FriendUpdateRequest;
import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.friend.service.FriendService;
import com.twtw.backend.support.docs.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("FriendController의")
@WebMvcTest(FriendController.class)
class FriendControllerTest extends RestDocsTest {
    @MockBean private FriendService friendService;

    @Test
    @DisplayName("친구 전체 조회 API가 수행되는가")
    void getFriends() throws Exception {
        // given
        final List<FriendResponse> expected =
                List.of(
                        new FriendResponse(UUID.randomUUID(), "정해진", "http://hojiniSelfie"),
                        new FriendResponse(UUID.randomUUID(), "주어진", "http://hojiniSelfCamera"));
        given(friendService.getFriends()).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/friends/all")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

        // docs
        perform.andDo(print())
                .andDo(document("get all friends", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("친구 전체 조회 캐시 API가 수행되는가")
    void getFriendsWithCache() throws Exception {
        // given
        final List<FriendResponse> expected =
                List.of(
                        new FriendResponse(UUID.randomUUID(), "정해진", "http://hojiniSelfie"),
                        new FriendResponse(UUID.randomUUID(), "주어진", "http://hojiniSelfCamera"));
        given(friendService.getFriendsWithCache()).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/friends/all/cache")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

        // docs
        perform.andDo(print())
                .andDo(document("get all friends with cache", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("친구 상태별 조회 API가 수행되는가")
    void getFriendsByStatus() throws Exception {
        // given
        final List<FriendResponse> expected =
                List.of(
                        new FriendResponse(UUID.randomUUID(), "호전", "http://HJ39Selfie"),
                        new FriendResponse(UUID.randomUUID(), "후진", "http://HJ39SelfCamera"));
        given(friendService.getFriendsByStatus(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/friends?friendStatus=REQUESTED")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "get friends by status",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("친구 상태별 조회 캐시 API가 수행되는가")
    void getFriendsByStatusWithCache() throws Exception {
        // given
        final List<FriendResponse> expected =
                List.of(
                        new FriendResponse(UUID.randomUUID(), "호전", "http://HJ39Selfie"),
                        new FriendResponse(UUID.randomUUID(), "후진", "http://HJ39SelfCamera"));
        given(friendService.getFriendsByStatusWithCache(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/friends/cache?friendStatus=REQUESTED")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "get friends by status with cache",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("친구 신청 API가 수행되는가")
    void addRequest() throws Exception {
        // given
        willDoNothing().given(friendService).addRequest(any());

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/friends/request")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(new FriendRequest(UUID.randomUUID())))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "post request friend",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("친구 상태 업데이트 API가 수행되는가")
    void updateStatus() throws Exception {
        // given
        willDoNothing().given(friendService).updateStatus(any());

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/friends/status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new FriendUpdateRequest(
                                                        UUID.randomUUID(), FriendStatus.ACCEPTED)))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "post update friend status",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("친구 닉네임으로 검색 API가 수행되는가")
    void getFriendByName() throws Exception {
        // given
        final List<FriendResponse> expected =
                List.of(
                        new FriendResponse(UUID.randomUUID(), "호진정", "http://hojiniSelfie"),
                        new FriendResponse(UUID.randomUUID(), "진정해", "http://hojiniSelfCamera"));
        given(friendService.getFriendByNickname(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/friends/search?nickname=hojin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

        // docs
        perform.andDo(print())
                .andDo(document("get search friend", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("친구 닉네임으로 검색 캐시 API가 수행되는가")
    void getFriendByNameWithCache() throws Exception {
        // given
        final List<FriendResponse> expected =
                List.of(
                        new FriendResponse(UUID.randomUUID(), "호진정", "http://hojiniSelfie"),
                        new FriendResponse(UUID.randomUUID(), "진정해", "http://hojiniSelfCamera"));
        given(friendService.getFriendByNicknameWithCache(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/friends/search/cache?nickname=hojin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

        // docs
        perform.andDo(print())
                .andDo(document("get search friend with cache", getDocumentRequest(), getDocumentResponse()));
    }
}
