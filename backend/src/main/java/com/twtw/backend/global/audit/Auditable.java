package com.twtw.backend.global.audit;

public interface Auditable {
    BaseTime getBaseTime();

    void setBaseTime(final BaseTime baseTime);
}
