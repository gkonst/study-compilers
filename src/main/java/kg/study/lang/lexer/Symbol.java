package kg.study.lang.lexer;

import java.util.HashMap;
import java.util.Map;

public enum Symbol implements Token {
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
    private static Map<Integer, Symbol> mapOfValues;

    private Symbol(char text) {
        this.text = text;
    }

    public static Map<Integer, Symbol> getMapOfValues() {
        if (mapOfValues == null) {
            mapOfValues = new HashMap<>();
            for (Symbol symbol : values()) {
                mapOfValues.put((int) symbol.text, symbol);
            }
        }
        return mapOfValues;
    }
}
