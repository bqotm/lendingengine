package com.peerlender.lendingengine.domain.exception;

public class LoanNotFoundException extends RuntimeException{
    //nothing is added, so an intruder doesn't bruteforce the endpoint to tell him
    //what's there and what's not on the database

}
