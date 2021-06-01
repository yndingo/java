package ru.pflb;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.*;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
    
    public static void main(String[] args) throws DummyException{
        Queue <String> queue = new LinkedList<>();
        queue.offer("Раз");
        queue.offer("Два");
        queue.offer("Три");

        try(
            Connection conn = new ConnectionImpl();//close
            Session session = conn.createSession(true);//close
                )
        {
            Destination dest = session.createDestination("test");
            Producer prod = session.createProducer(dest);

            String str;
            while ( (str = queue.poll()) != null){
                prod.send(str);
                try{
                    Thread.sleep(2000L);
                }
                catch(InterruptedException e){
                    System.out.println(e);
                }  
            }
        }
    }
}
