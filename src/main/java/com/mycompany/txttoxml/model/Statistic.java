package com.mycompany.txttoxml.model;

import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@XmlType(propOrder = { "paragraphCount", "lineCount", "wordCount", "distinctWordCount", "creationDate", "author", "applicationClassName" })
public class Statistic {
    private int paragraphCount;
    private int lineCount;
    private int wordCount;
    private int distinctWordCount;
    private String creationDate;
    private String author;
    private String applicationClassName;

    public int getParagraphCount() {
        return paragraphCount;
    }

    public void setParagraphCount(int paragraphCount) {
        this.paragraphCount = paragraphCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getDistinctWordCount() {
        return distinctWordCount;
    }

    public void setDistinctWordCount(int distinctWordCount) {
        this.distinctWordCount = distinctWordCount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getApplicationClassName() {
        return applicationClassName;
    }

    public void setApplicationClassName(String applicationClassName) {
        this.applicationClassName = applicationClassName;
    }
}
