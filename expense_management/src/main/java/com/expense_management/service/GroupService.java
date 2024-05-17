package com.expense_management.service;

import com.expense_management.entity.Group;
import com.expense_management.entity.GroupMember;
import com.expense_management.entity.User;

import java.util.List;

public interface GroupService {
    Group createGroup(String name, String description, String creatorEmail);
    GroupMember inviteMember(Long groupId, String email);
    List<Group> getAllGroups();
    Group getGroupById(Long id);
    Group updateGroup(Long id, Group newGroup);
    boolean deleteGroup(Long id);
}
