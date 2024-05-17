package com.expense_management.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoandebtDTO {
    private Long id;
    private String type;
    private String creditorDebtor;
    private String description;
    private double amount;
    private Date expirationDate;
}
