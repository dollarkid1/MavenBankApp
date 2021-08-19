package com.maven.bank.services;

import com.maven.bank.datastore.LoanStatus;
import com.maven.bank.datastore.LoanType;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.CurrentAccount;
import com.maven.bank.entities.Request;
import com.maven.bank.exceptions.MavenBankException;
import com.maven.bank.exceptions.MavenBankLoanException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceImplTest {
    private Request johnRequest;
    private LoanService loanService;
    private AccountService accountService;


    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl ( );
        loanService = new LoanServiceImpl ();
        johnRequest = new Request ();
        johnRequest.setLoanAmount (BigDecimal.valueOf (9000000));
        johnRequest.setApplyDate (LocalDateTime.now());
        johnRequest.getInterestRate (0.1);
        johnRequest.setStatus (LoanStatus.NEW);
        johnRequest.setTenor (25);
        johnRequest.setTypeOfLoan (LoanType.SME);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void approveLoanWithNullAccount(){
        assertThrows (MavenBankLoanException.class, () -> loanService.approveLoan (null));
    }

    @Test
    void approveLoanWithNullLoan(){
        CurrentAccount accountWithoutLoan = new CurrentAccount (  );
        assertThrows (MavenBankLoanException.class, () -> loanService.approveLoan(accountWithoutLoan));
    }


    @Test
    void approveLoan(){
        try{
            Account johnCurrentAccount = accountService.findAccount (1000110002);
            assertNull (johnCurrentAccount.getAccountLoan ());
            johnCurrentAccount.setAccountLoan (johnRequest);

            Request processedRequest = loanService.approveLoan (johnCurrentAccount);
            assertEquals (LoanStatus.APPROVED, processedRequest.getStatus ());
        } catch (MavenBankException e) {
            e.printStackTrace ( );
        }

    }
}