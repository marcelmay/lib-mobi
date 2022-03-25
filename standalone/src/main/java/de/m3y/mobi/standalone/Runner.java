package de.m3y.mobi.standalone;

import java.io.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.m3y.mobi.core.MobiHeader;

/**
 * CLI runner.
 */
public class Runner {

    public static void main(String[] args) throws IOException {
        if (args.length >= 1) {
            String filename;
            boolean full = false;
            if (args.length == 2) {
                full = "-full".equals(args[0]);
                filename = args[1];
            } else {
                filename = args[0];
            }

            try (final DataInputStream is = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(filename)))) {
                ObjectMapper mapper = new ObjectMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .enable(SerializationFeature.WRAP_ROOT_VALUE)
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true)
                        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                final MobiHeader header = MobiHeader.read(is);
                if (!full) {
                    header.palmDatabaseHeader.records = null;
                }

                mapper.writeValue(System.out, header);
            }
        } else {
            System.err.println("Expect MOBI file as argument");
            usage();
        }
    }

    private static void usage() {
        System.out.println("\nUsage: java -jar libmobi-standalone.jar [-full] mobi-file");
        System.out.println("\nOptions:");
        System.out.println("       -full : includes Palm database records");
    }
}
