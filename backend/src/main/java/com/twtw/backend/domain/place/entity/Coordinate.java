package com.twtw.backend.domain.place.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@AllArgsConstructor
@EqualsAndHashCode(of = {"x", "y"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {
    private Double x;
    private Double y;
}
