package model.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class InputReaderTest {
	private final InputStream originalIn = System.in;

	@AfterEach
	public void tearDown() {
		System.setIn(originalIn);
	}

	@Test
	public void testGetString() {
		String input = "test input\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		InputReader inputReader = new InputReader(System.in);
		String line = inputReader.getString();
		assertEquals("test input", line);
	}

	@Test
	public void testGetInt() {
		String input = "42\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		InputReader inputReader = new InputReader(System.in);
		int number = inputReader.getInt();
		assertEquals(42, number);
	}

	@Test
	public void testGetDouble() {
		String input = "3.14\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		InputReader inputReader = new InputReader(System.in);
		double number = inputReader.getDouble();
		assertEquals(3.14, number, 0.0001);
	}

	@Test
	public void testGetIntInvalidInput() {
		String input = "invalid\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		InputReader inputReader = new InputReader(System.in);
		assertThrows(RuntimeException.class, inputReader::getInt);
	}

	@Test
	public void testGetDoubleInvalidInput() {
		String input = "invalid\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		InputReader inputReader = new InputReader(System.in);
		assertThrows(RuntimeException.class, inputReader::getDouble);
	}

	@Test
	public void testIsValidAnswer() {
		InputReader inputReader = new InputReader(System.in);
		assertTrue(inputReader.isValidAnswer("s"));
		assertTrue(inputReader.isValidAnswer("S"));
		assertTrue(inputReader.isValidAnswer("si"));
		assertTrue(inputReader.isValidAnswer("Si"));
		assertTrue(inputReader.isValidAnswer("sI"));
		assertTrue(inputReader.isValidAnswer("SI"));
		assertTrue(inputReader.isValidAnswer("sì"));
		assertTrue(inputReader.isValidAnswer("Sì"));
		assertTrue(inputReader.isValidAnswer("sÌ"));
		assertTrue(inputReader.isValidAnswer("SÌ"));
		assertTrue(inputReader.isValidAnswer("n"));
		assertTrue(inputReader.isValidAnswer("N"));
		assertTrue(inputReader.isValidAnswer("no"));
		assertTrue(inputReader.isValidAnswer("No"));
		assertTrue(inputReader.isValidAnswer("nO"));
		assertTrue(inputReader.isValidAnswer("NO"));
		
		assertFalse(inputReader.isValidAnswer("yes"));
		assertFalse(inputReader.isValidAnswer("nope"));
		assertFalse(inputReader.isValidAnswer("1"));
		assertFalse(inputReader.isValidAnswer("0"));
	}

	@Test
	public void testIsPositiveAnswer() {
		InputReader inputReader = new InputReader(System.in);
		assertTrue(inputReader.isPositiveAnswer("s"));
		assertTrue(inputReader.isPositiveAnswer("S"));
		assertTrue(inputReader.isPositiveAnswer("si"));
		assertTrue(inputReader.isPositiveAnswer("Si"));
		assertTrue(inputReader.isPositiveAnswer("sI"));
		assertTrue(inputReader.isPositiveAnswer("SI"));
		assertTrue(inputReader.isPositiveAnswer("sì"));
		assertTrue(inputReader.isPositiveAnswer("Sì"));
		assertTrue(inputReader.isPositiveAnswer("sÌ"));
		assertTrue(inputReader.isPositiveAnswer("SÌ"));

		assertFalse(inputReader.isPositiveAnswer("n"));
		assertFalse(inputReader.isPositiveAnswer("N"));
		assertFalse(inputReader.isPositiveAnswer("no"));
		assertFalse(inputReader.isPositiveAnswer("No"));
		assertFalse(inputReader.isPositiveAnswer("nO"));
		assertFalse(inputReader.isPositiveAnswer("NO"));
	}
}
