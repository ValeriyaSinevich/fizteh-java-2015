package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class PropertiesLoaderTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testLoadInputStream() throws Exception {
        String s = "/google.properties";
        String wrongS = "abracadabra";
        PropertiesLoader loader = spy(PropertiesLoader.class);

        loader.loadInputStream(s);
        verify(loader.getClass());

        thrown.expect(PropertiesException.class);
        loader.loadInputStream(wrongS);
    }

    @Test
    public void testLoadKey() throws Exception {
        PropertiesLoader loader = new PropertiesLoader();
        Properties googleKeys = new Properties();
        String ans = loader.loadKey(googleKeys);
        assertThat(ans, is("\"AIzaSyB-_tiO6Z9cJusdLmgQoJ_GOAS7lYy3UHU\""));

        try (InputStream inputStream
                     = this.getClass().getResourceAsStream("/google.properties")) {
            doThrow(new IOException()).when(googleKeys).load(inputStream);
            thrown.expect(PropertiesException.class);
            loader.loadKey(googleKeys);
        } catch (Exception e) {
        }

        googleKeys = new Properties();
        PropertiesLoader loader2 = spy(loader);
        doThrow(new Exception()).when(loader2).loadInputStream("/google.properties");
        thrown.expect(PropertiesException.class);
        loader2.loadKey(googleKeys);
    }
}