public class task1 {
    private static String itoBase(int num, String base){
        switch (base){
            case ("01"): return Integer.toBinaryString(num);
            case ("012"): 
                StringBuilder sb= new StringBuilder();
                while (num>0){
                    sb.append(num%3);
                    num /=3;
                }
                return sb.reverse().toString();
            case ("0123456789abcdef"): return Integer.toHexString(num);
            case ("котики"): return java.lang.Math.random()+"";      
            default: return num + " " + base + " Формат не распознан";
        }
    }  
    private static String itoBase(String nb, String baseSrc, String baseDst){
        if (baseSrc.equals("10")) return itoBase(Integer.parseInt(nb),baseDst);
        return "Реализовано только 10е преобразование";
    }
    
    public static void main(String[] args){
        //System.out.println("Функция печати числа в указанной системе изчисления");
        //System.out.println("Ожидается ввод: ЧИСЛО и \"Система изчисления\"");
        //System.out.println(" «01» - двоичная, «012» - троичная, «0123456789abcdef» - шеснадцатиричная, «котики»\n""- система исчисления в котиках");
        if (args.length == 3) {
            try{
                System.out.println( itoBase(args[0],args[1],args[2]) );
            }
            catch (Exception e){
                System.out.println("Ошибка вызова недоделанной перегруженной функции - " + e);
            }
        }
        else System.out.println("Недостаточно аргументов при запуске программы args[] для анализа, требуется 2 (число, сист изчисл)");
        
        if (args.length == 2) {
            try{
                int num = Integer.parseInt(args[0]);
                System.out.println( itoBase(num,args[1]) );
            }
            catch (NumberFormatException e){
                System.out.println("Неожиданный формат числа. Ошибка - " + e);
            }
        }
        else if (args.length != 2 & args.length != 3) System.out.println("Недостаточно аргументов при запуске программы args[] для анализа, требуется 2 (число, сист изчисл)");
    }
    
}
