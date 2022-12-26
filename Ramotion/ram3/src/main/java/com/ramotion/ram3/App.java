//Простые делители числа 13195 равны 5, 7, 13 и 29.
//Какой самый большой простой делитель числа 600851475143?
package com.ramotion.ram3;

import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String args[]){

        ArrayList<Integer> al = new ArrayList<Integer>();
        long num = 600851475143L;
        int div = 2;
        
        for (div = 2; div <= num; div++){
            if (num % div == 0)
                al.add(div);
            while (num % div == 0)
                num /= div;
        }
        /*
        while(div*div <= num){
            if (num % div == 0){
                al.add(div);
                num /= div;
            }
            else div ++;
        }
        if (num > 1)
            al.add(num);
        */
        //System.out.println(al);
        System.out.println(Collections.max(al));

               
    }
}
