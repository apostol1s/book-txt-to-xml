package com.mycompany.txttoxml.service;

import java.io.IOException;

public interface ConvertTxtToXml {
    void convertTxtToXml(String inputFileName, String outputFileName) throws IOException;
}
