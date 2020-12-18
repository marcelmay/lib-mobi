libmobi
=======================================
![Maven Central](https://img.shields.io/maven-central/v/de.m3y.libmobi/libmobi-core) [![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/apache/maven.svg?label=License)](LICENSE)

A simple lib for reading MOBI header data in Java.

The example standalone runner extracts a MOBI - or AZW3 - header and prints it as JSON.

Example (see [MobiHeaderTest](core/src/test/java/de/m3y/mobi/core/MobiHeaderTest.java) class)
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

```bash
java -jar standalone/target/libmobi-standalone-1.0-SNAPSHOT.jar my_book.mobi
```

How to build
-----

* Build from source 

  `mvn clean install`

  Make sure you got [Maven 3.6+][maven_download] and JDK 1.8+ .

[maven_download]: http://maven.apache.org

Example output from standalone runner
------

```bash
java -jar standalone/target/libmobi-standalone-1.0.jar ./core/src/test/resources/progit-en.984.mobi
```
```json
{
  "MobiHeader" : {
    "palmDatabaseHeader" : {
      "name" : "Pro_Git",
      "attributes" : 0,
      "version" : 0,
      "creationDate" : 1452046139000,
      "modificationDate" : 1452046149000,
      "nextRecordListId" : 0,
      "modificationNumber" : 0,
      "lastBackupDate" : 0,
      "appInfoId" : 0,
      "sortInfoId" : 0,
      "type" : "BOOK",
      "creator" : "MOBI",
      "uniqueIdSeed" : 2353,
      "numRecords" : 1176
    },
    "compression" : "OLD_MOBIPOCKET_COMPRESSION",
    "textLength" : 1373193,
    "recordCount" : 336,
    "recordSize" : 4096,
    "encryptionType" : 0,
    "identifier" : "MOBI",
    "headerLength" : 264,
    "mobiType" : "MOBIPOCKET_BOOK",
    "encoding" : "UTF-8",
    "uniqueId" : -492220346,
    "fileVersion" : 6,
    "ortographicIndex" : -1,
    "inflectionIndex" : -1,
    "indexNames" : -1,
    "indexKeys" : -1,
    "extraIndex" : [ -1, -1, -1, -1, -1, -1 ],
    "firstNonBookIndex" : 337,
    "fullNameOffset" : 616,
    "fullNameLength" : 7,
    "locale" : 9,
    "inputLanguage" : 0,
    "outputLanguage" : 0,
    "minVersion" : 6,
    "firstImageIndex" : 340,
    "huffmanRecordOffset" : 0,
    "huffmanRecordCount" : 0,
    "huffmanTableOffset" : 0,
    "huffmanTableLength" : 0,
    "hasExth" : true,
    "exthHeader" : {
      "identifier" : "EXTH",
      "headerLength" : 336,
      "recordCount" : 18,
      "records" : [ {
        "typeCode" : 116,
        "typeLabel" : "START_READING",
        "length" : 12,
        "data" : "\u0000\u0000\u0000("
      }, {
        "typeCode" : 542,
        "typeLabel" : "UNKNOWN_BUT_CHANGES_WITH_FILE_NAME",
        "length" : 12,
        "data" : "ExGK"
      }, {
        "typeCode" : 524,
        "typeLabel" : "LANGUAGE",
        "length" : 10,
        "data" : "en"
      }, {
        "typeCode" : 525,
        "typeLabel" : "ALIGNMENT",
        "length" : 21,
        "data" : "horizontal-lr"
      }, {
        "typeCode" : 129,
        "typeLabel" : "KF8_COVER_URI",
        "length" : 25,
        "data" : "kindle:embed:0069"
      }, {
        "typeCode" : 131,
        "typeLabel" : "UNKNOWN",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\uFFFD"
      }, {
        "typeCode" : 300,
        "typeLabel" : "FONT_SIGNATURE",
        "length" : 78,
        "data" : "\u0007\u0000\u0000\u0000\uFFFD(\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\uFFFD\u0000 \u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\uFFFD\uFFFD\uFFFD\u0001\uFFFD\u0002\uFFFD@\uFFFD@\uFFFD@\uFFFD@\uFFFD@\uFFFD@\uFFFD@\uFFFDB\uFFFDC\uFFFDF\uFFFDJ\uFFFDJ\uFFFDJ\uFFFDJ\uFFFDK\uFFFD"
      }, {
        "typeCode" : 204,
        "typeLabel" : "CREATOR_SOFTWARE_RECORDS",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\uFFFD"
      }, {
        "typeCode" : 205,
        "typeLabel" : "CREATOR_MAJOR_VERSION",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\u0002"
      }, {
        "typeCode" : 206,
        "typeLabel" : "CREATOR_MINOR_VERSION",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\t"
      }, {
        "typeCode" : 535,
        "typeLabel" : "KINDLEGEN_BUILDREV_NUMBER",
        "length" : 20,
        "data" : "0730-890adc2"
      }, {
        "typeCode" : 207,
        "typeLabel" : "CREATOR_BUILD_NUMBER",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\u0000"
      }, {
        "typeCode" : 125,
        "typeLabel" : "COUNT_OF_RESOURCES",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\uFFFD"
      }, {
        "typeCode" : 201,
        "typeLabel" : "COVER_OFFSET",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\u0000"
      }, {
        "typeCode" : 203,
        "typeLabel" : "HAS_FAKE_COVER",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\u0000"
      }, {
        "typeCode" : 202,
        "typeLabel" : "THUMB_OFFSET",
        "length" : 12,
        "data" : "\u0000\u0000\u0000\uFFFD"
      }, {
        "typeCode" : 121,
        "typeLabel" : "KF8_BOUNDARY_OFFSET",
        "length" : 12,
        "data" : "\u0000\u0000\u0002#"
      }, {
        "typeCode" : 536,
        "typeLabel" : "CONTAINER_INFO",
        "length" : 24,
        "data" : "2400x3840:0-202|"
      } ]
    }
  }
}
```

Misc
------
Licensed under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)

