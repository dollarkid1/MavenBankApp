package com.maven.bank.services;

import com.maven.bank.entities.Account;
import com.maven.bank.entities.Customer;
import com.maven.bank.entities.LoanRequest;
import com.maven.bank.exceptions.MavenBankLoanException;

public interface LoanService {
    LoanRequest approveLoanRequest(Account loanAccount) throws MavenBankLoanException;
    LoanRequest approveLoanRequest(Account loanService, Customer customer) throws  MavenBankLoanException;
}
