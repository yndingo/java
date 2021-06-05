package ru.pflb;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.*;
import ru.pflb.mq.dummy.interfaces.*;
import java.util.Queue;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

class GlobalKeyListenerExample implements NativeKeyListener {
    static boolean exit = false;  
    public void nativeKeyPressed(NativeKeyEvent e) {
            //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

            if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                    try {
                            System.out.println("Нажата кнопка ESC. Завершаем работу.");
                            exit = true;
                            GlobalScreen.unregisterNativeHook();
                    } catch (NativeHookException nativeHookException) {
                            nativeHookException.printStackTrace();
                    }
            }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
            //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
            //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
}

public class Main {      
    
    public static void main(String[] args) throws DummyException{
        
         // Get the logger for "org.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
        try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());

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

        try(
            BufferedReader bufRead = new BufferedReader(input);
            Connection conn = new ConnectionImpl();//close
            Session session = conn.createSession(true);//close
                )
        {
            Queue <String> queue = new LinkedList<>();
            String str;
            try {
                while ( (str = bufRead.readLine())!=null )
                    queue.offer(str);
            }
            catch(Exception e){System.out.println(e);}
        
            Destination dest = session.createDestination("test");
            Producer prod = session.createProducer(dest);
            System.out.println("Для остановки программы нажмите ESC.");

            while (true){
                if (GlobalKeyListenerExample.exit) break;
                str = queue.poll();
                queue.offer(str);
                prod.send(str);
                try{
                    Thread.sleep(2000L);
                }
                catch(InterruptedException e){ System.out.println(e); }
            }
        }
        catch(IOException e){ System.out.println(e); }
    }
}
