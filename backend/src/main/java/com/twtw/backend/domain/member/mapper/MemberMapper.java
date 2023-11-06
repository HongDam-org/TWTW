package com.twtw.backend.domain.member.mapper;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {

    @Mapping(target = "oauthInfo", source = "oauthRequest",qualifiedByName = "convertOauth")
    Member toMemberEntity(MemberSaveRequest request);

    MemberResponse toMemberResponse(Member member);

    @Named("convertOauth")
    default OAuth2Info convertOauth(OAuthRequest request){
        return new OAuth2Info(request.getToken(),request.getAuthType());
    }
}
