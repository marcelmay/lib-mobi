package de.m3y.mobi.core;

import de.m3y.mobi.core.MobiHeader;
import de.m3y.mobi.core.PalmDatabase;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MobiHeaderTest {

    /**
     Output from https://github.com/gluggy/Java-Mobi-Metadata-Editor.git :

     PDB Header
     ----------
     Name: Pro_Git
     Version: 0
     Creation Date: 1452046139
     Modification Date: 1452046149
     Last Backup Date: 0
     Modification Number: 0
     App Info ID: 0
     Sort Info ID: 0
     Type: 1112493899
     Creator: 1297039945
     Unique ID Seed: 2353

     PalmDOC Header
     --------------
     Compression: PalmDOC
     Text Length: 1373193
     Record Count: 336
     Record Size: 4096
     Encryption Type: None

     MOBI Header
     -----------
     Header Length: 264
     Mobi Type: Mobipocket Book
     Unique ID: 3802746950
     File Version: 6
     Orthographic Index: 4294967295
     Inflection Index: 4294967295
     Index Names: 4294967295
     Index Keys: 4294967295
     Extra Index 0: 4294967295
     Extra Index 1: 4294967295
     Extra Index 2: 4294967295
     Extra Index 3: 4294967295
     Extra Index 4: 4294967295
     Extra Index 5: 4294967295
     First Non-Book Index: 337
     Full Name Offset: 616
     Full Name Length: 7
     Min Version: 6
     Huffman Record Offset: 0
     Huffman Record Count: 0
     Huffman Table Offset: 0
     Huffman Table Length: 0

     */
    @Test
    public void testReadMobiHeader() throws IOException {
        try (final DataInputStream is = new DataInputStream(new BufferedInputStream(
                getClass().getResourceAsStream("/progit-en.984.mobi")))) {
            final MobiHeader header = MobiHeader.read(is);
            assertNotNull(header);

            PalmDatabase.Header palmDatabaseHeader = header.palmDatabaseHeader;
            assertEquals("Pro_Git", palmDatabaseHeader.name);
            assertEquals(0, palmDatabaseHeader.version);
            assertEquals(1452046139, palmDatabaseHeader.creationDate.getTime()/1000L);
            assertEquals(1452046149, palmDatabaseHeader.modificationDate.getTime()/1000L);
            assertEquals(0, palmDatabaseHeader.lastBackupDate.getTime());
            assertEquals(0, palmDatabaseHeader.modificationNumber);
            assertEquals(0, palmDatabaseHeader.appInfoId);
            assertEquals(0, palmDatabaseHeader.sortInfoId);
            assertEquals("BOOK", palmDatabaseHeader.type);
            assertEquals("MOBI", palmDatabaseHeader.creator);
            assertEquals(2353, palmDatabaseHeader.uniqueIdSeed);

            assertEquals(MobiHeader.MobiType.MOBIPOCKET_BOOK, header.mobiType);
            assertEquals(264, header.headerLength);
            assertEquals(-492220346, header.uniqueId);
            assertEquals(616, header.fullNameOffset);
            assertEquals(7, header.fullNameLength);
            assertEquals(6, header.minVersion);
            assertEquals(337, header.firstNonBookIndex);
            assertEquals(616, header.fullNameOffset);
            assertEquals(7, header.fullNameLength);
            assertEquals(header.huffmanRecordOffset, 0);
            assertEquals(header.huffmanRecordCount, 0);
            assertEquals(header.huffmanTableLength, 0);
            assertEquals(header.huffmanTableOffset, 0);

            assertEquals("en", header.exthHeader.getRecordByTypeCode(MobiHeader.Exth.RecordType.LANGUAGE).data);
        }
    }
}
