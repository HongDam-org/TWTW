package com.twtw.backend.global.audit;

import org.hibernate.annotations.Where;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Where(clause = "deleted_at is null")
public @interface SoftDelete {}
