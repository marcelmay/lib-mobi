package de.m3y.mobi.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads MOBI header data.
 * <p/>
 * Example:
 * <code>
 * try (final DataInputStream is = new DataInputStream(new BufferedInputStream(
 * getClass().getResourceAsStream("/progit-en.984.mobi")))) {
 * final MobiHeader header = MobiHeader.read(is);
 * </code>
 * <p/>
 * For more info about MOBI, see http://wiki.mobileread.com/wiki/MOBI
 */
public class MobiHeader {

    /**
     * Reads MOBI EXTH.
     * <p/>
     * See http://wiki.mobileread.com/wiki/MOBI#EXTH_Header
     */
    public static class Exth {
        /**
         * A EXTH record.
         */
        public static class Record {
            public int typeCode;
            public String typeLabel;
            public int length;
            public String data;

            /**
             * Reads a new record from stream.
             *
             * @param is the input stream.
             * @return the representing header.
             * @throws IOException on error.
             */
            public static Record read(DataInputStream is) throws IOException {
                Record record = new Record();
                record.typeCode = is.readInt();
                record.typeLabel = RecordType.getLabel(record.typeCode);
                record.length = is.readInt();
                record.data = StreamHelper.readString(is, record.length - 8 /* size of type and length */);
                return record;
            }

            @Override
            public String toString() {
                return "Record{" +
                        "typeCode=" + typeCode +
                        ", typeCodeLabel=" + RecordType.getLabel(typeCode) +
                        ", length=" + length +
                        ", data='" + data + '\'' +
                        '}';
            }
        }

        /**
         * Helper for dealing with EXTH Header types.
         * <p/>
         * See http://wiki.mobileread.com/wiki/MOBI#EXTH_Header
         * and https://github.com/dougmassay/kindleunpack-calibre-plugin/blob/master/core_subtree/lib/mobi_header.py
         */
        public static class RecordType {
            public static final int DRM_SERVER_ID = 1;
            public static final int DRM_COMMERCE_ID = 2;
            public static final int DRM_EBOOKBASE_BOOK_ID = 3;
            public static final int AUTHOR = 100;
            public static final int PUBLISHER = 101;
            public static final int IMPRINT = 102;
            public static final int DESCRIPTION = 103;
            public static final int ISBN = 104;
            public static final int SUBJECT = 105;
            public static final int PUBLISHING_DATE = 106;
            public static final int REVIEW = 107;
            public static final int CONTRIBUTOR = 108;
            public static final int RIGHTS = 109;
            public static final int SUBJECT_CODE = 110;
            public static final int TYPE = 111;
            public static final int SOURCE = 112;
            public static final int ASIN = 113;
            public static final int VERSION_NUMBER = 114;
            public static final int SAMPLE = 115;
            public static final int START_READING = 116;
            public static final int ADULT = 117;                    // Mobipocket Creator adds this if Adult only is checked; contents: "yes"
            public static final int RETAIL_PRICE = 118;             // As text, e.g. "4.99"
            public static final int RETAIL_PRICE_CURRENCY = 119;    // As text, e.g. "USD"
            public static final int KF8_BOUNDARY_OFFSET = 121;
            public static final int FIXED_LAYOUT = 122;
            public static final int BOOK_TYPE = 123;
            public static final int ORIENTATION_LOCK = 124;
            public static final int COUNT_OF_RESOURCES = 125;
            public static final int ORIGINAL_RESOLUTION = 126;
            public static final int ZERO_GUTTER = 127;
            public static final int ZERO_MARGIN = 128;
            public static final int KF8_COVER_URI = 129;
            public static final int REGION_MAGNIFICATION = 132;
            public static final int DICT_SHORT_NAME = 200;
            public static final int COVER_OFFSET = 201;             // Add to first image field in Mobi Header to find PDB record containing the cover image
            public static final int THUMB_OFFSET = 202;             // Add to first image field in Mobi Header to find PDB record containing the thumbnail cover image
            public static final int HAS_FAKE_COVER = 203;
            public static final int CREATOR_SOFTWARE_RECORDS = 204; // 204-207 are usually the same for all books from a certain source
            public static final int CREATOR_MAJOR_VERSION = 205;
            public static final int CREATOR_MINOR_VERSION = 206;
            public static final int CREATOR_BUILD_NUMBER = 207;
            public static final int WATERMARK = 208;
            public static final int TAMPER_PROOF_KEYS = 209;        // Used by the Kindle  = and Android app) for generating book-specific PIDs.
            public static final int FONT_SIGNATURE = 300;
            public static final int CLIPPING_LIMIT = 401;
            public static final int PUBLISHER_LIMIT = 402;
            public static final int TEXT_TO_SPEECH = 403;           // 1 - Text to Speech disabled; 0 - Text to Speech enabled
            public static final int TTS_FLAG = 404;
            public static final int RENT_BORROW_FLAG = 405;
            public static final int RENT_BORROW_EXPIRATION = 406;
            public static final int CDE_TYPE = 501;                 // PDOC - Personal Doc EBOK - ebook;
            public static final int LAST_UPDATE_TIME = 502;
            public static final int UPDATED_TITLE = 503;
            public static final int ASIN_2 = 504; // Twice?
            public static final int UNKNOWN_TITLE_FURIGANA = 508;
            public static final int UNKNOWN_CREATOR_FURIGANA = 517;
            public static final int UNKNOWN_PUBLISHER_FURIGANA = 522;
            public static final int LANGUAGE = 524;
            public static final int ALIGNMENT = 525;                    // primary writing mode?
            public static final int PAGE_PROGRESSION = 527;
            public static final int OVERRIDE_KINDLE_FONTS = 528;
            public static final int KINDLEGEN_SOURCE_TARGET = 529;
            public static final int INPUT_SOURCE_TYPE = 534;
            public static final int KINDLEGEN_BUILDREV_NUMBER = 535;
            public static final int CONTAINER_INFO = 536;
            public static final int CONTAINER_RESOLUTION = 538;
            public static final int CONTAINER_MIMETYPE = 539;
            public static final int UNKNOWN_BUT_CHANGES_WITH_FILE_NAME = 542;
            public static final int CONTAINER_ID = 543;
            public static final int IN_MEMORY = 547;

            /**
             * Gets a text representation of header type (best effort).
             *
             * @param typeCode the EXTH header type code.
             * @return the label or "UNKNOWN"
             */
            public static String getLabel(int typeCode) {
                switch (typeCode) {
                    case 1:
                        return "DRM_SERVER_ID";
                    case 2:
                        return "DRM_COMMERCE_ID";
                    case 3:
                        return "DRM_EBOOKBASE_BOOK_ID";
                    case 100:
                        return "AUTHOR";
                    case 101:
                        return "PUBLISHER";
                    case 102:
                        return "IMPRINT";
                    case 103:
                        return "DESCRIPTION";
                    case 104:
                        return "ISBN";
                    case 105:
                        return "SUBJECT";
                    case 106:
                        return "PUBLISHING_DATE";
                    case 107:
                        return "REVIEW";
                    case 108:
                        return "CONTRIBUTOR";
                    case 109:
                        return "RIGHTS";
                    case 110:
                        return "SUBJECT_CODE";
                    case 111:
                        return "TYPE";
                    case 112:
                        return "SOURCE";
                    case 113:
                        return "ASIN";
                    case 114:
                        return "VERSION_NUMBER";
                    case 115:
                        return "SAMPLE";
                    case 116:
                        return "START_READING";
                    case 117:
                        return "ADULT";
                    case 118:
                        return "RETAIL_PRICE";
                    case 119:
                        return "RETAIL_PRICE_CURRENCY";
                    case 121:
                        return "KF8_BOUNDARY_OFFSET";
                    case 122:
                        return "FIXED_LAYOUT";
                    case 123:
                        return "BOOK_TYPE";
                    case 124:
                        return "ORIENTATION_LOCK";
                    case 125:
                        return "COUNT_OF_RESOURCES";
                    case 126:
                        return "ORIGINAL_RESOLUTION";
                    case 127:
                        return "ZERO_GUTTER";
                    case 128:
                        return "ZERO_MARGIN";
                    case 129:
                        return "KF8_COVER_URI";
                    case 132:
                        return "REGION_MAGNIFICATION";
                    case 200:
                        return "DICT_SHORT_NAME";
                    case 201:
                        return "COVER_OFFSET";
                    case 202:
                        return "THUMB_OFFSET";
                    case 203:
                        return "HAS_FAKE_COVER";
                    case 204:
                        return "CREATOR_SOFTWARE_RECORDS";
                    case 205:
                        return "CREATOR_MAJOR_VERSION";
                    case 206:
                        return "CREATOR_MINOR_VERSION";
                    case 207:
                        return "CREATOR_BUILD_NUMBER";
                    case 208:
                        return "WATERMARK";
                    case 209:
                        return "TAMPER_PROOF_KEYS";
                    case 300:
                        return "FONT_SIGNATURE";
                    case 401:
                        return "CLIPPING_LIMIT";
                    case 402:
                        return "PUBLISHER_LIMIT";
                    case 403:
                        return "TEXT_TO_SPEECH";
                    case 404:
                        return "TTS_FLAG";
                    case 405:
                        return "RENT_BORROW_FLAG";
                    case 406:
                        return "RENT_BORROW_EXPIRATION";
                    case 501:
                        return "CDE_TYPE";
                    case 502:
                        return "LAST_UPDATE_TIME";
                    case 503:
                        return "UPDATED_TITLE";
                    case 504:
                        return "ASIN_2";
                    case 508:
                        return "UNKNOWN_TITLE_FURIGANA";
                    case 517:
                        return "UNKNOWN_CREATOR_FURIGANA";
                    case 522:
                        return "UNKNOWN_PUBLISHER_FURIGANA";
                    case 524:
                        return "LANGUAGE";
                    case 525:
                        return "ALIGNMENT";
                    case 527:
                        return "PAGE_PROGRESSION";
                    case 528:
                        return "OVERRIDE_KINDLE_FONTS";
                    case 529:
                        return "KINDLEGEN_SOURCE_TARGET";
                    case 534:
                        return "INPUT_SOURCE_TYPE";
                    case 535:
                        return "KINDLEGEN_BUILDREV_NUMBER";
                    case 536:
                        return "CONTAINER_INFO";
                    case 538:
                        return "CONTAINER_RESOLUTION";
                    case 539:
                        return "CONTAINER_MIMETYPE";
                    case 542:
                        return "UNKNOWN_BUT_CHANGES_WITH_FILE_NAME";
                    case 543:
                        return "CONTAINER_ID";
                    case 547:
                        return "IN_MEMORY";

                    default:
                        return "UNKNOWN";
                }
            }
        }

        /**
         * Represents an EXTH header, including EXTH records.
         */
        public static class Header {
            public String identifier;
            public int headerLength;
            public int recordCount;
            public Record[] records;
            private Map<Integer, Record> recordMap;

            /**
             * Reads EXTH header and records.
             *
             * @param is the input stream.
             * @return the EXTH header including records.
             * @throws IOException on error.
             */
            public static Header read(DataInputStream is) throws IOException {
                Header header = new Header();
                header.identifier = StreamHelper.readString(is, 4);
                if (!"EXTH".equals(header.identifier)) {
                    throw new IllegalStateException("Expected EXTH header to start with identifier EXTH but got " + header.identifier);
                }
                header.headerLength = is.readInt();
                header.recordCount = is.readInt();
                header.records = new Record[header.recordCount];
                header.recordMap = new HashMap<>(header.recordCount);
                for (int i = 0; i < header.recordCount; i++) {
                    final Record record = Record.read(is);
                    header.records[i] = record;
                    header.recordMap.put(Integer.valueOf(record.typeCode), record);
                }
                // Null bytes to pad the EXTH header to a multiple of four bytes (none if the header is already
                // a multiple of four). This padding is not included in the EXTH header length.
                is.skipBytes(4 - header.headerLength % 4);
                return header;
            }

            /**
             * Gets an EXTH records by record type code.
             *
             * @param recordTypeCode the record type code, eg 104 for ISBN.
             * @return the record or null if not available.
             */
            public Record getRecordByTypeCode(int recordTypeCode) {
                return recordMap.get(Integer.valueOf(recordTypeCode));
            }

            @Override
            public String toString() {
                return "Header{" +
                        "identifier='" + identifier + '\'' +
                        ", headerLength=" + headerLength +
                        ", recordCount=" + recordCount +
                        ", records=" + Arrays.toString(records) +
                        '}';
            }
        }
    }

    public PalmDatabase.Header palmDatabaseHeader;
    public CompressionType compression;
    public int textLength;
    public short recordCount;
    public short recordSize;
    public short encryptionType;
    public String identifier;
    public int headerLength;
    public MobiType mobiType;
    public Charset encoding;
    public int uniqueId;
    public int fileVersion;
    public int ortographicIndex;
    public int inflectionIndex;
    public int indexNames;
    public int indexKeys;
    public int[] extraIndex = new int[6];
    public int firstNonBookIndex;
    public int fullNameOffset;
    public int fullNameLength;
    public int locale;
    public int inputLanguage;
    public int outputLanguage;
    public int minVersion;
    public int firstImageIndex;
    public int huffmanRecordOffset;
    public int huffmanRecordCount;
    public int huffmanTableOffset;
    public int huffmanTableLength;
    public boolean hasExth;
    public Exth.Header exthHeader;

//    int drmOffset;
//    int drmCount;
//    int drmSize;
//    int drmFlags;

    public static MobiHeader read(DataInputStream is) throws IOException {
        final MobiHeader header = new MobiHeader();
        header.palmDatabaseHeader = PalmDatabase.Header.read(is);

        // http://wiki.mobileread.com/wiki/MOBI#PalmDOC_Header
        header.compression = CompressionType.convert(is.readShort());
        is.skipBytes(2);
        header.textLength = is.readInt();
        header.recordCount = is.readShort();
        header.recordSize = is.readShort();
        header.encryptionType = is.readShort();
        is.skipBytes(2);

        // http://wiki.mobileread.com/wiki/MOBI#MOBI_Header
        header.identifier = StreamHelper.readString(is, 4);
        header.headerLength = is.readInt();
        header.mobiType = MobiType.convert(is.readInt());
        int encoding = is.readInt();
        switch (encoding) {
            case 1252:
                header.encoding = Charset.forName("CP1252");
                break;
            case 65001:
                header.encoding = StandardCharsets.UTF_8;
                break;
            default:
                throw new IllegalArgumentException("Unsupported encoding type " + encoding);
        }
        header.uniqueId = is.readInt();
        header.fileVersion = is.readInt();
        header.ortographicIndex = is.readInt();
        header.inflectionIndex = is.readInt();
        header.indexNames = is.readInt();
        header.indexKeys = is.readInt();
        header.extraIndex[0] = is.readInt();
        header.extraIndex[1] = is.readInt();
        header.extraIndex[2] = is.readInt();
        header.extraIndex[3] = is.readInt();
        header.extraIndex[4] = is.readInt();
        header.extraIndex[5] = is.readInt();
        header.firstNonBookIndex = is.readInt();
        header.fullNameOffset = is.readInt();
        header.fullNameLength = is.readInt();
        header.locale = is.readInt();
        header.inputLanguage = is.readInt();
        header.outputLanguage = is.readInt();
        header.minVersion = is.readInt();
        header.firstImageIndex = is.readInt();
        header.huffmanRecordOffset = is.readInt();
        header.huffmanRecordCount = is.readInt();
        header.huffmanTableOffset = is.readInt();
        header.huffmanTableLength = is.readInt();
        header.hasExth = ((is.readInt() & 0x40) != 0);
        is.skipBytes(header.headerLength - 132 + 16);
//        header.drmOffset = is.readInt();
//        header.drmCount = is.readInt();
//        header.drmSize = is.readInt();
//        header.drmFlags = is.readInt();

        if (header.hasExth) {
            header.exthHeader = Exth.Header.read(is);
        }

        return header;
    }

    public enum CompressionType {
        NO_COMPRESSION,
        OLD_MOBIPOCKET_COMPRESSION,
        HUFF_CDIC_COMPRESSION;

        public static CompressionType convert(short s) {
            switch (s) {
                case 1:
                    return NO_COMPRESSION;
                case 2:
                    return OLD_MOBIPOCKET_COMPRESSION;
                case 17480:
                    return HUFF_CDIC_COMPRESSION;
                default:
                    throw new IllegalArgumentException("Invalid input : " + s);
            }
        }
    }

    public enum MobiType {
        MOBIPOCKET_BOOK(2),
        PALM_DOC_BOOK(3),
        AUDIO(4),
        MOBIPOCKET_GENERATED_BY_KINDELGEN_1_2(232),
        KF8_GENERATED_BY_KINDLEGEN_2(248),
        NEWS(257),
        NEWS_FEED(258),
        NEWS_MAGAZINE(259),
        PICS(513),
        WORD(514),
        XLS(515),
        PPT(516),
        TEXT(517),
        HTML(518);

        int code;

        MobiType(int code) {
            this.code = code;
        }

        /**
         * Converts MOBI type code into enum.
         * Throws IllegalArgumentException if unknown.
         *
         * @param code the type code.
         * @return the enum
         */
        static MobiType convert(int code) {
            switch (code) {
                case 2:
                    return MOBIPOCKET_BOOK;
                case 3:
                    return PALM_DOC_BOOK;
                case 4:
                    return AUDIO;
                case 232:
                    return MOBIPOCKET_GENERATED_BY_KINDELGEN_1_2;
                case 248:
                    return KF8_GENERATED_BY_KINDLEGEN_2;
                case 257:
                    return NEWS;
                case 258:
                    return NEWS_FEED;
                case 259:
                    return NEWS_MAGAZINE;
                case 513:
                    return PICS;
                case 514:
                    return WORD;
                case 515:
                    return XLS;
                case 516:
                    return PPT;
                case 517:
                    return TEXT;
                case 518:
                    return HTML;
                default:
                    throw new IllegalArgumentException("Illegal type code " + code);
            }
        }
    }

    @Override
    public String toString() {
        return "MobiHeader{" +
                "palmDatabaseHeader=" + palmDatabaseHeader +
                ", compression=" + compression +
                ", textLength=" + textLength +
                ", recordCount=" + recordCount +
                ", recordSize=" + recordSize +
                ", encryptionType=" + encryptionType +
                ", identifier='" + identifier + '\'' +
                ", headerLength=" + headerLength +
                ", mobiType=" + mobiType +
                ", encoding=" + encoding +
                ", uniqueId=" + uniqueId +
                ", fileVersion=" + fileVersion +
                ", ortographicIndex=" + ortographicIndex +
                ", inflectionIndex=" + inflectionIndex +
                ", indexNames=" + indexNames +
                ", indexKeys=" + indexKeys +
                ", extraIndex=" + Arrays.toString(extraIndex) +
                ", firstNonBookIndex=" + firstNonBookIndex +
                ", fullNameOffset=" + fullNameOffset +
                ", fullNameLength=" + fullNameLength +
                ", locale=" + locale +
                ", inputLanguage=" + inputLanguage +
                ", outputLanguage=" + outputLanguage +
                ", minVersion=" + minVersion +
                ", firstImageIndex=" + firstImageIndex +
                ", huffmanRecordOffset=" + huffmanRecordOffset +
                ", huffmanRecordCount=" + huffmanRecordCount +
                ", huffmanTableOffset=" + huffmanTableOffset +
                ", huffmanTableLength=" + huffmanTableLength +
                ", hasExth=" + hasExth +
                ", exthHeader=" + exthHeader +
                '}';
    }

}
