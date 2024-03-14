package com.twtw.backend.domain.friend.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.twtw.backend.domain.friend.exception.InvalidFriendMemberException;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@Where(clause = "deleted_at is null and friend_status != 'EXPIRED'")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend implements Auditable {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

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
        if (isRequestNotExpired()) {
            this.friendStatus = friendStatus;
            return;
        }
        this.friendStatus = FriendStatus.EXPIRED;
    }

    public Member getFriendMember(final Member loginMember) {
        if (isFromMember(loginMember)) {
            return this.toMember;
        }
        return this.fromMember;
    }

    private boolean isFromMember(final Member member) {
        return this.fromMember.getId().equals(member.getId());
    }

    public void checkExpire() {
        if (isRequestNotExpired()) {
            return;
        }
        this.friendStatus = FriendStatus.EXPIRED;
    }

    private boolean isRequestNotExpired() {
        return this.baseTime == null
                || (this.friendStatus == FriendStatus.REQUESTED
                        && this.baseTime
                                .getCreatedAt()
                                .isAfter(LocalDateTime.now().minusMinutes(30L)));
    }

    public boolean nicknameContains(final String nickname) {
        return this.fromMember.nicknameContains(nickname) || this.toMember.nicknameContains(nickname);
    }
}
