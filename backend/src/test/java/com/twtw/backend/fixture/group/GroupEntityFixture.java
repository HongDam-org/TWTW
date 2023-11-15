package com.twtw.backend.fixture.group;

import com.twtw.backend.domain.group.entity.Group;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public enum GroupEntityFixture {
    HDJ_GROUP("HDG", "http://hdgToS3", UUID.randomUUID()),
    BTS_GROUP("BTS", "https://btsToS3", UUID.randomUUID());

    private final String name;

    private final String groupImage;

    private final UUID leaderId;

    public Group toEntity() {
        return new Group(this.name, this.groupImage, this.leaderId);
    }
}
