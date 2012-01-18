package kg.study.lang.lexer;

public class ValueExpression implements Expression {
    private final Object value;

    ValueExpression(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
