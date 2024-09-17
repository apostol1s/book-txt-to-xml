package com.mycompany.txttoxml.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class SelectedElementsXmlImpl implements SelectedElementsXml {

    /**
     * Extracts the specified chapter and paragraph from the input XML file and
     * writes them to the output XML file, along with statistics.
     *
     * @param inputXmlFile The name of the input XML file.
     * @param outputXmlFile The name of the output XML file where the result
     * will be written.
     * @param targetChapter The number of the chapter to extract.
     * @param targetParagraph The number of the paragraph within the chapter to
     * extract.
     * @throws IOException if there is an issue opening or reading the XML file.
     */
    @Override
    public void extractChapterAndParagraph(String inputXmlFile, String outputXmlFile, int targetChapter, int targetParagraph) throws IOException {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(inputXmlFile));

            StringWriter stringWriter = new StringWriter();
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlWriter = outputFactory.createXMLStreamWriter(stringWriter);

            xmlWriter.writeStartDocument("UTF-8", "1.0");
            xmlWriter.writeStartElement("Selected_book");

            processXMLWithStats(eventReader, xmlWriter, targetChapter, targetParagraph);
            xmlWriter.writeEndElement(); // Close Selected_book
            xmlWriter.writeEndDocument();
            xmlWriter.flush();
            xmlWriter.close();

            formatXml(stringWriter.toString(), outputXmlFile);
        } catch (FileNotFoundException | XMLStreamException | TransformerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Processes the XML content and writes the selected chapter and paragraph
     * along with statistics.
     *
     * @param eventReader The XMLEventReader used to read the input XML.
     * @param xmlWriter The XMLStreamWriter used to write the selected elements
     * to the output XML.
     * @param targetChapter The number of the chapter to extract.
     * @param targetParagraph The number of the paragraph to extract within the
     * chapter.
     */
    private static void processXMLWithStats(XMLEventReader eventReader, XMLStreamWriter xmlWriter, int targetChapter, int targetParagraph) {
        try {
            String currentElement = null;
            String chapterNumber = null;
            String paragraphNumber = null;
            boolean isInTargetChapter = false;
            boolean isInTargetParagraph = false;
            int paragraphCounter = 0;
            int lineCounter = 0;
            int wordCounter = 0;
            Set<String> distinctWords = new HashSet<>();

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    switch (elementName) {
                        case "chapter":
                            currentElement = "chapter";
                            break;

                        case "paragraph":
                            currentElement = "paragraph";
                            if (isInTargetChapter && isInTargetParagraph) {
                                paragraphCounter++;
                            }
                            break;

                        case "line":
                            currentElement = "line";
                            if (isInTargetChapter && isInTargetParagraph) {
                                lineCounter++;
                            }
                            break;

                        case "number":
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                String currentText = event.asCharacters().getData().trim();

                                if (currentElement.equals("chapter")) {
                                    chapterNumber = currentText;
                                    if (targetChapter == Integer.parseInt(chapterNumber)) {
                                        isInTargetChapter = true;
                                        xmlWriter.writeStartElement("chapter");
                                        xmlWriter.writeStartElement("number");
                                        xmlWriter.writeCharacters(String.valueOf(targetChapter));
                                        xmlWriter.writeEndElement(); // Close number
                                        System.out.println("Chapter number " + targetChapter);
                                    } else {
                                        isInTargetChapter = false;
                                    }
                                }

                                if (currentElement.equals("paragraph")) {
                                    paragraphNumber = currentText;
                                    if (isInTargetChapter && targetParagraph == Integer.parseInt(paragraphNumber)) {
                                        isInTargetParagraph = true;
                                        xmlWriter.writeStartElement("paragraph");
                                        xmlWriter.writeStartElement("number");
                                        xmlWriter.writeCharacters(String.valueOf(targetParagraph));
                                        xmlWriter.writeEndElement(); // Close number
                                        System.out.println("Paragraph number" + targetParagraph);
                                    } else {
                                        isInTargetParagraph = false;
                                    }
                                }

                                if (currentElement.equals("line")) {
                                    if (isInTargetChapter && isInTargetParagraph) {
                                        xmlWriter.writeStartElement("line");
                                        xmlWriter.writeStartElement("number");
                                        xmlWriter.writeCharacters(String.valueOf(lineCounter));
                                        xmlWriter.writeEndElement(); // Close number
                                        System.out.println("Line number" + lineCounter);
                                    }
                                }
                            }
                            break;

                        case "sentence":
                            if (isInTargetChapter && isInTargetParagraph) {
                                event = eventReader.nextEvent();
                                if (event instanceof Characters) {
                                    String sentence = event.asCharacters().getData();
                                    xmlWriter.writeStartElement("sentence");
                                    xmlWriter.writeCharacters(sentence);
                                    xmlWriter.writeEndElement();
                                    System.out.println("Sentence: " + sentence);

                                    String[] words = sentence.split(" ");
                                    wordCounter += words.length;
                                    for (String word : words) {
                                        distinctWords.add(word.toLowerCase());
                                    }

                                    xmlWriter.writeEndElement(); // Close line
                                }
                            }
                            break;
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String elementName = endElement.getName().getLocalPart();

                    switch (elementName) {
                        case "chapter":
                            if (isInTargetChapter) {
                                xmlWriter.writeEndElement(); // Close chapter
                                System.out.println("Ending chapter " + targetChapter);
                            }
                            break;

                        case "paragraph":
                            if (isInTargetParagraph) {
                                xmlWriter.writeEndElement(); // Close paragraph
                                System.out.println("Ending paragraph " + targetParagraph);
                            }
                            break;
                    }
                }
            }

            xmlWriter.writeStartElement("statistics");
            xmlWriter.writeStartElement("paragraphCount");
            xmlWriter.writeCharacters(String.valueOf(paragraphCounter));
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("lineCount");
            xmlWriter.writeCharacters(String.valueOf(lineCounter));
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("wordCount");
            xmlWriter.writeCharacters(String.valueOf(wordCounter));
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("distinctWordCount");
            xmlWriter.writeCharacters(String.valueOf(distinctWords.size()));
            xmlWriter.writeEndElement();

            xmlWriter.writeEndElement();

        } catch (XMLStreamException e) {
            System.out.println("Error " + e.getMessage());
        }
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
