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
@Table(name = "group_transaction")
public class GroupTransaction {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transactionId;
}
