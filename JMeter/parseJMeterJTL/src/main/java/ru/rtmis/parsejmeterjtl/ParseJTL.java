package ru.rtmis.parsejmeterjtl;
/*
обрабатываю лог JMeter testResults.jtl сгенерированный после теста
где содержится информация в виде:
2021-10-08 10:26:26,391,TC_01.Create_SEMD,200,"Number of samples in transaction : 1, number of failing samples : 0",UTG_TEST-1-1 1-259,,true,,284,1109,358,358,null,391,33,21
2021-10-08 10:26:26,587,TC_01.Create_SEMD,200,"Number of samples in transaction : 1, number of failing samples : 0",UTG_TEST-1-1 1-87,,true,,284,1109,358,358,null,586,282,358
выбираю в этом файле необходимый мне интервал времени
Формирую ramp.jtl с заголовками "timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect"
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class ParseJTL {
    public static void main(String[] args) throws IOException{
        System.out.println("begin parsing");      

       //file objects
       //File fileIN        = FileUtils.getFile("c:/!d/testResults.jtl");
       //File fileOUT       = FileUtils.getFile("c:/!d/ramp.jtl");
       if (args.length == 0) {
           System.out.println("Данные вводить через параметры:");
           System.out.println("аргумент 1 - файл для ввода например: c:/!d/testResults.jtl");
           System.out.println("аргумент 2 - файл для ввода например: c:/!d/ramp.jtl");
           System.out.println("аргумент 3 - номер полки например: 2");
           System.out.println("аргумент 4 - время начала полки: 2021-10-14 17:21:00");
           System.out.println("аргумент 5 - время конца  полки: 2021-10-15 05:52:00");         
           
           System.exit(0);
       }
       String fileNameOUT = args[1].substring(0, args[1].length()-4) + args[2] + args[1].substring(args[1].length()-4);
       System.out.println("Начата работа с " + args[0] + " Вывод будет в " + fileNameOUT);
       System.out.println("Полка началась в " + args[3] + " Полка закончилась в " + args[4]);
       
       File fileIN        = FileUtils.getFile(args[0]);
       //File fileOUT       = FileUtils.getFile(args[1]+args[2]);
       File fileOUT       = FileUtils.getFile(fileNameOUT);    
       
       PrintWriter writer = new PrintWriter(fileOUT, "UTF-8");     
       // Format data and time to analyze
       DateTimeFormatter inputFormatWithMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       //LocalDateTime rampBegin             = LocalDateTime.parse("2021-10-14 17:21:00", inputFormatWithMS);
       //LocalDateTime rampEnd               = LocalDateTime.parse("2021-10-15 05:52:00", inputFormatWithMS);
       LocalDateTime rampBegin             = LocalDateTime.parse(args[3], inputFormatWithMS);
       LocalDateTime rampEnd               = LocalDateTime.parse(args[4], inputFormatWithMS);

       //creating ramp JTL
       writer.println("timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect");
       try(LineIterator lineIterator = FileUtils.lineIterator(fileIN)) {
           while(lineIterator.hasNext()) {
               String line = lineIterator.next();
               String[] strArray = line.split(",");               
               if(strArray.length > 0){
                   try{                       
                       LocalDateTime ldt = LocalDateTime.parse(strArray[0], inputFormatWithMS);
                       if ( ldt.isAfter(rampBegin) && ldt.isBefore(rampEnd) )                           
                           writer.println(line);
                   }
                   catch(DateTimeParseException e){
                       System.out.println("Дата в данной строке не найдена");
                       System.out.println(line);
                   } 
                }
            }               
           writer.close();
           System.out.println("END parsing");
        }
    }
}
