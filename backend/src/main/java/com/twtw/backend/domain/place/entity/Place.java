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
import org.mapstruct.Named;

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

    private Integer distance;

    private String placeUrl;

    private String categoryName;

    private CategoryGroupCode categoryGroupCode;

    @Embedded private Address address;

    @Embedded private Coordinate coordinate;

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    @Builder
    public Place(
            final String placeName,
            final Integer distance,
            final String placeUrl,
            final String categoryName,
            final CategoryGroupCode categoryGroupCode,
            final String addressName,
            final String roadAddressName,
            final String x,
            final String y) {
        this.placeName = placeName;
        this.distance = distance;
        this.placeUrl = placeUrl;
        this.categoryName = categoryName;
        this.categoryGroupCode = categoryGroupCode;
        this.address = new Address(addressName, roadAddressName);
        this.coordinate = new Coordinate(Double.valueOf(x), Double.valueOf(y));
    }
}
