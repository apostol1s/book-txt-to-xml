package com.mycompany.txttoxml.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ConvertTxtToXmlImpl implements ConvertTxtToXml {

    /**
     * Converts the input txt file to an XML file with a specified structure. It
     * creates chapters with paragraphs, where each paragraph contains lines and
     * sentences. After writing the XML content, it appends statistical data.
     *
     * @param inputFileName the name of the input text file.
     * @param outputFileName the name of the output XML file.
     * @throws IOException if there is an issue reading the input file or
     * writing the output file.
     */
    @Override
    public void convertTxtToXml(String inputFileName, String outputFileName) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            StringWriter stringWriter = new StringWriter();
            XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(stringWriter);

            xmlWriter.writeStartDocument("UTF-8", "1.0");
            xmlWriter.writeStartElement("book");

            String line;
            int chapterLineCounter = 0;
            int chapterNumber = 0;
            int paragraphNumber = 0;
            int lineNumber = 0;
            int paragraphChapterNumber = 0;

            xmlWriter.writeStartElement("chapters");

            xmlWriter.writeStartElement("chapter");
            xmlWriter.writeStartElement("number");
            xmlWriter.writeCharacters(String.valueOf(++chapterNumber));
            xmlWriter.writeEndElement(); // Close number

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    xmlWriter.writeStartElement("paragraph");

                    xmlWriter.writeStartElement("number");
                    xmlWriter.writeCharacters(String.valueOf(++paragraphChapterNumber));
                    xmlWriter.writeEndElement(); // Close number

                    int paragraphLineNumber = 1;

                    String[] sentences = line.split("\\.");

                    for (String sentence : sentences) {
                        if (!sentence.trim().isEmpty()) {
                            xmlWriter.writeStartElement("line");

                            xmlWriter.writeStartElement("number");
                            xmlWriter.writeCharacters(String.valueOf(paragraphLineNumber++));
                            xmlWriter.writeEndElement(); // Close number

                            xmlWriter.writeStartElement("sentence");
                            xmlWriter.writeCharacters(sentence.trim() + ".");
                            xmlWriter.writeEndElement();// Close sentence

                            xmlWriter.writeEndElement(); // Close line

                            lineNumber++;
                        }
                    }

                    xmlWriter.writeEndElement(); // Close paragraph
                    paragraphNumber++;
                    chapterLineCounter++;

                    if (chapterLineCounter == 20) {
                        xmlWriter.writeEndElement(); // Close chapter
                        chapterLineCounter = 0;
                        chapterNumber++;
                        paragraphChapterNumber = 0;

                        xmlWriter.writeStartElement("chapter");
                        xmlWriter.writeStartElement("number");
                        xmlWriter.writeCharacters(String.valueOf(chapterNumber));
                        xmlWriter.writeEndElement(); // Close number
                    }
                }
            }

            if (chapterLineCounter > 0) {
                xmlWriter.writeEndElement(); // Close chapter
            }

            xmlWriter.writeEndElement(); // Close chapters
            xmlWriter.writeStartElement("statistics");

            xmlWriter.writeStartElement("paragraphCount");
            xmlWriter.writeCharacters(String.valueOf(paragraphNumber - 1));
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("lineCount");
            xmlWriter.writeCharacters(String.valueOf(lineNumber - 1));
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("wordCount");
            xmlWriter.writeCharacters(String.valueOf(countWords(inputFileName)));
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("distinctWordCount");
            xmlWriter.writeCharacters(String.valueOf(countDistinctWords(inputFileName)));
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("creationDate");
            xmlWriter.writeCharacters(LocalDateTime.now().toString());
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("author");
            xmlWriter.writeCharacters("Apostolos Tourlidas");
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("applicationClassName");
            xmlWriter.writeCharacters("TxtToXml");
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();

            xmlWriter.writeEndElement();
            xmlWriter.writeEndDocument();
            xmlWriter.flush();

            xmlWriter.close();

            formatXml(stringWriter.toString(), outputFileName);

        } catch (FileNotFoundException | XMLStreamException | TransformerException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }

    /**
     * Counts the total number of words in the input text file.
     *
     * @param inputFileName the name of the input text file.
     * @return the total number of words in the file.
     * @throws IOException if there is an issue reading the input file.
     */
    private static int countWords(String inputFileName) throws IOException {
        int wordCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordCount += line.split(" ").length;
            }
        }
        return wordCount;
    }

    /**
     * Counts the number of distinct words in the input text file. cates.
     *
     * @param inputFileName the name of the input text file.
     * @return the total number of distinct words in the file.
     * @throws IOException if there is an issue reading the input file.
     */
    private static int countDistinctWords(String inputFileName) throws IOException {
        Set<String> distinctWords = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    distinctWords.add(word);
                }
            }
        }
        return distinctWords.size();
    }

    /**
     * Formats the XML string to be well-indented and writes it to the output
     * file.
     *
     * @param xml the XML content as a string.
     * @param outputFileName the name of the output XML file.
     * @throws TransformerException if there is an issue transforming the XML
     * content.
     */
    private static void formatXml(String xml, String outputFileName) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StreamSource source = new StreamSource(new StringReader(xml));
        StreamResult xmlOutput = new StreamResult(new File(outputFileName));

        transformer.transform(source, xmlOutput);
    }
}
