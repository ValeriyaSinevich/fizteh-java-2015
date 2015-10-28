package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public InputStream loadInputStream(String s) throws PropertiesException {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(s);
            return inputStream;
        } catch (Exception e) {
            throw new
                    ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException("can't open properties file\n");
        }
    }

    public String loadKey(Properties googleKeys) throws ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException {
        try (InputStream inputStream = loadInputStream("/google.properties")) {
                googleKeys.load(inputStream);
                return googleKeys.getProperty("key");
         } catch (IOException ex) {
            throw new PropertiesException("can't read Google\n");
        }
    }
}
