package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public String loadKey() throws PropertiesException {
        Properties googleKeys = new Properties();
        try (InputStream inputStream
                     = this.getClass().getResourceAsStream("/google.properties")) {
            googleKeys.load(inputStream);
            return googleKeys.getProperty("key");
        } catch (Exception e) {
            throw new PropertiesException("can't open properties file");
        }
    }
}

