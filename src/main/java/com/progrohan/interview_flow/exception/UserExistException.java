package com.progrohan.interview_flow.exception;

public class UserExistException extends RuntimeException {

    public UserExistException(String message) {
        super(message);
    }
}
