package com.maven.bank;

import com.maven.bank.datastore.CustomerRepo;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.Customer;
import com.maven.bank.entities.SavingsAccount;
import com.maven.bank.exceptions.MavenBankTransactionException;
import com.maven.bank.services.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AccountTest {

    Customer john;
    Account johnSavingsAccount;
    @BeforeEach
    void setUp(){
        john = new Customer();
        johnSavingsAccount = new SavingsAccount (  );

    }

    @Test
    void openAccount() throws MavenBankTransactionException {
        john.setBvn (BankService.generateBvn ());
        john.getEmail ("john@doe.com");
        john.setFirstName ("john");
        john.setSurname ("doe");
        john.setPhone ("12345678901");

        johnSavingsAccount.setAccountNumber(BankService.generateAccountNumber ());
        johnSavingsAccount.setBalance (new BigDecimal (5000));
        johnSavingsAccount.setAccountPin ("1470");
        john.getAccounts().add(johnSavingsAccount);

        assertFalse(CustomerRepo.getCustomers().isEmpty ());

    }

}
