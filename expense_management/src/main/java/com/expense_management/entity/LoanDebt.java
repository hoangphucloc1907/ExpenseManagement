package com.expense_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loandebt")
public class LoanDebt {
    @Id
    @GeneratedValue
    private int id;
    private String type;
    private String creditorDebtor;
    private String description;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private LocalDate expirationDate;
}
