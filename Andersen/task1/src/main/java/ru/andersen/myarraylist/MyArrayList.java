package ru.andersen.myarraylist;

import java.util.AbstractList;
import java.util.Arrays;
import lombok.Data;

import java.util.Iterator;

class MyException extends RuntimeException{
    public MyException(String errorMessage) {
        super(errorMessage);
    }    
}

@Data
public class MyArrayList extends AbstractList{

    Object[] elementData;
    private int size;
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    protected int modCount = 0;
    
    MyArrayList() {
		elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	}    
    
    private void ensureCapacity(int minCapacity) {
	if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
	}
	if (minCapacity - elementData.length > 0) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - minCapacity < 0) {
		newCapacity = minCapacity;
            }

            elementData = Arrays.copyOf(elementData, newCapacity);
	}
    }

    @Override
    public int size() {
	return size;
    }
    
    public void rangeCheck(int index) {
	if (index >= size) {
            throw new IndexOutOfBoundsException();
	}
    }
    
    @Override
    public Object get(int index) {
	rangeCheck(index);
	return elementData[index];
    }    
    
    @Override
    public boolean add(Object e){
	ensureCapacity(size + 1);
	elementData[size++] = e;
        if (size > 20) throw new MyException("Размер коллекции не может превышать 20");
	return true;
    }

}
