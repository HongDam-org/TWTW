package com.twtw.backend.domain.place.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@AllArgsConstructor
@EqualsAndHashCode(of = {"addressName"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String addressName;
    private String roadAddressName;
}
