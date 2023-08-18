package com.twtw.backend.global.client;

public interface MapClient<T, R> {
    R request(T request);
}
