package com.maven.bank.entities;

import com.maven.bank.datastore.AccountType;
import com.maven.bank.exceptions.MavenBankTransactionException;

import java.math.BigDecimal;

public abstract class Account {
    private long accountNumber;
//    private AccountType type;
//    private AccountType typeOfAccount;
    private BigDecimal balance = BigDecimal.ZERO;
    private String pin;
    private static String accountPin;
    private Loan accountLoan;


    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

//    public AccountType getTypeOfAccount() {
//        return typeOfAccount;
//    }

//    public void setTypeOfAccount(AccountType typeOfAccount) {
//        this.typeOfAccount = typeOfAccount;
//    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static String getAccountPin() {
        return accountPin;
    }

    public void setAccountPin(String accountPin) throws MavenBankTransactionException {
        Account.accountPin = accountPin;
        validatePin (accountPin);
    }

    public void validatePin(String pin) throws MavenBankTransactionException {
        if (pin.length () != 4){
            throw new MavenBankTransactionException ( "invalid pin please enter the correct pin" );
        }
        if (!accountPin.equals (pin)){
            throw new MavenBankTransactionException ( "invalid pin please enter the correct pin" );
        }
    }

    public Loan getAccountLoan() {
        return accountLoan;
    }

    public void setAccountLoan(Loan accountLoan) {
        this.accountLoan = accountLoan;
    }
}
