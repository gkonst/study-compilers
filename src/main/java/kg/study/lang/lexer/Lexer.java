package kg.study.lang.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Lexer {

    private final BufferedReader reader;
    private int line = 0;
    private int col = 0;
    private String row;

    private Lexer(BufferedReader reader) {
        this.reader = reader;
        readNextLine();
    }

    public static Lexer forStream(InputStream stream) {
        return new Lexer(new BufferedReader(new InputStreamReader(stream)));
    }

    public static Lexer forString(String str) {
        return new Lexer(new BufferedReader(new StringReader(str)));
    }

    public Token next() {
        Token result = null;
        while (result == null) {
            if (row == null) {
                result = Token.EOF;
            } else {
                if (col == row.length()) {
                    readNextLine();
                } else {
                    int ch = row.charAt(col);
                    if (Character.isSpaceChar(ch)) {
                        col++;
                    } else if (Symbol.getMapOfValues().containsKey(ch)) {
                        result = Symbol.getMapOfValues().get(ch);
                        col++;
                    } else if (Character.isDigit(ch)) {
                        result = numberValue();
                    } else if (ch == '"') {
                        result = stringValue();
                        col++;
                    } else if (Character.isLetter(ch)) {
                        StringBuilder sb = new StringBuilder();
                        while (col < row.length() && Character.isLetter(row.charAt(col))) {
                            sb.append(row.charAt(col));
                            col++;
                        }
                        String word = sb.toString();
                        if (Keyword.getMapOfValues().containsKey(word)) {
                            result = Keyword.getMapOfValues().get(word);
                        } else {
                            result = new Identifier(word);
                        }
                    } else {
                        throw new LexerException("Unexpected character : " + (char) ch);
                    }
                }
            }
        }
        return result;
    }

    private Token numberValue() {
        Token result;
        Number value;
        StringBuilder sb = new StringBuilder();
        while (col < row.length() && Character.isDigit(row.charAt(col))) {
            sb.append(row.charAt(col));
            col++;
        }
        if (col < row.length() && row.charAt(col) == '.') {
            sb.append(row.charAt(col));
            col++;
            while (col < row.length() && Character.isDigit(row.charAt(col))) {
                sb.append(row.charAt(col));
                col++;
            }
            value = Float.valueOf(sb.toString());
        } else {
            value = Integer.valueOf(sb.toString());
        }
        result = new Value(value);
        return result;
    }

    private Token stringValue() {
        StringBuilder sb = new StringBuilder();
        col++;
        while (row.charAt(col) != '"') {
            sb.append(row.charAt(col));
            col++;
            if (col == row.length()) {
                throw new LexerException("Illegal line end in string literal");
            }
        }
        return new Value(sb.toString());
    }

    private void readNextLine() {
        try {
            row = reader.readLine();
        } catch (IOException e) {
            throw new LexerException(e);
        }
        if (row != null) {
            line++;
        }
        col = 0;
    }
}
