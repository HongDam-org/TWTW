package com.twtw.backend.domain.group.repository;

import com.twtw.backend.domain.group.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaGroupRepository extends JpaRepository<Group, UUID>, GroupRepository {}
