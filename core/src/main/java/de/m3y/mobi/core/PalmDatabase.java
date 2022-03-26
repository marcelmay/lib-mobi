package de.m3y.mobi.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

/**
 * Palm database (PDB) contains one header and zero to many records.
 * <p/>
 * http://wiki.mobileread.com/wiki/PDB
 */
public class PalmDatabase {
    /**
     * A PDB record entry.
     */
    public static class Record {
        public int dataOffset; // 4 byte
        public byte attributes; // 1 byte
        public int uniqueID; // 3 bytes

        /**
         * Reads a record from stream.
         *
         * @param is the input stream.
         * @return the representing header.
         * @throws IOException on error.
         */
        public static Record read(DataInputStream is) throws IOException {
            Record record = new Record();
            record.dataOffset = is.readInt();
            record.attributes = is.readByte();
            record.uniqueID = ByteBuffer.wrap(new byte[]{0, is.readByte(), is.readByte(), is.readByte()}).getInt();
            return record;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "dataOffset=" + dataOffset +
                    ", attributes=" + attributes +
                    ", uniqueID=" + uniqueID +
                    '}';
        }
    }

    /**
     * A PDB header.
     */
    public static class Header {
        public String name; // 32byte
        public short attributes;
        public MobiHeader.CompressionType compression; // 2 byte
        public short version;
        public Date creationDate;
        public Date modificationDate;
        public int nextRecordListId;
        public int modificationNumber;
        public Date lastBackupDate;
        public int appInfoId;
        public int sortInfoId;
        public String type;
        public String creator;
        public int uniqueIdSeed;
        public short numRecords;
        public Record[] records;


        /**
         * Reads header and records from stream.
         *
         * @param is the input stream.
         * @return the representing header.
         * @throws IOException on error.
         *                     <p>
         *                     See https://wiki.mobileread.com/wiki/PDB#Palm_Database_Format and
         *                     https://wiki.mobileread.com/wiki/MOBI
         */
        public static Header read(DataInputStream is) throws IOException {
            Header header = new Header();

            header.name = StreamHelper.readStringTillNull(is, 32, StandardCharsets.ISO_8859_1);

            header.attributes = is.readShort();
            header.version = is.readShort();
            header.creationDate = convertPdpTimeToDate(is.readInt());
            header.modificationDate = convertPdpTimeToDate(is.readInt());
            header.lastBackupDate = convertPdpTimeToDate(is.readInt());
            header.modificationNumber = is.readInt();
            header.appInfoId = is.readInt();
            header.sortInfoId = is.readInt();
            header.type = StreamHelper.readString(is, 4, StandardCharsets.ISO_8859_1);
            header.creator = StreamHelper.readString(is, 4, StandardCharsets.ISO_8859_1);
            header.uniqueIdSeed = is.readInt();
            header.nextRecordListId = is.readInt();
            header.numRecords = is.readShort();
            header.records = new Record[header.numRecords];
            for (int i = 0; i < header.records.length; i++) {
                header.records[i] = Record.read(is);
            }

            // 2 byte gap
            is.skipBytes(2);

            return header;
        }

        private static Date convertPdpTimeToDate(int pdpTime) {
            // If the time has the top bit set, it's an unsigned 32-bit number counting from 1st Jan 1904
            // If the time has the top bit clear, it's a signed 32-bit number counting from 1st Jan 1970.
            if ((pdpTime & Integer.MIN_VALUE) == Integer.MIN_VALUE) {
                // -2082848400000 == new java.text.SimpleDateFormat('dd MMM yyyy').parse("1 Jan 1904").getTime()
                long converted = -2082848400000L + ((long) pdpTime) * 1000L;
                return new Date(converted);
            } else {
                return new Date(pdpTime * 1000L);
            }
        }

        @Override
        public String toString() {
            return "PalmDOCHeader{" +
                    "name='" + name + '\'' +
                    ", attributes=" + attributes +
                    ", version=" + version +
                    ", creationDate=" + creationDate +
                    ", modificationDate=" + modificationDate +
                    ", nextRecordListId=" + nextRecordListId +
                    ", lastBackupDate=" + lastBackupDate +
                    ", modificationNumber=" + modificationNumber +
                    ", appInfoId=" + appInfoId +
                    ", sortInfoId=" + sortInfoId +
                    ", type=" + type +
                    ", creator=" + creator +
                    ", uniqueIdSeed=" + uniqueIdSeed +
                    ", compression=" + compression +
                    ", numRecords=" + numRecords +
                    ", records=" + (records.length > 10 ? "... (too long)" : Arrays.toString(records)) +
                    '}';
        }
    }
}
