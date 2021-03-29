//Напишите программу, которая сравнивает 2 строки одинаковые ли они. Результат: вывод «ОК»,
//если строки идентичны, «КО», если не идентичны. Строки подаются в виде аргументов командной
//строки.
//Примечание: во второй строке может быть символ ‘*’ – он заменяет собой любое количество
//любых символов.
//На пример:
//«аааа» «аааа» - ОК
//«аааа» «аа*» - ОК
//«a» «*****» - ОК
//Дополнение: конечно, в этом задании нас будет интересовать ваш код.
public class task4 {
    public static String compStrings(String s1, String s2){
        return s1.equals(s2)?"OK":"KO";
    }
    public static String compStringsWithStar(String s1, String s2){
        int end = s2.indexOf('*');
        return s2.indexOf('*')==0?"OK":s1.substring(0,end).equals(s2.substring(0,end))?"OK":"KO";
    }
    public static void main(String[] args){
        if (args.length == 2) {
            if ( args[1].indexOf('*') == 0 ) System.out.println( compStrings(args[0],args[1]) );
            else System.out.println( compStringsWithStar(args[0],args[1]) );
        }
        else System.out.println("Недостаточно аргументов при запуске программы args[] для анализа, требуется 2");
    
    }
}
