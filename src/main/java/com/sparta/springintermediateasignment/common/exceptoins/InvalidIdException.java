package com.sparta.springintermediateasignment.common.exceptoins;

public class InvalidIdException extends RuntimeException {

    public InvalidIdException(String repositoryName, String name, Long id) {
        super(repositoryName + "에서 " + "[ " + name + " ] " + "아이디( " + id + " )를 찾을 수 없습니다.");
    }
}
