//Напишите программу для проверки, является ли число простым
//тест Рабина-Миллера
package com.multicard.test1;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args){
        if (args.length > 0){            
            try{
                Integer integer = Integer.parseInt(args[0]);
                BigInteger bigInteger = BigInteger.valueOf(integer);
                boolean probablePrime = bigInteger.isProbablePrime((int) Math.log(integer));
                System.out.println(probablePrime);
            }
            catch(NumberFormatException e){
                System.out.println("eto ne chislo");
            }
        }
        else System.out.println("Nado peredat v pervom argymente chislo. Programma opredelit prostoe ono ili net");          
    }
}
