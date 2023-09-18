package com.twtw.backend.domain.path.dto.client.car;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchPathOption {
    TRAFAST("실시간 빠른길", "trafast"),
    TRACOMFORT("실시간 편한길", "tracomfort"),
    TRAOPTIONAL("실시간 최적", "traoptional"),
    TRAAVOIDTOLL("무료 우선", "travoidtoll"),
    TRAAVOIDCARONLY("자동차 전용도로 회피 우선", "traavoidcaronly");

    private final String toKorean;
    private final String toSmall;

    public String toSmallOption() {
        return this.toSmall;
    }
}
