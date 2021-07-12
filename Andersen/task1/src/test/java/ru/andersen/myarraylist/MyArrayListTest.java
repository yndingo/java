package ru.andersen.myarraylist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.java.Log;

@Log
public class MyArrayListTest {
    
    public MyArrayListTest() {
    }
    /**
     * Test of size method, of class MyArrayList.
     */
    @Test
    public void testSize() {
        log.info("size");
        MyArrayList instance = new MyArrayList();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
    }
    @Test
    public void testSize2() {
        log.info("size2");
        MyArrayList instance = new MyArrayList();
        instance.add("1");
        int expResult = 1;
        int result = instance.size();
        assertEquals(expResult, result);
    }
    /**
     * Test of rangeCheck method, of class MyArrayList.
     */
    @Test
    public void testRangeCheck() {
        log.info("rangeCheck");
        int index = 0;
        MyArrayList instance = new MyArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            instance.rangeCheck(index);
        });
    }
    @Test
    public void testRangeCheck2() {
        log.info("rangeCheck2");
        int index = 0;
        MyArrayList instance = new MyArrayList();
        instance.add("1");
        instance.rangeCheck(index);
    }

    /**
     * Test of get method, of class MyArrayList.
     */
    @Test
    public void testGet() {
        log.info("get");        
        int index = 0;
        MyArrayList instance = new MyArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Object result = instance.get(index);
        });
    }
    @Test
    public void testGet2() {
        log.info("get2");
        int index = 0;
        MyArrayList instance = new MyArrayList();
        instance.add("1");
        Object expResult = "1";
        Object result = instance.get(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class MyArrayList.
     */
    @Test
    public void testAdd() {
        log.info("add");
        Object e = null;
        MyArrayList instance = new MyArrayList();
        boolean expResult = true;
        boolean result = instance.add(e);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class MyArrayList.
     */
    @Test
    public void testEquals() {
        log.info("equals");
        Object o = null;
        MyArrayList instance = new MyArrayList();
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class MyArrayList.
     */
    @Test
    public void testHashCode() {
        log.info("hashCode");
        MyArrayList instance = new MyArrayList();
        int expResult = 1;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class MyArrayList.
     */
    @Test
    public void testToString() {
        log.info("toString");
        MyArrayList instance = new MyArrayList();
        String expResult = "[]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
