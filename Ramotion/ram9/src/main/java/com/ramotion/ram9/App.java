/*
Пифагорейская тройка — это набор из трех натуральных чисел a b c, для которых
а2 + Ь2 = с2
Например, 32 + 42 = 9 + 16 = 25 = 52.
Существует ровно одна пифагорейская тройка, для которой a + b + c = 1000.
Найдите произведение abc.
 */
package com.ramotion.ram9;

public class App {
    public static void main(String args[]){

        int a=0, b=0, c=0;
        boolean found = true;

        for (a = 1; found; a++)
        {
            for (b = 1; b<=1000; b++)
            {
                for (c = 1; c<=1000; c++)
                {
                    if ( (a*a + b*b == c*c) && ( a+b+c ==1000) ){
                        //System.out.println(a + " " + b + " " + c);
                        System.out.println(a * b * c);
                        found = false;
                    }
                        
               }
            }
        }
        
        
        
        
    }
}
