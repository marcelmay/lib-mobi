libmobi
=======================================

A simple lib for reading MOBI header data in Java.

The example standalone runner extracts a MOBI header and prints it as JSON.

Example (see MobiHeaderTest class)
----------------------------------

    try (final DataInputStream is = ...) { // Open stream for MOBI file
        final MobiHeader header = MobiHeader.read(is);
    
        System.out.println("ISBN: " +
          header.exthHeader.getRecordByTypeCode(MobiHeader.Exth.RecordType.ISBN).data);
        System.out.println("Language: " +
          header.exthHeader.getRecordByTypeCode(MobiHeader.Exth.RecordType.LANGUAGE).data);
    }

Standalone runner
-----------------
Extracts the MOBI header data and prints it JSON formatted.

```
java -jar standalone/target/libmobi-standalone-1.0-SNAPSHOT.jar my_book.mobi
```

How to build
-----

* Build from source 

  `mvn clean install`

  Make sure you got [Maven 3.2.1+][maven_download] and JDK 1.7+ .

[maven_download]: http://maven.apache.org
