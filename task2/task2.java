//Напишите программу, которая находит точки столкновения сферы и прямой линии. Если их нет,
//то выводится фраза: «Коллизий не найдено» (кириллицей, будьте внимательны), если есть, то
//выводятся координаты точек, ограниченные символом новой строки. Координаты считываются из
//файла, который имеет следующий формат:
//Примечание: файл не будет содержать синтаксических ошибок, однако объекты и ключи могут
//находится в свободной последовательности. Координаты точек – массив [x, y, z].
//Дополнительно: верх крутости – рендеринг данной сцены.
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.regex.*;

public class task2 {
     public static void main(String[] args){
         String fileText = "";
         if (args.length == 1) {
            try {fileText = Files.lines(Paths.get(args[0]))
                 .findFirst()
                 .map(Object::toString).orElse("");
            }
            catch (IOException e){
             System.out.println(e);
            }
        }
        else System.out.println("Не найдено имя файла для анализа");
         
        double sphereCenterX = 0, sphereCenterY = 0, sphereCenterZ = 0, sphereRad = 0;
        double lineX1 = 0, lineY1 = 0, lineZ1 = 0, lineX2 = 0, lineY2 = 0, lineZ2 = 0;
         
         String sphere="";
         Matcher m = Pattern.compile("sphere.+?\\}").matcher(fileText);
         while (m.find()){
             //System.out.println(m.group());
             sphere = m.group();
         }

         String line="";
         Matcher m2 = Pattern.compile("line.+?\\}").matcher(fileText);
         while (m2.find()){
             //System.out.println(m2.group());
             line = m2.group();
         }
         
         Matcher m3 = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?").matcher(sphere);
         int i = 0;
         //String number="";
         while (m3.find()){
             if ( fileText.indexOf("center") < fileText.indexOf("radius") )
                 switch(i){
                     case 0: sphereCenterX = Double.parseDouble(m3.group()); break;
                     case 1: sphereCenterY = Double.parseDouble(m3.group()); break;
                     case 2: sphereCenterZ = Double.parseDouble(m3.group()); break;
                     case 3: sphereRad = Double.parseDouble(m3.group()); break;
                 }             
             else
                switch(i){
                     case 1: sphereCenterX = Double.parseDouble(m3.group()); break;
                     case 2: sphereCenterY = Double.parseDouble(m3.group()); break;
                     case 3: sphereCenterZ = Double.parseDouble(m3.group()); break;
                     case 0: sphereRad = Double.parseDouble(m3.group()); break;
                }
             i++;                    
         }
         Matcher m4 = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?").matcher(line);
         i = 0;
         while (m4.find()){
             if ( fileText.indexOf("center") < fileText.indexOf("radius") )
                 switch(i){
                     case 0: lineX1 = Double.parseDouble(m4.group()); break;
                     case 1: lineY1 = Double.parseDouble(m4.group()); break;
                     case 2: lineZ1 = Double.parseDouble(m4.group()); break;
                     case 3: lineX2 = Double.parseDouble(m4.group()); break;
                     case 4: lineY2 = Double.parseDouble(m4.group()); break;
                     case 5: lineZ2 = Double.parseDouble(m4.group()); break;
                 }             
             i++;                    
         }

        double vx = lineX2 - lineX1;
        double vy = lineY2 - lineY1;
        double vz = lineZ2 - lineZ1;

        double A = (vx * vx + vy * vy + vz * vz);
        double B = 2.0 * (lineX1 * vx + lineY1 * vy + lineZ1 * vz - vx * sphereCenterX - vy * sphereCenterY - vz * sphereCenterZ);
        double C = lineX1 * lineX1 - 2 * lineX1 * sphereCenterX + sphereCenterX * sphereCenterX + lineY1 * lineY1 - 2 * lineY1 * sphereCenterY + sphereCenterY * sphereCenterY +
            lineZ1 * lineZ1 - 2 * lineZ1 * sphereCenterZ    + sphereCenterZ * sphereCenterZ - sphereRad * sphereRad;
        double D = B * B - 4 * A * C;
        double t = -1.0;
        double tt1=0,tt2=0;
        if (D >= 0)
        {
            tt1 = (-B - java.lang.Math.sqrt(D)) / (2.0 * A);
            tt2 = (-B + java.lang.Math.sqrt(D)) / (2.0 * A);
            double x = lineX1*(1-tt1) + tt1*lineX2;
            double y  = lineY1*(1-tt1) + tt1*lineY2;
            double z  = lineZ1*(1-tt1) + tt1*lineZ2;
            System.out.print("[" + x + ", " + y + ", " + z + "], ");

            x = lineX1*(1-tt2) + tt2*lineX2;
            y  = lineY1*(1-tt2) + tt2*lineY2;
            z  = lineZ1*(1-tt2) + tt2*lineZ2;
            System.out.println("[" + x + ", " + y + ", " + z + "] ");
        }
        else System.out.print("Коллизий не найдено");
     }
     
     
     
     
     
     
     
     
}
