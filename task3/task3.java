
//Некоторое количество человек то наливают воду в бочку, то черпают из бочки. Если человек
//пытается налить больше воды, чем есть свободного объема – это ошибка, при этом объем воды в
//бочке не меняется. Так же если человек пытается зачерпнуть больше воды, чем есть в бочке –
//ошибка, объем воды также при этом не меняется. В остальных случаях – успех.
//Вам дан лог файл. Напишите программу, которая ответит на следующие вопросы:
//- какое количество попыток налить воду в бочку было за указанный период?
//- какой процент ошибок был допущен за указанный период?
//- какой объем воды был налит в бочку за указанный период?
//- какой объем воды был не налит в бочку за указанный период?
//- … тоже самое для забора воды из бочки …
//- какой объем воды был в бочке в начале указанного периода? Какой в конце указанного
//периода?
//Путь к логу, желаемый период – подаются в качестве аргументов командной строки. Результат
//записывается в csv файл (с наименованием столбцов).
//Пример строки запуска: java –jar task3 ./log.log 2020-01-01T12:00:00 2020-01-01T13:00:00
//Пример лог файла:
//{sphere: {center: [0, 0, 0], radius: 10.67}, line: {[1, 0.5, 15], [43, -14.6, 0.04]}}META DATA:
//200 (объем бочки)
//32 (текущий объем воды в бочке)
//2020-01-01Т12:51:32.124Z – [username1] - wanna top up 10l (успех)
//2020-01-01Т12:51:34.769Z – [username2] - wanna scoop 50l (фейл)
//…
//Примечание: для проверки сгенерируйте лог файл объемом 1 Mb, приложите его к решению.
//Обратите внимание, искомого временного интервала может не быть в логе, приложение не
//должно при этом крашиться. Если аргументы поданы не верно, в stdout должен выводится usage.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class task3 {
    public static void main(String[] args){
        
//        try {
//      File myObj = new File("filename.txt");
//      if (myObj.createNewFile()) {
//        System.out.println("File created: " + myObj.getName());
//      } else {
//        System.out.println("File already exists.");
//      }
//    } catch (IOException e) {
//      System.out.println("An error occurred.");
//      e.printStackTrace();
//    }
        
        for (String s : args){
            System.out.println(s);
        }
        
        if (args.length == 0) {
            System.out.println("Не найдено имя файла для анализа");
            return;
        }
        FileReader input = null;
        try{
            input = new FileReader(args[0]);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
            return;
        }
        
        BufferedReader bufRead = new BufferedReader(input);
        String myLine = null;

        try {
            while ( (myLine = bufRead.readLine()) != null)
            {    
                String[] array1 = myLine.split(":");
                //System.out.println(array1.length);
                // проверить что есть правильные данные
                String[] array2 = array1[0].split(" ");
                //System.out.println(array2.length);
                for (int i = 0; i < array2.length; i++)
                    //function(array1[0], array2[i]);
                    System.out.println(array2[i]);
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
}
