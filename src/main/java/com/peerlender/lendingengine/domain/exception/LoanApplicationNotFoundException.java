package com.peerlender.lendingengine.domain.exception;

public class LoanApplicationNotFoundException extends  RuntimeException{

    public LoanApplicationNotFoundException(long loanAppId){
        super("loan application with id "+loanAppId+" not found");
    }
}
