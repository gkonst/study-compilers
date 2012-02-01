package kg.study.lang.lexer;

public class Value implements Token {
    private final Object value;

    Value(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
