package com.twtw.backend.domain.plan.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.plan.dto.request.PlanMemberRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlaceDetails;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.service.PlanService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

@DisplayName("PlanController의")
@WebMvcTest(PlanController.class)
class PlanControllerTest extends RestDocsTest {
    @MockBean private PlanService planService;

    @Test
    @DisplayName("키워드와 카테고리 기반 검색 API가 수행되는가")
    void searchPlanDestination() throws Exception {
        // given
        final PlanDestinationResponse expected =
                new PlanDestinationResponse(
                        List.of(
                                new PlaceDetails(
                                        "이디야커피 안성죽산점",
                                        "http://place.map.kakao.com/1562566188",
                                        "경기 안성시 죽산면 죽주로 287-1",
                                        127.426865189637,
                                        37.0764635355795),
                                new PlaceDetails(
                                        "카페 온마이마인드",
                                        "https://place.map.kakao.com/1625295668",
                                        "경기 안성시 죽산면 죽산초교길 36-4",
                                        127.420430538256,
                                        37.0766874564297)),
                        false);
        given(planService.searchPlanDestination(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/plans/search/destination")
                                .queryParam("query", "이디야 안성")
                                .queryParam("longitude", "127.426")
                                .queryParam("latitude", "37.0764")
                                .queryParam("page", "1")
                                .queryParam("categoryGroupCode", "CE7")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.isLast").isBoolean());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "get search plan destination",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("계획 저장 API가 수행되는가")
    void savePlan() throws Exception {
        // given
        final PlanResponse expected = new PlanResponse(UUID.randomUUID(), UUID.randomUUID());
        given(planService.savePlan(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/plans")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new SavePlanRequest(
                                                        UUID.randomUUID(),
                                                        new PlaceDetails(
                                                                "카페 온마이마인드",
                                                                "https://place.map.kakao.com/1625295668",
                                                                "경기 안성시 죽산면 죽산초교길 36-4",
                                                                127.420430538256,
                                                                37.0766874564297))))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").isString())
                .andExpect(jsonPath("$.groupId").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post save plan", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("단건 조회 API가 수행되는가")
    void getPlanById() throws Exception {
        // given
        final PlanInfoResponse expected =
                new PlanInfoResponse(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        new PlaceDetails(
                                "카페 온마이마인드",
                                "https://place.map.kakao.com/1625295668",
                                "경기 안성시 죽산면 죽산초교길 36-4",
                                127.420430538256,
                                37.0766874564297),
                        new GroupInfoResponse(
                                UUID.randomUUID(), UUID.randomUUID(), "홍담진", "http://someUrlToS3"),
                        List.of(new MemberResponse(UUID.randomUUID(), "진호정")));
        given(planService.getPlanById(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/plans/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").isString())
                .andExpect(jsonPath("$.members").isArray());

        // docs
        perform.andDo(print())
                .andDo(document("get plan", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("단건 삭제 API가 수행되는가")
    void deletePlanById() throws Exception {
        // when
        final ResultActions perform =
                mockMvc.perform(
                        delete("/plans/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(document("delete plan", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("계획 참여 API가 수행되는가")
    void joinPlan() throws Exception {
        // given
        final PlanResponse expected = new PlanResponse(UUID.randomUUID(), UUID.randomUUID());
        given(planService.joinPlan(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/plans/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new SavePlanRequest(
                                                        UUID.randomUUID(),
                                                        new PlaceDetails(
                                                                "이디야커피 안성죽산점",
                                                                "http://place.map.kakao.com/1562566188",
                                                                "경기 안성시 죽산면 죽주로 287-1",
                                                                127.426865189637,
                                                                37.0764635355795))))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").isString())
                .andExpect(jsonPath("$.groupId").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post join plan", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("계획 탈퇴 API가 수행되는가")
    void outPlan() throws Exception {
        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/plans/out")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(new PlanMemberRequest(UUID.randomUUID())))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(document("post out plan", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("계획 전체 조회 API가 수행되는가")
    void getPlans() throws Exception {
        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/plans")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("get all plans", getDocumentRequest(), getDocumentResponse()));
    }
}
