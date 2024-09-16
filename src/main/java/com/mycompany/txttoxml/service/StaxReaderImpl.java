package com.mycompany.txttoxml.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxReaderImpl implements StaxReader {

    /**
     * Reads and processes an XML file using StAX parser. The method reads
     * elements from the XML file such as "chapter", "paragraph", "line", and
     * "statistics", and prints their content to the console. It also extracts
     * statistical data.
     *
     * @param xmlFileName the name of the XML file to read and process.
     * @throws IOException if there is an issue opening or reading the XML file.
     */
    @Override
    public void StaxReader(String xmlFileName) throws IOException {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(xmlFileName));

            String chapterNumber = null;
            String paragraphNumber = null;
            String lineNumber = null;
            String paragraphCounter = null;
            String lineCounter = null;
            String wordCounter = null;
            String distinctWordCounter = null;
            String creationDate = null;
            String author = null;
            String applicationClassName = null;
            String sentence = null;
            String currentElement = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    switch (elementName) {
                        case "chapter":
                            System.out.println("Start of Chapter");
                            currentElement = "chapter";
                            break;

                        case "paragraph":
                            System.out.println("Start of Paragraph");
                            currentElement = "paragraph";
                            break;

                        case "line":
                            System.out.println("Start of Line");
                            currentElement = "line";
                            break;

                        case "number":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                String currentText = event.asCharacters().getData().trim();

                                switch (currentElement) {
                                    case "chapter":
                                        chapterNumber = currentText;
                                        System.out.println("Chapter Number: " + chapterNumber);
                                        break;
                                    case "paragraph":
                                        paragraphNumber = currentText;
                                        System.out.println("Paragraph Number: " + paragraphNumber);
                                        break;
                                    case "line":
                                        lineNumber = currentText;
                                        System.out.println("Line Number: " + lineNumber);
                                        break;
                                }
                            }
                            break;

                        case "sentence":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                sentence = event.asCharacters().getData();
                                System.out.println("Sentence: " + sentence);
                            }
                            break;

                        case "statistics":
                            System.out.println("Statistics Section:");
                            break;

                        case "paragraphCount":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                paragraphCounter = event.asCharacters().getData();
                                System.out.println("Paragraph Count: " + paragraphCounter);
                            }
                            break;

                        case "lineCount":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                lineCounter = event.asCharacters().getData();
                                System.out.println("Line Count: " + lineCounter);
                            }
                            break;

                        case "wordCount":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                wordCounter = event.asCharacters().getData();
                                System.out.println("Word Count: " + wordCounter);
                            }
                            break;

                        case "distinctWordCount":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                distinctWordCounter = event.asCharacters().getData();
                                System.out.println("Distinct Word Count: " + distinctWordCounter);
                            }
                            break;

                        case "creationDate":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                creationDate = event.asCharacters().getData();
                                System.out.println("Creation Date: " + creationDate);
                            }
                            break;

                        case "author":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                author = event.asCharacters().getData();
                                System.out.println("Author: " + author);
                            }
                            break;

                        case "applicationClassName":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                applicationClassName = event.asCharacters().getData();
                                System.out.println("Application Class Name: " + applicationClassName);
                            }
                            break;
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String elementName = endElement.getName().getLocalPart();

                    switch (elementName) {
                        case "chapter":
                            System.out.println("End of Chapter");
                            break;
                        case "paragraph":
                            System.out.println("End of Paragraph");
                            break;
                        case "line":
                            System.out.println("End of Line");
                            break;
                        case "statistics":
                            System.out.println("End of Statistics");
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            System.out.println("Error " + e.getMessage());
        }
    }
}
