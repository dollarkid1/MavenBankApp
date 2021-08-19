package com.maven.bank.services;

import com.maven.bank.datastore.LoanRequestStatus;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.Customer;
import com.maven.bank.entities.LoanRequest;
import com.maven.bank.exceptions.MavenBankLoanException;

import java.math.BigDecimal;

public class LoanServiceImpl implements LoanService{
    @Override
    public LoanRequest approveLoan(Account accountSeekingLoan) throws MavenBankLoanException {
        if (accountSeekingLoan == null){
            throw new MavenBankLoanException ( "An account is required to process loan" );
        }
        if (accountSeekingLoan.getAccountLoan () == null){
            throw new MavenBankLoanException ( "No loan provided for processing" );
        }
        LoanRequest theLoanRequest = accountSeekingLoan.getAccountLoan ();
        theLoanRequest.setStatus (decideOnLoan (accountSeekingLoan));

        return theLoanRequest;
    }

    @Override
    public LoanRequest approveLoan(Account accountSeekingLoan, Customer customer) throws MavenBankLoanException {
        return approveLoan (accountSeekingLoan);
    }

    public LoanRequestStatus decideOnLoan(Account accountSeekingLoan) throws MavenBankLoanException {
        LoanRequestStatus decision = decideOnLoanWithAccountBalance (accountSeekingLoan);

        return decision;
    }

    public LoanRequestStatus decideOnLoanWithAccountBalance(Account accountSeekingLoan) throws MavenBankLoanException {
        LoanRequestStatus decision = LoanRequestStatus.PENDING;
        LoanRequest theLoanRequest = accountSeekingLoan.getAccountLoan ();
        BigDecimal bankAccountBalancePercentage = BigDecimal.valueOf (0.2);

        BigDecimal loanAmountApprovedAutomatically = accountSeekingLoan.getBalance ().multiply (bankAccountBalancePercentage);
        if (theLoanRequest.getLoanAmount().compareTo(loanAmountApprovedAutomatically) < BigDecimal.ZERO.intValue ()){
            decision = LoanRequestStatus.APPROVED;
        }

        return decision;
    }

    public LoanRequestStatus decideOnLoanWithLengthOfRelationship(Account accountSeekingLoan) throws MavenBankLoanException {
        LoanRequestStatus decision = LoanRequestStatus.PENDING;
        LoanRequest theLoanRequest = accountSeekingLoan.getAccountLoan ();
        BigDecimal bankAccountBalancePercentage = BigDecimal.valueOf (0.2);

        BigDecimal loanAmountApprovedAutomatically = accountSeekingLoan.getBalance ().multiply (bankAccountBalancePercentage);
        if (theLoanRequest.getLoanAmount().compareTo(loanAmountApprovedAutomatically) < BigDecimal.ZERO.intValue ()){
            decision = LoanRequestStatus.APPROVED;
        }

        return decision;
    }

}
