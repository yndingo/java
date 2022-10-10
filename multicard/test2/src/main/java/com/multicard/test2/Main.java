//Напишите программу, которая посчитает 
//количество различных(без учета регистра) букв в файле.
package com.multicard.test2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello");
        if (args.length > 0){            
            try{
                File file = new File(args[0]);
                if (file.isFile()) {
                    String fileName = file.getName();
                    System.out.println("analiz " + fileName);

                    FileInputStream fis = new FileInputStream(file);
                    byte[] byteArray = new byte[(int)file.length()];
                    fis.read(byteArray);
                    String data = new String(byteArray);
                    System.out.println("Number of characters in the given file are "+data.length());
                }
                else System.out.println("Nado vvesti polnoe file name, s rasshireniem. Naprimer text.txt");
            }
            catch(IOException e){
                System.out.println("problem with file/fileName");
            }
        }
        else System.out.println("Nado peredat v pervom argymente file name. Programma opredelit kol-vo znakov v nem");
        
        
    }
}
    
