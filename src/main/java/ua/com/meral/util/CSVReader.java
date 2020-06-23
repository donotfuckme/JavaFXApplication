package ua.com.meral.util;

import org.apache.log4j.Logger;
import ua.com.meral.exception.FileCannotBeProcessedException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CSVReader {

    public static final Logger LOG = Logger.getLogger(CSVReader.class);

    private static final String DATA_SEPARATOR = ",";

    private CSVReader() {
        throw new UnsupportedOperationException("Cannot create instance of CSVReader.class");
    }

    public static List<String[]> read(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String[]> data = new ArrayList<>();

            String str;
            while ((str = br.readLine()) != null) {
                LOG.debug("Read line: " + str);
                data.add(str.split(DATA_SEPARATOR));
            }

            return data;
        } catch (FileNotFoundException e) {
            LOG.error(String.format("Cannot find the file %s", filename), e);
            throw new FileCannotBeProcessedException(String.format("Cannot find the file %s", filename), e);
        } catch (IOException e) {
            LOG.error(String.format("Cannot read the file %s", filename), e);
            throw new FileCannotBeProcessedException(String.format("Cannot read the file %s", filename), e);
        }
    }
}
