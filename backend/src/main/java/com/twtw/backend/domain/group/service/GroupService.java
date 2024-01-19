package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.dto.request.*;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.GroupMemberResponse;
import com.twtw.backend.domain.group.dto.response.GroupResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.mapper.GroupMapper;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.messagequeue.FcmProducer;
import com.twtw.backend.global.constant.NotificationBody;
import com.twtw.backend.global.constant.NotificationTitle;
import com.twtw.backend.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final MemberService memberService;
    private final GroupMapper groupMapper;
    private final FcmProducer fcmProducer;

    public GroupService(
            GroupRepository groupRepository,
            AuthService authService,
            MemberService memberService,
            GroupMapper groupMapper,
            FcmProducer fcmProducer) {
        this.groupRepository = groupRepository;
        this.authService = authService;
        this.memberService = memberService;
        this.groupMapper = groupMapper;
        this.fcmProducer = fcmProducer;
    }

    @Transactional(readOnly = true)
    public GroupInfoResponse getGroupById(UUID groupId) {
        final Group group =
                groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
        return groupMapper.toGroupInfo(
                group, groupMapper.toGroupMemberResponseList(group.getGroupMembers()));
    }

    public Group getGroupEntity(UUID groupId) {
        return groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public GroupInfoResponse makeGroup(MakeGroupRequest groupDto) {
        final Member member = authService.getMemberByJwt();
        final Group group = groupRepository.save(groupMapper.toGroupEntity(groupDto, member));
        final List<GroupMemberResponse> groupMemberResponses =
                groupMapper.toGroupMemberResponseList(group.getGroupMembers());

        return groupMapper.toGroupInfo(group, groupMemberResponses);
    }

    @Transactional
    public void joinGroup(JoinGroupRequest joinGroupRequest) {
        Member member = authService.getMemberByJwt();

        final Group group = getGroupEntity(joinGroupRequest.getGroupId());
        group.join(member);
    }

    @Transactional
    public void shareLocation(final UUID id) {
        changeShare(GroupMember::share, id);
    }

    @Transactional
    public void unShareLocation(final UUID id) {
        changeShare(GroupMember::unShare, id);
    }

    private void changeShare(final Consumer<GroupMember> changeShare, final UUID id) {
        Member member = authService.getMemberByJwt();
        GroupInfoResponse groupInfo = getGroupById(id);

        final Group group = getGroupEntity(groupInfo.getGroupId());

        GroupMember groupMember = group.getSameMember(member);
        changeShare.accept(groupMember);
    }

    @Transactional
    public GroupInfoResponse inviteGroup(InviteGroupRequest inviteGroupRequest) {
        Group group = getGroupEntity(inviteGroupRequest.getGroupId());
        List<Member> friends =
                memberService.getMembersByIds(inviteGroupRequest.getFriendMemberIds());
        group.inviteAll(friends);

        String groupName = group.getName();
        final UUID id = group.getId();
        friends.forEach(friend -> sendNotification(friend.getDeviceTokenValue(), groupName, id));

        final List<GroupMemberResponse> groupMemberResponses =
                groupMapper.toGroupMemberResponseList(group.getGroupMembers());

        return groupMapper.toGroupInfo(group, groupMemberResponses);
    }

    private void sendNotification(final String deviceToken, final String groupName, final UUID id) {
        fcmProducer.sendNotification(
                new NotificationRequest(
                        deviceToken,
                        NotificationTitle.GROUP_REQUEST_TITLE.getName(),
                        NotificationBody.GROUP_REQUEST_BODY.toNotificationBody(groupName),
                        id.toString()));
    }

    public GroupInfoResponse getGroupInfoResponse(Group group) {
        final List<GroupMemberResponse> groupMemberResponses =
                groupMapper.toGroupMemberResponseList(group.getGroupMembers());
        return groupMapper.toGroupInfo(group, groupMemberResponses);
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getMyGroups() {
        Member loginMember = authService.getMemberByJwt();

        if (loginMember.hasNoGroupMember()) {
            return List.of();
        }

        return groupMapper.toGroupResponses(loginMember.getGroupMembers());
    }

    @Transactional
    public void updateLocation(final UpdateLocationRequest updateLocationRequest) {
        final Member member = authService.getMemberByJwt();
        final Group group = getGroupEntity(updateLocationRequest.getGroupId());
        group.updateMemberLocation(
                member, updateLocationRequest.getLongitude(), updateLocationRequest.getLatitude());
    }

    @Transactional
    public void outGroup(final OutGroupRequest outGroupRequest) {
        final Member member = authService.getMemberByJwt();
        final Group group = getGroupEntity(outGroupRequest.getGroupId());
        group.outGroup(member);
    }

    @Transactional
    public void deleteInvite(final DeleteGroupInviteRequest deleteGroupInviteRequest) {
        final Member member = authService.getMemberByJwt();
        final Group group = getGroupEntity(deleteGroupInviteRequest.getGroupId());
        group.deleteInvite(member);
    }
}
