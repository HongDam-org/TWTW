package com.twtw.backend.domain.path.controller;

import com.twtw.backend.domain.path.dto.client.*;
import com.twtw.backend.domain.path.service.PathService;
import com.twtw.backend.support.docs.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("PathController의")
@WebMvcTest(PathController.class)
class PathControllerTest extends RestDocsTest {
    @MockBean private PathService pathService;

    @Test
    @DisplayName("경로 검색 API가 수행되는가")
    void searchPath() throws Exception {
        //given
        final SearchPathResponse expected = new SearchPathResponse(
                0,
                "",
                "",
                Map.of("", new RouteUnitEnt[]{
                        new RouteUnitEnt(new Summary(), List.of(new Double[]{0.0, 0.0}, new Double[]{0.0, 0.0}))}));

        given(pathService.searchPath(any())).willReturn(expected);

        //when
        final ResultActions perform = mockMvc.perform(
                post("/paths/search")
                        .content(
                                toRequestBody(new SearchPathRequest("", "", "", SearchPathOption.TRAFAST, SearchPathFuel.DIESEL, 0)))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("post search path", getDocumentRequest(), getDocumentResponse()));
    }
}
