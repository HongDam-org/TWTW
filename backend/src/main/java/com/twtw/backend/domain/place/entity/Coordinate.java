package com.twtw.backend.domain.place.entity;

import jakarta.persistence.Embeddable;

import lombok.*;

@Getter
@Embeddable
@AllArgsConstructor
@EqualsAndHashCode(of = {"longitude", "latitude"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {
    private Double longitude;
    private Double latitude;
}
