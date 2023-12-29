package com.twtw.backend.domain.member.entity;

import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceToken implements Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String deviceToken;

    @OneToOne(mappedBy = "deviceToken")
    private Member member;

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    public DeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void organizeMember(Member member) {
        this.member = member;
    }
}
