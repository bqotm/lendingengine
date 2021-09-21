package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


//Adapter class
@Component
public class LoanApplicationAdapter {

    private final UserRepository userRepository;

    @Autowired
    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanApplication transform(LoanRequest loanRequest, User borrower){
        Optional<User> user=userRepository.findById(borrower.getUsername());
        if(user.isPresent()){
            return new LoanApplication(loanRequest.getAmount(), user.get(),
                    loanRequest.getDaysToRepay(), loanRequest.getInterestRate());
        } else {
            throw new UserNotFoundException(borrower.getUsername());
        }
    }
}
