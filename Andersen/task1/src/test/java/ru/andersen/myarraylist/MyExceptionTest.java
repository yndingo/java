/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.andersen.myarraylist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.andersen.myarraylist.exception.ExceptionLengthMoreThan20;

/**
 *
 * @author Adminlocal
 */
public class MyExceptionTest {
    
    public MyExceptionTest() {
    }

    @Test
    public void testException() {
        MyArrayList instance = new MyArrayList();
        for (int i=1;i<21;i++) instance.add("1");
        assertThrows(ExceptionLengthMoreThan20.class, () -> {
            instance.add("1");
        });
    }
    
}
