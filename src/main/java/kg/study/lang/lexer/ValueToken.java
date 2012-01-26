package kg.study.lang.lexer;

public class ValueToken implements Token {
    private final Object value;

    ValueToken(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
