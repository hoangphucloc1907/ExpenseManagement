package com.expense_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_member")
public class GroupMember {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private String status;
}
