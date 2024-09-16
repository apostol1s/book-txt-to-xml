package com.mycompany.txttoxml.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    private int id;
    private int paragraphCount;
    private int lineCount;
    private int wordCount;
    private int distinctWordCount;
    private LocalDateTime creationDate;
    private String author;
    private String applicationClassName;
}
