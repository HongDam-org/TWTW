package com.twtw.backend.domain.place.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.place.dto.response.PlaceResponse;
import com.twtw.backend.domain.place.service.PlaceService;
import com.twtw.backend.domain.plan.dto.client.PlaceClientDetails;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@DisplayName("PlaceController의")
@WebMvcTest(PlaceController.class)
class PlaceControllerTest extends RestDocsTest {
    @MockBean private PlaceService placeService;

    @Test
    @DisplayName("주변 장소 검색 API가 수행되는가")
    void searchSurroundPlace() throws Exception {
        final PlaceResponse expected =
                new PlaceResponse(
                        List.of(
                                new PlaceClientDetails(
                                        "이디야커피 안성죽산점",
                                        "http://place.map.kakao.com/1562566188",
                                        "경기 안성시 죽산면 죽주로 287-1",
                                        127.426865189637,
                                        37.0764635355795),
                                new PlaceClientDetails(
                                        "카페 온마이마인드",
                                        "https://place.map.kakao.com/1625295668",
                                        "경기 안성시 죽산면 죽산초교길 36-4",
                                        127.420430538256,
                                        37.0766874564297)),
                        false);
        given(placeService.searchSurroundPlace(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/places/surround")
                                .queryParam("longitude", "127.426")
                                .queryParam("latitude", "37.0764")
                                .queryParam("page", "1")
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
                                "get search surround place",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
}
