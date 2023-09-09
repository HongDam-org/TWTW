package com.twtw.backend.global.client;

public interface PathClient<T, R> {
    public R request(T request);
}
