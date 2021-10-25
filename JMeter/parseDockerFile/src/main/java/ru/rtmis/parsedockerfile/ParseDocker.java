/*
обрабатываю лог из докер-контейнера docker.log
где содержится информация в виде:
{"@timestamp":"2021-10-14T03:53:46.086Z","@version":"1","message":"Template context: {\"User\":{\"name\":\"test_user_1\"},\"Settings\":{\"messages\":{\"welcome\":\"welcome buddy!\"}}}","logger_name":"ru.mis.template.engine.service.TemplateService","thread_name":"http-nio-8080-exec-2164","level":"INFO","level_value":20000,"request-chain-id":"123"}
{"@timestamp":"2021-10-14T03:53:46.086Z","@version":"1","message":"Template context: {\"User\":{\"name\":\"test_user_1\"},\"Settings\":{\"messages\":{\"welcome\":\"welcome buddy!\"}}}","logger_name":"ru.mis.template.engine.service.TemplateService","thread_name":"http-nio-8080-exec-2056","level":"INFO","level_value":20000,"request-chain-id":"123"}
{"@timestamp":"2021-10-14T03:53:46.087Z","@version":"1","message":"Template test.ftl processed in 0ms","logger_name":"ru.mis.template.engine.service.TemplateService","thread_name":"http-nio-8080-exec-2164","level":"INFO","level_value":20000,"request-chain-id":"123"}

от туда вычленяю строки с "processed in"
и забираю дату и время и задержку
Формирую parse.log с заголовками "DateTime,Delay"
*/
package ru.rtmis.parsedockerfile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class ParseDocker {
    public static void main(String[] args) throws IOException {
        System.out.println("begin parsing");      
       //file objects
       //File fileIN        = FileUtils.getFile("c:/!d/docker.log");
       //File fileOUT       = FileUtils.getFile("c:/!d/parse.log");
       
       if (args.length == 0) {
           System.out.println("обрабатываю лог из докер-контейнера docker.log\n" +
                   "где содержится информация в виде:\n\n" +
                   "{'@timestamp':'2021-10-14T03:53:46.086Z','@version':'1','message':'Template context: {\'User\':{\'name\':\'test_user_1\'},\'Settings\':{\'messages\':{\'welcome\':\'welcome buddy!\'}}}','logger_name':'ru.mis.template.engine.service.TemplateService','thread_name':'http-nio-8080-exec-2164','level':'INFO','level_value':20000,'request-chain-id':'123'}\n" +
                   "{'@timestamp':'2021-10-14T03:53:46.086Z','@version':'1','message':'Template context: {\'User\':{\'name\':\'test_user_1\'},\'Settings\':{\'messages\':{\'welcome\':\'welcome buddy!\'}}}','logger_name':'ru.mis.template.engine.service.TemplateService','thread_name':'http-nio-8080-exec-2056','level':'INFO','level_value':20000,'request-chain-id':'123'}\n" +
                   "{'@timestamp':'2021-10-14T03:53:46.087Z','@version':'1','message':'Template test.ftl processed in 0ms','logger_name':'ru.mis.template.engine.service.TemplateService','thread_name':'http-nio-8080-exec-2164','level':'INFO','level_value':20000,'request-chain-id':'123'}\n\n" +
                   "от туда вычленяю строки с 'processed in'\n" +
                   "и забираю дату и время и задержку\n" +
                   "Формирую parse.log с заголовками 'DateTime,Delay'\n");

           System.out.println("Данные вводить через параметры:");
           System.out.println("аргумент 1 - файл для ввода например: c:/!d/docker.log");
           System.out.println("аргумент 2 - файл для ввода например: c:/!d/parse.log");
     
           System.exit(0);
       }
       System.out.println("Начата работа с " + args[0] + " Вывод будет в " + args[1] + "\n");
       
       int maxDelay = 0;//вывожу информацию о максимальной задержке
       //file objects
       File fileIN        = FileUtils.getFile(args[0]);
       File fileOUT       = FileUtils.getFile(args[1]);
       //regex options
       Pattern defRegex   = Pattern.compile("\\d{4}-\\S{18}Z|(\\d*)(ms)");
       
       PrintWriter writer = new PrintWriter(fileOUT, "UTF-8");     
       // Format data and time to analyze
       DateTimeFormatter inputFormatWithMS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
       DateTimeFormatter inputFormatSTD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
       
       //creating log
       writer.println("DateTime,Delay");
       try(LineIterator lineIterator = FileUtils.lineIterator(fileIN)) {
           while(lineIterator.hasNext()) {
               String line = lineIterator.next();
               //checking "processed in" only that string contains delay if no then check next
               if (!line.contains("processed in"))
                       continue;                        
               //System.out.println(line);
               Matcher m     = defRegex.matcher(line);
               String result = "";               
               
               while (m.find()){
                    String temp = m.group();                    
                    if (temp.contains("T")){
                        // Parsing the date
                        //LocalDate date = LocalDate.parse(temp, inputFormatWithMS);
                        //LocalTime time = LocalTime.parse(temp, inputFormatWithMS);
                        LocalDateTime ldt = LocalDateTime.parse(temp, inputFormatWithMS);
                        
                        //при простом приведении к строке toString обрезаются нулевые секунды вместо 03:00:00(3 часа 0 минут 0 секунд) будет 03:00
                        temp = ldt.format(inputFormatSTD);
                        //temp = date + " " + time;
                        //temp = date + " " + time.truncatedTo(ChronoUnit.SECONDS).format(inputFormatWithoutMS);
                    }                  
                    if (temp.contains("ms")){
                        // Parsing delay
                        temp = temp.substring(0, temp.length()-2);
                        if (maxDelay < Integer.parseInt(temp))
                            maxDelay = Integer.parseInt(temp);                                
                    }
                    result += temp+",";
                    //System.out.println(result);
                }
                //writing to log without last comma
                if (result.length() > 0) 
                    writer.println(result.substring(0, result.length()-1));                                             
         }
           writer.close();
           System.out.println("\n END parsing");
           System.out.println("Максимальная задержка составила: " + maxDelay);
      }
   }
}
