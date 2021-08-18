package com.maven.bank.services;

import com.maven.bank.datastore.LoanStatus;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.Loan;
import com.maven.bank.exceptions.MavenBankLoanException;
import com.maven.bank.exceptions.MavenBankTransactionException;

import java.math.BigDecimal;

public class LoanServiceImpl implements LoanService{
    @Override
    public Loan approveLoan(Account accountSeekingLoan) throws MavenBankLoanException {
        if (accountSeekingLoan == null){
            throw new MavenBankLoanException ( "An account is required to process loan" );
        }
        if (accountSeekingLoan.getAccountLoan () == null){
            throw new MavenBankLoanException ( "No loan provided for processing" );
        }
        Loan theLoan = accountSeekingLoan.getAccountLoan ();
        theLoan.setStatus (decideOnLoan (accountSeekingLoan));

        return theLoan;
    }

    public LoanStatus decideOnLoan(Account accountSeekingLoan) throws MavenBankLoanException {

        LoanStatus decision = LoanStatus.PENDING;
        Loan theLoan = accountSeekingLoan.getAccountLoan ();
        BigDecimal bankAccountBalancePercentage = BigDecimal.valueOf (0.2);

        BigDecimal loanAmountApprovedAutomatically = accountSeekingLoan.getBalance ().multiply (bankAccountBalancePercentage);
        if (theLoan.getLoanAmount().compareTo(loanAmountApprovedAutomatically) < BigDecimal.ZERO.intValue ()){
            decision = LoanStatus.APPROVED;
        }

        return decision;
    }

}
