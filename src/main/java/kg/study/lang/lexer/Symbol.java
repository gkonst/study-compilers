package kg.study.lang.lexer;

import java.util.HashMap;
import java.util.Map;

public enum Symbol implements Expression {
    LBRA('{'),
    RBRA('}'),
    EQ('='),
    SEMICOLON(';'),
    LPAR('('),
    RPAR(')'),
    PLUS('+'),
    MINUS('-'),
    LESS('<');

    private final char text;
    private static Map<Character, Symbol> mapOfValues;

    private Symbol(char text) {
        this.text = text;
    }

    public static Map<Character, Symbol> getMapOfValues() {
        if (mapOfValues == null) {
            mapOfValues = new HashMap<>();
            for (Symbol symbol : values()) {
                mapOfValues.put(symbol.text, symbol);
            }
        }
        return mapOfValues;
    }
}
