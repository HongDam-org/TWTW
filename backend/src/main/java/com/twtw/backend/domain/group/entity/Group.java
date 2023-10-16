package com.twtw.backend.domain.group.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String groupImage;
    private UUID leaderId;

    @OneToMany(
            mappedBy = "group",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GroupMember> groupMembers = new ArrayList<>();

    public Group(String name, String groupImage, UUID leader) {
        this.name = name;
        this.groupImage = groupImage;
        this.leaderId = leader;
    }
}
