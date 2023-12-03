package com.twtw.backend.fixture.group;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupEntityFixture {
    HDJ_GROUP("HDG", "http://hdgToS3"),
    BTS_GROUP("BTS", "https://btsToS3");

    private final String name;

    private final String groupImage;

    public Group toEntity(final Member member) {
        return new Group(this.name, this.groupImage, member);
    }
}
