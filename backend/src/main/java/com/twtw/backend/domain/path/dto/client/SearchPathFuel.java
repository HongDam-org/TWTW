package com.twtw.backend.domain.path.dto.client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchPathFuel {
    GASOLINE("gasoline"),
    HIGHGRADEGASOLINE("highgradegasoline"),
    DIESEL("diesel"),
    LPG("lpg");

    private final String toSmall;

    public String toSmallFuel() {
        return this.toSmall;
    }
}
