//Не делимое подмножество
//Дан набор различных целых чисел, выведите размер максимального подмножества, в
//котором сумма любых чисел не делится без остатка на указанный делитель
//Ввод данных организован через System.in
//в первой строке надо ввести разделенные пробелом 2 целочисленных числа
//1е это величина массива для анализа, 2е делитель
//во второй строке ввести разделенные пробелом массив чисел величину которого
//указали в 1й строке
//Ввод данных переорганизован через файл с данными, строки файлы указаны выше
package javaapplication1;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
public class JavaApplication1 {
    static int notDivisibleSubset(int arr[], int N, int K)
    {
        // Массив для храненения частоты деления по модуля 
        int f[] = new int[K];
        Arrays.fill(f, 0);
      
        // Массив заполняем результатом деления на делитель
        for (int i = 0; i < N; i++)
            f[arr[i] % K]++;
      
        // Если делитель четный тогда обновляем f[K/2]
        if (K % 2 == 0)
            f[K/2] = Math.min(f[K/2], 1);
      
        // Инициализируем результат 1 или количеством чисел давших остаток 
        //от деления 0
        int res = Math.min(f[0], 1);
      
        // Выбираем максимум из чисел давших остаток i или K-i
        for (int i = 1; i <= K/2; i++)
            res += Math.max(f[i], f[K-i]);
      
        return res;
    }
    public static void main(String[] args)
    {     
        //int arr[] = {3, 7, 2, 9, 1, 11, 5};
        //int N = arr.length;
        //int K = 3;        
        //Scanner sc = new Scanner(System.in);
//        int N = sc.nextInt();
//        int K = sc.nextInt();
//        int arr[] = new int[N];
//        for (int i = 0; i< N;i++)
//            arr[i] = sc.nextInt();
        File file = new File("input16.txt");
        try {
            Scanner sc = new Scanner(file);
            int N = 0, K = 0;
            int arr[] = null;
            while (sc.hasNextLine()) {
                N = sc.nextInt();
                K = sc.nextInt();
                arr = new int[N];
                for (int i = 0; i< N;i++)
                    arr[i] = sc.nextInt();
            }            
            System.out.println(notDivisibleSubset(arr, N, K));
            sc.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
}
