package com.twtw.backend.domain.group.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.group.dto.request.*;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.dto.response.SimpleGroupInfoResponse;
import com.twtw.backend.domain.group.service.GroupService;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DisplayName("GroupController의")
@WebMvcTest(GroupController.class)
class GroupControllerTest extends RestDocsTest {
    @MockBean private GroupService groupService;

    @Test
    @DisplayName("GroupId로 그룹 조회가 되는가")
    void getGroupById() throws Exception {

        final GroupInfoResponse expected =
                new GroupInfoResponse(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        UUID.randomUUID(),
                        "HDJ",
                        "GROUP-IMAGE",
                        List.of(
                                new MemberResponse(
                                        UUID.randomUUID(), "DEAN", "http://hojiniSelfie"),
                                new MemberResponse(
                                        UUID.randomUUID(), "ZION-T", "http://hojiniSelfie")));
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
        final GroupInfoResponse expected =
                new GroupInfoResponse(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        UUID.randomUUID(),
                        "HDJ",
                        "GROUP-IMAGE",
                        List.of(
                                new MemberResponse(
                                        UUID.randomUUID(), "DEAN", "http://hojiniSelfie"),
                                new MemberResponse(
                                        UUID.randomUUID(), "ZION-T", "http://hojiniSelfie")));
        given(groupService.makeGroup(any())).willReturn(expected);

        final ResultActions perform =
                mockMvc.perform(
                        post("/group")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(new MakeGroupRequest("HDJ", "GROUP-IMAGE")))
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

    @Test
    @DisplayName("그룹 가입 API가 수행되는가")
    void joinGroup() throws Exception {
        // given
        final SimpleGroupInfoResponse expected = new SimpleGroupInfoResponse(UUID.randomUUID());
        given(groupService.joinGroup(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/group/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(new JoinGroupRequest(UUID.randomUUID())))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.groupId").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post join group", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("그룹 초대 API가 수행되는가")
    void inviteGroup() throws Exception {
        // given
        final GroupInfoResponse expected =
                new GroupInfoResponse(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "홍담진",
                        "http://someUrlToS3",
                        List.of(
                                new MemberResponse(
                                        UUID.randomUUID(), "DEAN", "http://hojiniSelfie"),
                                new MemberResponse(
                                        UUID.randomUUID(), "ZION-T", "http://hojiniSelfie")));
        given(groupService.inviteGroup(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/group/invite")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new InviteGroupRequest(
                                                        List.of(UUID.randomUUID()),
                                                        UUID.randomUUID())))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").isString())
                .andExpect(jsonPath("$.name").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post invite group", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("그룹 초대 삭제 API가 수행되는가")
    void deleteInvite() throws Exception {
        // given
        willDoNothing().given(groupService).deleteInvite(any());

        // when
        final ResultActions perform =
                mockMvc.perform(
                        delete("/group/invite")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new DeleteGroupInviteRequest(UUID.randomUUID())))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "delete group invite",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("위치 공유 수정 API가 수행되는가")
    void shareLocation() throws Exception {
        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/group/share/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "post share location",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("위치 공유 수정 API가 수행되는가")
    void unShareLocation() throws Exception {
        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/group/unshare/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "post unshare location",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("위치 공유 조회 API가 수행되는가")
    void getShare() throws Exception {
        // given
        final ShareInfoResponse expected =
                new ShareInfoResponse(UUID.randomUUID(), UUID.randomUUID(), true);
        given(groupService.getShare(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        get("/group/share/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new InviteGroupRequest(
                                                        List.of(UUID.randomUUID()),
                                                        UUID.randomUUID())))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.share").isBoolean());

        // docs
        perform.andDo(print())
                .andDo(document("get share", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("자신의 그룹 정보가 정상적으로 반환되는가")
    void getMyGroups() throws Exception {
        // given
        final UUID leaderId = UUID.randomUUID();

        List<GroupInfoResponse> responseList = new ArrayList<>();

        GroupInfoResponse response1 =
                new GroupInfoResponse(
                        UUID.randomUUID(),
                        leaderId,
                        "BLACK_PINK",
                        "I_LOVE_YOU_LOSE",
                        List.of(
                                new MemberResponse(
                                        UUID.randomUUID(), "LISA", "http://hojiniSelfieWow"),
                                new MemberResponse(
                                        UUID.randomUUID(), "제니", "http://hojiniSelfieAwesome")));
        GroupInfoResponse response2 =
                new GroupInfoResponse(
                        UUID.randomUUID(),
                        leaderId,
                        "LE_SSERAFIM",
                        "I_LOVE_YOU_채원",
                        List.of(
                                new MemberResponse(
                                        UUID.randomUUID(), "카즈하", "http://hojiniSelfieGreat"),
                                new MemberResponse(
                                        UUID.randomUUID(), "사쿠라", "http://hojiniSelfieGoat")));

        responseList.add(response1);
        responseList.add(response2);

        given(groupService.getMyGroups()).willReturn(responseList);
        // when

        final ResultActions perform =
                mockMvc.perform(
                        get("/group")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isOk());

        perform.andDo(print())
                .andDo(document("get myGroups", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("자신의 위치가 정상적으로 수정되는가")
    void updateLocation() throws Exception {
        // given
        willDoNothing().given(groupService).updateLocation(any());

        // when`
        final ResultActions perform =
                mockMvc.perform(
                        post("/group/location")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        toRequestBody(
                                                new UpdateLocationRequest(
                                                        UUID.randomUUID(), 0.0, 0.0)))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isNoContent());

        perform.andDo(print())
                .andDo(
                        document(
                                "post update group member location",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("그룹 탈퇴가 수행되는가")
    void outGroup() throws Exception {
        // given
        willDoNothing().given(groupService).outGroup(any());

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/group/out")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(new OutGroupRequest(UUID.randomUUID())))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));
        // then
        perform.andExpect(status().isNoContent());

        perform.andDo(print())
                .andDo(document("post out group", getDocumentRequest(), getDocumentResponse()));
    }
}
