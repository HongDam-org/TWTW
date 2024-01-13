package com.twtw.backend.domain.plan.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.service.PlanService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.UUID;

@WebMvcTest(PlanController.class)
@DisplayName("LocalDateTimeDto 변환")
class LocalDateTimeDtoTest extends RestDocsTest {

    @MockBean private PlanService planService;

    @Test
    @DisplayName("LocalDateTimeDto 변환 성공")
    void formatSuccess() throws Exception {

        when(planService.savePlan(any()))
                .thenReturn(new PlanResponse(UUID.randomUUID(), UUID.randomUUID()));

        mockMvc.perform(
                        post("/plans")
                                .content(
                                        "{\n"
                                            + "  \"name\": \"name_c195e577132b\",\n"
                                            + "  \"groupId\":"
                                            + " \"63fcf33b-55a7-49fc-b8e7-97c993e43632\",\n"
                                            + "  \"planDay\": \"2024-01-13 20:22\",\n"
                                            + "  \"placeDetails\": {\n"
                                            + "    \"placeName\": \"placeName_2bd877f5726b\",\n"
                                            + "    \"placeUrl\": \"placeUrl_fd39b3850bfe\",\n"
                                            + "    \"roadAddressName\":"
                                            + " \"roadAddressName_d020d911090a\",\n"
                                            + "    \"longitude\": 0.00,\n"
                                            + "    \"latitude\": 0.00\n"
                                            + "  },\n"
                                            + "  \"memberIds\": [\n"
                                            + "    \"6fc8d409-7010-42b9-8c6f-e807f4028242\"\n"
                                            + "  ]\n"
                                            + "}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"))
                .andExpect(status().isOk());
    }
}
