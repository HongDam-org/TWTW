package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.dto.request.*;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.dto.response.SimpleGroupInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.mapper.GroupMapper;
import com.twtw.backend.domain.group.repository.GroupMemberRepository;
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
    private final GroupMemberRepository groupMemberRepository;
    private final AuthService authService;
    private final MemberService memberService;
    private final GroupMapper groupMapper;
    private final FcmProducer fcmProducer;

    public GroupService(
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            AuthService authService,
            MemberService memberService,
            GroupMapper groupMapper,
            FcmProducer fcmProducer) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.authService = authService;
        this.memberService = memberService;
        this.groupMapper = groupMapper;
        this.fcmProducer = fcmProducer;
    }

    @Transactional(readOnly = true)
    public GroupInfoResponse getGroupById(UUID groupId) {
        return groupMapper.toGroupInfo(
                groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new));
    }

    public Group getGroupEntity(UUID groupId) {
        return groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
    }

    private GroupMember getGroupMemberEntity(UUID groupId, UUID memberId) {
        return groupMemberRepository
                .findByGroupIdAndMemberId(groupId, memberId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public GroupInfoResponse makeGroup(MakeGroupRequest groupDto) {
        Member member = authService.getMemberByJwt();
        Group group = groupMapper.toGroupEntity(groupDto, member);

        return groupMapper.toGroupInfo(groupRepository.save(group));
    }

    @Transactional
    public SimpleGroupInfoResponse joinGroup(JoinGroupRequest joinGroupRequest) {
        Member member = authService.getMemberByJwt();
        GroupMember groupMember =
                getGroupMemberEntity(joinGroupRequest.getGroupId(), member.getId());

        groupMember.acceptInvite();

        return new SimpleGroupInfoResponse(groupMember.getGroupId());
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
        Member member = this.authService.getMemberByJwt();
        GroupInfoResponse groupInfo = getGroupById(id);

        GroupMember groupMember = getGroupMemberEntity(groupInfo.getGroupId(), member.getId());
        changeShare.accept(groupMember);
    }

    @Transactional
    public ShareInfoResponse getShare(UUID id) {
        Member member = this.authService.getMemberByJwt();
        GroupInfoResponse groupInfo = getGroupById(id);

        GroupMember groupMember = getGroupMemberEntity(groupInfo.getGroupId(), member.getId());

        return groupMapper.toShareInfo(groupMember);
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

        return groupMapper.toGroupInfo(group);
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
        return groupMapper.toGroupInfo(group);
    }

    @Transactional(readOnly = true)
    public List<GroupInfoResponse> getMyGroups() {
        Member loginMember = authService.getMemberByJwt();

        if (loginMember.hasNoGroupMember()) {
            return List.of();
        }

        return groupMapper.toMyGroupsInfo(loginMember.getGroupMembers());
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
