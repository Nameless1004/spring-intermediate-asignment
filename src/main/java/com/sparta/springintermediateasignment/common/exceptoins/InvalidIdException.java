package com.sparta.springintermediateasignment.common.exceptoins;

public class InvalidIdException extends RuntimeException {

    public InvalidIdException(String repositoryName) {
        super(repositoryName + "에서 " + "해당 아이디를 찾을 수 없습니다.");
    }
}
