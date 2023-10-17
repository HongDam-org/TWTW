package com.twtw.backend.domain.group.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.dto.response.GroupInfoDto;
import com.twtw.backend.domain.group.service.GroupService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

@DisplayName("GroupController의")
@WebMvcTest(GroupController.class)
public class GroupControllerTest extends RestDocsTest {
    @MockBean private GroupService groupService;

    @Test
    @DisplayName("GroupId로 그룹 조회가 되는가")
    void getGroupById() throws Exception {

        final GroupInfoDto expected =
                new GroupInfoDto(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        UUID.randomUUID(),
                        "HDJ",
                        "GROUP-IMAGE");
        given(groupService.getGroupById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")))
                .willReturn(expected);

        final ResultActions perform =
                mockMvc.perform(
                        get("/group/{id}", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("get group by id", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("Group이 정상적으로 생성되는가")
    void makeGroup() throws Exception {
        final GroupInfoDto expected =
                new GroupInfoDto(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        UUID.randomUUID(),
                        "HDJ",
                        "GROUP-IMAGE");
        given(groupService.makeGroup(any())).willReturn(expected);

        final ResultActions perform =
                mockMvc.perform(
                        post("/group")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new MakeGroupDto(
                                                        "HDJ",
                                                        "GROUP-IMAGE",
                                                        UUID.fromString(
                                                                "550e8400-e29b-41d4-a716-446655440000"))))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").exists())
                .andExpect(jsonPath("$.leaderId").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.groupImage").isString());

        perform.andDo(print())
                .andDo(document("post save group", getDocumentRequest(), getDocumentResponse()));
    }
}
