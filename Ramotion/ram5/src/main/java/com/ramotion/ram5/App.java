/*
2520 — это наименьшее число, которое можно разделить на каждое из чисел от 1 до 10 без остатка.
Какое наименьшее положительное число делится без остатка на все числа от 1 до 20?
 */
package com.ramotion.ram5;

public class App {
    //Greatest Common Divisor
    private static long gcd(long m, long n)
    {
        while( (m > 0) && (n > 0) ) 
            if (m < n) 
                n %= m; 
            else m %= n;
        return (m == 0L) ? n : m;
    }
    //Lowest Common Divisor
    private static long lcm(long m, long n)
    {
        return (m/gcd(m,n))*n;
    }

    public static void main(String args[]){
       
        long Res = 1;
        for(int i = 2; i <= 20; ++i)
        {
            Res = lcm(Res,i);
        }

    System.out.println(Res);
    }
}
