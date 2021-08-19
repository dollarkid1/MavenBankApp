package com.maven.bank.services;

import com.maven.bank.datastore.CustomerRepo;
import com.maven.bank.datastore.LoanRequestStatus;
import com.maven.bank.datastore.LoanType;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.CurrentAccount;
import com.maven.bank.entities.Customer;
import com.maven.bank.entities.LoanRequest;
import com.maven.bank.exceptions.MavenBankException;
import com.maven.bank.exceptions.MavenBankLoanException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceImplTest {
    private LoanRequest johnLoanRequest;
    private LoanService loanService;
    private AccountService accountService;


    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl ( );
        loanService = new LoanServiceImpl ();
        johnLoanRequest = new LoanRequest ();
        johnLoanRequest.setApplyDate (LocalDateTime.now());
        johnLoanRequest.getInterestRate (0.1);
        johnLoanRequest.setStatus (LoanRequestStatus.NEW);
        johnLoanRequest.setTenor (25);
        johnLoanRequest.setTypeOfLoan (LoanType.SME);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void approveLoanRequestWithNullAccount(){
        assertThrows (MavenBankLoanException.class, () -> loanService.approveLoanRequest (null));
    }

    @Test
    void approveLoanRequestWithNullLoan(){
        CurrentAccount accountWithoutLoan = new CurrentAccount (  );
        assertThrows (MavenBankLoanException.class, () -> loanService.approveLoanRequest (accountWithoutLoan));
    }


    @Test
    void approveLoanRequestWithAccountBalance(){
        try{
            Account johnCurrentAccount = accountService.findAccount (1000110002);
            assertNull (johnCurrentAccount.getAccountLoanRequest ());
            johnLoanRequest.setLoanAmount (BigDecimal.valueOf (9000000));
            johnCurrentAccount.setAccountLoanRequest (johnLoanRequest);

            LoanRequest processedLoanRequest = loanService.approveLoanRequest (johnCurrentAccount);
            assertEquals (LoanRequestStatus.APPROVED, processedLoanRequest.getStatus ());
        } catch (MavenBankException e) {
            e.printStackTrace ( );
        }

    }

    @Test
    void approveLoanRequestWithAccountBalanceAndHighLoanRequestAmount(){
        try{
            Account johnCurrentAccount = accountService.findAccount (1000110002);
            assertNull (johnCurrentAccount.getAccountLoanRequest ());
            johnLoanRequest.setLoanAmount (BigDecimal.valueOf (90000000));
            johnCurrentAccount.setAccountLoanRequest (johnLoanRequest);

            LoanRequest processedLoanRequest = loanService.approveLoanRequest (johnCurrentAccount);
            assertEquals (LoanRequestStatus.PENDING, processedLoanRequest.getStatus ());
        } catch (MavenBankException e) {
            e.printStackTrace ( );
        }

    }

    @Test
    void approveLoanRequestWithLengthOfRelationship(){
        try{
            Account johnSavingsAccount = accountService.findAccount (1000110001);
            Optional<Customer> optionalCustomer = CustomerRepo.getCustomers ().values ().stream ().findFirst ();
            Customer john = optionalCustomer.isPresent () ? optionalCustomer.get ( ) : null;
            assertNotNull (john);
            john.setRelationshipStartDate (johnSavingsAccount.getStartDate ().minusYears (2));
            johnLoanRequest.setLoanAmount (BigDecimal.valueOf (3000000));
            johnSavingsAccount.setAccountLoanRequest (johnLoanRequest);
//            johnSavingsAccount.getStartDate ();

        }catch (MavenBankException ex){
            ex.printStackTrace ();
        }

    }
}