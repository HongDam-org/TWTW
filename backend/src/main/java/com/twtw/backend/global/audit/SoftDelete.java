package com.twtw.backend.global.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.Where;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Where(clause = "deleted_at is null")
public @interface SoftDelete {}
