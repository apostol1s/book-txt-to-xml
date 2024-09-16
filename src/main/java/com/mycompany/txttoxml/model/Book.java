package com.mycompany.txttoxml.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Book {
   private List<Chapter> chapters;
   private Statistic statistics;
}
