/*
обрабатываю после LogParser файл output2.log
где содержится информация в виде:
{"@timestamp":"2021-10-13T10:45:00.007Z","@version":"1","message":"Template test.ftl processed in 0ms","logger_name":"ru.mis.template.engine.service.TemplateService","thread_name":"http-nio-8080-exec-2116","level":"INFO","level_value":20000,"request-chain-id":"123"}
{"@timestamp":"2021-10-13T10:45:00.008Z","@version":"1","message":"Template test.ftl processed in 0ms","logger_name":"ru.mis.template.engine.service.TemplateService","thread_name":"http-nio-8080-exec-2094","level":"INFO","level_value":20000,"request-chain-id":"123"}

от туда забираю дату и время и задержку
Формирую parse.log с заголовками "DateTime,Delay"
*/
package ru.rtmis.parsedockerfile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ParseDocker {
    public static void main(String[] args){
        System.out.println("begin parsing");      

        try {
            usingLineIterator();
        } catch(IOException e) {
            System.out.println("Исключение в usingLineIterator");
            System.out.println(e.getMessage());
        }
   }
   public static void usingLineIterator() throws IOException {
       //regex options
       Pattern defRegex   = Pattern.compile("\\d{4}-\\S{18}Z|(\\d*)(ms)");
       //file objects
       File fileIN        = FileUtils.getFile("c:/!d/output2.log");
       File fileOUT       = FileUtils.getFile("c:/!d/parse.log");
       PrintWriter writer = new PrintWriter(fileOUT, "UTF-8");     
       // Format data and time to analyze
       DateTimeFormatter inputFormatWithMS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
       DateTimeFormatter inputFormatWithoutMS = DateTimeFormatter.ISO_TIME;

       //creating log
       writer.println("DateTime,Delay");
       try(LineIterator lineIterator = FileUtils.lineIterator(fileIN)) {
           while(lineIterator.hasNext()) {
               String line = lineIterator.next();
               //System.out.println(line);
               Matcher m     = defRegex.matcher(line);
               String result = "";               
               
               while (m.find()){
                    String temp = m.group();                    
                    if (temp.contains("T")){
                        // Parsing the date
                        LocalDate date = LocalDate.parse(temp, inputFormatWithMS);
                        LocalTime time = LocalTime.parse(temp, inputFormatWithMS);
                        
                        temp = date + " " + time;
                        //temp = date + " " + time.truncatedTo(ChronoUnit.SECONDS).format(inputFormatWithoutMS);
                    }                  
                    if (temp.contains("ms")){
                        // Parsing delay
                        temp = temp.substring(0, temp.length()-2);
                    }
                    result += temp+",";
                    //System.out.println(result);
                }
                //writing to log without last comma
                if (result.length() > 0) 
                    writer.println(result.substring(0, result.length()-1));                                             
         }
           writer.close();
           System.out.println("END parsing");
      }
   }
}
