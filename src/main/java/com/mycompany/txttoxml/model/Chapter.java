package com.mycompany.txttoxml.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Chapter {    
    private int number;
    private List<Paragraph> paragraph;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    
    public List<Paragraph> getParagraph() {
        return paragraph;
    }

    public void setParagraph(List<Paragraph> paragraph) {
        this.paragraph = paragraph;
    }
}
