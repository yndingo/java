/*
В том же проекте, в котором вы выполнили задание 3, внесите изменения таким образом, чтобы выводить только нечётные строки (как для файла, так и для пользовательского ввода).
 */
package Task4;
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
        String str = sc.nextLine();

        FileReader input = null;

        try{
            input = new FileReader(str);
            fileExists = true;
        }
        catch (Exception e){
            System.out.println("Файл не распознан. Введите ваши сообщения");
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
        for (int i = 0; i < al.size(); i++){
            if (i % 2 > 0) System.out.println(al.get(i));
            //System.out.println( i % 2 > 0 );
        }

    }
}
