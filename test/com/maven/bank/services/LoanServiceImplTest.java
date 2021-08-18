package com.maven.bank.services;

import com.maven.bank.datastore.LoanStatus;
import com.maven.bank.datastore.LoanType;
import com.maven.bank.entities.Account;
import com.maven.bank.entities.Loan;
import com.maven.bank.exceptions.MavenBankException;
import com.maven.bank.exceptions.MavenBankLoanException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceImplTest {
    private Loan johnLoan;
    private LoanService loanService;
    private AccountService accountService;


    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl ( );
        loanService = new LoanServiceImpl ();
        johnLoan = new Loan ();
        johnLoan.setLoanAmount (BigDecimal.valueOf (5000000));
        johnLoan.setApplyDate (LocalDateTime.now());
        johnLoan.getInterestRate (0.1);
        johnLoan.setStatus (LoanStatus.NEW);
        johnLoan.setTenor (25);
        johnLoan.setTypeOfLoan (LoanType.SME);
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
        assertThrows (MavenBankLoanException.class, () -> loanService.approveLoan (null));
    }


    @Test
    void approveLoan(){
        try{
            Account johnCurrentsAccount = accountService.findAccount (0000110002);
            assertNull (johnCurrentsAccount.getAccountLoan ());
            johnCurrentsAccount.setAccountLoan (johnLoan);
            LoanStatus decision = accountService.applyForLoans (johnCurrentsAccount);
            Loan processedLoan = loanService.approveLoan (johnCurrentsAccount);
            assertNotNull (decision);
        } catch (MavenBankException e) {
            e.printStackTrace ( );
        }

    }
}