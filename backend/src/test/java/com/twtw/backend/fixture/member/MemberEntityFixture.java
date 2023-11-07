package com.twtw.backend.fixture.member;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;

public enum MemberEntityFixture {
    LOGIN_MEMBER("호즨이", "http://someUrlToS3", "12345", AuthType.APPLE);

    private final String name;
    private final String profileImage;
    private final String clientId;
    private final AuthType authType;

    MemberEntityFixture(
            final String name,
            final String profileImage,
            final String clientId,
            final AuthType authType) {
        this.name = name;
        this.profileImage = profileImage;
        this.clientId = clientId;
        this.authType = authType;
    }

    public Member toEntity() {
        return new Member(name, profileImage, new OAuth2Info(clientId, authType));
    }
}
