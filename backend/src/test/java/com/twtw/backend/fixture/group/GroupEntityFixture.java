package com.twtw.backend.fixture.group;

import com.twtw.backend.domain.group.entity.Group;

import java.util.UUID;

public enum GroupEntityFixture {
    HDJ_GROUP("HDG", "http://hdgToS3", UUID.randomUUID()),
    BTS_GROUP("BTS", "https://btsToS3", UUID.randomUUID());

    private String name;

    private String groupImage;

    private UUID leaderId;

    GroupEntityFixture(final String name, final String groupImage, final UUID leaderId) {
        this.name = name;
        this.groupImage = groupImage;
        this.leaderId = leaderId;
    }

    public Group toEntity() {
        return new Group(this.name, this.groupImage, this.leaderId);
    }
}
