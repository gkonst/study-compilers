package kg.study.lang.lexer;

public class LexerException extends RuntimeException {
    public LexerException(String message) {
        super(message);
    }

    public LexerException(Throwable cause) {
        super(cause);
    }
}
