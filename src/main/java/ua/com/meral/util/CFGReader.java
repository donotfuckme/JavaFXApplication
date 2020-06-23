package ua.com.meral.util;

import org.apache.log4j.Logger;
import ua.com.meral.exception.FileCannotBeProcessedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class CFGReader {

    public static final Logger LOG = Logger.getLogger(CFGReader.class);

    private CFGReader() {
        throw new UnsupportedOperationException("Cannot create instance of CFGReader.class");
    }

    public static Map<String, String> read(String filename) {
        try {
            File file = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(file));
            Map<String, String> configFile = new HashMap<>();

            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                configFile.put(str.substring(0, str.lastIndexOf('=')), str.substring(str.lastIndexOf('=')));
            }

            return configFile;
        } catch (FileNotFoundException e) {
            LOG.error(String.format("Cannot find the file %s", filename), e);
            throw new FileCannotBeProcessedException(String.format("Cannot find the file %s", filename), e);
        } catch (IOException e) {
            LOG.error(String.format("Cannot read the file %s", filename), e);
            throw new FileCannotBeProcessedException(String.format("Cannot read the file %s", filename), e);
        }
    }
}
