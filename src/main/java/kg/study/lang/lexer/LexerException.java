package kg.study.lang.lexer;

public class LexerException extends RuntimeException {
    private final int row;
    private final int col;

    public LexerException(String message, int row, int col) {
        super(message);
        this.row = row;
        this.col = col;
    }

    public LexerException(Throwable cause, int row, int col) {
        super(cause);
        this.row = row;
        this.col = col;
    }

    @Override
    public String getMessage() {
        return String.format("%s [%d:%d]", super.getMessage(), row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
