package kg.study.lang.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class Lexer {

    private final Reader reader;
    private int ch;

    public Lexer(Reader reader) {
        this.reader = reader;
        readNextChar();
    }

    public static Lexer forStream(InputStream stream) {
        return new Lexer(new BufferedReader(new InputStreamReader(stream)));
    }

    public static Lexer forString(String str) {
        return new Lexer(new StringReader(str));
    }

    public Token next() {
        Token result = null;
        while (result == null) {
            if (ch == -1) {
                result = Token.EOF;
            } else if (Character.isSpaceChar(ch)) {
                readNextChar();
            } else if (Symbol.getMapOfValues().containsKey(ch)) {
                result = Symbol.getMapOfValues().get(ch);
                readNextChar();
            } else if (Character.isDigit(ch)) {
                Number value;
                StringBuilder sb = new StringBuilder();
                while (Character.isDigit(ch)) {
                    sb.append((char) ch);
                    readNextChar();
                }
                if (ch == '.') {
                    sb.append((char) ch);
                    readNextChar();
                    while (Character.isDigit(ch)) {
                        sb.append((char) ch);
                        readNextChar();
                    }
                    value = Float.valueOf(sb.toString());
                } else {
                    value = Integer.valueOf(sb.toString());
                }
                return new Value(value);
            } else if (Character.isLetter(ch)) {
                StringBuilder sb = new StringBuilder();
                while (Character.isLetter(ch)) {
                    sb.append((char) ch);
                    readNextChar();
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
        return result;
    }

    private void readNextChar() {
        try {
            ch = reader.read();
        } catch (IOException e) {
            throw new LexerException(e);
        }
    }
}
