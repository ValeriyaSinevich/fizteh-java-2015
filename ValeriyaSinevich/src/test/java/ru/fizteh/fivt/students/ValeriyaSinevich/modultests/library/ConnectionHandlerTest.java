package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import twitter4j.JSONObject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConnectionHandlerTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testReturnJsonString() throws Exception {
        ConnectionHandler ch = new ConnectionHandler();
        String query = "http://ipinfo.io/json";
        String wrongQuery = "some://extremely.meaningful/abracadabra/catchmeifyoucan/youshallnotpass/wewillremeberyou.Gandalf/json";

        JSONObject s = ch.returnJsonString(query);
        assertThat(s.getString("city"), is("Dolgoprudnyy"));

        thrown.expect(LocationException.class);
        ch.returnJsonString(wrongQuery);
    }
}