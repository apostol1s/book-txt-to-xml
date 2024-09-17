package com.mycompany.txttoxml.service;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public class XmlValidationImpl implements XmlValidation {

    /**
     * Validates an XML file against a given XSD schema.
     *
     * @param xmlFileName The name of the XML file to be validated.
     * @param xsdFileName The name of the XSD schema file to validate against.
     * @return true if the XML file is valid according to the XSD schema, false
     * otherwise.
     * @throws IOException If an input or output exception occurred while
     * reading the XML or XSD file.
     */
    @Override
    public boolean validateXml(String xmlFileName, String xsdFileName) throws IOException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(new File(xsdFileName));

            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new File(xmlFileName)));
            System.out.println("XML is valid.");
            return true;
        } catch (SAXException e) {
            System.out.println("XML is NOT valid. " + e.getMessage());
            return false;
        }
    }
}
