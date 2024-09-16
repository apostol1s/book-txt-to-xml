package com.mycompany.txttoxml;

import com.mycompany.txttoxml.service.ConvertTxtToXml;
import com.mycompany.txttoxml.service.ConvertTxtToXmlImpl;
import com.mycompany.txttoxml.service.StaxReader;
import com.mycompany.txttoxml.service.StaxReaderImpl;
import java.io.IOException;

public class TxtToXml {

    public static void main(String[] args) {
        ConvertTxtToXml convertTxtToXml = new ConvertTxtToXmlImpl();
        StaxReader staxReader = new StaxReaderImpl();

        try {
            convertTxtToXml.convertTxtToXml("txt/sample-lorem-ipsum-text-file.txt", "xml/book.xml");
        } catch (IOException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        try {
            staxReader.StaxReader("xml/book.xml");
        } catch (IOException ex) {
            System.out.println("Error" + ex.getMessage());
        }      
    }
}
