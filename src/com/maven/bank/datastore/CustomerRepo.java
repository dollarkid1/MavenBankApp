package com.maven.bank.datastore;

import com.maven.bank.entities.Account;
import com.maven.bank.entities.CurrentAccount;
import com.maven.bank.entities.Customer;
import com.maven.bank.entities.SavingsAccount;
import com.maven.bank.services.BankService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CustomerRepo {
    private static Map<Long, Customer> customers = new HashMap<> ();

    public static  Map<Long, Customer> getCustomers() {
        return customers;
    }

    private void setCustomers(Map<Long, Customer> customers) {
        this.customers = customers;
    }

    static{
        reset ();
    }

    public static void reset() {
        Customer john = new Customer ();
        john.setBvn (BankService.generateBvn ());
        john.getEmail ("john@doe.com");
        john.setFirstName ("john");
        john.setSurname ("doe");
        john.setPhone ("12345678901");
        Account johnSavingsAccount = new SavingsAccount  (1000110001);
        john.setRelationshipStartDate (johnSavingsAccount.getStartDate ());

        john.getAccounts ().add (johnSavingsAccount);
        Account johnCurrentAccount = new CurrentAccount ( 1000110002, new BigDecimal (50000000));
        john.getAccounts ().add (johnCurrentAccount);
        customers.put (john.getBvn (), john);

        Customer jane = new Customer ();
        jane.setBvn (BankService.generateBvn ());
        jane.getEmail ("jane@blackie.com");
        jane.setFirstName ("jane");
        jane.setSurname ("blackie");
        jane.setPhone ("90876543211");

        Account janeSavingsAccount = new SavingsAccount ( 1000110003 );
        jane.setRelationshipStartDate (janeSavingsAccount.getStartDate ());
        jane.getAccounts ().add (janeSavingsAccount);
        customers.put (jane.getBvn (), jane);
    }
}
