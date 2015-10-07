package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.*;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.PropertiesException;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public String loadKey() throws ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException {
        Properties googleKeys = new Properties();
        try (InputStream inputStream
                     = this.getClass().getResourceAsStream("/google.properties")) {
            googleKeys.load(inputStream);
            return googleKeys.getProperty("key");
        } catch (Exception e) {
            throw new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException("can't open properties file");
        }
    }
}

