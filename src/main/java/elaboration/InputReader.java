package elaboration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class InputReader {
    private BufferedReader reader;

    public InputReader(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("fallita lettura input", e);
        }
    }

    public int readInt() {
        try {
            return Integer.parseInt(readLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("valore intero invalido", e);
        }
    }

    public double readDouble() {
        try {
            return Double.parseDouble(readLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("valore decimale invalido", e);
        }
    }
}
