package com.expense_management.service.impl;

import com.expense_management.dto.LoandebtDTO;
import com.expense_management.entity.LoanDebt;
import com.expense_management.entity.User;
import com.expense_management.repository.LoanDebtRepository;
import com.expense_management.service.LoanDebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanDebtServiceImpl implements LoanDebtService {
    private final LoanDebtRepository loanDebtRepository;


    @Override
    public LoandebtDTO createLoanDebt(LoanDebt loanDebt, User user) {
        loanDebt.setUserId(user);
        loanDebtRepository.save(loanDebt);
        return LoandebtDTO.builder()
                .id(loanDebt.getId())
                .type(loanDebt.getType())
                .amount(loanDebt.getAmount())
                .creditorDebtor(loanDebt.getCreditorDebtor())
                .description(loanDebt.getDescription())
                .expirationDate(loanDebt.getExpirationDate())
                .build();
    }

    @Override
    public LoanDebt getLoanDebtById(Long id) {
        LoanDebt loanDebt = loanDebtRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("LoanDebt not found"));
        return LoanDebt.builder()
                .id(loanDebt.getId())
                .type(loanDebt.getType())
                .amount(loanDebt.getAmount())
                .creditorDebtor(loanDebt.getCreditorDebtor())
                .description(loanDebt.getDescription())
                .expirationDate(loanDebt.getExpirationDate())
                .build();
    }

    @Override
    public List<LoandebtDTO> getLoanDebtsByUser(User user) {
        List<LoanDebt> loanDebts = loanDebtRepository.findByUserId(user);
        return loanDebts.stream()
                .map(loanDebt -> new LoandebtDTO(
                        loanDebt.getId(),
                        loanDebt.getType(),
                        loanDebt.getCreditorDebtor(),
                        loanDebt.getDescription(),
                        loanDebt.getAmount(),
                        loanDebt.getExpirationDate()
                )).collect(Collectors.toList());
    }

    @Override
    public LoandebtDTO updateLoanDebt(Long id, LoanDebt LoanDebt) {
        LoanDebt existingLoanDebt = getLoanDebtById(id);
        existingLoanDebt.setType(LoanDebt.getType() != null ? LoanDebt.getType() : existingLoanDebt.getType());
        existingLoanDebt.setCreditorDebtor(LoanDebt.getCreditorDebtor() != null ? LoanDebt.getCreditorDebtor() : existingLoanDebt.getCreditorDebtor());
        existingLoanDebt.setDescription(LoanDebt.getDescription() != null ? LoanDebt.getDescription() : existingLoanDebt.getDescription());
        existingLoanDebt.setAmount(LoanDebt.getAmount() != 0 ? LoanDebt.getAmount() : existingLoanDebt.getAmount());
        existingLoanDebt.setExpirationDate(LoanDebt.getExpirationDate() != null ? LoanDebt.getExpirationDate() : existingLoanDebt.getExpirationDate());
        loanDebtRepository.save(existingLoanDebt);
        return LoandebtDTO.builder()
                .id(existingLoanDebt.getId())
                .expirationDate(existingLoanDebt.getExpirationDate())
                .description(existingLoanDebt.getDescription())
                .creditorDebtor(existingLoanDebt.getCreditorDebtor())
                .amount(existingLoanDebt.getAmount())
                .type(existingLoanDebt.getType())
                .build();
    }

    @Override
    public void deleteLoanDebt(Long id) {
        LoanDebt loanDebt = getLoanDebtById(id);
        loanDebtRepository.delete(loanDebt);
    }
}
