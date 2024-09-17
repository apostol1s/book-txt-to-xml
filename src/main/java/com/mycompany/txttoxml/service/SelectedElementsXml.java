package com.mycompany.txttoxml.service;

import java.io.IOException;

public interface SelectedElementsXml {
    void extractChapterAndParagraph(String inputXmlFile, String outputXmlFile, int targetChapter, int targetParagraph) throws IOException;
}
