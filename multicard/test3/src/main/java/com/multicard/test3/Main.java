/*
написать функцию для генерации пирамиды
*******************
 *****************
  ***************
   *************
    ***********
     *********
      *******
       *****
        ***
         *
         *
        ***
       *****
      *******
     *********
    ***********
   *************
  ***************
 *****************
*******************
*         *
**        **
***       ***
****      ****
*****     *****
******    ******
*******   *******
********  ********
********* *********
********************
********************
********* *********
********  ********
*******   *******
******    ******
*****     *****
****      ****
***       ***
**        **
*         *
*/
package com.multicard.test3;

import java.util.Collections;

public class Main {
    private static void draw(int rows){
        //пирамида
        for (int i = rows; i > 0; i--) { 
            System.out.println(String.join("", Collections.nCopies(rows - i, " "))
                + String.join("", Collections.nCopies(2 * i - 1, "*")));            
        }
        //обратная пирамида
        for (int i = 0; i < rows; i++) { 
            System.out.println(String.join("", Collections.nCopies(rows - i - 1, " "))
                    + String.join("", Collections.nCopies(2 * i + 1, "*"))); 
        }
        
        int space = rows;
        for (int i = 0; i < rows; i++) {
            //полуконус
            for (int j = 0; j <= i; j++) {
                System.out.print("*");                
            }
            //пробелы
            for (int z = 1; z < space; z++)
                    System.out.print(" ");
            space--;
            //полуконус рядом
            for (int j = 0; j <= i; j++) {
                System.out.print("*");                
            }
            System.out.println("");
        }
        
        space = 1;
        for (int i = 0; i < rows; i++) {
            //обратный полуконус
            for (int j = rows; j > i; j--) {
                System.out.print("*");                
            }
            //пробелы
            for (int z = space; z > 1; z--)
                    System.out.print(" ");
            space++;
            //обратный полуконус рядом
            for (int j = rows; j > i; j--) {
                System.out.print("*");                
            }

            
           System.out.println(""); 
        }
    }
    
    public static void main(String[] args){
        int rows = 10;  
        draw(rows); 
    }
}
