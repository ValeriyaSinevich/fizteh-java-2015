package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class PropertiesLoaderTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testLoadInputStream() throws Exception {
        String s = "/google.properties";
        String wrongS = "abracadabra";
        PropertiesLoader loader = new PropertiesLoader();

        PropertiesLoader loader2 = spy(loader);

        loader2.loadInputStream(s);
        verify(loader2).getClass();

        InputStream in = loader.loadInputStream(wrongS);
        assertThat(in, is(nullValue()));

    }

    @Test
    public void testLoadKey() throws Exception {
        PropertiesLoader loader = new PropertiesLoader();
        Properties googleKeys = mock(Properties.class);
        doNothing().when(googleKeys).load(any(InputStream.class));
        when(googleKeys.getProperty("key")).thenReturn("\"AIzaSyB-_tiO6Z9cJusdLmgQoJ_GOAS7lYy3UHU\"");
        String ans = loader.loadKey(googleKeys);
        assertThat(ans, is("\"AIzaSyB-_tiO6Z9cJusdLmgQoJ_GOAS7lYy3UHU\""));

        try (InputStream inputStream
                     = this.getClass().getResourceAsStream("/google.properties")) {
            doThrow(IOException.class).when(googleKeys).load(inputStream);
            thrown.expect(PropertiesException.class);
            loader.loadKey(googleKeys);
        } catch (Exception e) {
        }

        googleKeys = new Properties();
        PropertiesLoader loader2 = spy(loader);
        doThrow(new PropertiesException("")).when(loader2).loadInputStream(anyString());
        thrown.expect(PropertiesException.class);
        loader2.loadKey(googleKeys);
    }
}