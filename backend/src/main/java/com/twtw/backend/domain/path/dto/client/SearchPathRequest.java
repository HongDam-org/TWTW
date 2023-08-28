package com.twtw.backend.domain.path.dto.client;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPathRequest {
    String start;
    String end;
    String wayPoint;

    @Enumerated(value = EnumType.STRING)
    SearchPathOption option;

    String carType;

    @Enumerated(value = EnumType.STRING)
    SearchPathFuel fuelType;
}
