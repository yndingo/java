package ru.pflb;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.*;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
    
    public static void main(String[] args) throws DummyException{
        //System.out.println("Hello");
        Queue <String> queue = new LinkedList<>();
        queue.offer("Раз");
        queue.offer("Два");
        queue.offer("Три");
        ConnectionImpl ConImpl = new ConnectionImpl();
        ConImpl.start();
        SessionImpl SessImpl = (SessionImpl)(ConImpl.createSession(true));
        DestinationImpl DestImpl = (DestinationImpl)(SessImpl.createDestination("test"));
        ProducerImpl ProdImpl = (ProducerImpl)(SessImpl.createProducer(DestImpl));
        //ProdImpl.send("World");
        
        String str;
        while ( (str = queue.poll()) != null){
            //System.out.println(str);
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
