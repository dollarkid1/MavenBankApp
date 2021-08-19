package com.maven.bank.services;

import com.maven.bank.entities.Account;
import com.maven.bank.entities.Request;
import com.maven.bank.exceptions.MavenBankLoanException;

public interface LoanService {
    public Request approveLoan(Account loanAccount) throws MavenBankLoanException;
}
