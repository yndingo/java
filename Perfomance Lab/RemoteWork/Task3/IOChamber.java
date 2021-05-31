/*
Скопируйте проект из задания 2 в новую папку, переименуйте в IOChamber. Добавьте возможность вместо считывания пользовательского ввода из консоли, 
считать содержимое текстового файла, вывести его содержимое в консоль, закончить работу приложения.
Выбор осуществляется следующим образом: если при запуске приложения ему передан аргумент – путь к текстовому файлу, то приложение выводит его содержимое 
(для работы с файлом можно использовать, например, java.io.FileReader, а также рекомендую обратить внимание на java.nio.Files для получения BufferedReader, 
у которого есть очень хороший метод lines(), преимущества которого мы обсудим на следующем занятии).
Если при запуске передан аргумент, но он не является валидным идентификатором файла или такого файла не существует, должно быть выведено сообщение “Файл не распознан. 
Введите ваши сообщения”, после чего приложение должно работать, как в задании 2.
Если при запуске не передается аргумент, приложение должно работать, как в задании 2.
 */
package Task3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class IOChamber {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Boolean fileExists = false;
        ArrayList al = new ArrayList();
        String str = null;
        
        FileReader input = null;

        try{
            if (args.length > 0) str = args[0];
            input = new FileReader(str);
            //System.out.println(str);
            fileExists = true;
        }
        catch (Exception e){
            System.out.println("Файл не распознан. Введите ваши сообщения.");
            System.out.println("Прерывание: " + e);
        }
        if (fileExists) {
            BufferedReader bufRead = new BufferedReader(input);
            String myLine = null;

            try {
                while ( (myLine = bufRead.readLine()) != null) al.add(myLine);
            }
            catch(IOException e){
                System.out.println(e);
            }
        }
        else {
            str = sc.nextLine();
            while (str.equalsIgnoreCase("") == false){                        
                al.add(str);
                str = sc.nextLine();
            }
        }
        al.forEach(s -> System.out.println(s));

    }
}
