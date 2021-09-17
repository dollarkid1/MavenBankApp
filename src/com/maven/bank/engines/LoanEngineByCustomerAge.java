package com.maven.bank.engines;

import com.maven.bank.entities.Account;
import com.maven.bank.entities.Customer;
import com.maven.bank.exceptions.MavenBankLoanException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanEngineByCustomerAge implements LoanEngine{
    @Override
    public BigDecimal calculateAmountAutoApproved(Customer customer, Account accountSeekingLoan) throws MavenBankLoanException {
        BigDecimal loanAmountApprovedAutomatically = BigDecimal.ZERO;
        validateLoanRequest(customer, accountSeekingLoan);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(customer.getDateOfBirth(), today);
        if (age<=17 && age >=66){
            throw new MavenBankLoanException("Loan cannot be approved for given age bracket");
        }

        return loanAmountApprovedAutomatically;
    }

}
