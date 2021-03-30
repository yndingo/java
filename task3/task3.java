
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
import java.time.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class task3 {
    
   static int getNumber(String s){        
         Matcher m = Pattern.compile("\\d+").matcher(s);
         String result="";
         while (m.find()){
             result+=m.group();
             //System.out.println(result);
         }
         try {
             return Integer.parseInt(result);
         }
         catch(NumberFormatException e){
             System.out.println(e);
             return -1;
         }
    }
   static int getNumber(String s,String s2){        
         Matcher m = Pattern.compile("\\d+"+s2).matcher(s);
         String result="";
         while (m.find()){
             result+=m.group();
             //System.out.println(result);
         }
         try {
             
             return new Integer(getNumber(result));
         }
         catch(NumberFormatException e){
             System.out.println(e);
             return -1;
         }
    }
   static int getDateVol(String s, LocalDateTime begin, LocalDateTime end){        
        //System.out.println(s); 
        //Matcher m = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}\\d{2}\\d{2}\\.\\d{3}").matcher(s);
         Matcher m = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z").matcher(s);
         String result="";
         while (m.find()){
             result+=m.group();
             //System.out.println(result);
         }
         try {
             System.out.println();
             LocalDateTime checkDate = ZonedDateTime.parse(result).toLocalDateTime();
             if (checkDate.isAfter( begin ) && checkDate.isBefore(end)) {
                 //System.out.println("inPeriod");
                 return getNumber(s,"l");
             }    
         }
         catch(Exception e){
             System.out.println(e);             
         }
         return -1;
    }
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
            //System.out.println(s);
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

        int volTotal = 0, volNowStart = 0, volNow = 0, volAdd = 0;
        int volAddTotal = 0, volAddFails = 0, numTriesAdd = 0, numFailsAdd = 0; 
        int volSubTotal = 0, volSubFails = 0, numTriesSub = 0, numFailsSub = 0; 
        
        
        
        LocalDateTime begin = LocalDateTime.parse(args[1]);
        LocalDateTime end = LocalDateTime.parse(args[2]);
        LocalDateTime checkDate = LocalDateTime.of(1,1,1,1,1);
        ArrayList <String> al = new ArrayList<>();
        
        try {
            while ( (myLine = bufRead.readLine()) != null) al.add(myLine);
        }
        catch(IOException e){
            System.out.println(e);
        }

        for (int i=0;i<al.size();i++){
            //System.out.println(al.get(i));                

            if (al.get(i).indexOf("объем бочки")>0) volTotal = getNumber(al.get(i));
            if (volTotal < 0) {
                System.out.print("Ошибка в парсинге volTotal");
                return;
            }
            if (al.get(i).indexOf("текущий объем")>0) {
                volNow = getNumber(al.get(i));
                volNowStart = volNow;
            }
            if (volNow < 0) {
                System.out.print("Ошибка в парсинге volNow");
                return;
            }
//            if (al.get(i).indexOf("-")>0) checkDate = getDate(al.get(i));
//            if (checkDate.isAfter( begin ) && checkDate.isBefore(end)) {
//                System.out.println("inPeriod");
//            if (al.get(i).indexOf("-")>0) volEnd = getDate(al.get(i),begin,end);
//            if (checkDate.isAfter( begin ) && checkDate.isBefore(end)) {
//                System.out.println("inPeriod");
            if (al.get(i).indexOf("-")>0) volAdd = getDateVol(al.get(i),begin,end);
            if (volAdd > 0) {
                if (al.get(i).indexOf("top")>0) {
                //наливаем    
                    numTriesAdd++;
                    if (al.get(i).indexOf("успех")>0) {
                        volNow += volAdd;
                        volAddTotal += volAdd;
                        if (volNow > volTotal) volNow = volTotal;
                    }
                    else {
                        numFailsAdd++;
                        volAddFails+= volAdd;
                    }
                }
                else{
                //сливаем
                    numTriesSub++;
                    if (al.get(i).indexOf("успех")>0) {
                        volNow -= volAdd;
                        volSubTotal += volAdd;
                        if (volNow < 0) volNow = 0;
                    }
                    else {
                        numFailsSub++;
                        volSubFails+= volAdd;
                    }
                }

            }
            
        }
        
        System.out.println("- какое количество попыток налить воду в бочку было за указанный период? -- " + numTriesAdd);
        System.out.println("- какой процент ошибок был допущен за указанный период? -- " + (double)numFailsAdd * 100 / numTriesAdd +"%");
        System.out.println("- какой объем воды был налит в бочку за указанный период? -- " + volAddTotal);
        System.out.println("- какой объем воды был не налит в бочку за указанный период? -- " + volAddFails);
        System.out.println();
        System.out.println("- какое количество попыток слить воду в бочку было за указанный период? -- " + numTriesSub);
        //System.out.println("- какой процент ошибок был допущен за указанный период? -- " + numFailsSub);
        System.out.println("- какой процент ошибок был допущен за указанный период? -- " + (double)numFailsSub * 100 / numTriesSub +"%");
        System.out.println("- какой объем воды был слит из бочки за указанный период? -- " + volSubTotal);
        System.out.println("- какой объем воды был не слит в бочку за указанный период? -- " + volSubFails);
        System.out.println();
        System.out.println("- какой объем воды был в бочке в начале указанного периода? -- " + volNowStart);
        System.out.println("- Какой в конце указанного периода? -- " + volNow); 
                
                
        
        
    }
    
}
