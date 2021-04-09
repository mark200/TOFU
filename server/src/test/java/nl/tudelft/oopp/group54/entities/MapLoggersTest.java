package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MapLoggersTest {

    private MapLoggers map;

    @BeforeEach
    public void init() {
        map = MapLoggers.getInstance();
    }

    @Test
    public void testConstructor() {
        assertNotNull(map);
    }

    @Test
    public void testWriteToFileNull() {
        assertDoesNotThrow(() -> {
            map.writeToFile(null, "123");
        });
    }

    @Test
    public void testWriteToFile() throws IOException {
        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("test #1");
        expectedMessages.add("test #2");
        expectedMessages.add("test #3");

        map.setMapValue(31245, expectedMessages);

        map.writeToFile(31245, "../server/Logs/31245.log");

        BufferedReader bufferedReader = new BufferedReader(new FileReader("../server/Logs/31245.log"));

        List<String> actualMessages = bufferedReader.lines().collect(Collectors.toList());

        assertEquals(expectedMessages, actualMessages);

        File file = new File("../server/Logs/31245.log");
        file.delete();
    }

    @Test
    public void testSetMapValueNull() {
        map.setMapValue(null, new ArrayList<>());
        assertDoesNotThrow(() -> Exception.class);
    }

    @Test
    public void testSetMapValue() {
        List<String> actualMessages = new ArrayList<>();
        actualMessages.add("test #1");
        actualMessages.add("test #2");

        map.setMapValue(34213, actualMessages);

        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("test #1");
        expectedMessages.add("test #2");

        assertEquals(expectedMessages, map.map.get(34213));
    }

    @Test
    public void testLogWarningNull() {
        map.logWarning(null, "msg", "some file");
        assertDoesNotThrow(() -> Exception.class);
    }

    @Test
    public void testLogWarning() throws IOException {
        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("test #1");
        expectedMessages.add("test #2");
        expectedMessages.add("test #3");
        expectedMessages.add("test #4");
        expectedMessages.add("test #5");
        expectedMessages.add("test #6");
        expectedMessages.add("test #7");
        expectedMessages.add("test #8");
        expectedMessages.add("test #9");
        expectedMessages.add("test #10");
        expectedMessages.add("test #11");

        map.setMapValue(31245, expectedMessages);

        map.logWarning(31245, "new test #12", "../server/Logs/31245.log");

        BufferedReader bufferedReader = new BufferedReader(new FileReader("../server/Logs/31245.log"));

        List<String> actualMessages = bufferedReader.lines().collect(Collectors.toList());

        assertEquals(12, actualMessages.size());

        File file = new File("../server/Logs/31245.log");
        file.delete();
    }
}
