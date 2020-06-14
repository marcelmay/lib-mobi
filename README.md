libmobi
=======================================
[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/apache/maven.svg?label=License)](LICENSE)

A simple lib for reading MOBI header data in Java.

The example standalone runner extracts a MOBI - or AZW3 - header and prints it as JSON.

Example (see [MobiHeaderTest](core/test/java/de/m3y/mobi/core/MobiHeaderTest.java) class)
----------------------------------

```java
try (final DataInputStream is = ...) { // Open stream for MOBI file
    final MobiHeader header = MobiHeader.read(is);

    System.out.println("ISBN: " +
        header.exthHeader.getRecordByTypeCode(MobiHeader.Exth.RecordType.ISBN).data);
    System.out.println("Language: " +
        header.exthHeader.getRecordByTypeCode(MobiHeader.Exth.RecordType.LANGUAGE).data);
}
```

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

  Make sure you got [Maven 3.6+][maven_download] and JDK 1.8+ .

[maven_download]: http://maven.apache.org

Mics
------
Licensed under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
