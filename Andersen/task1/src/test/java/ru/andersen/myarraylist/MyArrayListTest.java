/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.andersen.myarraylist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Adminlocal
 */
public class MyArrayListTest {
    
    public MyArrayListTest() {
    }

    /**
     * Test of size method, of class MyArrayList.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        MyArrayList instance = new MyArrayList();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
    }
    @Test
    public void testSize2() {
        System.out.println("size2");
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
        System.out.println("rangeCheck");
        int index = 0;
        MyArrayList instance = new MyArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            instance.rangeCheck(index);
        });
    }
    @Test
    public void testRangeCheck2() {
        System.out.println("rangeCheck");
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
        System.out.println("get");
        int index = 0;
        MyArrayList instance = new MyArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Object result = instance.get(index);
        });
    }
    @Test
    public void testGet2() {
        System.out.println("get");
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
        System.out.println("add");
        Object e = null;
        MyArrayList instance = new MyArrayList();
        boolean expResult = true;
        boolean result = instance.add(e);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSize method, of class MyArrayList.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        MyArrayList instance = new MyArrayList();
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getModCount method, of class MyArrayList.
     */
    @Test
    public void testGetModCount() {
        System.out.println("getModCount");
        MyArrayList instance = new MyArrayList();
        int expResult = 0;
        int result = instance.getModCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of setElementData method, of class MyArrayList.
     */
    @Test
    public void testSetElementData() {
        System.out.println("setElementData");
        Object[] elementData = null;
        MyArrayList instance = new MyArrayList();
        instance.setElementData(elementData);
    }

    /**
     * Test of setSize method, of class MyArrayList.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int size = 12;
        MyArrayList instance = new MyArrayList();
        instance.setSize(size);
    }

    /**
     * Test of setModCount method, of class MyArrayList.
     */
    @Test
    public void testSetModCount() {
        System.out.println("setModCount");
        int modCount = 0;
        MyArrayList instance = new MyArrayList();
        instance.setModCount(modCount);
    }

    /**
     * Test of equals method, of class MyArrayList.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        MyArrayList instance = new MyArrayList();
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of canEqual method, of class MyArrayList.
     */
    @Test
    public void testCanEqual() {
        System.out.println("canEqual");
        Object other = null;
        MyArrayList instance = new MyArrayList();
        boolean expResult = false;
        boolean result = instance.canEqual(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class MyArrayList.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        MyArrayList instance = new MyArrayList();
        int expResult = 205380;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class MyArrayList.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        MyArrayList instance = new MyArrayList();
        String expResult = "MyArrayList(elementData=[], size=0, modCount=0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
