package com.twtw.backend.support.stub;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.repository.GroupRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class StubGroupRepository implements GroupRepository {

    private final Map<UUID, Group> map = new HashMap<>();

    @Override
    public Optional<Group> findById(final UUID groupId) {
        return Optional.ofNullable(map.get(groupId));
    }

    @Override
    public Group save(final Group group) {
        map.put(group.getId(), group);
        return group;
    }
}
