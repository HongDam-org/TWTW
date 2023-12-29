package com.twtw.backend.fixture.member;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberEntityFixture {
    LOGIN_MEMBER("호즨이", "http://someUrlToS3", "12345", "device", AuthType.APPLE),
    FIRST_MEMBER("담이와써요", "http://myPictureIsExpensive", "777", "deviceToken", AuthType.APPLE),
    SECOND_MEMBER("홍담진", "http://fighting", "1234567", "DeviceToken!", AuthType.KAKAO);

    private final String name;
    private final String profileImage;
    private final String clientId;
    private final String deviceToken;
    private final AuthType authType;

    public Member toEntity() {
        return new Member(name, profileImage, new OAuth2Info(clientId, authType), deviceToken);
    }
}
