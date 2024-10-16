import elaboration.InputReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class InputReaderTest {
    private final InputStream originalIn = System.in;

    @AfterEach
    public void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    public void testReadLine() {
        String input = "test input\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputReader inputReader = new InputReader(System.in);
        String line = inputReader.readLine();
        assertEquals("test input", line);
    }

    @Test
    public void testReadInt() {
        String input = "42\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputReader inputReader = new InputReader(System.in);
        int number = inputReader.readInt();
        assertEquals(42, number);
    }

    @Test
    public void testReadDouble() {
        String input = "3.14\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputReader inputReader = new InputReader(System.in);
        double number = inputReader.readDouble();
        assertEquals(3.14, number, 0.0001);
    }

    @Test
    public void testReadIntInvalidInput() {
        String input = "invalid\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputReader inputReader = new InputReader(System.in);
        assertThrows(RuntimeException.class, inputReader::readInt);
    }

    @Test
    public void testReadDoubleInvalidInput() {
        String input = "invalid\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputReader inputReader = new InputReader(System.in);
        assertThrows(RuntimeException.class, inputReader::readDouble);
    }
}

