package com.peerlender.lendingengine.application;


import com.peerlender.lendingengine.application.model.LoanRepaymentRequest;
import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.application.service.TokenValidationService;
import com.peerlender.lendingengine.domain.model.Loan;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.Status;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import com.peerlender.lendingengine.domain.service.LoanApplicationAdapter;
import com.peerlender.lendingengine.domain.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;

    @Autowired
    public LoanController(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository, LoanApplicationAdapter loanApplicationAdapter, LoanService loanService, TokenValidationService tokenValidationService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository=userRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
    }

    @PostMapping(value = "/loan/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest, HttpServletRequest request){

        User borrower=tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        LoanApplication loanApplication=loanApplicationAdapter.transform(loanRequest, borrower);
        loanApplicationRepository.save(loanApplication);
    }

    @GetMapping(value = "/loan/requests")
    public List<LoanApplication> findAllLoanApplications(HttpServletRequest request){

        tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));

        return loanApplicationRepository.findAllByStatusEquals(Status.ONGOING);
    }

    @GetMapping(value="/loan/{status}/borrowed")
    public List<Loan> findBorrowedLoans(@RequestHeader String authorization, @PathVariable Status status){
        User borrower=tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllByBorrowedLoans(borrower, status);
    }

    @GetMapping(value="/loan/{status}/lent")
    public List<Loan> findLentLoans(@RequestHeader String authorization, @PathVariable Status status){
        User lender=tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllByLentLoans(lender, status);
    }

    @PostMapping(value = "/loan/accept/{loanApplicationId}")
    public void acceptLoan(@PathVariable final String loanApplicationId,
                           HttpServletRequest request){
        User lender=tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));

        loanService.acceptLoan(lender.getUsername(), Long.parseLong(loanApplicationId));

    }

    @PostMapping(value = "/loan/repay")
    public void repayLoan(@RequestBody LoanRepaymentRequest request, @RequestHeader String authorization){
        User borrower=tokenValidationService.validateTokenAndGetUser(authorization);
        loanService.repayLoan(request.getAmount(), request.getLoanId(), borrower);
    }

    @GetMapping(value = "/loans")
    public List<Loan> getLoans(){
        return loanService.getLoans();
    }

}
