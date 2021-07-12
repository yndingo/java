package ru.andersen.myarraylist.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExceptionLengthMoreThan20 extends RuntimeException{
    private final String msg;   
}