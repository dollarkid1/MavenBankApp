package com.maven.bank.services;

import com.maven.bank.entities.Account;
import com.maven.bank.entities.Customer;
import com.maven.bank.entities.LoanRequest;
import com.maven.bank.exceptions.MavenBankLoanException;

public interface LoanService {
    public LoanRequest approveLoan(Account loanAccount) throws MavenBankLoanException;
    public LoanRequest approveLoan(Account loanService, Customer customer) throws  MavenBankLoanException;
}
