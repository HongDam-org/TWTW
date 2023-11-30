package com.twtw.backend.domain.place.entity;

import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@Entity
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String placeName;

    private String placeUrl;

    private CategoryGroupCode categoryGroupCode;

    private String roadAddressName;

    @Embedded private Coordinate coordinate;

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    @Builder
    public Place(
            final String placeName,
            final String placeUrl,
            final CategoryGroupCode categoryGroupCode,
            final String roadAddressName,
            final Double longitude,
            final Double latitude) {
        this.placeName = placeName;
        this.placeUrl = placeUrl;
        this.categoryGroupCode = categoryGroupCode;
        this.roadAddressName = roadAddressName;
        this.coordinate = new Coordinate(longitude, latitude);
    }
}
