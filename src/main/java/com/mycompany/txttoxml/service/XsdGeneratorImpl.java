package com.mycompany.txttoxml.service;

import com.mycompany.txttoxml.model.Book;
import com.mycompany.txttoxml.model.Chapter;
import com.mycompany.txttoxml.model.Line;
import com.mycompany.txttoxml.model.Paragraph;
import com.mycompany.txttoxml.model.Statistic;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class XsdGeneratorImpl implements XsdGenerator {

    /**
     * Generates an XML Schema (XSD) based on the JAXB-annotated Java classes
     * (`Book`, `Chapter`, `Line`, `Paragraph`, and `Statistic`).
     *
     * @throws IOException If an I/O error occurs during the schema generation
     * process.
     */
    @Override
    public void xsdGenerator() throws IOException {

        try {
            String xsdFileName = "xml/book-schema.xsd";
            JAXBContext context = JAXBContext.newInstance(Book.class, Chapter.class, Line.class, Paragraph.class, Statistic.class);

            context.generateSchema(new MySchemaOutputResolver(xsdFileName));

        } catch (JAXBException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    static class MySchemaOutputResolver extends SchemaOutputResolver {

        private String xsdFileName;

        public MySchemaOutputResolver(String xsdFileName) {
            this.xsdFileName = xsdFileName;
        }

        /**
         * Creates and returns a Result object that defines where to write the
         * generated schema.
         *
         * @param namespaceUri The namespace URI.
         * @param suggestedFileName A suggested name for the schema file.
         * @return A Result object containing the output stream for the XSD
         * schema file.
         * @throws IOException If an I/O error occurs while creating the file.
         */
        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            File file = new File(xsdFileName);
            StreamResult result = new StreamResult(file);

            result.setSystemId(file.toURI().toString());
            return result;
        }
    }
}
