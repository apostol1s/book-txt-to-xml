# book-txt-to-xml

## Overview

The TxtToXml Converter is an application that converts a text file (.txt) into an XML file. Additionally, the application:

- Generates an XML with a specific data structure (including chapters, paragraphs, lines, and statistics).
- Creates an XSD (XML Schema) that describes the structure of the generated XML.
- Validates the XML file against the corresponding XSD.

## Steps to Run the Project on Windows

1. **Clone the Repository**
   ```bash
   git clone https://github.com/apostol1s/book-txt-to-xml.git
   ```

2. **Navigate to the project directory**
   ```bash
   C:\Users\<yourPath>\book-txt-to-xml
   ```

3. **Open command prompt and run**
   ```bash
   mvn clean compile exec:java
   ```   
