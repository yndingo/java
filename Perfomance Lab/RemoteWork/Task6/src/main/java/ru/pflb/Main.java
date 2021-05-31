package ru.pflb;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.*;
import java.util.Queue;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws DummyException{

        for (String s : args){
            System.out.println(s);
        }
        if (args.length == 0) {
            System.out.println("Не найдено имя файла для анализа");
            return;
        }
        FileReader input = null;
        try{
            input = new FileReader(args[0]);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
            return;
        }
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        Queue <String> queue = new LinkedList<>();
        try {
            while ( (myLine = bufRead.readLine())!=null )
                queue.offer(myLine);
        }
        catch(Exception e){System.out.println(e);}

        ConnectionImpl ConImpl = new ConnectionImpl();
        ConImpl.start();
        SessionImpl SessImpl = (SessionImpl)(ConImpl.createSession(true));
        DestinationImpl DestImpl = (DestinationImpl)(SessImpl.createDestination("test"));
        ProducerImpl ProdImpl = (ProducerImpl)(SessImpl.createProducer(DestImpl));
        //ProdImpl.send("World");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                SessImpl.close();
                ConImpl.close();
                //System.out.println("Обработка прерывания, останавливаем сессию и соединение");
            }
        });

        String str;
        while ( (str = queue.poll()) != null){
            //System.out.println(str);
            //s = sc.nextLine();
            //if (sc.nextLine().isEmpty()) break;
            queue.offer(str);
            ProdImpl.send(str);
            try{
                Thread.sleep(2000L);
            }
            catch(InterruptedException e){
                System.out.println(e);
            }
        }
        SessImpl.close();
        ConImpl.close();


    }

}
