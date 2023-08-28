package com.twtw.backend.global.client;

public interface PathClient<T, R>{
    public String request(T request);
}
