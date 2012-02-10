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
        while (true) {
            if (row == null) {
                return Token.EOF;
            }

            if (col == row.length()) {
                readNextLine();
                continue;
            }

            int ch = currentChar();

            if (Character.isSpaceChar(ch)) {
                col++;
            } else if (Symbol.getMapOfValues().containsKey(ch)) {
                col++;
                return Symbol.getMapOfValues().get(ch);
            } else if (Character.isDigit(ch)) {
                return numberValue();
            } else if (ch == '"') {
                return stringValue();
            } else if (Character.isLetter(ch)) {
                return identifierOrKeyword();
            } else {
                throw new LexerException("Unexpected character : " + (char) ch);
            }
        }
    }

    private Token identifierOrKeyword() {
        StringBuilder sb = new StringBuilder();
        char ch = currentChar();
        while (Character.isLetter(ch)) {
            sb.append(ch);
            ch = nextChar();
        }
        String word = sb.toString();
        if (Keyword.getMapOfValues().containsKey(word)) {
            return Keyword.getMapOfValues().get(word);
        } else {
            return new Identifier(word);
        }
    }

    private Token numberValue() {
        Token result;
        Number value;
        StringBuilder sb = new StringBuilder();
        char ch = currentChar();
        while (Character.isDigit(ch)) {
            sb.append(ch);
            ch = nextChar();
        }
        if (ch == '.') {
            sb.append(ch);
            ch = nextChar();
            while (Character.isDigit(ch)) {
                sb.append(ch);
                ch = nextChar();
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
        char ch = nextChar();
        while (ch != '"') {
            sb.append(ch);
            ch = nextChar();
            if (col == row.length()) {
                throw new LexerException("Illegal line end in string literal");
            }
        }
        col++;
        return new Value(sb.toString());
    }

    private char nextChar() {
        col++;
        return currentChar();
    }

    private char currentChar() {
        if (col < row.length()) {
            return row.charAt(col);
        } else {
            return '\n';
        }
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
