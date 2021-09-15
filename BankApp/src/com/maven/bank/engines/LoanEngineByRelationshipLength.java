package com.maven.bank.engines;

import com.maven.bank.datastore.AccountType;
import com.maven.bank.datastore.CustomerRepo;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.Customer;
import com.maven.bank.exceptions.MavenBankException;
import com.maven.bank.exceptions.MavenBankLoanException;
import com.maven.bank.services.AccountServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class LoanEngineByRelationshipLength implements LoanEngine{

    @Override
    public BigDecimal calculateAmountAutoApproved(Customer customer, Account accountSeekingLoan) throws MavenBankLoanException {
        validateLoanRequest (accountSeekingLoan);

        LocalDateTime openingDate =accountSeekingLoan.getStartDate ();
        System.out.println (openingDate.getMonth () );
        LocalDateTime currentDate = LocalDateTime.now ();
        long period = ChronoUnit.MONTHS.between (openingDate.toLocalDate (), currentDate.toLocalDate ());
        if (period < 0) {
            throw new MavenBankLoanException ( "Loan cannot be approved this period" );
        }
        System.out.println (period );
        BigDecimal totalCustomerBalance = BigDecimal.ZERO;
        for(Account customerAccount : customer.getAccounts ( )) {
            totalCustomerBalance = totalCustomerBalance.add (customerAccount.getBalance ( ));
        }
        if (period > 23){
                return totalCustomerBalance.multiply (BigDecimal.valueOf (0.1));
        }else if(period >= 18){
            return totalCustomerBalance.multiply (BigDecimal.valueOf (0.08));
        }else if(period >= 12){
            return totalCustomerBalance.multiply (BigDecimal.valueOf (0.06));
        }else if(period >= 6){
            return totalCustomerBalance.multiply (BigDecimal.valueOf (0.04));
        }else if(period >= 3){
            return totalCustomerBalance.multiply (BigDecimal.valueOf (0.02));
        }else{
         return  BigDecimal.valueOf (0);
        }
    }


}
