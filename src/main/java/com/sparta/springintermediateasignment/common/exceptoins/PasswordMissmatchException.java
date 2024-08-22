package com.sparta.springintermediateasignment.common.exceptoins;

public class PasswordMissmatchException extends RuntimeException {

    public PasswordMissmatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
