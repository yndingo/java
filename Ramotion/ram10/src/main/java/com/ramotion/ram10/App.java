/*
Сумма простых чисел меньше 10 равна 2 + 3 + 5 + 7 = 17.
Найдите сумму всех простых чисел меньше двух миллионов.
 */
package com.ramotion.ram10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    
    private static List<Integer> reshetoEratosfena(int sieveSize){
        
        boolean[] sieve;
        sieve = new boolean[sieveSize];
        Arrays.fill(sieve, true);
        sieve[0] = false;
        sieve[1] = false;

        for (int i = 2; i < sieve.length; i++) {
            if (sieve[i]) {
                for (int j = 2; i * j < sieve.length; j++) {
                    sieve[i * j] = false;
                }
            }
        }
        
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i < sieve.length; i++) {
            if (sieve[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
    
    public static void main(String args[]){

        int num = 2000000;
        List<Integer> nums = reshetoEratosfena(num);
        long sum = nums.stream().mapToLong(Integer::intValue).sum();
        System.out.println(sum);
    
    }
}
