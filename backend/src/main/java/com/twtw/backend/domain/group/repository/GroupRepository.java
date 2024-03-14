package com.twtw.backend.domain.group.repository;

import com.twtw.backend.domain.group.entity.Group;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository {
    Optional<Group> findById(final UUID groupId);

    Group save(final Group group);
}
