package com.api.group.service;

import com.api.group.domain.Group;
import com.api.group.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> getAllGroup();
    Optional<Group> getGroupById(Long id);
    Group saveGroup(Group group);
    void deleteGroupById(Long id);
}
