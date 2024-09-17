package com.mycompany.txttoxml;

import com.mycompany.txttoxml.service.ConvertTxtToXml;
import com.mycompany.txttoxml.service.ConvertTxtToXmlImpl;
import com.mycompany.txttoxml.service.SelectedElementsXml;
import com.mycompany.txttoxml.service.SelectedElementsXmlImpl;
import com.mycompany.txttoxml.service.StaxReader;
import com.mycompany.txttoxml.service.StaxReaderImpl;
import com.mycompany.txttoxml.service.XmlValidation;
import com.mycompany.txttoxml.service.XmlValidationImpl;
import com.mycompany.txttoxml.service.XsdGenerator;
import com.mycompany.txttoxml.service.XsdGeneratorImpl;
import java.io.IOException;

public class TxtToXml {

    public static void main(String[] args) {
        ConvertTxtToXml convertTxtToXml = new ConvertTxtToXmlImpl();
        StaxReader staxReader = new StaxReaderImpl();
        SelectedElementsXml selectedElementsXml = new SelectedElementsXmlImpl();
        XsdGenerator xsdGenerator = new XsdGeneratorImpl();
        XmlValidation xmlValidation = new XmlValidationImpl();

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
        try {
            selectedElementsXml.extractChapterAndParagraph("xml/book.xml", "xml/Selected_book.xml", 5, 5);
        } catch (IOException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        try {
            xsdGenerator.xsdGenerator();
        } catch (IOException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        try {
            xmlValidation.validateXml("xml/book.xml", "xml/book-schema.xsd");
        } catch (IOException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }
}
