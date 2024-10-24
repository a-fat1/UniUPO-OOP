package model.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class InputReader {
	private BufferedReader reader;

	public InputReader(InputStream input) {
		this.reader = new BufferedReader(new InputStreamReader(input));
	}

	public String getString() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException("fallita lettura input", e);
		}
	}

	public int getInt() {
		try {
			return Integer.parseInt(getString());
		} catch (NumberFormatException e) {
			throw new RuntimeException("valore intero invalido", e);
		}
	}

	public double getDouble() {
		try {
			return Double.parseDouble(getString());
		} catch (NumberFormatException e) {
			throw new RuntimeException("valore decimale invalido", e);
		}
	}

	public boolean isValidAnswer(String answer) {
		return answer.equalsIgnoreCase("s")
            || answer.equalsIgnoreCase("si")
            || answer.equalsIgnoreCase("sì")
            || answer.equalsIgnoreCase("n")
            || answer.equalsIgnoreCase("no");
	}

	public boolean isPositiveAnswer(String answer) {
		return answer.equalsIgnoreCase("s")
            || answer.equalsIgnoreCase("si")
            || answer.equalsIgnoreCase("sì");
	}
}
