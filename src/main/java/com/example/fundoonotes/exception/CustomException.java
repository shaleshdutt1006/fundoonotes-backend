package com.example.fundoonotes.exception;

//Custom CustomException for GoogleKeepApplication

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        //super keyword used to give message to the runtime CustomException class
        super(message);
    }
}
