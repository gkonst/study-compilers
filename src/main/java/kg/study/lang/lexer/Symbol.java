package kg.study.lang.lexer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Symbol implements Token {
    LBRA('{'),
    RBRA('}'),
    ASSIGN('='),
    SEMICOLON(';'),
    LPAR('('),
    RPAR(')'),
    PLUS('+'),
    MINUS('-'),
    LT('<'),
    GT('>');

    private final char text;
    public static final Map<Character, Symbol> VALUES;

    private Symbol(char text) {
        this.text = text;
    }

    static {
        Map<Character, Symbol> map = new HashMap<>();
        for (Symbol symbol : values()) {
            map.put(symbol.text, symbol);
        }
        VALUES = Collections.unmodifiableMap(map);
    }
}
