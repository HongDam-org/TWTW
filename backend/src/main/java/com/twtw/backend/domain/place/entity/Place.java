package com.twtw.backend.domain.place.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@Entity
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place implements Auditable {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    @Column(nullable = false)
    private String placeName;

    private String placeUrl;

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
            final String roadAddressName,
            final Double longitude,
            final Double latitude) {
        this.placeName = placeName;
        this.placeUrl = placeUrl;
        this.roadAddressName = roadAddressName;
        this.coordinate = new Coordinate(longitude, latitude);
    }

    public void update(
            final String placeName,
            final String placeUrl,
            final String roadAddressName,
            final Double longitude,
            final Double latitude) {
        this.placeName = placeName;
        this.placeUrl = placeUrl;
        this.roadAddressName = roadAddressName;
        this.coordinate = new Coordinate(longitude, latitude);
    }
}
