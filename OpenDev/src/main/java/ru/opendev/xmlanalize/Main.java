package ru.opendev.xmlanalize;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.IOException;
import java.io.File;
import java.util.HashSet;

public class Main {
    private static HashSet<String> modifications = new HashSet<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException { 
        boolean fileExists = false; 

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        
        XMLHandler handler = new XMLHandler();
        
        //parser.parse(new File("resource/cars.xml"), handler);
        try{
            parser.parse(new File(args[0]), handler);
            fileExists = true;
        }
        catch (Exception e){
            System.out.println("Файл не распознан.");
            System.out.println("Прерывание: " + e);
        }

        if (fileExists) {
            //for (String x : modifications)
            //    System.out.println(x);
            System.out.println(modifications.size());
        }
    }

    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            // Тут будет логика реакции на начало элемента
            if (qName.equals("modification")) {
                String name = attributes.getValue("name");
                Integer id = Integer.parseInt( attributes.getValue("id") );                
                modifications.add(name);
            }
        }
    }
}
