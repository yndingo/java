/*Напишите приложение Multiplication, которое после запуска выводит на экран таблицу умножения чисел до 10 и завершает работу. 
Числа разделить пробелом. (Вариант “со звёздочкой”: выровняйте их нужным количеством пробелов).
*/
package Task1;

public class Multiplication {
    
    public static void main(String args[]){
        for (int i = 1; i < 11; i++){
            if (i > 1){
                System.out.println();
                System.out.print("        ");
            }
            for (int j = 1; j < 11; j++)
                System.out.print(i*j + " ");
        }
    }
}
