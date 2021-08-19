package com.maven.bank.services;

import com.maven.bank.datastore.LoanStatus;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.Request;
import com.maven.bank.exceptions.MavenBankLoanException;

import java.math.BigDecimal;

public class LoanServiceImpl implements LoanService{
    @Override
    public Request approveLoan(Account accountSeekingLoan) throws MavenBankLoanException {
        if (accountSeekingLoan == null){
            throw new MavenBankLoanException ( "An account is required to process loan" );
        }
        if (accountSeekingLoan.getAccountLoan () == null){
            throw new MavenBankLoanException ( "No loan provided for processing" );
        }
        Request theRequest = accountSeekingLoan.getAccountLoan ();
        theRequest.setStatus (decideOnLoan (accountSeekingLoan));

        return theRequest;
    }

    public LoanStatus decideOnLoan(Account accountSeekingLoan) throws MavenBankLoanException {
        LoanStatus decision = LoanStatus.PENDING;
        Request theRequest = accountSeekingLoan.getAccountLoan ();
        BigDecimal bankAccountBalancePercentage = BigDecimal.valueOf (0.2);

        BigDecimal loanAmountApprovedAutomatically = accountSeekingLoan.getBalance ().multiply (bankAccountBalancePercentage);
        if (theRequest.getLoanAmount().compareTo(loanAmountApprovedAutomatically) < BigDecimal.ZERO.intValue ()){
            decision = LoanStatus.APPROVED;
        }

        return decision;
    }

}
