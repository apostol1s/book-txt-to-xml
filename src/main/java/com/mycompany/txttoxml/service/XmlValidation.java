package com.mycompany.txttoxml.service;

import java.io.IOException;

public interface XmlValidation {
    boolean validateXml(String xmlFilePath, String xsdFilePath) throws IOException;
}
