package com.maven.bank.services;

import com.maven.bank.Account;
import com.maven.bank.Customer;
import com.maven.bank.datastore.AccountType;
import com.maven.bank.datastore.CustomerRepo;
import com.maven.bank.datastore.TransactionType;
import com.maven.bank.exceptions.MavenBankException;
import com.maven.bank.exceptions.MavenBankInsufficientFundsException;
import com.maven.bank.exceptions.MavenBankTransactionException;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService{
    @Override
    public long openAccount(Customer theCustomer, AccountType type) throws MavenBankException {
        if (theCustomer == null || type == null){
            throw new MavenBankException ( "customer and account type required to open new account" );
        }
        if (accountTypeExists (theCustomer, type )){
            throw new MavenBankException ( "Customer already has the requested account type " );
        }

        Account newAccount = new Account ();
        newAccount.setAccountNumber (BankService.generateAccountNumber ());
        newAccount.setTypeOfAccount (type);
        theCustomer.getAccounts ().add (newAccount);
        CustomerRepo.getCustomers ().put (theCustomer.getBvn ( ), theCustomer);
        return  newAccount.getAccountNumber ();
    }

    @Override
    public BigDecimal deposit(BigDecimal amount, long accountNumber) throws MavenBankException {
        Account account = findAccount (accountNumber);
        TransactionType typeOfTransaction = TransactionType.DEPOSIT;

        validateTransaction (amount, account, typeOfTransaction);

        BigDecimal newBalance = BigDecimal.ZERO;
        newBalance = account.getBalance ().add (amount);
        account.setBalance (newBalance);
        return newBalance;
    }

    @Override
    public Account findAccount(long accountNumber) throws MavenBankException {
        Account foundAccount = null;
        boolean accountFound = false;

        for(Customer customer : CustomerRepo.getCustomers ().values ()){
            for (Account anAccount : customer.getAccounts ()) {
                if (anAccount.getAccountNumber () == accountNumber){
                    foundAccount = anAccount;
                    accountFound = true;
                    break;
                }
            }
            if (accountFound){
                break;
            }
        }
        return foundAccount;
    }

    @Override
    public Account findAccount(Customer customer, long accountNumber) throws MavenBankException {
        return null;
    }

    @Override
    public BigDecimal withdraw(BigDecimal amount, long accountNumber, String pin) throws MavenBankException {
        TransactionType typeOfTransaction = TransactionType.WITHDRAWAL;
        Account account = findAccount (accountNumber);
        validateTransaction (amount, account, typeOfTransaction );
        BigDecimal newBalance = debitAccount (amount, accountNumber);

        return newBalance;
    }

    public BigDecimal debitAccount(BigDecimal amount, long accountNumber) throws MavenBankException {

        Account theAccount = findAccount (accountNumber);
       BigDecimal newBalance =  theAccount.getBalance ().subtract (amount);
        theAccount.setBalance (newBalance);
        return newBalance;

    }

    private void validateTransaction(BigDecimal amount, Account account, TransactionType typeOfTransaction) throws MavenBankException {
        switch (typeOfTransaction){
            case DEPOSIT -> {
                if (amount.compareTo (BigDecimal.ZERO) < 0){
                    throw new MavenBankException ( "Transaction amount cannot be negative" );
                }

                if (account == null){
                    throw new MavenBankTransactionException ( "Transaction account not found" );
                }
            }

            case WITHDRAWAL -> {
                if (amount.compareTo (BigDecimal.ZERO) < BigDecimal.ONE.intValue ()){
                    throw new MavenBankTransactionException ( "Withdrawal amount cannot be Negative!!" );
                }

                if (account == null){
                    throw new MavenBankTransactionException ( "Withdrawal account not found" );
                }
                if (amount.compareTo (account.getBalance ()) > 0){
                    throw new MavenBankInsufficientFundsException ( "Insufficient Funds" );
                }
            }
        }
    }

    private boolean accountTypeExists(Customer aCustomer, AccountType type){
        boolean accountTypeExists = false;
        for(Account customerAccount : aCustomer.getAccounts ()){
            if (customerAccount.getTypeOfAccount () == type){
                accountTypeExists = true;
                break;
            }
        }
        return accountTypeExists;
    }


}
