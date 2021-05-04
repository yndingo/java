/*
Напишите приложение EchoChamber, которое после запуска считывает строки, вводимые пользователем в консоль (для этого можно использовать, например, java.util.Scanner), 
и сохраняет их в коллекции (какой-нибудь реализации java.util.List), пока пользователь не введёт пустую строку.
После ввода пользователем пустой строки, вывести все строки, которые пользователь ввёл до этого, в том же порядке, и закончить работу приложения.
 */
package Task2;
import java.util.Scanner;
import java.util.ArrayList;

public class EchoChamber {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ArrayList al = new ArrayList();
        String str = sc.nextLine();
        while (str.equalsIgnoreCase("") == false){                        
            al.add(str);
            str = sc.nextLine();
        }
        al.forEach(s -> System.out.println(s));
    }
}
