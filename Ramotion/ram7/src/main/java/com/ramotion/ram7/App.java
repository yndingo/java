/*
Перечислив первые шесть простых чисел: 2, 3, 5, 7, 11 и 13, мы увидим, что 6-е простое число равно 13.
Что такое 10 001-е простое число?

натуральное число называется простым, если у него ровно два делителя — единица и само число
для проверки числа n на простоту достаточно проверить остаток от деления n на все простые числа i < n, для которых i <= n / i
 */
package com.ramotion.ram7;

public class App {
    private static boolean is_prime(final int num){     
        for (int i = 1; i < num/2; i += 2 )
        //for (int i = 1; i < num; i ++ )
            if (num % i == 0 && i != 1)
                return(false);
        return (true);
}
    public static void main(String args[]){

        int ans = 1;  
        int i =0;
        
        while( true ) {
            if( is_prime( ans ) ) {
                if( ++i == 10001 ) {
                    break;
                }
            }
            ans += 2;
        }   
        System.out.println(ans);
    }
}
