package ua.com.meral.util;

import org.apache.log4j.Logger;
import ua.com.meral.exception.FileCannotBeProcessedException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class CFGReader {

    public static final Logger LOG = Logger.getLogger(CFGReader.class);

    private static final String DATA_SEPARATOR = "=";

    private CFGReader() {
        throw new UnsupportedOperationException("Cannot create instance of CFGReader.class");
    }

    public static Map<String, String> read(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            Map<String, String> options = new HashMap<>();

            String str;
            while ((str = br.readLine()) != null) {
                LOG.debug("Read line: " + str);
                options.put(str.substring(0, str.lastIndexOf(DATA_SEPARATOR)), str.substring(str.lastIndexOf(DATA_SEPARATOR) + 1));
            }

            return options;
        } catch (FileNotFoundException e) {
            LOG.error(String.format("Cannot find the file %s", filename), e);
            throw new FileCannotBeProcessedException(String.format("Cannot find the file %s", filename), e);
        } catch (IOException e) {
            LOG.error(String.format("Cannot read the file %s", filename), e);
            throw new FileCannotBeProcessedException(String.format("Cannot read the file %s", filename), e);
        }
    }
}
