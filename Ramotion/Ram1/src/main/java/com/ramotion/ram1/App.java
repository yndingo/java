//Если мы перечислим все натуральные числа до 10, кратные 3 или 5, мы получим 3, 5, 6 и 9. Сумма этих кратных равна 23.
//Найдите сумму всех чисел, кратных 3 или 5 меньше 1000.
package com.ramotion.ram1;

public class App {
    public static void main(String[] args){        
        int num = 1000;
        int sum = 0;
        
        num--;
        while (num != 0) {
            //Суммирование цифр числа в случае кратности трем
 
            if (num % 3 == 0) {
                sum += num;
            }
            else if (num % 5 == 0) {
                sum += num;
            }            
            num--;
        }
        System.out.println(sum);
    }
    
}
