package com.twtw.backend.domain.member.mapper;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.plan.entity.PlanMember;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {

    @Mapping(target = "oauthInfo", source = "oauthRequest", qualifiedByName = "convertOauth")
    Member toMemberEntity(MemberSaveRequest request);

    MemberResponse toMemberResponse(Member member);

    @Named("convertOauth")
    default OAuth2Info convertOauth(OAuthRequest request) {
        return new OAuth2Info(request.getToken(), request.getAuthType());
    }

    @IterableMapping(elementTargetType = MemberResponse.class)
    List<MemberResponse> toMemberResponses(Set<PlanMember> planMembers);

    @Mapping(target = "id", source = "planMember.member.id")
    @Mapping(target = "nickname", source = "planMember.member.nickname")
    MemberResponse toMemberResponse(PlanMember planMember);
}
