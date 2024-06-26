package com.expense_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loandebt")
public class LoanDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String creditorDebtor;
    private String description;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private Date expirationDate;
}
