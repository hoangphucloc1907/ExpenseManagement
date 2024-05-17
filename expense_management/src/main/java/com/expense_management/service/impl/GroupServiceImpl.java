package com.expense_management.service.impl;

import com.expense_management.entity.Group;
import com.expense_management.entity.GroupMember;
import com.expense_management.entity.User;
import com.expense_management.repository.GroupMemberRepository;
import com.expense_management.repository.GroupRepository;
import com.expense_management.repository.UserRepository;
import com.expense_management.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    @Override
    public Group createGroup(String name, String description, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + creatorEmail));
        Group group = Group.builder()
                .name(name)
                .description(description)
                .build();
        return groupRepository.save(group);
    }

    @Override
    public GroupMember inviteMember(Long groupId, String email) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        GroupMember groupMember = GroupMember.builder()
                .groupId(group)
                .userId(user)
                .status("INVITED")
                .build();
        return groupMemberRepository.save(groupMember);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public Group updateGroup(Long id, Group newGroup) {
        return groupRepository.findById(id).map(group -> {
            group.setName(newGroup.getName());
            group.setDescription(newGroup.getDescription());
            return groupRepository.save(group);
        }).orElse(null);
    }

    @Override
    public boolean deleteGroup(Long id) {
        return groupRepository.findById(id).map(group -> {
            groupRepository.delete(group);
            return true;
        }).orElse(false);
    }
}
