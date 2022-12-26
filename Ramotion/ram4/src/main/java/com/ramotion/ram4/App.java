/*
Палиндромное число одинаково читается в обоих случаях. Самый большой палиндром, составленный из произведения двух двузначных чисел, равен 9009 = 91 × 99.
Найдите наибольший палиндром, составленный из произведения двух трехзначных чисел.
 */
package com.ramotion.ram4;

public class App {
    public static void main(String args[]){

        int max = 0;
        int a = 0;
        int b = 0;

        for (int i = 100; i <= 999; i++)
        {
            for (int j = 10; j <= 999; j++)
            {
                int multiplication = i * j;
                String s = Integer.toString(multiplication);
                StringBuilder sb = new StringBuilder(s).reverse();
                if ( sb.toString().equals(s) )
                {
                    if (max < multiplication)
                    {
                        max = multiplication;
                        a = i;
                        b = j;
                    }
                }
            }
        }

        //System.out.println("Biggest palindrom: " + max + " get by multiplication " + a + " and " + b);
        System.out.println(max);
    }
    
}
