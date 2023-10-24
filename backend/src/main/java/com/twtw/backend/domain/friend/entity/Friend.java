package com.twtw.backend.domain.friend.entity;

import com.twtw.backend.domain.friend.exception.InvalidFriendMemberException;
import com.twtw.backend.domain.member.entity.Member;

import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;
import com.twtw.backend.global.audit.SoftDelete;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
import lombok.Setter;

@Getter
@Entity
@SoftDelete
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "BINARY(16)")
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "BINARY(16)")
    private Member toMember;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    @Builder
    public Friend(final Member fromMember, final Member toMember) {
        validate(fromMember, toMember);
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.friendStatus = FriendStatus.REQUESTED;
    }

    private void validate(final Member fromMember, final Member toMember) {
        if (fromMember.getId().equals(toMember.getId())) {
            throw new InvalidFriendMemberException();
        }
    }

    public void updateStatus(final FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }
}
